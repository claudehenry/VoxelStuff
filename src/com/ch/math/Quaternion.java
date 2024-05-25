package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * in Java provides a way to represent 3D rotations and transformations. It offers
 * several methods for calculating quaternion values from Euler angles, as well as
 * methods for converting between different coordinate systems and performing geometric
 * calculations. The class also includes utility methods for working with quaternions,
 * such as normalizing and conjugating them, as well as implementing the multiplication
 * and division operations.
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
	 * computes the Euclidean length of a vector in 3D space by squaring its components
	 * and taking the square root.
	 * 
	 * @returns the square root of the sum of the squares of the four components of a vector.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes a quaternion by dividing each component by its length, resulting in a
	 * unit quaternion with a length of 1.
	 * 
	 * @returns a normalized quaternion representation of the original quaternion.
	 * 
	 * 	- The Quaternion object has four components: w, x, y, and z, which represent the
	 * real and imaginary parts of the quaternion, respectively.
	 * 	- Each component is a float value between 0 and 1.
	 * 	- The length of the quaternion is calculated as the square root of the sum of the
	 * squares of its components.
	 * 	- The quaternion is normalized by dividing each component by its corresponding length.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * creates a new quaternion by returning a copy with the conjugate of its components:
	 * `-w`, `-x`, `-y`, and `-z`.
	 * 
	 * @returns a new Quaternion object with the conjugate of its input parameters.
	 * 
	 * The Quaternion object created is assigned to the variable `return`. The variable
	 * `w`, `x`, `y`, and `z` represent the components of the input Quaternion.
	 * The returned Quaternion has the same components as the input Quaternion, but their
	 * signs are reversed.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies a quaternion by a scalar value, returning a new quaternion with the
	 * product of the quaternion's components and the input scalar.
	 * 
	 * @param r scalar value to be multiplied with the quaternion's components, resulting
	 * in the updated quaternion values.
	 * 
	 * @returns a new quaternion object with the product of the input `r` multiplied by
	 * each component of the original quaternion.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * computes the multiplication of two quaternions by multiplying their respective
	 * components and storing the result in a new quaternion object.
	 * 
	 * @param r 4-component quaternion to be multiplied with the current quaternion,
	 * resulting in a new quaternion that combines the two.
	 * 
	 * 	- `w_`: The magnitude (or length) of the quaternion `r`.
	 * 	- `x_`, `y_`, and `z_`: The coordinates of the quaternion `r`.
	 * 
	 * @returns a new quaternion instance representing the product of the input quaternions.
	 * 
	 * 	- The returned Quaternion object has four components: `w_, x_, y_, z_`. These
	 * represent the real and imaginary parts of the product of the input Quaternions `r`
	 * multiplied by the current Quaternion.
	 * 	- The values of `w_`, `x_`, `y_`, and `z_` are calculated using the multiplication
	 * of the `w`, `x`, `y`, and `z` components of the input Quaternions `r`.
	 * 	- The returned Quaternion represents a rotational transformation that is the
	 * result of multiplying the current Quaternion by the input Quaternion. This
	 * transformation can be used to rotate an object in 3D space.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * takes a `Vector3f` argument `r` and multiplies it with the quaternion represented
	 * by the function, resulting in another `Quaternion` object.
	 * 
	 * @param r 3D vector that is multiplied with the quaternion, resulting in another
	 * 3D vector output.
	 * 
	 * 	- `r.getX()` returns the x-coordinate of the vector.
	 * 	- `r.getY()` returns the y-coordinate of the vector.
	 * 	- `r.getZ()` returns the z-coordinate of the vector.
	 * 
	 * The function then computes and stores the product of the quaternion and the vector
	 * in a new quaternion object, which is returned as output.
	 * 
	 * @returns a new `Quaternion` instance representing the result of multiplying the
	 * given `Vector3f` instance by the quaternion represented by the function.
	 * 
	 * The `Quaternion` object returned by the function has four attributes: `w`, `x`,
	 * `y`, and `z`. Each attribute represents a component of the quaternion and is a
	 * floating-point value between 0 and 1. The values of these components are determined
	 * by multiplying the input vector `r` by various constants based on the orientation
	 * of the quaternion.
	 * 
	 * The quaternion is a non-rotating element in 3D space, meaning that its rotation
	 * axis does not change when it is applied to a 3D object. This property makes it
	 * useful for representing rotations in computer graphics and other applications where
	 * quaternions are used.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * computes the quaternion difference between two quaternions by subtracting their
	 * component values.
	 * 
	 * @param r quaternion to be subtracted from the current quaternion.
	 * 
	 * The `Quaternion r` object returned is a new instance with the following values:
	 * 	- W (w): The magnitude (or length) of the quaternion, which is also the scalar
	 * part of the result.
	 * 	- X (x), Y (y), and Z (z): These are the vector parts of the result, which are
	 * calculated by subtracting the corresponding components of `r` from the corresponding
	 * components of the input quaternion.
	 * 
	 * @returns a new Quaternion object representing the difference between the input
	 * Quaternion and the reference Quaternion.
	 * 
	 * 	- The output is a new Quaternion object with values `w - r.getW()`, `x - r.getX()`,
	 * `y - r.getY()`, and `z - r.getZ()`.
	 * 	- The resulting quaternion represents the difference between the current quaternion
	 * and the argument quaternion.
	 * 	- The quaternion components are calculated by subtracting the corresponding values
	 * of the argument quaternion from those of the current quaternion.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * adds two quaternions by combining their scalar and vector components.
	 * 
	 * @param r quaternion to be added to the current quaternion.
	 * 
	 * 	- `w`: The real part of the quaternion.
	 * 	- `x`, `y`, and `z`: The imaginary parts of the quaternion, each representing a
	 * different component of the quaternion.
	 * 
	 * @returns a new Quaternion instance representing the sum of the input arguments.
	 * 
	 * 	- The output is a new Quaternion object representing the sum of the input Quaternions.
	 * 	- The `w`, `x`, `y`, and `z` components of the output are calculated by adding
	 * the corresponding components of the input Quaternions.
	 * 	- The output has the same orientation as the input Quaternions, indicating that
	 * the operation preserves the rotational properties of the original values.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * computes a rotation matrix based on the provided vector values for forward, up,
	 * and right. The resulting matrix can be used to represent a 3D rotation in various
	 * applications.
	 * 
	 * @returns a 4x4 rotation matrix.
	 * 
	 * The returned object is a 4x4 matrix representing a rotation transformation in 3D
	 * space. The matrix consists of three columns representing the forward, up, and right
	 * vectors of the rotation, respectively. Each column is a 4-element vector representing
	 * the coordinate of the corresponding axis in the rotation.
	 * 
	 * The rotation matrix is obtained by multiplying the product of two vectors: (x*z -
	 * w*y) and (2*x*y + z*z - w*w). This product represents the dot product of the two
	 * vectors, which is then scaled by a factor of 2 to obtain the final result.
	 * 
	 * The properties of the rotation matrix can be observed as follows:
	 * 
	 * 	- The determinant of the matrix is non-zero, indicating that the transformation
	 * is a valid rotation.
	 * 	- The eigenvalues of the matrix are all non-zero and have absolute values less
	 * than or equal to 1, which means that the transformation is a rotation in the complex
	 * plane.
	 * 	- The matrix represents a counterclockwise rotation around the origin, as observed
	 * from the positive x-axis.
	 * 
	 * In summary, the `toRotationMatrix` function returns a 4x4 rotation matrix representing
	 * a valid rotation transformation in 3D space.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * computes the dot product of a quaternion and another quaternion, returning a float
	 * value.
	 * 
	 * @param r 4D quaternion value that is multiplied with the `this` quaternion to
	 * produce the dot product output.
	 * 
	 * 	- `x`, `y`, `z`, and `w` are variables representing the real-valued components
	 * of the quaternion.
	 * 
	 * @returns a scalar value representing the dot product of the quaternion and the
	 * provided quaternion.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a linear interpolation between two quaternions, `dest` and `start`, based
	 * on the provided factor `lerpFactor`. It returns the interpolated quaternion
	 * normalized to have length equal to 1.
	 * 
	 * @param dest 4-dimensional vector that will be modified by the lerping process.
	 * 
	 * 	- `Quaternion dest`: This is the destination quaternion to which the interpolation
	 * will be applied. It has four components: `w`, `x`, `y`, and `z`.
	 * 	- `lerpFactor`: This is a parameter that determines how much of the interpolation
	 * should be applied to the `dest` quaternion. It is a floating-point value between
	 * 0 and 1.
	 * 	- `shortest`: This boolean parameter specifies whether the interpolation should
	 * be performed in the shortest path or not. If `true`, then the interpolation will
	 * be performed in the shortest path, while if `false`, it will be performed in the
	 * straight line.
	 * 
	 * @param lerpFactor 0 to 1 factor by which the destination quaternion is linearly
	 * interpolated between the source quaternion and the shortest form of the destination
	 * quaternion, if applicable.
	 * 
	 * @param shortest shortest quaternion path from the original quaternion to the
	 * destination quaternion, by negating the quaternion if its dot product with the
	 * destination quaternion is less than zero.
	 * 
	 * @returns a Quaternion representation of the interpolated rotation between two given
	 * values.
	 * 
	 * 	- The output is a quaternion, represented by the `Quaternion` class in Java.
	 * 	- The quaternion has four components: `w`, `x`, `y`, and `z`. These represent the
	 * real and imaginary parts of the quaternion, respectively.
	 * 	- The quaternion is normalized, meaning that its length is equal to 1. This ensures
	 * that the quaternion has a fixed direction in 3D space.
	 * 	- The quaternion is created by multiplying the input `dest` quaternion by a scalar
	 * factor `lerpFactor`, which represents the interpolation fraction between the two
	 * inputs.
	 * 	- If the `shortest` parameter is true, then the output quaternion is flipped if
	 * the dot product of the input and destination quaternions is negative. This ensures
	 * that the resulting quaternion points in the correct direction for forward rendering.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * computes a quaternion interpolation between two given quaternions, based on the
	 * shortest distance method or the linear interpolation method. It returns the
	 * interpolated quaternion.
	 * 
	 * @param dest 4D quaternion vector that the function will interpolate between, based
	 * on the `lerpFactor` and `shortest` parameters.
	 * 
	 * 	- `dest.getW()` represents the scalar component (magnitude) of the quaternion.
	 * 	- `dest.getX()`, `dest.getY()`, and `dest.getZ()` represent the vector components
	 * (direction) of the quaternion.
	 * 
	 * Additionally, `EPSILON` is a constant representing an arbitrary small value used
	 * to avoid division by zero in the calculation of `sin`.
	 * 
	 * @param lerpFactor factor by which the quaternion is to be linearly interpolated
	 * between the start and end values.
	 * 
	 * @param shortest shortest quaternion path between the source and destination
	 * quaternions, which is used to calculate the lerped quaternion when the cosine of
	 * the angle between them is close to -1.
	 * 
	 * @returns a new quaternion that interpolates between the given destination quaternion
	 * and the original quaternion, based on the specified lerp factor.
	 * 
	 * 	- The output is a Quaternion object, which represents a 3D rotation matrix in the
	 * form of a complex number.
	 * 	- The quaternion is created by multiplying the input quaternion by two factors:
	 * `srcFactor` and `destFactor`. These factors are calculated based on the input
	 * values of `lerpFactor` and `shortest`, which determine how much to lerp between
	 * the source and destination quaternions.
	 * 	- The `srcFactor` is a complex number that represents the amount of rotation from
	 * the source quaternion towards the destination quaternion, while `destFactor`
	 * represents the amount of rotation from the destination quaternion back to the
	 * source quaternion.
	 * 	- The output quaternion has four components: `w`, `x`, `y`, and `z`, which represent
	 * the real and imaginary parts of the complex number.
	 * 	- The quaternion is normalized, meaning that its magnitude (i.e., the length of
	 * the vector) is equal to 1.0f. This ensures that the output quaternion has a
	 * consistent size and orientation, regardless of the input values.
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
	 * rotates a vector by 90 degrees around the z-axis to produce a forward vector
	 * pointing in the same direction as the entity's movement.
	 * 
	 * @returns a vector indicating the forward direction of the rotated object.
	 * 
	 * 	- The output is a Vector3f object, which represents a 3D vector in homogeneous coordinates.
	 * 	- The vector's components are specified by the method signature: `(0, 0, 1)`.
	 * 	- The vector is rotated using the `rotate` method of the same object, which means
	 * that the vector's orientation is changed relative to its original position.
	 * 
	 * The properties of the output vector can be further analyzed as follows:
	 * 
	 * 	- The x-component is 0, indicating that the vector points directly along the x-axis.
	 * 	- The y-component is also 0, indicating that the vector points directly along the
	 * y-axis.
	 * 	- The z-component is 1, indicating that the vector points directly along the z-axis.
	 * 
	 * Therefore, the output of the `getForward` function is a vector that points directly
	 * towards the positive z-axis.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the z-axis, effectively "moving"
	 * it backward along the negative z-axis.
	 * 
	 * @returns a rotated version of the original vector with a magnitude of -1.
	 * 
	 * The `Vector3f` object generated is a rotated version of the original vector, with
	 * the x-component unchanged and y- and z-components negative.
	 * The rotation is performed using the `rotate` method of the `Vector3f` class, which
	 * takes another `Vector3f` argument as input and returns a new `Vector3f` object
	 * representing the result of the rotation.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the z-axis, resulting in a new vector pointing
	 * upwards in the local coordinate system.
	 * 
	 * @returns a rotated vector representing the upward direction relative to the object's
	 * orientation.
	 * 
	 * The output is a `Vector3f` object, which represents a 3D vector in homogeneous
	 * form. It has three components: x, y, and z, representing the magnitude and direction
	 * of the vector in 3D space. The vector is rotated by the angle of the object it was
	 * called on, providing an upward direction vector relative to the rotation of the object.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the Z-axis, resulting in a new vector that
	 * points downwards from the original position.
	 * 
	 * @returns a rotated version of the original vector.
	 * 
	 * The output is a `Vector3f` object representing the downward direction from the
	 * current position.
	 * It has a x-coordinate of 0, indicating that it points directly downwards from the
	 * origin.
	 * Its y-coordinate is -1, indicating that it points in the negative y-direction
	 * relative to the origin.
	 * It has a z-coordinate of 0, indicating that it points in the positive z-direction
	 * relative to the origin.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the x-axis, resulting in a new vector pointing
	 * in the right direction.
	 * 
	 * @returns a rotated vector with a magnitude of 1 and a direction perpendicular to
	 * the original vector.
	 * 
	 * The `Vector3f` object returned by this function is a transformed version of the
	 * original vector, where it has been rotated by 90 degrees around the x-axis. This
	 * means that the x-component of the vector remains unchanged, while the y- and
	 * z-components are swapped. Therefore, the vector now points in the direction
	 * perpendicular to the original vector.
	 * 
	 * The rotation is performed using the `rotate` method, which takes the current vector
	 * as input and returns a new vector that has been rotated by the specified angle
	 * around the specified axis. In this case, the angle of rotation is 90 degrees, and
	 * the axis of rotation is the x-axis.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the x-axis, resulting in a new vector that
	 * points left from the original position.
	 * 
	 * @returns a rotated vector with a magnitude of -1 and a direction perpendicular to
	 * the original vector.
	 * 
	 * 	- The output is a `Vector3f` object, indicating that it has three components
	 * representing the magnitude and direction of a point in 3D space.
	 * 	- The first component of the output is negative, indicating that the point is
	 * shifted to the left relative to the original vector.
	 * 	- The second and third components are zero, indicating that the point does not
	 * change in the Y and Z directions.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * updates the fields of a `Quaternion` object with the provided values for x, y, z,
	 * and w, and returns the modified object.
	 * 
	 * @param x 3D coordinate of the quaternion's axis of rotation in the x-direction.
	 * 
	 * @param y 2D projection of the quaternion along the x-axis, and it is assigned to
	 * the `y` field of the quaternion object.
	 * 
	 * @param z 3rd component of the quaternion, which is multiplied by the rotation angle
	 * to update the quaternion's orientation.
	 * 
	 * @param w 4th component of the quaternion, which is used to rotate the object in
	 * 3D space.
	 * 
	 * @returns a reference to the same `Quaternion` object, allowing for chaining of
	 * method calls.
	 * 
	 * 	- `this`: refers to the instance of the `Quaternion` class being modified.
	 * 	- `x`, `y`, `z`, and `w`: represent the new values for each component of the quaternion.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * converts a vector of Euler angles into a quaternion representation, which can be
	 * used for rotations in 3D space.
	 * 
	 * @param eulerAngles 3D Euler angles (phi, theta, yota) that are used to compute the
	 * quaternion representation of the rotation.
	 * 
	 * 	- `eulerAngles`: A `Vector3f` object containing the Euler angles in radians,
	 * represented as a 3D vector. The angles are stored in the x, y, and z components
	 * of the vector, respectively.
	 * 	- `phi`: The angle of rotation around the x-axis (radians).
	 * 	- `theta`: The angle of rotation around the y-axis (radians).
	 * 	- `yota`: The angle of rotation around the z-axis (radians).
	 * 
	 * @returns a quaternion representation of the provided Euler angles.
	 * 
	 * 	- `q0`, `q1`, `q2`, and `q3`: These are the components of the quaternion
	 * representation of the rotation represented by the input `eulerAngles`. Each component
	 * is a float value between -1 and 1, representing the magnitude and direction of the
	 * corresponding axis of rotation.
	 * 	- `Quaternion`: This is the class that `fromEuler` returns, which represents a
	 * quaternion as a set of four components. The Quaternion class provides methods for
	 * converting between other representations of rotations, such as Euler angles,
	 * rotation matrices, and other quaternion representations.
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
	 * @param r 4-dimensional quaternion to which the current quaternion will be set.
	 * 
	 * 	- `getX()`: Retrieves the x-coordinate of the quaternion.
	 * 	- `getY()`: Retrieves the y-coordinate of the quaternion.
	 * 	- `getZ()`: Retrieves the z-coordinate of the quaternion.
	 * 	- `getW()`: Retrieves the w-coordinate of the quaternion.
	 * 
	 * @returns a reference to the same `Quaternion` object, unchanged.
	 * 
	 * The function takes four arguments - `x`, `y`, `z`, and `w` representing the
	 * quaternion components. These values are used to set the corresponding components
	 * of the `this` object. The function then returns a reference to the modified `this`
	 * object, indicating that it is now the new value of the `set` method.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * retrieves the value of the `x` field, which is a `float` variable.
	 * 
	 * @returns a floating-point representation of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the class to which it belongs.
	 * 
	 * @param x float value that sets the object's `x` field.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the `y` field.
	 * 
	 * @returns the value of the `y` field, which is a `float` type.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field of its receiver to the input argument `y`.
	 * 
	 * @param y 2D coordinate of the shape's vertex in the Y-axis, which is assigned to
	 * the `y` field of the shape object.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of a field named `z`. It returns the value of the field as a
	 * `float` type.
	 * 
	 * @returns a floating-point value representing the z-coordinate of an object.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the field `z` of its object reference parameter to the specified
	 * float value.
	 * 
	 * @param z 3D coordinate of an object's position and is assigned to the `z` field
	 * of the class, effectively setting the object's vertical position.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * retrieves the value of a variable `w`.
	 * 
	 * @returns the value of the `w` field.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the value of the field `w` to the argument passed as a float.
	 * 
	 * @param w float value that sets the object's width.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares two Quaternion objects based on their `x`, `y`, `z`, and `w` values,
	 * returning `true` if they match and `false` otherwise.
	 * 
	 * @param r 4D vector to which the current Quaternion should be compared for equality.
	 * 
	 * 	- `x`: The real part of `r`, represented by `Double`.
	 * 	- `y`: The imaginary part of `r`, also represented by `Double`.
	 * 	- `z`: The scalar part of `r`, represented by `Double`.
	 * 	- `w`: The magnitude (or length) of the complex part of `r`, also represented by
	 * `Double`.
	 * 
	 * @returns a boolean value indicating whether the current quaternion is equal to the
	 * provided quaternion.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
