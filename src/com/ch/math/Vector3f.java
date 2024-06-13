package com.ch.math;

/**
 * Is a mathematical representation of a three-dimensional point in space. It has
 * several fields and methods for performing various calculations related to vectors,
 * such as addition, subtraction, multiplication, and division by other vectors or
 * scalars. The class also provides methods for calculating the magnitude (length)
 * of a vector, normalizing it, rotating it, and converting it between different
 * coordinate systems. Additionally, it has a string representation method for
 * converting the vector to a String.
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
	 * Calculates the Euclidean distance of a point from its origin using the Pythagorean
	 * theorem and square root operation.
	 * 
	 * @returns the square root of the sum of the squares of the coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calculates the square of a 3D vector's components and returns the result as a float
	 * value.
	 * 
	 * @returns a floating-point number representing the square of the length of the
	 * provided vector.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * Computes the maximum value of three arguments: `x`, `y`, and `z`. It returns the
	 * maximum value using the `Math.max()` method.
	 * 
	 * @returns the maximum value of `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * Computes the dot product of a `Vector3f` object and another vector.
	 * 
	 * @param r 3D vector to be dot-producted with the `x`, `y`, and `z` components of
	 * the function's output.
	 * 
	 * @returns a scalar value representing the dot product of the vector `r` and the
	 * object's position vector.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * Takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the dot
	 * product of the two vectors as its components.
	 * 
	 * @param r 2D vector that the `cross()` method computes the cross product of with
	 * the given 3D vector.
	 * 
	 * @returns a vector with the cross product of the input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Normalizes a `Vector3f` instance by dividing each component by its magnitude,
	 * resulting in a unitized vector representation.
	 * 
	 * @returns a normalized vector in the form of a `Vector3f` instance, where the
	 * magnitude of the vector is equal to 1.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * Rotates a 3D vector by an angle around a specified axis, returning the resultant
	 * vector.
	 * 
	 * @param axis 3D vector that defines the rotation axis.
	 * 
	 * @param angle 3D rotation angle of the vector, which is used to calculate the
	 * resulting rotated vector.
	 * 
	 * @returns a rotated version of the original vector, based on the provided axis and
	 * angle of rotation.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * Rotates a `Vector3f` object by the specified `Quaternion` rotation, resulting in
	 * a new `Vector3f` object with the rotated components.
	 * 
	 * @param rotation 3D rotation matrix that is applied to the `Vector3f` object.
	 * 
	 * @returns a vector representing the rotated position of the original vector.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * Interpolates a destination vector (`dest`) towards a starting vector (`this`),
	 * based on a lerp factor `lerpFactor`. The function returns the interpolated vector
	 * as the result.
	 * 
	 * @param dest 3D destination vector to which the current vector will be interpolated.
	 * 
	 * @param lerpFactor amount of interpolation or blending between the starting and
	 * ending points of the vector, with higher values resulting in more gradual transitions.
	 * 
	 * @returns a new vector that is a linear combination of the input vectors, with the
	 * specified lerp factor.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Adds two vector objects, returning a new vector object with the sum of the x, y,
	 * and z components of each vector.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector3f` instance with the sum of the input vectors' coordinates.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * Updates the instance's position by adding the specified vector's components to its
	 * own.
	 * 
	 * @param r 3D vector that adds its components to the current object's position.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * Adds a scalar value `r` to the components of its input vector, returning a new
	 * vector with the added value.
	 * 
	 * @param r 3D vector to be added to the existing vector.
	 * 
	 * @returns a new `Vector3f` object representing the sum of the original vector and
	 * the specified float value.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * Adds a vector to the current position, scaling the input vector by a provided
	 * factor beforehand. The resulting vector is then returned.
	 * 
	 * @param v 3D vector to be added to the current vector, multiplied by a scalar value.
	 * 
	 * @param scale scalar value that is multiplied with the `v` input parameter, resulting
	 * in the new vector sum.
	 * 
	 * @returns a new vector that is the result of adding the original vector multiplied
	 * by the scale factor.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * Multiplies a `Vector3f` object by a scalar value and adds it to the current vector
	 * representation of the object, scaling the vector's components accordingly.
	 * 
	 * @param v 3D vector to be scaled.
	 * 
	 * @param scale 3D vector that is to be multiplied with the original `Vector3f` value.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * Takes a `Vector3f` argument `r` and returns a new `Vector3f` object representing
	 * the difference between the input and the current object's values.
	 * 
	 * @param r 3D vector to be subtracted from the current vector, resulting in the new
	 * vector.
	 * 
	 * @returns a new `Vector3f` instance representing the difference between the input
	 * vector and the reference vector.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Takes a single float argument `r` and returns a new `Vector3f` instance representing
	 * the difference between the original vector and `r`.
	 * 
	 * @param r 3D position from which to subtract the output of the function.
	 * 
	 * @returns a vector representing the difference between the original vector and the
	 * given value.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * Takes a `Vector3f` object `r` and multiplies its components by the corresponding
	 * components of the current vector, returning a new vector with the result.
	 * 
	 * @param r 3D vector to be multiplied with the current vector, resulting in a new
	 * 3D vector output.
	 * 
	 * @returns a vector with the product of the input vectors' coordinates.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * Multiplies the components of a `Vector3f` object by a scalar value `r`. The returned
	 * `Vector3f` object has the same x, y, and z components as the original, but with
	 * the values scaled by `r`.
	 * 
	 * @param r scalar value that is to be multiplied with the vector components of the
	 * `Vector3f` object.
	 * 
	 * @returns a vector with components scaled by the input parameter `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * Takes a reference to another vector and returns a new vector with the components
	 * scaled by the reciprocal of the corresponding values of the provided vector.
	 * 
	 * @param r vector to divide by, and it is used to calculate the output vector.
	 * 
	 * @returns a vector with the same components as the input vector, but scaled by the
	 * reciprocal of the corresponding component of the argument vector.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * Takes a scalar value `r` and returns a new `Vector3f` instance with components
	 * scaled by the reciprocal of `r`.
	 * 
	 * @param r scalar value used to divide each component of the `Vector3f` object.
	 * 
	 * @returns a vector with its x, y, and z components scaled by the input parameter `r`.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * Computes and returns a new `Vector3f` object containing the absolute values of its
	 * input components.
	 * 
	 * @returns a new vector with the absolute value of the original vector's components.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Returns a string representation of an object, including its instance variables (x,
	 * y, and z) separated by spaces.
	 * 
	 * @returns a string representation of a point in 3D space, consisting of three values
	 * separated by spaces.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * Returns a `Vector2f` object containing the x and y coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the `x` and `y` coordinates of the point.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * Returns a `Vector2f` object representing the y- and z-components of a point.
	 * 
	 * @returns a `Vector2f` object containing the values of `y` and `z`.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * Returns a new `Vector2f` object representing the point (x, z).
	 * 
	 * @returns a `Vector2f` object representing the coordinates (x, z).
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * Creates a new `Vector2f` object and returns its values for `y` and `x`.
	 * 
	 * @returns a `Vector2f` object representing the coordinate values of the point (x,
	 * y).
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * Returns a `Vector2f` object containing the `z` and `y` components of a point.
	 * 
	 * @returns a `Vector2f` object representing the coordinate (z, y) of a point.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * Returns a `Vector2f` object containing the `x` and `z` coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the x and z coordinates of the point.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * Sets the values of the `x`, `y`, and `z` fields of a `Vector3f` object to the input
	 * parameters.
	 * 
	 * @param x 3D position of the vector along the x-axis.
	 * 
	 * @param y 2D position of the vector in the Y-axis.
	 * 
	 * @param z 3D position of the object along the z-axis.
	 * 
	 * @returns a reference to the modified `Vector3f` object.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Sets the position of a vector to the given coordinates.
	 * 
	 * @param r 3D vector that sets the values of the `Vector3f` object.
	 * 
	 * @returns a reference to the same `Vector3f` object, unchanged.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * Retrieves the value of the `x` field.
	 * 
	 * @returns the value of the `x` field.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the `x` field of its object to the input float value.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being passed as an argument to the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Retrieves and returns the value of the `y` field of a Java object.
	 * 
	 * @returns a float value representing the y-coordinate of a point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of a object's `y` field to the input float value.
	 * 
	 * @param y vertical component of an object's position and stores it as the field `y`.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns the value of the `z` field.
	 * 
	 * @returns a floating-point representation of the value `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the value of the field `z` to the provided float parameter.
	 * 
	 * @param z 3D position of an object in the `setZ` function.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Compares a vector with another vector by checking if its x, y and z components are
	 * equal to those of the other vector.
	 * 
	 * @param r 3D vector to be compared with the current vector.
	 * 
	 * @returns a boolean value indicating whether the vector's components are equal to
	 * those of the provided vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
