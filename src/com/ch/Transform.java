package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * Is an extension of the Math package, allowing for rotations and scale changes in
 * 3D space. It has several public methods such as update() to update the transform
 * matrix, rotate() and lookAt() to change the orientation, and getTransformedPos()
 * and getTransformedRot() to return transformed positions and rotations respectively.
 * Additionally, it has properties like pos, rot, and scale that can be modified, and
 * a parent field that can link this transform to another one in a hierarchy.
 */
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
	 * Updates an object's position, rotation, and scale based on its previous values or
	 * initializes them if not provided.
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
	 * Multiplies a quaternion representing a rotation axis by an angle and normalizes
	 * the result, returning a new quaternion representing the rotated orientation.
	 * 
	 * @param axis 3D rotation axis around which the rotation will be performed.
	 * 
	 * @param angle 3D rotation angle of the object around the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Computes the rotation required to face a specified point while maintaining a
	 * specific direction perpendicular to the plane of the point. The rotation is returned
	 * as a `Quaternion` object.
	 * 
	 * @param point 3D position of an object that the `lookAt()` method should rotate around.
	 * 
	 * @param up 3D direction perpendicular to the look-at point, which is used to calculate
	 * the rotation necessary for the camera to face that direction.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Computes a rotation quaternion that looks at a specified point while orienting
	 * along a supplied up vector.
	 * 
	 * @param point 3D position from which to look at the scene.
	 * 
	 * @param up 3D direction that the look-at rotation should be applied to, relative
	 * to the position of the object being rotated.
	 * 
	 * @returns a quaternion representing the rotation needed to look at a point in the
	 * environment from a specific position and orientation.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * Checks if an object's properties have changed from their previous values, returning
	 * `true` if any change was detected and `false` otherwise.
	 * 
	 * @returns a boolean indicating whether any of the object's properties have changed.
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
	 * Computes the transformation matrix for an object based on its position, rotation,
	 * and scale. It takes the position, rotation, and scale values as input and returns
	 * the transformed matrix as output.
	 * 
	 * @returns a matrix representation of a transformation consisting of translation,
	 * rotation, and scaling.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * Retrieves and returns the transformation matrix of its parent node in the scene
	 * graph, taking into account changes made to the parent node's transformation.
	 * 
	 * @returns a `Matrix4f` representation of the transformation matrix of the parent node.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Sets the `Transform` field `parent`.
	 * 
	 * @param parent Transform to which the current Transform should be set as a child.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Transforms a `Vector3f` object using a matrix representation, returning the
	 * transformed position.
	 * 
	 * @returns a transformed position vector based on the parent matrix.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * Takes a `Quaternion` object `parentRotation` and multiplies it with another
	 * `Quaternion` object `rot`, returning the transformed rotation.
	 * 
	 * @returns a Quaternion representation of the transformed rotation matrix multiplied
	 * by the input rotation matrix `rot`.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * Returns a `Vector3f` object containing the position of an entity.
	 * 
	 * @returns a `Vector3f` object representing the position of the entity.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Sets the position of an object to a specified value.
	 * 
	 * @param pos 3D position of an object or entity that the method is called on, and
	 * assigns it to the `pos` field of the class instance.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Adds a vector to the position component of its object parameter, by first getting
	 * the current position and then adding the provided vector to it.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object, which
	 * is then updated using the `setPos()` method.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * Returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a Quaternion object representing the robot's rotation.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Sets the instance field `rot` to the given `Quaternion` representation of a rotation
	 * matrix.
	 * 
	 * @param rotation 4D quaternion that updates the rotational state of the object,
	 * which is stored in the `rot` field of the class.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * Returns the object's scale value as a `Vector3f` instance.
	 * 
	 * @returns a `Vector3f` object representing the scale of the game object.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Sets the scale of an object, replacing its previous value.
	 * 
	 * @param scale 3D scaling factor for the object, which is applied to its geometry.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * Returns an empty string.
	 * 
	 * @returns an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
