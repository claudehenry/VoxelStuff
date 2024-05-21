package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * has several methods and fields for manipulating transforms, including updating
 * positions, rotations, and scales, as well as performing look-at transformations
 * and getting the transformation matrix. The class also has a parent reference and
 * methods for setting and getting the transformation matrix.
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
	 * multiplies a quaternion representing a rotation axis and an angle, normalizes the
	 * result, and returns the new quaternion representing the rotated orientation.
	 * 
	 * @param axis 3D axis of rotation.
	 * 
	 * 	- `axis`: A 3D vector representing the axis of rotation. It has three components:
	 * `x`, `y`, and `z`.
	 * 
	 * @param angle 3D rotation angle about the specified `axis` vector.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * computes the rotation needed to face a given point and a specified up direction,
	 * returning the rotation value as a quaternion.
	 * 
	 * @param point 3D position of an object that the `lookAt()` method is looking at.
	 * 
	 * 	- `point`: A 3D vector representing the direction in which the camera should look.
	 * Its properties include magnitude (a non-negative value) and direction (a unit vector).
	 * 
	 * @param up 3D direction of the "up" axis in the look-at rotation calculation.
	 * 
	 * 	- `up`: A `Vector3f` object representing the direction away from the eye,
	 * perpendicular to the line connecting the eye to the point being looked at.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * computes a quaternion representing the rotation required to look at a point `point`
	 * from a specified up direction `up`. The function returns a Quaternion object
	 * containing the rotation matrix.
	 * 
	 * @param point 3D position that the look-at rotation is relative to.
	 * 
	 * 1/ `Vector3f point`: A 3D vector representing a point in space. It has three
	 * attributes: `x`, `y`, and `z`, each representing the component of the vector in
	 * the corresponding dimension.
	 * 2/ `Vector3f up`: A 3D vector representing the direction of the "up" axis in the
	 * local coordinate system. It has three attributes: `x`, `y`, and `z`, each representing
	 * the component of the vector in the corresponding dimension.
	 * 
	 * @param up 3D direction along which the rotation is applied, and it is used to
	 * create a rotation matrix that aligns with that direction.
	 * 
	 * 	- `up` is a `Vector3f` object representing an upward direction vector. It has
	 * three components: `x`, `y`, and `z`.
	 * 	- The `up` vector is used to calculate the rotation matrix for looking at a point
	 * from a specific viewpoint.
	 * 	- The length of the `up` vector must be non-zero for the function to work correctly.
	 * 
	 * @returns a quaternion representing the rotation from the camera's current position
	 * to look at a point in space.
	 * 
	 * 	- The output is a Quaternion object, which represents a 3D rotation transformation.
	 * 	- The quaternion is generated using the `initRotation` method of the `Matrix4f`
	 * class, which takes two vectors as input: the direction of the look-at point (`point`)
	 * and the up vector (`up`).
	 * 	- The method returns a rotated representation of the input vectors, resulting in
	 * a rotation transformation that looks at the specified point from the specified up
	 * vector.
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * checks if an object's state has changed by comparing its current values to its
	 * previous ones. It returns `true` if any value has changed, and `false` otherwise.
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
	 * generates a transformation matrix based on the position, rotation, and scale of
	 * an object, and returns the resulting matrix.
	 * 
	 * @returns a transformed matrix representing the combination of translation, rotation,
	 * and scaling.
	 * 
	 * 	- The output is a `Matrix4f` object representing a transformation matrix.
	 * 	- The matrix is created by multiplying the parent matrix with the translation,
	 * rotation, and scale matrices.
	 * 	- The parent matrix is obtained by calling the `getParentMatrix()` method on the
	 * object that calls the `getTransformation()` method.
	 * 	- The translation matrix represents a 3D translation vector from the origin of
	 * the coordinate system to the position of the object in world space.
	 * 	- The rotation matrix represents a 3D rotation angle around an axis, which is
	 * defined by the rotational axis and angle in radians.
	 * 	- The scale matrix represents a scaling factor for the object in all three dimensions.
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * retrieves and caches the transformation matrix of its parent node in a hierarchical
	 * tree structure, using the `parent.getTransformation()` method.
	 * 
	 * @returns a Matrix4f representation of the parent transformation matrix.
	 * 
	 * The returned value is a `Matrix4f` object representing the parent matrix transformation.
	 * If the `parent` reference is not null and has changed, then the resulting matrix
	 * is set to the parent's transformation.
	 * Otherwise, the resulting matrix is the same as the current matrix.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * sets a reference to a `Transform` object, which represents the parent node of the
	 * current node in a hierarchical structure.
	 * 
	 * @param parent transform that will be used to modify the current transform's position,
	 * rotation, or scale.
	 * 
	 * 	- The `Transform` class is assigned to the `parent` field. This class represents
	 * a transformation in a graphical modeling language, and it contains various attributes
	 * and methods for manipulating the transformation.
	 * 	- The `Transform` class has several attributes that can be accessed through the
	 * `parent` field, including the `id`, `type`, `name`, and `attributes`. These
	 * attributes represent the unique identifier of the transformation, its type (e.g.,
	 * "translate", "rotate", etc.), its name or label, and any additional metadata
	 * associated with the transformation.
	 * 	- The `Transform` class also has various methods that can be called through the
	 * `parent` field, including `transformPoint`, `transformVector`, and `getInverse`.
	 * These methods allow for manipulation of the transformation and its effects on
	 * geometric objects.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * takes a `Vector3f` object `pos` and applies a transformation matrix provided by
	 * the parent matrix to obtain another `Vector3f` object representing the transformed
	 * position.
	 * 
	 * @returns a transformed position vector in homogeneous coordinates.
	 * 
	 * 	- The `Vector3f` object represents the transformed position of an object in 3D space.
	 * 	- It is the result of applying the parent matrix to the original position vector
	 * using the `transform` method.
	 * 	- The parent matrix is a transform matrix that represents the transformation of
	 * the object's position and orientation relative to its parent object.
	 * 	- The transformed position vector contains the coordinates of the object in the
	 * new coordinate system after applying the transformation.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * transforms the rotation represented by a Quaternion object `rot` using the parent
	 * rotation represented by another Quaternion object `parentRotation`, and returns
	 * the transformed rotation as a new Quaternion object.
	 * 
	 * @returns a Quaternion representation of the rotational transformation applied to
	 * the parent rotation matrix.
	 * 
	 * The `Quaternion` object returned by the function is the transformed rotation of
	 * the parent rotation matrix multiplied by the input `rot` matrix. The resulting
	 * quaternion represents the rotation transformation from the parent's orientation
	 * to the final orientation after applying the multiplication.
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
	 * @returns a reference to a `Vector3f` object containing the position of an entity.
	 * 
	 * 	- `pos`: A `Vector3f` object that represents the position of the entity in 3D
	 * space. It has three components: `x`, `y`, and `z`, which correspond to the x, y,
	 * and z coordinates of the position, respectively.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * sets the position of an object to a specified Vector3f value.
	 * 
	 * @param pos 3D position of the object being manipulated by the `setPos()` method.
	 * 
	 * 	- `this.pos`: This attribute stores the position of the object in the 3D space.
	 * It has three components - x, y, and z, which denote the position along the x, y,
	 * and z axes, respectively. The type of this attribute is a Vector3f class, which
	 * is a standard part of the Java programming language.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * adds a vector to the position of an object, updating its position accordingly.
	 * 
	 * @param addVec 3D vector to be added to the current position of the object.
	 * 
	 * 	- `Vector3f`: This is the class that represents the vector in 3D space, which
	 * includes x, y, and z components.
	 * 	- `setPos()`: This is a method in the current object that sets the position of
	 * the object to the sum of its current position and the input vector.
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * returns a `Quaternion` object representing the rotation of an entity.
	 * 
	 * @returns a Quaternion object representing the rotation of the gameobject.
	 * 
	 * 	- The `rot` field represents a quaternion value that contains information about
	 * the rotation of an object in 3D space.
	 * 	- The quaternion is a mathematical construct used to describe rotations in 3D
	 * space and is defined by four components: x, y, z, and w.
	 * 	- The x, y, and z components represent the real part of the quaternion, while the
	 * w component represents the imaginary part.
	 * 	- The value of the `rot` field can be manipulated to perform various rotational
	 * operations, such as rotation around a specific axis or concatenation with other quaternions.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * sets the rotation of an object to a given Quaternion value.
	 * 
	 * @param rotation 4-dimensional quaternion that represents the desired rotational
	 * transformation of the object being manipulated by the function.
	 * 
	 * 	- `Quaternion`: This is the data type of the serialized rotation value, which
	 * represents 3D rotations using a combination of scalar values and matrix multiplication.
	 * 	- `rot`: This is the instance variable being assigned the `rotation` value in the
	 * function call. It represents the current rotation state of the entity to which
	 * this method belongs.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * retrieves the current scale value of an object.
	 * 
	 * @returns a vector of three floating-point values representing the scale factor in
	 * the X, Y, and Z directions.
	 * 
	 * The `Vector3f` object `scale` represents a 3D vector that holds the scale factors
	 * for the model's dimensions. The x, y, and z components of this vector correspond
	 * to the scale factors in the x, y, and z directions, respectively. The magnitude
	 * or length of the vector indicates the total scale factor applied to the model.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * sets the scale of a `Vector3f` object, assigning the given scale value to the
	 * object's `scale` field.
	 * 
	 * @param scale 3D scaling factor for the object being manipulated by the `setScale`
	 * method.
	 * 
	 * 	- `scale` is a `Vector3f`, which represents a 3D vector with floating-point values.
	 * 	- It has three components: `x`, `y`, and `z`, each representing a component of
	 * the vector in the respective dimension.
	 * 	- The values of these components are set by the user through the `setScale`
	 * function, allowing for precise control over the scale of the game object.
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
	 * 	- The output is an empty string (`""`).
	 * 	- This indicates that the object being toString()'d does not have any non-empty
	 * attributes or methods that can be included in the toString() output.
	 */
	@Override
	public String toString() { return "";
	}

}
