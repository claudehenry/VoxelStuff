package com.ch.math;

/**
 * Represents a three-dimensional vector with floating-point components x, y, and z.
 * It provides various methods for operations such as addition, subtraction,
 * multiplication, division, rotation, normalization, and length calculation.
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
	 * Calculates the Euclidean distance or magnitude of a three-dimensional vector
	 * represented by its components `x`, `y`, and `z`. It returns the floating-point
	 * value of the square root of the sum of squares of these components.
	 *
	 * @returns a floating-point value representing the Euclidean distance from the origin.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calculates the length of a vector represented by the coordinates x, y, and z using
	 * the Pythagorean theorem, which is the square root of the sum of the squares of its
	 * components. The result is returned as a floating-point value.
	 *
	 * @returns a floating-point value representing the sum of squares of `x`, `y`, and
	 * `z`.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * Returns the maximum value among three floating-point numbers x, y, and z by using
	 * the built-in `Math.max` method recursively to compare the values.
	 *
	 * @returns a floating-point value representing the maximum of `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * Calculates a dot product between two vectors, where the first vector is represented
	 * by the `x`, `y`, and `z` variables and the second vector is provided as an argument
	 * of type `Vector3f`. It returns the result of the dot product.
	 *
	 * @param r 3D vector used to calculate the dot product with the current vector,
	 * returning a float value as the result of the multiplication and addition operations.
	 *
	 * @returns a floating-point value representing the dot product of two vectors.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * Calculates the cross product between two 3D vectors, represented by objects of
	 * type `Vector3f`, using the standard formula for cross products. The result is a
	 * new vector perpendicular to both input vectors and with magnitude equal to their
	 * magnitudes times the sine of the angle between them.
	 *
	 * @param r 3D vector to be crossed with the current vector, used for calculating the
	 * resulting cross product.
	 *
	 * @returns a new vector perpendicular to both input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Calculates and returns a new vector that is the normalization of the input vector,
	 * ensuring its magnitude (length) is equal to 1 by dividing each component by the
	 * original magnitude.
	 *
	 * @returns a new vector with components scaled to have a length of one.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * Computes a rotation of a vector around an axis by a given angle. It uses the
	 * Rodrigues' formula to calculate the resulting vector, considering both rotation
	 * and translation components.
	 *
	 * @param axis 3D vector around which the rotation is performed, used to compute the
	 * resulting rotated vector.
	 *
	 * @param angle 3D rotation angle around the specified axis, which is used to calculate
	 * the rotated vector.
	 *
	 * @returns a new vector representing the rotation of the original input vector.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * Applies a rotation to a vector using quaternion multiplication, first computing
	 * the conjugate of the input quaternion and then multiplying it with the original
	 * quaternion and the input vector, returning the resulting rotated vector as a
	 * Vector3f object.
	 *
	 * @param rotation 3D rotation to be applied, which is used to compute the resulting
	 * vector after applying the conjugate of the given rotation and then multiplying it
	 * with the current object.
	 *
	 * @returns a Vector3f representing the rotated version of the input vector.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * Interpolates a vector from its current position to a destination point using a
	 * specified factor. It calculates the difference between the current position and
	 * the destination, scales it by the interpolation factor, and adds the result back
	 * to the original position.
	 *
	 * @param dest 3D vector that defines the target point to which the interpolation is
	 * being performed.
	 *
	 * @param lerpFactor value between 0 and 1 that determines the interpolation factor,
	 * controlling how much of the destination vector is included in the result.
	 *
	 * @returns a vector calculated by interpolating between the input vector and the
	 * destination vector.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Performs an element-wise addition between two 3D vectors, combining corresponding
	 * components (x, y, z). It returns a new Vector3f object with the sum of the input
	 * vector and the given `r` vector.
	 *
	 * @param r 3D vector to be added to the current object, whose coordinates are retrieved
	 * and combined with those of the current object to produce the result.
	 *
	 * @returns a new vector resulting from the addition of two input vectors.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * Modifies the internal state of an object by adding a given vector `r` to its
	 * corresponding components (`x`, `y`, and `z`). This operation updates the object's
	 * position by incorporating the specified vector. The result is reflected in the
	 * object's attributes.
	 *
	 * @param r 3D vector to be added to the current position of an object, as specified
	 * by the x, y, and z components of `r`.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * Returns a new instance of `Vector3f` with its x, y, and z components incremented
	 * by the given floating-point value. The original vector is left unchanged. This
	 * operation performs element-wise addition between the vector and the scalar.
	 *
	 * @param r 3D vector offset to be added to the current `x`, `y`, and `z` components
	 * of the Vector3f object, resulting in a new Vector3f object with the corresponding
	 * sum.
	 *
	 * @returns a new `Vector3f` object with incremented components.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * Multiplies a given vector by a scalar and then adds the result to the current
	 * vector. This operation is typically used for scaling and translating vectors in
	 * three-dimensional space. The scaled vector is added to the original vector, resulting
	 * in a new position or direction.
	 *
	 * @param v 3D vector that is multiplied by a scalar factor and then added to the
	 * current vector.
	 *
	 * @param scale scalar value that multiplies the vector `v`, resulting in its scaled
	 * representation.
	 *
	 * @returns a new vector resulting from scaling and adding to the original.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * Scales a vector by a given factor and then adds it to itself. The scaled vector
	 * is obtained by multiplying the input vector with the specified scale factor, and
	 * the result is added to the current state of the object using its `addSelf` method.
	 *
	 * @param v 3D vector that is multiplied by a given scalar value (`scale`) and then
	 * added to the current object.
	 *
	 * @param scale scalar value used to multiply the input vector `v`, scaling its
	 * magnitude before adding it to the current object's vector.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * Calculates the difference between two vector-like objects. It subtracts each
	 * component of the input `r` from the corresponding components of the object it is
	 * called on, resulting in a new `Vector3f` object with the computed differences.
	 *
	 * @param r 3D vector whose components are subtracted from those of the current object
	 * to produce the result.
	 *
	 * @returns a new `Vector3f` instance representing the difference between two vectors.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Subtracts a floating-point value from each component of a 3D vector, creating a
	 * new instance of `Vector3f`. The original vector remains unchanged. This function
	 * returns the resulting vector with its x, y, and z coordinates modified accordingly.
	 *
	 * @param r scalar value to be subtracted from each component (x, y, and z) of the
	 * current vector.
	 *
	 * @returns a new `Vector3f` object with subtracted values from the input `r`.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * Performs element-wise multiplication between two three-dimensional vectors represented
	 * by `Vector3f`. It multiplies corresponding x, y, and z components of each vector
	 * together, resulting in a new vector with scaled values. The output is returned as
	 * a new `Vector3f` object.
	 *
	 * @param r 3D vector whose components are multiplied by the corresponding components
	 * of the calling object's `x`, `y`, and `z` properties, respectively.
	 *
	 * @returns a new `Vector3f` object with scaled components.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * Scales a 3D vector by a given factor `r`, multiplying its components `x`, `y`, and
	 * `z` by `r`. The result is a new 3D vector with scaled components. This operation
	 * is typically used for uniform scaling of vectors in 3D space.
	 *
	 * @param r scalar value that is used to scale each component of the original vector
	 * (x, y, z).
	 *
	 * @returns a new `Vector3f` object scaled by the input `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * Divides each component of a given `Vector3f` instance by the corresponding component
	 * of another `Vector3f` instance and returns a new `Vector3f` instance with the
	 * results. The division operation is element-wise, applying to x, y, and z components
	 * separately.
	 *
	 * @param r 3D vector to be used for division, providing the values by which the
	 * corresponding components of the current vector are divided.
	 *
	 * @returns a new `Vector3f` object with divided components.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * Performs element-wise division of a `Vector3f` by a given floating-point number
	 * `r`. It creates and returns a new `Vector3f` object with its components divided
	 * by `r`. The original vector is not modified.
	 *
	 * @param r scalar value by which each component of the current vector is divided to
	 * produce the resulting vector.
	 *
	 * @returns a new `Vector3f` object with divided components.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * Returns a new `Vector3f` object with its components set to the absolute values of
	 * the corresponding components of the input vector, leaving the original vector
	 * unchanged. This function is used for mathematical operations involving vectors
	 * where absolute values are necessary.
	 *
	 * @returns a new `Vector3f` object with absolute values of x, y, and z.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Returns a string representation of an object, specifically representing its
	 * coordinates (x, y, z) enclosed within parentheses. The resulting string includes
	 * the values of x, y, and z separated by spaces. This allows for easy printing or
	 * conversion to other data types.
	 *
	 * @returns a string representation of three coordinates in the format "(x y z)".
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * Creates a new instance of `Vector2f` with components x and y, then returns it as
	 * a result. This allows external access to the internal state of the object. The
	 * returned `Vector2f` can be used for further processing or manipulation.
	 *
	 * @returns a new `Vector2f` object with x and y coordinates.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * Creates and returns a new instance of `Vector2f`, with its x-component set to the
	 * value of `y` and its y-component set to the value of `z`. The returned vector
	 * represents a point in a two-dimensional space, with coordinates from the `y` and
	 * `z` variables.
	 *
	 * @returns a `Vector2f` object with y and z coordinates.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * Creates a new instance of `Vector2f` and initializes it with values from variables
	 * `z` and `x`, then returns the resulting object. This function appears to swap the
	 * typical x-y axis order, returning a vector with z as the first element and x as
	 * the second.
	 *
	 * @returns a new `Vector2f` object with z and x coordinates.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * Returns a new `Vector2f` object with its y and x components swapped. It creates a
	 * new vector with the original y value as its x component and the original x value
	 * as its y component. The result is a new vector with the coordinates in reversed order.
	 *
	 * @returns a new `Vector2f` object containing y-coordinate followed by x-coordinate.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * Creates a new instance of the `Vector2f` class and returns it with the z-coordinate
	 * value from an unknown source (referenced as "z") and the y-coordinate value from
	 * another unknown source (referenced as "y").
	 *
	 * @returns a `Vector2f` object containing the values of `z` and `y`.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * Returns a new instance of `Vector2f`, which represents a two-dimensional vector
	 * with x and z components. The x and z values are obtained from external sources,
	 * likely properties or fields within the class. This function does not modify the
	 * original vector but creates a copy.
	 *
	 * @returns a new instance of `Vector2f` containing `x` and `z` values.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * Initializes and assigns specified float values to Vector3f object's attributes,
	 * namely `x`, `y`, and `z`. It then returns a reference to itself for method chaining.
	 * This allows subsequent operations to be performed on the same object without
	 * creating a new one.
	 *
	 * @param x 3D vector's x-coordinate, which is assigned to the instance variable `this.x`.
	 *
	 * @param y 2D component of a three-dimensional vector and is assigned to the instance
	 * variable `this.y`.
	 *
	 * @param z 3D coordinate value that is assigned to the internal variable `this.z`
	 * of the Vector3f class.
	 *
	 * @returns an instance of the `Vector3f` class.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Updates a Vector3f object with new values provided by another Vector3f object and
	 * returns the updated object. It calls another `set` function to set individual x,
	 * y, z components from the input Vector3f object. The update is done in-place.
	 *
	 * @param r 3D vector to be used as an argument for calling another method, set().
	 *
	 * @returns an instance of `Vector3f`, modified with new values.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * Returns a floating-point value represented by the variable `x`. This variable
	 * stores a numerical value that can be accessed and retrieved through this method.
	 * The returned value is a copy of the current state of `x`.
	 *
	 * @returns a floating-point number value of variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a new value to the instance variable `x`. It takes a single float parameter
	 * and updates the current value of `x` with the provided input. This method allows
	 * external code to modify the internal state of the object by setting its x-coordinate.
	 *
	 * @param x new value for the instance variable `this.x`, which is assigned to it
	 * within the method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Retrieves and returns a floating-point value representing the property 'y'. This
	 * function does not modify any data, it simply provides access to an existing attribute
	 * 'y' with a getter method. The returned value is a copy of the original 'y' value.
	 *
	 * @returns a floating-point value representing the attribute 'y'.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates the instance variable `y` with a new value provided as an argument. This
	 * allows modifying the existing value of `y`. The updated value is stored internally
	 * within the class for future use.
	 *
	 * @param y value to be assigned to the instance variable `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns a floating-point value representing the z-coordinate. The function retrieves
	 * and exposes the value of `z`, which is presumably an instance variable or a field,
	 * allowing external code to access it directly without modification. The returned
	 * value is a copy of the stored z-coordinate.
	 *
	 * @returns a floating-point value representing the variable `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Assigns a value to the instance variable `z`. It updates the current value of `z`
	 * with a new float value passed as an argument. The updated value is then stored in
	 * the object's internal state.
	 *
	 * @param z 3D coordinate value that is being assigned to the instance variable `z`.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Compares two instances of a vector, `Vector3f`, based on their component values
	 * (x, y, and z). It returns a boolean indicating whether the two vectors have identical
	 * components or not. This method is used to check for equality between two vectors.
	 *
	 * @param r 3D vector to be compared with the object's own position for equality verification.
	 *
	 * @returns a boolean value indicating whether two Vector3f objects are identical.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
