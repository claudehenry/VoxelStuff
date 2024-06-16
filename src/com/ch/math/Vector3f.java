package com.ch.math;

/**
 * Is a mathematical representation of a three-dimensional point in space. It has
 * fields for x, y, and z coordinates, as well as methods for basic arithmetic
 * operations, rotation, and scaling. The class also provides methods for computing
 * the magnitude, direction, and normalization of the vector, as well as setting and
 * getting the coordinates.
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
	 * Calculates the Euclidean distance of a 3D point from the origin, using the square
	 * root of the sum of the squared x, y, and z coordinates.
	 * 
	 * @returns the square root of the sum of the squares of the coordinates of a 3D point.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calculates the length of a point in 3D space by squaring its coordinates and summing
	 * them.
	 * 
	 * @returns a value representing the square of the length of a vector in 3D space.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * Computes and returns the maximum value among `x`, `y`, and `z`.
	 * 
	 * @returns the maximum value of `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * Computes the dot product of a `Vector3f` object and another vector, returning the
	 * result as a float value.
	 * 
	 * @param r 3D vector to be dot-producted with the `x`, `y`, and `z` components of
	 * the function's output.
	 * 
	 * @returns a scalar value representing the dot product of the input vector and the
	 * vector represented by the `x`, `y`, and `z` variables.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * Computes and returns the cross product of two vectors in 3D space, represented as
	 * `Vector3f` objects.
	 * 
	 * @param r 3D vector to be crossed with the current vector, resulting in a new 3D vector.
	 * 
	 * @returns a new vector with the cross product of the input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Normalizes a 3D vector by dividing its components by the magnitude of the vector,
	 * resulting in a unit-length vector.
	 * 
	 * @returns a normalized vector in the form of a `Vector3f`.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * Rotates a vector by an angle around a specified axis, returning the new vector.
	 * 
	 * @param axis 3D vector that defines the rotation axis.
	 * 
	 * @param angle 3D rotation angle about the specified `axis`.
	 * 
	 * @returns a rotated vector based on the given axis and angle of rotation.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * Takes a quaternion argument `rotation`, multiplies it with the current vector's
	 * position, and returns the rotated vector's position.
	 * 
	 * @param rotation 3D rotation transformation to be applied to the Vector3f object.
	 * 
	 * @returns a vector representing the rotated position of the original vector.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * Calculates a linear interpolation between two vectors, resulting in a third vector
	 * that is a weighted combination of the original inputs.
	 * 
	 * @param dest 3D vector to which the lerped value will be added.
	 * 
	 * @param lerpFactor 0.0-1.0 factor by which the current position is to be interpolated
	 * towards the destination position.
	 * 
	 * @returns a vector that interpolates between two given vectors, using a factor to
	 * control the interpolation amount.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Takes a `Vector3f` argument and returns a new `Vector3f` object with the sum of
	 * the current instance's coordinates and those of the input argument.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector3f` object representing the sum of the input vectors.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * Adds the components of a `Vector3f` object to the corresponding components of the
	 * current object.
	 * 
	 * @param r 3D vector to be added to the current position of the object, and its
	 * values are used to update the object's x, y, and z components.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * Adds a scalar value to a `Vector3f` object's components, returning a new `Vector3f`
	 * instance with the added value.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector3f` object with the sum of the original vector's components
	 * and the given float value.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * Adds a vector to the current vector, scaling the input vector by the given scalar
	 * value before concatenation.
	 * 
	 * @param v 3D vector to be scaled and added to the current vector.
	 * 
	 * @param scale scalar value by which the input vector is multiplied before being
	 * added to the current vector.
	 * 
	 * @returns a new vector that is the result of adding the provided vector multiplied
	 * by a scale factor to the current vector.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * Multiplies a `Vector3f` object by a scalar value and adds it to the current vector,
	 * scaling the current vector accordingly.
	 * 
	 * @param v 3D vector that will be scaled by the specified `scale` value before being
	 * added to the current vector.
	 * 
	 * @param scale 3D scaling factor applied to the `Vector3f` object passed as argument
	 * to the function.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * Takes a reference to another `Vector3f` object `r`, and returns a new `Vector3f`
	 * object representing the difference between the current object and `r`.
	 * 
	 * @param r 3D vector to be subtracted from the current vector, resulting in a new
	 * vector that represents the difference between the two.
	 * 
	 * @returns a new vector with the difference between the input vector and the current
	 * vector's components.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Takes a single float argument `r` and returns a new `Vector3f` object representing
	 * the difference between the original vector and the `r` value subtracted from it.
	 * 
	 * @param r 3D position to which the vector is being subtracted.
	 * 
	 * @returns a new vector representing the difference between the original vector and
	 * a given reference point.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * Takes a `Vector3f` object as input and returns a new `Vector3f` object with the
	 * dot product of the current vector and the given vector.
	 * 
	 * @param r 3D vector that multiplies with the current vector, resulting in a new 3D
	 * vector output.
	 * 
	 * @returns a vector with the product of the input vectors' coordinates.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * Multiplies a vector by a scalar value, returning a new vector with the product.
	 * 
	 * @param r scalar value that is multiplied with the vector components of the `Vector3f`
	 * object.
	 * 
	 * @returns a vector with x, y, and z components scaled by the input parameter `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * Takes a reference to another vector and returns a new vector with the components
	 * scaled by the reciprocal of the corresponding component of the input vector.
	 * 
	 * @param r 3D vector to which the current vector is to be divided.
	 * 
	 * @returns a new vector with the same components as the input vector, scaled by the
	 * reciprocal of its magnitude.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * Takes a scalar value `r` and returns a new `Vector3f` instance with components
	 * scaled by the ratio of the original vector's magnitude to `r`.
	 * 
	 * @param r scalar value used to divide the vector components.
	 * 
	 * @returns a vector with the same x, y, and z values divided by the input `r`.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * Computes and returns a vector with the absolute value of its components.
	 * 
	 * @returns a new vector with the absolute value of the input vector's components.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Returns a string representation of a mathematical expression, consisting of three
	 * variables: `x`, `y`, and `z`.
	 * 
	 * @returns a string representation of the current state of the object, including its
	 * `x`, `y`, and `z` properties.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * Returns a `Vector2f` object representing the coordinates (x, y) of some entity.
	 * 
	 * @returns a Vector2f object containing the x and y coordinates.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * Returns a `Vector2f` object representing the y-axis and z-axis coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the `y` and `z` coordinates of a point.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * Returns a `Vector2f` object representing the position (x and z) of an entity.
	 * 
	 * @returns a `Vector2f` object representing the point (x, z).
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * Returns a `Vector2f` instance representing the position (x, y) of an object.
	 * 
	 * @returns a `Vector2f` object representing the coordinates (x, y) of a point.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * Returns a `Vector2f` object representing the point (z,y) coordinates.
	 * 
	 * @returns a `Vector2f` object representing the z-coordinate and y-coordinate of a
	 * point.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * Returns a `Vector2f` object representing the x and z coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the `x` and `z` coordinates of the point.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * Sets the coordinates of a `Vector3f` instance to the input values for `x`, `y`,
	 * and `z`.
	 * 
	 * @param x 3D vector's x component, which is being set to the provided value.
	 * 
	 * @param y 2D coordinate value that is being set for the `Vector3f` instance.
	 * 
	 * @param z 3rd component of the vector, which is assigned the value provided by the
	 * user.
	 * 
	 * @returns a reference to the same `Vector3f` object, with its components set to the
	 * input values.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Sets the components of a `Vector3f` object to the corresponding values of another
	 * `Vector3f` object.
	 * 
	 * @param r 3D vector that sets the values of the `Vector3f` object.
	 * 
	 * @returns a reference to the original `Vector3f` object, unchanged.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * Returns the value of the `x` field.
	 * 
	 * @returns a floating-point representation of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of the `x` field of its object reference to the provided float value.
	 * 
	 * @param x floating-point value that sets the `x` field of the class instance being
	 * modified by the function.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of the `y` field.
	 * 
	 * @returns the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of an object's `y` field to the input `float` value.
	 * 
	 * @param y 𝑥 coordinate of an object, where the function is called to update or set
	 * that value.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Retrieves the value of the `z` field.
	 * 
	 * @returns a floating-point value representing the z component of an object's position.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Sets the value of the `z` field of the object to which it belongs.
	 * 
	 * @param z 3D coordinates of an object's position in space, and it is being assigned
	 * the value passed as argument to the function.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Compares a `Vector3f` object with another object, returning `true` if all components
	 * are equal, and `false` otherwise.
	 * 
	 * @param r 3D vector to compare with the current vector.
	 * 
	 * @returns a boolean value indicating whether the vector is equal to the provided
	 * reference vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
