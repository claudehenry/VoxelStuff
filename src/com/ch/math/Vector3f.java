package com.ch.math;

/**
 * TODO
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
  * calculates the magnitude of a vector using the Pythagorean theorem, with inputs
  * for `x`, `y`, and `z`.
  * 
  * @returns the square root of the sum of the squares of its input arguments.
  */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
 /**
  * calculates the length of a point in 3D space by squaring its coordinates and summing
  * them.
  * 
  * @returns a floating-point representation of the square of the length of the geometric
  * object.
  */
	public float squareLength() {
		return (float) x*x + y*y + z*z;
	}

 /**
  * returns the maximum value of three arguments: `x`, `y`, and `z`. It applies the
  * `Math.max` method to each argument, then returns the maximum value.
  * 
  * @returns the maximum of three values: `x`, `y`, and `z`.
  */
	public float max() {
		return Math.max(x, Math.max(y, z));
	}

 /**
  * computes the dot product of a `Vector3f` object and another vector, returning the
  * result as a floating-point value.
  * 
  * @param r 3D vector to which the current vector is being dot-producted, and its
  * value is used to compute the dot product of the two vectors.
  * 
  * 	- `x`, `y`, and `z` are the components of `r`, which are floating-point numbers
  * representing the coordinates of a 3D point.
  * 
  * @returns a floating-point number representing the dot product of the input vector
  * and the current vector.
  */
	public float dot(Vector3f r) {
		return x * r.getX() + y * r.getY() + z * r.getZ();
	}

 /**
  * computes the vector that results from crossing two given vectors. It returns a new
  * vector with the components computed based on the product of the two input vectors.
  * 
  * @param r 3D vector to which the `Vector3f` instance is being crossed, providing
  * the value that is used to calculate the output vector's components.
  * 
  * 	- `r` is a `Vector3f` object with three components representing x, y, and z
  * coordinates, respectively.
  * 	- `getZ()` and `getY()` methods retrieve the values of the z and y components,
  * respectively, of the `r` object.
  * 	- `getX()` method retrieves the value of the x component of the `r` object.
  * 
  * @returns a vector with the cross product of two given vectors.
  * 
  * 	- The output is a new `Vector3f` object that represents the cross product of the
  * two input vectors.
  * 	- The components of the output vector are calculated using the dot product formula
  * for the two input vectors and the resulting values are stored in the corresponding
  * components of the output vector.
  * 	- The order of the input vectors does not matter when calculating the cross
  * product, so the function is commutative.
  * 	- The cross product operation is distributive over addition, meaning that the
  * result of multiplying a vector by a scalar and then crossing it with another vector
  * is the same as multiplying the two vectors separately and then crossing them.
  * 
  * In summary, the `cross` function takes two vectors as input and returns their cross
  * product as output, adhering to the properties mentioned above.
  */
	public Vector3f cross(Vector3f r) {
		float x_ = y * r.getZ() - z * r.getY();
		float y_ = z * r.getX() - x * r.getZ();
		float z_ = x * r.getY() - y * r.getX();

		return new Vector3f(x_, y_, z_);
	}

 /**
  * normalizes a vector by dividing each component by the magnitude of the vector,
  * resulting in a unitized vector representation.
  * 
  * @returns a vector with the given position values normalized to have a length of 1.
  * 
  * 	- The output is a `Vector3f` instance representing the normalized version of the
  * original vector.
  * 	- The elements of the output are the coordinates of the normalized vector, where
  * each element is scaled by dividing it by the magnitude (or length) of the original
  * vector.
  * 	- The magnitude of the output vector is always non-negative and less than or equal
  * to 1, regardless of the input value.
  * 	- The direction of the output vector is unchanged from the input vector.
  */
	public Vector3f normalized() {
		float length = length();

		return new Vector3f(x / length, y / length, z / length);
	}

 /**
  * rotates a vector by an angle around a given axis, returning the new vector. It
  * computes the sin and cos of the angle, and then performs a series of vector
  * operations to produce the result.
  * 
  * @param axis 3D rotation axis around which the object is rotated.
  * 
  * 	- `axis` is a 3D vector representing the rotation axis.
  * 	- It has three components: `x`, `y`, and `z`.
  * 	- The length of `axis` is non-zero, indicating that the rotation is not around
  * the origin.
  * 	- The direction of `axis` is along the Z-axis (the axis perpendicular to the plane
  * of the rotation).
  * 
  * @param angle 3D rotation angle of the object around the specified axis.
  * 
  * @returns a new vector that represents the rotation of the original vector about
  * the specified axis, with the angle of rotation specified.
  * 
  * The output is a vector in 3D space, represented by the `Vector3f` class in Java.
  * 
  * The vector is rotated around the specified axis, which is represented by the `axis`
  * parameter. The rotation angle is given by the `angle` parameter.
  * 
  * The rotation is performed using the sine and cosine of the rotation angle, as
  * calculated using `Math.sin()` and `Math.cos()`. These values are then used to
  * calculate the new vector using matrix multiplication and addition.
  * 
  * The resulting vector has a magnitude that is equal to the dot product of the
  * original vector and the axis multiplied by the sine of the rotation angle, minus
  * the dot product of the original vector and the axis multiplied by the cosine of
  * the rotation angle.
  */
	public Vector3f rotate(Vector3f axis, float angle) {
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);

		return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
	}

 /**
  * rotates a `Vector3f` instance by the angle represented by a `Quaternion` rotation,
  * resulting in a new `Vector3f` instance representing the rotated position and
  * orientation in 3D space.
  * 
  * @param rotation 3D rotation to be applied to the `Vector3f` object.
  * 
  * The `Quaternion` type is a mathematical representation of 3D rotations. It consists
  * of four components: x, y, z, and w. The last component (w) is used to determine
  * the orientation of the quaternion in 3D space.
  * 
  * The `conjugate` property returns a new `Quaternion` object that is the conjugate
  * of the original `rotation`. This is done by multiplying the `rotation` with its
  * own conjugate, resulting in a new quaternion that represents the inverse of the
  * original rotation.
  * 
  * The `mul()` method is used to perform the multiplication between the `rotation`
  * and its conjugate, producing a new `Quaternion` object that represents the result
  * of the rotation.
  * 
  * Finally, the `getX()`, `getY()`, and `getZ()` methods are used to extract the X,
  * Y, and Z components of the resulting `Quaternion`, which are then returned as a
  * `Vector3f` object representing the rotated position.
  * 
  * @returns a vector representing the rotated position of the original vector.
  * 
  * 	- The output is a `Vector3f` object representing the rotated position in 3D space.
  * 	- The x, y, and z components of the vector represent the rotated position along
  * the respective axes.
  * 	- The value of each component is obtained by multiplying the original position
  * by the quaternion rotation matrix, followed by the conjugate of the rotation matrix.
  * This ensures that the rotation is performed in a consistent and correct manner.
  */
	public Vector3f rotate(Quaternion rotation) {
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul(this).mul(conjugate);

		return new Vector3f(w.getX(), w.getY(), w.getZ());
	}

 /**
  * computes a linear interpolation between two vectors, `dest` and `this`, based on
  * the provided factor `lerpFactor`. It returns the interpolated vector.
  * 
  * @param dest 3D vector that the method will smoothly transition from the current
  * vector to, with the specified lerp factor.
  * 
  * 	- `dest` is a Vector3f object that represents a 3D point in space with x, y, and
  * z components.
  * 	- `lerpFactor` is a floating-point value representing the interpolation factor
  * between the current position of the object and the desired destination.
  * 
  * The function returns a new Vector3f object that represents the interpolated position
  * of the object between its current position and the desired destination. The function
  * first subtracts the current position from the desired destination, then multiplies
  * the result by the `lerpFactor`, and finally adds the original position back to get
  * the interpolated position.
  * 
  * @param lerpFactor factor by which the current vector is to be interpolated towards
  * the destination vector.
  * 
  * @returns a vector that interpolates between two given vectors based on a provided
  * factor.
  * 
  * The output is a `Vector3f` object that represents the interpolation between the
  * `dest` vector and the current vector.
  * 
  * The `lerpFactor` parameter determines how much the output vector is scaled from
  * the `dest` vector, with a value of 0 resulting in the `dest` vector being returned
  * unchanged.
  * 
  * The output vector has the same direction as the `dest` vector, but its magnitude
  * is interpolated between the magnitudes of the `dest` and current vectors.
  */
	public Vector3f lerp(Vector3f dest, float lerpFactor) {
		return dest.sub(this).mul(lerpFactor).add(this);
	}

 /**
  * takes a `Vector3f` object as input and returns a new `Vector3f` object with the
  * sum of the input and the current object's values.
  * 
  * @param r 3D vector to be added to the current vector.
  * 
  * 	- `x`: an integer value representing the x-coordinate of the input vector.
  * 	- `y`: an integer value representing the y-coordinate of the input vector.
  * 	- `z`: an integer value representing the z-coordinate of the input vector.
  * 
  * @returns a new `Vector3f` instance with the sum of the input vectors' components.
  * 
  * The returned value is a new instance of the `Vector3f` class, representing the sum
  * of the input arguments. The x, y, and z components of the return value are calculated
  * by adding the corresponding components of the two input vectors.
  */
	public Vector3f add(Vector3f r) {
		return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
	}
	
 /**
  * updates the component values of a `Vector3f` object by adding the corresponding
  * components of another `Vector3f` object to the current object's values.
  * 
  * @param r 3D vector to add to the object's position, and its x, y, and z components
  * are added to the object's x, y, and z components, respectively.
  * 
  * 	- `x`, `y`, and `z` represent the individual coordinates of the vector.
  */
	public void addSelf(Vector3f r) {
		this.x += r.x;
		this.y += r.y;
		this.z += r.z;
	}

 /**
  * takes a scalar value `r` and adds it to the components of its input vector, returning
  * a new vector with the sum.
  * 
  * @param r 3D vector to be added to the current vector.
  * 
  * @returns a new `Vector3f` instance with the sum of the original vector's components
  * and the given scalar value.
  * 
  * The `Vector3f` object returned by the function represents a 3D vector with x, y,
  * and z components that have been added to their corresponding input values by a
  * float value 'r'. The resulting vector has an x component equal to the sum of the
  * input x component and the input r value, a y component equal to the sum of the
  * input y component and the input r value, and a z component equal to the sum of the
  * input z component and the input r value.
  */
	public Vector3f add(float r) {
		return new Vector3f(x + r, y + r, z + r);
	}
	
 /**
  * adds a vector to the current vector, scaling the result by a specified factor.
  * 
  * @param v 3D vector that is to be scaled and added to the current vector instance.
  * 
  * The `v` parameter is of type `Vector3f`, which represents a 3D vector with three
  * components: x, y, and z. These components have decimal values between -1 and 1.
  * 
  * The `scale` parameter is a float value that represents the scaling factor applied
  * to the `v` vector. This value can be any real number between 0 and infinity.
  * 
  * @param scale 3D vector that is to be multiplied with the input `v` before adding
  * it to the current vector.
  * 
  * @returns a new vector that represents the sum of the original vector and the scaled
  * version of it.
  * 
  * The output is a `Vector3f` object that represents the sum of the original vector
  * `this` and the scaled version of the input vector `v`. The scaling factor `scale`
  * is applied to the components of the input vector before adding it to `this`.
  * Therefore, the resulting vector has the same magnitude as the input vector, but
  * its direction may be different depending on the sign and magnitude of the scaling
  * factor.
  */
	public Vector3f addScaledVector(Vector3f v, float scale) {
		return this.add(v.mul(scale));
	}
	
 /**
  * multiplies a vector by a scalar value and adds it to the current vector, scaling
  * the current vector accordingly.
  * 
  * @param v 3D vector to be scaled, which is then multiplied by the specified scalar
  * value before being added to the current vector representation of the object.
  * 
  * 	- `v` is a `Vector3f` instance, representing a 3D vector with floating-point values.
  * 	- `scale` is a float value passed as an argument to the function, indicating the
  * scaling factor applied to the vector.
  * 
  * @param scale 3D vector that is multiplied by the corresponding component of the
  * `v` argument, resulting in the new value added to the current state of the object.
  */
	public void addSelfScaledVector(Vector3f v, float scale) {
		this.addSelf(v.mul(scale));
	}

 /**
  * calculates the difference between two vectors and returns a new vector with the
  * component-wise subtraction.
  * 
  * @param r 3D vector to be subtracted from the current vector, resulting in a new
  * vector that represents the difference between the two vectors.
  * 
  * 	- `x`: The real value of `r`.
  * 	- `y`: The imaginary value of `r`.
  * 	- `z`: The scalar value of `r`.
  * 
  * @returns a new vector with the difference between the input vector and the current
  * vector's components.
  * 
  * 	- The output is a new Vector3f object with the difference between the input vectors
  * calculated.
  * 	- The vector components are represented by the variables `x`, `y`, and `z`.
  * 	- These components can take on any real value within the range of -1.0 to 1.0.
  * 
  * Note: The output is not modified in any way, it is a new object that represents
  * the difference between the input vectors.
  */
	public Vector3f sub(Vector3f r) {
		return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
	}

 /**
  * takes a single float parameter and subtracts it from the corresponding components
  * of a `Vector3f` object, returning a new `Vector3f` instance with the updated values.
  * 
  * @param r 3D vector to subtract from the current vector.
  * 
  * @returns a new `Vector3f` instance representing the difference between the original
  * vector and the given scalar value.
  * 
  * The function returns a new `Vector3f` object representing the difference between
  * the input vector and the reference vector. The returned vector has the same
  * components as the input vector, but with the values shifted by the amount of the
  * reference vector.
  * 
  * The returned vector's x-component is equal to the input vector's x-component minus
  * the reference vector's x-component.
  * 
  * The returned vector's y-component is equal to the input vector's y-component minus
  * the reference vector's y-component.
  * 
  * The returned vector's z-component is equal to the input vector's z-component minus
  * the reference vector's z-component.
  */
	public Vector3f sub(float r) {
		return new Vector3f(x - r, y - r, z - r);
	}

 /**
  * multiplies two vector structures and returns a new vector with the result.
  * 
  * @param r 3D vector that multiplies with the current vector, resulting in a new 3D
  * vector output.
  * 
  * `x`, `y`, and `z` are the components of the input vector in 3D space.
  * 
  * @returns a new vector with the product of the input vectors' components.
  * 
  * 	- The output is a new instance of the `Vector3f` class.
  * 	- The output's `x`, `y`, and `z` components are calculated by multiplying the
  * corresponding components of the input vector `r` by the corresponding components
  * of the function's argument `x`, `y`, and `z`.
  * 	- The resulting components of the output are stored in a new instance of the
  * `Vector3f` class.
  */
	public Vector3f mul(Vector3f r) {
		return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
	}

 /**
  * multiplies the vector's components by a scalar value `r`, resulting in a new vector
  * with the same direction as the original but scaled by the magnitude of `r`.
  * 
  * @param r scalar value that is multiplied with the components of the `Vector3f`
  * instance being manipulated.
  * 
  * @returns a vector with the product of the x, y, and z components and the input
  * parameter `r`.
  * 
  * The `Vector3f` object returned is an instance of a class that represents a 3D
  * vector in Java.
  * The `x`, `y`, and `z` fields of the object represent the coordinates of the vector.
  * Each field is assigned a value based on the product of the `x`, `y`, and `z`
  * components of the original input vector and the scalar `r`.
  * Therefore, the output vector has a magnitude that is equal to the product of the
  * magnitudes of the input vector's components and the scalar `r`, and its direction
  * is parallel to the line connecting the origin of the coordinate system to the point
  * on which the dot product of the input vector with a unit vector in the direction
  * of `r` results.
  */
	public Vector3f mul(float r) {
		return new Vector3f(x * r, y * r, z * r);
	}

 /**
  * takes a reference to another vector and returns a new vector with the components
  * scaled by the reciprocal of the corresponding components of the input vector.
  * 
  * @param r vector to divide the current vector by, resulting in a new vector with
  * the same components but with the values scaled by the corresponding ratio.
  * 
  * 	- `x`: The componentwise multiplication of `r.x` with the divisor is performed
  * to produce the result.
  * 	- `y`: Similarly, the componentwise multiplication of `r.y` with the divisor is
  * carried out to produce the result.
  * 	- `z`: The same applies to `r.z`.
  * 
  * @returns a new vector with the same x, y, and z components, but with the values
  * scaled by the reciprocal of the input vector's x, y, and z components.
  * 
  * 	- The output is a new instance of the `Vector3f` class with the values x, y, and
  * z scaled by the corresponding values in the argument `r`.
  * 	- The resulting vector has the same magnitude as the original input vector, but
  * its direction is reversed. This means that if the original vector was pointing in
  * a particular direction, the divided vector will point in the opposite direction.
  * 	- The division is performed element-wise, meaning that each component of the
  * output is equal to the corresponding component of the input scaled by the factor
  * from the argument `r`.
  */
	public Vector3f div(Vector3f r) {
		return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
	}

 /**
  * takes a scalar `r` and returns a `Vector3f` with components scaled by the inverse
  * of `r`.
  * 
  * @param r scalar value used to divide each component of the input vector by.
  * 
  * @returns a vector with the same x, y, and z components divided by the input parameter
  * `r`.
  * 
  * The `Vector3f` object returned is a scaled version of the original vector, where
  * each component has been divided by the input parameter `r`. Therefore, the magnitude
  * of the returned vector is equal to the magnitude of the original vector divided
  * by `r`. The direction of the returned vector remains unchanged from the original
  * vector.
  */
	public Vector3f div(float r) {
		return new Vector3f(x / r, y / r, z / r);
	}

 /**
  * computes the absolute value of a `Vector3f` object, returning a new `Vector3f`
  * instance with the absolute values of its components.
  * 
  * @returns a new `Vector3f` object containing the absolute values of the input
  * vectors' components.
  * 
  * 	- The return type is `Vector3f`, which represents a 3D vector with floating-point
  * values.
  * 	- The function takes no arguments, meaning that it returns a newly created
  * `Vector3f` object with the absolute value of the input vector's components.
  * 	- The returned vector has the same x, y, and z components as the input vector,
  * but with their absolute values.
  */
	public Vector3f abs() {
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
	}

 /**
  * returns a string representation of a mathematical expression involving `x`, `y`,
  * and `z` variables, using parentheses and spaces to organize the terms.
  * 
  * @returns a string representing the point (x, y, z) as a single value.
  * 
  * The output is a string that consists of three components separated by spaces. The
  * first component is the value of the `x` field, the second component is the value
  * of the `y` field, and the third component is the value of the `z` field.
  */
	public String toString() {
		return "(" + x + " " + y + " " + z + ")";
	}

 /**
  * returns a `Vector2f` object containing the values of `x` and `y`.
  * 
  * @returns a `Vector2f` object representing the position of an entity in a 2D space.
  * 
  * 	- The Vector2f object represents an ordered pair of values in 2D space, with x
  * representing the horizontal position and y representing the vertical position.
  * 	- Both x and y have a type of float, indicating that they can hold decimal values
  * for precise positioning.
  * 	- The Vector2f object is a mutable type, meaning it can be modified after creation.
  * 	- No information is provided about the author or licensing of the code.
  */
	public Vector2f getXY() {
		return new Vector2f(x, y);
	}

 /**
  * returns a new `Vector2f` object containing the `y` and `z` components of an
  * unspecified original vector.
  * 
  * @returns a `Vector2f` object containing the `y` and `z` coordinates of the point.
  * 
  * 	- The output is a `Vector2f` object, representing a 2D point in homogeneous
  * coordinates with x and y components.
  * 	- The `y` component represents the vertical distance from the origin, while the
  * `z` component represents the depth or height from the origin along the z-axis.
  * 	- The ` Vector2f` class is a part of the Java library for mathematical operations
  * on 2D points.
  */
	public Vector2f getYZ() {
		return new Vector2f(y, z);
	}

 /**
  * returns a `Vector2f` object representing the z-axis and x-axis coordinates of a point.
  * 
  * @returns a `Vector2f` object representing the coordinate pair (z, x).
  * 
  * 	- `z`: The z-coordinate of the vector, which represents the vertical position in
  * the 2D space.
  * 	- `x`: The x-coordinate of the vector, which represents the horizontal position
  * in the 2D space.
  * 
  * The resulting vector object has two components: `z` and `x`, which can be used to
  * represent a point in 2D space with both vertical and horizontal coordinates.
  */
	public Vector2f getZX() {
		return new Vector2f(z, x);
	}

 /**
  * returns a `Vector2f` object containing the `y` and `x` coordinates of a point.
  * 
  * @returns a `Vector2f` object representing the position of an entity in the Cartesian
  * coordinate system, with `y` and `x` components.
  * 
  * 	- The output is a `Vector2f` object that represents a point in 2D space with x
  * and y coordinates.
  * 	- The x coordinate has a value of `x`, which is an int representing the x-position
  * of the point.
  * 	- The y coordinate has a value of `y`, which is also an int representing the
  * y-position of the point.
  * 
  * Overall, the `getYX` function returns a point object that represents a 2D location
  * in the plane.
  */
	public Vector2f getYX() {
		return new Vector2f(y, x);
	}

 /**
  * returns a `Vector2f` object representing the point (z, y)
  * 
  * @returns a `Vector2f` object representing the point (z, y) in the coordinate system.
  * 
  * 	- The output is a `Vector2f` object representing the z-y coordinate pair in
  * homogeneous coordinates.
  * 	- The z component of the vector represents the distance from the origin along the
  * z-axis, while the y component represents the distance from the origin along the y-axis.
  * 	- Both components have real numbers as their values, which can range from negative
  * infinity to positive infinity.
  */
	public Vector2f getZY() {
		return new Vector2f(z, y);
	}

 /**
  * returns a `Vector2f` object containing the `x` and `z` coordinates of an unknown
  * entity.
  * 
  * @returns a `Vector2f` object containing the values of `x` and `z`.
  * 
  * 	- The `Vector2f` object returned represents a 2D point in the coordinate system,
  * with x-component representing the horizontal position and z-component representing
  * the vertical position.
  * 	- The values of the x and z components are set to the parameters passed to the
  * function, which can be any valid values for a 2D point.
  * 	- The `Vector2f` class defines several methods for manipulating points in 2D
  * space, such as addition, subtraction, multiplication by a scalar, and more.
  */
	public Vector2f getXZ() {
		return new Vector2f(x, z);
	}

 /**
  * sets the components of a `Vector3f` object to the input values of `x`, `y`, and `z`.
  * 
  * @param x 3D position of the vector along the x-axis.
  * 
  * @param y 2D position of the vector in the Y-axis, which is updated to match the
  * value provided by the user.
  * 
  * @param z 3rd component of the `Vector3f` object, which is updated to match the new
  * value passed in the function call.
  * 
  * @returns a reference to the modified vector instance.
  * 
  * The returned output is an instance of the `Vector3f` class, which represents a 3D
  * vector in homogeneous coordinates.
  * The `x`, `y`, and `z` fields of the output represent the components of the vector,
  * respectively.
  * These components can take on any valid floating-point value within their respective
  * ranges, which are specified by the Java documentation for the `Vector3f` class.
  * The returned output is a modified copy of the original input vector, with the given
  * components updated to match the new values provided in the function call.
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
  * @param r 3D vector that contains the new values for the object's x, y, and z
  * components, which are then set as the object's own properties through the `set()`
  * method.
  * 
  * 	- `getX()`: Retrieves the x-coordinates of the input vector.
  * 	- `getY()`: Retrieves the y-coordinates of the input vector.
  * 	- `getZ()`: Retrieves the z-coordinates of the input vector.
  * 
  * @returns a reference to the same `Vector3f` object, with its components set to the
  * values passed as arguments.
  * 
  * 	- The output is a reference to the same `Vector3f` instance as the input parameter
  * `r`.
  * 	- The output contains the same values for the x, y, and z components as the input
  * parameter `r`.
  * 	- The output maintains the same state as the input parameter `r`, including any
  * modifications made to its components.
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
  * sets the value of the `x` field of the object to which it belongs.
  * 
  * @param x float value that is being assigned to the `x` field of the object being
  * modified by the `setX()` method.
  */
	public void setX(float x) {
		this.x = x;
	}

 /**
  * returns the value of the `y` field.
  * 
  * @returns a floating-point value representing the current value of the `y` field.
  */
	public float getY() {
		return y;
	}

 /**
  * sets the value of the object's `y` field to the input parameter.
  * 
  * @param y 2D coordinate of a point in the graphical user interface (GUI) of the application.
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
  * sets the value of the field `z` of its object reference to the input parameter `z`.
  * 
  * @param z 3D coordinate of an object in the x, y, and z dimensions, and assigns its
  * value to the `z` field of the class instance upon call execution.
  */
	public void setZ(float z) {
		this.z = z;
	}

 /**
  * compares two `Vector3f` objects based on their `x`, `y`, and `z` components,
  * returning `true` if they are identical, and `false` otherwise.
  * 
  * @param r 3D vector that is being compared to the current vector for equality.
  * 
  * `x`: The first component of the vector, which corresponds to the x-coordinate of
  * the point in 3D space.
  * 
  * `y`: The second component of the vector, which corresponds to the y-coordinate of
  * the point in 3D space.
  * 
  * `z`: The third component of the vector, which corresponds to the z-coordinate of
  * the point in 3D space.
  * 
  * @returns a boolean value indicating whether the vector's coordinates are equal to
  * those of the provided vector.
  */
	public boolean equals(Vector3f r) {
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

}
