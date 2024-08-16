package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that represents a camera in a 3D graphics system. It maintains
 * internal state such as a projection matrix and a transformation matrix, and provides
 * methods to calculate the view-projection matrix based on this state. The class
 * also defines an abstract method for calculating the projection matrix and adjusting
 * the camera to fit within a viewport.
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
	 * Calculates a matrix representing the combined view and projection transformations
	 * if they have changed or the previous result is null. It then returns this calculated
	 * matrix, allowing for efficient reuse when no changes occur.
	 *
	 * @returns a Matrix4f object.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix for rendering purposes. It multiplies the camera's rotation,
	 * represented as a conjugated rotation matrix, with its translation, and then combines
	 * the result with a projection matrix to produce the final view-projection matrix.
	 *
	 * @returns a view-projection matrix, which combines rotation and translation.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Multiplies a given camera position by -1 and then initializes a translation matrix
	 * with the resulting x, y, z coordinates to create a transformation for object rendering.
	 *
	 * @returns a matrix representing translation to the camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns a reference to an object of type `Transform`. This suggests that it provides
	 * access to the current state or configuration of some transformation, allowing
	 * external code to read and potentially use its properties.
	 *
	 * @returns an instance of class `Transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that provides a common interface for camera-related data
	 * structures to implement. It defines a single abstract method getAsMatrix4() which
	 * returns a Matrix4f object. This allows for polymorphic usage of different camera
	 * data types.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
