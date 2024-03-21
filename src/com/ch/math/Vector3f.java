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
	 * calculates the magnitude or length of a 3D vector by taking the square root of the
	 * sum of the squares of its coordinates.
	 * 
	 * @returns the square root of the sum of the squares of the input coordinates.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * calculates the length of a point in three-dimensional space by squaring its
	 * coordinates and summing them.
	 * 
	 * @returns a floating-point value representing the square of the length of a vector
	 * in three dimensions.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * calculates the maximum value of its three arguments: `x`, `y`, and `z`. It returns
	 * the maximum value as a float.
	 * 
	 * @returns the maximum of `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * takes a `Vector3f` object `r` as input and computes the dot product of the vector's
	 * components with those of a given vector `x`, `y`, and `z`. The result is a scalar
	 * value representing the amount of overlap between the two vectors.
	 * 
	 * @param r 3D vector that is dot-producted with the object's components to produce
	 * the output value.
	 * 
	 * @returns a floating-point number representing the dot product of the input vector
	 * and the given vector.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * takes a second vector `r` as input and returns the vector resulting from cross-product
	 * between the two vectors.
	 * 
	 * @param r 2D vector to cross with the current 3D vector.
	 * 
	 * @returns a vector with magnitude and direction perpendicular to the input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * normalizes a given vector by dividing its components by the vector's magnitude,
	 * resulting in a unit-length vector.
	 * 
	 * @returns a normalized version of the input vector.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * rotates a `Vector3f` instance by an angle around a given axis, based on the dot
	 * product of the input vector and the axis.
	 * 
	 * @param axis 3D vector that defines the rotation axis around which the rotation
	 * will occur.
	 * 
	 * @param angle 3D rotation angle around the specified `axis`.
	 * 
	 * @returns a rotated vector based on the provided axis and angle of rotation.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * takes a rotation quaternion as input and returns the rotated vector's x, y, and z
	 * components.
	 * 
	 * @param rotation 3D rotation to be applied to the Vector3f object.
	 * 
	 * @returns a new `Vector3f` instance representing the rotated position of the original
	 * vector.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * computes a vector interpolation between two input vectors, based on a given factor.
	 * The result is the weighted sum of the two inputs, with the weight being the factor.
	 * 
	 * @param dest 3D vector that the current vector is being interpolated towards.
	 * 
	 * @param lerpFactor 0.0 to 1.0 value that determines how much the `dest` vector
	 * should be blended with the current vector to produce the resulting vector.
	 * 
	 * @returns a vector that interpolates between two given vectors using a lerp factor.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a `Vector3f` object `r` as input and returns a new `Vector3f` object with
	 * the sum of the values of the objects x, y, and z.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * @returns a new vector with the sum of the inputs' coordinates.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * adds the components of a `Vector3f` object to the corresponding components of the
	 * current object.
	 * 
	 * @param r 3D vector that adds to the corresponding components of the `this` object.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * adds a scalar value `r` to each component of its argument, returning a new `Vector3f`
	 * instance with the resulting values.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * @returns a new `Vector3f` instance with the sum of the original values and the
	 * provided scalar value added to each component.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * multiplies a vector by a scalar value and returns the result, adding it to the
	 * current vector.
	 * 
	 * @param v 3D vector that is to be added to the current vector, scaled by the given
	 * float value.
	 * 
	 * @param scale scalar value that is multiplied with the input vector `v` before
	 * adding it to the current vector.
	 * 
	 * @returns a new vector that is the result of adding the provided vector scaled by
	 * the given scalar multiplier to the original vector.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * adds a vector to the current position of an object, scaling the vector by a specified
	 * factor before adding it.
	 * 
	 * @param v 3D vector to be scaled.
	 * 
	 * @param scale 3D scaling factor applied to the provided `Vector3f` object when added
	 * to the current state of the `this` object.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * takes a reference to a `Vector3f` object `r` and returns a new `Vector3f` object
	 * with the difference between the values of `x`, `y`, and `z` of the input `r` and
	 * the current object.
	 * 
	 * @param r 3D vector to which the `x`, `y`, and `z` components of the returned vector
	 * will be subtracted.
	 * 
	 * @returns a new `Vector3f` object representing the difference between the input
	 * vector and the reference vector.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a single float argument `r` and returns a new `Vector3f` instance with
	 * components calculated as `x - r`, `y - r`, and `z - r`.
	 * 
	 * @param r 3D vector of the difference between the original position and the new
	 * position of the subtraction operation.
	 * 
	 * @returns a vector with the difference between the input `r` and each component of
	 * the original vector.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * multiplies two `Vector3f` objects, returning a new vector with the product of their
	 * x, y, and z components.
	 * 
	 * @param r 3D vector that multiplies with the current vector, resulting in a new 3D
	 * vector output.
	 * 
	 * @returns a new vector instance with the product of the input vectors' components.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * multiplies each component of a `Vector3f` object by a given scalar value, returning
	 * a new `Vector3f` object with the scaled components.
	 * 
	 * @param r scalar value that is multiplied with each component of the `Vector3f` object.
	 * 
	 * @returns a vector with the product of the input scalar `r` and the components of
	 * the input vector.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * divides the input vector by a specified reference vector, returning a new vector
	 * with the resulting scaled values.
	 * 
	 * @param r 3D vector to divide by.
	 * 
	 * @returns a new `Vector3f` object with the given scalars divided from the input vector.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * takes a scalar value `r` and returns a `Vector3f` instance with each component
	 * divided by `r`.
	 * 
	 * @param r scalar value used to perform the division of the vector components.
	 * 
	 * @returns a vector with the x, y, and z components scaled by the input parameter `r`.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * returns a new `Vector3f` instance with the absolute values of its input components.
	 * 
	 * @returns a new `Vector3f` instance representing the absolute value of the input vector.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * generates a string representation of an object by concatenating its three component
	 * fields (`x`, `y`, and `z`).
	 * 
	 * @returns a string representation of a point in 3D space, consisting of three values
	 * separated by spaces.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * returns a `Vector2f` object containing the `x` and `y` coordinates of an entity.
	 * 
	 * @returns a `Vector2f` object containing the x and y coordinates of the point.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * returns a `Vector2f` object containing the `y` and `z` components of a given vector.
	 * 
	 * @returns a `Vector2f` object containing the values of `y` and `z`.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * creates a new `Vector2f` instance representing the point (x, z).
	 * 
	 * @returns a vector representing the coordinate (x, z) of a point in 2D space.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * returns a `Vector2f` object representing the coordinates (x, y) of a point.
	 * 
	 * @returns a `Vector2f` object containing the values of `y` and `x`.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * returns a `Vector2f` object containing the values of `z` and `y`.
	 * 
	 * @returns a `Vector2f` object representing the point (z, y) in the coordinate system.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * sets the x, y and z components of a `Vector3f` object to the input values.
	 * 
	 * @param x 3D coordinate value for the x-axis of the vector.
	 * 
	 * @param y 2D position of the vector in the Y-axis direction, which is updated to
	 * match the input value.
	 * 
	 * @param z 3rd component of the `Vector3f` object, and by assigning a value to it,
	 * the function updates the object's 3rd component.
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
	 * sets the components of the object to those of the input vector, `r`.
	 * 
	 * @param r 3D vector that contains the new values to be set for the `x`, `y`, and
	 * `z` components of the `Vector3f` object.
	 * 
	 * @returns a reference to the original `Vector3f` object, unchanged.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * returns the value of the `x` field.
	 * 
	 * @returns a floating-point number representing the value of `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the object's `x` field to the input float value passed as argument.
	 * 
	 * @param x value that sets the instance field `x` of the class to which the `setX`
	 * method belongs.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * retrieves the value of the `y` field of a given object.
	 * 
	 * @returns the value of the `y` field.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of the field `y` of the object to which it belongs.
	 * 
	 * @param y 2D coordinate of a point in the graph, and by calling the `setY()` method,
	 * it assigns the value of the input parameter to the field `y` of the `Graph` object.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * retrieves the value of the `z` field, which is a float variable containing the
	 * z-coordinate of an object or point.
	 * 
	 * @returns the value of the `z` field.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the instance field `z` to the argument passed as a float parameter.
	 * 
	 * @param z 3D coordinate's third component, setting its value to the provided float
	 * value for the specified object.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * compares two `Vector3f` objects based on their `x`, `y`, and `z` components,
	 * returning `true` if they are identical and `false` otherwise.
	 * 
	 * @param r 3D vector to be compared with the current vector, and is used to determine
	 * equality.
	 * 
	 * @returns a boolean value indicating whether the input vector is equal to the current
	 * vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
