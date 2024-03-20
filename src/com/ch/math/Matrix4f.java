package com.ch.math;

public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * initializes a `Matrix4f` object to its identity matrix, with all elements set to
	 * their default values.
	 * 
	 * @returns a Matrix4f object with identity matrix elements.
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
	 * initializes a matrix with translation data, where each element represents the
	 * corresponding coordinate in 3D space.
	 * 
	 * @param x 3D translation vector in the x-axis direction, which is negated and then
	 * stored in the data array as element `data[0][3]`.
	 * 
	 * @param y 2D translation along the y-axis in the returned matrix.
	 * 
	 * @param z 3rd dimension of the translation vector, which is applied to the matrix's
	 * elements in the form (0, 0, z).
	 * 
	 * @returns a matrix representing the translation of a point in 3D space.
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
	 * initializes a rotation matrix `data` by applying successive rotations around the
	 * x, y, and z axes using the Math.toRadians method.
	 * 
	 * @param x 3D rotation around the x-axis and is used to compute the yaw angle of the
	 * resulting rotation matrix.
	 * 
	 * @param y 2D rotation angle around the z-axis and is used to calculate the yaw
	 * component of the resulting rotation matrix.
	 * 
	 * @param z 3D rotation axis around which the rotation is performed, and it determines
	 * the angle of rotation through its value.
	 * 
	 * @returns a 4x4 matrix representing the rotation of a 3D object about an arbitrary
	 * axis.
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
	 * initializes a matrix with identity values and sets the scale factors for the x,
	 * y, and z axes.
	 * 
	 * @param x 4th column of the returned matrix, which will contain the scaled value
	 * of x.
	 * 
	 * @param y 2nd component of the scaling factor for the matrix.
	 * 
	 * @param z 2nd dimension of the scaled matrix, which is set to the value provided
	 * by the user.
	 * 
	 * @returns a matrix with its elements scaled by the given factors.
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
	 * calculates a 4x4 matrix for a perspective projection, given the field of view
	 * (fov), aspect ratio, near and far distances. It sets the matrix elements to represent
	 * the desired perspective projection.
	 * 
	 * @param fov field of view of the camera, which determines the aspect ratio and
	 * scaling factor for the matrix.
	 * 
	 * @param aspectRatio 2D aspect ratio of the viewport, which is used to determine the
	 * scale of the x and y dimensions of the projective matrix.
	 * 
	 * @param zNear near plane distance of the perspective projection, which is used to
	 * determine the scaling factors for the horizontal and vertical dimensions of the
	 * view frustum.
	 * 
	 * @param zFar 3D space far clipping plane, which determines how much of the scene
	 * is rendered and affects the final image.
	 * 
	 * @returns a matrix representing a perspective projection, with values that define
	 * the viewport, near and far distances, and aspect ratio.
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
	 * initializes a 4x4 homogeneous transformation matrix representing an orthographic
	 * projection, where the near and far planes are specified along with the horizontal
	 * and vertical extents of the image plane.
	 * 
	 * @param left left edge of the orthographic projection in the X dimension.
	 * 
	 * @param right 2D coordinate of the right edge of the orthographic projection, which
	 * is used to calculate the scaling factors for the x-axis in the resulting matrix.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the viewport, which is
	 * used to calculate the width and height of the viewport in the `initOrthographic()`
	 * function.
	 * 
	 * @param top 2D coordinate of the top of the orthographic projection, which is used
	 * to compute the z-component of the matrix's data.
	 * 
	 * @param near 3D space near the camera, which is used to calculate the orthographic
	 * projection of the scene.
	 * 
	 * @param far 3D far clip plane in the orthographic projection, which determines the
	 * distance beyond which objects are clipped and hidden from view.
	 * 
	 * @returns a Matrix4f object representing an orthographic projection matrix.
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
	 * `r`. It generates a rotation matrix by cross-multiplying the input vectors and
	 * returning it.
	 * 
	 * @param forward 3D direction in which the rotation will occur.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis and used to calculate
	 * the rotation matrix.
	 * 
	 * @returns a matrix representation of a rotation around the `forward` axis.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * initializes a rotation matrix based on three input vectors: `forward`, `up`, and
	 * `right`. It sets the corresponding elements of the matrix to the dot product of
	 * the input vectors, scaling the result by 1.
	 * 
	 * @param forward 3D direction of rotation, which is used to set the X, Y, and Z
	 * components of the rotation matrix.
	 * 
	 * @param up 3D vector that points upwards relative to the current orientation of the
	 * matrix, and its value is used to set the third column of the matrix's data array.
	 * 
	 * @param right 3D right vector of the rotation, which is used to initialize the x,
	 * y, and z components of the rotation matrix's data array.
	 * 
	 * @returns a Matrix4f object representing the rotation matrix.
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
	 * takes a vector `r` and returns a new vector resulting from applying a transformation
	 * to its components.
	 * 
	 * @param r 3D transformation to apply to the output vector.
	 * 
	 * @returns a new vector with the same components as the input vector, transformed
	 * by the specified multiplication.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * multiplies two matrix4f objects and returns the result as a new matrix4f object.
	 * 
	 * @param r 4x4 matrix to be multiplied with the current matrix, resulting in the
	 * updated matrix.
	 * 
	 * @returns a new Matrix4f object containing the result of multiplying the input
	 * matrix by another matrix.
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
	 * returns an array of arrays, where each inner array has 4 elements representing a
	 * matrix of 4x4 floats. It creates and returns an exact copy of the input `data` array.
	 * 
	 * @returns an array of arrays, each element of which contains a copy of the original
	 * data.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * generates an array of floats containing the data points at different indices in
	 * the x, y, and z-axes.
	 * 
	 * @returns an array of 9 floating-point values.
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
	 * retrieves the value of a 2D array element at specified coordinates `x` and `y`.
	 * 
	 * @param x 1D coordinate of the point in the 2D array where the corresponding value
	 * is being sought.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function.
	 * 
	 * @returns a floating-point value representing the specified pixel coordinates.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * sets the value of a private field `data` to an input array of floats `[float][float]`.
	 * 
	 * @param data 2D array of float values that will be stored as the instance field
	 * `data` of the `SetM` method.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * sets a value for a specific position in an array of two-dimensional data.
	 * 
	 * @param x 0-based index of the row in which the value will be stored in the 2D array
	 * `data`.
	 * 
	 * @param y 2nd dimension of the data array being modified by the function, and it
	 * is used to locate the specific element within the array that should be assigned
	 * the provided value.
	 * 
	 * @param value 2D coordinate of a pixel in the data array that is being modified.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transposes an array of 4x4 floating-point values by interchanging rows and columns,
	 * assigning each value to its corresponding position in the transposed array.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
