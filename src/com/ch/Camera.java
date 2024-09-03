package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Defines an abstract base class for camera functionality in a 3D graphics context.
 * It encapsulates projection and view matrices along with transformation data.
 * Abstract methods handle specific camera calculations based on provided data.
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
	 * Returns a Matrix4f object representing the combined view and projection matrices,
	 * or recalculates them if necessary to ensure consistency with changed transformation
	 * data. A check is performed to determine if the view projection matrix needs updating.
	 * The updated or cached view projection matrix is returned.
	 *
	 * @returns a Matrix4f object representing the combined view and projection transformations.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Computes a view matrix by multiplying three matrices: the inverse rotation of the
	 * camera, the translation of the camera, and a predefined projection matrix. The
	 * result is stored in the `viewProjectionMat4` variable and returned. This is likely
	 * used for rendering or graphics purposes.
	 *
	 * @returns a view-projection matrix resulting from multiplication of three matrices.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix based on a camera position, which is obtained by
	 * transforming the current transform position and negating it. The result is returned
	 * as a new Matrix4f instance initialized with the translation parameters. The resulting
	 * matrix represents the transformation that translates an object to the negative of
	 * the camera position.
	 *
	 * @returns a matrix representing the translation of the camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an instance of the `Transform` class representing a transformation,
	 * encapsulating the current state of some object or system. The returned `transform`
	 * instance is read-only and provides access to its properties without modifying them.
	 * This method allows external components to utilize this information.
	 *
	 * @returns an instance of a `Transform` class or object.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that provides a basic structure for camera-related data. It
	 * has an abstract method getAsMatrix4 that returns a Matrix4f object representing
	 * the camera's state. The purpose of this class is to encapsulate camera-specific
	 * data and behavior.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
