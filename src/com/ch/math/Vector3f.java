package com.ch.math;

/**
 * in the given Java file provides methods for vector operations such as length,
 * square length, max, dot product, cross product, normalization, rotation, lerp,
 * addition, subtraction, multiplication, division, absolute value, and string
 * representation. It also offers setters and equals method for setting and comparing
 * the values of its x, y, and z fields.
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
	 * calculates the Euclidean distance of a 3D point from the origin using the formula
	 * `(x*x+y*y+z*z)^(1/2)`.
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
	 * @returns a floating-point value representing the length of the square of the input
	 * coordinates.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * calculates the maximum value of three arguments: `x`, `y`, and `z`. It returns the
	 * maximum value using the `Math.max()` method.
	 * 
	 * @returns the maximum of `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * calculates the dot product of a `Vector3f` object and another vector, returning a
	 * floating-point value representing the result of the multiplication.
	 * 
	 * @param r 3D vector that dot product is being calculated with.
	 * 
	 * 	- `x`, `y`, and `z` are the components of the `r` vector.
	 * 
	 * The function then computes the dot product of these components by multiplying each
	 * component of `r` with the corresponding component of the input `r`. The result is
	 * a single floating-point value representing the dot product between the two vectors.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and the component's scalar value.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * computes and returns a vector that is perpendicular to both input vectors, based
	 * on their magnitudes and directions.
	 * 
	 * @param r 3D vector that the function `cross` takes as an argument, which is used
	 * to calculate the cross product of the two vectors.
	 * 
	 * 	- `r`: A `Vector3f` object representing a 3D vector with properties `x`, `y`, and
	 * `z`.
	 * 
	 * @returns a vector with the cross product of two other vectors.
	 * 
	 * The output is a `Vector3f` object containing the cross product of two input vectors.
	 * The x, y, and z components of the output represent the projection of the first
	 * input vector onto the second input vector's basis.
	 * The order of the inputs does not affect the result of the cross product calculation,
	 * so the function is commutative.
	 * The result is a linearly independent vector relative to the two input vectors,
	 * meaning that it can be used as a component of a larger vector without causing any
	 * contradictions.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * normalizes a given vector by dividing its components by the magnitude of the vector,
	 * resulting in a unit-length vector.
	 * 
	 * @returns a normalized vector with the same x, y, and z components as the original
	 * input vector, but scaled to have a length of 1.
	 * 
	 * The `Vector3f` object is created with the scalar value of x, y, and z divided by
	 * their corresponding lengths.
	 * 
	 * The resulting vector has the same direction as the original vector, but its magnitude
	 * is equal to the length of the original vector.
	 * 
	 * The magnitude of the returned vector is always between 0 and 1, inclusive.
	 * 
	 * The returned vector has the same orientation as the original vector.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * rotates a vector by an angle around a given axis, returning the resultant vector.
	 * 
	 * @param axis 3D rotational axis around which the vector is rotated.
	 * 
	 * `axis`: A `Vector3f` object representing the rotation axis. It has three components:
	 * x, y, and z, which are floating-point values representing the coordinates of the
	 * axis in the coordinate system of the rotated object.
	 * 
	 * @param angle 3D rotation angle of the vector in radians, which is used to calculate
	 * the resulting rotated vector.
	 * 
	 * @returns a new vector that represents the rotation of the original vector around
	 * the specified axis by the specified angle.
	 * 
	 * 	- The output is a `Vector3f` object, representing a 3D vector with floating-point
	 * values for x, y, and z components.
	 * 	- The components of the output vector are calculated using the given `axis`
	 * parameter and the angle of rotation in radians. Specifically, the x, y, and z
	 * components are obtained by multiplying the input vector by the sine and cosine of
	 * the angle of rotation, respectively.
	 * 	- The output vector is then added to itself multiple times, with each addition
	 * involving the multiplication of the input vector by a scalar value and the result
	 * being added to the previous sum. This process effectively rotates the input vector
	 * around the given `axis`.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * takes a rotation quaternion as input and returns a new vector with the rotated
	 * coordinates. The function multiplies the current vector by the conjugate of the
	 * rotation quaternion, then by the rotation quaternion itself, and finally returns
	 * the resulting vector in the form (x, y, z).
	 * 
	 * @param rotation 3D rotation transformation that is applied to the current vector,
	 * resulting in a new vector with the rotated coordinates.
	 * 
	 * 	- `Quaternion rotation`: This is a mathematical object that represents a rotation
	 * in 3D space. It has four components: x, y, z, and w, where w is the scalar component
	 * representing the angle of rotation around the axis defined by the other three components.
	 * 
	 * @returns a new vector representing the rotated version of the original vector.
	 * 
	 * The output is a `Vector3f` object, representing the rotated position of the original
	 * vector.
	 * 
	 * The x, y, and z components of the output represent the rotated values of the
	 * original vector's coordinates, calculated using the multiplication operation
	 * performed on the rotation quaternion and the original vector. Specifically, the x
	 * component represents the rotated value of the original vector's x-coordinate, the
	 * y component represents the rotated value of the original vector's y-coordinate,
	 * and the z component represents the rotated value of the original vector's z-coordinate.
	 * 
	 * The quaternion multiplication operation performed in the function is used to
	 * calculate the rotated position of the input vector. The conjugate quaternion (i.e.,
	 * the complex conjugate of the rotation quaternion) is also computed and multiplied
	 * with the original vector, resulting in a second quaternion that represents the
	 * rotated position of the original vector. This multiplication operation is performed
	 * to ensure that the rotation is properly oriented in 3D space.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * computes the linear interpolation between two vectors, `dest` and `this`, based
	 * on a factor `lerpFactor`. It returns the interpolated vector by subtracting the
	 * difference between the two vectors, multiplying by the factor, and adding the
	 * starting vector.
	 * 
	 * @param dest 3D vector that the current vector will be lerped towards, with the
	 * lerp factor determining the mix of the current vector and the dest vector.
	 * 
	 * 	- `dest` represents a 3D vector with components `x`, `y`, and `z`.
	 * 	- It is used as the destination point for the linear interpolation.
	 * 	- The `lerpFactor` parameter represents the factor by which the current position
	 * is to be interpolated towards the `dest` position.
	 * 
	 * @param lerpFactor factor by which the current vector is to be linearly interpolated
	 * towards the `dest` vector.
	 * 
	 * @returns a new vector that is a weighted blend of the input vectors.
	 * 
	 * 	- The output is a `Vector3f` object, representing a point in 3D space with x, y,
	 * and z components.
	 * 	- The `dest` parameter represents the final position to which the current position
	 * is being interpolated.
	 * 	- The `lerpFactor` parameter represents the interpolation factor between the
	 * current position and the final destination.
	 * 	- The function returns a new `Vector3f` object representing the interpolated position.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * adds two vector objects together, returning a new vector object with the sum of
	 * the x, y, and z components of each input vector.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * The input `r` is a `Vector3f` type with properties `x`, `y`, and `z`.
	 * 
	 * @returns a new `Vector3f` object representing the sum of the input vectors.
	 * 
	 * 	- The output is a new `Vector3f` object with the sum of the inputs `x`, `y`, and
	 * `z`.
	 * 	- The resulting vector has the same data type as the input arguments.
	 * 	- The vector's components are calculated by adding the corresponding values of
	 * the two input vectors.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * adds the component values of a provided `Vector3f` object to the corresponding
	 * components of the current object.
	 * 
	 * @param r 3D vector that adds its x, y, and z components to the corresponding
	 * components of the `this` object.
	 * 
	 * 	- `x`, `y`, and `z` are the component values of `r`, representing the 3D position
	 * of the object in the world space.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * adds a scalar value to the `x`, `y`, and `z` components of the vector, returning
	 * a new vector with the updated coordinates.
	 * 
	 * @param r 3D offset that is added to the existing vector values of `x`, `y`, and `z`.
	 * 
	 * @returns a new vector instance with the sum of the input value and the current
	 * vector's components.
	 * 
	 * The `Vector3f` object returned by the function represents a 3D vector with x, y,
	 * and z components that have been added to their respective input values. The resulting
	 * vector has the same magnitude and direction as the original inputs.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * takes a `Vector3f` object `v` and a scalar value `scale`, and returns a new
	 * `Vector3f` object that is the result of adding the scaled version of `v` to this
	 * vector.
	 * 
	 * @param v 3D vector to be added to the current vector, multiplied by a scalar value.
	 * 
	 * 	- `v`: A `Vector3f` object containing the vector to be added with a scale factor.
	 * 
	 * @param scale scalar value by which the input `Vector3f` is multiplied before being
	 * added to the current vector.
	 * 
	 * @returns a new vector that is the result of adding the provided vector scaled by
	 * the given value to the current vector.
	 * 
	 * The output is a `Vector3f` object that represents the sum of the original vector
	 * `this` and the scaled version of the input vector `v`.
	 * 
	 * The scalar value `scale` multiplied the input vector `v`, which means that the
	 * resulting vector has the same magnitude as the input vector but with the direction
	 * modified by the scalar value.
	 * 
	 * Therefore, the output vector has the same direction as the input vector but with
	 * a larger magnitude due to the scaling factor.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * multiplies a vector by a scalar value and adds it to the current vector, scaling
	 * the current vector by that amount.
	 * 
	 * @param v 3D vector that is being scaled, and it is multiplied by the specified
	 * `scale` value before being added to the object's internal state.
	 * 
	 * 	- `v`: A `Vector3f` object representing a 3D vector with floating-point values
	 * for x, y, and z components.
	 * 
	 * @param scale scalar value that is multiplied with the `Vector3f` input, resulting
	 * in the new vector that is added to the current state of the object.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` instance with the
	 * difference between the current instance's values and those of `r`.
	 * 
	 * @param r 3D vector to be subtracted from the current vector, resulting in the new
	 * vector being returned by the function.
	 * 
	 * 	- `x`: an integer value representing the x-coordinate of the input vector.
	 * 	- `y`: an integer value representing the y-coordinate of the input vector.
	 * 	- `z`: an integer value representing the z-coordinate of the input vector.
	 * 
	 * @returns a new `Vector3f` object representing the difference between the input
	 * vector and the reference vector.
	 * 
	 * The `Vector3f` object returned has three components - `x`, `y`, and `z`. Each
	 * component is the difference between the corresponding component of the input vector
	 * and the current instance's corresponding component.
	 * 
	 * Thus, if the input vector is `r = new Vector3f(x, y, z)`, then the output vector
	 * will be `new Vector3f(x - x, y - y, z - z)`.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a scalar value `r` and subtracts it from the components of a `Vector3f`
	 * object, returning a new `Vector3f` object with the resulting values.
	 * 
	 * @param r 3D vector of subtraction.
	 * 
	 * @returns a new vector with the difference between the input value and each component
	 * of the original vector.
	 * 
	 * The `Vector3f` object returned by the function represents a vector in 3D space,
	 * with x, y, and z components. The value of each component is calculated by subtracting
	 * the input parameter `r` from the corresponding component of the original vector.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * takes a `Vector3f` argument `r` and multiplies its components by the corresponding
	 * components of the current vector, returning a new `Vector3f` object with the result.
	 * 
	 * @param r 3D vector that the `mul()` method applies multiplication to.
	 * 
	 * The returned `Vector3f` object contains the product of the corresponding properties
	 * of the deserialized input `r`. Specifically, the value of `x`, `y`, and `z` in the
	 * input `r` are multiplied by the corresponding values of the input `r`.
	 * 
	 * @returns a new vector instance with the product of the input vectors' coordinates.
	 * 
	 * 	- The output is of type `Vector3f`, indicating that it is a 3D vector with
	 * floating-point components.
	 * 	- The components of the output are calculated by multiplying the corresponding
	 * components of the input vectors `x`, `y`, and `z` together.
	 * 	- The output has the same dimensionality as the input, meaning that it can be
	 * used in the same contexts as the input without any loss of information.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * multiplies its input vector by a scalar value, returning a new vector with the
	 * resulting scaled values.
	 * 
	 * @param r scalar value used to multiply the vector components.
	 * 
	 * @returns a vector with the product of the component values of the input vector and
	 * the scalar value `r`.
	 * 
	 * The output is a new instance of the `Vector3f` class, with the elements x, y, and
	 * z multiplied by the input parameter r.
	 * The resultant vector has the same properties as the original input vector, including
	 * its magnitude, direction, and orientation in 3D space.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * takes a reference to a `Vector3f` object `r` and returns a new `Vector3f` object
	 * with the components scaled by the reciprocal of the corresponding components of `r`.
	 * 
	 * @param r vector to be divided, and its value is used to calculate the output
	 * vector's components.
	 * 
	 * `x`: The first component of the vector.
	 * 
	 * `y`: The second component of the vector.
	 * 
	 * `z`: The third component of the vector.
	 * 
	 * @returns a vector with the same x, y, and z values as the input vector divided by
	 * the corresponding values of the reference vector.
	 * 
	 * 	- The output is of type `Vector3f`, indicating that it is a 3D vector containing
	 * the result of dividing the input vectors by their corresponding values.
	 * 	- The return value represents a scaled version of the input vector, where each
	 * component has been divided by the corresponding component of the input vector.
	 * 	- The scale factor for each component is determined by the input values, with
	 * `x`, `y`, and `z` representing the input vectors.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * takes a float value `r` and returns a new `Vector3f` instance with components
	 * scaled by the reciprocal of `r`.
	 * 
	 * @param r scalar value used to divide each component of the input vector by.
	 * 
	 * @returns a vector with x, y, and z components scaled by the input value `r`.
	 * 
	 * 	- The `Vector3f` object has three components, which are the x, y, and z coordinates
	 * of the output, respectively. These coordinates have values that are the result of
	 * dividing each component of the input vector by the input scalar value `r`.
	 * 	- The resulting vector has the same units as the input vector, as the division
	 * operation preserves the unit of the original values.
	 * 	- The magnitude of the output vector is proportional to the magnitude of the input
	 * vector, with the ratio being the inverse of the input scalar value `r`.
	 * 	- The direction of the output vector is the same as the input vector, but with a
	 * scaled length that is inversely proportional to the input scalar value `r`.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * computes and returns a vector with the absolute value of its components.
	 * 
	 * @returns a new `Vector3f` object with the absolute values of the input parameters.
	 * 
	 * 1/ Vector3f object: The function returns an instance of the `Vector3f` class, which
	 * represents a 3D vector in homogeneous coordinates.
	 * 2/ Magnitude: The magnitude of the vector is calculated using the `Math.abs` method
	 * and is stored in each component of the vector.
	 * 3/ Unit length: The vector has unit length, meaning that its magnitude is equal
	 * to one.
	 * 4/ Direction: The direction of the vector remains unchanged from the original input
	 * vector.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * returns a string representation of an object's state by concatenating three values:
	 * `x`, `y`, and `z`.
	 * 
	 * @returns a string representation of a point in 3D space, consisting of three numbers
	 * separated by spaces.
	 * 
	 * 	- The parentheses in the output indicate that the string is a concatenation of
	 * three values.
	 * 	- The first value is `x`, which is represented by a numerical value.
	 * 	- The second value is `y`, also represented by a numerical value.
	 * 	- The third value is `z`, represented by another numerical value.
	 * 
	 * These properties indicate that the `toString` function returns a string representation
	 * of a set of numbers, concatenated in a specific order.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * returns a `Vector2f` object containing the x and y coordinates of an entity.
	 * 
	 * @returns a `Vector2f` object containing the x and y coordinates of the point.
	 * 
	 * 	- `x`: The x-coordinate of the vector.
	 * 	- `y`: The y-coordinate of the vector.
	 * 
	 * The output is a `Vector2f` object representing a point in 2D space with coordinates
	 * `x` and `y`.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * returns a `Vector2f` object representing the y and z coordinates of a point.
	 * 
	 * @returns a `Vector2f` object representing the pair of values (y, z).
	 * 
	 * 	- `y`: The first component of the `Vector2f` object represents the y-coordinate
	 * of the point.
	 * 	- `z`: The second component of the `Vector2f` object represents the z-coordinate
	 * of the point.
	 * 
	 * Both `y` and `z` are double values that represent the position of the point in 3D
	 * space.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * returns a `Vector2f` object containing the `z` and `x` coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the `z` and `x` coordinates.
	 * 
	 * 	- `z`: This is the y-coordinate value of the vector, which represents the vertical
	 * component of the position in 2D space.
	 * 	- `x`: This is the x-coordinate value of the vector, which represents the horizontal
	 * component of the position in 2D space.
	 * 
	 * Both `z` and `x` are type `float`, which means they can represent any real number
	 * within a certain range, depending on the context in which they are used.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * returns a new `Vector2f` object representing the position (x, y) of an entity.
	 * 
	 * @returns a `Vector2f` object representing the coordinates (x, y) of a point.
	 * 
	 * 	- `y`: The y-coordinate value of the Vector2f object, which represents the vertical
	 * position of a point in 2D space.
	 * 	- `x`: The x-coordinate value of the Vector2f object, which represents the
	 * horizontal position of a point in 2D space.
	 * 	- Type: `Vector2f` is a class that represents a 2D vector in Java, consisting of
	 * two components representing the x and y coordinates of a point.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * returns a new `Vector2f` object containing the `z` and `y` components of a given
	 * vector.
	 * 
	 * @returns a `Vector2f` object containing the `z` and `y` components of the vector.
	 * 
	 * The Vector2f object `zY` represents a 2D point with two components: `z` and `y`.
	 * The value of `z` is an integer representing the vertical coordinate, while `y` is
	 * an integer representing the horizontal coordinate. Both coordinates have a range
	 * of -100 to 100, inclusive.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * returns a `Vector2f` object containing the `x` and `z` components of an entity's
	 * position.
	 * 
	 * @returns a `Vector2f` object containing the `x` and `z` coordinates of a point.
	 * 
	 * 	- The output is a `Vector2f` object, representing a point in 2D space with x and
	 * z components.
	 * 	- The x component represents the horizontal position of the point, while the z
	 * component represents the vertical position.
	 * 	- Both components have real-valued attributes, indicating the magnitude of the
	 * point's position in each dimension.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * sets the x, y, and z components of a `Vector3f` object to the specified values,
	 * returning a reference to the modified object.
	 * 
	 * @param x 3D position of the vector along the x-axis.
	 * 
	 * @param y 2D position of the vector in the xy plane.
	 * 
	 * @param z 3rd component of the `Vector3f` instance being modified, and setting it
	 * to the provided value updates that component.
	 * 
	 * @returns a reference to the same `Vector3f` instance, allowing for chaining of
	 * method calls.
	 * 
	 * 	- The return type is `Vector3f`, indicating that the function modifies the state
	 * of an existing `Vector3f` object.
	 * 	- The function takes three floating-point arguments `x`, `y`, and `z`, which are
	 * used to set the corresponding components of the returned object.
	 * 	- The `return this;` statement indicates that the modified object is returned,
	 * allowing the caller to continue manipulating it.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * sets the x, y, and z components of the vector to the corresponding values of the
	 * provided argument.
	 * 
	 * @param r 3D vector that contains the new values for the `x`, `y`, and `z` components
	 * of the `Vector3f` object, which are then set to those values in the function.
	 * 
	 * 	- `getX()`: Returns the x-coordinate of the input vector.
	 * 	- `getY()`: Returns the y-coordinate of the input vector.
	 * 	- `getZ()`: Returns the z-coordinate of the input vector.
	 * 
	 * @returns a reference to the same `Vector3f` object, unchanged.
	 * 
	 * The `Vector3f` class's `set` method sets three floating-point values representing
	 * x, y, and z coordinates. The values are set by passing them as arguments to the method.
	 * The method returns a reference to the same instance of the `Vector3f` class,
	 * indicating that the original object remains unchanged after setting its properties.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * retrieves the value of the `x` field, which is a floating-point number.
	 * 
	 * @returns a floating-point value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of its caller to the input parameter `x`.
	 * 
	 * @param x float value that sets the internal state of the object being acted upon
	 * by the function, `setX()`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field, which is a `float`. The function simply
	 * returns the stored value without any modification or additional processing.
	 * 
	 * @returns a floating-point value representing the current position of the line on
	 * the screen.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field of its receiver object to the input float value
	 * passed as parameter.
	 * 
	 * @param y float value that will be assigned to the field `y` of the object being
	 * manipulated by the `setY()` method.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of a `z` field, which is likely used to store a scalar value
	 * representing the z-coordinate of an object or point in a mathematical or graphical
	 * context.
	 * 
	 * @returns a floating-point value representing the z component of an object's position.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of an object's `z` field to the input `float` value.
	 * 
	 * @param z 3D coordinates of an object or entity, and by assigning a new value to
	 * it within the function, the object's position is updated.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * compares the component values of two `Vector3f` objects and returns a boolean
	 * indicating if they are equal.
	 * 
	 * @param r 3D vector to compare with the current vector.
	 * 
	 * `x`: The first component of the vector, which represents the x-coordinate of the
	 * point in 3D space.
	 * 
	 * `y`: The second component of the vector, which represents the y-coordinate of the
	 * point in 3D space.
	 * 
	 * `z`: The third component of the vector, which represents the z-coordinate of the
	 * point in 3D space.
	 * 
	 * @returns a boolean value indicating whether the vector and the provided vector
	 * have the same coordinates.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
