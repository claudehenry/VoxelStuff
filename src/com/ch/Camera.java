package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices, as well as handling transformations and adjusting to the viewport. It
 * also defines an abstract class called CameraStruct, which can be used to store
 * camera-related data.
 */
public abstract class Camera {

	protected Matrix4f projection;
	protected Matrix4f viewProjectionMat4;
	protected CameraStruct values;
	protected Transform transform;

	protected Camera(Matrix4f projection) {
		this.projection = projection;
		transform = new Transform();
	}

	/**
	 * calculates and returns a `Matrix4f` object representing the view projection
	 * transformation, which combines the camera's view matrix with the projection matrix
	 * to produce the final 3D representation.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * 	- The `viewProjectionMat4` variable is a matrix that represents the view projection
	 * transformation.
	 * 	- It is created by multiplying the view matrix and the projection matrix together.
	 * 	- If the `viewProjectionMat4` is null or has changed, it will be recalculated
	 * using the `calculateViewMatrix()` function.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix used in computer graphics to transform 3D points from
	 * world space to screen space, based on camera rotation and translation.
	 * 
	 * @returns a Matrix4f object representing the view matrix, which combines the camera
	 * rotation and translation matrices.
	 * 
	 * 1/ `viewProjectionMat4`: This is a 4x4 matrix representing the view and projection
	 * transforms combined. It contains the transformation from camera coordinates to
	 * world coordinates, followed by the perspective projection.
	 * 2/ `cameraRotation`: A 3x3 rotation matrix representing the rotation of the camera.
	 * 3/ `cameraTranslation`: A 3x1 vector representing the translation of the camera.
	 * 4/ The multiplication of `cameraRotation` and `cameraTranslation` in the function
	 * produces a 4x4 matrix that represents the full view transformation, including both
	 * rotation and translation.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * computes a translation matrix based on the negative of the transformed position
	 * of the camera, represented by `Vector3f` object `cameraPos`. The resulting matrix
	 * is returned as an instance of `Matrix4f`.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position from the
	 * world coordinate system to the viewport coordinate system.
	 * 
	 * The function returns a 4x4 Matrix object representing a translation transformation
	 * matrix. The elements of this matrix indicate the displacement of the camera
	 * coordinate system in world space. Specifically, the first three elements (row 0)
	 * represent the x-displacement, the next three elements (row 1) represent the
	 * y-displacement, and the last three elements (row 2) represent the z-displacement.
	 * 
	 * The returned matrix is created by multiplying the transform position vector (-1
	 * times the transform position vector to get the negative translation) with the
	 * identity matrix, which sets all other elements to zero. This ensures that the
	 * resulting matrix represents a pure translation transformation without any scaling
	 * or rotation.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * retrieves a `Transform` object, which is stored in a class variable `transform`.
	 * The returned object represents the transformation of an input value.
	 * 
	 * @returns a `Transform` object containing the transformation matrix.
	 * 
	 * 	- `transform`: The transformed data is stored in this variable.
	 * 	- Type: This variable returns an instance of the `Transform` class.
	 * 	- Description: This method retrieves the transform data and stores it in a variable
	 * for further use.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base class for other classes in the Camera
	 * package. It has an abstract method called `getAsMatrix4()` which returns a Matrix4f
	 * object, but does not provide any implementation details. The class provides a way
	 * to define a struct that can be used to store and manipulate matrix data in a
	 * standardized manner across different classes in the package.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
