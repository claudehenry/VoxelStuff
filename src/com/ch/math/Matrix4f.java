package com.ch.math;

/**
 * is a utility class for mathematical operations in 3D graphics, including matrix
 * multiplication, scaling, rotation, and translation. It provides a set of methods
 * to initialize matrices with different transformations, such as identity, rotation,
 * translation, and scale, and also provides methods to transform vectors and get the
 * matrix data in various formats.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * initializes a `Matrix4f` object to its identity matrix, which consists of rows and
	 * columns of zeros with a determinant of 1.
	 * 
	 * @returns a reference to the same instance of the `Matrix4f` class.
	 * 
	 * 	- The matrix is initialized with all elements set to their default values, which
	 * are 0 for the scalars and the identity matrix for the non-scalar matrices.
	 * 	- The returned object is a reference to the same matrix instance, allowing chaining
	 * of methods without creating a new copy of the matrix.
	 * 	- The `initIdentity` function does not modify the input matrix, ensuring that it
	 * can be safely called on any matrix without altering its original state.
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
	 * initializes a matrix with translation values for x, y, and z coordinates. It sets
	 * the corresponding elements of the matrix to the given values and returns the
	 * modified matrix reference.
	 * 
	 * @param x 3D translation component along the x-axis in the generated matrix.
	 * 
	 * @param y 2nd component of the translation vector and is used to set the y-coordinate
	 * of the translation in the matrix data.
	 * 
	 * @param z 3rd component of the translation vector, which is added to the matrix's
	 * second row.
	 * 
	 * @returns a new `Matrix4f` instance with the translation components applied to its
	 * elements.
	 * 
	 * 	- The returned output is a matrix with 16 elements, representing a 4D translation
	 * vector in homogeneous form (x, y, z, w).
	 * 	- The elements of the matrix are set to the following values:
	 * 	+ data[0][0] = 1: represents the x-component of the translation vector.
	 * 	+ data[0][1] = 0: represents the y-component of the translation vector.
	 * 	+ data[0][2] = 0: represents the z-component of the translation vector.
	 * 	+ data[0][3] = x: represents the x-component of the translation vector in the
	 * w-dimensional space.
	 * 	+ data[1][0] = 0: represents the y-component of the translation vector.
	 * 	+ data[1][1] = 1: represents the z-component of the translation vector.
	 * 	+ data[1][2] = 0: represents the t-component of the translation vector.
	 * 	+ data[1][3] = y: represents the y-component of the translation vector in the
	 * w-dimensional space.
	 * 	+ data[2][0] = 0: represents the z-component of the translation vector.
	 * 	+ data[2][1] = 0: represents the t-component of the translation vector.
	 * 	+ data[2][2] = 1: represents the z-component of the translation vector in the
	 * w-dimensional space.
	 * 	+ data[2][3] = z: represents the z-component of the translation vector.
	 * 	+ data[3][0] = 0: represents the t-component of the translation vector.
	 * 	+ data[3][1] = 0: represents the z-component of the translation vector.
	 * 	+ data[3][2] = 0: represents the w-component of the translation vector.
	 * 	+ data[3][3] = 1: represents the w-component of the translation vector.
	 * 
	 * In summary, the `initTranslation` function returns a matrix that represents a 4D
	 * translation vector in homogeneous form, where each element represents a component
	 * of the vector.
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
	 * initializes a rotation matrix based on three Euler angles (x, y, z) using the
	 * Rodrigues formula. It returns a new Matrix4f object representing the rotation.
	 * 
	 * @param x 3D rotation angle around the x-axis, which is used to compute the
	 * corresponding rotation matrix rx.
	 * 
	 * @param y 2D rotation axis around which the matrix will be rotated, and it is used
	 * to calculate the rotation matrix `ry`.
	 * 
	 * @param z 3D rotation axis around the z-axis, which is used to create a new rotation
	 * matrix by multiplying it with the existing rotation matrices created for the x and
	 * y axes.
	 * 
	 * @returns a new matrix that represents the rotation of a 3D object based on the
	 * provided angles.
	 * 
	 * 	- The `data` field is a 16-element array of floating-point values representing
	 * the rotation matrix. Each element of the array represents a component of the
	 * rotation matrix.
	 * 	- The matrix is represented in row-major order, meaning that the elements of each
	 * row are stored in ascending order within the row.
	 * 	- The matrix has a determinant of 1, indicating that it is an orthogonal rotation
	 * matrix (i.e., its inverse is equal to its transpose).
	 * 	- The matrix has a trace of 0, indicating that it does not rotate any axis by its
	 * own.
	 * 	- The matrix has a determinant of 0 in the upper-left 3x3 submatrix, indicating
	 * that the rotation is not invertible.
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
	 * sets the scale factors for a matrix, where the x, y, and z components are set to
	 * the input arguments. The function returns the modified matrix.
	 * 
	 * @param x 4th element of the scale matrix in the initialization process.
	 * 
	 * @param y 2D scaling factor for the vertical direction of the matrix.
	 * 
	 * @param z 2nd dimension of the scaling factor for the matrix, which is applied to
	 * the elements of the matrix.
	 * 
	 * @returns a reference to the same `Matrix4f` object.
	 * 
	 * 1/ The returned object is a `Matrix4f` instance, indicating that it is a 4D matrix
	 * with floating-point elements.
	 * 2/ The `data` array of the returned object contains the scale factors for each
	 * axis in column-major order (i.e., [x, y, z, 1]).
	 * 3/ Each element in the `data` array is a single float value representing the scale
	 * factor for its corresponding axis.
	 * 4/ The `this` keyword in the function signature indicates that the returned object
	 * refers to the same matrix instance as the function's parameter.
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
	 * initializes a matrix for perspective projection, setting elements to control the
	 * aspect ratio and field of view.
	 * 
	 * @param fov Field of View (FOV) angle of the matrix, which determines the aspect
	 * ratio of the viewport and is used to calculate the tan hyperbolic function for the
	 * near and far clipping planes.
	 * 
	 * @param aspectRatio 2D aspect ratio of the viewport, which determines how much of
	 * the image is visible in the perspective projection.
	 * 
	 * @param zNear near plane distance of the perspective projection matrix, which
	 * determines how much of the scene is visible and in what scale.
	 * 
	 * @param zFar 3D space far clipping plane distance, which is used to calculate the
	 * perspective divide and project the 3D space onto the image plane.
	 * 
	 * @returns a reference to the same matrix4f object.
	 * 
	 * 1/ The matrix data contains four elements:
	 * 		- data[0][0]: The near clip plane's distance from the origin, expressed as a fraction.
	 * 		- data[0][1]: The aspect ratio of the viewport, expressed as a fraction.
	 * 		- data[0][2]: The far clip plane's distance from the origin, expressed as a fraction.
	 * 		- data[0][3]: The z-range, expressed as a fraction.
	 * 2/ The matrix data has four rows and four columns, with each element representing
	 * a different coordinate in the viewport.
	 * 3/ The values stored in the matrix represent the perspective transformation for a
	 * given field of view (fov), aspect ratio (aspectRatio), near clip plane (zNear),
	 * and far clip plane (zFar).
	 * 4/ The returned matrix is a reference to the original matrix, meaning it can be
	 * used to transform points or other matrices within the same frame.
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
	 * initializes a matrix for orthographic projection, where the camera is placed at a
	 * specific location and dimensions, with a near and far clipping plane.
	 * 
	 * @param left left coordinate of the orthographic projection.
	 * 
	 * @param right right edge of the orthographic projection, which is used to calculate
	 * the scaling factors for the matrix.
	 * 
	 * @param bottom 2D coordinate of the bottom left corner of the orthographic projection,
	 * which determines the size and orientation of the viewport.
	 * 
	 * @param top 2D coordinate of the top side of the orthographic projection, which is
	 * used to determine the corresponding value in the output matrix.
	 * 
	 * @param near near plane of the orthographic projection, which determines the distance
	 * from the origin at which objects appear to be in perspective.
	 * 
	 * @param far 4th coordinate of the orthographic projection, which determines the
	 * distance from the camera to the farthest point in the 3D space.
	 * 
	 * @returns a Matrix4f object representing an orthographic projection matrix.
	 * 
	 * 	- `data`: This is an array of 16 float values, representing the elements of a 4x4
	 * orthographic projection matrix.
	 * 	- Each element of the array corresponds to a particular component of the matrix
	 * (e.g. data[0][0] represents the leftmost element of the matrix).
	 * 	- The values of each element are calculated using mathematical formulas and
	 * variables provided in the function, such as `width`, `height`, `depth`, `left`,
	 * `right`, `bottom`, `top`, `near`, and `far`.
	 * 	- The resulting matrix is a 4x4 orthographic projection matrix, which can be used
	 * to transform 3D coordinates into screen space.
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
	 * initializes a rotation matrix based on three input vectors: `forward`, `up`, and
	 * `u`. It computes the rotation axis (represented by `r`) and the rotation angle
	 * (represented by `u`). The resulting rotation matrix is returned.
	 * 
	 * @param forward 3D direction of the rotation axis.
	 * 
	 * 	- `forward` is a vector in 3D space with a magnitude and direction, represented
	 * by a Vector3f object.
	 * 
	 * @param up 3D direction perpendicular to the forward direction and is used to compute
	 * the cross product with the forward direction to obtain the rotation axis.
	 * 
	 * 	- `up` is a vector representing the upward direction in 3D space.
	 * 	- It is normalized to have a length of 1, indicating that it points in the direction
	 * of a unit vector.
	 * 	- The cross product of `f` and `r` produces another vector that is perpendicular
	 * to both `f` and `r`, representing the right-hand rule for determining the orientation
	 * of a 3D rotation matrix.
	 * 	- The final output of the function is a Matrix4f object that represents the
	 * initialized rotation matrix.
	 * 
	 * @returns a Matrix4f object representing a rotation matrix based on the provided
	 * forward and up vectors.
	 * 
	 * 	- The returned output is a Matrix4f object, which represents a 4x4 matrix.
	 * 	- The elements of the matrix are determined by the input vectors `forward`, `up`,
	 * and the cross product operations performed on them. Specifically, the columns of
	 * the matrix represent the rotation axis (represented by the vector `u`), the rotation
	 * angle (represented by the vector `f`), and the cross product of the rotation axis
	 * and the forward direction (represented by the vector `r`).
	 * 	- The matrix has a determinant of 1, indicating that it is an orthogonal matrix.
	 * 	- The matrix satisfies the properties of orthogonality, meaning that the dot
	 * product of any two different columns is zero.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * initializes a rotation matrix based on three vector inputs: forward, up, and right.
	 * It sets the elements of the rotation matrix to the corresponding components of the
	 * input vectors.
	 * 
	 * @param forward 3D direction of the rotation axis.
	 * 
	 * 	- `f`: The `forward` vector has three components representing the x, y, and z
	 * coordinates of the direction it points.
	 * 	- `r`: The `right` vector has three components representing the x, y, and z
	 * coordinates of the direction perpendicular to the plane defined by `forward`.
	 * 	- `u`: The `up` vector has three components representing the x, y, and z coordinates
	 * of the direction perpendicular to both `forward` and `right`.
	 * 
	 * The function then computes the corresponding components of the rotation matrix
	 * `data`, which represents the rotation from the original coordinate system to the
	 * new one defined by `forward`, `right`, and `up`. The last line returns the rotated
	 * matrix.
	 * 
	 * @param up 3D vector that points in the "up" direction and is used to initialize
	 * the rotation matrix's third column.
	 * 
	 * 	- `up`: This is a vector representing the upward direction in 3D space. It has
	 * three components - `x`, `y`, and `z` - which represent the coordinates of the
	 * upward direction in each dimension.
	 * 	- `r`: This is a vector representing the rightward direction in 3D space. It has
	 * three components - `x`, `y`, and `z` - which represent the coordinates of the
	 * rightward direction in each dimension.
	 * 
	 * @param right 3D right vector of the rotation axis, which is used to initialize the
	 * elements of the matrix's data array.
	 * 
	 * 	- `right`: This is a `Vector3f` object representing the rightward vector in the
	 * local coordinate system of the matrix. It has three components: `x`, `y`, and `z`,
	 * which represent the magnitude and direction of the rightward vector.
	 * 
	 * @returns a `Matrix4f` object that represents a rotation matrix based on three vectors.
	 * 
	 * 	- `data`: An array of 12 double values representing the elements of the matrix.
	 * 	+ Elements [0,0]: rx, ry, and rz components of the rotation matrix.
	 * 	+ Elements [0,1]: ux,uy, and us components of the rotation matrix.
	 * 	+ Elements [0,2]: fx, fy, and fz components of the translation vector.
	 * 	+ Elements [1,1]: lu value representing the scalar multiplication factor.
	 * 	- `this`: The object being returned, which is an instance of the `Matrix4f` class.
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
	 * takes a `Vector3f` object `r` and returns a new `Vector3f` object with the result
	 * of multiplying each component of the original vector by a set of coefficients and
	 * adding the result to a constant value.
	 * 
	 * @param r 3D transformation to be applied to the output vector.
	 * 
	 * 	- `getX()` and `getY()` represent the x- and y-coordinates of the input vector,
	 * respectively.
	 * 	- `getZ()` represents the z-coordinate of the input vector.
	 * 	- `data[0][0]`, `data[0][1]`, `data[0][2]`, and `data[0][3]` are component values
	 * of a larger data array, which is used to transform the input vector.
	 * 	- `data[1][0]`, `data[1][1]`, `data[1][2]`, and `data[1][3]` represent the component
	 * values of another larger data array, also used for transformation.
	 * 	- `data[2][0]`, `data[2][1]`, `data[2][2]`, and `data[2][3]` have similar meaning
	 * as `data[1][0]`, `data[1][1]`, etc.
	 * 
	 * @returns a new `Vector3f` object containing the result of multiplying each component
	 * of the input `Vector3f` by the corresponding components of a second `Vector3f`
	 * object, and then adding the results.
	 * 
	 * 	- The output is of type Vector3f, which represents a 3D point in homogeneous coordinates.
	 * 	- The first three elements of the output represent the x, y, and z components of
	 * the transformed point, respectively.
	 * 	- The fourth element of the output is an additional scalar value that represents
	 * the fourth component of the vector, which is usually set to 1 by default.
	 * 
	 * Overall, the `transform` function appears to be a simple transformation function
	 * that takes a Vector3f input and returns a new Vector3f object with the transformed
	 * coordinates.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * multiplies a matrix by another matrix, element-wise multiplying corresponding
	 * elements and storing the result in a new matrix.
	 * 
	 * @param r 4x4 matrix to which the current matrix will be multiplied.
	 * 
	 * 	- `r` is an instance of `Matrix4f`, which represents a 4x4 floating-point matrix.
	 * 	- The matrix elements are accessed through the `get` method, which returns the
	 * element at the specified position (row and column).
	 * 	- The `set` method sets a new value for the element at the specified position.
	 * 
	 * In summary, the function takes another `Matrix4f` object `r` as input, performs
	 * matrix multiplication, and returns the result as a new `Matrix4f` object.
	 * 
	 * @returns a new Matrix4f object containing the result of multiplying the input
	 * matrix `r` by the current matrix.
	 * 
	 * The `res` variable is assigned to a new `Matrix4f` object, which represents the
	 * result of multiplying the input matrices `data` and `r`.
	 * 
	 * The multiplication is performed element-wise, where each element of the output
	 * matrix is calculated by multiplying corresponding elements of the input matrices.
	 * The resulting values are stored in the output matrix.
	 * 
	 * The `set` method is used to set the elements of the output matrix. It takes two
	 * indices (`i` and `j`) as arguments, and sets the value of the element at position
	 * `(i, j)` to the result of multiplying the corresponding elements of the input
	 * matrices `data` and `r`.
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
	 * generates an array of arrays, where each inner array has a size of 4x4 and contains
	 * values from a larger 2D array `data`. The generated array is returned as a float
	 * array of arrays.
	 * 
	 * @returns an array of arrays, where each inner array has four elements representing
	 * the data at each position.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * returns an array of floats representing a set of data points in a linear format.
	 * 
	 * @returns an array of 12 float values representing the linear data at 4 points in
	 * a 3D space.
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
	 * retrieves a value from an array at position (x, y).
	 * 
	 * @param x 0-based index of a grid cell in the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function, and it
	 * is used to locate the specific value within the array that the function returns.
	 * 
	 * @returns the value of the `data` array at the specified coordinates (x, y).
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * sets the value of a private field `data`.
	 * 
	 * @param data 2D array of float values that will be stored and manipulated by the
	 * `SetM()` method.
	 * 
	 * 	- `data`: A float array of shape (n, m), where n and m are variables that determine
	 * the size of the array.
	 * 	- `n`: The number of rows in the array.
	 * 	- `m`: The number of columns in the array.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * updates a two-dimensional array `data` by replacing the value at position `(int
	 * x, int y)` with the provided `float` value.
	 * 
	 * @param x 0-based index of the array element to be updated.
	 * 
	 * @param y 2nd dimension of the data array being manipulated, and it is used to
	 * specify the position within that array where the value should be stored or updated.
	 * 
	 * @param value 3D coordinate value that will be assigned to the corresponding element
	 * in the 2D array `data`.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transforms an array of arrays into a new array with the same elements, but arranged
	 * in a different format.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
