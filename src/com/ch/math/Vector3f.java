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
	 * This function calculates the length of a 3D vector by taking the square root of
	 * the sum of the squares of its components (x*, y*, and z*).
	 * 
	 * @returns The output returned by this function is a float value representing the
	 * length of the vector. It is calculated as the square root of the sum of the
	 * components (x^2 + y^2 + z^2).
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * The given function calculates the length of a vector represented by three doubles
	 * `x`, `y`, and `z`. It does this by multiplying each component by itself and then
	 * summing the results. The result is returned as a float.
	 * 
	 * @returns The output returned by the function `squareLength()` is the length of the
	 * vector expressed as a float value. The function calculates the square of the
	 * magnitude of the vector (i.e., the sum of the squares of the x-, y-, and z-coordinates)
	 * and returns it as a float.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * The given function takes three `float` parameters `x`, `y`, and `z`, and returns
	 * the maximum of these values using the `Math.max()` method.
	 * 
	 * @returns The function takes three `float` arguments `x`, `y`, and `z`. It returns
	 * the largest of these values using the `Math.max()` method. Therefore the output
	 * returned by this function is the largest of `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * This function computes the dot product of a Vector3f object (i.e., the object's
	 * x-, y-, and z-coordinates) with a given Vector3f r. It returns the sum of the
	 * products of the object's coordinates and the corresponding coordinates of r.
	 * 
	 * @param r In this function `r` is a `Vector3f` object that is being passed as an
	 * argument to the `dot()` method. It represents a second vector that is used to
	 * calculate the dot product with the vector represented by `this`.
	 * 
	 * @returns The function `dot` takes a `Vector3f` argument `r` and returns a float
	 * value representing the dot product of the current `Vector3f` instance and `r`. The
	 * return value is a sum of the products of the corresponding components of the two
	 * vectors.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * This function computes the cross product of two vectors and returns the result as
	 * a new vector.
	 * 
	 * @param r The `r` input parameter is a Vector3f object that represents another
	 * vector that is used to calculate the cross product.
	 * 
	 * @returns This function takes another `Vector3f` object as input and returns a new
	 * `Vector3f` object representing the cross product of the two inputs. The output
	 * vector has three elements that are the dot products of the corresponding elements
	 * of the input vectors. In other words:
	 * 
	 * 	- `x_ = y * r.getZ() - z * r.getY()` computes the product of the x component of
	 * the first vector and the z component of the second vector minus the product of the
	 * y component of the first vector and the z component of the second vector. This
	 * value becomes the first element of the output vector.
	 * 	- `y_ = z * r.getX() - x * r.getZ()` computes the product of the y component of
	 * the first vector and the x component of the second vector minus the product of the
	 * z component of the first vector and the x component of the second vector. This
	 * value becomes the second element of the output vector.
	 * 	- `z_ = x * r.getY() - y * r.getX()` computes the product of the z component of
	 * the first vector and the y component of the second vector minus the product of the
	 * x component of the first vector and the y component of the second vector. This
	 * value becomes the third element of the output vector.
	 * 
	 * In a nutshell: this function takes two 3D vectors as input and produces a new 3D
	 * vector representing their cross product.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * This function takes a `Vector3f` object as input and returns a normalized version
	 * of it. The normalization process involves dividing each element of the vector by
	 * its own length.
	 * 
	 * @returns This function takes a `Vector3f` object as input and returns a new
	 * `Vector3f` object that represents the normalized version of the original vector.
	 * The new vector has the same direction as the original vector but with its components
	 * scaled down to a length of 1.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * This function rotates a `Vector3f` by a specified angle around a specified axis.
	 * 
	 * @param axis The `axis` parameter is used to specify the axis about which the
	 * rotation will take place.
	 * 
	 * @param angle The `angle` input parameter represents the angle of rotation around
	 * the specified axis.
	 * 
	 * @returns This function takes a `Vector3f` object as input and returns a rotated
	 * version of the original vector based on a given axis and angle of rotation. The
	 * output is a new `Vector3f` object that represents the rotated vector.
	 * 
	 * The function first calculates the sin and cos of the angle of rotation using the
	 * `Math.sin` and `Math.cos` methods. It then uses these values to create a series
	 * of vector multiplications and additions to generate the final rotated vector. The
	 * resulting vector is returned as the output of the function.
	 * 
	 * In more detail:
	 * 
	 * 1/ The function cross-products the input vector with the sin and cos of the angle
	 * of rotation to create two perpendicular vectors.
	 * 2/ It then multiplies each of these perpendicular vectors by the corresponding
	 * component of the input vector (i.e., the dot product of the input vector and the
	 * cos/sin of the angle).
	 * 3/ The resulting vectors are added together to form the final rotated vector.
	 * 
	 * The output of the function is a rotated version of the original input vector that
	 * is calculated based on the specified axis and angle of rotation.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * This function rotates a Vector3f by applying a given Quaternion rotation. It
	 * computes the conjugate of the input Quaternion and then multiplies it with the
	 * input Vector3f and itself to get the rotated vector.
	 * 
	 * @param rotation The `rotation` input parameter represents a quaternion that specifies
	 * the rotation to be applied to the vector. It is used to concatenate the rotation
	 * with the existing orientation of the vector.
	 * 
	 * @returns The output returned by this function is a new `Vector3f` object that
	 * represents the rotated version of the current `Vector3f` object using the specified
	 * `Quaternion` rotation.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * This function implements a linear interpolation between two Vector3f objects. It
	 * returns a new Vector3f object that is a weighted average of the current object and
	 * the destination object.
	 * 
	 * @param dest The `dest` parameter is the target location that the method should
	 * interpolate towards.
	 * 
	 * @param lerpFactor The `lerpFactor` parameter controls the interpolation between
	 * the current position and the destination position. It takes a value between 0 and
	 * 1 and maps to a value between the minimum and maximum values of the destination vector.
	 * 
	 * @returns The function takes a `Vector3f` object as input and returns a new `Vector3f`
	 * object that is the result of a linear interpolation between the current object and
	 * the `dest` parameter. The interpolation factor is specified by the `lerpFactor` parameter.
	 * 
	 * The output returned by this function is a new `Vector3f` object that represents
	 * the point on the line segment defined by the current point and the destination
	 * point. The output point is obtained by interpolating between the current point and
	 * the destination point using the `lerpFactor`.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * This function adds the components of two vector3fs (x+r.x etc.) and returns a new
	 * vector3f with the result.
	 * 
	 * @param r In this function implementation of `Vector3f`, `r` is the parameter that
	 * represents the vector to be added to the current vector.
	 * 
	 * @returns The output returned by this function is a new `Vector3f` object that
	 * represents the sum of the current vector and the specified `r` vector. The returned
	 * vector has the same coordinates as the current vector plus the corresponding
	 * coordinates of the `r` vector. In other words:
	 * 
	 * `(x + r.getX(), y + r.getY(), z + r.getZ()`
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * This function adds the values of the `Vector3f` object's `x`, `y`, and `z` fields
	 * to the corresponding components of the `r` parameter's `Vector3f` object.
	 * 
	 * @param r The `r` parameter is a `Vector3f` object that contains the amount to add
	 * to each component of the current vector.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * This function takes a single float parameter 'r' and returns a new Vector3f object
	 * that represents the sum of the current vector and 'r' applied to each component
	 * (x+r; y+r; z+r).
	 * 
	 * @param r The `r` input parameter adds a fixed value to each component of the vector
	 * (x++, y++, and z++).
	 * 
	 * @returns The output returned by this function is a new `Vector3f` object with all
	 * components (x/y/z) increased by the value `r`. In other words:
	 * 
	 * 	- `x + r` becomes the new x-coordinate
	 * 	- `y + r` becomes the new y-coordinate
	 * 	- `z + r` becomes the new z-coordinate
	 * 
	 * So the function returns a new vector that is `r` units away from the original vector.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * This function adds a scaled version of the passed `Vector3f` to this instance.
	 * 
	 * @param v The `v` parameter is a vector that is added to the current vector after
	 * being scaled by the provided scale factor.
	 * 
	 * @param scale The `scale` input parameter multiplies the `v` vector by the specified
	 * value before adding it to the current vector. In other words., it scales the input
	 * vector before combining it with the current vector.
	 * 
	 * @returns The output of the function `addScaledVector` is a new `Vector3f` object
	 * that represents the result of adding the input `v` to the current vector scaled
	 * by the factor `scale`.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * This function adds a scaled version of the given `Vector3f` object to the current
	 * `Vector3f` instance. The scale factor is passed as a float parameter `scale`.
	 * 
	 * @param v The `v` parameter is the vector that should be scaled and added to the
	 * current position of the object.
	 * 
	 * @param scale The `scale` input parameter scales the input vector by a factor of
	 * `scale`, before adding it to the current vector.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * This function subtracts the values of the x , y and z components of the current
	 * vector from the corresponding components of a given vector "r" and returns a new
	 * vector with the result.
	 * 
	 * @param r The `r` parameter is a reference to another `Vector3f` object and is used
	 * as a source of subtraction for the elements of the current vector.
	 * 
	 * @returns The function `sub(Vector3f r)` takes a `Vector3f` argument `r` and returns
	 * a new `Vector3f` object representing the subtraction of `this` vector from `r`.
	 * The output returned by this function is a new vector with the same dimensionality
	 * as the input vectors and components that are equal to the difference between the
	 * corresponding components of the input vectors. In other words:
	 * 
	 * 	- `x` of the output vector = `x` - `r.getX()`
	 * 	- `y` of the output vector = `y` - `r.getY()`
	 * 	- `z` of the output vector = `z` - `r.getZ()`
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * This function calculates and returns a new `Vector3f` instance with all components
	 * reduced by the passed value `r`.
	 * 
	 * @param r The `r` input parameter represents a scalar value that is subtracted from
	 * each component of the `Vector3f` object.
	 * 
	 * @returns The output returned by this function is a new `Vector3f` object that
	 * represents the original vector minus the value `r`. The vector components are each
	 * subtracted by `r`, resulting In a new vector with values `x - r`, `y - r`, and `z
	 * - r`.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * This function performs a component-wise multiplication of the vector `this` with
	 * another vector `r`, returning the resulting vector as a new `Vector3f` object.
	 * 
	 * @param r The `r` parameter is a `Vector3f` object that represents a point with
	 * three numbers (x/y/z). This function performs a dot product multiplication between
	 * the current `Vector3f` object and the input `r` object.
	 * 
	 * @returns The output returned by this function is a new `Vector3f` object representing
	 * the result of multiplying the current `Vector3f` instance by a given `Vector3f`
	 * parameter `r`. The return value is calculated as the dot product of the current
	 * vector's components with the corresponding components of `r`, and the result is
	 * stored into a new vector object.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * This function performs a scalar multiplication of the current vector by a given
	 * float value `r`, and returns the result as a new `Vector3f` object.
	 * 
	 * @param r The `r` input parameter is a scalar multiplier that is used to scale all
	 * the components of the `Vector3f` object by the same amount.
	 * 
	 * @returns The function `mul` takes a single floating-point parameter `r` and returns
	 * a new `Vector3f` object with the original vector's components multiplied by `r`.
	 * 
	 * The output of the function is a new `Vector3f` object with the following values:
	 * 
	 * 	- `x * r` (the x component of the original vector multiplied by `r`)
	 * 	- `y * r` (the y component of the original vector multiplied by `r`)
	 * 	- `z * r` (the z component of the original vector multiplied by `r`)
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * This function takes a `Vector3f` object `r` and returns a new `Vector3f` object
	 * representing the original vector divided by `r`.
	 * 
	 * @param r The `r` input parameter is a vector that represents the divisor for the
	 * multiplication operation.
	 * 
	 * @returns The output of the given function `div` is a `Vector3f` object representing
	 * the divided vectors. It takes another `Vector3f` object `r` as input and returns
	 * a new `Vector3f` object with the divided coordinates:
	 * 
	 * 	- x / r.getX()
	 * 	- y / r.getY()
	 * 	- z / r.getZ()
	 * 
	 * In other words ,the function returns a vector with the same direction as the input
	 * vector `r`, but with the magnitudes of the corresponding components divided by the
	 * magnitude of `r`.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * The given function `div` takes a single parameter `r` and returns a new `Vector3f`
	 * object representing the result of dividing all three components of the current
	 * vector by `r`.
	 * 
	 * @param r The `r` input parameter is a factor by which each component of the
	 * `Vector3f` object is divided.
	 * 
	 * @returns The function `div(r)` returns a new `Vector3f` object that represents the
	 * vector divided by a float value `r`. The elements of the returned vector are the
	 * original x/r; y/r; and z/r. In other words:
	 * 
	 * output = (x / r), (y / r), and (z / r).
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * The function takes the current `Vector3f` object as input and returns a new
	 * `Vector3f` object with the absolute values of the XYZ components.
	 * 
	 * @returns The function takes a `Vector3f` object as input and returns a new `Vector3f`
	 * object with the absolute values of the x%, y%, and z% components.
	 * 
	 * The output is a new vector with all positive values. For example:
	 * 
	 * Input: Vector3f(1,-2., 3.)
	 * Output: Vector3f(1., 2., 3.)
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * This function defines a Object implementation of the toString() method that returns
	 * a string representation of the object. It takes the values of the instance variables
	 * x，y and z and concatenates them into a single string enclosed by parentheses.
	 * 
	 * @returns The output returned by this function is "()" (an empty string). This is
	 * because the string concatenation operator (+) operates on void variables
	 * (x=y=z=undefined), resulting an empty string when toString() is called.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * This function returns a new `Vector2f` object containing the x and y coordinates
	 * of the object.
	 * 
	 * @returns The function `getXY()` returns a new `Vector2f` object with x and y
	 * coordinates equal to the current x and y coordinates of the object. In other words:
	 * the function returns the current position of the object as a vector.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * The function `getYZ()` returns a new vector with the values of the object's `y`
	 * and `z` components.
	 * 
	 * @returns The function `getYZ()` returns a `Vector2f` object with two elements: `y`
	 * and `z`.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * This function returns a new `Vector2f` object with the `x` and `z` components of
	 * the current object.
	 * 
	 * @returns The function `getZX()` returns a new `Vector2f` object with the values
	 * `z` and `x` as its components. In other words:
	 * 
	 * 	- The `x` component is taken from the variable `x`.
	 * 	- The `y` component is taken from the variable `z`.
	 * 
	 * So the output of this function is a vector with the x-value of `x` and the y-value
	 * of `z`.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * This function returns a new `Vector2f` object containing the values of `y` and `x`.
	 * 
	 * @returns The function `getYX()` returns a new instance of `Vector2f` containing
	 * the values of `y` and `x`. In other words:
	 * 
	 * 	- `getYX().y` = `y`
	 * 	- `getYX().x` = `x`
	 * 
	 * So the output returned by this function is a vector with the coordinates `y` and
	 * `x`.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * This function creates a new `Vector2f` object and returns it with the `x` and `y`
	 * components set to the values of the `z` and `y` variables respectively.
	 * 
	 * @returns The output of this function is a new `Vector2f` object containing the
	 * values of the `z` and `y` components of the current object. In other words:
	 * 
	 * 	- `x` and `y` values remain unchanged.
	 * 	- `z` value is used to create a new `Vector2f` object with the same `y` value as
	 * the current object.
	 * 
	 * The function returns a newly created `Vector2f` object with the `z` and `y`
	 * components set to the current object's values.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * The function "getXZ()" returns a new vector with the x and z coordinates of the
	 * current object.
	 * 
	 * @returns The output returned by this function is a `Vector2f` object with `x` and
	 * `z` coordinates only (i.e., without `y` coordinate). In other words:
	 * 
	 * `getXZ()` function returns a vector pointing from the origin (0/0) to the point
	 * (x/z) on the XZ-plane.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * This function sets the values of all three components of the `Vector3f` object to
	 * the passed floating-point values `x`, `y`, and `z`.
	 * 
	 * @param x The `x` input parameter sets the `x` component of the `Vector3f` object.
	 * 
	 * @param y In this function implementation of `set`, the `y` parameter does not have
	 * any effect because it is never used anywhere within the implementation. It is
	 * essentially a dead code.
	 * 
	 * @param z In this function `set(float x、float y、float z)`, the `z` parameter sets
	 * the value of the `z` component of the `Vector3f` object being called.
	 * 
	 * @returns The function `set(float x、float y、float z)` takes three arguments and
	 * assigns them to the members `x`, `y`, and `z` of an instance of the class `Vector3f`.
	 * The return value of the function is the instance itself (`this`).
	 * 
	 * In other words， the output returned by this function is the modified `Vector3f`
	 * instance that has been assigned the values passed as arguments.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * This function sets the components of the current vector to the corresponding
	 * components of the input vector `r`.
	 * 
	 * @param r In the given function `set`, the `r` input parameter is a `Vector3f`
	 * object that contains the new values for the x/y/z coordinates of the instance. The
	 * function uses these values to set the corresponding components of the current instance.
	 * 
	 * @returns The function `set` takes a `Vector3f` object as parameter and sets the
	 * elements of this vector to the corresponding elements of the passed `Vector3f`
	 * object. The function returns `this` object itself.
	 * 
	 * So the output returned by this function is the same object that was passed as
	 * parameter to the `set` method. In other words: the function does not return a new
	 * `Vector3f` object but instead updates the existing object with the contents of the
	 * passed object and returns the updated object.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * This function returns the value of the "x" field of the object.
	 * 
	 * @returns The output returned by this function is `undefined`. The reason for this
	 * is that the `x` field is not initialized before it is being returned as the result
	 * of the `getX()` method. In other words," undefined" is returned because there is
	 * no value associated with the field `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * The `setX()` function sets the value of the variable `x` within the object to the
	 * value passed as a parameter `x`.
	 * 
	 * @param x In the function you provided `setX(float x)`, the `x` input parameter is
	 * used to set the value of the member variable `x` of the object that this method
	 * belongs to.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * This function returns the value of the "y" field of the object.
	 * 
	 * @returns The output returned by this function is `undefined`.
	 */
	public float getY() {
		return y;
	}

	/**
	 * This function sets the value of the variable `y` within the class to the passed
	 * `float` argument `y`.
	 * 
	 * @param y The `y` input parameter sets the value of the instance variable `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * This function returns the value of the "z" field of the object.
	 * 
	 * @returns The function `getZ()` returns the value of the field `z`, which is currently
	 * `undefined`. Therefore the output returned by this function is `NaN` (Not a Number).
	 */
	public float getZ() {
		return z;
	}

	/**
	 * This function sets the value of the `z` field of the object to the given `float`
	 * argument `z`.
	 * 
	 * @param z The `z` input parameter sets the value of the member variable `z`.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * This function implements the `equals()` method for the `Vector3f` class and compares
	 * the values of its components (`x`, `y`, and `z`) with the corresponding components
	 * of the passed `r` vector. It returns `true` if all components are equal and `false`
	 * otherwise.
	 * 
	 * @param r In the function `equals(Vector3f r)`, `r` is a reference to another
	 * `Vector3f` object that is compared for equality with the current object.
	 * 
	 * @returns The function `equals()` compares the values of three floating-point numbers
	 * (`x`, `y`, and `z`) of the current `Vector3f` object with the corresponding values
	 * of the `r` object. If all the values are equal (i.e., `x` is equal to `r.getX()`,
	 * `y` is equal to `r.getY()`, and `z` is equal to `r.getZ()`), then the function
	 * returns `true`. Otherwise (if any of the values are different), the function returns
	 * `false`.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
