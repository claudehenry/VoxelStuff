package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Encapsulates camera functionality with matrix operations for projection and view
 * transformation. It has abstract methods to calculate projection matrices and adjust
 * to viewport dimensions. The class is designed to support various camera configurations
 * through inheritance.
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
	 * Checks if a specific transformation matrix is valid or has changed, and recalculates
	 * it as necessary. It appears to be part of an object-oriented system managing
	 * transformations. The calculated matrix is then returned for use in view projection
	 * calculations.
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
	 * Computes a view-projection matrix by multiplying the projection matrix, the inverse
	 * rotation matrix of the camera's transformed rotation, and the translation matrix
	 * of the camera's position. The result is returned as a Matrix4f object. The resulting
	 * view-projection matrix is stored in the viewProjectionMat4 variable.
	 *
	 * @returns a `Matrix4f` representing the view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on a specified position, and returns it as a
	 * Matrix4f object. The position is calculated by transforming the current position
	 * and negating the result.
	 *
	 * @returns a matrix representing translation to the negative of the transformed
	 * camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns a reference to an existing transformation object named `transform`. It is
	 * likely intended for access, not modification, as it does not allow for changing
	 * the state of the object. The returned object can be manipulated independently
	 * elsewhere in the program.
	 *
	 * @returns an instance of a class implementing the `Transform` interface.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class representing a camera-related structure, providing a way to
	 * convert its internal state into a Matrix4f representation. It has a single abstract
	 * method getAsMatrix4(). The class serves as an interface for camera-specific data
	 * structures.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
