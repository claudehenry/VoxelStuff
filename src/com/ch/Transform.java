package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * Represents an object's position, rotation, and scale in 3D space. It provides
 * methods for updating, rotating, looking at points, and calculating transformations
 * based on its parent and child relationships. The class also tracks changes to the
 * transform properties.
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
	 * Updates stored transformation values if they differ from current values, or
	 * initializes them as default values if they are null. It checks for equality between
	 * old and new values of position, rotation, and scale before updating them. New
	 * values are set using the `set` method.
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
	 * Updates a quaternion object (`rot`) to represent a rotation around a specified
	 * axis by a given angle, incorporating the existing rotation. The new rotation is
	 * calculated using the product of a unit quaternion representing the rotation and
	 * the current rotation. The result is normalized.
	 *
	 * @param axis 3D vector around which rotation is applied to the object's current
	 * orientation stored in the `rot` variable.
	 *
	 * @param angle rotation angle around the specified axis, affecting the resulting quaternion.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Computes a rotation that orients an object's view to face a specified point while
	 * maintaining a specific upward direction. It returns this rotation as a value, which
	 * is stored in the `rot` variable. The rotation is calculated using the `getLookAtRotation`
	 * method.
	 *
	 * @param point 3D coordinate of a location in space that the object will be oriented
	 * towards upon rotation.
	 *
	 * @param up 3D vector that defines the world-up direction, influencing the rotation
	 * of the object to face a specific point.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Returns a quaternion representing the rotation that would align the local forward
	 * direction with a specified point and up direction, given a positional reference.
	 * It calculates this using a matrix initialized with rotation to normalize a vector
	 * from the position to the point, then the up direction.
	 *
	 * @param point 3D location to which the look-at rotation is being calculated, relative
	 * to the object's position stored in the `pos` variable.
	 *
	 * @param up 3D direction vector that will be used as the rotation's up axis,
	 * perpendicular to the line of sight from the current position to the target point.
	 *
	 * @returns a rotation quaternion.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * Checks if an object's state has changed by comparing its position, rotation, and
	 * scale values with their previous states. If any of these values have changed or
	 * if a parent object has changed, the function returns true; otherwise, it returns
	 * false.
	 *
	 * @returns a boolean value indicating whether object has changed or not.
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
	 * Computes a transformation matrix by combining translation, rotation, and scaling
	 * matrices. The result is obtained by multiplying these matrices together in a
	 * specific order: scale, rotation, and then translation. The final product represents
	 * the cumulative effect of these transformations on an object's position.
	 *
	 * @returns a combined transformation matrix.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * Returns the transformation matrix of the parent object, if it exists and has changed
	 * since the last update. It retrieves the matrix from the parent object's
	 * `getTransformation` method and stores it in its own field for later use. The current
	 * transformation is returned to the caller.
	 *
	 * @returns a 4x4 matrix representing the parent transformation.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * Assigns a new Transform object as the parent to the current Transform object,
	 * replacing any existing parent. The assignment is done directly by setting the
	 * `parent` field to the provided parameter. This establishes a hierarchical relationship
	 * between the two transforms.
	 *
	 * @param parent 2D transform that will be assigned as the new parent for this transform
	 * instance.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * Transforms a position `pos` using its parent's transformation matrix, returning
	 * the resulting transformed position. It uses the `getParentMatrix` method to obtain
	 * the parent's matrix and the `transform` method to apply it to `pos`. The result
	 * is returned as a Vector3f object.
	 *
	 * @returns a transformed vector position based on parent matrix transformation.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * Multiplies a quaternion by its parent's transformed rotation, if present. It creates
	 * an initial parent rotation as identity and updates it with the parent's transformation
	 * if available. The final result is returned after multiplication with the object's
	 * local rotation.
	 *
	 * @returns a Quaternion object representing the transformed rotation.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * Returns a vector representing an object's position, with type `Vector3f`. The
	 * returned value is read-only, implying it may be retrieved but not modified by the
	 * caller. The return type and method name suggest a getter pattern for encapsulating
	 * data access.
	 *
	 * @returns an instance of a `Vector3f` class containing position data.
	 * The `Vector3f` object encapsulates three float values representing x, y, and z coordinates.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * Updates an object's position to a specified value. A Vector3f object is passed as
	 * an argument, which represents three-dimensional coordinates. The new position
	 * replaces the existing one stored within the object.
	 *
	 * @param pos 3D position to be assigned to an instance variable, presumably named
	 * `pos`, which is a Vector3f object.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Modifies an object's position by adding a specified vector to its current position,
	 * updating it in place. It achieves this through chaining of methods: getting the
	 * current position, adding the provided vector, and setting the updated position as
	 * the new value.
	 *
	 * @param addVec 3D vector to be added to the current position.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * Returns a quaternion representing the rotation associated with an object or entity.
	 * The quaternion is stored in a variable named `rot`. It provides access to the
	 * rotational state of the object.
	 *
	 * @returns a Quaternion object, specifically the instance variable `rot`.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * Assigns a specified Quaternion object to the `rot` field. This sets the current
	 * rotation to the provided value. The function updates the internal state of the
	 * instance with the new rotation.
	 *
	 * @param rotation 3D rotation to be applied, passed as a Quaternion object that is
	 * then assigned directly to the instance variable `rot`.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * Retrieves a three-dimensional scale value, represented as a float vector (Vector3f),
	 * from an object or data structure referred to by the variable `scale`. The retrieved
	 * value can be used elsewhere in the code for scaling purposes. This function does
	 * not modify any state.
	 *
	 * @returns a vector containing three floating-point values representing the object's
	 * scale.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * Updates an object's scale property with a new value specified by the `scale`
	 * parameter, which is expected to be a Vector3f object. The function assigns the
	 * provided scale value directly to the object's scale attribute. Scaling is typically
	 * used for geometric transformations.
	 *
	 * @param scale 3D scaling factor and is used to update the object's internal state
	 * by assigning it to an instance variable of the same name.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * Returns an empty string when called on an instance of its class. This implementation
	 * is a default behavior, typically used for debugging or logging purposes when no
	 * specific string representation of the object is needed. It provides minimal
	 * information about the object's state.
	 *
	 * @returns an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
