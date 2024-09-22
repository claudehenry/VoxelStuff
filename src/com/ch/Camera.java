package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract base class that provides functionality for camera operations in a
 * 3D rendering context. It encapsulates projection and view transformation matrices,
 * as well as a Transform object to manage the camera's position and orientation. The
 * class requires subclassing to implement specific camera behaviors.
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
	 * Checks if a view projection matrix is available and up-to-date. If not, it calculates
	 * the view matrix by calling another method. The most recent view projection matrix
	 * is then returned.
	 *
	 * @returns a Matrix4f representing combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {
		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Transforms a scene into a view based on the camera's rotation and translation. It
	 * multiplies the camera's rotation, translation, and projection matrices to produce
	 * a view-projection matrix. The result is stored in the `viewProjectionMat4` variable.
	 *
	 * @returns a concatenated view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {
		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on the negative transformed position of the
	 * camera. The matrix is initialized with the x, y, and z coordinates of the camera's
	 * negated position. A new Matrix4f object is returned.
	 *
	 * @returns a translation matrix initialized with the negative of the camera's
	 * transformed position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);

		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves a pre-existing `transform` object and returns it as is without any
	 * modification or creation. This operation does not alter the state of the external
	 * environment. The function serves as a getter, providing access to an existing object.
	 *
	 * @returns an instance of a class implementing the `Transform` interface.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that defines a data structure containing camera settings. It
	 * has no implementation details and requires subclasses to implement its methods.
	 * Its primary purpose is to provide a standardized interface for storing and retrieving
	 * camera-specific data.
	 */
	protected abstract class CameraStruct {
		protected abstract Matrix4f getAsMatrix4();
	}
}
