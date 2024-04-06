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
	 * calculates the Euclidean length of a 4D vector by squaring its components and
	 * taking the square root of the result.
	 * 
	 * @returns the square root of the sum of the squares of the function's arguments.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes a given quaternion by dividing its components by their length, resulting
	 * in a unit quaternion.
	 * 
	 * @returns a normalized quaternion representation of the original input quaternion.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * generates a quaternion with the same magnitude and direction as the given quaternion,
	 * but with its polar components inverted.
	 * 
	 * @returns a new quaternion with the same scalar part as the input quaternion, but
	 * with the axis components reversed.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies a quaternion by a scalar value and returns the result as a new quaternion
	 * object.
	 * 
	 * @param r 4D vector to be multiplied with the quaternion.
	 * 
	 * @returns a Quaternion representation of the product of the input value `r` and the
	 * quaternion's coefficients.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * computes the result of multiplying two quaternions by scalar multiplication and
	 * component-wise multiplication, and returns the resulting quaternion.
	 * 
	 * @param r quaternion to be multiplied with the current quaternion, and its values
	 * are used to compute the new quaternion's components.
	 * 
	 * @returns a quaternion representing the product of two quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * computes the product of a quaternion and a vector, returning the resulting quaternion.
	 * 
	 * @param r 3D vector that multiplies with the quaternion.
	 * 
	 * @returns a new Quaternion instance representing the result of multiplying the given
	 * Vector3f by the quaternion.
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
	 * between the input quaternion's values and the current quaternion's values.
	 * 
	 * @param r 4-dimensional vector to be subtracted from the current quaternion.
	 * 
	 * @returns a new quaternion with the difference between the input quaternions' values.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a `Quaternion` object `r` as input and returns a new `Quaternion` object
	 * representing the sum of the two quaternions.
	 * 
	 * @param r 4D quaternion to be added to the current quaternion.
	 * 
	 * @returns a new Quaternion object with the sum of the input quaternions' components.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * converts a quaternion representing a rotation into a 4x4 matrix representation of
	 * that rotation.
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
	 * computes the dot product of a quaternion and another vector.
	 * 
	 * @param r 4D rotational vector that, when multiplied by the function's arguments,
	 * produces the dot product of the two vectors.
	 * 
	 * @returns a floating-point number representing the dot product of the Quaternion
	 * object and the input argument.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a quaternion interpolation between two given quaternions based on the
	 * Lerp formula, optionally using shortest path to avoid going beyond the boundary
	 * of the rotation axis.
	 * 
	 * @param dest 4D vector that will be interpolated towards the output value using the
	 * provided lerp factor.
	 * 
	 * @param lerpFactor 0 to 1 value that determines how much of the destination quaternion
	 * should be interpolated towards the source quaternion during the lerping process.
	 * 
	 * @param shortest 3D vector that results from normalizing the output quaternion when
	 * it has a negative dot product with the destination quaternion, which is used to
	 * avoid producing large jumps in orientation when interpolating between two quaternions.
	 * 
	 * @returns a quaternion that represents the linear interpolation of two given quaternions.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * computes a quaternion interpolation between two given quaternions, based on the
	 * Lerp method, and returns the result. It takes into account the shortest path option
	 * and handles the sign of the cosine angle to ensure proper orientation.
	 * 
	 * @param dest 4-dimensional vector that the resulting quaternion will be closest to,
	 * with the quaternion being computed based on the angle between the original and
	 * dest vectors.
	 * 
	 * @param lerpFactor 0-1 value that determines how quickly the Quaternion will be
	 * interpolated between the starting and ending points.
	 * 
	 * @param shortest shortest path between the two quaternions, which is computed by
	 * flipping the sign of the destination quaternion when the cosine of the dot product
	 * between the two quaternions is negative.
	 * 
	 * @returns a Quaternion representing the interpolated rotation between two given
	 * Quaternions, with a maximum interpolation angle of 180 degrees.
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
	 * rotates the vector `this` by 90 degrees around the x-axis, resulting in a new
	 * vector pointing towards the forward direction.
	 * 
	 * @returns a rotated version of the original vector, pointing in the forward direction.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees around the z-axis, resulting in a vector that points
	 * backward from the original position.
	 * 
	 * @returns a rotated vector with a magnitude of -1 in the opposite direction of the
	 * original vector.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the z-axis, resulting in a
	 * vector pointing upwards from the original position.
	 * 
	 * @returns a rotated vector pointing upwards.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the x-axis, resulting in a new
	 * vector pointing downward from the original position.
	 * 
	 * @returns a rotated version of the original vector, pointing downward.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees to the right, resulting in a new vector
	 * that points in the right direction.
	 * 
	 * @returns a rotated vector representing the right component of the object.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees to the left, resulting in a new vector
	 * that points in the opposite direction from the original vector's start position.
	 * 
	 * @returns a rotated vector with a magnitude of -1 and an angle of 90 degrees relative
	 * to the original vector.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * updates the instance variables `x`, `y`, `z`, and `w` of a `Quaternion` object
	 * with the provided values, and returns the modified object.
	 * 
	 * @param x 3D position of the quaternion in the x-axis direction.
	 * 
	 * @param y 2D projection of the quaternion along the x-axis, which is used to modulate
	 * the overall orientation of the quaternion.
	 * 
	 * @param z 3D position of the quaternion in the x, y, and z axes.
	 * 
	 * @param w 4th component of the quaternion, which is used to specify the orientation
	 * of the quaternion in 3D space.
	 * 
	 * @returns a reference to the modified quaternion object.
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
	 * sets the values of a quaternion's components to those of another quaternion object.
	 * 
	 * @param r 4-dimensional vector that contains the new values for the quaternion's
	 * coordinates, which are then assigned to the corresponding fields of the quaternion
	 * object.
	 * 
	 * @returns a reference to the same `Quaternion` object.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns the value of `x`, a floating-point number representing the horizontal
	 * position of an object.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the object to which it belongs.
	 * 
	 * @param x float value that is assigned to the `x` field of the object instance being
	 * passed to the function, effectively setting the value of the `x` field to the
	 * provided value.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field.
	 * 
	 * @returns the value of the `y` field, which is a floating-point number.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the object's `y` field to the input parameter.
	 * 
	 * @param y 2D coordinate of the point that the function is modifying, specifically
	 * its Y-coordinate.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves and returns the value of the `z` field.
	 * 
	 * @returns a `float` value representing the z-coordinate of a point.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of a class instance field named 'z' to the provided float value.
	 * 
	 * @param z 3D position of an object in space, which is assigned directly to the `z`
	 * field of the `this` instance.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * retrieves the value of `w`, a float variable, and returns it.
	 * 
	 * @returns the value of `w`, which is a float representing the width of the code.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the instance field `w` to the provided float value.
	 * 
	 * @param w 4th coordinate of the point being modified within the class, and its value
	 * is assigned to the `w` field of the class.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares a Quaternion object with another Quaternion object by checking the values
	 * of its x, y, z, and w components.
	 * 
	 * @param r 4D quaternion to be compared with the current quaternion for equality checking.
	 * 
	 * @returns a boolean value indicating whether the provided quaternion is equal to
	 * the current quaternion.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
