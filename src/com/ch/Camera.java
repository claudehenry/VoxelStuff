package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating and manipulating matrices
 * related to camera view and projection. It takes a Matrix4f object as its constructor
 * argument and provides methods for calculating the view matrix, translation matrix,
 * and projection matrix. The class also includes an abstract method for adjusting
 * the camera to fit within a specified viewport size.
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
	 * retrieves a matrix object representing the view projection, calculated from the
	 * transform and stored as `viewProjectionMat4`.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * The `viewProjectionMat4` is a 4x4 matrix representing the view projection transformation.
	 * It is calculated based on the camera's position and orientation, as well as any
	 * transformations applied to the scene.
	 * The matrix includes elements for the camera's position, roll, pitch, yaw, as well
	 * as the aspect ratio of the viewport.
	 * The `viewProjectionMat4` is used in various applications such as rendering 3D
	 * graphics or creating computer vision algorithms.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * computes a view matrix that combines a camera rotation and translation with a
	 * projection matrix, resulting in a transformed view matrix for rendering purposes.
	 * 
	 * @returns a matrix representing the view transformation of a 3D camera.
	 * 
	 * 	- `viewProjectionMat4`: This is a 4x4 matrix representing the view and projection
	 * transforms combined. The rows represent the 3D coordinates (x, y, z) of points in
	 * the scene, while the columns represent the corresponding projected coordinates (u,
	 * v, 1-t).
	 * 	- `projection`: This is an instance of a class that represents the camera's
	 * projection transformation, which maps the 3D points to 2D screen coordinates.
	 * 	- `cameraRotation`: This is an instance of a class that represents the rotation
	 * of the camera around its center, represented as a 4x4 matrix.
	 * 	- `cameraTranslation`: This is an instance of a class that represents the translation
	 * of the camera relative to its original position, represented as a 4x4 matrix.
	 * 
	 * The `calculateViewMatrix` function first multiplies the rotation matrix by the
	 * translation matrix to get the total transformation matrix, and then applies the
	 * projection transformation to this matrix to produce the final view and projection
	 * matrix.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * computes a translation matrix for a given position.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position from its
	 * original location.
	 * 
	 * The function returns a 4x4 Matrix4f object representing a translation transformation
	 * from the camera's current position to a new position. The matrix elements represent
	 * the offset in world coordinates from the origin of the camera's coordinate system
	 * to the new position.
	 * 
	 * The first three elements (column) of the matrix represent the x, y, and z-components
	 * of the translation vector, respectively, while the fourth element represents the
	 * scale factor or "zoom" of the transformation. This means that when applying this
	 * matrix to a point in 3D space, the resulting point will be at the new position by
	 * the amount specified by the translation vector components, with any scaling applied
	 * by the zoom factor.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * retrieves the `transform` object, which is an instance variable. The returned
	 * object represents a transformation operation that can be applied to a matrix or
	 * other mathematical structure.
	 * 
	 * @returns the `transform` object itself.
	 * 
	 * The `transform` object returned is of type `Transform`.
	 * 
	 * This transform is used to perform geometric transformations on 2D points and shapes.
	 * It has several methods for manipulating points, lines, rectangles, circles, and
	 * other shapes, as well as combining multiple transformations into a single
	 * transformation matrix.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base for other classes inheriting from it.
	 * It has an abstract method `getAsMatrix4()` that returns a Matrix4f object, which
	 * is likely used to represent the camera's projection matrix in various situations.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
