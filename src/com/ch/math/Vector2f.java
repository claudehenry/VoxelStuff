package com.ch.math;

/**
 * has various methods for manipulating and calculating values related to a 2D vector.
 * These include length, max, dot, normalized, cross, lerp, rotate, add, sub, mul,
 * div, abs, set, and as3DVector. The class also provides equality checking through
 * the equals method.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * calculates the Euclidean distance between a point and the origin, using the
	 * Pythagorean theorem.
	 * 
	 * @returns the square root of the sum of the squares of the x and y coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * computes the maximum value of two input floating-point numbers `x` and `y`, returning
	 * the result as a float value.
	 * 
	 * @returns the larger of the two input values, `x` and `y`, returned as a floating-point
	 * number.
	 */
	public float max() {
		return Math.max(x, y);
	}

	/**
	 * computes the dot product of a `Vector2f` object `r` and the instance variables `x`
	 * and `y` of the same class.
	 * 
	 * @param r 2D vector to be multiplied with the current vector.
	 * 
	 * 	- `x` and `y` are the `float` values of the `r` object's `x` and `y` attributes,
	 * respectively.
	 * 	- The `getX()` and `getY()` methods return the current values of the `x` and `y`
	 * attributes, respectively.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and the vector passed as an argument.
	 */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

	/**
	 * normalizes a `Vector2f` object by dividing its components by the object's magnitude,
	 * returning a new `Vector2f` instance with the normalized values.
	 * 
	 * @returns a vector with x and y components scaled by the length of the original vector.
	 * 
	 * The output is a `Vector2f` object that represents the normalized version of the
	 * original input vector.
	 * 
	 * The x-component of the output is calculated as `x / length`, where `length` is the
	 * magnitude (or length) of the original input vector.
	 * 
	 * The y-component of the output is similarly calculated as `y / length`.
	 * 
	 * The resulting output has a magnitude equal to 1, which means that it represents a
	 * unit vector in the same direction as the original input vector.
	 */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	/**
	 * takes a `Vector2f` object `r` as input and returns the vector product of the
	 * component-wise multiplication of the vector's x and y components with the input
	 * vector `r`.
	 * 
	 * @param r 2D vector that the function will cross with the `x` and `y` components
	 * of the function's output.
	 * 
	 * 	- `r` is an instance of the `Vector2f` class, which represents a 2D vector in Java.
	 * 	- The `x` and `y` fields of `r` contain the coordinates of the vector.
	 * 
	 * @returns a scalar value representing the cross product of two vectors in float format.
	 */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

	/**
	 * computes a vector that is a linear combination of two given vectors, with a specified
	 * factor. The resulting vector is the weighted sum of the input vectors, where the
	 * weight is the lerp factor.
	 * 
	 * @param dest 2D destination vector that the current vector will be interpolated towards.
	 * 
	 * 	- `dest`: A `Vector2f` object that represents the destination point for the linear
	 * interpolation.
	 * 	- `lerpFactor`: A floating-point value representing the interpolation factor
	 * between the current position and the destination point.
	 * 
	 * @param lerpFactor linear interpolation value between the current vector and the
	 * destination vector.
	 * 
	 * @returns a vector that interpolates between the input `Vector2f` and the destination
	 * `Vector2f`.
	 * 
	 * 	- The output is a `Vector2f` object that represents the interpolated position
	 * between the starting position and the destination position.
	 * 	- The `sub()` method is used to calculate the difference between the starting
	 * position and the current position, which is then multiplied by the `lerpFactor`.
	 * 	- The `add()` method is used to add the interpolated position to the current
	 * position, resulting in the final output.
	 * 
	 * Overall, the `lerp` function calculates the position of an object at a specific
	 * time using interpolation, allowing for smooth motion and animations in games or
	 * other graphics-intensive applications.
	 */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a float parameter `angle`, converts it to radians, and returns a new `Vector2f`
	 * instance with the rotated coordinates.
	 * 
	 * @param angle angle of rotation in radians, which is used to calculate the cosine
	 * and sine values that are applied to the vector's x and y components to produce the
	 * rotated vector.
	 * 
	 * @returns a new `Vector2f` object with its x-coordinate rotated by the given angle
	 * and its y-coordinate unchanged.
	 * 
	 * The output is a `Vector2f` object that represents a rotated version of the original
	 * vector.
	 * 
	 * The coordinates of the output vector are calculated using the cosine and sine of
	 * the angle of rotation in radians.
	 * 
	 * The x-coordinate of the output vector is equal to the original x-coordinate
	 * multiplied by the cosine of the angle of rotation, minus the y-coordinate multiplied
	 * by the sine of the angle of rotation.
	 * 
	 * The y-coordinate of the output vector is equal to the original x-coordinate
	 * multiplied by the sine of the angle of rotation, plus the original y-coordinate
	 * multiplied by the cosine of the angle of rotation.
	 */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	/**
	 * takes a `Vector2f` argument `r`, and returns a new `Vector2f` object with the sum
	 * of the current vector's components and the given vector's components.
	 * 
	 * @param r 2D vector to be added to the current vector.
	 * 
	 * 	- `x`: The x-coordinate of the input vector.
	 * 	- `y`: The y-coordinate of the input vector.
	 * 
	 * @returns a new `Vector2f` object representing the sum of the input vectors.
	 * 
	 * The `Vector2f` object returned by the function represents the sum of the input
	 * vectors' `x` and `y` components. It has the form (x + r.getX(), y + r.getY()). The
	 * resulting vector has the same magnitude as both input vectors, indicating that the
	 * operation is commutative.
	 */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	/**
	 * adds a floating-point value to a `Vector2f` object's `x` and `y` components.
	 * 
	 * @param r 2D vector addition to be performed on the `Vector2f` instance being acted
	 * upon.
	 * 
	 * @returns a new `Vector2f` object with the sum of the current vector's x and y
	 * components and the provided float value.
	 * 
	 * The return type of the `add` function is `Vector2f`, which represents a 2D vector
	 * with floating-point components x and y. The expression `(x + r)` and `(y + r)`
	 * produce the component values of the resulting vector, respectively. Therefore, the
	 * returned vector has its x and y components increased by the argument value r.
	 */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * adds two floating-point values to a `Vector2f` instance, returning a new `Vector2f`
     * object with the sum of the original instance's x and y coordinates and the input
     * x and y values.
     * 
     * @param x 2D coordinate that is added to the current vector's position.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` object representing the sum of the current vector's
     * components and the input values.
     * 
     * The output is a new instance of the `Vector2f` class, which is a subclass of the
     * `Vector` class in Java. The returned vector has its `x` and `y` components set to
     * the sum of the corresponding components of the input arguments.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

	/**
	 * takes a `Vector2f` argument `r`, returns a new `Vector2f` instance representing
	 * the difference between the current vector and `r`.
	 * 
	 * @param r 2D vector to be subtracted from the current vector, resulting in the
	 * difference vector.
	 * 
	 * 	- `x`: The x-coordinate of the input vector.
	 * 
	 * @returns a new `Vector2f` object representing the difference between the input
	 * vector and the reference vector.
	 * 
	 * 	- The returned vector has the same x-coordinate as this object minus the corresponding
	 * coordinate of the argument.
	 * 	- The y-coordinate of the returned vector is equal to the y-coordinate of this
	 * object minus the corresponding y-coordinate of the argument.
	 */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	/**
	 * takes a single floating-point argument `r` and returns a new `Vector2f` object
	 * representing the difference between the current vector's components and `r`.
	 * 
	 * @param r 2D vector to subtract from the current vector's components, resulting in
	 * a new vector with the difference between the two vectors.
	 * 
	 * @returns a new `Vector2f` instance representing the difference between the original
	 * vector and a given value.
	 * 
	 * 	- The returned Vector2f object represents the difference between the original
	 * position (x, y) and the given value r in the x-axis and y-axis respectively.
	 */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

	/**
	 * multiplies a `Vector2f` object by a scalar value, returning a new `Vector2f` object
	 * with the product of the two components.
	 * 
	 * @param r 2D vector to which the current vector will be multiplied, resulting in a
	 * new 2D vector.
	 * 
	 * 	- `r.getX()` and `r.getY()` represent the x and y components of the input vector,
	 * respectively.
	 * 
	 * @returns a new vector with the product of the input vector's x and y components
	 * multiplied together.
	 * 
	 * The returned Vector2f object represents the product of the current vector and the
	 * given vector `r`.
	 * 
	 * The x-component of the returned vector is equal to the product of the current
	 * vector's x-coordinate and the scalar value `r.getX()`.
	 * 
	 * The y-component of the returned vector is equal to the product of the current
	 * vector's y-coordinate and the scalar value `r.getY()`.
	 */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	/**
	 * multiplies its `Vector2f` argument by a given scalar value, returning a new
	 * `Vector2f` instance with the result.
	 * 
	 * @param r scalar value that is multiplied with the vector's x and y components to
	 * produce the resultant vector.
	 * 
	 * @returns a vector with components that are the product of the original vector's x
	 * and y components and the input scalar value.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D vector in the form (x,
	 * y).
	 * 	- The x and y components of the output are calculated by multiplying the corresponding
	 * components of the input vector by the scalar value `r`.
	 * 	- The resultant vector has the same direction as the input vector but is scaled
	 * by the scalar value `r`.
	 */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

	/**
	 * takes a reference to another `Vector2f` object `r` and returns a new `Vector2f`
	 * object with the same x-coordinate divided by the x-coordinate of `r`, and the same
	 * y-coordinate divided by the y-coordinate of `r`.
	 * 
	 * @param r 2D vector to which the current vector is divided.
	 * 
	 * 	- The `x` attribute of `r` is used as the dividend in the division operation.
	 * 	- The `y` attribute of `r` is used as the divisor in the division operation.
	 * 
	 * @returns a vector with the same x-coordinate as the original vector, scaled by the
	 * reciprocal of the scalar argument.
	 * 
	 * 	- The vector is created with the x-component being the result of dividing the
	 * input's x-component by the input's y-component and vice versa for the y-component.
	 */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	/**
	 * takes a single floating-point argument `r` and returns a new `Vector2f` instance
	 * with x and y components scaled by the inverse of `r`.
	 * 
	 * @param r scalar value used to divide the vector's components.
	 * 
	 * @returns a new `Vector2f` instance with scaled x and y components proportionate
	 * to the input `r`.
	 * 
	 * 	- The output is a `Vector2f` object representing a 2D point with x-coordinate and
	 * y-coordinate divided by the input value `r`.
	 * 	- The `x` and `y` components of the output represent the scaled coordinates of
	 * the original point.
	 * 	- The output has the same direction and magnitude as the original point before
	 * division, but is reduced in size by a factor of `r`.
	 */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

	/**
	 * takes a `Vector2f` object and returns a new `Vector2f` object with the absolute
	 * value of its components.
	 * 
	 * @returns a new `Vector2f` object containing the absolute values of the input
	 * vector's `x` and `y` components.
	 * 
	 * The Vector2f object returned has two components, x and y, which represent the
	 * absolute value of the original input vector's x and y coordinates, respectively.
	 * 
	 * The returned Vector2f object has a magnitude equal to the Euclidean distance between
	 * the origin and the original input vector.
	 */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	/**
	 * returns a string representation of a point in the Cartesian coordinate system,
	 * comprising the values of 'x' and 'y'.
	 * 
	 * @returns a string representation of a point in Cartesian coordinates, consisting
	 * of two numbers separated by a space.
	 */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

	/**
	 * modifies the component values of a `Vector2f` object, returning the modified object.
	 * 
	 * @param x 2D coordinate value that updates the `x` property of the `Vector2f` object.
	 * 
	 * @param y 2D coordinate of the vector's new position.
	 * 
	 * @returns a reference to the same `Vector2f` instance, with the updated x and y values.
	 * 
	 * The returned output is an instance of the `Vector2f` class, which represents a 2D
	 * vector in the form (x, y).
	 * The `this` reference in the function body refers to the original `Vector2f` object
	 * that called the `set` function.
	 * The `x` and `y` fields of the returned output are set to the input values passed
	 * to the function.
	 */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * sets the x and y coordinates of the receiver vector to the corresponding values
	 * of the provided `Vector2f` argument.
	 * 
	 * @param r 2D vector to be set, and its x and y components are used to update the
	 * corresponding fields of the `Vector2f` object.
	 * 
	 * 	- `getX()` and `getY()` methods are used to retrieve the x-coordinate and
	 * y-coordinate values of `r`, respectively.
	 * 	- `Vector2f` is a class that represents a 2D vector in the function signature.
	 * 
	 * @returns a reference to the same `Vector2f` object, unchanged.
	 * 
	 * 	- The output is a reference to the same instance as the original input, allowing
	 * for efficient chaining of method calls.
	 * 	- The output's `x` and `y` components are set to the corresponding values of the
	 * input parameter `r`.
	 */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * creates a new `Vector3f` instance with the given `x`, `y`, and `z` components.
     * 
     * @returns a `Vector3f` object containing the values (x, y, 0).
     * 
     * The output is a `Vector3f` object, which represents a 3D vector with three components:
     * x, y, and z. These components represent the position of the vector in 3D space.
     * 
     * The `x`, `y`, and `z` components of the vector are assigned the values of the
     * original input variables `x`, `y`, and `0`, respectively.
     * 
     * The resulting `Vector3f` object can be used for various purposes, such as calculations,
     * transformations, or visualization in a 3D space.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

	/**
	 * retrieves the value of the `x` field, which is a `float` variable representing the
	 * x-coordinate of an object or point.
	 * 
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the field `x` to the provided floating-point argument.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being manipulated by the function.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field.
	 * 
	 * @returns a floating-point value representing the height of an object.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field of its object reference parameter to the float
	 * value provided in the method invocation.
	 * 
	 * @param y value of the object's `y` field, which is being assigned to by the function.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * compares the `x` and `y` coordinates of its argument with those of the current
	 * instance, returning `true` if they match.
	 * 
	 * @param r 2D vector to which the current vector is being compared.
	 * 
	 * 	- `x`: The `x` property of `r` is an instance of the `Vector2f` class, representing
	 * the x-coordinate of the vector.
	 * 	- `y`: The `y` property of `r` is an instance of the `Vector2f` class, representing
	 * the y-coordinate of the vector.
	 * 
	 * @returns a boolean value indicating whether the vector's coordinates are equal to
	 * those of the provided vector.
	 */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
