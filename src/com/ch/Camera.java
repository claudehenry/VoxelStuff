package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Has several abstract and concrete methods for manipulating camera views and
 * projections. It takes in a Matrix4f object for projection and has various methods
 * to calculate view matrices, transformations, and adjust the camera to fit within
 * a specified viewport size. The class also includes an abstract class called
 * CameraStruct for storing camera data.
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
	 * Returns the view-projection matrix, calculated or retrieved from storage based on
	 * changes to the transform.
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
	 * Computes the view matrix, which represents the transformation from the world
	 * coordinate system to the camera's coordinate system, by multiplying the rotation
	 * and translation matrices of the camera.
	 * 
	 * @returns a matrix representation of the view transformation, including both rotation
	 * and translation.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

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
	 * Is an abstract class that serves as a base for various camera-related classes in
	 * the provided code snippet. It has an abstract method called `getAsMatrix4()` which
	 * returns a Matrix4f object, but its implementation is left to the subclass.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
