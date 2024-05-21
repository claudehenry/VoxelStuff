package com.ch.math;

/**
 * is used to represent 2D points in mathematics and graphics. It has several methods
 * for calculating distances, magnitudes, and angles between vectors, as well as
 * methods for scaling, rotating, and adding vectors. The class also provides methods
 * for getting and setting the x and y components of a vector.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculates the Euclidean distance of a point in 2D space from its coordinates,
	 * using the square root of the sum of the squares of the x and y coordinates as the
	 * result.
	 * 
	 * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * computes the maximum value of two floating-point arguments `x` and `y`, and returns
	 * it as a float value.
	 * 
	 * @returns the larger of the two input values, `x` and `y`, represented as a float
	 * value.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * computes the dot product of a `Vector2f` instance and another vector.
	 * 
	 * @param r 2D vector to be dot-producted with the current vector.
	 * 
	 * The input `r` is a `Vector2f` object, which represents a two-dimensional vector
	 * in floating-point representation. It has two components: `x` and `y`, which represent
	 * the coordinates of the vector in the x-axis and y-axis, respectively.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and another vector.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a vector by dividing its components by the vector's length, resulting
	 * in a unitized vector with a length of 1.
	 * 
	 * @returns a normalized vector with x and y components scaled proportionally to their
	 * respective lengths.
	 * 
	 * 	- The output is a `Vector2f` object, representing a normalized version of the
	 * original vector.
	 * 	- The `x` and `y` components of the output are calculated as fractions of the
	 * length of the original vector, which is calculated using the `length()` method.
	 * 	- The resulting values have a range of 0 to 1, representing a normalized value
	 * that can be used in various mathematical operations without affecting the magnitude
	 * of the original vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * computes the dot product of two vectors and returns the result as a float value.
	 * 
	 * @param r 2D vector to be multiplied with the current vector, resulting in the cross
	 * product.
	 * 
	 * 	- `r` is a `Vector2f` object representing a 2D point.
	 * 	- It has two components: `x` and `y`, which represent the x- and y-coordinates
	 * of the point, respectively.
	 * 
	 * @returns a floating-point value representing the dot product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * computes a vector interpolation between two given vectors, weighted by the specified
	 * factor. The resulting vector is the sum of the intermediate vector created by
	 * multiplying the difference between the two input vectors and the lerp factor, and
	 * then adding the starting vector to that result.
	 * 
	 * @param dest 2D destination vector that the current vector will be interpolated towards.
	 * 
	 * 	- `dest` is a `Vector2f` object representing a 2D point in homogeneous coordinates
	 * (x, y).
	 * 	- `lerpFactor` is a floating-point value representing the interpolation factor
	 * between the current position and the destination position.
	 * 
	 * The function computes the interpolated position by performing the following operations:
	 * 
	 * 1/ Subtracting the current position from the destination position using `sub()` method.
	 * 2/ Multiplying the resultant vector by the interpolation factor using `mul()` method.
	 * 3/ Adding the current position to the resulting vector using `add()` method, thereby
	 * obtaining the interpolated position.
	 * 
	 * @param lerpFactor factor by which the current vector is to be linearly interpolated
	 * towards the `dest` vector.
	 * 
	 * @returns a Vector2f object representing the interpolated position between the
	 * original vector and the destination vector.
	 * 
	 * 1/ The output is a `Vector2f` object representing the linear interpolation between
	 * the input `Vector2f` `dest` and the current position of the entity.
	 * 2/ The interpolation factor `lerpFactor` is used to determine how much of the
	 * destination vector should be mixed with the current position.
	 * 3/ The output has the same x-coordinate as the input `dest`, but its y-coordinate
	 * is calculated by interpolating between the y-coordinate of the input and the
	 * y-coordinate of the current position.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a float argument representing the angle of rotation in radians and returns
	 * a new vector with the rotated coordinates. The formula used is based on the sine
	 * and cosine of the angle of rotation.
	 * 
	 * @param angle angle of rotation in radians, which is used to calculate the cosine
	 * and sine components of the resulting vector.
	 * 
	 * @returns a rotated vector in the xy-plane, where the angle of rotation is given
	 * as a parameter.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point with x and y coordinates.
	 * 	- The x coordinate of the output is calculated as the product of the original x
	 * coordinate and the cosine of the angle of rotation, minus the product of the
	 * original y coordinate and the sine of the angle of rotation.
	 * 	- The y coordinate of the output is calculated as the product of the original x
	 * coordinate and the sine of the angle of rotation, plus the product of the original
	 * y coordinate and the cosine of the angle of rotation.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * takes a `Vector2f` argument `r`, and returns a new `Vector2f` object with the sum
	 * of its own x-coordinate and the x-coordinate of `r`, and the same y-coordinate as
	 * itself.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * `r`: A `Vector2f` object containing the x-coordinate `x` and y-coordinate `y`.
	 * 
	 * @returns a new vector with the sum of the input vectors' x and y coordinates.
	 * 
	 * 	- The output is a new `Vector2f` object, created by adding the `x` and `y`
	 * components of the input arguments.
	 * 	- The resulting vector has the same component values as the input vectors added
	 * together.
	 * 	- The magnitude (length) of the output vector is the sum of the magnitudes of the
	 * input vectors.
	 * 	- The direction of the output vector is the result of adding the directions of
	 * the input vectors.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * adds a scalar value to a `Vector2f` object's components.
	 * 
	 * @param r 2D vector to add to the current vector.
	 * 
	 * @returns a new `Vector2f` object with an x-coordinate equal to the sum of the
	 * current vector's x-coordinate and the input float value, and a y-coordinate equal
	 * to the sum of the current vector's y-coordinate and the input float value.
	 * 
	 * The returned object is a `Vector2f`, which represents a 2D position with x and y
	 * components. The x and y components of the output are calculated by adding the input
	 * parameter `r` to the existing values of the `x` and `y` components of the original
	 * object.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * adds two floating-point values to the current vector's position, returning a new
     * vector with the sum of the original vector's position and the given values.
     * 
     * @param x 2D coordinate to be added to the existing coordinates of the `Vector2f`
     * instance.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` instance representing the sum of the current vector's
     * components and the input `x` and `y` values.
     * 
     * 	- The output is a new instance of the `Vector2f` class.
     * 	- Its x-coordinate is equal to the sum of the x-coordinates of the two input vectors.
     * 	- Its y-coordinate is equal to the sum of the y-coordinates of the two input vectors.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` object with the
	 * difference between the current vector's components and the given vector's components.
	 * 
	 * @param r 2D vector that the function will subtract from the original vector to
	 * produce the result.
	 * 
	 * `x`: The first component of `r`, which represents the x-coordinate of the vector.
	 * 
	 * `y`: The second component of `r`, which represents the y-coordinate of the vector.
	 * 
	 * @returns a new vector with the difference between the input vector's components
	 * and the given reference vector's components.
	 * 
	 * 	- The return type is a `Vector2f` which represents a 2D point with floating-point
	 * coordinates.
	 * 	- The value of the returned vector is calculated by subtracting the `x` and `y`
	 * coordinates of the `r` argument from the `x` and `y` coordinates of the function's
	 * output.
	 * 	- The resulting vector has the same orientation as the original vector, but its
	 * magnitude is reduced by the difference between the `x` and `y` coordinates of the
	 * `r` argument and the `x` and `y` coordinates of the function's output.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a scalar parameter `r` and returns a new `Vector2f` instance representing
	 * the difference between the current vector's components and `r`.
	 * 
	 * @param r 2D vector to subtract from the current vector.
	 * 
	 * @returns a vector with the difference between the input `r` and the current position
	 * of the object.
	 * 
	 * 	- The output is a `Vector2f` object representing the difference between the
	 * original input vector's `x` and `y` components and the input `r`.
	 * 	- The resulting vector has the same coordinates as the original input vector, but
	 * with the values of `x` and `y` reduced by the value of `r`.
	 * 	- The returned vector has the same direction as the original input vector.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies the vector `this` by a given vector `r`, returning a new vector with
	 * the product of the corresponding components.
	 * 
	 * @param r 2D vector to which the current vector is multiplied, resulting in a new
	 * 2D vector output.
	 * 
	 * 	- `r` is a `Vector2f` object representing a 2D vector with x and y components.
	 * 	- The `x` and `y` properties of `r` represent the x- and y-coordinates of the
	 * vector, respectively.
	 * 
	 * @returns a new vector with the product of the input vectors' x and y components.
	 * 
	 * The output is a new `Vector2f` object, representing the product of the input vectors.
	 * 
	 * The `x` component of the output is equal to the product of the `x` component of
	 * the input vector `r` multiplied by the input vector `u`.
	 * 
	 * Similarly, the `y` component of the output is equal to the product of the `y`
	 * component of the input vector `r` multiplied by the input vector `u`.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies a vector's x and y components by a given scalar value, returning a new
	 * vector with the product.
	 * 
	 * @param r scalar value that is multiplied with the vector's x and y components to
	 * produce the resultant vector.
	 * 
	 * @returns a vector with x and y components scaled by the input value `r`.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point in homogeneous
	 * coordinates.
	 * 	- The `x` and `y` components of the output are determined by multiplying the
	 * original `x` and `y` components of the input vector by the scalar value `r`.
	 * 	- The resulting output has the same dimension as the input, meaning that it is a
	 * valid 2D point in homogeneous coordinates.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` object with x and y
	 * components calculated as the ratio of the input vector's x and y coordinates to
	 * the corresponding values of `r`.
	 * 
	 * @param r 2D vector to which the current vector will be divided.
	 * 
	 * 	- `x`: The x-coordinate of `r`.
	 * 	- `y`: The y-coordinate of `r`.
	 * 
	 * @returns a new `Vector2f` object with scaled coordinates proportional to the input
	 * `r`.
	 * 
	 * The return value is a new Vector2f object containing the result of dividing the
	 * current vector's x-component by the input vector's x-component and the current
	 * vector's y-component by the input vector's y-component.
	 * 
	 * The resulting vector has the same magnitude as the original vector, but its direction
	 * is adjusted to be the reciprocal of the input vector. This means that if the input
	 * vector points in the positive direction of the x-axis, the returned vector will
	 * point in the negative direction of the x-axis, and vice versa for the y-axis.
	 * 
	 * The return value has a length of 1 by default, which indicates that the divided
	 * vector has the same magnitude as the original vector. However, this can be modified
	 * by multiplying the returned vector with a non-zero scalar value.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a single float argument `r` and returns a `Vector2f` object with x-coordinate
	 * and y-coordinate scaled by the ratio of `r`.
	 * 
	 * @param r scaling factor applied to the vector's x and y components in the division
	 * operation.
	 * 
	 * @returns a vector with x and y components scaled by the same factor as the input
	 * `r`.
	 * 
	 * The `Vector2f` object represents a two-dimensional vector with real-valued coordinates
	 * x and y. The returned value is a scaled version of the original input vector, where
	 * each coordinate has been divided by the input parameter r. As a result, the magnitude
	 * (or length) of the output vector is proportional to the ratio of the original
	 * vector's magnitude to the input parameter r.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * computes and returns a new `Vector2f` instance with the absolute values of its `x`
	 * and `y` components.
	 * 
	 * @returns a new `Vector2f` instance with the absolute values of its input components.
	 * 
	 * The `Vector2f` object returned by the function represents the absolute value of
	 * the original `x` and `y` coordinates, respectively.
	 * 
	 * The x-coordinate of the returned vector is equal to the absolute value of the
	 * original `x` coordinate.
	 * 
	 * Similarly, the y-coordinate of the returned vector is equal to the absolute value
	 * of the original `y` coordinate.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of a point (x,y) using parentheses and the values
	 * of x and y as arguments.
	 * 
	 * @returns a string representing the coordinates of a point as (x,y).
	 * 
	 * The output is a string that consists of two components separated by a space. The
	 * first component is the value of the `x` field, and the second component is the
	 * value of the `y` field.
	 * 
	 * The concatenation operator (`+`) is used to combine the two components into a
	 * single string. This allows for a simple and concise representation of the object's
	 * state in a string format.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * sets the `x` and `y` properties of a `Vector2f` object to the provided values,
	 * returning the modified object.
	 * 
	 * @param x 2D coordinate value that sets the horizontal position of the `Vector2f`
	 * instance.
	 * 
	 * @param y 2nd component of the `Vector2f` object, which is updated to match the
	 * provided value.
	 * 
	 * @returns a reference to the same instance of the `Vector2f` class with updated `x`
	 * and `y` values.
	 * 
	 * The output is a reference to the same `Vector2f` instance as the input parameter
	 * `this`. This means that modifying the output does not create a new object but
	 * rather updates the existing one.
	 * 
	 * The `x` and `y` fields of the output are set to the input values `x` and `y`, respectively.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the x and y components of the vector to the corresponding values of a given
	 * vector argument, returning the modified vector instance.
	 * 
	 * @param r 2D vector that contains the x and y coordinates to be set in this function.
	 * 
	 * 	- `getX()` and `getY()`: These are methods that return the x-coordinates and
	 * y-coordinates of the vector, respectively.
	 * 
	 * @returns a reference to the same `Vector2f` object, unchanged.
	 * 
	 * 	- The `Vector2f` object is modified by setting its x-coordinate to `r.getX()` and
	 * its y-coordinate to `r.getY()`.
	 * 	- The returned object retains its original attributes, including its original
	 * values for x and y.
	 * 	- The function returns a reference to the same `Vector2f` object that was passed
	 * as an argument, indicating that the modification is done in place.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * creates a new `Vector3f` object from the provided `x`, `y`, and `z` values.
     * 
     * @returns a `Vector3f` object representing a 3D vector with an x-coordinate of `x`,
     * a y-coordinate of `y`, and a z-coordinate of 0.
     * 
     * The `Vector3f` object is created with three components - `x`, `y`, and `z`, which
     * are initialized to the values of `x`, `y`, and `0`, respectively. These components
     * represent the position of the vector in 3D space.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns a floating-point value representing the x coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the object to which it belongs.
	 * 
	 * @param x float value that will be assigned to the field `x` of the class object
	 * being passed as an argument to the function.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of a `y` field, which is a `float`.
	 * 
	 * @returns a floating-point value representing the y-coordinate of a point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field of its object to the provided float value.
	 * 
	 * @param y value to be assigned to the `y` field of the object upon which the `setY()`
	 * method is being called.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares the object's `x` and `y` values with those of the provided `Vector2f`
	 * object, returning `true` if they match.
	 * 
	 * @param r 2D vector to be compared with the current vector for equality.
	 * 
	 * 	- `x`: The first component (x-coordinate) of `r`.
	 * 	- `y`: The second component (y-coordinate) of `r`.
	 * 
	 * @returns a boolean value indicating whether the vector's components are equal to
	 * those of the provided vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
