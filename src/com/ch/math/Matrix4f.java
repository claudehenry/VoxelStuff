package com.ch.math;

/**
 * Is designed to represent and manipulate 4x4 matrices used in computer graphics for
 * transformations such as rotation, scaling, translation, perspective projection,
 * and orthographic projection. It provides methods for initializing matrices with
 * different types of transformations, performing matrix multiplication, and transforming
 * vectors using the matrix operations.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Initializes a matrix with an identity value, where each element on the main diagonal
	 * is set to 1 and all other elements are set to 0. This results in a square matrix
	 * that does not change the direction or scale of vectors during multiplication.
	 *
	 * @returns a 4x4 identity matrix.
	 *
	 * The returned output is an identity matrix represented by 4x4 array data, which has
	 * the main attributes of being a square matrix with all diagonal elements set to 1
	 * and all non-diagonal elements set to 0.
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
	 * Initializes a 4x4 matrix with translation values x, y, and z for 3D space
	 * transformations. The first three rows represent the rotation axes while the fourth
	 * row represents the origin.
	 *
	 * @param x 4th element of the first column, which is used to translate the matrix
	 * along the x-axis by the specified value.
	 *
	 * @param y 2nd element of the translation vector, which is used to offset the
	 * y-coordinate of the object being translated along the x-y plane.
	 *
	 * @param z 3D translation along the z-axis, which is stored in the last column of
	 * the fourth row of the matrix.
	 *
	 * @returns a matrix representing a translation transformation.
	 *
	 * The output is a 4x4 matrix with floating-point values, specifically a translation
	 * matrix.
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
	 * Initializes a 4x4 rotation matrix based on input angles x, y, and z in radians.
	 * It calculates the rotation matrices for each axis and multiplies them to create a
	 * single rotation matrix. The resulting matrix is then used to update the object's
	 * data.
	 *
	 * @param x Euler angle of rotation around the x-axis, converted to radians using the
	 * `Math.toRadians` method, and is used to construct the rotation matrix Rx.
	 *
	 * @param y 2D rotation angle around the Y-axis of a 3D coordinate system, which is
	 * converted to radians and used to construct a rotation matrix that is multiplied
	 * by previously constructed X- and Z-axis rotation matrices.
	 *
	 * @param z 3D rotation angle around the z-axis, which is used to calculate the
	 * corresponding 4x4 rotation matrix.
	 *
	 * @returns a 4x4 rotation matrix.
	 *
	 * The output is a 4x4 matrix that represents the rotation along x, y and z axes. The
	 * main attributes of this matrix include its ability to rotate points in 3D space
	 * by scaling, translating, and rotating them based on the input values of x, y, and
	 * z.
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
	 * Initializes a matrix with scaling factors for x, y, and z axes, while setting other
	 * elements to zero or one, and returns the modified object.
	 *
	 * @param x 11th element of the resulting matrix, which is responsible for scaling
	 * along the x-axis.
	 *
	 * @param y scale factor for the y-axis, which is stored in the data element at
	 * position [1][1].
	 *
	 * @param z scale factor for the z-axis, used to transform points along this axis
	 * according to the specified value.
	 *
	 * @returns a Matrix4f object with scaled values for x, y, and z.
	 *
	 * The 4x4 matrix is initialized to represent a scaling transformation with non-zero
	 * values on the main diagonal (x, y, z).
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
	 * Initializes a perspective projection matrix for a given field of view, aspect
	 * ratio, and near/far clipping planes. It calculates the necessary values to construct
	 * the perspective transformation and stores them in the `data` array. The resulting
	 * matrix is returned.
	 *
	 * @param fov field of view angle, which is used to calculate the perspective
	 * projection's scaling factors for the x and y axes.
	 *
	 * @param aspectRatio ratio of the width to the height of the viewport and is used
	 * to calculate the corresponding elements in the first two rows of the perspective
	 * transformation matrix.
	 *
	 * @param zNear near clipping plane distance, which is used to determine the range
	 * of distances from the camera that are considered visible.
	 *
	 * @param zFar farthest distance from the camera, used to calculate the z-coordinates
	 * of the view frustum and determine the perspective projection's depth range.
	 *
	 * @returns a Matrix4f object representing a perspective transformation matrix.
	 *
	 * The output is a 4x4 matrix that represents a perspective projection transformation.
	 * The main attributes include: a horizontal aspect ratio, vertical angle of view,
	 * and depth range.
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
	 * Initializes an orthographic projection matrix for a specified region defined by
	 * left, right, bottom, top, near, and far boundaries. The function calculates the
	 * coefficients for the projection transformation based on these bounds and populates
	 * the corresponding elements of the matrix.
	 *
	 * @param left 3D coordinate value used to calculate the x-axis scaling factor and
	 * translation component of the resulting orthographic matrix.
	 *
	 * @param right 3D point on the right edge of the viewing volume, used to calculate
	 * the scaling factor for the x-axis and the translation value for the x-coordinate
	 * of the orthographic projection.
	 *
	 * @param bottom bottom-most point of the orthographic view's clipping plane, used
	 * to compute the matrix's bottom row elements.
	 *
	 * @param top top boundary of the orthographic view, used to calculate the perspective
	 * transformation matrix for the specified viewport.
	 *
	 * @param near distance from the camera plane to the nearest point that can be seen,
	 * used to calculate the depth component of the projection matrix.
	 *
	 * @param far distance from the camera to the farthest visible point, used to calculate
	 * the depth component of the projection matrix.
	 *
	 * @returns a populated Matrix4f object.
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
	 * Calculates a rotation matrix based on two input vectors, `forward` and `up`. It
	 * first normalizes these vectors to ensure they have a length of 1. The function
	 * then uses these normalized vectors to calculate the right-handed coordinate system's
	 * basis vectors (`f`, `u`, and `r`).
	 *
	 * @param forward 3D vector that defines the direction of rotation around which the
	 * subsequent calculation is performed.
	 *
	 * @param up 3D vector that defines the up direction of the rotation, which is used
	 * to calculate the right and up axes of the rotation matrix.
	 *
	 * @returns a `Matrix4f` representing a rotation matrix.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Initializes a rotation matrix based on given forward, right, and up vectors. It
	 * sets the values in the data array to create a rotation matrix with the specified
	 * direction vectors as its columns. The function returns the instance of itself.
	 *
	 * @param forward 3D vector that defines the direction of the forward axis, used to
	 * populate the third row of the rotation matrix.
	 *
	 * Forward is decomposed into three components: x, y, z. It represents the direction
	 * vector in 3D space.
	 *
	 * @param up 3D vector that corresponds to the up direction of the rotation matrix,
	 * which is used to populate the second row of the matrix.
	 *
	 * Up has x, y, z coordinates which specify its direction.
	 *
	 * @param right 3D vector that determines the x-axis of the rotation matrix, which
	 * is stored in the first row and column of the matrix.
	 *
	 * right has x, y, z components. Its x, y, z values are assigned to specific positions
	 * in the 4x4 matrix data.
	 *
	 * @returns a rotation matrix initialized from given forward, up, and right vectors.
	 *
	 * The output is a 4x4 matrix where each row represents a vector in the new coordinate
	 * system.
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
	 * Takes a `Vector3f` object `r` as input, multiplies its components with corresponding
	 * elements from a 3x4 matrix represented by `data`, and returns a new `Vector3f`
	 * object with the transformed result.
	 *
	 * @param r 3D vector to be transformed, with its components (x, y, z) multiplied by
	 * corresponding elements in a 3x4 matrix stored in the `data` array.
	 *
	 * @returns a transformed Vector3f object based on input vector and predefined matrix
	 * data.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies two matrixes 4x4 element-wise and returns a new resulting matrix4f
	 * object. Each element in the result is calculated by summing products of corresponding
	 * elements from both input matrices.
	 *
	 * @param r 4x4 matrix to be multiplied with the current matrix, with its elements
	 * used for element-wise multiplication and addition during the calculation of the
	 * resulting matrix.
	 *
	 * Multiply each element of `data` with corresponding elements of `r`. The result is
	 * added up to form another element in the output matrix `res`.
	 *
	 * @returns a new Matrix4f object resulting from multiplying two input matrices.
	 *
	 * The result is a 4x4 matrix where each element at row `i`, column `j` is calculated
	 * by summing the product of corresponding elements from the rows of the first input
	 * matrix and columns of the second input matrix.
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
	 * Initializes a 4x4 array, copies the data from another source into it, and then
	 * returns the resulting array.
	 *
	 * @returns a 4x4 array of floats.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Returns a float array containing linear data from a multidimensional array `data`.
	 * The data is arranged column-wise, with each element corresponding to a specific
	 * row and column index.
	 *
	 * @returns an array of 16 float values.
	 *
	 * The output is an array of 16 floating-point numbers arranged in a linear sequence.
	 * It consists of four rows and four columns from the original data structure. Each
	 * element represents a value at a specific position in the data structure.
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
	 * Retrieves a value from a two-dimensional array called `data`, using the provided
	 * integer coordinates `x` and `y`. It returns the corresponding floating-point value.
	 *
	 * @param x 1-based index of an element in a 2D array named `data`, used to retrieve
	 * its corresponding value at that position.
	 *
	 * @param y 2D array column index for accessing the corresponding element in the
	 * `data` array.
	 *
	 * @returns a floating-point value from a two-dimensional array at specified coordinates.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Assigns a given two-dimensional float array to the object's internal data attribute.
	 * This method allows the object to hold and store floating-point values in a matrix-like
	 * structure. The input data is stored for future use within the object.
	 *
	 * @param data 2D array of floats that is assigned to the instance variable `this.data`.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Assigns a floating-point value to a specified location in a two-dimensional array
	 * represented by `data`. The location is defined by two integer indices, `x` and
	 * `y`, which specify the row and column respectively.
	 *
	 * @param x 2D array's row index to which the specified `value` should be assigned.
	 *
	 * @param y 2D array column index for accessing and modifying a specific element of
	 * the `data` array.
	 *
	 * @param value 3D float data to be stored at the specified coordinates (x, y).
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Transposes a 4x4 matrix, which means it swaps its rows with columns and assigns
	 * the resulting matrix to itself. The original data is replaced by the transposed data.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
