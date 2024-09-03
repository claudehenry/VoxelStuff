package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents an abstract camera entity in a 3D graphics context. It encapsulates
 * projection and view transformation matrices, as well as a transform object that
 * handles rotation and translation of the camera. The class provides methods for
 * calculating view and projection matrices based on camera transformations.
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
	 * Returns a projection matrix that combines camera and viewing transformations,
	 * recalculating it if necessary based on changed transformations or an invalid cached
	 * matrix. The function relies on pre-existing functions to perform calculations. It
	 * returns the updated view-projection matrix.
	 *
	 * @returns a Matrix4f representing the combined view and projection transforms.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a combined view-projection matrix by multiplying a camera's rotation,
	 * translation, and projection matrices. It stores the result in the `viewProjectionMat4`
	 * variable, returning it as the output. The result represents the transformation
	 * from 3D world space to screen space.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix based on the negative transformed position of an
	 * object's transform, and returns it. The resulting matrix represents a translation
	 * operation that moves the object to its current position but negates its direction.
	 * A new Matrix4f instance is returned each time.
	 *
	 * @returns a translation matrix. The matrix represents a translation operation based
	 * on a given vector of coordinates. It defines a transformation that moves points
	 * along specified axes.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an instance of a class named `Transform`, stored within the current object
	 * as a field. The `transform` field is assumed to be non-null, although it does not
	 * explicitly check for nullity. This method provides access to the contained transform
	 * data.
	 *
	 * @returns an instance of a class implementing the `Transform` interface.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a structure for storing camera-related data
	 * and provides functionality to convert it into a Matrix4f representation. It is
	 * intended to be used by subclasses of the Camera class, possibly providing different
	 * types of camera configurations or settings. The class encapsulates a set of specific
	 * camera parameters in a structured format.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
