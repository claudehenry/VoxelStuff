package com.ch.math;

/**
 * Is a 4x4 matrix representation class that provides various methods for manipulating
 * and transforming matrices. It has several methods such as initRotation, transform,
 * mul, getData, getLinearData, get, set, transposeSelf, etc. These methods allow
 * users to perform various operations on the matrix, including rotation, translation,
 * scaling, and more. The class also provides a data array that stores the matrix
 * elements, which can be accessed through the getData() method.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * Initializes a Matrix4f object to the identity matrix, setting all elements to their
	 * default values.
	 * 
	 * @returns a Matrix4f object with identity matrix values.
	 * 
	 * The `Matrix4f` object is initialized to have an identity matrix, with all elements
	 * set to either 0 or 1. Specifically, the elements in the first row and column are
	 * set to [1, 0, 0, 0], and the remaining elements are set to [0, 1, 0, 0]. This means
	 * that when this matrix is multiplied by any other matrix, it will result in a matrix
	 * with the same elements as the original matrix.
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
	 * Initializes a matrix with translation coordinates, performing simple transformations
	 * to the x, y, and z components of the matrix.
	 * 
	 * @param x 3D translation component along the x-axis in the generated matrix.
	 * 
	 * @param y 2D translation of the matrix in the y-axis direction, which is added to
	 * the previous value stored in the matrix at position (1, 0).
	 * 
	 * @param z 3rd component of the translation vector, which is added to the matrix's
	 * second row.
	 * 
	 * @returns a reference to the original matrix.
	 * 
	 * * The `Matrix4f` object is returned, which represents a 4x4 homogeneous transformation
	 * matrix.
	 * * The matrix elements have been updated to reflect the translation (x, y, z) applied
	 * to the origin of the coordinate system. Specifically, the x, y, and z components
	 * of each element in the matrix have been modified accordingly.
	 * * The matrix remains invertible, meaning it can be used to transform vectors or
	 * points in 3D space.
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
	 * Initializes a rotation matrix based on three Euler angles (x, y, z) and returns
	 * the transformed matrix.
	 * 
	 * @param x 3D rotation around the x-axis and is used to calculate the z-component
	 * of the resulting rotation matrix.
	 * 
	 * @param y 2D rotation angle about the x-axis, which is used to compute the 3D
	 * rotation matrix `ry`.
	 * 
	 * @param z 3D rotation axis around which the rotation is performed, and it is used
	 * to calculate the rotation matrix `rz`.
	 * 
	 * @returns a new Matrix4f object representing the rotation matrix.
	 * 
	 * * The `data` field contains the transformed rotation matrix. It is a 4x4 matrix
	 * representing a rotation in 3D space.
	 * * The matrix elements represent the rotations around the x, y, and z axes,
	 * respectively. Each element is a complex number representing the sin and cos values
	 * of the corresponding angle.
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
	 * Sets the scale factors for the matrix's elements, assigning new values to the
	 * matrix's data array and returning a reference to the modified matrix.
	 * 
	 * @param x 3D translation amount along the x-axis when initializing the matrix.
	 * 
	 * @param y 2D scaling factor for the matrix in the `initScale()` function.
	 * 
	 * @param z 2nd dimension of the scale factor in the matrix, which is used to transform
	 * the coordinate values in the 2D plane.
	 * 
	 * @returns a reference to the original matrix.
	 * 
	 * The `Matrix4f` object is returned unchanged.
	 * 
	 * The input parameters `x`, `y`, and `z` are assigned to the respective elements of
	 * the matrix's data array.
	 * 
	 * The matrix's data array has four rows and four columns, with each element representing
	 * a 3D coordinate in the form of (x, y, z).
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
	 * Initializes a matrix for a perspective projection, setting elements to define the
	 * field of view, aspect ratio, near and far plane distances, and other parameters
	 * needed for perspective transformation.
	 * 
	 * @param fov field of view, which determines the angle of the perspective projection
	 * and is used to calculate the tan(halfFOV) value needed for the matrix initialization.
	 * 
	 * @param aspectRatio 2D screen aspect ratio of the viewport, which determines the
	 * scaling factor for the x- and y-axes when projecting the 3D space onto the 2D image.
	 * 
	 * @param zNear near clipping plane distance in the perspective projection, which
	 * determines the range of values that are transformed from the original coordinate
	 * system to the view-dependent coordinate system.
	 * 
	 * @param zFar 4th coordinate of the projection matrix, which is used to calculate
	 * the height of objects in the virtual environment.
	 * 
	 * @returns a `Matrix4f` object with values that define a perspective projection matrix.
	 * 
	 * * The `data` array has 4 rows and 16 columns, representing the 4D homogeneous
	 * coordinates of a perspective projection matrix.
	 * * Each column represents a component of the projection matrix, with the first three
	 * components representing the x, y, and z positions of the projection, respectively,
	 * and the last component representing the w component, which is the weight for the
	 * perspective transformation.
	 * * The values in each row are the coefficients of the perspective projection matrix,
	 * which are computed based on the input fov, aspectRatio, zNear, and zFar parameters.
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
	 * Sets up an orthographic projection matrix with given dimensions, aspect ratio, and
	 * near and far clipping planes.
	 * 
	 * @param left left coordinate of the orthographic projection, which is used to scale
	 * the matrix's rows and columns.
	 * 
	 * @param right right side of the orthographic projection, which is used to calculate
	 * the coordinates of the pixels in the resulting image.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection,
	 * which is used to determine the dimensions of the projection matrix.
	 * 
	 * @param top 2D coordinate of the upper-left corner of the view frustum, which is
	 * used to calculate the width and height of the frustum in the formula for `data[1][3]`.
	 * 
	 * @param near near clipping plane of the orthographic projection, which determines
	 * the distance from the viewer at which objects appear to be in front of or behind
	 * the near plane.
	 * 
	 * @param far 3D far clip plane distance, which determines how far away objects can
	 * be rendered in the orthogonal projection.
	 * 
	 * @returns a matrix representing an orthographic projection of a 3D space.
	 * 
	 * * The matrix is a 4x4 Orthographic projection matrix.
	 * * The data array contains the values for each element of the matrix, with the first
	 * three elements representing the x, y, and z scaling factors, and the last three
	 * elements representing the x, y, and z translation vectors.
	 * * The matrix will transform points in 3D space from an orthographic viewpoint,
	 * where the near and far clipping planes are defined by the `near` and `far` parameters,
	 * respectively.
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
	 * Initializes a rotation matrix based on two input vectors: `forward` and `up`. It
	 * computes the cross product of these vectors to obtain the rotation axis and then
	 * sets the rotation angle using the resulting vector. The rotation angle is then
	 * applied to create a rotation matrix that can be used for 3D transformations.
	 * 
	 * @param forward 3D direction of the rotation axis.
	 * 
	 * @param up 3D direction perpendicular to the forward direction, which is used to
	 * calculate the rotation matrix.
	 * 
	 * @returns a Matrix4f object representing a rotation matrix based on the input vectors.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * Sets the rotation matrix based on three input vectors: `forward`, `right`, and
	 * `up`. It returns the updated rotation matrix.
	 * 
	 * @param forward 3D direction of rotation, which is used to initialize the x, y, and
	 * z components of the rotation matrix.
	 * 
	 * * `forward` is a 3D vector representing the direction of forward motion.
	 * 
	 * @param up 3D vector that points upwards from the rotation axis, which is used to
	 * determine the rotation matrix's vertical component.
	 * 
	 * * `up` is a vector representing the upward direction in the 3D space.
	 * 
	 * @param right 3D right vector of the rotation axis, which is used to initialize the
	 * components of the matrix.
	 * 
	 * * `right`: A vector representing the rightward direction of the rotation. Its
	 * magnitude is typically non-zero, indicating that the rotation is not purely around
	 * the x, y, or z axis.
	 * 
	 * @returns a `Matrix4f` object representing the rotation matrix.
	 * 
	 * The output is a `Matrix4f` object, which represents a 4x4 homogeneous transformation
	 * matrix.
	 * 
	 * The elements of the matrix are set based on the input vectors `forward`, `up`, and
	 * `right`. Specifically, the elements in the first two rows represent the rotation
	 * components around the x, y, and z axes, respectively, while the elements in the
	 * third row represent the translation component along the fourth axis. The element
	 * in the fourth row is always 1, indicating that the matrix is a full rotation matrix.
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
	 * of multiplying the components of the input vector by the corresponding components
	 * of a set of data values, stored in an array.
	 * 
	 * @param r 4D vector that is multiplied element-wise with the 3D vector returned by
	 * the function.
	 * 
	 * @returns a new Vector3f object containing the result of multiplying each element
	 * of the input Vector3f object by the corresponding elements of a set of data,
	 * followed by addition of the resultant values.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * Multiplies two matrix objects and returns the result as a new matrix object.
	 * 
	 * @param r 4x4 matrix to be multiplied with the current matrix.
	 * 
	 * * It is an instance of the `Matrix4f` class.
	 * * It represents a 4x4 homogeneous matrix.
	 * 
	 * @returns a new Matrix4f object containing the result of multiplying the input
	 * matrix by another matrix.
	 * 
	 * * The `res` variable is initialized as an instance of `Matrix4f`.
	 * * The values of the input matrices `data` and `r` are multiplied element-wise to
	 * produce the output.
	 * * Each element of the output matrix is calculated by multiplying the corresponding
	 * elements of `data` and `r`, using the indexing mechanism provided by the matrix structure.
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
	 * Returns an array of arrays, each element being a copy of the original array `data`.
	 * 
	 * @returns an array of arrays, where each inner array contains four elements
	 * representing the input data.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * Returns an array of float values, representing a linear data set with nine data
	 * points in two dimensions.
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
	 * Retrieves a value from a 2D array `data`. The value is located at position (x, y)
	 * and returned as a float.
	 * 
	 * @param x 1D coordinate of the point in the data array to be retrieved.
	 * 
	 * @param y 2nd dimension of the data array being accessed by the function.
	 * 
	 * @returns a floating-point value representing the element at position (x, y) of a
	 * 2D array.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * Sets the value of a private field `data` to a provided `float[][]` argument.
	 * 
	 * @param data 2D array of float values that will be stored in the `data` field of
	 * the calling object.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * Sets a value at a specific position in a two-dimensional array of integers and floats.
	 * 
	 * @param x 0-based index of a cell in the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array being manipulated, and it determines which
	 * element in the array is being assigned the `value` parameter.
	 * 
	 * @param value 3D coordinate value that will be assigned to the corresponding cell
	 * in the `data` array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * Transforms an array of arrays into a new array by swapping elements between rows
	 * and columns.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
