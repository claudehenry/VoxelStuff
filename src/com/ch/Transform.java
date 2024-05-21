package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * has several fields and methods for manipulating transforms in a 3D space. The class
 * has a `pos` field for storing the position of the transform, a `rot` field for
 * storing the rotation of the transform, and a `scale` field for storing the scale
 * of the transform. The class also has methods for updating the transform, rotating
 * it, looking at a point in space, and getting the transformation matrix. Additionally,
 * the class has a `parent` field for storing the transform's parent and a
 * `getParentMatrix()` method for getting the parent transform's transformation matrix.
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
	 * updates an object's position, rotation, and scale based on a provided input. If
	 * the input is not equal to the previous value, it sets the previous value as the
	 * new current value.
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
	 * multiplies a quaternion representing a rotation axis and an angle, normalizes the
	 * result, and returns the new quaternion representation of the rotated orientation.
	 * 
	 * @param axis 3D axis of rotation for the object being rotated.
	 * 
	 * 	- `axis`: A `Vector3f` object that represents the rotation axis in 3D space.
	 * 	- `angle`: An `float` value representing the angle of rotation around the `axis`.
	 * 
	 * @param angle 3D rotation angle about the specified `axis` of the object being rotated.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * computes the rotation required to face a provided point while maintaining a specified
	 * direction as the "up" vector.
	 * 
	 * @param point 3D position that the camera should look at.
	 * 
	 * 	- `point`: A `Vector3f` representing the point to look at in 3D space.
	 * 	- `up`: A `Vector3f` representing the upward direction in 3D space, used for
	 * computing the rotation towards the point.
	 * 
	 * @param up 3D direction opposite to which the camera's orientation should be rotated
	 * to look at the specified `point`.
	 * 
	 * 	- The `Vector3f` object `up` represents a 3D vector with x, y, and z components,
	 * which contain information about the direction of rotation.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes the rotation quaternion that looks at a given point from a specified up
	 * direction. The rotation is computed using a rotation matrix, which is created by
	 * multiplying a translation matrix and an orthonormal matrix.
	 * 
	 * @param point 3D position that the look-at rotation is based on.
	 * 
	 * 	- `point`: A `Vector3f` object representing a 3D point in space. It has three
	 * attributes: `x`, `y`, and `z`, which represent the coordinates of the point along
	 * the x, y, and z axes, respectively.
	 * 
	 * @param up 3D direction of the "up" vector in the local coordinate system, which
	 * is used to compute the rotation matrix for the `getLookAtRotation()` function.
	 * 
	 * 	- `up` is a `Vector3f` object that represents a vector in 3D space.
	 * 	- `up` has three components: `x`, `y`, and `z`, which represent the coordinates
	 * of the vector in the x, y, and z axes, respectively.
	 * 	- The magnitude of `up` is non-zero, indicating that it is not the zero vector.
	 * 
	 * The function returns a `Quaternion` object that represents the rotation necessary
	 * to look at the point `point` from the perspective of the `up` direction. This
	 * rotation is calculated using the `initRotation` method of the `Matrix4f` class,
	 * which takes the normalized difference between the point and the position (`pos`)
	 * as input and returns a rotation matrix.
	 * 
	 * @returns a quaternion representing the rotation necessary to look at a point from
	 * a specific position and direction.
	 * 
	 * 	- The output is a Quaternion object that represents the rotation necessary to
	 * look at a point in 3D space from a specific position and up direction.
	 * 	- The Quaternion is created by multiplying a rotation matrix (generated using the
	 * `Matrix4f` class) with the difference vector between the point and the position,
	 * normalized, and then adding the up vector.
	 * 	- This resultant quaternion represents the rotation needed to face the lookAt
	 * direction from the current position.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * evaluates if any of its arguments have changed, returning `true` if they have and
	 * `false` otherwise. It compares the values of `parent`, `pos`, `rot`, and `scale`
	 * to their previous values stored in `old` variables.
	 * 
	 * @returns a boolean value indicating whether the object has changed in any of its
	 * properties.
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
	 * combines a translation, rotation, and scaling matrix to create a single transformation
	 * matrix. It takes the position, rotation, and scale of an object as inputs and
	 * returns the transformed matrix.
	 * 
	 * @returns a matrix representing a transformation from the parent matrix, consisting
	 * of translation, rotation, and scaling components.
	 * 
	 * The returned matrix is a 4x4 homogeneous transformation matrix, which represents
	 * a 3D transformation consisting of translation, rotation, and scaling. The translation
	 * component represents the movement of the object in the world space, while the
	 * rotation component describes the orientation of the object around its center. The
	 * scaling component determines the size of the object in each dimension.
	 * 
	 * The `getParentMatrix()` method is called first to obtain the transformation matrix
	 * of the parent node, which is then multiplied with the translation, rotation, and
	 * scaling matrices to produce the final transformation matrix. This process reflects
	 * the hierarchical structure of the scene graph, where the transformation of a node
	 * is dependent on its parent node's transformation.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves the transformation matrix of the parent node in a hierarchical graph and
	 * returns it.
	 * 
	 * @returns a Matrix4f representation of the parent transformation matrix.
	 * 
	 * 	- `parentMatrix`: This is a `Matrix4f` object representing the parent transformation
	 * matrix of the current transform.
	 * 	- `hasChanged()`: A boolean method indicating whether the parent transformation
	 * matrix has changed since the last call to `getParentMatrix`. If the matrix has
	 * changed, the function will return the updated matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the `parent` field to a given `Transform` object, providing a new parent node
	 * for the current node in a hierarchical structure.
	 * 
	 * @param parent Transform to which the current instance will be added as a child transform.
	 * 
	 * 	- Type: `Transform` - This class represents a transform in a graph.
	 * 	- Fields: `transformId`, `parent`, `child` - These fields contain information
	 * about the transform's identity and relationships with other transforms in the graph.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * takes a `Vector3f` object `pos` and applies a transformation to it using the parent
	 * matrix, returning the transformed position as another `Vector3f` object.
	 * 
	 * @returns a transformed position vector based on the matrix provided by its parent
	 * matrix.
	 * 
	 * 	- The `Vector3f` object represents the transformed position of an entity after
	 * applying a matrix transformation.
	 * 	- The transformation is performed by calling the `transform` method on the parent
	 * matrix, passing in the original position vector as its argument.
	 * 	- The resulting transformed position vector contains the position of the entity
	 * after applying the specified transformation.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * transforms a given rotation quaternion `rot` by multiplying it with the transformation
	 * quaternion of its parent object, if available, and returns the result.
	 * 
	 * @returns a transformed Quaternion representing the combination of the parent's
	 * rotation and the provided rotation.
	 * 
	 * The `Quaternion` object returned by the function represents the transformed rotation
	 * of the parent node relative to the original rotation. The quaternion is a mathematical
	 * representation of a 3D rotation that can be used to rotate objects in 3D space.
	 * 
	 * The first component of the quaternion (1, 0, 0, 0) represents the scale factor of
	 * the transformation. The second component (0) indicates that no translation was
	 * applied. The third component (0, 0, 0) represents the rotation axis, which is the
	 * direction of the rotation. The fourth component (0) indicates that the rotation
	 * angle is zero, meaning that no rotation was applied.
	 * 
	 * Therefore, the returned quaternion represents the identity transformation, which
	 * means that no rotation or translation has been applied to the parent node relative
	 * to its original position and orientation.
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
	 * @returns a reference to a `Vector3f` object representing the position of the entity.
	 * 
	 * The `Vector3f` object returned by the function represents the position of an entity
	 * in 3D space, consisting of x, y, and z components. The x component represents the
	 * horizontal position, y component represents the vertical position, and the z
	 * component represents the depth or height position. Each component has a floating-point
	 * value between -1 and 1, which can be used to represent a wide range of positions
	 * within the 3D space.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a provided Vector3f value.
	 * 
	 * @param pos 3D position of an object and is assigned to the `pos` field of the class
	 * instance, updating its internal state.
	 * 
	 * 	- The `Vector3f` class has three properties: `x`, `y`, and `z`, which represent
	 * the coordinates of the position in 3D space.
	 * 	- Each property is a `float` value representing the coordinate value.
	 * 	- The `Vector3f` class provides methods for adding, subtracting, multiplying, and
	 * dividing its properties, as well as for calculating the magnitude (length) of the
	 * vector.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * adds a `Vector3f` object to the current position of an entity, by setting the
	 * position to the sum of the current position and the input vector.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object, which
	 * is then updated by calling the `setPos()` method with the resulting new position.
	 * 
	 * 	- `Vector3f`: This is the class that represents a 3D vector in Java, with three
	 * attributes: `x`, `y`, and `z`.
	 * 	- `add Vec`: This method takes another `Vector3f` object as input and adds its
	 * values to the current position of the object.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a Quaternion object representing the current rotation of the entity.
	 * 
	 * The return value is of type Quaternion, which represents an object that encodes
	 * rotation in 3D space as a combination of real and imaginary parts. The Quaternion
	 * class is part of the Java Math library.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the rotational orientation of an object by assigning a Quaternion value to
	 * the `rot` field of the class.
	 * 
	 * @param rotation 4D quaternion value that specifies the rotation of the object to
	 * which the method is called, and it is assigned to the `rot` field of the class.
	 * 
	 * 	- `Quaternion rotation`: This class represents a quaternion-based rotation in 3D
	 * space. It contains various attributes such as `x`, `y`, `z`, and `w`, which represent
	 * the real and imaginary parts of the quaternion, respectively. These attributes can
	 * be used to manipulate the rotation matrix.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * returns a `Vector3f` object representing the scale of the game object.
	 * 
	 * @returns a `Vector3f` object representing the current scale of the game object.
	 * 
	 * 	- The output is a `Vector3f` object that represents the scale of the game object.
	 * 	- The vector consists of three elements: x, y, and z, which represent the scaling
	 * factors in the respective dimensions.
	 * 	- Each element of the vector has a value between 0 and 1, indicating the amount
	 * of scaling applied to the object in each dimension.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scale of an object, updating its `scale` field with the provided value.
	 * 
	 * @param scale 3D vector that determines the scaling of the object.
	 * 
	 * The `Vector3f` class in Java represents a 3D vector with three elements, representing
	 * x, y, and z components, respectively. Therefore, `scale` is a 3D vector that
	 * contains the scalar values for each component.
	 * 
	 * By assigning the input `scale` to the field `this.scale`, the function effectively
	 * modifies the instance variable `scale` of the class `this`.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * returns an empty string.
	 * 
	 * @returns an empty string.
	 * 
	 * The function returns an empty string ("") as its output. This indicates that the
	 * object does not have any additional information to display when it is converted
	 * into a string.
	 * 
	 * The absence of any content in the returned string suggests that the object has no
	 * attributes or properties to display. As a result, the `toString` function provides
	 * minimal information about the object.
	 */
	@Override
	public String toString() { return "";
	}

}
