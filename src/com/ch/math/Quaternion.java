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
	 * calculates the Euclidean distance of a vector using the formula (x^2 + y^2 + z^2
	 * + w^2)^0.5
	 * 
	 * @returns the square root of the sum of the squares of the coordinates of a vector.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * normalizes a given quaternion by dividing each component by its length, resulting
	 * in a new quaternion with a length of 1.
	 * 
	 * @returns a normalized quaternion representation of the original input quaternion.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * generates a new quaternion with the same scalar component values as the input
	 * quaternion, but with the x, y, and z components reversed.
	 * 
	 * @returns a new quaternion with the conjugate of the input quaternion's scalar
	 * component and the negative of its vector components.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * multiplies the quaternion's components by a scalar value `r`.
	 * 
	 * @param r scalar value that multiplies the quaternion's components.
	 * 
	 * @returns a new quaternion instance with the product of the input scalar `r` and
	 * the components of the original quaternion.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * computes the product of two quaternions and returns the result as a new quaternion.
	 * 
	 * @param r 4D quaternion to be multiplied with the current quaternion, resulting in
	 * a new quaternion that represents the combined rotation and scale.
	 * 
	 * @returns a new quaternion instance representing the product of the input quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * multiplies a quaternion by a vector, resulting in a new quaternion representing
	 * the same rotation as the original one, but translated by the vector's distance
	 * from the origin.
	 * 
	 * @param r 3D vector that the quaternion is multiplied by, resulting in a new
	 * quaternion representation of the rotation.
	 * 
	 * @returns a new `Quaternion` instance representing the result of multiplying the
	 * given `Vector3f` by the quaternion.
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
	 * between the original quaternion's components and the input quaternion's components.
	 * 
	 * @param r 4D quaternion to be subtracted from the current quaternion.
	 * 
	 * @returns a new Quaternion representing the difference between the input Quaternion
	 * and the reference Quaternion.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a Quaternion `r` as input and returns a new Quaternion with the sum of its
	 * elements.
	 * 
	 * @param r 4D vector to be added to the current quaternion.
	 * 
	 * @returns a new Quaternion object representing the sum of the input Quaternions.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * converts a quaternion representation of a rotation into a 4x4 homogeneous matrix
	 * representation of the same rotation.
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
	 * computes the dot product of two quaternions, returning a floating-point value
	 * representing the amount of linear alignment between them.
	 * 
	 * @param r 4D quaternion to which the `x`, `y`, `z`, and `w` components of the current
	 * quaternion are multiplied.
	 * 
	 * @returns a scalar value representing the dot product of two quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * computes a smooth interpolation of a Quaternion value along a specified direction,
	 * based on the given lerp factor and shortest path option.
	 * 
	 * @param dest 4D vector that will be modified by the LERP operation.
	 * 
	 * @param lerpFactor 0-to-1 value that determines how much the destination quaternion
	 * is interpolated towards the source quaternion during linear interpolation.
	 * 
	 * @param shortest 4th input parameter, and when `true`, it reverses the sign of the
	 * Quaternion if its dot product with the destination Quaternion is negative.
	 * 
	 * @returns a Quaternion that represents the interpolation of two given Quaternions
	 * along the shortest path.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * computes a smooth interpolation between two quaternions based on the linear
	 * interpolant formula, taking into account shortest path when applicable.
	 * 
	 * @param dest 4-component quaternion that the returned quaternion will be a linear
	 * combination of, with the coefficients determined by the `lerpFactor` and `shortest`
	 * parameters.
	 * 
	 * @param lerpFactor 0-1 value that determines how quickly the starting quaternion
	 * is changed to the destination quaternion during the spherical linear interpolation
	 * process.
	 * 
	 * @param shortest shortest quaternion path, which returns the quaternion that minimizes
	 * the angle between the starting and ending quaternions.
	 * 
	 * @returns a Quaternion that interpolates between the given `dest` Quaternion and
	 * the original input Quaternion based on the provided `lerpFactor`.
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
	 * rotates the provided object by 90 degrees along the z-axis and returns the resulting
	 * vector as a `Vector3f`.
	 * 
	 * @returns a vector pointing forward from the object's position.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` object by 90 degrees around the z-axis, effectively moving
	 * it backward along the negative z-axis.
	 * 
	 * @returns a rotated version of the original vector, with its z-component shifted
	 * to -1.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the x-axis, resulting in a
	 * vector pointing upwards from the original position.
	 * 
	 * @returns a rotation of the original vector in the x-y plane, resulting in a vector
	 * pointing upwards.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * rotates a `Vector3f` instance by 90 degrees around the z-axis, resulting in a new
	 * vector pointing downward from the original position.
	 * 
	 * @returns a rotated vector with an x-axis value of 0, a y-axis value of -1, and a
	 * z-axis value of 0.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees to the right, based on the object's orientation.
	 * 
	 * @returns a rotated vector representing the right component of the original object.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * rotates a vector by 90 degrees counterclockwise around the x-axis to produce a new
	 * vector pointing leftward from the original vector's starting point.
	 * 
	 * @returns a rotated vector with a magnitude of -1 and all other components set to
	 * 0.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * sets the quaternion's x, y, z, and w components to the input values, returning the
	 * modified quaternion object.
	 * 
	 * @param x 3D position of the quaternion along the x-axis.
	 * 
	 * @param y 2D projection of the quaternion in the Y-plane.
	 * 
	 * @param z 3rd component of the quaternion and is set to the value passed as an
	 * argument to the function.
	 * 
	 * @param w 4th component of the quaternion, which is used to specify the final
	 * rotation angle of the object in 3D space.
	 * 
	 * @returns a reference to the modified `Quaternion` object.
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
	 * sets the components of a quaternion to the corresponding values of another quaternion
	 * passed as argument.
	 * 
	 * @param r 4D quaternion value that contains the new values for the `x`, `y`, `z`,
	 * and `w` components of the `set()` method.
	 * 
	 * @returns a reference to the same `Quaternion` object, unchanged.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns a floating-point value representing the X coordinate of an object.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the instance field `x` to the argument passed as a float.
	 * 
	 * @param x new value of the instance field `x` in the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the variable `y`.
	 * 
	 * @returns the value of the `y` field, which is a floating-point number representing
	 * the position of an object in the vertical direction.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the instance variable `y` to the input parameter `y`.
	 * 
	 * @param y 2D coordinate of a point in the graph, and by assigning a new value to
	 * it within the function, the position of the point is updated.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * returns the value of the variable `z`.
	 * 
	 * @returns a float value representing the z-coordinate of a point.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the `z` field of an object to the input parameter passed.
	 * 
	 * @param z 3D position of an object in the X, Y, and Z dimensions when the `setZ()`
	 * method is called.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * returns the value of the variable `w`.
	 * 
	 * @returns the value of `w`, which is a floating-point number representing the width
	 * of a rectangle.
	 */
	public float getW() {
		return w;
	}

	/**
	 * sets the object's `w` field to the input `float` value.
	 * 
	 * @param w 2D coordinate of the center of an ellipse, which is used to calculate the
	 * size and shape of the ellipse when it is set as the value of this function.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * compares a `Quaternion` object with another given quaternion, returning `true` if
	 * all components are equal, and `false` otherwise.
	 * 
	 * @param r 4D vector to be compared with the current 4D vector.
	 * 
	 * @returns a boolean value indicating whether the given quaternion is equal to the
	 * current quaternion.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
