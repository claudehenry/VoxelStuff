package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Serves as an abstraction for various types of cameras in a rendering system,
 * providing functionality such as matrix transformations and projection calculations
 * based on its internal state and external data.
 * It has the ability to calculate matrices representing camera views and projections,
 * as well as adjust its settings to match different viewport sizes.
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
	 * Checks if a cached projection matrix exists or if an object has been transformed,
	 * then recalculates the view projection matrix if necessary before returning it. It
	 * provides direct access to the view projection matrix when needed. The returned
	 * value is a pre-calculated Matrix4f instance.
	 *
	 * @returns a Matrix4f representing the combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Computes a combined view-projection matrix by multiplying a rotation matrix, a
	 * translation matrix, and a pre-existing projection matrix. It conjugates the camera's
	 * rotation to obtain a view transformation, combines it with its translation, and
	 * multiplies the result with the projection matrix.
	 *
	 * @returns a combined view and projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on the negative of the transformed position of
	 * an object's transform. The resulting matrix is then returned, allowing for the
	 * object's movement along the x, y, and z axes. A new Matrix4f instance is initialized
	 * with translation values.
	 *
	 * @returns a 4x4 translation matrix based on camera position.
	 * It transforms objects relative to the camera's negative position.
	 * A new 4x4 matrix with specified translation values is created.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns the current transformation state represented by the `transform` variable,
	 * allowing access to its properties or values as needed. The return type is likely
	 * a Transform object. This function provides read-only access to the transform data.
	 *
	 * @returns an instance of the `Transform` class, currently stored in the `transform`
	 * field.
	 * The field contains an object reference to a Transform instance.
	 * Its type and state remain unchanged.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is defined as an abstract class with one protected abstract method, getAsMatrix4,
	 * which returns a Matrix4f object. It provides a common interface for camera-related
	 * data structures to be implemented by concrete subclasses. Its purpose is to
	 * encapsulate specific camera settings and properties.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
