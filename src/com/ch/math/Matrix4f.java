package com.ch.math;

/**
 * TODO
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

 /**
  * initializes a matrix to the identity matrix, where all elements are set to their
  * default (0-based) values.
  * 
  * @returns a matrix with all elements set to either 0 or 1, depending on their
  * position in the matrix.
  * 
  * 	- The `Matrix4f` object is returned as the output, indicating that it has been
  * initialized with an identity matrix.
  * 	- The elements of the matrix are set to their default values, which are 1 for the
  * diagonal elements (row and column), and 0 for all other elements. This ensures
  * that the matrix has no transformation effect when passed through.
  * 	- The returned object is a reference to the original `Matrix4f` instance, indicating
  * that it remains unchanged and can be used further in the program.
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
  * sets the translation components (x, y, z) for a 4D matrix. The x, y and z values
  * are transformed to create a new coordinate system based on the matrix's transformation
  * rules.
  * 
  * @param x 3D translation's x-coordinate in the returned matrix.
  * 
  * @param y 2nd component of the translation vector, which is added to the origin of
  * the matrix.
  * 
  * @param z 3rd translation component in the resulting matrix, which is updated to
  * have a value of 1 in the corresponding position of the matrix.
  * 
  * @returns a reference to the original matrix4f object.
  * 
  * The `Matrix4f` object is returned as the output, which represents a 4x4 homogeneous
  * transformation matrix.
  * 
  * The elements of the matrix are set to specific values, including:
  * 
  * 	- data[0][0] = 1 (the identity matrix has this value)
  * 	- data[0][1] = 0 (this element is ignored)
  * 	- data[0][2] = 0 (the x-axis is not translated)
  * 	- data[0][3] = x (the translation amount on the x-axis)
  * 	- data[1][0] = 0 (the y-axis is not translated)
  * 	- data[1][1] = 1 (the identity matrix has this value)
  * 	- data[1][2] = 0 (the y-axis is not translated)
  * 	- data[1][3] = y (the translation amount on the y-axis)
  * 	- data[2][0] = 0 (the z-axis is not translated)
  * 	- data[2][1] = 0 (this element is ignored)
  * 	- data[2][2] = 1 (the identity matrix has this value)
  * 	- data[2][3] = z (the translation amount on the z-axis)
  * 	- data[3][0] = 0 (the w-axis is not translated)
  * 	- data[3][1] = 0 (this element is ignored)
  * 	- data[3][2] = 0 (the x and y axes are not translated)
  * 	- data[3][3] = 1 (the identity matrix has this value)
  * 
  * In summary, the `initTranslation` function sets the elements of a 4x4 homogeneous
  * transformation matrix to specific values based on the input translation amounts.
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
  * initializes a rotation matrix based on three Euler angles (x, y, z) and returns
  * the resulting matrix.
  * 
  * @param x 3D rotation angle around the z-axis and is used to calculate the rotation
  * matrix rz.
  * 
  * @param y 2D rotation angle around the x-axis, which is used to compute the rotation
  * matrix rz.
  * 
  * @param z 3D rotation angle around the z-axis, which is used to create the third
  * row of the resulting matrix.
  * 
  * @returns a Matrix4f object representing a rotation matrix based on the provided
  * Euler angles.
  * 
  * The `data` field of the returned object is an instance of the `Matrix4f` class,
  * which represents a 4x4 homogeneous transformation matrix. The matrix elements
  * represent the rotation and translation components of the final rotation.
  * 
  * The `data` field has four rows and four columns, each representing a component of
  * the transformation matrix. The first three rows (0-2) represent the rotation
  * component of the transformation, while the last row (3) represents the translation
  * component. Each column represents a different component of the transformation,
  * with the first column representing the x-axis, the second column representing the
  * y-axis, and the third column representing the z-axis.
  * 
  * The elements of the matrix are represented as floating-point numbers, with each
  * element being a complex number representing the magnitude and phase of the
  * corresponding component of the transformation. The magnitude of each element is
  * represented by its absolute value, while the phase is represented by its argument
  * (in radians).
  * 
  * The `initRotation` function initializes the rotation and translation components
  * of the matrix using the input parameters `x`, `y`, and `z`. Specifically, it sets
  * the elements of the `rz` matrix to the complex exponentials of the rotation angles
  * `x`, `y`, and `z`, respectively. It then multiplies the resulting matrix by the
  * matrices `ry` and `rx` to further manipulate the rotation and translation components.
  * Finally, it returns the transformed matrix as its output.
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
  * modifies a matrix's scale factor by setting elements to specified values.
  * 
  * @param x 4th column of the matrix, which is scaled by the value assigned to it.
  * 
  * @param y 2nd element of the scaling matrix in the `initScale()` function.
  * 
  * @param z 2nd element of the scaling vector in the returned matrix.
  * 
  * @returns a reference to the original matrix.
  * 
  * 	- The `Matrix4f` object is returned, indicating that the method modifies the
  * original matrix in place.
  * 	- The data elements of the matrix are set to specific values for the x, y, and z
  * components.
  * 	- The matrix's identity is preserved, as evidenced by the return value being the
  * same object as the original matrix.
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
  * initializes a matrix for perspective projection, setting up the necessary elements
  * to transform points from homogeneous space to screen space according to a specified
  * field of view (FOV), aspect ratio, and near and far distances.
  * 
  * @param fov 90-degree field of view for the perspective projection, which determines
  * the aspect ratio of the output matrix and is used to calculate the tan of half the
  * field of view.
  * 
  * @param aspectRatio 2D aspect ratio of the view frustum, which determines how much
  * the near and far clipping planes are stretched or compressed horizontally when the
  * field of view is changed.
  * 
  * @param zNear near plane distance of the perspective projection, which determines
  * the position of the image plane in the virtual environment.
  * 
  * @param zFar 3D space far clipping plane, which determines how much of the 3D scene
  * is visible from the camera's perspective.
  * 
  * @returns a matrix that represents a perspective projection, with values for each
  * element of the matrix that determine the position and dimensions of the viewport.
  * 
  * 	- `data`: This is an array of 16 floating-point values that represent the components
  * of a 4x4 matrix.
  * 	- `tanHalfFOV`: This variable represents the tan of half of the field of view in
  * radians.
  * 	- `aspectRatio`: This variable represents the aspect ratio of the viewport in the
  * perspective projection.
  * 	- `zNear`: This variable represents the near plane of the perspective projection
  * in meters.
  * 	- `zFar`: This variable represents the far plane of the perspective projection
  * in meters.
  * 	- `zRange`: This variable represents the distance between the near and far planes
  * in meters.
  * 
  * The returned output is a modified version of the original matrix, with new values
  * assigned to specific elements based on the input parameters. The properties of the
  * returned matrix are:
  * 
  * 	- The array has 16 elements, with dimensions [4, 4].
  * 	- The elements are floating-point values between 0 and 1.
  * 	- The matrix is a perspective projection matrix, which means it maps points in
  * 3D space to points on a 2D viewport.
  * 	- The matrix is orthonormal, meaning that its rows and columns are orthonormal vectors.
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
  * initializes a matrix representing an orthographic projection, where the near and
  * far planes are specified, and the left, right, bottom, top, and depth of the view
  * frustum are set.
  * 
  * @param left left side of the orthographic projection, which determines the scale
  * factor for the x-axis of the resulting matrix.
  * 
  * @param right right edge of the orthographic projection, which is used to calculate
  * the values of the matrix's elements.
  * 
  * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection,
  * which is used to determine the size and orientation of the projection matrix.
  * 
  * @param top 2D coordinate of the top-left corner of the orthographic projection,
  * which is used to determine the scale factor for the y-axis in the orthogonal projection.
  * 
  * @param near near clipping plane of the orthographic projection, and is used to
  * calculate the values of the matrix elements responsible for projecting points from
  * behind the near clipping plane onto the image plane.
  * 
  * @param far 4th coordinate of the orthographic projection, which is used to calculate
  * the depth of the projected image.
  * 
  * @returns a reference to the original matrix object.
  * 
  * 	- `data`: This is an array of 16 floating-point values that represent the components
  * of a 4x4 orthogonal projection matrix.
  * 	- Each element in the `data` array has a specific property:
  * 	+ Elements at row 0, column 0 have a value of 2/width, indicating the scaling
  * factor for the x-axis.
  * 	+ Elements at row 0, column 1 have a value of 0, indicating no translation along
  * the x-axis.
  * 	+ Elements at row 0, column 2 have a value of 0, indicating no rotation around
  * the x-axis.
  * 	+ Elements at row 0, column 3 have a value of -(right + left) / width, indicating
  * the translation along the x-axis.
  * 	+ Similarly, elements at row i, column j have values that represent the scaling
  * factor and translation/rotation along the i-th axis for each axis.
  * 	- The `return this;` statement indicates that the function returns a reference
  * to the same matrix object that was passed as an argument.
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
  * initializes a rotation matrix from three vectors: `forward`, `up`, and `u`. The
  * vectors are used to compute the x, y, and z components of the rotation matrix using
  * the dot product and cross product operations.
  * 
  * @param forward 3D direction of the object's forward motion and is used to calculate
  * the rotation axis.
  * 
  * 	- `forward` is a vector in 3D space with a magnitude and direction.
  * 	- The magnitude of `forward` is non-zero, indicating that it represents a direction
  * in space.
  * 	- The direction of `forward` is a unit vector, as it has been normalized to have
  * a length of one.
  * 
  * @param up 3D direction perpendicular to the forward direction, which is used to
  * compute the rotation matrix.
  * 
  * 	- `normalized()` is applied to both `forward` and `up`, which normalizes the
  * vectors to have a length of 1. This ensures that the rotation matrix has units
  * that are consistent throughout.
  * 	- The cross product `r = up.cross(f)` computes a vector `r` that is perpendicular
  * to both `forward` and `up`. This vector will be used as the third column of the
  * rotation matrix.
  * 	- The cross product `u = f.cross(r)` computes a vector `u` that is perpendicular
  * to both `f` and `r`. This vector will be used as the second column of the rotation
  * matrix.
  * 
  * @returns a `Matrix4f` object representing a rotation matrix based on two input vectors.
  * 
  * 	- The output is a `Matrix4f` object representing a rotation matrix.
  * 	- The rotation is defined by three vectors: `forward`, `up`, and `r`.
  * 	- The vectors `f`, `u`, and `r` are normalized, meaning their magnitudes are equal
  * to 1.
  * 	- The resulting rotation matrix is computed using the cross product operation
  * between the vectors.
  */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

 /**
  * sets the elements of a Matrix4f object to represent a rotation based on three
  * vectors: forward, right, and up.
  * 
  * @param forward 3D forward direction of the rotation, which is used to initialize
  * the rotation matrix.
  * 
  * 	- `forward` is a 3D vector representing the forward direction in the rotation.
  * It has three components: `x`, `y`, and `z`.
  * 	- The `x`, `y`, and `z` components of `forward` can take on any real value within
  * their respective ranges.
  * 	- The magnitude of `forward` is the length of the vector, which can be used to
  * determine the orientation of the rotation.
  * 	- The direction of `forward` is the direction in which the rotation is applied.
  * 
  * The properties and attributes of `up` and `right` are similar: they are also 3D
  * vectors representing the upward and rightward directions, respectively.
  * 
  * @param up 3D direction perpendicular to the rotation axis and is used to set the
  * z-component of the rotation matrix.
  * 
  * 	- `up` is a `Vector3f` object representing the up direction vector in 3D space.
  * 	- It has three components: `x`, `y`, and `z`, which represent the coordinates of
  * the direction vector in the x, y, and z axes, respectively.
  * 	- The magnitude (length) of the `up` vector is equal to 1 by default, but it can
  * be modified if desired.
  * 
  * @param right 3D rightward vector, which is used to initialize the rotation matrix's
  * elements.
  * 
  * 	- `r.getX()` returns the x-coordinate of the right vector.
  * 	- `r.getY()` returns the y-coordinate of the right vector.
  * 	- `r.getZ()` returns the z-coordinate of the right vector.
  * 	- `r` is a 3D vector representing the rightward direction.
  * 	- `u.getX()`, `u.getY()`, and `u.getZ()` return the x, y, and z coordinates of
  * the up vector, respectively.
  * 	- `u` is a 3D vector representing the upward direction.
  * 	- `f.getX()`, `f.getY()`, and `f.getZ()` return the x, y, and z coordinates of
  * the forward vector, respectively.
  * 	- `f` is a 3D vector representing the forward direction.
  * 
  * The function then sets the corresponding elements of the matrix `data` to the
  * values of the input vectors. Finally, it returns the transformed matrix.
  * 
  * @returns a `Matrix4f` object representing the rotation matrix.
  * 
  * 	- `data`: This is an array of length 4, where each element is a floating-point
  * value representing the rotation matrix components in the order of (x, y, z, w).
  * The elements are initialized to the corresponding values from the input vectors
  * `forward`, `up`, and `right`.
  * 	- `this`: This refers to the `Matrix4f` object being modified. It is used to
  * indicate that the return value is a reference to the same object being mutated.
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
  * takes a `Vector3f` object `r` as input and returns a new `Vector3f` object with
  * the result of multiplying each component of the input vector by corresponding
  * components of a predefined array `data`, and then adding the result of these
  * multiplications together.
  * 
  * @param r 3D transformation to be applied to the output vector.
  * 
  * 	- `getX()` and `getY()` represent the x-axis and y-axis coordinates of `r`, respectively.
  * 	- `getZ()` represents the z-axis coordinate of `r`.
  * 	- `data[0][0]`, `data[0][1]`, `data[0][2]`, and `data[0][3]` are variables that
  * hold the transformation values for each dimension of the input vector.
  * 	- `data[1][0]`, `data[1][1]`, `data[1][2]`, and `data[1][3]` are similar to `data[0]`.
  * 	- `data[2][0]`, `data[2][1]`, `data[2][2]`, and `data[2][3]` are similar to `data[0]`.
  * 
  * @returns a new Vector3f object containing the result of multiplying each component
  * of the input vector `r` by the corresponding components of the input matrix `data`.
  * 
  * 	- The first component of the output is calculated as `data[0][0] * r.getX() +
  * data[0][1] * r.getY() + data[0][2] * r.getZ()`.
  * 	- The second component of the output is calculated as `data[1][0] * r.getX() +
  * data[1][1] * r.getY() + data[1][2] * r.getZ()`.
  * 	- The third component of the output is calculated as `data[2][0] * r.getX() +
  * data[2][1] * r.getY() + data[2][2] * r.getZ()`.
  * 
  * The output is a new `Vector3f` object with values calculated by multiplying the
  * corresponding components of the input `r` vector with the corresponding components
  * of the fixed function `data`.
  */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

 /**
  * multiplies a matrix by another matrix, component-wise, and returns the result as
  * a new matrix.
  * 
  * @param r 4x4 matrix that is multiplied with the current matrix to produce the
  * result matrix.
  * 
  * The `r` object is an instance of `Matrix4f`, which represents a 4x4 matrix in
  * homogeneous coordinates. It has four rows and four columns, each containing 32-bit
  * floating-point values representing the elements of the matrix. The elements are
  * arranged in a row-major order, meaning that the elements of each row are stored
  * in consecutive memory locations.
  * 
  * The `r` object has several attributes that can be used to manipulate it:
  * 
  * 	- `get(int r, int c)`: Returns the element at position `(r, c)` as a 32-bit
  * floating-point value.
  * 	- `set(int r, int c, float value)`: Sets the element at position `(r, c)` to the
  * specified value.
  * 	- `get(int[] rows, int[] cols)`: Returns an array of 32-bit floating-point values
  * representing the elements of the matrix in the specified rows and columns.
  * 	- `set(int[] rows, int[] cols, float[] values)`: Sets the elements of the matrix
  * in the specified rows and columns to the specified values.
  * 
  * @returns a new Matrix4f object containing the result of multiplying the input
  * matrix with another unknown matrix.
  * 
  * The `res` variable is initialized as a new instance of the `Matrix4f` class.
  * 
  * The elements of the output matrix are calculated by multiplying corresponding
  * elements of the input matrices and storing them in the output matrix. The indices
  * of the output matrix are zero-based, meaning that the first element of the output
  * matrix corresponds to the first element of the input matrices, and so on.
  * 
  * The `set` method is used to set the elements of the output matrix. The method takes
  * two integers, `i` and `j`, representing the row and column index of the element
  * to be set, respectively, and a single integer `value` representing the value to
  * be set. In this case, the value being set is the result of multiplying the
  * corresponding elements of the input matrices.
  * 
  * The output matrix has four elements, each representing a 4D vector in homogeneous
  * coordinates. The elements are represented by floating-point numbers and have a
  * range of values between -1 and 1, inclusive.
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
  * generates an array of floats, `res`, with dimensions 4x4 by copying values from
  * an existing array, `data`.
  * 
  * @returns an array of arrays of float values.
  */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
 /**
  * returns an array of floats representing a linear data set with 6 elements, each
  * element being a 2D array with 4 elements (x, y coordinates).
  * 
  * @returns an array of 12 float values.
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
  * retrieves a value from a 2D array `data`, based on the coordinates `x` and `y`.
  * The value is returned as a `float`.
  * 
  * @param x 0-based index of the pixel location within the 2D array 'data'.
  * 
  * @param y 2nd dimension of the data array being accessed by the function, which is
  * used to compute the desired value.
  * 
  * @returns a floating-point value representing the element at the specified position
  * in a 2D array.
  */
	public float get(int x, int y) {
		return data[x][y];
	}

 /**
  * sets the value of a member field `data`.
  * 
  * @param data 2D array of float values that will be stored in the class instance
  * variable `data`.
  * 
  * 	- The input `data` is an array of arrays, with each inner array being of type float.
  * 	- The length of the inner array can vary, depending on the input provided.
  * 	- The elements of the inner array represent the values to be processed by the function.
  */
	public void SetM(float[][] data) {
		this.data = data;
	}

 /**
  * updates the element at position (x, y) of a two-dimensional array with the provided
  * floating-point value.
  * 
  * @param x 0-based index of a cell in the grid, where the value is being assigned to.
  * 
  * @param y 2D coordinate position of the data element being updated in the `data` array.
  * 
  * @param value 3D vector value that is assigned to the corresponding elements of the
  * 2D array `data`.
  */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

 /**
  * transposes an array of floats by copying its elements to a new array, and assigns
  * the new array to the function's field `data`.
  */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
