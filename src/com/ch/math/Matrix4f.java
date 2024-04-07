package com.ch.math;

public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * initializes a matrix to the identity matrix, with all elements set to their default
	 * value of 0 or 1.
	 * 
	 * @returns a Matrix4f object with identity values.
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
	 * initializes a Matrix4f object with translation coordinates (x, y, z). It updates
	 * the matrix data accordingly and returns the modified matrix object.
	 * 
	 * @param x 3D translation vector's x-coordinate in the returned matrix.
	 * 
	 * @param y 2D coordinate of the translation origin along the y-axis.
	 * 
	 * @param z 3rd translation axis and sets its value to the input `z` value, which is
	 * then included in the resulting matrix.
	 * 
	 * @returns a Matrix4f object with the specified translation applied to its components.
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
	 * generates a rotation matrix based on three Euler angles (x, y, z) and returns it
	 * as a Matrix4f object.
	 * 
	 * @param x 3D rotation around the x-axis.
	 * 
	 * @param y 2D rotation angle around the z-axis, which is applied to the z-rotation
	 * matrix along with the x-rotation matrix multiplied by the y-rotation matrix.
	 * 
	 * @param z 3D rotation axis around which the matrix is rotated, and it is used to
	 * compute the components of the rotation matrix.
	 * 
	 * @returns a 4x4 homogeneous transformation matrix representing a rotation in 3D space.
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
	 * modifies the elements of a `Matrix4f` object to reflect a new scaling transformation,
	 * where the x, y, and z components are applied element-wise.
	 * 
	 * @param x 3D position of the top-left corner of the scaled matrix, which is set as
	 * the value of the `data[0][0]` element.
	 * 
	 * @param y 2D scale factor for the vertical dimension of the matrix.
	 * 
	 * @param z 2nd component of the scale vector and is used to set the value for that
	 * component of the matrix's representation of the transformation.
	 * 
	 * @returns a matrix with scaled elements.
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
	 * calculates a perspective projection matrix based on field of view (fov), aspect
	 * ratio, near and far distances, resulting in a transformed coordinate system for
	 * visualization purposes.
	 * 
	 * @param fov 90-degree field of view of the camera, which determines the aspect ratio
	 * of the image and is used to calculate the tan of half of the field of view.
	 * 
	 * @param aspectRatio 2D screen aspect ratio of the perspective projection, which is
	 * used to calculate the scaling factor for the x-axis in the projection matrix.
	 * 
	 * @param zNear near plane distance of the perspective projection.
	 * 
	 * @param zFar 3D distance from the viewer to the farthest point in the scene, which
	 * is used to calculate the correct perspective projection of the scene.
	 * 
	 * @returns a `Matrix4f` object with the necessary elements to represent a perspective
	 * projection.
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
	 * initializes a matrix for orthographic projection, where left and right are the
	 * coordinates of the near clipping plane, bottom and top are the coordinates of the
	 * far clipping plane, near is the distance from the camera to the near clipping
	 * plane, and far is the distance from the camera to the far clipping plane.
	 * 
	 * @param left 2D coordinate of one of the two left corners of the orthographic
	 * projection, which determines the origin of the projection.
	 * 
	 * @param right 2D right coordinate of the orthographic projection, which is used to
	 * compute the scaling factors for the matrix's data array.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the viewport in the
	 * orthogonal projection, which is used to calculate the width and height of the viewport.
	 * 
	 * @param top 2D coordinate of the top of the orthographic projection, which is used
	 * to calculate the position of the projected points in the vertical dimension.
	 * 
	 * @param near near plane of the orthographic projection, which determines how the
	 * perspective view is zoomed in or out.
	 * 
	 * @param far 3D point from which to view the scene, and it determines the distance
	 * of the near plane from the viewer's eye.
	 * 
	 * @returns a matrix that represents an orthographic projection.
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
	 * `r`. It computes the rotation axis `u` and returns the rotation matrix.
	 * 
	 * @param forward 3D direction of rotation.
	 * 
	 * @param up 3D up direction of the rotation, which is cross-producted with the
	 * `forward` parameter to form the rotational axis.
	 * 
	 * @returns a matrix representing a rotation transformation.
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
	 * `right`. It sets the elements of the rotation matrix to the corresponding components
	 * of the input vectors, ensuring that the resulting matrix represents a valid rotation.
	 * 
	 * @param forward 3D direction of the rotation axis, which is used to initialize the
	 * rotation matrix.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis, which is used to define
	 * the orientation of the rotation matrix.
	 * 
	 * @param right 3D right vector of the rotation, which is used to initialize the
	 * elements of the output matrix.
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
	 * takes a `Vector3f` argument `r` and returns a new `Vector3f` object with transformed
	 * coordinates based on a set of linear transformations represented by a 3x3 matrix
	 * stored in the class's instance variables.
	 * 
	 * @param r 3D transformation to be applied to the output vector.
	 * 
	 * @returns a new Vector3f object containing the result of multiplying each component
	 * of the input vector `r` by the corresponding components of a set of data, and then
	 * adding the products together.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * multiplies a matrix `r` by another matrix `data` and returns the result as a new
	 * matrix.
	 * 
	 * @param r 4x4 matrix that is multiplied with the current matrix, resulting in the
	 * updated state of the current matrix.
	 * 
	 * @returns a new Matrix4f object containing the result of multiplying the input
	 * matrix `r` with the current matrix.
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
	 * returns an array of arrays, where each inner array has four elements representing
	 * the four quadrants of a square matrix with dimensions 4x4. The returned values are
	 * the same as the input `data`.
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
	 * returns an array of floats representing a linear dataset with eight data points,
	 * each consisting of two values (x and y coordinates).
	 * 
	 * @returns an array of 12 floats, representing the linear data.
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
	 * retrieves a value from a two-dimensional array `data`. It takes two integer arguments
	 * `x` and `y` representing the row and column indices, respectively, of the desired
	 * element within the array. The function returns the value stored at the specified
	 * position in the array.
	 * 
	 * @param x 1D coordinate of the point in the 2D array at which to retrieve the value.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function, which is
	 * used to locate the corresponding value in the array.
	 * 
	 * @returns a floating-point number representing the value at the specified coordinates
	 * of an array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * sets the value of the object's internal `data` field to the provided array of floats.
	 * 
	 * @param data 2D array of floating-point values that will be assigned to the `data`
	 * field of the `SetM` method.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * sets a value at a specific position in a 2D array.
	 * 
	 * @param x 0-based index of the row in the 2D array where the value of `value` will
	 * be stored.
	 * 
	 * @param y 2nd dimension of the data array being manipulated by the function.
	 * 
	 * @param value 3D coordinate value to be stored at the specified position in the
	 * data array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transforms an array of floats into its transposed form, which is a 2D array of
	 * rows as columns. It sets the new data array to be the same as the original data array.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
