package com.ch.math;

/**
 * Is designed to represent and manipulate 4x4 matrices for 3D transformations,
 * including identity, translation, rotation, scaling, perspective projection, and
 * orthographic projection. It provides methods for initialization of various
 * transformation types, matrix multiplication, and data access. The class supports
 * both row-wise and column-wise representation of the matrix data.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Initializes a matrix to its identity form by setting all elements on and above the
	 * diagonal to their respective values, making it an identity transformation matrix.
	 * The resulting matrix is returned. The function modifies the existing object instance.
	 *
	 * @returns an identity matrix with all elements set to zero except for the diagonal.
	 *
	 * It is an identity matrix of size 4x4.
	 * All non-diagonal elements are zero.
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
	 * Sets up a translation matrix with the specified x, y, and z values, effectively
	 * defining a translation vector. It initializes the corresponding row in the 4x4
	 * matrix to represent this translation. The function returns the object itself for
	 * method chaining.
	 *
	 * @param x 4th column of the translation matrix, which corresponds to the X-coordinate
	 * of the translation vector.
	 *
	 * @param y translation along the y-axis, and its value is stored directly in the
	 * corresponding element of the matrix (data[1][3]).
	 *
	 * @param z 3D translation along the z-axis, determining how much to move the object
	 * in that direction.
	 *
	 * @returns a translated matrix. The translation vector is (x, y, z).
	 *
	 * The returned Matrix4f represents an identity matrix with translation applied to
	 * it in the x, y, and z axes by the given values. The translation is stored in the
	 * 0th, 1st, and 2nd columns of the last row (row 3).
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
	 * Initializes a rotation matrix for 3D objects based on Euler angles x, y, and z.
	 * It calculates the rotation matrices for each axis, combines them using matrix
	 * multiplication, and stores the resulting rotation matrix in the current object's
	 * data.
	 *
	 * @param x rotation angle around the x-axis, which is used to calculate and initialize
	 * the rotation matrix.
	 *
	 * @param y yaw angle, used to create a rotation matrix that rotates around the y-axis
	 * when multiplied with other rotation matrices.
	 *
	 * @param z 3D rotation angle around the z-axis, converting which to radians and using
	 * it to populate a rotation matrix that accounts for this axis of rotation.
	 *
	 * @returns a matrix representing rotation around x, y, and z axes.
	 *
	 * The 4x4 matrix represents the rotation transformation. Its diagonal elements
	 * (data[0][0], data[1][1], data[2][2]) are set to 1 and all non-diagonal elements
	 * are set based on trigonometric functions of x, y, z angles.
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
	 * Sets up a 4x4 matrix with a specified scale factor on the x, y, and z axes, leaving
	 * the other elements unchanged. The diagonal elements at indices (0,0), (1,1), and
	 * (2,2) are set to the scale factors.
	 *
	 * @param x 4th column's x-coordinate in the resulting matrix, which is used to scale
	 * objects along the X-axis.
	 *
	 * @param y 2D scaling factor along the y-axis of the matrix, affecting the
	 * transformation's vertical component.
	 *
	 * @param z 3D scale factor along the Z-axis, scaling all points by it according to
	 * their distance from the origin.
	 *
	 * @returns a scaled Matrix4f object with specified scaling factors.
	 *
	 * The return value is an object that represents a 4x4 matrix with elements on the
	 * main diagonal (0-3) set to specified scale factors x, y and z, while other elements
	 * are zeros or ones as needed for a transformation matrix. The remaining entries in
	 * the last row and column are also zeros except the lower right entry which is one.
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
	 * Initializes a matrix with perspective projection data, taking into account field
	 * of view, aspect ratio, near clipping plane distance, and far clipping plane distance.
	 * It calculates and sets values for the matrix elements to represent a perspective
	 * transformation.
	 *
	 * @param fov field of view, which determines how wide the frustum (view volume) is
	 * along the y-axis.
	 *
	 * @param aspectRatio ratio of width to height of the viewport and is used to determine
	 * the field of view's horizontal extent based on its vertical extent specified by
	 * the field-of-view angle.
	 *
	 * @param zNear near clipping plane distance from the camera, which affects the
	 * projection matrix's calculation of depth values within the scene.
	 *
	 * @param zFar farthest depth value in the view frustum, used to calculate the
	 * perspective projection matrix.
	 *
	 * @returns a populated 4x4 matrix representing a perspective projection transformation.
	 *
	 * The returned Matrix4f object represents a perspective projection matrix with
	 * specified field of view (fov), aspect ratio, and depth range (zNear and zFar). It
	 * contains 16 float elements organized in 4x4 rows and columns.
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
	 * Initializes a matrix for orthographic projection, setting its elements based on
	 * the provided parameters: left, right, bottom, top, near, and far clipping planes.
	 * It calculates the necessary scaling factors to map the scene onto the screen. The
	 * resulting matrix is returned.
	 *
	 * @param left 2D coordinate of the left edge of the viewing frustum, and its value
	 * affects the calculation of the matrix elements that map screen coordinates to clip
	 * space.
	 *
	 * @param right 3D coordinate along the x-axis of the top edge of the view frustum,
	 * and is used to compute the elements of the orthographic projection matrix.
	 *
	 * @param bottom bottom-most y-coordinate of the orthographic projection volume, used
	 * to calculate the height and position scaling factors for the matrix.
	 *
	 * @param top 3D y-coordinate of the image plane, and its value is used to determine
	 * the scaling factor for the matrix's second row.
	 *
	 * @param near near clipping plane distance, influencing depth calculation and resulting
	 * orthographic projection matrix.
	 *
	 * @param far depth of the orthographic projection, influencing the transformation
	 * of points along the z-axis.
	 *
	 * @returns an initialized Matrix4f with orthographic projection matrix values.
	 *
	 * The output is a matrix that represents an orthographic projection transformation.
	 * It has a scale factor for width, height, and depth in its diagonal elements (1x,
	 * 2/width, 2/height, -2/depth).
	 * Its non-diagonal elements contain translation factors that shift the projected coordinates.
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
	 * Normalizes a forward vector and calculates two orthogonal vectors, r and u, by
	 * performing cross products with the normalized forward vector. These three vectors
	 * form an orthonormal basis for a rotation matrix, which is then constructed and returned.
	 *
	 * @param forward 3D vector indicating the direction of rotation, which is normalized
	 * to create a unit-length vector used for calculating the rotational axes.
	 *
	 * @param up 3D vector that determines the orientation of the object's local Z-axis
	 * in world space, influencing the rotation calculation.
	 *
	 * @returns a rotation matrix computed from given forward and up vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Initializes a 4x4 matrix with an orthonormal rotation basis. It sets the first
	 * three rows to match the given right, up, and forward vectors respectively, followed
	 * by a column of zeros and a last row containing the identity values. This establishes
	 * a basis for rotating objects in 3D space.
	 *
	 * @param forward 3D direction vector used to define the z-axis of the rotation matrix,
	 * and its components are assigned directly to the corresponding elements of the
	 * `data` array.
	 *
	 * Forward direction has x, y, and z components. The values of these components
	 * represent its magnitude along each axis.
	 *
	 * @param up 3D direction vector used to determine the z-axis of the rotation matrix,
	 * thereby defining its orientation in space.
	 *
	 * Set data[1][0] to up.getX() and data[1][1] to up.getY(). The z-component is set
	 * to u.getZ() and the w-component is 0.
	 *
	 * @param right 3D vector that defines the right axis of the rotation matrix and is
	 * used to populate the first row of the matrix.
	 *
	 * It represents a 3D vector. Its x-component represents the right direction on the
	 * rotation plane. The rest of its components are not analyzed here.
	 *
	 * @returns a pre-populated 4x4 rotation matrix.
	 *
	 * The return value is an instance of the class itself. The Matrix4f object's data
	 * is initialized with the provided forward, up, and right vectors as its column
	 * vectors. The resulting matrix represents a rotation transformation.
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
	 * Multiplies a given 3D vector by a matrix, resulting in a new transformed vector.
	 * It achieves this through dot products of the input vector's components with
	 * corresponding matrix elements and adds an additional translation component. The
	 * result is a new vector representing the object's position after transformation.
	 *
	 * @param r 3D vector being transformed by the function, which applies a matrix
	 * transformation based on the elements of the `data` array to produce an output vector.
	 *
	 * @returns a transformed vector resulting from matrix multiplication.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies two 4x4 matrices together element-wise and stores the result in a new
	 * matrix.
	 * It performs the standard matrix multiplication operation, combining corresponding
	 * elements from each input matrix.
	 * The resulting product matrix is returned as an instance of the `Matrix4f` class.
	 *
	 * @param r 4x4 matrix to be multiplied with the current instance's matrix.
	 *
	 * Multiply each element by corresponding elements from `data`.
	 * Matrix4f.
	 *
	 * @returns a new Matrix4f object representing the product of two matrices.
	 *
	 * The output is a 4x4 matrix representing the result of the multiplication operation
	 * between two matrices. The elements of this matrix are computed as weighted sums
	 * of products from the input matrices' elements. Each element's value corresponds
	 * to an affine transformation applied to a vector.
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
	 * Initializes a 4x4 array with values from another 2D array called `data`. It then
	 * returns this initialized array, effectively copying the data from one location to
	 * another for retrieval or further processing. The returned data is of type float.
	 *
	 * @returns a 2D array of floats with values copied from `data`.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Returns an array of float values representing linear data from a 4x4 grid, with
	 * each row's first element followed by its second, third, and fourth elements in
	 * sequence. The result is a flattened 1D representation of the original 2D grid's
	 * first column.
	 *
	 * @returns a 9-element float array containing linear data values.
	 *
	 * The output is an array of floats with 16 elements.
	 * It appears to be an interleaved representation of two 4x4 matrices, where each row
	 * of the first matrix is followed by the corresponding row of the second matrix.
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
	 * Retrieves a value from a two-dimensional array `data` at the specified coordinates
	 * `x` and `y`. The function takes two integer parameters, x and y, to index into the
	 * data array. It returns a floating-point number stored at that location.
	 *
	 * @param x 1D array index of the data element to be retrieved from the 2D `data` array.
	 *
	 * @param y 2D array index at which to access the element in the `data` array.
	 *
	 * @returns a floating-point value from the `data` array at indices `x` and `y`.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Assigns a new value to the class's `data` field. The `data` parameter is a
	 * two-dimensional array of floating-point numbers, which is directly assigned to the
	 * class's data member. This updates the object's internal state with the provided
	 * matrix values.
	 *
	 * @param data 2D array of floating-point numbers that is assigned to the instance
	 * variable `this.data`.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Assigns a given float value to an element at specified integer coordinates (x, y)
	 * within a multi-dimensional data array. The function modifies the data structure
	 * by replacing the existing value with the new one provided as input argument `value`.
	 * The coordinates must be valid indices for the data array.
	 *
	 * @param x 1D array index of a 2D array element being updated.
	 *
	 * @param y 1D index of the data array when accessed with `data[x][y]`, allowing the
	 * function to store values at specific locations within the multidimensional array.
	 *
	 * @param value 3D point or vector component that is being updated at the specified
	 * coordinates `x` and `y`.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Creates a transposed version of its own data and assigns it to itself, effectively
	 * modifying the original array. It does so by swapping rows with columns. The resulting
	 * data is symmetric.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
