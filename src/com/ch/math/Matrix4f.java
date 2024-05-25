package com.ch.math;

/**
 * is a Java implementation of a 4x4 matrix object that can be used for various
 * mathematical operations. It has several methods for setting and getting the elements
 * of the matrix, as well as methods for transforming vectors and matrices. The class
 * also provides methods for multiplying the matrix with another matrix, getting the
 * linear data of the matrix, and transposing the matrix.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * sets the elements of a 4x4 matrix to identity values, making it useful for
	 * initialization purposes or as a starting point for further transformations.
	 * 
	 * @returns a `Matrix4f` object with identity matrix values.
	 * 
	 * 	- The returned object is the same as the original one, indicating that the function
	 * modifies the input matrix directly.
	 * 	- The data array of the matrix is initialized with values that satisfy the identity
	 * condition, meaning that the product of any two elements remains unchanged.
	 * Specifically, the elements in the first row and column are set to their appropriate
	 * values (1 for the first element in each row and column, and 0 otherwise). Similarly,
	 * the elements in the second row and column are also set to their appropriate values,
	 * and so on.
	 * 	- The resulting matrix has the same shape as the original one, with the same
	 * number of rows and columns.
	 * 	- The returned object is the same instance of the `Matrix4f` class, indicating
	 * that any references to the original matrix remain valid after calling `initIdentity`.
	 */
	public Matrix4f initIdentity() {
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * initializes a matrix with translation values in the x, y, and z directions. It
	 * modifies the matrix elements to reflect the translated position and returns the
	 * modified matrix.
	 * 
	 * @param x 3D translation component along the x-axis in the returned matrix.
	 * 
	 * @param y 2nd element of the translation vector, which is added to the corresponding
	 * component of the matrix's data array.
	 * 
	 * @param z 3rd component of the translation vector, which is added to the current
	 * position of the matrix.
	 * 
	 * @returns a new instance of the `Matrix4f` class with the given translation values
	 * applied to its elements.
	 * 
	 * 1/ The output is a reference to the original matrix object, indicating that the
	 * translation has been applied to the matrix itself.
	 * 2/ The matrix's data structure has been modified by setting specific elements to
	 * new values, depending on the input parameters x, y, and z. These modifications
	 * will affect how the matrix behaves when used for transformations or other mathematical
	 * operations.
	 * 3/ The elements of the matrix are now transformed in a way that represents a
	 * translation from the origin in the x, y, and z directions. Specifically, the x-axis
	 * element is negated, while the y- and z-axis elements are set to their respective
	 * input values.
	 * 4/ The returned matrix will have the same shape and dimensions as the original matrix.
	 */
	public Matrix4f initTranslation(float x, float y, float z) {
//        x = -x;
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = x;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = y;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = z;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * creates a rotation matrix based on three Euler angles (x, y, and z) using the
	 * Rodrigues formula. It then returns the resulting rotation matrix as a new instance
	 * of the `Matrix4f` class.
	 * 
	 * @param x 3D rotation around the x-axis and is used to calculate the z-component
	 * of the resulting rotation matrix.
	 * 
	 * @param y 2D rotation angle around the x-axis, which is used to compute the rotated
	 * values for the `ry` matrix.
	 * 
	 * @param z 3D rotation axis around which the rotation is performed, and its value
	 * determines the orientation of the resulting rotation matrix.
	 * 
	 * @returns a `Matrix4f` object representing the rotation matrix based on the given
	 * x, y, and z angles.
	 * 
	 * 	- `data`: This is an instance of the `Matrix4f` class, which represents a 4x4
	 * homogeneous transformation matrix. The matrix contains the rotation and scaling
	 * components of the input vectors.
	 * 	- `rz`: This is an instance of the `Matrix4f` class, which represents a 4x4
	 * rotation matrix. It is created by first rotating the x-axis, then the y-axis, and
	 * finally the z-axis separately using the `Math.toRadians()` method to convert the
	 * angles from degrees to radians.
	 * 	- `ry`: This is an instance of the `Matrix4f` class, which represents a 4x4
	 * rotation matrix. It is created by first rotating the y-axis, then the x-axis, and
	 * finally the z-axis separately using the `Math.toRadians()` method to convert the
	 * angles from degrees to radians.
	 * 	- `rx`: This is an instance of the `Matrix4f` class, which represents a 4x4
	 * rotation matrix. It is created by first rotating the x-axis, then the y-axis, and
	 * finally the z-axis separately using the `Math.toRadians()` method to convert the
	 * angles from degrees to radians.
	 * 	- `mul`: This is a method of the `Matrix4f` class that performs matrix multiplication.
	 * It takes another instance of the `Matrix4f` class as an argument and multiplies
	 * its elements with those of the current matrix.
	 * 	- `getData`: This is a method of the `Matrix4f` class that returns an array of
	 * 16 float values representing the components of the matrix.
	 */
	public Matrix4f initRotation(float x, float y, float z) {
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();

		x = (float) Math.toRadians(x);
		y = (float) Math.toRadians(y);
		z = (float) Math.toRadians(z);

		rz.data[0][0] = (float) Math.cos(z);
		rz.data[0][1] = -(float) Math.sin(z);
		rz.data[0][2] = 0;
		rz.data[0][3] = 0;
		rz.data[1][0] = (float) Math.sin(z);
		rz.data[1][1] = (float) Math.cos(z);
		rz.data[1][2] = 0;
		rz.data[1][3] = 0;
		rz.data[2][0] = 0;
		rz.data[2][1] = 0;
		rz.data[2][2] = 1;
		rz.data[2][3] = 0;
		rz.data[3][0] = 0;
		rz.data[3][1] = 0;
		rz.data[3][2] = 0;
		rz.data[3][3] = 1;

		rx.data[0][0] = 1;
		rx.data[0][1] = 0;
		rx.data[0][2] = 0;
		rx.data[0][3] = 0;
		rx.data[1][0] = 0;
		rx.data[1][1] = (float) Math.cos(x);
		rx.data[1][2] = -(float) Math.sin(x);
		rx.data[1][3] = 0;
		rx.data[2][0] = 0;
		rx.data[2][1] = (float) Math.sin(x);
		rx.data[2][2] = (float) Math.cos(x);
		rx.data[2][3] = 0;
		rx.data[3][0] = 0;
		rx.data[3][1] = 0;
		rx.data[3][2] = 0;
		rx.data[3][3] = 1;

		ry.data[0][0] = (float) Math.cos(y);
		ry.data[0][1] = 0;
		ry.data[0][2] = -(float) Math.sin(y);
		ry.data[0][3] = 0;
		ry.data[1][0] = 0;
		ry.data[1][1] = 1;
		ry.data[1][2] = 0;
		ry.data[1][3] = 0;
		ry.data[2][0] = (float) Math.sin(y);
		ry.data[2][1] = 0;
		ry.data[2][2] = (float) Math.cos(y);
		ry.data[2][3] = 0;
		ry.data[3][0] = 0;
		ry.data[3][1] = 0;
		ry.data[3][2] = 0;
		ry.data[3][3] = 1;

		data = rz.mul(ry.mul(rx)).getData();

		return this;
	}

	/**
	 * initializes a matrix with given scale values for the x, y, and z axes, returning
	 * the modified matrix.
	 * 
	 * @param x 4th element of the scale factor matrix.
	 * 
	 * @param y 2nd component of the scaling factor for the matrix.
	 * 
	 * @param z 2nd dimension of the scaling factor for the matrix, which is applied to
	 * the corresponding elements of the matrix.
	 * 
	 * @returns a reference to the original matrix object.
	 * 
	 * 1/ The returned object is a `Matrix4f` instance, indicating that it is a 4D matrix
	 * with float elements.
	 * 2/ The `data` array of the returned object contains the scale values for each
	 * dimension of the matrix, where the first dimension corresponds to the x-axis, the
	 * second dimension corresponds to the y-axis, and the third dimension corresponds
	 * to the z-axis. Each element in the array is a single float value representing the
	 * scale factor for that dimension.
	 * 3/ The returned object retains ownership of its `data` array, meaning it is
	 * responsible for managing its memory allocation and deallocation.
	 * 4/ The `initScale` function returns a reference to the same `Matrix4f` instance,
	 * allowing the caller to modify the matrix further without creating a new instance.
	 */
	public Matrix4f initScale(float x, float y, float z) {
		data[0][0] = x;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = y;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = z;
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * initializes a matrix for perspective projection, setting up the necessary
	 * transformations to project a 2D view into a 3D space with a specified field of
	 * view (FOV), aspect ratio, and near and far distances.
	 * 
	 * @param fov 90-degree angle of the field of view, which is used to calculate the
	 * tan of half of that angle and is multiplied by the aspect ratio to determine the
	 * scaling factor for the matrix.
	 * 
	 * @param aspectRatio 2D aspect ratio of the view frustum, which is used to scale the
	 * near and far planes of the perspective projection.
	 * 
	 * @param zNear near plane distance of the perspective projection, which determines
	 * the position of the near clipping plane and affects the appearance of objects in
	 * the foreground.
	 * 
	 * @param zFar 3D position of an object in the far distance from the camera, which
	 * is used to calculate the perspective projection matrix.
	 * 
	 * @returns a matrix representing the perspective projection of a 3D scene with a
	 * specified field of view, aspect ratio, near and far planes.
	 * 
	 * 	- `data`: This is an array of four float values that represent the components of
	 * the matrix.
	 * 	+ `data[0][0]`: The value of this element is 1 / (tan(half FOV) * aspect ratio).
	 * 	+ `data[0][1]`: The value of this element is 0.
	 * 	+ `data[0][2]`: The value of this element is 0.
	 * 	+ `data[0][3]`: The value of this element is 0.
	 * 	+ `data[1][0]`: The value of this element is 0.
	 * 	+ `data[1][1]`: The value of this element is 1 / tan(half FOV).
	 * 	+ `data[1][2]`: The value of this element is 0.
	 * 	+ `data[1][3]`: The value of this element is 0.
	 * 	+ `data[2][0]`: The value of this element is 0.
	 * 	+ `data[2][1]`: The value of this element is the negative of the sum of zNear and
	 * zFar divided by the range.
	 * 	+ `data[2][2]`: The value of this element is -zNear - zFar.
	 * 	+ `data[2][3]`: The value of this element is 2 * zFar * zNear / range.
	 * 	+ `data[3][0]`: The value of this element is 0.
	 * 	+ `data[3][1]`: The value of this element is 0.
	 * 	+ `data[3][2]`: The value of this element is 1.
	 * 	+ `data[3][3]`: The value of this element is 0.
	 */
	public Matrix4f initPerspective(float fov, float aspectRatio, float zNear, float zFar) {
		float tanHalfFOV = (float) Math.tan(Math.toRadians(fov) / 2);
		float zRange = zNear - zFar;

		data[0][0] = 1.0f / (tanHalfFOV * aspectRatio);
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = 1.0f / tanHalfFOV;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = (-zNear - zFar) / zRange;
		data[2][3] = 2 * zFar * zNear / zRange;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 1;
		data[3][3] = 0;

		return this;
	}

	/**
	 * initializes a matrix representing an orthographic projection, where the near and
	 * far planes are separated by a distance `far - near`, and the left and right edges
	 * are separated by a distance `right - left`. The matrix is returned as the result
	 * of the function.
	 * 
	 * @param left left edge of the orthographic projection, which is used to calculate
	 * the scaling factors for the x-axis in the matrix.
	 * 
	 * @param right right edge of the orthographic projection, which is used to calculate
	 * the values for the matrix's data elements.
	 * 
	 * @param bottom 2D coordinate of the bottom left corner of the orthographic view
	 * frustum, which is used to calculate the scale factors for the width, height, and
	 * depth of the frustum.
	 * 
	 * @param top 2D coordinate of the top-left corner of the orthographic projection,
	 * which is used to calculate the corresponding 3D coordinate in the matrix data structure.
	 * 
	 * @param near near plane of the orthographic projection, and it is used to compute
	 * the corresponding value for the depth component of the resulting matrix.
	 * 
	 * @param far 4th coordinate of the orthographic projection, which determines how the
	 * perspective view is scaled from near to far.
	 * 
	 * @returns a new `Matrix4f` object that represents an orthographic projection matrix.
	 * 
	 * 	- `data`: This is an array of 16 floating-point values that represent the components
	 * of a 4x4 orthographic projection matrix. Each element of the array is assigned a
	 * specific value based on the input parameters.
	 * 	- `width`, `height`, and `depth`: These are the dimensions of the orthographic
	 * projection, which are used to compute the values of the matrix components.
	 * 	- `near` and `far`: These are the near and far clipping planes, respectively,
	 * which determine the range of points that are clipped by the orthographic projection.
	 * 
	 * Overall, the returned output is an instance of a 4x4 orthographic projection matrix
	 * that can be used to project 3D points and surfaces into a 2D image plane.
	 */
	public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		data[0][0] = 2 / width;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = -(right + left) / width;
		data[1][0] = 0;
		data[1][1] = 2 / height;
		data[1][2] = 0;
		data[1][3] = -(top + bottom) / height;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = -2 / depth;
		data[2][3] = -(far + near) / depth;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * generates a rotation matrix from three vectors: `forward`, `up`, and `u`. The
	 * rotation is created by cross-producting the vectors, resulting in a matrix that
	 * represents the desired orientation.
	 * 
	 * @param forward 3D forward vector of the rotation.
	 * 
	 * The `forward` vector has three components, which represent the x, y, and z axes
	 * of a 3D space. It is normalized, meaning that its magnitude (or length) is equal
	 * to 1. This ensures that the rotation matrix generated by the function will have
	 * units of radians, rather than any other unit of measurement.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis.
	 * 
	 * 	- `up`: A normalized vector representing the upward direction in 3D space.
	 * 	- `r`: The cross product of `f` and `up`, which represents the right-hand rotation
	 * axis.
	 * 
	 * @returns a Matrix4f object representing a rotation matrix based on the provided
	 * forward and up vectors.
	 * 
	 * 	- The output is a Matrix4f object representing a rotation transformation.
	 * 	- The matrix has three columns, each representing a coordinate system (x, y, and
	 * z) for the rotation.
	 * 	- The elements of the matrix are defined as follows:
	 * 	+ F11: The x-axis rotation angle
	 * 	+ F12: The y-axis rotation angle
	 * 	+ F13: The z-axis rotation angle
	 * 	+ F21: The x-y plane rotation angle
	 * 	+ F22: The y-z plane rotation angle
	 * 	+ F23: The x-z plane rotation angle
	 * 	+ F31: The z-x axis rotation angle
	 * 	+ F32: The z-y plane rotation angle
	 * 	+ F33: The z-z axis rotation angle.
	 * 	- These angles can be positive or negative, indicating clockwise or counterclockwise
	 * rotation around each axis, respectively.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * sets the elements of a Matrix4f object to represent a rotation based on three
	 * vectors: forward, right, and up.
	 * 
	 * @param forward 3D direction vector that points towards the front of the rotating
	 * object, which is used to initialize the rotation matrix.
	 * 
	 * 	- `forward`: A Vector3f object representing the direction of motion, which contains
	 * the x, y, and z components of the direction from the origin to the end point of
	 * the motion. The x, y, and z components of `forward` have values between -1 and 1,
	 * representing the magnitude of the direction.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis and is used to set the
	 * z-component of the rotation matrix.
	 * 
	 * 	- `up`: A 3D vector representing the up direction in the rotation. It has x, y,
	 * and z components, which are assigned to the corresponding elements of the `data`
	 * array.
	 * 
	 * @param right 3D right vector of the rotation, which is used to initialize the
	 * upper-left 3x3 submatrix of the returned `Matrix4f` object.
	 * 
	 * 	- `right`: A `Vector3f` object representing the right-hand orientation vector in
	 * 3D space. It has three components: `x`, `y`, and `z`.
	 * 
	 * @returns a reference to the same `Matrix4f` instance.
	 * 
	 * 	- The output is a `Matrix4f` object, which represents a 4x4 homogeneous transformation
	 * matrix.
	 * 	- The elements of the matrix are initialized with values from the input vectors
	 * `forward`, `up`, and `right`. Specifically, the rows of the matrix correspond to
	 * the x, y, and z axes, respectively, while the columns represent the rotations
	 * around those axes.
	 * 	- The elements of the matrix are arranged in a specific order, following the
	 * convention of homogeneous transformation matrices. This means that the first three
	 * elements of each row represent the rotation around the respective axis, while the
	 * fourth element represents the scale factor for that axis.
	 * 	- The resulting matrix has the following properties:
	 * 	+ It represents a 4x4 transformation matrix, which can be used to transform points
	 * and vectors in 3D space.
	 * 	+ Its elements are arranged in a specific order, which allows it to be easily
	 * composed with other matrices to perform more complex transformations.
	 * 	+ It is a homogeneous transformation matrix, which means that the fourth element
	 * of each row is always equal to 1, allowing for easy scaling of the transformed
	 * points and vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
		Vector3f f = forward;
		Vector3f r = right;
		Vector3f u = up;

		data[0][0] = r.getX();
		data[0][1] = r.getY();
		data[0][2] = r.getZ();
		data[0][3] = 0;
		data[1][0] = u.getX();
		data[1][1] = u.getY();
		data[1][2] = u.getZ();
		data[1][3] = 0;
		data[2][0] = f.getX();
		data[2][1] = f.getY();
		data[2][2] = f.getZ();
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	/**
	 * transforms a vector `r` by multiplying each component with corresponding values
	 * from an array `data`. The resulting vector is returned.
	 * 
	 * @param r 3D transformation to be applied to the output vector.
	 * 
	 * 	- `r.getX()`: returns the x-component of the input vector.
	 * 	- `r.getY()`: returns the y-component of the input vector.
	 * 	- `r.getZ()`: returns the z-component of the input vector.
	 * 	- `data[0][0]`, `data[0][1]`, `data[0][2]`, and `data[0][3]`: these are the
	 * components of a 4D vector, which are multiplied by the corresponding components
	 * of `r` to produce the transformed vector.
	 * 
	 * @returns a new Vector3f object with the dot product of the input vector and each
	 * element of the input vector multiplied by the corresponding elements of the
	 * transformation matrix.
	 * 
	 * The returned output is of type `Vector3f`, indicating that it represents a 3D
	 * vector in homogeneous coordinates.
	 * 
	 * The first three elements of the output represent the transformed x, y, and z
	 * components of the input vector, respectively. These elements are derived from the
	 * original data array through multiplications with the corresponding components of
	 * the input vector.
	 * 
	 * The fourth element of the output is a summary of the remaining data in the original
	 * array, which has been discarded during the transformation process. This value is
	 * computed as the sum of the elements in the second and third dimensions of the
	 * original array, scaled by the product of the corresponding components of the input
	 * vector.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * multiplies a matrix by another matrix, element-wise multiplying the corresponding
	 * elements of each matrix and storing the result in a new matrix.
	 * 
	 * @param r 4x4 matrix that multiplies the current matrix, resulting in a new 4x4 matrix.
	 * 
	 * 	- `r` is a `Matrix4f` object representing a 4x4 homogeneous transformation matrix.
	 * 	- It has four elements (0, 1, 2, and 3) that correspond to the components of the
	 * matrix.
	 * 	- Each element is a floating-point number representing the value of the corresponding
	 * component of the matrix.
	 * 
	 * @returns a new matrix `res` containing the result of multiplying the input matrices.
	 * 
	 * The `res` object is created as a new instance of `Matrix4f`.
	 * 
	 * The values of the elements in the output matrix are calculated by multiplying
	 * corresponding elements in the input matrices using the given indices. Specifically,
	 * for each element (i, j) in the output matrix, the value is set to the result of
	 * multiplying the element at position (0, j) of the first input matrix by the element
	 * at position i of the second input matrix, plus the result of multiplying the element
	 * at position i of the first input matrix by the element at position (1, j) of the
	 * second input matrix, and so on.
	 * 
	 * Therefore, the output matrix represents the matrix product of the two input matrices.
	 */
	public Matrix4f mul(Matrix4f r) {
		Matrix4f res = new Matrix4f();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res.set(i, j, data[i][0] * r.get(0, j) + data[i][1] * r.get(1, j) + data[i][2] * r.get(2, j) + data[i][3] * r.get(3, j));
			}
		}

		return res;
	}

	/**
	 * creates a new `float[][]` array and populates it with values from the input `data`
	 * array using a nested loop. It returns the created array.
	 * 
	 * @returns an array of arrays, where each inner array has four elements representing
	 * the data at different positions.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * returns an array of floats containing the data at different positions of a linear
	 * system.
	 * 
	 * @returns an array of 12 float values.
	 */
	public float[] getLinearData() {
		return new float[] {
			data[0][0],
			data[1][0],
			data[2][0],
			data[3][0],
			data[0][1],
			data[1][1],
			data[2][1],
			data[3][1],
			data[0][2],
			data[1][2],
			data[2][2],
			data[3][2],
			data[0][3],
			data[1][3],
			data[2][3],
			data[3][3],
		};
	}

	/**
	 * retrieves a value from a 2D array `data` based on the input coordinates `x` and `y`.
	 * 
	 * @param x 1D coordinate of the point within the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function, which is
	 * used to locate the specific value within the array that the function returns.
	 * 
	 * @returns a floating-point value representing the data at the specified position
	 * in a 2D array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * sets the `data` field of the current object to the input `float[][]` array.
	 * 
	 * @param data 2D array of floating-point values that will be assigned to the `data`
	 * field of the function's caller.
	 * 
	 * 	- The `float[][]` data type represents an array of arrays, each containing
	 * floating-point numbers.
	 * 	- The `this.data` attribute assigns the input `data` to a field in the calling object.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * sets a value at a specific position in a two-dimensional array.
	 * 
	 * @param x 0-based index of the row in the 2D array where the value should be assigned.
	 * 
	 * @param y 2nd dimension of the data array being manipulated, and it provides the
	 * position of the element within that dimension.
	 * 
	 * @param value 2D coordinate point where the value is being assigned to the data array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transforms an array of floats `data` into a new array `tr` with elements swapped
	 * horizontally. The original array `data` is then replaced with the transformed array
	 * `tr`.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
