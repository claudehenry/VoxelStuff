package com.ch.math;

/**
 * is a mathematical representation of a point in 2D space with two components (x and
 * y). It provides various methods for manipulating and calculating distances, angles,
 * and ratios between points in 2D space.
 */
public class Vector2f {	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculates the Euclidean distance between a point and the origin, based on the
	 * coordinates of the point.
	 * 
	 * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * computes the maximum value of its two arguments, `x` and `y`, and returns it as a
	 * float value.
	 * 
	 * @returns the larger of the two input values, `x` and `y`, represented as a
	 * floating-point number.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * computes the dot product of a `Vector2f` instance `r` and the current instance,
	 * returning the result as a float value.
	 * 
	 * @param r 2D vector that dot product is being calculated with.
	 * 
	 * 	- `x`: The real value of the first component of `r`.
	 * 	- `y`: The real value of the second component of `r`.
	 * 
	 * @returns a floating-point number representing the dot product of the input `Vector2f`
	 * and the component values of the function.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a `Vector2f` object by dividing its components by the object's magnitude,
	 * resulting in a unitized vector.
	 * 
	 * @returns a new `Vector2f` instance with scaled X and Y components based on the
	 * vector's length.
	 * 
	 * 	- The output is a `Vector2f` object representing a normalized version of the
	 * original vector.
	 * 	- The x-component of the output is calculated as the original x-component divided
	 * by the length of the original vector.
	 * 	- The y-component of the output is calculated as the original y-component divided
	 * by the length of the original vector.
	 * 
	 * The output has a length of 1, indicating that it is a unit vector in the original
	 * coordinate system.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * computes the vector product of two vectors in the form of a single float value,
	 * where the output is equal to the product of the x-component of one vector and the
	 * y-component of the other vector, minus the product of the x-component of both vectors.
	 * 
	 * @param r 2D vector that is being multiplied with the `x` and `y` components of the
	 * input `Vector2f` object to produce the output value.
	 * 
	 * 	- `x`: The real part of the complex number `r`.
	 * 	- `y`: The imaginary part of the complex number `r`.
	 * 	- `getY()` and `getX()`: Methods used to retrieve the values of `y` and `x`,
	 * respectively, from the deserialized input `r`.
	 * 
	 * @returns a scalar value representing the cross product of two vectors in polar coordinates.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * interpolates a Vector2f value between two given values, using a linear interpolation
	 * based on the `lerpFactor`.
	 * 
	 * @param dest 2D position to which the object will be interpolated between its current
	 * position and the specified `lerpFactor`.
	 * 
	 * `dest`: A `Vector2f` instance representing the destination point. It has two
	 * components, x and y, representing the coordinates of the point in the 2D space.
	 * 
	 * @param lerpFactor factor by which the current position is to be interpolated towards
	 * the `dest` position.
	 * 
	 * @returns a new `Vector2f` object representing the interpolation of the input
	 * `Vector2f` and the destination `Vector2f`.
	 * 
	 * The returned value is a `Vector2f` object that represents the interpolated position
	 * between the input `this` and the destination `dest`. The interpolation is performed
	 * using the `sub`, `mul`, and `add` methods.
	 * 
	 * The `sub` method is used to subtract the `this` position from the `dest` position,
	 * resulting in a vector representation of the difference between the two positions.
	 * 
	 * The `mul` method is used to multiply the result of the subtraction by the `lerpFactor`,
	 * which determines the interpolation weight between the two positions.
	 * 
	 * Finally, the `add` method is used to add the interpolated position to the `this`
	 * position, resulting in the final interpolated position that is returned as the
	 * output of the `lerp` function.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a float argument representing the angle of rotation and returns a new
	 * `Vector2f` instance with its x-coordinate rotated by the specified angle and its
	 * y-coordinate unchanged.
	 * 
	 * @param angle angle of rotation in radians, which is used to calculate the cosine
	 * and sine values that are applied to the `x` and `y` components of the returned
	 * `Vector2f` object.
	 * 
	 * @returns a new `Vector2f` object with the x and y components rotated by the specified
	 * angle.
	 * 
	 * The output is a `Vector2f` object, which represents a 2D point with x and y
	 * components. The x and y components of the output are calculated using the angle
	 * passed as a parameter, which is represented in radians. Specifically, the x component
	 * is equal to the original x coordinate multiplied by the cosine of the angle, while
	 * the y component is equal to the original y coordinate multiplied by the sine of
	 * the angle.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * adds two `Vector2f` objects and returns a new object with the sum of their x and
	 * y components.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * `x`: The x-coordinate of the vector.
	 * `y`: The y-coordinate of the vector.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the input vector and the
	 * given vector.
	 * 
	 * 	- The returned vector has an x-component that is the sum of the x-components of
	 * the two input vectors.
	 * 	- The returned vector has a y-component that is the sum of the y-components of
	 * the two input vectors.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * adds a scalar value to the existing vector components, resulting in a new vector
	 * with updated coordinates.
	 * 
	 * @param r 2D vector to be added to the current vector representation of the class.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the original vector and
	 * the given float value.
	 * 
	 * 	- The return type is `Vector2f`, which represents a 2D vector with two components
	 * (x and y).
	 * 	- The expression `x + r` computes the new x-component of the vector, where `r`
	 * is a float value.
	 * 	- Similarly, the expression `y + r` computes the new y-component of the vector.
	 * 
	 * As a result, the returned output has two components that represent the sum of the
	 * original vector's x and y components with the input float value `r`.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * adds a float value to the `x` and `y` components of an existing `Vector2f` object,
     * returning a new `Vector2f` object with the updated values.
     * 
     * @param x 2D position to add to the existing position of the vector.
     * 
     * @param y 2nd component of the resulting vector, which is calculated by adding it
     * to the current value of the `y` component of the `this` object.
     * 
     * @returns a new `Vector2f` instance representing the sum of the input `x` and `y`
     * values.
     * 
     * The returned Vector2f object represents the sum of the current instance's x-coordinate
     * and the input argument x, and the sum of the current instance's y-coordinate and
     * the input argument y. Therefore, the x-coordinate and y-coordinate of the returned
     * object will be equal to the sum of the corresponding coordinates of the current
     * instance and the input arguments.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` instance representing
	 * the difference between the current vector's coordinates and those of the provided
	 * vector.
	 * 
	 * @param r 2D vector that the function subtracts from the input vector `x` and `y`.
	 * 
	 * 	- `x`: an integer value representing the x-coordinate of the input vector.
	 * 	- `y`: an integer value representing the y-coordinate of the input vector.
	 * 
	 * @returns a new vector with the difference between the input vector's coordinates
	 * and the reference vector's coordinates.
	 * 
	 * The returned vector has two components, representing the difference between the
	 * original vector's components and the input parameter's components. Specifically,
	 * the x-component is equal to the original vector's x-component minus the input
	 * parameter's x-component, while the y-component is equal to the original vector's
	 * y-component minus the input parameter's y-component.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a single floating-point argument `r` and returns a new `Vector2f` object
	 * representing the difference between the current vector's coordinates and `r`.
	 * 
	 * @param r 2D vector to subtract from the current vector.
	 * 
	 * @returns a new `Vector2f` instance representing the difference between the original
	 * vector and the given value.
	 * 
	 * The `Vector2f` object returned by the function has two components: `x` and `y`.
	 * The `x` component represents the difference between the original vector's `x`
	 * coordinate and the input `r`, while the `y` component represents the difference
	 * between the original vector's `y` coordinate and the input `r`.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies a `Vector2f` object by another `Vector2f` object, returning a new
	 * `Vector2f` object with the product of the two values.
	 * 
	 * @param r 2D vector that the method will multiply with the original vector.
	 * 
	 * The `Vector2f` class represents a 2D vector in homogeneous coordinates, with two
	 * components: `x` and `y`. The `r` object is also an instance of `Vector2f`, which
	 * means it has the same components as the input to the `mul` function.
	 * 
	 * @returns a new vector with the product of the input vector's x and y components
	 * multiplied by the corresponding values of the given vector.
	 * 
	 * The `Vector2f` object returned by the function represents the product of the current
	 * vector and the given vector `r`. The product is computed as the sum of the products
	 * of the corresponding components of the vectors. Specifically, the output has the
	 * same x-component as the product of the current vector's x-component and the given
	 * vector's x-component, and the same y-component as the product of the current
	 * vector's y-component and the given vector's y-component.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies the `Vector2f` instance x and y by a float value r and returns a new
	 * `Vector2f` instance with the product.
	 * 
	 * @param r scaling factor applied to the vector's x and y components.
	 * 
	 * @returns a new `Vector2f` instance with the product of the `x` and `y` components
	 * of the input vector multiplied by the scalar value `r`.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D vector in homogeneous
	 * coordinates.
	 * 	- The x-component and y-component of the output are calculated by multiplying the
	 * corresponding components of the input `Vector2f` object with the scalar value `r`.
	 * 	- The resulting vector has the same direction as the input vector, but its magnitude
	 * is equal to the product of the input vector's magnitude and the scalar value `r`.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` with the values of
	 * `x` and `y` divided by the corresponding values of `r`.
	 * 
	 * @param r vector that the method will divide the current vector by.
	 * 
	 * 	- `x`: The x-coordinate of the input vector.
	 * 	- `y`: The y-coordinate of the input vector.
	 * 
	 * @returns a vector with the same x-coordinate as the input vector, scaled by the
	 * reciprocal of the x-coordinate of the argument vector.
	 * 
	 * 	- The returned vector has the same magnitude as the original input vector `r`.
	 * 	- The x-component of the returned vector is equal to the original x-component
	 * divided by the x-component of `r`, while the y-component is equal to the original
	 * y-component divided by the y-component of `r`.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a single float argument `r` and returns a new `Vector2f` instance with x and
	 * y components scaled by the ratio of `r`.
	 * 
	 * @param r scalar value used to divide the vector's x and y components.
	 * 
	 * @returns a vector with the x and y coordinates scaled by the input parameter `r`.
	 * 
	 * The returned output is a `Vector2f` object, which represents a 2D point with x and
	 * y components.
	 * 
	 * The x component of the output is equal to the `x` component of the input vector
	 * divided by the input value `r`.
	 * 
	 * The y component of the output is equal to the `y` component of the input vector
	 * divided by the input value `r`.
	 * 
	 * Overall, the function reduces the magnitude of the input vector by a specified
	 * factor, resulting in a smaller 2D point.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * returns a new `Vector2f` object with the absolute value of its input components.
	 * 
	 * @returns a new `Vector2f` object containing the absolute values of its input components.
	 * 
	 * 	- The returned Vector2f object represents the absolute value of the original
	 * Vector2f object's x and y components.
	 * 	- The Vector2f class in Java has two components, x and y, which represent the
	 * coordinates of a 2D point.
	 * 	- The `Math.abs()` method calculates the absolute value of a double value, returning
	 * its magnitude without considering its sign.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of an object by concatenating two values, `x` and
	 * `y`.
	 * 
	 * @returns a string representation of the given coordinates, consisting of the values
	 * of `x` and `y` separated by a space.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * updates the `x` and `y` properties of the `Vector2f` object to the provided values,
	 * returning the updated object reference.
	 * 
	 * @param x 2D coordinate of the vector's point where the set operation should occur.
	 * 
	 * @param y 2D coordinate value that updates the `y` component of the `Vector2f` object.
	 * 
	 * @returns a reference to the same `Vector2f` instance with updated `x` and `y` values.
	 * 
	 * The `Vector2f` object is updated with the new values of `x` and `y`.
	 * 
	 * After updating the values, the returned output is the same `Vector2f` object that
	 * was passed as an argument to the function.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the x and y components of a vector to the corresponding values of an argument
	 * vector.
	 * 
	 * @param r 2D vector that contains the new values for the `x` and `y` components of
	 * the `Vector2f` object, which are then set to those values in the function.
	 * 
	 * 	- `getX()` and `getY()`: These methods return the x-coordinate and y-coordinate
	 * of the vector, respectively.
	 * 
	 * @returns a reference to the original vector with the x and y components updated.
	 * 
	 * The method `set(r.getX(), r.getY());` sets the x-coordinate and y-coordinate of
	 * the vector to the corresponding values in the input vector `r`. The method returns
	 * a reference to the same vector object, indicating that the original object is unchanged.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * converts a `Vector2f` instance into a `Vector3f` instance by adding a third component
     * representing the z-value to be zero.
     * 
     * @returns a new `Vector3f` instance representing the point (x, y, 0) in 3D space.
     * 
     * 	- The `x`, `y`, and `z` values represent the coordinates of the 3D vector in the
     * range of -1 to 1.
     * 	- The vector is a `Vector3f` object, which can be used for various mathematical
     * operations related to 3D vectors.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the object to which it belongs.
	 * 
	 * @param x Float value that will be assigned to the member field `x` of the object
	 * on which the `setX()` method is called.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which is a `float` variable, and returns its
	 * value.
	 * 
	 * @returns the value of the `y` field, which is a `float` variable.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the object's `y` field to the input parameter `y`.
	 * 
	 * @param y 2D coordinate of a point to which the `setY()` method will assign the
	 * value passed as argument.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares the object reference `this` with another `Vector2f` object `r`. It returns
	 * a boolean value indicating whether the x- and y-components of `this` are equal to
	 * the corresponding components of `r`.
	 * 
	 * @param r 2D vector to compare with the current vector.
	 * 
	 * 	- `x`: represents the x-coordinate of the vector.
	 * 
	 * @returns a boolean value indicating whether the vector's x and y coordinates are
	 * equal to those of the provided vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
