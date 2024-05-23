package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that contains methods for calculating and manipulating camera
 * matrices. It has several instance variables such as projection, viewProjectionMat4,
 * values, transform, and calculation methods such as calculateViewMatrix and
 * getTranslationMatrix. The class also has a few abstract methods like
 * calculateProjectionMatrix and adjustToViewport.
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
	 * calculates and returns the view projection matrix, which is used to transform 3D
	 * points from the world coordinate system to the camera's projection plane. The
	 * function updates the view projection matrix only if it has changed or if the view
	 * matrix is null.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * The `Matrix4f` object `viewProjectionMat4` represents the view-projection matrix,
	 * which combines the camera's perspective and orientation with the projection matrix
	 * to generate the final view. The matrix is not null when the function is called,
	 * unless `transform` has changed.
	 * 
	 * The view matrix is calculated using the `calculateViewMatrix()` method if necessary,
	 * based on changes in the `transform`.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix that combines a rotation and translation of a camera,
	 * performed using the `projection` and `transform` objects, and returns the result
	 * as a `Matrix4f` object.
	 * 
	 * @returns a 4x4 matrix representing the view transformation of a camera.
	 * 
	 * 	- The `viewProjectionMat4` variable represents a 4x4 matrix that combines the
	 * camera rotation and translation matrices using the multiplication operation.
	 * 	- The resulting matrix is a view projection matrix, which encapsulates both the
	 * perspective and zooming effects of the camera's movement.
	 * 	- The matrix has four rows, each representing a different component of the view
	 * transformation: translation, rotation around the world origin, and scaling in the
	 * local coordinate system.
	 * 	- The components of the matrix are arranged in columns, with each column representing
	 * a different axis of transformation (x, y, z, and w).
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a 4x4 transformation matrix that translates a point by a specific amount
	 * in the x, y, and z directions.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera's position in
	 * homogeneous coordinates.
	 * 
	 * The `Matrix4f` object returned by the function represents a 4x4 homogeneous
	 * transformation matrix that includes a translation component in the last column.
	 * The translation vector is computed as the negative of the transform position
	 * multiplied by 3 (since the transform position is a Vector3f). Therefore, the first
	 * three components of the translation vector represent the x, y, and z coordinates
	 * of the position shift, while the fourth component represents the scale factor of
	 * the transformation.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * returns the `transform` object, which is an instance of the `Transform` class,
	 * providing access to its methods and properties.
	 * 
	 * @returns a `Transform` object containing the transformation details.
	 * 
	 * The output is of type `Transform`, which represents a transformation matrix that
	 * can be used to transform points in 3D space. The matrix contains the necessary
	 * elements to perform rotations, scaling, and translations on a point. The values
	 * of the matrix are determined by the underlying properties of the transform object,
	 * such as its rotation, scale, and translation components.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class with a single abstract method, `getAsMatrix4()`. This method
	 * is expected to return a Matrix4f object, which will be used for various purposes
	 * related to the camera's view matrix calculations. The class does not provide any
	 * field or method for calculating the view matrix, rather it relies on the
	 * `calculateProjectionMatrix()` method of the Camera Class to perform that calculation.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
