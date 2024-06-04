package com.ch.math;

/**
 * Is a matrix multiplication class with various methods for manipulating matrices.
 * It has four parameters (data[0][0], data[1][0], data[2][0], and data[3][0]) that
 * represent the matrix's linear data, which can be accessed through getLinearData().
 * The class also provides methods for transforming vectors using the matrix, such
 * as transform(Vector3f r) and mul(Matrix4f r), as well as setting the matrix's data
 * through the SetM(float[][] data) method.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Sets the elements of a `Matrix4f` object to identity values, i.e., all rows and
	 * columns are identical.
	 * 
	 * @returns a matrix with identity values.
	 * 
	 * 	- The `Matrix4f` object is returned as the output.
	 * 	- The data members of the matrix are initialized to identity values, meaning that
	 * the matrix has an identity transformation when multiplied with itself.
	 * 	- All elements of the matrix are set to their identity values, which are 1 for
	 * the diagonal elements (column and row), 0 for the off-diagonal elements, and 0 for
	 * the remaining elements.
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
	 * Initializes a matrix with translation values for x, y, and z coordinates, and
	 * returns the modified matrix.
	 * 
	 * @param x 3D translation amount in the x-axis direction.
	 * 
	 * @param y 2D translation component of the matrix, where it is assigned to the third
	 * column of the matrix, `data[1][3]`.
	 * 
	 * @param z 3rd coordinate of the translation vector and is assigned the value `z`
	 * in the function.
	 * 
	 * @returns a reference to the original matrix.
	 * 
	 * 	- The `Matrix4f` object is returned, indicating that the method modifies the
	 * original matrix in place.
	 * 	- The data elements of the matrix are initialized with specific values, including
	 * 1 for the first column and row, 0 for the second column and row, and the input
	 * `x`, `y`, and `z` coordinates for the third column and row.
	 * 	- The resulting matrix will have a transformation that translates the origin to
	 * the point (`x`, `y`, `z`).
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
	 * Initializes a rotation matrix `data` based on three Euler angles `x`, `y`, and
	 * `z`. It creates separate rotation matrices for each axis (`rx`, `ry`, and `rz`)
	 * using the angles, then multiplies them together to form the final rotation matrix
	 * `data`.
	 * 
	 * @param x 3D rotation around the x-axis, which is used to compute the rotations
	 * around the y and z axes.
	 * 
	 * @param y 2D rotation angle around the z-axis, which is used to compute the rotation
	 * matrix rz.
	 * 
	 * @param z 3D rotation axis around which the rotation is performed, and it is used
	 * to compute the rotation matrix `rz`.
	 * 
	 * @returns a new `Matrix4f` object representing the rotation of the input vectors
	 * in a 3D space.
	 * 
	 * 	- The `data` field represents a 4x4 matrix, where each element is a 32-bit
	 * floating-point number.
	 * 	- The matrix contains the rotation information in the x, y, and z axes, which can
	 * be used to perform rotations in 3D space.
	 * 	- Each row (or column) of the matrix represents a different axis of rotation,
	 * with the first three rows representing the x, y, and z axes, respectively. The
	 * last row represents the identity matrix.
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
	 * Sets the scale factors for a matrix, by updating the respective elements of the
	 * data array. It returns the modified matrix object.
	 * 
	 * @param x 3D scale factor along the x-axis of the matrix.
	 * 
	 * @param y 2nd element of the scaling matrix.
	 * 
	 * @param z 2nd coordinate of the scaling vector, which is applied to the matrix's
	 * second dimension, affecting its translation component.
	 * 
	 * @returns a reference to the original matrix.
	 * 
	 * The returned object is an instance of the `Matrix4f` class.
	 * The matrix's data contains new values for the elements in the 0-3 and 12-15 ranges,
	 * representing a transformation that scales the matrix by factors x, y, and z along
	 * the x, y, and z axes, respectively.
	 * The returned object can be used to perform transformations on other matrices or
	 * objects using matrix multiplication.
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
	 * Initializes a matrix representing a perspective projection, where the x-axis is
	 * aligned with the view direction, and the y-axis is aligned with the screen aspect
	 * ratio. It sets the near and far clipping planes and computes the proper values for
	 * the rest of the matrix elements.
	 * 
	 * @param fov field of view of the matrix, which determines the aspect ratio of the
	 * projection and affects the perspective correction performed by the function.
	 * 
	 * @param aspectRatio 2D viewport's width-to-height ratio, which is used to scale the
	 * matrix's values to conform to the perspective projection.
	 * 
	 * @param zNear near plane distance of the perspective projection, which determines
	 * the position of the near clipping plane and affects the shape of the perspective
	 * view.
	 * 
	 * @param zFar 2D distance from the center of the view frustum at which the near plane
	 * is located, and it is used to calculate the aspect ratio of the frustum.
	 * 
	 * @returns a reference to a `Matrix4f` object that represents a perspective projection
	 * matrix.
	 * 
	 * 	- The matrix has 16 elements, consisting of four rows and four columns.
	 * 	- Each row represents a coordinate system transformation (rotation and translation)
	 * for a particular axis (x, y, z).
	 * 	- The elements in each row are the values of the transformations applied to the
	 * corresponding axes.
	 * 	- The matrix is normalized, meaning that its determinant is equal to 1, ensuring
	 * proper matrix multiplication.
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
	 * Initializes an orthographic projection matrix with specified aspect ratios and
	 * near/far planes. It sets the matrix elements to achieve correct perspective distortion.
	 * 
	 * @param left left coordinate of the orthographic projection.
	 * 
	 * @param right right side of the orthogonal projection, which is used to calculate
	 * the width of the matrix.
	 * 
	 * @param bottom 2D coordinate of the bottom edge of the orthographic projection,
	 * which is used to calculate the scaling factors for the x, y, and z dimensions of
	 * the matrix.
	 * 
	 * @param top 2D coordinate of the top-left corner of the orthographic projection,
	 * which is used to calculate the positions of the corners of the projection.
	 * 
	 * @param near near plane of the orthographic projection, and it is used to calculate
	 * the depth of objects in the image.
	 * 
	 * @param far 3D far clipping plane distance, which determines how far into the
	 * distance the matrix will project the 3D scene.
	 * 
	 * @returns a matrix representing an orthographic projection.
	 * 
	 * 	- The returned object is a matrix with four elements, representing the orthographic
	 * projection of a 3D space.
	 * 	- Each element in the matrix represents the coordinate of a point in the projected
	 * space, with the origin being the center of the projection.
	 * 	- The matrix has dimensions 4x4, indicating that each point in the projected space
	 * can be represented by four coordinates.
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
	 * Takes a forward and up vector and computes a rotation matrix based on them. The
	 * resulting matrix can be used to rotate objects in 3D space.
	 * 
	 * @param forward 3D direction in which the rotation will be applied.
	 * 
	 * @param up 3D up direction vector that is used to compute the rotation matrix.
	 * 
	 * @returns a matrix representing a rotation based on the input vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Initializes a rotation matrix based on three input vectors: `forward`, `right`,
	 * and `up`. It sets the elements of the matrix to represent the rotation around each
	 * axis.
	 * 
	 * @param forward 3D direction of rotation, which is multiplied with the identity
	 * matrix to create a rotation matrix.
	 * 
	 * 	- It is a 3D vector representing the direction of forward motion.
	 * 
	 * @param up 3D vector that points in the upward direction and is used to set the
	 * z-component of the rotation matrix.
	 * 
	 * 	- `up` is a vector representing the upward direction.
	 * 
	 * @param right 3D rightward vector, which is used to initialize the rotation matrix's
	 * columns.
	 * 
	 * 	- Right: A vector representing the direction of rotation in the xyz plane. It is
	 * normalized and has a magnitude of 1.
	 * 
	 * @returns a `Matrix4f` object representing the rotation matrix.
	 * 
	 * 	- The returned object is an instance of `Matrix4f`, which represents a 4x4 matrix.
	 * 	- The elements of the matrix are set based on the input vectors `forward`, `up`,
	 * and `right`. Specifically, the elements in the first row (i.e., `data[0]`), second
	 * column (i.e., `data[1]`), and third row (i.e., `data[2]`) are set to the corresponding
	 * components of the input vectors. The remaining elements (`data[3][*]`), which
	 * represent the determinant of the matrix, are set to 1.
	 * 	- The returned object is assigned back to the original instance, so the modifications
	 * made by the function are effective for future uses of the instance.
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
	 * Takes a `Vector3f` object `r` and returns a new `Vector3f` object with the result
	 * of multiplying each element of the input vector by the corresponding element of a
	 * pre-defined array, followed by addition of the original elements.
	 * 
	 * @param r 4D vector to be transformed, and its values are multiplied component-wise
	 * with the corresponding elements of the `data` array to produce the transformed vector.
	 * 
	 * @returns a new vector with the result of multiplying each component of the input
	 * vector `r` by the corresponding components of a pre-defined matrix `data`.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Takes a `Matrix4f` argument `r` and returns a new `Matrix4f` instance containing
	 * the product of the current matrix's elements and the corresponding elements of `r`.
	 * 
	 * @param r 4x4 matrix to be multiplied with the current matrix.
	 * 
	 * R is a matrix with 4 rows and columns. It has 4 elements per row.
	 * The values of R range from -1 to 1 for each element.
	 * 
	 * @returns a new matrix with the product of the input matrices.
	 * 
	 * The `res` variable is initialized as an instance of the `Matrix4f` class. The
	 * method returns this instance, indicating that it is a new object that represents
	 * the result of multiplying the input matrices.
	 * 
	 * The matrix elements are calculated by multiplying corresponding elements of the
	 * input matrices and storing them in the resulting matrix. Specifically, for each
	 * element in the first column of the input matrices (`data`), the corresponding
	 * element in the resulting matrix `res` is set to the product of the corresponding
	 * elements of the input matrices (`r`).
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
	 * Creates an array of floats of size 4x4 and returns it, containing values from a
	 * provided 2D array `data`.
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
	 * Returns an array of float values representing the data at various points in a
	 * linear system.
	 * 
	 * @returns an array of 12 float values representing the linear data.
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
	 * Retrieves a value from a 2D array `data` at the specified `x` and `y` coordinates,
	 * returning a float value.
	 * 
	 * @param x 1D coordinate of the point in the data array from which to retrieve the
	 * value.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function, and it
	 * determines the specific value within that dimension that the function returns.
	 * 
	 * @returns a float value representing the data at the specified position in the 2D
	 * array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Sets the value of the `data` field to a given array of floats.
	 * 
	 * @param data 2D array of floating-point values that will be stored in the `this.data`
	 * field.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Sets a value at a specific position in a two-dimensional array.
	 * 
	 * @param x 0-based index of a cell in the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array being manipulated by the function.
	 * 
	 * @param value 2D coordinate of the pixel to be set in the data array, with the x
	 * and y coordinates indicating the position in the array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Transposes an array of arrays, assigning each element to a corresponding position
	 * in the new array.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
