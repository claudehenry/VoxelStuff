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
	 * Takes a rotation axis and an angle as input and returns a normalized quaternion
	 * representing the rotated state.
	 * 
	 * @param axis 3D vector that defines the rotation axis for the rotation operation.
	 * 
	 * @param angle 3D rotation angle around the axis provided by `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Computes and sets the rotation required to face a specified point while maintaining
	 * a specified up direction.
	 * 
	 * @param point 3D position of the object that the function is called on, which is
	 * used to calculate the rotation needed to look at the specified point.
	 * 
	 * @param up 3D direction of the "up" vector relative to the current view orientation,
	 * which is used to calculate the look-at rotation.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Computes a quaternion representing the rotation from the object's current position
	 * to look at a point `point` and aligned with the `up` direction.
	 * 
	 * @param point 3D point around which to rotate the look-at axis.
	 * 
	 * @param up 3D direction of the "up" vector relative to the object's orientation,
	 * which is used to compute the rotation quaternion that looks at the `point` parameter.
	 * 
	 * @returns a quaternion representation of the rotation required to face the given
	 * point `point` while looking along the direction of the vector `up`.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

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

	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Sets the `Transform` instance variable `parent`.
	 * 
	 * @param parent 3D transform of the parent object to which the current object will
	 * be attached or moved relative to.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Sets the position of an object to a specified value.
	 * 
	 * @param pos 3D position of an object to which the `setPos()` method is being applied.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Adds a specified vector to the position of an object, updating its position to be
	 * the sum of its current position and the input vector.
	 * 
	 * @param addVec 3D vector to be added to the object's current position.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Sets the `rot` field of its receiver to a provided `Quaternion` value.
	 * 
	 * @param rotation 4D quaternion that defines the rotational transformation to be
	 * applied to the object, and it is assigned to the `rot` field of the class.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Sets the scale factor for a GameObject. It takes a `Vector3f` parameter representing
	 * the new scale value and updates the object's scale accordingly.
	 * 
	 * @param scale 3D vector of the object's scale.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	@Override
	public String toString() { return "";
	}

}
