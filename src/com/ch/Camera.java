package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents an abstract camera with properties for projection, view, and transformation,
 * allowing for customizable projection matrices and viewport adjustments.
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
	 * It recalculates the view matrix if the object's transformation has changed or if
	 * the view projection matrix is null.
	 *
	 * @returns a Matrix4f object representing the combined view and projection transformation.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix by combining camera rotation and translation, then
	 * multiplies it with a projection matrix to produce a view-projection matrix.
	 *
	 * @returns a 4x4 matrix representing the combined view and projection transformations.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix based on the camera's position, then returns it.
	 * The camera position is negated before being used to initialize the translation matrix.
	 *
	 * @returns a 4x4 translation matrix representing the camera's position in 3D space.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns the current transformation value stored in the `transform` variable.
	 *
	 * @returns an instance of the Transform class, which is stored in the transform variable.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Defines an abstract class that represents a camera's internal state.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
