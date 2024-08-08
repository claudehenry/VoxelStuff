package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class responsible for managing camera transformations and projections.
 * It initializes camera matrices based on camera position and rotation, and provides
 * methods to calculate view and projection matrices.
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
	 * Returns a Matrix4f object representing the combined view and projection transformation.
	 * It first checks if the view-projection matrix is null or if the transform has
	 * changed, then calculates the new view matrix before returning it.
	 *
	 * @returns a Matrix4f object representing the combined view and projection matrices.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix by multiplying three matrices: the conjugate of the
	 * camera's rotation, the translation matrix, and the projection matrix. The result
	 * is stored in the `viewProjectionMat4`.
	 *
	 * @returns a combined view-projection matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Creates a translation matrix that represents a camera's position. It calculates
	 * the camera's position by multiplying its original position with -1 and then
	 * initializes a new Matrix4f object with the calculated x, y, and z coordinates to
	 * form the translation matrix.
	 *
	 * @returns a 4x4 translation matrix.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns an object of type `Transform`, which appears to be a property or attribute
	 * of the class in which it is defined. The returned object seems to represent some
	 * kind of geometric transformation, potentially used for rendering or animation purposes.
	 *
	 * @returns a reference to an existing `transform` object.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base type for camera-specific data structures.
	 * It defines an abstract method getAsMatrix4() that returns a Matrix4f object,
	 * implying its purpose is to provide a means of converting the camera's structural
	 * information into a matrix representation. The class is protected and designed for
	 * use within the Camera class itself or its subclasses.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
