package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that provides methods for calculating view and projection
 * matrices, as well as adjusting to a specified viewport size. It also has a
 * `getTransform()` method for retrieving the transform of the camera. Additionally,
 * it has an abstract method `calculateProjectionMatrix(CameraStruct data)` and an
 * abstract class `CameraStruct` for storing camera-related data.
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
	 * Retrieves and returns a `Matrix4f` object representing the view projection
	 * transformation, which is calculated either from a pre-defined matrix or updated
	 * based on changes to the transform property.
	 * 
	 * @returns a Matrix4f object representing the view projection matrix.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Calculates the view matrix for a given camera transformation and translation, by
	 * multiplying the rotation matrix and translation matrix using the `projection` matrix.
	 * 
	 * @returns a 4x4 matrix representing the view transformation of a 3D camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a matrix that represents the translation of a given position from the
	 * world space to the camera space.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns the `transform` object, which contains the transformation logic for the application.
	 * 
	 * @returns the `transform` object itself.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class with an abstract method `getAsMatrix4()` and no fields. This
	 * class serves as a base for other classes that extend the `Camera` class, allowing
	 * them to define their own structure and methods specific to each class.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
