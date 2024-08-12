package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that encapsulates camera-related functionality. It manages
 * projection and view matrices as well as transformation data, enabling the creation
 * of customizable cameras for various applications. The class also defines methods
 * to calculate matrices and adjust camera settings based on viewport dimensions.
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
	 * Returns a matrix combining the view and projection transformations. It calculates
	 * this combination only when necessary, i.e., if the transformation has changed or
	 * the cached matrix is null. Otherwise, it reuses the previously calculated result.
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
	 * Transforms a 3D scene based on the camera's position and rotation. It combines the
	 * camera's translation matrix with its rotation matrix, then multiplies the result
	 * by a projection matrix to produce the final view matrix.
	 *
	 * @returns a Matrix4f object representing the view matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix based on the negative transformed position of a
	 * camera. It multiplies the original transformation by -1 to invert its effect, then
	 * initializes a new Matrix4f with the resulting x, y, and z coordinates.
	 *
	 * @returns a 4x4 matrix representing a translation transformation.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves a transformation object, represented by the variable `transform`, and
	 * returns it to the caller. This method provides read-only access to the stored
	 * transformation information. The returned object allows for inspection or further
	 * processing of the transformation data.
	 *
	 * @returns an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that defines a template for objects containing camera-specific
	 * data. It provides a method to retrieve this data as a Matrix4f object. This structure
	 * is used in the calculation of projection matrices and other camera-related operations.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
