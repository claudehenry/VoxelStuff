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
  * @returns The function `length()` returns the square root of the sum of the squared
  * coordinates of the object. In other words:
  * 
  * output = sqrt(x^2 + y^2 + z^2)
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
 /**
  * The function `squareLength()` returns the length of a vector (a point with three
  * components: x(), y(), and z()) calculated by summing the squares of each component.
  * 
  * @returns The output returned by this function is a `float` value that represents
  * the length of the square formed by the vector's components (i.e., the magnitude
  * of the vector). The function simply calculates the sum of the squared components
  * (`x*x + y*y + z*z`) and returns the result as a `float`.
  */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

 /**
  * This function takes no arguments and returns the maximum of three values: `x`,
  * `y`, and `z`.
  * 
  * @returns This function takes no arguments and returns a `float` value representing
  * the maximum of three variables `x`, `y`, and `z`. The function calls `Math.max()`
  * twice to determine the maximum of each variable separately and then combines them
  * using the ` Math.max()` method again to get the final maximum value.
  * 
  * Therefore ,the output of this function is the maximum of all three variables which
  * is unclear without knowledge of the values of x ,y ,and z .
  */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

 /**
  * This function calculates the dot product of a Vector3f object (r) with the current
  * vector.
  * 
  * @param r In the given function `dot(Vector3f r)`, the `r` input parameter represents
  * a second vector that is used to calculate the dot product with the current vector.
  * 
  * @returns This function takes a `Vector3f` object called `r` as input and returns
  * a `float` value representing the dot product of the current vector with `r`. The
  * output is a scalar value computed as the sum of the products of the components of
  * the two vectors.
  */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

 /**
  * This function computes the cross product of two vector3fs and returns the result
  * as a new vector3f.
  * 
  * @param r The `r` parameter is a second vector that is used to compute the cross
  * product of two vectors.
  * 
  * @returns The function takes a `Vector3f` object `r` as input and returns a new
  * `Vector3f` object containing the cross product of `this` vector and `r`. The
  * returned vector has x-coordinate `y * r.getZ() - z * r.getY()`, y-coordinate `-z
  * * r.getX() + x * r.getY()`, and z-coordinate `x * r.getY() - y * r.getX()`. In
  * other words: the cross product of two vectors is a vector perpendicular to both
  * input vectors.
  */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

 /**
  * This function returns a new vector that is the original vector normalized to a
  * length of 1.
  * 
  * @returns The output of the `normalized()` function is a new vector with the same
  * direction as the input vector but with all elements scaled down to a maximum value
  * of 1.0 (i.e., normalized).
  */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

 /**
  * This function rotates a Vector3f by a specified angle around a given axis.
  * 
  * @param axis The `axis` input parameter represents the axis about which the vector
  * should be rotated. It is used to compute the new position of the vector after rotation.
  * 
  * @param angle The `angle` input parameter represents the angle of rotation around
  * the specified axis (defined by `axis`). It specifies how much to rotate the vector
  * by.
  * 
  * @returns The function takes two parameters `axis` and `angle`, and returns a new
  * vector `Vector3f` representing the result of rotating the original vector around
  * the given axis by the given angle.
  */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

 /**
  * This function takes a Quaternion representation of a rotation as input and returns
  * a Vector3f representing the rotated point or object. It does so by first calculating
  * the conjugate of the input quaternion and then applying the rotation to the current
  * vector using multiplication. Finally it creates a new vector with the rotated coordinates.
  * 
  * @param rotation The `rotation` input parameter is a quaternion that represents the
  * rotation to be applied to the vector. It is used to modify the vector's orientation
  * by multiplying it with the rotation quaternion and returning the result as a new
  * vector.
  * 
  * @returns This function takes a Quaternion rotation as input and returns a Vector3f
  * representing the result of rotating the input vector by that quaternion. The output
  * is a new Vector3f with the x-, y-, and z-components of the result of multiplying
  * the input vector by the quaternion and its conjugate.
  */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

 /**
  * This function implements a linear interpolation (lerp) between two Vector3f objects.
  * It returns a new Vector3f object that is a weighted sum of the current object and
  * the dest object with a given lerp factor.
  * 
  * @param dest The `dest` parameter is the destination vector that the function will
  * interpolate from the current position to reach the target position defined by `lerpFactor`.
  * 
  * @param lerpFactor The `lerpFactor` input parameter controls the rate at which the
  * vector is interpolated towards the `dest` vector. It ranges from 0 (no interpolation)
  * to 1 (complete interpolation).
  * 
  * @returns The function `lerp` takes a `Vector3f` `dest` and a `float` `lerpFactor`,
  * and returns a new `Vector3f` that is the linear interpolation between the current
  * object and `dest`. The output returned by this function is a new `Vector3f` that
  * represents the interpolated position.
  */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * This function adds the elements of two Vector3f objects and returns a new Vector3f
  * object with the resulting sum.
  * 
  * @param r In this function implementation of Vector3f class method add(Vector3f r),
  *   'r' is a parameter that holds a another Vector3f object representing an additional
  * translation or offset to be added to this vector.
  * 
  * @returns This function takes a `Vector3f` object `r` as an argument and returns a
  * new `Vector3f` object that represents the sum of the current vector and `r`. The
  * output returned by the function is a new vector with elements `x + r.getX()`, `y
  * + r.getY()`, and `z + r.getZ()`.
  */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
 /**
  * This function adds the components of the given `Vector3f` `r` to the current
  * instance of `Vector3f`.
  * 
  * @param r The `r` input parameter is adding its components (x + r.x , y + r.y , z
  * + r.z) to the corresponding components of this vector .
  */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

 /**
  * This function takes a float parameter 'r' and returns a new Vector3f object where
  * the x/y/z coordinates have been incremented by 'r'.
  * 
  * @param r The `r` input parameter adds the value to each component of the `Vector3f`.
  * 
  * @returns The function `add` takes a single float parameter `r` and returns a new
  * `Vector3f` object that represents the original vector plus `r` along each dimension.
  * The returned vector has the same type as the input vector.
  */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
 /**
  * This function adds the given `v` vector to the current vector scaled by a factor
  * of `scale`.
  * 
  * @param v In the provided function `addScaledVector`, the `v` input parameter
  * represents a `Vector3f` object that will be added to the current vector scaled by
  * the provided `float` value `scale`.
  * 
  * @param scale The `scale` parameter multiplies the input `Vector3f` `v` by a scalar
  * value before adding it to the current vector. Effectively scaling the input vector
  * by the specified factor.
  * 
  * @returns The output of this function is a new `Vector3f` object that is the result
  * of adding the input vector `v` to the current vector `this`, scaled by the specified
  * factor `scale`. In other words:
  * 
  * `return this.add(v.mul(scale))`
  * 
  * The `add()` method adds two vectors element-wise (component-wise) multiplication
  * is done on the corresponding elements of the input vectors before adding them.
  */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
 /**
  * This function adds a vector to the current position of the object after scaling
  * the input vector by a given factor.
  * 
  * @param v The `v` input parameter is the vector that should be scaled and added to
  * the current vector.
  * 
  * @param scale The `scale` parameter multiplies the input `Vector3f` by a scalar
  * value before adding it to the current vector. In other words., it scales the input
  * vector by the specified factor before combining it with the current vector.
  */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

 /**
  * This function subtracts the values of all three components of the `r` vector from
  * the current instance of `Vector3f`, and returns the resulting new vector.
  * 
  * @param r The `r` parameter is a reference to another `Vector3f` object and it is
  * used to subtract its components from the current `Vector3f` object.
  * 
  * @returns This function takes another `Vector3f` object as a parameter and returns
  * a new `Vector3f` object representing the difference between this object and the
  * passed object. In other words;
  * 
  * Output: A new vector with coordinates x - r.getX(), y - r.getY() , and z - r.getZ().
  */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * The function takes a `float` parameter `r` and returns a new `Vector3f` object
  * that represents the subtraction of `r` from each component of the original `Vector3f`
  * object.
  * 
  * @param r The `r` input parameter is a scalar value that represents the amount by
  * which each component of the vector should be subtracted. It is used to create a
  * new vector that is equivalent to the original vector minus the specified amount.
  * 
  * @returns This function takes a single float parameter `r` and returns a new
  * `Vector3f` object with all components reduced by `r`. In other words:
  * 
  * 	- `x` - `r`
  * 	- `y` - `r`
  * 	- `z` - `r`
  * 
  * So the output is a new vector that is `r` units away from the original vector.
  */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

 /**
  * The given function is a member function of a Vector3f class that performs the
  * multiplication of two vector3f objects. It returns a new vector3f object with the
  * same components as the original vector scaled by the magnitude of the passed-in
  * vector. In other words , it returns a new vector that represents the original
  * vector transformed by a scalar multiplied by the given vector .
  * 
  * @param r The `r` input parameter is a vector that is used to scale the current
  * vector (the result of multiplication is a vector that is proportional to the current
  * vector and the scalar value of `r`).
  * 
  * @returns The function takes a `Vector3f` parameter `r` and returns a new `Vector3f`
  * object that contains the result of multiplying each element of the current vector
  * with the corresponding element of `r`. The output is a new vector that represents
  * the original vector scaled by a factor specified by `r`.
  */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

 /**
  * This function multiplies the components of a `Vector3f` instance by a given float
  * value `r`, returning a new `Vector3f` instance with the result of the multiplication.
  * 
  * @param r The `r` input parameter represents a scaling factor and is multiplied
  * element-wise with each component of the current vector.
  * 
  * @returns The function `mul(float r)` takes a single parameter `r` and returns a
  * new `Vector3f` object with all components multiplied by `r`. In other words:
  * 
  * 	- The `x` component is multiplied by `r`, producing a new `x` component.
  * 	- The `y` component is multiplied by `r`, producing a new `y` component.
  * 	- The `z` component is multiplied by `r`, producing a new `z` component.
  * 
  * The output of this function is therefore a new `Vector3f` object with all components
  * scaled up (multiplied) by a factor of `r`.
  */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

 /**
  * This function calculates the division of the current vector (this) by another
  * vector (r), and returns the result as a new Vector3f object.
  * 
  * @param r The `r` parameter is a divisor used to perform element-wise division of
  * the current vector with the given `r` vector.
  * 
  * @returns The output returned by this function is a `Vector3f` object representing
  * the vector `x / r.getX(), y / r.getY(), z / r.getZ()`, where `r` is another
  * `Vector3f` object passed as an argument. In other words: the function takes a
  * vector `r` and returns a new vector that represents the original vector divided
  * by `r`.
  */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

 /**
  * This function divides all elements of the vector by a given floating-point value
  * `r`.
  * 
  * @param r The `r` input parameter is a scalar divide factor that scales the components
  * of the `Vector3f` instance by dividing each component by the passed value.
  * 
  * @returns The function takes a single parameter `r` and returns a `Vector3f` object
  * containing the current vector elements divided by `r`. In other words:
  * 
  * Output: A new `Vector3f` object with x/, y/, and z/ r respectively.
  */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

 /**
  * The function takes the current `Vector3f` object as input and returns a new
  * `Vector3f` object containing only the absolute values of the x-, y-, and z-components
  * of the original vector.
  * 
  * @returns This function takes the current `Vector3f` object as input and returns a
  * new `Vector3f` object with all components changed to their absolute values using
  * `Math.abs()`. In other words; the x-component will be the positive value of the
  * current x-value if it's negative and vice versa and the same goes for y and z. The
  * output returned by this function will contain three components that are all
  * non-negative and will be identical to the current `Vector3f` object with only its
  * magnitudes changed.
  */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

 /**
  * This function defines a `toString()` method for an object of an unknown type
  * (represented by `undefined`) that returns a string representation of the object.
  * The string consists of the values of the object's fields `x`, `y`, and `z` separated
  * by spaces.
  * 
  * @returns The output returned by this function is:
  * 
  * "(undefined undefined undefined)"
  */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

 /**
  * This function returns a new `Vector2f` object containing the x and y coordinates
  * of the object.
  * 
  * @returns The function `getXY()` returns a newly created `Vector2f` object containing
  * the x and y coordinates of the object. The object has the values `x` and `y` as
  * its elements.
  */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

 /**
  * This function returns a new Vector2f object containing the values of the `y` and
  * `z` coordinates of the current instance.
  * 
  * @returns The function `getYZ()` returns a new `Vector2f` object containing the
  * values of the `y` and `z` components of the current object. The output is a vector
  * with two components: `y` and `z`.
  */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

 /**
  * This function returns a new `Vector2f` object with the values of `z` and `x`.
  * 
  * @returns The output returned by this function is a `Vector2f` object with the `x`
  * and `z` components swapped. Specifically:
  * 
  * 	- The `x` component of the output vector is set to the `z` component of the input
  * vector.
  * 	- The `z` component of the output vector is set to the `x` component of the input
  * vector.
  * 
  * In other words. the function "swaps" the `x` and `z` components of the input vector
  * to produce the output vector.
  */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

 /**
  * This function returns a new `Vector2f` object with the x-coordinate set to `x` and
  * the y-coordinate set to `y`.
  * 
  * @returns The output of the function `getYX()` is a new `Vector2f` object containing
  * the values `y` and `x`.
  */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

 /**
  * This function returns a new Vector2f object with the x and y coordinates swapped
  * from the current instance.
  * 
  * @returns The function `getZY()` returns a `Vector2f` object containing the values
  * `z` and `y`, respectively. In other words:
  * 
  * 	- The x-coordinate of the returned vector is `z`.
  * 	- The y-coordinate of the returned vector is `y`.
  */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

 /**
  * This function returns a new `Vector2f` object with the x and z components of the
  * current object.
  * 
  * @returns The function `getXZ()` returns a `Vector2f` object with two elements: `x`
  * and `z`. The first element is `x`, and the second element is `z`.
  */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

 /**
  * This function sets the elements of a `Vector3f` object to the specified `x`, `y`,
  * and `z` values.
  * 
  * @param x The `x` input parameter sets the value of the `x` component of the
  * `Vector3f` object.
  * 
  * @param y In the function you provided the `y` input parameter is not used at all.
  * The function only modifies the `x` and `z` components of the `Vector3f` object and
  * returns the same object reference. Therefore the `y` input parameter can be ignored
  * and has no effect on the behavior of the function.
  * 
  * @param z The `z` input parameter sets the `z` component of the `Vector3f` object.
  * 
  * @returns The function `set(float x`, `float y`, `float z)` modifies the instance
  * variables of a `Vector3f` object and returns the same object as its output. In
  * other words， the function does not return any new object or value but rather modify
  * the existing one. Therefore the output returned by this function is the modified
  * `Vector3f` object itself.
  */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

 /**
  * This function sets the components of the Vector3f object to the corresponding
  * components of the passed Vector3f argument.
  * 
  * @param r In the function you provided:
  * 
  * The `r` parameter is an object of type `Vector3f` that is passed as a argument to
  * the function. It is used to set the values of the current vector to the values of
  * the input vector.
  * 
  * @returns This function takes a `Vector3f` argument named `r` and sets the components
  * of this object to the components of `r`. The function returns a reference to this
  * object (i.e., `this`).
  * 
  * In other words:
  * 
  * 	- The output returned by this function is `this`, which is the object that was
  * passed as an argument.
  * 	- This function does not return a new object or any value; it modifies the existing
  * object.
  */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

 /**
  * This function "getX()" returns the value of the variable "x", which is a float.
  * 
  * @returns The function `getX()` returns `float` value of the field `x`. However,'x'
  * is undefined since it has not been assigned any value yet. Therefore ,the output
  * of the function would be 'NaN'(Not a Number).
  */
	public float getX() {
		return x;
	}

 /**
  * The given function sets the value of the field `x` of the current object to the
  * specified `float` value passed as an argument.
  * 
  * @param x The `x` input parameter sets the value of the field `x` within the object
  * to the value passed as an argument.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * This function `getY()` returns the value of the field `y`.
  * 
  * @returns The function `getY()` returns the value of the field `y` which is undefined.
  * Therefore the output of this function is undefined or null.
  */
	public float getY() {
		return y;
	}

 /**
  * This function sets the `y` field of the object to the given `float` value passed
  * as an argument.
  * 
  * @param y The `y` input parameter sets the value of the field `y` within the object.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * This function returns the value of the `z` field of the object.
  * 
  * @returns The function `getZ()` returns `float` value of `z`, which is currently
  * `undefined`. Therefore the output returned by this function is `NaN` (Not a Number).
  */
	public float getZ() {
		return z;
	}

 /**
  * This function sets the value of the field `z` to the given `float` parameter `z`.
  * 
  * @param z The `z` input parameter sets the value of the member variable `z`.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * This function implements the `equals` method for a `Vector3f` object. It compares
  * the current instance with another `Vector3f` instance passed as an argument and
  * returns `true` if all three components (x/y/z) are equal; otherwise returns `false`.
  * 
  * @param r In this function implementation of `equals()` method for `Vector3f`, `r`
  * is a reference to another `Vector3f` object that is being compared with the current
  * vector for equality.
  * 
  * @returns This function returns a boolean value indicating whether the vector is
  * equal to another vector. The output returned is:
  * 
  * true if all the elements of the current vector are equal to the corresponding
  * elements of the other vector; otherwise false.
  */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}

