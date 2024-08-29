package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that encapsulates camera functionality. It manages projection
 * and view matrices, as well as transform data. The class provides methods for
 * calculating view and projection matrices based on the camera's position and rotation.
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
	 * Returns a Matrix4f object, which is either retrieved from memory if it exists and
	 * has not changed since the last transformation, or recalculated using the
	 * `calculateViewMatrix` method if necessary. The calculated view matrix is then
	 * stored for future use.
	 *
	 * @returns a Matrix4f object.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix by multiplying the camera's rotation and translation
	 * matrices with the current projection matrix. The result is then returned as a new
	 * Matrix4f object, which represents the combined transformation from world space to
	 * camera space.
	 *
	 * @returns a matrix representing the combined view and projection transformations.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a translation matrix based on a camera position calculated from the
	 * result of a transformation. It initializes a new Matrix4f object with translation
	 * values derived from the camera's x, y, and z coordinates multiplied by -1.
	 *
	 * @returns a 4x4 matrix representing the translation of the camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an instance of a `Transform` object. This object likely represents a
	 * mathematical transformation or operation, such as rotation or scaling. The returned
	 * object is accessible for further processing and manipulation by the caller.
	 *
	 * @returns an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that defines a structure for camera data. It provides a way
	 * to get this data as a Matrix4f object. This abstract class serves as a template
	 * for concrete classes that will provide the actual implementation of the camera data.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
