package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract base class that encapsulates camera settings and transformations.
 * It calculates view and projection matrices based on its internal state and provides
 * functionality for adjusting to viewport dimensions. The class is designed to be
 * extended by concrete camera implementations.
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
	 * Returns a matrix representing the combined view and projection transformations for
	 * rendering. It checks if the transformation has changed, recalculating the view
	 * matrix as needed before returning the resulting view-projection matrix.
	 *
	 * @returns a Matrix4f, representing combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {
		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Generates a view matrix for a scene, combining camera rotation and translation
	 * into a single transformation. It multiplies the inverse of the camera's rotation
	 * by its translation to obtain the view-projection matrix. The result is assigned
	 * to the `viewProjectionMat4` variable.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {
		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix based on a given position. It transforms an initial
	 * position, multiplies it by -1 to reverse direction, and uses the resulting vector
	 * as input for initializing a new translation matrix. The function returns this new
	 * translation matrix.
	 *
	 * @returns a translation matrix with a specified camera position.
	 * It represents a transformation that moves an object to the camera's location,
	 * opposite from its current position.
	 * The result is a 4x4 matrix.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);

		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an instance of a `Transform` object, identified as `transform`. The returned
	 * value is presumably stored elsewhere and can be accessed through this getter method.
	 * The state of the internal object remains unchanged.
	 *
	 * @returns an instance of a `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Defines an interface for a camera's structural data, containing abstract method
	 * to retrieve this data as a Matrix4f object. The purpose of this class is to
	 * standardize the camera's internal representation across different implementations.
	 * It provides a common format for other classes to interact with the camera's structure.
	 */
	protected abstract class CameraStruct {
		protected abstract Matrix4f getAsMatrix4();
	}
}
