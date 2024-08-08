package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that represents a camera object in a 3D graphics system. It
 * manages the projection and view matrices for rendering scenes. The class provides
 * methods for calculating these matrices, adjusting to different viewport sizes, and
 * accessing the transform of the camera.
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
	 * Returns a matrix representing the combined view and projection transformations.
	 * If the transformation has changed or no such matrix exists, it calculates the view
	 * matrix using the `calculateViewMatrix` method before returning the result.
	 *
	 * @returns a Matrix4f object, possibly calculated or retrieved from cache.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Computes a view matrix by combining camera rotation and translation. It first
	 * obtains the conjugate rotation and translation matrices from the input data, then
	 * multiplies them to produce the final view matrix. The result is returned as a
	 * product of the projection matrix and the combined rotation-translation matrix.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Multiplies the current position by -1 to obtain a vector representing the translation
	 * from the origin to the camera's position, then initializes a Matrix4f with this
	 * translation. The resulting matrix is used for transformation purposes.
	 *
	 * @returns a 4x4 translation matrix.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an object reference named `transform`. This method retrieves and provides
	 * access to the value of the `transform` variable, allowing external code to use or
	 * modify it as needed. The returned object is read-only, indicating that its value
	 * cannot be changed by the caller.
	 *
	 * @returns a reference to the `transform` variable.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base type for camera-related data structures.
	 * It defines a contract with one abstract method, getAsMatrix4(), which returns a
	 * Matrix4f object representing the camera's structural properties.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
