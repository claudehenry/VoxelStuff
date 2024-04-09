package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * is a class that represents a transformation in 3D space. It has several fields and
 * methods for manipulating its transform, including position, rotation, and scale.
 * The class also has a parent reference, which allows it to inherit transformations
 * from other Transform objects. Additionally, the class provides methods for updating
 * its transform based on changes to its parent's transform, and for calculating the
 * transformation matrix for its position, rotation, and scale.
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
  * updates an object's position, rotation, and scale based on its previous values,
  * storing the new values for future use.
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
  * takes a reference to a quaternion `rot` and multiplies it by a new quaternion
  * created by rotating a `Vector3f` axis by an angle, normalizing the result.
  * 
  * @param axis 3D rotational axis around which the rotation will be performed.
  * 
  * 	- `axis`: A `Vector3f` object representing the rotation axis.
  * 	+ It has three components: x, y, and z, which are the coordinates of the rotation
  * axis in the 3D space.
  * 
  * @param angle 3D rotation angle of the axis about which the quaternion multiplication
  * is performed.
  */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

 /**
  * computes and stores the rotation needed to face a specified point while maintaining
  * a specific up direction.
  * 
  * @param point 3D position that the camera should look at.
  * 
  * 1/ The `Vector3f` class represents a 3D vector with three components: x, y, and z.
  * 2/ The `getLookAtRotation()` method computes the rotation required to face a point
  * in 3D space relative to the current position of the object.
  * 
  * @param up 3D direction towards which the camera should look, relative to its current
  * orientation.
  * 
  * 	- `up`: A vector representing the direction from the object's position to the
  * look-at point.
  * 	- It has three components (x, y, and z) that determine the direction of the vector
  * in 3D space.
  */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

 /**
  * computes the rotation needed to face a given point while maintaining a constant
  * orientation with respect to a provided up vector.
  * 
  * @param point 3D position from which to compute the look-at rotation.
  * 
  * 	- `point` is a `Vector3f` object representing a 3D point in space.
  * 	- The `x`, `y`, and `z` components of `point` represent the coordinates of the
  * point in the XYZ coordinate system.
  * 	- `up` is also a `Vector3f` object representing a direction in 3D space, which
  * is used to compute the rotation matrix.
  * 
  * @param up 3D direction towards which the rotation is applied.
  * 
  * 	- The `Vector3f` class is used to represent the vector `up`.
  * 	- The `normalized()` method is called on the `up` vector to make its magnitude
  * equal to 1. This is done to ensure that the resulting quaternion has a length of
  * 1, which makes it easier to interpret and use in various applications.
  * 
  * @returns a Quaternion representing the rotation from the camera's current position
  * to look at a point in the scene.
  * 
  * 	- The Quaternion object represents a rotation that aligns a virtual camera with
  * a given point and up vector.
  * 	- The rotation is represented as a 4x4 matrix in the form `M = R * T`, where `R`
  * is the rotation matrix and `T` is the translation vector.
  * 	- The rotation matrix `M` is obtained by initializing the product of the position
  * vector difference `p - pos` and the normalized direction vector `up`.
  */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

 /**
  * determines if an object has been modified by checking its parent, position, rotation,
  * and scale. If any of these properties have changed from their previous values, the
  * function returns `true`. Otherwise, it returns `false`.
  * 
  * @returns a boolean value indicating whether the object has changed.
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
  * computes and returns a transformation matrix that combines a translation, rotation,
  * and scaling of a given object based on its parent matrix and local position,
  * rotation, and scale values.
  * 
  * @returns a transformation matrix that combines a translation, rotation, and scaling
  * operation.
  * 
  * The `getParentMatrix()` method is used to obtain the parent matrix of the current
  * transformation matrix. This parent matrix represents the transformation that was
  * applied to the object before it reached its current position and rotation.
  * 
  * The `mul()` method is used to multiply two matrices together, resulting in a new
  * matrix that represents the composition of the two transformations. In this case,
  * the input matrices are `translationMatrix`, `rotationMatrix`, and `scaleMatrix`.
  * The resultant matrix represents the combined transformation of translation, rotation,
  * and scaling.
  * 
  * The properties of the returned output can be explained as follows:
  * 
  * 	- The matrix is a 4x4 homogeneous transformation matrix, representing the combination
  * of translation, rotation, and scaling.
  * 	- The elements of the matrix represent the amounts of transformation applied to
  * each axis (x, y, z, w). For example, the element at position (1, 2) represents the
  * amount of translation along the x-axis, plus the amount of rotation around the
  * y-axis, plus the amount of scaling along the z-axis.
  * 	- The matrix can be used to transform points, vectors, and other geometric objects
  * in a 3D space.
  * 	- The matrix is immutable, meaning it cannot be modified after it is created.
  */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

 /**
  * retrieves and returns the transformation matrix of its parent component, if it
  * exists and has been modified.
  * 
  * @returns a Matrix4f representation of the parent transformation matrix.
  * 
  * 	- The function returns a `Matrix4f` object representing the parent transformation
  * matrix of the current transform node.
  * 	- If the `parent` field is not null and its `hasChanged()` method returns true,
  * then the parent matrix is computed by calling the `getTransformation()` method on
  * the `parent` object.
  * 	- The returned matrix represents the combination of the current transform node's
  * transformation and its parent's transformation.
  */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

 /**
  * sets the `Transform` object reference for the current instance.
  * 
  * @param parent Transform object that will serve as the new parent of the current object.
  * 
  * The `Transform` class contains the `parent` attribute, which is set to a new value
  * passed as a parameter in this function call. The `parent` attribute holds a reference
  * to an object of type `Transform`, representing the parent transform for this one.
  */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

 /**
  * takes a `Vector3f` object `pos` and applies a transformation matrix provided by
  * `getParentMatrix()` to obtain a new `Vector3f` object representing the transformed
  * position.
  * 
  * @returns a transformed position vector in homogeneous coordinates.
  * 
  * The Vector3f object represent the transformed position after applying the parent
  * matrix transformation. The components of the vector are affected by the scaling,
  * translation, and rotation properties of the parent matrix. Therefore, the transformed
  * position may have different values compared to the original position due to these
  * transformations.
  */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

 /**
  * transforms a given quaternion `rot` using the parent rotation `parentRotation`,
  * and returns the transformed quaternion.
  * 
  * @returns a transformed quaternion representation of the given rotation.
  * 
  * 	- The `Quaternion` object `parentRotation` is created with the scalar value 1 in
  * the `w` component and the vector values (0, 0, 0) in the `x`, `y`, and `z` components.
  * 	- If the `parent` parameter is not null, the `parentRotation` object is transformed
  * by multiplying it with the `rot` parameter, resulting in a new `Quaternion` object
  * with the combined rotation values.
  * 
  * The output of the function is a `Quaternion` object representing the transformed
  * rotation matrix.
  */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

 /**
  * returns the position of an object as a `Vector3f` instance.
  * 
  * @returns a `Vector3f` object representing the position of the game entity.
  * 
  * 	- `pos`: This is a `Vector3f` object that represents the position of the entity
  * in 3D space. It has three components: x, y, and z, which correspond to the position
  * of the entity along the x, y, and z axes, respectively.
  * 	- Type: The type of the output is `Vector3f`, which is a class in the Java
  * programming language that represents a 3D vector.
  */
	public Vector3f getPos() {
		return pos;
	}

 /**
  * sets the position of an object to a specified Vector3f value.
  * 
  * @param pos 3D position of an object or entity that the method is called on, and
  * it assigns that position to the `pos` field of the calling object or entity.
  * 
  * 	- `this.pos = pos;` sets the position of the object to which the function belongs.
  * 	- `pos` is a `Vector3f` class representing a 3D vector with x, y, and z components.
  */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

 /**
  * adds a vector to the position of an object, updating its position accordingly.
  * 
  * @param addVec 3D vector to be added to the current position of the object, which
  * is then updated by calling the `setPos()` method with the result.
  * 
  * 	- `Vector3f` is a class representing a 3D vector with floating-point values.
  * 	- `getPos()` and `setPos()` are methods that return and set the position of an
  * object, respectively.
  */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

 /**
  * returns a `Quaternion` object representing the rotation component of the game
  * object's state.
  * 
  * @returns a Quaternion object representing the rotation of the game object.
  * 
  * The `getRot` function returns a `Quaternion` object representing the rotation of
  * an entity in 3D space. The `Quaternion` class in Java represents a quaternion
  * number as a combination of real and imaginary parts, which can be used to represent
  * rotations in 3D space.
  * The returned `Quaternion` object has four components: x, y, z, and w, where x, y,
  * and z represent the real parts, and w represents the imaginary part. These components
  * can be accessed and manipulated separately or together to perform various operations
  * related to rotations in 3D space.
  */
	public Quaternion getRot() {
		return rot;
	}

 /**
  * sets the rotation of an object, represented by the `Quaternion` class, to a new
  * value provided as input.
  * 
  * @param rotation 3D rotational transformation to be applied to the object instance
  * being manipulated by the `setRot` method.
  * 
  * 	- The Quaternion data type is represented by the variable `rot`.
  * 	- The rotation values of the Quaternion are stored in this variable.
  * 	- A Quaternion represents a 3D rotation as a combination of axis and angle, with
  * the axis being a 3D vector and the angle being an angle measured from the x-axis.
  */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

 /**
  * returns the scale value of a vector, which is a fundamental component in computer
  * graphics and game development.
  * 
  * @returns a `Vector3f` object representing the current scale of the entity.
  * 
  * 	- The `Vector3f` object represents a 3D vector with three components: x, y, and
  * z.
  * 	- The `scale` field of the object contains the magnitude or length of the vector.
  * 	- The vector's direction is not explicitly defined, but can be inferred based on
  * the magnitude.
  */
	public Vector3f getScale() {
		return scale;
	}

 /**
  * sets the scale of an object, which affects its size and proportions in 3D space.
  * 
  * @param scale 3D scaling factor for the game object, which is applied to its position,
  * rotation, and size.
  * 
  * 	- `scale` is a `Vector3f` object, representing a 3D vector in Java.
  * 	- It contains the x, y, and z components of the scale factor for this GameObject.
  * 	- The values of these components can range from -1 to 1, where a value of 0
  * represents no scaling.
  */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
 /**
  * returned an empty string, indicating that the class has no inherent value or
  * identity beyond its name and possible references.
  * 
  * @returns an empty string.
  * 
  * 	- The return type is `String`, indicating that the function returns a string value.
  * 	- The function itself does not perform any action or modification to the object
  * it is called on, as indicated by the absence of any explicit parameters in the
  * function signature.
  * 	- The function always returns an empty string ("") by default, meaning that no
  * information about the object is provided when the `toString` method is called.
  */
	@Override
	public String toString() { return "";
	}

}
