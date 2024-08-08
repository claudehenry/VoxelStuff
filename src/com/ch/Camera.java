package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that manages camera-related transformations and projections.
 * It provides functionality for calculating view and projection matrices based on
 * the camera's rotation, translation, and position. The class also includes methods
 * to adjust the camera to a specific viewport and retrieve its transformation data.
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
	 * Returns a matrix combining the view and projection transformations if it is available
	 * or calculates it if necessary, considering any changes to the transformation since
	 * its last calculation. It ensures consistency by recalculating if the underlying
	 * data has changed.
	 *
	 * @returns a Matrix4f representing the combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Generates a view matrix by multiplying the camera's rotation and translation
	 * matrices with the projection matrix. The result is stored in the `viewProjectionMat4`.
	 * This matrix represents the combined transformation from the world space to the
	 * camera's coordinate system.
	 *
	 * @returns a composite view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on a camera position transformed by a given
	 * transform and multiplies it by -1 to invert the direction. The result is then
	 * returned as a Matrix4f object initialized with the translated coordinates.
	 *
	 * @returns a translation matrix initialized with the negated camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an instance of type `Transform`. It retrieves and provides access to the
	 * `transform` object, allowing its value to be accessed or used elsewhere in the
	 * program. The returned value is a reference to an existing object rather than
	 * creating a new one.
	 *
	 * @returns an object of type `Transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base type for camera-related data structures.
	 * It provides a way to encapsulate and manipulate camera-specific values. The class
	 * includes an abstract method getAsMatrix4, which allows for conversion of the camera
	 * data into a matrix format.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
