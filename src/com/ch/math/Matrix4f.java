package com.ch.math;

public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

 /**
  * The function `initIdentity()` initializes a `Matrix4f` object with the values of
  * an identity matrix.
  * 
  * 
  * @returns { Matrix4f } The function `initIdentity()` returns a newly created instance
  * of the class `Matrix4f` filled with the values of an identity matrix. The output
  * is a 4x4 matrix with all elements outside the main diagonal equal to zero and all
  * elements on the main diagonal equal to one.
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
  * This function creates a new Matrix4f object and sets its elements to represent a
  * translation by the specified values x/y/z along the corresponding axes.
  * 
  * 
  * @param { float } x - The `x` input parameter sets the z-component of the translation
  * vector.
  * 
  * @param { float } y - The `y` input parameter sets the "forward" component of the
  * translation matrix. It is used to translate the origin along the Y-axis by the
  * specified amount.
  * 
  * @param { float } z - In the given function `initTranslation(float x., float y.,
  * float z)`, the `z` input parameter sets the fourth column of the returned `Matrix4f`
  * object to `z`.
  * 
  * @returns { Matrix4f } The `initTranslation` function takes three floating-point
  * argument and returns a new `Matrix4f` instance with the following transformation:
  * 
  * 	- Translate the origin by (x., y., z.).
  * 
  * In other words:
  * 
  * 	- The matrix rotates the coordinate system by 0 degrees around the old origin.
  * 	- The matrix translates the new origin to (x., y., z.).
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
  * This function initializes a 4x4 rotation matrix using the input x/y/z angles
  * represented as radians. It calculates the rotational matrices for x/y/z axis
  * individually and then composites them to obtain the final rotation matrix.
  * 
  * 
  * @param { float } x - The `x` input parameter specifies the rotation about the
  * x-axis. It is used to compute the components of the rotation matrix for that axis.
  * 
  * @param { float } y - The `y` input parameter represents the second angle of rotation
  * around the y-axis and is used to modify the resulting rotation matrix by combining
  * it with rotations around the x and z axes.
  * 
  * @param { float } z - The `z` input parameter rotates the matrix by the specified
  * angle around the z-axis.
  * 
  * @returns { Matrix4f } The function takes three float arguments representing x ,y
  * and z axis rotations and returns a new Matrix4f object representing the combined
  * rotation of all three axes.
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
  * This function initializes a `Matrix4f` object with scales along the x/, y/, and
  * z-axes respectively.
  * 
  * 
  * @param { float } x - The `x` input parameter sets the x-scale component of the
  * Matrix4f object.
  * 
  * @param { float } y - In the `initScale` function provided above and according to
  * the input signature given `<float x> <float y> <float z>` the value of parameter
  * `y` will solely be assigned to the value stored at position `[1][1]` of `data`.
  * All other position values of `data` won't be influenced by any way.
  * So within the scope of this function 'y' sets the value stored at `[1][1]`.
  * 
  * @param { float } z - The `z` input parameter sets the z-axis scale of the resulting
  * Matrix4f.
  * 
  * @returns { Matrix4f } The output returned by this function is a new Matrix4f object
  * that represents a scale transformation of the input points by the factors x、y and
  * z. In other words，the function takes three arguments of floating-point numbers
  * (x，y，and z), each representing a factor of scaling for the corresponding
  * dimension(x，y，or z). The returned matrix will have all zero elements along the
  * last dimensions(rows and columns), except for the main diagonal elements which
  * represent the factors of scaling.
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
  * This function initializes a Matrix4f object with a perspective projection matrix.
  * It takes four arguments: fov (field of view), aspectRatio (the ratio of the width
  * to height of the viewed area), zNear (near clipping plane), and zFar (far clipping
  * plane). The function returns the updated Matrix4f object.
  * 
  * 
  * @param { float } fov - The `fov` input parameter specifies the field of view (in
  * radians) for which the perspective projection is calculated. It is used to compute
  * the tangent half of the FOV as well as the z range.
  * 
  * @param { float } aspectRatio - The `aspectRatio` parameter determines the width
  * of the FOV (field of view) relative to its height. It is used to calculate the
  * perspective matrix's scaling factors for the x and y axes.
  * 
  * @param { float } zNear - The `zNear` input parameter sets the near clipping plane
  * distance for the perspective projection.
  * 
  * @param { float } zFar - The `zFar` input parameter specifies the far distance of
  * the perspective projection. It determines the range of values that the `z` component
  * of the homogeneous coordinates will take.
  * 
  * @returns { Matrix4f } This function creates and returns a new `Matrix4f` object
  * with a perspective projection matrix defined by the specified parameters. The
  * matrix has the following properties:
  * 
  * 	- It sets the near plane at `zNear` and the far plane at `zFar`.
  * 	- It sets the field of view (FOV) to the given angle (in radians) multiplied by
  * the aspect ratio.
  * 	- It scales the perspective matrix to fit the given range of z values.
  * 
  * The function returns the created `Matrix4f` object.
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
  * This function creates a new Orthographic camera transform with the specified field
  * of view parameters (left/right/bottom/top/near/far) and sets the perspective
  * matrices to map points from camera space to screen space.
  * 
  * 
  * @param { float } left - The `left` input parameter sets the left border of the
  * orthographic projection's view frustum.
  * 
  * @param { float } right - The `right` input parameter sets the right border of the
  * orthogonal perspective frustum. It is used to calculate the scale factors for the
  * view transformation matrix.
  * 
  * @param { float } bottom - The `bottom` input parameter sets the "near" plane of
  * an orthographic projection. It defines the distance from the camera to the near
  * clipping plane.
  * 
  * @param { float } top - The `top` input parameter sets the top edge of the orthographic
  * projection's view frustum. It determines how much of the scene is visible at the
  * top of the viewport.
  * 
  * @param { float } near - The `near` input parameter sets the near clip plane of the
  * orthographic projection. It determines how close objects can be to the camera
  * before they are culled and not rendered.
  * 
  * @param { float } far - The `far` input parameter sets the distance at which objects
  * will appear infinitely far away from the camera. It is used to set the depth
  * boundary of the orthographic projection.
  * 
  * @returns { Matrix4f } This function takes six parameters (left through far) which
  * set the coordinates for a basic frustum edge perspective and returns a newly
  * allocated orthogonal transformation matrix whose 4 rows have four elements each
  * representing coordinates from the data passed to the initialization methods.
  * Ultimately the return value will be an orthogonal affine linear coordinate
  * transformation (not quite strictly identical with the identity matrix) but will
  * represent nothing.
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
  * This function initializes a rotation matrix from three given vectors: forward
  * (representing the x-axis), up (representing the y-axis), and right (representing
  * the z-axis). It uses the following formula to compute the rotation matrix:
  * <p>R = (f cross r) cross u
  * where R is the rotation matrix and f. up and r are the given vectors.</p>
  * 
  * @param { Vector3f } forward - The `forward` parameter defines the forward direction
  * of the camera or object's perspective. It is used to calculate the rotation matrix.
  * 
  * @param { Vector3f } up - The `up` input parameter represents the orientation of
  * the "up" direction of the rotation. It is used to define the third axis of the
  * rotation axis (fraction) frame.
  * 
  * @returns { Matrix4f } This function takes three vectors as input: `forward`, `up`,
  * and `right`. It computes the rotational matrix from these vectors using the
  * Gram-Schmidt process. The output of the function is a rotation matrix represented
  * as a 4x4 float matrix.
  */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

 /**
  * This function initializes a Matrix4f object with rotation parameters represented
  * by three vectors (forward/right/up) and sets the rest of the elements to zero.
  * 
  * 
  * @param { Vector3f } forward - The `forward` input parameter sets the direction of
  * the "forward" axis of the resulting rotation matrix.
  * 
  * @param { Vector3f } up - The `up` input parameter specifies the upward direction
  * of the coordinate system. It is used to define one of the three orthogonal basis
  * vectors for the transformation matrix.
  * 
  * @param { Vector3f } right - The `right` parameter is used to specify the orientation
  * of the X-axis of the rotation matrix.
  * 
  * @returns { Matrix4f } This function takes three vectors (forward., up., and right.)
  * as input and creates a new matrix4f object representing a rotation of the coordinate
  * system based on these vectors. The function returns the newly created matrix4f object.
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
  * This function takes a `Vector3f` parameter `r` and returns a new `Vector3f` with
  * the result of transforming the components of `r` using the data stored at the class
  * level (i.e., the "transform matrix").
  * 
  * 
  * @param { Vector3f } r - The `r` parameter is the vector that should be transformed
  * by the function. It is multiplied element-wise with the coefficients stored on
  * each row of the matrix `data`, and the results are summed to produce the final
  * transformed vector.
  * 
  * @returns { Vector3f } The function takes a `Vector3f` object `r` as input and
  * returns a new `Vector3f` object with the transformed coordinates obtained by
  * multiplying the elements of the input vector with the matrix defined by the `data`
  * array. In other words:
  * 
  * 	- The output is a new vector with the transformed values of the input vector after
  * applying the linear transformation defined by the matrix.
  */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

 /**
  * This function multiplies two Matrix4f objects together. It takes another Matrix4f
  * object as an argument and returns the result of the multiplication.
  * 
  * 
  * @param { Matrix4f } r - The `r` input parameter is the second matrix to be multiplied
  * with the first matrix (the one this function is being called on).
  * 
  * @returns { Matrix4f } The function `mul(Matrix4f r)` returns a new Matrix4f object
  * `res` containing the result of multiplying the input matrix `data` by the input
  * matrix `r`. The output is a 4x4 matrix where each element is the dot product of
  * the corresponding elements of `data` and `r`.
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
  * This function returns a two-dimensional array of four rows and four columns
  * containing the elements of the 'data' array.
  * 
  * 
  * @returns { float[][] } This function returns a two-dimensional array of float
  * values with dimensions 4 x 4. Each element of the array represents a value from
  * the 'data' array passed as an argument to the function. The elements are copied
  * from 'data' to the new array res and returned by the function.
  */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
 /**
  * This function returns an array of float values that represent a linear function.
  * The values are taken from the "data" array and represent the output values for
  * each input value of 0-3.
  * 
  * 
  * @returns { float[] } This function returns an array of floating-point numbers
  * representing the linear data. The array contains 12 elements corresponding to the
  * 3 rows and 4 columns of the input matrix 'data'. Each element is a sum of all the
  * values of the respective row and column of 'data'.
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
  * The given function takes two `int` parameters `x` and `y`, and returns the value
  * of the float array element located at position `x * width + y`, where `width` is
  * some constant int width of the array.
  * 
  * 
  * @param { int } x - The `x` input parameter represents a row index into the 2D array
  * `data`.
  * 
  * @param { int } y - The `y` input parameter specifies the second index of the
  * two-dimensional array that the function should return a value for. It indicates
  * which element of the array should be retrieved based on the `x` parameter.
  * 
  * @returns { float } This function returns undefined for all inputs (x and y). The
  * function declaration states that it returns a float value but the code inside the
  * brackets does not provide any return statement. Therefore there is no defined
  * output value and undefined is returned.
  */
	public float get(int x, int y) {
		return data[x][y];
	}

 /**
  * This function sets the `data` field of the object to the input `data` parameter
  * which is a two-dimensional array of floats.
  * 
  * 
  * @param { float[][] } data - The `data` input parameter is an array of float arrays
  * that is assigned to the instance variable `data` inside the function.
  */
	public void SetM(float[][] data) {
		this.data = data;
	}

 /**
  * This function sets the value of a 2D array (data) at the position specified by x
  * and y to the value passed as an argument.
  * 
  * 
  * @param { int } x - The `x` input parameter represents the row index of the 2D array
  * `data`, and is used to specify which row should be modified when setting a value
  * at a particular position.
  * 
  * @param { int } y - The `y` input parameter specifies the row index of the 2D array
  * `data` that should be set to the given value.
  * 
  * @param { float } value - The `value` input parameter sets the value of the 2D array
  * `data` at the position specified by `x` and `y`.
  */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

 /**
  * This function transposes the current matrix "data" by swapping the rows and columns.
  */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}


