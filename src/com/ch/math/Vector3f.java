package com.ch.math;

/**
 * Represents three-dimensional vectors with floating-point components and provides
 * various mathematical operations to manipulate these vectors, such as addition,
 * subtraction, multiplication, and division. It also includes methods for calculating
 * vector magnitudes, dot products, cross products, and normalization, as well as
 * support for rotation using both Euler angles and quaternions.
 */
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
	 * Calculates the Euclidean distance, or magnitude, of a three-dimensional vector
	 * represented by the variables `x`, `y`, and `z`. It returns the square root of the
	 * sum of the squares of these variables as a floating-point value. The result
	 * represents the length of the vector.
	 *
	 * @returns a floating-point value representing the magnitude of a 3D vector.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calculates the length of a vector represented by three floating-point numbers x,
	 * y, and z using the Pythagorean theorem. It squares each component, adds them
	 * together, and returns the result as a floating-point number. The function assumes
	 * that x, y, and z are input parameters.
	 *
	 * @returns a floating-point value representing the sum of squares of three variables.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * Calculates and returns the maximum value among three floating-point numbers `x`,
	 * `y`, and `z`. The calculation involves nested uses of the `Math.max` method to
	 * determine the greatest value among the three input parameters.
	 *
	 * @returns the maximum value among `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * Calculates the dot product of two vectors represented by objects of type `Vector3f`.
	 * The dot product is a scalar value obtained by multiplying corresponding elements
	 * of the two input vectors and summing the results.
	 *
	 * @param r 3D vector to be dot-producted with the current object's position, its
	 * coordinates being accessed and used for calculation.
	 *
	 * @returns a floating-point value representing the dot product of two vectors.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * Calculates the cross product of two 3D vectors, represented as `Vector3f`. It takes
	 * another `Vector3f` object `r` as an argument and returns a new `Vector3f` object
	 * with components computed using the standard formula for the cross product.
	 *
	 * @param r 3D vector whose cross product with the current vector is calculated and
	 * returned as a new vector.
	 *
	 * @returns a new `Vector3f` representing the cross product of two input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Calculates the magnitude of a vector and returns a new vector with the same direction
	 * but unit length by dividing each component by the calculated magnitude. This ensures
	 * that the resulting vector has a length of exactly one, thereby normalizing it.
	 *
	 * @returns a vector with normalized coordinates (x', y', z').
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * Applies a rotation to a given vector based on an axis and angle. It calculates the
	 * sine and cosine of the negative angle, then uses these values to compute the rotated
	 * vector using cross and dot product operations with the original vector and the axis.
	 *
	 * @param axis 3D axis of rotation, which is used to compute the resulting vector
	 * after applying the given angle of rotation around that axis.
	 *
	 * @param angle 3D rotation angle around the specified axis, which is used to calculate
	 * the resulting rotated vector.
	 *
	 * @returns a Vector3f object representing the rotated input vector.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * Multiplies a quaternion with itself, then with its conjugate, and extracts the
	 * resulting vector components from the product quaternion to produce a new Vector3f
	 * object representing the rotated vector. The rotation is specified by the input
	 * Quaternion parameter.
	 *
	 * @param rotation 3D rotation that is applied to the object's orientation, which
	 * affects the calculation of the resulting vector.
	 *
	 * @returns a vector resulting from rotating the current object's position by the
	 * given quaternion.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * Interpolates between two points defined by `this` and `dest` using a factor of
	 * `lerpFactor`. It calculates the difference between the two points, scales it by
	 * the interpolation factor, and adds the result to `this`, effectively moving towards
	 * `dest` along the line connecting the two points.
	 *
	 * @param dest 3D vector that defines the target position, which is used to calculate
	 * the linear interpolation with the current position represented by `this`.
	 *
	 * @param lerpFactor proportion by which the difference between the current position
	 * and the destination is scaled, determining the final result of the interpolation.
	 *
	 * @returns a new vector resulting from interpolation between two vectors.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Adds two `Vector3f` objects by combining their respective components (x, y, and
	 * z) and returns a new `Vector3f` object with the resulting values. The original
	 * vectors remain unchanged.
	 *
	 * @param r 3D vector to be added to the current vector, with its x, y, and z components
	 * being used for element-wise addition.
	 *
	 * @returns a new `Vector3f` object with added values.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * Modifies its own vector components by adding corresponding components from another
	 * given vector `r`. It updates the x, y, and z values of the object's position by
	 * incrementing them with the respective values of the input vector.
	 *
	 * @param r 3D vector whose components are added to the corresponding components of
	 * the current object's position (`x`, `y`, and `z`).
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * Creates and returns a new `Vector3f` object with its components incremented by the
	 * provided floating-point value `r`. The original vector's x, y, and z values are
	 * added to `r`, resulting in a new vector with updated coordinates.
	 *
	 * @param r scalar value to be added to the existing x, y, and z components of the
	 * Vector3f object.
	 *
	 * @returns a new `Vector3f` object with incremented x, y, and z values.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * Adds a scaled vector to the current vector. It multiplies a given vector `v` by a
	 * scale factor `scale`, and then adds the result to the current vector using the
	 * `add` method.
	 *
	 * @param v 3D vector to be multiplied by a scaling factor and added to the current
	 * vector.
	 *
	 * @param scale scalar value by which each component of the input vector `v` is
	 * multiplied before being added to the current vector.
	 *
	 * @returns a new `Vector3f` object resulting from scaling and adding input vectors.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * Multiplies a given vector by a specified scale factor and then adds the result to
	 * itself. This operation effectively scales the original vector by the given factor.
	 * The scaled vector is added to the instance, modifying its internal state.
	 *
	 * @param v 3D vector to be scaled by the specified factor and then added to the
	 * object itself.
	 *
	 * @param scale scalar value that is multiplied with the `v` vector to produce the
	 * scaled result which is then added to the current object's state.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * Subtracts a vector `r` from a given vector, component-wise. The result is a new
	 * vector with elements x, y, and z calculated by subtracting corresponding components
	 * of vectors r and the original vector.
	 *
	 * @param r 3D vector to be subtracted from the current object's position, which is
	 * then returned as a new Vector3f instance.
	 *
	 * @returns a new `Vector3f` object representing the difference of two vectors.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Subtracts a floating-point value from each component of a 3D vector, creating and
	 * returning a new `Vector3f` object with the resulting values. This allows for
	 * efficient vector subtraction operations. The original vector remains unchanged.
	 *
	 * @param r scalar value to be subtracted from each component (x, y, and z) of the
	 * current Vector3f object.
	 *
	 * @returns a new `Vector3f` object representing the difference between original
	 * vector coordinates and input float value.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * Multiplies a given vector by another vector component-wise and returns the result
	 * as a new vector. Each component of the input vector is scaled by the corresponding
	 * component of the input vector r, resulting in a new vector with similar dimensions.
	 *
	 * @param r 3D vector to be multiplied with the current object's 3D vector, returning
	 * a new Vector3f instance as the result.
	 *
	 * @returns a new `Vector3f` object resulting from element-wise multiplication of
	 * input vectors.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * Scales a vector by a given scalar value `r`. It multiplies each component of the
	 * vector (`x`, `y`, and `z`) by `r`, resulting in a new vector with scaled values.
	 * The function returns the scaled vector as a new instance of `Vector3f`.
	 *
	 * @param r scalar value to multiply each component (x, y, and z) of the Vector3f
	 * object by its corresponding element-wise multiplication operation.
	 *
	 * @returns a new `Vector3f` object scaled by the input floating-point value.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * Performs element-wise division between a `Vector3f` object and another `Vector3f`
	 * object `r`. The result is a new `Vector3f` object with components obtained by
	 * dividing the corresponding components of the input vectors. This operation is
	 * performed component-wise.
	 *
	 * @param r 3D vector by whose components the current vector's components are divided,
	 * resulting in a new vector with scaled coordinates.
	 *
	 * @returns a new `Vector3f` object representing division of corresponding components.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * Creates a new `Vector3f` object and initializes its components with the result of
	 * dividing the corresponding components of the current vector by a given float value
	 * `r`.
	 *
	 * @param r divisor used to divide each component of the `Vector3f` object by a scalar
	 * value.
	 *
	 * @returns a new `Vector3f` object resulting from division of input coordinates by
	 * a float value.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * Returns a new `Vector3f` object with its components set to the absolute values of
	 * the corresponding components of the input vector. It does not modify the original
	 * vector. The resulting vector has non-negative coordinates, which is useful for
	 * various mathematical and physical applications.
	 *
	 * @returns a new `Vector3f` object with absolute values of its components.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Converts an object into a string representation, specifically it returns a string
	 * that represents three integer values, namely `x`, `y`, and `z`, enclosed within
	 * parentheses and separated by spaces. The resulting string is used for debugging
	 * purposes or when a human-readable representation of the object is needed.
	 *
	 * @returns a string representation of three variables, separated by spaces.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * Returns a new instance of `Vector2f` with its components set to the current values
	 * of `x` and `y`. The returned vector contains the spatial coordinates represented
	 * by `x` and `y`. This allows for retrieving or processing these coordinates separately.
	 *
	 * @returns a new `Vector2f` object with components x and y.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * Creates and returns a new `Vector2f` object with its x-coordinate set to the
	 * instance variable `y` and its y-coordinate set to the instance variable `z`.
	 *
	 * @returns a new `Vector2f` object with components y and z.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * Returns a `Vector2f` object with its x-coordinate set to the value of the variable
	 * x and its y-coordinate set to the value of the variable z. The new vector is created
	 * each time the function is called.
	 *
	 * @returns a new instance of `Vector2f` with z and x coordinates.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * Returns a new instance of `Vector2f` with its components set to the values of `y`
	 * and `x`, respectively. The returned vector has its y-coordinate set to the original
	 * value of x, and its x-coordinate set to the original value of y.
	 *
	 * @returns a new `Vector2f` object containing the values of `y` and `x`.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * Creates a new `Vector2f` object and initializes it with two float values `z` and
	 * `y`. The resulting vector has its x component set to `z` and y component set to
	 * `y`, effectively returning the z-y pair as a vector.
	 *
	 * @returns a `Vector2f` object with elements z and y.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * Returns a new instance of `Vector2f` with its x and z components set to the values
	 * of the original object's x and z properties respectively. The function creates a
	 * new vector and initializes it with specified x and z coordinates.
	 *
	 * @returns a `Vector2f` object with x and z components.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * Initializes a vector with specified values for its components x, y, and z, updating
	 * internal variables accordingly, and returns a reference to itself for method
	 * chaining purposes.
	 *
	 * @param x 3D coordinate's x-axis value, which is assigned to the object's internal
	 * `x` attribute.
	 *
	 * @param y 2D coordinate value that is assigned to the object's `y` property.
	 *
	 * @param z 3D vector's third coordinate, which is assigned to the instance variable
	 * `this.z`.
	 *
	 * @returns an instance of `Vector3f` with updated values.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Updates a vector with new values from another vector. It calls an internal method
	 * to set individual components (x, y, z) of the vector based on corresponding
	 * components of the input vector, and returns the updated vector instance.
	 *
	 * @param r 3D vector whose x, y, and z components are used to update the current
	 * object's state through the nested `set` method call.
	 *
	 * @returns a reference to itself, allowing method chaining.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * Returns a floating-point value denoted by the variable `x`. This method retrieves
	 * and outputs the value assigned to `x`, providing access to the internal state of
	 * the object. The returned value is a primitive float type.
	 *
	 * @returns a floating-point value representing the attribute `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of a float variable named `x`. The new value is assigned to the
	 * existing instance variable `this.x`, effectively updating its state. This function
	 * modifies an object's property, allowing external code to alter its internal state.
	 *
	 * @param x value to be assigned to the instance variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns a float value representing the current y-coordinate. This method simply
	 * retrieves and returns the existing value of `y`. It does not perform any calculations
	 * or modifications to the value.
	 *
	 * @returns a float value representing the y-coordinate.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the instance variable `y` to a specified floating-point number.
	 * The new value is assigned to the existing `y` attribute, overwriting any previous
	 * value. This allows external code to modify the internal state of an object or class.
	 *
	 * @param y value to be assigned to the instance variable `this.y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns a floating-point value representing the current value of variable `z`. It
	 * does not modify any external state or perform any calculations, but simply retrieves
	 * and outputs the stored value.
	 *
	 * @returns a floating-point value representing the current value of `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Updates the value of the `z` property of an object to a specified `float` value.
	 * This update is performed by assigning the provided `float` value to the `z` property.
	 * The modified `z` value replaces any previously stored value for this property.
	 *
	 * @param z 3D coordinate to be assigned to the object's `z` property, which is stored
	 * as a floating-point value.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Compares two `Vector3f` objects, checking if their x, y, and z components are
	 * equal. It returns a boolean value indicating whether the vectors have identical coordinates.
	 *
	 * @param r 3D vector to be compared for equality with the object containing the
	 * `equals` method.
	 *
	 * @returns a boolean value indicating whether two Vector3f objects are identical.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
