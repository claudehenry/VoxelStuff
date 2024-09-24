package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract representation of a camera in a 3D graphics context. It handles
 * transformations and projections for rendering scenes. It provides a way to calculate
 * view matrices and adjust the projection based on the viewport dimensions.
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
	 * Returns a matrix representing combined view and projection transformations. If
	 * either the cached transformation has changed or no cache exists, it  recalculates
	 * the view transformation before returning the updated or newly created view-projection
	 * matrix. The function ensures data consistency through caching.
	 *
	 * @returns a precomputed Matrix4f representing the combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Calculates a view-projection matrix by multiplying a camera's rotation and translation
	 * matrices with a pre-defined projection matrix. It returns the resulting combined
	 * matrix, storing it in the `viewProjectionMat4` variable. The transformation is
	 * done through left multiplication of the matrices.
	 *
	 * @returns a precomputed view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on the negative position of the camera. It
	 * multiplies the transformed camera position by -1 to reverse its direction and then
	 * initializes a new 4x4 matrix with the translated values as its elements. A translation
	 * matrix is returned.
	 *
	 * @returns a transformation matrix with the negative of the camera's position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an instance variable named `transform` which appears to be a reference to
	 * an object of type `Transform`. The returned value allows access to its properties
	 * and methods. It does not create or modify the `transform` object.
	 *
	 * @returns an object of type `Transform`, which contains transformation data.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract data container for camera-related settings and parameters.
	 * It provides a way to represent and store camera attributes in a structured manner.
	 * Its primary purpose is to encapsulate the underlying data used by various camera
	 * calculations.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
