package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

/**
 * TODO
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
  * updates an object's position, rotation, and scale based on the current values and
  * stored historical values.
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
  * takes a `Quaternion` and multiplies it by another `Quaternion` to rotate a `Vector3f`.
  * 
  * @param axis 3D rotation axis around which the rotation will occur.
  * 
  * 	- `axis`: A `Vector3f` object representing the axis of rotation. It has three
  * components - `x`, `y`, and `z` - which correspond to the coordinates of the axis
  * in the 3D space.
  * 
  * @param angle 3D rotation angle around the specified `axis`.
  */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

 /**
  * computes and stores the rotation required to face a given point while maintaining
  * a specific orientation with respect to the up direction.
  * 
  * @param point 3D position of an object that the `lookAt()` method is rotating towards.
  * 
  * 	- `point`: A 3D vector representing a point in space with x, y, and z components.
  * 	- `up`: A 3D vector representing a direction in space with x, y, and z components,
  * which is used to determine the orientation of the look-at rotation.
  * 
  * @param up 3D direction along which the camera's view will be looking when it rotates
  * to face the given `point`.
  * 
  * 	- The `up` parameter is a `Vector3f` class instance that represents the direction
  * from the eye to the up vector in the world coordinate system.
  * 	- The `up` vector has three components: x, y, and z, which represent the real-valued
  * coordinates of the up vector in the world coordinate system.
  */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

 /**
  * computes a quaternion representing the rotation from a reference frame to look at
  * a point in space, using the up vector as the direction of the look-at axis.
  * 
  * @param point 3D position of an object that the look-at rotation is being calculated
  * for.
  * 
  * 	- `point`: A `Vector3f` object representing a 3D point in space. It has three
  * attributes: `x`, `y`, and `z`, which represent the coordinates of the point in the
  * x, y, and z directions, respectively.
  * 
  * @param up 3D vector that defines the orientation of the look-at rotation relative
  * to the object's local up direction.
  * 
  * 	- `up` is a `Vector3f` object representing an upward direction vector.
  * 	- It has three components: `x`, `y`, and `z`, which correspond to the coordinates
  * of the direction vector in 3D space.
  * 	- The magnitude of `up` is always non-zero, indicating that it points in a specific
  * direction in 3D space.
  * 
  * @returns a Quaternion representation of the rotation from the position of the
  * object to look at.
  * 
  * The Quaternion object returned by the function represents a rotation from the
  * position `pos` to the direction `up`. The rotation is represented by a 4x4 matrix,
  * which is initialized using the `new Matrix4f().initRotation(point.sub(pos).normalized(),
  * up)`. This means that the Quaternion object has four elements: `w`, `x`, `y`, and
  * `z`, where `w` represents the magnitude of the rotation, and `x`, `y`, and `z`
  * represent the Euler angles of the rotation.
  */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

 /**
  * evaluates whether an object's position, rotation, or scale has changed since the
  * last evaluation. It returns `true` if any of these properties have changed, and
  * `false` otherwise.
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
  * scale of an object.
  * 
  * @returns a transformed matrix representing a 3D transformation of a object based
  * on its position, rotation, and scale.
  * 
  * The returned matrix is a product of three matrices: `getParentMatrix()`,
  * `translationMatrix`, and `rotationMatrix`. The `getParentMatrix()` matrix represents
  * the parent transformation of the current transformation, while `translationMatrix`
  * and `rotationMatrix` represent the translation and rotation components of the
  * transformation, respectively.
  * 
  * The multiplication of these matrices results in a transformation matrix that
  * combines the effects of all three components. This matrix can be used to transform
  * points, vectors, or other mathematical objects in 3D space according to the rules
  * of linear algebra.
  * 
  * In particular, the `translationMatrix` component represents a displacement of the
  * origin of the coordinate system by the vector `(pos.getX(), pos.getY(), pos.getZ())`,
  * while the `rotationMatrix` component represents a rotation of the coordinate system
  * about an axis specified by the rotation angle and axis vectors. The `scaleMatrix`
  * component represents a scaling of the coordinate system by the vector `(scale.getX(),
  * scale.getY(), scale.getZ())`.
  * 
  * Overall, the `getTransformation` function returns a transformation matrix that can
  * be used to manipulate objects in 3D space according to the desired transformation
  * rules.
  */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

 /**
  * retrieves and returns the transformation matrix of its parent component, taking
  * into account changes made to the parent matrix.
  * 
  * @returns a Matrix4f object representing the transformation matrix of the parent component.
  * 
  * 	- `parentMatrix`: This is a Matrix4f object that represents the transformation
  * matrix of the parent node in the scene graph.
  * 	- `hasChanged()`: This method checks whether the parent node's transformation
  * matrix has changed since the last call to `getParentMatrix`. If it has, the method
  * returns the updated transformation matrix. Otherwise, it returns the unchanged
  * transformation matrix.
  */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

 /**
  * sets the `Transform` field `parent` to a given value.
  * 
  * @param parent Transform to which the current instance will be added as a child transform.
  * 
  * 	- The `Transform` class contains several members that can be accessed through
  * this object reference, including `getTransform`, `setTransform`, `getParent`, and
  * `setParent`.
  * 	- The `parent` attribute is a reference to another transform object. This means
  * it is a Transform object that has its own set of properties, methods, and behavior.
  * 	- Depending on the context in which this method is called, it may be necessary
  * to call other methods on the `Transform` class before or after calling `setParent`.
  * For instance, you might want to first set the transform's position using `setPosition`
  * followed by setting its parent using `setParent`.
  */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

 /**
  * transforms a `Vector3f` object using the transformation matrix provided by its
  * parent matrix, and returns the transformed position.
  * 
  * @returns a transformed position vector in the format of a `Vector3f`.
  * 
  * 	- The output is of type `Vector3f`, which represents a 3D vector in Java.
  * 	- The vector is transformed by applying the parent matrix to the original position
  * vector using the `transform` method.
  * 	- The resulting vector represents the transformed position in the parent coordinate
  * system.
  */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

 /**
  * transforms a given `Quaternion` object `rot` using the parent `Quaternion` object
  * `parent Rotation`, and returns the transformed `Quaternion` object.
  * 
  * @returns a Quaternion representation of the rotational transformation applied to
  * the parent rotation.
  * 
  * The `Quaternion` object `parentRotation` represents the parent rotation of the
  * game object, which is transformed by multiplying it with the `rot` argument.
  * 
  * The resulting `Quaternion` object has the same direction as the `parentRotation`,
  * but its magnitude is reduced by the dot product of `parentRotation` and `rot`.
  * This means that the transformed rotation is a combination of the parent rotation
  * and the rotation applied to it.
  * 
  * The properties of the output `Quaternion` are determined by the values of
  * `parentRotation` and `rot`, which can be any valid quaternions representing 3D
  * rotations. The output quaternion represents the combined rotation of both the
  * parent and the application rotations, and its direction is the result of multiplying
  * these two rotations together.
  */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

 /**
  * retrieves the position of an object as a Vector3f data structure.
  * 
  * @returns a `Vector3f` object representing the position of an entity.
  * 
  * The `Vector3f` object returned by the function represents the position of an entity
  * in 3D space. It contains the x, y, and z coordinates of the position, represented
  * as floating-point values. These coordinates can range from -1 to 1 in each dimension,
  * indicating a finite range of positions within the 3D environment.
  */
	public Vector3f getPos() {
		return pos;
	}

 /**
  * sets the position of an object to a specified vector in 3D space.
  * 
  * @param pos 3D position of an object to which the `setPos()` method is applied, and
  * it is assigned to the `pos` field of the class instance.
  * 
  * 	- `pos` is a vector data type with three components, each representing the x, y,
  * and z coordinates of an object's position in 3D space.
  * 	- Each component is represented by a floating-point value between -1 and 1, inclusive.
  * 	- The total magnitude of the vector is always greater than or equal to 0, ensuring
  * that the position is valid and non-negative.
  */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

 /**
  * updates the position of an object by adding a specified vector to its current position.
  * 
  * @param addVec 3D vector to be added to the current position of the object.
  * 
  * 	- `addVec`: A `Vector3f` object representing a 3D vector with x, y, and z components.
  * 	- `x`, `y`, and `z`: The individual components of the vector.
  */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

 /**
  * retrieves the current rotation value of an object as a Quaternion.
  * 
  * @returns a Quaternion object representing the rotation of the entity.
  * 
  * 	- The `rot` variable returned is an instance of the Quaternion class.
  * 	- It represents a 4D mathematical object that represents a rotation in 3D space.
  * 	- The quaternion structure consists of a scalar part and a vector part, both of
  * which are multiplied together to produce the final rotation value.
  * 	- The scalar part is typically referred to as the "axis" of the rotation, while
  * the vector part is the "angle" of the rotation.
  */
	public Quaternion getRot() {
		return rot;
	}

 /**
  * sets the rotational component of an object, represented by a `Quaternion` instance,
  * to the given value.
  * 
  * @param rotation 4-dimensional quaternion that specifies the rotational transformation
  * to be applied to the entity represented by the `this` reference.
  * 
  * 1/ `Quaternion rotation`: This is the data type of the variable `rot`. It represents
  * a 4D vector that describes the orientation of an object in 3D space using a
  * mathematical construct called a quaternion. Quaternions are often used to represent
  * rotations in computer graphics and other fields where rotational movements need
  * to be mathematically represented.
  * 2/ `this.rot`: This is a reference to the instance variable `rot` within the same
  * class. It stores the deserialized value of `rotation`.
  * 3/ No attributes are found for this explanation.
  */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

 /**
  * returns the current scale value of a `Vector3f` object, which can be used to
  * represent 3D positions, vectors, and other geometric data in Java.
  * 
  * @returns a `Vector3f` object representing the current scale of the game entity.
  * 
  * The `Vector3f` object `scale` represents a 3D vector with magnitude equal to the
  * scaling factor applied to the object. The vector components are not explicitly
  * defined but can be obtained through mathematical operations such as `scale.x`,
  * `scale.y`, and `scale.z`.
  */
	public Vector3f getScale() {
		return scale;
	}

 /**
  * sets the scale of an object, which affects its size and proportions.
  * 
  * @param scale 3D vector that determines the scaling of the object to which the
  * method is applied.
  * 
  * 	- `this.scale`: The current scale value of the object, which can be a 3D vector
  * representing the magnitude and direction of the scaling transformation.
  * 	- `scale`: The input parameter passed to the function, representing the new scale
  * value that will be applied to the object.
  */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
 /**
  * returns an empty string.
  * 
  * @returns an empty string.
  * 
  * 	- The function returns a string with an empty value.
  * 	- The purpose of this method is to return a simple string with no content.
  * 	- The method name "toString" suggests that it should return a string representation
  * of the object, but in this case, it actually returns an empty string.
  */
	@Override
	public String toString() { return "";
	}

}
