package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that represents a camera in a 3D environment. It has various
 * methods and fields related to camera calculations, transformations, and viewport
 * adjustments. The class also includes an abstract method for calculating the
 * projection matrix, which can be used to transform 3D objects into 2D space.
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
	 * Retrieves the view projection matrix, which is used to transform 3D points from
	 * world space to screen space. If the matrix has changed or is null, it calculates
	 * and stores the view matrix locally.
	 * 
	 * @returns a Matrix4f object containing the view projection matrix.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix for a camera, combining rotation and translation of the
	 * camera with the projection matrix.
	 * 
	 * @returns a matrix representing the combined effects of camera rotation and translation.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a matrix representing the translation of a camera positioned in 3D space.
	 * 
	 * @returns a 4x4 transformation matrix representing the camera's translation vector
	 * in the world space.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves a reference to the `transform` object, which is used for geometric transformations.
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
	 * It has a method called getAsMatrix4() which returns a Matrix4f object, but no
	 * implementation details are provided. The purpose of this class seems to be to
	 * provide a common interface for various camera-related classes in the package,
	 * allowing them to share a common structure and methods.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
