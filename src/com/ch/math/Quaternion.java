package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Implements quaternion algebra operations, providing methods for creating quaternions
 * from various sources such as vectors and Euler angles, as well as performing
 * arithmetic operations like multiplication, addition, and conjugation. It also
 * includes methods for converting quaternions to rotation matrices and vice versa,
 * and for interpolating between two quaternions.
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
	 * Calculates the Euclidean norm of a point defined by its coordinates, treating each
	 * coordinate as a component of a four-dimensional vector. It returns the magnitude
	 * or distance of the point from the origin as a floating-point value.
	 *
	 * @returns a floating-point Euclidean distance from the origin in four-dimensional
	 * space.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Calculates a quaternion's unit vector representation by dividing each component
	 * by its magnitude. It returns a new quaternion with normalized components, effectively
	 * scaling the original quaternion to have a length of 1. The normalization preserves
	 * the orientation and direction information.
	 *
	 * @returns a unit-length quaternion of the original quaternion.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Returns a new quaternion with the sign of its non-real components inverted. The
	 * real component remains unchanged while the imaginary components are negated. This
	 * operation is essential for quaternion algebra and is used to simplify calculations
	 * involving quaternions.
	 *
	 * @returns a quaternion with w, x, y, and z values negated.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Scales a quaternion by a given scalar value. The quaternion's components are
	 * multiplied element-wise with the scalar, resulting in a new quaternion representing
	 * the scaled version. This is typically used for scaling transformations in
	 * three-dimensional space.
	 *
	 * @param r scalar value to multiply each component of the quaternion by.
	 *
	 * @returns a scaled quaternion. The function multiplies each component of the
	 * quaternion by the given scalar value. A new quaternion is created with these scaled
	 * components.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Multiplies two quaternions, resulting in a new quaternion that represents the
	 * product of the original two. It performs standard quaternion multiplication by
	 * combining corresponding components using dot products and cross products. The
	 * result is returned as a new Quaternion object.
	 *
	 * @param r 4D vector representing the quaternion to be multiplied with the instance's
	 * quaternion for result generation.
	 *
	 * @returns a new Quaternion object resulting from multiplying two input Quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Multiplies a given quaternion by a specified vector. The result is a new quaternion
	 * with components calculated based on Hamilton product rules for quaternions and
	 * vectors, effectively representing rotation.
	 *
	 * @param r 3D vector with which the quaternion is multiplied, influencing the resulting
	 * rotation.
	 *
	 * @returns a new quaternion representing the product of two quaternions.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Subtracts one quaternion from another, performing element-wise subtraction of their
	 * components. It returns a new quaternion with the result of the operation. The
	 * original quaternions are left unchanged by the operation.
	 *
	 * @param r 3D vector to be subtracted from the current object, whose properties are
	 * being accessed through the `getW()`, `getX()`, `getY()`, and `getZ()` methods.
	 *
	 * @returns a new Quaternion with components subtracted from the input Quaternion's
	 * corresponding components.
	 * The result Quaternion has its w component as (w - r.w), x component as (x - r.x)
	 * and so on. The returned Quaternion represents the difference between two Quaternions.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Returns a new Quaternion object that is the sum of the input Quaternion and another
	 * specified Quaternion. The new object's components are calculated by adding
	 * corresponding components from the two Quaternions. This results in a new Quaternion
	 * with combined rotation information.
	 *
	 * @param r 4D vector of another quaternion to be added to the current quaternion's
	 * components for element-wise addition.
	 *
	 * @returns a new quaternion with added components.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * Computes a rotation matrix from a 4-element vector. It creates three vectors
	 * representing forward, up, and right directions based on the input vector's components,
	 * then initializes a Matrix4f object with these vectors as its basis to form a
	 * rotation matrix.
	 *
	 * @returns a rotation matrix representing the orientation of a 3D object.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Computes the dot product of two quaternions by summing the products of corresponding
	 * component elements. It takes another quaternion as input and returns a floating-point
	 * value representing the result. The computation is based on the components x, y,
	 * z, and w of both quaternions involved.
	 *
	 * @param r 3D rotation quaternion with which the dot product is calculated.
	 *
	 * @returns a floating-point number representing the dot product of two Quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Calculates a normalized linear interpolation between two quaternions based on a
	 * given factor, ensuring that the destination quaternion is the shortest path. It
	 * adjusts the destination quaternion if necessary to maintain the shortest rotation.
	 * The result is then normalized to ensure unity length.
	 *
	 * @param dest 4D vector being interpolated, whose direction and magnitude are used
	 * to compute the intermediate rotation.
	 *
	 * @param lerpFactor factor by which the difference between `this` and the destination
	 * quaternion is interpolated to produce the final result.
	 *
	 * @param shortest 4th axis of quaternion interpolation, determining whether the
	 * shortest path or the full rotation is used when interpolating between two quaternions
	 * with different signs.
	 *
	 * @returns a quaternion that linearly interpolates between two input quaternions.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Computes a spherical linear interpolation between two quaternions, taking into
	 * account the shortest path when interpolation is not direct. It uses quaternion
	 * multiplication and addition to achieve smooth rotation over time. The result is a
	 * new quaternion representing the interpolated state.
	 *
	 * @param dest 3D rotation to be interpolated with the current quaternion, used to
	 * calculate the corrected destination quaternion and the slerp interpolation factors.
	 *
	 * Dest is a Quaternion object with w, x, y, z components.
	 *
	 * Its main properties are:
	 * - It has four float components representing 3D rotation.
	 *
	 * @param lerpFactor 3D linear interpolation factor between two quaternions, with
	 * values ranging from 0 (beginning of the segment) to 1 (end of the segment).
	 *
	 * @param shortest 180-degree shortest path between two points on a unit sphere,
	 * correcting the destination quaternion if it lies opposite to the source quaternion's
	 * hemisphere.
	 *
	 * @returns a new Quaternion value interpolated between two input Quaternions.
	 *
	 * The output is an instance of `Quaternion`. It represents the result of spherical
	 * linear interpolation between two quaternions.
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
	 * Returns a new instance of Vector3f representing the forward direction of an object,
	 * relative to its current orientation. The result is obtained by rotating the standard
	 * forward vector (0, 0, 1) around the object's current rotation. The rotated vector
	 * is returned as a new instance.
	 *
	 * @returns a rotated unit vector along the x-axis of the object's local coordinate
	 * system.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Returns a vector representing the direction behind the object it is called on,
	 * based on its current rotation. It creates a new vector pointing straight back (0,
	 * 0, -1) and applies the object's rotation to this vector. The resulting vector is
	 * then returned.
	 *
	 * @returns a rotated vector pointing backwards relative to the input vector's direction.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Returns a rotated version of the unit vector (0, 1, 0) with its orientation adjusted
	 * according to the rotation specified by the object it is called on. This creates
	 * an upward direction based on the current orientation of the object. The result is
	 * a new vector.
	 *
	 * @returns a Vector3f object representing the local up direction of the rotated vector.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Returns a vector that represents the downward direction from an object's current
	 * position and rotation. It creates a new unit vector pointing downwards and applies
	 * the object's rotation to it. The resulting vector is then returned.
	 *
	 * @returns a rotated Vector3f object with x and z components unchanged.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Returns a new vector that represents the right direction of its calling object,
	 * rotated according to its current orientation. It achieves this by creating a unit
	 * vector (1, 0, 0) and rotating it around itself by the angle specified by the
	 * object's rotation.
	 *
	 * @returns a vector representing the right direction of the input object.
	 * Rotated clockwise from its original position.
	 * Its magnitude remains unchanged.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Creates a left vector relative to the current object's orientation by rotating a
	 * fixed unit vector (-1, 0, 0) around its own axis. The resulting rotated vector is
	 * then returned as a new Vector3f instance. The rotation operation takes into account
	 * the object's current orientation.
	 *
	 * @returns a rotated vector pointing left from the current object's position.
	 * It results in a vector with x-component set to -1 and y/z-components as per the
	 * rotation applied to it.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Initializes and updates a quaternion's components with new values, setting its
	 * real part (w), and three imaginary parts (x, y, z). The function returns the updated
	 * quaternion instance. It allows for chaining multiple assignments together.
	 *
	 * @param x 4D real component of a quaternion and is used to set the corresponding
	 * property within the class instance.
	 *
	 * @param y 2D Cartesian coordinate of the quaternion, which is used to update the
	 * instance variable `this.y`.
	 *
	 * @param z 3D coordinate value of the quaternion, which is set as an attribute of
	 * the object instance.
	 *
	 * @param w 4th component of the quaternion, which is assigned to the corresponding
	 * instance variable.
	 *
	 * @returns an instance of Quaternion with updated values.
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
	 * Assigns the values of another quaternion to itself, copying its x, y, z, and w
	 * components from the given quaternion. It returns a reference to the modified object.
	 * The function is overloaded by taking separate arguments for each component.
	 *
	 * @param r 4D rotation to be applied, providing access to its components (x, y, z,
	 * w) for setting the current quaternion's values.
	 *
	 * @returns a reference to the modified Quaternion instance itself.
	 * Returned value is a Quaternion object with updated properties.
	 * Output is an instance of Quaternion class.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * Returns a floating-point value representing the current state of variable `x`. It
	 * provides direct access to the stored value, allowing it to be used in calculations
	 * or further processed within the application. The returned value is of type `float`.
	 *
	 * @returns a floating-point value representing the current state of variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a new value to the object's x field. It takes a float parameter and sets
	 * it as the value for the field, replacing any previous value. The change is direct
	 * and immediate.
	 *
	 * @param x 2D position of an object or element, which is then assigned to the instance
	 * variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the current value of a private variable `y`. The returned value is of type
	 * `float` and accessible from outside the class through the getter method provided.
	 * This allows for controlled access to the `y` variable, promoting encapsulation.
	 *
	 * @returns a value of type `float`, representing the `y` attribute's current state.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Assigns a new value to the instance variable `y`. It takes a float parameter, which
	 * is used to update the current value of `y`. This allows for dynamic modification
	 * of the object's state, enabling flexibility and adaptability.
	 *
	 * @param y 2D coordinate value that is being assigned to an instance variable,
	 * presumably a field named `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns a value representing the Z-coordinate of an object. The value is directly
	 * retrieved from a variable named `z`. This method is a getter, providing read-only
	 * access to the `z` property.
	 *
	 * @returns a floating-point value of the `z` attribute's current state.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Assigns a float value to an instance variable named `z`, updating its current value
	 * with the provided input `z`. The operation is a simple assignment, where the new
	 * value replaces the existing one without any validation or transformation.
	 *
	 * @param z 3D coordinate value to be assigned to an instance variable or field of
	 * the class.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Returns a value representing a floating-point number. It retrieves and exposes the
	 * private member variable `w`. The returned value is accessible from outside the
	 * class where it is defined, allowing its use or modification by other parts of the
	 * program.
	 *
	 * @returns a floating-point number representing the value of variable `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Assigns a value to an instance variable named `w`. The assigned value is specified
	 * by the input parameter `w`, which must be a floating-point number. This allows
	 * external code to modify the internal state of the class.
	 *
	 * @param w width that is being assigned to an instance variable of the class, allowing
	 * it to be updated with a new value.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Checks whether two Quaternion objects have equal component values, comparing the
	 * x, y, z, and w components with their corresponding values in another Quaternion
	 * object. It returns true if all values match exactly and false otherwise.
	 *
	 * @param r quaternion object to be compared for equality with the current quaternion
	 * object.
	 *
	 * @returns a boolean indicating whether two Quaternions are identical.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
