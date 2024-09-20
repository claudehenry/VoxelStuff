package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Represents a mathematical entity used to describe rotations in 3D space, providing
 * methods for quaternion creation, manipulation, and conversion between quaternions,
 * matrices, and Euler angles. It offers various operations such as scalar multiplication,
 * addition, subtraction, and conjugation. The class also includes methods for
 * converting quaternions to rotation matrices and vice versa.
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
	 * Calculates the Euclidean norm or magnitude of a vector. It returns the square root
	 * of the sum of squares of its components, i.e., x, y, z and w. The result is cast
	 * to float for precision.
	 *
	 * @returns the Euclidean magnitude of a 4-dimensional vector.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Computes the length of a quaternion and returns a new normalized quaternion with
	 * components scaled by the reciprocal of its length. This effectively normalizes the
	 * input quaternion to have unit length while preserving its direction. The original
	 * quaternion is unchanged.
	 *
	 * @returns a quaternion with components scaled to unit length.
	 * Its components are normalized values of the original w, x, y, and z values.
	 * The result has a magnitude of 1.0f.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Returns a quaternion that is the conjugate of the current instance, obtained by
	 * negating its non-real components (`x`, `y`, and `z`). The resulting quaternion has
	 * the same real part and an inverted imaginary part. The function creates a new
	 * quaternion object with these values.
	 *
	 * @returns a quaternion with negated x, y, and z components.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Multiplies a quaternion by a scalar value, returning a new quaternion with each
	 * component scaled by the given factor. The original quaternion's components are
	 * modified by multiplying them with the specified float value r. A new quaternion
	 * is created and returned.
	 *
	 * @param r scalar value to be multiplied with each component of the current quaternion.
	 *
	 * Scales each component of the quaternion by the given factor.
	 *
	 * @returns a new Quaternion instance with scalar and vector components scaled by the
	 * input parameter `r`.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Computes the product of two quaternions, represented as instances of the Quaternion
	 * class, and returns a new quaternion instance resulting from the multiplication
	 * operation. The product is calculated using the standard formula for quaternion multiplication.
	 *
	 * @param r quaternion to be multiplied by the current quaternion, whose result is
	 * computed and returned as a new quaternion object.
	 *
	 * @returns a new quaternion resulting from the multiplication of two quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Performs a quaternion-vector multiplication, resulting in a new quaternion with
	 * components calculated from the input vector and the current quaternion's components.
	 * It uses Hamilton product rules for quaternions and vectors to compute the products.
	 * The result is a new quaternion representing the rotation applied to the original
	 * quaternion.
	 *
	 * @param r 3D vector with which to multiply the quaternion, used for scalar
	 * multiplication and cross product calculations.
	 *
	 * @returns a new quaternion resulting from the multiplication of the current quaternion
	 * with the given vector.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Subtracts two quaternions, element-wise. It takes another quaternion as an argument
	 * and returns a new quaternion with components w, x, y, and z calculated by subtracting
	 * corresponding components of the input quaternion from its own components. The
	 * result is a new quaternion representing the difference between the two input quaternions.
	 *
	 * @param r 3D vector to be subtracted from another Quaternion's components, w, x,
	 * y, and z.
	 *
	 * @returns a new quaternion resulting from element-wise subtraction of input quaternions.
	 * Resulting components are differences between corresponding w, x, y, and z values.
	 * Subtraction is performed componentwise on each value.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Adds two quaternions together element-wise, combining corresponding elements from
	 * both quaternions to produce a new quaternion. This is achieved by creating a new
	 * quaternion instance with the sum of each component. The result is a new quaternion
	 * representing the sum of the input quaternions.
	 *
	 * @param r 4D vector components of another quaternion to be added to the current
	 * quaternion's components.
	 *
	 * @returns a new Quaternion with added components.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * Computes a rotation matrix from a quaternion. The input quaternion is assumed to
	 * be in the form (x, y, z, w). The function calculates the forward, up, and right
	 * vectors based on the quaternion components and creates a new rotation matrix with
	 * these vectors.
	 *
	 * @returns a rotation matrix representing a 3D rotation from a quaternion.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Computes the dot product of two quaternions by multiplying corresponding components
	 * and summing them together, returning a scalar result representing the similarity
	 * or magnitude between the two input quaternions. The operation is element-wise
	 * multiplication followed by addition. It produces a float value.
	 *
	 * @param r 4D vector to be dotted with the current quaternion's components, accessed
	 * through its getter methods.
	 *
	 * @returns a scalar value representing the dot product of two Quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Interpolates between two quaternions, effectively performing a non-linear interpolation
	 * (spherical linear interpolation) with a given factor and optional shortest-path
	 * correction. It calculates a intermediate quaternion based on the interpolated
	 * vector between the current quaternion and the destination quaternion. The result
	 * is then normalized.
	 *
	 * @param dest 4D quaternion that defines the target or destination rotation, which
	 * is used to perform spherical linear interpolation with the current instance's quaternion.
	 *
	 * @param lerpFactor 3D linear interpolation factor that determines the degree to
	 * which the current quaternion is interpolated towards the destination quaternion.
	 *
	 * @param shortest option to return the shortest arc between two rotations, which is
	 * achieved by reflecting the target rotation if the current rotation and target
	 * rotation have opposite directions.
	 *
	 * @returns a normalized Quaternion representing the result of natural LERP interpolation
	 * between two Quaternions.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Interpolates between two quaternions using spherical linear interpolation, ensuring
	 * a smooth rotation. It adjusts the destination quaternion based on whether shortest
	 * or longest path is preferred and handles cases where the quaternions are nearly
	 * antipodal. The result is a quaternion representing an intermediate rotation.
	 *
	 * @param dest destination quaternion towards which a smooth rotation is interpolated
	 * from the current object's quaternion.
	 *
	 * Dest has four components: w, x, y, z, which are accessed via methods getW(), getX(),
	 * getY(), and getZ() respectively.
	 *
	 * @param lerpFactor 0 to 1 interpolation factor that determines the proportion of
	 * the destination quaternion's rotation to blend into the source quaternion.
	 *
	 * @param shortest shortest path option for quaternion spherical lerp, swapping the
	 * target quaternion if necessary to ensure a shorter rotation arc when its dot product
	 * with the current quaternion is negative.
	 *
	 * @returns a quaternion interpolated between two input quaternions.
	 *
	 * The returned Quaternion is a weighted average of the input Quaternions `this` and
	 * `dest`, with weights determined by the lerpFactor and shortest flag. The resulting
	 * Quaternion has magnitude 1 and is on the great circle between the two input Quaternions.
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
	 * Returns a new rotation of the unit vector (0, 0, 1) based on the current object's
	 * orientation.
	 * It accomplishes this by applying the object's transformation to the unit vector
	 * through the `rotate` method.
	 * A Vector3f representing the forward direction in the object's coordinate system
	 * is returned.
	 *
	 * @returns a rotated vector pointing forward from the object's local space.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Returns a vector that represents the direction opposite to the current object's
	 * orientation, taking into account its rotation matrix. It does so by creating a new
	 * vector (0, 0, -1) and applying the object's rotation to it using the `rotate` method.
	 *
	 * @returns a rotated version of the unit vector (-1, 0, 0) relative to the instance's
	 * orientation.
	 * This vector represents the back direction from the object's perspective.
	 * Its direction is opposite to the x-axis in local space.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Returns a vector representing the local up direction for an object defined by this
	 * instance, calculated based on its rotation and an initial upward vector (0, 1, 0).
	 * The result is a new Vector3f object with the rotated coordinates. Rotation is
	 * performed using another object's transformation properties.
	 *
	 * @returns a rotated `Vector3f` object representing the vector (0, 1, 0) relative
	 * to the instance's orientation.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Returns a new vector pointing downward from the current orientation, calculated
	 * by rotating a unit vector (0, -1, 0) around the current rotation. The result is a
	 * vector that represents the downward direction relative to the object's current
	 * position and orientation. It returns a Vector3f object.
	 *
	 * @returns a rotated `Vector3f` with the z-axis remaining unchanged.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Returns a Vector3f instance representing the local right axis of an object, relative
	 * to its current rotation state.
	 * This is achieved by rotating the global unit vector along the X-axis (1,0,0) around
	 * the object's origin.
	 * The result is the object's local X-axis as a new vector.
	 *
	 * @returns a normalized vector in the direction to the right of the object.
	 * It is orthogonal to the object's forward and up vectors.
	 * Its magnitude is 1.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Returns a new vector by rotating the default left unit vector (-1, 0, 0) around
	 * the current object's rotation, resulting in a vector representing the direction
	 * to the left relative to the object's orientation. The returned vector is of type
	 * Vector3f.
	 *
	 * @returns a Vector3f rotated to the left of its original orientation.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Updates the values of a Quaternion object's components with new float values for
	 * x, y, z, and w coordinates. It assigns these new values to the corresponding
	 * instance variables. The function returns the updated Quaternion object itself.
	 *
	 * @param x 3D space component of the quaternion and sets it to the value passed as
	 * an argument.
	 *
	 * @param y 2nd component of the quaternion, which is used to define its rotation or
	 * transformation properties.
	 *
	 * @param z 3D coordinate on the z-axis of a quaternion, typically used to store
	 * rotation or transformation data in three-dimensional space.
	 *
	 * @param w 4th component of the quaternion, which is often used to represent the
	 * scalar or imaginary part.
	 *
	 * @returns a reference to the updated instance of Quaternion.
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
	 * Initializes the quaternion object with values from another quaternion object. It
	 * sets the x, y, z, and w components using the provided setter method. The function
	 * returns a reference to the current object for chaining.
	 *
	 * @param r 4D vector (quaternion) whose components are copied into the current object,
	 * modifying its state.
	 *
	 * @returns a reference to the modified instance of Quaternion.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * Returns a floating-point value representing a coordinate or position along the
	 * x-axis, likely as part of a larger geometric or spatial context. The function
	 * provides read-only access to a private member variable named `x`. Its return type
	 * is a primitive `float`.
	 *
	 * @returns a floating-point value representing the current state of field `x`. The
	 * exact value depends on its initialization. It returns the private instance variable
	 * `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a new value to an instance variable named `x`, which is presumably of type
	 * float, updating its current state with the provided input value. This updates the
	 * attribute's state directly without any validation or processing beyond assignment.
	 * The change persists in subsequent program execution.
	 *
	 * @param x 2D coordinate's X-axis value to be assigned to an object's internal state
	 * variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the current value of a private field named `y`, which is presumably an
	 * instance variable of a class, allowing external access to its value for various
	 * purposes such as calculations or visualization. The return type is float, indicating
	 * a decimal number. This is a getter method.
	 *
	 * @returns the current value of a private member variable `y`, represented as a
	 * floating-point number.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates the value of a floating-point field named `y`. The field can be accessed
	 * via an instance of its containing class, allowing modification of the object's
	 * state. This method does not perform any validation or checks on the input value.
	 *
	 * @param y vertical coordinate that is being assigned to an instance variable or
	 * field of a class, updating its value accordingly.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns the value of a variable named `z`, which is assumed to be a member of the
	 * same class and of type `float`. The purpose of this function appears to provide
	 * access to the `z` attribute. It does not modify the state of the object.
	 *
	 * @returns a floating-point value representing the current state of variable z.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Updates the `z` field with a new value provided as input, allowing it to be modified
	 * after initialization. The function takes a single parameter of type `float`, which
	 * is used to replace the existing value of `this.z`.
	 *
	 * @param z 3D coordinate value being assigned to an object's property or attribute,
	 * modifying its vertical position within a three-dimensional space.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Returns the value of a variable `w`, which is presumably used to represent some
	 * width or dimension, as it is declared with a data type of float indicating a
	 * numerical measurement.
	 *
	 * @returns a floating-point value representing the width of an object or variable
	 * named `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Sets a value for the instance variable `w`. It takes a float argument, assigns it
	 * to the `w` field, and does not return any value. The assignment is direct, without
	 * any validation or modification of the input value.
	 *
	 * @param w width value to be assigned to the object's field, which is then stored
	 * within it.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Compares two Quaternion objects for equality based on their component values,
	 * specifically whether x, y, z, and w coordinates match exactly between the two
	 * Quaternions. The comparison is done using strict equality checks via the `==`
	 * operator. A true result indicates identical Quaternions.
	 *
	 * @param r 3D quaternion object being compared for equality with the current quaternion
	 * instance.
	 *
	 * @returns a boolean value indicating equality of two Quaternion objects.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
