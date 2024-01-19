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
  * This function updates the "old" (or previous) values of the object's position
  * (pos), rotation (rot), and scale (scale). If any of these values have changed since
  * the last update(), the function stores the current value as the old value for next
  * time.
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
  * This function rotates the object's orientation by a specified angle around a given
  * axis.
  * 
  * @param axis The `axis` input parameter specifies the axis of rotation.
  * 
  * @param angle The `angle` input parameter represents the amount of rotation around
  * the axis specified by `axis`. It specifies the angle (in degrees) that should be
  * rotated by the function.
  */
	public void rotate(Vector3f axis, float angle) {
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

 /**
  * This function sets the rotation of the camera (represented by the "rot" variable)
  * to face a specified point and up direction.
  * 
  * @param point The `point` parameter is used to specify the location that the camera
  * should look at.
  * 
  * @param up The `up` input parameter specifies the "up" direction for the character's
  * eyes. It is used to calculate the look at rotation (i.e., the direction the character
  * is looking).
  */
	public void lookAt(Vector3f point, Vector3f up) {
		rot = getLookAtRotation(point, up);
	}

 /**
  * This function returns a Quaternion representation of the rotation from the object's
  * current position and orientation to look directly at a given point "point" with
  * respect to an optional up vector "up".
  * 
  * @param point The `point` input parameter specifies the point from which to look
  * at the up direction.
  * 
  * @param up The `up` input parameter specifies the "up" direction of the local
  * coordinate system. It is used to determine the rotation angle around the Y-axis
  * (i.e., the axis perpendicular to the plane defined by the "point" and the "pos")
  * when creating a lookat rotation from the current position and point.
  * 
  * @returns This function takes two vectors `point` and `up`, and returns a Quaternion
  * representation of the rotation from the current position (represented by `pos`)
  * to look at the point `point` while keeping the up direction fixed at `up`.
  */
	public Quaternion getLookAtRotation(Vector3f point, Vector3f up) {
		return new Quaternion(new Matrix4f().initRotation(point.sub(pos).normalized(), up));
	}

 /**
  * This function checks if the current position (pos), rotation (rot), and scale
  * (scale) of an object have changed since the last time they were saved (stored In
  * oldPos/oldRot/oldScale). If any of them have changed then return true.
  * 
  * @returns This function returns a `boolean` indicating whether the current state
  * of the object has changed compared to its previous state. It checks if any of the
  * following properties have changed:
  * 
  * 1/ `parent`: the parent object (if not null)
  * 2/ `pos`: the position of the object
  * 3/ `rot`: the rotation of the object
  * 4/ `scale`: the scale of the object
  * 
  * If any of these properties have changed since the last time the function was called
  * (as indicated by `oldPos`, `oldRot`, and `oldScale`), the function returns `true`.
  * Otherwise (if all properties are unchanged), the function returns `false`.
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
  * This function calculates the full transformation matrix for a Node (object) given
  * its position (translation), rotation (orientation), and scale. It creates three
  * separate matrices for translation ,rotation and scale and then combines them using
  * multiplication to create the final transformation matrix
  * 
  * @returns The output returned by this function is a matrix representing the
  * transformation of the object's position (translation), rotation (rotationMatrix),
  * and scale (scaleMatrix). The return value is the result of concatenating these
  * matrices using the multiplication operation.
  */
	public Matrix4f getTransformation() {
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

 /**
  * This function returns the transformation matrix of the parent object if it has
  * changed or null otherwise.
  * 
  * @returns This function returns a ` Matrix4f` object that represents the transformation
  * matrix of the parent object (if it has changed).
  */
	private Matrix4f getParentMatrix() {
		if (parent != null && parent.hasChanged())
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

 /**
  * This function sets the `parent` field of the object to the passed `Transform` parameter.
  * 
  * @param parent The `parent` input parameter is used to set the transform that this
  * transform will be a child of. It assigns the specified transform as the "parent"
  * of this transform.
  */
	public void setParent(Transform parent) {
		this.parent = parent;
	}

 /**
  * This function takes the `pos` vector as input and returns the transformed position
  * of the object based on its parent transform's matrix. The transformation is performed
  * using the `getParentMatrix()` method that returns the parent transform's matrix.
  * 
  * @returns The function `getTransformedPos()` returns a `Vector3f` object that
  * represents the transformed position of an object relative to its parent entity's
  * transformation matrix. In other words: the original position (`pos`) of the object
  * is transformed using the parent entity's transformation matrix (obtained by calling
  * `getParentMatrix()`), resulting into the final (transformed) position of the object.
  */
	public Vector3f getTransformedPos() {
		return getParentMatrix().transform(pos);
	}

 /**
  * This function returns the transformed rotation of a child object relative to its
  * parent object. It first retrieves the rotational orientation of the parent object
  * (if it has one), then multiplies it by the local rotation of the child object.
  * 
  * @returns This function takes the `rot` quaternion as input and returns the transformed
  * rotation (resulting from applying the parent transformation) as a new quaternion.
  * 
  * In other words:
  * 
  * 	- If `parent` is null (i.e., there is no parent transformation), the output is
  * simply the `rot` quaternion unchanged.
  * 	- Otherwise (i.e., `parent` is not null), the output is the result of multiplying
  * the `parent` transformation's rotated basis (represented by `parentRotation`) with
  * the original rotation (represented by `rot`).
  * 
  * So the output is a quaternion that represents the resulting orientation of the
  * object after applying both the local and parent transformations.
  */
	public Quaternion getTransformedRot() {
		Quaternion parentRotation = new Quaternion(1, 0, 0, 0);

		if (parent != null)
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul(rot);
	}

 /**
  * This function returns the current position of an object as a `Vector3f`.
  * 
  * @returns The output returned by this function is `null`. The function `getPos()`
  * is undefined because there is no `pos` variable defined.
  */
	public Vector3f getPos() {
		return pos;
	}

 /**
  * This function sets the "pos" field of the object to the value passed as an argument
  * of type Vector3f.
  * 
  * @param pos The `pos` input parameter sets the position of the object represented
  * by the `GameObject` instance.
  */
	public void setPos(Vector3f pos) {
		this.pos = pos;
	}

 /**
  * The function `addToPos(Vector3f addVec)` adds the given `addVec` to the current
  * position of the object and sets the new position as the object's new position.
  * 
  * @param addVec The `addVec` input parameter adds the given vector to the current
  * position of the object.
  */
	public void addToPos(Vector3f addVec) { this.setPos(this.getPos().add(addVec)); }

 /**
  * This function returns the quaternion representation of the object's rotation (stored
  * as `rot`).
  * 
  * @returns The function `getRot()` returns a reference to a field `rot`, which is a
  * `Quaternion` object that represents the rotation of the object. The output of this
  * function is the current rotation of the object as a `Quaternion` object.
  */
	public Quaternion getRot() {
		return rot;
	}

 /**
  * This function sets the object's rotation to the specified Quaternion.
  * 
  * @param rotation The `rotation` input parameter sets the orientation (rotation) of
  * the object to the specified Quaternion value.
  */
	public void setRot(Quaternion rotation) {
		this.rot = rotation;
	}

 /**
  * This function returns the `scale` vector of a `Vector3f` object.
  * 
  * @returns The function `getScale()` returns a `Vector3f` object containing the
  * scaling factors for the object's dimensions (x. y and z). In other words it returns
  * `scale`.
  */
	public Vector3f getScale() {
		return scale;
	}

 /**
  * This function sets the scale of an object to the given `scale` vector.
  * 
  * @param scale The `scale` input parameter sets the scaling factor ( Vector3f value
  * ) for this GameObject.
  */
	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
 /**
  * This function override the `toString()` method of an object and returns an empty
  * string ("").
  * 
  * @returns The output returned by this function is an empty string ("").
  */
	@Override
	public String toString() { return "";
	}

}

