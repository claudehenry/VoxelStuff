package com.ch.math;

public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * This function calculates the length of a 2D vector represented by the variables
	 * `x` and `y`. It returns the square root of the sum of the squares of `x` and `y`,
	 * which is the length of the vector.
	 * 
	 * @returns The function takes no arguments and returns the floating-point square
	 * root of the sum of the squares of its own `x` and `y` member variables. In other
	 * words:
	 * 
	 * The output returned by this function is the distance from the starting point (0)
	 * to the current point represented by the object.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * This function calculates the maximum value of x and y and returns it as a float.
	 * 
	 * @returns This function takes no arguments and returns the `float` value representing
	 * the maximum of the `x` and `y` variables. The output returned by this function is
	 * `Math.max(x、y)`, which is the largest of the two variables `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * This function computes the dot product of the current vector (this) with a given
	 * vector (r). It returns a float value representing the result of the dot product.
	 * 
	 * @param r The `r` input parameter is a `Vector2f` object that contains another point
	 * to which the dot product is being calculated.
	 * 
	 * @returns The output returned by this function is a float value representing the
	 * dot product of the current vector and the passed-in vector. Specifically:
	 * 
	 * output = x \* r.getX() + y \* r.getY()
	 * 
	 * where x and y are the components of the current vector and r.getX() and r.getY()
	 * are the components of the passed-in vector.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * This function takes a `Vector2f` object and returns its normalized form (i.e.,
	 * with values between -1 and 1), scaled down by the magnitude of the original vector.
	 * 
	 * @returns This function takes a `Vector2f` object as input and returns a new
	 * `Vector2f` object that represents the original vector normalized to have a length
	 * of 1. The output returned by the function is a vector with its x- and y-coordinates
	 * divided by the length of the original vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * This function calculates the cross product (also known as the vector product or
	 * outer product) of two vectors. It takes another `Vector2f` object `r` as a parameter
	 * and returns the cross product of the current `Vector2f` object with `r`. The result
	 * is a float value that represents the amount of dot product between the two vectors.
	 * 
	 * @param r The `r` input parameter is a vector that represents another point or
	 * direction used to calculate the cross product. It is multiplied by the x and y
	 * components of the function's return value to produce the desired result.
	 * 
	 * @returns This function takes a `Vector2f` argument `r` and returns a `float` value
	 * representing the dot product of the current `Vector2f` object with the input `r`.
	 * The return value is calculated as the product of the current `x` coordinate and
	 * the input `r`y coordinate minus the product of the current `y` coordinate and the
	 * input `r`x coordinate.
	 * 
	 * In simple terms: the function returns a vector representing the horizontal component
	 * of the difference between the two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * This function performs a linear interpolation between two vector2f objects (this
	 * and dest) based on the lerp factor. It returns a new vector2f object that is a
	 * weighted sum of this and dest using the lerp factor.
	 * 
	 * @param dest The `dest` parameter is the destination point that the method will
	 * interpolate between the current point and the destination point using the `lerpFactor`.
	 * 
	 * @param lerpFactor The `lerpFactor` input parameter represents a value between 0
	 * and 1 that controls the smoothness of the transition between the starting and
	 * ending points. It determines how much of the destination vector should be interpolated
	 * into the starting vector over the course of the lerp animation.
	 * 
	 * @returns The output returned by this function is a new `Vector2f` object that
	 * represents the point obtained by interpolating between the current point and the
	 * `dest` point using the `lerpFactor`. The interpolation is done by multiplying the
	 * difference between the points with the `lerpFactor`, adding the starting point to
	 * the resultant vector.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * This function rotates a `Vector2f` by an angle (in radians) around the origin.
	 * 
	 * @param angle The `angle` input parameter represents the angle of rotation around
	 * the origin (0;0) counter-clockwise. It is passed as a floating point number and
	 * multiplied by `Math.toRadians()` to convert it to radians for calculation purposes.
	 * 
	 * @returns The output returned by this function is a new `Vector2f` object that
	 * represents the original `Vector2f` rotated by the specified angle (`angle`) around
	 * the origin (`x=0`, `y=0`). The rotation is performed using trigonometric functions
	 * `Math.cos()` and `Math.sin()`, and the resulting vector components are then wrapped
	 * into a new `Vector2f` object. In other words: the output is the rotated version
	 * of the original vector.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * This function adds the elements of two Vector2f objects and returns a new Vector2f
	 * object containing the sum of the two input vectors.
	 * 
	 * @param r The `r` input parameter represents the vector to be added to the current
	 * vector.
	 * 
	 * @returns The output of this function is a new `Vector2f` object that represents
	 * the sum of the current `Vector2f` object and the passed-in `r` vector. Specifically:
	 * 
	 * 	- The `x` component of the output vector is the sum of the `x` components of the
	 * current vector and the input vector.
	 * 	- The `y` component of the output vector is the sum of the `y` components of the
	 * current vector and the input vector.
	 * 
	 * In other words: the function takes two `Vector2f` objects as inputs and returns a
	 * new `Vector2f` object that represents the point formed by adding the two vectors
	 * together.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * This function adds the passed `r` value to both the `x` and `y` components of the
	 * current `Vector2f` object.
	 * 
	 * @param r The `r` input parameter adds the input value to the existing `x` and `y`
	 * coordinates of the vector.
	 * 
	 * @returns The function takes a single floating-point parameter `r` and returns a
	 * new `Vector2f` object that represents the result of adding `r` to the current
	 * vector. The returned vector has the same `x` and `y` values as the original vector
	 * plus the value of `r`.
	 * 
	 * In other words:
	 * 
	 * Output = Vector2f(x + r y + r)
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * This function adds the floating-point numbers `x` and `y` to the current vector
     * and returns the result as a new `Vector2f` object.
     * 
     * @param x The `x` input parameter adds its value to the current `x` coordinate of
     * the `Vector2f` instance.
     * 
     * @param y In the given function `add(float x‚ vector2f y)’ the parameter ‘y’ is
     * passed by value and its value is added to the current `y’ component of the `Vector2f`
     * object.
     * 
     * @returns The function `add` takes two float parameters `x` and `y`, adds them to
     * the existing values of `x` and `y` of an instance of `Vector2f`, and returns a new
     * `Vector2f` object with the updated values.
     * 
     * The output returned by this function is a new `Vector2f` object containing the sum
     * of the input `x` and `y` values added to the existing `x` and `y` values of the instance.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * The function "sub" takes another Vector2f object "r" and returns a new Vector2f
	 * object that represents the difference between this vector and the given "r" vector.
	 * In other words; it subtracts r from the current vector.
	 * 
	 * @param r In this function `r` is the reference to another `Vector2f` object and
	 * is used as a point of departure for subtracting the components of the current
	 * vector from it.
	 * 
	 * @returns The function `sub` takes a `Vector2f` object `r` as an argument and returns
	 * a new `Vector2f` object that represents the difference between the current object
	 * and `r`. Specifically:
	 * 
	 * 	- The x-coordinate of the return value is the current object's x-coordinate minus
	 * the x-coordinate of `r`.
	 * 	- The y-coordinate of the return value is the current object's y-coordinate minus
	 * the y-coordinate of `r`.
	 * 
	 * In other words:
	 * 
	 * Output: A new `Vector2f` object representing the difference between the current
	 * object and the specified `r` vector.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * The function `sub(float r)` returns a new `Vector2f` instance with x and y coordinates
	 * that are respectively subtracted by the passed float value `r`.
	 * 
	 * @param r The `r` input parameter subtracts the specified value from the `x` and
	 * `y` components of the `Vector2f` object.
	 * 
	 * @returns The function `sub(r)` returns a new `Vector2f` object that represents the
	 * result of subtracting `r` from both the x and y coordinates of the current `Vector2f`
	 * object. In other words:
	 * 
	 * 	- The x coordinate of the return value is the current x coordinate minus `r`.
	 * 	- The y coordinate of the return value is the current y coordinate minus `r`.
	 * 
	 * So the output returned by this function is a new vector that has been subtracted
	 * by `r` from the original vector.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * The function multiplies two `Vector2f` objects component-wise (i.e., scales the
	 * coordinates of one vector by the coordinates of another).
	 * 
	 * @param r The `r` input parameter is a second vector that is used to multiply the
	 * components of the current vector by its elements.
	 * 
	 * @returns The output returned by this function is a `Vector2f` object representing
	 * the result of the multiplication of the current `Vector2f` object by a given
	 * `Vector2f` object `r`. The result is computed as the dot product of the x and y
	 * components of the current vector with the corresponding components of `r`, and is
	 * returned as a new `Vector2f` object.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * This function implements multiplication of a `Vector2f` object by a scalar value
	 * (represented as a `float` parameter `r`). It returns a new `Vector2f` object with
	 * coordinates that are the result of multiplying the original coordinates (`x` and
	 * `y`) by `r`.
	 * 
	 * @param r The `r` input parameter represents a scalar multiplication factor and
	 * multiplies each element of the `Vector2f` object by its corresponding value. In
	 * other words. It scales the vector by a factor of `r`.
	 * 
	 * @returns The output returned by this function is a new `Vector2f` object that
	 * contains the results of multiplying each element of the current `Vector2f` object
	 * by the input parameter `r`. The output vector will have the same coordinates as
	 * the input vector (x and y), but with each element scaled by a factor of `r`.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * This function takes a `Vector2f` object `r` as input and returns a new `Vector2f`
	 * object that represents the current object divided by `r`. It does this by dividing
	 * the x and y coordinates of the current object by the corresponding coordinates of
	 * `r`.
	 * 
	 * @param r The `r` parameter is a vector that serves as a divisor for the current
	 * vector. It is used to compute the component-wise division of the current vector
	 * by the divisor vector.
	 * 
	 * @returns This function takes a `Vector2f` parameter `r` and returns a new `Vector2f`
	 * object that represents the result of dividing the current `Vector2f` object by
	 * `r`. The return value is described concisely as:
	 * 
	 * Output: A new `Vector2f` object with coordinates (x/r.getX(), y/r.getY())
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * This function takes a single floating-point parameter `r` and returns a new
	 * `Vector2f` object with the original coordinates divided by `r`.
	 * 
	 * @param r The `r` input parameter is a factor by which the `x` and `y` components
	 * of the vector are divided.
	 * 
	 * @returns The output returned by this function is a new `Vector2f` object that
	 * represents the original vector divided by a given scalar value `r`. Specifically:
	 * 
	 * 	- The x-coordinate of the new vector is the current x-coordinate of the original
	 * vector divided by `r`.
	 * 	- The y-coordinate of the new vector is the current y-coordinate of the original
	 * vector divided by `r`.
	 * 
	 * In other words. the output is a scaled version of the original vector with all
	 * components divided by the given scalar value.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * This function calculates the absolute values of the `x` and `y` components of a
	 * `Vector2f` object and returns a new `Vector2f` object with the absolute values.
	 * 
	 * @returns The output of this function is a `Vector2f` object that contains the
	 * absolute values of the x and y components of the original vector. In other words.,
	 * the function takes a vector as input and returns a new vector with only the absolute
	 * values of the original vector's components.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * This function provides a string representation of the object by returning a string
	 * containing the values of the 'x' and 'y' properties separated by a space.
	 * 
	 * @returns The output returned by this function is a string consisting of the
	 * characters "(", the values of x and y (without any spaces), and the character ")."
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * This function sets the x and y coordinates of a Vector2f object to the specified
	 * values.
	 * 
	 * @param x In the given function `set(float x、float y)`, the `x` input parameter
	 * sets the value of the `x` component of the `Vector2f` object.
	 * 
	 * @param y The `y` input parameter sets the value of the `y` component of the
	 * `Vector2f` object.
	 * 
	 * @returns This function takes two `float` parameters `x` and `y`, assigns them to
	 * the corresponding components of a `Vector2f` object `this`, and returns `this`
	 * itself. In other words: the output returned is the modified `Vector2f` object that
	 * received the assigned values.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * This function sets the elements of the `Vector2f` object to the values of the
	 * elements of the given `Vector2f` argument `r`.
	 * 
	 * @param r The `r` parameter is a reference to another `Vector2f` object. It is used
	 * to set the components of the current vector to the values of the `Vector2f` object
	 * passed as an argument.
	 * 
	 * @returns The function takes a `Vector2f` argument `r` and sets the x and y components
	 * of the current vector to the corresponding components of `r`. The function returns
	 * the current vector (i.e., `this`).
	 * 
	 * In other words:
	 * 
	 * 	- The input parameter `r` is used to set the values of the current vector's x and
	 * y components.
	 * 	- The function returns a reference to the current vector (`this`).
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * This function returns a new `Vector3f` object with the values of `x`, `y`, and `0`.
     * 
     * @returns The output of the function `as3DVector()` is a new instance of the class
     * `Vector3f` with three float components: `x`, `y`, and `0`.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * The function `getX()` returns the value of the field `x` which is of type `float`.
	 * 
	 * @returns The function returns undefined because the field "x" is not defined or
	 * has no value.
	 */
	public float getX() {
		return x;
	}

	/**
	 * This function sets the value of the `x` field of the object to the passed `float`
	 * `x`.
	 * 
	 * @param x In the given function `setX(float x)`, the input parameter `x` sets the
	 * value of the field `x` within the object to the value passed as an argument.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * This function returns the value of the `y` field of the object.
	 * 
	 * @returns The function `getY()` returns the value of the field `y` which is
	 * `undefined`, therefore the output returned by this function is `NaN` (Not a Number).
	 */
	public float getY() {
		return y;
	}

	/**
	 * The `setY()` function sets the value of the `y` field of the object to the specified
	 * `float` value `y`.
	 * 
	 * @param y The `y` input parameter sets the value of the member variable `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * This function implements the `equals` method for the `Vector2f` class. It compares
	 * the current instance with a given `Vector2f` object and returns `true` if both
	 * vectors have the same x-coordinate and y-coordinate values.
	 * 
	 * @param r The `r` input parameter is a reference to another `Vector2f` object that
	 * is being compared for equality with the current instance.
	 * 
	 * @returns The output returned by this function is `false`.
	 * 
	 * This function compares the current object's `x` and `y` coordinates with the
	 * corresponding coordinates of the passed `r` object. Since both objects have undefined
	 * values for `x` and `y`, none of the comparison conditions are true. Therefore the
	 * function returns `false`.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
