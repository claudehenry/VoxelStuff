package com.ch.math;

public class Vector3f {
	
	private float x;
	private float y;
	private float z;

	public Vector3f() {
		this(0, 0, 0);
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

 /**
  * This function calculates the length of a 3D vector.
  * 
  * @returns This function takes no arguments and returns the square root of the sum
  * of the squared coordinates of the object. In other words: it returns the length
  * of the vector representing the object. The output is a float value that represents
  * the magnitude or distance of the object from the origin.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
 /**
  * This function calculates the length of a square that is defined by three coordinate
  * values (x; y; and z) by squaring each value and adding them together. It returns
  * the result as a floating-point number.
  * 
  * 
  * @returns { float } The function `squareLength()` returns the sum of the squares
  * of the three components of the object's position vector: `x*x + y*y + z*z`.
  * 
  * The output is a float value representing the length of the object's position vector.
  */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

 /**
  * This function calculates the maximum of three values x%, y%, and z%. It returns
  * the largest of these three values using theMath.max() method.
  * 
  * 
  * @returns { float } The function takes three floating-point numbers `x`, `y`, and
  * `z` as input and returns the maximum of those numbers.
  * 
  * The function first calculates the maximum of `x` and `y` using Math.max(), and
  * then the maximum of that result and `z` is returned.
  * 
  * Therefore: The output returned by this function will always be the largest of `x`,
  * `y`, and `z`.
  */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

 /**
  * This function calculates the dot product of a Vector3f object (represented by
  * 'this') with a another Vector3f object passed as a parameter (r). It returns a
  * float value representing the dot product.
  * 
  * 
  * @param { Vector3f } r - The `r` parameter is a `Vector3f` object that represents
  * a vector that the function will dot product with the current instance of `Vector3f`.
  * 
  * @returns { float } The output of the function `dot(Vector3f r)` is a floating-point
  * number representing the dot product of the current object (`this`) and the passed
  * `Vector3f` object `r`. Specifically:
  * 
  * The output is the sum of the products of the current object's `x`, `y`, and `z`
  * values with the corresponding components of `r`.
  */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

 /**
  * This function calculates the cross product of two Vector3f objects and returns a
  * new Vector3f object with the results.
  * 
  * 
  * @param { Vector3f } r - The `r` input parameter is a vector that is crossed with
  * the current vector to produce a new vector.
  * 
  * @returns { Vector3f } This function takes another `Vector3f` object as an argument
  * and returns a new `Vector3f` object representing the cross product of the current
  * vector with the input vector. The output vector has three elements:
  * 
  * 	- x_ (the z-component of the input vector multiplied by the y-component of the
  * current vector)
  * 	- y_ (the z-component of the current vector multiplied by the x-component of the
  * input vector)
  * 	- z_ (the x-component of the current vector multiplied by the y-component of the
  * input vector)
  * 
  * In other words: the output is a new vector that is perpendicular to both the current
  * vector and the input vector.
  */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

 /**
  * This function takes the current `Vector3f` object and returns a new vector with
  * its components normalized (i.e., scaled down to a unit length).
  * 
  * 
  * @returns { Vector3f } The function `normalized()` takes a `Vector3f` object as
  * input and returns a new `Vector3f` object representing the input vector normalized
  * to unit length (i.e., length equals 1). The return value is calculated by dividing
  * each component of the input vector by its corresponding length.
  */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

 /**
  * This function rotates a vector around an axis by angle. It calculates the sin and
  * cos of the angle and then applies the rotations to the vector using dot product
  * and cross product with the axis vector.
  * 
  * 
  * @param { Vector3f } axis - The `axis` input parameter represents the direction
  * about which the transformation is being applied. It is used to compute the rotational
  * component of the transformation.
  * 
  * @param { float } angle - The `angle` input parameter represents the angle of
  * rotation around the specified axis `axis`. It is a floating-point number that
  * determines how much the vector should be rotated.
  * 
  * @returns { Vector3f } This function takes a Vector3f object as input and rotates
  * it around an axis passed as parameters. The output is also a Vector3f object that
  * represents the rotated version of the original vector. The rotation is done using
  * trigonometric functions like sin and cos to calculate the new x and y components
  * of the vector based on the angle and axis passed as inputs.
  */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

 /**
  * This function rotates the input vector by a given rotation quaternion. It first
  * computes the conjugate of the rotation quaternion and then multiplies the input
  * vector by both the rotation quaternion and its conjugate to obtain the rotated vector.
  * 
  * 
  * @param { Quaternion } rotation - The `rotation` input parameter specifies the
  * rotation to be applied to the current vector3f instance.
  * 
  * @returns { Vector3f } The function takes a `Quaternion` `rotation` as input and
  * returns a `Vector3f` representing the result of rotating the original `Vector3f`
  * by the specified quaternion.
  */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

 /**
  * This function performs a linear interpolation between two vector3 positions: it
  * returns a new vector3 position that is the result of interpolating between the
  * current position and the dest position using the lerpFactor as the interpolator.
  * 
  * 
  * @param { Vector3f } dest - The `dest` input parameter specifies the target position
  * that the function will interpolate towards.
  * 
  * @param { float } lerpFactor - The `lerpFactor` input parameter controls the
  * interpolation between the current position and the destination position. It scales
  * the distance between the two points to determine how much of the distance should
  * be covered during the interpolation.
  * 
  * @returns { Vector3f } The function `lerp` takes a `Vector3f` object as input and
  * returns another `Vector3f` object representing the linear interpolation of the
  * current position and the destination position. The interpolation is based on the
  * specified `lerpFactor` value.
  * 
  * In other words: the function returns a new `Vector3f` that is a weighted sum of
  * the current position and the destination position where the weight is the `lerpFactor`.
  */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * This function adds the values of two vector3f objects and returns the result as a
  * new vector3f object.
  * 
  * 
  * @param { Vector3f } r - In the function you provided:
  * 
  * `r` is the input parameter that represents the vector to be added to the current
  * vector. It's passed by reference and its contents are used to compute the resulting
  * vector after the addition operation.
  * 
  * @returns { Vector3f } This function takes a `Vector3f` object `r` as an argument
  * and returns a new `Vector3f` object that represents the result of adding `this`
  * vector to `r`. The new vector has the same x-, y-, and z-components as `this`,
  * increased by the corresponding components of `r`. In other words:
  * 
  * Output: A new `Vector3f` object with values x + r.x(), y + r.y(), and z + r.z().
  */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
 /**
  * This function adds the components of the vector `r` to the components of the vector
  * `this`.
  * 
  * 
  * @param { Vector3f } r - The `r` input parameter is added to the current position
  * of the object. It increases the x(), y(), and z() values of the object by the
  * corresponding components of the `r` vector.
  */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

 /**
  * The function adds the value `r` to each component of the `Vector3f` object (i.e.,
  * `x`, `y`, and `z`) and returns a new `Vector3f` object with the modified values.
  * 
  * 
  * @param { float } r - The `r` input parameter adds the value passed to each component
  * of the `Vector3f` object.
  * 
  * @returns { Vector3f } The output returned by this function is a new `Vector3f`
  * object that represents the sum of the current vector and the given float value
  * `r`. The new vector has the same coordinates as the current vector but with each
  * coordinate increased by `r`. In other words:
  * 
  * 	- `x + r` is added to the existing `x` coefficient of the vector
  * 	- `y + r` is added to the existing `y` coefficient of the vector
  * 	- `z + r` is added to the existing `z` coefficient of the vector
  * 
  * The returned vector has the same memory location as the current vector and can be
  * used for further calculations.
  */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
 /**
  * This function adds the given vector `v` to this vector scaled by a factor of `scale`.
  * 
  * 
  * @param { Vector3f } v - The `v` input parameter is the vector to be added scaled
  * by the provided `scale` factor.
  * 
  * @param { float } scale - The `scale` parameter multiplies each element of the
  * vector `v` by the same factor before adding it to the current vector.
  * 
  * @returns { Vector3f } The output returned by this function is a new `Vector3f`
  * object that is the sum of the current instance and a scaled version of the `v`
  * parameter. The scaling is performed using the `mul()` method which returns a new
  * floating-point vector with the same coordinates as `v`, but with each element
  * multiplied by `scale`.
  */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
 /**
  * This function adds a vector to the current vector that is scaled by a given factor.
  * 
  * 
  * @param { Vector3f } v - In the provided function `addSelfScaledVector()`, the `v`
  * input parameter is a `Vector3f` object that contains the vector to be scaled and
  * added to the current vector.
  * 
  * @param { float } scale - The `scale` input parameter multiplies the `v` vector by
  * its value before adding it to the current vector. In other words ,it scales the
  * `v` vector by `scale` factor before adding it to the current vector.
  */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

 /**
  * This function calculates the difference between two Vector3f objects and returns
  * a new Vector3f object containing the differences.
  * 
  * 
  * @param { Vector3f } r - In this function `r` is a reference to another `Vector3f`
  * object and is used as a point of comparison for subtracting the corresponding
  * components from the current `Vector3f` object.
  * 
  * @returns { Vector3f } The output returned by this function is a new `Vector3f`
  * object that represents the difference between the current `Vector3f` object and
  * the passed-in `r` object. The x-, y-, and z-components of the result are respectively
  * the differences between the x-coordinates of the current vector and the passed-in
  * vector; the differences between the y-coordinates; and the differences between the
  * z-coordinates.
  */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * The function "sub" takes a single float parameter "r" and returns a new Vector3f
  * object that is the result of subtracting "r" from all components of the current
  * Vector3f object.
  * 
  * 
  * @param { float } r - The `r` input parameter subtracts the value of `r` from each
  * component of the `Vector3f` object.
  * 
  * @returns { Vector3f } The function takes a float parameter `r` and returns a new
  * `Vector3f` object representing the result of subtracting `r` from each component
  * of the current `Vector3f` object.
  * 
  * The output returned by this function is a new `Vector3f` object with values:
  * 
  * 	- `x - r`
  * 	- `y - r`
  * 	- `z - r`
  */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

 /**
  * The function mul takes a Vector3f parameter r and returns a new Vector3f with its
  * elements multiplied by the corresponding elements of r.
  * 
  * 
  * @param { Vector3f } r - The `r` parameter is a reference to another `Vector3f`
  * object and is used to scale the current `Vector3f` object by multiplying its
  * components with the corresponding components of `r`.
  * 
  * @returns { Vector3f } The output of this function is a new `Vector3f` object that
  * represents the result of multiplying the current `Vector3f` object by the input
  * `r` vector. The output object contains the component-wise product of the current
  * vector's components and the input vector's components.
  */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

 /**
  * This function scales the vector by a factor of `r`. It multiplies each component
  * of the vector (x*, y*, z*) by `r` and returns a new vector with the scaled components.
  * 
  * 
  * @param { float } r - The `r` input parameter is a floating-point number that scales
  * the entire vector by a factor of its value. In other words," multiply" each component
  * (x，y and z) of the Vector3f object by the passed floating-point value 'r'.
  * 
  * @returns { Vector3f } The output returned by this function is a new instance of
  * `Vector3f` that represents the current vector multiplied by the given scale factor
  * (specifically a floating-point number `r`). The newly created vector will have the
  * same coordinates as the original vector but scaled up or down by a factor of `r`.
  * In other words:
  * 
  * 	- `x` will be multiplied by `r`,
  * 	- `y` will be multiplied by `r`, and
  * 	- `z` will be multiplied by `r`.
  */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

 /**
  * This function performs element-wise division of the vector `this` by the passed
  * vector `r`, and returns the result as a new `Vector3f` object.
  * 
  * 
  * @param { Vector3f } r - The `r` input parameter is a vector to divide the current
  * vector by. It is used to calculate the divisions of the current vector's components
  * by the corresponding components of `r`.
  * 
  * @returns { Vector3f } The function `div` takes a `Vector3f` argument `r` and returns
  * a new `Vector3f` object with the components of the current vector divided by the
  * components of `r`. The output returned by this function is a vector with floats
  * for components representing the original vector divided by the components of `r`.
  */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

 /**
  * This function takes a float parameter `r` and returns a new Vector3f object that
  * represents the current vector divided by `r`. In other words., it scales the vector
  * down by the factor of `r`.
  * 
  * 
  * @param { float } r - The `r` input parameter is a factor by which each component
  * of the `Vector3f` object is divided.
  * 
  * @returns { Vector3f } The output returned by this function is a new instance of
  * `Vector3f` object with the coordinates divided by `r`. The method takes a float
  * parameter `r` and returns a vector with each coordinate (x/r), (y/r), and (z/r)
  * as its components.
  */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

 /**
  * This function returns a new `Vector3f` object with the absolute values of the
  * components of the current `Vector3f` object.
  * 
  * 
  * @returns { Vector3f } This function takes a `Vector3f` input and returns a new
  * `Vector3f` with the absolute values of all components. In other words:
  * 
  * Output: A new `Vector3f` with x=Math.abs(x), y=Math.abs(y), z=Math.abs(z).
  */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

 /**
  * This function implements the `toString()` method for a point class and returns a
  * string representation of the point with coordinates (x", "y", and "z".
  * 
  * 
  * @returns { String } The output returned by this function is the string "((null)
  * (null) (null))".
  */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

 /**
  * This function returns a new `Vector2f` object containing the current `x` and `y`
  * coordinates of the entity.
  * 
  * 
  * @returns { Vector2f } The output of the `getXY()` function is a `Vector2f` object
  * containing the current x and y coordinates of the undefined object.
  */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

 /**
  * This function returns a new Vector2f object containing the values of the instance's
  * y and z coordinates.
  * 
  * 
  * @returns { Vector2f } The function `getYZ()` returns a `Vector2f` object containing
  * the values of the `y` and `z` coordinates of the current object.
  */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

 /**
  * This function returns a new `Vector2f` object containing the values of the `z` and
  * `x` components of the object that contains it.
  * 
  * 
  * @returns { Vector2f } The output returned by this function is a `Vector2f` object
  * with the coordinates `z` and `x`.
  */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

 /**
  * This function returns a new Vector2f object with the x and y coordinates of the
  * instance reversed.
  * 
  * 
  * @returns { Vector2f } The output of this function is a `Vector2f` object containing
  * the values of `y` and `x`. Specifically:
  * 
  * 	- `y` is the value of the `y` component of the `Vector2f` object.
  * 	- `x` is the value of the `x` component of the `Vector2f` object.
  * 
  * In other words; The output is a vector containing both the x and y coordinates of
  * the point.
  */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

 /**
  * This function returns a new Vector2f object containing the values of the z and y
  * coordinates of the object.
  * 
  * 
  * @returns { Vector2f } The output of the `getZY()` function is a `Vector2f` object
  * with the values `z` and `y`.
  */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

 /**
  * This function returns a new `Vector2f` object containing the values of `x` and `z`
  * coordinates of the object.
  * 
  * 
  * @returns { Vector2f } The output of the function `getXZ()` is a new instance of
  * `Vector2f` containing the values `x` and `z`. In other words: the x-coordinate and
  * the z-coordinate of the object are combined to form a new vector with two components.
  */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

 /**
  * This function sets the elements of a Vector3f object to the specified float values
  * (x%, y%, and z%). It returns the same object for chaining purposes.
  * 
  * 
  * @param { float } x - In this function `set(float x`, the `x` input parameter sets
  * the value of the `x` component of the `Vector3f` object.
  * 
  * @param { float } y - The `y` input parameter sets the `y` component of the `Vector3f`
  * object.
  * 
  * @param { float } z - The `z` input parameter sets the value of the `z` component
  * of the `Vector3f` object.
  * 
  * @returns { Vector3f } The function `set` takes three float parameters and sets the
  * corresponding elements of a `Vector3f` object to those values. The output returned
  * by the function is the same `Vector3f` object that was passed as an argument. In
  * other words:
  * 
  * Output: `this`.
  */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

 /**
  * This function sets the values of this Vector3f object to the corresponding values
  * of the input Vector3f object.
  * 
  * 
  * @param { Vector3f } r - The `r` input parameter is a reference to another `Vector3f`
  * object and is used to set the components of the current vector to the components
  * of the passed vector.
  * 
  * @returns { Vector3f } The function `set` takes a `Vector3f` object as argument and
  * sets the elements of the current vector to the corresponding elements of the passed
  * vector. The output returned by the function is the modified `this` vector itself.
  * In other words. the function returns a reference to the current vector after
  * modifying its elements.
  */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

 /**
  * This function "getX" returns the value of the field "x" which is a float and
  * undefined at this point.
  * 
  * 
  * @returns { float } The output returned by this function is `undefined`. The function
  * does not provide a valid value for the property `x`, so it returns `undefined` instead.
  */
	public float getX() {
		return x;
	}

 /**
  * This function sets the value of the `x` field of the object to the passed `float`
  * argument `x`.
  * 
  * 
  * @param { float } x - The `x` input parameter sets the value of the member variable
  * `x` within the object.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * This function "getY()" returns the value of the "y" field or variable.
  * 
  * 
  * @returns { float } The output returned by this function is `NaN` (Not a Number)
  * because the field `y` is not initialized or defined.
  */
	public float getY() {
		return y;
	}

 /**
  * The `setY()` function sets the value of the object's `y` field to the given `float`
  * parameter.
  * 
  * 
  * @param { float } y - The `y` input parameter is assigned to the `y` field of the
  * object.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * This function returns the value of the field "z" which is a floating-point number.
  * 
  * 
  * @returns { float } The output returned by this function is `undefined`.
  */
	public float getZ() {
		return z;
	}

 /**
  * This function sets the value of the `z` field of the object to the specified `float`
  * value `z`.
  * 
  * 
  * @param { float } z - The `z` input parameter sets the value of the field `z` within
  * the object.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * This function implements the `equals` method for the `Vector3f` class. It compares
  * the values of the `x`, `y`, and `z` fields of the current vector with the corresponding
  * fields of the provided `r` vector. If all fields are equal (i.e., if all the `==`
  * checks pass), then the function returns `true`. Otherwise (i.e., if any of the
  * `==` checks fail), the function returns `false`.
  * 
  * 
  * @param { Vector3f } r - In the given function `equals(Vector3f r)`, `r` is a
  * reference to another `Vector3f` object that is being compared for equality with
  * the current instance.
  * 
  * @returns { boolean } The output returned by this function is `true` if the `Vector3f`
  * object has the same x/y/z values as the `r` parameter; otherwise it returns `false`.
  */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}


