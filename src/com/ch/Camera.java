package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Defines an abstract camera class with methods for calculating view and projection
 * matrices, handling transformations, and adjusting to viewport dimensions.
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
	 * Returns a 4x4 matrix representing the combined view and projection transformations.
	 * It recalculates the view matrix if the transformation has changed.
	 *
	 * @returns a 4x4 matrix representing the combined view and projection transformations.
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
	 * @returns a combined view-projection matrix resulting from the multiplication of
	 * camera rotation, translation, and projection matrices.
	 */
	public Matrix4f calculateViewMatrix() {
		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix based on the negative of the camera position. It
	 * transforms the position using a transformation object and then multiplies the
	 * result by -1. The resulting translation matrix is then returned.
	 *
	 * @returns a 4x4 translation matrix representing the camera's position in 3D space.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);

		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns the `transform` object, providing access to its properties and methods.
	 * This allows other parts of the code to use the transformation data. The function
	 * does not modify the state of the object.
	 *
	 * @returns an object reference to the `transform` variable.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is defined as an abstract class that represents a camera's structural data, providing
	 * a way to convert it into a Matrix4f.
	 */
	protected abstract class CameraStruct {
		protected abstract Matrix4f getAsMatrix4();
	}
}
