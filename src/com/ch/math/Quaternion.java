package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Represents a quaternion mathematical object used to describe rotations in 3D space.
 * It provides various methods for creating, manipulating, and transforming quaternions,
 * including conversion from Euler angles, matrix rotation, and interpolation between
 * two quaternions. Additionally, it supports various operations such as multiplication,
 * addition, and normalization of quaternions.
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
	 * Calculates the Euclidean norm of a four-dimensional vector, represented by its
	 * components `x`, `y`, `z`, and `w`. It returns the square root of the sum of the
	 * squares of these components as a floating-point value. This represents the magnitude
	 * or length of the vector.
	 *
	 * @returns a floating-point value representing the Euclidean distance from the origin.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Computes the normalized version of a quaternion by dividing its components by their
	 * magnitude (length). It returns a new quaternion with normalized values. This
	 * operation is often used to ensure unit quaternions, which are essential for
	 * representing rotations in 3D space.
	 *
	 * @returns a quaternion with components normalized to have unit magnitude.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Returns a new quaternion with the same scalar part and reversed vector parts of
	 * the original quaternion. This operation is used to compute the conjugate of a
	 * quaternion, which is essential for various mathematical operations in quaternion
	 * algebra.
	 *
	 * @returns a new quaternion with negated imaginary components and unchanged real component.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Scales a quaternion by a given floating-point number. It multiplies each component
	 * (w, x, y, and z) of the quaternion with the scalar value `r`, resulting in a new
	 * quaternion with scaled components. The function returns this newly created quaternion.
	 *
	 * @param r scalar value to be multiplied with each component of the quaternion when
	 * scaling it.
	 *
	 * @returns a new Quaternion instance with scaled components.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Multiplies two quaternions and returns the result as a new quaternion object. It
	 * performs a quaternion multiplication, which is used to combine rotations or
	 * transformations. The resulting quaternion represents the composite rotation or transformation.
	 *
	 * @param r 4D vector to be multiplied with the current quaternion, facilitating the
	 * computation of the resulting quaternion product.
	 *
	 * @returns a quaternion resulting from the multiplication of two input quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Multiplies a quaternion with a 3D vector, performing a rotation operation. It
	 * calculates the dot product of the quaternion and vector components to produce new
	 * quaternion components representing the rotated vector. The result is returned as
	 * a new quaternion object.
	 *
	 * @param r 3D vector that is being used to multiply with the current quaternion,
	 * resulting in the calculation of the new quaternion's components.
	 *
	 * @returns a Quaternion object representing the product of the input Vector3f and Quaternion.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Computes the difference between two quaternions by subtracting corresponding
	 * components (w, x, y, z). It returns a new quaternion with the resulting differences.
	 * The input quaternion is modified by accessing its members using get methods.
	 *
	 * @param r 4D vector quaternion to be subtracted from the current quaternion, used
	 * to calculate the resulting quaternion after subtraction.
	 *
	 * @returns a new Quaternion object representing the difference between two Quaternions.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Combines two quaternion values by adding their corresponding components: w, x, y,
	 * and z. It creates a new quaternion object with the sum of the component values
	 * from both input quaternions and returns it. The result is a new quaternion
	 * representing the sum of the original two.
	 *
	 * @param r 4D vector that is added to the current quaternion's corresponding components
	 * to produce the result of the addition operation.
	 *
	 * @returns a new quaternion resulting from adding two input quaternions.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * Computes and returns a rotation matrix from given parameters x, y, and z. It
	 * calculates forward, up, and right vectors based on these parameters and initializes
	 * a new `Matrix4f` object with these vectors to represent the rotation.
	 *
	 * @returns a rotation matrix initialized with provided forward, up, and right vectors.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Calculates the scalar product between two quaternions, represented by their
	 * components x, y, z and w, respectively. It takes another quaternion as input and
	 * returns the dot product result as a floating-point value. This operation is used
	 * to compute the similarity or magnitude of the quaternions.
	 *
	 * @param r 4D vector of another Quaternion object, which is used to compute the dot
	 * product with the current Quaternion's components (x, y, z, w).
	 *
	 * @returns a floating-point value representing the dot product of two Quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Performs spherical linear interpolation between two quaternions, with optional
	 * shortest path optimization. It calculates the target quaternion based on the input
	 * destination and factor, then normalizes the result to ensure proper orientation.
	 *
	 * @param dest 4D vector that defines the destination point in quaternion space for
	 * which to interpolate with respect to the current quaternion.
	 *
	 * @param lerpFactor 3D interpolation factor, used to compute the interpolated
	 * quaternion result by multiplying the difference between the corrected destination
	 * and current quaternions with it.
	 *
	 * @param shortest 3D shortest path, and when set to `true`, it ensures that the
	 * rotation between two quaternions is optimized for the shortest possible distance.
	 *
	 * @returns a Quaternion, normalized and resulting from interpolation with a specified
	 * factor.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Computes a spherical linear interpolation between two quaternions, ensuring that
	 * the result is closest to the destination quaternion if shortest arc is requested.
	 * It uses dot product and trigonometric functions to determine the interpolation factors.
	 *
	 * @param dest 4D vector to which the quaternion is interpolated, which is used to
	 * calculate the dot product and corrected rotation for spherical linear interpolation.
	 *
	 * Dest is a Quaternion object with w, x, y, and z components representing a 4D vector
	 * in a complex plane. The quaternion's dot product with the current instance (this)
	 * is calculated to determine if it needs correction due to its orientation.
	 *
	 * @param lerpFactor 0-to-1 value controlling the interpolation between the current
	 * quaternion and the destination quaternion, with 0 resulting in the original
	 * quaternion and 1 resulting in the destination quaternion.
	 *
	 * @param shortest shortest arc option, which determines whether to return the shortest
	 * or longest possible rotation from the current quaternion to the destination quaternion.
	 *
	 * @returns a Quaternion that interpolates between the current instance and the
	 * destination Quaternion.
	 *
	 * The result is a quaternion that smoothly interpolates between the source and
	 * destination quaternions according to the specified lerpFactor. The quaternion
	 * represents a rotation in 3D space.
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
	 * Creates a new `Vector3f` object with coordinates (0, 0, 1) and rotates it based
	 * on the instance's state. The resulting vector represents the forward direction
	 * relative to the instance.
	 *
	 * @returns a rotated `Vector3f` object with x and y components set to zero.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Creates a new `Vector3f` object initialized with values (0, 0, -1) and then rotates
	 * it by the instance's rotation, returning the resulting vector representing the
	 * direction behind the instance.
	 *
	 * @returns a new `Vector3f` object rotated from (0, 0, -1) by the current object's
	 * rotation.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Returns a new instance of `Vector3f`, rotated around the origin by the amount
	 * specified in the `this` object. The rotation is around the Y-axis, as indicated
	 * by the input vector (0, 1, 0), which represents a unit vector pointing up.
	 *
	 * @returns a new `Vector3f` object with x and z components zeroed.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Returns a new instance of `Vector3f` with components (0, -1, 0) rotated around the
	 * origin by the angle defined in the `this` object. The result is a vector pointing
	 * downwards from the current orientation.
	 *
	 * @returns a rotated `Vector3f` object with its y-component negated.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Returns a new `Vector3f` object that represents the right direction vector after
	 * rotating the standard right direction vector (1, 0, 0) by an angle specified by
	 * the `this` object.
	 *
	 * @returns a rotated `Vector3f` object with x-coordinate set to 1.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Rotates a vector by a given angle and returns a new `Vector3f` object representing
	 * the left direction from the original point. The rotation is applied around the
	 * origin (0,0,0) and results in a new vector pointing to the left (-1,0,0).
	 *
	 * @returns a rotated vector with x-coordinate -1 and y- and z-coordinates set to
	 * this object's values.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Initializes a Quaternion with specified float values for x, y, z, and w components,
	 * assigning them to corresponding instance variables. The function returns the current
	 * object reference, allowing method chaining.
	 *
	 * @param x 1st component of a quaternion and is assigned to the instance variable `this.x`.
	 *
	 * @param y 2nd component of the quaternion, which is assigned to the instance variable
	 * `this.y`.
	 *
	 * @param z 3rd component of a quaternion, which is set as the value of the `z` field
	 * within the object.
	 *
	 * @param w 4th component of a Quaternion, which is assigned to the instance variable
	 * `this.w`.
	 *
	 * @returns an instance of the class, with updated values.
	 */
	public Quaternion set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	/**
	 * Converts a vector of Euler angles (phi, theta, yota) into a quaternion representation.
	 * It calculates intermediate values for cosine and sine of half-angles to reduce
	 * calculation overhead, then uses these values to compute the quaternion's components
	 * (q0, q1, q2, q3).
	 *
	 * @param eulerAngles 3D Euler angles (phi, theta, yota) that define the rotation of
	 * an object.
	 *
	 * Dissected into its components, `eulerAngles` is a vector of three floating-point
	 * numbers representing Euler angles in degrees, specifically [phi, theta, yota].
	 *
	 * @returns a Quaternion object representing a rotation from Euler angles.
	 *
	 * A Quaternion is a 4-dimensional vector consisting of four elements q0, q1, q2, and
	 * q3 representing rotations in 3D space. The returned Quaternion has these attributes.
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
	 * Sets the values of a Quaternion object based on another Quaternion object, using
	 * its x, y, z, and w components. The function then returns itself. This allows for
	 * method chaining by calling multiple methods on the same object without having to
	 * create intermediate variables.
	 *
	 * @param r 4D vector of a Quaternion object that is being set into the current
	 * Quaternion instance.
	 *
	 * @returns an instance of itself with updated quaternion values.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * Returns a floating-point value representing the value of `x`. This method appears
	 * to be a getter for the private variable `x`, allowing access to its value from
	 * outside the class. The returned value is read-only, as it does not modify the
	 * original value.
	 *
	 * @returns a floating-point number representing the value of variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Updates the value of an instance variable `x` to a new float value provided as an
	 * argument. The updated value is stored in the object's internal state, replacing
	 * any previous value. This allows external code to modify the object's `x` property.
	 *
	 * @param x value to be assigned to the instance variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns a floating-point value representing the current state of variable `y`.
	 * This method does not perform any calculations or modifications, but simply retrieves
	 * and exposes the existing value of `y`. The returned value is used by external code
	 * to access or utilize the stored y-coordinate.
	 *
	 * @returns a floating-point number representing the value of the variable `y`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates a floating-point value for a variable `y`. It assigns the provided `float`
	 * parameter to the instance variable `this.y`, effectively changing its value. This
	 * method does not return any output, it only modifies the internal state of the object.
	 *
	 * @param y vertical coordinate of an object and updates its internal state by assigning
	 * it to the instance variable `this.y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Retrieves a floating-point value named `z`. The value is returned directly without
	 * any modifications or computations. The purpose of this function appears to be to
	 * provide access to the private variable `z`, allowing external code to read its
	 * current state.
	 *
	 * @returns a floating-point value representing the variable `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Assigns a floating-point value to the instance variable `z`. The value is provided
	 * as an argument to the method and is stored directly into the `z` field. This allows
	 * the object's state to be updated dynamically.
	 *
	 * @param z 3D coordinate value that is assigned to the instance variable `this.z`.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Returns a floating-point value stored in the variable `w`. The returned value is
	 * accessible to the caller and can be used as needed. This function provides read-only
	 * access to the private variable `w`.
	 *
	 * @returns a floating-point value representing the variable `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Assigns a new value to the instance variable `w`. This value is provided as an
	 * argument and becomes the new state of the object's attribute `w`. The assignment
	 * operation updates the internal representation of the object with the new value.
	 *
	 * @param w width value to be assigned to an object's `w` field, which is stored and
	 * persisted for future reference.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Compares two Quaternion objects for equality by checking if their respective x,
	 * y, z, and w components are identical.
	 *
	 * @param r 4D quaternion object to be compared with the current object for equality.
	 *
	 * @returns a boolean indicating whether two Quaternion objects are identical.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
