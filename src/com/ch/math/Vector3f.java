package com.ch.math;

/**
 * Represents a 3D vector with floating-point components x, y, and z. It supports
 * various mathematical operations such as addition, subtraction, multiplication,
 * division, and others to manipulate vectors. Additionally, it provides methods for
 * rotation, normalization, and other vector calculations.
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
	 * Calculates the Euclidean distance of a point from its origin, using its coordinates.
	 * It returns the square root of the sum of squares of its components. The result is
	 * cast to a float for precision.
	 *
	 * @returns a non-negative floating-point value representing the Euclidean distance.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calculates the squared magnitude of a 3D vector, represented by components `x`,
	 * `y`, and `z`. The result is returned as a floating-point value. This is equivalent
	 * to calculating the Euclidean distance from the origin to the vector's endpoint,
	 * but squared instead of taking the square root.
	 *
	 * @returns the sum of squares of its coordinates, as a float value.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * Returns the maximum value among three variables x, y, and z. It uses a nested call
	 * to the Math.max method to compare the values. The result is returned as a float.
	 *
	 * @returns the maximum value among variables x, y, and z.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * Calculates the dot product of two vectors, one represented by the instance's
	 * components and the other passed as an argument. The result is a scalar value
	 * indicating the magnitude of their similarity or angular relationship. The calculation
	 * uses component-wise multiplication.
	 *
	 * @param r 3D vector with which the current vector's components are multiplied to
	 * compute the dot product.
	 *
	 * @returns a scalar value, specifically a floating-point number representing dot
	 * product result.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * Calculates the cross product of two 3D vectors, returning a new vector whose
	 * components are computed by the formula:
	 * - x = y*r.z - z*r.y
	 * - y = z*r.x - x*r.z
	 * - z = x*r.y - y*r.x
	 *
	 * @param r 3D vector with which to calculate the cross product, used to compute the
	 * perpendicular vector result.
	 *
	 * @returns a perpendicular vector to the input `Vector3f` and argument `r`.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Normalizes a 3D vector by dividing its components by their magnitude, creating a
	 * unit vector with a length of 1. It returns a new normalized Vector3f object based
	 * on the original vector's components. The original vector remains unchanged.
	 *
	 * @returns a unit vector with the same direction as the original vector.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * Computes a rotation transformation based on a given axis and angle, applying
	 * Rodrigues' formula to obtain the resulting vector after rotation around the specified
	 * axis by the given angle. The result is a new Vector3f instance representing the
	 * rotated vector. Rotation is counter-clockwise.
	 *
	 * @param axis 3D axis of rotation, around which the object is rotated by an angle
	 * specified by the `angle` parameter.
	 *
	 * @param angle 3D rotational angle around the specified axis, where its sign determines
	 * whether rotation is clockwise or counter-clockwise.
	 *
	 * @returns a rotated Vector3f.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * Performs a quaternion-based rotation on a vector, applying the given rotation to
	 * the current vector and returning the resulting rotated vector as a new Vector3f
	 * instance. The rotation is effectively applied by multiplying the vector with the
	 * quaternion and its conjugate.
	 *
	 * @param rotation 3D rotation applied to an object, used to compute a new position
	 * vector after transformation.
	 *
	 * @returns a vector representing the axis of the rotation.
	 * The magnitude of the output vector represents the angle of rotation, and its
	 * direction the axis of rotation.
	 * The result is a normalized vector.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * Calculates a linear interpolation between two vectors, combining the current vector
	 * with another target vector based on a specified factor. It uses vector subtraction
	 * and addition to compute a new result vector that is proportionally closer to the
	 * target vector by the given factor. The result is scaled.
	 *
	 * @param dest 3D vector towards which another 3D vector is interpolated from its
	 * current position by a specified factor.
	 *
	 * @param lerpFactor 0 to 1 value that determines the proportion of the destination
	 * vector's influence on the resulting interpolated vector.
	 *
	 * @returns a vector interpolated between original and destination vectors.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Adds two vectors together by summing their corresponding components and returns a
	 * new vector with the resulting values. The original vectors are not modified, as a
	 * new instance is created to hold the result. This allows for element-wise addition
	 * of vector components.
	 *
	 * @param r 3D vector to be added to the current vector, its values are used for
	 * component-wise addition.
	 *
	 * @returns a new vector resulting from element-wise addition of two vectors.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * Adds the components of a given Vector3f to the current vector's components,
	 * incrementing x, y, and z values accordingly. The result is an updated version of
	 * the original vector. This operation combines two vectors element-wise.
	 *
	 * @param r 3D vector to be added to the current object's position, updating its x,
	 * y, and z coordinates accordingly.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * Creates a new vector with each component incremented by the specified value. It
	 * takes a float parameter representing the amount to add and returns a new Vector3f
	 * object containing the updated values. The original vector's components remain unchanged.
	 *
	 * @param r value to be added to each component of the vector.
	 *
	 * @returns a new vector with each component incremented by `r`.
	 * New vector components are x + r, y + r, and z + r.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * Adds a scaled version of vector `v` to itself. The scaling factor is `scale`. The
	 * operation effectively multiplies each component of `v` by `scale`, then adds the
	 * result to the original vector's components. A new instance is returned with the
	 * resulting values.
	 *
	 * @param v 3D vector to be scaled and added to the current object.
	 *
	 * @param scale scalar value by which the vector `v` is multiplied before being added
	 * to the current object using the `add` method.
	 *
	 * @returns a vector with scaled components added to the original vector's components.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * Adds a scaled version of the input vector to its own components, effectively scaling
	 * and adding the vector to itself without modifying the original input vector. Scaling
	 * is achieved by multiplying the input vector with the specified scale factor. The
	 * result is then added to this object's components.
	 *
	 * @param v 3D vector to be added scaled by the specified factor to the current
	 * object's position.
	 *
	 * @param scale scalar value by which each component of the vector `v` is multiplied
	 * before being added to the current object's transformation matrix.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * Performs vector subtraction, creating a new vector by subtracting corresponding
	 * components from another vector passed as an argument. It returns a new Vector3f
	 * object with the result. The original vectors remain unchanged.
	 *
	 * @param r 3D vector being subtracted from the current object's position, represented
	 * by the coordinates x, y, and z.
	 *
	 * @returns a new vector representing the difference between the current and input vectors.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Creates a new vector by subtracting a specified scalar value from each component
	 * (x, y, z) of the current vector, resulting in a new vector with modified components.
	 * The original vector remains unchanged. A new Vector3f object is returned with the
	 * updated values.
	 *
	 * @param r 3D vector's components are subtracted by its value.
	 *
	 * @returns a new Vector3f object with components decreased by the input value.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * Multiplies two 3D vector objects by their corresponding components, returning a
	 * new 3D vector object with the scaled values. The original vectors remain unchanged.
	 * This is an element-wise multiplication operation, applying scalar multiplication
	 * to each component of the input vector.
	 *
	 * @param r 3D vector with which the current object's coordinates are multiplied
	 * element-wise to produce a new Vector3f instance.
	 *
	 * @returns a new `Vector3f` object representing the element-wise product of two input
	 * vectors.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * Scales a vector by a scalar value, returning a new vector with each component
	 * multiplied by the scalar. The original vector remains unchanged. A copy of the
	 * scaled vector is returned as a new object.
	 *
	 * @param r scalar multiplier that scales each component of the vector by multiplying
	 * it with the corresponding components `x`, `y`, and `z`.
	 *
	 * @returns a new Vector3f instance scaled by the input float value.
	 * The x, y, and z components of the output vector are multiplied by the input scalar.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * Performs element-wise division of a vector by another, returning a new vector with
	 * each component divided by the corresponding component of the divisor. The result
	 * is scaled to preserve magnitude and orientation. Division is performed component-wise.
	 *
	 * @param r 3D vector being divided by the original vector to calculate the result
	 * of the division operation.
	 *
	 * @returns a new vector resulting from element-wise division of the current vector.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * Performs element-wise division of a vector's components by a scalar value `r`. It
	 * creates and returns a new `Vector3f` instance with each component divided by `r`.
	 * This operation is equivalent to dividing each dimension of the original vector by
	 * the specified ratio.
	 *
	 * @param r scalar value by which each component of the returned vector is divided.
	 *
	 * @returns a new vector divided by the specified scalar value.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * Returns a new vector with component-wise absolute values, creating a copy of the
	 * original vector but with all negative components replaced by their positive
	 * counterparts. The original vector remains unchanged. The function is used to extract
	 * the magnitude of each component from a vector.
	 *
	 * @returns a new vector with absolute values of its components.
	 * Values are non-negative, regardless of input sign.
	 * Resulting Vector3f has same type as original vector.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Returns a string representation of an object, formatting its coordinates (x, y,
	 * and z) into a standardized format enclosed in parentheses. It concatenates the
	 * numerical values of x, y, and z with spaces between them to form a human-readable
	 * output. The result is a formatted string.
	 *
	 * @returns a string representation of three coordinates in parentheses.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * Creates and returns a new instance of a two-dimensional vector, populated with
	 * values from `x` and `y`. The returned object is a separate entity from its components.
	 * The original state of `x` and `y` remains unchanged.
	 *
	 * @returns a new `Vector2f` object with x and y values from instance variables.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * Returns a new `Vector2f` object containing the y and z components of the parent
	 * vector. The original components are not modified; instead, a copy is created with
	 * the specified values. A new instance of `Vector2f` is returned each time the
	 * function is called.
	 *
	 * @returns a 2D vector with y and z coordinates from the parent object.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * Returns a new instance of `Vector2f` containing the z and x coordinates in that
	 * order. The original object's state remains unchanged. This creates a copy of
	 * specific data within the object.
	 *
	 * @returns a new Vector2f instance with y-component z and x-component x.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * Returns a new Vector2f instance with y and x coordinates swapped from the original
	 * vector. The returned vector contains the original y coordinate as its x component
	 * and vice versa. This effectively reverses the axis ordering of the input vector.
	 *
	 * @returns a Vector2f object with y-coordinate first and then x-coordinate.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * Creates and returns a new instance of Vector2f with x-coordinate set to the current
	 * z-coordinate and y-coordinate set to the current y-coordinate. The original Vector2f
	 * object remains unchanged. This function provides a way to access or retrieve
	 * specific coordinates of a vector in reverse order.
	 *
	 * @returns a new `Vector2f` instance with coordinates `z` and `y`.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * Returns a new instance of `Vector2f` with x and z coordinates from the current
	 * object's attributes x and z, respectively. The original object remains unchanged.
	 * A copy of specific vector components is created and returned.
	 *
	 * @returns a Vector2f object with x and z coordinates.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * Assigns new values to its three constituent components: x, y, and z. It allows for
	 * the modification of a vector's properties via three float parameters. The updated
	 * vector is then returned, enabling method chaining.
	 *
	 * @param x 3D coordinate along the X-axis that is assigned to the object's position.
	 *
	 * @param y 2D coordinate along the y-axis of a 3D point or vector, which is assigned
	 * to the instance variable `y` within the class.
	 *
	 * @param z 3D coordinate's Z-axis value, setting it to the specified float value
	 * within the Vector3f object.
	 *
	 * @returns an instance of Vector3f with updated coordinates.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Assigns a vector's X, Y, and Z components to the calling object using another
	 * vector's component values. This is an overloaded method that accepts either three
	 * float parameters or a Vector3f object. The original vector remains unchanged.
	 *
	 * @param r 3D vector whose values are to be assigned to the current object's properties,
	 * allowing for chaining of method calls.
	 *
	 * @returns a reference to the modified Vector3f object.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * Returns a floating-point value representing the current state of variable `x`. The
	 * result is directly obtained from an existing instance variable or field named `x`.
	 * This method provides read-only access to the object's internal data.
	 *
	 * @returns a float value representing the current state of variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Assigns a new value to the instance variable `x`. It takes one parameter, a
	 * floating-point number, and sets its value within the class context. The change is
	 * made directly on the object's state.
	 *
	 * @param x new value to be assigned to the instance variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of a variable named `y`, which is presumably a member field of
	 * a class. It provides read-only access to the `y` attribute, allowing it to be
	 * retrieved but not modified.
	 *
	 * @returns a float value representing the current Y coordinate.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates the value of a floating-point variable named `y`. It assigns a new value
	 * to the variable, replacing its previous value with the specified `y` parameter.
	 * The change is reflected in the object's state.
	 *
	 * @param y 2D coordinate to be assigned to the object, updating its position vertically.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns a floating-point value representing the z-coordinate. The z-coordinate is
	 * accessed and returned directly without any modification or calculation. This
	 * read-only accessor method provides programmatic access to the internal state of
	 * an object.
	 *
	 * @returns a floating-point number representing the value of variable `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Assigns a specified float value to the instance variable `z`. It updates the `z`
	 * field with the provided input value, replacing any existing value. The change is
	 * persisted until overwritten or reset by another assignment.
	 *
	 * @param z 3D coordinate to be assigned to the object's `z` property, allowing it
	 * to be updated with a new value.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Compares two `Vector3f` instances based on their corresponding components (x, y,
	 * and z). The comparison is done using the equality operator (`==`) to check if each
	 * component of one vector is equal to the corresponding component of the other.
	 *
	 * @param r 3D vector being compared for equality with the object's internal state.
	 *
	 * @returns a boolean indicating whether two vectors are identical.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
