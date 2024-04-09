package com.ch.math;

/**
 * is an extension of the Matrix3f class and provides additional functionality for
 * rotating and scaling 3D vectors. The class has several methods for manipulating
 * matrices, including initialization with a rotation matrix, multiplication with
 * another matrix, and getting the linear data as a float array. Additionally, there
 * are methods for transforming a vector using the rotation matrix, and setting or
 * getting specific values in the matrix.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

 /**
  * sets the elements of a `Matrix4f` object to identity values, ensuring that the
  * matrix is invertible and has no transformation effect.
  * 
  * @returns a reference to the initialized matrix.
  * 
  * 	- The matrix is a 4x4 identity matrix, with all elements set to either 0 or 1.
  * 	- The matrix has a consistent orientation, meaning that the rows and columns are
  * orthogonal.
  * 	- The matrix has a determinant of 1, indicating that it is an identity matrix.
  * 	- The matrix has no translation, meaning that its origin is at the origin of the
  * coordinate system.
  * 
  * Therefore, the output of the `initIdentity` function is a 4x4 identity matrix with
  * a determinant of 1 and consistent orientation.
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
  * initializes a matrix with translation values, where each element is set to the
  * corresponding coordinate value multiplied by a scalar factor of -1. The function
  * returns the initialized matrix.
  * 
  * @param x 3D translation component along the x-axis in the returned matrix.
  * 
  * @param y 2nd component of the translation vector and is assigned to the 1st column
  * of the matrix.
  * 
  * @param z 3rd translation dimension, which is added to the corresponding element
  * of the matrix.
  * 
  * @returns a reference to the original `Matrix4f` object.
  * 
  * 	- The matrix is the same as the input matrix, indicating that the translation has
  * been applied successfully.
  * 	- The data array of the matrix has been modified to reflect the translation, with
  * the corresponding elements being updated to represent the new position of the
  * translation origin.
  * 	- The dimensions of the matrix are unchanged, maintaining the original size and
  * shape of the matrix.
  * 	- The overall transformation effected by the matrix remains unchanged, as the
  * translation is applied independently of any other transformations that may have
  * been present in the input matrix.
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
  * initializes a rotation matrix `data` based on the provided Euler angles `x`, `y`,
  * and `z`. It computes the rotation matrices `rz`, `ry`, and `rx` using the Law of
  * Cosines, and then multiplies them to produce the final rotation matrix `data`.
  * 
  * @param x 3D rotation angle around the x-axis, which is used to calculate the
  * rotation matrix.
  * 
  * @param y 2D rotation angle about the z-axis, which is used to compute the rotation
  * matrix for the 3D rotation.
  * 
  * @param z 3D rotation axis around which the object will be rotated, and it is used
  * to calculate the rotation matrix `rz`.
  * 
  * @returns a `Matrix4f` object representing a rotation matrix based on the given x,
  * y, and z angles.
  * 
  * 	- `data`: This is an instance of the `Matrix4f` class, which represents a 4x4
  * matrix in homogeneous coordinates. The values of its elements are determined by
  * the arguments passed to the function.
  * 	- `rz`: This is an instance of the `Matrix4f` class, which represents a 4x4 matrix
  * in homogeneous coordinates. It is created by multiplying the sin and cos matrices
  * representing the rotation around the z-axis.
  * 	- `ry`: This is an instance of the `Matrix4f` class, which represents a 4x4 matrix
  * in homogeneous coordinates. It is created by multiplying the sin and cos matrices
  * representing the rotation around the y-axis.
  * 	- `rx`: This is an instance of the `Matrix4f` class, which represents a 4x4 matrix
  * in homogeneous coordinates. It is created by multiplying the sin and cos matrices
  * representing the rotation around the x-axis.
  * 	- `data`: This is an instance of the `Matrix4f` class, which represents a 4x4
  * matrix in homogeneous coordinates. It is the result of multiplying the `rz`, `ry`,
  * and `rx` matrices.
  * 
  * In summary, the output returned by the `initRotation` function is a 4x4 matrix
  * that represents the rotation of a 3D object around three different axes (x, y, and
  * z) based on the arguments passed to the function.
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
  * modifies a matrix to reflect a scaling transformation by setting elements of the
  * matrix.
  * 
  * @param x 1st component of the scale vector, which is multiplied with the matrix's
  * data elements to transform the matrix's shape and size.
  * 
  * @param y 2nd component of the scaling vector in the initialization of the matrix's
  * data.
  * 
  * @param z 2nd dimension of the matrix, which is being scaled by the function.
  * 
  * @returns a reference to the same Matrix4f object.
  * 
  * 1/ The return value is a reference to the same `Matrix4f` object that was passed
  * as an argument. This means that any changes made to the object in the function
  * will affect the original object.
  * 2/ The `data` array contains the 16 elements of the matrix, which are initialized
  * with the values provided in the function call. Each element is represented by a
  * floating-point number, which corresponds to the value of the corresponding component
  * of the matrix (e.g., `data[0][0]` represents the x-component of the matrix).
  * 3/ The `this` keyword in the return statement refers to the `Matrix4f` object that
  * was passed as an argument, indicating that the returned object is the same as the
  * one passed in.
  * 
  * Overall, the `initScale` function takes a scale factor for each axis and sets the
  * corresponding components of the matrix to those values, while maintaining the
  * overall structure and shape of the matrix.
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
  * initializes a matrix for perspective projection, where the field of view (fov) and
  * aspect ratio are used to calculate the values of each element in the matrix. The
  * zNear and zFar parameters determine the near and far clipping planes, respectively.
  * 
  * @param fov 90-degree half angle of the perspective projection, which determines
  * the field of view of the resulting matrix.
  * 
  * @param aspectRatio 2D aspect ratio of the viewport, which is used to calculate the
  * projection matrix's determinant and ensure correct perspective projection.
  * 
  * @param zNear near plane distance of the perspective projection, which determines
  * how much of the scene appears distorted or stretched when viewed from a particular
  * vantage point.
  * 
  * @param zFar 2D distance from the viewer at which objects become partially transparent,
  * and is used to calculate the near clipping plane of the perspective projection.
  * 
  * @returns a matrix that represents the perspective projection of a 3D scene from a
  * given field of view, aspect ratio, and near and far distances.
  * 
  * 	- The data array has 16 elements, with each element representing a component of
  * the 4x4 matrix.
  * 	- The elements of the data array are initialized based on the input parameters
  * fov, aspectRatio, zNear, and zFar. Specifically, the element at row 0, column 0
  * is set to 1/tan(fov/2*aspectRatio), while the remaining elements are set to 0.
  * 	- The element at row 1, column 1 is set to 1/tan(fov/2), and the remaining elements
  * in row 1 are set to 0.
  * 	- The element at row 2, column 0 is set to -zNear/zRange, while the remaining
  * elements in row 2 are set to 2*zFar*zNear/zRange.
  * 	- The element at row 3, column 0 is set to 1, and the remaining elements in row
  * 3 are set to 0.
  * 
  * Overall, the returned output represents a perspective projection matrix that can
  * be used to transform 3D points into screen coordinates for rendering.
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
  * initializes a matrix for orthographic projection, with dimensions corresponding
  * to the given aspect ratios and distances from the near plane to the far plane.
  * 
  * @param left left side of the orthographic projection and is used to scale the
  * matrix's rows.
  * 
  * @param right right-hand side of the orthographic projection, which is used to
  * calculate the values of the matrix's elements.
  * 
  * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection,
  * which is used to calculate the scaling factors for the width, height, and depth
  * dimensions of the matrix.
  * 
  * @param top 2D coordinate of the top-left corner of the orthographic projection,
  * which is used to calculate the coordinates of the upper-left corner of the projection
  * matrix.
  * 
  * @param near near clipping plane of the orthographic projection, which determines
  * the distance from the camera at which objects appear to shrink and become invisible.
  * 
  * @param far 3D point at far distance from the origin, which is used to calculate
  * the z-component of the matrix elements.
  * 
  * @returns a `Matrix4f` object representing an orthographic projection matrix.
  * 
  * 	- `data`: This is an array of length 16 that contains the elements of the
  * orthographic matrix. Each element is a floating-point number representing a value
  * in the matrix.
  * 	- `width`, `height`, and `depth`: These are instance variables of the class that
  * represent the dimensions of the orthographic projection.
  * 	- `near` and `far`: These are instance variables of the class that represent the
  * near and far clipping planes, respectively.
  * 	- The returned output is a reference to the same object as the function was called
  * on. This means that the output can be used to modify the matrix further or to
  * perform other operations on it.
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
  * generates a rotation matrix from a forward and up vector, returning the rotated
  * matrix for further use.
  * 
  * @param forward 3D direction of rotation.
  * 
  * 	- Normalization: The vector `forward` is normalized to have a length of 1, which
  * ensures that the rotation matrix has the correct dimensions and reduces numerical
  * instability.
  * 	- Direction: The direction of `forward` determines the axis of rotation, with the
  * positive direction indicating clockwise rotation around that axis.
  * 	- Length: The length of `forward` affects the magnitude of the resulting rotation
  * matrix, with longer vectors resulting in larger rotations.
  * 
  * @param up 3D axis along which the rotation will be applied.
  * 
  * 	- `up` is a normalized vector representing the up direction in 3D space.
  * 	- It has three components (x, y, and z) that represent the magnitude and direction
  * of the vector.
  * 
  * @returns a Matrix4f object representing a rotation matrix based on the provided
  * forward and up vectors.
  * 
  * The output is a Matrix4f object that represents a 4x4 rotation matrix.
  * 
  * The elements of the matrix are determined by the input vectors using the dot product
  * and cross product operations. Specifically, the elements in the top-left corner
  * are the dot product of the forward vector and the rotation axis (represented by
  * the up vector), while the remaining elements are the cross products of the forward
  * and up vectors with themselves.
  * 
  * The resulting matrix represents a rotation about the origin, as indicated by the
  * direction of the rotation axis (the up vector). The magnitude of the rotation is
  * determined by the length of the rotation axis.
  */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

 /**
  * initializes a matrix representing a rotation based on three input vectors: forward,
  * right, and up. It sets the elements of the matrix to the corresponding components
  * of the input vectors.
  * 
  * @param forward 3D vector of the direction of the rotation axis, which is used to
  * initialize the rotation matrix.
  * 
  * 	- `Vector3f forward`: This represents a 3D vector that points in the direction
  * of the rotation axis. It has three components: `x`, `y`, and `z`, which represent
  * the coordinates of the rotation axis along the x, y, and z axes, respectively.
  * 
  * @param up 3D vector that indicates the direction of up orientation in the rotation,
  * which is used to set the z-component of the rotation matrix.
  * 
  * 	- `up`: A Vector3f object representing the upward direction in 3D space. It has
  * three components: x, y, and z, which represent the magnitude and direction of the
  * upward vector.
  * 	- `r`: A Vector3f object representing the rightward direction in 3D space. It has
  * three components: x, y, and z, which represent the magnitude and direction of the
  * rightward vector.
  * 
  * @param right 3D rightward vector of the rotation and is used to initialize the
  * upper-left 3x3 submatrix of the matrix.
  * 
  * 	- `r.getX()` represents the x-coordinate of the right vector.
  * 	- `r.getY()` represents the y-coordinate of the right vector.
  * 	- `r.getZ()` represents the z-coordinate of the right vector.
  * 
  * The function then sets the corresponding elements of the matrix `data` to the
  * values of these coordinates.
  * 
  * @returns a new `Matrix4f` object that represents the rotation matrix.
  * 
  * The returned object is a `Matrix4f` instance, which represents a 4x4 homogeneous
  * transformation matrix. The data array of the matrix contains the rotation vectors
  * in column-major order, with the last column representing the translation vector.
  * The elements of the data array are assigned values based on the input vectors
  * provided. Specifically:
  * 
  * 	- `data[0][0]`: The x-component of the rightward vector
  * 	- `data[0][1]`: The y-component of the rightward vector
  * 	- `data[0][2]`: The z-component of the rightward vector
  * 	- `data[0][3]`: The w-component of the rightward vector, which is set to 0
  * 	- `data[1][0]`: The x-component of the upward vector
  * 	- `data[1][1]`: The y-component of the upward vector
  * 	- `data[1][2]`: The z-component of the upward vector
  * 	- `data[1][3]`: The w-component of the upward vector, which is set to 0
  * 	- `data[2][0]`: The x-component of the forward vector
  * 	- `data[2][1]`: The y-component of the forward vector
  * 	- `data[2][2]`: The z-component of the forward vector
  * 	- `data[2][3]`: The w-component of the forward vector, which is set to 0
  * 
  * The last three elements of the data array (`data[3][0]`, `data[3][1]`, and
  * `data[3][2]`) represent the translation vector in column-major order. These elements
  * are set to 0 for the upper triangular part of the matrix, and their values are
  * unused for the lower triangular part.
  * 
  * In summary, the returned output of the `initRotation` function is a 4x4 homogeneous
  * transformation matrix that represents a rotation around three mutually perpendicular
  * axes, followed by a translation along the z-axis.
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
  * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the result
  * of multiplying each element of the input vector by the corresponding elements of
  * a given matrix, followed by adding the results.
  * 
  * @param r 3D transformation to be applied to the output vector.
  * 
  * 	- `r.getX()` returns the x-coordinate of the vector.
  * 	- `r.getY()` returns the y-coordinate of the vector.
  * 	- `r.getZ()` returns the z-coordinate of the vector.
  * 	- `data[0][0]`, `data[0][1]`, `data[0][2]`, and `data[0][3]` are elements of a
  * 4D array, which are multiplied with the x, y, and z coordinates of `r` respectively
  * to form the output vector.
  * 	- Similarly, `data[1][0]`, `data[1][1]`, `data[1][2]`, and `data[1][3]` are
  * elements of a 4D array that are multiplied with the x, y, and z coordinates of `r`
  * respectively to form the output vector.
  * 	- `data[2][0]`, `data[2][1]`, `data[2][2]`, and `data[2][3]` are elements of a
  * 4D array that are multiplied with the x, y, and z coordinates of `r` respectively
  * to form the output vector.
  * 
  * @returns a new vector with the result of multiplying each component of the input
  * vector by the corresponding component of a set of coefficients, followed by addition
  * of the result.
  * 
  * 	- The output is a Vector3f object, which represents a 3D vector in homogeneous coordinates.
  * 	- The first three elements of the output represent the x, y, and z components of
  * the transformed vector, respectively.
  * 	- The fourth element of the output is set to the sum of the product of the
  * corresponding elements of the input vector and the transformation matrix, followed
  * by the addition of the scalar value of the third element of the input vector.
  */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

 /**
  * takes a `Matrix4f` object `r` and multiplies it with the current matrix, element-wise.
  * The result is assigned to a new `Matrix4f` object and returned.
  * 
  * @param r 4x4 matrix that will be multiplied with the current matrix, resulting in
  * a new 4x4 matrix representing the product of the two matrices.
  * 
  * `r`: A `Matrix4f` object that represents a 4x4 homogeneous matrix. It has four
  * rows and four columns, each containing 32-bit floating-point values representing
  * the components of the matrix.
  * 
  * @returns a new matrix with the product of the input matrices.
  * 
  * The `res` variable is initialized as a new instance of the `Matrix4f` class.
  * 
  * The elements of `res` are computed by multiplying the corresponding elements of
  * the input matrices `data` and `r`, using the standard matrix multiplication
  * algorithm. The resulting values are stored in the elements of `res`.
  * 
  * The `set` method is used to set the elements of `res` based on the input values.
  * The indices of the elements are `i` and `j`, which range from 0 to 3, representing
  * the rows and columns of the matrix, respectively. The values of the elements are
  * computed by multiplying the corresponding elements of `data` and `r`.
  * 
  * Overall, the `mul` function returns a new instance of the `Matrix4f` class that
  * represents the result of multiplying two 4x4 matrices.
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
  * returns an array of arrays representing a 2D matrix with a size of 4x4, filled
  * with values from a given 2D matrix `data`.
  * 
  * @returns an array of arrays, where each inner array has four elements representing
  * the input data.
  */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
 /**
  * returns an array of floats representing the linear data, which includes the values
  * at each position in the data array.
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
  * retrieves a value from a 2D array `data`, based on the specified `x` and `y`
  * indices. The value is returned as a `float`.
  * 
  * @param x 1D coordinate of the point in the data array to be retrieved.
  * 
  * @param y 2nd dimension of a 2D array, which is used to access the corresponding
  * element in the array at the position specified by the `x` parameter.
  * 
  * @returns a floating-point value representing the combination of two input coordinates.
  */
	public float get(int x, int y) {
		return data[x][y];
	}

 /**
  * sets the value of a field `data`.
  * 
  * @param data 2D array of floating-point values that will be stored and manipulated
  * by the `SetM()` method.
  * 
  * The `data` variable is a two-dimensional float array representing an unspecified
  * shape. It can be manipulated to access its elements using standard indexing notation,
  * such as `data[x][y]`. The length and width of each dimension are not explicitly
  * defined within the function, indicating that they may vary for different input values.
  */
	public void SetM(float[][] data) {
		this.data = data;
	}

 /**
  * sets a value at a specified coordinate in an array of integers and floats.
  * 
  * @param x 0-based index of the array element to be set.
  * 
  * @param y 2nd dimension of the data array being manipulated, and it is used to
  * specify the position of the element within the array where the value should be assigned.
  * 
  * @param value 3D point value that is assigned to the corresponding position in the
  * `data` array.
  */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

 /**
  * transforms an array of arrays into a new array with the same data, but transposed.
  */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
