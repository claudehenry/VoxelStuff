package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that provides methods for calculating view and projection
 * matrices, as well as getting and setting transformation values. It also has an
 * abstract method for calculating a projection matrix and another abstract method
 * for adjusting to the viewport size.
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
	 * Retrieves a matrix representing the view projection transformation, which combines
	 * the camera's view matrix and projection matrix. If the `viewProjectionMat4` variable
	 * is null or has changed since the last calculation, the function calculates and
	 * returns the view matrix.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}
		return viewProjectionMat4;
	}

	/**
	 * Calculates a view matrix that represents the transformation of a 3D camera's
	 * perspective and translation.
	 * 
	 * @returns a matrix representing the view transformation of a camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a transformation matrix for translating an object by a specified distance
	 * from its current position. The matrix is initialized with the negative of the
	 * camera's position, and then the translation amount is applied to each axis.
	 * 
	 * @returns a 4x4 transformation matrix that represents the camera's translation
	 * relative to its initial position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns the `transform` object, which represents a transformation from one coordinate
	 * system to another.
	 * 
	 * @returns a reference to an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that provides a base implementation for all camera structs
	 * in the `com.ch` package. It includes an abstract method `getAsMatrix4()` for
	 * returning a Matrix4f object, which can be used to represent the camera's transformation
	 * matrix in various contexts.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
