package com.ch.math;

/**
 * is a mathematical representation of a 2D vector in Java. It has two fields: x and
 * y, which represent the components of the vector, and several methods for manipulating
 * and computing with the vector. These include length(), max(), dot(), cross(),
 * lerp(), rotate(), add(), sub(), mul(), div(), abs(), set() and as3DVector(). The
 * class also provides methods for checking equality with another vector.
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

 /**
  * calculates the Euclidean distance of a point in two-dimensional space by taking
  * the square root of the sum of the squares of its x and y coordinates.
  * 
  * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

 /**
  * computes the maximum value of two floating-point numbers `x` and `y`.
  * 
  * @returns the maximum value of either `x` or `y`.
  */
	public float max() {
		return Math.max(x, y);
	}

 /**
  * computes the dot product of a `Vector2f` object and a given `Vector2f` argument,
  * returning the result as a floating-point value.
  * 
  * @param r 2D vector to which the `x` and `y` components of the returning value will
  * be dot-producted.
  * 
  * `r`: A `Vector2f` instance containing the coordinates (x, y) of a point in 2D
  * space. The `x` and `y` attributes hold the individual coordinates, respectively.
  * 
  * @returns a floating-point number representing the dot product of the input vector
  * and another vector represented by `r`.
  */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

 /**
  * normalizes a vector by dividing its components by the vector's length, resulting
  * in a unitized vector with magnitude equal to one.
  * 
  * @returns a normalized vector with x and y components scaled proportionally to the
  * length of the original vector.
  * 
  * 	- The output is a `Vector2f` object representing a normalized version of the
  * original vector.
  * 	- The x-component of the output is calculated as `x / length`, where `length` is
  * the magnitude (or length) of the original vector.
  * 	- The y-component of the output is calculated similarly, i.e., `y / length`.
  * 	- The resulting vector has a magnitude equal to the magnitude of the original
  * vector, but its direction is normalized to lie on the axis of the coordinate system.
  */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

 /**
  * takes a `Vector2f` argument `r` and returns the product of its `x` and `y` components,
  * without using any actual multiplication operation.
  * 
  * @param r 2D vector to be multiplied with the current vector, resulting in a new
  * 2D vector that is the cross product of the two vectors.
  * 
  * 	- `r` is an instance of Vector2f, which represents a 2D point or vector in the
  * Java programming language.
  * 	- `r.getX()` and `r.getY()` return the x- and y-coordinates of the point or vector,
  * respectively.
  * 	- The returned value is the dot product of `x` and `r.y` minus the dot product
  * of `y` and `r.x`.
  * 
  * @returns a scalar value representing the cross product of two vectors.
  */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

 /**
  * calculates the linear interpolation between two vector values, `dest` and `this`,
  * based on a specified factor `lerpFactor`. It returns a new vector representing the
  * interpolated value.
  * 
  * @param dest 2D destination vector that the current vector will be interpolated to.
  * 
  * 	- `dest`: This is the destination point where the linear interpolation will be
  * performed. It has two components - `x` and `y`, representing the position of the
  * point in the x-axis and y-axis, respectively.
  * 	- `lerpFactor`: This is a scalar value that represents the factor by which the
  * current position will be interpolated towards the destination point.
  * 
  * @param lerpFactor scalar value that determines the interpolation between the current
  * position and the destination position of the vector.
  * 
  * @returns a new `Vector2f` object that represents the interpolated value between
  * the current position and the destination position.
  * 
  * The `Vector2f` object returned by `lerp` represents the interpolated position
  * between the original `this` object and the destination `dest` object. The `mul`
  * method is applied to the difference between the two objects (`this - dest`) to
  * produce a value representing the interpolation factor, which is then added to the
  * original object using the `add` method. This produces the final interpolated position.
  */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * rotates a vector by an angle in radians, returning the rotated vector in a new
  * coordinate system.
  * 
  * @param angle 2D rotation angle in radians, which is multiplied by the cosine and
  * sine of that angle to produce the new coordinates of the rotated vector.
  * 
  * @returns a new `Vector2f` instance with the x-axis and y-axis values rotated by
  * the provided angle.
  * 
  * 	- The output is a `Vector2f` object, representing a 2D point in the xy-plane.
  * 	- The x-coordinate of the output is calculated as (x * cos - y * sin), where x
  * and y are the input coordinates of the function.
  * 	- The y-coordinate of the output is calculated as (x * sin + y * cos), again using
  * the input coordinates x and y.
  * 
  * In summary, the `rotate` function takes a float angle as input and returns a new
  * `Vector2f` object representing the rotated point based on the cosine and sine of
  * the angle in radians.
  */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` object representing
  * the sum of the current object's components and the argument's components.
  * 
  * @param r 2D vector to be added to the current vector.
  * 
  * 	- `x`: The x-coordinate of the input vector.
  * 	- `y`: The y-coordinate of the input vector.
  * 
  * @returns a new vector with the sum of the input vectors' x and y coordinates.
  * 
  * 	- The returned object is a new instance of `Vector2f`, which represents a 2D
  * vector with x and y components.
  * 	- The x component of the returned vector is the sum of the x component of the
  * input vector `r` and the x component of the current vector.
  * 	- The y component of the returned vector is the sum of the y component of the
  * input vector `r` and the y component of the current vector.
  */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

 /**
  * takes a single float argument `r` and returns a new `Vector2f` instance with the
  * sum of the current vector's `x` and `y` components and the provided `r` value.
  * 
  * @param r 2D vector to add to the current vector.
  * 
  * @returns a new `Vector2f` instance with the sum of the original vector's coordinates
  * and the given float value.
  * 
  * The returned Vector2f object represents a point in 2D space with x-coordinate equal
  * to the original vector's x coordinate plus the input parameter r and y-coordinate
  * equal to the original vector's y coordinate plus the input parameter r.
  */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * adds two floating-point values to a `Vector2f` object, returning a new `Vector2f`
     * instance with the sum of the original object's x and y components and the input x
     * and y values.
     * 
     * @param x 2D coordinate to add to the current position of the vector.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` object representing the sum of the input `x` and `y` values.
     * 
     * The returned Vector2f object has an x-component that is equal to the sum of the
     * x-component of this object and the argument x, and a y-component that is equal to
     * the sum of the y-component of this object and the argument y.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` instance representing
  * the difference between the current vector's components and those of the input vector.
  * 
  * @param r 2D vector to be subtracted from the current vector, resulting in a new
  * vector that represents the difference between the two vectors.
  * 
  * 	- `x`: The real value of the first component of the input vector.
  * 	- `y`: The real value of the second component of the input vector.
  * 
  * @returns a new `Vector2f` object representing the difference between the input
  * vector and the reference vector.
  * 
  * The returned Vector2f object has coordinates x and y that represent the difference
  * between the original vector's coordinates and the given vector's coordinates.
  */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

 /**
  * takes a single floating-point value `r` and returns a new `Vector2f` object
  * representing the difference between the current vector's coordinates and `r`.
  * 
  * @param r 2D point that the function subtracts from the current vector position to
  * produce the new vector output.
  * 
  * @returns a new `Vector2f` object representing the difference between the original
  * vector and the given value.
  * 
  * The `Vector2f` object returned by the function represents the difference between
  * the original vector's components and the input parameter `r`. Specifically, the
  * x-coordinate of the returned vector is equal to the original vector's x-coordinate
  * minus the input `r`, while its y-coordinate is equal to the original vector's
  * y-coordinate minus the input `r`.
  * 
  * The returned vector has the same components as the original vector, but with the
  * values shifted by the amount of `r` provided as input. This function can be used
  * to subtract a scalar value from a vector, effectively moving the vector in the
  * opposite direction by that amount.
  */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

 /**
  * multiplies the vector's x and y components by the corresponding components of
  * another vector, returning a new vector with the result.
  * 
  * @param r 2D vector to be multiplied with the current vector, resulting in a new
  * 2D vector with the product of the corresponding components.
  * 
  * 	- `x`: The x-coordinate of `r`, which is a `float`.
  * 	- `y`: The y-coordinate of `r`, which is also a `float`.
  * 
  * @returns a new `Vector2f` instance with the product of the input vector's `x` and
  * `y` components multiplied by the corresponding components of the given vector `r`.
  * 
  * The output is a new Vector2f object that represents the product of the input vectors.
  * 
  * The x-coordinate of the output is equal to the product of the x-coordinates of the
  * input vectors.
  * 
  * The y-coordinate of the output is equal to the product of the y-coordinates of the
  * input vectors.
  */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

 /**
  * multiplies the components of a `Vector2f` object by a given scalar value.
  * 
  * @param r scalar value used to multiply the `x` and `y` components of the `Vector2f`
  * instance.
  * 
  * @returns a vector with x and y components scaled by the input value `r`.
  * 
  * The output is a `Vector2f` object that represents the product of the input `x` and
  * `r`, and the input `y` and `r`. Therefore, the output has two components: `x * r`
  * and `y * r`, which correspond to the x-component and y-component of the resulting
  * vector, respectively.
  */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` object with the
  * components scaled by the reciprocals of the corresponding values in `r`.
  * 
  * @param r 2D vector to which the current vector is being divided.
  * 
  * 	- The `x` property of `r` is used as the dividend in the calculation.
  * 	- The `y` property of `r` is used as the divisor in the calculation.
  * 
  * @returns a vector with the same x-coordinates as the original vector, scaled by
  * the reciprocal of the input vector's x-coordinate.
  * 
  * 	- The output is a new instance of the `Vector2f` class, which means it has two
  * components (x and y) that represent the result of dividing the input vector's x
  * and y components by the input r.getX() and r.getY(), respectively.
  * 	- The output's x component represents the result of dividing the input vector's
  * x component by the input r.getX(), which is a non-negative value representing the
  * scaling factor applied to the x component of the output.
  * 	- The output's y component represents the result of dividing the input vector's
  * y component by the input r.getY(), which is also a non-negative value representing
  * the scaling factor applied to the y component of the output.
  */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

 /**
  * takes a scalar parameter `r` and returns a new `Vector2f` instance with the x and
  * y components scaled by the ratio of `r`.
  * 
  * @param r factor by which the vector's components are divided.
  * 
  * @returns a vector with x and y components scaled by the input parameter `r`.
  * 
  * The output is a `Vector2f` object representing a scaled version of the original vector.
  * The x and y components of the output are calculated by dividing the corresponding
  * components of the original vector by the input parameter `r`.
  * The resulting vector has the same magnitude as the original vector, but its direction
  * is scaled by a factor of `r`.
  */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

 /**
  * computes and returns a new `Vector2f` instance with the absolute values of its `x`
  * and `y` components.
  * 
  * @returns a new `Vector2f` object containing the absolute values of the original
  * vector's `x` and `y` components.
  * 
  * 	- The output is a new `Vector2f` object containing the absolute values of the
  * input `x` and `y` coordinates.
  * 	- The `x` and `y` components of the output are both non-negative integers,
  * representing the absolute values of the original coordinates.
  * 	- The output has the same dimension as the input, with the same type and scale.
  */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

 /**
  * returns a string representation of a point (x, y) by concatenating the values of
  * x and y inside parentheses.
  * 
  * @returns a string representing the point (x, y) using parentheses and the values
  * of x and y as separate components.
  * 
  * 	- The parentheses `( )` are included as part of the output.
  * 	- The variable `x` is included in the output followed by a space character `+`.
  * 	- The variable `y` is also included in the output followed by another space
  * character `+`.
  * 
  * Therefore, the output returned by the `toString` function is simply a concatenation
  * of the values of `x` and `y` separated by spaces.
  */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

 /**
  * sets the `x` and `y` components of a `Vector2f` object to the input values, returning
  * the modified object.
  * 
  * @param x 2D position of the `Vector2f` instance and assigns its value to the `x`
  * field of the instance.
  * 
  * @param y 2nd component of the `Vector2f` object being modified, which is updated
  * to match the provided value.
  * 
  * @returns a reference to the modified vector object.
  * 
  * 	- This is a method that sets the x-coordinate and y-coordinate of the Vector2f instance.
  * 	- The method returns a reference to the same Vector2f instance, indicating that
  * the object itself is modified rather than creating a new version with the updated
  * values.
  * 	- The return type is `Vector2f`, indicating that the method returns an instance
  * of the Vector2f class.
  */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

 /**
  * sets the `x` and `y` components of the `Vector2f` object to the values passed as
  * parameters.
  * 
  * @param r 2D vector that contains the x and y coordinates to be set in the `set()`
  * method of the `Vector2f` class.
  * 
  * 	- `getX()` and `getY()`: These methods return the x-coordinate and y-coordinate
  * values of the input vector, respectively.
  * 
  * @returns a reference to the original vector with its components updated to match
  * the input parameter values.
  * 
  * 	- The output is a reference to the same instance of the `Vector2f` class as the
  * input parameter. This means that any changes made to the input parameter within
  * the function will affect the output as well.
  * 	- The output contains the updated values of the `x` and `y` components of the
  * vector, which are set to the corresponding values of the input parameter.
  */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * converts a `Vector2f` instance into an equivalent `Vector3f` instance by adding a
     * third component representing the z-coordinate of the vector, which is set to zero
     * by default.
     * 
     * @returns a new `Vector3f` instance with the values `x`, `y`, and `0` for the x,
     * y, and z components, respectively.
     * 
     * 	- The `Vector3f` object represents a 3D vector with three components: `x`, `y`,
     * and `z`.
     * 	- The `x`, `y`, and `z` components represent the coordinates of the 3D vector.
     * 	- The vector is returned as an instance of the `Vector3f` class, which provides
     * methods for calculating the dot product, magnitude, and other operations on 3D vectors.
     */
    public Vector3f as3DVector() {
        return new Vector3f(x, y, 0);
    }

 /**
  * retrieves the value of a variable `x`.
  * 
  * @returns a floating-point value representing the variable `x`.
  */
	public float getX() {
		return x;
	}

 /**
  * sets the value of the `x` field of the object to which it belongs.
  * 
  * @param x float value that is assigned to the `x` field of the class instance being
  * modified by the `setX()` method.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * returns the value of the `y` field.
  * 
  * @returns the value of the `y` field, which is a `float` variable.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the instance field `y`.
  * 
  * @param y new value of the instance variable `y` that is being assigned to through
  * the function call.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * compares a `Vector2f` instance `r` to the current instance, returning `true` if
  * both instances have the same `x` and `y` coordinates.
  * 
  * @param r 2D vector to which the current vector is being compared for equality.
  * 
  * 	- `x`: The first component of the vector represents the x-coordinates of the two
  * vectors being compared.
  * 	- `y`: The second component of the vector represents the y-coordinates of the two
  * vectors being compared.
  * 
  * By comparing the values of these components, the function determines if the two
  * vectors are equal.
  * 
  * @returns a boolean value indicating whether the vector's coordinates are equal to
  * those of the provided vector.
  * 
  * 	- The `x` and `y` fields of the returning object reference are compared with their
  * corresponding counterparts in the argument vector using the `==` operator.
  * 	- If these fields match, the function returns `true`.
  * 	- Otherwise, it returns `false`.
  */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
