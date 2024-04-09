package com.ch.math;

/**
 * is a simple class for representing 2D vectors in Java. It has several methods for
 * calculating vector operations such as length, max, dot product, normalization, and
 * cross product. Additionally, it provides methods for adding, subtracting, multiplying,
 * dividing, and rotating vectors. The class also includes high-level functions such
 * as equality comparison and conversion to a 3D vector.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

 /**
  * calculates the Euclidean distance of a point from its origin, represented by `x`
  * and `y`, using the Pythagorean theorem.
  * 
  * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

 /**
  * computes the maximum value of two input values `x` and `y`, and returns it as a
  * float value.
  * 
  * @returns the larger of `x` and `y`.
  */
	public float max() {
		return Math.max(x, y);
	}

 /**
  * calculates the dot product of a `Vector2f` object and another vector, returning
  * the result as a floating-point number.
  * 
  * @param r 2D vector that the dot product is being calculated with.
  * 
  * 	- `x`: The first component of the vector `r`, which is a floating-point number.
  * 	- `y`: The second component of the vector `r`, which is also a floating-point number.
  * 
  * @returns a floating-point number representing the dot product of the input vector
  * and the component values of the function parameter `r`.
  */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

 /**
  * normalizes a `Vector2f` instance by dividing its components by their corresponding
  * lengths, returning a new `Vector2f` instance with normalized values.
  * 
  * @returns a normalized vector with x and y components proportional to the original
  * vector's length.
  * 
  * The output is a `Vector2f` object, where the x-component represents the normalized
  * value of the x-coordinate of the original vector, and the y-component represents
  * the normalized value of the y-coordinate.
  * 
  * The normalization is performed by dividing each component of the original vector
  * by its length, which is calculated using the `length()` method.
  * 
  * The resulting vector has a length of 1, indicating that it is a unit vector in the
  * original coordinate system.
  * 
  * The output can be used to represent a normalized version of the original vector,
  * which can be useful in various applications such as image processing, computer
  * vision, and machine learning.
  */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

 /**
  * takes a `Vector2f` object `r` as input and returns the product of its `x` and `y`
  * components, calculated using the dot product formula.
  * 
  * @param r 2D vector to be multiplied with the `x` and `y` components of the current
  * vector.
  * 
  * 	- `r` is an instance of the `Vector2f` class, which represents a 2D vector in Java.
  * 	- `r.getX()` and `r.getY()` return the x-coordinate and y-coordinate of the vector,
  * respectively.
  * 	- The function returns the dot product of the input vector with the x-axis of
  * another vector.
  * 
  * @returns a floating-point value representing the dot product of two vectors.
  */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

 /**
  * computes a vector interpolation between two given vectors, using the provided
  * `lerpFactor` to control the blending process.
  * 
  * @param dest 2D destination vector that the lerping operation will transform the
  * original vector towards.
  * 
  * The input `dest` is a `Vector2f` instance representing the destination point. It
  * has two components: x and y, which are floating-point values representing the
  * coordinates of the point.
  * 
  * @param lerpFactor amount of interpolation or blending between the current position
  * and the destination position, with higher values resulting in more rapid transition
  * towards the destination.
  * 
  * @returns a new `Vector2f` instance that represents the intermediate position between
  * the current position of the object and the destination position.
  * 
  * The `lerp` function takes in two parameters: `dest` and `lerpFactor`. The output
  * is a `Vector2f` object that represents the interpolation between the starting
  * vector `this` and the destination vector `dest`.
  * 
  * The function first computes the difference between `this` and `dest`, which results
  * in a second `Vector2f` object. It then multiplies this difference by `lerpFactor`
  * to obtain the interpolated value. Finally, it adds the interpolated value to `this`
  * to produce the final output.
  * 
  * The returned vector has the same properties as the input vectors, including magnitude
  * and direction. The interpolation is performed using a smooth, continuous function,
  * ensuring that the output is well-behaved and does not exhibit any discontinuities
  * or sharp corners.
  */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * rotates a `Vector2f` object by an angle in radians, returning a new `Vector2f`
  * object with its x and y components scaled by the cosine and sine of the angle, respectively.
  * 
  * @param angle angle of rotation in radians, which is used to calculate the cosine
  * and sine components of the resulting vector.
  * 
  * @returns a new vector with x and y components rotated by the specified angle.
  * 
  * 	- The Vector2f object represents a 2D point with x and y components.
  * 	- The angle parameter is passed to the Math.toRadians() method to convert it to
  * radians.
  * 	- The Cos and Sin methods of Math class are used to calculate the cosine and sine
  * of the angle, respectively.
  * 	- The output vector is created by multiplying the x and y components of the input
  * vector by the cosine and sine values, respectively.
  */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

 /**
  * takes a `Vector2f` parameter `r` and returns a new `Vector2f` instance with the
  * sum of the current vector's components and those of the provided vector.
  * 
  * @param r 2D vector to be added to the current vector.
  * 
  * 	- `x`: The x-coordinate of the input vector.
  * 
  * @returns a new vector with the sum of the input vectors' x and y coordinates.
  * 
  * 	- The output is a new `Vector2f` instance, containing the sum of the inputs' `x`
  * and `y` components.
  */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

 /**
  * adds a float value to a `Vector2f` object, returning a new vector with the sum of
  * the original and the added value.
  * 
  * @param r 2D vector component that is added to the current vector value of the
  * `Vector2f` object being modified.
  * 
  * @returns a new `Vector2f` instance with an x-coordinate equal to the sum of the
  * current vector's x-coordinate and the input `r`, and a y-coordinate equal to the
  * sum of the current vector's y-coordinate and the input `r`.
  * 
  * The returned object is of type `Vector2f`, which represents a 2D point with x and
  * y coordinates. The x and y coordinates of the output are calculated by adding the
  * input value `r` to the existing values of x and y, respectively. Therefore, the
  * output has an increased x and y coordinate compared to the original input.
  */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * takes a `float` argument `x` and `y` and returns a new `Vector2f` instance with
     * the sum of the current instance's `x` and `y` and the given `x` and `y` arguments.
     * 
     * @param x 2D coordinate of the point to be added to the existing vector.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` object representing the sum of the current vector and
     * the provided `x` and `y` values.
     * 
     * The returned object is of type `Vector2f`, which represents a 2D point in homogeneous
     * coordinates.
     * The `x` property of the returned object is equal to the sum of the `x` properties
     * of the input arguments `x` and the current instance `this.x`.
     * Similarly, the `y` property of the returned object is equal to the sum of the `y`
     * properties of the input arguments `y` and the current instance `this.y`.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

 /**
  * computes the difference between a `Vector2f` and another `Vector2f`. It returns a
  * new `Vector2f` object representing the difference in terms of x-coordinates and y-coordinates.
  * 
  * @param r 2D vector that the function will subtract from the current vector.
  * 
  * The `Vector2f` object `r` contains two components: `x` and `y`, which represent
  * the x-coordinate and y-coordinate of a 2D vector, respectively.
  * 
  * @returns a new `Vector2f` object representing the difference between the input
  * vector and the reference vector.
  * 
  * 	- The output is of type `Vector2f`.
  * 	- It has two components, `x` and `y`, which represent the difference between the
  * input vector's `x` and `y` coordinates and the input argument `r.getX()` and
  * `r.getY()`, respectively.
  */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

 /**
  * takes a single float argument `r` and returns a new `Vector2f` object representing
  * the difference between the original vector's coordinates and `r`.
  * 
  * @param r 2D vector to subtract from the current vector.
  * 
  * @returns a new `Vector2f` instance representing the difference between the original
  * vector and the specified value.
  * 
  * The `Vector2f` object represents a two-dimensional vector in the homogeneous
  * coordinate space, with the origin at (0, 0). The `x` and `y` fields represent the
  * x and y coordinates of the vector, respectively. When the function is called with
  * a real number `r`, it returns a new `Vector2f` object representing the vector that
  * is `r` units away from the original vector in both the x and y directions.
  */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

 /**
  * multiplies its argument by the component-wise product of the current vector's
  * components and the arguments' components.
  * 
  * @param r 2D vector to be multiplied with the current vector.
  * 
  * 	- `x`: The x-coordinate of the input vector.
  * 	- `y`: The y-coordinate of the input vector.
  * 
  * @returns a new `Vector2f` instance with the product of the input vector's `x` and
  * `y` components and the input argument `r`.
  * 
  * 	- The output is a new instance of the `Vector2f` class.
  * 	- The `x` and `y` fields of the output are computed by multiplying the corresponding
  * fields of the input `r` object.
  * 	- The resulting vector has the same magnitude as the input, but its direction may
  * be different due to the multiplication by a scalar value.
  */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

 /**
  * multiplies its input by a scalar value, returning a new `Vector2f` instance with
  * the product.
  * 
  * @param r scalar value that multiplies the `x` and `y` components of the resulting
  * vector.
  * 
  * @returns a new `Vector2f` object with x and y components multiplied by the input
  * parameter `r`.
  * 
  * The return value is of type `Vector2f`, which represents a 2D vector with x and y
  * components.
  * 
  * The x component of the returned vector is equal to the product of the `x` field
  * of the input parameter `r` multiplied by the `x` component of the original vector.
  * 
  * Similarly, the y component of the returned vector is equal to the product of the
  * `y` field of the input parameter `r` multiplied by the `y` component of the original
  * vector.
  */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` object with the
  * x-coordinate scaled by the reciprocal of the x-coordinate of `r`, and the y-coordinate
  * scaled by the reciprocal of the y-coordinate of `r`.
  * 
  * @param r vector to which the current vector is divided.
  * 
  * `x` and `y`: These are the coordinates of the vector in the input `r`.
  * 
  * @returns a new `Vector2f` instance with scaled x and y components proportional to
  * the input `r`.
  * 
  * 	- The output is a new Vector2f instance with x and y components calculated as the
  * ratio of the input vector's x and y components to the input vector itself.
  * 	- The resulting vector has the same magnitude as the original input vector, but
  * its direction is scaled by the reciprocal of the input vector's magnitude.
  */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

 /**
  * multiplies its input by the scalar value `r`, and returns a new `Vector2f` instance
  * with the scaled values for `x` and `y`.
  * 
  * @param r scalar value used to divide the vector's components by.
  * 
  * @returns a vector with x and y components scaled by the input value `r`.
  * 
  * The returned Vector2f object has a magnitude (length) equal to the input value 'r'
  * divided by the corresponding component of the original Vector2f object.
  * The direction (angle) of the returned Vector2f object is unchanged from that of
  * the original Vector2f object.
  * The returned Vector2f object has the same reference as the original Vector2f object,
  * meaning any modifications made to one affects the other.
  */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

 /**
  * returns a new `Vector2f` instance with the absolute values of its input parameters,
  * `x` and `y`.
  * 
  * @returns a new `Vector2f` instance containing the absolute values of its input components.
  * 
  * The return type is `Vector2f`, which means it has two components representing the
  * x and y coordinates of a 2D vector.
  * 
  * The expression `Math.abs(x)` returns the absolute value of the `x` component of
  * the input vector, which is a non-negative number. Similarly, `Math.abs(y)` returns
  * the absolute value of the `y` component, also a non-negative number.
  * 
  * Therefore, the returned output is a new vector with non-negative coordinates that
  * represent the absolute values of the original vector's x and y components.
  */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

 /**
  * returns a string representation of a point object, comprising its `x` and `y`
  * coordinates separated by a space.
  * 
  * @returns a string representation of a point in coordinates, consisting of an opening
  * paren followed by the values of `x` and `y`.
  */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

 /**
  * sets the `x` and `y` attributes of a `Vector2f` object to the input values, and
  * returns a reference to the modified object.
  * 
  * @param x 2D coordinate of the point to which the `Vector2f` instance should be set.
  * 
  * @param y 2nd component of the `Vector2f` object, which is being updated to match
  * the provided value.
  * 
  * @returns a reference to the modified vector instance.
  * 
  * 	- The `Vector2f` object is mutated to have the new values of `x` and `y`.
  * 	- The returned object is the same as the original one, indicating that the method
  * does not change the state of the object.
  * 	- No information about the author or licensing is provided in the response.
  */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

 /**
  * sets the x and y components of the vector to the corresponding values of the
  * provided `Vector2f` object.
  * 
  * @param r 2D vector to be set as the new value of the `Vector2f` object.
  * 
  * 	- `getX()` and `getY()` represent the X and Y coordinates of the vector, respectively.
  * 
  * @returns a reference to the original `Vector2f` object with its x and y components
  * updated to match the values of the provided `r` object.
  * 
  * 	- The `Vector2f` object is assigned the values of `r.getX()` and `r.getY()`.
  * 	- The function returns a reference to the original `Vector2f` object, indicating
  * that the state of the object remains unchanged after the assignment.
  * 	- No information about the author or licensing of the code is provided in the output.
  */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * converts a `Vector2f` instance to a `Vector3f` instance by adding a third component
     * representing the z-coordinate, which is set to zero.
     * 
     * @returns a new `Vector3f` instance with the values `x`, `y`, and `0` for its components.
     * 
     * 	- The Vector3f object returned has three components: x, y, and z, which represent
     * the coordinates of the 3D vector in the respective dimensions.
     * 	- Each component is a float value ranging from -1 to 1, representing the magnitude
     * and direction of the vector in the corresponding dimension.
     * 	- The vector is defined as having origin at (0, 0, 0), meaning that the x, y, and
     * z components all have a value of 0 at this point.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

 /**
  * returns the value of the `x` field, which is a `float` variable.
  * 
  * @returns a floating-point value representing the `x` field.
  */
	public float getX() {
		return x;
	}

 /**
  * sets the value of the field `x` to the argument passed as a float.
  * 
  * @param x new value for the `x` field of the class, which is being assigned to by
  * calling the `setX()` method.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * retrieves the value of `y` from a source and returns it as a floating-point number.
  * 
  * @returns a `float` value representing the `y` coordinate of the object.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the object's `y` field to the argument passed as a float parameter.
  * 
  * @param y new value for the field 'y' of the class, which is being assigned to by
  * calling the `setY()` method.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * compares two `Vector2f` objects based on their x and y coordinates, returning
  * `true` if they are equal, and `false` otherwise.
  * 
  * @param r 2D vector that the function is comparing to the current vector.
  * 
  * 	- `x`: The `x` property of `r` represents the x-coordinate of the vector. It is
  * an `int` value.
  * 	- `y`: The `y` property of `r` represents the y-coordinate of the vector. It is
  * also an `int` value.
  * 
  * @returns a boolean value indicating whether the object is equal to the given vector.
  */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
