package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Is a mathematical representation of a 3D rotation, providing methods for calculating
 * rotations and conversions between different rotation representations. It includes
 * methods for setting/getting values of x, y, z, and w, as well as methods for
 * converting from Euler angles and vice versa. Additionally, it provides methods for
 * getting forward, backward, up, down, right, and left directions in 3D space.
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
	 * Calculates the Euclidean length of a vector in 3D space, given its coordinates as
	 * input.
	 * 
	 * @returns the square root of the sum of the squares of the coordinates of a 3D point.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Normalizes a quaternion by dividing each component by its length, resulting in a
	 * unit quaternion with length of 1.
	 * 
	 * @returns a normalized quaternion representation of the original input.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Generates a new quaternion with conjugated coordinates w, x, y and z.
	 * 
	 * @returns a new `Quaternion` instance with the conjugate of the original quaternion's
	 * coordinates.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Multiplies a quaternion by a scalar value and returns a new quaternion with the
	 * resulting rotation value.
	 * 
	 * @param r scalar value to be multiplied with the quaternion's components.
	 * 
	 * @returns a quaternion representing the multiplication of the original quaternion
	 * and a scalar value.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Multiplies two quaternions and returns their product as a new quaternion.
	 * 
	 * @param r 4D quaternion to be multiplied with the current quaternion.
	 * 
	 * @returns a new Quaternion object with the product of the input quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Multiplies a quaternion by a vector, resulting in another quaternion with the same
	 * direction but modified magnitude.
	 * 
	 * @param r 3D vector that the quaternion is multiplied by, resulting in a new
	 * quaternion representation of the rotation.
	 * 
	 * @returns a new `Quaternion` instance representing the result of multiplying the
	 * input `Vector3f` by the quaternion.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * representing the difference between the original and the argument quaternions.
	 * 
	 * @param r 4D vector to be subtracted from the current 4D vector, resulting in the
	 * updated 4D vector of the subtraction operation.
	 * 
	 * @returns a new quaternion representing the difference between the input quaternions.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Adds two quaternions by concatenating their coefficients, resulting in a new quaternion.
	 * 
	 * @param r 4D quaternion to be added to the current quaternion.
	 * 
	 * @returns a new quaternion object containing the sum of the input quaternions' coefficients.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * Converts a quaternion representation of a rotation into a 4x4 homogeneous
	 * transformation matrix.
	 * 
	 * @returns a 4x4 rotation matrix.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Computes the dot product of a quaternion `r` and the current quaternion instance,
	 * returning a float value representing the magnitude of the cross product of the two
	 * quaternions.
	 * 
	 * @param r 4D quaternion value that is multiplied with the current quaternion to
	 * produce the dot product.
	 * 
	 * @returns a floating-point number representing the dot product of the input quaternion
	 * and the provided quaternion.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Computes a linear interpolation between two quaternions, taking into account the
	 * shortest distance path and the dot product of the quaternions.
	 * 
	 * @param dest 4-element Quaternion that will be interpolated between based on the `lerpFactor`.
	 * 
	 * @param lerpFactor 0-1 factor used for linear interpolation between the current
	 * value of the `dest` quaternion and the final value desired by the user.
	 * 
	 * @param shortest shortest quaternion direction, and if it is set to true, the
	 * function will return the negative of the original destination quaternion if its
	 * dot product with the given quaternion is less than zero.
	 * 
	 * @returns a normalized quaternion that represents the smooth interpolation of the
	 * given quaternion along the specified lerp factor.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Computes a smooth linear interpolation between two Quaternion values, based on the
	 * given `lerpFactor`. It first calculates the angle between the source and destination
	 * quaternions, and then interpolates between them using sine functions. The resulting
	 * quaternion is returned.
	 * 
	 * @param dest 4-dimensional vector that the interpolation will be applied to, and
	 * its value is modified based on the `shortest` parameter.
	 * 
	 * * `dest` represents the final destination quaternion.
	 * * It has four components: `w`, `x`, `y`, and `z`.
	 * 
	 * @param lerpFactor 0-1 value that determines how much the quaternion will be
	 * interpolated between the source and destination values.
	 * 
	 * @param shortest shortest path between the source and destination quaternions, which
	 * is used when the cosine angle between them is close to 90 degrees.
	 * 
	 * @returns a Quaternion object representing the interpolated rotation between two
	 * given rotations.
	 * 
	 * * The output is a Quaternion object, representing a rotational transformation.
	 * * The quaternion has four components: w, x, y, and z, which correspond to the real
	 * and imaginary parts of the complex number representation of the rotation.
	 * * The value of the w component represents the magnitude of the rotation, while the
	 * other three components (x, y, and z) represent the orientation of the rotation
	 * around the axis specified by the quaternion.
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
	 * Rotates the vector `this` by 90 degrees around the z-axis, resulting in a vector
	 * pointing directly forward from the original position.
	 * 
	 * @returns a vector pointing in the forward direction relative to the object's orientation.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Rotates a `Vector3f` instance by 90 degrees around the z-axis, resulting in a
	 * vector pointing backward from the original position.
	 * 
	 * @returns a rotated vector with a magnitude of -1.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Rotates a vector by 90 degrees around the z-axis to produce a new vector pointing
	 * upward from the current position.
	 * 
	 * @returns a rotation of the original vector by 90 degrees around the x-axis.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Rotates a `Vector3f` instance by 90 degrees around the Z-axis, resulting in a new
	 * vector pointing downward from the original position.
	 * 
	 * @returns a rotated vector representing the downward direction.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Rotates a vector by 90 degrees to the right based on the current object's orientation.
	 * 
	 * @returns a rotated vector representing the rightward direction of the object.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Rotates a vector by 90 degrees to the left, resulting in a new vector with its
	 * x-axis pointing towards the negative y-axis and its z-axis pointing towards the
	 * negative x-axis.
	 * 
	 * @returns a rotated vector with a magnitude of -1 and a direction that is perpendicular
	 * to the original vector.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Sets the quaternion's x, y, z, and w components to the input values, returning the
	 * modified quaternion object.
	 * 
	 * @param x 3D position of the quaternion along the x-axis.
	 * 
	 * @param y 2D component of the quaternion.
	 * 
	 * @param z 3rd component of the quaternion, which is updated to match the value provided.
	 * 
	 * @param w 4th component of the quaternion, which is used to rotate the object around
	 * its own center of mass.
	 * 
	 * @returns a reference to the same `Quaternion` object, allowing further method calls
	 * on the same instance.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * Converts a set of Euler angles into a quaternion representation, allowing for more
	 * efficient and compact representation of 3D rotations.
	 * 
	 * @param eulerAngles 3D rotation angles (phi, theta, and yota) of a quaternion, which
	 * are used to compute the quaternion representation of the rotation.
	 * 
	 * * `phi`: The angle of rotation around the x-axis.
	 * * `theta`: The angle of rotation around the y-axis.
	 * * `yota`: The angle of rotation around the z-axis.
	 * 
	 * @returns a Quaternion object representing the rotation matrix based on the provided
	 * Euler angles.
	 * 
	 * * The `Quaternion` object has four components: q0, q1, q2, and q3. These represent
	 * the real and imaginary parts of the quaternion, which describe a rotational
	 * transformation in 3D space.
	 * * The quaternion is normalized, meaning that its length is equal to 1. This ensures
	 * that the quaternion can be used to perform rotations in a consistent and predictable
	 * manner.
	 * * The quaternion is derived from the Euler angles phi, theta, and yota through a
	 * rotation matrix multiplication. This allows for efficient and accurate representation
	 * of 3D rotations using quaternions.
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
	 * Sets the quaternion's components based on input arguments and returns the modified
	 * quaternion.
	 * 
	 * @param r 4-element quaternion value that modifies the components of the current quaternion.
	 * 
	 * @returns a reference to the same `Quaternion` instance, unchanged.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * Retrieves the value of the `x` field, which is a floating-point number representing
	 * the horizontal position of an object or element in a graphical user interface.
	 * 
	 * @returns a floating-point value representing the x coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the class instance variable `x` to the argument passed as a float.
	 * 
	 * @param x float value to be assigned to the `x` field of the class instance being
	 * manipulated by the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of the `y` field.
	 * 
	 * @returns a floating-point value representing the height of an object.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the object's `y` field to the provided floating-point value.
	 * 
	 * @param y 2D coordinate of the point to which the method is being applied, and it
	 * is assigned the value passed as argument to the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns the value of the `z` field.
	 * 
	 * @returns the value of the `z` field.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the value of the object's `z` field to the provided float value.
	 * 
	 * @param z 3D coordinate of the object in the `setZ()` function.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Returns the value of the `w` field.
	 * 
	 * @returns a floating-point value representing the variable `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Sets the value of the `w` field of a class instance to the provided float value.
	 * 
	 * @param w 3D position of the object in the scene, which is assigned to the `w` field
	 * of the current instance.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Compares a Quaternion object with another Quaternion object, checking if their
	 * values for `x`, `y`, `z`, and `w` are equal.
	 * 
	 * @param r 4D quaternion to be compared with the current quaternion, and is used to
	 * determine equality.
	 * 
	 * @returns a boolean value indicating whether the given `Quaternion` object is equal
	 * to the current instance.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
