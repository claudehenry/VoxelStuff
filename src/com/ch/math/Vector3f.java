package com.ch.math;

/**
 * in Java is a mathematical representation of a three-dimensional vector with x, y,
 * and z components. It provides various methods for manipulating the vector, such
 * as addition, subtraction, multiplication, and division, as well as methods for
 * calculating the magnitude and direction of the vector. The class also offers methods
 * for rotating the vector around an axis and lerping between two vectors. Additionally,
 * it has getters and setters for each component of the vector.
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
  * calculates the Euclidean distance of a 3D point from origin, based on the coordinates'
  * square magnitudes and square root operation.
  * 
  * @returns the square root of the sum of the squares of the coordinates of a 3D point.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
 /**
  * calculates the length of a 3D point by squaring its coordinates and summing them.
  * 
  * @returns a floating-point representation of the length of the square of the given
  * coordinates.
  */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

 /**
  * takes three floating-point arguments `x`, `y`, and `z` and returns the largest of
  * them using the `Math.max()` method.
  * 
  * @returns the maximum value of `x`, `y`, and `z`.
  */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

 /**
  * computes the dot product of a `Vector3f` object and another vector, returning the
  * result as a floating-point value.
  * 
  * @param r 3D vector that the function will dot product with the `x`, `y`, and `z`
  * components of the function's output.
  * 
  * 	- `x`, `y`, and `z` represent the components of the input vector `r`.
  * 	- `getX()`, `getY()`, and `getZ()` are methods that return the individual component
  * values of the input vector.
  * 
  * @returns a floating-point number representing the dot product of the input vector
  * and the component vectors.
  */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

 /**
  * takes a vector `r` as input and returns a new vector `v` that is perpendicular to
  * both `r` and the original vector `u`.
  * 
  * @param r 2D vector that the `cross()` method will produce when multiplied by it,
  * resulting in a 3D vector.
  * 
  * `r`: A `Vector3f` object with attributes `x`, `y`, and `z`.
  * 
  * @returns a vector with the cross product of the input vectors.
  * 
  * The output is a new `Vector3f` instance with the values x_, y_, and z_.
  * The values of x_, y_, and z_ are determined by multiplying the corresponding
  * components of the input vectors r and the current vector by the appropriate scalars.
  * The resulting values are then stored in the components of the output vector.
  */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

 /**
  * normalizes a 3D vector by dividing its components by the vector's length, returning
  * a new vector with the same direction but scaled to have a length of 1.
  * 
  * @returns a normalized vector in the format `(x / length, y / length, z / length)`.
  * 
  * 	- The output is a `Vector3f` object representing the normalized version of the
  * original vector.
  * 	- The values of the output are in the range [0, 1], indicating that the vector
  * has been scaled to have a length of 1.
  * 	- The output's x, y, and z components represent the normalized values of the
  * original vector's corresponding components.
  */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

 /**
  * takes a rotation axis and angle as input and returns a new vector that has been
  * rotated by the given angle around the specified axis.
  * 
  * @param axis 3D rotational axis around which the object will be rotated.
  * 
  * 	- `axis`: A 3D vector representing the axis of rotation. It has three components
  * - `x`, `y`, and `z`.
  * 
  * @param angle 3D rotation angle of the vector, which is used to calculate the
  * resulting rotated vector.
  * 
  * @returns a rotated vector representing the result of applying a rotation transformation
  * to the original vector.
  * 
  * The output is a Vector3f object that represents the rotated version of the original
  * vector.
  * 
  * The vector's x, y, and z components have been transformed by applying the rotation
  * axis and angle.
  * 
  * The axis parameter represents the direction of rotation, and the angle parameter
  * represents the angle of rotation in radians.
  */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

 /**
  * rotates a `Vector3f` object by the angle represented by a given `Quaternion`
  * rotation, resulting in a new `Vector3f` object containing the rotated coordinates.
  * 
  * @param rotation 3D rotation transformation to be applied to the existing vector values.
  * 
  * 1/ Quaternion rotation multiplication: The function first multiplies the input
  * `rotation` with itself and then with its conjugate (`conjugate`). This is done to
  * generate a new quaternion that represents the rotated position of the vector.
  * 2/ Mul operation: The function performs a mul (multiplication) operation between
  * the deserialized input `rotation` and a newly created quaternion `w`. This quaternion
  * represents the resultant rotation of the input vector.
  * 3/ Output: The final output of the function is a new vector instance, which contains
  * the rotated position of the original input vector.
  * 
  * @returns a vector representing the rotated position of the object after applying
  * the given rotation quaternion.
  * 
  * 	- The output is a new instance of the `Vector3f` class, containing the result of
  * multiplying the rotation matrix by the original vector.
  * 	- The resulting vector has the same x, y, and z components as the input vector,
  * but they have been transformed by the rotation matrix.
  * 	- The magnitude (length) of the output vector is unaffected by the rotation, as
  * the multiplication is done component-wise.
  */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

 /**
  * calculates a linear interpolation between two vectors, `dest` and `this`, based
  * on the provided `lerpFactor`. The resulting vector is the weighted sum of the
  * original vectors.
  * 
  * @param dest 3D destination vector that the current vector will be interpolated towards.
  * 
  * `dest`: The destination vector to which the interpolation will be applied. It has
  * three components: x, y, and z, representing the position in 3D space.
  * 
  * @param lerpFactor 0-1 factor for interpolating between the current position of the
  * vector and the destination position.
  * 
  * @returns a vector that interpolates between the input `dest` and the current
  * position of the object.
  * 
  * The `Vector3f` object returned by the function is a blend of the input parameters,
  * where the parameter `dest` represents the destination vector and `lerpFactor`
  * represents the interpolation factor. The function first calculates the difference
  * between the current vector and the destination vector (`this.sub(dest)`), then
  * multiplies the result by the interpolation factor (`lerpFactor`), and finally adds
  * the original vector to the resulting vector (`add(this)`).
  * 
  * The resulting vector represents a smooth transition between the initial and final
  * vectors, allowing for a continuous variation of the vector's components. The `lerp`
  * function is commonly used in computer graphics and game development to perform
  * interpolation or extrapolation of vector values.
  */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the sum
  * of its own `x`, `y`, and `z` components and those of `r`.
  * 
  * @param r 3D vector to be added to the current vector.
  * 
  * 	- `x`: An integer representing the x-coordinate value of the input vector.
  * 	- `y`: An integer representing the y-coordinate value of the input vector.
  * 	- `z`: An integer representing the z-coordinate value of the input vector.
  * 
  * @returns a new vector with the sum of the input vectors' x, y, and z components.
  * 
  * 	- The returned object is a `Vector3f` instance representing the sum of the input
  * arguments.
  * 	- The `x`, `y`, and `z` components of the returned vector are calculated by adding
  * the corresponding components of the input arguments.
  * 	- The returned vector maintains the same orientation as the input vectors, i.e.,
  * it has the same direction and magnitude.
  */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
 /**
  * adds the vector values of a reference `r` to the corresponding components of the
  * current object, resulting in an updated position.
  * 
  * @param r 3D vector that adds to the component values of the `Vector3f` instance,
  * resulting in an updated position for the object.
  * 
  * 	- `x`: The x-coordinate of the input vector.
  * 	- `y`: The y-coordinate of the input vector.
  * 	- `z`: The z-coordinate of the input vector.
  */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

 /**
  * takes a float argument `r` and returns a new `Vector3f` instance with the sum of
  * the existing vector components and the provided value added to it.
  * 
  * @param r 3D position to add to the current vector position.
  * 
  * @returns a new `Vector3f` instance with the sum of the input `r` added to the
  * corresponding components of the original vector.
  * 
  * The `Vector3f` object returned by the function represents a new vector that is the
  * sum of the original vector and the input float value. The x, y, and z components
  * of the new vector are calculated as the sum of the corresponding components of the
  * original vector and the input value.
  */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
 /**
  * adds a vector to the current vector by scaling it with a given factor, returning
  * the resulting vector.
  * 
  * @param v 3D vector to be scaled and added to the current vector.
  * 
  * 	- `v` is a `Vector3f` object representing a 3D vector.
  * 	- It has three attributes: `x`, `y`, and `z`, which represent the component values
  * of the vector in the corresponding dimensions.
  * 
  * @param scale scalar value by which the input `Vector3f` is multiplied before being
  * added to the current vector.
  * 
  * @returns a new vector that is the result of adding the provided vector scaled by
  * the given factor to the current vector.
  * 
  * The returned value is a new Vector3f object that represents the sum of this vector
  * and the scaled version of the input vector.
  * The scaling factor applied to the input vector is represented by the `scale`
  * parameter passed to the function.
  * The resulting vector has the same properties as the original vector, including its
  * position, orientation, and magnitude.
  */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
 /**
  * multiplies a `Vector3f` object by a given scalar value and adds it to the current
  * instance of the class.
  * 
  * @param v 3D vector to be scaled, and it is multiplied by the specified `scale`
  * value before being added to the internal state of the `Vector3f` object.
  * 
  * 	- `v`: A `Vector3f` object representing a 3D vector with three components - x,
  * y, and z.
  * 	- `scale`: A float value representing the scalar value to be multiplied with the
  * vector's components.
  * 
  * @param scale 3D vector multiplication factor applied to the input `Vector3f` value
  * before adding it to the current object's state.
  */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

 /**
  * subtracts the vector `r` from the input vector, returning a new vector with the
  * resultant coordinates.
  * 
  * @param r 3D vector that the function will subtract from the original vector.
  * 
  * The input `r` is a `Vector3f` object with three components - `x`, `y`, and `z`.
  * These components represent the coordinates of a 3D point in the function.
  * 
  * @returns a new vector representing the difference between the original vector and
  * the given reference vector.
  * 
  * 	- The `x`, `y`, and `z` components of the returned vector represent the difference
  * between the input vector `r` and the current vector instance.
  * 	- The resulting vector has the same dimensions as the input vector, with each
  * component representing a different dimension of the original vector.
  * 	- The returned vector is a new object that is created from the differences of the
  * input vectors, and it does not affect the original vectors in any way.
  */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * takes a single floating-point value `r` as input and returns a new `Vector3f`
  * object representing the difference between the original vector and the provided value.
  * 
  * @param r 3D vector to subtract from the original vector's components, resulting
  * in a new vector with the difference between the two vectors.
  * 
  * @returns a new vector representing the difference between the original vector and
  * a given value.
  * 
  * The Vector3f object represents a 3D vector with x, y, and z components. When called
  * with a float argument `r`, the function returns a new Vector3f instance where each
  * component is equal to the corresponding component of the original vector minus the
  * input `r`. Therefore, the output represents the difference vector between the
  * original vector and the reference point represented by `r`.
  */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

 /**
  * takes a `Vector3f` argument `r` and multiplies its components by the corresponding
  * components of the calling object, returning a new `Vector3f` instance with the
  * resulting values.
  * 
  * @param r 3D vector that multiplies with the current vector, resulting in the new
  * vector output.
  * 
  * 	- `x`, `y`, and `z` are the component values of `r`.
  * 	- `getX()`, `getY()`, and `getZ()` are methods that return the component values
  * of `r` respectively.
  * 
  * @returns a new vector with the product of the input vectors' components.
  * 
  * 	- The output is a new `Vector3f` instance, which represents the product of the
  * input vectors `x`, `y`, and `z`.
  * 	- The `x`, `y`, and `z` components of the output are calculated by multiplying
  * the corresponding components of the input vectors `r` by the factor `x`, `y`, and
  * `z`, respectively.
  */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

 /**
  * multiplies a vector by a scalar value, returning a new vector with the product.
  * 
  * @param r scalar value that is multiplied with the vector components of the `Vector3f`
  * object.
  * 
  * @returns a vector with the product of the component values and the input parameter
  * `r`.
  * 
  * The `Vector3f` object returned by the function has three components, x, y, and z,
  * each representing the multiplication of the corresponding variable in the input
  * parameters with a scalar value r. The resultant vector's components have the same
  * magnitude as the input parameters but have their directions multiplied by the
  * scalar value.
  */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

 /**
  * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the
  * components scaled by the reciprocal of `r`.
  * 
  * @param r 3D vector to divide by, and its value is used to compute the output
  * vector's components.
  * 
  * 	- `x`: The x-coordinate of the input vector.
  * 	- `y`: The y-coordinate of the input vector.
  * 	- `z`: The z-coordinate of the input vector.
  * 
  * @returns a new vector with scaled components based on the divisor `r`.
  * 
  * The returned output is a new Vector3f instance with the values of x, y, and z
  * scaled by the corresponding values of the input parameter r.
  * 
  * The scale factor for each component is obtained by dividing the value of that
  * component in the current vector by the corresponding value in the input parameter
  * r.
  * 
  * The output vector has the same direction as the original vector, but its magnitude
  * is reduced by the factor specified by the scale factor.
  */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

 /**
  * takes a scalar parameter `r` and returns a new `Vector3f` instance with each
  * component divided by the input value.
  * 
  * @param r scalar value used to divide the vector's components by.
  * 
  * @returns a vector with the same x, y, and z components divided by the input parameter
  * `r`.
  * 
  * The `Vector3f` object returned has a magnitude (or length) that is equal to the
  * divisor `r`.
  * 
  * Its direction is unchanged, as it is simply scaled down by the divisor.
  * 
  * It maintains its original orientation in 3D space.
  */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

 /**
  * computes the absolute value of a `Vector3f` object, returning a new vector with
  * the absolute values of its components.
  * 
  * @returns a new `Vector3f` object containing the absolute values of the input
  * vector's components.
  * 
  * The returned object is a Vector3f with magnitude equal to the absolute value of
  * the original vector's components.
  * The vector's x, y, and z components are unchanged.
  * The returned vector has the same orientation as the original vector.
  */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

 /**
  * returns a string representation of an object by concatenating three values: `x`,
  * `y`, and `z`.
  * 
  * @returns a string representation of a point in space, consisting of three coordinates
  * separated by spaces.
  */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

 /**
  * returns a `Vector2f` instance containing the x and y coordinates of the object it
  * is called on.
  * 
  * @returns a vector representation of two floating-point values, `x` and `y`, which
  * are the coordinates of a point in 2D space.
  * 
  * 	- `x`: The first component of the `Vector2f` object represents the x-coordinate
  * of the point. It has a value of `x`.
  * 	- `y`: The second component of the `Vector2f` object represents the y-coordinate
  * of the point. It has a value of `y`.
  */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

 /**
  * returns a `Vector2f` object representing the y and z components of a specified point.
  * 
  * @returns a `Vector2f` object containing the `y` and `z` coordinates.
  * 
  * 	- The `Vector2f` object represents a two-dimensional point in homogeneous
  * coordinates, with the `x` component representing the x-coordinate and the `y`
  * component representing the y-coordinate.
  * 	- The `y` and `z` components of the vector represent the y-coordinates and
  * z-coordinates of the point, respectively.
  * 	- The `Vector2f` class is immutable, which means that once an instance is created,
  * its properties cannot be modified.
  */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

 /**
  * returns a new `Vector2f` object representing the z-value and x-coordinate of a point.
  * 
  * @returns a `Vector2f` object representing the point (x, z).
  * 
  * 	- The output is of type `Vector2f`, which represents a 2D point in homogeneous coordinates.
  * 	- The components of the vector represent the x-coordinate (x) and the z-coordinate
  * (z) of the point, respectively.
  * 	- The vector is returned as a new instance of `Vector2f`, allowing for efficient
  * use and manipulation of the point's properties.
  */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

 /**
  * returns a `Vector2f` object representing the coordinate (x, y) of a point.
  * 
  * @returns a `Vector2f` object containing the values of `y` and `x`.
  * 
  * The return value is a `Vector2f` object containing the x-coordinate (x) and
  * y-coordinate (y) of the point in question. Both coordinates are floating-point
  * numbers represented as a pair of values with two components each. The order of the
  * coordinates in the vector is (x, y).
  */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

 /**
  * returns a `Vector2f` object representing the position (x, y) of an entity.
  * 
  * @returns a `Vector2f` object representing the point (z, y) in homogeneous coordinates.
  * 
  * 	- The `Vector2f` object represents a 2D point in homogeneous coordinates, with
  * the x-coordinate represented by `z` and y-coordinate represented by `y`.
  * 	- The `Vector2f` class is part of the Java Math library and provides mathematical
  * operations on 2D points.
  * 	- The returned object is created as a new instance of the `Vector2f` class, with
  * the `z` and `y` coordinates set to the values passed in the function.
  */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

 /**
  * returns a `Vector2f` object representing the X and Z coordinates of a point.
  * 
  * @returns a `Vector2f` object containing the `x` and `z` coordinates of the point.
  * 
  * 	- The output is a `Vector2f` object, representing a 2D point in the x-z plane.
  * 	- The `x` and `z` components of the vector represent the coordinates of the point
  * in the x-z plane.
  * 	- The vector is newly created each time the function is called, so it does not
  * retain any previous values.
  */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

 /**
  * modifies the component values of the `Vector3f` object, returning a reference to
  * the same object for chaining methods.
  * 
  * @param x 3D vector's x-coordinate that is being set to the provided value.
  * 
  * @param y 2D coordinate of the vector in the Y direction, which is updated to match
  * the value provided by the user.
  * 
  * @param z 3rd component of the `Vector3f` object, which is updated to match the
  * value passed as an argument.
  * 
  * @returns a reference to the modified `Vector3f` instance.
  * 
  * The `Vector3f` object is updated with the new values of `x`, `y`, and `z`.
  * 
  * The returned value is the same `Vector3f` object that was passed as an argument
  * to the function.
  */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

 /**
  * sets the components of the object to the corresponding values of a provided
  * `Vector3f` argument.
  * 
  * @param r 3D vector that sets the corresponding components of the `Vector3f` object.
  * 
  * 	- `getX()`, `getY()`, and `getZ()`: These methods return the individual components
  * of the `Vector3f` object, which can be used to set the corresponding components
  * of this `Vector3f` object.
  * 
  * @returns a reference to the original `Vector3f` object, which has its components
  * set to the input values.
  * 
  * The `Vector3f` class is mutated when the set method is applied to it.
  * 
  * The `x`, `y`, and `z` components of this object are replaced with the values
  * provided in the r argument.
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
  * sets the value of the `x` field of the class it belongs to, by assigning the input
  * `float` parameter to the field.
  * 
  * @param x float value that sets the `x` field of the class instance, which is then
  * reflected in the state of the object.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * retrieves the value of the `y` field, which is a `float` variable.
  * 
  * @returns the value of the `y` field.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the `y` field of the object to which it belongs.
  * 
  * @param y value of the object's `y` field, which is being assigned to the object's
  * `y` field.
  */
	public void setY(float y) {
		this.y = y;
	}

 /**
  * retrieves the value of the `z` field.
  * 
  * @returns the value of the `z` field.
  */
	public float getZ() {
		return z;
	}

 /**
  * sets the value of the member field `z` of its object reference to the provided
  * float argument.
  * 
  * @param z 3D position of an object in the function `setZ()`.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * compares two `Vector3f` objects based on their `x`, `y`, and `z` components,
  * returning `true` if they are equal and `false` otherwise.
  * 
  * @param r 3D vector to be compared with the current vector.
  * 
  * 	- `x`: The first component of the vector, which is equal to the `getX()` method
  * of `r`.
  * 	- `y`: The second component of the vector, which is equal to the `getY()` method
  * of `r`.
  * 	- `z`: The third component of the vector, which is equal to the `getZ()` method
  * of `r`.
  * 
  * @returns a boolean value indicating whether the vector's x, y, and z components
  * are equal to those of the provided vector.
  */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
