package com.ch.math;

/**
 * is a mathematical representation of a 2D point in space, with fields for x and y
 * coordinates, as well as various methods for manipulating and querying the point.
 * The class provides methods for calculating the length, maximum value, dot product,
 * normalization, cross product, and rotation of the vector, as well as basic arithmetic
 * operations such as addition, subtraction, multiplication, and division. Additionally,
 * the class provides a method for converting the vector to a 3D vector and offers
 * equality checking functionality.
 */
public class Vector2f {	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculates the Euclidean distance of a point from its origin, by squaring its
	 * coordinates and taking the square root.
	 * 
	 * @returns the square root of the sum of the squares of the `x` and `y` variables.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * calculates the maximum value of two floating-point arguments and returns it as a
	 * float.
	 * 
	 * @returns the maximum value of `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * takes a `Vector2f` argument `r` and computes the dot product of the vector's
	 * components with those of a provided vector `x` and `y`.
	 * 
	 * @param r 2D vector that dot product is being calculated with.
	 * 
	 * 	- `x` and `y` are the x and y components of the `r` vector.
	 * 
	 * @returns a scalar value representing the dot product of the input vector and another
	 * vector.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a `Vector2f` object by dividing its components by their respective
	 * magnitudes, resulting in a unitized vector.
	 * 
	 * @returns a normalized version of the input vector, with its magnitude (length)
	 * divided by its value.
	 * 
	 * The `Vector2f` object returned by the function has two components: `x` and `y`,
	 * which represent the normalized values of the corresponding components of the
	 * original vector. The values of `x` and `y` are calculated as a fraction of the
	 * total length of the original vector, which is determined by calling the `length()`
	 * method on the original vector.
	 * 
	 * The returned `Vector2f` object has the same direction as the original vector, but
	 * its magnitude is scaled down to 1. This means that the normalized vector has the
	 * same orientation as the original vector, but its size is reduced to a single value
	 * between 0 and 1.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * takes a `Vector2f` object `r` as input and returns the dot product of the vector's
	 * x-axis and the input vector's y-axis, subtracted by the dot product of the vector's
	 * y-axis and the input vector's x-axis.
	 * 
	 * @param r 2D vector to be multiplied with the current vector, resulting in a new
	 * 2D vector that is the cross product of the two vectors.
	 * 
	 * `r`: A `Vector2f` object, representing a 2D vector in the format `(x, y)`.
	 * 
	 * @returns a floating-point value representing the cross product of the vector `r`
	 * and the current position of the entity.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * computes the linear interpolation between two `Vector2f` values, `dest` and `this`,
	 * based on a specified `lerpFactor`. It returns the interpolated vector by subtracting
	 * the difference between the two input vectors, scaling with the interpolation factor,
	 * and adding the original starting point.
	 * 
	 * @param dest 2D vector that the function will smoothly transition from the current
	 * position to.
	 * 
	 * 	- `dest`: This is the destination vector that will be reached by interpolating
	 * between the current vector and a reference vector.
	 * 	- `lerpFactor`: The interpolation factor used to blend the current vector with
	 * the reference vector.
	 * 
	 * @param lerpFactor factor by which the current vector is to be interpolated towards
	 * the `dest` vector.
	 * 
	 * @returns a vector that interpolates between two given vectors based on a provided
	 * factor.
	 * 
	 * 	- The output is a `Vector2f` object representing the interpolated value between
	 * the given `dest` vector and the current vector.
	 * 	- The `lerpFactor` parameter represents the interpolation factor, which determines
	 * how much the output vector will be influenced by the destination vector.
	 * 	- The function first calculates the difference between the current vector and the
	 * destination vector, then multiplies the result by the `lerpFactor` and adds it to
	 * the current vector. This produces an interpolated value that is a combination of
	 * the current vector and the destination vector.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a float argument representing the angle of rotation in radians and returns
	 * a new `Vector2f` object with the rotated coordinates.
	 * 
	 * @param angle 3D rotation angle in radians, which is used to calculate the corresponding
	 * 2D vector rotation.
	 * 
	 * @returns a new `Vector2f` instance with the coordinates modified based on the
	 * provided angle.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point in homogeneous
	 * coordinates.
	 * 	- The `x` and `y` components of the output represent the rotated position of the
	 * input point after applying the rotation angle.
	 * 	- The values of `cos` and `sin` used in the calculation are determined by the
	 * angle of rotation, which is passed as a float parameter to the function.
	 * 
	 * Overall, the `rotate` function takes a 2D point and rotates it around its center
	 * by the specified angle in radians.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * adds two `Vector2f` objects by combining their x and y components, returning a new
	 * `Vector2f` object with the sum of the input values.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * 	- `x`: The x-coordinates of `r` represent an additional value added to the existing
	 * x-coordinate of the return vector.
	 * 	- `y`: The y-coordinates of `r` represent an additional value added to the existing
	 * y-coordinate of the return vector.
	 * 
	 * @returns a new vector with the sum of the inputs' `x` and `y` coordinates.
	 * 
	 * The return type is `Vector2f`, indicating that it is a vector with two elements
	 * representing the x- and y-coordinates, respectively.
	 * 
	 * The expression `x + r.getX()` computes the new x-coordinate of the output vector
	 * by adding the value of `r.getX()` to the existing x-coordinate of the input vector.
	 * 
	 * Similarly, `y + r.getY()` computes the new y-coordinate of the output vector by
	 * adding the value of `r.getY()` to the existing y-coordinate of the input vector.
	 * 
	 * Therefore, the returned vector represents the sum of the input vectors in the x-
	 * and y-directions.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * takes a float argument `r` and returns a new `Vector2f` object with the sum of the
	 * current vector's `x` and `y` coordinates and the `r` argument.
	 * 
	 * @param r addition value to be added to the existing values of `x` and `y`.
	 * 
	 * @returns a new `Vector2f` instance with the sum of the input `r` added to the
	 * existing values of `x` and `y`.
	 * 
	 * The `Vector2f` object returned by the function represents a point in 2D space with
	 * x-coordinates and y-coordinates. The x-coordinate is the sum of the original
	 * x-coordinate and the input parameter r, while the y-coordinate is the sum of the
	 * original y-coordinate and the input parameter r.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * adds the `x` and `y` parameters to the current vector's `x` and `y` components,
     * respectively, returning a new vector instance.
     * 
     * @param x 2D coordinate to add to the existing coordinates of the vector.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` instance representing the sum of the input `x` and `y`
     * values.
     * 
     * The returned object is a `Vector2f` instance representing the sum of the current
     * vector's x-component and the input x value, plus the current vector's y-component
     * and the input y value.
     * The resulting vector has the same x and y coordinates as the original vector, but
     * with the added values of x and y.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` object representing
	 * the difference between the input vector and the reference vector.
	 * 
	 * @param r 2D vector to be subtracted from the current vector.
	 * 
	 * 	- `x`: The real value of the `x` component in `r`.
	 * 	- `y`: The real value of the `y` component in `r`.
	 * 
	 * @returns a new `Vector2f` object representing the difference between the current
	 * vector and the provided reference vector.
	 * 
	 * The return type is Vector2f, which represents a 2D vector in homogeneous coordinates.
	 * 
	 * The method takes another Vector2f argument 'r' and subtracts its components (x and
	 * y) from the current instance's components to produce the resultant vector.
	 * 
	 * The returned vector has the same magnitude as the original vector but with components
	 * that are opposite in sign, indicating a vector that is negated relative to the original.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a single float argument `r` and returns a new `Vector2f` instance with the
	 * difference between the current vector's `x` and `y` components and the input `r`.
	 * 
	 * @param r 2D offset vector that is subtracted from the current position of the
	 * `Vector2f` instance being manipulated, resulting in a new position vector.
	 * 
	 * @returns a new `Vector2f` instance representing the difference between the original
	 * vector and the given value.
	 * 
	 * The output is a `Vector2f` object representing the difference between the original
	 * `x` and `y` values and the input parameter `r`.
	 * 
	 * The resulting vector has the same magnitude as the original vector but with a new
	 * direction that is `r` units away from the original position.
	 * 
	 * The returned vector has the same properties as the original vector, including its
	 * magnitude and direction.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies its input vector `r` by the product of the component values of its own
	 * vector, resulting in a new vector with the same components.
	 * 
	 * @param r 2D vector to be multiplied with the current vector.
	 * 
	 * 	- `r` is a `Vector2f` instance with two components `x` and `y`.
	 * 
	 * @returns a new `Vector2f` object with the product of the input vector's x and y
	 * components multiplied by the corresponding components of the input parameter `r`.
	 * 
	 * The Vector2f object returned is created by multiplying the values of x and y in
	 * the calling instance with the corresponding values of r. As such, this output
	 * represents the product of the two inputs provided to the function.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies the `Vector2f` instance's x and y components by a given scalar value
	 * `r`, returning a new `Vector2f` instance with the result.
	 * 
	 * @param r scalar value to be multiplied with the vector's x and y components,
	 * resulting in a new vector object with doubled magnitudes of the original vector components.
	 * 
	 * @returns a new `Vector2f` instance with the product of the current vector's x and
	 * y components multiplied by the input parameter `r`.
	 * 
	 * The output is a `Vector2f` object, which represents a 2D point in homogeneous
	 * coordinates. The `x` and `y` components of the output are computed by multiplying
	 * the original `x` and `y` values with the input parameter `r`. Therefore, the output
	 * point will shift horizontally and vertically by factors of `r` along the x-axis
	 * and y-axis, respectively.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a `Vector2f` argument `r` and returns a new `Vector2f` object with the
	 * x-coordinate scaled by the reciprocal of the x-coordinate of `r`, and the y-coordinate
	 * scaled by the reciprocal of the y-coordinate of `r`.
	 * 
	 * @param r 2D vector to be divided by the current vector.
	 * 
	 * The input `r` is an instance of the class `Vector2f`, which represents a 2D vector
	 * in Java. It has two attributes: `x` and `y`, which represent the coordinates of
	 * the vector in the x-axis and y-axis, respectively.
	 * 
	 * @returns a new vector with the same x-coordinates as the original vector, scaled
	 * by the reciprocal of the input vector's x-coordinate.
	 * 
	 * The returned Vector2f object represents the result of dividing the input vector
	 * by the given scalar value.
	 * 
	 * The x and y components of the output vector are calculated as follows: `x / r.getX()`
	 * and `y / r.getY()`, respectively.
	 * 
	 * The output vector has the same dimensions as the input vector.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a scalar `r` and returns a `Vector2f` instance with x-axis and y-axis scaled
	 * by `r`.
	 * 
	 * @param r scalar value used to divide the vector's components.
	 * 
	 * @returns a vector with x and y components scaled by the input `r`.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point with x and y components.
	 * 	- The x and y components of the output are calculated by dividing the corresponding
	 * components of the input vector by the input scalar value `r`.
	 * 	- The output has the same magnitude as the input vector, but its direction is
	 * inverted proportionally to the input scalar value `r`.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * computes and returns a new `Vector2f` object with the absolute value of its x and
	 * y components.
	 * 
	 * @returns a new vector with the absolute value of its x and y components.
	 * 
	 * The returned Vector2f object has two components, x and y, which represent the
	 * absolute value of the original Vector2f's x and y components, respectively.
	 * 
	 * The returned Vector2f has a magnitude equal to the absolute value of the original
	 * Vector2f's magnitude, i.e., it retains the same magnitude but with its direction
	 * reversed if the original vector was negative.
	 * 
	 * The returned Vector2f's direction is always positive, regardless of the original
	 * vector's direction.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of an object by concatenating the values of its
	 * instance variables `x` and `y`.
	 * 
	 * @returns a string representation of the current object, consisting of the values
	 * of its `x` and `y` fields enclosed in parentheses.
	 * 
	 * 	- The parentheses denote that the value is a pair of two numbers (x and y).
	 * 	- The values within the parentheses are also expressed as numbers.
	 * 
	 * Therefore, the output "(" + x + " " + y + ")" represents a pair of two numbers.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * sets the `x` and `y` components of a `Vector2f` object.
	 * 
	 * @param x 2D coordinate of the point to which the vector's `x` component should be
	 * set.
	 * 
	 * @param y 2nd component of the `Vector2f` object and assigns it the value provided
	 * by the user.
	 * 
	 * @returns a reference to the same `Vector2f` instance, with the x and y components
	 * updated.
	 * 
	 * 	- `this`: refers to the current instance of the `Vector2f` class, which is the
	 * object that contains the values for x and y.
	 * 	- `x`: sets the value of the `x` attribute of the `Vector2f` object to the argument
	 * passed in the function call.
	 * 	- `y`: sets the value of the `y` attribute of the `Vector2f` object to the argument
	 * passed in the function call.
	 * 
	 * The returned output is a reference to the same instance of the `Vector2f` class,
	 * which means that any changes made to the object within the function are reflected
	 * outside of it as well.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the values of the object to those of the provided `Vector2f` reference.
	 * 
	 * @param r 2D vector to be set as the value of the method, which is then assigned
	 * to the `Vector2f` instance variable `this`.
	 * 
	 * `r`: A `Vector2f` object with two fields: `x` and `y`.
	 * 
	 * @returns a reference to the original vector object with its components updated to
	 * match the values of the provided `r` vector.
	 * 
	 * 	- The function sets the x-coordinate and y-coordinate of the vector to the values
	 * passed as parameters.
	 * 	- The output is a reference to the same vector object, indicating that the original
	 * object is modified.
	 * 	- No additional information about the code author or licensing is provided.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * converts a two-dimensional vector to a three-dimensional vector by adding an
     * additional component representing the z-axis value.
     * 
     * @returns a `Vector3f` object representing a 3D point with coordinates (x, y, 0).
     * 
     * 	- The `x`, `y`, and `z` values represent the coordinates of the 3D vector in the
     * range of -1 to 1.
     * 	- The vector is a new instance of the `Vector3f` class, indicating that it has
     * its own unique set of properties distinct from any other vectors.
     * 	- The `0` value for the `z` coordinate indicates that the vector points solely
     * in the x-y plane, without any z-component.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * retrieves the value of the `x` field.
	 * 
	 * @returns a floating-point value representing the X coordinate of an object.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of its object to the provided float argument.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being manipulated by the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which is a floating-point number representing
	 * the vertical position of an object.
	 * 
	 * @returns a float value representing the y-coordinate of a point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the object's `y` field to the input `float` value.
	 * 
	 * @param y float value that is assigned to the `y` field of the class instance being
	 * manipulated by the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares two `Vector2f` objects by checking if their `x` and `y` components are equal.
	 * 
	 * @param r 2D vector to be compared with the current vector for equality.
	 * 
	 * 	- `r.getX()` returns the x-coordinate of the vector.
	 * 	- `r.getY()` returns the y-coordinate of the vector.
	 * 
	 * @returns a boolean value indicating whether the vector's x and y components are
	 * equal to the corresponding components of the provided vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
