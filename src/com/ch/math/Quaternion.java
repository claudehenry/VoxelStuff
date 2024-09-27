package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Represents 3D rotations using quaternions, providing methods for basic operations,
 * conversions, and transformations.
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
	 * Calculates the Euclidean magnitude of a four-dimensional vector.
	 * It returns the square root of the sum of the squares of its components.
	 *
	 * @returns the Euclidean distance of a point in four-dimensional space.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Calculates the length of the quaternion and returns a new quaternion with its
	 * components scaled by the reciprocal of the length, effectively normalizing the quaternion.
	 *
	 * @returns a new Quaternion with components scaled to unit length.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Returns a new Quaternion with the sign of the real component negated and the sign
	 * of the imaginary components preserved.
	 *
	 * @returns a quaternion with the same real component and negated imaginary components.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Multiplies the current quaternion by a scalar value `r`, returning a new quaternion
	 * with each component scaled by `r`.
	 *
	 * @param r scale factor by which the components of the quaternion are multiplied.
	 *
	 * @returns a new Quaternion object scaled by the input float value `r`.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Calculates the product of two quaternions. It multiplies the corresponding components
	 * of the two input quaternions and rearranges them according to the quaternion
	 * multiplication rules.
	 *
	 * @param r right-hand operand in a quaternion multiplication operation.
	 *
	 * @returns a new Quaternion object resulting from the multiplication of the input
	 * Quaternion with the input parameter r.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Performs a quaternion-vector multiplication, combining a quaternion and a 3D vector
	 * to produce a new quaternion representing the rotation of the vector. The quaternion's
	 * components are updated based on the vector's components. The result is a new quaternion.
	 *
	 * @param r vector on which the quaternion multiplication operation is performed.
	 *
	 * @returns a new Quaternion resulting from the multiplication of the current Quaternion
	 * with the input Vector3f.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Performs element-wise subtraction of two quaternions, returning a new quaternion
	 * with components derived from the difference of corresponding components of the
	 * input quaternions.
	 *
	 * @param r right-hand side quaternion in the subtraction operation.
	 *
	 * @returns a new Quaternion representing the difference between the current quaternion
	 * and the input quaternion.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Performs element-wise addition of two quaternions, combining their real and imaginary
	 * parts into a new quaternion.
	 *
	 * @param r Quaternion to be added to the current Quaternion, allowing for element-wise
	 * addition.
	 *
	 * @returns a new Quaternion object with components summing corresponding components
	 * of input Quaternions.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Computes the dot product of two quaternions.
	 * It multiplies corresponding components of the two quaternions and sums the results.
	 *
	 * @param r second quaternion with which the dot product is computed.
	 *
	 * @returns the dot product of the current quaternion and the input quaternion r.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Calculates a normalized linear interpolation between two quaternions based on a
	 * given lerp factor. It adjusts the destination quaternion to ensure the shortest
	 * path if the shortest parameter is true. The result is a normalized quaternion.
	 *
	 * @param dest destination quaternion in a linear interpolation operation.
	 *
	 * @param lerpFactor amount of interpolation between the current quaternion and the
	 * destination quaternion.
	 *
	 * @param shortest flag that determines whether the shortest or the longest path is
	 * used for interpolation between the two quaternions.
	 *
	 * @returns a normalized quaternion that is the result of spherical linear interpolation.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Performs spherical linear interpolation between two quaternions, handling cases
	 * where the quaternions are nearly antipodal. It corrects the destination quaternion
	 * if necessary and then calculates the interpolation factors based on the angle
	 * between the source and destination quaternions.
	 *
	 * @param dest destination quaternion of the spherical linear interpolation.
	 *
	 * Break down `dest` into its components.
	 *
	 * `dest` is a Quaternion with four properties:
	 * - `getW()` - the real part of the quaternion.
	 * - `getX()` - the x-axis imaginary part of the quaternion.
	 * - `getY()` - the y-axis imaginary part of the quaternion.
	 * - `getZ()` - the z-axis imaginary part of the quaternion.
	 *
	 * @param lerpFactor fraction of the distance between the current quaternion and the
	 * destination quaternion that the function traverses.
	 *
	 * @param shortest option to select the shortest path between two quaternions when
	 * the angle between them is greater than 180 degrees.
	 *
	 * @returns a linearly interpolated quaternion between the current state and the
	 * destination state.
	 *
	 * The output is a Quaternion.
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
	 * Returns a new vector representing the forward direction of the object,
	 * obtained by rotating the standard forward vector (0, 0, 1) around the object's orientation.
	 *
	 * @returns a Vector3f representing the direction forward from the current object's
	 * orientation.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Rotates the global back vector (-1, 0, 0) by the object's rotation and returns the
	 * resulting vector.
	 *
	 * @returns a Vector3f representing the back direction from the object's current orientation.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Returns a new Vector3f representing the up direction in the local coordinate system
	 * of the object, which is calculated by rotating the global up vector (0, 1, 0) by
	 * the object's orientation.
	 *
	 * @returns a rotated Vector3f object with a z-axis component.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Returns a Vector3f representing the direction down from the current object's
	 * orientation, obtained by rotating the standard down vector (-1, 0, 0) by the
	 * object's rotation.
	 *
	 * @returns a Vector3f object representing the down direction relative to the input
	 * Vector3f.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Returns a new Vector3f object representing the right direction of the current
	 * object, calculated by rotating the unit vector (1, 0, 0) by the current object's
	 * rotation.
	 *
	 * @returns a rotated Vector3f representing the right direction of the object, based
	 * on its current orientation.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Returns a new `Vector3f` that represents the left direction relative to the current
	 * `Vector3f` instance, obtained by rotating the standard left unit vector (-1, 0,
	 * 0) by the current vector's angle.
	 *
	 * @returns a new Vector3f instance rotated 90 degrees counterclockwise from the
	 * original instance.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Updates the values of a Quaternion object's components `x`, `y`, `z`, and `w` with
	 * the provided parameters and returns the updated object itself.
	 *
	 * @param x x-coordinate of a quaternion and assigns its value to the `x` field of
	 * the Quaternion object.
	 *
	 * @param y y-coordinate of the quaternion, which is a fundamental component of the
	 * quaternion data structure.
	 *
	 * @param z imaginary part of the quaternion, with values typically used to describe
	 * rotations in 3D space.
	 *
	 * @param w imaginary part of the quaternion.
	 *
	 * @returns a Quaternion object with the specified x, y, z, and w components.
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

	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Retrieves and returns the value of the `z` variable, which is of type `float`. The
	 * function is a getter, providing access to the `z` attribute. It returns the current
	 * state of the `z` variable.
	 *
	 * @returns the value of the instance variable `z`, a float.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Assigns a float value to the instance variable `z`, updating its value.
	 *
	 * @param z value to be assigned to the object's `z` field.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Returns the value of the variable `w` as a floating-point number.
	 *
	 * @returns the value of the instance variable `w`, a float.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Updates the value of the instance variable `w` to a specified float value.
	 * It takes a single float parameter `w` and assigns it to the instance variable.
	 * The change is persisted within the object.
	 *
	 * @param w width of a shape or object, which is being set to the specified value.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Determines whether the current Quaternion object is equal to the specified Quaternion
	 * object. It checks for equality by comparing the x, y, z, and w components of the
	 * two objects.
	 *
	 * @param r right-hand side Quaternion object being compared with the current Quaternion
	 * object.
	 *
	 * @returns a boolean value indicating whether two Quaternion objects are identical.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
