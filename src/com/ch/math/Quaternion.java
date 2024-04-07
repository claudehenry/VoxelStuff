package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * is a mathematical representation of a rotation in three dimensions. It has several
 * methods for calculating rotations, including setting x, y, z, and w fields directly,
 * as well as methods for getting forward, back, up, down, right, and left directions.
 * It also has a method for converting Euler angles to a Quaternion representation.
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
  * calculates the Euclidean distance of a 3D point from origin using the formula (x^2
  * + y^2 + z^2)^(1/2).
  * 
  * @returns the square root of the sum of the squares of the component values of a vector.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

 /**
  * normalizes a given quaternion by dividing each component by its length, resulting
  * in a quaternion with a length of 1.
  * 
  * @returns a normalized quaternion representation of the original input quaternion.
  * 
  * 	- The first component of the Quaternion represents the magnitude of the quaternion,
  * which is computed as `w / length`.
  * 	- The second and third components represent the x and y coordinates of the
  * quaternion, respectively, and are computed as `x / length` and `y / length`, respectively.
  * 	- The fourth component represents the z coordinate of the quaternion and is
  * computed as `z / length`.
  */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

 /**
  * generates a new quaternion with the same magnitude as the original but with the
  * axis reversed.
  * 
  * @returns a new quaternion with the same w value as the input, and the opposite x,
  * y, and z values.
  * 
  * 	- The input argument `w` is unchanged in the output quaternion.
  * 	- The input arguments `-x`, `-y`, and `-z` are combined to create a new quaternion
  * with the same magnitude as the original but with its sign flipped.
  */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

 /**
  * multiplies a quaternion by a scalar value, returning a new quaternion with the product.
  * 
  * @param r 4-dimensional vector that is multiplied with the quaternion.
  * 
  * @returns a quaternion representation of the product of the input scalar value and
  * the quaternion's components.
  * 
  * 	- The output is a new instance of the `Quaternion` class.
  * 	- The `w`, `x`, `y`, and `z` fields of the input quaternion are multiplied by the
  * input `r`.
  * 	- The resultant quaternion represents a rotation by the angle `r` around the origin.
  */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

 /**
  * computes the product of two quaternions and returns the result as a new quaternion.
  * 
  * @param r quaternion to be multiplied with the current quaternion, and its value
  * is used to compute the output quaternion's components.
  * 
  * 	- `w`: The magnitude (or length) of the quaternion, represented as a float value.
  * 	- `x`, `y`, and `z`: The coefficients of the quaternion's rotation representation,
  * represented as floats.
  * 
  * @returns a new quaternion object representing the result of multiplying the input
  * quaternions.
  * 
  * 	- The output is a new `Quaternion` instance with the multiplied values of `w`,
  * `x`, `y`, and `z` components.
  * 	- The `w_` component represents the product of `w` and `r.getW()` components,
  * minus the sum of `x`, `y`, and `z` components of `r` multiplied by their respective
  * coefficients.
  * 	- The `x_`, `y_`, and `z_` components represent the products of `x`, `y`, and `z`
  * components of `this` quaternion and `r`, respectively, minus the sums of their
  * respective coefficients.
  * 
  * The output has the following attributes:
  * 
  * 	- It is a valid quaternion with non-negative `w` component and `x`, `y`, and `z`
  * components that sum to zero.
  * 	- Its `w` component represents the magnitude of the quaternion, which is equal
  * to the product of `w_` and the absolute value of `r.getW()`.
  * 	- Its `x`, `y`, and `z` components represent the directions of the quaternion,
  * which are related to the directions of the original quaternion and the multiplicand
  * quaternion.
  */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

 /**
  * multiplies a quaternion by a vector, resulting in another quaternion with the same
  * magnitude but rotated in the opposite direction.
  * 
  * @param r 3D vector to be multiplied with the quaternion.
  * 
  * 	- `getX()` and `getY()` are methods that return the x-coordinates and y-coordinates
  * of the vector, respectively.
  * 	- `getZ()` returns the z-coordinate of the vector.
  * 
  * The function then computes the product of the quaternion and the vector using the
  * multiplication of the scalar component of the quaternion with the corresponding
  * components of the vector. The resulting quaternion is then returned.
  * 
  * @returns a new `Quaternion` object containing the product of the input `Vector3f`
  * and the quaternion.
  * 
  * 	- The returned `Quaternion` instance has four components: `w`, `x`, `y`, and `z`.
  * These represent the real and imaginary parts of the quaternion multiplication.
  * 	- The values of `w`, `x`, `y`, and `z` are determined by the multiplication of
  * the input `Vector3f` instances `r` and the quaternion representation of the function.
  * Specifically, they are calculated as: `w = -x * r.getX() - y * r.getY() - z *
  * r.getZ()`, `x_ = w * r.getX() + y * r.getZ() - z * r.getY()`, `y_ = w * r.getY()
  * + x * r.getX() - z * r.getZ()`, and `z_ = w * r.getZ() + x * r.getY() - y * r.getX()`.
  * 	- The output quaternion represents the result of multiplying the input quaternion
  * with the provided vector, which can be used in various applications such as 3D
  * graphics, robotics, and computer vision.
  */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

 /**
  * takes a Quaternion `r` and returns a new Quaternion with the difference between
  * the input and the current instance.
  * 
  * @param r 4-dimensional vector to be subtracted from the current quaternion.
  * 
  * The `Quaternion r` is a serialized representation of a quaternion value with four
  * elements - `w`, `x`, `y`, and `z`. Each element represents a real number that
  * contributes to the overall quaternion value.
  * 
  * @returns a new quaternion with the difference of the input quaternion's values.
  * 
  * The Quaternion `r` is subtracted from the input Quaternion `this`, resulting in a
  * new Quaternion object with the values `w - r.getW()`, `x - r.getX()`, `y - r.getY()`,
  * and `z - r.getZ()`.
  */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * takes a `Quaternion` object `r` and returns a new `Quaternion` object representing
  * the sum of the two quaternions.
  * 
  * @param r 4D vector to be added to the current quaternion.
  * 
  * 	- `w`: The real part of the quaternion.
  * 	- `x`, `y`, and `z`: The imaginary parts of the quaternion, each representing a
  * complex number.
  * 
  * @returns a new Quaternion object representing the sum of the input quaternions.
  * 
  * 	- The `Quaternion` object returned has a value that is the sum of the values of
  * the input objects.
  * 	- The `W`, `X`, `Y`, and `Z` fields of the returned object represent the real and
  * imaginary parts of the quaternion, which are the sums of their corresponding fields
  * from the input objects.
  * 	- The resulting quaternion has the same rotation angle as the input quaternions.
  */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

 /**
  * computes a rotation matrix based on three unit vectors representing the axis of
  * rotation and the angle of rotation. The resulting matrix can be used for various
  * purposes, such as rotating objects in 3D space.
  * 
  * @returns a 4x4 rotation matrix representing the given rotation.
  * 
  * 	- The Matrix4f object represents a 4x4 homogeneous transformation matrix.
  * 	- The elements of the matrix are defined based on the input values of x, y, and
  * z, which represent the rotational angles about the x, y, and z axes, respectively.
  * 	- The resulting matrix has the following properties:
  * 	+ It represents a rotation around the origin, as expected from the input angles.
  * 	+ The determinant of the matrix is non-zero, indicating that the matrix is
  * invertible and can be used for transformations.
  * 	+ The elements of the matrix are scaled by factors of 2, which is a common
  * convention in 3D graphics and computer vision applications.
  */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

 /**
  * computes the dot product of a quaternion and another quaternion, returning a float
  * value representing the magnitude of the cross product between the two quaternions.
  * 
  * @param r 4-dimensional quaternion to be multiplied with the current quaternion,
  * resulting in the dot product of the two quaternions.
  * 
  * 	- `x`, `y`, `z`, and `w` represent the real components of the quaternion.
  * 	- The `getX()`, `getY()`, `getZ()`, and `getW()` methods return the individual
  * real values of the quaternion.
  * 
  * @returns a floating-point number representing the dot product of the input quaternion
  * and the provided quaternion.
  */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

 /**
  * computes a linear interpolation between two quaternions based on the given lerp
  * factor and whether the resulting quaternion should be shortest.
  * 
  * @param dest 4-dimensional vector that will be modified to perform a linear
  * interpolation of two other quaternions using the `NLerp()` method.
  * 
  * 	- `Quaternion dest`: This represents a quaternion value that is used to calculate
  * the lerped result. It has four attributes - `w`, `x`, `y`, and `z` - which correspond
  * to the real and imaginary parts of the quaternion.
  * 
  * @param lerpFactor 0 to 1 value that determines how quickly the Quaternion is
  * interpolated or smoothed between the source and destination values.
  * 
  * @param shortest shortest quaternion route between the given `dest` and the result,
  * by negating the quaternion if necessary to ensure the dot product of the resulting
  * quaternion with `dest` is as small as possible.
  * 
  * @returns a new quaternion that represents a linear interpolation between the input
  * `dest` and the original quaternion.
  * 
  * 	- The output is a `Quaternion` object that represents the interpolated rotation
  * between the two input quaternions.
  * 	- If `shortest` is true and the dot product of the input quaternions is less than
  * 0, the output quaternion is flipped to ensure that the result is in the positive
  * quadrant.
  * 	- The output is normalized to have a length of 1.
  */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

 /**
  * calculates a quaternion interpolation between two given quaternions, accounting
  * for shortest path and smoothness constraints. It returns a new quaternion that
  * represents the interpolation between the input quaternions.
  * 
  * @param dest 4D quaternion that the method will lerp to, with its magnitude and
  * direction being adjusted based on the `lerpFactor` parameter and the `shortest` parameter.
  * 
  * 	- `dot(dest)`: returns the dot product of the current quaternion and `dest`.
  * 	- `cos`: returns the cosine of the dot product of the current quaternion and `dest`.
  * 	- `shortest`: a boolean indicating whether to normalize `dest` if its dot product
  * with the current quaternion is negative.
  * 	- `correctedDest`: a new quaternion instance created by normalizing `dest` if
  * `shortest` is true and its dot product with the current quaternion is negative.
  * 	- `lerpFactor`: a float representing the interpolation factor between the current
  * quaternion and `dest`.
  * 
  * The properties of the input `dest` are as follows:
  * 
  * 	- `W`, `X`, `Y`, and `Z`: represent the real and imaginary parts of the quaternion,
  * respectively.
  * 
  * @param lerpFactor 0-1 blending factor for the linear interpolation between the
  * source and destination quaternions, which determines how much of the destination
  * quaternion is blended with the source quaternion to produce the final result.
  * 
  * @param shortest shortest path between the source and destination quaternions, which
  * is used to adjust the interpolation angle when cos(angle) < 1 - EPSILON.
  * 
  * @returns a new Quaternion instance that represents the linear interpolation of the
  * original Quaternion's rotation and scale components.
  * 
  * 	- The returned Quaternion is a linear combination of the original input Quaternion
  * and the destination Quaternion, with weights determined by the `lerpFactor` parameter
  * and the cosine of the angle between them.
  * 	- If `shortest` is true and the cosine of the angle between the two Quaternions
  * is negative, the returned Quaternion will be the opposite of the original input Quaternion.
  * 	- The `invSin` value is calculated as 1 / sin(angle), where angle is the angle
  * between the two Quaternions.
  * 	- The `srcFactor` and `destFactor` values are sinusoidal functions of the
  * `lerpFactor` parameter, with opposite signs for each factor.
  * 
  * Overall, the `SLerp` function returns a Quaternion that is a weighted combination
  * of the original input Quaternion and the destination Quaternion, where the weights
  * are determined by the angle between them and the `lerpFactor` parameter.
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
  * rotates a vector by the angle of the object and returns the resulting forward
  * direction as a new Vector3f instance.
  * 
  * @returns a vector pointing in the forward direction of the rotated object.
  * 
  * The output is a `Vector3f` object that represents the forward direction of the transform.
  * 
  * The x-component of the vector is 0, indicating that the forward direction is
  * parallel to the x-axis.
  * 
  * The y-component of the vector is 0, indicating that the forward direction is
  * parallel to the y-axis.
  * 
  * The z-component of the vector is 1, indicating that the forward direction is
  * directed along the z-axis.
  */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

 /**
  * rotates a `Vector3f` instance by 90 degrees around the z-axis, effectively moving
  * it backwards along the original axis.
  * 
  * @returns a rotated version of the original vector, with its x-axis pointing towards
  * the negative z-axis.
  * 
  * 	- The output is a `Vector3f` object representing a 3D vector with three elements
  * (x, y, and z components).
  * 	- The vector has its x component set to zero, its y component set to zero, and
  * its z component set to -1.
  * 	- The vector has been rotated by the original vector using the `rotate()` method.
  */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees around the x-axis, resulting in a vector pointing
  * upwards.
  * 
  * @returns a rotated vector representation of the object's up direction.
  * 
  * 	- The vector is of type Vector3f and represents the upward direction in 3D space.
  * 	- It is created by rotating the original vector (0, 1, 0) by a unknown amount
  * using the `rotate` method.
  * 	- As a result of this rotation, the x-axis component of the vector has been shifted
  * to represent the upward direction relative to the original vector.
  */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

 /**
  * rotates a `Vector3f` instance by 90 degrees around the x-axis, effectively "moving"
  * it downwards.
  * 
  * @returns a rotated vector in the downward direction.
  * 
  * The output is a new instance of `Vector3f`, which represents a 3D vector in the
  * format (x, y, z). The vector has its x-component set to zero, its y-component set
  * to -1, and its z-component set to zero. The rotation operation performed on the
  * input vector results in a new vector that is rotated by 90 degrees around the
  * x-axis, as measured from the origin (0, 0, 0).
  */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees clockwise to create a new vector pointing rightward.
  * 
  * @returns a rotated version of the original vector, with its x-component increased
  * by 1 and its y- and z-components unchanged.
  * 
  * The return type is `Vector3f`, which represents a 3D vector with floating-point values.
  * 
  * The method `rotate(this)` is applied to the input vector, resulting in a rotated
  * version of the vector. The rotation is not specified, but it is likely that the
  * vector has been rotated around the x, y, or z axis based on the context of the code.
  * 
  * The return value is a new `Vector3f` object that represents the rotated version
  * of the input vector.
  */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees around the x-axis and returns the resulting left vector.
  * 
  * @returns a rotated version of the original vector, with its x-component changed
  * to -1.
  * 
  * 	- The output is a `Vector3f` object that represents the leftward direction from
  * the original vector.
  * 	- The rotation is performed using the `rotate()` method, which takes the current
  * vector as input and returns a new vector rotated by the specified angle around the
  * specified axis.
  * 	- In this case, the angle of rotation is -1 radian, which means that the leftward
  * direction is obtained by rotating the original vector counterclockwise around the
  * x-axis.
  * 	- The resulting vector has the same magnitude as the original vector, but its
  * direction is opposite to the original direction.
  */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

 /**
  * sets the quaternion's `x`, `y`, `z`, and `w` components to the input values. It
  * returns a reference to the same quaternion object for chaining.
  * 
  * @param x 3D coordinate of the quaternion's rotation axis.
  * 
  * @param y 2D component of the quaternion, which is multiplied by the input value
  * to modify the quaternion's rotation.
  * 
  * @param z 3rd component of the quaternion and is used to set its value.
  * 
  * @param w 4th component of the quaternion and is used to update its value.
  * 
  * @returns a new instance of the `Quaternion` class with updated values for `x`,
  * `y`, `z`, and `w`.
  * 
  * 	- `this`: The `set` function modifies the attributes of the `Quaternion` instance
  * it is called on. Therefore, `this` refers to the modified instance.
  * 	- `x`, `y`, `z`, and `w`: These are the input values that are assigned to the
  * corresponding attributes of the `Quaternion` instance.
  * 
  * Therefore, the output of the `set` function is simply the modified `Quaternion`
  * instance with its attributes updated according to the input values provided.
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
 /**
  * takes a vector of Euler angles as input and returns a quaternion representation
  * of that angle rotation.
  * 
  * @param eulerAngles 3D Euler angles of a rotation, which are used to calculate the
  * quaternion representation of the rotation.
  * 
  * 	- `phi`: The zenith angle of the quaternion, represented as a float value between
  * 0 and 2π.
  * 	- `theta`: The right-hand rule angle of the quaternion, represented as a float
  * value between 0 and 2π.
  * 	- `yota`: The forward rotation angle of the quaternion, represented as a float
  * value between -π and π.
  * 
  * The function then computes several intermediate values using the input angles:
  * 
  * 	- `cos_half_phi`: The cosine of half of the zenith angle.
  * 	- `sin_half_phi`: The sine of half of the zenith angle.
  * 	- `cos_half_theta`: The cosine of half of the right-hand rule angle.
  * 	- `sin_half_theta`: The sine of half of the right-hand rule angle.
  * 	- `cos_half_yota`: The cosine of half of the forward rotation angle.
  * 	- `sin_half_yota`: The sine of half of the forward rotation angle.
  * 
  * The function then computes the quaternion values using the intermediate values:
  * 
  * 	- `q0`: The first component of the quaternion.
  * 	- `q1`: The second component of the quaternion.
  * 	- `q2`: The third component of the quaternion.
  * 	- `q3`: The fourth component of the quaternion.
  * 
  * Finally, the function returns a new `Quaternion` object with the computed values.
  * 
  * @returns a `Quaternion` object representing the rotation described by the provided
  * Euler angles.
  * 
  * 	- The `Quaternion` object represents a quaternion in 4D space, with four components:
  * `q0`, `q1`, `q2`, and `q3`. These components are calculated using the input
  * `eulerAngles` vector as follows: `q0 = cos_half_phi * cos_half_theta * cos_half_yota
  * + sin_half_phi * sin_half_theta * sin_half_yota; q1 = sin_half_phi * cos_half_theta
  * * cos_half_yota - cos_half_phi * sin_half_theta * sin_half_yota; q2 = cos_half_phi
  * * sin_half_theta * cos_half_yota + sin_half_phi * cos_half_theta * sin_half_yota;
  * q3 = cos_half_phi * cos_half_theta * sin_half_yota - sin_half_phi * sin_half_theta
  * * cos_half_yota`.
  * 	- The `Quaternion` object is a 4D vector that can be used for various applications
  * such as rotation, interpolation, and composition.
  * 	- The quaternion representation of rotation has some desirable properties, such
  * as being more efficient for certain types of transformations (e.g., rotations
  * around multiple axes at once) and being easier to handle in certain mathematical
  * contexts (e.g., eigenvalue decomposition).
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
  * sets the values of a quaternion to those of another quaternion.
  * 
  * @param r 4-element quaternion value to be combined with the current instance of
  * Quaternion, which is then returned as a new quaternion value.
  * 
  * 	- `getX()`: Returns the x-coordinate of the quaternion.
  * 	- `getY()`: Returns the y-coordinate of the quaternion.
  * 	- `getZ()`: Returns the z-coordinate of the quaternion.
  * 	- `getW()`: Returns the w-coordinate of the quaternion, which is a scalar
  * representing the magnitude of the quaternion.
  * 
  * @returns a reference to the same `Quaternion` object, unchanged.
  * 
  * 	- The `set` function takes in four parameters representing the x, y, z, and w
  * components of a quaternion.
  * 	- These parameters are used to update the corresponding components of the input
  * quaternion.
  * 	- The return value is the same quaternion instance as the input, indicating that
  * the method is commutative.
  */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

 /**
  * returns the value of the `x` field.
  * 
  * @returns a floating-point value representing the variable `x`.
  */
	public float getX() {
		return x;
	}

 /**
  * sets the value of the `x` field of the object to which it belongs.
  * 
  * @param x float value to be assigned to the `x` field of the class instance being
  * manipulated by the `setX()` method.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * returns the value of the `y` field.
  * 
  * @returns a floating-point value representing the y coordinate of an object.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the `y` field of an object to the input `float` parameter.
  * 
  * @param y new value of the object's `y` field, which is assigned to the object's
  * `y` field through the function call.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * returns the value of the `z` field.
  * 
  * @returns a floating-point value representing the z component of an object's position.
  */
	public float getZ() {
		return z;
	}

 /**
  * sets the value of the `z` field of the current object to the inputted value.
  * 
  * @param z 3D position of an object in the scene and assigns it to the `z` field of
  * the class instance, effectively storing its coordinates for later use.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * retrieves the value of a variable named `w`.
  * 
  * @returns the value of the `w` field, which is a `float` variable.
  */
	public float getW() {
		return w;
	}

 /**
  * sets the value of the `w` field of the current object to the argument passed as a
  * float.
  * 
  * @param w 3D position of an object's center of mass in the World coordinate system,
  * and its assignment updates the object's center of mass position.
  */
	public void setW(float w) {
		this.w = w;
	}

 /**
  * compares a `Quaternion` object with another provided quaternion, returning `true`
  * if all components are equal, and `false` otherwise.
  * 
  * @param r Quaternion to be compared with the current Quaternion, allowing the
  * function to determine equality based on the corresponding values of each component.
  * 
  * 	- `x`: The first component of the Quaternion represents the real part of the vector.
  * 	- `y`: The second component of the Quaternion represents the imaginary part of
  * the vector.
  * 	- `z`: The third component of the Quaternion represents the scalar part of the vector.
  * 	- `w`: The fourth component of the Quaternion represents the vector part of the
  * Quaternion.
  * 
  * @returns a boolean value indicating whether the specified Quaternion object has
  * the same values as the current Quaternion object.
  */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
