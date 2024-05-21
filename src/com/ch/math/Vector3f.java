package com.ch.math;

/**
 * from the file provides various methods for manipulating and querying 3D vectors.
 * Methods include length, square length, max, dot product, cross product, normalization,
 * rotation, lerp, addition, subtraction, multiplication, division, absolute value,
 * and string conversion. Additionally, the class provides getters and setters for
 * the x, y, and z components of the vector.
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
	 * calculates the Euclidean distance of a 3D point from the origin using the squared
	 * magnitude of the coordinates and the square root operation.
	 * 
	 * @returns the square root of the sum of the squares of the coordinates of a 3D point.
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * computes the length of a point in 3D space by squaring its coordinates and summing
	 * them.
	 * 
	 * @returns a floating-point representation of the square of the length of the object
	 * in 3D space.
	 */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

	/**
	 * computes the maximum value of three arguments: `x`, `y`, and `z`. It returns the
	 * maximum value computed using `Math.max()` method.
	 * 
	 * @returns the maximum of the input values `x`, `y`, and `z`.
	 */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

	/**
	 * calculates the dot product of a `Vector3f` object and another vector, returning
	 * the result as a float value.
	 * 
	 * @param r 3D vector to be dot-producted with the current vector.
	 * 
	 * 	- `x`, `y`, and `z` are the components of the vector object `r`.
	 * 	- The `getX()`, `getY()`, and `getZ()` methods retrieve the values of these components.
	 * 
	 * @returns a scalar value representing the dot product of the vector `r` and the
	 * object being processed.
	 */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

	/**
	 * computes the cross product of two vectors in 3D space, returning a new vector with
	 * components in the order X, Y, Z.
	 * 
	 * @param r 3D vector to which the current vector is being crossed, and its values
	 * are used to calculate the cross product.
	 * 
	 * 	- `r` is a `Vector3f` object representing a 3D vector.
	 * 	- It has three components: `x`, `y`, and `z`.
	 * 
	 * @returns a vector with the cross product of two input vectors.
	 * 
	 * The output is a new `Vector3f` object containing the cross product of the input vectors.
	 * 
	 * 	- The x-component of the output (x_) is equal to the product of the y-component
	 * of the first input vector and the z-component of the second input vector, minus
	 * the product of the z-component of the first input vector and the y-component of
	 * the second input vector.
	 * 	- The y-component of the output (y_) is equal to the product of the z-component
	 * of the first input vector and the x-component of the second input vector, minus
	 * the product of the x-component of the first input vector and the z-component of
	 * the second input vector.
	 * 	- The z-component of the output (z_) is equal to the product of the x-component
	 * of the first input vector and the y-component of the second input vector, minus
	 * the product of the y-component of the first input vector and the x-component of
	 * the second input vector.
	 */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

	/**
	 * normalizes a 3D vector by dividing each component by the vector's magnitude,
	 * returning a new vector with the same direction but with all components scaled to
	 * have the same value.
	 * 
	 * @returns a vector with the same x, y, and z components, but scaled by the length
	 * of the original vector.
	 * 
	 * The output is a `Vector3f` object representing a normalized version of the original
	 * input vector.
	 * 
	 * The value of each element in the output is calculated by dividing the corresponding
	 * element of the input vector by its magnitude (length).
	 * 
	 * The resulting output vector has a length of 1, indicating that it is a unit vector.
	 * 
	 * The output vector retains the same direction as the original input vector,
	 * representing the normalized version of the input in the same coordinate system.
	 */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

	/**
	 * rotates a `Vector3f` instance by an angle around a specified axis, returning a new
	 * `Vector3f` instance with the rotated coordinates.
	 * 
	 * @param axis 3D rotational axis around which the vector is rotated.
	 * 
	 * The `axis` variable is a 3D vector representing the axis of rotation. It has three
	 * components: `x`, `y`, and `z`.
	 * 
	 * @param angle 3D rotation angle of the object around the specified `axis`.
	 * 
	 * @returns a rotated version of the original vector, based on the given axis and
	 * angle of rotation.
	 * 
	 * The output is a `Vector3f` object representing the rotated version of the original
	 * vector.
	 * 
	 * 	- The `x`, `y`, and `z` components of the output vector are calculated using the
	 * given axis and angle parameters. Specifically, the `x` component is equal to the
	 * product of the original vector's `x` component, the sine of the angle, and the
	 * cosine of the angle; the `y` component is equal to the product of the original
	 * vector's `y` component, the sine of the angle, and the cosine of the angle; and
	 * the `z` component is equal to the product of the original vector's `z` component,
	 * the sine of the angle, and the cosine of the angle.
	 * 	- The resulting output vector has the same magnitude as the original vector, but
	 * its direction has been rotated by the specified angle around the given axis.
	 */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

	/**
	 * takes a `Quaternion` object as input and returns a `Vector3f` object representing
	 * the rotated position. It uses the quaternion multiplication to perform the rotation
	 * and returns the result in a new `Vector3f` object.
	 * 
	 * @param rotation 3D rotation to apply to the vector.
	 * 
	 * 	- The `Quaternion` object `rotation` represents a 4D rotational transformation
	 * matrix that can be used to rotate a 3D vector.
	 * 	- The `conjugate` property returns the conjugate of the original quaternion, which
	 * is a mirrored version of the quaternion.
	 * 	- The `mul()` method performs multiplication between the original quaternion and
	 * another quaternion or a scalar value. In this case, it multiplies the original
	 * quaternion with its conjugate to form a new quaternion representing the inverse rotation.
	 * 	- The resulting `Quaternion` object `w` represents the rotated vector. Its
	 * components in the 3D space represent the transformed position of the original
	 * vector after applying the rotation.
	 * 
	 * @returns a vector representing the rotated position of the original vector.
	 * 
	 * The output is a vector object of type `Vector3f`, which represents a 3D position
	 * in homogeneous coordinates. The values of the vector represent the new position
	 * of the rotated object after applying the rotation matrix.
	 * 
	 * The vector's x, y, and z components are computed as the result of multiplying the
	 * original object's position by the rotation matrix represented by `rotation` and
	 * its conjugate.
	 * 
	 * The rotation matrix is a 4x4 matrix that represents the rotation as a transformation
	 * from the original coordinate system to the rotated one. The conjugate of the
	 * rotation matrix is the transpose of the matrix, which is used in the multiplication
	 * operation to ensure that the resulting vector has the correct orientation.
	 */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

	/**
	 * interpolates a vector between two given values, `dest` and `lerpFactor`. It
	 * calculates the intermediate vector by multiplying the difference between the current
	 * vector and the destination vector with the lerp factor, adding the result to the
	 * current vector.
	 * 
	 * @param dest 3D vector that the method will blend the current vector with, using
	 * the provided `lerpFactor`.
	 * 
	 * 	- `dest`: A `Vector3f` instance representing the destination point to which the
	 * current instance is being interpolated.
	 * 	- `lerpFactor`: An integer value representing the interpolation factor between
	 * the current instance and the destination instance.
	 * 
	 * @param lerpFactor 0-1 factor for linear interpolation between the current vector
	 * and the destination vector, with 0 representing no interpolation and 1 representing
	 * full interpolation.
	 * 
	 * @returns a vector that interpolates between the input `dest` and the current
	 * position of the object.
	 * 
	 * The output is a `Vector3f` instance that represents the interpolation between the
	 * `this` argument and the `dest` argument. The interpolation is performed using the
	 * `sub`, `mul`, and `add` methods of the `Vector3f` class.
	 * 
	 * The result has the same components as the input arguments, but their values are
	 * interpolated based on the provided `lerpFactor`. The `lerpFactor` parameter
	 * represents the fraction of the distance from the start point to the end point that
	 * the output should represent.
	 */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	/**
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the sum
	 * of the input values.
	 * 
	 * @param r 3D vector to be added to the current vector.
	 * 
	 * 	- `x`: The x-coordinate of the input vector.
	 * 	- `y`: The y-coordinate of the input vector.
	 * 	- `z`: The z-coordinate of the input vector.
	 * 
	 * @returns a new `Vector3f` instance representing the sum of the input vectors.
	 * 
	 * The output is a new Vector3f object that represents the sum of the inputs.
	 * The x, y, and z components of the output are calculated by adding the corresponding
	 * components of the two input vectors.
	 * The resulting vector has the same semantic meaning as the original input vectors,
	 * representing a combination of the two.
	 */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
	/**
	 * adds the components of a provided vector to the corresponding components of the
	 * current object.
	 * 
	 * @param r 3D vector that adds its components to the current position of the object,
	 * resulting in the updated position of the object.
	 * 
	 * 	- `x`, `y`, and `z` are the components of the vector in the code.
	 */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

	/**
	 * takes a scalar value `r` and adds it to the components of the input vector, resulting
	 * in a new vector with the summed values.
	 * 
	 * @param r 3D vector to be added to the existing vector values of `x`, `y`, and `z`.
	 * 
	 * @returns a new vector with the sum of the original vector's components and the
	 * input float value added to it.
	 * 
	 * The `Vector3f` object returned by the function represents a 3D point in space,
	 * with x, y, and z components representing the coordinates of the point. The addition
	 * performed by the function results in a new 3D point with increased values for each
	 * component by the specified amount 'r'. Therefore, the output represents a new 3D
	 * point located at a distance 'r' away from the original point.
	 */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
	/**
	 * adds a vector to the current vector, scaling it by a float value beforehand. The
	 * returned vector represents the result of the addition.
	 * 
	 * @param v 3D vector to be scaled and added to the current vector.
	 * 
	 * The input `v` is a Vector3f object that represents a 3D vector with floating-point
	 * components.
	 * It has three attributes: x, y, and z, which represent the coordinates of the vector
	 * in the Cartesian coordinate system.
	 * 
	 * @param scale scaling factor applied to the input vector before adding it to the
	 * current vector.
	 * 
	 * @returns a new vector that is the result of adding the provided vector scaled by
	 * the given factor to the original vector.
	 * 
	 * The `Vector3f` object returned by this function is an instance of the class
	 * `Vector3f`. It represents a 3D vector with x, y, and z components.
	 * 
	 * The `x`, `y`, and `z` components of the returned vector are obtained by multiplying
	 * the input `v` vector by the scale factor `scale`. This means that the resulting
	 * vector has the same direction as `v`, but with a magnitude that is proportional
	 * to the square of the scale factor.
	 * 
	 * The returned vector can be used in various mathematical operations involving
	 * vectors, such as vector addition and scalar multiplication.
	 */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
	/**
	 * multiplies a `Vector3f` object by a floating-point value and adds the result to
	 * the current object's state, scaling its components proportionally.
	 * 
	 * @param v 3D vector to be scaled, which is then multiplied by the specified scaling
	 * factor before being added to the calling object's state.
	 * 
	 * 	- `v`: A `Vector3f` instance representing a 3D vector with three components (x,
	 * y, and z).
	 * 
	 * @param scale 3D vector that multiplies the given `Vector3f` argument, resulting
	 * in the new vector to be added to the current vector store in this object.
	 */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

	/**
	 * calculates the vector difference between two vectors represented as `Vector3f`
	 * objects, returning a new vector with the difference in each component.
	 * 
	 * @param r 3D vector to be subtracted from the current vector, resulting in a new
	 * vector that represents the difference between the two.
	 * 
	 * 	- `x`: The real-valued coordinate of the first dimension of `r`.
	 * 	- `y`: The real-valued coordinate of the second dimension of `r`.
	 * 	- `z`: The real-valued coordinate of the third dimension of `r`.
	 * 
	 * @returns a new vector with the difference of the input vector and the origin vector.
	 * 
	 * 	- The value of the `x`, `y`, and `z` elements represent the difference between
	 * the input vector's corresponding components and the output vector's corresponding
	 * components.
	 */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

	/**
	 * takes a scalar value `r` and returns a new `Vector3f` instance with the difference
	 * between the input vector's components and the scalar value subtracted from each component.
	 * 
	 * @param r 3D vector subtracting from the current vector's components to produce the
	 * new vector.
	 * 
	 * @returns a new `Vector3f` object representing the difference between the original
	 * vector and the specified value.
	 * 
	 * 	- The output is a new instance of the `Vector3f` class, indicating that it has
	 * its own memory location and can be modified independently.
	 * 	- The `x`, `y`, and `z` fields of the output represent the difference between the
	 * input vector's coordinates and the given value `r`.
	 * 	- The resulting vector has the same unit as the original vector, meaning that it
	 * maintains the same scale and direction.
	 */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

	/**
	 * takes a `Vector3f` object `r` and returns a new `Vector3f` object with the product
	 * of the elements of the two objects.
	 * 
	 * @param r 3D vector that is multiplied with the current vector, resulting in a new
	 * vector with the product of the two values.
	 * 
	 * `x`, `y`, and `z` represent the components of the input vector in 3D space. These
	 * values can take on any real number value within a specific range or scale.
	 * 
	 * @returns a new vector with the product of the input vectors' components.
	 * 
	 * 	- The output is a `Vector3f` object, indicating that it has three components (x,
	 * y, and z) representing the product of the input vectors.
	 * 	- The components of the output vector are determined by multiplying the corresponding
	 * components of the input vectors using the dot product formula. Specifically, the
	 * x component of the output is equal to the product of the x components of the input
	 * vectors, the y component is equal to the product of the y components, and the z
	 * component is equal to the product of the z components.
	 * 	- The output vector has the same units as the input vectors, since the multiplication
	 * operation preserves the unit of measurement.
	 */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

	/**
	 * multiplies the components of a `Vector3f` object by a scalar value `r`, returning
	 * a new `Vector3f` object with the scaled components.
	 * 
	 * @param r scalar value that is multiplied with the corresponding components of the
	 * `Vector3f` instance.
	 * 
	 * @returns a vector with the product of the input `r` and each component of the
	 * original vector.
	 * 
	 * 	- The output is a `Vector3f` object, which represents a 3D vector with x, y, and
	 * z components.
	 * 	- The x, y, and z components of the output are each multiplied by the input value
	 * `r`.
	 * 	- As a result, the magnitude (or length) of the output vector is increased by the
	 * factor of `r`.
	 * 	- The direction of the output vector remains unchanged, as it is only magnified.
	 */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

	/**
	 * takes a reference to another `Vector3f` object as input and returns a new `Vector3f`
	 * object with the component values divided by those of the input object.
	 * 
	 * @param r 3D vector to divide by, and it is used to calculate the output vector
	 * `Vector3f` with a scaled length of `x / r.getX()`, `y / r.getY()`, and `z / r.getZ()`.
	 * 
	 * 	- `x`, `y`, and `z` are the components of the vector.
	 * 	- `getX()`, `getY()`, and `getZ()` are methods that return the individual components
	 * of the vector.
	 * 
	 * @returns a vector with the same component values as the input vector, scaled by
	 * the reciprocal of the input vector's magnitude.
	 * 
	 * The output is of type Vector3f, indicating that it represents a 3D vector with x,
	 * y, and z components.
	 * 
	 * The x, y, and z components of the output are calculated by dividing the corresponding
	 * components of the input vector by the corresponding components of the input vector
	 * r. This means that the output vector will have the same direction as the input
	 * vector, but its magnitude will be reduced by the factor of r.
	 * 
	 * Therefore, the output vector represents a scaling down of the input vector by the
	 * factor of r.
	 */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

	/**
	 * takes a scalar value `r` and returns a new `Vector3f` instance with the original
	 * vector's components divided by `r`.
	 * 
	 * @param r divisor used to perform the division of the vector's components by it.
	 * 
	 * @returns a vector with the same x, y, and z components scaled by the given scalar
	 * value.
	 * 
	 * The `Vector3f` object returned by the function represents a vector with a magnitude
	 * that is equal to the input parameter `r` divided by the original vector's magnitude.
	 * The vector's direction remains unchanged.
	 */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

	/**
	 * computes the absolute value of a vector and returns it as a new vector with the
	 * same components.
	 * 
	 * @returns a new `Vector3f` object containing the absolute values of the input
	 * vector's components.
	 * 
	 * 	- `x`, `y`, and `z` are the components of the vector, which represent the magnitude
	 * (or length) of the vector in the corresponding dimensions.
	 * 	- The return type is a `Vector3f`, indicating that the function returns a 3D
	 * vector object with the same components as the original input vector.
	 * 	- The function simply computes the magnitude of the input vector using the
	 * `Math.abs` method and creates a new vector object with the computed magnitude in
	 * each component.
	 */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

	/**
	 * generates a string representation of an object by concatenating three values: `x`,
	 * `y`, and `z`.
	 * 
	 * @returns a string representation of a point in 3D space, consisting of three numbers
	 * separated by spaces.
	 */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

	/**
	 * returns a `Vector2f` object representing the position of an entity in a 2D space,
	 * with its x-coordinate and y-coordinate being the values returned.
	 * 
	 * @returns a vector containing the x and y coordinates of the point.
	 * 
	 * 	- `x`: The first component of the Vector2f object, representing the x-coordinate
	 * of the point.
	 * 	- `y`: The second component of the Vector2f object, representing the y-coordinate
	 * of the point.
	 */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

	/**
	 * returns a `Vector2f` object containing the `y` and `z` components of an unknown entity.
	 * 
	 * @returns a `Vector2f` object containing the `y` and `z` coordinates of the point.
	 * 
	 * 	- `y`: The x-coordinate of the vector.
	 * 	- `z`: The y-coordinate of the vector.
	 * 
	 * Both `x` and `y` coordinates represent a point in 2D space with floating-point numbers.
	 */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

	/**
	 * returns a `Vector2f` object representing the point (x, z) of a given vector.
	 * 
	 * @returns a `Vector2f` object containing the `z` and `x` components.
	 * 
	 * 	- The output is a `Vector2f` object, which represents a 2D point in homogeneous
	 * coordinates.
	 * 	- The first element of the vector, z, represents the Z-component of the point.
	 * 	- The second element of the vector, x, represents the X-component of the point.
	 * 
	 * These properties make the `getZX` function useful for various applications that
	 * require 2D points in homogeneous coordinates.
	 */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

	/**
	 * returns a `Vector2f` object containing the `y` and `x` coordinates of a point.
	 * 
	 * @returns a `Vector2f` object containing the values of `y` and `x`.
	 * 
	 * 1/ The output is of type `Vector2f`, which represents a 2D point with x and y components.
	 * 2/ The `y` component represents the vertical coordinate of the point, while the
	 * `x` component represents the horizontal coordinate.
	 * 3/ The vector is immutable, meaning its components cannot be changed once it is created.
	 */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

	/**
	 * returns a `Vector2f` object representing the component values of z and y.
	 * 
	 * @returns a `Vector2f` object containing the `z` and `y` coordinates of the point.
	 * 
	 * The `Vector2f` object represents a 2D point in homogeneous coordinates, with x and
	 * y components representing its position along the x-axis and y-axis, respectively.
	 * The z component represents the value of the third dimension (not shown). As an
	 * immutable object, its properties cannot be modified once created.
	 */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

	/**
	 * returns a `Vector2f` object containing the `x` and `z` components of a point.
	 * 
	 * @returns a `Vector2f` object containing the x and z coordinates of the point.
	 * 
	 * 	- `x`: The x-coordinate of the vector, which represents the real value of the
	 * point in the x-direction.
	 * 	- `z`: The z-coordinate of the vector, which represents the imaginary value of
	 * the point in the z-direction.
	 * 	- Type: The output is a `Vector2f` object, which is a two-dimensional vector
	 * representation of a point in a 2D space.
	 */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

	/**
	 * sets the x, y and z components of a `Vector3f` instance to the input values.
	 * 
	 * @param x 3D position of the vector in the x-axis direction.
	 * 
	 * @param y 2D position of the vector in the Y-axis.
	 * 
	 * @param z 3rd component of the vector and sets its value to the provided float value.
	 * 
	 * @returns a reference to the same `Vector3f` instance with updated coordinates.
	 * 
	 * 	- The `this` pointer is used to refer to the object being manipulated, allowing
	 * the function to modify its internal state.
	 * 	- The `x`, `y`, and `z` parameters represent the new values for the object's `x`,
	 * `y`, and `z` components, respectively. These values are assigned to the corresponding
	 * fields of the object using the assignment operator (=).
	 * 	- The function returns a reference to the same object, allowing it to be chainable
	 * and enabling further method calls on the same object without creating a new instance.
	 */
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * sets the components of the `Vector3f` object to the corresponding values of the
	 * provided `Vector3f` argument.
	 * 
	 * @param r 3D vector that sets the values of the `Vector3f` object.
	 * 
	 * 	- `getX()`: Returns the x-coordinate of the vector.
	 * 	- `getY()`: Returns the y-coordinate of the vector.
	 * 	- `getZ()`: Returns the z-coordinate of the vector.
	 * 
	 * @returns a reference to the same `Vector3f` object, unchanged.
	 * 
	 * The function takes three arguments representing the x, y, and z components of the
	 * vector to be set, respectively. The function then sets these components of the
	 * input vector in this object. After setting the components, the function returns a
	 * reference to this object, indicating that the method has modified the original object.
	 */
	public Vector3f set(Vector3f r) {
		set(r.getX(), r.getY(), r.getZ());
		return this;
	}

	/**
	 * retrieves the value of `x`, a `float` variable, and returns it unchanged.
	 * 
	 * @returns a floating-point value representing the `x` field.
	 */
	public float getX() {
		return x;
	}

	/**
	 * sets the value of the `x` field of the object to which it belongs.
	 * 
	 * @param x float value that will be assigned to the `x` field of the class instance
	 * being manipulated by the `setX()` method.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * returns the value of the `y` field, which is a `float`.
	 * 
	 * @returns a floating-point value representing the y coordinate of a point.
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of a class instance field 'y' to the argument passed as a float.
	 * 
	 * @param y floating-point value to be assigned to the `y` field of the class instance,
	 * which is then updated by calling the `this.y = y;` statement.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * returns the value of `z`.
	 * 
	 * @returns the value of `z`, which is a `float`.
	 */
	public float getZ() {
		return z;
	}

	/**
	 * sets the value of the field `z` of an object to the provided float value.
	 * 
	 * @param z 2D coordinate of the point to be modified, and its value is used to update
	 * the `z` component of the object's position.
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * compares a vector's components with those of another vector, returning `true` if
	 * they are identical, and `false` otherwise.
	 * 
	 * @param r 3D vector to be compared with the current vector, which is stored in the
	 * class instance variables `x`, `y`, and `z`.
	 * 
	 * 	- `x`: The x-coordinates of the two vectors are compared using the `==` operator.
	 * 	- `y`: The y-coordinates of the two vectors are compared using the `==` operator.
	 * 	- `z`: The z-coordinates of the two vectors are compared using the `==` operator.
	 * 
	 * The function returns a boolean value indicating whether the inputs are equal or not.
	 * 
	 * @returns a boolean value indicating whether the vector's components are equal to
	 * those of the provided vector.
	 */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
