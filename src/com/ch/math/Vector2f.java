package com.ch.math;

/**
 * TODO
 */
public class Vector2f {
	
	private float x;
	private float y;

	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

 /**
  * calculates the Euclidean distance of a point in two-dimensional space, given its
  * coordinates `x` and `y`. It uses the formula for the length of a vector to return
  * the square root of the sum of the squares of the coordinates.
  * 
  * @returns the square root of the sum of the squares of the `x` and `y` coordinates.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

 /**
  * returns the maximum value of two floating-point arguments `x` and `y`.
  * 
  * @returns the larger of the two input values, `x` and `y`, represented as a
  * floating-point number.
  */
	public float max() {
		return Math.max(x, y);
	}

 /**
  * takes a `Vector2f` object `r` as input and computes the dot product of its `x` and
  * `y` components with the corresponding components of the function's input parameters,
  * returning the result as a float value.
  * 
  * @param r 2D vector to be multiplied with the component values of the `Vector2f`
  * object returned by the method.
  * 
  * 	- `r` is a `Vector2f` class instance representing a two-dimensional vector in
  * homogeneous coordinates.
  * 	- `x` and `y` are the x and y coordinates of the vector, respectively.
  * 
  * @returns a floating-point number representing the dot product of the input vector
  * and the component values of the function.
  */
	public float dot(Vector2f r) {
		return x * r.getX() + y * r.getY();
	}

 /**
  * normalizes a vector by dividing its components by their magnitudes, returning a
  * new vector with the same direction but with magnitude equal to the original vector's
  * length.
  * 
  * @returns a normalized version of the input vector, with a length of 1.
  * 
  * 	- The returned vector has the same x-coordinates as the original input vector,
  * scaled by the length of the original vector.
  * 	- The returned vector has the same y-coordinates as the original input vector,
  * scaled by the length of the original vector.
  * 	- The scale factor is always non-zero, meaning that the output vector will never
  * be zero or have a magnitude of zero.
  * 
  * Overall, the `normalized` function takes an input vector and returns a scaled
  * version of it, where the scaling factor is the length of the input vector.
  */
	public Vector2f normalized() {
		float length = length();

		return new Vector2f(x / length, y / length);
	}

 /**
  * computes the vector product of two vectors, returning a scalar value representing
  * the magnitude of the cross product.
  * 
  * @param r 2D vector to perform the cross product with the current vector.
  * 
  * 	- `r` is a `Vector2f` object representing a 2D point or vector in the mathematical
  * space.
  * 	- `x` and `y` are the coordinates of the point or vector, which are float values.
  * 
  * The function calculates the cross product of two vectors by multiplying the
  * x-coordinate of the first vector by the y-coordinate of the second vector and vice
  * versa, and then returning the result as a floating-point value.
  * 
  * @returns a floating-point value representing the dot product of two vectors.
  */
	public float cross(Vector2f r) {
		return x * r.getY() - y * r.getX();
	}

 /**
  * takes a `Vector2f` object `dest` and a `float` parameter `lerpFactor`, and returns
  * a new `Vector2f` object that is a linear interpolation of the current object and
  * `dest`.
  * 
  * @param dest 2D destination point to which the vector is being interpolated towards.
  * 
  * The `dest` parameter is of type `Vector2f`, which represents a 2D vector in
  * mathematical notation. It contains the target values for the lerping operation.
  * 
  * @param lerpFactor linear interpolation factor between the current vector value and
  * the destination vector value.
  * 
  * @returns a vector that interpolates between the input `dest` and the current
  * position of the object.
  * 
  * 	- The output is a `Vector2f` object that represents the interpolated value between
  * the input `dest` and the current position of the entity.
  * 	- The interpolation is performed using the `sub`, `mul`, and `add` methods of the
  * `Vector2f` class, which allow for smooth and precise calculation of the interpolated
  * value.
  * 	- The `lerpFactor` parameter represents the ratio of the distance between the
  * input `dest` and the current position of the entity to the total distance between
  * the input `dest` and the entity's final destination.
  */
	public Vector2f lerp(Vector2f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * rotates a `Vector2f` object by the specified angle in radians, returning a new
  * `Vector2f` object with the rotated coordinates.
  * 
  * @param angle 2D angle of rotation in radians, which is used to calculate the
  * resulting vector's coordinates.
  * 
  * @returns a new `Vector2f` object with the x and y components rotated by the specified
  * angle.
  * 
  * The output is a `Vector2f` object, which represents a 2D point in homogeneous
  * coordinates. The x-coordinate represents the horizontal position of the point,
  * while the y-coordinate represents its vertical position.
  * 
  * The values of the output are calculated by multiplying the original point's x and
  * y coordinates by the cosine and sine of the angle of rotation, respectively. This
  * results in a new point that has been rotated around the origin by the specified angle.
  */
	public Vector2f rotate(float angle) {
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` instance with the sum
  * of the current vector's components and those of the given `r` vector.
  * 
  * @param r 2D vector to be added to the current vector.
  * 
  * The `Vector2f` object `r` represents a two-dimensional vector with an x-component
  * and a y-component, both of which are doubles. The x-component is represented by
  * the variable `x`, and the y-component is represented by the variable `y`.
  * 
  * @returns a new `Vector2f` object representing the sum of the input vectors.
  * 
  * The returned vector has an x-coordinate that is equal to the sum of the x-coordinates
  * of the two input vectors and a y-coordinate that is equal to the sum of the
  * y-coordinates of the two input vectors.
  */
	public Vector2f add(Vector2f r) {
		return new Vector2f(x + r.getX(), y + r.getY());
	}

 /**
  * adds a floating-point value to the vector's x and y components, returning a new
  * vector object with the updated values.
  * 
  * @param r 2D vector to be added to the current vector.
  * 
  * @returns a new `Vector2f` instance with the sum of the original vector's components
  * and the given scalar value.
  * 
  * The Vector2f object returned by the `add` function has two components, x and y,
  * which represent the added value in each dimension, respectively. Specifically, the
  * x component represents the addition of r to the existing x-coordinate, while the
  * y component represents the addition of r to the existing y-coordinate. The resulting
  * vector maintains the same orientation as the original input vector.
  */
	public Vector2f add(float r) {
		return new Vector2f(x + r, y + r);
	}

    /**
     * takes two floating-point arguments `x` and `y`, and returns a new `Vector2f`
     * instance with the sum of the current instance's `x` and `y` components and the
     * input `x` and `y` components.
     * 
     * @param x 2D coordinate that is added to the current position of the vector.
     * 
     * @param y 2nd component of the resulting vector.
     * 
     * @returns a new `Vector2f` instance representing the sum of the input `x` and `y`
     * values.
     * 
     * The return type is `Vector2f`, which means it represents a 2D vector with
     * floating-point components.
     * The method takes two floating-point arguments `x` and `y` and adds them to the
     * corresponding components of this object's vector, resulting in a new vector object
     * that represents the sum of the original vector and the input values.
     */
    public Vector2f add(float x, float y) {
        return new Vector2f(this.x + x, this.y + y);
    }

 /**
  * takes a `Vector2f` argument `r` and returns a new `Vector2f` object representing
  * the difference between the current vector and `r`.
  * 
  * @param r 2D vector to be subtracted from the current vector.
  * 
  * 	- `x`: The x-coordinate of the vector, which is also the input to the function.
  * 	- `y`: The y-coordinate of the vector, which is also a part of the input to the
  * function.
  * 
  * @returns a new vector representing the difference between the input vector and the
  * reference vector.
  * 
  * The returned value represents the difference between the original vector's components
  * (x and y) and the input vector's components (r.x and r.y).
  * 
  * The resulting vector has the same magnitude as the original vector, but its direction
  * is shifted by the amount of the input vector's components.
  * 
  * The returned value can be used to calculate various geometric calculations such
  * as distances, angles, and areas.
  */
	public Vector2f sub(Vector2f r) {
		return new Vector2f(x - r.getX(), y - r.getY());
	}

 /**
  * takes a single float argument `r` and returns a new `Vector2f` object with components
  * `x - r` and `y - r`.
  * 
  * @param r 2D vector to subtract from the current vector.
  * 
  * @returns a new `Vector2f` instance representing the difference between the original
  * vector and the given scalar value.
  * 
  * The output is a `Vector2f` object representing a point in 2D space, where `x` and
  * `y` represent the x- and y-coordinates, respectively, of the point. The value of
  * `r` is subtracted from both coordinates to obtain the resulting point.
  */
	public Vector2f sub(float r) {
		return new Vector2f(x - r, y - r);
	}

 /**
  * multiplies a `Vector2f` object by another `Vector2f` object, returning a new
  * `Vector2f` with the product of the two components.
  * 
  * @param r 2D vector that multiplies with the current vector, resulting in a new 2D
  * vector output.
  * 
  * 	- `x`: The real part of the vector representing the x-coordinate.
  * 	- `y`: The imaginary part of the vector representing the y-coordinate.
  * 
  * @returns a new vector with the product of the input vector's x and y components
  * multiplied by the corresponding components of the passed vector.
  * 
  * The `Vector2f` object returned by the function has the same x-coordinate as the
  * input `r`, and the y-coordinate is obtained by multiplying the x-coordinate of the
  * input by the scalar value passed as an argument to the function.
  * 
  * The resulting vector object has a length equal to the product of the magnitudes
  * of the two input vectors, and its direction is the same as that of the input vector
  * r, but with its magnitude increased by the product of the scalar value and the
  * magnitude of the input vector.
  */
	public Vector2f mul(Vector2f r) {
		return new Vector2f(x * r.getX(), y * r.getY());
	}

 /**
  * multiplies the components of a `Vector2f` object by a given scalar value, returning
  * a new `Vector2f` object with the scaled components.
  * 
  * @param r scalar value to be multiplied with the vector components of the `Vector2f`
  * instance returned by the function.
  * 
  * @returns a new `Vector2f` instance with x and y components scaled by the input `r`.
  * 
  * The output is a `Vector2f` object, representing a 2D point with x and y components.
  * The x component of the output is equal to the product of the x component of the
  * input vector and the scalar value r.
  * The y component of the output is equal to the product of the y component of the
  * input vector and the scalar value r.
  */
	public Vector2f mul(float r) {
		return new Vector2f(x * r, y * r);
	}

 /**
  * takes a vector `r` as input and returns a new vector with the same x-component
  * scaled by the reciprocal of the x-component of `r`, and the y-component scaled by
  * the reciprocal of the y-component of `r`.
  * 
  * @param r 2D vector to be divided by the current vector.
  * 
  * 	- `x`: The x-coordinate of `r`.
  * 	- `y`: The y-coordinate of `r`.
  * 
  * @returns a new vector with the same x-coordinates as the original vector, scaled
  * by the reciprocal of the input vector's x-coordinate.
  * 
  * The output is a `Vector2f` object with x-coord and y-coord components calculated
  * as follows: `x / r.getX()` and `y / r.getY()`. Therefore, the output has the same
  * units (e.g. pixels) as the input.
  */
	public Vector2f div(Vector2f r) {
		return new Vector2f(x / r.getX(), y / r.getY());
	}

 /**
  * takes a single floating-point argument `r` and returns a new `Vector2f` instance
  * with scaled x and y components proportional to `r`.
  * 
  * @param r scalar value that is used to perform the division on the `x` and `y`
  * components of the `Vector2f` object.
  * 
  * @returns a new `Vector2f` instance with the x-coordinate and y-coordinate scaled
  * by the input parameter `r`.
  * 
  * 	- The output is a `Vector2f` object representing a scaled version of the original
  * vector.
  * 	- The scaling factor is the input parameter `r`.
  * 	- The x and y components of the output are calculated as the original x and y
  * components divided by the input r.
  */
	public Vector2f div(float r) {
		return new Vector2f(x / r, y / r);
	}

 /**
  * calculates and returns a new `Vector2f` instance with the absolute values of its
  * `x` and `y` components.
  * 
  * @returns a new `Vector2f` instance with the absolute value of its input components.
  * 
  * 	- The output is a Vector2f object representing the absolute value of the input Vector2f.
  * 	- The x and y components of the output are equal to the absolute values of the
  * corresponding input components.
  * 	- The output has the same dimensions as the input, i.e., it has the same number
  * of elements.
  */
	public Vector2f abs() {
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

 /**
  * returns a string representing the location of an object in a two-dimensional space,
  * comprising its x and y coordinates enclosed in parentheses.
  * 
  * @returns a string representing the point (x, y) using parentheses and the values
  * of x and y separated by a space.
  * 
  * 	- The parentheses in the return statement indicate that the output is a tuple of
  * two values, x and y.
  * 	- The individual values within the parentheses are also expressed as literals,
  * indicating that they are not variables or expressions that can be evaluated at runtime.
  * 	- The concatenation operator used to combine the two values into a single string
  * (" " separates them) suggests that these values may represent coordinates or
  * positions in some context.
  */
	public String toString() {
		return "(" + x + " " + y + ")";
	}

 /**
  * modifies the component values of a `Vector2f` object by assigning new float values
  * to its `x` and `y` fields, and then returns the modified object reference.
  * 
  * @param x 2D coordinate value that updates the `x` component of the `Vector2f` object.
  * 
  * @param y 2nd component of the `Vector2f` object, which is being updated to match
  * the provided value.
  * 
  * @returns a reference to the same `Vector2f` instance, with the `x` and `y` components
  * updated to the provided values.
  * 
  * The `Vector2f` class returns an instance of itself after setting the `x` and `y`
  * components of the object to the input values `x` and `y`, respectively. Therefore,
  * the output is the same instance of `Vector2f` that was passed as an argument to
  * the function.
  */
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

 /**
  * sets the corresponding components of this vector to the corresponding components
  * of the `Vector2f` reference `r`.
  * 
  * @param r 2D vector to be set as the value of the `Vector2f` instance, and its
  * `getX()` and `getY()` methods are called to set the corresponding components of
  * the instance.
  * 
  * 	- `getX()` and `getY()`: These are methods that retrieve the x-coordinate and
  * y-coordinate values of `r`, respectively.
  * 
  * @returns a reference to the original vector with the x and y coordinates updated.
  * 
  * 	- The method sets the values of the `x` and `y` fields of the `Vector2f` object
  * to those of the parameter `r`.
  * 	- The return type of the method is the same as the original object, indicating
  * that the method does not create a new object but rather modifies the existing one.
  * 	- The method name `set` suggests that it is intended for setting or modifying the
  * properties of an object, rather than creating a new one.
  */
	public Vector2f set(Vector2f r) {
		set(r.getX(), r.getY());
		return this;
	}

    /**
     * converts a `Vector2f` instance into a `Vector3f` instance by adding a third component
     * representing the z-coordinate, which is initially set to zero.
     * 
     * @returns a new `Vector3f` instance with values `(x, y, 0)`.
     * 
     * 	- The Vector3f object represents a 3D vector with x, y, and z components.
     * 	- The x, y, and z components are set to the input values of the function.
     * 	- The resulting Vector3f object has a length of 0, indicating that it is a unit
     * vector.
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
  * sets the value of the `x` field of its container object to the argument passed as
  * a float value.
  * 
  * @param x float value that will be assigned to the `x` field of the class instance
  * upon calling the `setX()` method.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * returns the value of `y`.
  * 
  * @returns the value of the `y` field.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the field `y` to the argument passed as a float.
  * 
  * @param y float value that will be assigned to the `y` field of the object being
  * passed as an argument to the `setY()` method.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * compares two `Vector2f` objects based on their `x` and `y` components, returning
  * `true` if they are identical, and `false` otherwise.
  * 
  * @param r 2D point to which the current vector is compared for equality.
  * 
  * `x`: It is a double value representing the x-coordinate of the vector.
  * 
  * @returns a boolean value indicating whether the Vector2f object has the same x and
  * y coordinates as the provided Vector2f object.
  * 
  * 	- `x`: The value of `x` in the current object is compared with the corresponding
  * value of `r`. If they are equal, the function returns `true`.
  * 	- `y`: The value of `y` in the current object is compared with the corresponding
  * value of `r`. If they are equal, the function returns `true`.
  */
	public boolean equals(Vector2f r) {
		return x == r.getX() && y == r.getY();
	}
	
}
