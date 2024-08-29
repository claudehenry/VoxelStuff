package com.ch.math;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Represents a quaternion, a mathematical object used to represent 3D rotations in
 * computer graphics and other fields. It provides various methods for creating,
 * manipulating, and converting quaternions, including from Euler angles and matrices.
 * The class also includes operations for rotation, normalization, and interpolation
 * of quaternions.
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
	 * Calculates the Euclidean norm or magnitude of a point in four-dimensional space,
	 * considering the values of `x`, `y`, `z`, and `w`. It returns the square root of
	 * the sum of squares of these coordinates as a floating-point value.
	 *
	 * @returns a floating-point value representing the Euclidean distance.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	/**
	 * Calculates the magnitude (length) of a given quaternion and returns a new quaternion
	 * with its components scaled by the inverse of the magnitude to ensure unity. This
	 * process normalizes the quaternion, preserving its direction while reducing its
	 * magnitude to 1.
	 *
	 * @returns a new quaternion with normalized components.
	 */
	public Quaternion normalized() {
		float length = length();

		return new Quaternion(w / length, x / length, y / length, z / length);
	}

	/**
	 * Generates a quaternion that is the complex conjugate of the input quaternion. It
	 * returns a new quaternion with its real part unchanged and its imaginary parts
	 * negated. This is achieved by multiplying each of the imaginary components by -1.
	 *
	 * @returns a new quaternion with opposite imaginary components.
	 */
	public Quaternion conjugate() {
		return new Quaternion(w, -x, -y, -z);
	}

	/**
	 * Scales a quaternion by a given factor `r`, returning a new quaternion with its
	 * components multiplied by `r`. The original quaternion's components are preserved
	 * and then scaled by the specified factor, resulting in a new quaternion representing
	 * the scaled version.
	 *
	 * @param r scalar value to be multiplied with each component of the quaternion for
	 * scaling purposes.
	 *
	 * @returns a new `Quaternion` object with each component scaled by the input float
	 * value.
	 */
	public Quaternion mul(float r) {
		return new Quaternion(w * r, x * r, y * r, z * r);
	}

	/**
	 * Multiplies two quaternions, performing a quaternion multiplication operation that
	 * combines their components according to specific rules. It returns a new quaternion
	 * object representing the result of the multiplication.
	 *
	 * @param r 4D vector to be multiplied with the current quaternion, its values being
	 * used to compute the product quaternion.
	 *
	 * @returns a new Quaternion object resulting from the multiplication of two input Quaternions.
	 */
	public Quaternion mul(Quaternion r) {
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Multiplies a quaternion by a vector and returns the result as a new quaternion.
	 * The input vector is decomposed into its components, which are then multiplied by
	 * corresponding components of the quaternion to produce a new quaternion.
	 *
	 * @param r 3D vector used for multiplying with the quaternion to produce a resulting
	 * quaternion.
	 *
	 * @returns a quaternion resulting from multiplying a vector with the current quaternion.
	 */
	public Quaternion mul(Vector3f r) {
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(w_, x_, y_, z_);
	}

	/**
	 * Calculates the difference between two Quaternions, represented as `w`, `x`, `y`,
	 * and `z` components. It returns a new Quaternion object with elements obtained by
	 * subtracting corresponding components of the input Quaternions.
	 *
	 * @param r 4D vector to be subtracted from the current quaternion.
	 *
	 * @returns a new quaternion resulting from subtracting another quaternion.
	 */
	public Quaternion sub(Quaternion r) {
		return new Quaternion(w - r.getW(), x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Combines two quaternion numbers by adding their corresponding components. It creates
	 * a new quaternion object with the sum of w, x, y, and z components from the input
	 * quaternions. The result is a new quaternion representing the vector sum of the
	 * input quaternions.
	 *
	 * @param r 4D vector to be added to the current quaternion, allowing for element-wise
	 * addition of its components with those of the current quaternion.
	 *
	 * @returns a new quaternion resulting from adding the input quaternion to the instance
	 * quaternion.
	 */
	public Quaternion add(Quaternion r) {
		return new Quaternion(w + r.getW(), x + r.getX(), y + r.getY(), z + r.getZ());
	}

	/**
	 * Calculates a rotation matrix from three vectors: forward, up, and right. These
	 * vectors are generated using the x, y, z, and w components of an input quaternion.
	 * The resulting matrix is initialized as a rotation matrix with the given vectors.
	 *
	 * @returns a rotation matrix initialized with given forward, up, and right vectors.
	 */
	public Matrix4f toRotationMatrix() {
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	/**
	 * Computes a dot product between two quaternions, one defined by the local variables
	 * `x`, `y`, `z`, and `w`, and another specified as input parameter `r`. The result
	 * is a floating-point value representing the scalar product of corresponding components
	 * from both quaternions.
	 *
	 * @param r 3D quaternion object being used to calculate the dot product with the
	 * current quaternion object, whose components are accessed through getter methods
	 * (`getX()`, `getY()`, `getZ()`, and `getW()`).
	 *
	 * @returns a floating-point value representing the dot product of two quaternions.
	 */
	public float dot(Quaternion r) {
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	/**
	 * Calculates a linear interpolation between two quaternions, `this` and `dest`, using
	 * the given `lerpFactor`. If `shortest` is `true` and the dot product of `this` and
	 * `dest` is negative, it adjusts `dest` to ensure a shortest arc rotation.
	 *
	 * @param dest 4D vector to which the current quaternion is interpolated, with its
	 * sign being adjusted if necessary to ensure a shortest arc rotation.
	 *
	 * @param lerpFactor 0-to-1 interpolation factor used to calculate the intermediate
	 * quaternion between the current instance and the destination quaternion.
	 *
	 * @param shortest shortest path through the Quaternion space, flipping the destination
	 * quaternion when necessary to ensure it is on the same side as the current quaternion
	 * if shortest path is required.
	 *
	 * @returns a Quaternion, which is the interpolated result between two Quaternions.
	 */
	public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest) {
		Quaternion correctedDest = dest;

		if (shortest && this.dot(dest) < 0)
			correctedDest = new Quaternion(-dest.getW(), -dest.getX(), -dest.getY(), -dest.getZ());

		return correctedDest.sub(this).mul(lerpFactor).add(this).normalized();
	}

	/**
	 * Performs spherical linear interpolation between two quaternion values, ensuring
	 * the shortest path is taken if necessary. It calculates the dot product and angle
	 * between the quaternions, then applies the interpolation factors to produce the
	 * resulting quaternion value.
	 *
	 * @param dest 4D quaternion to which the input quaternion is being interpolated
	 * towards, with its magnitude normalized if necessary for shorter arc calculations.
	 *
	 * Destructures into:
	 * - A quaternion with four components (w, x, y, z)
	 * - Magnitude determined by the dot product with the current instance
	 *
	 * @param lerpFactor 0-to-1 value that determines the proportion of the destination
	 * quaternion to be interpolated towards from the current quaternion, with 0 resulting
	 * in no change and 1 resulting in complete interpolation.
	 *
	 * @param shortest option to choose the shortest arc between two quaternions when
	 * their dot product is negative, ensuring that the quaternion interpolation remains
	 * smooth and continuous.
	 *
	 * @returns a Quaternion interpolated between the current and destination Quaternions.
	 *
	 * The output is a Quaternion object that represents a linear interpolation between
	 * the current quaternion and the destination quaternion. The output's rotation is
	 * calculated based on the cosine and sine of the angle between the two quaternions.
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
	 * Creates a new instance of `Vector3f`, initializes it with values (0, 0, 1), and
	 * then applies rotation using the current object as an argument, returning the rotated
	 * vector. The resulting vector represents the forward direction.
	 *
	 * @returns a `Vector3f` instance representing the forward direction rotated according
	 * to the input object.
	 */
	public Vector3f getForward() {
		return new Vector3f(0, 0, 1).rotate(this);
	}

	/**
	 * Rotates a default vector (0, 0, -1) by a specified object (`this`) and returns the
	 * resulting rotated vector as a `Vector3f`. The rotation is likely applied to the
	 * default vector's orientation based on the properties of the object.
	 *
	 * @returns a rotated `Vector3f` object with x and y components set to zero.
	 */
	public Vector3f getBack() {
		return new Vector3f(0, 0, -1).rotate(this);
	}

	/**
	 * Returns a new instance of a `Vector3f` object representing an up vector. The up
	 * vector is created by rotating a default up vector (0, 1, 0) by the current rotation
	 * of the object referenced by `this`.
	 *
	 * @returns a rotated vector with its y-axis pointing upwards.
	 */
	public Vector3f getUp() {
		return new Vector3f(0, 1, 0).rotate(this);
	}

	/**
	 * Creates a new vector pointing downwards from the origin and rotates it by the angle
	 * specified by the object `this`. The resulting vector is then returned. This operation
	 * effectively translates the direction of the downwards vector based on the object's
	 * orientation.
	 *
	 * @returns a rotated vector pointing downwards from the origin.
	 */
	public Vector3f getDown() {
		return new Vector3f(0, -1, 0).rotate(this);
	}

	/**
	 * Returns a new instance of `Vector3f`, created by rotating the vector `(1, 0, 0)`
	 * around the origin by the angle specified by the `this` object. The result is a
	 * unit vector pointing to the right direction in the rotated coordinate system.
	 *
	 * @returns a `Vector3f` object representing the right vector rotated according to
	 * the input.
	 */
	public Vector3f getRight() {
		return new Vector3f(1, 0, 0).rotate(this);
	}

	/**
	 * Creates a new `Vector3f` object with coordinates (-1, 0, 0) and rotates it according
	 * to the current state of the object (`this`). The rotated vector is then returned
	 * as the result.
	 *
	 * @returns a rotated `Vector3f` object with coordinates -1, 0, and 0.
	 */
	public Vector3f getLeft() {
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	/**
	 * Initializes a Quaternion with specified floating-point values for x, y, z, and w
	 * components, updating its internal state accordingly. It then returns a reference
	 * to itself for method chaining purposes. This allows for fluent API usage when
	 * setting multiple Quaternions in sequence.
	 *
	 * @param x 0th component of the quaternion, which is assigned to the instance variable
	 * `this.x`.
	 *
	 * @param y 2nd component of the quaternion and is assigned to the instance variable
	 * `this.y`.
	 *
	 * @param z 3-dimensional component of a quaternion, which is assigned to the instance
	 * variable `z`.
	 *
	 * @param w 4th component of a quaternion, which is assigned to the object's internal
	 * `w` field.
	 *
	 * @returns a reference to the `Quaternion` object itself.
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
	 * Assigns a new quaternion value to an instance, taking four arguments representing
	 * its components (x, y, z, w), and then returns the modified instance. This allows
	 * for convenient chaining of method calls for setting quaternion values.
	 *
	 * @param r 4D vector to be assigned to the current quaternion object.
	 *
	 * @returns the modified instance of `Quaternion`, updated with new values.
	 */
	public Quaternion set(Quaternion r) {
		set(r.getX(), r.getY(), r.getZ(), r.getW());
		return this;
	}

	/**
	 * Retrieves the value of a variable named `x`, returning it as a floating-point
	 * number. The purpose is to provide access to the current state of `x`.
	 *
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a float value to an instance variable `x`. It takes a single parameter
	 * `x`, which represents the new value to be assigned, and updates the existing value
	 * of `x`.
	 *
	 * @param x value to be assigned to the instance variable `this.x`, updating its
	 * current state.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns a floating-point value representing the y-coordinate or y-value of an
	 * object. The returned value is retrieved from a variable named `y`. This getter
	 * function provides read-only access to the private field `y`.
	 *
	 * @returns a floating-point value representing the y-coordinate of an object or entity.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates the value of a private instance variable `y` with a new float value provided
	 * as an argument. The updated value is stored and becomes accessible through the
	 * object's public interface. This method modifies the state of the object by altering
	 * its internal representation.
	 *
	 * @param y 2D coordinate value that is assigned to the instance variable `this.y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Retrieves and returns the value of a variable `z`. The type of the returned value
	 * is a floating-point number. This function allows accessing the value of `z` from
	 * outside the scope where it is defined.
	 *
	 * @returns a floating-point value representing the variable `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Assigns a new value to the instance variable `z`. The parameter `z` is used to
	 * update the internal state of the object. This function does not return any value
	 * but modifies the existing value of the object's property.
	 *
	 * @param z 3D coordinate value to be assigned to the instance variable `this.z`.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Retrieves a floating-point value named `w` and returns it to the caller. It does
	 * not perform any calculations or modifications on the input data, but simply provides
	 * access to the stored value. The returned value is of type `float`.
	 *
	 * @returns a floating-point value representing the variable `w`.
	 */
	public float getW() {
		return w;
	}

	/**
	 * Assigns a float value to the instance variable `w`. The assigned value is stored
	 * and becomes the new value for `w`. This allows external modification of the internal
	 * state of the object, specifically its width or weight.
	 *
	 * @param w width value to be assigned to the object's `w` field.
	 */
	public void setW(float w) {
		this.w = w;
	}

	/**
	 * Compares two Quaternion objects for equality. It checks if all four components (x,
	 * y, z, and w) of the current object are equal to the corresponding components of
	 * another provided Quaternion object. If all conditions are met, it returns true;
	 * otherwise, false is returned.
	 *
	 * @param r quaternion object to be compared for equality with the current object's
	 * properties.
	 *
	 * @returns a boolean value indicating equality of two Quaternion objects.
	 */
	public boolean equals(Quaternion r) {
		return x == r.getX() && y == r.getY() && z == r.getZ() && w == r.getW();
	}
	
}
