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
	 * calculates the magnitude or length of a vector by taking the square root of the
	 * sum of its x, y, and z components.
	 * 
	 * @returns the square root of the sum of the squares of the function's input values.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * computes the length of a vector by squaring its components and summing them together.
	 * 
	 * @returns a floating-point value representing the square of the length of a vector
	 * in 3D space.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * computes the maximum value of its three arguments, `x`, `y`, and `z`, and returns
	 * it as a float value.
	 * 
	 * @returns the maximum value of `x`, `y`, or `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * calculates the dot product of two vectors in 3D space, returning a scalar value
	 * representing the magnitude of the product.
	 * 
	 * @param r 3D vector that the dot product is calculated with.
	 * 
	 * @returns a scalar value representing the dot product of the input vector and the
	 * specified vector.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * computes the cross product of two vectors in a 3D space, resulting in a new vector
	 * that is perpendicular to both input vectors.
	 * 
	 * @param r 3D vector that the `cross()` method is to be applied to.
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
	 * normalizes a `Vector3f` instance by dividing its components by their corresponding
	 * lengths, resulting in a new vector with equal magnitude and direction.
	 * 
	 * @returns a normalized vector in three dimensions, with the magnitude of each
	 * component set to the same value.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * rotates a 3D vector by an angle around a specified axis, using the law of sin and
	 * cos to calculate the required components.
	 * 
	 * @param axis 3D vector that defines the rotation axis.
	 * 
	 * @param angle 3D rotation angle around the specified axis.
	 * 
	 * @returns a new vector that represents the rotated version of the original vector.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * rotates a `Vector3f` object by the angle represented by the given `Quaternion`
	 * rotation, resulting in a new `Vector3f` object representing the rotated position
	 * and orientation.
	 * 
	 * @param rotation 3D rotation to be applied to the vector.
	 * 
	 * @returns a new vector representing the rotated position of the original vector.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * computes the linear interpolation between two vectors, resulting in a new vector
	 * that is a weighted blend of the input vectors.
	 * 
	 * @param dest 3D vector to which the current vector will be interpolated.
	 * 
	 * @param lerpFactor 0.0 to 1.0 factor used for linear interpolation between the
	 * current position of the `Vector3f` object and the specified `dest` position.
	 * 
	 * @returns a vector that interpolates between two given vectors.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * adds a vector to an existing vector, returning a new vector with the sum of the
	 * two values.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector3f` object representing the sum of the input `r`.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * updates the object's position by adding the specified vector's x, y, and z components
	 * to the object's current position.
	 * 
	 * @param r 3D vector that adds its x, y, and z components to the corresponding
	 * components of the object's position, updating the object's position.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * adds a scalar value to the component values of a `Vector3f` object, returning a
	 * new `Vector3f` instance with the updated values.
	 * 
	 * @param r scalar value added to the current position of the vector.
	 * 
	 * @returns a new vector with the sum of the input value and the existing vector components.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * adds a scaled version of another vector to the current vector, scaling the other
	 * vector by the provided value before addition.
	 * 
	 * @param v 3D vector to be scaled and added to the current vector.
	 * 
	 * @param scale scalar value used to multiply the components of the `v` input parameter
	 * before adding it to the current vector.
	 * 
	 * @returns a new vector that is the result of adding the given vector scaled by the
	 * specified value to the current vector.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * multiplies a `Vector3f` object by a scalar value and adds it to the current vector
	 * component, scaling the vector internally.
	 * 
	 * @param v 3D vector that will be scaled and added to the current state of the `this`
	 * object.
	 * 
	 * @param scale 3D vector that scales the input vector by multiplication before adding
	 * it to the current state of the component.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * subtracts a vector from another, resulting in a new vector with the difference
	 * between the two inputs.
	 * 
	 * @param r 3D vector to which the current vector is being subtracted, resulting in
	 * the difference vector.
	 * 
	 * @returns a new vector with the difference between the input vector and the given
	 * reference vector.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * subtracts a vector of float `r` from a given `Vector3f`.
	 * 
	 * @param r 3D vector to subtract from the current 3D position of the object, resulting
	 * in the updated position.
	 * 
	 * @returns a vector that represents the difference between the input `r` and the
	 * values of `x`, `y`, and `z`.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * multiplies a vector `r` by the vector `this`, resulting in a new vector with the
	 * product of the corresponding components.
	 * 
	 * @param r 3D vector that multiplies with the current vector.
	 * 
	 * @returns a new `Vector3f` object containing the product of the input vector's
	 * components and the given scalar value.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * multiplies a vector's components by a scalar value, returning a new vector with
	 * the scaled components.
	 * 
	 * @param r scaling factor applied to the `Vector3f` instance being multiplied.
	 * 
	 * @returns a new `Vector3f` object with the component values multiplied by the input
	 * `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the
	 * components scaled by the reciprocals of the corresponding values of `r`.
	 * 
	 * @param r 3D vector to divide by, resulting in a new vector with the opposite
	 * direction and magnitude as the original vector.
	 * 
	 * @returns a new vector with the elements scaled by the reciprocal of the input
	 * vector's magnitude.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * takes a single float argument `r` and returns a new `Vector3f` instance with its
	 * components scaled by the reciprocal of `r`.
	 * 
	 * @param r scalar value used to divide each component of the `Vector3f` instance.
	 * 
	 * @returns a vector with each component scaled by the reciprocal of the input value.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * takes a `Vector3f` object and returns a new `Vector3f` object with the absolute
	 * value of each component.
	 * 
	 * @returns a new `Vector3f` object containing the absolute values of the input
	 * vector's components.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * returns a string representation of a given object, including its x, y, and z properties.
	 * 
	 * @returns a string representation of the object's state, including its x, y, and z
	 * components.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * returns a `Vector2f` object representing the coordinates (x, y) of a point.
	 * 
	 * @returns a `Vector2f` object representing the position of an entity in a 2D space,
	 * with `x` and `y` components.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * returns a `Vector2f` object representing the y- and z-coordinates of a point.
	 * 
	 * @returns a `Vector2f` object representing the y- and z-coordinates of a point.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * returns a `Vector2f` instance representing the point (x, z).
	 * 
	 * @returns a `Vector2f` object containing the `z` and `x` coordinates of the point.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * returns a new `Vector2f` object containing the `y` and `x` components of an arbitrary
	 * object.
	 * 
	 * @returns a `Vector2f` object representing the coordinates (x, y) of a point.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * returns a `Vector2f` object representing the position (x,y) of an entity in a 2D
	 * space.
	 * 
	 * @returns a `Vector2f` object representing the component values of z and y.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * returns a `Vector2f` object representing the coordinates x and z of a point in 2D
	 * space.
	 * 
	 * @returns a `Vector2f` object containing the x and z components of a point.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * modifies the components of a `Vector3f` object to match the input values for `x`,
	 * `y`, and `z`.
	 * 
	 * @param x 3D coordinate of the vector's x-axis.
	 * 
	 * @param y 2D position of the vector in the Y-axis, which is updated to match the
	 * provided value when the function is called.
	 * 
	 * @param z 3rd component of the vector, and by assigning a new value to it, the
	 * method updates the corresponding component of the vector object.
	 * 
	 * @returns a reference to the modified vector object.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * sets the x, y and z components of a `Vector3f` object to the corresponding values
	 * of an input `Vector3f` object.
	 * 
	 * @param r 3D vector that contains the new values for the components of the `Vector3f`
	 * object, which are then assigned to the corresponding fields of the object.
	 * 
	 * @returns a reference to the same `Vector3f` object, unchanged.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * retrieves the value of the `x` field, which represents the horizontal position of
	 * an object or element in a graphical user interface (GUI).
	 * 
	 * @returns a float value representing the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the class instance variable `x` to the input parameter `x`.
	 * 
	 * @param x float value that will be assigned to the field `x` of the object on which
	 * the `setX()` method is called.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field of the given code object.
	 * 
	 * @returns a floating-point value representing the Y coordinate of a point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the object's `y` field to the input float value.
	 * 
	 * @param y 2D position of an object in the x-y plane and assigns it to the `y` field
	 * of the `Object` class.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field of a given object.
	 * 
	 * @returns a floating-point value representing the z-coordinate of a point.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the `z` field of its object parameter to the input value.
	 * 
	 * @param z 3D coordinates of an object in space, and by calling the `setZ()` method
	 * and passing in a new value for `z`, the object's position in the z-axis is updated.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * compares the object's `x`, `y`, and `z` components with those of the provided
	 * `Vector3f` instance, returning `true` if they are equal, and `false` otherwise.
	 * 
	 * @param r 3D vector to which the current vector is being compared for equality.
	 * 
	 * @returns a boolean value indicating whether the vector's components are equal to
	 * those of the provided reference vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
