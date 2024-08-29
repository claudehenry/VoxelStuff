package com.ch.math;

/**
 * Represents a 4x4 matrix for 3D transformations, with methods to initialize matrices
 * for identity, translation, rotation, scaling, perspective and orthographic
 * projections. The class also provides methods for multiplying matrices, transforming
 * vectors, and getting the data of the matrix in various formats.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Initializes a matrix with identity values, setting all elements on the main diagonal
	 * to 1 and all other elements to 0. This results in a square matrix where each row
	 * is identical to its corresponding column. The function returns the initialized matrix.
	 *
	 * @returns an identity matrix with all elements set to zero except for diagonal ones.
	 *
	 * The 4x4 matrix is an identity matrix with all elements on the main diagonal being
	 * 1 and all other elements being 0. This matrix serves as a reference point for
	 * performing transformations in linear algebra applications.
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
	 * Initializes a matrix for translating coordinates by setting specific values in a
	 * 4x4 array. It sets the translation values along the z-axis, y-axis, and x-axis to
	 * the provided input parameters x, y, and z respectively. The returned value is an
	 * instance of itself.
	 *
	 * @param x 4th element of the 1st column of the matrix, which corresponds to the
	 * translation along the x-axis in 3D space.
	 *
	 * @param y 2D translation along the y-axis and is stored at data[1][3], which affects
	 * the transformation matrix for subsequent operations.
	 *
	 * @param z 4th component of the translation vector and is used to translate the
	 * matrix along the z-axis by that amount.
	 *
	 * @returns a Matrix4f object representing a translation transformation.
	 *
	 * The output is a 4x4 matrix with elements that represent a translation transformation
	 * in three-dimensional space. The matrix's first three columns specify the x, y, and
	 * z coordinates of the origin.
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
	 * Initializes a rotation matrix for a 3D object based on input angles x, y, and z
	 * in radians. It creates three separate matrices for rotations around each axis (x,
	 * y, z) and then multiplies them together to generate the final rotation matrix.
	 *
	 * @param x 3D rotation angle around the X-axis, which is converted to radians and
	 * used to construct the rotation matrix Rx.
	 *
	 * @param y 2nd rotation angle around the Y-axis, which is used to construct the
	 * corresponding rotation matrix.
	 *
	 * @param z 3D rotation angle around the z-axis, used to calculate the rotation matrix
	 * for converting 3D coordinates into another coordinate system.
	 *
	 * @returns a matrix representing a rotation around x, y, and z axes.
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
	 * Initializes a scale transformation for a 4x4 matrix, setting the values at row 0
	 * and column 0 to x, row 1 and column 1 to y, and row 2 and column 2 to z. It returns
	 * the updated matrix object.
	 *
	 * @param x 11th element of the matrix, which corresponds to the scaling factor along
	 * the x-axis when applied to transformations on 3D space.
	 *
	 * @param y 1,1 element of the 4x4 matrix, which corresponds to the scaling factor
	 * along the y-axis.
	 *
	 * @param z 3D scaling factor for the z-axis of the matrix, setting the corresponding
	 * element of the bottom-right 3x3 submatrix to the specified value.
	 *
	 * @returns a scaled matrix with specified x, y, and z values.
	 *
	 * The output is a 4x4 matrix with scaling factors for x, y and z axes, and an identity
	 * value in the fourth row and column to maintain unity. The main attributes are that
	 * the diagonal elements (0,0), (1,1) and (2,2) represent the scale values of the
	 * respective axes, while the remaining elements are zero or one.
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
	 * Initializes a perspective projection matrix given field of view, aspect ratio, and
	 * near/far clipping planes. It calculates the necessary coefficients to transform
	 * 3D points into 2D screen coordinates, then populates the `data` array accordingly.
	 * The resulting matrix is returned.
	 *
	 * @param fov field of view angle, which is used to calculate the tangent of half of
	 * the angle and the matrix elements for the perspective projection transformation.
	 *
	 * @param aspectRatio aspect ratio of the output image and is used to determine the
	 * horizontal and vertical scaling factors for the perspective projection matrix.
	 *
	 * @param zNear near clipping plane distance, which determines the closest point to
	 * the viewer where objects will still be rendered.
	 *
	 * @param zFar farthest distance from the camera at which objects are still rendered,
	 * and is used to calculate the depth range of the perspective projection.
	 *
	 * @returns a populated matrix representing a perspective projection.
	 *
	 * The output is an instance of a Matrix4f with 4x4 elements, specifically designed
	 * for 3D perspective projections. Its main attributes include the left and right
	 * camera frustum boundaries set by aspect ratio and field of view, while the z-depth
	 * values define the near and far clipping planes.
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
	 * Initializes an orthographic projection matrix for a 3D perspective. It sets the
	 * coefficients based on the specified left, right, bottom, top, near, and far clipping
	 * planes to establish the viewing volume. The returned matrix is used to transform
	 * 3D points into screen coordinates.
	 *
	 * @param left x-coordinate of the left edge of the orthographic view frustum, which
	 * is used to calculate the scaling factor for the x-axis and the translation value
	 * for the x-component of the projection matrix.
	 *
	 * @param right 3D right boundary of the view volume, used to compute the values for
	 * the upper-left and lower-right corners of the viewport.
	 *
	 * @param bottom 6th point on the horizontal plane of the viewing volume, which is
	 * used to calculate the perspective transformation matrix for orthographic projection.
	 *
	 * @param top 3D point at which the top edge of the viewing frustum intersects with
	 * the viewing plane, and its value is used to calculate the corresponding row of the
	 * matrix.
	 *
	 * @param near distance from the viewer to the near clipping plane, used to compute
	 * the depth component of the matrix.
	 *
	 * @param far depth of the view frustum, used to calculate the denominator for the
	 * third row and column of the matrix.
	 *
	 * @returns an initialized orthographic projection matrix.
	 *
	 * The matrix is a 4x4 orthogonal projection matrix for an orthographic camera view.
	 * Its main attributes include its scale factors along each axis (width, height, and
	 * depth) and translation values to adjust the view to the specified coordinates.
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
	 * Computes a rotation matrix based on provided forward and up vectors. It first
	 * normalizes the input vectors, then calculates a right-handed orthogonal basis using
	 * the cross product operation. The resulting basis is used to initialize a rotation
	 * matrix.
	 *
	 * @param forward 3D vector that defines the direction of rotation around which the
	 * object is initialized.
	 *
	 * @param up 3D vector defining the direction of the up axis of the rotation matrix,
	 * which is used to calculate the orthogonal basis vectors for the rotation.
	 *
	 * @returns a Matrix4f representing a rotation based on the provided forward and up
	 * vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Initializes a 4x4 matrix with the given forward, up, and right vectors to represent
	 * an orientation. The matrix is filled with the corresponding vector components, and
	 * the last row remains unchanged (0, 0, 0, 1).
	 *
	 * @param forward 3D direction vector used to initialize the rotation matrix, with
	 * its components being assigned to the third row of the matrix.
	 *
	 * Forward is a Vector3f with three components representing the direction vector of
	 * rotation, namely x-coordinate, y-coordinate, and z-coordinate.
	 *
	 * @param up 3D vector defining the up direction, which is stored in the second row
	 * of the resulting rotation matrix.
	 *
	 * Up is a vector with three components (x, y, z) representing the direction along
	 * which an object's orientation is measured.
	 *
	 * @param right 3D vector that defines the x-axis of the rotation matrix, and its
	 * components are assigned to the first column of the matrix.
	 *
	 * Determines direction to the right in 3D space.
	 *
	 * @returns a fully initialized Matrix4f object.
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
	 * Multiplies a given vector by a 4x4 matrix represented by `data`, adding the fourth
	 * column to produce a transformed vector. The result is a new vector with components
	 * computed using dot products between the input vector and the corresponding rows
	 * of the matrix.
	 *
	 * @param r 3D vector to be transformed, and its components are multiplied with
	 * corresponding elements of a matrix stored in the `data` array.
	 *
	 * @returns a new `Vector3f` object resulting from matrix multiplication.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies two 4x4 matrices represented as `Matrix4f` objects and returns a new
	 * matrix resulting from the multiplication. It performs element-wise dot product
	 * calculations to combine corresponding elements of the input matrices. The result
	 * is stored in a newly created `Matrix4f` object.
	 *
	 * @param r 4x4 matrix to be multiplied with the original matrix, whose multiplication
	 * result is returned as the output.
	 *
	 * Matrix4f r represents a 4x4 matrix with floating-point numbers in its elements.
	 * The main properties of this matrix include its size and data type.
	 *
	 * @returns a resulting matrix from multiplying two 4x4 matrices.
	 *
	 * The output is a 4x4 matrix representing a linear transformation or an affine
	 * transformation. The elements of this matrix specify how each point in the input
	 * space is mapped to a corresponding point in the output space.
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
	 * Creates a new 4x4 array filled with values from another existing array `data`. It
	 * iterates through each element and assigns its corresponding value to the new array.
	 * The resulting array is then returned.
	 *
	 * @returns a 4x4 two-dimensional array of float values.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Returns an array of floating-point numbers representing data from a multi-dimensional
	 * array, specifically extracting values at index 0 and indices 1 to 3 for each
	 * dimension. The returned array is then comprised of these extracted values in a
	 * linear sequence.
	 *
	 * @returns an array of 16 float values.
	 *
	 * Returns an array of 16 float values in linear order from 2D data represented as a
	 * 4x4 matrix.
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
	 * Retrieves a value from a two-dimensional array `data` at the specified indices `x`
	 * and `y`. It takes two integer parameters, `x` and `y`, to determine the location
	 * of the desired value within the array. The function returns the corresponding float
	 * value.
	 *
	 * @param x 0-based row index of the 2D array `data`.
	 *
	 * @param y 2D array's column index for which the corresponding value is returned.
	 *
	 * @returns a floating-point value from the specified row and column of the `data` array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Assigns a two-dimensional array of floating-point numbers to an instance variable
	 * named `data`. This allows the object to store and access a matrix-like structure,
	 * enabling manipulation of numerical data within the class. The function updates the
	 * internal state of the object with the provided data.
	 *
	 * @param data 2D array of float values to be assigned to the instance variable `this.data`.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Assigns a specified floating-point value to a specific location in a two-dimensional
	 * array `data`, identified by its row index `x` and column index `y`. The assignment
	 * operation updates the corresponding element in the array with the new value.
	 *
	 * @param x 2D array index for accessing and storing values in the `data` array.
	 *
	 * @param y 2D array's row index for indexing into the `data` array to store the
	 * specified `value`.
	 *
	 * @param value 3D float point data that is assigned to the corresponding index in
	 * the 2D array `data`.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Transposes a 4x4 matrix stored in the `data` array. It creates a new 4x4 array,
	 * copies the elements from `data` to the new array while swapping the row and column
	 * indices, and then assigns the new array back to `data`.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
