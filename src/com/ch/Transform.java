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

	/**
	 * This function maintains a reference to the current state of an object's position
	 * rotation and scale by comparing the previous values with the new ones and storing
	 * them if they changed.
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
	 * This function rotates an object's position vector by a certain angle around an
	 * arbitrary axis using quaternions.
	 * 
	 * @param axis The axis input determines the direction of rotation. It defines a
	 * vector pointing from the current orientation to the new orientation that is sought
	 * after applying the rotate() method.
	 * 
	 * @param angle The `angle` input parameter specifies the amount of rotation to apply
	 * around the specified `axis`.
	 */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	/**
	 * The given function takes two `Vector3f` parameters - `point` and `up`, and then
	 * computes the rotation required to make the camera's `Vector3f` eye point towards
	 * the provided `point` while keeping the `up` direction. It returns the calculated
	 * rotation as the `rot` variable.
	 * 
	 * @param point The point parameter is used to determine the direction that the camera
	 * should be looking. It provides the world space location of the point that the
	 * camera should be looking at.
	 * 
	 * @param up The input `up` vector determines the direction of "up" for the character's
	 * view frustum; it affects the rotation matrix produced by `getLookAtRotation()`
	 * which translates point views into the character's view space
	 */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

	/**
	 * This function returns a Quaternion representing the rotation from a reference point
	 * (pos) to look at a target point (point) with a specified up direction (up).
	 * 
	 * @param point The `point` input parameter represents the location of the camera's
	 * focus or attention.
	 * 
	 * @param up The 'up' vector represents the local Z axis orientation and gets passed
	 * as an argument to a Rotation Matrix that generates Quaternions representing look
	 * at rotations around local axes at some given points and local up-axis direction
	 * from an arbitrary location (usually from eyes of some Agent)
	 * 
	 * @returns The function takes two input vectors (point and up) and returns a Quaternion
	 * representation of a rotation that aligns the point towards the up direction. In
	 * other words it's a look at rotation or rotations toward a specific direction vector(up).
	 */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

	/**
	 * This function checks if the current state of an object (represented by `this`) has
	 * changed relative to its previous state (stored on 'oldPos', 'oldRot', and 'oldScale'),
	 * returning true if any changes were detected and false otherwise.
	 * 
	 * @returns The `hasChanged()` method returns a `boolean` value indicating whether
	 * the current object state has changed since the last time it was retrieved. Specifically:
	 * 
	 * 1/ If the 'parent' reference has changed (i.e., is not null but differs from the
	 * previous value), return `true`.
	 * 2/ If any of the member variables (`pos`, `rot`, or `scale`) differ from their
	 * previous values stored inside the 'old' references (i.e., do not equal the previous
	 * values), return `true`.
	 * 3/ Otherwise (if no differences found), return `false`.
	 * 
	 * Therefore this method checks for any changes among its member variables as well
	 * as recursively checks whether its 'parent' reference has changed before deciding
	 * and returning whether or not an object state change exists.
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
	 * This function computes the total transformation (i.e., translation + rotation +
	 * scaling) of a rigid body part by concatenating its individual components and returns
	 * the resultant matrix.
	 * 
	 * @returns This function takes the object's position translation information (getPos)
	 * as well as its scale information (getScale). It then uses these values to create
	 * matrix objects that represent a rotation transformation matrix and a translation
	 * matrix based on their properties (using static method like rotateMatrix(),
	 * translationMatrix()); . Finally it returns the resulting composite transformation
	 * as a single matrix by calling mul()on the parent matrix , translationMatrix
	 * multiplication on rotationMatrix multiplication on scaleMatrix  The resulting
	 * matrix can then be used as necessary by anyone who possesses that particular
	 * object/objective or wants access.
	 * 
	 * In other words the getTransformation() function returns the composite transformation
	 * matrix that results from applying position (or location),rotation around that
	 * position according to Quaternion type rot and finally scaling of that location
	 * according scale factor( getX scaleX ,getY scaley  getz scalesz) all these factors
	 * together would then form our matrix . This matrix has access for those wanting it
	 * or just needing their own particular 'item' .
	 */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	/**
	 * This function retrieves the transformation matrix of the parent object and returns
	 * it as a Matrix4f object. It first checks if the parent object has changed and if
	 * so updates the transformation matrix accordingly.
	 * 
	 * @returns Based on the code provided above: The function "private Matrix4f
	 * getParentMatrix()". returns the "parent matrix", which is described as "the
	 * transformation matrix of the parent object" (if one exists). Specifically The
	 * return value of the function is a "Matrix4f" object that represents the transformation
	 * matrix of the parent object. The function checks if a "parent" object is not null
	 * and has changed its transformation matrix before returning the transformed matrix;
	 * otherwise It returns the cached value.
	 */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	/**
	 * The `setParent()` function sets the reference of the current `Transform` object's
	 * `parent` property to the passed `Transform` object.
	 * 
	 * @param parent The `parent` parameter is assigned to a private variable of the same
	 * name within the class. This means that theTransform's `parent` will be set to the
	 * passed `parent` Transform.
	 */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

	/**
	 * This function takes the object's current position `pos` and transforms it using
	 * the parent matrix `getParentMatrix()` to produce a new transformed position
	 * represented as a `Vector3f`.
	 * 
	 * @returns This function takes the vector 'pos' as input and returns the transformed
	 * position of the object relative to its parent component. The transformation is
	 * done using the parent matrix which is a combination of transformations applied to
	 * the object. The output is a Vector3f object that represents the transformed position
	 * of the object.
	 */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

	/**
	 * This function takes the rotational quaternion 'rot' and performs a series of
	 * transformations on it:
	 * 
	 * 1/ It first creates a new Quaternion object with the value (1.0f), indicating no
	 * rotation.
	 * 2/ It then updates the quaternion with the transformed rotation of the parent
	 * object (if available).
	 * 3/ It multiplies the resulting quaternion by 'rot', effectively combining the two
	 * rotations.
	 * 
	 * @returns The `getTransformedRot()` function returns a quaternion representing the
	 * transformed rotation of the object (this) after taking into account the rotation
	 * of its parent object (if it exists). Specifically , the function takes the rotation
	 * of the parent ( if it exists ) and multiplies it with the local rotation(rot)of
	 * this to produce the result .
	 */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

	/**
	 * The function "getPos()" returns the object's current position as a vector (which
	 * is held by an instance variable "pos", of type Vector3f").
	 * 
	 * @returns The output of the `getPos()` method is a `Vector3f` object containing the
	 * position of the entity.
	 */
	public Vector3f getPos() {
		return pos;
	}

	/**
	 * The function `setPos` sets the instance variable `pos` of the object to the given
	 * `Vector3f` input.
	 * 
	 * @param pos The `pos` parameter is an instance of the `Vector3f` class that is being
	 * passed as an argument to the function. It sets the value of the instance variable
	 * `pos` within the object to the value of the argument passed.
	 */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

	/**
	 * This function adds the contents of the `addVec` vector to the current position of
	 * an object and assigns the result back to the `pos` field of the object.
	 * 
	 * @param addVec Here is the function definition you provided:
	 * 
	 * public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }
	 * 
	 * The addVec parameter is an operand for the "add" method being called on a vector.
	 * It represents the vectors that get added to the current vector (retrieved from pos
	 * using the dotAccessor).
	 * Thus it can be said that it updates/adds the position by adding a specific vector
	 * 'addVec'
	 */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

	/**
	 * The function "getRot()" returns the value of the field "rot" which is a Quaternion.
	 * 
	 * @returns The output returned by the given function `getRot()` is a quaternion value
	 * called `rot`.
	 */
	public Quaternion getRot() {
		return rot;
	}

	/**
	 * This function sets the object's rotational orientation (represented by a Quaternion)
	 * to the value passed as an argument.
	 * 
	 * @param rotation The `rotation` parameter is a `Quaternion` object that contains
	 * the desired orientation (rotation) of the object after setting it with the setRot()
	 * method.
	 */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

	/**
	 * This function returns the scale vector of the object.
	 * 
	 * @returns The output returned by this function is a vector with three elements (x;
	 * y; z) that represents the scale of the object.
	 */
	public Vector3f getScale() {
		return scale;
	}

	/**
	 * The provided function `setScale(Vector3f scale)` sets the scale of an object to
	 * the given `Vector3f` value.
	 * 
	 * @param scale The `scale` parameter is a vector representing a scalar multiplier
	 * that will be applied to this mesh's geometry vertices. Setting `scale` will resize
	 * this mesh using these vertex scale factors instead of manually altering all three
	 * scaling axes (x/y/z).
	 */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	/**
	 * This function overrides the Object class toString() method with a new implementation
	 * that returns an empty string ("").
	 * 
	 * @returns The output of this function is an empty string(""). The reason being that
	 * the overridden `toString()` method is returning an empty string.
	 */
	@Override
	public String toString() { return "";
	}

}
