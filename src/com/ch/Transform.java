package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * from the file has the following functionality: it maintains positions, rotations,
 * and scales for an object, updating them when necessary; it provides methods to
 * rotate, look at points, and get transformations; it also has a parent-child
 * relationship through the `setParent` method.
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
	 * updates an entity's position, rotation, and scale based on their previous values,
	 * preserving any changes made to them.
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
	 * performs a rotation on a 3D vector `rot` using a provided axis and angle, resulting
	 * in a new rotated vector that is normalized to a specific orientation.
	 * 
	 * @param axis 3D vector that defines the rotation axis for the transformation.
	 * 
	 * 	- `axis`: A `Vector3f` object representing the axis of rotation. It has three
	 * components: x, y, and z, which correspond to the coordinates of the rotation axis
	 * in 3D space.
	 * 
	 * @param angle 3D rotation angle of the object around the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * calculates the rotation required to face a specified point and direction in 3D
	 * space. It returns the rotation angle as a quaternion.
	 * 
	 * @param point 3D position of an object that the function is looking at.
	 * 
	 * 	- `point`: A `Vector3f` object representing a 3D point in space. It has x, y, and
	 * z components.
	 * 
	 * @param up 3D direction perpendicular to the line of sight, which is used to calculate
	 * the rotation needed to look at a point from a specific position.
	 * 
	 * 	- `up` is a `Vector3f` object representing an upward direction in 3D space.
	 * 	- It has three components: x, y, and z, which represent the coordinates of the
	 * upward direction in the corresponding dimensions.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a rotation quaternion that looks at a given point from a specified up
	 * vector. The rotation is computed using a matrix multiplication and returned as a
	 * Quaternion object.
	 * 
	 * @param point 3D position that the look-at rotation should be applied to.
	 * 
	 * 	- `point`: A `Vector3f` instance that represents a point in 3D space. It has three
	 * components - `x`, `y`, and `z` representing the coordinates of the point.
	 * 	- `up`: A `Vector3f` instance that represents a vector pointing upwards from the
	 * viewpoint. It has three components - `x`, `y`, and `z` representing the coordinates
	 * of the upward vector.
	 * 
	 * @param up 3D direction that the rotation will be applied to, and it is used by the
	 * `Matrix4f` class to determine the orientation of the quaternion.
	 * 
	 * 	- `up` is a `Vector3f` representing an axis in 3D space.
	 * 	- It is used to determine the orientation of the look-at rotation.
	 * 	- The `up` vector can be any direction, but it is typically set to a default value
	 * or obtained from user input.
	 * 
	 * @returns a quaternion representing the rotation from the camera's current position
	 * to look at a point in the scene.
	 * 
	 * The Quaternion object returned by the function represents a rotation that aligns
	 * a virtual camera with a specified point and up direction. The rotation is represented
	 * as a 4x4 matrix, where the first three columns represent the rotation around the
	 * x, y, and z axes, respectively, and the fourth column represents the scale factor
	 * for the rotation.
	 * 
	 * The matrix returned by the function is generated using the `Matrix4f.initRotation`
	 * method, which takes two input vectors: the first is the position of the virtual
	 * camera relative to the origin, and the second is the up direction vector. The
	 * method computes the rotation matrix based on these inputs, ensuring that the
	 * resulting rotation aligns the virtual camera with the specified point and up direction.
	 * 
	 * The Quaternion object returned by the function has several useful properties,
	 * including its ability to be multiplied by other vectors to perform rotations, or
	 * added to other Quaternions to compute the composite rotation. Additionally, the
	 * Quaternion can be converted to a 3x3 rotation matrix using the `quatToMat3` method,
	 * allowing for further manipulation and transformation of the rotation.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * evaluates whether an object's state has changed by comparing its current properties
	 * to their previous values. If any property has changed, the function returns `true`,
	 * otherwise it returns `false`.
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
	 * computes a transformation matrix based on a position (`pos`), rotation (`rot`),
	 * and scale (`scale`). It returns the product of the parent matrix, translation,
	 * rotation, and scale matrices.
	 * 
	 * @returns a transformed matrix representing a combination of translation, rotation,
	 * and scaling.
	 * 
	 * 	- The multiplication of the parent matrix (`getParentMatrix()`), translation
	 * matrix (`translationMatrix`), rotation matrix (`rotationMatrix`), and scale matrix
	 * (`scaleMatrix`) results in a transformation matrix that represents the combined
	 * effects of position, rotation, and scaling.
	 * 	- The resulting transformation matrix is a 4x4 matrix, with each element representing
	 * the product of the corresponding elements of the individual matrices.
	 * 	- The order of the matrices in the multiplication is important, as it determines
	 * the sequence in which the transformations are applied. In this case, the parent
	 * matrix is multiplied first, followed by the translation, rotation, and scaling matrices.
	 * 	- The transformation matrix represents a 3D transformation that can be used to
	 * transform points, lines, or other objects in a 3D space.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * returns the transformation matrix of the parent node in a hierarchical graph, based
	 * on the `parent` and `hasChanged()` properties.
	 * 
	 * @returns a matrix representation of the parent transformation.
	 * 
	 * The output is a `Matrix4f` object representing the parent transformation matrix.
	 * The matrix contains four 3x3 sub-matrices that represent the position, rotation,
	 * and scale of the parent transform.
	 * The matrix can be transformed using basic arithmetic operations such as addition,
	 * subtraction, multiplication, and division.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the `Transform` reference field of an object to a provided `Transform` parameter.
	 * 
	 * @param parent Transform to which the current instance should be added as a child
	 * transform.
	 * 
	 * 	- `Transform parent`: This is an object of the `Transform` class, which contains
	 * properties related to geometric transformations, such as translation, rotation,
	 * scaling, and affine transformation matrices.
	 * 	- `this`: Referencing the current instance of the `Node` class, which contains
	 * properties related to the node's geometry, position, and other attributes.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * takes a position vector as input and applies a transformation to it using the
	 * matrix provided by the parent object's `getParentMatrix()` method, returning the
	 * transformed position vector as output.
	 * 
	 * @returns a transformed position vector.
	 * 
	 * 	- The `Vector3f` object represents the transformed position of the GameObject in
	 * the world space.
	 * 	- The transformation is applied by multiplying the position vector with the matrix
	 * representation of the parent transform.
	 * 	- The resulting vector has its coordinates transformed based on the transform's
	 * orientation, scale, and translation.
	 * 	- The transform matrix is a 4x4 homogeneous coordinate transformation matrix that
	 * represents the transform's orientation, scale, and translation.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * takes a `Quaternion` object `parentRotation` and multiplies it by another `Quaternion`
	 * object `rot`, returning the transformed rotation.
	 * 
	 * @returns a transformed quaternion representation of the parent rotation multiplied
	 * by the rotation represented by the `rot` parameter.
	 * 
	 * 	- The returned Quaternion represents the transformed rotation from the parent
	 * rotation to the current rotation.
	 * 	- The rotation is represented as a 4-dimensional vector in the form (w, x, y, z),
	 * where w is the magnitude and x, y, and z are the axis angles.
	 * 	- The magnitude of the quaternion is always non-zero, indicating that the rotation
	 * is never identity.
	 * 	- The axis angles of the quaternion are derived from the parent rotation and the
	 * current rotation by multiplying them together, resulting in a unique set of axis
	 * angles for each invocation of the function.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns the position of an object as a `Vector3f` object.
	 * 
	 * @returns a reference to a `Vector3f` object containing the position of an entity.
	 * 
	 * The `pos` field returns a Vector3f object that represents the position of the
	 * component in 3D space. This object contains the x, y, and z coordinates of the
	 * position, represented by floating-point numbers. The Vector3f class is a part of
	 * the Java SE API and provides methods for calculating the magnitude, direction, and
	 * other properties of vectors in 3D space.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of a class object to a specified Vector3f value.
	 * 
	 * @param pos 3D position of the object being manipulated by the `setPos()` method.
	 * 
	 * 	- The `Vector3f` class in Java represents a three-dimensional vector and has three
	 * properties: x, y, and z, which are double values representing the vector's components.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * adds a vector to the position component of its argument, updating the position
	 * field of the object accordingly.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object.
	 * 
	 * 	- `addVec`: A `Vector3f` object representing the vector to be added to the current
	 * position of the game entity. The object contains x, y, and z components representing
	 * the horizontal and vertical offsets from the original position.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns the current rotation of an object represented as a Quaternion.
	 * 
	 * @returns a Quaternion object containing the rotation information.
	 * 
	 * The `getRot` function returns a `Quaternion` object representing the rotation of
	 * the component. The Quaternion is a mathematical construct that can be used to
	 * represent 3D rotations in a more efficient and intuitive way than other methods
	 * such as Euler angles or rotation matrices. It has four properties: `x`, `y`, `z`,
	 * and `w` which correspond to the real and imaginary parts of the quaternion. These
	 * properties can be used to manipulate the rotation of the component in various ways.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the rotation of an object, storing it as a member variable `rot`.
	 * 
	 * @param rotation 4-dimensional quaternion that represents the rotation of the object
	 * in 3D space, which is assigned to the `rot` field of the class.
	 * 
	 * 	- `Quaternion rotation`: A `Quaternion` object representing a 3D rotational transformation.
	 * 	- `this.rot`: The current value of the `rot` field in the class.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * returns the current scale value of an object.
	 * 
	 * @returns a vector of three elements representing the scale factor for the object.
	 * 
	 * The `Vector3f` object represents a 3D vector with three components: x, y, and z.
	 * The value of each component is stored in separate fields within the class.
	 * 
	 * The `scale` field of the returned object contains the magnitude (or length) of the
	 * vector. This value can be positive, negative, or zero.
	 * 
	 * The vector's direction is determined by the x, y, and z components, which can take
	 * on any real number value between -1 and 1 inclusive.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scale of the object to which it belongs, by assigning a new value to its
	 * `scale` field.
	 * 
	 * @param scale 3D scaling factor for the game object, which is applied to its position,
	 * size, and rotation.
	 * 
	 * 	- `this.scale`: A `Vector3f` object representing the scale of the component.
	 * 	- `scale`: The input parameter that holds the new value for the scale of the component.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * simply returns an empty string.
	 * 
	 * @returns an empty string.
	 * 
	 * 	- The string returned is an empty string.
	 * 	- It does not contain any information about the class or instance it represents.
	 * 	- It does not provide any details about its type or composition.
	 */
	@Override
	public String toString() { return "";
	}

}
