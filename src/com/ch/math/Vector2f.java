package com.ch.math;

/**
 * has various methods and fields for performing operations on 2D vectors. These
 * include length, max, dot product, normalization, cross product, rotation, addition,
 * subtraction, multiplication, division, absolute value, and string representation.
 * The class also provides setter methods for setting the x and y components directly
 * or by passing in a Vector2f or float argument.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * computes the Euclidean distance of a point (represented by its Cartesian coordinates
	 * x and y) from the origin.
	 * 
	 * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * computes the maximum value of two floating-point arguments and returns it.
	 * 
	 * @returns the maximum value of either `x` or `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * computes the dot product of a `Vector2f` object and another vector, returning a
	 * float value representing the magnitude of the dot product.
	 * 
	 * @param r 2D vector that the function computes the dot product with.
	 * 
	 * 	- `r` is an instance of `Vector2f`, representing a 2D vector with x and y components.
	 * 	- The `x` and `y` components of `r` can be accessed through the getter methods
	 * `getX()` and `getY()`.
	 * 
	 * @returns a floating-point number representing the dot product of the vector and a
	 * given reference vector.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a `Vector2f` object by dividing its components by their corresponding
	 * lengths, resulting in a new vector with a magnitude of 1.
	 * 
	 * @returns a normalized version of the input vector.
	 * 
	 * The returned Vector2f has an x-component that is equal to the input vector's
	 * x-coordinate scaled by the length of the input vector, divided by the total length
	 * of the input vector.
	 * Similarly, the y-component of the returned Vector2f is equal to the input vector's
	 * y-coordinate scaled by the length of the input vector, divided by the total length
	 * of the input vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * computes the dot product of two vectors and returns the result as a float value.
	 * 
	 * @param r 2D vector to perform the cross product operation with the current vector.
	 * 
	 * 	- `r` is a `Vector2f` object representing a two-dimensional point or vector in
	 * the Cartesian coordinate system.
	 * 	- `r.getX()` and `r.getY()` return the x and y coordinates of the point or vector,
	 * respectively.
	 * 
	 * @returns a floating-point value representing the cross product of the vector's x
	 * and y components and the input vector r.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * computes a vector that is a linear interpolation between two given vectors, based
	 * on the specified interpolation factor.
	 * 
	 * @param dest 2D destination vector to which the current vector is interpolated.
	 * 
	 * 	- `Vector2f`: Represents a 2D vector in homogeneous coordinates. It has two
	 * components: `x` and `y`, which represent the position of the vector in the x-axis
	 * and y-axis, respectively.
	 * 	- `lerpFactor`: A scalar value representing the interpolation factor between the
	 * start and end vectors. It determines how much the function should blend the starting
	 * and ending vectors.
	 * 
	 * @param lerpFactor factor by which the current vector is to be interpolated towards
	 * the destination vector.
	 * 
	 * @returns a new `Vector2f` instance representing the linear interpolation of the
	 * given `dest` vector and the current vector, scaled by the provided `lerpFactor`.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point in homogeneous
	 * coordinates.
	 * 	- The `dest` parameter represents the destination point to which the `this`
	 * parameter's value is being lerped.
	 * 	- The `lerpFactor` parameter represents the factor by which the `this` parameter's
	 * value is being scaled before being added to the destination point.
	 * 
	 * The output of the `lerp` function can be described as follows:
	 * 
	 * 	- It is the result of subtracting the `dest` point from the current `this` point,
	 * scaling the result by the `lerpFactor`, and then adding the resulting vector back
	 * to the `dest` point.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a float angle as input and returns a new `Vector2f` object with its x-component
	 * rotated by the specified angle and its y-component unchanged.
	 * 
	 * @param angle 2D rotation angle in radians, which is used to calculate the cosine
	 * and sine components of the resulting vector.
	 * 
	 * @returns a new `Vector2f` instance with the x-component and y-component rotated
	 * by the specified angle.
	 * 
	 * 	- The output is a `Vector2f` instance representing the rotated coordinate pair
	 * (x, y).
	 * 	- The x-coordinate of the output is calculated as the product of the original
	 * x-coordinate and the cosine of the angle of rotation, minus the product of the
	 * original y-coordinate and the sine of the angle of rotation.
	 * 	- The y-coordinate of the output is calculated as the product of the original
	 * x-coordinate and the sine of the angle of rotation, plus the product of the original
	 * y-coordinate and the cosine of the angle of rotation.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` object with the sum
	 * of the current object's coordinates and the provided argument's coordinates.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * 	- `x`: The x-coordinate of `r`, an instance of Vector2f.
	 * 	- `y`: The y-coordinate of `r`, an instance of Vector2f.
	 * 
	 * @returns a new vector with the sum of the inputs' x and y coordinates.
	 * 
	 * The returned object is of type `Vector2f`, which represents a 2D vector with x and
	 * y components.
	 * The x component of the output is equal to the sum of the x component of the first
	 * argument and the x component of the second argument, while the y component is equal
	 * to the sum of the y component of the first argument and the y component of the
	 * second argument.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * adds a scalar value to the corresponding component of the vector.
	 * 
	 * @param r float value to be added to the existing values of the `x` and `y` components
	 * of the `Vector2f` object.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the original vector and
	 * the given value.
	 * 
	 * The `add` function returns a new `Vector2f` object that represents the sum of the
	 * input vector and the addend `r`. The resulting vector has the same coordinates as
	 * the input vector, but with an updated x-coordinate and y-coordinate. Specifically,
	 * the x-coordinate of the returned vector is equal to the x-coordinate of the input
	 * vector plus `r`, while the y-coordinate is equal to the y-coordinate of the input
	 * vector plus `r`.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * takes a `float` and an additional `float`, then returns a new `Vector2f` object
     * with the sum of the original vector's `x` and `y` components and the input `x` and
     * `y` components.
     * 
     * @param x 2D coordinate to add to the existing position of the vector.
     * 
     * @param y 2nd component of the resulting vector, which is computed by adding the
     * corresponding value from the `this` object to it.
     * 
     * @returns a new `Vector2f` instance representing the sum of the original vector's
     * `x` and `y` components and the input `x` and `y` values.
     * 
     * The output is a new instance of the `Vector2f` class with the sum of the `x` and
     * `y` components of this object and the input parameters.
     * The x and y components of the output are calculated by adding the corresponding
     * components of this object to the input values.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` instance representing
	 * the difference between the current instance and `r`.
	 * 
	 * @param r 2D vector to subtract from the current vector.
	 * 
	 * 	- `x`: The first component of the vector, which is 24 in decimal value.
	 * 	- `y`: The second component of the vector, which is 35 in decimal value.
	 * 
	 * @returns a new `Vector2f` object representing the difference between the input
	 * vector and the reference vector.
	 * 
	 * The returned Vector2f object represents the difference between the current instance's
	 * position (x, y) and the provided reference vector (r.x, r.y). The resulting vector
	 * has the same direction as the original vector, but with a magnitude equal to the
	 * distance between the two positions.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a single floating-point argument `r` and returns a new `Vector2f` object
	 * representing the difference between the current vector's coordinates and `r`.
	 * 
	 * @param r 2D offset to subtract from the current vector position of `x` and `y`.
	 * 
	 * @returns a new `Vector2f` object representing the difference between the original
	 * vector and the given value.
	 * 
	 * 	- The Vector2f object represents a two-dimensional point in space, with x and y
	 * coordinates.
	 * 	- The return value is a new Vector2f instance that represents the difference
	 * between the original vector's coordinates and the given value r.
	 * 	- The resulting vector has the same dimensions as the original vector.
	 * 	- The values of the returned vector are in the range of (-r, r), where r is the
	 * value passed to the function.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies its input by a scalar value and returns a new `Vector2f` instance with
	 * the result.
	 * 
	 * @param r 2D vector that the method is multiplying with the current vector.
	 * 
	 * 	- `r` is a `Vector2f` object representing a 2D vector with two components, `x`
	 * and `y`.
	 * 
	 * @returns a vector with the product of the input vectors' x and y components.
	 * 
	 * 	- The output is a new `Vector2f` instance that represents the result of multiplying
	 * the input `x` and `y` values with the corresponding values in the input `r` vector.
	 * 	- The resulting vector has the same dimensions as the input vectors, meaning it
	 * retains the same spatial representation as the input data.
	 * 	- The magnitude of the output vector is equal to the product of the magnitudes
	 * of the input vectors and the corresponding elements of the input `r` vector.
	 * 	- The direction of the output vector is determined by the dot product of the input
	 * vectors and the corresponding elements of the input `r` vector, which represents
	 * the orientation of the output vector relative to the input data.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies a vector by a scalar value, returning a new vector with the product.
	 * 
	 * @param r scalar value that is multiplied with the vector's x and y components to
	 * produce the new vector value.
	 * 
	 * @returns a new `Vector2f` instance with components scaled by the input parameter
	 * `r`.
	 * 
	 * 	- The output is a `Vector2f` object that represents the product of the input `x`
	 * and `r`, and the input `y` and `r`.
	 * 	- The resulting vector has the same magnitude as the input vectors, but its
	 * direction is modified by scaling.
	 * 	- The scaled vector points in the same direction as the original vectors, but
	 * with a larger or smaller magnitude depending on the scaling factor `r`.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a reference to another `Vector2f` object `r` as input and returns a new
	 * `Vector2f` object with the component-wise division of the input vector by the
	 * reference vector.
	 * 
	 * @param r 2D vector that the method will divide the `Vector2f` object by.
	 * 
	 * 	- `x`: The real component of the input vector
	 * 	- `y`: The imaginary component of the input vector
	 * 
	 * The function then returns a new vector with the result of dividing the values of
	 * `x` and `y` by the corresponding values of `r`.
	 * 
	 * @returns a new `Vector2f` object with scaled x and y components based on the input
	 * `r`.
	 * 
	 * The returned value is a new Vector2f object containing the result of dividing the
	 * original vector's x-coordinate by the input vector's x-coordinate and the original
	 * vector's y-coordinate by the input vector's y-coordinate.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a scalar `r` and returns a `Vector2f` instance with x and y components scaled
	 * by the ratio of `r`.
	 * 
	 * @param r scalar value used to divide the vector's components by.
	 * 
	 * @returns a vector with scaled x and y components proportional to the input ratio.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point with x and y components.
	 * 	- The x and y components of the output are calculated by dividing the corresponding
	 * variables in the input parameter 'r' by each other.
	 * 	- The resulting values represent the scaled coordinates of the output point
	 * relative to the original input point.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * returns a new `Vector2f` instance with the absolute values of its input arguments,
	 * `x` and `y`.
	 * 
	 * @returns a new `Vector2f` instance containing the absolute values of the original
	 * vector's components.
	 * 
	 * 	- The returned value is a new Vector2f object containing the absolute values of
	 * the input x and y components.
	 * 	- The new vector has the same dimensions as the input vector.
	 * 	- The magnitude of the returned vector is equal to the absolute value of the input
	 * vector.
	 * 	- The direction of the returned vector is the same as that of the input vector,
	 * since the absolute value operation does not change the direction.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of an object by concatenating the values of its
	 * `x` and `y` fields.
	 * 
	 * @returns a string representing the point (x, y) as a pair of coordinates.
	 * 
	 * The output is a string that consists of two elements separated by a space. The
	 * first element is the value of the `x` variable, and the second element is the value
	 * of the `y` variable.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * updates the `x` and `y` fields of a `Vector2f` object to the input values, returning
	 * the modified object for chaining.
	 * 
	 * @param x 2D position of the vector's x-axis, which is updated to match the provided
	 * value.
	 * 
	 * @param y 2D vector's y component, which is assigned the value passed as an argument
	 * to update its value.
	 * 
	 * @returns a reference to the same `Vector2f` instance with updated `x` and `y` values.
	 * 
	 * 	- The output is of type `Vector2f`, which represents a 2D point in homogeneous coordinates.
	 * 	- The output retains the same values as the input parameters, indicating that the
	 * method modifies the object state and returns the modified object for further manipulation.
	 * 	- The return statement with the `this` keyword indicates that the method returns
	 * the modified object reference, allowing it to be used further in the program.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the x and y components of the Vector2f object to the values provided in the
	 * given Vector2f reference.
	 * 
	 * @param r 2D vector to be set as the value of the `Vector2f` object.
	 * 
	 * 	- `getX()` and `getY()`: These methods return the x-coordinates and y-coordinates
	 * of the input vector, respectively.
	 * 
	 * @returns a reference to the same vector instance with its x and y components set
	 * to the values provided in the argument.
	 * 
	 * 	- The returned object is an instance of the same class as the original input parameter.
	 * 	- The x and y components of the output are set to the values of the corresponding
	 * components of the r parameter.
	 * 	- The return value is an instance of Vector2f, which represents a 2D vector in
	 * homogeneous coordinates.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * converts a `Vector2f` object to a `Vector3f` object by adding a scalar value to
     * its x and y components, creating a new `Vector3f` instance with the modified values.
     * 
     * @returns a new `Vector3f` object containing the input values of `x`, `y`, and `z`
     * set to zero.
     * 
     * The returned object is a `Vector3f`, which represents a 3D vector with three
     * components (x, y, and z).
     * 
     * The first component (x) represents the x-coordinate of the vector.
     * 
     * The second component (y) represents the y-coordinate of the vector.
     * 
     * The third component (z) represents the z-coordinate of the vector.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * retrieves the value of the `x` field, which is a `float` variable.
	 * 
	 * @returns a floating-point value representing the x coordinate.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of its class instance to the inputted float value.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being acted upon by the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the `y` field, which is a `float` variable containing an
	 * unknown value.
	 * 
	 * @returns a floating-point value representing the y-coordinate of an object.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field of the instance it is called on to the argument
	 * passed in.
	 * 
	 * @param y value that will be assigned to the instance field `y` of the class on
	 * which the `setY()` method is defined.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares two `Vector2f` objects based on their `x` and `y` components, returning
	 * `true` if they are equal, and `false` otherwise.
	 * 
	 * @param r 2D vector to which the current vector is being compared for equality.
	 * 
	 * 	- `x`: A double value representing the x-coordinate of the Vector2f object.
	 * 	- `y`: A double value representing the y-coordinate of the Vector2f object.
	 * 
	 * @returns a boolean value indicating whether the object's `x` and `y` values match
	 * those of the provided vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
