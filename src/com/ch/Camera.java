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
  * This function returns the current view-projection matrix of a 4D transformation
  * object (representing a camera's view). It first checks if the matrix has been
  * calculated already or if any transform changes have occurred since the last
  * calculation; if so. The "calculateViewMatrix()" method is called to re-compute the
  * correct matrix
  * 
  * @returns The function `getViewProjection()` returns a `Matrix4f` object containing
  * both the view and projection transformations combined.
  */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

 /**
  * This function calculates the view matrix for a 3D object using the provided
  * transformation matrices. It does so by first getting the rotation and translation
  * of the camera's transform data (cameraRotation and cameraTranslation), and then
  * multiplying these two matrices together (using the left-multiply and right-multiply
  * operator) to create the view matrix. The resulting view matrix is then returned.
  * 
  * @returns This function takes a `Transform` object and returns a `Matrix4f` object
  * representing the view matrix. The view matrix is obtained by first getting the
  * transformed rotation and translation of the camera from the transform object using
  * `getTransformedRot()` and `getTranslationMatrix()`, then multiplying these two
  * matrices together using the dot product of the projection matrix and the result
  * of conjugating the rotation matrix.
  */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

 /**
  * This function returns a translation matrix that moves the camera's position by
  * subtracting the current position of the camera from the transformed position of
  * the camera.
  * 
  * @returns The output returned by this function is a `Matrix4f` object representing
  * a translation matrix that translates the origin to the camera position (`cameraPos`).
  */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

 /**
  * This function returns the "transform" field of the object.
  * 
  * @returns The output of this function is `transform`, which is a reference to a
  * `Transform` object.
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

