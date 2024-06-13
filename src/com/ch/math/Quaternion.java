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
	 * Calculates the Euclidean length of a vector in 3D space based on its components,
	 * returning the result as a float value.
	 * 
	 * @returns the square root of the sum of the squares of the component vectors.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Normalizes a given quaternion by dividing each component by its magnitude, resulting
	 * in a new quaternion with a length of 1.
	 * 
	 * @returns a normalized quaternion representation of the input quaternion.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Generates a new `Quaternion` object with the conjugate of its input parameters:
	 * `w`, `-x`, `-y`, and `-z`.
	 * 
	 * @returns a new quaternion with the opposite orientation.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Multiplies a quaternion by a scalar value, returning a new quaternion with the
	 * resultant rotation matrix.
	 * 
	 * @param r scalar value multiplied by which the quaternion's components are updated.
	 * 
	 * @returns a quaternion representing the multiplication of the input scalar `r` with
	 * the original quaternion.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Multiplies a quaternion by another quaternion, returning the result as a new
	 * quaternion with the same angle and axis but possibly different magnitude.
	 * 
	 * @param r 3D rotation vector to which the current quaternion is multiplied.
	 * 
	 * @returns a quaternion representing the product of two given quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Takes a `Vector3f` argument `r` and returns a new `Quaternion` object that represents
	 * the multiplication of the quaternion and the vector.
	 * 
	 * @param r 3D vector that is being multiplied with the quaternion.
	 * 
	 * @returns a new `Quaternion` object containing the product of the input vector and
	 * the rotation matrix.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Subtracts the quaternion `r` from the given quaternion, resulting in a new quaternion
	 * representation with the same w, x, y, and z components as the original quaternion.
	 * 
	 * @param r quaternion to which the original quaternion will be subtracted.
	 * 
	 * @returns a quaternion representing the difference between two quaternions.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Takes a quaternion `r` as input and returns a new quaternion with the sum of its
	 * components.
	 * 
	 * @param r 4th component of the quaternion to be added to the current quaternion.
	 * 
	 * @returns a new quaternion with the sum of the inputs' coefficients.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * Computes a rotation matrix based on three orthogonal vectors representing the axis
	 * of rotation and the angle of rotation in radians.
	 * 
	 * @returns a 4x4 rotation matrix representing the rotation defined by the `x`, `y`,
	 * and `z` components of the input vector.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Computes the dot product of a quaternion and another vector, returning a floating-point
	 * value.
	 * 
	 * @param r 4D vector that is dot-producted with the current 4D vector.
	 * 
	 * @returns a float value representing the dot product of the provided quaternion and
	 * the given quaternion.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Interpolates between two Quaternions using the Lanczos extrapolation method. It
	 * takes into account the magnitude and direction of the result, and adjusts the
	 * interpolation factor accordingly to ensure shortest distance or to maintain a
	 * desired orientation. The function returns the interpolated Quaternion normalized
	 * to have a length of 1.
	 * 
	 * @param dest 4-element vector that will be transformed using the lerp method, and
	 * its value is used to compute the corrected destination quaternion.
	 * 
	 * @param lerpFactor 0-1 value of the interpolation between the initial and final
	 * values of the Quaternion object, allowing for smoother and more gradual transitions
	 * during numerical linearization.
	 * 
	 * @param shortest shortest quaternion path to the destination, when the dot product
	 * between the current and final quaternions is negative, the opposite of the destination
	 * quaternion is calculated first before lerping to the final destination quaternion.
	 * 
	 * @returns a Quaternion representation of the linear interpolation between two
	 * provided quaternions.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Calculates a smooth transition between two Quaternion values using linear
	 * interpolation, with adjustments for shortest path and handlinig of angles close
	 * to pi.
	 * 
	 * @param dest 4-component quaternion destiantion point towards which the quaternion
	 * interpolation will be performed.
	 * 
	 * @param lerpFactor 0-1 interpolant used to smoothly blend between the initial and
	 * final quaternion values, allowing for efficient and fluid motion.
	 * 
	 * @param shortest shortest quaternion route to get from the starting Quaternion to
	 * the Destination Quaternion, and it affects how the Lerp method is applied to
	 * calculate the final result.
	 * 
	 * @returns a quaternion that interpolates between the given `dest` quaternion and
	 * the original quaternion, based on the input `lerpFactor` and `shortest` parameter.
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
	 * Rotates a vector by 90 degrees around the x-axis, resulting in a forward vector
	 * pointing towards the negative z-axis.
	 * 
	 * @returns a forward-facing vector in the same dimension as the object it was called
	 * on.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Rotates the current vector by 90 degrees around the x-axis, resulting in a vector
	 * that points towards the origin.
	 * 
	 * @returns a rotated vector of size 3, with components in the range [-1, 1].
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Rotates a `Vector3f` instance by 90 degrees around the z-axis to generate a vector
	 * pointing upwards relative to the current object's orientation.
	 * 
	 * @returns a rotated vector pointing upwards in 3D space.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Rotates a `Vector3f` instance by 90 degrees around the x-axis, resulting in a new
	 * vector pointing downwards from the original position.
	 * 
	 * @returns a rotated vector pointing downwards.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Rotates the current object by 90 degrees around the x-axis, resulting in a vector
	 * pointing directly to the right.
	 * 
	 * @returns a rotation of the input vector by 90 degrees around the x-axis.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Rotates the input vector by 90 degrees counterclockwise, resulting in a vector
	 * pointing leftward.
	 * 
	 * @returns a rotated version of the original vector with its x-coordinate reduced
	 * by 1.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Sets the quaternion's `x`, `y`, `z`, and `w` components to the input values,
	 * returning the updated quaternion instance.
	 * 
	 * @param x 3D position of the quaternion's rotation axis.
	 * 
	 * @param y 2D projection of the quaternion in the XY plane, and it is being assigned
	 * the value of `y`.
	 * 
	 * @param z 3D rotation axis in the xy plane, which is used to rotate the quaternion
	 * around that axis.
	 * 
	 * @param w 4th component of the quaternion, which is used to modify its orientation
	 * in 3D space.
	 * 
	 * @returns a reference to the modified Quaternion object.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * Calculates a quaternion representation of a rotation from a set of Euler angles.
	 * 
	 * @param eulerAngles 3D orientation of an object in Euler angles format, providing
	 * the values of the phi, theta, and yota angles.
	 * 
	 * @returns a Quaternion object representing the rotation defined by the input Euler
	 * angles.
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
	 * Sets the values of a Quaternion object to the corresponding values of another
	 * Quaternion object.
	 * 
	 * @param r 4-dimensional quaternion value to be set for the current quaternion object
	 * instance, and it is passed as a reference to the function to allow modification
	 * of its components.
	 * 
	 * @returns a reference to the same `Quaternion` instance.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * Returns the value of the `x` field.
	 * 
	 * @returns a float value representing the variable 'x'.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the class's `x` field to the input `x` parameter.
	 * 
	 * @param x Float value that sets the value of the `x` field within the `Object`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of `y`.
	 * 
	 * @returns a float value representing the y-coordinate of the point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of its receiver's `y` field to the provided `y` argument.
	 * 
	 * @param y 2D point's y-coordinate and assigns its value to the instance field `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns the value of the `z` field.
	 * 
	 * @returns a `float` value representing the z-coordinate of an object.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the value of the instance field `z` to the argument passed as a float.
	 * 
	 * @param z 3D position of an object along the z-axis when the `setZ()` method is called.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Retrieves the `w` variable's value and returns it as a float.
	 * 
	 * @returns a float value representing the width of the container.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Sets the `w` field of an object to the given `float` value.
	 * 
	 * @param w 3D rotation of an object along the x-axis in radians.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Compares a given quaternion with another provided quaternion, returning `true` if
	 * the values of each component are identical.
	 * 
	 * @param r 4D vector to compare with the current 4D vector, and is used to determine
	 * equality between the two vectors.
	 * 
	 * @returns a boolean value indicating whether two Quaternions are equal.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
