package com.ch.math;

/**
 * Is a representation of a 4x4 homogeneous transformation matrix. It has several
 * methods for setting and getting values of its elements, as well as methods for
 * multiplying it with other matrices, transforming vectors, and getting the linear
 * data of the matrix.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Initializes a matrix to the identity matrix, with all elements set to either 0 or
	 * 1.
	 * 
	 * @returns a Matrix4f object with identity matrix elements.
	 * 
	 * The output is a `Matrix4f` object representing an identity matrix with 4x4 dimensions.
	 * The elements of the matrix are set to their default values, which are:
	 * 
	 * * Data[0][0] = 1
	 * * Data[0][1] = 0
	 * * Data[0][2] = 0
	 * * Data[0][3] = 0
	 * * Data[1][0] = 0
	 * * Data[1][1] = 1
	 * * Data[1][2] = 0
	 * * Data[1][3] = 0
	 * * Data[2][0] = 0
	 * * Data[2][1] = 0
	 * * Data[2][2] = 1
	 * * Data[2][3] = 0
	 * * Data[3][0] = 0
	 * * Data[3][1] = 0
	 * * Data[3][2] = 0
	 * * Data[3][3] = 1
	 * 
	 * In summary, the `initIdentity` function returns an identity matrix with 4x4
	 * dimensions, where each element is set to its default value.
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
	 * Initializes a matrix with translation values for x, y, and z coordinates. It returns
	 * the initialized matrix.
	 * 
	 * @param x 3D translation vector along the x-axis in the matrix.
	 * 
	 * @param y 2nd component of the translation vector in the matrix.
	 * 
	 * @param z 3rd coordinate of the translation vector, which is added to the object's
	 * position in the `data` array.
	 * 
	 * @returns a new matrix instance with translation data filled in.
	 * 
	 * 1/ The returned object is a `Matrix4f` instance.
	 * 2/ The elements of the matrix have been modified to reflect the given translation
	 * vector (x, y, z). Specifically, the element at row 0, column 0 has been set to -x,
	 * while the remaining elements have been unchanged.
	 * 3/ The returned object remains in the same state as the original input, allowing
	 * for chaining of methods without modifying the original matrix.
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
	 * Initializes a rotation matrix `rz` based on given x, y, and z angles, and then
	 * multiplies it with two other rotation matrices (ry and rx) to create a final
	 * rotation matrix `data`.
	 * 
	 * @param x 3D rotation angle around the x-axis, which is used to calculate the
	 * components of the rotation matrix.
	 * 
	 * @param y 2D rotation angle about the z-axis and is used to calculate the rotation
	 * matrix rz.
	 * 
	 * @param z 3D rotation axis around which the matrix is being constructed, and it is
	 * used to calculate the components of the resulting rotation matrix.
	 * 
	 * @returns a `Matrix4f` object representing a rotation matrix.
	 * 
	 * The data field of the Matrix4f object contains the rotation matrix represented as
	 * a 16x16 floating-point array. The matrix is composed of three rotation components
	 * around the x, y, and z axes, respectively. Each component is represented by a 4x4
	 * submatrix within the larger matrix.
	 * 
	 * The elements of the matrix are arranged in columns, with each column representing
	 * the rotation around a specific axis. The rows represent the individual rotation
	 * components. The values within each column and row are floats ranging from 0 to 1,
	 * indicating the amount of rotation around that particular axis.
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
	 * Modifies a `Matrix4f` instance to have a scale factor applied to its x, y, and z
	 * components.
	 * 
	 * @param x 4th component of the scale vector that is being initialized and assigned
	 * to the `data` array of the Matrix4f object.
	 * 
	 * @param y 2D scaling factor in the x-axis direction.
	 * 
	 * @param z 2nd dimension of the matrix and sets its value to the input `x`.
	 * 
	 * @returns a reference to the original matrix object, which has been scaled by the
	 * provided values.
	 * 
	 * * The output is a reference to the same Matrix4f object that was passed as input.
	 * * The matrix's data has been updated with new values for the x, y, and z scales.
	 * Specifically, the elements of the matrix have been modified in the following way:
	 * 	+ Element at position (0, 0) is set to x.
	 * 	+ Element at position (0, 1) is set to 0.
	 * 	+ Element at position (0, 2) is set to 0.
	 * 	+ Element at position (0, 3) is set to 0.
	 * 	+ Elements at positions (1, 0), (1, 1), and (1, 2) are set to y.
	 * 	+ Elements at positions (2, 0), (2, 1), and (2, 2) are set to z.
	 * 	+ All other elements of the matrix remain unchanged.
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
	 * Initializes a matrix for perspective projection, setting values for the viewport
	 * dimensions, aspect ratio, near and far distances, and creating the matrix itself.
	 * 
	 * @param fov 90-degree angle of the field of view, which is used to calculate the
	 * aspect ratio and tan(halfFOV) value for the matrix initialization.
	 * 
	 * @param aspectRatio 2D aspect ratio of the viewport, which is used to scale the
	 * horizontal and vertical dimensions of the projection matrix to maintain the correct
	 * aspect ratio.
	 * 
	 * @param zNear near plane distance of the perspective projection, which determines
	 * the position of objects in the near field of view.
	 * 
	 * @param zFar 2D distance from the camera to the farthest point in the scene, which
	 * is used to calculate the perspective projection matrix.
	 * 
	 * @returns a matrix that represents the view transformation for a perspective projection.
	 * 
	 * * `data`: an array of 4 float values representing the components of a matrix.
	 * * Each element in `data` has a specific meaning and is calculated based on input
	 * parameters.
	 * * The resulting matrix represents a perspective projection, which is useful for
	 * rendering 3D objects from a particular viewpoint.
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
	 * Initializes an orthographic projection matrix with the given dimensions and near
	 * and far distances. It sets the matrix elements to appropriate values for a correct
	 * projection.
	 * 
	 * @param left left coordinate of the orthographic projection.
	 * 
	 * @param right right side of the orthographic projection, which is used to calculate
	 * the perspective correction coefficients for the matrix.
	 * 
	 * @param bottom 2D coordinate of the bottom edge of the view frustum, which is used
	 * to determine the width of the frustum.
	 * 
	 * @param top 2D coordinate of the upper edge of the orthographic projection, which
	 * is used to determine the y-coordinate of the projected points.
	 * 
	 * @param near near clipping plane of the orthographic projection, and it determines
	 * how far from the viewer the near edge of the image will be projected.
	 * 
	 * @param far 3D space beyond which the orthographic projection is performed, and its
	 * value determines the distance from the viewer at which objects appear to be in perspective.
	 * 
	 * @returns a reference to the original Matrix4f object.
	 * 
	 * * The return value is a reference to the same Matrix4f object that was passed as
	 * an argument.
	 * * The data array of the matrix contains four elements representing the coordinates
	 * of the origin of the orthographic projection, with each element being a floating-point
	 * value between 0 and 1. Specifically, `data[0][0]` to `data[3][3]` represent the
	 * coordinates (x, y, z, w) of the origin in the respective dimensions.
	 * * The values of these elements are calculated using the input parameters and the
	 * dimensions of the matrix, as shown in the function body.
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
	 * Generates a rotation matrix based on two input vectors: `forward` and `up`. It
	 * returns the rotation matrix.
	 * 
	 * @param forward 3D forward direction of the rotation axis.
	 * 
	 * @param up 2D cross product of the `forward` input parameter and itself, which is
	 * used to calculate the rotational component of the matrix.
	 * 
	 * @returns a Matrix4f object representing the rotation matrix based on the input vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Initializes a rotation matrix based on three vector inputs: `forward`, `right`,
	 * and `up`. It sets the elements of the rotation matrix to represent the orientation
	 * of the axis defined by these vectors.
	 * 
	 * @param forward 3D vector that points in the direction of the forward axis of
	 * rotation, which is used to initialize the rotation matrix.
	 * 
	 * * The vector `forward` represents the direction of forward motion in 3D space.
	 * * It has three components: `x`, `y`, and `z`, which indicate the magnitude and
	 * direction of the forward motion.
	 * 
	 * @param up 3D direction of the up vector, which is used to set the y-component of
	 * the rotation matrix.
	 * 
	 * * `up` is a vector representing the upward direction.
	 * * It has three components: `x`, `y`, and `z`.
	 * * These components represent the x, y, and z coordinates of the upward direction
	 * in the local coordinate system of the matrix.
	 * 
	 * @param right 3D rightward vector, which is used to set the elements of the matrix's
	 * data array at position (0, 1, 2).
	 * 
	 * * `right` is a vector that points from the origin to the rightward direction in
	 * 3D space.
	 * 
	 * @returns a `Matrix4f` object representing a rotation matrix based on the provided
	 * vectors.
	 * 
	 * * The returned output is an instance of the `Matrix4f` class, representing a 4x4
	 * matrix with floating-point elements.
	 * * The matrix data contains the rotation vectors in three dimensions (r, u, f),
	 * each represented by a column of the matrix.
	 * * Each element in the matrix data is set to zero, except for the last row and
	 * column, which contain non-zero values indicating the identity matrix.
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
	 * Takes a `Vector3f` argument `r` and returns a new `Vector3f` object with the result
	 * of multiplying each component of the input vector by a set of pre-defined values,
	 * then adding the result to the corresponding components of a reference vector.
	 * 
	 * @param r 3D vector to which the original 3D vector is to be transformed.
	 * 
	 * @returns a new Vector3f object containing the result of multiplying each component
	 * of the input vector by the corresponding components of a set of data, and then
	 * adding the results.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies two `Matrix4f` objects and returns the result as a new `Matrix4f` object,
	 * with the elements of the resulting matrix being the product of the corresponding
	 * elements of the input matrices.
	 * 
	 * @param r 4x4 matrix to be multiplied with the current matrix.
	 * 
	 * * `r` is a `Matrix4f` object.
	 * * It has 16 elements, each representing a value in the matrix.
	 * 
	 * @returns a matrix that represents the product of two 4x4 matrices.
	 * 
	 * * The output is a `Matrix4f` object representing the result of multiplying the
	 * input matrices `data` and `r`.
	 * * The dimensions of the output matrix are 4x4, indicating that it represents a 3D
	 * transformation matrix.
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
	 * Generates a 4x4 array of float values by referencing an external array `data`. It
	 * then returns the generated array as its output.
	 * 
	 * @returns an array of 4x4 floats, containing the input data.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Returns an array of floats representing the linear data for a given input.
	 * 
	 * @returns an array of 18 floats representing the linear data.
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
	 * Retrieves a value from a 2D array `data`. It returns the value at the specified
	 * position (x, y) of the array.
	 * 
	 * @param x 2D coordinate of the point in the matrix where the value is being retrieved.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function.
	 * 
	 * @returns a floating-point value representing the pixel value at the specified
	 * coordinates (x, y) from the input data array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Sets the value of a member field named `data` to an array of arrays of float values.
	 * 
	 * @param data 2D array of float values that will be assigned to the `data` field of
	 * the `SetM` method.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Sets a value at a specific position in a 2D array.
	 * 
	 * @param x 0-based index of a cell in the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array being manipulated, and it takes on the
	 * value of the `value` argument.
	 * 
	 * @param value 3D coordinate's z-value that will be assigned to the corresponding
	 * element of the 2D array `data`.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Transforms an array of floats `data` into a new array of floats, where each element
	 * in the new array is the corresponding element in the original array, but in a
	 * different position.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
