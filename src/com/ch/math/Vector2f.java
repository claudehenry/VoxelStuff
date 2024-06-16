package com.ch.math;

/**
 * Is a mathematical representation of a 2D vector with x and y components. It provides
 * various methods for calculating and manipulating the vector's length, direction,
 * and angle, as well as methods for comparing vectors and converting between different
 * representations of the vector. Additionally, it includes high-level methods for
 * rotating, scaling, and adding/subtracting/multiplying vectors.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance between a point and the origin, represented by
	 * (0, 0).
	 * 
	 * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Calculates the maximum value of two floating-point numbers `x` and `y`. It returns
	 * the larger of the two values.
	 * 
	 * @returns the larger of `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * Calculates the dot product of a `Vector2f` instance `r` and the component-wise
	 * multiplication of its `x` and `y` values with those of the input `Vector2f`.
	 * 
	 * @param r 2D vector that provides the dot product value when multiplied by the `x`
	 * and `y` components of the function's output.
	 * 
	 * @returns a scalar value representing the dot product of the input vector and another
	 * vector.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Normalizes a given vector by dividing its components by the vector's magnitude,
	 * returning a new vector with normalized coordinates.
	 * 
	 * @returns a normalized vector with x and y components scaled by the length of the
	 * original vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * Computes the dot product of two vectors and returns the result as a scalar value.
	 * 
	 * @param r 2D vector to be crossed with the current vector.
	 * 
	 * @returns a floating-point value representing the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * Computes a vector interpolation between two input vectors, scaling the difference
	 * between them by the given factor and adding the result to the starting vector.
	 * 
	 * @param dest 2D destination vector to which the current vector is interpolated.
	 * 
	 * @param lerpFactor factor by which the current position is to be linearly interpolated
	 * towards the destination position.
	 * 
	 * @returns a new `Vector2f` object representing the interpolated value between the
	 * original vector and the destination vector.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Takes a float argument representing the angle of rotation in radians and returns
	 * a new vector with its x and y components rotated accordingly.
	 * 
	 * @param angle angle of rotation in radians, which is used to calculate the x and y
	 * components of the returned vector.
	 * 
	 * @returns a vector with x and y components that have been rotated by the specified
	 * angle.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * Calculates and returns a new `Vector2f` object with the sum of its own `x` and `y`
	 * components and those of the provided `r` argument.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the input vectors.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * Adds a scalar value to the vector's x and y components, returning a new vector
	 * with the updated coordinates.
	 * 
	 * @param r 2D vector that is added to the existing vector.
	 * 
	 * @returns a new `Vector2f` instance with the sum of the current vector's x and y
	 * coordinates and the input value added to it.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * Takes two floating-point values `x` and `y`, calculates their sum and returns a
     * new `Vector2f` object containing the result.
     * 
     * @param x 2D coordinate to be added to the current vector's position.
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
	 * Calculates the vector difference between the current vector and a provided reference
	 * vector, returning a new vector object.
	 * 
	 * @param r 2D vector that the method will subtract from the input `Vector2f`.
	 * 
	 * @returns a new `Vector2f` instance representing the difference between the input
	 * vector and the reference vector.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * Subtracts a `Vector2f` component from the input vector, resulting in a new vector
	 * with the difference between the input and the specified component.
	 * 
	 * @param r offset that is subtracted from the vector's `x` and `y` components to
	 * produce the resultant vector.
	 * 
	 * @returns a new `Vector2f` instance representing the difference between the original
	 * vector and the given value.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * Multiplies a `Vector2f` object by another `Vector2f` object, returning a new
	 * `Vector2f` object with the product of the two component values.
	 * 
	 * @param r 2D vector to be multiplied with the current vector.
	 * 
	 * @returns a new vector with the product of the input vector's x and y components
	 * multiplied by the input parameter vector's x and y components.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * Takes a scalar value `r` and returns a new `Vector2f` instance with the product
	 * of the existing vector's x-component and `r`, and the same y-component.
	 * 
	 * @param r scalar value that multiplies the x and y components of the `Vector2f`
	 * instance being manipulated.
	 * 
	 * @returns a vector with components scaled by the given scalar value.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * Takes a `Vector2f` argument `r` and returns a new `Vector2f` instance with
	 * x-coordinate calculated as `x / r.getX()` and y-coordinate calculated as `y / r.getY()`.
	 * 
	 * @param r 2D vector to divide by, and its value is used to calculate the output
	 * vector's x and y components.
	 * 
	 * @returns a vector with the same x-coordinate as the input vector, scaled by the
	 * reciprocal of the input vector's x-coordinate.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * Multiplies its input by a scalar value and returns a new `Vector2f` instance with
	 * scaled x and y components.
	 * 
	 * @param r scalar value used to divide the vector's components by.
	 * 
	 * @returns a vector with scaled X and Y components proportional to the input parameter
	 * `r`.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * Computes and returns a new `Vector2f` instance with the absolute values of its x
	 * and y components.
	 * 
	 * @returns a new `Vector2f` object containing the absolute values of the input
	 * vector's `x` and `y` components.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * Returns a string representation of a point in the form "(x,y)".
	 * 
	 * @returns a string representation of the object's state, consisting of two values
	 * separated by a space.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * Modifies the object's `x` and `y` fields to reflect the input values, returning a
	 * reference to the modified object.
	 * 
	 * @param x 2D coordinate value to assign to the `x` component of the `Vector2f` object.
	 * 
	 * @param y 2nd component of the `Vector2f` object being modified, and its value is
	 * assigned to the `y` field of the object.
	 * 
	 * @returns a reference to the modified `Vector2f` instance.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the x and y components of the vector to the corresponding values of the
	 * provided reference `r`.
	 * 
	 * @param r 2D vector to be set as the value of the method's return, which is a
	 * reference to an instance of the `Vector2f` class.
	 * 
	 * @returns a reference to the original `Vector2f` object, unchanged.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * Converts a vector of type `float[]` into a `Vector3f` object, returning a new
     * instance with the same components but with the `z`-component set to zero.
     * 
     * @returns a new `Vector3f` object containing the x, y, and z coordinates of the
     * input vector.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * Retrieves the value of the `x` field.
	 * 
	 * @returns a floating-point value representing the x coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the `x` field to the input float value provided in the argument
	 * list.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being manipulated by the function.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of its `y` field.
	 * 
	 * @returns the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of the field `y` to the argument passed as a float.
	 * 
	 * @param y float value to be assigned to the `y` field of the class instance being
	 * manipulated by the `setY()` method.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Compares a `Vector2f` instance with another provided as parameter, returning `true`
	 * if both x and y components are equal, otherwise `false`.
	 * 
	 * @param r 2D vector to be compared with the current vector.
	 * 
	 * @returns a boolean value indicating whether the vector's `x` and `y` components
	 * are equal to those of the provided vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
