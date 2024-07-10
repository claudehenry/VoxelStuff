package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that provides methods for calculating view and projection
 * matrices, as well as adjusting to a specific viewport size. It also contains a
 * transform structure for storing camera position and rotation information.
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
	 * Computes and returns a `Matrix4f` object representing the view projection
	 * transformation. If the `viewProjectionMat4` variable is null or the transform has
	 * changed, it recomputes the view matrix using the `calculateViewMatrix()` method.
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
	 * Calculates the view matrix, which is used to transform 3D points from world
	 * coordinates to screen coordinates, based on the camera's rotation and translation.
	 * 
	 * @returns a 4x4 transformation matrix representing the view projection transformation.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a transformation matrix that translates the camera position by its
	 * negative vector, represented as a Vector3f object.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position in 3D space.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves a `Transform` object, which is an instance of a class representing a
	 * transformation in a geometric space.
	 * 
	 * @returns the `Transform` object `transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that contains methods and fields related to camera data. It
	 * has a single abstract method `getAsMatrix4()` that returns a Matrix4f object, which
	 * is used for calculating projection matrices. The class does not provide any specific
	 * information on its fields or methods, but it serves as a base class for other
	 * classes in the Camera package.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
