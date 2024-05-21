package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * in Java provides a way to represent 3D rotations and transformations. It has several
 * methods for converting between rotation matrices, quaternions, and other representations
 * of 3D rotations. Additionally, it includes utility methods such as dot product
 * calculation and Nlerp and Sliderp functions for interpolating between two quaternions.
 */
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
	 * computes the Euclidean distance of a vector from origin, by squaring the coordinates
	 * and summing them, then taking the square root.
	 * 
	 * @returns the square root of the sum of the squares of the cartesian coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes a given quaternion by dividing its components by their absolute value,
	 * resulting in a unit quaternion.
	 * 
	 * @returns a normalized quaternion representation of the original quaternion.
	 * 
	 * 	- The function returns a new `Quaternion` instance with the normalized values of
	 * the original quaternion's components.
	 * 	- The length of the original quaternion is calculated using the `length()` method
	 * before normalization.
	 * 	- The normalized values of the components are computed by dividing each component
	 * by the length of the quaternion.
	 * 
	 * Therefore, the output of the `normalized` function is a new quaternion with
	 * normalized components, where the length of the quaternion is equal to 1.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * creates a new quaternion with the conjugate of its components.
	 * 
	 * @returns a new quaternion with the same magnitude as the original but with the
	 * orientation of the axis swapped.
	 * 
	 * 	- The returned object is a `Quaternion` instance representing the conjugate of
	 * the original input quaternion.
	 * 	- The `w`, `x`, `y`, and `z` components of the conjugate quaternion are negative
	 * reciprocals of the corresponding components of the input quaternion.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies a quaternion by a scalar value and returns a new quaternion with the result.
	 * 
	 * @param r 4D scalar value that is multiplied with the quaternion's components to
	 * produce the resulting quaternion.
	 * 
	 * @returns a new quaternion instance representing the product of the input quaternion
	 * and a scalar value.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * takes a `Quaternion` argument `r` and multiplies its components by the current
	 * quaternion's components, storing the result in a new quaternion object.
	 * 
	 * @param r 4D quaternion to be multiplied with the current quaternion, resulting in
	 * the final output quaternion.
	 * 
	 * 	- `w`: The scalar value representing the real part of the quaternion.
	 * 	- `x`, `y`, `z`: The scalar values representing the imaginary parts of the quaternion.
	 * 
	 * The function computes the product of two quaternions by first computing the scalar
	 * product of the real and imaginary parts of each quaternion, and then combining
	 * these products using the formula for the quaternion product. The result is a new
	 * quaternion representing the product of the two original quaternions.
	 * 
	 * @returns a new Quaternion object containing the product of the input Quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * computes the dot product of a quaternion and a vector, and returns the resulting
	 * quaternion.
	 * 
	 * @param r 3D vector to which the quaternion is multiplied.
	 * 
	 * 	- `getX()` and `getY()`: These methods retrieve the x- and y-coordinates of the
	 * vector, respectively.
	 * 	- `getZ()`: This method retrieves the z-coordinate of the vector.
	 * 	- `w_`, `x_,`, `y_`, and `z_`: These variables are created by multiplying the `r`
	 * object with the corresponding components of the function's input.
	 * 
	 * @returns a quaternion representing the multiplication of the input vector and a
	 * rotation matrix.
	 * 
	 * 	- The `Quaternion` object has four components: `w`, `x`, `y`, and `z`, which
	 * represent the quaternion's real and imaginary parts, respectively.
	 * 	- The values of these components are computed as a result of multiplying the input
	 * vector `r` by a complex number.
	 * 	- The quaternion's conjugate (i.e., the negative of its magnitude) is ensured by
	 * the use of the `-` operator in the calculation of `w`.
	 * 	- The values of `x`, `y`, and `z` are computed as products of `w` and the components
	 * of `r`.
	 * 	- The quaternion's norm (i.e., its magnitude) is equal to the square root of the
	 * sum of the squares of its components.
	 * 
	 * In summary, the `mul` function returns a Quaternion object that represents the
	 * result of multiplying the input vector by a complex number, and it ensures that
	 * the quaternion's conjugate is negative when the input vector is not equal to zero.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * takes a `Quaternion` argument `r` and returns a new `Quaternion` instance with the
	 * difference between the current quaternion's values and those of the input quaternion.
	 * 
	 * @param r 4D vector to be subtracted from the current 4D vector, resulting in the
	 * updated 4D vector of the function.
	 * 
	 * 	- `w`: The real part of the quaternion representation.
	 * 	- `x`, `y`, and `z`: The imaginary parts of the quaternion representation.
	 * 
	 * @returns a new Quaternion instance representing the difference between the input
	 * Quaternion and the current Quaternion.
	 * 
	 * 	- The output is of type `Quaternion`, indicating that it represents a quaternion
	 * value.
	 * 	- The `w`, `x`, `y`, and `z` components of the output represent the difference
	 * between the input `r` and the current quaternion, respectively. These components
	 * are scaled by the corresponding values of the input `r`.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * representing the sum of the original object's values with those of `r`.
	 * 
	 * @param r 4D vector to be added to the current quaternion.
	 * 
	 * 	- `w`: The magnitude (or length) of the quaternion.
	 * 	- `x`, `y`, and `z`: The axis components of the quaternion.
	 * 
	 * @returns a new quaternion instance with the sum of the input quaternions' coefficients.
	 * 
	 * The output is a new Quaternion object containing the sum of the inputs' `w`, `x`,
	 * `y`, and `z` components.
	 * The resulting Quaternion has the same orientation as the input quaternions, but
	 * with their magnitudes added together.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * generates a rotation matrix based on the input vectors `forward`, `up`, and `right`.
	 * The resulting matrix represents a rotation around the origin, with the `forward`
	 * vector serving as the axis of rotation.
	 * 
	 * @returns a 4x4 rotation matrix.
	 * 
	 * 	- The `Matrix4f` object returned represents a rotation matrix in 3D space, as
	 * indicated by the `initRotation` method used to construct it.
	 * 	- The three components of the rotation matrix (`forward`, `up`, and `right`) are
	 * defined as vectors in 3D space, where each vector has a magnitude of 1 and an
	 * orientation that represents the desired rotation about an axis.
	 * 	- The rotation is performed around the x-axis, y-axis, and z-axis, respectively,
	 * as indicated by the components of the rotation matrix.
	 * 	- The magnitude of the rotation matrix is equal to the product of the magnitudes
	 * of the three vectors that make up the rotation.
	 * 	- The orientation of the rotation matrix is determined by the dot product of the
	 * vectors that make up the rotation, and is represented by the determinant of the matrix.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * computes the dot product of a `Quaternion` and another `Quaternion`, returning a
	 * float value.
	 * 
	 * @param r 4D quaternion object to which the dot product is being computed.
	 * 
	 * 	- `x`, `y`, `z`, and `w` are the components of the quaternion representation.
	 * 	- `getX()`, `getY()`, `getZ()`, and `getW()` are methods that return the respective
	 * components of the input quaternion.
	 * 
	 * @returns a scalar value representing the dot product of the input quaternion and
	 * a given quaternion.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a linear interpolation of a Quaternion based on a given factor and whether
	 * the resulting quaternion should be shortest. It returns a new quaternion that
	 * represents the interpolated value.
	 * 
	 * @param dest 4-dimensional vector that will be interpolated between using the `Lerp`
	 * method.
	 * 
	 * 	- `Quaternion dest`: This is the destination quaternion to which the interpolation
	 * will be applied.
	 * 	- `lerpFactor`: This is a scalar value that determines the interpolation amount
	 * between the two inputs.
	 * 	- `shortest`: This is a boolean value indicating whether the interpolation should
	 * be performed in the shortest path or not.
	 * 	- `this`: This is the current quaternion being interpolated, used to calculate
	 * the intermediate quaternions.
	 * 
	 * @param lerpFactor 0-to-1 value of the interpolation between the current Quaternion
	 * and the destination Quaternion.
	 * 
	 * @param shortest 4-vector's closest orientation to the destination vector, which
	 * is determined by comparing the dot product of the current and destination vectors
	 * and adjusting the result accordingly.
	 * 
	 * @returns a Quaternion that represents the intermediate value between the given
	 * `dest` Quaternion and the result of linearly interpolating between the input values.
	 * 
	 * 	- The output is a quaternion, which represents a 3D rotational transformation.
	 * 	- The quaternion is derived from the input parameters `dest`, `lerpFactor`, and
	 * `shortest`.
	 * 	- If `shortest` is true, and the dot product of the input quaternion with the
	 * destination quaternion is negative, then the output quaternion is flipped sign.
	 * 	- The output quaternion represents the linear interpolation between the input
	 * quaternions, with the factor `lerpFactor` determining the interpolation rate.
	 * 	- The resulting quaternion is normalized to have a length of 1.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * computes a smooth interpolation between two Quaternions based on the Lerp formula,
	 * while ensuring that the resulting quaternion is shortest (i.e., has the smallest
	 * magnitude) when the interpolation factor is close to 1.
	 * 
	 * @param dest 4-dimensional quaternion to which the lerping is applied, and it is
	 * used to calculate the corrected quaternion based on the shortest distance between
	 * the source and destination quaternions.
	 * 
	 * 	- `dest.getW()` represents the scalar component of the quaternion.
	 * 	- `dest.getX()`, `dest.getY()`, and `dest.getZ()` represent the vector components
	 * of the quaternion.
	 * 	- `dest` is a quaternion object that contains the desired destination value for
	 * the linear interpolation.
	 * 
	 * @param lerpFactor 0-1 interpolating factor between the source and destination
	 * quaternions, which determines how quickly the quaternion is transformed to the
	 * destination value.
	 * 
	 * @param shortest shortest path between the source and destination quaternions, which
	 * is used to determine the sign of the cosine value in the calculation of the lerp
	 * factor.
	 * 
	 * @returns a Quaternion that interpolates between two given Quaternions based on the
	 * provided lerp factor and shortest path preference.
	 * 
	 * 	- The output is a Quaternion object representing a linear interpolation between
	 * the given source and dest quaternions.
	 * 	- The quaternion is constructed by multiplying the source quaternion by a scalar
	 * factor and then adding the destination quaternion.
	 * 	- The scalar factor is calculated based on the given lerp factor and angle of interpolation.
	 * 	- The quaternion has the following properties:
	 * 	+ W component: The magnitude of the quaternion, which represents the speed at
	 * which the interpolation occurs.
	 * 	+ X, Y, Z components: The direction of the quaternion, which represents the
	 * orientation of the object being interpolated.
	 * 
	 * Note: The output quaternion is normalized, meaning that its magnitude is equal to
	 * 1.
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
	 * rotates a `Vector3f` instance by the angle of the object's orientation, returning
	 * the resulting forward vector.
	 * 
	 * @returns a vector representing the direction of the rotated object.
	 * 
	 * The function returns a `Vector3f` object representing the forward direction of the
	 * rotated reference object.
	 * The returned vector has a magnitude of 1 and points in the positive z-axis direction.
	 * The rotation is performed using the `rotate()` method, which takes the reference
	 * object as an argument and rotates it around the origin by the specified angle.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the Z-axis, resulting in a
	 * vector pointing towards the negative Z direction.
	 * 
	 * @returns a rotated version of the original vector, with its x-component unchanged
	 * and its y- and z-components reversed.
	 * 
	 * The output is a new Vector3f object that represents the back direction of the
	 * original vector.
	 * 
	 * The rotate method used in the function rotates the original vector by 90 degrees
	 * around the x-axis, which results in a vector pointing towards the negative z-axis.
	 * 
	 * Therefore, the output vector has an x-coordinate of 0, a y-coordinate of 0, and a
	 * z-coordinate of -1.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the z-axis, resulting in a new vector that
	 * points upwards from the original position.
	 * 
	 * @returns a rotated version of the original vector.
	 * 
	 * 	- The output is a `Vector3f` object representing the upward direction from the
	 * rotation of the current position.
	 * 	- The magnitude of the vector is 1, indicating that the upward direction is always
	 * perpendicular to the current position.
	 * 	- The direction of the vector is constant and points towards the positive z-axis.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the z-axis to create a new vector pointing
	 * downward from the original position.
	 * 
	 * @returns a vector representing the downward direction, with coordinates (0, -1,
	 * 0) rotated based on the object's orientation.
	 * 
	 * 	- The output is a `Vector3f` object representing the downward direction from the
	 * current position of the `Object3d` instance.
	 * 	- The vector has a magnitude of 0 in the x-axis and y-axis directions, indicating
	 * that it points directly downwards.
	 * 	- The z-component of the vector is negative, which means the direction of the
	 * vector is towards the negative z-axis.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees to the right, resulting in a new vector that points
	 * in the right direction.
	 * 
	 * @returns a rotated vector representation of the object's right direction.
	 * 
	 * The function returns a new `Vector3f` object that represents the rightward direction
	 * of the rotated vector. This is calculated by applying a rotation to the original
	 * vector using the `rotate` method.
	 * 
	 * The rotate method takes the current vector as input and returns a new vector that
	 * has been rotated based on the specified angle and axis. In this case, the angle
	 * is 90 degrees (i.e., the vertical angle) and the axis is the current vector itself.
	 * As a result, the returned vector will be perpendicular to the original vector in
	 * the horizontal plane.
	 * 
	 * The properties of the returned vector include its magnitude (which is equal to the
	 * magnitude of the original vector), its direction (which is perpendicular to the
	 * original vector), and its axis (which is the same as the original vector).
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the x-axis, resulting in a new vector pointing
	 * leftwards.
	 * 
	 * @returns a rotated vector representing the left component of the provided object.
	 * 
	 * 	- The Vector3f object represents a 3D vector with x, y, and z components.
	 * 	- The rotation method is applied to the original vector, which results in a new
	 * vector that is rotated by a certain angle around the x, y, or z axis.
	 * 	- In this case, the rotation is applied around the x-axis, resulting in a leftward
	 * rotation of the vector.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * sets the values of a `Quaternion` object's `x`, `y`, `z`, and `w` fields to the
	 * input values.
	 * 
	 * @param x 3D position of the quaternion in the x-axis direction.
	 * 
	 * @param y 2D component of the quaternion.
	 * 
	 * @param z 3D coordinate of the quaternion in the x-y plane, and it is being set to
	 * the value provided by the user.
	 * 
	 * @param w 4th component of the quaternion, which is updated to match the value
	 * passed in.
	 * 
	 * @returns a reference to the same `Quaternion` instance, allowing for chaining of
	 * method calls.
	 * 
	 * 	- The `this` keyword in the function signature refers to the Quaternion object
	 * itself, indicating that the method modifies the object's fields directly.
	 * 	- The four arguments passed to the function (`x`, `y`, `z`, and `w`) correspond
	 * to the fields of the Quaternion class: `x`, `y`, `z`, and `w`.
	 * 	- The method returns a reference to the same Quaternion object, indicating that
	 * the method is an in-place update.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * converts Euler angles (phi, theta, yota) to a quaternion representation, which can
	 * be used for 3D rotations and transformations.
	 * 
	 * @param eulerAngles 3D Euler angles of a rotation, specifically the phi, theta, and
	 * yota angles, which are used to calculate the quaternion representation of the rotation.
	 * 
	 * 	- `phi`: The zenith angle of the quaternion, representing the rotation around the
	 * x-axis.
	 * 	- `theta`: The rotation angle around the y-axis, representing the rotation around
	 * the y-axis.
	 * 	- `yota`: The rotation angle around the z-axis, representing the rotation around
	 * the z-axis.
	 * 
	 * The function then calculates and stores various cosine and sine values of these
	 * angles, which are used to construct the final quaternion representation.
	 * 
	 * @returns a quaternion representation of the given Euler angles.
	 * 
	 * 	- The `Quaternion` object represents a 4D quaternion representation of the input
	 * Euler angles.
	 * 	- The `q0`, `q1`, `q2`, and `q3` fields represent the four components of the
	 * quaternion, which are computed using the input angles and various trigonometric
	 * functions (cos, sin, tan).
	 * 	- Each component of the quaternion represents a specific rotation about a particular
	 * axis in 3D space. The quaternion can be used to perform rotations on objects in
	 * 3D space by multiplying it with the object's position vector.
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
	 * sets the values of a `Quaternion` object to those of another `Quaternion` object.
	 * 
	 * @param r 4-dimensional vector that contains the new values for the quaternion's
	 * components, which are then set as the new values of the quaternion object through
	 * the `set()` method.
	 * 
	 * 	- `getX()`
	 * 	- `getY()`
	 * 	- `getZ()`
	 * 	- `getW()`
	 * 
	 * @returns a reference to the original `Quaternion` object, unchanged.
	 * 
	 * The function takes in four arguments representing the x, y, z, and w components
	 * of the input quaternion. These arguments are used to set the corresponding components
	 * of the current quaternion object to the same values as the input quaternion. As a
	 * result, the current quaternion object is updated to match the input quaternion.
	 * 
	 * The function returns the updated quaternion object, indicating that the method has
	 * completed successfully and the new state of the object can be used.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * returns the value of the variable `x`.
	 * 
	 * @returns a floating-point representation of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of its receiver to the specified float parameter.
	 * 
	 * @param x float value that will be assigned to the `x` field of the object on which
	 * the `setX()` method is being called.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which is a `float` variable.
	 * 
	 * @returns a floating-point value representing the y-coordinate of a point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field of the invoking object to the provided float argument.
	 * 
	 * @param y 2D coordinate of a point that is being manipulated by the `setY()` method.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field, which is assumed to be a floating-point number.
	 * 
	 * @returns the value of the `z` field.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the `z` field of its receiver to the provided float argument.
	 * 
	 * @param z 3D coordinate of an object in space, which is assigned to the class member
	 * variable `z` upon call of the `setZ()` method.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * returns the value of the `w` field.
	 * 
	 * @returns a floating-point value representing the variable `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the `w` field of its object reference to the passed float value.
	 * 
	 * @param w floating-point value that sets the width of the shape.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares a `Quaternion` object with another `Quaternion` object based on the values
	 * of its components: `x`, `y`, `z`, and `w`. If all components are equal, the method
	 * returns `true`.
	 * 
	 * @param r 4D vector to be compared with the current 4D vector.
	 * 
	 * 	- `x`: The real part of the quaternion.
	 * 	- `y`: The imaginary part of the quaternion.
	 * 	- `z`: The third component of the quaternion (i.e., the scalar part).
	 * 	- `w`: The fourth component of the quaternion (i.e., the vector part).
	 * 
	 * @returns a boolean value indicating whether the given quaternion is equal to the
	 * current quaternion.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
