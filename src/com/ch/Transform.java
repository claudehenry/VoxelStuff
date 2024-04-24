package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;


/**
 * is a representation of a transformation in 3D space, with fields for position
 * (pos), rotation (rot), and scale (scale). It also has methods to update the transform
 * based on changes in the position, rotation, or scale, as well as get/setters for
 * each field. Additionally, it provides methods to rotate and look at a point, and
 * gets the transformation matrix that represents the transform.
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
	 * updates an object's position, rotation, and scale based on the current values
	 * provided, storing the previous values in a temporary vector for future reference.
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
	 * takes a `Quaternion` representing an axis and an angle as input, rotates it by
	 * that angle around the specified axis, and returns the resulting quaternion.
	 * 
	 * @param axis 3D axis of rotation for the object being rotated.
	 * 
	 * 	- `axis`: A `Vector3f` object representing the axis of rotation. It contains the
	 * x, y, and z components of the rotation axis.
	 * 
	 * @param angle 3D rotation angle about the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * calculates and returns a rotation matrix that orients the agent's look direction
	 * towards a given point and up vector in 3D space.
	 * 
	 * @param point 3D position of an object that the method is meant to look at.
	 * 
	 * 	- `point`: A 3D vector representing the direction the camera should look at. It
	 * has three components: x, y, and z.
	 * 
	 * @param up 3D direction perpendicular to the line of sight, which is used to calculate
	 * the rotation needed to look at a point in 3D space.
	 * 
	 * 	- `up`: This is a `Vector3f` object representing an upward direction in 3D space.
	 * It provides information about the direction and magnitude of the upward vector.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a rotation quaternion that looks at a point `point` from a starting
	 * position `pos`, while maintaining a specified up direction `up`. The resulting
	 * quaternion represents the orientation of the camera relative to its starting position.
	 * 
	 * @param point 3D position that the look-at rotation is to be applied around, as a
	 * Vector3f object.
	 * 
	 * 	- `point`: A `Vector3f` object representing the point in 3D space where the
	 * rotation is to be calculated relative to the `up` direction. The point's components
	 * are (x, y, z).
	 * 
	 * @param up 3D axis along which the rotation is applied, which is used to compute
	 * the quaternion representation of the look-at rotation.
	 * 
	 * 1/ The `Vector3f` class is used to represent `up`, which is a 3D vector representing
	 * the upward direction in 3D space.
	 * 2/ The `normalized()` method is called on `up` to normalize it, ensuring that its
	 * magnitude is equal to 1. This is necessary for the rotation matrix to be properly
	 * formed.
	 * 3/ The resulting quaternion representation of the rotation is returned as a
	 * `Quaternion` object.
	 * 
	 * @returns a Quaternion representing the rotation needed to look at a point in 3D
	 * space from a specific position and orientation.
	 * 
	 * 	- The output is a Quaternion object, which represents a 3D rotation transformation.
	 * 	- The Quaternion is created by multiplying a rotation matrix with the normalized
	 * vector difference between the point and the position, and then scaling it with the
	 * up vector.
	 * 	- The rotation matrix is constructed using the dot product of the position and
	 * up vectors, and then normalizing the result.
	 * 	- The resulting Quaternion represents the rotation needed to look at the point
	 * from the position, while maintaining the same orientation as the up vector.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * evaluates whether an object has changed by checking its parent, position, rotation,
	 * and scale values against their previous states. If any of these values have changed,
	 * the function returns `true`. Otherwise, it returns `false`.
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
	 * computes a transformation matrix by multiplying the parent matrix, translation,
	 * rotation, and scale matrices.
	 * 
	 * @returns a transformed matrix representing a 3D transformation of a position,
	 * rotation, and scale.
	 * 
	 * The `getTransformation` function returns a Matrix4f object, which represents a 4x4
	 * transformation matrix. The matrix is constructed by multiplying the parent matrix,
	 * the translation matrix, the rotation matrix, and the scale matrix. Each of these
	 * matrices has its own properties and attributes, such as the translation vector,
	 * rotation angle, and scale factors.
	 * 
	 * The `Matrix4f` class represents a 4x4 transformation matrix, which is a fundamental
	 * data structure in computer graphics and game development. It provides methods for
	 * multiplying two matrices, scaling a matrix, and converting between matrix
	 * representation formats.
	 * 
	 * In the returned matrix, the translation vector represents the position of the
	 * transformed object relative to its original position. The rotation angle specifies
	 * the amount of rotation around the origin, while the scale factors determine the
	 * size and shape of the transformed object.
	 * 
	 * Overall, the `getTransformation` function returns a fully-defined transformation
	 * matrix that can be used to transform objects in 3D space.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves and returns the transformation matrix of its parent node in a hierarchical
	 * tree structure, taking into account any changes made to the parent node's transformation.
	 * 
	 * @returns a Matrix4f representation of the parent transformation matrix.
	 * 
	 * 	- The `parentMatrix` variable is of type `Matrix4f`, which represents a 4D
	 * transformation matrix in the form of a homogeneous quaternion.
	 * 	- The matrix contains the transformation information from the parent object to
	 * the current object.
	 * 	- If the `parent` variable is not null and has changed, the matrix will be set
	 * to the transformed value of the parent object's transformation.
	 * 
	 * In summary, the `getParentMatrix` function returns the transformation matrix
	 * representing the relationship between the current object and its parent object.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the `parent` field of the current object to a specified `Transform`.
	 * 
	 * @param parent Transform object to which the current instance will be added as a
	 * child transform.
	 * 
	 * 	- The `Transform` class is the type of the `parent` field, which indicates that
	 * it is an object of a transformer class.
	 * 	- The `this.parent` assignment updates the field `parent` to refer to the provided
	 * `Transform` instance.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * takes a position vector as input and returns its transformed version using the
	 * matrix provided by the `getParentMatrix` function.
	 * 
	 * @returns a transformed position vector.
	 * 
	 * 	- The output is a `Vector3f` object representing the transformed position of the
	 * entity.
	 * 	- The transformation is applied using the `transform` method of the parent matrix,
	 * which represents the transform of the entity's parent component.
	 * 	- The resulting position vector is in the local space of the entity's parent
	 * component, taking into account any transformations applied to it.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * takes a `Quaternion` object `parentRotation` and multiplies it by another `Quaternion`
	 * object `rot`, returning the transformed rotation.
	 * 
	 * @returns a transformed quaternion representing the rotation of the parent object
	 * based on the provided rotational angle.
	 * 
	 * 1/ Quaternion structure: The returned value is a Quaternion object, which represents
	 * a 4D vector in mathematical notation (w, x, y, z).
	 * 2/ Parent rotation: The parent rotation is applied to the input rotation before
	 * combining them using multiplication. This allows for chaining multiple transformations
	 * together.
	 * 3/ Rotation combination: The resulting quaternion represents the composition of
	 * the input rotation and the parent rotation. It can be used to represent complex
	 * rotations by combining multiple smaller rotations.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns a reference to the position of an object in 3D space, represented as a
	 * Vector3f object.
	 * 
	 * @returns a `Vector3f` object containing the position of the entity.
	 * 
	 * 	- `pos`: A `Vector3f` object representing the position of the entity. It contains
	 * the x, y, and z coordinates of the position in a floating-point format.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a new value represented as a Vector3f.
	 * 
	 * @param pos 3D position of an object or entity to which the method is being applied,
	 * and it is assigned to the `pos` field of the class instance.
	 * 
	 * 	- `this.pos`: This attribute is set to the input vector `pos`, indicating that
	 * it has been assigned to the instance field `pos`.
	 * 	- `Vector3f`: The type of the `pos` attribute indicates that it is a 3D vector
	 * with floating-point values.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * adds a vector to the position of an object, updating its position accordingly.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object, which
	 * is then updated through the `setPos()` method.
	 * 
	 * 	- `addVec`: A `Vector3f` object that represents a 3D vector with x, y, and z components.
	 * 	- `x`, `y`, and `z`: The individual components of the vector, which can take on
	 * any real value between -1 and 1.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * retrieves a quaternion representation of the current rotation of an object.
	 * 
	 * @returns a Quaternion object representing the rotation of the game object.
	 * 
	 * 	- `rot`: A Quaternion object representing the rotation of the entity.
	 * 	- Type: Quaternion
	 * 	- Attributes: Rotation angle in degrees.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the rotational transformation of an object, storing it as a member variable
	 * `rot`.
	 * 
	 * @param rotation 3D rotational transformation to be applied to the object being
	 * manipulated by the `setRot()` method.
	 * 
	 * 	- `Quaternion rotation`: This is a complex number object representing a quaternion,
	 * which is a mathematical construct used to represent 3D rotations. It has four
	 * components: x, y, z, and w, where x, y, and z represent the real part of the
	 * quaternion, and w represents its imaginary part.
	 * 	- `this.rot`: This refers to the current value of the `rot` field in the calling
	 * object, which is a complex number representing the rotation of the object in 3D space.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * returns a `Vector3f` object representing the scale of the component it belongs to.
	 * 
	 * @returns a `Vector3f` object containing the scale value.
	 * 
	 * 	- `scale`: A `Vector3f` object that represents the scale of the entity.
	 * 	- Type: `Vector3f`
	 * 	- Attributes: Three float values representing the x, y, and z components of the
	 * scale.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scale of the `Vector3f` object, which represents a 3D point, to the
	 * provided value.
	 * 
	 * @param scale 3D scaling factor for the object, which is applied to its position,
	 * rotation, and size.
	 * 
	 * 	- `this.scale`: The scale is assigned to the class instance field `scale`.
	 * 	- Vector3f: The data type of the `scale` parameter is specified as a vector with
	 * three elements in the form (x, y, z).
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * returns an empty string, indicating that it does not provide any information or
	 * value when called.
	 * 
	 * @returns an empty string.
	 * 
	 * 	- The output is a string with an empty value (`""`).
	 * 	- The length of the output string is 0, indicating that it contains no characters.
	 * 	- The output does not have any special features or attributes, such as formatting
	 * or encoding.
	 */
	@Override
	public String toString() { return "";
	}

}
