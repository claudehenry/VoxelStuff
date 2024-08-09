package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class responsible for handling camera-related operations. It provides
 * a way to calculate and manage various matrices related to camera transformations,
 * including view projection and translation matrices. The class also allows for
 * customization through abstract methods that must be implemented by its subclasses.
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
	 * Returns a matrix representing the combined view and projection transformations.
	 * It checks if the internal state has changed, recalculates the view matrix if
	 * necessary, and caches the result to optimize performance.
	 *
	 * @returns a Matrix4f instance, either cached or newly calculated.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Conjugates a transformed rotation matrix, multiplies it with a translation matrix,
	 * and then multiplies the result with a projection matrix to produce a view-projection
	 * transformation matrix.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on the camera position. It multiplies the
	 * original position by -1 to invert it, then initializes a new Matrix4f object with
	 * the inverted position coordinates. The resulting matrix is used for translations.
	 *
	 * @returns a 4x4 matrix representing translation based on camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an instance of the `Transform` class, which is stored in the `transform`
	 * variable. The returned object provides information about the transformation to be
	 * applied to a geometric object. This function simply retrieves and exposes the
	 * internal state of the transformation.
	 *
	 * @returns an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base for other camera-related structs. It
	 * provides a means to convert its internal state into a Matrix4f object. This
	 * abstraction enables polymorphic behavior and allows concrete subclasses to customize
	 * their representation.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
