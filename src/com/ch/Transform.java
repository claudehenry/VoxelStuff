package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * Is a versatile class for representing 3D transformations in computer graphics. It
 * has fields for position (pos), rotation (rot), and scale (scale), as well as methods
 * for rotating, looking at a point, and getting the transformation matrix. The class
 * also has an update method that checks if the transform has changed and calls the
 * appropriate methods to update the fields accordingly. Additionally, it has a parent
 * field to represent the parent transform in a hierarchical transformation tree.
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
	 * Updates the position, rotation, and scale of an object based on its previous values,
	 * storing the new values for future use.
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
	 * Rotates a vector by an angle around a specified axis, resulting in a normalized
	 * quaternion representation of the rotation.
	 * 
	 * @param axis 3D axis of rotation.
	 * 
	 * @param angle 3D rotation angle around the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Calculates and returns the rotation required to face a specified point while
	 * maintaining a specific orientation relative to the up direction.
	 * 
	 * @param point 3D position of an object that the player is looking at.
	 * 
	 * @param up 3D direction of the "up" vector relative to the current orientation of
	 * the camera, which is used to calculate the rotation needed to look at a specified
	 * point from the camera's perspective.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Computes a quaternion representing the rotation from the camera's current position
	 * to look at a point in the world, with a specified up direction.
	 * 
	 * @param point 3D position of an object that the look-at rotation is being calculated
	 * for.
	 * 
	 * @param up 3D direction of the "up" axis in the rotation matrix, which is used to
	 * calculate the rotational part of the quaternion.
	 * 
	 * @returns a Quaternion representation of the rotation required to face a given point
	 * while looking along a specified up direction.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * Checks if any of its attributes have changed, returning `true` if they have and
	 * `false` otherwise.
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
	 * Computes and returns a transformation matrix based on the position, rotation, and
	 * scale of an object. It takes the parent matrix as input and multiplies it with the
	 * translation, rotation, and scaling matrices to produce the final transformation matrix.
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
	 * Computes and returns the matrix representation of its parent transformation, based
	 * on the current state of the parent transformation.
	 * 
	 * @returns a Matrix4f representation of the parent transformation matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Sets the `Transform` reference for the object.
	 * 
	 * @param parent Transform to which the current instance is being added as a child
	 * transform, and its value is assigned to the `parent` field of the current instance.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Computes the transformed position of an object based on a provided matrix, applying
	 * a transformation using the given parent matrix.
	 * 
	 * @returns a transformed position vector in a 3D space.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * Takes a `Quaternion` object `parentRotation` and multiplies it by another `Quaternion`
	 * object `rot` to get the transformed rotation.
	 * 
	 * @returns a Quaternion representing the transformed rotation of the parent object
	 * based on its own rotation and the provided rotation vector.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * Returns the position of an object in a 3D space.
	 * 
	 * @returns a `Vector3f` object representing the position of the game entity.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Sets the position of an object to a specified Vector3f value.
	 * 
	 * @param pos 3D position of an object or entity in the function `setPos`.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * Returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a `Quaternion` object representing the rotation of the entity.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Sets the rotation of an object by assigning a Quaternion value to its internal
	 * `rot` field, effectively modifying its orientation in 3D space.
	 * 
	 * @param rotation 3D rotation of the object to which the `setRot` method belongs,
	 * and it is used to set the rotation of that object.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * Returns the current scale value of the entity it is called on.
	 * 
	 * @returns a `Vector3f` object representing the scale of the entity.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Sets the scale of the object to which it is called, by assigning a new `Vector3f`
	 * value to the `scale` field of the object.
	 * 
	 * @param scale 3D vector that determines the scaling of the object to which the
	 * method is applied.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * Simply returns an empty string.
	 * 
	 * @returns an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
