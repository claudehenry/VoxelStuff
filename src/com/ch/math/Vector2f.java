package com.ch.math;

/**
 * Represents a two-dimensional vector with float components x and y. It provides
 * various mathematical operations for vectors such as addition, subtraction,
 * multiplication, division, normalization, rotation, and length calculation. The
 * class also supports string representation and equals comparison.
 */
public class Vector2f {	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance from the origin to a point represented by its
	 * coordinates (`x` and `y`). It returns the square root of the sum of squares of
	 * these coordinates, cast as a floating-point number. The result is the magnitude
	 * or length of the vector.
	 *
	 * @returns a non-negative floating-point number representing the Euclidean distance
	 * from the origin.
	 * It approximates the square root of the sum of squares of coordinates x and y.
	 * This value ranges from 0 to infinity.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns the maximum value between two floating-point numbers x and y. It utilizes
	 * the built-in Java method `Math.max()` to determine the larger number. The result
	 * is a float representing the greater value.
	 *
	 * @returns a floating-point number representing the maximum value between `x` and `y`.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * Computes the dot product of two vectors, which is a scalar value representing the
	 * amount of "similarity" between them. It multiplies corresponding components and
	 * sums the results. The result is a float value indicating the degree of alignment
	 * or angle between the input vector and reference vector r.
	 *
	 * @param r 2D vector with which the current vector's components are multiplied to
	 * compute the dot product.
	 *
	 * @returns a floating-point value representing the dot product of two vectors.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * Computes the magnitude (length) of a vector and returns a new vector with the same
	 * direction but unit length. The original vector's components are divided by their
	 * magnitude to achieve normalization.
	 *
	 * @returns a new vector with components scaled to unit length.
	 * Its magnitude is 1 and direction remains the same as the original vector.
	 * The result is normalized within the specified range.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * Calculates the oriented area between two vectors, determined by the difference of
	 * their scalar product and cross product. The result is the magnitude of the cross
	 * product of two vectors, returning a signed value indicating direction and magnitude
	 * of the volume enclosed by the vectors.
	 *
	 * @param r 2D vector with which the cross product is to be computed, providing its
	 * x and y components through the methods `getX()` and `getY()`.
	 *
	 * @returns a scalar value representing the magnitude of the cross product of two vectors.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * Interpolates a value by calculating a weighted average between two points. It
	 * subtracts the current object from the destination, scales the result by the lerp
	 * factor, and adds it back to the current object. The resulting value is a smooth
	 * transition between the initial and final values.
	 *
	 * @param dest 2D vector towards which interpolation is being performed, serving as
	 * the target point for the linear interpolation process.
	 *
	 * @param lerpFactor 0-to-1 interpolation factor that determines how much to blend
	 * the current position with the destination position.
	 *
	 * @returns a Vector2f representing a linear interpolation between input and destination
	 * vectors.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Transforms a 2D vector by rotating it around the origin by a specified angle. It
	 * uses trigonometric functions to calculate the new x and y coordinates based on the
	 * original values, rotation angle, cosine, and sine values. The resulting transformed
	 * vector is returned as a new instance.
	 *
	 * @param angle 2D rotation angle, which is converted to radians for mathematical calculations.
	 *
	 * @returns a rotated version of the original vector.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * Calculates the sum of two vectors and returns a new vector with the resulting
	 * components. It takes another vector as input, adds its x and y values to the current
	 * vector's corresponding components, and creates a new instance with these sums. The
	 * original vector remains unchanged.
	 *
	 * @param r 2D vector to be added to the current vector, allowing for element-wise
	 * addition of its x and y components.
	 *
	 * @returns a new vector with elements being the sums of corresponding x and y values.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * Adds a specified value to both the x and y coordinates of a vector. The result is
	 * a new vector with modified coordinates. It does not modify the original vector.
	 *
	 * @param r value to be added to both the x and y components of the vector.
	 *
	 * @returns a new `Vector2f` object with components x and y incremented by r.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * Creates and returns a new vector with components that are the sum of its own
     * components and the provided scalar values. It modifies the original vector's
     * position by adding x and y coordinates from the input parameters. The result is a
     * new instance of Vector2f.
     *
     * @param x x-coordinate value to be added to the current vector's x-coordinate.
     *
     * @param y vertical component of an offset value that is added to the current vector's
     * y-coordinate.
     *
     * @returns a new vector with x and y components that are sums of original and input
     * values. The original vector remains unchanged.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * Performs vector subtraction by creating a new `Vector2f` object with components
	 * that are the differences between corresponding components of itself and another
	 * given `Vector2f`. It takes another vector as input and returns a new vector
	 * representing their difference. The original vectors remain unchanged.
	 *
	 * @param r 2D vector from which components are subtracted.
	 *
	 * @returns a new vector with coordinates resulting from subtracting input vector's
	 * components.
	 * Coordinates are x and y components of the resulting vector.
	 * Resulting vector has same dimensions as input vector.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * Subtracts a given float value from both the x and y coordinates of a Vector2f
	 * object, effectively moving it down and to the left by that amount, and returns the
	 * resulting new vector. The original vector remains unchanged. A new vector is created
	 * with updated values.
	 *
	 * @param r 2D distance to subtract from each component (x and y) of the vector.
	 *
	 * @returns a new vector with components x and y decremented by the specified value.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * Multiplies two vectors by performing element-wise multiplication, where the
	 * x-component of the result is the product of the x-components of the input vectors
	 * and the y-component of the result is the product of the y-components of the input
	 * vectors. The result is a new vector.
	 *
	 * @param r 2D vector with which the current instance's coordinates are multiplied
	 * element-wise to produce a new vector.
	 *
	 * @returns a new vector resulting from element-wise multiplication of input vectors.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * Scales a 2D vector by a given scalar value. It returns a new vector with its
	 * components multiplied by the scale factor. The original vector remains unchanged.
	 *
	 * @param r scalar value by which each component of the vector is multiplied.
	 *
	 * @returns a new scaled Vector2f instance.
	 * It scales the original vector's components by the specified factor.
	 * Resulting vector has elements x*r and y*r.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * Performs division operations on a vector's components by corresponding values from
	 * another vector, returning a new vector with the results. It divides the x-component
	 * by the x-component and y-component by the y-component of the input vector,
	 * respectively. The original vectors remain unchanged.
	 *
	 * @param r 2D vector being divided into, with its components used to normalize the
	 * current vector's components.
	 *
	 * @returns a Vector2f instance with elements divided by corresponding elements of
	 * input Vector2f. The resulting Vector2f has x and y components representing the
	 * division result.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * Normalizes a vector by dividing its x and y components by a scalar value `r`. This
	 * operation effectively scales the vector to have a length of 1 unit. The result is
	 * a new vector with normalized coordinates.
	 *
	 * @param r divisor used to divide the vector's x and y components.
	 *
	 * @returns a Vector2f object with x and y coordinates divided by a scalar value.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * Returns a new vector with absolute values of its components. It creates a copy of
	 * the original vector, applies the absolute value operation to both x and y coordinates,
	 * and returns the resulting vector. The original vector remains unchanged.
	 *
	 * @returns a new vector with absolute values of x and y components.
	 * This new vector has non-negative coordinates.
	 * Its magnitude is greater than or equal to the original vector's magnitude.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * Generates a string representation of an object, specifically for a point or
	 * coordinate with attributes `x` and `y`. The result is a formatted string containing
	 * the values of these attributes enclosed within parentheses. This enables easy
	 * printing of the coordinates.
	 *
	 * @returns a string representation of coordinates in the format "(x y)".
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * Initializes a vector with specified x and y coordinates, updating its internal
	 * state. It then returns the updated vector instance itself, enabling method chaining.
	 * The function modifies the original object's properties by reference.
	 *
	 * @param x 0-based column or horizontal coordinate of a point, assigning it to the
	 * instance variable `this.x`.
	 *
	 * @param y 2D coordinate's vertical position, which is updated to match the provided
	 * value.
	 *
	 * @returns a reference to the updated Vector2f object itself.
	 * Returned as an instance of Vector2f.
	 * Returns the modified Vector2f instance.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Accepts a `Vector2f` object and updates its own x and y coordinates with those
	 * from the input object, using the overloaded `set` function that takes two float
	 * parameters. The updated object is returned as a reference to itself.
	 *
	 * @param r 2D vector whose components are assigned to the current object.
	 *
	 * @returns a reference to the original `Vector2f` object with updated values.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * Creates a new instance of `Vector3f` and initializes its components from existing
     * variables `x` and `y`, while setting the z-component to 0, effectively converting
     * an existing vector to a 3-dimensional representation with zero depth. It returns
     * this newly created vector.
     *
     * @returns a 3D vector with x and y components set to input values. Z component
     * defaults to zero.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * Returns a float value representing an attribute named `x`. It retrieves and exposes
	 * the current state of `x`, making it accessible to other parts of the program. The
	 * returned value can be used for calculations or assignments elsewhere in the code.
	 *
	 * @returns a floating-point value representing the current state of attribute `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets a property named `x` to a given value. It takes a single parameter, a
	 * floating-point number. The updated value is stored in an instance variable or field
	 * `this.x`.
	 *
	 * @param x value that will be assigned to the instance variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns a value representing the y-coordinate of an object or data point, allowing
	 * access to this attribute through method invocation. The return type indicates that
	 * the y-coordinate is represented as a floating-point number. The function simply
	 * retrieves and returns the stored y value.
	 *
	 * @returns a floating-point value representing a coordinate's vertical position,
	 * stored as a private variable named `y`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Assigns a new value to an instance variable named `y`. The value is provided as a
	 * floating-point number parameter, allowing for precise positioning along the y-axis.
	 * The assignment updates the object's internal state with the new value.
	 *
	 * @param y 2D coordinate's vertical position, which is then assigned to an instance
	 * variable of the same name within the method.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Checks for equality between two instances of a class, comparing their x and y
	 * coordinates with another Vector2f object's x and y coordinates. It returns true
	 * if both pairs of coordinates match exactly, false otherwise. Equality is determined
	 * by reference and not value in this case.
	 *
	 * @param r 2D vector being compared to the current object for equality.
	 *
	 * @returns a boolean value indicating vector equality or inequality.
	 * This value is true if the vectors have equal components, false otherwise.
	 * It evaluates to true if both x and y are identical.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
