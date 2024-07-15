package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that provides methods for calculating and manipulating matrices
 * related to camera functionality. It has fields for projection, viewProjectionMat4,
 * values, transform, and allows for calculation of projection matrix and adjustment
 * to the viewport size. Additionally, it provides abstract methods for
 * calculateProjectionMatrix and adjustToViewport, and a protected class for CameraStruct.
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
	 * Retrieves a `Matrix4f` object representing the view projection transformation,
	 * calculating it if necessary based on the current view and transformation matrices.
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
	 * Calculates a view matrix for a 3D scene based on the rotation and translation of
	 * a camera. The view matrix is used to transform 3D points from world space to screen
	 * space.
	 * 
	 * @returns a matrix representing the view transformation of a 3D scene from a camera's
	 * perspective.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Calculates a translation matrix that moves the camera position by a distance equal
	 * to its negation.
	 * 
	 * @returns a 4x4 transformation matrix representing the camera's position in the
	 * world, translated by a specified amount in the x, y, and z directions.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves and returns the `transform` object, which is an instance of the `Transform`
	 * class.
	 * 
	 * @returns a reference to an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base for other classes in the package
	 * com.ch.math. It provides an abstract method called getAsMatrix4() that returns a
	 * Matrix4f object, which can be used to perform various calculations related to
	 * camera and matrix operations.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
