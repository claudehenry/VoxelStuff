package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an abstract class that provides methods for calculating and manipulating matrices
 * related to camera transformations and viewport adjustments. It has fields for
 * projection, viewProjectionMat4, values, transform, and a method for calculateViewMatrix.
 * Additionally, it has abstract methods for calculateProjectionMatrix and adjustToViewport.
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
	 * Retrieves a matrix object representing the view projection transformation, which
	 * combines the camera's view transform and projection. If the matrix is null or has
	 * changed, it calculates the view matrix using the `calculateViewMatrix()` method.
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
	 * Calculates the view matrix by multiplying the camera's rotation and translation
	 * matrices using the projection matrix. The resulting matrix represents the
	 * transformation from world space to the camera's view.
	 * 
	 * @returns a 4x4 matrix representing the view transformation of a camera.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * Generates a matrix that represents the translation of a 3D transform from its
	 * original position to the opposite side of the screen.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera's position in
	 * homogeneous coordinates.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * Retrieves the `transform` object and returns it, allowing for easy access to the
	 * transformation capabilities of the class.
	 * 
	 * @returns a reference to an instance of the `Transform` class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * Is an abstract class that serves as a base for other classes in the "Camera"
	 * package. It contains an abstract method called getAsMatrix4() and does not have
	 * any fields or methods of its own. The purpose of this class is to provide a common
	 * base for other classes in the package, allowing them to inherit its properties and
	 * methods without having to duplicate code.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
