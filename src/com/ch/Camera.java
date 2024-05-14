package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices, as well as adjusting to a specific viewport size. It also includes a
 * transform structure and an abstract class for implementing different camera types.
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
	 * retrieves a matrix representing the view and projection transforms for rendering
	 * 3D graphics, taking into account any changes to the transform.
	 * 
	 * @returns a Matrix4f object containing the view and projection transformations.
	 * 
	 * The matrix `viewProjectionMat4` represents the view projection transformation,
	 * which combines the camera's view matrix and the projection matrix. This transformation
	 * is used to render 3D objects from a specific viewpoint.
	 * 
	 * The matrix is calculated based on the camera's position, orientation, and field
	 * of view. The calculation involves multiplying the camera's view matrix by the
	 * projection matrix, which maps the 3D objects onto the image plane.
	 * 
	 * The `viewProjectionMat4` matrix has four rows, representing the homogeneous
	 * coordinates (x, y, z, w) of the 3D points in the world coordinate system. The
	 * matrix elements represent the transformation from the world coordinates to the
	 * image coordinates.
	 * 
	 * The returned matrix can be used in rendering algorithms to project 3D objects onto
	 * the image plane, taking into account the camera's perspective and field of view.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates the view matrix, which is used to transform 3D points from world space
	 * to view space, based on the rotation and translation of the camera.
	 * 
	 * @returns a Matrix4f object representing the view matrix, which combines the camera
	 * rotation and translation with the projection matrix.
	 * 
	 * The `calculateViewMatrix` function returns a `Matrix4f` object called `viewProjectionMat4`,
	 * which represents the view and projection matrix combined. This matrix is used to
	 * transform 3D points from world coordinates to screen coordinates. The matrix is
	 * computed by multiplying the rotation matrix `cameraRotation` with the translation
	 * matrix `cameraTranslation`.
	 * 
	 * The `cameraRotation` matrix represents the rotation of the camera, which is expressed
	 * as a 4x4 homogeneous transformation matrix. The multiplication with the
	 * `cameraTranslation` matrix adds the translation of the camera to its original position.
	 * 
	 * The resulting matrix `viewProjectionMat4` has dimensions 16x16 and contains 16
	 * elements, each representing a value between -1 and 1 for the corresponding element
	 * in the 3D coordinate system. This matrix can be used to perform transformations
	 * on 3D points, such as projection and viewing, by multiplying it with the point's
	 * coordinates.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a matrix that represents a translation in 3D space, given the position
	 * of the camera in world coordinates.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera's position from
	 * its original location.
	 * 
	 * The `Matrix4f` object returned by the function represents a 4x4 transformation
	 * matrix that translates the camera position by a vector in 3D space. The translation
	 * vector is obtained by multiplying the position of the transform by -1. The resulting
	 * matrix represents the inverse of the transformation, which when applied to a 3D
	 * point, will move the point back to its original position relative to the camera.
	 * 
	 * The matrix has four rows and four columns, with elements that represent the linear
	 * transformation between the camera coordinate system and the world coordinate system.
	 * The elements of the matrix can be accessed using the dot product of vectors, or
	 * through matrix multiplication with other matrices.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * retrieves the `transform` object, which is a instance of the `Transform` class,
	 * and returns it as an instance of the `Transform` interface.
	 * 
	 * @returns a `Transform` object.
	 * 
	 * The `transform` object returned is an instance of the `Transform` class.
	 * This object represents a transformation in the data flow graph and provides methods
	 * for applying transformations to input data.
	 * The `transform` object has various attributes, including its name, type, and any
	 * additional information that may be relevant in the context of the application.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base class for other classes in the Camera
	 * package. It provides an abstract method called getAsMatrix4() which returns a
	 * Matrix4f object of some kind, but the implementation details are left to the subclasses.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
