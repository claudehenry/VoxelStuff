package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * in Java provides a flexible and efficient way to represent 3D rotations. It offers
 * various methods for converting between rotation representations (euler angles,
 * angle axis, and unit quaternion) and performs the necessary calculations to
 * interpolate or extrapolate rotations smoothly. Additionally, it provides utility
 * methods for computing the forward, backward, up, down, right, and left directions
 * in 3D space.
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
	 * calculates the Euclidean distance of a point in 3D space from its origin, based
	 * on the coordinates of the point and the magnitude of its components.
	 * 
	 * @returns the square root of the sum of the squares of the input coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes a given quaternion by dividing each component by its magnitude, resulting
	 * in a unit quaternion.
	 * 
	 * @returns a normalized quaternion representation of the original quaternion.
	 * 
	 * 	- The function returns a new `Quaternion` object with its `w`, `x`, `y`, and `z`
	 * components normalized to have the same length.
	 * 	- The `length()` method is called on the current quaternion instance, which
	 * computes the length of the quaternion as a whole.
	 * 	- The `Quaternion` constructor takes four parameters: `w`, `x`, `y`, and `z`,
	 * which are used to create a new quaternion instance with normalized components.
	 * 
	 * Overall, the `normalized()` function returns a normalized quaternion object that
	 * maintains the original orientation but has equal length for all components.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * calculates a quaternion's conjugate by returning a new quaternion with the same
	 * coefficients but with all components reversed in magnitude and sign.
	 * 
	 * @returns a new Quaternion object with the conjugate of the input quaternion's coordinates.
	 * 
	 * 	- The output is a new Quaternion object, created with the values of the original
	 * quaternion's coefficients (-w, -x, -y, and -z).
	 * 	- The sign of the quaternion's coefficients is flipped, resulting in a conjugate
	 * quaternion that has the same direction as the original but with opposite magnitude.
	 * 	- The conjugation does not affect the quaternion's norm or angle.
	 * 	- The conjugate quaternion is also a unit quaternion, meaning its length is equal
	 * to 1.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies a quaternion by a scalar value, returning a new quaternion with the product.
	 * 
	 * @param r scalar value to be multiplied with the quaternion's components, resulting
	 * in a new quaternion object.
	 * 
	 * @returns a new Quaternion object with the product of the input `r` multiplied by
	 * the quaternion components.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * multiplies two quaternions and returns the result as a new quaternion.
	 * 
	 * @param r quaternion to be multiplied with the current quaternion, affecting the
	 * resulting quaternion's values of w, x, y, and z.
	 * 
	 * 	- `w`: The real part of `r`, which represents the scalar component of the quaternion.
	 * 	- `x`, `y`, and `z`: The imaginary parts of `r`, which represent the vector
	 * components of the quaternion.
	 * 
	 * @returns a new quaternion object with the product of the two input quaternions.
	 * 
	 * 	- The output is a new `Quaternion` instance representing the product of the input
	 * `Quaternion` instances.
	 * 	- The `w_`, `x_,`, `y_`, and `z_` fields of the output represent the dot product
	 * of the input quaternions.
	 * 	- The order of the input quaternions does not affect the result of the multiplication.
	 * 	- The result is a unit quaternion, meaning that its length (or magnitude) is equal
	 * to 1.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * multiplies a quaternion by a vector, computing the result as the dot product of
	 * the quaternion's components and the vector's components, and returning the resulting
	 * quaternion.
	 * 
	 * @param r 3D vector that is multiplied with the quaternion.
	 * 
	 * 	- `r` is a `Vector3f` object representing a 3D vector with three elements: `x`,
	 * `y`, and `z`.
	 * 
	 * @returns a quaternion representation of the multiplication of the input vector and
	 * a scalar value.
	 * 
	 * 	- The output is of type `Quaternion`, which represents a 4D vector in homogeneous
	 * coordinates.
	 * 	- The first component (w_) of the output represents the scalar multiplication of
	 * the input quaternion's scalar part with the input vector's x-component.
	 * 	- The second component (x_) represents the scalar multiplication of the input
	 * quaternion's scalar part with the input vector's y-component.
	 * 	- The third component (y_) represents the scalar multiplication of the input
	 * quaternion's scalar part with the input vector's z-component.
	 * 	- The fourth component (z_) represents the scalar multiplication of the input
	 * quaternion's scalar part with the input vector's w-component.
	 * 
	 * The output quaternion is a representation of the rotation described by the dot
	 * product of the input quaternion and the input vector, scaled by the scalar multiplication.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * takes a quaternion `r` as input and returns a new quaternion with the difference
	 * between the two quaternions' components.
	 * 
	 * @param r 4D vector to be subtracted from the current quaternion.
	 * 
	 * 	- `w`: The real part of the quaternion.
	 * 	- `x`, `y`, and `z`: The imaginary parts of the quaternion.
	 * 
	 * @returns a quaternion representing the difference between the input quaternions.
	 * 
	 * 	- The `w` component of the output Quaternion is calculated as `w - r.getW()`.
	 * This represents the difference between the input and output Quaternions' magnitude
	 * (or length).
	 * 	- Similarly, the `x`, `y`, and `z` components of the output Quaternion are
	 * calculated as `x - r.getX()`, `y - r.getY()`, and `z - r.getZ()` respectively,
	 * representing the difference between the input and output Quaternions' orientation
	 * in 3D space.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a Quaternion `r` as input and returns a new Quaternion object representing
	 * the addition of the two quaternions.
	 * 
	 * @param r 4D vector to be added to the current quaternion.
	 * 
	 * 	- `w`: The magnitude (or length) of the quaternion.
	 * 	- `x`, `y`, and `z`: The axis of rotation represented by the quaternion.
	 * 
	 * @returns a new Quaternion object representing the sum of the input Quaternions.
	 * 
	 * 	- The output is a Quaternion object representing the sum of the input arguments.
	 * 	- The `W`, `X`, `Y`, and `Z` components of the output are computed as the sum of
	 * the corresponding components of the input arguments.
	 * 	- The resulting Quaternion object has the same rotation representation as the
	 * input arguments.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * takes a quaternion representation of a rotation and returns a corresponding 4x4
	 * rotation matrix.
	 * 
	 * @returns a rotation matrix in column-major order.
	 * 
	 * 1/ The returned Matrix4f object represents a rotation matrix in 3D space.
	 * 2/ It has four columns, each representing one axis of rotation (x, y, z, and w).
	 * 3/ The elements of the matrix are scaled values between -1 and 1, indicating the
	 * amount of rotation about that axis.
	 * 4/ The matrix is created using the dot product of three vectors: forward, up, and
	 * right. These vectors represent the x, y, and z axes of the rotation, respectively.
	 * 5/ The resulting matrix can be used to perform a rotation in 3D space by multiplying
	 * it with a vector, which will result in a rotated version of that vector.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * computes the dot product of two quaternions, returning a float value representing
	 * the amount of linear and angular correlation between them.
	 * 
	 * @param r 4-dimensional quaternion to be multiplied with the current quaternion.
	 * 
	 * The `Quaternion` class has four attributes: `x`, `y`, `z`, and `w`. These represent
	 * the real and imaginary parts of a complex number, respectively. The `dot` function
	 * computes the dot product of the two quaternions by multiplying their respective
	 * `x`, `y`, `z`, and `w` attributes.
	 * 
	 * @returns a float value representing the dot product of the input quaternion and
	 * the given quaternion.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a linear interpolation of a destination quaternion based on a factor,
	 * respecting the orientation of the source quaternion if shortest is true.
	 * 
	 * @param dest 4-dimensional quaternion vector that is transformed or interpolated
	 * by the `NLerp()` method.
	 * 
	 * 	- `Quaternion dest`: This variable represents a quaternion object that is the
	 * target of the interpolation.
	 * 	- `lerpFactor`: A floating-point value representing the interpolation factor
	 * between the two inputs.
	 * 	- `shortest`: An boolean value indicating whether the shortest path should be
	 * taken (true) or not (false).
	 * 
	 * The function first checks if the shortest path should be taken, and if so, it
	 * corrects the input `dest` by negating its values. Then, the function applies the
	 * interpolation using the `sub`, `mul`, and `add` methods to produce the final output
	 * quaternion. Finally, the function normalizes the result to ensure that it has a
	 * length of 1.
	 * 
	 * @param lerpFactor 0-1 value for which the destination quaternion is linearly
	 * interpolated between the source quaternion and another intermediate quaternion,
	 * as specified by the `shortest` parameter.
	 * 
	 * @param shortest 4-vector of a shortest quaternion rotation to be applied to the
	 * destination vector, when the dot product between the destination and the quaternion
	 * is negative.
	 * 
	 * @returns a new quaternion that represents the interpolation between the input
	 * `dest` and the reference quaternion.
	 * 
	 * 	- The output is a Quaternion object that represents the interpolated rotation
	 * between the two input values.
	 * 	- If the `shortest` parameter is set to `true`, and the dot product of the current
	 * rotation with the destination rotation is negative, the output will be flipped
	 * signwise to ensure the shortest path to the destination rotation.
	 * 	- The output is normalized to have a length of 1.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * computes a smooth interpolation between two Quaternion values based on the given
	 * lerp factor and shortest path flag. It first computes the cosine and sine of the
	 * angle between the two inputs, then interpolates between them using the sinusoidal
	 * function. The resulting quaternion is returned.
	 * 
	 * @param dest 4D quaternion value that the function will interpolate between, based
	 * on the input `lerpFactor`.
	 * 
	 * 	- `dot(dest)` computes the dot product of the current quaternion and `dest`, which
	 * is used to determine if the quaternions are "close enough" for the shortest path
	 * algorithm to work.
	 * 	- `correctedDest` is created by scaling `dest` by a negative factor if the cosine
	 * of the angle between the two quaternions is negative, to ensure that the resulting
	 * quaternion is in the correct direction.
	 * 	- `lerpFactor` is a parameter controlling how much the quaternion is interpolated
	 * between the "start" and "end" quaternions.
	 * 	- `shortest` is a boolean parameter indicating whether the shortest path algorithm
	 * should be used if possible.
	 * 	- `sin`, `cos`, and `invSin` are computed using the standard math functions to
	 * perform the necessary trigonometric calculations for the interpolation.
	 * 
	 * @param lerpFactor 0-1 value for how close to the destination quaternion the source
	 * quaternion should be interpolated, with higher values resulting in more linear
	 * interpolation and lower values resulting in more circular interpolation.
	 * 
	 * @param shortest shortest quaternion path to reach the final destination, which is
	 * used when `cos` is negative to correct the sign of the quaternion result.
	 * 
	 * @returns a new Quaternion object representing the interpolation of the original
	 * Quaternion and the target Quaternion.
	 * 
	 * 	- The return value is a `Quaternion` instance representing the interpolated
	 * rotation between the two input quaternions.
	 * 	- The `correctedDest` variable is set to the input `dest` quaternion if the
	 * shortest path is not taken, otherwise it is set to the negative of the `dest` quaternion.
	 * 	- The `sin`, `cos`, and `angle` variables are used to calculate the interpolation
	 * factors for the source and destination quaternions.
	 * 	- The `srcFactor` and `destFactor` variables represent the interpolation factors
	 * for the source and destination quaternions, respectively. These factors are
	 * calculated using the sine and cosine of the angle between the two quaternions, as
	 * well as the lerp factor.
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
	 * rotates a vector by 90 degrees around the x-axis to produce a forward vector.
	 * 
	 * @returns a vector pointing in the direction of the rotated forward vector.
	 * 
	 * The `Vector3f` object returned has a magnitude of 1 and an x-axis, y-axis, and
	 * z-axis component of 0, 0, and 1, respectively. This means that the direction of
	 * the vector is along the z-axis, while its magnitude is 1, indicating that it points
	 * in the same direction as the original object's forward direction.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the z-axis, resulting in a
	 * vector that points in the opposite direction of the original vector.
	 * 
	 * @returns a rotated vector with magnitude of -1.
	 * 
	 * The Vector3f object returned represents the backward direction of the rotated 3D
	 * vector. The x, y, and z components represent the directions of the backward
	 * displacement in the respective dimensions. The rotation is performed by multiplying
	 * the input vector with a rotation matrix obtained by rotating the origin of the
	 * vector space around the x-axis.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the x-axis to produce a new vector pointing
	 * upward.
	 * 
	 * @returns a rotated vector with a magnitude of 1 and a direction perpendicular to
	 * the object's orientation.
	 * 
	 * The `Vector3f` object that is returned represents an upward direction vector
	 * relative to the current position of the entity. The vector has three components:
	 * x, y, and z, each representing a directional component in 3D space. The vector's
	 * magnitude (length) is always equal to 1, regardless of the position of the entity.
	 * The direction of the vector can be changed by multiplying it with a rotation matrix,
	 * as done in the function call `rotate(this)`.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the x-axis, effectively moving it downwards
	 * from its original position.
	 * 
	 * @returns a rotated vector representing the downward direction from the current position.
	 * 
	 * The `Vector3f` object returned is a rotated version of the original vector, where
	 * the x-component remains unchanged while the y-component is negated and the z-component
	 * is unaffected. This means that the resulting vector points in a downward direction
	 * relative to the original vector's position.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the x-axis, resulting in a new vector that
	 * points in the right direction.
	 * 
	 * @returns a rotated vector representing the right component of the current object.
	 * 
	 * The output is a `Vector3f` object, representing the right-hand side of the current
	 * transform.
	 * 
	 * The x-component of the vector represents the offset in the x-direction from the
	 * origin, while the y-component represents the offset in the y-direction, and the
	 * z-component represents the offset in the z-direction.
	 * 
	 * The rotation applied to the input vector is represented by the `rotate` method,
	 * which takes the current transform as its argument and returns a new vector
	 * representing the rotated version of the input vector.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees counterclockwise around the x-axis, resulting in a
	 * new vector that is left to the original one.
	 * 
	 * @returns a rotated vector with a magnitude of -1 and an x-component of 0, y-component
	 * of 0, and z-component of 0.
	 * 
	 * The output is a `Vector3f` object representing the left component of the current
	 * transform.
	 * 
	 * The vector's coordinates are (-1, 0, 0), indicating that it points in the negative
	 * x-direction and has a length of 1.
	 * 
	 * When rotated by the current transform using the `rotate` method, the output is
	 * transformed accordingly.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * sets the components of a `Quaternion` object to the input values.
	 * 
	 * @param x 3D position of the quaternion in the x-axis direction.
	 * 
	 * @param y 2D component of the quaternion.
	 * 
	 * @param z 3D coordinate of the quaternion in the z-axis direction.
	 * 
	 * @param w 4th component of the quaternion, which is used to rotate the object along
	 * the `z` axis.
	 * 
	 * @returns a reference to the same `Quaternion` object, allowing chaining of method
	 * calls.
	 * 
	 * The `Quaternion` object is mutated to have its `x`, `y`, `z`, and `w` fields updated
	 * to the specified values.
	 * 
	 * The returned output is a reference to the same `Quaternion` object that was passed
	 * as an argument, indicating that the function modifies the input rather than creating
	 * a new copy.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * takes a `Vector3f` object representing Euler angles and returns a `Quaternion`
	 * object representing a 3D rotational transformation based on those angles.
	 * 
	 * @param eulerAngles 3D Euler angles, which are phi, theta, and yota, that correspond
	 * to the rotational motion of an object in 3D space.
	 * 
	 * 	- `phi`: The polar angle of rotation around the x-axis (axis of rotation).
	 * 	- `theta`: The polar angle of rotation around the y-axis (perpendicular to the
	 * rotation axis).
	 * 	- `yota`: The polar angle of rotation around the z-axis (parallel to the rotation
	 * axis).
	 * 
	 * The function locally stores intermediate cosine and sine values of these angles
	 * to avoid recalculating them multiple times. These values are used in the calculation
	 * of the quaternion components.
	 * 
	 * @returns a quaternion representation of the input Euler angles.
	 * 
	 * 	- `q0`, `q1`, `q2`, and `q3` represent the quaternion components in the order
	 * `x`, `y`, `z`, and `w`, respectively.
	 * 	- These components are computed using the input `eulerAngles` as follows:
	 * 	+ `q0 = cos_half_phi * cos_half_theta * cos_half_yota + sin_half_phi * sin_half_theta
	 * * sin_half_yota`
	 * 	+ `q1 = sin_half_phi * cos_half_theta * cos_half_yota - cos_half_phi * sin_half_theta
	 * * sin_half_yota`
	 * 	+ `q2 = cos_half_phi * sin_half_theta * cos_half_yota + sin_half_phi * cos_half_theta
	 * * sin_half_yota`
	 * 	+ `q3 = cos_half_phi * cos_half_theta * sin_half_yota - sin_half_phi * sin_half_theta
	 * * cos_half_yota`
	 * 	- The resulting quaternion is a unitary (length equal to 1) object, which means
	 * that the `w` component is equal to the square root of the sum of the squares of
	 * the other components.
	 * 	- The quaternion represents a rotation in 3D space, and its properties can be
	 * used to manipulate objects in 3D space.
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
	 * sets the quaternion's values to those of a given quaternion.
	 * 
	 * @param r 4-dimensional vector that contains the new rotation values to be applied
	 * to the current quaternion representation, which are then passed as arguments to
	 * the `set()` method.
	 * 
	 * 	- `getX()`, `getY()`, and `getZ()`: These methods are used to access the x, y,
	 * and z components of the quaternion.
	 * 	- `getW()`: This method returns the w component of the quaternion, which is a
	 * scalar value that represents the magnitude of the quaternion.
	 * 
	 * @returns a reference to the same Quaternion object, unchanged.
	 * 
	 * 	- The output is an instance of the same class as the input parameter `r`.
	 * 	- The output has the same values for the `x`, `y`, `z`, and `w` fields as the
	 * input parameter `r`.
	 * 	- The output object refers to the same memory location as the input parameter `r`.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * retrieves the value of the `x` field, which is a floating-point number.
	 * 
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the object it is called on to the provided float
	 * argument.
	 * 
	 * @param x float value that is assigned to the `x` field of the class instance being
	 * modified by the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the `y` field.
	 * 
	 * @returns a floating-point representation of the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the object's `y` field to the argument passed as a float.
	 * 
	 * @param y new value to be assigned to the `y` field of the class instance.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field, which is a `float` variable storing an
	 * unspecified value.
	 * 
	 * @returns the value of the `z` field.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the `z` field of its object reference to the specified floating-point
	 * value.
	 * 
	 * @param z 3D position of an object in space, which is stored in the `z` field of
	 * the class instance upon calling the `setZ()` method.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * returns the value of the variable `w`.
	 * 
	 * @returns a floating-point value representing the width of a rectangle.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the value of the field `w` to the provided float argument.
	 * 
	 * @param w 2D width of the game window, which is then assigned to the `w` field of
	 * the class instance.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares a `Quaternion` object with another `Quaternion` object, checking for
	 * equality based on the values of its components (`x`, `y`, `z`, and `w`).
	 * 
	 * @param r 4D quaternion to be compared with the current quaternion.
	 * 
	 * 	- `x`: The real component of `r`.
	 * 	- `y`: The imaginary component of `r`.
	 * 	- `z`: The third component of `r`, which is usually a scalar value.
	 * 	- `w`: The fourth component of `r`, which is usually a scalar value.
	 * 
	 * @returns a boolean value indicating whether the current Quaternion object is equal
	 * to the provided Quaternion object.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
