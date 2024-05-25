package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * from the given Java file is a complex object that represents a transformation in
 * a 3D space. It has several fields and methods for rotating, looking at points,
 * updating its position and rotation, and getting its transformation matrix. The
 * class also has a parent-child relationship, where the child transform can have its
 * position, rotation, and scale updated based on its parent's changes.
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
	 * updates an object's position, rotation, and scale based on changes to their
	 * corresponding properties.
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
	 * transforms a rotation quaternion `rot` by applying a rotation about an axis
	 * represented by a vector `axis` and an angle `angle`, then normalizes the result.
	 * 
	 * @param axis 3D rotation axis around which the rotation will occur.
	 * 
	 * 	- `axis`: A `Vector3f` object representing the rotation axis. It has three
	 * components: x, y, and z, which represent the coordinates of the axis in 3D space.
	 * 
	 * @param angle 3D rotation angle around the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * computes and returns the rotation required to face a provided point while maintaining
	 * a specified up direction.
	 * 
	 * @param point 3D point that the camera should look at.
	 * 
	 * 1/ Vector3f point - represents a 3D location in homogeneous coordinates (x, y, z,
	 * w).
	 * 2/ Vector3f up - represents a 3D vector in homogeneous coordinates (x, y, z) that
	 * represents the "up" direction relative to the point.
	 * 
	 * @param up 3D direction perpendicular to the line of sight and is used to calculate
	 * the rotation required to face the specified point.
	 * 
	 * 	- `Vector3f` represents a three-dimensional vector in mathematical space, with
	 * x, y, and z components.
	 * 	- `getLookAtRotation()` is an internal method that computes a rotation matrix
	 * based on the provided `point` and `up` parameters.
	 * 	- `rot` is a variable of type `float[]` that stores the resulting rotation matrix
	 * as a 3x3 homogeneous matrix.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes the rotation needed to look at a point `point` from a view direction `up`.
	 * The rotation is computed using a 4x4 matrix and returned as a Quaternion object.
	 * 
	 * @param point 3D position from which to look at the scene.
	 * 
	 * 	- `point`: A Vector3f object representing the point in 3D space that the rotation
	 * is to be applied to.
	 * 	- `up`: A Vector3f object representing the direction of the up vector in 3D space,
	 * which is used as a reference for the rotation.
	 * 
	 * @param up 3D direction vector that the quaternion should rotate around, which is
	 * used to compute the rotation matrix for the `getLookAtRotation()` method.
	 * 
	 * 	- `up`: A `Vector3f` object representing the up direction vector. It is used to
	 * calculate the rotation required to look at a point from the current viewpoint.
	 * 
	 * @returns a quaternion representing the rotation required to look at a point in the
	 * environment from a specific position and orientation.
	 * 
	 * 	- The output is a `Quaternion` object representing the rotation from the camera's
	 * current position to look at a point in 3D space.
	 * 	- The rotation is represented as a 4x4 matrix, where the top row represents the
	 * x, y, and z components of the quaternion.
	 * 	- The bottom three columns represent the position of the camera in world coordinates.
	 * 	- The quaternion is normalized to have a length of 1, ensuring that it always
	 * represents a valid rotation.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * checks if an object's position, rotation, and scale have changed compared to a
	 * previous state. If any of these attributes have changed, the function returns
	 * `true`. Otherwise, it returns `false`.
	 * 
	 * @returns a boolean value indicating whether any of the object's properties have changed.
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
	 * computes a transformation matrix based on the position, rotation, and scale of an
	 * object. It first computes the translation, rotation, and scale matrices separately,
	 * then multiplies them together to produce the final transformation matrix.
	 * 
	 * @returns a transformed matrix representing the combination of translation, rotation,
	 * and scaling of an object.
	 * 
	 * The output is a `Matrix4f` object representing the combination of translations,
	 * rotations, and scaling of the parent matrix and the matrices passed as arguments.
	 * 
	 * The translation matrix represents the position of the object in 3D space, with the
	 * x, y, and z components of the vector representing the position in the respective
	 * dimensions.
	 * 
	 * The rotation matrix represents the rotation of the object around its center,
	 * described by the Euler angles roll, pitch, and yaw.
	 * 
	 * The scale matrix represents the size and shape of the object in 3D space, with the
	 * x, y, and z components representing the scale factors in each dimension.
	 * 
	 * The multiplication of these matrices using the `mul()` method results in a matrix
	 * that combines the transformations represented by each individual matrix.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves the transformation matrix of its parent node, updating it if necessary
	 * and returning the result.
	 * 
	 * @returns a Matrix4f representation of the transform matrix of its parent node, if
	 * it exists and has changed.
	 * 
	 * 1/ The function returns a `Matrix4f` object representing the parent matrix of the
	 * current matrix.
	 * 2/ The parent matrix is retrieved from the `parent` variable, which is expected
	 * to be a `Transform3d` object. If the `parent` variable has changed, the parent
	 * matrix is updated with the transformation contained within it.
	 * 3/ The returned matrix represents the transformation applied by the parent matrix
	 * on the current matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the parent transform of an object, allowing for hierarchical manipulation of
	 * geometric data.
	 * 
	 * @param parent Transform object that will serve as the new parent of the current object.
	 * 
	 * 	- `this.parent = parent`: The reference to the parent transform is assigned to
	 * the field `parent`.
	 * 	- `Transform`: The type of the `parent` field, indicating that it holds a reference
	 * to a `Transform` object.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * transforms a `Vector3f` object using the matrix provided by the `getParentMatrix`
	 * method, and returns the transformed position.
	 * 
	 * @returns a transformed position vector in 3D space.
	 * 
	 * 	- The `Vector3f` object represents the transformed position of an entity in a 3D
	 * space, taking into account any transformations applied by its parent matrix.
	 * 	- The transformation is performed using the `transform()` method of the parent
	 * matrix, which takes the original position `pos` as input and returns the transformed
	 * position as output.
	 * 	- The resulting transformed position has the same components (x, y, z) as the
	 * original position, but they are shifted by the values in the parent matrix.
	 * 
	 * Note: This explanation does not provide a summary of the code or the function's
	 * purpose, nor does it refer to the author or any licensing information.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * transforms a given rotation quaternion `rot` using the parent's rotation quaternion
	 * `parentRotation`, and returns the transformed quaternion.
	 * 
	 * @returns a Quaternion representation of the rotational transformation applied to
	 * the parent object's rotation.
	 * 
	 * The `Quaternion` object `parentRotation` is initialized with the values 1, 0, 0,
	 * and 0, representing a rotation of the parent object around an arbitrary axis.
	 * 
	 * If the `parent` parameter is not null, the method multiplies the rotation `rot`
	 * with the `parentRotation` object, resulting in a new quaternion representation of
	 * the combined rotation of both objects.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns a `Vector3f` object representing the position of an entity.
	 * 
	 * @returns a `Vector3f` object containing the position of the entity.
	 * 
	 * The `Vector3f` object represents a 3D point in space with three components: x, y,
	 * and z. Each component is represented by an integer value between -128 and 127, inclusive.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a given Vector3f value.
	 * 
	 * @param pos 3D position of an object in the game world, which is assigned to the
	 * `pos` field of the `Entity` class.
	 * 
	 * 	- `this.pos = pos;` assigns the value of `pos` to the field `pos` in the current
	 * object.
	 * 	- `pos` is a `Vector3f` data structure containing x, y, and z components of the
	 * position.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * updates the position of an object by adding a vector to its current position.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object.
	 * 
	 * 	- `Vector3f`: This is the class that represents a 3D vector in Java, with properties
	 * for x, y, and z components.
	 * 	- `setPos()`: This is a method of the same class, used to set the position of an
	 * object using the specified vector as input.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a Quaternion object representing the rotation of the entity.
	 * 
	 * The `getRot` function returns a Quaternion object representing the rotation of an
	 * entity in 3D space. The Quaternion is a mathematical construct that represents a
	 * 3D rotation as a combination of a scalar and a vector. The scalar component
	 * represents the angle of rotation around a specific axis, while the vector component
	 * represents the direction of rotation.
	 * 
	 * The Quaternion object returned by the `getRot` function has several attributes,
	 * including its magnitude (representing the length of the quaternion), its axis
	 * (representing the axis of rotation), and its angle (representing the angle of
	 * rotation around that axis). These attributes can be accessed through methods such
	 * as `magnitude()`, `axis()`, and `angle()`.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the object's rotational orientation by assigning a new `Quaternion` value to
	 * its `rot` field.
	 * 
	 * @param rotation 4D quaternion value that specifies the rotation of the object to
	 * which the method is called, and it is stored as an instance variable `rot` within
	 * the function.
	 * 
	 * 	- Quaternion structure with x, y, z components for rotational angle and axis.
	 * 	- Each component is a floating-point value representing a particular aspect of
	 * the rotation.
	 * 	- The order of the components (x, y, z) follows the standard quaternion convention.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * retrieves the current value of the `scale` field, which represents the size or
	 * magnitude of an object in a 3D space.
	 * 
	 * @returns a `Vector3f` object representing the scale of the game object.
	 * 
	 * The `Vector3f` object `scale` represents a 3D vector with three elements: x, y,
	 * and z components. It holds the scale value for this game object. The value is
	 * non-negative and has a range of [0, infinity).
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scale of an object, represented by the `Vector3f` parameter, to be its
	 * new value.
	 * 
	 * @param scale 3D scaling factor for the object, which is assigned to the `scale`
	 * field of the class instance.
	 * 
	 * 	- `Vector3f`: This is the class that represents the scale value as a 3D vector
	 * in homogeneous coordinates.
	 * 	- `this.scale`: This is an instance variable that stores the current value of the
	 * scale property, which can be modified by calling the `setScale` function.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * returns an empty string, indicating that it does not provide any additional
	 * information about its input or output beyond what is already available through its
	 * type and other built-in methods.
	 * 
	 * @returns an empty string.
	 * 
	 * 1/ Empty string return value: The function returns an empty string, indicating
	 * that there is no information available to display about the object.
	 */
	@Override
	public String toString() { return "";
	}

}
