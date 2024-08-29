package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that represents a camera in a 3D scene. It manages the view
 * and projection matrices based on the transformation of the camera's position and
 * rotation.
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
	 * It checks if a transformation has occurred or if the cached view-projection matrix
	 * is null, and recalculates it if necessary before returning.
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
	 * Generates a view matrix for a 3D scene. It combines camera rotation, obtained from
	 * a transformed rotation and conjugated to ensure correctness, with camera translation
	 * into a single matrix through matrix multiplication. The resulting matrix is then
	 * multiplied with the projection matrix to produce the final view-projection matrix.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix based on a given camera position, which is obtained
	 * by multiplying the original position with -1. The resulting matrix represents a
	 * transformation that translates the origin to the camera's position. It returns the
	 * calculated translation matrix as an object of type Matrix4f.
	 *
	 * @returns a 4x4 matrix representing a translation from origin to a specified position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves and returns a reference to an instance variable named `transform`, which
	 * is likely of type `Transform`. This method does not modify the state of the object
	 * it belongs to, but rather provides read-only access to its internal transform property.
	 *
	 * @returns a reference to an object of type `Transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that provides a base structure for camera-related data. It
	 * defines a single abstract method getAsMatrix4, which returns a Matrix4f object.
	 * This class serves as a blueprint for specific camera configurations or settings.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
