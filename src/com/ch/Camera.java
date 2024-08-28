package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that manages camera transformations and projections in a
 * virtual environment. It provides methods for calculating view and projection
 * matrices based on camera position, rotation, and viewport dimensions. The class
 * also handles transformation data storage and retrieval.
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
	 * Calculates a combined view and projection matrix if necessary, based on changes
	 * to the transformation or null state of the previous result. It then returns the
	 * calculated matrix.
	 *
	 * @returns a matrix representing the combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Generates a view matrix for a camera by multiplying its rotation and translation
	 * matrices. It uses the conjugate of the camera's rotated transform, converts it to
	 * a rotation matrix, multiplies it with a translation matrix, and then combines the
	 * result with the projection matrix.
	 *
	 * @returns a Matrix4f representing the combined view and projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix from a given camera position, which is obtained by
	 * multiplying the transformed position by -1. The resulting matrix represents a
	 * translation based on the camera's position.
	 *
	 * @returns a 4x4 translation matrix based on the given camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves a reference to an existing `transform` object and returns it. It does
	 * not perform any computations or modifications, but rather provides access to the
	 * stored `transform` instance. The returned value can be used elsewhere for further
	 * processing or manipulation.
	 *
	 * @returns a reference to a `transform` object.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base structure for camera-specific data. It
	 * contains a single abstract method getAsMatrix4(), which returns a Matrix4f object
	 * representing the camera's properties in a matrix format. This class provides a
	 * framework for subclasses to implement custom camera data and matrices.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
