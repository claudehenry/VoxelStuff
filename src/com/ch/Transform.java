package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * Represents an object with position, rotation, and scale attributes in 3D space.
 * It provides various methods for updating transformations, calculating changes, and
 * retrieving transformed positions and rotations. The class also supports hierarchical
 * transformations through a parent-child relationship.
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
	 * Compares current positions, rotations, and scales with previous ones. If they
	 * differ, it updates the old values to match the new ones. If no old values exist,
	 * it initializes them with the current values for future comparisons.
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
	 * Transforms a quaternion `rot` by rotating it around an axis by a specified angle.
	 * The rotation is performed using a new quaternion generated from the given axis and
	 * angle, which is then multiplied with the original quaternion and normalized to
	 * ensure proper orientation.
	 *
	 * @param axis 3D vector around which the rotation is performed.
	 *
	 * @param angle rotation angle around the specified `axis`, which is used to create
	 * a new quaternion that combines with the current `rot` quaternion through multiplication.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Calculates a rotation based on a given point and up vector. The calculated rotation
	 * is stored in the `rot` variable. This function appears to be used for camera
	 * positioning or object orientation in 3D graphics, aligning the view with the
	 * specified point while maintaining the up direction.
	 *
	 * @param point 3D coordinates of the target point to be looked at by the rotation
	 * operation, influencing the resulting orientation.
	 *
	 * @param up 3D vector that defines the upward direction from which the rotation is
	 * computed for looking at a given point.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Calculates a quaternion rotation to orient an object towards a specified point
	 * while maintaining a certain up direction. It takes two input vectors, `point` and
	 * `up`, representing the target position and desired orientation axis, respectively.
	 *
	 * @param point 3D point to look at, which is used to calculate the rotation matrix
	 * of the quaternion object.
	 *
	 * @param up 3D vector that defines the upward direction of the rotation, used to
	 * construct the rotation matrix along with the target point.
	 *
	 * @returns a Quaternion object representing rotation.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * Returns a boolean indicating whether an object's properties have changed or not.
	 * It checks if the parent, position, rotation, or scale has been modified since the
	 * last check. If any of these properties have changed, it returns true; otherwise,
	 * it returns false.
	 *
	 * @returns a boolean indicating whether the object's state has changed.
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
	 * Returns a transformation matrix combining position, rotation, and scale transformations.
	 * It initializes matrices for translation, rotation, and scaling based on input
	 * positions and scales, then multiplies them together with a parent matrix to produce
	 * the final transformation.
	 *
	 * @returns a transformed matrix representing position, rotation, and scaling.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * Retrieves a transformation matrix from its parent object, if available and changed.
	 * It returns the retrieved matrix.
	 *
	 * @returns a `Matrix4f` object representing the transformed matrix of the parent.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Assigns a value to the `parent` variable. It sets the current object's parent
	 * transform to the specified `Transform` object, effectively establishing a hierarchical
	 * relationship between the two transforms. The function updates the internal state
	 * of the object with the provided parent reference.
	 *
	 * @param parent Transform object to be assigned as the new parent of the current
	 * Transform object.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Transforms a position using the parent matrix and returns the result as a Vector3f
	 * object. It obtains the parent matrix through the `getParentMatrix` method and
	 * applies it to the original position `pos`. The transformed position is then returned.
	 *
	 * @returns a transformed position vector resulting from matrix multiplication.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * Returns a Quaternion transformed by its own rotation (`rot`) and the parent's
	 * rotation, if it exists. If there is no parent, it uses an identity Quaternion as
	 * the base. The result is obtained by multiplying the two Quaternions together.
	 *
	 * @returns a Quaternion object resulting from multiplying two Quaternions.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * Returns a `Vector3f` object representing the position. It retrieves and exposes
	 * the current position value to other parts of the program for use or further
	 * processing. The returned vector contains three floating-point values for x, y, and
	 * z coordinates.
	 *
	 * @returns a `Vector3f` object representing the current position.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Updates the value of an object's position to a specified `Vector3f` instance,
	 * replacing any previous value. The updated position is stored as a member variable
	 * within the object.
	 *
	 * @param pos 3D position to be assigned to the object's current position, replacing
	 * any previous value.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Adds a specified vector to the current position and updates it.
	 *
	 * @param addVec 3D vector to be added to the current position of an object, updating
	 * its position accordingly.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * Returns a quaternion value, represented by the `rot` variable, without performing
	 * any operations on it. This allows access to the stored rotation value from outside
	 * the class or method. The returned quaternion provides information about an object's
	 * orientation in three-dimensional space.
	 *
	 * @returns a Quaternion object named `rot`.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Assigns a Quaternion object to an instance variable named `rot`, updating its
	 * internal state with the provided rotation data. The updated rotation value is
	 * stored for future use. This function does not perform any calculations or
	 * transformations on the input data, simply storing it.
	 *
	 * @param rotation 3D Quaternion object to be assigned to the instance variable `rot`.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * Retrieves a vector containing scaling factors for an object and returns it. The
	 * retrieved scale is not modified by this operation. This function allows accessing
	 * the current scale values without altering them.
	 *
	 * @returns a `Vector3f` object representing the scale values.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Sets a new value for the instance variable `scale`, which is a three-dimensional
	 * vector representing the scaling factors for the object. The new value is assigned
	 * from the `scale` parameter passed to the method. This updates the scale of the
	 * object accordingly.
	 *
	 * @param scale 3D vector to be assigned as the new value for the instance variable
	 * `this.scale`.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * Returns an empty string when invoked.
	 *
	 * @returns an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
