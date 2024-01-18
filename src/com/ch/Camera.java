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
  * This function returns the current view-projection matrix of the object. It first
  * checks if the matrix has already been computed and if the transform has changed
  * since the last computation. If either is true it recomputes the view matrix using
  * the calculateViewMatrix() method and then returns the resultant view-projection matrix.
  * 
  * @returns This function returns a `Matrix4f` object named `viewProjectionMat4`,
  * which represents the view projection transformation matrix. The output is a matrix
  * that transforms 3D points from camera space to projective space.
  */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

 /**
  * This function calculates the view matrix for a camera using the provided transform
  * matrix and projection matrix.
  * 
  * @returns The function `calculateViewMatrix()` returns a `Matrix4f` object that
  * represents the view matrix for the current camera position and rotation. The view
  * matrix is obtained by multiplying the camera's translation vector and rotation
  * matrix with the projection matrix. In other words:
  * 
  * Output: `Matrix4f` representing the view matrix for the current camera position
  * and rotation.
  */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

 /**
  * This function returns a matrix that represents a translation (i.e., displacement)
  * of the camera's position by the negative of its current transformed position. In
  * other words: it moves the camera back to its original position relative to the scene.
  * 
  * @returns This function returns a `Matrix4f` object that represents a translation
  * matrix. The matrix translates any point by the specified amounts along the x., y.,
  * and z-axes. Specifically:
  * 
  * 	- The `initTranslation()` method takes three double arguments (x-, y-, and
  * z-coordinates) to create a translation matrix.
  * 	- The return value is a newly created `Matrix4f` object that contains only the
  * translational part of the matrix (i.e., all zeroes for the rotational part).
  * 
  * In other words: The function takes three numbers representing the camera's position
  * and returns a matrix that can be multiplied by any 3D vector to move the vector
  * by the specified distance along each axis.
  */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

 /**
  * This function returns the current transform of an object.
  * 
  * @returns The output returned by this function is `undefined`. The reason is that
  * the variable `transform` has not been initialized or assigned a value. Therefore
  * the method `getTransform()` returns `null`, which is then cast to `undefined` when
  * returned from the function.
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

