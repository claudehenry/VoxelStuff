package com.ch.math;

/**
 * Represents a 2-dimensional vector with methods for basic arithmetic operations,
 * geometric calculations, and transformations.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance of a point from the origin in a two-dimensional
	 * space, based on its x and y coordinates. The result is returned as a floating-point
	 * number. This function is typically used to compute the magnitude of a vector.
	 *
	 * @returns the Euclidean distance of the point from the origin, as a floating-point
	 * number.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the maximum value between two variables `x` and `y`.
	 *
	 * @returns the larger of two float values, x and y.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * Calculates the dot product of the current vector and the input vector `r`, returning
	 * a float value. The dot product is a scalar value representing the amount of
	 * "similarity" between the two vectors. It is calculated by multiplying corresponding
	 * vector components and summing the results.
	 *
	 * @param r second vector in a dot product operation, used to compute the product of
	 * the current vector's components with the corresponding components of `r`.
	 *
	 * @returns the dot product of the calling object and the input vector `r`.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Normalizes a 2D vector by dividing its components by its magnitude, ensuring the
	 * vector's length equals 1. This operation preserves the direction while scaling the
	 * vector. The result is a unit vector.
	 *
	 * @returns a new Vector2f with components normalized to the unit circle.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * Computes the cross product of two vectors in 2D space, returning a scalar value
	 * representing the magnitude of the perpendicular vector.
	 *
	 * @param r right-hand side vector in a cross product operation.
	 *
	 * @returns the cross product of two vectors, which is a scalar value.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * Calculates a new vector by interpolating between the current vector and a destination
	 * vector based on a given lerp factor, resulting in a linearly interpolated vector.
	 * The lerp factor determines the proportion of the destination vector to add to the
	 * current vector.
	 *
	 * @param dest destination vector towards which the current vector is interpolated.
	 *
	 * @param lerpFactor amount of interpolation between the current position and the
	 * destination position.
	 *
	 * @returns a new Vector2f that is the interpolated result between the current vector
	 * and the destination vector.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * Performs element-wise addition of two vectors, creating a new vector with corresponding
	 * components increased by the components of the input vector. The original vectors
	 * remain unchanged.
	 *
	 * @param r vector to be added to the current vector.
	 *
	 * @returns a new Vector2f object with components being the sum of the input Vector2f's
	 * components.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * Adds a given scalar value `r` to the x and y components of a 2D vector, returning
	 * a new vector with the updated components.
	 *
	 * @param r amount to be added to the vector's x and y components.
	 *
	 * @returns a new vector with components x and y incremented by the specified float
	 * value r.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * Performs vector addition by adding the given x and y components to the current
     * vector's components, returning a new vector with the resulting components. It does
     * not modify the original vector.
     *
     * @param x x-coordinate to be added to the current x-coordinate of the vector.
     *
     * @param y y-component of a vector to be added to the current vector.
     *
     * @returns a new `Vector2f` object with its x and y components incremented by the
     * input values.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * Performs vector subtraction by creating a new vector with components equal to the
	 * difference between the current vector's components and the corresponding components
	 * of the input vector.
	 *
	 * @param r vector to be subtracted from the current vector.
	 *
	 * @returns a new `Vector2f` object representing the difference between the current
	 * vector and the input vector `r`.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * Subtracts a specified value from the x and y components of a Vector2f, returning
	 * a new Vector2f instance with the updated components.
	 *
	 * @param r amount to subtract from the x and y coordinates.
	 *
	 * @returns a new `Vector2f` instance with x and y coordinates reduced by the specified
	 * value `r`.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * Performs scalar multiplication of the current vector with another vector, resulting
	 * in a new vector with components that are the product of the corresponding components
	 * of the two input vectors.
	 *
	 * @param r vector to multiply with the current vector, with its components used to
	 * scale the current vector's components.
	 *
	 * @returns a new Vector2f object with components resulting from component-wise
	 * multiplication of the input vectors.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * Scales a 2D vector by a given scalar value, returning a new vector with its
	 * components multiplied by the scalar.
	 *
	 * @param r scalar value by which the vector's components are multiplied.
	 *
	 * @returns a new Vector2f object scaled by the given factor r.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * Performs element-wise division of the current vector by the input vector `r`,
	 * returning a new `Vector2f` object with the divided components.
	 *
	 * @param r vector that the function divides the current vector by, performing scalar
	 * division on its components.
	 *
	 * @returns a new `Vector2f` object with components resulting from dividing the current
	 * vector's components by the corresponding components of the input vector `r`.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	public String toString() {
		return "(" + x + " " + y + ")";
	}

	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * Returns the value of the variable `x` as a floating-point number.
	 *
	 * @returns the value of the instance variable `x`, a float.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of a float variable named `x` to the input parameter `x`.
	 * This allows the value of `x` to be updated dynamically.
	 * The value of `x` is assigned directly to the instance variable `x`.
	 *
	 * @param x new value to be assigned to the `x` field of the class.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of the `y` variable as a floating-point number. The function
	 * provides access to the `y` attribute, allowing it to be retrieved and used in
	 * calculations or other operations.
	 *
	 * @returns the value of the instance variable `y`, a float.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the instance variable `y` to the specified `float` value.
	 * This value is used to represent a y-coordinate.
	 *
	 * @param y new value to be assigned to the `y` field of the class.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Compares two `Vector2f` objects for equality by checking if their x and y coordinates
	 * are equal.
	 *
	 * @param r object being compared with the current object for equality.
	 *
	 * @returns a boolean value indicating whether two Vector2f objects have identical x
	 * and y coordinates.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
