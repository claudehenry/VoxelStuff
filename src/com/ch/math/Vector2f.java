package com.ch.math;

/**
 * Represents a two-dimensional vector with float precision, offering various
 * mathematical operations for manipulating and combining vectors, such as addition,
 * subtraction, multiplication, division, and more. It also provides methods to
 * calculate the length, maximum value, dot product, cross product, and normalization
 * of the vector.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance or magnitude of a point represented by its
	 * coordinates `x` and `y`, returning the result as a floating-point value.
	 *
	 * @returns a floating-point value representing the Euclidean distance from the origin.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Calculates and returns the maximum value between two variables `x` and `y`. This
	 * is achieved by utilizing Java's built-in `Math.max` method. The resulting maximum
	 * value is a floating-point number.
	 *
	 * @returns the maximum of two floating-point numbers x and y.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * Calculates the dot product of two vectors, `x` and `y`, with the corresponding
	 * elements of input vector `r`. It multiplies corresponding components and sums them
	 * to produce a scalar result. The output is a floating-point value representing the
	 * dot product.
	 *
	 * @param r 2D vector that is used to calculate the dot product with the current
	 * object's vector (x, y).
	 *
	 * @returns a floating-point value representing the dot product of two vectors.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Calculates and returns a new vector with components equal to the original vector's
	 * components divided by its magnitude (length). The resulting vector has the same
	 * direction as the original but with a length of unity, i.e., it is normalized.
	 *
	 * @returns a new vector with normalized coordinates.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * Calculates the cross product of two vectors, specifically the vector with components
	 * `x` and `y`, and a second vector `r`. It returns the resulting scalar value as a
	 * float. The calculation is performed by multiplying the x component of the first
	 * vector by the y component of the second vector, then subtracting the product of
	 * the y component of the first vector and the x component of the second vector.
	 *
	 * @param r 2D vector used to compute the cross product with the object's own position
	 * vector, represented by `x` and `y`.
	 *
	 * @returns a floating-point value representing the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * Calculates a new vector by interpolating between the current vector and a target
	 * destination vector using a specified lerp factor, resulting in a position that is
	 * some fraction of the way from the starting point to the destination.
	 *
	 * @param dest 2D vector towards which linear interpolation is performed, from the
	 * current position of the object identified by `this`.
	 *
	 * @param lerpFactor 0-to-1 interpolation factor that determines the proportion of
	 * the distance between the current position and the destination to be applied when
	 * interpolating towards the destination.
	 *
	 * @returns a new `Vector2f` object interpolated between `this` and `dest`.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Applies a rotation to a 2D vector by calculating the new x and y coordinates based
	 * on the original coordinates, angle in degrees, and trigonometric functions. The
	 * result is a new Vector2f object with the rotated coordinates.
	 *
	 * @param angle angle of rotation, which is used to calculate the new coordinates of
	 * the Vector2f object after rotation.
	 *
	 * @returns a new Vector2f instance with rotated coordinates.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * Combines two vector objects of type `Vector2f`. It creates a new vector by adding
	 * corresponding elements from the input vector `r` to the current vector, returning
	 * the resulting vector. The new vector's x-coordinate is the sum of the current
	 * x-coordinate and the input x-coordinate, similarly for y-coordinates.
	 *
	 * @param r 2D vector to be added to the current vector, whose components are accessed
	 * through the `getX()` and `getY()` methods.
	 *
	 * @returns a new `Vector2f` object representing the sum of the input vector and the
	 * current instance.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * Creates and returns a new `Vector2f` object with x and y coordinates incremented
	 * by a given float value `r`. This operation adds a scalar to each component of the
	 * original vector, resulting in a translated vector. The original vector remains unchanged.
	 *
	 * @param r floating-point value to be added to both the x and y components of the
	 * vector object.
	 *
	 * @returns a new `Vector2f` object with incremented x and y coordinates.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * Creates a new `Vector2f` object by adding the input values `x` and `y` to the
     * corresponding components (`this.x` and `this.y`) of the current `Vector2f`. The
     * result is a new vector with the sum of the original vector's coordinates and the
     * added values.
     *
     * @param x 1st coordinate of the vector to be added to the current vector's coordinates.
     *
     * @param y 2D component to be added to the current object's y-coordinate.
     *
     * @returns a new `Vector2f` object with updated values.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * Calculates and returns a new vector that represents the difference between the
	 * current vector and the provided input vector `r`. This is achieved by subtracting
	 * the corresponding components (x and y) of both vectors. The result is a new vector
	 * with updated x and y values.
	 *
	 * @param r 2D vector from which the corresponding components of `x` and `y` are
	 * subtracted to produce the result.
	 *
	 * @returns a new `Vector2f` instance representing the difference between two vectors.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * Subtracts a given float value from both the x and y components of a vector, returning
	 * a new vector with the resulting values. This operation performs element-wise
	 * subtraction on the vector's coordinates.
	 *
	 * @param r scalar value to be subtracted from both x and y components of the current
	 * vector, resulting in a new vector with modified coordinates.
	 *
	 * @returns a new `Vector2f` object with x and y values subtracted by a given float
	 * value.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * Multiplies two vector objects represented by the `Vector2f` class, returning a new
	 * object with x and y components scaled by corresponding values from another input
	 * object.
	 *
	 * @param r 2D vector whose components are multiplied with the current object's
	 * components to produce a new result.
	 *
	 * @returns a new `Vector2f` object with scaled components.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * Scales a vector by a given factor. It takes a float value as input and returns a
	 * new `Vector2f` object with its x and y components multiplied by the input factor.
	 * The original vector is left unchanged, and a new scaled vector is returned.
	 *
	 * @param r scalar value by which the x and y components of the vector are multiplied
	 * to produce the scaled result.
	 *
	 * @returns a new `Vector2f` object with scaled components.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * Divides each component of a `Vector2f` object by the corresponding component of
	 * another `Vector2f` object, and returns the result as a new `Vector2f` object. The
	 * division is performed using Java's integer division operator `/`.
	 *
	 * @param r 2D vector used to divide the current vector, with its x and y components
	 * serving as the divisors for the corresponding components of the original vector.
	 *
	 * @returns a new `Vector2f` object with divided x and y coordinates.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * Performs element-wise division on a given vector by a scalar value. It takes a
	 * float parameter `r`, divides the x and y components of the current vector by `r`,
	 * and returns a new Vector2f object with the results.
	 *
	 * @param r divisor for division of the x and y components of the current vector.
	 *
	 * @returns a new `Vector2f` object with its components divided by the given float value.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * Returns a new `Vector2f` object with its x and y components replaced by their
	 * absolute values, without modifying the original object. This operation creates a
	 * copy of the input vector with all negative coordinates changed to positive ones.
	 *
	 * @returns a new `Vector2f` object with absolute values of x and y.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * Returns a string representation of an object. It takes two integer variables, `x`
	 * and `y`, concatenates them with spaces using the "+" operator, and encloses the
	 * result within parentheses to form a string.
	 *
	 * @returns a string representation of an object's coordinates in the format "(x y)".
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * Updates the values of `x` and `y` attributes to the provided floating-point numbers,
	 * then returns a reference to itself. This allows method chaining, enabling consecutive
	 * operations on the same object without intermediate variables. The updated object
	 * is returned for further processing.
	 *
	 * @param x 1st coordinate of a Vector2f object, assigning its value to the internal
	 * variable `this.x`.
	 *
	 * @param y y-coordinate of a 2D vector and is assigned to the `this.y` field, updating
	 * its value.
	 *
	 * @returns an instance of `Vector2f` with updated coordinates.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Updates the current object with new values from a given `Vector2f` object `r`. It
	 * calls another function to set individual x and y coordinates, and returns the
	 * updated object itself for method chaining.
	 *
	 * @param r 2D vector to be passed for updating the current object's properties.
	 *
	 * @returns an instance of `Vector2f` with updated values.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * Creates a new `Vector3f` object with x and y coordinates set to the corresponding
     * values of the current object, and sets z to 0. It returns this newly created vector.
     * This function effectively converts a 2D vector into a 3D vector.
     *
     * @returns a new `Vector3f` object with x and y components.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * Returns a value of type `float`. It retrieves and returns the current value of the
	 * variable `x`. This function provides read-only access to the `x` variable, allowing
	 * users to retrieve its value without modifying it.
	 *
	 * @returns a floating-point value representing the attribute `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a specified float value to the instance variable `x`. It updates the
	 * internal state of the object by replacing its current value with the new one. This
	 * change is done through the assignment operator and has no return value or output.
	 *
	 * @param x value to be assigned to the instance variable `this.x`, which is then
	 * stored for further use.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns a floating-point value representing the current position on the y-axis.
	 * This value is stored as an instance variable `y`. The function simply retrieves
	 * and returns this value without performing any calculations or modifications.
	 *
	 * @returns a floating-point value representing the `y` variable's state.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates the value of the instance variable `y` with a given floating-point number.
	 * This allows external classes to modify the internal state of the object by assigning
	 * new values to the `y` attribute.
	 *
	 * @param y vertical coordinate that is assigned to an object's property with the
	 * same name, updating its value.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Compares two `Vector2f` objects for equality by checking if their x and y coordinates
	 * are identical. It returns a boolean value indicating whether the two vectors have
	 * the same position. The comparison is based on exact equality, not approximate similarity.
	 *
	 * @param r 2D vector to be compared with the current object for equality, based on
	 * its x and y coordinates.
	 *
	 * @returns a boolean value indicating whether two Vector2f objects have identical coordinates.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
