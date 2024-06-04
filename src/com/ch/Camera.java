package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that defines various methods and fields for manipulating
 * camera perspectives in a 3D environment. It has an associated projection matrix
 * and view projection matrix, as well as a transformation matrix for adjusting the
 * position and rotation of the camera. Additionally, it has a getTranslationMatrix
 * method for calculating the translation matrix of the camera, and an adjustToViewport
 * method for adjusting the camera to the specified viewport size. The class also
 * includes an abstract calculateProjectionMatrix method and an abstract class
 * CameraStruct for storing camera-related data.
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
	 * Calculates and returns a matrix representation of the view-projection transformation,
	 * based on the current view and projection matrices.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix by multiplying the camera's rotation and translation
	 * matrices with the projection matrix.
	 * 
	 * @returns a matrix representation of the view transformation, which combines the
	 * rotation and translation of the camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a matrix that represents the translation of a 3D camera's position from
	 * its original location to a new location. The matrix is initialized with the camera's
	 * current position and then translated by a negative value to represent the new position.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position from its
	 * original location.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns the `transform` object, which is an instance of the `Transform` class.
	 * This object contains information about the position and orientation of an object
	 * in a 3D space.
	 * 
	 * @returns a reference to an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base for other classes in the Camera package.
	 * It has an abstract method called getAsMatrix4() that returns a Matrix4f object,
	 * but it does not provide any implementation details. The class provides a way for
	 * subclasses to inherit and implement their own matrix manipulation methods without
	 * having to write their own abstract methods.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
