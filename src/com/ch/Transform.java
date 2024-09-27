package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * Represents a 3D transformation with position, rotation, and scale, and allows for
 * recursive inheritance of transformations from parent objects.
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
	 * Maintains a history of object transformations by comparing current and previous
	 * positions, rotations, and scales. It updates the previous values if they have
	 * changed. If no previous values exist, it initializes them.
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
	 * Rotates the current rotation `rot` by a specified angle around a given axis,
	 * updating `rot` to the resulting quaternion. The rotation is performed by multiplying
	 * the new rotation quaternion by the current `rot` and normalizing the result.
	 *
	 * @param axis axis around which the rotation is performed.
	 *
	 * @param angle amount of rotation, specified in radians, around the specified axis.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Rotates the object to face a specified point in 3D space. It uses the `getLookAtRotation`
	 * method to calculate the necessary rotation. The rotation is based on the object's
	 * current position and a specified up direction.
	 *
	 * @param point target location that the rotation is calculated to face.
	 *
	 * @param up direction of the world's Y-axis in the final orientation, used to determine
	 * the rotation of the object.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Calculates a quaternion representing a rotation to face a specified point while
	 * maintaining a specified up direction. It uses the `pos` vector as the origin and
	 * performs the calculation based on the normalized vector difference between the
	 * `pos` and the `point`.
	 *
	 * @param point target point in 3D space relative to the object's position (`pos`),
	 * used to calculate the rotation.
	 *
	 * @param up world-up direction that the resulting rotation should preserve.
	 *
	 * @returns a Quaternion representing the rotation needed to face the specified point
	 * while maintaining the specified up direction.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * Determines whether the object's parent, position, rotation, or scale has changed
	 * from its previous state, returning `true` if any of these values have changed and
	 * `false` otherwise.
	 *
	 * @returns a boolean value indicating whether the object's properties have changed
	 * since the last check.
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
	 * Calculates a 4x4 transformation matrix based on the object's position, rotation,
	 * and scale. It multiplies the individual transformation matrices for translation,
	 * rotation, and scale to produce a combined transformation matrix. The combined
	 * matrix is then returned.
	 *
	 * @returns a 4x4 transformation matrix combining translation, rotation, and scale transformations.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * Returns the transformation matrix of the parent object if it exists and has been
	 * changed.
	 *
	 * @returns a 4x4 transformation matrix representing the parent's transformation.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Assigns a `Transform` object to the `parent` field, establishing a parent-child
	 * relationship between the current object and the specified transform.
	 *
	 * @param parent Transform object that the current object is being set as a child of.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Transforms the current position by applying the parent matrix, returning the
	 * resulting position.
	 *
	 * @returns a Vector3f representing the position transformed by the parent matrix.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * Calculates the transformed rotation of an object by multiplying its local rotation
	 * with its parent's rotation. If the object has no parent, its local rotation is
	 * returned unchanged.
	 *
	 * @returns a quaternion representing the combined rotation of the current object and
	 * its parent object.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * Returns the current position as a Vector3f object.
	 *
	 * @returns a Vector3f representing the position.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Updates the position of an object by assigning a new value to its `pos` attribute,
	 * which is a 3D vector. The new position is set to the provided `pos` parameter.
	 *
	 * @param pos position to be assigned to the object's position.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Modifies the current position by adding a specified vector to it.
	 *
	 * @param addVec vector to be added to the current position.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * Returns the value of the `rot` variable, which represents a quaternion object.
	 * This quaternion likely represents a rotation in 3D space. The function provides
	 * read-only access to the rotation data.
	 *
	 * @returns an instance of Quaternion class, representing a rotation.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Assigns a Quaternion object to the `rot` variable, updating the object's rotation.
	 * The assigned rotation replaces any previous rotation. The `rot` variable now holds
	 * the specified Quaternion object.
	 *
	 * @param rotation new rotation to be set for the object.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * Returns the current scale value of an object, represented as a Vector3f.
	 *
	 * @returns a Vector3f object representing the scale of an object.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Updates the object's scale property with the provided `Vector3f` value, effectively
	 * setting the new scale.
	 *
	 * @param scale new scale value to be set for an object.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	@Override
	public String toString() { return "";
	}

}
