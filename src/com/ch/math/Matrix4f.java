package com.ch.math;

/**
 * Represents a 4x4 matrix used for 3D transformations, providing methods for
 * initialization, transformation, and matrix operations.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Sets the matrix to its default identity state, where the main diagonal elements
	 * are 1 and the rest are 0, allowing for the representation of a standard coordinate
	 * system.
	 *
	 * @returns a 4x4 matrix representing the identity transformation with all diagonal
	 * elements equal to 1.
	 *
	 * The returned output is a 4x4 identity matrix with diagonal elements set to 1 and
	 * non-diagonal elements set to 0.
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
	 * Sets the translation values in a 4x4 matrix, allowing for 3D object movement. It
	 * initializes the first three columns of the matrix's last row to (x, y, z) and the
	 * remaining elements to zero, effectively translating the object by the specified coordinates.
	 *
	 * @param x translation along the x-axis, with a negative value effectively moving
	 * the object to the right.
	 *
	 * @param y y-coordinate of the translation vector, which is used to set the second
	 * column of the matrix.
	 *
	 * @param z depth translation along the z-axis.
	 *
	 * @returns a 4x4 translation matrix with specified x, y, and z values.
	 *
	 * It is a 4x4 matrix with a translation component. The translation is represented
	 * by the last column (column 3) of the matrix, where the elements are x, y, and z.
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
	 * Constructs a rotation matrix from three Euler angles (x, y, z) and stores the
	 * result in the current matrix. The rotation is performed in the order z-y-x (right-hand
	 * rule).
	 *
	 * @param x rotation angle around the x-axis.
	 *
	 * @param y rotation angle around the y-axis.
	 *
	 * @param z rotation around the z-axis
	 *
	 * @returns a 4x4 rotation matrix representing the specified rotation around x, y,
	 * and z axes.
	 *
	 * The returned output is a 4x4 Matrix4f representing a rotation transformation.
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
	 * Initializes a 4x4 matrix with a scale transformation. It sets the first row to the
	 * x scale factor, the second row to the y scale factor, and the third row to the z
	 * scale factor, with the fourth row being the identity matrix.
	 *
	 * @param x scale factor along the x-axis.
	 *
	 * @param y scale factor applied along the y-axis.
	 *
	 * @param z scale factor along the z-axis in the 3D space.
	 *
	 * @returns a 4x4 matrix initialized with scale values along the x, y, and z axes.
	 *
	 * The output is a 4x4 matrix with the scale factors x, y, and z on the diagonal, and
	 * all other elements set to 0 or 1. The scale factors are located at data[0][0],
	 * data[1][1], and data[2][2].
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
	 * Initializes a 4x4 matrix for a perspective projection with the given field of view,
	 * aspect ratio, near, and far clipping planes. It calculates the matrix elements
	 * based on the provided parameters to achieve a perspective projection transformation.
	 *
	 * @param fov field of view angle, which determines the perspective of the projection.
	 *
	 * @param aspectRatio ratio of the width to the height of the viewport, used to
	 * calculate the field of view in the x and y axes.
	 *
	 * @param zNear distance from the camera to the near clipping plane, with values
	 * closer to the camera resulting in a more zoomed-in view.
	 *
	 * @param zFar farthest point along the z-axis from the viewer, affecting the perspective
	 * projection's depth range.
	 *
	 * @returns a modified 4x4 matrix representing a perspective projection transformation.
	 *
	 * The returned output is a 4x4 matrix with specific values. The matrix represents a
	 * perspective projection transformation.
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
	 * Calculates a rotation matrix based on a given forward and up vector.
	 * It normalizes the input vectors, computes a right vector, and uses it to construct
	 * the rotation matrix.
	 * The result is a rotation matrix representing a rotation around the calculated right
	 * axis.
	 *
	 * @param forward direction of the rotation axis in 3D space.
	 *
	 * @param up desired direction of the local up axis in the resulting rotation matrix.
	 *
	 * @returns a 4x4 rotation matrix representing a rotation based on the given forward
	 * and up vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Populates the elements of a 4x4 matrix with the components of the input vectors,
	 * representing a rotation matrix in 3D space. The vectors define the orientation of
	 * the matrix in the forward, up, and right directions. The function returns the
	 * matrix instance.
	 *
	 * @param forward direction in which the rotation is performed, with its components
	 * used to populate the third row of the rotation matrix.
	 *
	 * Extract its X, Y, Z components.
	 *
	 * @param up direction of the Y-axis in the resulting rotation matrix.
	 *
	 * Normalize.
	 *
	 * @param right direction of the x-axis in the new coordinate system and is used to
	 * initialize the first column of the rotation matrix.
	 *
	 * Extracts the x, y, and z components of the `right` vector.
	 *
	 * @returns a 4x4 rotation matrix populated with the given forward, up, and right
	 * vector components.
	 *
	 * It is a 4x4 matrix.
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
	 * Performs a linear transformation on a 3D vector `r` using a 3x4 transformation
	 * matrix `data`.
	 *
	 * @param r 3D vector being transformed, with its x, y, and z components used to
	 * compute the result of the transformation.
	 *
	 * @returns a new `Vector3f` object resulting from a linear transformation of the
	 * input vector.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Performs matrix multiplication between two 4x4 matrices, resulting in a new 4x4
	 * matrix. The function takes another 4x4 matrix as input and returns the product of
	 * the two matrices. This operation is essential in 3D graphics and linear algebra.
	 *
	 * @param r matrix to be multiplied with the current matrix, with the result being
	 * stored in the `res` matrix.
	 *
	 * Decompose.
	 * It is a 4x4 matrix of type `Matrix4f`.
	 *
	 * @returns a new 4x4 matrix resulting from the multiplication of the input matrix
	 * with the parameter matrix.
	 *
	 * The output is a 4x4 matrix representing the result of matrix multiplication.
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
	 * Copies a 4x4 array of data into a new 4x4 array and returns it. The data is copied
	 * from a presumably existing 4x4 array `data`. The function does not modify the
	 * original data.
	 *
	 * @returns a 4x4 2D array of floats, containing data from another source.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Returns a float array containing the first column of a 4x4 data array.
	 *
	 * @returns an array of 16 float values representing the first row and column of a
	 * 4x4 matrix.
	 *
	 * The returned output is an array of 16 floats. It appears to contain a subset of
	 * the data, with elements arranged in a linear fashion, presumably representing a
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
	 * Retrieves a float value from a 2D array at the specified coordinates x and y. It
	 * returns the value at the position data[x][y].
	 *
	 * @param x row index of the 2D array `data` from which the function retrieves a value.
	 *
	 * @param y column index in a 2D array, used to access an element at a specific position.
	 *
	 * @returns the value of the element at the specified 2D array index.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Assigns a 2D array of type float to the `data` field of the current object. The
	 * array is stored directly without any validation or transformation.
	 *
	 * @param data 2D array of float values to be assigned to the `data` field of the class.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Transposes a 4x4 matrix by swapping its elements, effectively flipping it over its
	 * main diagonal. The original matrix is replaced with its transposed version. This
	 * operation is commonly used in linear algebra and matrix transformations.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
