package com.ch.math;


public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * sets the values of a matrix to identity values for proper use and manipulation.
	 * 
	 * @returns a matrix with all elements set to zero, except for the diagonal elements
	 * which are set to 1.
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
	 * initializes a matrix with translation data. It sets the elements of the matrix to
	 * represent the x, y, and z coordinates of the translation.
	 * 
	 * @param x 3D translation amount in the x-direction.
	 * 
	 * @param y 2D translation component of the matrix in the returned transformation,
	 * with values ranging from -1 to 1.
	 * 
	 * @param z 3rd dimension of the translation vector, which is used to update the value
	 * of the fourth column of the matrix.
	 * 
	 * @returns a matrix representing the translation of a point in 4D space.
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
	 * Rodrigues formula. It returns the rotated matrix as a new object.
	 * 
	 * @param x 3D rotation around the z-axis, which is applied to the resulting rotation
	 * matrix.
	 * 
	 * @param y 2D rotation angle around the z-axis and is used to compute the 3D rotation
	 * matrix.
	 * 
	 * @param z 3D rotation axis around which the object is rotated, and its value is
	 * used to calculate the cosine and sine of the angle of rotation in radians.
	 * 
	 * @returns a new Matrix4f object representing the rotation matrix based on the
	 * provided Euler angles.
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
	 * sets the scale factors for the matrix's columns, where (x, y, z) are the new values
	 * to be applied to the matrix.
	 * 
	 * @param x 3D scale factor in the x direction.
	 * 
	 * @param y 2nd coordinate of the scale factor in the initialization process.
	 * 
	 * @param z 2nd dimension of the scale matrix, which is used to set the zoom value
	 * for the resulting transformation matrix.
	 * 
	 * @returns a reference to the original matrix object.
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
	 * initializes a matrix for a perspective projection, where the near and far planes
	 * are specified. It sets the matrix elements to map screen coordinates to 3D space.
	 * 
	 * @param fov 90-degree field of view of the perspective projection, which determines
	 * the aspect ratio of the viewport and is used to calculate the tan hyperbolic function.
	 * 
	 * @param aspectRatio 2D screen aspect ratio of the viewport, which is used to calculate
	 * the appropriate scale factor for the projection matrix.
	 * 
	 * @param zNear near plane distance of the perspective projection, which determines
	 * how much of the scene is clipped off at the near edge.
	 * 
	 * @param zFar 3D coordinate of the far clipping plane, which is used to determine
	 * the depth of objects beyond the near clipping plane and to perform perspective projection.
	 * 
	 * @returns a Matrix4f object with the necessary values for a perspective projection.
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
	 * sets up an orthographic projection matrix with specified aspect ratios, focal
	 * points, and depth ranges.
	 * 
	 * @param left left edge of the orthographic projection.
	 * 
	 * @param right right edge of the orthogonal view frustum, and is used to calculate
	 * the width of the frustum.
	 * 
	 * @param bottom 2D coordinate of the bottom left corner of the view frustum, which
	 * determines the size and shape of the frustum.
	 * 
	 * @param top 2D coordinate of the top-left corner of the orthographic view frustum.
	 * 
	 * @param near near plane of the orthographic projection, and it determines the
	 * distance from the camera to the near side of the projection.
	 * 
	 * @param far 3D far clipping plane, which sets the distance beyond which points are
	 * not rendered.
	 * 
	 * @returns a matrix representing an orthographic projection of a 3D scene.
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
	 * generates a rotation matrix from a forward and up vector, using the cross product
	 * to compute the unit vectors for x, y, and z rotations.
	 * 
	 * @param forward 3D direction that the rotation will be applied to.
	 * 
	 * @param up 2D orientation of the rotation axis.
	 * 
	 * @returns a Matrix4f object representing the rotation matrix.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * initializes a rotation matrix with three input vectors representing the forward,
	 * right, and up directions, respectively. It sets the appropriate elements of the
	 * matrix to reflect these inputs.
	 * 
	 * @param forward 3D direction of the rotation axis, which is used to set the X
	 * component of the rotation matrix.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis, which is used to determine
	 * the rotation matrix's orientation around that axis.
	 * 
	 * @param right 3D right vector of the rotation axis, which is used to generate the
	 * rotation matrix.
	 * 
	 * @returns a `Matrix4f` object representing the rotation matrix.
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
	 * of multiplying the corresponding elements of the input vector by the values stored
	 * in the `data` array, followed by addition of the bias values stored in the `data`
	 * array.
	 * 
	 * @param r 3D vector to which the `data` array is transformed.
	 * 
	 * @returns a new `Vector3f` object with the result of multiplying each element of
	 * the input vector by the corresponding elements of the transformation matrix, and
	 * adding the resulting values.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * multiplies a matrix by another matrix element-wise and returns the result as a new
	 * matrix.
	 * 
	 * @param r 4x4 matrix that is multiplied with the current matrix, resulting in a new
	 * 4x4 matrix.
	 * 
	 * @returns a new Matrix4f object containing the result of multiplying the input matrices.
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
	 * creates a 2D array of floats, initializing its elements with the input `data` array.
	 * 
	 * @returns an array of arrays, where each inner array contains four elements
	 * representing the data points at each position.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * returns an array of floats representing a linear sequence of data points in row-major
	 * order.
	 * 
	 * @returns an array of 12 floating-point values.
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
	 * retrieves the value at a specific position in a 2D array, given its coordinates
	 * `x` and `y`.
	 * 
	 * @param x 1D coordinate of the point within the 2D grid where the value is being accessed.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function, and it
	 * is used to index into the array to retrieve the corresponding value.
	 * 
	 * @returns a floating-point value representing the specified pixel value at position
	 * (x, y) of an image.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * sets the `data` field of its receiver to a provided array of floats.
	 * 
	 * @param data 2D array of float values that will be stored as the field `data` within
	 * the function's scope.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * updates the value of a cell at position (x, y) in an array of floats, by assigning
	 * a new float value to that cell.
	 * 
	 * @param x 0-based index of the row in which the value will be assigned in the 2D
	 * array `data`.
	 * 
	 * @param y 2D coordinate position of the element in the 2D array being manipulated
	 * by the function, and it is used to determine which element in the array is being
	 * updated with the provided value.
	 * 
	 * @param value 3D coordinate where the value will be assigned in the data array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transforms an array of floats into a new array with the elements swapped between
	 * rows and columns.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
