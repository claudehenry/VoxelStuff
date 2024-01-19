package com.ch.math;

public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

 /**
  * This function initializes a Matrix4f object with the values of an identity matrix.
  * 
  * @returns The output returned by this function is a `Matrix4f` object that represents
  * the identity matrix. In other words，all elements of the matrix are zero except for
  * the diagonal elements which are all equal to one.
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
  * This function initializes a `Matrix4f` object with a translation transformation
  * that moves the object by the specified amount `x`, `y`, and `z` along the x', y',
  * and z' axes respectively.
  * 
  * @param x In the function `initTranslation(float x`, the input parameter `x` is
  * used to set the `x` component of the translated matrix.
  * 
  * @param y The `y` input parameter sets the `y` component of the resulting translation
  * matrix.
  * 
  * @param z The `z` input parameter sets the value of the fourth component of the
  * matrix (the one along the z-axis).
  * 
  * @returns The output returned by the `initTranslation` function is a `Matrix4f`
  * object that represents a translation matrix. Specifically:
  * 
  * 	- The matrix translates the origin of the coordinate system by (x; y; z) units
  * along the three axes respectively.
  * 	- The values stored at each element of the matrix are [1 0 0 0], [0 1 0 0], [0 0
  * 1 0], and [0 0 0 x].
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
  * This function initializes a `Matrix4f` object with a rotation about the XYZ axes
  * based on three input angles (x=x radians) = (y=y radians) = z=z radians), returns
  * the rotated matrix.
  * 
  * @param x The `x` input parameter specifies the angle of rotation around the x-axis.
  * 
  * @param y The `y` input parameter rotates the matrix by the y-axis angle (in radians)
  * around the origin of the coordinate system.
  * 
  * @param z The `z` input parameter specifies the angle of rotation around the z-axis.
  * 
  * @returns The given function initializes a new `Matrix4f` instance and returns it
  * after multiplying three rotation matrices (around x-, y-, and z-axes) together.
  * The returned matrix represents a complete rotation around all three axes.
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
  * This function initializes a `Matrix4f` object with scales only along the x-, y-,
  * and z-axes respectively. It sets all elements off the main diagonal to 0.
  * 
  * @param x The `x` input parameter sets the x-scale component of the resulting
  * Matrix4f object.
  * 
  * @param y In the given function `initScale`, the `y` input parameter sets the scale
  * factor for the second dimension (i.e., the x-axis) of the resulting Matrix4f object.
  * 
  * @param z The `z` input parameter sets the z-component of the scale matrix.
  * 
  * @returns The function `initScale` takes three `float` parameters and returns a new
  * instance of the class `Matrix4f`. The output returned by the function is a matrix
  * that represents a scaling transformation with scales x (in the x-axis), y (in the
  * y-axis), and z (in the z-axis) respectively.
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
  * This function creates a Matrix4f object representing a perspective transformation.
  * It takes four arguments:
  * 
  * 	- fov (field of view): the angle of theFOV (field of view) of the perspective projection.
  * 	- aspectRatio: the aspect ratio of the viewport.
  * 	- zNear: the near clipping plane distance.
  * 	- zFar: the far clipping plane distance.
  * 
  * It then sets the elements of the Matrix4f object to the appropriate values to
  * implement a perspective transformation using the following formulas:
  * 
  * | | tan( FOV/2 )  |
  * | |  1        |
  * | | (zNear + zFar) / zRange  |
  * | |              |
  * | ---       |
  * | | tan( FOV/2 )      |
  * | |   0    |
  * | |    0      |
  * | |    (zNear - zFar) / zRange     |
  * | |       0          |
  * | |_______________|
  * | | 1           |
  * | |  0    |
  * | |   0      |
  * | |       1          |
  * 
  * Note that the zRange term is computed as (zNear + zFar) / 2.
  * 
  * @param fov The `fov` input parameter specifies the field of view (in radians) of
  * the perspective projection matrix.
  * 
  * @param aspectRatio The `aspectRatio` parameter sets the proportional relationship
  * between the width and height of the view frustum's plane surfaces. It is used to
  * adjust the near and far planes such that the width of the frustum matches the width
  * of the viewer's perspective.
  * 
  * @param zNear The `zNear` input parameter controls the near plane of the perspective
  * projection. It sets the distance from the camera where objects appear nearest to
  * the camera.
  * 
  * @param zFar The `zFar` input parameter controls the depth boundary of the frustum
  * created by the perspective transformation. It sets the distance at which objects
  * become infinitely far away.
  * 
  * @returns This function creates and returns a new Matrix4f object with values derived
  * from the specified parameters:
  * 
  * 	- fov (field of view): the angle of vision on the horizontal plane
  * 	- aspectRatio: the ratio of the width to the height of the viewport
  * 	- zNear and zFar: near and far planes for the perspective projection
  * 
  * The output is a Matrix4f object with values that define a perspective projection
  * transformation. Specifically:
  * 
  * 	- The first column (data[0]) defines the X-axis; the second column (data[1])
  * defines the Y-axis; and the third column (data[2]) defines the Z-axis.
  * 	- The fourth column (data[3]) defines the w-component for homogeneous coordinates.
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
  * This function initializes a 4x4 Matrix4f object with an orthographic projection
  * transformation that maps the window coordinates (-1..1) to the device coordinates
  * (-width..width and -height..height) while preserving depth.
  * 
  * @param left The `left` input parameter specifies the left edge of the orthographic
  * camera's viewport. It sets the value of the left column of the projection matrix.
  * 
  * @param right The `right` input parameter sets the distance from the center of the
  * screen to the right edge of the viewport. It is used to compute the scale factors
  * for the x-axis.
  * 
  * @param bottom The `bottom` input parameter sets the near plane of the orthographic
  * projection. It defines the distance from the camera to the nearest point on the
  * image plane.
  * 
  * @param top The `top` input parameter sets the y-value of the orthographic camera's
  * near plane (i.e., the distance from the camera at which objects start to appear clipped).
  * 
  * @param near The `near` input parameter sets the near plane of the orthogonal camera
  * frustum. It determines the distance at which objects appear closest to the camera
  * and are no longer rendered.
  * 
  * @param far The `far` input parameter sets the depth boundary for the frustum created
  * by the orthographic projection. It defines the distance from the viewer at which
  * objects will be considered as being "far away" and will not be rendered.
  * 
  * @returns This function creates a new orthographic projection matrix that frames a
  * 2D image with a near plane and a far plane. The matrix has four columns (0-3) and
  * 16 elements (0-15).
  * 
  * The output returned by this function is the newly created orthographic projection
  * matrix ( Matrix4f).
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
  * This function initializes a 4x4 rotation matrix from three vectors: forward (f),
  * up (u), and their cross product (r).
  * 
  * @param forward The `forward` parameter defines the "forward" direction of the
  * reference frame that the rotation is being applied to. It is used to calculate the
  * other two axes of the rotation matrix (u and r).
  * 
  * @param up The `up` input parameter defines the vertical direction (as opposed to
  * the horizontal forward direction), and is used along with the forward direction
  * to define the orientation of the XY plane of the rotation matrix.
  * 
  * @returns The output returned by this function is a 4x4 float matrix representing
  * a rotation transformation (i.e., a rotational matrices) based on the input vectors
  * `forward`, `up`, and `right` (calculated using the grammar- Schmidt algorithm).
  */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

 /**
  * This function initializes a 4x4 rotation matrix with the specified forward (f),
  * up (u), and right (r) vectors as its rotational components. It sets all translation
  * components to zero.
  * 
  * @param forward The `forward` input parameter defines the forward direction of the
  * local coordinate system. It is used to compute the rotation matrix that aligns the
  * local coordinate system with the world coordinate system.
  * 
  * @param up The `up` input parameter specifies the orientation of the coordinate
  * axes within the rotation matrix. In this case specifically the `u` axis is defined
  * as the up direction. The function initializes the upper 3x3 part of the 4x4 rotation
  * matrix based on the values of `u`, `right` and `forward`.
  * 
  * @param right The `right` parameter specifies the orientation of the X-axis (the
  * third axis) of the resulting matrix.
  * 
  * @returns The function `initRotation` takes three vectors as input (`forward`, `up`,
  * and `right`) and returns a newly initialized Matrix4f object that represents a
  * rotation around the local axes of the specified vectors. The matrix has the following
  * properties:
  * 
  * 	- Row 0 represents the local x-axis (pointing towards the right)
  * 	- Row 1 represents the local y-axis (pointing upwards)
  * 	- Row 2 represents the local z-axis (pointing forwards)
  * 	- Row 3 is the fourth column and represents the scale factor (1.0)
  * 
  * In other words: The function creates a rotation matrix that rotates any point
  * (x,...,z) by the angle of the corresponding axis around the origin.
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
  * This function transforms a vector3f object 'r' by applying a transformation
  * represented by a 4x4 matrix 'data'. It returns a new vector3f object with the
  * transformed coordinates.
  * 
  * @param r The `r` parameter is a Vector3f object that is used as a scalar to multiply
  * each component of the resulting Vector3f value.
  * 
  * @returns The output of this function is a new `Vector3f` object that is transformed
  * from the input `Vector3f` object using a matrix transformation described by the
  * `data` array. The transformation involves multiplying each component of the input
  * vector by the corresponding elements of the `data` array and adding the result to
  * the component of the output vector. In other words:
  * 
  * (output_x = data[0][0] * input_x + data[0][1] * input_y + data[0][2] * input_z +
  * data[0][3])
  * (output_y = data[1][0] * input_x + data[1][1] * input_y + data[1][2] * input_z +
  * data[1][3])
  * (output_z = data[2][0] * input_x + data[2][1] * input_y + data[2][2] * input_z +
  * data[2][3])
  */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

 /**
  * This function performs a multiplication of two matrix4f objects using the following
  * formula:
  * 
  * res(i++, j++) = data[i][0] * r(0%, j) + data[i][1] * r(1%, j) + data[i][2] * r(2%,
  * j) + data[i][3] * r(3%, j)
  * 
  * In other words it performs element-wise multiplication of the two matrices.
  * 
  * @param r The `r` input parameter is a second Matrix4f object that is multiplied
  * element-wise with the current Matrix4f object (the object on which the function
  * is called).
  * 
  * @returns The output returned by this function is a new Matrix4f object 'res' which
  * contains the result of multiplying the current matrix 'this' by another matrix
  * 'r'. The result is calculated by element-wise multiplication of the corresponding
  * elements of 'this' and 'r', and the values are stored back into the res object.
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
  * This function returns a 4x4 array of float values from an underlying 2D array
  * "data". It copies the values from "data" to a new 4x4 floating-point array "res".
  * 
  * @returns The output of this function is a 2-dimensional array of size 4x4 consisting
  * of floating-point numbers.
  */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
 /**
  * This function returns an array of floats representing the values of a 3x4 matrix.
  * 
  * @returns This function returns an array of 12 floating-point numbers representing
  * the linear data.
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
  * This function returns the value of the two-dimensional array `data` at the coordinates
  * `x` and `y`.
  * 
  * @param x The `x` input parameter is used to index into the two-dimensional array
  * `data` to retrieve a specific element at the corresponding position.
  * 
  * @param y In the given function `get(int x)`, the `y` parameter is not used at all
  * and is therefore undefined. The function only depends on the `x` parameter and
  * returns a value based on that.
  * 
  * @returns The output returned by this function is undefined because the "data" array
  * is not defined.
  */
	public float get(int x, int y) {
		return data[x][y];
	}

 /**
  * The `SetM()` function sets the value of the `data` field of the object to the input
  * `float[][]` array `data`.
  * 
  * @param data The `data` input parameter is an array of float arrays and it assigns
  * the contents of the array to the `data` field of the class.
  */
	public void SetM(float[][] data) {
		this.data = data;
	}

 /**
  * This function sets the value of a 2D array (data) at a given position (x and y)
  * to a new value.
  * 
  * @param x In the given function `set(int x)`, `x` is an index to access a specific
  * element of the 2D array `data`. It specifies the row of the array where the specified
  * value should be stored.
  * 
  * @param y The `y` input parameter specifies the row index of the 2D array `data`
  * where the value should be stored.
  * 
  * @param value The `value` input parameter sets the value of the cell at position
  * (`x`, `y`) to a float value.
  */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

 /**
  * This function transposes the two-dimensional array `data` by swapping the rows and
  * columns. It creates a new four-by-four float array `tr`, and then copies the values
  * of `data` into the appropriate position inside `tr`. Finally it sets `this.data`
  * to point to the newly transposed `tr`.
  */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}

