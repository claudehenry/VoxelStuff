package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Represents a mathematical object used to describe 3D rotations in computer graphics
 * and game development. It provides various methods for performing quaternion
 * operations such as multiplication, normalization, conjugation, and conversion from
 * Euler angles. The class also includes getter and setter methods for accessing and
 * modifying the quaternion's components.
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
	 * Calculates and returns the Euclidean norm, or magnitude, of a four-dimensional
	 * vector represented by the variables `x`, `y`, `z`, and `w`. It uses the Pythagorean
	 * theorem to combine the squares of each component, then takes the square root of
	 * the result.
	 *
	 * @returns a floating-point value representing the Euclidean distance from origin.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Computes a new quaternion by dividing each component (w, x, y, and z) of the input
	 * quaternion by its magnitude (length). This results in a quaternion with unit length,
	 * often used for normalization or to ensure rotation matrices are orthonormal.
	 *
	 * @returns a new quaternion with its magnitude normalized to 1.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Returns a quaternion with the imaginary parts negated. It takes the original
	 * quaternion's real part and flips the signs of its imaginary components. The resulting
	 * quaternion is the complex conjugate of the input quaternion.
	 *
	 * @returns a new Quaternion object with reversed imaginary components.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Multiplies a given float value with each component (w, x, y, and z) of a quaternion
	 * object and returns a new quaternion object with the resulting values.
	 *
	 * @param r scalar value by which each component of the quaternion (w, x, y, and z)
	 * is multiplied to produce the result of the multiplication operation.
	 *
	 * @returns a new `Quaternion` object with scaled components.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Multiplies two quaternions element-wise, following the Hamilton product rule for
	 * quaternion multiplication. It calculates the product of two quaternions and returns
	 * a new quaternion as the result. The product is represented by four floating-point
	 * numbers: w, x, y, z.
	 *
	 * @param r 4D vector (w, x, y, z) of another quaternion that is multiplied with the
	 * current quaternion.
	 *
	 * @returns a new quaternion representing the result of multiplying two quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Multiplies a quaternion by a 3D vector, performing a rotation operation on the
	 * vector. It calculates the resulting components of the rotated vector based on the
	 * input quaternion and returns a new quaternion with the updated values.
	 *
	 * @param r 3D vector to be multiplied with the quaternion, and its coordinates are
	 * used to calculate the resulting quaternion components.
	 *
	 * @returns a new quaternion representing the product of an input quaternion and a vector.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Calculates the difference between two quaternions, subtracting corresponding
	 * components (w, x, y, and z). It returns a new quaternion object representing the
	 * result of the subtraction operation.
	 *
	 * @param r 4D vector to be subtracted from the current Quaternion instance's components
	 * (w, x, y, and z).
	 *
	 * @returns a Quaternion object resulting from subtracting another Quaternion from
	 * the current one.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Combines two quaternion objects by adding their corresponding components, namely
	 * w, x, y, and z, and returns a new quaternion object with the resulting values.
	 *
	 * @param r 4D vector of another quaternion, which is added to the current quaternion's
	 * components.
	 *
	 * @returns a new quaternion resulting from adding the input quaternion to the current
	 * one.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * Calculates a rotation matrix from three vectors: forward, up, and right. These
	 * vectors are derived from input values x, y, w, and z using various trigonometric
	 * formulas. The function then initializes a new `Matrix4f` object with the calculated
	 * rotation information.
	 *
	 * @returns a rotation matrix represented as a `Matrix4f`.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Computes the dot product of a quaternion and another given quaternion. It multiplies
	 * corresponding elements of both quaternions (x, y, z, w) and returns their sum as
	 * a floating-point value.
	 *
	 * @param r 4D vector of another Quaternion object, whose components are used to
	 * compute the dot product with the current Quaternion's components.
	 *
	 * @returns a floating-point value representing the dot product of two quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Interpolates between a source quaternion and a destination quaternion by calculating
	 * the shortest rotation from the source to the destination, considering the dot
	 * product of the quaternions. It takes into account whether the shortest path is
	 * required and normalizes the result.
	 *
	 * @param dest 4D vector towards which the quaternion is interpolated, with its sign
	 * determining the direction of interpolation and its magnitude influencing the rate
	 * of interpolation.
	 *
	 * @param lerpFactor 0-to-1 factor that controls the interpolation between the current
	 * quaternion and the destination quaternion.
	 *
	 * @param shortest 3D vector of the dot product of two quaternion, which is used to
	 * determine if it should be negated for calculating the shortest path to destination.
	 *
	 * @returns a normalized Quaternion resulting from lerping to the destination.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Interpolates between two quaternions, `this` and `dest`, using spherical linear
	 * interpolation (Slerp). It returns a quaternion that represents the intermediate
	 * state, taking into account whether to use the shortest or longest path.
	 *
	 * @param dest 3D rotation to be interpolated towards, serving as the target quaternion
	 * for the spherical linear interpolation process.
	 *
	 * Dest has a quaternion structure with four components - W, X, Y and Z. Its dot
	 * product with the current object (this) is calculated. If shortest arc is required,
	 * its components are negated accordingly.
	 *
	 * @param lerpFactor 0-to-1 value that determines the proportion of the destination
	 * quaternion to blend with the current quaternion during the linear interpolation process.
	 *
	 * @param shortest flag that determines whether to adjust the destination quaternion
	 * for the shortest rotation path, if necessary, based on its cosine value with respect
	 * to the current quaternion.
	 *
	 * @returns a Quaternion that smoothly interpolates between the source and destination.
	 *
	 * The output is an instance of `Quaternion`, which represents a 4D vector with w,
	 * x, y, and z components. The magnitude or length of this quaternion vector can be
	 * calculated as the square root of the sum of the squares of its components.
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
	 * Returns a new `Vector3f` object representing the forward direction after rotation
	 * by the instance it belongs to. The vector (0, 0, 1) is rotated using the instance
	 * as the rotation center.
	 *
	 * @returns a new `Vector3f` object rotated based on the input.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Returns a new `Vector3f` object representing the direction behind an entity. The
	 * vector is created by rotating a default vector pointing towards negative z-axis
	 * around the entity's orientation, represented by the `this` parameter.
	 *
	 * @returns a rotated `Vector3f` object with x and y coordinates as zero.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Rotates a predefined vector (0, 1, 0) by a specified angle using the object `this`,
	 * and returns the resulting vector as a new `Vector3f`. This represents the upward
	 * direction in a three-dimensional space. The rotation is applied around an arbitrary
	 * axis defined by `this`.
	 *
	 * @returns a rotated vector (0, 1, 0) based on the input object's orientation.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Creates a new `Vector3f` object with coordinates (0, -1, 0) and rotates it by the
	 * instance's transformation matrix before returning the result. The rotation is
	 * applied around the origin to produce a vector pointing downwards from the instance's
	 * position.
	 *
	 * @returns a rotated vector pointing down (0, -1, 0) relative to the object's orientation.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Returns a new `Vector3f` object created by rotating the vector (1, 0, 0) around
	 * the origin by the angle specified in the `this` object. The result is a vector
	 * representing the right direction of the coordinate system defined by `this`.
	 *
	 * @returns a rotated vector with its x-axis pointing right.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Returns a new `Vector3f` object that is the result of rotating a fixed vector (-1,
	 * 0, 0) around the origin by an amount specified by the current instance. The rotation
	 * is applied using the `rotate` method, which takes this instance as its argument.
	 *
	 * @returns a rotated vector with negative x-axis component and zero y- and z-axis components.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Initializes a Quaternion object with specified values for its components (x, y,
	 * z, and w) and returns the same object instance. It updates the internal state of
	 * the object by assigning the given float values to the corresponding properties.
	 *
	 * @param x 1st component of a quaternion, which is assigned to the instance variable
	 * `this.x`.
	 *
	 * @param y 2nd component of a quaternion and is assigned to the instance variable `this.y`.
	 *
	 * @param z 3D axis of the quaternion and is assigned to the instance variable `this.z`.
	 *
	 * @param w 4th component of a quaternion, which is assigned to the instance variable
	 * `this.w`.
	 *
	 * @returns an instance of Quaternion with updated values for x, y, z, and w.
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
	 * Modifies the current quaternion instance by updating its components with those
	 * from a provided `Quaternion` object, and returns the updated instance for method
	 * chaining.
	 *
	 * @param r 3D rotation to be applied, from which the x, y, z, and w components are
	 * extracted and used to set the corresponding values of the current Quaternion object.
	 *
	 * @returns a reference to the modified `Quaternion` object itself.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * Returns a floating-point value representing the value of variable `x`. It simply
	 * retrieves and provides access to the current state of `x`, without modifying it.
	 * The returned value is used by other parts of the program as needed.
	 *
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a floating-point value to an instance variable named `x`. The input `float`
	 * parameter is used to update the internal state of the object, effectively changing
	 * its value. This method sets the x-coordinate or attribute of the object.
	 *
	 * @param x value to be assigned to the instance variable `x` of the class, updating
	 * its current value.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns a floating-point value representing the y-coordinate. It simply retrieves
	 * and outputs the value of the variable `y`. This method does not perform any
	 * calculations or modifications, but rather provides direct access to the stored value.
	 *
	 * @returns a floating-point value representing the attribute `y`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates the value of a float variable named `y`. It takes a single float parameter
	 * and assigns it to the object's instance field with the same name. This allows
	 * external code to modify the internal state of the object.
	 *
	 * @param y vertical coordinate to be assigned to the object, and its value is stored
	 * as an instance variable of the class.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Retrieves a floating-point value representing the value of the variable `z`. It
	 * does not perform any calculations or modifications, instead, it simply returns the
	 * existing value. The returned value is stored as a float type and can be used
	 * elsewhere in the program.
	 *
	 * @returns a floating-point value representing the variable `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Updates the value of a floating-point variable `z`. The new value is assigned to
	 * the existing instance variable `this.z`, effectively modifying its state. This
	 * allows external code to alter the internal state of the object.
	 *
	 * @param z 3D coordinate value that is assigned to the instance variable `z`.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Retrieves and returns a floating-point value named `w`. It appears to be a getter
	 * method, allowing access to the private variable `w` from outside the class. This
	 * enables other parts of the program to use or display the value stored in `w`.
	 *
	 * @returns a floating-point value representing the variable `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Assigns a value to the instance variable `w`. It takes a float parameter `w`, which
	 * is used to initialize or update the value of the `w` variable. The updated value
	 * becomes accessible through subsequent references to the object's `w` property.
	 *
	 * @param w width value to be assigned to an instance variable of the same name within
	 * the class.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Compares two Quaternion objects for equality by checking if their respective
	 * components (x, y, z, and w) are equal. It returns a boolean value indicating whether
	 * the Quaternions are identical or not.
	 *
	 * @param r quaternion to be compared with the current object for equality.
	 *
	 * @returns a boolean indicating whether two Quaternions are identical.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
