package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * is a Java class that represents quaternions as 4D vectors in the form (w, x, y,
 * z). It provides various methods for calculating and manipulating quaternions,
 * including multiplication, conjugation, normalization, and rotation matrices. The
 * class also includes methods for calculating dot products, inverse of quaternions,
 * and fast animation calculations.
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
	 * calculates the Euclidean distance of a 3D point (x, y, z) from the origin using
	 * the Pythagorean theorem.
	 * 
	 * @returns the square root of the sum of the squares of the components of a vector.
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
	 * 	- The output is a Quaternion object, represented by four values (w, x, y, and z)
	 * that represent the normalized form of the original Quaternion.
	 * 	- The values of w, x, y, and z are calculated based on the length of the original
	 * Quaternion, with each value being set to a ratio of the length divided by the
	 * original value.
	 * 	- The resulting Quaternion has the same orientation as the original Quaternion,
	 * but its magnitude is limited to 1, which means that it is now a unit quaternion.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * generates a quaternion with the same magnitude as the original quaternion but with
	 * its sign inverted.
	 * 
	 * @returns a new quaternion with the same scalar component as the original quaternion,
	 * but with all vector components negated.
	 * 
	 * 	- The output is a `Quaternion` object representing the conjugate of the original
	 * input `Quaternion`.
	 * 	- The `w` component of the output is equal to `-w` of the input.
	 * 	- The `x`, `y`, and `z` components of the output are equal to `-x`, `-y`, and
	 * `-z` of the input, respectively.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies a quaternion by a scalar value and returns a new quaternion with the product.
	 * 
	 * @param r scalar value to be multiplied with the quaternion components.
	 * 
	 * @returns a quaternion representing the multiplication of the quaternion's `w`,
	 * `x`, `y`, and `z` components with the input scalar value `r`.
	 * 
	 * The Quaternion object returned has a w component, x component, y component, and z
	 * component, all multiplied by the input value r.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * multiplies two quaternions and returns their product as a new quaternion.
	 * 
	 * @param r 4D quaternion to be multiplied with the current quaternion, resulting in
	 * a new quaternion output.
	 * 
	 * 	- `w`: The real part of the quaternion, which is a scalar value.
	 * 	- `x`, `y`, and `z`: The imaginary parts of the quaternion, which are vectors in
	 * 3D space.
	 * 	- `getW()`, `getX()`, `getY()`, and `getZ()`: Methods that retrieve the real,
	 * imaginary x, imaginary y, and imaginary z components of the input quaternion.
	 * 
	 * @returns a quaternion representation of the product of two quaternions.
	 * 
	 * 	- `w_`: The product of the `w` components of the two input quaternions.
	 * 	- `x_`, `y_`, and `z_`: The products of the `x`, `y`, and `z` components of the
	 * two input quaternions, respectively.
	 * 
	 * The returned output is a new quaternion instance with the product of the `w`
	 * components of the input quaternions and the products of the `x`, `y`, and `z`
	 * components of the input quaternions, respectively.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * multiplies a quaternion by a vector and returns the result as another quaternion.
	 * 
	 * @param r 3D vector to be multiplied with the quaternion.
	 * 
	 * The `Vector3f` class represents a 3D vector in homogeneous coordinates, where `x`,
	 * `y`, and `z` are the components of the vector. The `getX()`, `getY()`, and `getZ()`
	 * methods return the individual components of the vector.
	 * 
	 * @returns a new `Quaternion` instance representing the result of multiplying the
	 * input `Vector3f` by a quaternion.
	 * 
	 * 	- The output is a `Quaternion` object, which represents a 4D vector with real
	 * components representing the quaternion's parameters.
	 * 	- The first component of the output, `w_`, represents the scalar part of the quaternion.
	 * 	- The second and third components, `x_` and `y_`, respectively, represent the
	 * vector part of the quaternion. They are obtained by multiplying the input vector
	 * `r` with the scalars `w` and `z`, respectively, followed by adding the scalar `y`.
	 * 	- The fourth component, `z_`, represents the scalar part of the quaternion that
	 * is opposite to the scalar part of the input vector `r`.
	 * 
	 * In summary, the `mul` function takes a vector as input and returns a quaternion
	 * as output, where the quaternion's parameters represent the scaled and rotated
	 * version of the input vector.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * representing the difference between the original and `r`.
	 * 
	 * @param r 4-dimensional vector to be subtracted from the current quaternion, resulting
	 * in a new quaternion representing the difference between the two vectors.
	 * 
	 * The `Quaternion` class represents a 4D vector in float format, with four components:
	 * `w`, `x`, `y`, and `z`. The `w` component corresponds to the scalar part of the
	 * quaternion, while the `x`, `y`, and `z` components represent the vector part.
	 * 
	 * @returns a new quaternion with the difference between the input quaternion's values
	 * and the given quaternion's values.
	 * 
	 * The returned Quaternion object represents the difference between the current
	 * Quaternion and the provided Quaternion `r`. The resulting quaternion has the same
	 * orientation as the current one but with a scaled magnitude.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * representing the sum of the two quaternions.
	 * 
	 * @param r 4D vector to be added to the current 4D vector represented by the `this`
	 * object.
	 * 
	 * 	- `w`: The real component of `r`.
	 * 
	 * @returns a new Quaternion object representing the sum of the input Quaternions.
	 * 
	 * 	- The `Quaternion` object returned has four components: w, x, y, and z. These
	 * represent the real and imaginary parts of the sum of the two input quaternions.
	 * 	- The sum is computed component-wise, meaning that the real part of one quaternion
	 * is added to the real part of the other, the imaginary part of one quernion is added
	 * to the imaginary part of the other, and so on.
	 * 	- The resultant quaternion represents the composition of the two input quaternions,
	 * which can be used in various applications such as 3D graphics or robotics.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * generates a rotation matrix based on a given forward, up, and right vectors. It
	 * returns a Matrix4f object representing the rotation transformation.
	 * 
	 * @returns a 4x4 rotation matrix.
	 * 
	 * 	- The output is a `Matrix4f` object representing a rotation matrix in 3D space.
	 * 	- The matrix has four rows and four columns, corresponding to the three dimensions
	 * (x, y, z) and the one scalar (w) in the original vector.
	 * 	- Each element of the matrix is a real number between -1 and 1, representing the
	 * scale factor for each axis of rotation.
	 * 	- The matrix is initialized using the `initRotation` method, which takes three
	 * vectors as inputs: `forward`, `up`, and `right`. These vectors represent the x,
	 * y, and z components of the rotation vector, respectively.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * computes the dot product of two quaternions, returning a float value.
	 * 
	 * @param r 4D quaternion value that is multiplied element-wise with the `this`
	 * quaternion to produce the dot product result.
	 * 
	 * 	- `x`, `y`, `z`, and `w` are floating-point variables that represent the component
	 * values of the Quaternion structure.
	 * 
	 * @returns a scalar value representing the dot product of two quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a quaternion interpolation between two given quaternions using the linear
	 * interpolation method, taking into account the shortest path and the dot product
	 * of the quaternions.
	 * 
	 * @param dest 4D vector that will be interpolated between using the lerping method.
	 * 
	 * 1/ `Quaternion dest`: This is the destination quaternion to which the linear
	 * interpolation will be applied. It has four components: `w`, `x`, `y`, and `z`.
	 * 2/ `lerpFactor`: This is a scalar value that represents the factor by which the
	 * input quaternion will be interpolated towards the destination quaternion.
	 * 3/ `shortest`: This boolean flag indicates whether the resulting quaternion should
	 * be shortened to avoid negative values (if `true`) or not (if `false`). If `shortest`
	 * is `true`, and the dot product of the input quaternion with the destination
	 * quaternion is less than zero, then the corrected destination quaternion is negated.
	 * 
	 * @param lerpFactor 0-1 blending factor for the quaternion interpolation, determining
	 * how much of the starting quaternion is combined with the ending quaternion to
	 * produce the final result.
	 * 
	 * @param shortest shortest quaternion path between the given `dest` quaternion and
	 * the result, which is used to correct the sign of the quaternion if necessary to
	 * ensure the final result is in the positive orientation.
	 * 
	 * @returns a Quaternion that represents the linear interpolation of two given Quaternions.
	 * 
	 * 	- The output is a Quaternion object, representing the interpolated rotation between
	 * the input values.
	 * 	- The `correctedDest` variable is used to ensure that the interpolation is performed
	 * in the correct orientation, by adjusting the destination quaternion if necessary.
	 * 	- The `lerpFactor` parameter represents the factor by which the interpolation is
	 * performed.
	 * 	- If `shortest` is true, the interpolation is performed in the shortest path
	 * between the input values, resulting in a more rapid change in rotation.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * calculates a quaternion interpolation between two given quaternions based on the
	 * Lerp formula, with an option to use the shortest path.
	 * 
	 * @param dest 4D quaternion that the returned quaternion will be a linear combination
	 * of, with the coefficients determined by the `lerpFactor` and shortest route logic.
	 * 
	 * 	- `dest.getW()` represents the magnitude (or length) of the quaternion.
	 * 	- `dest.getX()`, `dest.getY()`, and `dest.getZ()` represent the x, y, and z
	 * components of the quaternion, respectively.
	 * 	- The `EPSILON` constant is set to 1e3f, which is a small positive value used as
	 * an arbitrary tolerance for determining when two numbers are considered equal or
	 * nearly equal.
	 * 
	 * The function then performs various calculations based on the input parameters:
	 * 
	 * 	- `cos = this.dot(dest)` computes the cosine of the angle between the quaternion
	 * and a hypothetical axis (represented by the `this` object).
	 * 	- `correctedDest = dest` is a copy of the original `dest` quaternion, which may
	 * be modified later in the function.
	 * 	- `shortest && cos < 0` checks whether the input quaternion and the hypothetical
	 * axis are nearly parallel, and if so, flips the sign of the quaternion. This is
	 * done to ensure that the resulting quaternion has a non-zero w component, which is
	 * necessary for correct interpolation.
	 * 	- `if (Math.abs(cos) >= 1 - EPSILON)` checks whether the cosine of the angle
	 * between the quaternion and the hypothetical axis is greater than or equal to 1
	 * minus a small tolerance. If it is, the function returns the result of calling the
	 * `NLerp` function with the `dest` quaternion as input.
	 * 	- `sin = (float) Math.sqrt(1.0f - cos * cos)` computes the sine of the angle
	 * between the quaternion and the hypothetical axis, using a square root operation
	 * to obtain a non-negative value.
	 * 	- `angle = (float) Math.atan2(sin, cos)` computes the angle between the quaternion
	 * and the hypothetical axis, using the `Math.atan2` function to obtain a value in
	 * the range of (-π, π].
	 * 	- `invSin = 1.0f / sin` computes the reciprocal of the sine of the angle, which
	 * is used in the interpolation formula.
	 * 	- `srcFactor = (float) Math.sin((1.0f - lerpFactor) * angle)` computes the factor
	 * by which to multiply the quaternion to obtain a intermediate result.
	 * 	- `destFactor = (float) Math.sin((lerpFactor) * angle)` computes the factor by
	 * which to multiply the `correctedDest` quaternion to obtain the final result.
	 * 
	 * Finally, the function returns the result of multiplying the `srcFactor` and
	 * `destFactor`, which is the interpolated quaternion.
	 * 
	 * @param lerpFactor 0-1 value that determines how quickly the quaternion is interpolated
	 * between the source and destination values, with higher values resulting in slower
	 * interpolation and lower values resulting in faster interpolation.
	 * 
	 * @param shortest 3D vector that results from applying the linear interpolation to
	 * the destination quaternion, and it is used to determine whether the quaternion
	 * should be flipped if the cosine of the dot product between the two quaternions is
	 * negative.
	 * 
	 * @returns a Quaternion object that represents a smooth interpolation between two
	 * specified quaternions, based on the given lerp factor and shortest route flag.
	 * 
	 * 	- The `Quaternion` object `dest` represents the destination quaternion in the interpolation.
	 * 	- The `float` variable `lerpFactor` determines the interpolation factor between
	 * the source and destination quaternions. If it is close to 1, the resulting quaternion
	 * will be very similar to the destination quaternion, while if it is close to 0, the
	 * resulting quaternion will be very similar to the source quaternion.
	 * 	- The `boolean` variable `shortest` determines whether to use a shorter path for
	 * the interpolation if the cosine of the angle between the source and destination
	 * quaternions is negative. If set to true, the shorter path will be taken, which may
	 * result in a different quaternion than if it were set to false.
	 * 	- The `float` variable `EPSILON` represents an epsilon value used for tolerance
	 * in the interpolation.
	 * 	- The `float` variables `cos`, `sin`, and `angle` are computed as part of the
	 * interpolation process. They represent the cosine of the angle between the source
	 * and destination quaternions, and the angle itself.
	 * 	- The `float` variable `invSin` is computed as part of the interpolation process
	 * and represents the inverse of the sine of the angle.
	 * 	- The `float` variables `srcFactor` and `destFactor` are computed as part of the
	 * interpolation process and represent the factors used to interpolate between the
	 * source and destination quaternions.
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
	 * rotates a vector by an angle based on the object's rotation and returns the resulting
	 * forward vector.
	 * 
	 * @returns a vector pointing in the forward direction of the rotated object.
	 * 
	 * The function returns a `Vector3f` object that represents the forward direction of
	 * the transform. The object contains the x, y, and z components of the forward
	 * direction, which are all positive values. Specifically, the x component is 0, the
	 * y component is 0, and the z component is 1.
	 * 
	 * The returned vector is created by rotating the original vector using the `rotate`
	 * method. This means that the resulting vector is in the same orientation as the
	 * transform, but its direction is now in the forward direction.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the z-axis to return a new vector pointing
	 * towards the negative z-axis.
	 * 
	 * @returns a rotated vector in the opposite direction of the object's orientation.
	 * 
	 * 	- The first element represents the x-coordinate of the vector, which is 0 in this
	 * case.
	 * 	- The second element represents the y-coordinate, which is also 0.
	 * 	- The third element represents the z-coordinate, which is -1.
	 * 
	 * The `rotate` method used to generate the output rotates the vector around its own
	 * center by a certain angle, in this case, 0 radians.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the x-axis, resulting in a new
	 * vector pointing upwards.
	 * 
	 * @returns a rotated vector with an x-component of 0, a y-component of 1, and a
	 * z-component of 0.
	 * 
	 * The output is a `Vector3f` object representing the up direction relative to the
	 * current position of the object. The vector has three components: x, y, and z, which
	 * represent the amount of movement in the respective directions.
	 * 
	 * The `rotate` method used to generate the output takes the current position of the
	 * object as input and returns a new `Vector3f` object representing the up direction
	 * relative to that position. This method performs a rotation around the x-axis by
	 * an angle equal to half the time step, which is why the z-component is zero.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the x-axis, resulting in a new
	 * vector that points downwards from the original position.
	 * 
	 * @returns a rotated vector representing the direction down from the current position.
	 * 
	 * The output is a `Vector3f` object representing the downward direction from the
	 * current position of the `Object3d` instance.
	 * The x-coordinate of the output vector is 0, indicating that the downward direction
	 * is along the x-axis.
	 * The y-coordinate of the output vector is -1, indicating that the downward direction
	 * is 1 unit below the current position along the y-axis.
	 * The z-coordinate of the output vector is 0, indicating that the downward direction
	 * is at the same height as the current position along the z-axis.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees clockwise, returning a new vector that points in
	 * the right direction.
	 * 
	 * @returns a rotated vector representing the rightward direction of the object.
	 * 
	 * The output is a `Vector3f` object that represents the right-hand side vector of
	 * the original vector.
	 * 
	 * The vector's magnitude is equal to 1 and its direction is aligned with the x-axis.
	 * 
	 * The rotation of the vector is performed using the `rotate` method, which takes the
	 * original vector as input and returns the rotated vector.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees counterclockwise around the x-axis, resulting in a
	 * new vector that is leftward from the original vector.
	 * 
	 * @returns a rotated vector with a magnitude of -1 and an x-component of 0, y-component
	 * of 0, and z-component of 0.
	 * 
	 * The output is a `Vector3f` object representing the left vector of the rotated matrix.
	 * The vector has a magnitude of -1 and its x, y, and z components are 0.
	 * The rotation is performed using the `rotate` method, which takes the matrix as
	 * input and returns a new vector with the rotated components.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * sets the `x`, `y`, `z`, and `w` components of a `Quaternion` object to the input
	 * values, returning the modified object instance.
	 * 
	 * @param x 3D position of the quaternion along the x-axis.
	 * 
	 * @param y 2D component of the quaternion.
	 * 
	 * @param z 3rd component of the quaternion and updates its value to match the provided
	 * value.
	 * 
	 * @param w 4th component of the quaternion and updates its value when set.
	 * 
	 * @returns a new `Quaternion` instance with updated values for `x`, `y`, `z`, and `w`.
	 * 
	 * 	- This function sets the `x`, `y`, `z`, and `w` fields of the provided `Quaternion`
	 * instance to the input values.
	 * 	- The returned `Quaternion` instance is the same as the original one, indicating
	 * that the method has no side effects.
	 * 	- The method does not modify the input parameters in any way.
	 * 	- The method signature does not include a `return` statement, which means that
	 * the function does not explicitly return anything. Instead, it " returns this" ,
	 * which means that the returned value is the same as the original instance.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * converts a set of Euler angles into a quaternion representation, which can be used
	 * for rotations in 3D space.
	 * 
	 * @param eulerAngles 3D orientation of an object in space, which is defined as a
	 * vector of three angles (phi, theta, and yota) that measure the rotational movements
	 * around the x, y, and z axes, respectively.
	 * 
	 * 	- `phi`: The angle of rotation around the x-axis (first component).
	 * 	- `theta`: The angle of rotation around the y-axis (second component).
	 * 	- `yota`: The angle of rotation around the z-axis (third component).
	 * 
	 * @returns a Quaternion object representing the rotation matrix based on the provided
	 * Euler angles.
	 * 
	 * 	- `q0`, `q1`, `q2`, and `q3`: These are the components of the quaternion
	 * representation of the input Euler angles. They are computed as a combination of
	 * the cosine and sine of the individual angle values, multiplied by each other.
	 * 	- `Quaternion`: This is the class that represents the quaternion, which is a
	 * mathematical object used to describe 3D rotations. The returned quaternion has
	 * four components, which correspond to the x, y, and z components of the rotation matrix.
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
	 * sets the values of a quaternion object to those of another quaternion object.
	 * 
	 * @param r 4-dimensional vector that contains the values to be set for the quaternion
	 * object.
	 * 
	 * 	- `getX()`: Retrieves the x-component of the quaternion.
	 * 	- `getY()`: Retrieves the y-component of the quaternion.
	 * 	- `getZ()`: Retrieves the z-component of the quaternion.
	 * 	- `getW()`: Retrieves the w-component of the quaternion, which represents the
	 * magnitude or length of the quaternion.
	 * 
	 * @returns a reference to the original object, unchanged.
	 * 
	 * The Quaternion object is updated with the given values for x, y, z, and w. The
	 * method returns the same instance of the Quaternion class, indicating that the
	 * original object has been modified.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns a floating-point representation of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the object's `x` field to the provided float argument.
	 * 
	 * @param x float value to be assigned to the field 'x' of the object on which the
	 * setX method is called.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the `y` field.
	 * 
	 * @returns the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field of its receiver to the provided float argument.
	 * 
	 * @param y 2D coordinate of the shape's center point, which is updated to reflect
	 * the new value assigned to it by the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field, which is a `float` variable containing an
	 * unspecified value.
	 * 
	 * @returns the value of the `z` field.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the field `z` to the argument passed as a float.
	 * 
	 * @param z 3D position of an object in the function `setZ()`.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * retrieves the value of a `float` variable `w`.
	 * 
	 * @returns the value of the `w` field.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the `w` field of the class to which it belongs.
	 * 
	 * @param w 2D width of an object that is being manipulated by the function.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares two quaternions for equality based on their component values.
	 * 
	 * @param r 4D vector to which the current Quaternion instance will be compared for
	 * equality.
	 * 
	 * 	- `x`: The real part of the Quaternion representation.
	 * 	- `y`: The imaginary part of the Quaternion representation.
	 * 	- `z`: The scalar part of the Quaternion representation.
	 * 	- `w`: The vector part of the Quaternion representation, which is a 3D vector.
	 * 
	 * @returns a boolean value indicating whether the input `Quaternion` object is equal
	 * to the current object.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
