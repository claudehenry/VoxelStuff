package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Quaternion {

	private float x;
	private float y;
	private float z;
	private float w;

	public Quaternion() {
		this(0, 0, 0, 0);
	}

	
	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Quaternion(Vector3f axis, float angle) {
		float sinHalfAngle = (float) Math.sin(angle / 2);
		float cosHalfAngle = (float) Math.cos(angle / 2);

		this.x = axis.getX() * sinHalfAngle;
		this.y = axis.getY() * sinHalfAngle;
		this.z = axis.getZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}

 /**
  * This function calculates the length of a 3D vector using the Euclidean distance
  * formula: sqrt(x^2 + y^2 + z^2 + w^2).
  * 
  * 
  * @returns { float } The output returned by this function is the length of the vector
  * represented by the variables x ,y ,z and w .
  * The formula used to calculate the length is the square root of the sum of the
  * squares of the individual components.
  * Therefore , the output would be a floating point number representing the magnitude
  * or length of the vector.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

 /**
  * This function normalizes the quaternion by dividing each component by its length
  * (a scalar value). The resulting quaternion has the same direction as the original
  * one but with a length of 1.
  * 
  * 
  * @returns { Quaternion } This function takes a quaternion as input and returns a
  * new quaternion that has been normalized to have a length of 1. The output quaternion
  * is computed by dividing each component of the input quaternion by its length. In
  * other words , it normalizes the quaternion and returns a unit quaternion.
  */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

 /**
  * The function "conjugate" returns a new quaternion that is the complex conjugate
  * of the current quaternion.
  * 
  * 
  * @returns { Quaternion } The output returned by this function is a new `Quaternion`
  * object that is the conjugate of the current `Quaternion` object. The conjugate of
  * a quaternion is obtained by changing the sign of the x , y and z components while
  * leaving the w component unchanged. In other words , the conjugate of a quaternion
  * `q` is a quaternion `q'` such that `q'.x = - q.x`, `q'.y = - q.y` , and `q'.z = -
  * q.z`.
  */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

 /**
  * This function performs a scaling of the quaternion components by a factor `r`.
  * 
  * 
  * @param { float } r - The `r` input parameter multiplies each component of the
  * Quaternion by its value.
  * 
  * @returns { Quaternion } The output of this function is a new Quaternion object
  * that represents the result of multiplying the current quaternion with a scalar
  * value (r). The resulting quaternion has the same values for w , x , y and z as the
  * current quaternion but with each component scaled by the factor r.
  */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

 /**
  * This function implements the multiplication of two quaternions and returns a new
  * quaternion representing the result of the multiplication.
  * 
  * 
  * @param { Quaternion } r - The `r` input parameter represents the second quaternion
  * that is being multiplied with the current quaternion.
  * 
  * @returns { Quaternion } This function takes a `Quaternion` object `r` as input and
  * returns a new `Quaternion` object that represents the result of multiplying the
  * current quaternion with `r`. The output quaternion has components `w_, x_, y_, z_`
  * that are computed using the dot product and cross product of the current and input
  * quaternions.
  */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

 /**
  * This function takes a `Vector3f` argument `r` and computes the quaternion product
  * of the current quaternion and `r`. It returns a new quaternion representing the
  * result of the multiplication.
  * 
  * 
  * @param { Vector3f } r - The `r` input parameter is a vector that represents the
  * point on which the quaternion operation is performed. It is used to compute the
  * dot product of the quaternion and the input vector.
  * 
  * @returns { Quaternion } The function `mul(Vector3f r)` takes a vector as input and
  * returns a new quaternion as output. The quaternion is derived from the dot product
  * of the vector with the current quaternion's axes.
  * 
  * In other words:
  * 
  * The output is a new quaternion that represents the result of rotating the input
  * vector by the current quaternion's orientation.
  */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

 /**
  * This function calculates the difference between two quaternions by subtracting the
  * values of each component from the corresponding components of the other quaternion.
  * 
  * 
  * @param { Quaternion } r - The `r` input parameter represents the second quaternion
  * that is being subtracted from the first quaternion. It is used to calculate the
  * difference between the two quaternions component-wise.
  * 
  * @returns { Quaternion } The output returned by this function is a new `Quaternion`
  * object that represents the difference between the current quaternion and the
  * specified `r` quaternion. The difference is calculated as the negative of the
  * corresponding component of `r` (w minus the W component of `r`, x minus the X
  * component of `r`, y minus the Y component of `r`, and z minus the Z component of
  * `r`).
  */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * This function adds two quaternions together component-wise (i.e., adding the
  * corresponding elements of the two quaternion vectors). It returns a new quaternion
  * object representing the sum of the two input quaternions.
  * 
  * 
  * @param { Quaternion } r - The `r` input parameter represents another Quaternion
  * object that is being added to the current Quaternion. It is added component-wise
  * to the current Quaternion's components (wxyz).
  * 
  * @returns { Quaternion } The output returned by this function is a new Quaternion
  * object that represents the sum of two quaternions: `this` and `r`. The elements
  * of the resulting quaternion are simply the corresponding elements of the two input
  * quaternions added element-wise: `w + r.getW()`, `x + r.getX()`, `y + r.getY()`,
  * and `z + r.getZ()`.
  */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

 /**
  * This function takes the position of an object (represented by a Vector3f) and
  * returns a rotation matrix as a Matrix4f. The rotation is constructed from the
  * object's forward vector (normalized), up vector (always normalized to (0,-1,-1)),
  * and right vector (calculated from the other two vectors).
  * 
  * 
  * @returns { Matrix4f } The output returned by this function is a `Matrix4f` object
  * that represents a rotation matrix based on the given `Vector3f` parameters `forward`,
  * `up`, and `right`. The matrix is constructed using the initializers `new
  * Matrix4f().initRotation(forward`, `up`, `right)`, which creates a rotation matrix
  * with the specified forward and up vectors as the major and minor axes of rotation
  * around the origin.
  */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

 /**
  * The `dot` function computes the dot product of two quaternions.
  * 
  * 
  * @param { Quaternion } r - The `r` parameter is a Quaternion object that represents
  * another orientation to be transformed against the current orientation represented
  * by the Quaternion object on which the dot() function is called.
  * 
  * @returns { float } The output returned by this function is a float value that
  * represents the dot product of two quaternions. The function takes one quaternion
  * as an argument and returns the result of multiplying the values of all components
  * of the input quaternion with the corresponding components of another quaternion.
  * The result is a scalar value that represents the amount of agreement between the
  * two quaternions.
  */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

 /**
  * This function performs a non-linear interpolation (NLerp) of the current quaternion
  * to reach a destination quaternion based on the `lerpFactor` value. It normalizes
  * the result and returns it.
  * 
  * 
  * @param { Quaternion } dest - The `dest` input parameter is the destination quaternion
  * that the function is attempting to reach through linear interpolation.
  * 
  * @param { float } lerpFactor - The `lerpFactor` input parameter specifies the
  * interpolation factor between the current and destination quaternions. It ranges
  * from 0 (completely current quaternion) to 1 (completely destination quaternion).
  * 
  * @param { boolean } shortest - The `shortest` input parameter determines whether
  * to clamp the resulting quaternion to the range (-180.0f..180.0f) if its dot product
  * with the dest quaternion is negative. This ensures that the interpolation results
  * remain within a reasonable range and avoids potential numeric instability issues.
  * 
  * @returns { Quaternion } The function `NLerp` returns a quaternion that is a linear
  * interpolation between the current quaternion and the destination quaternion. The
  * interpolation is performed based on the length of the vectors and whether the
  * shortcutest route is desired or not. The output quaternion is normalized.
  */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

 /**
  * This function performs a quaternion-based Slerp (smooth lattice interpolation) of
  * two quaternions based on a given lerp factor and optionally uses the shorter of
  * the two paths if 'shortest' is set to true.
  * 
  * 
  * @param { Quaternion } dest - The `dest` input parameter represents the target
  * quaternion to which the source quaternion will be interpolated towards.
  * 
  * @param { float } lerpFactor - The `lerpFactor` input parameter controls the
  * interpolation speed of the quaternion blend. It ranges from 0 to 1 and determines
  * how much of the destination quaternion should be used at each step of the
  * interpolation. A value of 0 will result i pure source quaternion animation and a
  * value of 1 will result pure destination quaternion animation.
  * 
  * @param { boolean } shortest - The `shortest` input parameter controls whether the
  * interpolation is performed along theshortest path between the starting and ending
  * quaternions. If `shortest` is `true`, the function returns the interpolation between
  * the two quaternions that lies on the Shortest Path between them.
  * 
  * @returns { Quaternion } The output of this function is a new Quaternion that
  * linearly interpolates between the current quaternion and a destination quaternion.
  * The interpolation is done using spherical law of cosines and the function returns
  * the resulting quaternion.
  */
	public Quaternion SLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		final float EPSILON = 1e3f;

		float cos = this.dot(dest);
		Quaternion correctedDest = dest;

		if (shortest && cos < 0) {
			cos = -cos;
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());
		}

		if (Math.abs(cos) >= 1 - EPSILON)
			return NLerp(correctedDest, lerpFactor, false);

		float sin = (float) Math.sqrt(1.0f - cos * cos);
		float angle = (float) Math.atan2(sin, cos);
		float invSin = 1.0f / sin;

		float srcFactor = (float) Math.sin((1.0f - lerpFactor) * angle) * invSin;
		float destFactor = (float) Math.sin((lerpFactor) * angle) * invSin;

		return this.mul(srcFactor).add(correctedDest.mul(destFactor));
	}

	// From Ken Shoemake's "Quaternion Calculus and Fast Animation" article
	public Quaternion(Matrix4f rot) {
		float trace = rot.get(0, 0) + rot.get(1, 1) + rot.get(2, 2);

		if (trace > 0) {
			float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
			w = 0.25f / s;
			x = (rot.get(1, 2) - rot.get(2, 1)) * s;
			y = (rot.get(2, 0) - rot.get(0, 2)) * s;
			z = (rot.get(0, 1) - rot.get(1, 0)) * s;
		} else {
			if (rot.get(0, 0) > rot.get(1, 1) && rot.get(0, 0) > rot.get(2, 2)) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(0, 0) - rot.get(1, 1) - rot.get(2, 2));
				w = (rot.get(1, 2) - rot.get(2, 1)) / s;
				x = 0.25f * s;
				y = (rot.get(1, 0) + rot.get(0, 1)) / s;
				z = (rot.get(2, 0) + rot.get(0, 2)) / s;
			} else if (rot.get(1, 1) > rot.get(2, 2)) {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(1, 1) - rot.get(0, 0) - rot.get(2, 2));
				w = (rot.get(2, 0) - rot.get(0, 2)) / s;
				x = (rot.get(1, 0) + rot.get(0, 1)) / s;
				y = 0.25f * s;
				z = (rot.get(2, 1) + rot.get(1, 2)) / s;
			} else {
				float s = 2.0f * (float) Math.sqrt(1.0f + rot.get(2, 2) - rot.get(0, 0) - rot.get(1, 1));
				w = (rot.get(0, 1) - rot.get(1, 0)) / s;
				x = (rot.get(2, 0) + rot.get(0, 2)) / s;
				y = (rot.get(1, 2) + rot.get(2, 1)) / s;
				z = 0.25f * s;
			}
		}

		float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}

 /**
  * This function returns the forward direction of the object as a `Vector3f` rotated
  * by the orientation of the current instance. In other words - the function generates
  * the direction towards the front of the object.
  * 
  * 
  * @returns { Vector3f } The output returned by this function is a Vector3f object
  * that represents the forward direction of the entity based on its orientation. The
  * function first creates a Vector3f with all zeros and then rotates it using the
  * orientation of the entity to get the forward direction.
  */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

 /**
  * This function returns the backwards direction of the object as a `Vector3f`,
  * obtained by rotating the object's own orientation by 180 degrees around all three
  * axes.
  * 
  * 
  * @returns { Vector3f } The function `getBack()` returns a `Vector3f` object
  * representing the back direction of the object. Specifically:
  * 
  * 	- The `Vector3f(0.0f)` creates a vector with all components set to 0.
  * 	- The `-1.0f` is multiplied element-wise with the input vector to create a vector
  * with all components negative.
  * 	- The result is then rotated by the contents of the object using the `rotate()`
  * method.
  * 
  * The output is a vector pointing away from the object along the negative Z-axis.
  */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

 /**
  * This function returns a new `Vector3f` representing the up direction rotated by
  * the orientation of the object.
  * 
  * 
  * @returns { Vector3f } The function `getUp()` returns a new `Vector3f` object that
  * represents the direction perpendicular to the surface of the object at the current
  * position. The vector is created by rotating the standard unit vector `(0`, `1`,
  * `0)` by the same rotation as the object's orientation.
  */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

 /**
  * The given function `getDown()` returns a vector that points downward from the
  * object referred to by `this`, calculated using the rotation of `this` object.
  * 
  * 
  * @returns { Vector3f } The function `getDown()` returns a vector pointing downwards
  * from the current position of the object. Specifically:
  * 
  * 	- The x-component of the vector is 0 (zero)
  * 	- The y-component is -1 (minus one)
  * 	- The z-component is 0 (zero)
  * 
  * This means that the vector points straight downwards from the current position of
  * the object.
  */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

 /**
  * This function returns a new vector that is the right vector of the object to which
  * it is called. It calculates the right vector by multiplying a default right vector
  * (1., 0., 0.) with the object's rotation using the `rotate()` method.
  * 
  * 
  * @returns { Vector3f } The function `getRight()` returns a newly created `Vector3f`
  * object that is the result of rotating the current instance of `Vector3f` by the
  * axis specified by the method's name (i.e., the "right" direction). In other words:
  * 
  * Output: A new `Vector3f` object representing the rotation of the current instance
  * by 90 degrees clockwise around the x-axis.
  */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

 /**
  * The function `getLeft()` returns a new `Vector3f` instance that represents the
  * left direction of the object (i.e., to the negative x-axis), obtained by rotating
  * the current orientation of the object by 90 degrees clockwise around the y-axis
  * using the `rotate()` method.
  * 
  * 
  * @returns { Vector3f } The function `getLeft()` returns a vector with coordinates
  * `-1`, `0`, and `0`. To see why this happens think of Rotation matrices like so:
  * if we wanted to move our position from point (1 - (a * b), and +b along the axis
  * of a by some angle (measured anticlockwise from x-axis)), and this applies both
  * the rotations one after another where x is whatever angle of rotation along the 
  * y-axis that has to be performed for aligning y coordinate at point p towards +y.
  * Afterward b units to move from x=0 along axis a by applying Rotation along x -
  * axis about x=1 such that after that rotation result will get (a*b - unit circle
  * angle towards x of vector (1-b), a and b can have different values if so point
  * lies below unit circle after two rotations as mentioned. Here all these operations
  * will align y= 0 for new point lying on line connecting origi(since z is now 0 and
  * y component remains at origin i e zero).
  * 
  * Since rotation doesn’t modify the original coordinates of the points which remain
  * stationary relative to the reference frame attached with a moving object then
  * rotation(new-position. Rotate(new-posistion;-Math.toRadians((rotationAngle ==0 )||
  * return- zero) : otherwise vector along x and y axes about origin  as already mentioned;
  * So original points new-position coordinates got altered but by the angle difference
  * of (oldpoint - origiposition)^T( oldpostiion*transpose()) - Tranспоose matrix
  * should have its offdiagonal terms set to zero
  * Finally after multiplication vector result gets updated using addition; Now lets
  * move back atleast on coordinate x so that the y
  */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

 /**
  * This function sets the values of all elements of a Quaternion object (x`, y`, z`,
  * and w`) to the specified floats.
  * 
  * 
  * @param { float } x - The `x` input parameter sets the value of the `x` component
  * of the Quaternion.
  * 
  * @param { float } y - In the provided `set()` function for a `Quaternion` class`,
  * the `y` input parameter sets the value of the quaternion's `y` component.
  * 
  * @param { float } z - The `z` input parameter sets the value of the Quaternion's
  * vector component.
  * 
  * @param { float } w - The `w` input parameter represents the fourth component of
  * the Quaternion and is used to set the scaling aspect of the quaternion. It has no
  * effect on the orientation part of the quaternion.
  * 
  * @returns { Quaternion } The function `set` takes four float parameters and sets
  * the xyzww components of a Quaternion object to those values. The output returned
  * by the function is the modified Quaternion object itself. In other words:
  * 
  * Output: A reference to the modified Quaternion object (`this`).
  */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * @param eulerAngles - @see <a href="https://en.wikipedia.org/wiki/Euler_angles#Proper_Euler_angles">Wikipedia's Article on Euler Angles</a> for a description
	 *                    of their usage/definition.
	 * @return The {@link Quaternion} associated with the Euler angles.
	 */
	public static Quaternion fromEuler(Vector3f eulerAngles) {
		//eulerAngles = [phi, theta, yota]
		float phi = eulerAngles.getX();
		float theta = eulerAngles.getY();
		float yota = eulerAngles.getZ();


		//locally store all cos/sin so we don't have to calculate them twice each
		float cos_half_phi = (float) Math.cos(phi / 2.0f);
		float sin_half_phi = (float) Math.sin(phi / 2.0f);
		float cos_half_theta = (float) Math.cos(theta / 2.0f);
		float sin_half_theta = (float) Math.sin(theta / 2.0f);
		float cos_half_yota = (float) Math.cos(yota / 2.0f);
		float sin_half_yota = (float) Math.sin(yota / 2.0f);

		float q0 = cos_half_phi * cos_half_theta * cos_half_yota + sin_half_phi * sin_half_theta * sin_half_yota;
		float q1 = sin_half_phi * cos_half_theta * cos_half_yota - cos_half_phi * sin_half_theta * sin_half_yota;
		float q2 = cos_half_phi * sin_half_theta * cos_half_yota + sin_half_phi * cos_half_theta * sin_half_yota;
		float q3 = cos_half_phi * cos_half_theta * sin_half_yota - sin_half_phi * sin_half_theta * cos_half_yota;

		return new Quaternion(q0, q1, q2, q3);

	}

 /**
  * This function sets the components of the current Quaternion object to the corresponding
  * components of the given Quaternion object "r".
  * 
  * 
  * @param { Quaternion } r - The `r` input parameter is a quaternion that contains
  * the values to be assigned to the member variables of the current quaternion object.
  * 
  * @returns { Quaternion } The output returned by this function is a reference to the
  * object itself (`this`). In other words:
  * 
  * output = this
  */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

 /**
  * This function does not do anything because it is undefined. The `x` variable is
  * not initialized and there is no code that sets its value. Therefore the function
  * will always return `undefined`.
  * 
  * 
  * @returns { float } The function `getX()` returns `undefined` because the variable
  * `x` is not initialized.
  */
	public float getX() {
		return x;
	}

 /**
  * The provided code is a `setX` method that takes a single `float` parameter `x`.
  * It assigns the value of `x` to the instance field `x` of the current object.
  * 
  * 
  * @param { float } x - The `x` input parameter sets the value of the field `x` within
  * the object to the passed `float` value.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * This function is a getter method that returns the value of the instance variable
  * 'y'.
  * 
  * 
  * @returns { float } The function returns `float` value of `y` which is currently
  * `undefined`. Therefore the output returned by this function is `NaN` (Not a Number)
  * since `y` is not defined.
  */
	public float getY() {
		return y;
	}

 /**
  * The function `setY(float y)` sets the value of the member variable `y` of the
  * current object to the specified `y` value.
  * 
  * 
  * @param { float } y - The `y` input parameter sets the value of the field `y` of
  * the object that the function is called on.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * This function returns the value of the 'z' field of the object.
  * 
  * 
  * @returns { float } The output returned by this function is "NaN" (Not a Number)
  * because the field "z" is undefined.
  */
	public float getZ() {
		return z;
	}

 /**
  * This function sets the value of the member variable "z" to the provided float
  * parameter "z".
  * 
  * 
  * @param { float } z - The `z` input parameter sets the value of the field `z` of
  * the object on which the method is called.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * This function public float getW() returns the value of the field w.
  * 
  * 
  * @returns { float } The output returned by this function is "undefined".
  */
	public float getW() {
		return w;
	}

 /**
  * This function sets the value of the "w" field of the object to which it is called.
  * 
  * 
  * @param { float } w - The `w` input parameter is used to set the value of the `w`
  * field of the object.
  */
	public void setW(float w) {
		this.w = w;
	}

 /**
  * This function implements the `equals()` method for the `Quaternion` class. It
  * compares the current quaternion (represented by the `this` object) with another
  * quaternion (represented by the `r` object) and returns `true` if all four components
  * (x/y/z/w) are equal.
  * 
  * 
  * @param { Quaternion } r - The `r` input parameter is a reference to another
  * `Quaternion` object that is being compared for equality with the current quaternion.
  * 
  * @returns { boolean } This function takes a `Quaternion` object `r` as a parameter
  * and returns a `boolean` value indicating whether the current object is equal to
  * `r`. The output returned by this function is `true` if all the components of the
  * current object are equal to the corresponding components of `r`, and `false`
  * otherwise. In other words., the function compares the `x`, `y`, `z`, and `w`
  * components of the current object with those of `r` and returns `true` only if they
  * are all equal.
  */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}


