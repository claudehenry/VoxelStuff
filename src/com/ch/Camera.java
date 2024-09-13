package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents an abstract camera entity that encapsulates projection and view
 * transformation matrices, as well as a transform component for position and rotation.
 * It provides functionality for calculating the view matrix and adjusting to viewport
 * dimensions. The class is designed to be extended by concrete camera classes.
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
	 * Returns a pre-calculated matrix representing the combined view and projection
	 * transformations. It checks if the transformation has changed or if the matrix is
	 * null, then recalculates the view matrix before returning it. The function serves
	 * as a cache for efficient retrieval of the transformed matrix.
	 *
	 * @returns a Matrix4f object representing combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Computes a view matrix by combining camera rotation and translation, then multiplies
	 * it with a predefined projection matrix to produce a view-projection matrix. The
	 * resulting matrix is stored in the `viewProjectionMat4` variable and returned. The
	 * calculation involves matrix multiplication of the projection, camera rotation, and
	 * translation matrices.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on the negated position of a transformed object's
	 * position. The resulting matrix is then returned to represent a translation operation
	 * in 3D space. A new Matrix4f instance is initialized with translation components
	 * from the negated camera position.
	 *
	 * @returns a 4x4 translation matrix.
	 * The matrix represents transformation from world space to local space of the object.
	 * It positions an object at the negative location of its transformed position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns a reference to an existing Transform object named `transform`. The Transform
	 * object is likely used for graphical transformations, such as scaling or rotating
	 * objects on the screen. It does not create a new Transform object but rather provides
	 * access to one already in existence.
	 *
	 * @returns an instance of a class named `Transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Defines an abstract interface for camera-related data structures,
	 * which is meant to be implemented by subclasses.
	 * It provides a way to represent and manipulate camera settings in a generic manner.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
