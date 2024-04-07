package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

public class Transform {

	private Transform parent;
	private Matrix4f parentMatrix;

	private Vector3f pos;
	private Quaternion rot;
	private Vector3f scale;

	private Vector3f oldPos;
	private Quaternion oldRot;
	private Vector3f oldScale;

	public Transform() {
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(1, 0, 0, 0);
		scale = new Vector3f(1, 1, 1);
		
		oldPos = new Vector3f(0, 0, 0);
		oldRot = new Quaternion(1, 0, 0, 0);
		oldScale = new Vector3f(1, 1, 1);

		parentMatrix = new Matrix4f().initIdentity();
	}

	/**
	 * updates the `pos`, `rot`, and `scale` variables of an object based on their current
	 * values and the given ones, storing the old values in new vectors for future use.
	 */
	public void update() {
		if (oldPos != null) {
			if (!oldPos.equals(pos))
				oldPos.set(pos);
			if (!oldRot.equals(rot))
				oldRot.set(rot);
			if (!oldScale.equals(scale))
				oldScale.set(scale);
		} else {
			oldPos = new Vector3f().set(pos);
			oldRot = new Quaternion().set(rot);
			oldScale = new Vector3f().set(scale);
		}
	}

	/**
	 * transforms a `Vector3f` object by multiplying it with a quaternion representing a
	 * rotation around a specified axis, and normalizing the result.
	 * 
	 * @param axis 3D axis around which the rotation will occur.
	 * 
	 * @param angle 3D rotation angle about the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * calculates and returns a rotation matrix that aligns the object's local axis with
	 * a provided point and up vector in the world frame of reference.
	 * 
	 * @param point 3D position of an object or point of interest that the `lookAt()`
	 * method is rotating towards.
	 * 
	 * @param up 3D direction perpendicular to the line of sight and towards which the
	 * camera will look.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a rotation matrix to orient the object's forward vector towards a given
	 * point while keeping the object's up vector parallel to its own z-axis.
	 * 
	 * @param point 3D position from which the look-at rotation is to be computed.
	 * 
	 * @param up 3D direction perpendicular to the line connecting the `pos` parameter
	 * to the `point` parameter, which is used to calculate the rotation quaternion for
	 * looking at the `point` from the `pos`.
	 * 
	 * @returns a quaternion representing the rotation from the object's current position
	 * to look at a point in space.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * checks if any of its attributes have changed by comparing them to their previous
	 * values. If any have, the function returns `true`. Otherwise, it returns `false`.
	 * 
	 * @returns a boolean value indicating whether the object has changed.
	 */
	public boolean hasChanged() {
		if (parent != null && parent.hasChanged())
			return true;

		if (!pos.equals(oldPos))
			return true;

		if (!rot.equals(oldRot))
			return true;

		if (!scale.equals(oldScale))
			return true;

		return false;
	}

	/**
	 * generates a transformation matrix based on the position, rotation, and scale of
	 * an object. It returns the multiplication of the parent matrix, translation matrix,
	 * rotation matrix, and scale matrix.
	 * 
	 * @returns a transformed matrix representing the combination of translation, rotation,
	 * and scaling.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves and returns the transformation matrix of the parent node in a scene
	 * graph, taking into account any changes made to the parent node's transformation.
	 * 
	 * @returns a Matrix4f representation of the transformation matrix of the parent node.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the `Transform` reference of an object to a given `Transform` object.
	 * 
	 * @param parent 3D transform to which the current transform will be attached as a child.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * returns a transformed position of a GameObject, based on the transformation matrix
	 * of its parent GameObject.
	 * 
	 * @returns a transformed position value in a Vector3f format.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * calculates and returns a rotated Quaternion representation of its input, based on
	 * the provided parent rotation and multiplication operation.
	 * 
	 * @returns a transformed quaternion representing the combination of the parent
	 * rotation and the given rotation.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns the position of an object in a 3D space as a `Vector3f` object.
	 * 
	 * @returns a `Vector3f` object representing the position of the entity.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a specified Vector3f value.
	 * 
	 * @param pos 3D position of an object in the scene, which is assigned to the `pos`
	 * field of the class instance.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * updates the position of an object by adding a vector to its current position.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns the current rotation of an object in a Quaternion format, representing its
	 * orientation in 3D space.
	 * 
	 * @returns a `Quaternion` object representing the rotation of the object.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets a quaternion value to a class instance field `rot`.
	 * 
	 * @param rotation 4D rotational transformation that is applied to the object, modifying
	 * its orientation in 3D space.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * returns the scale value of the current GameObject.
	 * 
	 * @returns a vector representing the scale of the object.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scaling factors for an object's position, size, and rotation.
	 * 
	 * @param scale 3D scaling factor for the GameObject, which is applied to its dimensions
	 * upon calling the `setScale()` method.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * simply returns an empty string, without any additional functionality or modification
	 * to the original code.
	 * 
	 * @returns an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
