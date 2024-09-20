package com.ch.math;

/**
 * Represents a two-dimensional vector with x and y components, implementing various
 * mathematical operations such as addition, subtraction, multiplication, division,
 * normalization, and rotation. It also includes methods for calculating length,
 * maximum value, dot product, cross product, and interpolation between vectors. The
 * class provides a string representation of the vector and support for 3D conversion.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance of a point from the origin, using the Pythagorean
	 * theorem with coordinates `x` and `y`. The result is returned as a floating-point
	 * value. The calculation involves squaring the coordinates, summing them, and then
	 * taking the square root of the result.
	 *
	 * @returns a floating-point value representing the distance of the point from the origin.
	 * Calculated as the Euclidean norm of the point's coordinates.
	 * Approximated using the square root of the sum of squares.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the maximum value between two variables `x` and `y`. It utilizes Java's
	 * built-in `Math.max` method to perform the comparison. This method can be used to
	 * determine the larger of two values.
	 *
	 * @returns the greater of two values stored in variables x and y. The result is a
	 * floating-point number representing the maximum value. It is the largest numeric
	 * value between x and y.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * Computes the dot product of a vector with another given vector. It multiplies
	 * corresponding components of both vectors and sums them up to produce the result.
	 * The resulting value represents the scalar projection of one vector onto the other.
	 *
	 * @param r 2D vector with which the current vector's components are multiplied and
	 * summed in the dot product calculation.
	 *
	 * @returns a scalar value resulting from the dot product of two vectors.
	 * It calculates the sum of products of corresponding components.
	 * The result is a float representing the magnitude of their projection.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Calculates a vector's magnitude and returns a new vector with components divided
	 * by that magnitude. The result is a unit vector in the same direction as the original
	 * vector. This is used to normalize a vector.
	 *
	 * @returns a unit vector (length of 1) with original direction.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * Computes the cross product between two vectors, resulting in a scalar value
	 * representing the z-component of their vector product.
	 * It takes another 2D vector `r` as input and returns its magnitude times the sine
	 * of the angle with the original vector.
	 * This operation is also known as the "perpendicular distance" or "z-component".
	 *
	 * @param r 2D vector being crossed with the vector represented by `x` and `y`, its
	 * elements used to calculate the cross product result.
	 *
	 * @returns a scalar value representing the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * Linearly interpolates between two points. It calculates a new point that is a
	 * fraction (`lerpFactor`) of the distance from the current object to the destination
	 * point, then adds this interpolated difference to the original position. The result
	 * is a smooth transition between the two points.
	 *
	 * @param dest 2D vector towards which interpolation is being performed, serving as
	 * the target point for linear interpolation with the current position.
	 *
	 * @param lerpFactor 0 to 1 interpolation factor that controls the amount of vector
	 * transformation applied from the original position to the destination.
	 *
	 * @returns a new `Vector2f` interpolated between the current vector and `dest`.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Transforms a 2D vector by applying a rotation transformation, based on the provided
	 * angle. The new vector coordinates are calculated using trigonometric functions to
	 * rotate the original vector around its origin. The result is a new vector with
	 * updated x and y components after rotation.
	 *
	 * @param angle 2D rotation to be applied to the current vector, specified in degrees.
	 *
	 * @returns a rotated version of the input vector's x and y coordinates.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * Takes a vector as input and returns a new vector with components equal to the sum
	 * of corresponding components from the original vector and the input vector. The
	 * operation is element-wise addition. A new vector is created for each addition.
	 *
	 * @param r 2D vector to be added to the current vector, with its x and y components
	 * accessed through the getX() and getY() methods respectively.
	 *
	 * @returns a new `Vector2f` object with summed components.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * Scales a vector by adding a specified float value to both its x and y components.
	 * The result is a new vector with the scaled coordinates. A copy of the original
	 * vector is returned, unchanged.
	 *
	 * @param r 2D offset to be added to the current vector, scaling equally in both x
	 * and y directions.
	 *
	 * @returns a new vector with updated x and y coordinates.
	 * The values are the sum of the original vector's coordinates and the input parameter
	 * 'r'.
	 * Resultant vector's type remains Vector2f.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * Creates a new vector by adding two input floats to its corresponding component
     * values (x and y). The result is a new `Vector2f` object with the added coordinates,
     * without modifying the original vector. A copy of this new vector is then returned.
     *
     * @param x 0-axis component to be added to the current vector's x-coordinate.
     *
     * @param y vertical component to be added to the existing vector's vertical component.
     *
     * @returns a new vector with components that are sums of input values and existing
     * vector's components. The output vector has updated x and y coordinates. New Vector2f
     * object is created.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * Subtracts a given `Vector2f` object from another, element-wise, returning a new
	 * vector with the result. The original vectors remain unchanged. This operation is
	 * equivalent to calculating the difference between two points or positions.
	 *
	 * @param r 2D vector to be subtracted from the instance vector's x and y components,
	 * resulting in a new Vector2f object with the difference values.
	 *
	 * @returns a new vector representing the difference between two vectors.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * Subtracts a specified floating-point value from both components of the current
	 * vector. A new instance of `Vector2f` is created with the resulting values. The
	 * original vector remains unchanged.
	 *
	 * @param r value to be subtracted from both the x and y components of the vector.
	 *
	 * @returns a new `Vector2f` with coordinates reduced by the specified value. The x
	 * and y components are subtracted from their respective initial values. A Vector2f
	 * object representing a reduced position is created.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * Performs element-wise scalar multiplication between two instances of a vector
	 * class. It takes another instance of the vector class as input, multiplies corresponding
	 * elements (x and y), and returns a new instance with the result. This operation
	 * allows for scaling or componentwise modification of vectors.
	 *
	 * @param r 2D vector to be scaled by the current object's components, x and y.
	 *
	 * @returns a new vector with elements scaled by the input vector's corresponding components.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * Multiplies each component of a vector by a scalar value, returning a new vector
	 * with the scaled components. The operation is element-wise, applying the multiplication
	 * to both x and y coordinates. The original vector remains unchanged.
	 *
	 * @param r scalar value by which each component of the vector is multiplied.
	 *
	 * @returns a new vector with components scaled by the input factor.
	 * The resultant vector has x and y coordinates multiplied by the given float value.
	 * It is a new Vector2f object.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * Divides a vector by another vector, resulting in a new vector with its components
	 * divided by the corresponding components of the divisor. The division is element-wise,
	 * meaning each component of the result vector is the corresponding component of the
	 * original vector divided by the corresponding component of the divisor.
	 *
	 * @param r 2D vector by which the division is performed.
	 *
	 * @returns a new Vector2f containing the result of element-wise division. The resulting
	 * x and y components are calculated as the input vector's x/y component divided by
	 * the corresponding component of the argument vector r.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * Performs division operation on a vector component-wise by a scalar value. It divides
	 * the x and y components of the vector by the given scalar `r`, returning a new
	 * Vector2f object with the result. This operation is often used to scale or normalize
	 * vectors.
	 *
	 * @param r divisor used to scale down the vector's components by division.
	 *
	 * @returns a Vector2f object containing the x and y coordinates divided by the input
	 * float r.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * Computes the absolute values of a vector's components, returning a new vector with
	 * the same dimensions but all elements represented by their absolute magnitude. It
	 * creates and returns an instance of Vector2f with its x and y components set to the
	 * absolute values of the original vector's x and y components.
	 *
	 * @returns a new vector with absolute values of its components.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * Returns a string representation of an object, specifically the coordinates (x, y).
	 * It concatenates the values of x and y with spaces in between and encloses them
	 * within parentheses. This allows for a human-readable display of the object's state.
	 *
	 * @returns a string representation of the object's coordinates as a pair.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * Initializes a vector with specified x and y coordinates. It assigns these values
	 * to the object's internal state, allowing for chaining method calls to simplify
	 * code. The function returns the modified object itself, enabling fluent interface
	 * usage.
	 *
	 * @param x 0-based horizontal coordinate of a point or vector, which is assigned to
	 * the object's internal state.
	 *
	 * @param y 2D coordinate's vertical position or value.
	 *
	 * @returns a reference to the modified instance of the Vector2f class.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets a vector's coordinates to those of another vector, and returns the current
	 * object instance for method chaining. The vector's x and y components are updated
	 * with values from the provided reference vector r. The operation is performed through
	 * an internal `set` method.
	 *
	 * @param r 2D vector whose values are used to update the current object's coordinates.
	 *
	 * @returns an instance of `Vector2f`, with updated x and y values.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * Creates a new three-dimensional vector with x and y components from the current
     * object and sets z component to zero. It returns an instance of Vector3f representing
     * a point or direction in 3D space.
     *
     * @returns a 3D vector with x and y components set to the original values.
     * Its z-component defaults to zero.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * Returns a floating-point value representing the current state of variable `x`. The
	 * returned value is accessible to any part of the program that calls the function,
	 * allowing retrieval and potential manipulation elsewhere in the code. The return
	 * type specifies a single float result.
	 *
	 * @returns a floating-point number representing the value of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Accepts a float value as input and assigns it to the instance variable `x`. This
	 * allows for modification of the object's state by external means. The new value is
	 * stored directly, without validation or manipulation.
	 *
	 * @param x 2D point's X-coordinate to be assigned to the class instance variable
	 * with the same name, effectively updating its value.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Retrieves and returns a value stored in the instance variable `y`, which is
	 * presumably a position or coordinate on the Y-axis, as suggested by its name. The
	 * function has no arguments and simply exposes the internal state. Its return type
	 * is `float`.
	 *
	 * @returns a floating-point value representing the current y-coordinate of an object.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates the value of a variable `y` to a specified float value. It assigns the
	 * provided `y` parameter to an instance field named `y`, effectively changing its
	 * state. The updated value is then stored for future reference or calculation purposes.
	 *
	 * @param y vertical coordinate value to be assigned to an object's property, allowing
	 * its position to be updated.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Compares two instances of a class representing a 2D vector, returning true if both
	 * vectors have identical x and y coordinates. It employs strict equality checks to
	 * determine equivalence. The result is a boolean value indicating whether the compared
	 * vectors are equal.
	 *
	 * @param r 2D vector to compare with the current object's position for equality purposes.
	 *
	 * @returns a boolean value indicating equality between two Vector2f objects.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
