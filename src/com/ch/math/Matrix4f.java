package com.ch.math;

/**
 * Represents a 4x4 matrix with operations for initializing various types of
 * transformations, such as identity, translation, rotation, and scaling. It also
 * includes methods for performing multiplication between matrices, transforming
 * vectors, and accessing matrix data. The class provides an implementation for common
 * mathematical operations related to 3D graphics and linear algebra.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Initializes a matrix to an identity matrix, where non-diagonal elements are set
	 * to zero and diagonal elements are set to one. The function returns the initialized
	 * matrix, allowing method chaining. This is typically used as an initialization step
	 * for matrices used in transformations or calculations.
	 *
	 * @returns a 4x4 matrix with diagonal elements set to 1 and others set to 0.
	 *
	 * The matrix is an identity matrix with diagonal elements equal to 1 and off-diagonal
	 * elements equal to 0. The size of the matrix is assumed to be 4x4 based on its use
	 * as a Matrix4f object.
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
	 * Initializes a translation matrix with the specified x, y, and z coordinates. The
	 * values are hardcoded into a predefined 4x4 matrix layout, setting up a transformation
	 * to move along the specified axes. The result is returned for chaining.
	 *
	 * @param x 4th column of the translation matrix, specifying the x-axis translation
	 * amount.
	 *
	 * @param y 4th column element of the translation matrix at row index 1, effectively
	 * setting the y-coordinate of the translation vector.
	 *
	 * @param z 3D translation along the z-axis, which is stored in the fourth column of
	 * the matrix's third row.
	 *
	 * @returns a 4x4 translation matrix with specified x, y, and z values.
	 *
	 * The output is a 4x4 homogeneous transformation matrix representing a translation
	 * operation in 3D space. It has non-zero elements only on its top-left 3x3 submatrix
	 * and the fourth column, which corresponds to the translation vector (x, y, z).
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
	 * Initializes a rotation matrix based on input angles x, y, and z. It creates
	 * individual matrices for rotation around each axis and combines them to produce a
	 * single transformation matrix, which is then stored in the object's internal data
	 * structure.
	 *
	 * @param x 3D rotation angle around the X-axis, which is used to construct an
	 * orthogonal matrix representing a counter-clockwise rotation by that angle.
	 *
	 * @param y rotation angle around the y-axis, used to construct an Euler rotation matrix.
	 *
	 * @param z 3D rotation angle around the z-axis in degrees, which is used to compute
	 * the corresponding rotation matrix for an object's orientation transformation.
	 *
	 * @returns a matrix representing the combined rotation around x, y, and z axes.
	 *
	 * The returned Matrix4f is a 4x4 orthogonal rotation matrix representing a rotation
	 * around the x, y, and z axes in 3D space. The matrix has a determinant of +1 or -1
	 * depending on whether it represents an even or odd number of rotations.
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
	 * Initializes a scale transformation matrix with the specified x, y, and z values.
	 * The function sets up a 4x4 matrix where non-diagonal elements are set to zero and
	 * diagonal elements correspond to scaling factors along each axis. It then returns
	 * the modified object instance.
	 *
	 * @param x 4th column of the first row in the matrix, which corresponds to the X-axis
	 * scaling factor when the matrix is used for transformation.
	 *
	 * @param y 2D scale factor along the Y-axis, setting the value of the matrix's middle
	 * row, column 1.
	 *
	 * @param z 3D scale factor along the z-axis of the matrix, which affects scaling
	 * transformations in 3D space.
	 *
	 * @returns a Matrix4f with scale transformation applied on x, y, and z axes.
	 *
	 * The output is an instance of the current object. It represents a 4x4 matrix with
	 * scale transformations applied to its first three columns (the last column remains
	 * unchanged).
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
	 * Initializes a perspective projection matrix based on provided field-of-view (FOV),
	 * aspect ratio, and near/far clipping planes. It sets up the matrix elements to
	 * transform 3D coordinates into 2D screen space, enabling correct projection of
	 * objects within the specified FOV range.
	 *
	 * @param fov field of view angle, which determines how wide the perspective projection
	 * is.
	 *
	 * @param aspectRatio ratio of the width to the height of the view's field of vision,
	 * used to calculate the projection matrix's values for elements [0][0] and [1][0].
	 *
	 * @param zNear near clipping plane distance, used to calculate the perspective
	 * projection matrix elements that determine how objects are transformed from 3D space
	 * to screen space.
	 *
	 * @param zFar distance from the viewer to the far clipping plane, determining where
	 * objects are culled from view.
	 *
	 * @returns a 4x4 matrix representing a perspective projection transformation.
	 *
	 * The output is an instance of Matrix4f. Its structure consists of 16 elements
	 * organized in a 4x4 matrix layout. The diagonal and upper triangular elements have
	 * unique values.
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
	 * Initializes a 4x4 matrix representing an orthographic projection with a given left,
	 * right, bottom, top, near, and far clipping planes, then returns the modified object.
	 * The resulting matrix scales x-coordinates horizontally, y-coordinates vertically,
	 * and z-coordinates depth-wise according to the provided clipping planes.
	 *
	 * @param left x-coordinate of the left boundary of the orthographic projection
	 * frustum, influencing the calculation of the matrix's first row and third column elements.
	 *
	 * @param right 3D coordinate of the right edge of the viewing frustum, used to
	 * calculate the x-axis scale factor for an orthographic projection matrix.
	 *
	 * @param bottom Y-coordinate of the lower-left corner of the orthographic projection,
	 * which is used to calculate the perspective division element for row 1 of the
	 * transformation matrix.
	 *
	 * @param top 4th coordinate of the projected image plane, influencing the position
	 * and scale of objects rendered within the orthographic projection.
	 *
	 * @param near distance between the viewer's position and the near clipping plane,
	 * used to calculate the depth scale factor of the orthographic projection matrix.
	 *
	 * @param far distance from the view plane to the far clipping plane, used to calculate
	 * the depth component of an orthographic projection matrix.
	 *
	 * @returns a modified Matrix4f object representing an orthographic projection matrix.
	 *
	 * The matrix is an orthogonal projection transformation.
	 * It preserves angles and shapes but not sizes or positions.
	 * It is used for rendering 2D graphics in a specific viewport.
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
	 * Normalizes a forward vector and computes two orthogonal vectors: right and up. The
	 * right vector is computed by taking the cross product of the normalized forward
	 * vector and an input up vector. These three vectors form the basis for rotation
	 * transformation matrices.
	 *
	 * @param forward 3D forward direction vector that is used to compute the rotation matrix.
	 *
	 * @param up 3D up direction vector, which is used to compute an orthonormal basis
	 * for the matrix through cross product operations with the `forward` vector and the
	 * result of the first cross operation.
	 *
	 * @returns a rotation matrix initialized with the given orientation.
	 * It describes a rotation from the world coordinate system to the object's local
	 * coordinate system.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Initializes a 4x4 rotation matrix with the given forward, up, and right vectors.
	 * The matrix is populated with values from these vectors to represent a rotation
	 * transformation, leaving the last row as the identity vector for translation purposes.
	 * The resulting matrix is returned.
	 *
	 * @param forward 3D forward direction vector, which is used to populate the third
	 * row of the rotation matrix.
	 *
	 * It contains x, y, and z coordinates, which represent its components along the
	 * respective axes. The x component is f.getX(), the y component is f.getY(), and the
	 * z component is f.getZ().
	 *
	 * @param up 3D up direction vector and its components are used to populate the second
	 * row of the matrix, specifically data[1][0] through data[1][2].
	 *
	 * Up vector is a unit vector in the up direction. Its X, Y and Z components represent
	 * the coordinates along the up axis.
	 *
	 * Properties:
	 * - Unit vector: has length 1
	 * - Directional: represents an arbitrary direction in 3D space
	 *
	 * @param right 3D vector that defines the orientation of the x-axis in the resulting
	 * rotation matrix, setting its x-component and y- and z-components to that of the
	 * right vector respectively.
	 *
	 * Right has x, y, and z components that define its direction.
	 * Its magnitude or length is not provided in this context.
	 *
	 * @returns a 4x4 rotation matrix populated with vector data.
	 *
	 * A 4x4 rotation matrix is created with specified forward, up, and right vectors as
	 * its first three rows. The resulting matrix has linearly independent columns.
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
	 * Multiplies a given vector by a 3x4 transformation matrix stored in a 2D array,
	 * applying it to each component of the input vector and returning the resulting
	 * transformed vector. The function performs affine transformations such as translation,
	 * rotation, or scaling on the input vector.
	 *
	 * @param r 3D vector to be transformed, whose components (x, y, z) are used for
	 * calculations in the function.
	 *
	 * @returns a transformed Vector3f object.
	 * It represents a new position after applying a linear transformation.
	 * Its coordinates are derived from matrix multiplication with input Vector3f.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies a given matrix by another 4x4 matrix. The result is a new matrix where
	 * each element is the dot product of the corresponding row from the first matrix and
	 * column from the second matrix.
	 *
	 * @param r 4x4 matrix to be multiplied with the current matrix, with its elements
	 * used for multiplication and accumulation operations within the function.
	 *
	 * Matrix4f r is a 4x4 matrix with data elements stored in a two-dimensional array.
	 * Its dimensions allow for spatial transformations involving translation, rotation
	 * and scaling operations.
	 *
	 * @returns a new Matrix4f object representing the product of two input matrices.
	 *
	 * The returned Matrix4f object represents the product of two 4x4 matrices, resulting
	 * in another 4x4 matrix. Its elements correspond to the dot products of rows from
	 * the first matrix with columns from the second matrix.
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
	 * Initializes a 4x4 matrix and copies data from another 2D array into it, returning
	 * the resulting matrix. The data is copied row by row from the original array to the
	 * new one. The result is then returned as a float[][] object.
	 *
	 * @returns a 2D array of floats with specific values copied from another array. The
	 * array has dimensions 4x4.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Retrieves a linear array of float values from a multidimensional data structure,
	 * presumably representing pixel or coordinate data in three dimensions. The result
	 * is an array containing each row's first element, followed by its second and so on
	 * for all rows. It returns this one-dimensional array.
	 *
	 * @returns an array of 16 consecutive float values.
	 * These values represent four rows of data from a two-dimensional array.
	 * Values are ordered column-major, with each row contiguous.
	 *
	 * The return type is an array of floating-point numbers. The size of the array is
	 * 16 elements.
	 *
	 * The data is organized in a specific pattern with values from data[0][0] to data[3][3].
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
	 * Retrieves a value from a multi-dimensional array `data` at specified indices `x`
	 * and `y`, returning it as a floating-point number. The function does not perform
	 * any error checking on the input values, assuming they are valid indices for the
	 * array. A value is returned.
	 *
	 * @param x 2D array index corresponding to the row of the data value to be retrieved.
	 *
	 * @param y 2D array index that corresponds to the second dimension of the `data` array.
	 *
	 * @returns a float value stored in a 2D array at specified coordinates.
	 * It retrieves and returns data[x][y] from an unspecified 2D array "data".
	 * The retrieved value is of type float.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Sets the internal state of an object by assigning a two-dimensional array of
	 * floating-point numbers to it. This function replaces any existing data with the
	 * new input data. The assignment is direct and immediate, with no validation or
	 * processing of the input.
	 *
	 * @param data 2D array of floating-point numbers to be assigned to the instance
	 * variable `this.data`.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Assigns a specified floating-point value to a specific location in a multi-dimensional
	 * array, identified by two integer indices. The value is set at position x and y in
	 * the data structure. This operation updates an existing element with new content.
	 *
	 * @param x 1D index of a 2D array element.
	 *
	 * @param y 1D array index corresponding to the desired element position in the `data`
	 * array.
	 *
	 * It serves as an offset into a 2D array represented by a 1D array, suggesting a
	 * packed or flattened layout of multi-dimensional data.
	 *
	 * @param value 3D data point to be assigned to the specified coordinates `x` and `y`.
	 * It is stored at those coordinates within a multi-dimensional array, which presumably
	 * contains 3D data points.
	 * This assignment operation updates the value of the corresponding element in the array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Creates a transposed version of its own array, copying elements from the original
	 * 4x4 matrix to a new one, and then replaces the original with the transposed version.
	 * This effectively swaps the rows and columns of the matrix. The result is a matrix
	 * where rows become columns.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
