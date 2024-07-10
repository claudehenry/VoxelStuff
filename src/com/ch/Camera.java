package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that provides methods for calculating view and projection
 * matrices, as well as adjusting to a specified viewport size. It also has a
 * getTransform() method that returns the transform of the camera, and an abstract
 * calculateProjectionMatrix() method that can be overridden by subclasses.
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
	 * Retrieves a matrix representing the view projection, which combines the camera's
	 * view transformation and projection onto the image plane. If the matrix is null or
	 * has changed since the last calculation, it calculates and returns the view projection
	 * matrix.
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
	 * Calculates a view matrix by multiplying the camera rotation and translation matrices
	 * using the projection matrix. The resulting matrix represents the transformation
	 * from world space to camera space for rendering purposes.
	 * 
	 * @returns a 4x4 matrix representing the view transformation of a camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a matrix that represents a translation from the origin to a point in 3D
	 * space, based on the position of a camera transform.
	 * 
	 * @returns a 4x4 transformation matrix representing the camera's position in 3D
	 * space, with the x, y, and z components of the matrix corresponding to the translation
	 * vector in the correct order.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that contains an abstract method for getting a Matrix4f object
	 * and offers no other publicly accessible methods or fields.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
