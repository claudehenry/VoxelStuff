package com.ch.math;

/**
 * represents a 3D vector in homogeneous coordinates and provides various methods for
 * manipulating and querying its components. It includes methods such as addition,
 * subtraction, multiplication, division, cross product, normalization, rotation, and
 * more. Additionally, it provides getters and setters for each component of the vector.
 */
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
  * calculates the Euclidean distance of a 3D point from origin, using the Pythagorean
  * theorem to calculate the square root of the sum of the squared x, y, and z coordinates.
  * 
  * @returns the square root of the sum of the squares of the function's input variables.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
 /**
  * calculates the length of a point in 3D space by squaring its coordinates and summing
  * them.
  * 
  * @returns a floating-point number representing the square of the length of the 3D
  * vector.
  */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

 /**
  * computes the maximum value from three input values `x`, `y`, and `z`, using the
  * `Math.max()` method.
  * 
  * @returns the maximum value of `x`, `y`, and `z`.
  */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

 /**
  * computes the dot product of a `Vector3f` instance `r` and the component values of
  * the function's parameter `x`, `y`, and `z`.
  * 
  * @param r 3D vector that the dot product is to be calculated with.
  * 
  * 	- `x`, `y`, and `z` are variables that represent the coordinates of the vector.
  * 	- `getX()`, `getY()`, and `getZ()` are methods that retrieve the values of these
  * coordinates from the `r` object.
  * 
  * @returns a float value representing the dot product of the input vector and the
  * current vector.
  */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

 /**
  * computes the cross product of two vectors in 3D space, returning a new vector with
  * magnitudes and directions determined by the dot product of the input vectors.
  * 
  * @param r 3D vector to cross with the current vector.
  * 
  * 	- `getZ()` and `getY()` return the z- and y-coordinates of the input vector, respectively.
  * 	- `getX()` returns the x-coordinate of the input vector.
  * 
  * The function then computes the cross product between the input vector and another
  * unspecified vector, resulting in a new vector with coordinates `x`, `y`, and `z`.
  * The returned vector is a new object of type `Vector3f`.
  * 
  * @returns a vector that represents the cross product of the input vectors.
  * 
  * 	- The output is a new Vector3f object that represents the cross product of the
  * input vectors.
  * 	- The x, y, and z components of the output represent the projections of the input
  * vectors onto the corresponding axes.
  * 	- The order of the input vectors does not affect the result of the cross product
  * operation.
  * 	- The cross product is commutative, meaning that the order of the input vectors
  * does not change the result.
  */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

 /**
  * normalizes a vector by dividing its components by the vector's length, returning
  * a new vector with the same direction but scaled to have a length of 1.
  * 
  * @returns a normalized version of the input vector.
  * 
  * The output is a `Vector3f` object that represents the normalized version of the
  * original vector.
  * 
  * The elements of the output vector are scaled by dividing them by the magnitude of
  * the original vector, which is calculated using the `length()` method.
  * 
  * The resulting vector has the same direction as the original vector, but its magnitude
  * is reduced to 1.0.
  * 
  * The output vector is a new object that is independent of the original vector and
  * can be used for further calculations or operations.
  */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

 /**
  * rotates a `Vector3f` object by an angle around a specified axis, returning the
  * rotated vector.
  * 
  * @param axis 3D rotation axis around which the object is rotated.
  * 
  * 	- `axis`: A `Vector3f` object representing the axis of rotation. It has three
  * components: `x`, `y`, and `z`.
  * 
  * @param angle 3D rotation angle of the vector in radians, which is used to calculate
  * the rotation matrix for the vector's cross product and addition with other vectors.
  * 
  * @returns a new vector that represents the rotated version of the original vector.
  * 
  * 	- The output is a vector with three components, representing the new position of
  * the original vector after rotation.
  * 	- The first component represents the new x-position, the second component represents
  * the new y-position, and the third component represents the new z-position.
  * 	- The magnitude of the output vector remains unchanged, as the rotation is performed
  * around a fixed axis.
  * 	- The direction of the output vector is rotated relative to the original vector,
  * based on the angle of rotation and the axis of rotation.
  */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

 /**
  * takes a quaternion as input and rotates the vector by applying a series of
  * multiplication operations on it, resulting in a new vector with the same x, y, and
  * z components as the original vector but with a different orientation in 3D space.
  * 
  * @param rotation 4D rotation matrix that is applied to the current vector, resulting
  * in a new vector output.
  * 
  * 	- The `Quaternion` class is utilized to model a 3D rotation transformation.
  * 	- The `conjugate()` method returns a conjugated quaternion that cancels out the
  * original rotation when multiplied.
  * 	- The multiplication of `rotation` with itself followed by multiplication with
  * the conjugate (`w`) generates the final rotated vector.
  * 
  * @returns a new vector representing the rotated position of the original vector.
  * 
  * The output is a `Vector3f` object containing the rotated coordinates of the original
  * vector.
  * The x-coordinate represents the new x-coordinate of the rotated vector.
  * The y-coordinate represents the new y-coordinate of the rotated vector.
  * The z-coordinate represents the new z-coordinate of the rotated vector.
  */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

 /**
  * calculates a smooth transition between two vectors, `dest` and `this`, based on
  * the given `lerpFactor`. It returns the resulting vector by adding the product of
  * the difference between `dest` and `this` scaled by `lerpFactor` to `this`.
  * 
  * @param dest 3D vector to which the current vector will be interpolated or mixed
  * with the given `lerpFactor`.
  * 
  * 	- `dest` is a `Vector3f` object that represents the destination point in 3D space.
  * 	- `lerpFactor` is a floating-point value representing the interpolation factor
  * between the current position and the destination point.
  * 
  * The function then returns a new `Vector3f` object that represents the interpolated
  * position between the current position and the destination point, calculated by
  * subtracting the current position from the destination point, multiplying the result
  * by the interpolation factor, and adding the current position back to the resulting
  * vector.
  * 
  * @param lerpFactor 0 to 1 value that determines how much the current vector should
  * be linearly interpolated towards the destination vector.
  * 
  * @returns a vector that interpolates between the input `dest` and the current
  * position of the object.
  * 
  * The returned Vector3f object represents the interpolated value between the input
  * `dest` and the current state of the entity.
  * The `mul` method is applied to the difference between the current state and `dest`,
  * scaling the result by the provided `lerpFactor`.
  * The resulting vector is then added to the current state, resulting in the final
  * interpolated value.
  */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * adds two `Vector3f` objects together, returning a new `Vector3f` object with the
  * sum of their components.
  * 
  * @param r 3D vector to be added to the current vector.
  * 
  * 	- `x`: The x-coordinate of `r`.
  * 	- `y`: The y-coordinate of `r`.
  * 	- `z`: The z-coordinate of `r`.
  * 
  * @returns a new vector with the sum of the input vectors' components.
  * 
  * 	- The output is a new `Vector3f` instance, which represents the sum of the input
  * vectors `x`, `y`, and `z`.
  * 	- The `x`, `y`, and `z` components of the output are calculated by adding the
  * corresponding components of the input vectors.
  * 	- The output has the same type and dimension as the input vectors, which is a 3D
  * vector in this case.
  */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
 /**
  * adds the component-wise sum of the input vector to the current position of the object.
  * 
  * @param r 3D vector to add to the object's position, and its x, y, and z components
  * are added to the object's corresponding components, respectively.
  * 
  * 	- `x`, `y`, and `z` represent the x, y, and z components of the vector, respectively.
  */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

 /**
  * takes a single float argument `r` and returns a new `Vector3f` object with the sum
  * of the current vector's components and the `r` value added to each component.
  * 
  * @param r 3D offset to add to the vector's components.
  * 
  * @returns a new vector instance with the sum of the original vector's components
  * and the input parameter `r`.
  * 
  * 	- The output is a new `Vector3f` object that represents the sum of the original
  * vector and the input float value.
  * 	- The x, y, and z components of the output are calculated by adding the corresponding
  * components of the original vector and the input float value.
  */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
 /**
  * adds a vector to this object by scaling it with a given factor and then adding it
  * to the current position.
  * 
  * @param v 3D vector to be scaled and added to the current vector.
  * 
  * 	- `v` is an instance of `Vector3f`, representing a 3D vector in homogeneous coordinates.
  * 	- The `scale` parameter represents a scalar value that multiplies the components
  * of `v`.
  * 
  * @param scale scalar value by which the given `Vector3f` is multiplied before being
  * added to the current vector.
  * 
  * @returns a new vector that is the sum of the original vector and the scaled version
  * of the original vector.
  * 
  * The output is a new Vector3f object that represents the sum of the original vector
  * `this` and the scaled version of the input vector `v`. The scaling factor is
  * multiplied to the elements of the input vector before adding it to the original vector.
  * 
  * The resulting vector has the same dimensions as the original vector, with each
  * element representing the sum of its corresponding elements in the original vector
  * and the scaled input vector.
  */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
 /**
  * multiplies a vector by a scalar value and adds it to the current vector, scaling
  * the original vector.
  * 
  * @param v 3D vector to be scaled.
  * 
  * 	- `v` is a `Vector3f` object representing a 3D vector with three components (x,
  * y, and z).
  * 	- The `mul()` method is used to multiply the vector by a scalar value `scale`,
  * which is passed as an argument to the function. This operation scales the vector's
  * components by the specified factor.
  * 
  * @param scale 3D vector that multiplies the input `Vector3f` object, resulting in
  * the updated object being added to the calling scope.
  */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

 /**
  * takes a `Vector3f` argument `r` and returns a new `Vector3f` object representing
  * the difference between the original vector and the given vector.
  * 
  * @param r 3D vector to be subtracted from the current vector.
  * 
  * The input `r` is a `Vector3f`, which represents a 3D vector in a homogeneous
  * coordinate space. It has three components: `x`, `y`, and `z`, each representing
  * the component of the vector in the corresponding dimension.
  * 
  * @returns a new `Vector3f` instance representing the difference between the input
  * vector and the given reference vector.
  * 
  * The `Vector3f` object `r` is subtracted from the original input vector, resulting
  * in a new vector with the same components but with the values of the input vector
  * shifted by the corresponding amount.
  * 
  * The return type of the function is a `Vector3f` object, indicating that it returns
  * an object of this class with its properties representing the magnitude and direction
  * of a 3D vector.
  */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * takes a single floating-point argument `r` and returns a new `Vector3f` object
  * representing the difference between the original vector and the provided value.
  * 
  * @param r 3D position from which the vector is subtrahed.
  * 
  * @returns a new `Vector3f` instance representing the difference between the original
  * vector and a given value.
  * 
  * 	- The returned vector has its x-component set to the difference between the input
  * `r` and the current `x` component of the original vector.
  * 	- Similarly, the y-component is set to the difference between the input `r` and
  * the current `y` component of the original vector.
  * 	- Finally, the z-component is set to the difference between the input `r` and the
  * current `z` component of the original vector.
  */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

 /**
  * multiplies two vector objects and returns a new vector object with the product of
  * the x, y, and z components of each vector.
  * 
  * @param r 3D vector to multiply with the current vector, resulting in a new 3D vector.
  * 
  * 	- `x`, `y`, and `z` represent the component values of the `Vector3f` class.
  * 	- `getX()`, `getY()`, and `getZ()` return the individual component values of the
  * `r` object.
  * 
  * @returns a new vector with the product of the input vectors' components.
  * 
  * The `Vector3f` object created by the function has the same x, y, and z components
  * as the product of the input vectors `x`, `y`, and `z`. In other words, the output
  * vector is a scaled version of the input vectors. The scaling factor is equal to
  * the product of the input vectors' corresponding components.
  */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

 /**
  * multiplies the components of a `Vector3f` object by a scalar value, returning a
  * new `Vector3f` object with the modified components.
  * 
  * @param r scalar value used to multiply each component of the `Vector3f` object.
  * 
  * @returns a new `Vector3f` instance with the product of the component values and
  * the input scalar `r`.
  * 
  * 	- The output is of type `Vector3f`, which represents a 3D vector in homogeneous
  * coordinates.
  * 	- The value of each component (x, y, and z) of the output is equal to the
  * corresponding component of the input multiplied by the scalar value `r`.
  * 	- The output has the same origin as the input, meaning that the origin of the
  * input is also the origin of the output.
  */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

 /**
  * takes a reference to another vector and returns a new vector with the component
  * values divided by the corresponding values of the reference vector.
  * 
  * @param r vector to be divided by the return value of the function.
  * 
  * 	- `x`: The x-coordinate of the input vector.
  * 	- `y`: The y-coordinate of the input vector.
  * 	- `z`: The z-coordinate of the input vector.
  * 
  * The function then returns a new `Vector3f` object with the result of dividing each
  * component of the input vector by the corresponding component of `r`.
  * 
  * @returns a new vector with the same components as the input vector, scaled by the
  * ratio of the input vector's magnitude to the magnitude of the reference vector.
  * 
  * The output is a new `Vector3f` object that represents the result of dividing the
  * input vector by the specified reference vector.
  * 
  * The output has the same x, y, and z components as the input vector, but with the
  * values scaled by the reciprocal of the corresponding component of the reference vector.
  * 
  * The resulting vector has the same orientation and magnitude as the input vector,
  * but with the values normalized to represent a fractional quantity.
  */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

 /**
  * takes a scalar value `r` and returns a new `Vector3f` instance with its components
  * scaled by the reciprocal of `r`.
  * 
  * @param r scalar value used to divide each component of the `Vector3f` instance
  * being divided.
  * 
  * @returns a vector with the same x, y, and z components scaled by the input value
  * `r`.
  * 
  * The `Vector3f` object returned by the function has its x, y, and z components
  * scaled by the input parameter `r`.
  * 
  * The resulting vector has a magnitude that is inversely proportional to the value
  * of `r`.
  * 
  * The direction of the vector remains unchanged.
  */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

 /**
  * calculates and returns a new `Vector3f` object representing the absolute value of
  * the input vector's components.
  * 
  * @returns a new `Vector3f` instance containing the absolute values of the input
  * vector's components.
  * 
  * 	- The output is a new instance of the `Vector3f` class, indicating that it is an
  * independent entity with its own set of properties and attributes.
  * 	- The `x`, `y`, and `z` components of the output are the absolute values of the
  * corresponding components of the input vector.
  * 	- The output has the same dimension as the input vector, meaning it preserves the
  * original shape and structure of the input.
  */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

 /**
  * generates a string representation of a given object by concatenating its properties
  * (x, y, and z) inside parentheses.
  * 
  * @returns a string representation of the object's state, consisting of three values:
  * `x`, `y`, and `z`.
  */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

 /**
  * returns a `Vector2f` object containing the x and y coordinates of a point.
  * 
  * @returns a `Vector2f` object containing the x and y coordinates of the point.
  * 
  * 	- `x`: The first component of the Vector2f represents the x-coordinate of the
  * point. It has a value of `x` in this case.
  * 	- `y`: The second component of the Vector2f represents the y-coordinate of the
  * point. It has a value of `y` in this case.
  * 
  * The returned Vector2f object contains the coordinates of the point in a structured
  * form, making it easy to use and manipulate in various contexts.
  */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

 /**
  * returns a `Vector2f` object representing the Y and Z coordinates of an entity.
  * 
  * @returns a `Vector2f` object representing the Y and Z coordinates of a point.
  * 
  * The returned object is of type `Vector2f`, which represents a 2D point in homogeneous
  * coordinates. The `y` and `z` fields of the object contain the x-coordinates and
  * z-coordinates of the point, respectively.
  */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

 /**
  * returns a `Vector2f` object representing the component values of z and x.
  * 
  * @returns a new `Vector2f` object containing the `z` and `x` components.
  * 
  * 	- The output is a `Vector2f` object, which represents a 2D point with two components:
  * `z` and `x`.
  * 	- The `z` component represents the z-coordinate of the point, which ranges from
  * -1 to 1.
  * 	- The `x` component represents the x-coordinate of the point, which ranges from
  * -1 to 1.
  * 
  * Overall, the `getZX` function returns a `Vector2f` object that contains the
  * coordinates of a 2D point in a compact and efficient format.
  */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

 /**
  * returns a `Vector2f` object representing the coordinates (x, y) of a point.
  * 
  * @returns a new `Vector2f` object containing the `y` and `x` coordinates of the
  * original vector.
  * 
  * 	- `y`: The y-coordinate of the point, which is a floating-point number representing
  * the distance from the origin along the y-axis.
  * 	- `x`: The x-coordinate of the point, which is also a floating-point number
  * representing the distance from the origin along the x-axis.
  * 
  * Overall, the `getYX` function returns a `Vector2f` object that represents a point
  * in 2D space with both coordinates specified.
  */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

 /**
  * returns a `Vector2f` object representing the position (x, y) of an entity in a 2D
  * space.
  * 
  * @returns a `Vector2f` object representing the point (z, y) in the Cartesian
  * coordinate system.
  * 
  * 	- The output is a `Vector2f` object representing a 2D point with two components
  * - `z` and `y`.
  * 	- The values of `z` and `y` are assigned to the corresponding components of the
  * vector.
  * 	- The resulting vector represents the location in 3D space, with the `z` component
  * indicating distance from the origin along the z-axis, and the `y` component
  * indicating distance from the origin along the y-axis.
  */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

 /**
  * returns a `Vector2f` object containing the `x` and `z` coordinates of a point.
  * 
  * @returns a new `Vector2f` object containing the `x` and `z` components of the
  * original vector.
  * 
  * 	- The output is of type `Vector2f`, which represents a 2D vector in homogeneous
  * coordinates.
  * 	- The `x` and `z` components of the vector represent the x-coordinates and
  * z-coordinates of the point in 3D space, respectively.
  * 	- The vector is returned as a new instance of the `Vector2f` class, which allows
  * for efficient manipulation and processing of the vector's properties.
  */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

 /**
  * sets the values of the `x`, `y`, and `z` fields of a `Vector3f` object to the input
  * parameters.
  * 
  * @param x 3D position of the vector along the x-axis.
  * 
  * @param y 2D component of the vector, and by setting it to a new value, the vector's
  * 2D component is updated.
  * 
  * @param z 3D position of the vector along the z-axis, which is updated by assigning
  * the value to the `z` field of the `Vector3f` object.
  * 
  * @returns a reference to the same `Vector3f` object, allowing chaining of method calls.
  * 
  * 	- This function modifies the instance variables `x`, `y`, and `z` of the `Vector3f`
  * object and returns the modified object itself.
  * 	- The returned object will have its instance variables set to the input values
  * `x`, `y`, and `z`.
  * 	- The `set` function is a method that belongs to the `Vector3f` class and can be
  * used to modify the properties of an existing `Vector3f` object.
  */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

 /**
  * sets the values of the `Vector3f` object to those of the provided `r` object.
  * 
  * @param r 3D vector that contains the new values for the object's position, and it
  * is used to update the object's position by setting the `x`, `y`, and `z` components
  * of the object to the corresponding values in the `r` vector.
  * 
  * 	- `getX()`: The x-coordinate of the vector.
  * 	- `getY()`: The y-coordinate of the vector.
  * 	- `getZ()`: The z-coordinate of the vector.
  * 
  * @returns a reference to the original `Vector3f` object, unchanged.
  * 
  * The function sets the values of the Vector3f object to those of the provided reference.
  * It returns a reference to the same Vector3f object that was passed in as an argument,
  * indicating that the method modified the original object and returned a reference
  * to it.
  */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
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
  * sets the value of the field `x` to the argument passed as a float, assigning it
  * to the field directly.
  * 
  * @param x float value that will be assigned to the `x` field of the object upon
  * calling the `setX()` method.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * returns the value of the `y` field.
  * 
  * @returns the value of the `y` field.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of a class instance field 'y' to the argument passed as a float parameter.
  * 
  * @param y new value of the object's `y` field, which is being assigned to by the
  * function call.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * retrieves the value of the `z` field, which represents an object's height or depth
  * in a 3D space.
  * 
  * @returns a floating-point value representing the z component of an object's position.
  */
	public float getZ() {
		return z;
	}

 /**
  * sets the value of a class instance field `z`.
  * 
  * @param z 3D coordinate of the object being manipulated, which is assigned to the
  * `z` field of the function's caller.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * compares the `Vector3f` object `r` to the current instance, returning `true` if
  * all component values are equal.
  * 
  * @param r 3D vector to be compared with the current vector, and is used to determine
  * equality between the two vectors.
  * 
  * 	- `x`: The `x` property of `r` represents the x-coordinate of the vector.
  * 	- `y`: The `y` property of `r` represents the y-coordinate of the vector.
  * 	- `z`: The `z` property of `r` represents the z-coordinate of the vector.
  * 
  * @returns a boolean value indicating whether the vector's components are equal to
  * those of the provided vector.
  */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
