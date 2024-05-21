package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices, as well as adjusting to the size of the viewport. It also has a transform
 * field and a calculateProjectionMatrix method that returns a Matrix4f object.
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
	 * computes and returns a matrix representing the view-projection transformation,
	 * based on the current view matrix and any changes to the transform.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * 	- The `Matrix4f` object represents a 4x4 matrix that encodes the view-projection
	 * transformation.
	 * 	- The matrix is created by multiplying the camera's view matrix and projection matrix.
	 * 	- The view matrix transforms the 3D world coordinates into screen coordinates,
	 * while the projection matrix scales the screen coordinates to fit the image size
	 * and aspect ratio.
	 * 	- The `viewProjectionMat4` variable stores the resulting transformation matrix
	 * in a 4x4 format.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix that represents the combination of a camera rotation and
	 * translation. The rotation is obtained from the `transform` object, while the
	 * translation is obtained from the `getTranslationMatrix()` method. The resulting
	 * view matrix is then returned in the `viewProjectionMat4` variable.
	 * 
	 * @returns a 4x4 homogeneous transformation matrix that represents the view matrix.
	 * 
	 * 	- `viewProjectionMat4`: This is a 4x4 matrix representing the view and projection
	 * transforms combined. It contains the rotation and translation components of the
	 * camera's view, as well as the perspective and scale factors of the projection.
	 * 	- `cameraRotation`: This is a 3x3 rotation matrix representing the rotation of
	 * the camera relative to its original position. It is obtained by multiplying the
	 * transform object's rot property with its conjugate.
	 * 	- `cameraTranslation`: This is a 3x1 vector representing the translation of the
	 * camera relative to its original position. It is obtained by calling the
	 * getTranslationMatrix method on the transform object.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a transformation matrix that translates a 3D vector by a specified amount
	 * in the x, y, and z directions.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position by a
	 * vector in the X, Y, and Z directions.
	 * 
	 * 	- The Matrix4f object represents a 4x4 homogeneous transformation matrix that
	 * contains the translation component in its lower-right 3x3 submatrix.
	 * 	- The upper-left 1x1 submatrix contains the identity matrix, which means that the
	 * translation component does not affect the position of the camera relative to the
	 * world.
	 * 	- The translation vector is represented as (x, y, z), where x, y, and z are the
	 * components of the Vector3f object passed as an argument to the function.
	 * 
	 * Overall, this function returns a matrix that represents a translation from the
	 * current position of the camera in the world to a new position based on the given
	 * vector.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * retrieves a reference to the `transform` object, which is used for some purpose
	 * within the Java code.
	 * 
	 * @returns a reference to an object of type `Transform`.
	 * 
	 * 	- The `transform` variable is of type `Transform`.
	 * 	- It represents the result of a transformation operation on some input data.
	 * 	- The exact nature of the transformation operation and the input data are not
	 * specified in this function.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base class for other classes in the Camera
	 * package. It provides an abstract method called `getAsMatrix4()` which returns a
	 * Matrix4f object, but does not provide any implementation details or fields.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
