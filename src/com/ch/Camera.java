package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating and manipulating matrices
 * related to camera functionality. It has various fields and methods for calculating
 * and adjusting view and projection matrices, as well as handling transformations
 * and translation. The class also includes an abstract method for calculating a
 * projection matrix.
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
	 * computes and returns the view projection matrix, which is a combination of the
	 * view matrix and the projection matrix used to transform 3D coordinates into screen
	 * space.
	 * 
	 * @returns a Matrix4f object representing the view projection matrix.
	 * 
	 * 	- `viewProjectionMat4`: A 4x4 matrix object representing the view projection
	 * transformation. This transformation combines the view and projection matrices to
	 * produce the final view frustum for rendering.
	 * 	- `transform`: A transform instance containing information about the view and
	 * projection transformations, including their respective matrices and other parameters.
	 * The `transform` instance is used to determine whether the view projection matrix
	 * needs to be recalculated.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix that combines a rotation and translation of a camera. The
	 * rotation is derived from the transform's rotational component, while the translation
	 * is obtained from the getTranslationMatrix() method. The resulting view matrix is
	 * then created by multiplying the projection matrix with the product of the rotation
	 * and translation matrices.
	 * 
	 * @returns a matrix representation of the view transformation, including rotation
	 * and translation.
	 * 
	 * 	- The output is a 4x4 matrix, representing a view projection matrix that combines
	 * a rotation and translation of the camera.
	 * 	- The rotation part of the matrix represents the transformation of the camera's
	 * orientation relative to the world coordinate system.
	 * 	- The translation part of the matrix represents the position of the camera in the
	 * world coordinate system.
	 * 	- The resulting matrix is obtained by multiplying the projection matrix with the
	 * rotated and translated camera matrix, using the `mul` method.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a matrix that represents the translation of a camera positioned in a
	 * specific coordinate system, given by `transform.getTransformedPos()`. The resulting
	 * matrix is initialized with the negative of the camera's position vector and then
	 * transformed using multiplication to create the final translation matrix.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera's position from
	 * its current location to a new location.
	 * 
	 * 	- The Matrix4f object represents a 4x4 homogeneous transformation matrix.
	 * 	- The translation component of the matrix is represented by the vector (cameraPos.x,
	 * cameraPos.y, cameraPos.z), which indicates the distance and direction from the
	 * origin to the translation point in 3D space.
	 * 	- The matrix itself is initialized using the `initTranslation` method, which takes
	 * the x, y, and z components of the translation vector as arguments.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * returns the `transform` object, which represents a transformation matrix for
	 * geometric transformations.
	 * 
	 * @returns the `transform` object itself.
	 * 
	 * 	- The returned transform object is an instance of the `Transform` class.
	 * 	- It contains the transformation applied to the input data.
	 * 	- The transformation includes information about the matrix and any additional
	 * attributes required for the specific application.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base for other classes in the package
	 * com.ch.math. It does not contain any fields or methods of its own, but rather
	 * provides an abstract method called getAsMatrix4() that allows subclasses to provide
	 * their own implementation for calculating a Matrix4f object.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
