package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

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
	 * retrieves and returns a `Matrix4f` object representing the view projection matrix,
	 * which is used to transform 3D vectors into screen space.
	 * 
	 * @returns a matrix representing the view projection transformation.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * generates a view matrix that combines a rotation and translation of a camera based
	 * on its position, rotation, and projection information.
	 * 
	 * @returns a Matrix4f object representing the view matrix, which combines the rotation
	 * and translation of the camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a matrix that represents the translation of a virtual camera's position
	 * from its original position to the opposite side of the screen.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera's position by the
	 * negative of its current position.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * retrieves the transform object associated with the current state.
	 * 
	 * @returns a `Transform` object representing the transformation of the input.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
