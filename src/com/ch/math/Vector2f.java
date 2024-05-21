package com.ch.math;

/**
 * has various methods for manipulating and querying vector values, including length,
 * max, dot product, normalization, cross product, rotation, addition, subtraction,
 * multiplication, division, absolute value, and string representation.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculates the Euclidean distance of a point from its origin, using the Pythagorean
	 * theorem to calculate the square of the x and y coordinates, and then taking the
	 * square root of the sum to obtain the length.
	 * 
	 * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * computes the maximum value of two floating-point arguments `x` and `y`, returning
	 * the result as a float.
	 * 
	 * @returns the maximum value of `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * computes the dot product of a `Vector2f` instance `r` with the current object,
	 * returning the result as a floating-point value.
	 * 
	 * @param r 2D vector that dot product is being calculated with.
	 * 
	 * 	- `x`: The real value of the first component of `r`.
	 * 	- `y`: The real value of the second component of `r`.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and another vector.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a `Vector2f` object by dividing its components by their magnitudes,
	 * resulting in a new vector with a length of 1.
	 * 
	 * @returns a new vector with a magnitude equal to the length of the original vector,
	 * and a direction that points in the same direction as the original vector.
	 * 
	 * The returned vector has a magnitude equal to the length of the original vector
	 * divided by its own length. This means that the magnitude of the normalized vector
	 * is always between 0 and 1.
	 * 
	 * The direction of the normalized vector is the same as that of the original vector.
	 * 
	 * The normalized vector has the same units as the original vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * computes the vector product of two vectors, returning a scalar value representing
	 * the dot product of their component-wise products.
	 * 
	 * @param r 2D vector that the function will perform the cross product with.
	 * 
	 * 	- `r` is a `Vector2f` data type representing a 2D vector with `x` and `y` attributes.
	 * 	- The `x` attribute of `r` represents the x-coordinate of the vector, while the
	 * `y` attribute represents the y-coordinate.
	 * 	- The `getY()` and `getX()` methods are used to access the coordinates of the vector.
	 * 
	 * @returns a float value representing the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * computes a linear interpolation between two `Vector2f` objects, `dest` and `this`,
	 * based on the given `lerpFactor`. It returns a new `Vector2f` object representing
	 * the interpolated position.
	 * 
	 * @param dest 2D vector to which the current vector will be interpolated.
	 * 
	 * 	- `dest` is a `Vector2f` class instance representing a 2D vector with x and y components.
	 * 	- The `x` component of `dest` represents the starting position of the linear interpolation.
	 * 	- The `y` component of `dest` represents the starting position of the linear interpolation.
	 * 	- The `lerpFactor` parameter is a floating-point value representing the interpolation
	 * factor between the current position and the destination position.
	 * 
	 * @param lerpFactor factor by which the current position of the vector is to be
	 * interpolated towards the destination position.
	 * 
	 * @returns a new `Vector2f` object representing the interpolation between the current
	 * vector and the destination vector.
	 * 
	 * The `Vector2f` object returned by the function is the result of interpolating the
	 * given `dest` vector with the current vector using the specified `lerpFactor`.
	 * 
	 * The resulting vector has a magnitude that is a weighted combination of the magnitudes
	 * of the `dest` and current vectors, where the weights are determined by the `lerpFactor`.
	 * 
	 * The direction of the returned vector is a linear combination of the directions of
	 * the `dest` and current vectors, again weighted by the `lerpFactor`.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * rotates a `Vector2f` object by an angle in radians, returning a new `Vector2f`
	 * object with its x and y components shifted according to the sin and cosine of the
	 * angle.
	 * 
	 * @param angle 2D rotation angle in radians.
	 * 
	 * @returns a new `Vector2f` instance with the x and y components rotated by the given
	 * angle.
	 * 
	 * The output is a `Vector2f` object, representing a 2D point in homogeneous coordinates.
	 * The x-component represents the horizontal position of the point after rotation,
	 * while the y-component represents the vertical position. The values of x and y are
	 * calculated using the cosine and sine of the angle of rotation, which are derived
	 * from the `Math.toRadians` method.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * adds two vector objects and returns a new vector object with the sum of the
	 * x-coordinates and y-coordinates of the input vectors.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * 	- `x`: The x-coordinate of `r`, which is an instance of `Vector2f`.
	 * 	- `y`: The y-coordinate of `r`, which is also an instance of `Vector2f`.
	 * 
	 * @returns a new `Vector2f` instance with the sum of the input vectors' x and y components.
	 * 
	 * 	- The returned vector has the sum of the input vectors' x-coordinates and
	 * y-coordinates as its new value.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * adds a given value to the existing vector's x and y components, returning a new
	 * vector with the updated values.
	 * 
	 * @param r 2D vector addition to be performed on the existing `Vector2f` instance.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the current vector and
	 * the given float value.
	 * 
	 * The `Vector2f` object returned by the function represents a point in 2D space with
	 * x-coordinate equal to the sum of the original vector's x-coordinate and the input
	 * parameter r, and y-coordinate equal to the sum of the original vector's y-coordinate
	 * and the input parameter r.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * adds the `x` and `y` parameters to the current vector's `x` and `y` values, returning
     * a new vector with the sum.
     * 
     * @param x 2D coordinate to be added to the existing vector's X component.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` instance representing the sum of the input `x` and `y`
     * values.
     * 
     * The `Vector2f` object returned by the function is a new instance that represents
     * the sum of the original object's `x` and `y` components and the input `x` and `y`
     * values.
     * 
     * The returned object has the same attributes as the original object, including its
     * `x` and `y` components, which are calculated by adding the input `x` and `y` values
     * to the original object's `x` and `y` components.
     * 
     * The returned object is a new instance, so it does not share any state with the
     * original object.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` instance with the
	 * difference between the current vector's components and the passed vector's components.
	 * 
	 * @param r 2D vector to be subtracted from the current vector.
	 * 
	 * 	- `x`: The real component of the vector.
	 * 	- `y`: The imaginary component of the vector.
	 * 
	 * @returns a new vector with the difference between the input vector's coordinates
	 * and the reference vector's coordinates.
	 * 
	 * 	- The returned Vector2f object has an x-coordinate that is equal to the difference
	 * between the input vector's x-coordinate and the parameter vector's x-coordinate.
	 * 	- The returned Vector2f object has a y-coordinate that is equal to the difference
	 * between the input vector's y-coordinate and the parameter vector's y-coordinate.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a single floating-point argument `r` and returns a new `Vector2f` instance
	 * representing the difference between the current vector's coordinates and `r`.
	 * 
	 * @param r 2D vector to subtract from the current vector.
	 * 
	 * @returns a new `Vector2f` object representing the difference between the original
	 * vector and the given value.
	 * 
	 * 	- The output is a `Vector2f` object representing a 2D vector with two components
	 * - `x` and `y`.
	 * 	- The `x` component represents the distance from the original position in the
	 * x-axis direction.
	 * 	- The `y` component represents the distance from the original position in the
	 * y-axis direction.
	 * 	- The returned vector has the same magnitude as the original vector, but its
	 * direction is reversed due to the subtraction operation.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies its input vector by a scalar value, returning a new vector with the product.
	 * 
	 * @param r 2D vector to which the current vector is multiplied, resulting in a new
	 * 2D vector output.
	 * 
	 * 	- `x` and `y` are the properties of the vector that represent the x-axis and
	 * y-axis values, respectively.
	 * 
	 * @returns a new `Vector2f` object with the product of the input vectors' x and y components.
	 * 
	 * The output is a new `Vector2f` instance that represents the product of the input
	 * vectors `x` and `r`. The first component of the output is calculated as `x *
	 * r.getX()`, while the second component is calculated as `y * r.getY()`. Therefore,
	 * the output vector has the same magnitude as the input vectors, but its direction
	 * is modified to be the result of multiplying the two input vectors' directions.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies a vector's components by a given scalar value and returns a new vector
	 * with the multiplied values.
	 * 
	 * @param r scalar value that is multiplied with the current vector's coordinates,
	 * resulting in a new vector with the product of the coordinates and the scalar value.
	 * 
	 * @returns a vector with components that are the product of the corresponding
	 * coordinates and the scalar value `r`.
	 * 
	 * The output is a `Vector2f` object, which represents a 2D point with two components
	 * - `x` and `y`. The components are multiplied by the input parameter `r`, resulting
	 * in a new vector with the same magnitude but a different direction.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a reference to another `Vector2f` object `r` and returns a new `Vector2f`
	 * object with the values of `x / r.getX()` and `y / r.getY()`.
	 * 
	 * @param r 2D vector that the method divides the original vector by.
	 * 
	 * 	- `x`: The first component of `r`, which is a floating-point number.
	 * 	- `y`: The second component of `r`, which is also a floating-point number.
	 * 
	 * @returns a new `Vector2f` object with scaled coordinates based on the division of
	 * the input vector's coordinates by the input argument.
	 * 
	 * 	- The output is of type Vector2f, which represents a 2D point with floating-point
	 * coordinates.
	 * 	- The x and y components of the output are calculated by dividing the corresponding
	 * components of the input vector by the corresponding components of the reference vector.
	 * 	- The output has the same direction and magnitude as the input vector, but its
	 * size is reduced by the factor of division.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a single float argument `r` and returns a new `Vector2f` instance with x and
	 * y components scaled by the ratio of `r`.
	 * 
	 * @param r scaling factor used to divide the vector's x and y components.
	 * 
	 * @returns a vector with x and y components scaled by the given factor.
	 * 
	 * 	- The output is a `Vector2f` object with two components: `x` and `y`.
	 * 	- The value of each component is calculated by dividing the corresponding element
	 * of the input vector by the argument `r`.
	 * 	- The resultant vector has the same magnitude as the input vector, but its direction
	 * is reversed, i.e., it points in the opposite direction of the input vector.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * takes a `Vector2f` object and returns a new `Vector2f` object with the absolute
	 * value of its components.
	 * 
	 * @returns a new vector with the absolute values of the input vector's x and y components.
	 * 
	 * The function returns a new Vector2f object containing the absolute value of the
	 * original vector's x-coordinate and y-coordinate, respectively.
	 * 
	 * Each component of the returned Vector2f has an absolute value between 0 and 1,
	 * inclusive, since the inputs x and y are constrained to the same range.
	 * 
	 * The Vector2f object returned by the function has its own set of properties, such
	 * as magnitude (or length), direction, and angle from the origin, which can be used
	 * for various purposes in mathematical and scientific computations.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of a point (x,y) by concatenating the values of x
	 * and y inside parentheses.
	 * 
	 * @returns a string representation of a point in Cartesian coordinates, consisting
	 * of two numbers separated by a space.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * updates the `x` and `y` components of the `Vector2f` object by assigning new values
	 * to them directly, returning a reference to the same object for further manipulation.
	 * 
	 * @param x 2D coordinates of the vector and assigns the value to the `x` field of
	 * the `Vector2f` object.
	 * 
	 * @param y 2D coordinate of the vector in the Y-axis direction, which is updated to
	 * match the value passed as an argument when the `set()` method is called.
	 * 
	 * @returns a reference to the same instance of the `Vector2f` class, with the `x`
	 * and `y` components updated to the specified values.
	 * 
	 * The `Vector2f` object is mutated to have the new values assigned to its x and y components.
	 * 
	 * The return value of the function is the same instance of `Vector2f` that was passed
	 * as an argument, indicating that the method is a reference-returning method.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the position of the `Vector2f` object to the values of the given `Vector2f`
	 * object.
	 * 
	 * @param r 2D vector to set the values of the `Vector2f` object.
	 * 
	 * 	- `getX()` returns the x-coordinate of the vector.
	 * 	- `getY()` returns the y-coordinate of the vector.
	 * 
	 * @returns a reference to the original `Vector2f` object, with its x and y components
	 * set to the values passed as arguments.
	 * 
	 * The `Vector2f` object is updated with the new values for x and y, which are passed
	 * as parameters.
	 * After updating the values, the function returns a reference to the same `Vector2f`
	 * object, indicating that the changes made to the object are now visible outside of
	 * the function.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * returns a new `Vector3f` instance with the same x, y coordinates as the original
     * vector and a z value of 0.
     * 
     * @returns a new `Vector3f` object with the values x, y, and 0 for z.
     * 
     * 	- The vector is represented as a three-dimensional array of floating-point values,
     * with the x, y, and z components stored in separate elements of the array.
     * 	- Each component of the vector is a floating-point value between -1.0 and 1.0, inclusive.
     * 	- The order of the components in the array follows the standard mathematical
     * convention for representing three-dimensional vectors.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * returns the value of `x`.
	 * 
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the object to which it belongs.
	 * 
	 * @param x float value that will be assigned to the field `x` of the class instance
	 * upon calling the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which is a floating-point number representing
	 * the Y coordinate of an object.
	 * 
	 * @returns the value of the `y` field, which is a `float`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the field `y` of an object to the input parameter `y`.
	 * 
	 * @param y value that will be assigned to the `y` field of the class instance being
	 * manipulated by the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares the `x` and `y` components of the current vector to those of the provided
	 * vector, returning `true` if they are equal, and `false` otherwise.
	 * 
	 * @param r 2D vector to be compared with the current vector for equality.
	 * 
	 * `x`: The first component of the `Vector2f` class, which is represented by an integer
	 * value between -128 and 127, inclusive.
	 * 
	 * `y`: The second component of the `Vector2f` class, which is represented by an
	 * integer value between -128 and 127, inclusive.
	 * 
	 * @returns a boolean value indicating whether the object's `x` and `y` components
	 * are equal to those of the provided `r` vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
