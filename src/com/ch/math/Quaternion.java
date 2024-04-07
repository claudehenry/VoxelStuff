package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * in Java is a mathematical construct used to represent rotations in 3D space. It
 * has four fields: x, y, z, and w, which represent the real and imaginary parts of
 * a complex number that represents the rotation. The class also provides methods for
 * calculating the quaternion's conjugate, inverse, and norm, as well as for converting
 * between different rotational representations such as Euler angles and rotation
 * matrices. Additionally, it provides methods for rotating vectors by the quaternion's
 * rotation.
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
  * computes the Euclidean distance of a point in 3D space from origin, using the
  * Pythagorean theorem and square root operation.
  * 
  * @returns a float value representing the Euclidean distance of a 3D point from its
  * origin.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

 /**
  * normalizes a given quaternion by dividing each component by its length, resulting
  * in a new quaternion with a length of 1.
  * 
  * @returns a normalized quaternion representation of the input quaternion.
  * 
  * 	- The `Quaternion` object represents a normalized quaternion, with length equal
  * to 1.
  * 	- The `w`, `x`, `y`, and `z` fields represent the component values of the quaternion,
  * which are normalized to have a length of 1.
  * 	- The function returns a new `Quaternion` object representing the normalized quaternion.
  */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

 /**
  * generates a quaternion with the same magnitude as the input and the opposite
  * direction of its axis.
  * 
  * @returns a new Quaternion object with the conjugate of the input quaternion's components.
  * 
  * 	- The output is a new instance of the `Quaternion` class with the same scalar
  * components as the original input but with the `w` component reversed. This means
  * that if the original quaternion has `w = w0`, then the conjugate quaternion will
  * have `w = -w0`.
  * 	- The `x`, `y`, and `z` components of the output are the same as those of the
  * original input.
  * 	- The conjugate quaternion is a mirror image of the original quaternion, meaning
  * that if the original quaternion rotates an object in one direction, then the
  * conjugate quaternion will rotate the same object in the opposite direction.
  */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

 /**
  * multiplies a quaternion by a scalar value and returns the result as a new quaternion
  * object.
  * 
  * @param r 4D vector that is multiplied with the quaternion represented by the function.
  * 
  * @returns a quaternion with components scaled by the input value `r`.
  */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

 /**
  * computes the product of two quaternions by multiplying their coefficients and
  * combining them into a new quaternion.
  * 
  * @param r quaternion to be multiplied with the current quaternion, and its values
  * are used to compute the new quaternion's x, y, and z components.
  * 
  * 	- `w`: The scalar value representing the magnitude (or length) of the quaternion.
  * 	- `x`, `y`, and `z`: The vector components of the quaternion, representing the
  * rotation around the x, y, and z axes, respectively.
  * 
  * @returns a new quaternion instance representing the product of two quaternions.
  * 
  * 	- The output is a new instance of the `Quaternion` class, indicating that the
  * function returns a fresh quaternion object.
  * 	- The fields of the output quaternion are determined by multiplying the input
  * quaternions `w`, `x`, `y`, and `z`. Specifically, the `w_` field is the product
  * of `w` and `r.getW()`, the `x_` field is the sum of `x` and `w * r.getX()`, the
  * `y_` field is the sum of `y` and `w * r.getY()`, and the `z_` field is the sum of
  * `z` and `w * r.getZ()`.
  * 	- The resulting quaternion has the same direction as the input quaternions, but
  * its magnitude may be different due to the multiplication.
  */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

 /**
  * computes the quaternion product of a given vector and a quaternion, resulting in
  * another quaternion representation of the same rotation as the original input.
  * 
  * @param r 3D vector to which the quaternion is multiplied.
  * 
  * 	- `r.getX()`: returns the x-coordinate of the vector.
  * 	- `r.getY()`: returns the y-coordinate of the vector.
  * 	- `r.getZ()`: returns the z-coordinate of the vector.
  * 
  * The function then computes and returns a new quaternion instance with the product
  * of the input quaternion and the vector `r`.
  * 
  * @returns a Quaternion object representing the result of multiplying the input
  * vector with the quaternion.
  * 
  * The `Quaternion` object returned by the function has four components: `w`, `x`,
  * `y`, and `z`. These components represent the quaternion's real and imaginary parts,
  * respectively. The values of these components are calculated as follows: `w_ = -x
  * * r.getX() - y * r.getY() - z * r.getZ()`, `x_ = w * r.getX() + y * r.getZ() - z
  * * r.getY()`, `y_ = w * r.getY() + x * r.getX() - z * r.getX()`, and `z_ = w *
  * r.getZ() + x * r.getY() - y * r.getX()`.
  * 
  * These calculations result in a quaternion that represents the multiplication of
  * the input `r` vector by a scalar value. The quaternion's conjugate is equal to the
  * input vector multiplied by the scalar value, which makes it useful for representing
  * rotations and other geometric transformations in 3D space.
  */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

 /**
  * takes a `Quaternion` argument `r`, subtracts its components from those of the
  * original quaternion, and returns a new quaternion representing the resultant rotation.
  * 
  * @param r 4D quaternion to be subtracted from the current quaternion, resulting in
  * the updated quaternion output.
  * 
  * 	- `w`: The scalar value representing the real part of the quaternion.
  * 	- `x`, `y`, and `z`: The scalar values representing the imaginary parts of the
  * quaternion, each representing a different component of the quaternion.
  * 
  * @returns a new Quaternion object representing the result of subtracting the
  * quaternion `r` from the original quaternion.
  * 
  * 	- The `Quaternion` object returned has four components - `w`, `x`, `y`, and `z`,
  * which represent the differences between the input `r` and the current quaternion.
  * 	- These components are in the range of `-1 to 1`, representing the magnitude of
  * the difference between the two quaternions.
  * 	- The `Quaternion` object is created by subtracting `r` from the current quaternion,
  * resulting in a new quaternion that represents the difference between the two.
  */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * takes a `Quaternion` argument `r` and returns a new `Quaternion` instance with the
  * sum of its own values and those of `r`.
  * 
  * @param r 4D quaternion to be added to the current quaternion, resulting in the new
  * quaternion output.
  * 
  * 	- `r.getW()`: Retrieves the real part of the quaternion's scalar component.
  * 	- `r.getX()`, `r.getY()`, and `r.getZ()`: Retrieves the imaginary parts of the
  * quaternion's scalar components.
  * 
  * @returns a new Quaternion instance representing the sum of the input Quaternions.
  * 
  * 	- The output is a new Quaternion object representing the sum of the input Quaternions.
  * 	- The `w` field of the output represents the sum of the `w` fields of the input
  * Quaternions.
  * 	- The `x`, `y`, and `z` fields of the output represent the sum of the `x`, `y`,
  * and `z` fields of the input Quaternions, respectively.
  */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

 /**
  * generates a rotation matrix from a given vector representing the forward, up, and
  * right components of the rotation. The returned matrix can be used to perform
  * rotations in 3D space.
  * 
  * @returns a rotation matrix in the form of a 4x4 matrix.
  * 
  * The Matrix4f object returned by the function represents a 4x4 rotation matrix,
  * where each element is a 32-bit floating-point number. The matrix is initialized
  * using the `initRotation` method, which takes three vectors as input: forward, up,
  * and right. These vectors represent the x, y, and z components of the rotation axis,
  * respectively.
  * 
  * The forward vector has a magnitude of 2 and an angle of 0 or π/2 radians, depending
  * on the orientation of the rotation axis. The up vector has a magnitude of 1 and
  * an angle of π/2 radians, regardless of the orientation of the rotation axis.
  * Finally, the right vector has a magnitude of 1 and an angle of 0 radians, as it
  * is always perpendicular to both the forward and up vectors.
  * 
  * The matrix itself represents a rotation around the origin of the coordinate system,
  * where each element is computed based on the dot product of the corresponding axis
  * vectors and the angles between them. The resulting matrix has the properties that
  * its determinant is 1, and its inverse is equal to its transpose. This makes it an
  * orthonormal matrix, which is required for many applications in computer graphics
  * and robotics.
  */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

 /**
  * takes a `Quaternion` object `r` and returns the dot product of the two objects' coordinates.
  * 
  * @param r 4-dimensional quaternion value to be multiplied with the current quaternion,
  * resulting in a new quaternion value that is returned as the function output.
  * 
  * 	- `x`, `y`, `z`, and `w` are instance fields representing the real-valued components
  * of `Quaternion`.
  * 	- `getX()`, `getY()`, `getZ()`, and `getW()` are methods providing access to the
  * respective component values.
  * 
  * @returns a floating-point number representing the dot product of two quaternions.
  */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

 /**
  * takes a destination quaternion `dest`, a lerp factor, and an optional shortest
  * parameter. It calculates the interpolated quaternion using the LERP method, taking
  * into account the sign of the dot product between the current quaternion and the
  * destination quaternion, and normalizing the result to ensure it has the correct length.
  * 
  * @param dest 4-dimensional vector that the function will interpolate between using
  * the provided `lerpFactor`.
  * 
  * 	- `Quaternion dest`: The destination quaternion for interpolation.
  * 	- `lerpFactor`: A factor used to interpolate between the source and destination
  * quaternions.
  * 	- `shortest`: An optional parameter that indicates whether the shortest path
  * should be taken when interpolating between the source and destination quaternions.
  * If true, the function will correct the destination quaternion if it would result
  * in a negative dot product with the source quaternion.
  * 
  * @param lerpFactor 0-1 value for which the quaternion is linearly interpolated
  * between the source and destination quaternions.
  * 
  * @param shortest 4-vector component of the destination quaternion to be corrected
  * if it is less than -1, and sets it to its negative value instead.
  * 
  * @returns a Quaternion that represents the interpolated rotation between two given
  * Quaternions, taking into account the shortest path and the dot product of the two
  * input Quaternions.
  * 
  * 	- The output is a Quaternion object, representing a rotational transformation matrix.
  * 	- The value of `dest` is interpolated between the input arguments `lerpFactor`
  * and `shortest`, resulting in a corrected destination quaternion.
  * 	- If `shortest` is true and the dot product of the source and destination quaternions
  * is negative, the output is negated to ensure proper orientation.
  * 	- The resulting quaternion is normalized to have a length of 1.
  */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

 /**
  * computes a linear interpolation between two Quaternions based on their dot product
  * and shortest path, with an optional adjustment for when the interpolated quaternion
  * would be inverted.
  * 
  * @param dest 4-component quaternion that is to be interpolated towards a target
  * quaternion, using linear interpolation with optional shortest path optimization.
  * 
  * 	- `dest.getW()`: The scalar component of the quaternion representation of the
  * destination point.
  * 	- `dest.getX()`, `dest.getY()`, and `dest.getZ()`: The vector components of the
  * quaternion representation of the destination point.
  * 
  * The `correctedDest` variable is created by modifying the original `dest` value
  * when the cosine of the angle between the source and destination quaternions is
  * negative, indicating a flipped orientation.
  * 
  * @param lerpFactor factor by which the quaternion is to be linearly interpolated
  * between the source and destination quaternions, with values ranging from 0 to 1.
  * 
  * @param shortest 3D-space distance between the starting and ending quaternions and
  * determines whether to invert the quaternion when cosine is negative, returning a
  * new quaternion if necessary.
  * 
  * @returns a quaternion that interpolates between two given quaternions based on the
  * Lerp formula.
  * 
  * 1/ The output is a Quaternion object, which represents a 3D rotational transformation.
  * 2/ The w-component (getW()) of the Quaternion represents the rotation angle around
  * the axis of rotation.
  * 3/ The x, y, and z-components (getX(), getY(), and getZ()) of the Quaternion
  * represent the rotation along the corresponding axes.
  * 4/ If `shortest` is set to true, then the rotation will be limited to the range
  * (-π, π], otherwise, it will be unlimited.
  * 5/ The `lerpFactor` parameter represents the interpolation factor between the
  * source and destination Quaternions.
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
  * rotates a vector by 90 degrees around the x-axis, resulting in a forward-facing vector.
  * 
  * @returns a vector representing the direction of the rotated object.
  * 
  * The returned value is a `Vector3f` object representing the forward direction of
  * the rotated object.
  * 
  * The `x`, `y`, and `z` components of the vector represent the offset from the origin
  * in the x, y, and z directions, respectively.
  * 
  * The `rotation` property of the vector represents the amount of rotation applied
  * to the object.
  */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees around the z-axis to return a new vector pointing
  * backward from the current position.
  * 
  * @returns a rotated vector with a magnitude of -1, representing the direction of
  * the object's back.
  * 
  * The `Vector3f` object returned is a transformed version of the original vector
  * with its x-component set to 0, y-component set to 0, and z-component set to -1.
  * This transformation occurs as a result of applying the rotate operation to the
  * original vector.
  */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees around the z-axis, resulting in a vector pointing
  * upwards from the original position.
  * 
  * @returns a rotated vector representing the upward direction relative to the object's
  * orientation.
  * 
  * The output is a `Vector3f` object, which represents a 3D vector in standard form
  * (x, y, z).
  * The vector is rotated by multiplying it with the rotation matrix of the object.
  * The rotation matrix is obtained by calling the `rotate` method on the object itself.
  * The resulting vector has the same direction as the original vector, but its magnitude
  * is proportional to the dot product of the original vector and the rotation matrix.
  */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees around the x-axis, resulting in a new vector pointing
  * downward from the original position.
  * 
  * @returns a rotated vector representing the direction down from the current position.
  * 
  * The output is a `Vector3f` object representing the downward direction from the
  * current position of the `Entity` instance. The vector has three components: x, y,
  * and z, each representing the distance from the current position along the respective
  * axis.
  */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees around the x-axis, resulting in a new vector that
  * points towards the right.
  * 
  * @returns a rotated vector with a magnitude of 1 and an orientation of 90 degrees
  * around the x-axis.
  * 
  * The `Vector3f` object that is returned has three components representing the x,
  * y, and z values of the right vector, respectively. The value of each component is
  * determined by applying a rotation transformation to the original vector using the
  * `rotate` method. Specifically, the rotation is performed around the x-axis, with
  * the z-axis serving as the origin of the rotation. This means that the resulting
  * vector points in the direction of the positive y-axis, passing through the origin.
  */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

 /**
  * rotates a vector by 90 degrees to the left, resulting in a new vector that points
  * in the opposite direction of the original vector's tail.
  * 
  * @returns a rotated vector of size (1, 0, 0).
  * 
  * The output is a vector in 3D space with magnitude of -1 and direction pointing
  * leftward from the origin.
  * When the vector is rotated by the same amount as the object it was taken from, the
  * resulting vector points back to the original position of the object.
  * The rotation is performed using the `rotate` method of the `Vector3f` class, which
  * takes in the object being rotated and returns a new vector transformed by that rotation.
  */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

 /**
  * sets the values of a `Quaternion` object's `x`, `y`, `z`, and `w` fields to the
  * input arguments.
  * 
  * @param x 3D position of the quaternion in the x-axis direction.
  * 
  * @param y 2D component of the quaternion.
  * 
  * @param z 3rd component of the quaternion, which is updated to match the provided
  * value.
  * 
  * @param w 4th component of the quaternion, which is used to store the magnitude of
  * the quaternion.
  * 
  * @returns a reference to the same `Quaternion` instance, allowing for chaining of
  * method calls.
  * 
  * 	- The `this` keyword is used to refer to the current instance of the `Quaternion`
  * class.
  * 	- The `x`, `y`, `z`, and `w` parameters represent the new values for each component
  * of the quaternion, respectively.
  * 	- By assigning these values to the respective fields of the `Quaternion` object,
  * the function updates the quaternion's components.
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
  * converts a vector of Euler angles into a quaternion representation, which is a
  * more efficient and intuitive way to represent 3D rotations.
  * 
  * @param eulerAngles 3D Euler angles, specifically the phi, theta, and yota angles,
  * which are used to calculate the quaternion representation of the rotation.
  * 
  * 	- `phi`: The zenith angle of the quaternion, which ranges from 0 to 2π.
  * 	- `theta`: The azimuth angle of the quaternion, which ranges from 0 to 2π.
  * 	- `yota`: The elevation angle of the quaternion, which ranges from -π to π.
  * 
  * The function then performs various calculations involving these angles using
  * mathematical constants and operations to produce the final output: a Quaternion object.
  * 
  * @returns a quaternion representation of the input Euler angles.
  * 
  * 	- `q0`, `q1`, `q2`, and `q3` represent the four components of the quaternion,
  * which are calculated based on the input `eulerAngles`.
  * 	- These components are represented as floats, with a range of -1 to 1.
  * 	- The quaternion is a 4D vector representation of a rotation in 3D space, where
  * each component represents a factor of the rotation.
  * 	- The order of the components is (w, x, y, z), where w is the magnitude of the
  * quaternion and the other three components represent the axis of rotation.
  * 	- The values of `q0`, `q1`, `q2`, and `q3` can be used to perform rotations in
  * 3D space using the quaternion multiplication formula.
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
  * sets the values of a Quaternion object to those of another Quaternion object,
  * without changing its internal representation.
  * 
  * @param r 4-component quaternion that modifies the components of the `this` quaternion.
  * 
  * 	- `getX()` and `getY()` represent the x-coordinates of the quaternion's coordinates.
  * 	- `getZ()` and `getW()` represent the z-coordinates and the "wrap angle" (or
  * "wrapping angle") of the quaternion, respectively.
  * 
  * These properties are used to set the values of this quaternion's coordinates accordingly.
  * 
  * @returns a reference to the same Quaternion object, unchanged.
  * 
  * 	- This function sets the values of the Quaternion object to those of the input argument.
  * 	- The function takes four arguments, representing the x, y, z, and w components
  * of the input quaternion.
  * 	- The function returns a reference to the same Quaternion object, indicating that
  * the method modifies the original object rather than returning a new one.
  */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

 /**
  * retrieves the value of a `float` variable named `x`.
  * 
  * @returns a floating-point value representing the variable `x`.
  */
	public float getX() {
		return x;
	}

 /**
  * sets the value of the instance field `x` to the provided float argument.
  * 
  * @param x float value that will be assigned to the class instance variable `x`.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * returns the value of the variable `y`.
  * 
  * @returns the value of the `y` field, which is a floating-point number.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the `y` field of the object it is called on to the provided float
  * argument.
  * 
  * @param y float value to be assigned to the `y` field of the class instance, and
  * it is being assigned using the `this.y = y;` statement.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * retrieves the value of a `float` variable named `z`.
  * 
  * @returns the value of the `z` field.
  */
	public float getZ() {
		return z;
	}

 /**
  * sets the value of the field `z` of the object it is called on to the argument
  * passed as a float.
  * 
  * @param z 3D coordinate of an object in the function `setZ()`.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * retrieves the value of a `w` field, which is likely used to store a specific value
  * related to the program's functionality.
  * 
  * @returns the value of the `w` field.
  */
	public float getW() {
		return w;
	}

 /**
  * sets the `w` field of its object argument to the provided float value.
  * 
  * @param w floating-point value that sets the width of an object.
  */
	public void setW(float w) {
		this.w = w;
	}

 /**
  * compares a `Quaternion` object with another `Quaternion` object and returns `true`
  * if all components are equal, otherwise `false`.
  * 
  * @param r Quaternion to which the current Quaternion is compared for equality.
  * 
  * 	- `x`: represents the real part of the quaternion
  * 	- `y`: represents the imaginary part of the quaternion
  * 	- `z`: represents the scalar part of the quaternion
  * 	- `w`: represents the vector part of the quaternion
  * 
  * @returns a boolean value indicating whether the given quaternion is equal to the
  * current quaternion.
  */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
