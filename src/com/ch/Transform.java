package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * has several methods for updating and manipulating its position, rotation, and
 * scale. It also has a parent reference and methods to get and set these properties.
 * Additionally, it has a hasChanged() method to check if any of the properties have
 * changed.
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
	 * updates the positions, rotations and scales of an object based on the provided
	 * values, storing the previous values for future updates.
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
	 * rotates a 3D vector by an angle around a specified axis, using quaternions to
	 * perform the rotation and normalize the result.
	 * 
	 * @param axis 3D axis of rotation for the transformation.
	 * 
	 * 	- `axis`: The axis of rotation, which can be represented as a 3D vector in
	 * homogeneous form (x, y, z, w).
	 * 	- `angle`: The angle of rotation around the specified axis, represented as a float
	 * value.
	 * 
	 * @param angle 3D rotation angle about the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * computes the rotation required to face a given point while maintaining a specified
	 * up direction.
	 * 
	 * @param point 3D position that the camera should look at.
	 * 
	 * 	- `point`: A `Vector3f` object that represents the position in 3D space where the
	 * camera should look.
	 * 
	 * @param up 3D vector pointing upwards relative to the object's position, which is
	 * used to calculate the rotation necessary to look at a given point from the object's
	 * perspective.
	 * 
	 * 	- The `Vector3f` class represents a 3D vector with floating-point values.
	 * 	- The `getLookAtRotation()` method computes the rotation required to look at a
	 * specified point from a particular orientation, as represented by the input `up`.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a rotation quaternion that looks at a given point from a specified up
	 * direction. The rotation is computed using the cross product and normalization of
	 * the vector difference between the point and the position, and the up vector.
	 * 
	 * @param point 3D position that the look-at rotation is applied to.
	 * 
	 * 	- `point`: A `Vector3f` object representing the point in 3D space that the rotation
	 * should be applied to. Its components are (x, y, z).
	 * 
	 * @param up 3D vector that defines the "up" direction for the rotation, which is
	 * used to calculate the quaternion representation of the look-at rotation.
	 * 
	 * 	- `point`: The point in 3D space that the rotation is centered around. (A Vector3f
	 * object)
	 * 	- `up`: A reference to a Vector3f object representing the up direction of the
	 * look-at rotation. This can be used to determine the orientation of the rotation
	 * relative to the camera's coordinate system.
	 * 
	 * @returns a quaternion representation of the rotation from the camera's current
	 * position to look at a point in the scene.
	 * 
	 * 	- The Quaternion object represents a rotation matrix that rotates the vector `pos`
	 * by the angle subtended by the line connecting `pos` and `up`, around the axis
	 * defined by `up`.
	 * 	- The rotation is counterclockwise from the positive x-axis.
	 * 	- The resulting quaternion has a magnitude of 1, which means that it represents
	 * a rotation rather than a scale or translation.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * checks if any of the object's properties have changed by comparing them to their
	 * previous values. If any property has changed, the function returns `true`, otherwise
	 * it returns `false`.
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
	 * computes and returns a transformation matrix based on the position, rotation, and
	 * scale of an object, using the parent matrix of the object as the basis for computation.
	 * 
	 * @returns a transformed matrix representing the combination of translation, rotation,
	 * and scaling of the entity.
	 * 
	 * The returned matrix is a product of three matrices: `getParentMatrix()`,
	 * `translationMatrix`, and `rotationMatrix`. The `getParentMatrix()` represents the
	 * overall transformation, while `translationMatrix` and `rotationMatrix` represent
	 * the translation and rotation components, respectively.
	 * 
	 * The multiplication of these matrices results in a 4x4 homogeneous transformation
	 * matrix, which includes both translation and rotation components. This means that
	 * the returned matrix can be used to transform points in 3D space using linear algebra
	 * operations.
	 * 
	 * The elements of the returned matrix represent the transformation coefficients for
	 * each dimension (x, y, z, and w), where w is the fourth component representing the
	 * scaling factor. The elements are arranged in a column-major order, following the
	 * convention used in most 3D graphics libraries.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves and caches the transformation matrix of its parent component, updating
	 * it if necessary based on changes to the parent.
	 * 
	 * @returns a Matrix4f representation of the parent transformation matrix.
	 * 
	 * 	- The `parentMatrix` variable is of type `Matrix4f`.
	 * 	- It represents the transformation matrix of the parent node in the hierarchical
	 * tree structure.
	 * 	- If the `parent` field is not null and has changed, the matrix is returned as a
	 * result of the function.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets the parent transformation of an object, which is a reference to another
	 * transformation that controls its position and rotation in the 3D space.
	 * 
	 * @param parent Transform object to which the current instance will be added as a
	 * child transformation.
	 * 
	 * 	- `Transform parent`: This is a reference to an object of type `Transform`, which
	 * represents a 3D transformation matrix.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * computes the transformed position of an object by multiplying its original position
	 * with the matrix provided by its parent node.
	 * 
	 * @returns a transformed position vector in 3D space.
	 * 
	 * 	- The `Vector3f` object represents a 3D position with x, y, and z components,
	 * which have decimal values between -10 and 10 inclusive.
	 * 	- The transformation is applied using the `getParentMatrix()` method, which returns
	 * a matrix object representing a 4x4 transformation matrix.
	 * 	- The `transform()` method of the matrix object takes a `Vector3f` argument and
	 * applies the transformation to it, resulting in a new position vector that represents
	 * the transformed coordinate.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * takes a `Quaternion` object `parentRotation` and multiplies it by another `Quaternion`
	 * object `rot`, returning the transformed rotation.
	 * 
	 * @returns a transformed quaternion representation of the parent rotation multiplied
	 * by the provided rotation quaternion.
	 * 
	 * 	- The Quaternion object represents a 3D rotation transformation that is the result
	 * of multiplying the parent rotation with the rot vector.
	 * 	- The Quaternion object has four components: x, y, z, and w, where x, y, and z
	 * represent the axis of rotation, and w represents the angle of rotation around those
	 * axes.
	 * 	- The returned Quaternion object is a transformed version of the parent rotation,
	 * which means that it encodes the rotation of the parent object followed by the
	 * rotation of the rot vector.
	 * 	- The resulting Quaternion object can be used for further transformations or other
	 * mathematical operations involving 3D rotations.
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * returns the position of an object in a three-dimensional space, represented by a
	 * `Vector3f` instance.
	 * 
	 * @returns a `Vector3f` object representing the position of the game entity.
	 * 
	 * The `Vector3f` object returned represents the position of an object in 3D space,
	 * with each component representing the value of the corresponding coordinate (x, y,
	 * and z) as a floating-point number.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object, represented by a `Vector3f` object, to a new value.
	 * 
	 * @param pos 3D position of an object in the function `setPos()`.
	 * 
	 * 	- `fX`: A 32-bit floating-point number representing the x-coordinate of the position.
	 * 	- `fY`: A 32-bit floating-point number representing the y-coordinate of the position.
	 * 	- `fZ`: A 32-bit floating-point number representing the z-coordinate of the position.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * adds a vector to the position component of an object.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object.
	 * 
	 * 	- `Vector3f` is a class in Java for representing 3D vectors.
	 * 	- `setPos()` is a method that sets the position of an object.
	 * 	- `add()` is a method that adds two vectors and returns their sum.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns a `Quaternion` object representing the rotation component of its caller.
	 * 
	 * @returns a `Quaternion` object representing the rotation of the object.
	 * 
	 * 	- The `getRot` function returns a Quaternion object representing the rotation of
	 * the game object.
	 * 	- The Quaternion object has four attributes: x, y, z, and w, which represent the
	 * real and imaginary parts of the quaternion in a specific order.
	 * 	- Each attribute represents a component of the rotation, with values between -1
	 * and 1 that can be combined to create complex rotations.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the rotational transformation of an object by assigning a Quaternion value
	 * to the `rot` field of the class.
	 * 
	 * @param rotation 3D rotational transformation to be applied to the object being
	 * manipulated by the `setRot()` method.
	 * 
	 * 	- Quaternion is the class that represents rotation in 3D space.
	 * 	- The `rot` field is assigned with the given `rotation`.
	 * 	- The `rotation` parameter can have various attributes such as x, y, and z
	 * components representing the angle of rotation around each axis, respectively.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * returns the current scale value of the object.
	 * 
	 * @returns a `Vector3f` object representing the current scale of the game object.
	 * 
	 * The `Vector3f` object `scale` is returned as the output of the function. It
	 * represents the scale factor for the current GameObject, which can be used to adjust
	 * its size and position in the game world. The vector components represent the x,
	 * y, and z values of the scale factor, respectively.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the `scale` field of the object to the provided vector.
	 * 
	 * @param scale 3D scaling factor for the object, which is applied to its size and
	 * position components.
	 * 
	 * 	- `this.scale = scale;` - The `scale` field is assigned the value of the input parameter.
	 * 	- `scale` - A `Vector3f` object representing a 3D vector with three components
	 * (x, y, and z) representing the scalar values for each component in the range of
	 * -1 to 1.
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * returns an empty string, indicating that the object it is called on has no meaningful
	 * representation as a string.
	 * 
	 * @returns an empty string.
	 * 
	 * 	- The output is an empty string.
	 * 	- This indicates that the `toString` function does not return any meaningful
	 * information or data.
	 * 	- It simply returns an empty string to indicate that the object it represents has
	 * no additional details or attributes beyond its basic type and identity.
	 */
	@Override
	public String toString() { return "";
	}

}
