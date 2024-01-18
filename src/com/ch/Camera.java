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
  * This function returns the view projection matrix of a transformed object. It first
  * checks if the view projection matrix has not been created or if the transform has
  * changed and if so recalculates it and returns it.
  * 
  * @returns This function returns a `Matrix4f` object called "viewProjectionMat4"
  * that represents the view projection matrix. The output is a matrix that combines
  * the view and projection transformations.
  */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

 /**
  * This function calculates the view matrix for a camera given its translation and
  * rotation transformations. It first computes the rotation matrix from the transform's
  * transformed rotation and conjugates it; then multiplies that by the camera translation
  * to obtain the position vector of the camera and finally projects it through the
  * frustum provided by the perspective matrix.
  * 
  * @returns The output returned by this function is a Matrix4f object called
  * `viewProjectionMat4` that represents the view-projection transformation of the
  * camera. It is calculated by first converting the camera rotation and translation
  * to rotational and translational matrices using `transform.getTransformedRot()` and
  * `getTranslationMatrix()`, then applying the multiplication of these matrices to
  * the projection matrix `projection` using the function `mul()`. The resulting matrix
  * represents the final transformation that applies both the view and projection
  * perspectives to the 3D object coordinates.
  */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

 /**
  * This function creates a transformation matrix that translates the current camera
  * position (obtained from `transform.getTransformedPos())` by -1 times the camera
  * position and returns it as a Matrix4f object.
  * 
  * @returns The function returns a `Matrix4f` object that represents a translation
  * matrix that translates the camera position from the origin to the desired position
  * ( `-cameraPos` ). The resulting matrix will have a identity on the left and a
  * translation vector on the right ( `(0 0 0 1)` * `cameraPos` ), which will move the
  * camera position by the specified amount.
  */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

 /**
  * This function returns the current `Transform` object associated with the instance.
  * 
  * @returns The output returned by this function is `null`, because the variable
  * `transform` is undefined.
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

