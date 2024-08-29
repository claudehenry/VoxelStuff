package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * Represents a transform in 3D space, which includes position, rotation, and scale.
 * It provides methods to update its transformation, rotate around an axis, look at
 * a point, and get the transformed position and rotation.
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
	 * Updates three variables: `oldPos`, `oldRot`, and `oldScale`. It compares these
	 * with their respective current values (`pos`, `rot`, and `scale`) to determine
	 * whether they have changed. If a change is detected, the old value is updated to
	 * match the new one.
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
	 * Normalizes a quaternion by rotating it around a specified axis by a given angle
	 * and then multiplying it with the existing rotation quaternion `rot`. This effectively
	 * updates the rotation quaternion to represent the combined rotation.
	 *
	 * @param axis 3D vector that defines the rotation axis around which the object is
	 * rotated by the specified angle.
	 *
	 * @param angle 3D rotational angle around the specified `axis`, used to compute the
	 * new quaternion value by multiplying the current rotation with the axis-angle
	 * rotation and normalizing the result.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Calculates a rotation matrix based on a specified point and upward direction. The
	 * result is stored in the `rot` variable. This function appears to be used for camera
	 * orientation or object transformation in a 3D graphics context.
	 *
	 * @param point 3D location of interest that the camera should be oriented towards.
	 *
	 * @param up 3D vector defining the direction of the upward axis in the new coordinate
	 * system after the rotation is applied to align with the specified point.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Calculates a quaternion representing the rotation from a specified position to
	 * another point while maintaining a specific up direction. It returns this quaternion,
	 * which can be used for 3D transformations. The result is based on the normalized
	 * difference vector between the two points and the given up vector.
	 *
	 * @param point 3D point that defines the direction of rotation, used to calculate
	 * the rotation quaternion for looking at that point.
	 *
	 * @param up 3D vector defining the upward direction from which the rotation is
	 * calculated to look at the specified `point`.
	 *
	 * @returns a quaternion representing rotation to look at a specified point.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * Returns whether a transformation has changed compared to its previous state. It
	 * checks if the parent's change status is true, or if the position, rotation, or
	 * scale values have changed from their old values.
	 *
	 * @returns a boolean value indicating whether any attribute has changed.
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
	 * Creates a transformation matrix by multiplying four matrices together: translation,
	 * rotation, and scale. The result is then combined with the parent matrix using multiplication.
	 *
	 * @returns a transformed matrix combining translation, rotation, and scaling.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * Returns a matrix representing the transformation of the object's parent node, if
	 * available and has changed since last update. If no parent is present or its
	 * transformation hasn't changed, an empty matrix is returned.
	 *
	 * @returns a `Matrix4f` object representing the transformed parent matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Assigns a value to the `parent` variable, setting it to the specified `Transform`
	 * object. This establishes a relationship between the current object and its new
	 * parent object. The function modifies the internal state of the object by updating
	 * the reference to the parent object.
	 *
	 * @param parent Transform object that is assigned to the instance variable `this.parent`.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Applies a transformation to a position `pos` based on its parent matrix and returns
	 * the resulting transformed position as a `Vector3f`.
	 *
	 * @returns a transformed vector position based on the parent matrix.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * Combines a quaternion representing an object's rotation with its parent's transformed
	 * rotation, if it exists. It returns the result of multiplying these two quaternions
	 * together. This operation allows for hierarchical transformations of rotations in
	 * 3D space.
	 *
	 * @returns a quaternion resulting from the multiplication of two quaternions.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * Returns a `Vector3f` object representing the position.
	 *
	 * @returns a `Vector3f`, which represents a position with three floating-point values.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Updates the position of an object to a specified `Vector3f` value, assigning it
	 * to the instance variable `pos`. This allows the object's position to be modified
	 * and updated throughout its lifetime. The new position is stored for future use or
	 * processing.
	 *
	 * @param pos 3D position to be assigned to the object's position attribute, which
	 * is updated within the method.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Modifies the current position by adding a given vector to it, updating the new
	 * position and assigning it back to itself.
	 *
	 * @param addVec 3D vector to be added to the current position of an object, which
	 * is then updated accordingly.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * Retrieves a quaternion value represented by the `rot` variable and returns it. The
	 * returned quaternion value is a mathematical entity that can be used for 3D rotations,
	 * transformations, or other geometric calculations. It provides direct access to the
	 * stored rotation information.
	 *
	 * @returns a `Quaternion` object.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Assigns a new value to the instance variable `rot`, which represents a Quaternion
	 * object representing an orientation or rotation. The function takes one parameter,
	 * `rotation`, and updates the internal state of the object with this new value.
	 *
	 * @param rotation 3D rotational transformation that is assigned to the internal `rot`
	 * variable of the object.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * Returns a `Vector3f` object representing the current scale. It retrieves and exposes
	 * the internal `scale` variable, allowing access to its values without modifying
	 * them directly. The function does not perform any computations or operations on the
	 * data, simply providing a read-only view of the stored scale information.
	 *
	 * @returns a `Vector3f` object representing a three-dimensional scale.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Assigns a new value to the `scale` property, which is expected to be an instance
	 * of `Vector3f`. This updates the internal state of the object with the provided
	 * scaling values.
	 *
	 * @param scale 3D vector to be assigned to the `scale` attribute of the class,
	 * replacing its current value.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * Returns an empty string when called. This indicates that the object does not provide
	 * a meaningful string representation and is likely used for objects that do not need
	 * to be converted to strings or are abstract entities.
	 *
	 * @returns an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
