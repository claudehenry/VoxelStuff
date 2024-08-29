package com.ch.math;

/**
 * Represents two-dimensional vectors with floating-point values. It provides methods
 * for calculating vector operations such as length, dot product, cross product, and
 * rotation. The class also supports various mathematical operations like addition,
 * subtraction, multiplication, and division on vectors.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance of a point from its origin, considering the
	 * Cartesian coordinates `x` and `y`. It uses the Pythagorean theorem with the help
	 * of the `Math.sqrt` method to compute the square root of the sum of squares of `x`
	 * and `y`.
	 *
	 * @returns a floating-point value representing the Euclidean distance from the origin.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the maximum value between two floating-point numbers `x` and `y`. This is
	 * achieved by calling the `Math.max` method from Java's standard library. The result
	 * is a single floating-point number representing the greater of the two input values.
	 *
	 * @returns the maximum value between `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * Calculates a scalar product between two-dimensional vectors represented by instances
	 * of `Vector2f`. It multiplies corresponding components (x and y) of the calling
	 * object and the provided argument, then returns the resulting sum as a floating-point
	 * value.
	 *
	 * @param r 2D vector whose components are multiplied with the object's x and y
	 * coordinates, respectively, to calculate the dot product.
	 *
	 * @returns a floating-point value representing the dot product of two vectors.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Returns a new `Vector2f` object with its components scaled to have a magnitude
	 * (length) of 1, preserving its direction. It does this by calculating the length
	 * of the current vector and then dividing each component by that length.
	 *
	 * @returns a new vector with components scaled to unity.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * Calculates the vector product (also known as the scalar product or dot product)
	 * between two-dimensional vectors represented by the current object's coordinates
	 * `(x, y)` and the input vector `r`. The result is a floating-point value representing
	 * the magnitude of the resulting vector.
	 *
	 * @param r 2D vector to which the dot product operation is applied, yielding the
	 * result of the cross-product calculation.
	 *
	 * @returns a scalar value representing the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * Linearly interpolates between the current position and a destination position by
	 * scaling the difference vector with a given factor, then adding it to the original
	 * position. The result is a new position that is closer to the destination position
	 * by the specified amount.
	 *
	 * @param dest 2D vector towards which interpolation is performed, providing the
	 * target point for linearly interpolating the current position with the given `lerpFactor`.
	 *
	 * @param lerpFactor 0 to 1 value that determines the interpolation ratio between the
	 * current position and the destination position, influencing the output vector.
	 *
	 * @returns a new vector that smoothly interpolates between the current and destination
	 * positions.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Applies a rotation to a vector specified by its x and y components, based on a
	 * given angle. It calculates the sine and cosine of the angle, then uses these values
	 * to calculate the new x and y coordinates after rotation. The result is a new vector
	 * with the rotated position.
	 *
	 * @param angle angle to be used for rotation of the vector, which is then converted
	 * into radians and utilized to calculate the new coordinates of the rotated vector.
	 *
	 * @returns a transformed vector with its components adjusted according to the given
	 * angle.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * Combines two `Vector2f` objects by adding corresponding x and y coordinates. It
	 * returns a new `Vector2f` object with the results, leaving the original vectors unchanged.
	 *
	 * @param r 2D vector to be added to the current object's position, returning a new
	 * `Vector2f` instance with the sum of the corresponding components.
	 *
	 * @returns a new `Vector2f` object with added x and y coordinates.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * Adds a scalar value to both components (x and y) of a given vector and returns a
	 * new instance of `Vector2f` with updated values. The original vector remains
	 * unchanged. The result is a new vector with incremented coordinates.
	 *
	 * @param r scalar value to be added to both the x and y coordinates of the vector.
	 *
	 * @returns a new `Vector2f` object with updated x and y values.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * Takes two float parameters and adds them to the current x and y values of a Vector2f
     * object, returning a new Vector2f object with the resulting values. The original
     * Vector2f object remains unchanged. This function enables vector addition operations
     * in a class-based representation of vectors.
     *
     * @param x 1st component of the vector to be added to the current vector's components.
     *
     * @param y 2D coordinate value to be added to the existing y-coordinate of the vector
     * object.
     *
     * @returns a new `Vector2f` object with updated coordinates.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * Subtracts another vector from the current one, resulting in a new vector with
	 * components calculated by subtracting corresponding elements of the input vector
	 * and the current vector. The function returns a new `Vector2f` object representing
	 * the subtraction result.
	 *
	 * @param r 2D vector to be subtracted from the current vector, having its x and y
	 * components used for the calculation of the resulting vector.
	 *
	 * @returns a new `Vector2f` object with subtracted coordinates.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * Subtracts a given float value from both the x and y components of a Vector2f object,
	 * returning a new Vector2f instance with the updated values. This effectively
	 * translates the vector by a specified amount. The original vector is not modified
	 * in the process.
	 *
	 * @param r 2D vector offset to be subtracted from the current position (`x` and `y`)
	 * of the object.
	 *
	 * @returns a new `Vector2f` object with coordinates subtracted by the input float value.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * Multiplies two vector objects with floating point components, combining their
	 * respective x and y values to produce a resulting vector. It returns a new object
	 * representing the product of the input vectors. The original vectors are not modified.
	 *
	 * @param r 2D vector to be multiplied with the current object's coordinates, returning
	 * a new vector resulting from the multiplication.
	 *
	 * @returns a new `Vector2f` object with scaled components.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * Multiplies a given scalar value with each component of a `Vector2f`. The result
	 * is a new `Vector2f` object created by scaling the original vector's x and y
	 * coordinates by the specified factor.
	 *
	 * @param r scalar value to be multiplied with the existing x and y components of the
	 * Vector2f object.
	 *
	 * @returns a new `Vector2f` object with scaled coordinates.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * Divides the current `Vector2f` by another vector specified as an argument, returning
	 * a new `Vector2f` object with its x and y components divided respectively by the
	 * corresponding components of the input vector.
	 *
	 * @param r 2D vector whose components are used to divide the current vector's
	 * corresponding components.
	 *
	 * @returns a new `Vector2f` instance with divided x and y components.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * Divides the x and y components of a given vector by a specified float value,
	 * resulting in a new vector with scaled coordinates. The operation is performed
	 * component-wise, meaning that each coordinate (x and y) is divided separately.
	 *
	 * @param r divisor that is used to divide both the x and y components of the current
	 * vector.
	 *
	 * @returns a new `Vector2f` instance with divided x and y coordinates.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * Returns a new `Vector2f` object with its components set to the absolute values of
	 * the corresponding components of the input vector. The absolute value of a number
	 * is its distance from zero, ignoring its sign. This function effectively removes
	 * any negative signs from the original vector's coordinates.
	 *
	 * @returns a new `Vector2f` object with absolute values of x and y.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * Returns a string representation of an object in the format "(x y)", where `x` and
	 * `y` are the values of instance variables `x` and `y`, respectively. This allows
	 * for easy conversion of the object to a human-readable string.
	 *
	 * @returns a string representation of an object with coordinates (x, y).
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * Updates the values of a Vector2f object with new floating-point values for its x
	 * and y components, replacing the original values. This function returns the updated
	 * vector object itself to facilitate method chaining.
	 *
	 * @param x 0-based horizontal coordinate to be assigned to the object's position,
	 * replacing its current value.
	 *
	 * @param y vertical component of the vector's coordinates and is assigned to the
	 * instance variable `this.y`.
	 *
	 * @returns an instance of `Vector2f` with updated values.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Updates the current object's x and y coordinates with the corresponding values
	 * from the provided `Vector2f` parameter `r`. This function then returns a reference
	 * to itself, allowing method chaining.
	 *
	 * @param r 2D vector that provides the new x and y coordinates for setting the current
	 * object's position.
	 *
	 * @returns an instance of `Vector2f`, with modified x and y values.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * Creates a new 3D vector with components x and y from its original state, and
     * initializes z to 0. The resulting 3D vector is then returned. This conversion
     * allows for the integration of the original data into a 3D context.
     *
     * @returns a new `Vector3f` object with x and y components.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * Returns a floating-point value represented by variable `x`. It does not modify any
	 * values and only provides access to the current state of `x`. The returned value
	 * is directly retrieved from `x`, without performing any calculations or operations
	 * on it.
	 *
	 * @returns a floating-point value representing the current value of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a float value to an instance variable `x`. It takes a single argument,
	 * `x`, and uses it to update the corresponding attribute of the class. This allows
	 * external code to modify the internal state of the object.
	 *
	 * @param x value to be assigned to the instance variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns a floating-point value representing the current state of the variable `y`.
	 * This value is directly retrieved without any modifications or calculations performed
	 * within the function. The returned result is simply the current value stored in `y`.
	 *
	 * @returns a floating-point value representing the current state of variable `y`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Assigns a new value to the instance variable `y`. It takes a single argument `y`
	 * of type `float`, updates the internal state of the object, and does not return any
	 * value. The updated value is stored internally for future use.
	 *
	 * @param y coordinate value to be assigned to the instance variable `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Compares two instances of a Vector2f object, checking if their x and y coordinates
	 * are identical. It returns a boolean value indicating whether the comparison is
	 * true or false.
	 *
	 * @param r 2D vector to be compared with the current object for equality, based on
	 * its x and y coordinates.
	 *
	 * @returns a boolean value indicating whether two vectors are identical.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
