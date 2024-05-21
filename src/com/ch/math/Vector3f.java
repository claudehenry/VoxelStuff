package com.ch.math;

/**
 * in Java provides a set of methods for manipulating and working with 3D vectors.
 * It has fields for x, y, and z components, as well as methods for adding, subtracting,
 * multiplying, dividing, and normalizing vectors. Additionally, it provides methods
 * for rotating vectors, lerping between two vectors, and calculating the magnitude
 * of a vector.
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
	 * calculates the Euclidean distance of a point from its origin, using the squared
	 * magnitudes of the coordinates and the square root operation.
	 * 
	 * @returns the square root of the sum of the squares of its input arguments.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * computes the length of a point in 3D space by squaring its coordinates and summing
	 * them.
	 * 
	 * @returns a floating-point value representing the square of the length of a 3D object.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * computes the maximum value of three arguments: `x`, `y`, and `z`. It returns the
	 * maximum value computed using `Math.max()` method.
	 * 
	 * @returns the maximum of the three input values, `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * calculates the dot product of two vectors, returning a floating-point value
	 * representing the amount of linear correlation between them.
	 * 
	 * @param r 3D vector that is to be dot-producted with the `x`, `y`, and `z` components
	 * of the function's output.
	 * 
	 * 	- `x`, `y`, and `z` are variables denoting the components of the vector in question.
	 * 	- `getX()`, `getY()`, and `getZ()` are methods that retrieve the corresponding
	 * component values from the input vector `r`.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and the vector represented by the `x`, `y`, and `z` variables.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * computes the cross product of two vectors and returns the resulting vector.
	 * 
	 * @param r 3D vector that the method `cross` operates on, and its values are used
	 * to compute the cross product of the two vectors.
	 * 
	 * 	- `getZ()`: Returns the z-component of the vector.
	 * 	- `getY()`: Returns the y-component of the vector.
	 * 	- `getX()`: Returns the x-component of the vector.
	 * 
	 * @returns a vector with the cross product of the input vectors.
	 * 
	 * 	- The output is a new `Vector3f` object with the values `x_`, `y_`, and `z_` from
	 * the input parameters `r`.
	 * 	- The values of `x_, y_, z_` represent the component-wise product of the two input
	 * vectors.
	 * 	- The resulting vector has the same direction as the original vectors, but with
	 * the magnitude of their product.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * normalizes a 3D vector by dividing each component by the vector's length, resulting
	 * in a unitized vector representation.
	 * 
	 * @returns a normalized vector with the same direction as the original vector but
	 * with a magnitude equal to the length of the original vector.
	 * 
	 * 	- The output is a `Vector3f` object representing a normalized version of the
	 * original vector.
	 * 	- The magnitude (length) of the output is equal to 1, regardless of the magnitude
	 * of the original vector.
	 * 	- The direction of the output is the same as that of the original vector, scaled
	 * by the ratio of the length of the original vector to its magnitude.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * rotates a 3D vector by an angle around a given axis, returning the resulting vector.
	 * 
	 * @param axis 3D rotation axis around which the object will be rotated.
	 * 
	 * 	- `axis`: A 3D vector representing the axis of rotation. It has three components
	 * - `x`, `y`, and `z`.
	 * 	- `angle`: The angle of rotation in radians.
	 * 
	 * @param angle 3D rotation angle of the vector, which is used to calculate the
	 * resulting rotated vector.
	 * 
	 * @returns a rotated version of the input vector, taking into account the provided
	 * angle and axis of rotation.
	 * 
	 * The output is a `Vector3f` object representing the rotated vector.
	 * 
	 * The `axis` parameter represents the axis of rotation, and its dot product with
	 * itself is 1 - cos(angle), which is added to the original vector's dot product with
	 * the axis to produce the rotated vector.
	 * 
	 * The `angle` parameter represents the angle of rotation in radians.
	 * 
	 * The `sinAngle` and `cosAngle` variables are calculated using the sine and cosine
	 * functions, respectively, of the negative angle. These values are used in the
	 * calculation of the rotated vector.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * takes a `Quaternion` object as input, rotates it by that amount, and returns the
	 * resultant vector in 3D space.
	 * 
	 * @param rotation 3D rotation of the vector, which is applied to the original vector
	 * through the multiplication operation.
	 * 
	 * 	- Quaternion rotation is a mathematical representation of 3D rotations, consisting
	 * of a scalar component and a vector component.
	 * 	- The conjugate of a quaternion is another quaternion that is the negative of the
	 * original quaternion.
	 * 	- The multiplication of a quaternion by another quaternion results in a new
	 * quaternion representing the composition of the rotations.
	 * 	- The properties of the resulting quaternion, such as its scalar component and
	 * vector component, can be accessed through methods like `getX()`, `getY()`, and `getZ()`.
	 * 
	 * @returns a new `Vector3f` object containing the rotated position of the original
	 * vector.
	 * 
	 * 	- The output is a `Vector3f` object that represents the rotated position of the
	 * original vector.
	 * 	- The x-coordinate of the output represents the cosine of the angle of rotation.
	 * 	- The y-coordinate of the output represents the sine of the angle of rotation.
	 * 	- The z-coordinate of the output represents the original position vector unrotated.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * interpolates between two vectors, `dest` and `this`, based on a given factor
	 * `lerpFactor`. It returns the resulting vector by subtracting `this`, multiplying
	 * the difference by `lerpFactor`, and adding `this` back.
	 * 
	 * @param dest 3D vector that the current vector will be blended with, using the
	 * specified `lerpFactor`.
	 * 
	 * 	- `dest`: A `Vector3f` instance representing the target position.
	 * 	- `lerpFactor`: A floating-point value representing the interpolation factor
	 * between the current position and the target position.
	 * 
	 * @param lerpFactor 0 to 1 factor by which the current vector is interpolated towards
	 * the destination vector.
	 * 
	 * @returns a vector that interpolates between two given vectors, with the specified
	 * lerp factor.
	 * 
	 * 	- The `Vector3f` object represents a 3D vector with three elements representing
	 * x, y, and z components, respectively.
	 * 	- The `dest` parameter is the starting point for the linear interpolation between
	 * the current position of the object and the desired destination.
	 * 	- The `lerpFactor` parameter represents the interpolation factor between the
	 * current position and the destination, with higher values resulting in more
	 * interpolated movement.
	 * 	- The function returns a new `Vector3f` object representing the interpolated
	 * position of the object.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a `Vector3f` argument and returns a new `Vector3f` object with the sum of
	 * the current instance's coordinates and those of the argument.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * 	- `x`: The integer value of the `x` component of `r`.
	 * 	- `y`: The integer value of the `y` component of `r`.
	 * 	- `z`: The integer value of the `z` component of `r`.
	 * 
	 * @returns a new vector with the sum of the input vectors' coordinates.
	 * 
	 * The `Vector3f` object returned by the function represents the sum of the inputs
	 * `x`, `y`, and `z`. The resulting vector has the same properties as the original
	 * inputs, including its magnitude (length) and direction.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * adds the components of a `Vector3f` object to the corresponding components of the
	 * current object, updating its position.
	 * 
	 * @param r 3D vector that adds its components to the current position of the `Vector3f`
	 * instance being modified by the `addSelf()` method.
	 * 
	 * 	- `x`, `y`, and `z` are the components of the vector in the function.
	 * 
	 * The function performs the addition of these components to the corresponding
	 * properties of the current instance `this`.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * adds a scalar value to the components of a `Vector3f` object, returning a new
	 * `Vector3f` instance with the modified values.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * @returns a new vector with the sum of the input `r` added to the corresponding
	 * components of the original vector.
	 * 
	 * 	- The `x`, `y`, and `z` fields represent the position of the vector in 3D space,
	 * respectively. Each field takes on values within the range of -1.0 to 1.0.
	 * 	- The vector's magnitude (or length) is equal to the square root of the sum of
	 * the squares of its components: `Math.sqrt(x*x + y*y + z*z)`.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * multiplies a vector by a scalar value and adds it to the current vector, resulting
	 * in a new vector with the combined values.
	 * 
	 * @param v 3D vector that is to be added to the current vector, scaled by the specified
	 * `scale` value.
	 * 
	 * 	- `v`: A `Vector3f` object representing a 3D vector with x, y, and z components.
	 * 	- `scale`: An integer value representing the scaling factor applied to the `v`.
	 * 
	 * @param scale scalar value by which the `v` input is multiplied before being added
	 * to the current vector.
	 * 
	 * @returns a new vector that is the result of adding the provided vector scaled by
	 * the given factor to the original vector.
	 * 
	 * The `Vector3f` object returned by the function represents the result of adding the
	 * provided `v` vector scaled by `scale`. Specifically, it is a new instance of
	 * `Vector3f` with the same x, y, and z components as the original `this` vector, but
	 * with the magnification applied to each component. Therefore, the resulting vector
	 * has the same direction and magnitude as the original `this` vector, but with the
	 * scaled value of the provided `v` vector added to it.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * multiplies a `Vector3f` object by a floating-point value and adds the result to
	 * the current vector instance, scaling its components proportionally.
	 * 
	 * @param v 3D vector to be scaled.
	 * 
	 * 	- `v`: A `Vector3f` object representing a 3D vector with x, y, and z components.
	 * 	- `scale`: A floating-point value representing the scaling factor to be applied
	 * to `v`.
	 * 
	 * @param scale 3D scaling factor applied to the vector being added to the object's
	 * position, rotation, and scale.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` instance representing
	 * the difference between the current instance and `r`.
	 * 
	 * @param r 3D vector to be subtracted from the current vector.
	 * 
	 * 	- `x`: The x-coordinate of the input vector.
	 * 	- `y`: The y-coordinate of the input vector.
	 * 	- `z`: The z-coordinate of the input vector.
	 * 
	 * @returns a new `Vector3f` instance representing the difference between the input
	 * vector and the reference vector.
	 * 
	 * 	- The Vector3f object represents the difference between the original vector's
	 * components and the reference vector's components.
	 * 	- The resulting vector has the same unit as the original vector, which means that
	 * its magnitude is preserved.
	 * 	- The direction of the resulting vector is the opposite of the reference vector's
	 * direction with respect to the original vector.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a scalar `r` and returns a new `Vector3f` instance representing the vector
	 * subtraction of the input vector's components from the origin, where (x, y, z) = (0,0,0).
	 * 
	 * @param r 3D vector to subtract from the current vector.
	 * 
	 * @returns a new `Vector3f` instance representing the difference between the original
	 * vector and the given value.
	 * 
	 * The `Vector3f` object returned by the function has three components: `x`, `y`, and
	 * `z`. Each component represents the difference in value between the original position
	 * of the vector and the position after subtraction. For example, if the original
	 * position of the vector is `(3, 4, 5)`, and the subtraction is performed with a
	 * value of `2`, the resulting vector would be `(1, 2, 3)`.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * multiplies two `Vector3f` objects, returning a new vector with the product of their
	 * x, y, and z components.
	 * 
	 * @param r 3D vector that the method multiplies the original vector by.
	 * 
	 * 	- `x`, `y`, and `z` represent the components of `r`.
	 * 	- The components are scalars that can be multiplied element-wise with the
	 * corresponding components of `this` to produce new values for each component.
	 * 
	 * @returns a new vector with the product of the input vectors' coordinates.
	 * 
	 * 	- The output is a new instance of the `Vector3f` class.
	 * 	- The `x`, `y`, and `z` components of the output are calculated by multiplying
	 * the corresponding components of the input `r` vector by the scalars `x`, `y`, and
	 * `z`, respectively.
	 * 	- The resulting vector has the same direction as the input vector, but its magnitude
	 * is increased by the product of the scalar multiplication.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * multiplies a vector by a scalar value, resulting in a new vector with the same
	 * components scaled by the given factor.
	 * 
	 * @param r scalar value used to multiply the components of the `Vector3f` instance.
	 * 
	 * @returns a new vector with the product of the input `r` and the corresponding
	 * components of the original vector.
	 * 
	 * The returned vector has three components, each representing the product of the
	 * corresponding component of the input vector with the scalar value `r`.
	 * 
	 * The magnitude (or length) of the output vector is equal to the product of the
	 * magnitudes of the input components and the scalar value `r`.
	 * 
	 * The direction of the output vector is unchanged from the input vector, as the
	 * multiplication is a component-wise operation.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * takes a reference to another `Vector3f` object and returns a new `Vector3f` object
	 * with the component values of the original divided by the corresponding values of
	 * the referenced object.
	 * 
	 * @param r vector to divide by, and it is used to calculate the output vector's components.
	 * 
	 * 	- `x`, `y`, and `z` are members of `Vector3f`.
	 * 	- They denote the coordinates of the vector.
	 * 
	 * The function returns a new `Vector3f` object with scaled coordinates based on the
	 * divisor `r`.
	 * 
	 * @returns a vector with the same components as the input vector, but with the values
	 * scaled by the inverse of the input vector's magnitude.
	 * 
	 * 	- The output is a new `Vector3f` instance with values x / r.getX(), y / r.getY(),
	 * and z / r.getZ().
	 * 	- The output represents the result of dividing the input vector by the specified
	 * reference vector.
	 * 	- The resulting vector has the same units as the input vector, representing a
	 * scaling of the input vector by the ratio of the reference vector's magnitude to
	 * its own magnitude.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * takes a single float argument `r` and returns a new `Vector3f` instance with x,
	 * y, and z components scaled by the ratio of `r`.
	 * 
	 * @param r scalar value that the function will divide each component of the `Vector3f`
	 * object by.
	 * 
	 * @returns a vector with components scaled by the divisor `r`.
	 * 
	 * 	- The output is a new instance of the `Vector3f` class, representing the division
	 * of the input vector by the specified scalar value.
	 * 	- The resulting vector has the same coordinates as the input vector, but with
	 * each coordinate divided by the scalar value.
	 * 	- The scaled coordinates are stored in the same locations as the original input
	 * vector, with no change to their original values or attributes.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * computes the absolute value of a vector's components and returns a new vector
	 * object with those values.
	 * 
	 * @returns a new vector with the absolute value of the original vector's components.
	 * 
	 * The return type is `Vector3f`, indicating that the function returns a new instance
	 * of the `Vector3f` class with the absolute value of the input values.
	 * 
	 * The expression `Math.abs(x)` returns the absolute value of the `x` variable, which
	 * is a double value representing the x-coordinate of a 3D vector. Similarly,
	 * `Math.abs(y)` and `Math.abs(z)` return the absolute values of the y- and z-coordinates,
	 * respectively.
	 * 
	 * The resulting `Vector3f` object has the same coordinates as the input vector, but
	 * with all values replaced by their absolute values.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * returns a string representation of an object by concatenating its component values.
	 * 
	 * @returns a string representation of the object's state, including its `x`, `y`,
	 * and `z` components.
	 * 
	 * 	- The parentheses are included as part of the output to indicate that the value
	 * is a composite one consisting of three elements - x, y, and z.
	 * 	- Each element is separated by a space from the others.
	 * 	- There is no explicit indication of the data types of each element, as the
	 * `toString` function is designed to work with any type of object that can be converted
	 * to a string.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * returns a `Vector2f` object representing the position of an entity in a two-dimensional
	 * space, with its x and y components as attributes.
	 * 
	 * @returns a `Vector2f` object containing the x and y coordinates of the point.
	 * 
	 * The Vector2f object represents a 2D location in homogeneous coordinates, with x
	 * representing the horizontal component and y representing the vertical component.
	 * The Vector2f class is a part of the Java 8 Math library. The returned vector has
	 * an x component of 0, which means it represents the origin (0, 0) on the coordinate
	 * plane. Similarly, the y component is also 0, indicating that the location is at
	 * the origin. Therefore, the returned Vector2f object represents the point (0, 0)
	 * in the Cartesian coordinate system.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * returns a `Vector2f` object representing the y and z coordinates of a point.
	 * 
	 * @returns a `Vector2f` object representing the position of the entity in the Y-Z plane.
	 * 
	 * 	- `y`: The y-component of the vector, representing the vertical position in the
	 * 2D space.
	 * 	- `z`: The z-component of the vector, representing the horizontal position in the
	 * 2D space.
	 * 
	 * Both components are represented as floating-point values, allowing for precise
	 * calculations and manipulations within the 2D space.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * returns a `Vector2f` object containing the `z` and `x` coordinates of a point.
	 * 
	 * @returns a `Vector2f` object representing the coordinates (x, z) of a point.
	 * 
	 * The Vector2f object returned has two components - z and x. The z component represents
	 * the vertical coordinate of the point, while the x component represents the horizontal
	 * coordinate.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * returns a new `Vector2f` object representing the coordinates (x, y).
	 * 
	 * @returns a `Vector2f` object containing the values of `y` and `x`.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a point in 2D space with x
	 * and y coordinates.
	 * 	- The x coordinate is represented by the variable `x`, while the y coordinate is
	 * represented by the variable `y`.
	 * 	- Both `x` and `y` are of type `float`, indicating that they can hold decimal values.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * returns a `Vector2f` instance representing the point with coordinates `z` and `y`.
	 * 
	 * @returns a `Vector2f` object containing the `z` and `y` coordinates of the point.
	 * 
	 * 	- `z`: The x-coordinate of the vector.
	 * 	- `y`: The y-coordinate of the vector.
	 * 
	 * The `getZY` function returns a new instance of the `Vector2f` class, which represents
	 * a two-dimensional vector in homogeneous coordinates. This vector has an x-coordinate
	 * of `z` and a y-coordinate of `y`.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * returns a new `Vector2f` object containing the values of `x` and `z`.
	 * 
	 * @returns a `Vector2f` object containing the `x` and `z` coordinates of the point.
	 * 
	 * The Vector2f object returned is defined by two components, `x` and `z`, which
	 * represent the x-coordinate and z-coordinate of the point, respectively.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * sets the x, y, and z components of a `Vector3f` object to the input values, returning
	 * a reference to the modified object.
	 * 
	 * @param x 3D position of the vector along the x-axis.
	 * 
	 * @param y 2D position of the vector in the Y-axis direction, and its value is used
	 * to update the corresponding component of the vector.
	 * 
	 * @param z 3D position of the vector in the z-axis, and its value is assigned to the
	 * `z` field of the `Vector3f` object.
	 * 
	 * @returns a reference to the same `Vector3f` instance with the updated x, y, and z
	 * values.
	 * 
	 * 	- The output is a reference to the same `Vector3f` instance as the input parameters.
	 * 	- The `x`, `y`, and `z` fields of the output object are assigned the values of
	 * the corresponding input parameters.
	 * 	- The output object retains its original identity, meaning it can be used in
	 * further method calls or operations without any changes to its state.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * sets the components of a `Vector3f` object to the corresponding values of another
	 * `Vector3f` object.
	 * 
	 * @param r 3D vector that contains the new values for the object's position, and it
	 * is used to set the object's position by passing its components directly into the
	 * `set()` method.
	 * 
	 * `r.getX()`: The x-coordinate of the vector.
	 * 
	 * `r.getY()`: The y-coordinate of the vector.
	 * 
	 * `r.getZ()`: The z-coordinate of the vector.
	 * 
	 * @returns a reference to the same `Vector3f` object, which has its components set
	 * to the values provided in the input parameter.
	 * 
	 * 	- The output is a reference to the same `Vector3f` instance as the input parameter
	 * `r`.
	 * 	- The output contains the new values for the x, y, and z components of the vector,
	 * which are obtained by calling the `getX()`, `getY()`, and `getZ()` methods on the
	 * input parameter `r`.
	 * 	- The output has the same properties and attributes as the input parameter `r`,
	 * including its position, direction, or any other attributes defined within the
	 * `Vector3f` class.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns a floating-point representation of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the object it is called on to the input parameter
	 * `x`.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being manipulated by the function.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of a `y` field, which is assumed to be a `float`. It returns
	 * the stored value.
	 * 
	 * @returns the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the `y` field within the object to which it belongs.
	 * 
	 * @param y 2-dimensional coordinate value for setting the Y component of an object's
	 * location.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field, which represents an object's position along
	 * the z-axis.
	 * 
	 * @returns the value of the `z` field.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the `z` field of its object argument to the inputted float value.
	 * 
	 * @param z 3D position of an object in the coordinate system and assigns it to the
	 * field `z` of the class, which stores the 3D position of the object.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * compares a `Vector3f` object with another one, returning `true` if all its components
	 * are equal, and `false` otherwise.
	 * 
	 * @param r 3D vector to compare with the current object's 3D coordinates.
	 * 
	 * 	- `x`: represents the x-coordinate of the vector
	 * 	- `y`: represents the y-coordinate of the vector
	 * 	- `z`: represents the z-coordinate of the vector
	 * 
	 * @returns a boolean value indicating whether the vector's components are equal to
	 * those of the provided vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
