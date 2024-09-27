package com.ch.math;

public class Vector3f {

	private float x;
	private float y;
	private float z;

	public Vector3f() {
		this(0, 0, 0);
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates the Euclidean distance of a point in 3D space, represented by its x,
	 * y, and z coordinates. The function returns the square root of the sum of the squares
	 * of these coordinates, resulting in a floating-point value.
	 *
	 * @returns the Euclidean distance of a point from the origin in 3D space.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calculates the square of the Euclidean distance of a point in three-dimensional
	 * space, where `x`, `y`, and `z` are coordinates.
	 *
	 * @returns the square of the Euclidean distance between a point in 3D space.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * Returns the maximum value among three float variables x, y, and z. It uses the
	 * Math.max method to compare the values recursively. The result is the largest float
	 * value among the three.
	 *
	 * @returns the maximum value among x, y, and z.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * Calculates the dot product of two 3D vectors by multiplying corresponding components
	 * and summing the results. The function takes a `Vector3f` object as input and returns
	 * a scalar value. It appears to be part of a 3D vector class.
	 *
	 * @param r input vector on which the dot product operation is performed.
	 *
	 * @returns the scalar product of the current vector and the input vector `r`.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * Calculates the cross product of two 3D vectors, returning a new vector that is
	 * perpendicular to both input vectors. The cross product is computed using the
	 * determinant formula, where the new vector's components are calculated as the
	 * differences of products of the input vector's components.
	 *
	 * @param r second vector used to calculate the cross product with the vector on which
	 * the function is called.
	 *
	 * @returns a new Vector3f object representing the cross product of the input vector
	 * and the parameter vector r.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Calculates the magnitude of a 3D vector, then returns a new vector with the same
	 * direction but a length of 1, by dividing each component by the calculated magnitude.
	 *
	 * @returns a new vector with the same direction as the original, but unit length.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * Rotates a 3D vector about a specified axis by a given angle. It uses the Rodrigues'
	 * rotation formula to calculate the new vector's coordinates. The rotation is performed
	 * in the counter-clockwise direction.
	 *
	 * @param axis axis of rotation.
	 *
	 * @param angle angle of rotation around the specified axis.
	 *
	 * @returns a Vector3f representing the rotation of the current object around the
	 * specified axis by the given angle.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * Rotates a 3D vector by a specified quaternion and returns the resulting vector.
	 * The rotation is performed using quaternion multiplication and conjugation to ensure
	 * correct orientation.
	 * The resulting vector is returned as a Vector3f object.
	 *
	 * @param rotation rotation to be applied to the current vector, where its quaternion
	 * is used to perform a rotation transformation.
	 *
	 * @returns a new Vector3f representing the rotated version of the input vector.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * Calculates a linear interpolation between the current vector and a destination
	 * vector, resulting in a new vector that is a proportion of the distance between the
	 * two vectors based on the specified lerp factor.
	 *
	 * @param dest destination vector for linear interpolation, serving as the target
	 * vector towards which the current vector is interpolated.
	 *
	 * @param lerpFactor scale factor that determines the amount of interpolation between
	 * the current position and the destination position.
	 *
	 * @returns a Vector3f representing the linear interpolation of the current object
	 * and the destination object.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Performs element-wise vector addition, combining two 3D vectors by adding their
	 * corresponding components. It returns a new vector with the sum of the input vectors'
	 * components. The original vectors remain unchanged.
	 *
	 * @param r vector to be added to the current vector.
	 *
	 * @returns a new Vector3f object with components summing the input Vector3f's components.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * Adds the components of the input `Vector3f` object `r` to the corresponding
	 * components of the current object, effectively modifying the current object's
	 * position in 3D space.
	 *
	 * @param r vector to be added to the current object.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * Adds a scaled vector to the current object,
	 * where the scaling factor is applied to the vector before addition,
	 * and the result is added to the object itself.
	 *
	 * @param v vector to be scaled and added to the current object.
	 *
	 * @param scale factor by which the input vector `v` is scaled before being added to
	 * the current vector.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Subtracts a specified float value `r` from each component (x, y, z) of a 3D vector,
	 * returning a new vector with the resulting coordinates. The original vector remains
	 * unchanged. This operation is equivalent to vector subtraction in mathematics.
	 *
	 * @param r value to be subtracted from each component of the Vector3f.
	 *
	 * @returns a new `Vector3f` object with components reduced by the specified value `r`.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * Performs element-wise multiplication of two 3D vectors, returning a new Vector3f
	 * instance with the product of corresponding x, y, and z components. It multiplies
	 * each component of the input vector `r` with the respective component of the
	 * instance's vector. The result is a new vector.
	 *
	 * @param r vector with which the current vector is multiplied.
	 *
	 * @returns a new Vector3f object representing the element-wise product of the input
	 * Vector3f and r.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * Performs scalar multiplication on a 3D vector, returning a new vector with each
	 * component multiplied by the given scalar `r`. The original vector remains unchanged.
	 * The resulting vector has components `x*r`, `y*r`, and `z*r`.
	 *
	 * @param r scaling factor that is multiplied with the current vector components.
	 *
	 * @returns a new vector with components scaled by the input scalar `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * Divides the current vector by another vector element-wise, resulting in a new
	 * vector with each component being the quotient of the corresponding components of
	 * the two input vectors.
	 *
	 * @param r vector to be divided, and its components serve as the divisor for the
	 * corresponding components of the vector being operated on.
	 *
	 * @returns a new Vector3f object containing the division result of the current
	 * Vector3f and the input Vector3f.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * Performs element-wise division of a 3D vector by a scalar value.
	 *
	 * @param r denominator for the division operation applied to the vector's components.
	 *
	 * @returns a new Vector3f object with its components divided by the input scalar `r`.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * Calculates the absolute values of the vector's components, returning a new Vector3f
	 * object with the results. The original vector remains unchanged.
	 *
	 * @returns a new Vector3f object with absolute values of x, y, and z.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Returns a string representation of an object, consisting of the values of its
	 * properties `x`, `y`, and `z`, enclosed in parentheses. The function concatenates
	 * the values into a single string. This is often used for debugging purposes.
	 *
	 * @returns a string representation of the coordinates in the format "(x y z)".
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * Returns a new instance of `Vector2f` containing the current x and y values.
	 * It does not modify the original state of the object.
	 *
	 * @returns a new Vector2f object with x and y coordinates.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * Creates a new `Vector2f` object with the y and z components of the current vector.
	 * The function does not modify the original vector but returns a copy of its y and
	 * z components.
	 *
	 * @returns a new Vector2f object with y and z coordinates.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * Sets the values of a Vector3f object's x, y, and z components to the provided float
	 * values and returns the updated object.
	 *
	 * @param x x-coordinate of the Vector3f object, which is set to the provided value.
	 *
	 * @param y y-coordinate of a 3D vector, which is updated to the specified value.
	 *
	 * @param z third coordinate of a 3D vector, which is assigned to the object's `z` field.
	 *
	 * @returns a `Vector3f` object with the specified x, y, and z coordinates.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Sets the components of a 3D vector to the values provided by another vector, and
	 * returns the current vector instance for method chaining.
	 *
	 * @param r vector to be set.
	 *
	 * @returns a Vector3f object with the specified coordinates set.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * Returns the value of the `x` variable as a float.
	 *
	 * @returns the value of the instance variable `x`, a float.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a float value to the instance variable `x`. The function updates the value
	 * of `x` with the provided input. This allows for dynamic modification of the object's
	 * state.
	 *
	 * @param x new value to be assigned to the instance variable `x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Exposes the value of the instance variable `y` as a float, allowing it to be
	 * retrieved by other parts of the program.
	 *
	 * @returns the value of the instance variable `y` of type `float`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the object's `y` field to a specified `float` value. The new
	 * value is assigned directly to the `y` field, replacing any previous value. This
	 * is a basic setter method in Java.
	 *
	 * @param y new value to be assigned to the instance variable `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns the value of a variable `z` of type `float`.
	 * This value is directly accessible through the function call.
	 *
	 * @returns the value of the `z` variable.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Updates the value of the `z` attribute to the specified `z` value.
	 *
	 * @param z value to be assigned to the instance variable `z`.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Compares two `Vector3f` objects for equality by checking if their corresponding
	 * x, y, and z components are equal. It returns a boolean value indicating whether
	 * the two vectors are identical. The comparison is based on exact equality, not
	 * approximate values.
	 *
	 * @param r right-hand side vector being compared to the current vector for equality.
	 *
	 * @returns a boolean value indicating whether two Vector3f objects have identical
	 * x, y, and z coordinates.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
