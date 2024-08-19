package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that represents a camera in a 3D environment. It encapsulates
 * the projection and view matrices, as well as transformation data, allowing it to
 * render scenes from different viewpoints. The class provides methods for calculating
 * these matrices based on its own state and external input.
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
	 * Calculates a matrix representing the combined transformation from the camera's
	 * viewpoint and projection onto the screen. It does so by checking if either the
	 * previous result or the object transform has changed, and updates the matrix
	 * accordingly before returning it.
	 *
	 * @returns a `Matrix4f` object, either pre-calculated or newly calculated.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix by combining camera rotation and translation matrices.
	 * The rotation matrix is obtained from the transformed rotation of a transform object,
	 * while the translation matrix is retrieved from a separate method. The result is
	 * multiplied with the projection matrix to produce the final view matrix.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on the position of the camera, which is obtained
	 * by multiplying the original position with -1. The result is initialized as a new
	 * translation matrix with the x, y, and z coordinates of the transformed camera position.
	 *
	 * @returns a 4x4 matrix representing the translation transformation.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns a reference to an instance variable named `transform`. This indicates that
	 * it provides access to the current state of the object's transformation, allowing
	 * external code to retrieve and use its value. The transformation itself is not
	 * modified within this method.
	 *
	 * @returns an instance of class `Transform`, assigned to variable `transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that defines a structure for camera-related data. It provides
	 * a mechanism to retrieve this data in the form of a Matrix4f object. The exact
	 * composition and behavior of this data is left to concrete subclasses.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
