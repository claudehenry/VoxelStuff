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
  * This function returns the View-Projection matrix of a transform object. It first
  * checks if the matrix is already calculated or if the transform has changed and
  * then returns the view projection matrix.
  * 
  * 
  * @returns { Matrix4f } The function `getViewProjection()` returns a `Matrix4f`
  * object representing the combined view and projection matrix of the object.
  */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

 /**
  * This function calculates the view matrix for a 3D object using the current camera
  * transformation (rotation and translation). It first gets the rotation and translation
  * matrices from the transform component of the object and then multiplies them
  * together to get the view matrix using the `projection` matrix from the scene.
  * 
  * 
  * @returns { Matrix4f } This function takes the view projection matrix and returns
  * a new matrix that represents the combined view and projection transformations. The
  * output is a Matrix4f object containing the following components:
  * 
  * 	- Camera rotation matrix (obtained from the transformedRot property of the transform
  * object)
  * 	- Camera translation matrix (obtained from the getTranslationMatrix method)
  * 	- Projection matrix (from the projection object's mul method)
  * 
  * In other words., the function creates a new Matrix4f that represents the product
  * of the camera rotation and translation matrices and the projection matrix.
  */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

 /**
  * This function returns the translation matrix that represents the position of the
  * camera relative to the scene. It takes into account the inverse transformation of
  * the camera's position and then initializes a new translation matrix with those values.
  * 
  * 
  * @returns { Matrix4f } The output returned by this function is a `Matrix4f` object
  * that represents a translation matrix. The matrix translates the origin of the
  * coordinate system by the vector `cameraPos`, which is obtained by multiplying the
  * position of the camera by negative one (to flip the sign). The resulting matrix
  * moves all points along the axes of the camera's local coordinate system to the
  * desired position relative to the camera.
  */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

 /**
  * This function returns the "transform" object associated with the object.
  * 
  * 
  * @returns { Transform } The function `getTransform()` returns the object reference
  * `transform`, which is undefined. Therefore the output returned by this function
  * is `undefined`.
  */
	public Transform getTransform() {
		return transform;
	}
	
 /**
  * The function `calculateProjectionMatrix(CameraStruct data)` computes and returns
  * a 4x4 matrix that represents the projection transformation of a camera specified
  * by the `data` parameter.
  * 
  * 
  * @param { CameraStruct } data - The `data` input parameter passed to the
  * `calculateProjectionMatrix()` function is a `CameraStruct` object that contains
  * the camera's intrinsic and extrinsic parameters (e.g., position of camera/eye/viewer).
  * The function uses this data to generate the projection matrix based on the camera's
  * viewpoint.
  * 
  * @returns { Matrix4f } The function `calculateProjectionMatrix(CameraStruct data)`
  * returns a `Matrix4f` object that represents the projection matrix of a camera based
  * on the provided `CameraStruct` data.
  */
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

 /**
  * The `adjustToViewport` function resizes the viewport to fit the image within the
  * specified `width` and `height`, while maintaining its aspect ratio.
  * 
  * 
  * @param { int } width - The `width` input parameter determines the new viewport
  * size based on which the component should be scaled to fit within the specified bounds.
  * 
  * @param { int } height - The `height` input parameterin the `adjustToViewport()`
  * function specifies the height of the viewport into which the element should be adjusted.
  */
	public abstract void adjustToViewport(int width, int height);

	protected abstract class CameraStruct {

  /**
   * This function `getAsMatrix4()` is a protected abstract method of an object that
   * returns the object as a `Matrix4f` representation. It abstracts the implementation
   * detail of how the object can be converted to a `Matrix4f`, making it possible for
   * subclasses to provide their own implementation if needed.
   * 
   * 
   * @returns { Matrix4f } The function `getAsMatrix4()` returns a matrix representation
   * of the object as a `Matrix4f`. In other words，it returns a 4x4 matrix that represents
   * the object's position、rotation、and scalingin the form of a openGL  Matrix4f.
   */
		protected abstract Matrix4f getAsMatrix4();

	}

}


