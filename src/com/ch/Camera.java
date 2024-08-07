package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that represents a camera in a 3D scene. It manages transformations
 * and projections for rendering scenes. The class provides methods to calculate view
 * matrices and projection matrices based on the camera's position, rotation, and
 * viewport dimensions.
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
	 * It checks if the view projection matrix is null or has been updated, and if so,
	 * calculates the new matrix before returning it.
	 *
	 * @returns a `Matrix4f`, potentially recalculated based on changes to the transformation.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix by multiplying the camera's rotation and translation
	 * matrices with the projection matrix. The result is then assigned to the
	 * `viewProjectionMat4` variable, representing the combined view and projection transformation.
	 *
	 * @returns a combined view and projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Multiplies a given position by -1 to obtain the camera's position and then creates
	 * a translation matrix from the resulting coordinates. The matrix represents the
	 * transformation that moves the origin to the camera's position.
	 *
	 * @returns a Matrix4f object initialized with a translation based on camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves and returns a `Transform` object. The retrieved object is stored in the
	 * `transform` variable, which appears to be an instance field or property of the
	 * containing class. This function provides read-only access to the `Transform` object.
	 *
	 * @returns an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that provides a way to represent camera-related data in a
	 * matrix format. It has an abstract method getAsMatrix4 that returns a Matrix4f
	 * object representing the camera's properties. This allows for flexibility and
	 * customization of how camera data is handled.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
