package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that provides methods for calculating view and projection
 * matrices, as well as handling transformation and translation. It also includes a
 * sub-class called CameraStruct with an abstract method for getting a Matrix4f representation.
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
	 * Retrieves and returns a matrix representing the view projection transformation,
	 * calculated either from a provided matrix or recalculated if the transform has
	 * changed since the last calculation.
	 * 
	 * @returns a Matrix4f object representing the view projection matrix.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * Calculates the view matrix, which represents the transformation from the world
	 * coordinate system to the camera coordinate system. It takes into account the
	 * rotation and translation of the camera and applies the projection matrix to the result.
	 * 
	 * @returns a matrix representing the view transformation of a camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a matrix that represents the translation of a 3D camera from its original
	 * position to a new position, based on the vector representation of the camera's position.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves and returns a `Transform` object, which represents a transformation
	 * applied to an input value.
	 * 
	 * @returns a reference to an object of type `Transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that provides a framework for creating objects with common
	 * properties and behaviors among various camera classes. It has an abstract method
	 * `getAsMatrix4()` that allows subclasses to provide their own implementation of the
	 * matrix representation, which can be used in methods like `calculateProjectionMatrix()`
	 * and `adjustToViewport()`.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
