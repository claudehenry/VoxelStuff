package com.ch.math;

public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

 /**
  * This function calculates the length ( magnitude ) of a 2D vector ( x , y ) and
  * returns it as a float .
  * 
  * 
  * @returns { float } This function takes no arguments and returns a floating-point
  * number representing the length of the vector. It calculates the square root of the
  * sum of the squares of the x and y coordinates using the `Math.sqrt()` method.
  * 
  * The output returned by this function is the length of the vector.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

 /**
  * This function calculates and returns the maximum of the two floating-point numbers
  * `x` and `y`.
  * 
  * 
  * @returns { float } This function takes no arguments and returns a float value
  * representing the maximum of `x` and `y`. The return value is calculated using the
  * `Math.max()` method. Therefore the output returned by this function is the greater
  * of `x` and `y`.
  */
	public float max() {
		return Math.max(x, y);
	}

 /**
  * This function calculates the dot product of two vectors.
  * 
  * 
  * @param { Vector2f } r - The `r` input parameter is a `Vector2f` object that contains
  * another point to calculate the dot product with the current point.
  * 
  * @returns { float } The output returned by this function is a float value representing
  * the dot product of the current vector (this) and the input vector (r). Specifically:
  * 
  * 	- It returns the sum of the products of the components of the two vectors: x *
  * r.getX() + y * r.getY()
  */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

 /**
  * This function computes and returns a normalized version of the vector `this`. It
  * divides the component-wise values of `this` by its magnitude (length), resulting
  * into a new vector with length 1.
  * 
  * 
  * @returns { Vector2f } The function `normalized()` takes a `Vector2f` object as
  * input and returns a new `Vector2f` object that represents the original vector
  * scaled down to a length of 1.0. In other words , it normalizes the vector . The
  * returned vector has its x and y components divided by the length of the original
  * vector . The output is a unit vector (a vector with a length of 1) that points to
  * the same direction as the input vector.
  */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

 /**
  * This function calculates the cross product of two vectors. It returns a floating-point
  * value representing the dot product of the two vectors' respective x and y components.
  * 
  * 
  * @param { Vector2f } r - The `r` parameter is a reference to another `Vector2f`
  * object and is used to compute the cross product of the current vector and the other
  * vector.
  * 
  * @returns { float } The function `cross` takes a `Vector2f` argument `r` and returns
  * a `float` value that represents the cross product of the current vector (`this`)
  * and `r`.
  * 
  * The output of the function is calculated as the dot product of the x-coordinate
  * of `this` and the y-coordinate of `r`, minus the dot product of the y-coordinate
  * of `this` and the x-coordinate of `r`. In other words:
  * 
  * `cross(r) = (x * r.getY()) - (y * r.getX())`
  * 
  * The output is a single floating-point number that represents the cross product of
  * two vectors.
  */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

 /**
  * This function takes a `Vector2f` object as an argument and returns a new `Vector2f`
  * object that represents the linear interpolation between the current object and a
  * destination point. It does this by multiplying the difference between the two
  * points by a given `lerpFactor`, adding the current point to the result of that multiplication.
  * 
  * 
  * @param { Vector2f } dest - The `dest` parameter is the target position that the
  * vector will interpolate towards. It is used to calculate the difference between
  * the current position and the target position.
  * 
  * @param { float } lerpFactor - The `lerpFactor` input parameter controls the
  * interpolation between the current position and the destination position. It takes
  * a value between 0 and 1 and returns a Vector2f that is a linear interpolation of
  * the current position and the destination position based on the given lerp factor.
  * 
  * @returns { Vector2f } The function takes two `Vector2f` arguments: `this` and
  * `dest`, and a `float` argument `lerpFactor`. It returns a new `Vector2f` object
  * that is the linear interpolation of `this` and `dest`, with the value of `lerpFactor`
  * specifying the point on the line between the two points where the interpolation
  * should be made.
  * 
  * In other words: The function calculates the difference between `this` and `dest`,
  * scales the difference by `lerpFactor`, and adds the result back to `this`, resulting
  * is a new vector that represents the interpolated position between the original and
  * dest vectors.
  */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * This function rotates a `Vector2f` by an angle degree around the origin.
  * 
  * 
  * @param { float } angle - The `angle` input parameter represents the angle of
  * rotation (in degrees) that the vector should be rotated by.
  * 
  * @returns { Vector2f } The function takes a single `angle` parameter and returns a
  * new instance of `Vector2f`, which represents the rotated version of the original
  * vector. The output is a vector with two float components that represent the x- and
  * y-coordinates of the rotated point.
  */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

 /**
  * The function `add` takes another vector `r` and returns a new vector that is the
  * sum of the current vector and `r`.
  * 
  * 
  * @param { Vector2f } r - The `r` input parameter is the other vector being added
  * to the current vector. It serves as a reference to another Vector2f object and its
  * x and y coordinates are used to calculate the result of the addition.
  * 
  * @returns { Vector2f } The output returned by this function is a new `Vector2f`
  * object that represents the sum of the current vector and the input vector `r`. The
  * new vector has coordinates `x + r.getX()` and `y + r.getY()`.
  */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

 /**
  * The function adds the value "r" to both the x and y coordinates of the vector.
  * 
  * 
  * @param { float } r - The `r` input parameter adds the input value to the existing
  * x and y coordinates of the vector.
  * 
  * @returns { Vector2f } The function takes a float `r` as parameter and returns a
  * new `Vector2f` instance with the X and Y coordinates of the original vector increased
  * by `r`.
  * 
  * In other words:
  * 
  * The output returned by this function is a new vector that is equal to the original
  * vector (`this`) but with the X and Y coordinates incremented by the passed value
  * `r`.
  */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * This function takes two float arguments `x` and `y`, adds them to the existing
     * values of the object's `x` and `y` fields respectively and returns a new `Vector2f`
     * instance with the updated values.
     * 
     * 
     * @param { float } x - The `x` input parameter adds to the current `x` value of the
     * vector.
     * 
     * @param { float } y - In this function `y` is just added to the existing `y` value
     * of the `Vector2f` object. It has no other effect.
     * 
     * @returns { Vector2f } The output returned by this function is a new `Vector2f`
     * object that represents the sum of the current `Vector2f` object and two float
     * parameters `x` and `y`.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

 /**
  * This function takes a `Vector2f` parameter `r` and returns a new `Vector2f` that
  * represents the subtraction of `r` from the current instance. In other words; it
  * computes `x - r.getX()` and `y - r.getY()`.
  * 
  * 
  * @param { Vector2f } r - The `r` parameter is a reference to another `Vector2f`
  * object that is used as a point of reference for subtraction. It represents the
  * other point with respect to which the current vector's components will be calculated.
  * 
  * @returns { Vector2f } The function `sub` takes a `Vector2f` object `r` as input
  * and returns a new `Vector2f` object representing the difference between the current
  * object and `r`. The return value has x-coordinate equal to the current object's x
  * coordinate minus `r`'s x coordinate and y-coordinate equal to the current object's
  * y coordinate minus `r`'s y coordinate.
  * 
  * In other words: the function returns a new vector that points from the current
  * position to the point `r` away from the current position.
  */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

 /**
  * The function takes a single float argument `r` and returns a new `Vector2f` instance
  * with the `x` and `y` coordinates of the current vector subtracted by `r`. In other
  * words:
  * <p>```
  * sub(r) = Vector2f(x - r.0f) + y - r.1f
  * ```</p>
  * 
  * @param { float } r - The `r` parameter is a float value that is subtracted from
  * both the x and y coordinates of the current vector to create a new vector with the
  * same direction but moved by the amount of 'r'.
  * 
  * @returns { Vector2f } The function takes a float `r` as an argument and returns a
  * new `Vector2f` object representing the result of subtracting `r` from the current
  * position of the vector. The new vector has x-coordinate `x - r` and y-coordinate
  * `y - r`.
  */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

 /**
  * This function multiplies the current `Vector2f` object by a given `Vector2f`
  * parameter and returns the result as a new `Vector2f` object.
  * 
  * 
  * @param { Vector2f } r - The `r` parameter is a reference to another `Vector2f`
  * object that is used to multiply each element of the current vector by the corresponding
  * element of the `r` vector.
  * 
  * @returns { Vector2f } This function takes another `Vector2f` object `r` and
  * multiplies the components of this object by the components of the current object
  * (i.e., `x` and `y`). The output is a new `Vector2f` object with the product of the
  * two input vectors. In other words:
  * 
  * Output: A new `Vector2f` object with the component-wise multiplication of the
  * current vector and `r`.
  */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

 /**
  * This function implements vector multiplication by a scalar value (a float). It
  * returns a new Vector2f object with the x and y components multiplied by the passed-in
  * value r.
  * 
  * 
  * @param { float } r - The `r` input parameter is a scalar factor that multiplies
  * the component values of the `Vector2f` object.
  * 
  * @returns { Vector2f } The output of this function is a new `Vector2f` object that
  * represents the result of multiplying the current vector with a given floating-point
  * value `r`. Specifically:
  * 
  * 	- The `x` component of the returned vector is equal to the current `x` component
  * multiplied by `r`.
  * 	- The `y` component of the returned vector is equal to the current `y` component
  * multiplied by `r`.
  * 
  * In other words:
  * 
  * Returned vector = (x * r), (y * r)
  */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

 /**
  * This function takes a `Vector2f` object `r` as an argument and returns a new
  * `Vector2f` object that represents the division of the original vector by `r`.
  * 
  * 
  * @param { Vector2f } r - The `r` parameter is a vector that represents the divisor
  * for the element-wise division of the `this` vector. It is used to calculate the
  * scalar multipliers for each component of the `this` vector.
  * 
  * @returns { Vector2f } This function takes a `Vector2f` parameter `r` and returns
  * a new `Vector2f` object with the componentwise division of the current vector by
  * `r`. In other words:
  * 
  * Output: A new `Vector2f` object representing the current vector divided element-wise
  * by the values of `r`.
  */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

 /**
  * This function takes a single parameter `r` and returns a new `Vector2f` instance
  * with the components divided by `r`. In other words; the `x` and `y` values of the
  * resulting vector will be the current values of `x` and `y`, divided by `r`.
  * 
  * 
  * @param { float } r - The `r` input parameter is a factor by which the vectors'
  * components are divided. It is used to scale the components of the vector down by
  * a given amount.
  * 
  * @returns { Vector2f } The output returned by this function is a new `Vector2f`
  * object containing the component-wise division of the current vector by a floating-point
  * number `r`. In other words:
  * 
  * 	- `x / r` is calculated and stored as the x-component of the new vector
  * 	- `y / r` is calculated and stored as the y-component of the new vector
  * 
  * So the output vector has the same dimensions as the input vector and its components
  * are the results of dividing the corresponding components of the input vector by
  * the input parameter `r`.
  */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

 /**
  * This function takes a `Vector2f` object as input and returns a new `Vector2f`
  * object containing the absolute values of the original vector's x and y components.
  * 
  * 
  * @returns { Vector2f } The function `abs()` takes no parameters and returns a new
  * `Vector2f` object containing the absolute values of the x and y coordinates of the
  * current object.
  * 
  * In other words:
  * 
  * 	- The x coordinate of the return vector is the absolute value of the current
  * object's x coordinate (i.e., if the current object's x coordinate is positive or
  * zero; otherwise zero).
  * 	- The y coordinate of the return vector is the absolute value of the current
  * object's y coordinate (i.e., if the current object's y coordinate is positive or
  * zero; otherwise zero).
  * 
  * So the output returned by this function is a new `Vector2f` object with only the
  * absolute values of the x and y coordinates.
  */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

 /**
  * This function returns a string representation of the object by concatenating the
  * values of its members "x" and "y" separated by a space.
  * 
  * 
  * @returns { String } The output returned by this function is "( Undefined )".
  */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

 /**
  * This function sets the x and y coordinates of the Vector2f object to the passed
  * floats x and y.
  * 
  * 
  * @param { float } x - In this function implementation of `set(float x`, `float y)`,
  * `x` is an input parameter that sets the value of the `x` component (i.e., the
  * x-coordinate) of the `Vector2f` object.
  * 
  * @param { float } y - The `y` input parameter sets the value of the `y` component
  * of the `Vector2f` object.
  * 
  * @returns { Vector2f } The function `set(float x‚ á float y)` takes two float
  * arguments and returns a reference to the same object as its output.
  */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

 /**
  * This function sets the components of the `Vector2f` object to the corresponding
  * components of the passed `r` object.
  * 
  * 
  * @param { Vector2f } r - The `r` input parameter is a reference to another `Vector2f`
  * object that contains the new values to be assigned to the current instance.
  * 
  * @returns { Vector2f } This function takes a `Vector2f` parameter named `r` and
  * sets the components of the current vector to the corresponding components of `r`.
  * The function returns a reference to the current vector (`this`). In other words:
  * 
  * The output returned by this function is the same `Vector2f` object that was passed
  * as an argument.
  */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * This function creates a new Vector3f object with the x and y coordinates of the
     * current Point2f object and a z coordinate of 0.
     * 
     * 
     * @returns { Vector3f } The output returned by this function is a new `Vector3f`
     * object with the values `x`, `y`, and `0` (i.e., the z-coordinate is set to 0).
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

 /**
  * The given function "getX()" returns the value of the "x" field (which is currently
  * undefined) as a floating-point number.
  * 
  * 
  * @returns { float } The function `getX()` returns the value of the field `x`, which
  * is `undefined`. Therefore the output of this function is `NaN` (Not a Number).
  */
	public float getX() {
		return x;
	}

 /**
  * This function sets the value of the member variable "x" to the input "x".
  * 
  * 
  * @param { float } x - The `x` input parameter is assigned to the member variable
  * `x` of the same name within the class.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * This function returns the value of the "y" field of the object.
  * 
  * 
  * @returns { float } The output returned by this function is "null" or "undefined".
  * The reason is that the variable "y" is not initialized or set to any value before
  * being returned as a float value from the getY() function. Therefore. the function
  * is returning an uninitialized or "undefined" value.
  */
	public float getY() {
		return y;
	}

 /**
  * The function sets the value of the field "y" to the given float parameter "y".
  * 
  * 
  * @param { float } y - The `y` input parameter sets the value of the `y` field of
  * the object that the function is called on.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * This function implements the `equals` method for a `Vector2f` object. It compares
  * the current `Vector2f` object with another `Vector2f` object `r` and returns `true`
  * if both the `x` and `y` components are equal to each other's corresponding components.
  * 
  * 
  * @param { Vector2f } r - The `r` input parameter is a reference to another `Vector2f`
  * object that is passed to the function for comparison. It is used as a second operand
  * for the equality check on each field (x and y) of the current `Vector2f` object.
  * 
  * @returns { boolean } This function returns `false` for all inputs. The `x` and `y`
  * fields are never defined because they are initialized as `undefined`, so none of
  * the comparisons inside the `equals()` method will ever evaluate to `true`.
  */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}


