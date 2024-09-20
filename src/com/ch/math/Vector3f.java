package com.ch.math;

/**
 * Represents a three-dimensional vector with float components x, y, and z, providing
 * operations for calculations such as length, dot product, cross product, normalization,
 * rotation, and arithmetic operations. It also includes methods for comparing vectors,
 * getting or setting individual component values, and converting to other data types.
 * The class is designed to support basic mathematical operations on 3D vectors.
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
	 * Calculates the Euclidean norm or magnitude of a point in three-dimensional space.
	 * It returns the square root of the sum of squares of its coordinates, effectively
	 * computing the distance from the origin to the given point. The result is returned
	 * as a floating-point value.
	 *
	 * @returns the Euclidean distance of the point from its origin. Calculated using a
	 * formula for vector magnitude. A positive float value result is expected.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calculates the square of the Euclidean distance between a point and the origin,
	 * using its Cartesian coordinates x, y, and z. It returns a float value representing
	 * the squared length. The result is obtained by summing the squares of the individual
	 * coordinates.
	 *
	 * @returns the square of the Euclidean distance from the origin.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * Returns the maximum value among three float values `x`, `y`, and `z`. It uses
	 * nested calls to `Math.max` to find the largest value by comparing each pair first,
	 * then the two results. This approach ensures correct comparison of floating-point
	 * numbers due to possible precision issues.
	 *
	 * @returns the maximum value among `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * Calculates the dot product of a given vector with the current instance, returning
	 * a scalar value representing their magnitude of alignment. The result is obtained
	 * by summing the products of corresponding components. This operation is often used
	 * for testing angle between vectors or projection calculations.
	 *
	 * @param r 3D vector being dotted with the current object, providing its components
	 * to compute the dot product.
	 *
	 * @returns a scalar value representing the dot product of two vectors.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * Computes the cross product of two 3D vectors, resulting in a new vector perpendicular
	 * to both input vectors. The operation is performed component-wise using the determinant
	 * formula for the cross product. The result is returned as a new Vector3f object.
	 *
	 * @param r 3D vector with which another 3D vector is crossed, used to compute the
	 * result of the cross product operation.
	 *
	 * @returns a new vector perpendicular to both input vectors.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * Returns a new vector with the same direction as the original but with a magnitude
	 * (length) of one. It calculates the original vector's length and divides each
	 * component by that value. This creates a unit vector in the same direction as the
	 * original.
	 *
	 * @returns a unit-length vector in the original direction.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * Calculates a point's rotation around an arbitrary axis by the given angle. It uses
	 * vector operations to compute the rotated point, incorporating cross products and
	 * dot products to accurately apply the rotation transformation. The result is a new
	 * vector representing the rotated point.
	 *
	 * @param axis 3D axis around which the rotation is performed.
	 *
	 * @param angle 3D rotation angle around the specified axis, with negative values
	 * indicating clockwise rotation and positive values counterclockwise rotation.
	 *
	 * @returns a vector representing the rotated input vector around the specified axis.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * Takes a quaternion as input, computes its conjugate and product with the current
	 * object's rotation, then returns the resulting vector as a new Vector3f instance.
	 * The result represents the axis and angle of rotation from the original position
	 * to the rotated position.
	 *
	 * @param rotation 3D rotation to be applied, and it is used to calculate a new vector
	 * by transforming the current object's position with respect to the specified rotation.
	 *
	 * @returns a vector representing the rotated position of an object.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * Interpolates between the current object and a destination vector. It calculates
	 * the weighted difference between the two vectors, multiplies it by a factor, and
	 * adds the result to the current vector, effectively moving towards the destination
	 * by that factor. The result is a new interpolated vector.
	 *
	 * @param dest 3D vector towards which interpolation is performed, serving as a target
	 * point for linearly interpolating with the current object's position.
	 *
	 * @param lerpFactor 0-1 interpolation factor controlling the amount of blending
	 * between the original object and the destination vector.
	 *
	 * @returns a new vector that interpolates between original and destination vectors.
	 * The result is a weighted average of the two input vectors.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * Adds two 3D vectors element-wise, creating a new vector by summing corresponding
	 * components from each input vector. It returns a new `Vector3f` object with the
	 * resulting coordinates. The original vectors remain unchanged.
	 *
	 * @param r 3D vector to be added to the instance's current position, with its
	 * components used to update the coordinates of the returned Vector3f object.
	 *
	 * @returns a new vector with components equal to the sum of the input vectors.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * Increments the vector's components by corresponding values from a provided Vector3f
	 * instance, effectively adding the two vectors element-wise. This results in updating
	 * the current vector with the sum of its original components and those of the input
	 * vector. The updated vector is stored within itself.
	 *
	 * @param r 3D vector to be added to the current position of the object, represented
	 * by the `x`, `y`, and `z` fields.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * Creates a new vector by adding a specified float value to each component of the
	 * current vector. The original vector is not modified; instead, a new instance with
	 * incremented components is returned.
	 *
	 * @param r 3D vector to be added component-wise to each element of the current vector.
	 *
	 * @returns a new `Vector3f` object with updated values based on addition.
	 * Resulting vector's components are x + r, y + r, and z + r.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * Returns a vector that is the sum of the current vector and a scaled version of the
	 * input vector `v`. The scaling factor is specified by the argument `scale`, which
	 * multiplies each component of `v`. The result is equivalent to adding the unscaled
	 * vector `v` multiplied by `scale`.
	 *
	 * @param v 3D vector to be scaled by the factor specified in the `scale` input
	 * parameter before being added to the current object's position.
	 *
	 * @param scale scalar value by which the vector `v` is multiplied before being added
	 * to `this`.
	 *
	 * @returns a new vector resulting from scaling and adding to the original.
	 * It scales the input vector `v` by `scale`, then adds it to the current object's position.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * Multiplies a given vector by a specified scale factor and then adds the result to
	 * the object's current position or state. The scaled vector is created through
	 * multiplication with a scalar value, followed by addition to the current state via
	 * `addSelf`.
	 *
	 * @param v 3D vector to be scaled and added to the object itself.
	 *
	 * @param scale scalar factor by which to multiply the vector `v`.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * Subtracts a given Vector3f object from another. It calculates the difference
	 * component-wise and returns a new Vector3f object representing the result. The
	 * components x, y, and z are updated by subtracting corresponding values of the input
	 * vector.
	 *
	 * @param r 3D vector to be subtracted from the current object's position, specified
	 * by its x, y, and z coordinates.
	 *
	 * It performs element-wise subtraction of the current object's components from `r`'s
	 * components.
	 *
	 * The result is a new vector with components that are the differences between
	 * corresponding components of the two input vectors.
	 *
	 * @returns a new vector with components representing the difference of corresponding
	 * components.
	 * This new vector's components are the result of subtracting the input vector's
	 * components from the current object's components.
	 * It represents a displacement or a change in position.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * Creates a new vector by subtracting a scalar value from each component of an
	 * existing 3D vector. It takes a single float parameter `r` and returns a new
	 * `Vector3f` instance with components x-r, y-r, z-r. The original vector remains unchanged.
	 *
	 * @param r 3D space position offset from which the current position is subtracted
	 * to calculate a new Vector3f instance.
	 *
	 * @returns a new Vector3f instance with each component reduced by the specified float
	 * value. The resulting vector has been scaled by subtracting the input value from
	 * its components.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * Multiplies two vector objects, resulting in a new vector with components obtained
	 * by multiplying corresponding components of the input vectors and returns it. The
	 * result is a scalar multiplication of each component of one vector by the corresponding
	 * component of another vector. Components are stored in a new Vector3f object.
	 *
	 * @param r 3D vector being multiplied by the current vector, allowing element-wise
	 * multiplication of their corresponding components.
	 *
	 * @returns a vector resulting from element-wise multiplication of input vectors.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * Scales a vector by a scalar value, multiplying each component of the vector with
	 * the given factor and returning a new scaled vector. It performs element-wise
	 * multiplication between the vector components and the scalar. A new instance of
	 * Vector3f is created to store the result.
	 *
	 * @param r scalar value by which each component of the vector is multiplied to produce
	 * a new scaled vector.
	 *
	 * @returns a new `Vector3f` with components scaled by the input scalar `r`.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * Performs element-wise division between two instances of a three-dimensional vector
	 * class, returning a new instance with each component divided by its corresponding
	 * counterpart in the input vector. It returns a new Vector3f object resulting from
	 * this operation. The original vectors remain unchanged.
	 *
	 * @param r 3D vector to be divided by, used for scalar division operations on the
	 * current vector's x, y, and z components.
	 *
	 * @returns a new vector with each component divided by the corresponding component
	 * of the input vector. The resulting components are floats. Division is element-wise.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * Performs element-wise division of a vector's components by a scalar value. It
	 * returns a new vector with each component divided by the specified float value. The
	 * original vector is not modified, and the result is returned as a new Vector3f object.
	 *
	 * @param r 3D distance or magnitude whose reciprocal is used to normalize each
	 * component of the vector, effectively scaling it by its inverse.
	 *
	 * @returns a new vector with components divided by input scalar value.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * Returns a new Vector3f instance with absolute values of its components. It takes
	 * no arguments and applies the `Math.abs` method to each component (x, y, z) of the
	 * vector. The original vector remains unchanged.
	 *
	 * @returns a new `Vector3f` object with absolute values of x, y, and z.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * Returns a string representation of an object, formatted as a triplet (x, y, z).
	 * The function concatenates three parts using addition operators to form a single
	 * string containing the values of x, y, and z separated by spaces.
	 *
	 * @returns a formatted string representing coordinates (x, y, z).
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * Creates a new instance of a `Vector2f` object with its x and y components set to
	 * the current values of the class's internal x and y members. The function returns
	 * this newly created vector. It does not modify the original state of the class.
	 *
	 * @returns a new `Vector2f` object containing the current values of x and y.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * Creates a new instance of Vector2f with its x-component set to zero and its y- and
	 * z-components set to the current values of the object's y and z components,
	 * respectively. It then returns this new Vector2f instance. This allows access to
	 * only the y and z coordinates.
	 *
	 * @returns a two-dimensional vector with y and z coordinates.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * Creates and returns a new instance of `Vector2f` with its x component set to the
	 * current value of y and its z component set to the current value of x, based on
	 * member variables not shown. The original values remain unchanged.
	 *
	 * @returns a new Vector2f object with coordinates (z, x).
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * Reverses the order of the coordinates in a vector from (x, y) to (y, x). It creates
	 * and returns a new vector with these reversed coordinates. The original vector's
	 * values remain unchanged.
	 *
	 * @returns a new Vector2f instance with y and x coordinates swapped.
	 * A copy of the object's vector is created with its elements transposed.
	 * Coordinates are reordered from (x, y) to (y, x).
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * Returns a new instance of Vector2f with z and y coordinates swapped. The returned
	 * vector is independent from the original. It does not modify the original values
	 * but creates a copy.
	 *
	 * @returns a vector with x-component 'z' and y-component 'y'.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * Returns a two-dimensional vector containing the x and z components of its instance's
	 * position. It instantiates a new Vector2f object with these values. This allows for
	 * selective access to specific dimensions of the original vector.
	 *
	 * @returns a Vector2f object with x and z coordinates of the current vector.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * Initializes a vector with new values and returns the updated instance for method
	 * chaining. It takes three float parameters representing x, y, and z coordinates to
	 * be assigned to the corresponding properties of the vector object. The function
	 * modifies existing state without creating a new instance.
	 *
	 * @param x 3D coordinate value to be assigned to the vector's X-axis component.
	 *
	 * @param y 2D coordinate value that is assigned to the `y` component of the object's
	 * position vector.
	 *
	 * @param z 3D coordinate's vertical axis, which is set to the specified value as
	 * part of the vector's initialization.
	 *
	 * @returns a reference to the instance itself.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Accepts a `Vector3f` object and assigns its x, y, and z components to the current
	 * vector's corresponding components using another overloaded `set` method. The
	 * original vector is returned for chaining operations. This facilitates fluent
	 * interface programming by allowing multiple method calls in succession.
	 *
	 * @param r 3D vector to be copied into this object's coordinates, allowing for fluent
	 * chaining of method calls.
	 *
	 * @returns a reference to itself, with its internal state updated.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * Returns the current value of a variable named `x`, which is expected to be a
	 * floating-point number, allowing access and potential modification by other parts
	 * of the program. This getter function provides read-only access to the private
	 * member variable `x`. The returned value is a float.
	 *
	 * @returns a floating-point number, which is the value of the variable `x`.
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets the value of a private float variable named `x` to a specified value, effectively
	 * updating its state by assigning the new input value directly to it. It has no error
	 * checking or validation mechanism. The assignment is immediate and unconditional.
	 *
	 * @param x 3D coordinate of an object along the x-axis that is being assigned to the
	 * object's internal state variable `this.x`.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the value of the variable `y`. The function is a getter method, providing
	 * access to the private field `y`, allowing other parts of the program to retrieve
	 * its current value. The return type is a floating-point number.
	 *
	 * @returns a floating-point value representing an object's position or coordinate
	 * on the y-axis.
	 * It corresponds to the private variable `y`. The method simply returns this internal
	 * state.
	 */
	public float getY() {
		return y;
	}

	/**
	 * Updates an instance variable `y` with a new value provided as a parameter. This
	 * value is assigned directly to the instance variable without any validation or
	 * processing. The change takes effect immediately after calling the method, allowing
	 * other parts of the program to access the updated value.
	 *
	 * @param y y-coordinate value to be assigned to an instance variable or field,
	 * updating its current state.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns a floating-point value representing the state or attribute named `z`. The
	 * returned value is read-only, as it directly accesses the field `z` without
	 * modification. It provides access to the z-coordinate of an object or point.
	 *
	 * @returns a floating-point number representing the value of variable `z`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * Updates the value of a floating-point field named `z` with the provided input `z`.
	 * It takes a single parameter, which is assigned to the instance variable without
	 * any validation or transformation. The updated value becomes available for subsequent
	 * use within the class.
	 *
	 * @param z 3D coordinate to be assigned to an instance variable of the class,
	 * effectively setting its value.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * Compares two Vector3f objects for equality by checking if their corresponding
	 * components (x, y, z) are identical. The comparison is made using the `==` operator,
	 * which checks for object reference equality rather than component-wise equality.
	 *
	 * @param r 3D vector being compared for equality to an instance of itself.
	 *
	 * @returns a boolean value indicating object equality.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
