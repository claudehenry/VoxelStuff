package com.ch.math;

public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

 /**
  * This function calculates the length of a 2D vector (x and y coordinates) and returns
  * the result as a float value.
  * 
  * @returns The output returned by this function is a `float` value representing the
  * length of the 2D vector represented by the `x` and `y` coordinates. Specifically:
  * 
  * Returns the square root of the sum of the squared `x` and `y` values.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

 /**
  * The given function is a `public float max()` function which takes no parameters
  * and returns the maximum of the field values `x` and `y`.
  * 
  * @returns The output returned by this function is the maximum value of `x` and `y`,
  * which is calculated using the `Math.max()` method. The function returns a
  * floating-point number representing the maximum value.
  */
	public float max() {
		return Math.max(x, y);
	}

 /**
  * This function calculates the dot product of two Vector2f objects. It returns a
  * float value representing the result of multiplying the x and y components of the
  * object by the corresponding components of the argument vector.
  * 
  * @param r The `r` parameter is a `Vector2f` object that contains two floating-point
  * numbers (x and y) representing a point or a vector to be multiplied element-wise
  * with the instance variable `this`.
  * 
  * @returns The function `dot(Vector2f r)` returns a floating-point number that
  * represents the dot product of the current object (a Vector2f) and the input `r`.
  * The output is the sum of the products of the corresponding components of the two
  * vectors.
  */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

 /**
  * This function computes and returns the vector normalized (i.e., with length 1) by
  * dividing its components by the length of the vector.
  * 
  * @returns The output of this function is a new `Vector2f` object that represents
  * the original vector scaled down to unit length (i.e., the magnitude of the vector
  * is set to 1). Specifically:
  * 
  * 	- The x-coordinate of the returned vector is the input `x` value divided by the
  * length of the input vector.
  * 	- The y-coordinate of the returned vector is the input `y` value divided by the
  * length of the input vector.
  */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

 /**
  * This function computes the dot product of two vectors represented by `x` and `y`,
  * and returns the result as a floating-point number.
  * 
  * @param r The `r` input parameter is a `Vector2f` object that represents a second
  * vector to cross with the first vector represented by `this`.
  * 
  * @returns This function calculates the cross product of two vectors. The output
  * returned by this function is a floating-point number representing the dot product
  * of the two vectors. In other words:
  * 
  * output = x * r.getY() - y * r.getX()
  * 
  * Here "x" and "y" are the elements of the first vector and "r.getX()" and "r.getY()"
  * are the elements of the second vector.
  */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

 /**
  * This function performs a linear interpolation between two Vector2f objects based
  * on the given lerp factor. It returns a new Vector2f object that represents the
  * interpolated position between the current object and the dest object.
  * 
  * @param dest The `dest` parameter represents the target position that the current
  * object should interpolate towards.
  * 
  * @param lerpFactor The `lerpFactor` parameter controls the degree to which the
  * method will "lurch" between the current position and the destination position. It
  * ranges from 0.0f (no change) to 1.0f (complete transition).
  * 
  * @returns The output returned by this function is a new `Vector2f` object that
  * represents the linear interpolation between the current object and the `dest`
  * object. The interpolation factor is used to calculate the weighted sum of the two
  * vectors.
  */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * This function rotates a `Vector2f` object by an angle specified by the `angle`
  * parameter. It returns the rotated vector.
  * 
  * @param angle The `angle` input parameter represents the angle of rotation (in
  * degrees) to be applied to the vector.
  * 
  * @returns The output returned by this function is a new `Vector2f` object that
  * represents the original vector rotated by the specified angle. The new vector's x
  * and y components are calculated using the cosine and sine of the angle of rotation.
  */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

 /**
  * This function adds the values of `x` and `y` of the current `Vector2f` object with
  * the corresponding values of the `r` parameter's `Vector2f` object and returns a
  * new `Vector2f` object containing the result.
  * 
  * @param r The `r` parameter is the other vector being added to the current vector.
  * 
  * @returns The function takes a `Vector2f` object `r` as an argument and adds its
  * `x` and `y` components to the current `x` and `y` components of the object. The
  * result is a new `Vector2f` object that represents the sum of the two input vectors.
  * The output returned by this function is a `Vector2f` object with the sum of the x
  * and y coordinates of the two input vectors.
  */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

 /**
  * The function adds the parameter `r` to both the x and y coordinates of the `Vector2f`.
  * 
  * @param r The `r` input parameter adds the input value to each component of the `Vector2f`.
  * 
  * @returns The output of this function is a new `Vector2f` object that represents
  * the sum of the current vector and the value passed as an argument `r`. The new
  * vector has coordinates `x + r` and `y + r`.
  */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * This function adds the `x` and `y` parameters to the current `Vector2f` instance's
     * `x` and `y` fields and returns a new `Vector2f` object with the updated values.
     * 
     * @param x The `x` input parameter adds the passed value to the current `x` component
     * of the vector.
     * 
     * @param y The `y` input parameter adds the passed value to the current `y` coordinate
     * of the `Vector2f` object.
     * 
     * @returns The function takes two `float` arguments `x` and `y`, adds them to the
     * current `Vector2f` component values (`this.x` and `this.y`), and returns a new
     * `Vector2f` object containing the resulting sum.
     * 
     * The output returned by this function is a new `Vector2f` object with the updated
     * component values: `x + this.x` and `y + this.y`.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

 /**
  * This function subtracts the values of `x` and `y` from the current `Vector2f`
  * object by the corresponding values of the `r` object's `x` and `y`, and returns
  * the result as a new `Vector2f` object.
  * 
  * @param r The `r` parameter is the vector to subtract from the current vector.
  * 
  * @returns The function takes a `Vector2f` parameter `r` and returns a new `Vector2f`
  * object that represents the difference between the current vector and `r`. The
  * output is a vector with two float components representing the differences of the
  * x- and y-coordinates of the two input vectors.
  */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

 /**
  * This function subtracts the value `r` from both the x and y coordinates of the
  * vector and returns a new vector with the result.
  * 
  * @param r The `r` input parameter is a floating-point number that represents the
  * distance by which the vector components (x and y) are reduced.
  * 
  * @returns The output returned by this function is a `Vector2f` object representing
  * the difference between the current `Vector2f` object and a new vector with the
  * same direction as the current vector but shifted by `r` units along both the x and
  * y axes. In other words," subtracting r" from the current vector.
  */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

 /**
  * The function "mul" (short for "multiply") takes a Vector2f object "r" as input and
  * returns a new Vector2f object representing the multiplication of the current
  * Vector2f object by "r". In other words，it multiplies the x- and y-coordinates of
  * the current vector by the corresponding coordinates of the input vector.
  * 
  * @param r The `r` input parameter is a reference to another `Vector2f` object. The
  * function returns a new `Vector2f` object created by multiplying the current
  * `Vector2f` object by the values of `r`.
  * 
  * @returns This function takes another `Vector2f` object `r` as input and returns a
  * new `Vector2f` object with the dot product of the current vector and `r`. In other
  * words:
  * 
  * The output returned by this function is a new vector with components x*r.getX()
  * and y*r.getY().
  */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

 /**
  * The function `mul(float r)` takes a single argument `r` and returns a new `Vector2f`
  * object where both x and y components are multiplied by the passed value `r`.
  * 
  * @param r The `r` input parameter is a scaling factor that multiplies the components
  * of the vector by its value.
  * 
  * @returns The output of this function is a new `Vector2f` object with components
  * that are the product of the corresponding components of the input vector and the
  * multiplication factor `r`. In other words:
  * 
  * 	- `x * r` becomes the new `x` component of the output vector
  * 	- `y * r` becomes the new `y` component of the output vector
  */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

 /**
  * This function performs element-wise division of the current vector with a given
  * vector `r`, and returns a new vector with the resulting fractions.
  * 
  * @param r The `r` parameter is the divisor used to perform element-wise division
  * on the current vector's elements.
  * 
  * @returns This function takes another vector as an argument and returns a new vector
  * with the components of the current vector divided by the components of the argument
  * vector. The output returned is a vector with the same type (Vector2f) containing
  * thedivided values.
  */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

 /**
  * This function takes a float "r" and returns a new Vector2f object that represents
  * the original Vector2f divided by "r". In other words , it divides all elements of
  * the Vector2f by the given float value.
  * 
  * @param r The `r` input parameter is a divisor used to scale the x and y components
  * of the vector by the same amount.
  * 
  * @returns The output returned by this function is a new `Vector2f` object that
  * represents the current vector divided by the given scalar value `r`. Specifically:
  * 
  * 	- The `x` component of the returned vector is the current `x` component of the
  * vector divided by `r`.
  * 	- The `y` component of the returned vector is the current `y` component of the
  * vector divided by `r`.
  */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

 /**
  * This function takes a `Vector2f` object as input and returns its absolute value
  * (magnitude) as a new `Vector2f` object.
  * 
  * @returns The function `abs()` takes no arguments and returns a new `Vector2f`
  * object with the absolute values of the x and y components of the original vector.
  * In other words. the output is a new vector with the magnitudes of the original
  * vector's components without any directional information.
  * 
  * For example: if the original vector is `(3.0f 4.5f)`, the output of the `abs()`
  * function would be `(3.0f 4.5f)` since the absolute value of each component is unchanged.
  */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

 /**
  * This function is an implementation of the `toString()` method for an object that
  * has two attributes: `x` and `y`. It returns a string that displays the values of
  * `x` and `y` separated by a space.
  * 
  * @returns The output returned by this function is "(undefined)".
  */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

 /**
  * This function sets the `x` and `y` components of the `Vector2f` object to the
  * passed `float` values and returns the updated `Vector2f` object.
  * 
  * @param x The `x` input parameter sets the value of the `x` field of the `Vector2f`
  * object.
  * 
  * @param y In this function `set(float x$, float y$)`, the `y` input parameter sets
  * the value of the `y` field of the `Vector2f` object.
  * 
  * @returns The function `set` takes two `float` parameters `x` and `y`, sets the
  * corresponding elements of the `Vector2f` instance to those values and returns the
  * modified instance itself. In other words: the output returned is the modified
  * `Vector2f` instance.
  */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

 /**
  * This function sets the x and y coordinates of the current Vector2f object to the
  * x and y coordinates of the given Vector2f object 'r'. It returns a reference to
  * the current object.
  * 
  * @param r In this function declaration for `public Vector2f set(Vector2f r)`, the
  * `r` parameter is used as a reference to another `Vector2f` object. It is passed
  * by reference and is not returned as part of the function. The function takes
  * advantage of this to "assign" the values from `r` to the current `Vector2f` object's
  * properties `x` and `y`.
  * 
  * @returns The function takes a `Vector2f` argument `r` and sets the values of this
  * object's components to the corresponding components of `r`. The function returns
  * a reference to this object.
  * 
  * In other words: The function modifies the current instance of the `Vector2f` class
  * by setting its x and y coordinates to the values of the `x` and `y` coordinates
  * of the passed `r` object. And then it returns the modified `this` instance as output.
  */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * This function creates a new `Vector3f` object with the values of `x` and `y`, but
     * with a zero `z` value.
     * 
     * @returns The output returned by this function is a new `Vector3f` object with the
     * coordinates `x` and `y`, but with the `z` coordinate set to `0`.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

 /**
  * This function does not do anything because it is defined but not implemented (i.e.,
  * the `x` field is not assigned a value). Therefore the function `getX()` will always
  * return `undefined`.
  * 
  * @returns The output returned by this function is "undefined". This is because the
  * field "x" has not been initialized yet.
  */
	public float getX() {
		return x;
	}

 /**
  * The `setX` function sets the value of the field `x` to the given `float` parameter
  * `x`.
  * 
  * @param x The `x` input parameter is assigned to the instance field `x` of the class.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * This function "getY" returns the value of the field "y" which is a float.
  * 
  * @returns The output returned by this function is `undefined`.
  */
	public float getY() {
		return y;
	}

 /**
  * This function sets the `y` field of the object to the given `float` value `y`.
  * 
  * @param y The `y` input parameter sets the value of the instance field `y`.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * This function checks if the object has the same x and y coordinates as another
  * Vector2f object.
  * 
  * @param r In the given function `equals`, the `r` input parameter is a reference
  * to another `Vector2f` object. The function compares the values of `x` and `y`
  * fields of the current object with the corresponding fields of the object referenced
  * by `r`.
  * 
  * @returns This function takes another `Vector2f` object as a parameter and compares
  * its `x` and `y` values to the current instance's values. It returns `true` if all
  * four values are equal and `false` otherwise.
  */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}


