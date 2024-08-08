package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Provides an abstract representation of a camera object that can perform transformations
 * and calculations for rendering. It maintains internal state such as projection and
 * view matrices, as well as a transform object. The class also defines several
 * abstract methods for calculating the view matrix, projection matrix, and adjusting
 * to a viewport.
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
	 * Returns a Matrix4f instance representing the product of the current view and
	 * projection matrices. It recalculates the view matrix if necessary, based on whether
	 * it is null or the transformation has changed.
	 *
	 * @returns a Matrix4f object, which is either cached or recalculated based on input
	 * conditions.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Generates a view matrix for rendering by combining camera rotation and translation
	 * into a single transformation. It multiplies the camera's rotation, represented as
	 * a quaternion, with its translation to produce the view matrix. The result is then
	 * multiplied with the projection matrix.
	 *
	 * @returns a product of three matrices: rotation, translation, and projection.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix based on a camera position, which is obtained by
	 * multiplying the original position with -1. The resulting matrix represents a
	 * translation from the origin to the camera's position.
	 *
	 * @returns a 4x4 transformation matrix for translation.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns a reference to an object of type `Transform`. This allows access to the
	 * internal state of the object without modifying it. The returned value is a read-only
	 * view of the transform data.
	 *
	 * @returns an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that defines a base structure for camera-related data. It
	 * provides a way to represent camera settings and configurations in a standardized
	 * manner. The class contains an abstract method getAsMatrix4(), which returns the
	 * camera data as a Matrix4f object.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
