package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * Represents an object's position, rotation, and scale in 3D space. It provides
 * methods for updating, rotating, and translating the object's state, as well as
 * retrieving its transformation matrix and transformed positions. The class also
 * supports a hierarchical structure through parent-child relationships between transforms.
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
	 * Updates or initializes three old state variables (`oldPos`, `oldRot`, and `oldScale`)
	 * based on their current values. If the old states exist, it updates them if they
	 * have changed; otherwise, it initializes new instances with the current values of
	 * position, rotation, and scale.
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
	 * Updates a rotation by applying a new rotation around a specified axis and angle.
	 * It multiplies the existing rotation (represented as a quaternion) by the new
	 * rotation, then normalizes the result to prevent accumulation of numerical errors.
	 * The updated rotation is stored in the `rot` variable.
	 *
	 * @param axis 3D vector around which the rotation is performed.
	 *
	 * @param angle 3D rotation angle around the specified `axis` in radians, which is
	 * used to construct a new quaternion for interpolation with the existing `rot` quaternion.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Determines a rotation to align a viewpoint with a specified target and up direction.
	 * It computes a rotation based on the point and direction provided, storing the
	 * result as the value of the `rot` variable. The calculated rotation is then applied
	 * to orient the viewpoint appropriately.
	 *
	 * @param point 3D location that the camera is looking at or towards.
	 *
	 * @param up 3D vector that defines the direction of the camera's "up" axis when
	 * calculating its rotation for looking at a specified point.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Returns a quaternion representing a rotation that aligns the local z-axis with a
	 * given direction from an object's position to a specified point, with an optional
	 * constraint on the orientation relative to an up vector. The resulting rotation is
	 * calculated based on this target direction and up vector.
	 *
	 * @param point 3D vector from which the look-at rotation is calculated, based on its
	 * position relative to the object's position (`pos`).
	 *
	 * @param up 3D direction perpendicular to both the rotation axis and the target
	 * point, used to calculate the quaternion for a look-at rotation.
	 *
	 * @returns a Quaternion representing the rotation to face a target point.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * Checks if any attributes have changed since the last update, returning `true` if
	 * a change is detected and `false` otherwise. It recursively checks the parent
	 * object's changes and compares the current position, rotation, and scale with their
	 * stored old values.
	 *
	 * @returns a boolean indicating whether object properties have changed.
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
	 * Combines a translation, rotation, and scaling matrix to form a single transformation
	 * matrix that represents the position, orientation, and size of an object in 3D
	 * space. It applies these transformations in sequence from right to left: scale,
	 * then rotate, and finally translate.
	 *
	 * @returns a composite transformation matrix.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * Retrieves and returns a transformation matrix from its parent object, updating it
	 * if the parent has changed since its last retrieval. It checks if the parent is
	 * valid and has been modified before retrieving the transformation. The retrieved
	 * matrix is stored for future use.
	 *
	 * @returns a transformation matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Sets a Transform object as the parent of another object, establishing a hierarchical
	 * relationship between them. It takes a Transform object as an argument and assigns
	 * it to the `parent` field. The assignment updates the internal state of the object.
	 *
	 * @param parent new transform object to be assigned as the parent of the current
	 * transform instance.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Returns a transformed position based on the parent matrix and an original position
	 * (`pos`). The transformation is achieved by multiplying the parent matrix with the
	 * original position, effectively applying any scaling, rotation, or translation
	 * applied to the parent object. A Vector3f is returned.
	 *
	 * @returns a transformed vector position based on parent matrix.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * Returns a transformed rotation quaternion by multiplying it with the parent's
	 * rotation, if any. It creates an initial identity quaternion and updates it based
	 * on the parent's transformation. The result is then multiplied with the object's
	 * own rotation quaternion to produce the final transformed rotation.
	 *
	 * @returns a quaternion representing the transformed rotation of the object.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * Returns the current position of an object as a Vector3f, which represents
	 * three-dimensional coordinates. The position is stored in a variable named `pos`.
	 * This method provides access to the object's location without modifying it.
	 *
	 * @returns a 3D vector represented as an instance of the `Vector3f` class.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Assigns a new value to the object's `pos` field, replacing its existing value with
	 * the specified Vector3f. The function modifies the internal state of the object by
	 * updating this reference. This is an assignment operation.
	 *
	 * @param pos 3D position to be assigned to an object's or entity's location.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Increments a position vector by adding another vector to it, resulting in an updated
	 * position. It utilizes Java's Vector3f class for mathematical operations and
	 * object-oriented design principles. The updated position is stored within the same
	 * instance.
	 *
	 * @param addVec 3D vector to be added to the current position, specified by the
	 * `getPos()` method.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * Retrieves and returns a pre-computed rotation. It is assumed that the `rot` variable
	 * has been initialized with a valid rotation. The function provides direct access
	 * to the stored rotation without performing any calculations or transformations.
	 *
	 * @returns a Quaternion object representing the rotation of an entity or object.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Assigns a new Quaternion object to the `rot` field. The assigned Quaternion
	 * represents a 3D rotation, which is likely used for transformations or orientation
	 * calculations. This assignment replaces any existing rotation state with the provided
	 * one.
	 *
	 * @param rotation 3D rotational value to be assigned to the object's `rot` property.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * Retrieves and returns a vector representing the current scale of an object. The
	 * returned value is likely a reference to a pre-existing `scale` attribute, indicating
	 * that the function does not create a new instance but rather provides access to
	 * existing data. This suggests a getter pattern for encapsulation purposes.
	 *
	 * @returns a `Vector3f` object containing scalar values for x, y, and z axes.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Updates a game object's or model's scaling properties to match the provided Vector3f
	 * values. The new scale vector replaces any existing scale values for the object,
	 * allowing it to be resized or repositioned. Scaling changes are directly reflected
	 * in the object's dimensions and appearance.
	 *
	 * @param scale 3D scaling factor for an object, being assigned to the class's internal
	 * `scale` field directly without any modifications or checks.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * Returns an empty string as its result. This method is used to provide a string
	 * representation of an object, but in this case, it does not provide any meaningful
	 * information about the object's state or content. It simply returns an empty string.
	 *
	 * @returns an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
