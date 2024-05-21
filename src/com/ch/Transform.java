package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * is a representation of an object's position, rotation, and scale in a 3D space.
 * It has various methods for updating these values, such as `update()`, `rotate()`,
 * and `setParent()`. The class also provides a transformation matrix that can be
 * used to transform the object in the 3D space. Additionally, it has methods for
 * getting and setting the position, rotation, and scale of the object.
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
	 * updates the reference values of `pos`, `rot`, and `scale` based on the current
	 * values of the same properties. If any of the reference values have changed, the
	 * corresponding field is set to the new value.
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
	 * rotates a `Quaternion` instance by an angle around a specified axis, resulting in
	 * a new `Quaternion` instance that represents the rotated orientation.
	 * 
	 * @param axis 3D vector that defines the axis of rotation.
	 * 
	 * 	- `axis` is a `Vector3f`, representing a 3D vector with floating-point values for
	 * its x, y, and z components.
	 * 	- The `x`, `y`, and `z` attributes of `axis` represent the coordinates of the
	 * axis along the x, y, and z axes, respectively.
	 * 
	 * @param angle 3D rotation angle of the axis around which the rotation will occur.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * computes the rotation required to face a given point while maintaining a specific
	 * up direction. The rotation is returned as a quaternion object.
	 * 
	 * @param point 3D position that the camera should look at.
	 * 
	 * 	- `point` is a `Vector3f` object representing a 3D point in space.
	 * 	- `up` is a `Vector3f` object representing a direction in space, which is used
	 * to calculate the rotation needed to look at the specified point from the camera's
	 * current position.
	 * 
	 * @param up 3D direction along which the entity will look at the point passed as argument.
	 * 
	 * 	- The `Vector3f` class represents a 3D vector in homogeneous coordinates, consisting
	 * of x, y, and z components.
	 * 	- The `getLookAtRotation()` method calculates the rotation necessary to look at
	 * a given point from a specific viewpoint, as represented by the `up` parameter.
	 * 	- The resulting rotation is stored in the `rot` field for later use in rendering
	 * or other applications.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a quaternion representation of the rotation from the camera's current
	 * position to look at a specified point, using the `pos` and `up` parameters as
	 * reference frames.
	 * 
	 * @param point 3D position that the rotation is to be applied around, which is used
	 * to calculate the rotation matrix.
	 * 
	 * 	- `point`: A 3D vector representing the point to look at. It has three components
	 * (x, y, z) representing the position of the point in the 3D space.
	 * 	- `up`: A 3D vector representing the direction of the up axis. Its components are
	 * (0, 0, 1), indicating that it points directly upwards in the 3D space.
	 * 
	 * @param up 3D vector that defines the "up" direction in the rotation, which is used
	 * to calculate the quaternion representing the look-at rotation.
	 * 
	 * 	- `up`: A `Vector3f` object representing a vector that is perpendicular to both
	 * the forward direction and the right-hand rule. This property allows for the rotation
	 * to be calculated based on the cross product of this vector with the difference
	 * between the `point` and the current position.
	 * 
	 * @returns a quaternion representing the rotation needed to look at a point from a
	 * specific position and direction.
	 * 
	 * 	- The output is a `Quaternion` object that represents the rotation required to
	 * look at a point in 3D space from a specific position and direction.
	 * 	- The quaternion is generated using the rotation matrix method, where the input
	 * vector is subtracted from the position vector, normalized, and then multiplied by
	 * the up vector to obtain the rotation axis.
	 * 	- The resulting quaternion represents the rotation around the rotation axis, with
	 * the point as the center of rotation.
	 * 	- The quaternion can be used to rotate a 3D object or camera to look at the
	 * specified point from the given position and direction.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * evaluates whether an object has changed by checking its parent, position, rotation,
	 * and scale, returning `true` if any have changed and `false` otherwise.
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
	 * calculates a transformation matrix based on an object's position, rotation, and
	 * scale, and returns it as a Matrix4f object.
	 * 
	 * @returns a transformed matrix representing the combination of translation, rotation,
	 * and scaling.
	 * 
	 * 	- The matrix is a product of three matrices: `getParentMatrix()`, `translationMatrix`,
	 * and `rotationMatrix`. Each of these matrices represents a transformation in 3D space.
	 * 	- `getParentMatrix()` refers to the transformation applied to the object's parent,
	 * which is used to calculate the final transformation.
	 * 	- `translationMatrix` represents a translation in 3D space, with three elements
	 * (x, y, and z) that specify the offset from the origin.
	 * 	- `rotationMatrix` represents a rotation of the object around its center, with
	 * nine elements (r, g, b, x, y, and z) that specify the amount of rotation in different
	 * directions.
	 * 	- `scaleMatrix` represents a scaling of the object, with three elements (x, y,
	 * and z) that specify the new size of the object after scaling.
	 * 
	 * The resulting matrix is a composition of these transformations, which can be used
	 * to apply the desired transformation to an object in 3D space.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves and returns the transformation matrix of its parent node in a hierarchical
	 * tree structure, taking into account changes to the parent node's transformation.
	 * 
	 * @returns a Matrix4f object representing the parent transformation matrix.
	 * 
	 * 	- `parentMatrix`: A `Matrix4f` object representing the parent transformation
	 * matrix of the current transform node in the hierarchy.
	 * 	- `hasChanged()`: A boolean method that indicates whether the parent transformation
	 * matrix has changed since the last time it was accessed. If `hasChanged()` is `true`,
	 * then the `getTransformation()` method of the `parent` object returns a new
	 * transformation matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the `parent` field of the current object to a given `Transform` instance,
	 * allowing for the inheritance of properties and methods from the parent object.
	 * 
	 * @param parent 3D transform to which the current transform will be attached as a
	 * child, effectively creating a hierarchical relationship between the two transforms.
	 * 
	 * 	- The `Transform` class is used as the type of the `parent` parameter, indicating
	 * that it is an object of this class.
	 * 	- The `this.parent = parent;` statement assigns the value of the `parent` parameter
	 * to the `parent` field of the current instance.
	 * 
	 * In summary, the `setParent` function modifies the `parent` field of the current
	 * instance with the value provided in the input parameter.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * transforms a position vector using the matrix provided by the parent component,
	 * and returns the transformed position.
	 * 
	 * @returns a transformed position vector.
	 * 
	 * The output is a `Vector3f` object that represents the transformed position of the
	 * GameObject in question, based on the transformation applied by the parent matrix.
	 * The vector contains the x, y, and z components of the transformed position, which
	 * can be used for various purposes such as rendering, collision detection, or other
	 * game logic operations.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * takes a `Quaternion` object `parentRotation` and multiplies it by another `Quaternion`
	 * object `rot`, returning the transformed rotation.
	 * 
	 * @returns a Quaternion representing the transformed rotation of the parent object
	 * based on its own rotation and the given rotation.
	 * 
	 * 	- `Quaternion parentRotation`: This is the rotation of the parent node in the
	 * tree, represented as a Quaternion object.
	 * 	- `rot`: This is the rotation of the current node in the tree, also represented
	 * as a Quaternion object. The multiplication between `parentRotation` and `rot` is
	 * performed to generate the transformed rotation of the current node.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns the position of an object in a three-dimensional space, represented as a
	 * vector in the form (x, y, z).
	 * 
	 * @returns a `Vector3f` object representing the position of the entity.
	 * 
	 * 	- `pos`: A Vector3f object that represents the position of the game object in 3D
	 * space, with x, y, and z components.
	 * 	- The values of these components can range from -10 to 10 for each dimension,
	 * indicating the possible positions of the object within the game environment.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a new value provided as a parameter.
	 * 
	 * @param pos 3D position of an object, which is assigned to the `pos` field of the
	 * `Object` class in the function call.
	 * 
	 * 	- `this.pos`: It is a field of type Vector3f in the class where the function is
	 * defined. This field represents the position of an object in 3D space.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * adds a vector to the position component of its caller, updating the position field
	 * accordingly.
	 * 
	 * @param addVec 3D vector that adds to the current position of the object.
	 * 
	 * 	- ` Vector3f`: This class represents a 3D vector in homogeneous coordinates,
	 * consisting of three components: x, y, and z. It has getters and setters for each
	 * component, as well as methods for adding, subtracting, multiplying, and dividing
	 * the vector by a scalar value.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a Quaternion object representing the rotation of the game object.
	 * 
	 * 	- `rot`: This is a Quaternion object representing the rotation of the gameObject
	 * in question. It contains x, y, z components representing the real and imaginary
	 * parts of the quaternion.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the rotational orientation of an object by assigning a Quaternion value to a
	 * member variable 'rot'.
	 * 
	 * @param rotation 4-dimensional quaternion value that updates the rotational orientation
	 * of the object being referenced by the `this` keyword.
	 * 
	 * 	- The `Quaternion` class is used to represent the rotation.
	 * 	- It has four attributes: `x`, `y`, `z`, and `w`, which correspond to the real
	 * and imaginary parts of the quaternion.
	 * 	- The `x`, `y`, and `z` values represent the axis of rotation, while `w` represents
	 * the magnitude or length of the quaternion.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * retrieves the current scale value of an object, which is a `Vector3f` instance
	 * variable named `scale`.
	 * 
	 * @returns a `Vector3f` object containing the scale value.
	 * 
	 * The `Vector3f` object `scale` represents a 3D vector with three components: x, y,
	 * and z. These components represent the scale factors for each dimension of the
	 * vector, which can be used to transform or manipulate the vector in various ways.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scale of an object, which is represented by a `Vector3f` instance, to a
	 * new value.
	 * 
	 * @param scale 3D scaling factor for the game object, which is applied to its position,
	 * size, and orientation.
	 * 
	 * 	- `Vector3f`: This indicates that the input scale is a 3D vector containing three
	 * floating-point numbers representing the X, Y, and Z components of the scale.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * returns an empty string, indicating that it does not provide any information about
	 * its input or output.
	 * 
	 * @returns an empty string.
	 * 
	 * The returned string is an empty string, indicating that the object does not have
	 * any inherent value that can be represented in a string.
	 * 
	 * The lack of a non-empty string returned by the `toString` function highlights the
	 * object's fundamental property of having no meaningful representation as an immutable
	 * object. This property makes it unique among objects and is crucial for understanding
	 * its behavior when interacting with other objects or systems.
	 */
	@Override
	public String toString() { return "";
	}

}
