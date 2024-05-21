package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices. It also has a transform property and an abstract calculateProjectionMatrix
 * method. Additionally, it has subclasses that inherit from the class and provide
 * their own implementation of the calculateProjectionMatrix method.
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
	 * computes and returns a matrix that represents the view projection transformation,
	 * taking into account changes to the transform property.
	 * 
	 * @returns a Matrix4f object representing the view projection matrix.
	 * 
	 * 	- `viewProjectionMat4`: A matrix object representing the view projection transformation.
	 * 	- `transform`: The transformation state of the camera, indicating whether the
	 * view matrix has changed.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * computes a view matrix based on the camera's rotation and translation, using the
	 * `projection` matrix to transform the rotation and translation matrices. The resulting
	 * view matrix is returned.
	 * 
	 * @returns a 4x4 matrix representing the view transformation of a camera.
	 * 
	 * 	- `viewProjectionMat4`: This is a 4x4 matrix representing the view and projection
	 * transforms combined. The elements of this matrix represent the transformation from
	 * the world coordinate system to the camera's coordinate system, taking into account
	 * both the rotation and translation of the camera.
	 * 	- `cameraRotation`: This is a 3x3 rotation matrix representing the rotation of
	 * the camera relative to the world coordinate system.
	 * 	- `cameraTranslation`: This is a 3x1 vector representing the translation of the
	 * camera relative to the world origin.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a translation matrix based on the position of the camera, represented
	 * by the `Vector3f` object `cameraPos`. The resulting matrix translates the origin
	 * to the negative position of the camera.
	 * 
	 * @returns a matrix representation of a translation, where the translation vector
	 * is (0, 0, -1).
	 * 
	 * 	- The Matrix4f object represents a 4x4 transformation matrix that translates the
	 * origin of the coordinate system by the specified amount in the x, y, and z directions.
	 * 	- The initTranslation method takes three floating-point values representing the
	 * translation vector as inputs.
	 * 	- The resulting matrix has the form [a b c d; e f g h], where a, b, c, d, e, f,
	 * g, and h are constants that determine the position of the translation vector in
	 * the coordinate system.
	 * 	- Specifically, the elements of the matrix represent the amount of shift or
	 * rotation in each direction relative to the original position of the origin.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * returns the `transform` object, which contains the transformation logic for the application.
	 * 
	 * @returns a reference to an object of type `Transform`.
	 * 
	 * 	- The return type is `Transform`, indicating that it is an object of the `Transform`
	 * class.
	 * 	- The name of the returned variable is `transform`, which suggests that it may
	 * contain information related to the transforming process.
	 * 	- The value of the returned variable is the actual transform object, which can
	 * be used for further processing or manipulation.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base class for other classes that represent
	 * camera-related data. It contains an abstract method `getAsMatrix4()` that allows
	 * subclasses to provide their own implementation of a matrix representation of the
	 * camera data.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
