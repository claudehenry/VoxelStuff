package com.ch.math;

public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Sets all elements of a matrix to their corresponding identities, such as (1,0),
	 * (0,1), etc.
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
	 * Initializes a matrix representing a translation in 3D space with the provided `x`,
	 * `y`, and `z` values.
	 * 
	 * @param x 3D translation's x-component in the returned matrix.
	 * 
	 * @param y 2nd element of the translation vector, which is added to the corresponding
	 * elements of the matrix's data array.
	 * 
	 * @param z 3rd coordinate of the translation vector, which is applied to the upper-left
	 * corner of the matrix.
	 * 
	 * @returns a reference to the original matrix.
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
	 * Initializes a rotation matrix based on three input angles (x, y, z) using the law
	 * of cosines and sine. It returns the initialized rotation matrix as a new Matrix4f
	 * object.
	 * 
	 * @param x 3D rotation around the x-axis and affects the resulting rotation matrix
	 * through its conversion to radians and multiplication with other rotation matrices.
	 * 
	 * @param y 2nd angle of rotation, which is used to transform the reference frame and
	 * generate the resulting matrix representation of the rotated vector.
	 * 
	 * @param z 3D rotation around the z-axis and is used to calculate the resulting
	 * rotation matrix.
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
	 * Sets the elements of a `Matrix4f` object to scaling factors for x, y, and z axes.
	 * 
	 * @param x 3D scaling factor along the x-axis for the matrix.
	 * 
	 * @param y 2nd component of the scaling vector, which determines the vertical scale
	 * factor for the matrix.
	 * 
	 * @param z 2nd element of the scaling matrix and is responsible for setting the
	 * vertical component of the transformation.
	 * 
	 * @returns a matrix with updated data elements.
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
	 * Sets up a 4x4 homogeneous transformation matrix to perform a perspective projection.
	 * It calculates the near and far plane values based on the given field of view,
	 * aspect ratio, and stores them in the matrix data.
	 * 
	 * @param fov Field of View (FOV) of the camera, which determines the angle of the
	 * vertical visual horizon visible to the user, and it is used to calculate the aspect
	 * ratio of the viewport.
	 * 
	 * @param aspectRatio 1:n ratio of the viewport width to the viewport height, and is
	 * used to scale the x-axis of the matrix inversely proportional to the y-axis, which
	 * ensures that objects appear proportionate in their intended orientation within the
	 * viewport.
	 * 
	 * @param zNear near clip plane of the perspective projection, which determines the
	 * range of values for the z-coordinate of pixels in the resulting image.
	 * 
	 * @param zFar 2D perspective projection's far clip plane, which sets the distance
	 * beyond which objects will appear blurred or distorted.
	 * 
	 * @returns a matrix with four elements representing the projection of a 3D scene
	 * onto a 2D viewplane.
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
	 * Sets up a orthographic projection matrix for a 3D scene, based on input values of
	 * left, right, bottom, top, near and far coordinates.
	 * 
	 * @param left 3D coordinate of one of the two plane boundaries, with positive values
	 * corresponding to the near boundary and negative values to the far boundary.
	 * 
	 * @param right right boundary of the orthographic projection, which is used to
	 * calculate the z-coordinates of the vertices of the projected image.
	 * 
	 * @param bottom 2D height of the bottom-left corner of the orthographic projection.
	 * 
	 * @param top 3D coordinate of the top-left corner of the view frustum, which determines
	 * the position and size of the frustum in the virtual world.
	 * 
	 * @param near 3D coordinate of the near plane, where the function will project any
	 * 3D points into the view frustum.
	 * 
	 * @param far 4th coordinate of the orthographic projection, which determines the
	 * distance from the near plane to the far plane along the depth axis.
	 * 
	 * @returns a Matrix4f object representing an orthographic projection.
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
	 * Initializes a rotation matrix based on a forward vector and an up vector, returning
	 * the rotation matrix.
	 * 
	 * @param forward 3D direction of rotation.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis.
	 * 
	 * @returns a matrix representing a rotation in 3D space.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Sets up a rotation matrix based on three input vectors: `forward`, `up`, and
	 * `right`. It generates a matrix where the rows represent the rotations around each
	 * axis.
	 * 
	 * @param forward 3D vector of the direction of rotation.
	 * 
	 * @param up 3D axis perpendicular to the rotation axis, which is used to calculate
	 * the rotation matrix's skew element.
	 * 
	 * @param right 3D direction of the rightward vector, which is used to construct the
	 * rotation matrix.
	 * 
	 * @returns a matrix representation of a rotation in 3D space.
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
	 * Takes a vector `r` and returns a transformed vector using the elements of an array
	 * `data`. The transformation is applied component-wise by multiplying each element
	 * of `r` with a corresponding element of `data`.
	 * 
	 * @param r 3D transformation vector applied to the output vector.
	 * 
	 * @returns a new Vector3f instance representing the result of applying the transformation
	 * to the given vector.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies a given `Matrix4f` object by another `Matrix4f` object, computing the
	 * resulting matrix product and returning it.
	 * 
	 * @param r 4x4 transformation matrix that the current matrix will be multiplied by,
	 * and its elements are used to compute the new elements of the resulting matrix.
	 * 
	 * @returns a matrix resulting from the multiplication of two other matrices.
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
	 * Creates an array of size 4x4 containing values from an external `data` array. It
	 * does this by using a nested loop to access each element in the `data` array and
	 * assign it to the corresponding position in the returned array.
	 * 
	 * @returns an array of 4x4 floats containing the input data.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Retrieves an array of floats representing linear data consisting of input values
	 * for 4 channels, each with two output values.
	 * 
	 * @returns an array of 9 floats representing 3 sets of 3 values each.
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
	 * Retrieves the value of a pixel at a given coordinates (x, y) from an array of
	 * pixels called "data".
	 * 
	 * @param x 1-based integer index of a cell within the 2D array 'data'.
	 * 
	 * @param y 2nd dimension of the 2D array `data`, providing its value for calculating
	 * the output.
	 * 
	 * @returns a floating-point value representing the cell at position (x, y) in a 2D
	 * grid.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Sets the value of a private member variable, `data`, to the input array.
	 * 
	 * @param data 2D array of floating-point numbers that will be assigned to the `data`
	 * field of the calling object.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Updates the specified element of a 2D array with the provided float value.
	 * 
	 * @param x 0-based index of a pixel in the grid of pixels data structure.
	 * 
	 * @param y 2D position of the element being manipulated within the grid.
	 * 
	 * @param value 2D coordinate and sets its corresponding element of the data array
	 * to that value.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Reverses an array of 4x4 float values by creating a new array with the same
	 * dimensions and copying the original values into it.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
