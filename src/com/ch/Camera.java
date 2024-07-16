package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class with multiple fields and methods for manipulating camera
 * views. It has a matrix4f field for projection and viewProjectionMat4, which are
 * used to calculate the view matrix. The class also has a transform field for storing
 * transformations, and a getTransform method for accessing it. Additionally, there
 * are methods for calculating the view matrix and adjusting the camera to fit within
 * a specified viewport size.
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
	 * Returns the view projection matrix, calculated either from a pre-existing matrix
	 * or recomputed if the transform has changed.
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
	 * Calculates a view matrix that combines the camera's rotation and translation with
	 * the perspective projection matrix to produce the final view matrix.
	 * 
	 * @returns a matrix representing the view transformation of a 3D scene from a camera's
	 * perspective, taking into account both rotation and translation.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a matrix that represents the translation of a camera's position by a
	 * negative value. The returned matrix has the form `translationX, translationY,
	 * translationZ`, where `translationX`, `translationY`, and `translationZ` are the
	 * component values of the camera's position.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Returns the `transform` object, which is an instance of the `Transform` class,
	 * representing a transformation matrix for 3D graphics.
	 * 
	 * @returns a reference to an object of type `Transform`.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base for other classes in the same package.
	 * It has a protected abstract method called `getAsMatrix4()` that returns a Matrix4f
	 * object, which represents a mathematical matrix used in the calculations of camera
	 * movements and transformations.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
