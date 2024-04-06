package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * TODO
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
  * retrieves a Matrix4f object representing the view projection transformation,
  * calculated or retrieved from a previous calculation or storage.
  * 
  * @returns a `Matrix4f` object representing the view projection matrix.
  * 
  * 	- `viewProjectionMat4`: This is a `Matrix4f` object that represents the view
  * projection matrix, which combines the view and projection matrices. It is calculated
  * based on the camera's position, orientation, and field of view.
  * 	- `transform`: This refers to the transform component that is used to update the
  * view projection matrix when the camera moves or rotates. It indicates whether the
  * view projection matrix has changed.
  */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

 /**
  * calculates a view matrix based on a camera's rotation and translation vectors. The
  * function takes the transformation matrix, rotation matrix, and translation vector
  * as inputs and returns the resulting view matrix.
  * 
  * @returns a Matrix4f object representing the view matrix, which combines the camera
  * rotation and translation matrices.
  * 
  * The `calculateViewMatrix` function returns a `Matrix4f` object, representing a 4x4
  * homogeneous transformation matrix. This matrix contains the view and projection
  * transformations applied to the camera's position, rotation, and scale. Specifically,
  * it is the result of multiplying the camera rotation matrix (`cameraRotation`) with
  * the camera translation vector (`cameraTranslation`), followed by the multiplication
  * with the projection matrix (`projection`).
  * 
  * The `Matrix4f` object contains several attributes that can be used to describe its
  * properties:
  * 
  * 	- `values`: a 16-element array containing the components of the matrix in row-major
  * order. This can be accessed and modified using the `get()` and `set()` methods.
  * 	- `determinant`: the determinant of the matrix, which is used to determine the
  * invertibility of the matrix. This can be accessed using the `determinant()` method.
  * 	- `isInvertible(): a boolean indicating whether the matrix is invertible or not.
  * This can be accessed using the `isInvertible()` method.
  * 
  * Overall, the `calculateViewMatrix` function returns a transformation matrix that
  * encodes the view and projection transformations applied to the camera's position,
  * rotation, and scale, which can be used in various applications such as 3D rendering,
  * computer vision, or robotics.
  */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

 /**
  * generates a 4x4 transformation matrix that translates a virtual camera by a specified
  * distance in the x, y, and z directions.
  * 
  * @returns a 4x4 matrix representing the translation of the camera position by (-1,
  * -1, -1).
  * 
  * The `Matrix4f` object represents a 4x4 matrix that contains the translation vector
  * in its elements.
  * 
  * The `initTranslation` method takes three float arguments representing the x, y,
  * and z components of the translation vector. These values are used to initialize
  * the matrix with the specified translation.
  * 
  * The resulting matrix will have the form:
  * ```
  * [translation_x, 0, 0, 0]
  * [0, translation_y, 0, 0]
  * [0, 0, translation_z, 0]
  * [0, 0, 0, 1]
  * ```
  * where `translation_x`, `translation_y`, and `translation_z` are the x, y, and z
  * components of the translation vector, respectively.
  */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

 /**
  * returns the `transform` object, which is an instance of the `Transform` class.
  * 
  * @returns a `Transform` object containing the transformation details.
  * 
  * The output is a `Transform` object, which represents a transformation in the system.
  * This transformation can be used to apply various effects to the input data, such
  * as scaling, rotating, or translating it.
  * The `Transform` class has several attributes that determine its behavior, including
  * `getType`, `getX`, `getY`, and `getZ`.
  * These attributes provide information about the transformation's location and
  * orientation in 3D space.
  */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

 /**
  * TODO
  */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
