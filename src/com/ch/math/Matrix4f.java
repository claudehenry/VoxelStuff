package com.ch.math;

/**
 * in Java is a data structure for representing 4x4 matrices. It provides various
 * methods for setting and getting values of matrix elements, as well as methods for
 * transforming vectors and matrices with the help of these matrices. The class also
 * provides methods for scaling, rotating, and perspectively projecting vectors and
 * matrices. Additionally, it offers a method for initializing matrices from given values.
 */
public class Matrix4f {
	
	private float[][] data;

	public Matrix4f() {
		data = new float[4][4];
	}

	/**
	 * initializes a matrix with identity values, meaning its elements are equal to their
	 * own row and column sums.
	 * 
	 * @returns a reference to the same Matrix4f object.
	 * 
	 * The `Matrix4f` object is returned as the output, which represents an identity
	 * matrix in 4D space.
	 * The elements of the matrix are initialized to their default values, which are 0
	 * for all the elements in the main diagonal and 1 for all the elements off-diagonal.
	 * The matrix has dimensions 4x4, with each element represented as a floating-point
	 * number between -2^31 and 2^31-1.
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
	 * initializes a matrix with translation data, where the x, y, and z components are
	 * set to the input parameters. It returns the initialized matrix.
	 * 
	 * @param x 3D translation amount in the x-axis direction, which is negated before
	 * being stored in the matrix data.
	 * 
	 * @param y 2D translation of the matrix in the y-axis direction, which is added to
	 * the previous value of the y-axis component of the matrix.
	 * 
	 * @param z 3rd coordinate of the translation vector, which is added to the matrix's
	 * upper-left element to transform the coordinate system.
	 * 
	 * @returns a reference to the original matrix.
	 * 
	 * The `Matrix4f` object is returned as the output, indicating that the method modifies
	 * the original matrix in place.
	 * 
	 * The data array of the matrix is modified by setting specific elements to specific
	 * values. The elements are referenced using their zero-based index [0][0] - [3][3].
	 * 
	 * The x, y, and z values passed as parameters are assigned to the corresponding
	 * elements of the data array. Specifically:
	 * 
	 * 	- `data[0][0]` is set to 1.
	 * 	- `data[0][1]` is set to 0.
	 * 	- `data[0][2]` is set to 0.
	 * 	- `data[0][3]` is set to the negative of the input `x` value.
	 * 
	 * Similarly, the y and z values passed as parameters are assigned to the corresponding
	 * elements of the data array:
	 * 
	 * 	- `data[1][0]` is set to 0.
	 * 	- `data[1][1]` is set to 1.
	 * 	- `data[1][2]` is set to 0.
	 * 	- `data[1][3]` is set to the input `y` value.
	 * 
	 * 	- `data[2][0]` is set to 0.
	 * 	- `data[2][1]` is set to 0.
	 * 	- `data[2][2]` is set to 1.
	 * 	- `data[2][3]` is set to the input `z` value.
	 * 
	 * The elements of the data array are assigned values in a specific order based on
	 * their indices, without any additional operations or modifications beyond what is
	 * explicitly stated above.
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
	 * initializes a rotation matrix based on three Euler angles (x, y, z). It generates
	 * three rotation matrices (`rx`, `ry`, and `rz`) and then multiplies them to produce
	 * the final rotation matrix. The returned matrix represents the rotated coordinate
	 * system.
	 * 
	 * @param x 3D rotation around the x-axis, which is applied to the matrix data in the
	 * `rz` variable.
	 * 
	 * @param y 2D rotation angle around the z-axis, which is used to calculate the
	 * rotation matrix rz.
	 * 
	 * @param z 3D rotation axis around which the matrix is rotated, and its value
	 * determines the angle of rotation.
	 * 
	 * @returns a new `Matrix4f` object containing the rotation matrix based on the input
	 * angles.
	 * 
	 * 	- The `data` field is an instance of the `Matrix4f` class, representing a 4x4
	 * matrix of floating-point values.
	 * 	- The elements of the matrix correspond to the rotation matrix representation in
	 * homogeneous coordinates (i.e., x, y, z, and w).
	 * 	- The matrix has four rows (or columns) representing the rotation around the x,
	 * y, and z axes, respectively.
	 * 	- Each row represents a 4x1 vector of floating-point values, where each element
	 * corresponds to a factor in the rotation.
	 * 	- The matrix is created by multiplying three matrices: `rz`, `ry`, and `rx`. These
	 * matrices are constructed from the input arguments `x`, `y`, and `z` using mathematical
	 * operations such as sine, cosine, and multiplication.
	 * 	- The resulting matrix `data` represents the rotation of a 3D object based on the
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
	 * initializes a matrix with scaled values for x, y, and z axes, returning a reference
	 * to the updated matrix.
	 * 
	 * @param x 3D scaling factor along the x-axis of the matrix.
	 * 
	 * @param y 2nd coordinate of the scaling vector in the Matrix4f object.
	 * 
	 * @param z 3rd component of the scale vector, which is used to transform the matrix's
	 * data elements in the (2,2) position.
	 * 
	 * @returns a reference to the original matrix object.
	 * 
	 * 	- The output is an instance of the `Matrix4f` class.
	 * 	- The `data` array of the matrix has been modified to set the elements corresponding
	 * to the scale factors `x`, `y`, and `z`.
	 * 	- The resulting matrix has a determinant of 1, indicating that it is an identity
	 * matrix.
	 * 	- The matrix has the same dimensions as the original matrix, i.e., 4x4.
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
	 * initializes a matrix for perspective projection, setting elements based on field
	 * of view (fov), aspect ratio, near and far distances.
	 * 
	 * @param fov field of view, which determines the angle of the perspective projection
	 * and is used to calculate the tan of half the field of view.
	 * 
	 * @param aspectRatio 2D viewport aspect ratio, which is used to scale the near and
	 * far planes of the perspective projection accordingly.
	 * 
	 * @param zNear near plane distance of the perspective projection, which determines
	 * the distance from the viewer at which objects appear to be at their actual size.
	 * 
	 * @param zFar 3D space far clip plane, which determines the distance beyond which
	 * objects are clipped and removed from rendering.
	 * 
	 * @returns a `Matrix4f` object that represents a perspective projection matrix.
	 * 
	 * 	- `data`: This is an array of four elements, where each element represents a
	 * component of the 4x4 homogeneous transformation matrix. The elements are:
	 * 	+ `0,0`: scaling factor for the x-axis
	 * 	+ `0,1`: translation vector along the y-axis
	 * 	+ `1,0`: rotation angle around the z-axis
	 * 	+ `0,0,0,1`: identity element
	 * 	- Each element is assigned a value based on the input parameters:
	 * 	+ `fov`: The field of view in radians, used to compute the scaling factor for the
	 * x-axis.
	 * 	+ `aspectRatio`: The aspect ratio of the viewport, used to compute the scaling
	 * factor for the y-axis.
	 * 	+ `zNear`: The near clipping plane distance in homogeneous coordinates.
	 * 	+ `zFar`: The far clipping plane distance in homogeneous coordinates.
	 * 	- The values assigned to each element are:
	 * 	+ `1.0f / (tanHalfFOV * aspectRatio)` for the scaling factor for the x-axis.
	 * 	+ `0` for the translation vector along the y-axis.
	 * 	+ `0` for the rotation angle around the z-axis.
	 * 	+ `1` for the identity element.
	 * 	- The returned output is a reference to the same matrix object, allowing it to
	 * be chainable and modifiable.
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
	 * initializes a matrix with orthographic projection parameters, where the near plane
	 * is at a distance of `near`, and the far plane is at a distance of `far`. The width,
	 * height, and depth of the view frustum are set to `right - left`, `top - bottom`,
	 * and `far - near`, respectively.
	 * 
	 * @param left left edge of the orthogonal projection, which determines the scale
	 * factor for the x-axis in the resulting matrix.
	 * 
	 * @param right right edge of the orthographic projection, which is used to calculate
	 * the scaling factors for the matrix.
	 * 
	 * @param bottom 2D coordinate of the bottom-left corner of the orthographic projection,
	 * which is used to determine the width and height of the projection.
	 * 
	 * @param top 2D coordinate of the top of the orthographic projection, which is used
	 * to calculate the values of the matrix's elements.
	 * 
	 * @param near near plane of the orthographic projection, and is used to calculate
	 * the ratio of the far plane to the near plane in the matrix generation process.
	 * 
	 * @param far 3D distance from the camera to the farthest point in the scene, which
	 * is used to calculate the near clipping plane and the depth of field.
	 * 
	 * @returns a reference to the original matrix object, which has been transformed to
	 * an orthographic projection matrix.
	 * 
	 * 	- `data`: This is an array of 16 floats that represent the elements of the matrix.
	 * Each element is initialized with a value that depends on the input parameters.
	 * 	- The dimensions of the matrix are determined by the values of `left`, `right`,
	 * `bottom`, `top`, `near`, and `far`. Specifically, the width and height of the
	 * matrix are equal to `right - left` and `top - bottom`, respectively, while the
	 * depth is equal to `far - near`.
	 * 	- The elements of the matrix are normalized based on their respective dimensions.
	 * For example, the element at position `(0, 0)` is set to `2 / width`, where `width`
	 * is the width of the matrix. Similarly, the element at position `(1, 1)` is set to
	 * `2 / height`, and so on.
	 * 	- The matrix elements are also transformed based on their positions in the matrix.
	 * For example, the element at position `(0, 0)` is transformed by `-(right + left)
	 * / width`, while the element at position `(1, 1)` is transformed by `-(top + bottom)
	 * / height`.
	 * 
	 * Overall, the `initOrthographic` function returns a matrix that represents an
	 * orthographic projection with the specified dimensions and transformation properties.
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
	 * generates a rotation matrix from a forward and up vector. It returns the rotation
	 * matrix after cross-product operations on the vectors.
	 * 
	 * @param forward 3D direction in which the rotation will be applied.
	 * 
	 * 	- `forward` is a vector in 3D space with a magnitude and direction, represented
	 * by two floating-point values (x, y, z).
	 * 	- The magnitude of `forward` is non-zero, indicating that it is a valid direction
	 * vector.
	 * 	- The direction of `forward` is normalized to have a length of one, ensuring that
	 * it is a unit vector.
	 * 
	 * @param up 3D direction perpendicular to the forward direction and is used to
	 * calculate the rotation axis.
	 * 
	 * 	- `normalized()` is applied to `up` to ensure that it has unit length in all
	 * directions, which is required for proper rotation matrix computation.
	 * 	- `cross()` is used to compute the cross product of two vectors, which provides
	 * a new vector perpendicular to both input vectors. In this case, `r = up.cross(forward)`
	 * gives a vector that is perpendicular to both `forward` and `up`.
	 * 	- `u = f.cross(r)` computes a third vector that is perpendicular to both the
	 * previous two vectors. This vector will be used as the third element of the rotation
	 * matrix.
	 * 
	 * @returns a `Matrix4f` object representing a rotation matrix based on the input vectors.
	 * 
	 * The output is a `Matrix4f` object representing a rotation matrix.
	 * The rotation is represented by three axes: `f`, `r`, and `u`. These axes are
	 * normalized and represent the x, y, and z components of the rotation, respectively.
	 * The rotation is performed around the `up` axis, which is also normalized.
	 * The resulting rotation matrix can be used to transform points or vectors in 3D space.
	 */
	public Matrix4f initRotation(Vector3f forward, Vector3f up) {
		Vector3f f = forward.normalized();

		Vector3f r = up.normalized();
		r = r.cross(f);

		Vector3f u = f.cross(r);

		return initRotation(f, u, r);
	}

	/**
	 * sets up a rotation matrix based on three input vectors: `forward`, `up`, and
	 * `right`. It returns the updated rotation matrix.
	 * 
	 * @param forward 3D direction in which the rotation will occur.
	 * 
	 * 	- `forward` is a `Vector3f` instance representing the direction of forward motion.
	 * It has three components: `x`, `y`, and `z`, which represent the real-valued
	 * coordinates of the vector in 3D space.
	 * 	- The `Vector3f` class represents a 3D vector in homogeneous coordinates, where
	 * each component (x, y, z, and w) can take on any real value within its respective
	 * range.
	 * 
	 * @param up 3D direction perpendicular to the rotation axis, which is used to generate
	 * the upper triangular part of the rotation matrix.
	 * 
	 * 	- `up` is a Vector3f object representing the upward direction in 3D space. It has
	 * three components: `x`, `y`, and `z`, which correspond to the x, y, and z coordinates
	 * of the upward vector in homogeneous coordinates.
	 * 	- The `up` vector is typically used as a reference for rotations, as it points
	 * towards the "up" direction in the context of the current coordinate system.
	 * 
	 * @param right 3D right vector of the rotation, which is used to initialize the
	 * elements of the matrix.
	 * 
	 * 	- `right`: A vector representing the rightward direction in 3D space. It has three
	 * components: `x`, `y`, and `z`.
	 * 	- `u`: A vector representing the upward direction in 3D space. It has three
	 * components: `x`, `y`, and `z`.
	 * 	- `forward`: A vector representing the forward direction in 3D space. It has three
	 * components: `x`, `y`, and `z`.
	 * 
	 * @returns a `Matrix4f` object representing a rotation matrix based on the provided
	 * vectors.
	 * 
	 * 	- The `data` array is a 4x3 matrix, where each element represents a component of
	 * the rotation transformation.
	 * 	- The elements of `data` are initialized to values representing the rotation
	 * around the `forward`, `right`, and `up` axes. Specifically, the `r` axis is
	 * represented by the vector `(r.getX(), r.getY(), r.getZ())`, the `u` axis is
	 * represented by the vector `(u.getX(), u.getY(), u.getZ())`, and the `f` axis is
	 * represented by the vector `(f.getX(), f.getY(), f.getZ())`.
	 * 	- The elements of `data` are initialized to zeroes outside of the rotation
	 * transformation, indicating that the matrix is identity.
	 * 	- The `initRotation` function returns a reference to itself, allowing for chaining
	 * of method calls and modifying the same instance of the matrix.
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
	 * of multiplying each component of the input vector by a set of coefficients stored
	 * in an array, followed by addition of a constant value for each coordinate.
	 * 
	 * @param r 4D vector to be transformed, which is multiplied element-wise with the
	 * 3D vector returned by the function.
	 * 
	 * 	- `r.getX()` and `r.getY()` represent the x- and y-coordinates of the input vector,
	 * respectively.
	 * 	- `r.getZ()` represents the z-coordinate of the input vector.
	 * 	- `data[0][0]`, `data[0][1]`, `data[0][2]`, and `data[0][3]` are the elements of
	 * a 4D array that is being transformed by the function.
	 * 	- Similarly, `data[1][0]`, `data[1][1]`, `data[1][2]`, and `data[1][3]` represent
	 * the elements of another 4D array that is also being transformed.
	 * 	- `data[2][0]`, `data[2][1]`, `data[2][2]`, and `data[2][3]` represent the elements
	 * of yet another 4D array that is being transformed by the function.
	 * 
	 * @returns a new Vector3f object containing the result of multiplying each component
	 * of the input vector `r` by the corresponding components of a matrix stored in an
	 * array.
	 * 
	 * 	- The output is a new `Vector3f` object that represents the result of multiplying
	 * each component of the input vector `r` by the corresponding components of the array
	 * `data`.
	 * 	- The resulting vector has the same components as the input vector, but with the
	 * values scaled by the corresponding elements of the array `data`.
	 * 	- The order of the components in the output vector is the same as that of the
	 * input vector.
	 */
	public Vector3f transform(Vector3f r) {
		return new Vector3f(data[0][0] * r.getX() + data[0][1] * r.getY() + data[0][2] * r.getZ() + data[0][3], data[1][0] * r.getX() + data[1][1] * r.getY() + data[1][2]
				* r.getZ() + data[1][3], data[2][0] * r.getX() + data[2][1] * r.getY() + data[2][2] * r.getZ() + data[2][3]);
	}

	/**
	 * multiplies two `Matrix4f` objects and returns the result as a new `Matrix4f` object.
	 * It performs element-wise multiplication between the elements of the two matrices.
	 * 
	 * @param r 4x4 matrix that is multiplied with the current matrix, resulting in the
	 * updated matrix.
	 * 
	 * 	- `r` is an instance of `Matrix4f`, which represents a 4x4 floating-point matrix.
	 * 	- `r.get(i, j)` returns the element at position `(i, j)` of the matrix.
	 * 
	 * @returns a matrix with the product of the input matrices multiplied element-wise.
	 * 
	 * 	- The `res` variable is declared as a `Matrix4f`, indicating that it is a 4x4
	 * matrix with floating-point elements.
	 * 	- The function takes two arguments: `r`, which is also a `Matrix4f`, and `data`,
	 * which is an array of 16 float values representing the components of the input matrix.
	 * 	- The function first iterates over the rows of both matrices, multiplying each
	 * element of the input matrix by the corresponding element of the parameter matrix
	 * `r`. The result of each multiplication is stored in the corresponding element of
	 * the output matrix `res`.
	 * 	- The function then returns the output matrix `res`.
	 * 
	 * The output of the `mul` function depends on the specific values of the input
	 * matrices, but generally represents a transformation or combination of the input
	 * matrices. The properties of the output matrix, such as its dimensions and data
	 * type, are determined by the parameters passed to the function.
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
	 * creates a new array of floats, `res`, with dimensions `4x4`. It then sets the
	 * elements of `res` to the corresponding elements of an input array `data`. The
	 * function returns `res`.
	 * 
	 * @returns an array of arrays, where each inner array has four elements representing
	 * the data at each position.
	 */
	public float[][] getData() {
		float[][] res = new float[4][4];

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				res[i][j] = data[i][j];

		return res;
	}
	
	/**
	 * returns an array of floats containing data points at intervals of 0, 1, 2, and 3.
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
	 * retrieves a value from a 2D array `data`. The value is retrieved based on the row
	 * and column indices `x` and `y`.
	 * 
	 * @param x 0-based index of a pixel location within the 2D array `data`.
	 * 
	 * @param y 2nd dimension of the data array that is being accessed and returned by
	 * the function.
	 * 
	 * @returns the value of `data[x][y]`.
	 */
	public float get(int x, int y) {
		return data[x][y];
	}

	/**
	 * sets the value of a member field `data` to an array of arrays of floats.
	 * 
	 * @param data 2D array of float values that are to be stored in the `SetM()` method's
	 * instance field, `data`.
	 * 
	 * 	- Data is a float array of dimensions (length x width).
	 * 	- Each element in the array represents a value within the range of -1 to 1.
	 * 	- The size and shape of the data array determine the resolution of the 2D grid
	 * it represents.
	 */
	public void SetM(float[][] data) {
		this.data = data;
	}

	/**
	 * sets a value at a specific position in a two-dimensional array of integers and floats.
	 * 
	 * @param x 0-based index of the cell in the 2D array where the value should be stored.
	 * 
	 * @param y 2nd dimension of the data array being modified by the function.
	 * 
	 * @param value 4-dimensional vector value that is to be stored at the specified
	 * position in the `data` array.
	 */
	public void set(int x, int y, float value) {
		data[x][y] = value;
	}

	/**
	 * transforms an array of floats into a new array with the same elements, arranged
	 * in a different order.
	 */
	public void transposeSelf() {
		float[][] tr = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				tr[i][j] = data[j][i];
		this.data = tr;
	}
	
}
