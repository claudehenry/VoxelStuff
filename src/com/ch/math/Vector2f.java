package com.ch.math;

/**
 * Provides a set of methods for working with 2D vectors in a mathematical context.
 * It has fields for storing the x and y components of the vector, and various methods
 * for performing operations such as length, max, dot product, normalization, cross
 * product, lerping, rotating, adding and subtracting other vectors or scalars,
 * multiplying by other vectors, dividing by other vectors, and more. Additionally,
 * it provides a method for converting itself to a 3D vector and offers getters and
 * setters for the x and y components.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance between a point and the origin, taking into
	 * account both the x and y coordinates.
	 * 
	 * @returns the square root of the sum of the squares of the x and y coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Calculates the maximum value of two input values `x` and `y`, using the `Math.max()`
	 * method.
	 * 
	 * @returns the larger of the input values `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * Computes the dot product of a `Vector2f` object and a given `Vector2f` argument,
	 * returning the result as a float value.
	 * 
	 * @param r 2D vector to which the `x` and `y` components of the returned value should
	 * be dot-producted.
	 * 
	 * @returns a scalar value representing the dot product of the input vector and another
	 * vector.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Normalizes a `Vector2f` object by dividing its components by their corresponding
	 * lengths.
	 * 
	 * @returns a normalized version of the input vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * Computes the dot product of two vectors and returns the result as a single float
	 * value, with the x-component of the first vector multiplied by the y-component of
	 * the second vector and vice versa.
	 * 
	 * @param r 2D vector to be crossed with the current vector, resulting in a new 2D
	 * vector output.
	 * 
	 * @returns a floating-point number representing the dot product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * Computes a vector interpolating between two given vectors using linear interpolation.
	 * The input vectors are combined to form the destination vector, and the resulting
	 * vector is returned.
	 * 
	 * @param dest 2D destination vector that the `lerp` method will interpolate between
	 * the `this` parameter and the given `lerpFactor`.
	 * 
	 * @param lerpFactor amount of linear interpolation between the starting and ending
	 * positions of the vector, with values between 0 and 1 resulting in a smooth transition.
	 * 
	 * @returns a vector that interpolates between the original and destination vectors
	 * using the provided lerp factor.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Takes a float angle and returns a new `Vector2f` instance with its x-coordinate
	 * rotated by the angle and its y-coordinate unchanged.
	 * 
	 * @param angle angle of rotation in radians, which is used to calculate the x and y
	 * components of the output vector.
	 * 
	 * @returns a new `Vector2f` instance with its x-component rotated by the specified
	 * angle and its y-component unaffected.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * Adds a vector to the current vector. It takes another vector as input and returns
	 * a new vector representing the sum of both vectors.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the input vectors.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * Takes a floating-point value `r` and returns a new `Vector2f` instance representing
	 * the sum of the current vector's components plus `r`.
	 * 
	 * @param r addition to be applied to the `x` and `y` coordinates of the vector.
	 * 
	 * @returns a new `Vector2f` instance representing the sum of the original vector and
	 * the input float value.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * Adds two floating-point values to a `Vector2f` object, returning a new `Vector2f`
     * instance with the sum of the original's x and y components and the input x and y
     * values.
     * 
     * @param x 2D coordinate to be added to the current position of the vector.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` instance representing the sum of the input `x` and `y`
     * values.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * Takes a `Vector2f` argument `r` and returns a new `Vector2f` representing the
	 * difference between the input vector and the reference vector.
	 * 
	 * @param r 2D vector to be subtracted from the current vector.
	 * 
	 * @returns a new vector representing the difference between the input vector and the
	 * reference vector.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * Subtracts a given scalar value `r` from a `Vector2f` object's `x` and `y` components,
	 * returning a new `Vector2f` object with the differences.
	 * 
	 * @param r 2D vector offset to subtract from the original vector's components,
	 * resulting in the new vector output.
	 * 
	 * @returns a vector with the difference between the original vector's coordinates
	 * and the given value.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * Multiplies its input `Vector2f` by a given scalar value and returns the result as
	 * a new `Vector2f`.
	 * 
	 * @param r 2D vector that multiplies with the current vector, resulting in a new 2D
	 * vector.
	 * 
	 * @returns a vector with the product of the input vectors' x and y components.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * Multiplies its input vector by a scalar value, returning a new vector with the product.
	 * 
	 * @param r scaling factor applied to the `Vector2f` instance being multiplied.
	 * 
	 * @returns a new `Vector2f` instance with the product of the component values of the
	 * input vector and the scalar value `r`.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * Takes a `Vector2f` argument `r` and returns a new `Vector2f` object with the
	 * x-coordinate calculated as `x / r.getX()` and the y-coordinate calculated as `y /
	 * r.getY()`.
	 * 
	 * @param r vector that the current vector will be divided by.
	 * 
	 * @returns a vector with the same x-coordinate as the original vector, scaled by the
	 * reciprocal of the input vector's x-coordinate.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * Divides the components of a `Vector2f` object by a given scalar value, resulting
	 * in a new `Vector2f` object with scaled components.
	 * 
	 * @param r scalar value that the vector is to be divided by.
	 * 
	 * @returns a vector with x and y components scaled by the input parameter `r`.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * Computes and returns a vector with absolute values of its x and y components.
	 * 
	 * @returns a new `Vector2f` object containing the absolute values of the input
	 * vector's components.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * Returns a string representation of a point (x,y) by concatenating the values of x
	 * and y in parentheses.
	 * 
	 * @returns a string representation of the current point in the form (x, y).
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * Updates the `x` and `y` components of the `Vector2f` object, returning a reference
	 * to the same object for chaining calls.
	 * 
	 * @param x 2D vector's x-coordinate, which is being set to the provided value.
	 * 
	 * @param y 2D coordinate value that will be set for the `Vector2f` object.
	 * 
	 * @returns a reference to the modified `Vector2f` instance.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the x and y components of the object to the corresponding values of the
	 * provided `Vector2f` object.
	 * 
	 * @param r 2D vector to be set as the value of the `this` vector.
	 * 
	 * @returns a reference to the original `Vector2f` object, unchanged.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * Converts a `Vector2f` object to a `Vector3f` object by adding a third component
     * representing the z-coordinate, which is set to zero by default.
     * 
     * @returns a new `Vector3f` instance with the values `x`, `y`, and `0`.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * Retrieves the value of the `x` field, which is a `float` variable representing the
	 * X-coordinate of an object or point.
	 * 
	 * @returns the value of `x`, which is a float variable.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the `x` field of its object parameter to the provided float value.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class object.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of the `y` field.
	 * 
	 * @returns the value of the `y` field, which is a `float` variable.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the `y` field of its receiver to the argument passed as a float.
	 * 
	 * @param y vertical position of an object or point in a two-dimensional space, and
	 * it is assigned to the field `y` of the class.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Compares a `Vector2f` object to another, returning `true` if the object's `x` and
	 * `y` values match those of the compared object, otherwise `false`.
	 * 
	 * @param r 2D vector to be compared with the current vector for equality.
	 * 
	 * @returns a boolean value indicating whether the vector's x and y coordinates are
	 * equal to those of the provided vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
