package com.ch.math;


public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculates the Euclidean distance between a point and its origin, based on the
	 * square of the coordinates' magnitudes.
	 * 
	 * @returns the square root of the sum of the squares of its input arguments, expressed
	 * as a floating-point value.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * computes and returns the maximum value of two arguments `x` and `y`.
	 * 
	 * @returns the larger of the input values `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * computes the dot product of a `Vector2f` object `r` and the object passed as an
	 * argument, returning the result as a float value.
	 * 
	 * @param r 2D vector to which the current vector is dot-producted.
	 * 
	 * @returns a scalar value representing the dot product of two vectors.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a `Vector2f` instance by dividing its components by their respective
	 * magnitudes, resulting in a new vector with a length of 1.
	 * 
	 * @returns a normalized vector with x and y components calculated as a fraction of
	 * the original vector's length.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * takes a `Vector2f` object `r` as input and computes the vector product of two
	 * vectors, returning the result as a single float value.
	 * 
	 * @param r 2D vector to be crossed with the current vector.
	 * 
	 * @returns a floating-point value representing the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * interpolates between two `Vector2f` values, `dest` and the current position, using
	 * a factor `lerpFactor`. It returns the interpolated position as a new `Vector2f` object.
	 * 
	 * @param dest 2D target position towards which the current position is being interpolated.
	 * 
	 * @param lerpFactor linear transition rate between the current position of the
	 * `Vector2f` instance and the target `dest` position.
	 * 
	 * @returns a vector that interpolates between the input `this` vector and the
	 * destination `dest` vector.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a float parameter `angle`, converts it to radians, computes the cosine and
	 * sine values, and returns a new `Vector2f` instance with its x-coordinate modified
	 * by the product of the cosine and the original vector's x-coordinate, and its
	 * y-coordinate modified by the product of the sine and the original vector's y-coordinate.
	 * 
	 * @param angle angle of rotation in radians, which is used to calculate the x and y
	 * components of the returned vector.
	 * 
	 * @returns a new `Vector2f` object with the x and y coordinates rotated by the given
	 * angle.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * takes a `Vector2f` object as input and returns a new `Vector2f` object with the
	 * sum of the current object's x and y components and those of the input argument.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * @returns a new vector with the sum of the inputs.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * adds a floating-point number to the vector's `x` and `y` components, returning a
	 * new vector with the modified coordinates.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector2f` object with the sum of the original `x` and `y` coordinates
	 * and the input `r`.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * adds two floating-point numbers to a `Vector2f` object, returning a new `Vector2f`
     * object with the result.
     * 
     * @param x 2D coordinate to add to the current position of the `Vector2f` object.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` instance with the sum of the inputs' `x` and `y` coordinates.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a reference to another `Vector2f` object `r` and returns a new `Vector2f`
	 * object representing the difference between the two vectors in terms of their x-
	 * and y-coordinates.
	 * 
	 * @param r 2D vector to which the current vector will be subtracted.
	 * 
	 * @returns a vector that represents the difference between the input vector and the
	 * reference vector.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a single floating-point argument `r` and returns a new `Vector2f` object
	 * representing the difference between the original vector's coordinates and `r`.
	 * 
	 * @param r 2D vector displacement from the original position of the `Vector2f`
	 * instance, which is then subtracted to produce the new 2D vector output.
	 * 
	 * @returns a new `Vector2f` instance representing the difference between the input
	 * `r` and the current position of the object.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies the vector by another vector, returning a new vector with the product
	 * of the componentwise multiplication of the two vectors' coordinates.
	 * 
	 * @param r 2D vector that multiplies with the current vector, resulting in a new 2D
	 * vector output.
	 * 
	 * @returns a new vector with the product of the input vectors' x and y components.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies the components of a `Vector2f` instance by a floating-point value.
	 * 
	 * @param r scalar value that multiplies the values of the `x` and `y` components of
	 * the returned `Vector2f` object.
	 * 
	 * @returns a new `Vector2f` object with the product of the `x` and `y` components
	 * of the input vector multiplied by the scalar `r`.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a reference to a `Vector2f` object `r` and returns a new `Vector2f` object
	 * with the same components, but with the values scaled by the reciprocal of the
	 * corresponding values of `r`.
	 * 
	 * @param r 2D vector that the method will divide the current vector by.
	 * 
	 * @returns a vector with the same x-coordinates as the input vector divided by the
	 * x-coordinate of the second argument, and the same y-coordinates as the input vector
	 * divided by the y-coordinate of the second argument.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a scalar value `r` and returns a vector with the same x and y components
	 * scaled by the reciprocal of `r`.
	 * 
	 * @param r scalar value used to perform the division of the `x` and `y` components
	 * of the `Vector2f` instance.
	 * 
	 * @returns a vector with x and y components scaled by the same factor.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * returns a new `Vector2f` object containing the absolute values of its x and y components.
	 * 
	 * @returns a new `Vector2f` object containing the absolute values of the input
	 * coordinates, x and y.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of a point with x and y coordinates.
	 * 
	 * @returns a string consisting of the values of `x` and `y` separated by a space.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * sets the `x` and `y` properties of the `Vector2f` object to the input values, and
	 * returns a reference to the same object.
	 * 
	 * @param x 2D coordinate of the point at which the vector's x component will be set.
	 * 
	 * @param y 2nd component of the vector and updates its value to match the new value
	 * passed as an argument to the `set()` function.
	 * 
	 * @returns a reference to the same `Vector2f` object, with its `x` and `y` components
	 * set to the provided values.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the x and y components of the current vector to the corresponding values of
	 * the given `Vector2f` object, and returns a reference to the current vector.
	 * 
	 * @param r 2D vector to be set as the new value for the `Vector2f` object.
	 * 
	 * @returns a reference to the same `Vector2f` instance, unchanged.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * transforms a given vector into an equivalent 3D vector with x-coordinate set to
     * 0, while returning a new instance of the `Vector3f` class.
     * 
     * @returns a 3D vector with x-coordinates of 0, y-coordinates of 0, and z-coordinate
     * of the value passed as input.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns a float value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the instance field `x` to the argument passed to it.
	 * 
	 * @param x float value that sets the instance variable `x` of the class to which the
	 * `setX()` method belongs.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which represents the vertical position of
	 * an object in a two-dimensional space.
	 * 
	 * @returns the value of `y`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the instance field `y` to the argument passed in.
	 * 
	 * @param y 2D coordinate of the object being manipulated by the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares two `Vector2f` objects based on their `x` and `y` coordinates, returning
	 * `true` if they are equal, and `false` otherwise.
	 * 
	 * @param r 2D vector to which the current vector is compared for equality.
	 * 
	 * @returns a boolean value indicating whether the input vector has the same x and y
	 * coordinates as the current vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
