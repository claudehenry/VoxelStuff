package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Quaternion;
import com.ch.math.Vector3f;

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
	 * Rotates a `Vector3f` object by an angle about an arbitrary axis, resulting in a
	 * new quaternion representation of the rotation.
	 * 
	 * @param axis 3D vector that defines the rotation axis.
	 * 
	 * @param angle 3D rotation angle of the object being rotated around the specified axis.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * Computes the rotation required to face a specified point while maintaining a given
	 * upward direction.
	 * 
	 * @param point 3D position of the point that the code should look at.
	 * 
	 * @param up 3D direction perpendicular to the line of sight and used to calculate
	 * the rotation needed to look at the provided point.
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * Computes a quaternion representation of a rotation that looks at a given point
	 * from a specified direction. The function takes a point and an up vector as input,
	 * and returns a quaternion representing the desired rotation.
	 * 
	 * @param point 3D point that the look-at rotation should be applied to.
	 * 
	 * @param up 3D direction perpendicular to the look-at point, which is used to create
	 * a rotation matrix that aligns with the local coordinate system of the function.
	 * 
	 * @returns a quaternion representing the rotation necessary to look at a point in
	 * space from a specified starting position and orientation.
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
	 * Sets the `parent` field of an object to a given `Transform` instance, allowing for
	 * the inheritance of transform properties and layout management.
	 * 
	 * @param parent 3D transform of the parent object to which the current object will
	 * be attached or moved relative to.
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
	 * Sets the position of a component or object to the input vector.
	 * 
	 * @param pos 3D position of the entity to which the method is being called, and it
	 * is assigned to the `pos` field of the class.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * Updates the position of an object by adding a vector to its current position.
	 * 
	 * @param addVec 3D vector to be added to the position of the object, and its value
	 * is used to calculate the new position of the object.
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
	 * Sets the rotational orientation of an object by assigning a provided `Quaternion`
	 * instance to the object's `rot` field.
	 * 
	 * @param rotation 4D quaternion that specifies the rotational transformation to be
	 * applied to the object's orientation.
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
	 * Sets the scaling factor for an object, affecting its size and proportions.
	 * 
	 * @param scale 3D scaling factor for the entity, which is applied to its dimensions
	 * to transform it.
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
