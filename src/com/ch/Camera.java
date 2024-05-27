package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices. It also has a transform field that can be used to adjust the camera's
 * position and orientation. The class has an abstract method for calculating the
 * projection matrix and another method for adjusting the camera to the desired
 * viewport size. Additionally, it has a protected class for storing camera-specific
 * data.
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
	 * calculating it if necessary based on the transform state.
	 * 
	 * @returns a Matrix4f object representing the view projection matrix.
	 * 
	 * 	- The matrix represents the view projection transformation, which combines a view
	 * matrix and a projection matrix to produce a 4x4 homogeneous transform.
	 * 	- The view matrix is responsible for transforming the 3D world coordinates into
	 * screen coordinates, taking into account the position of the camera and its orientation.
	 * 	- The projection matrix projects the 3D scene onto a 2D image plane, scaling and
	 * translating the 3D points to fit within the image boundaries.
	 * 	- Both matrices are combined in the returned output, allowing for efficient
	 * transformation of 3D objects into 2D images.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * computes a view matrix that combines a rotation and translation of a camera, using
	 * the `projection`, `transform`, and `getTranslationMatrix` methods.
	 * 
	 * @returns a Matrix4f object representing the view matrix, which combines the camera
	 * rotation and translation matrices.
	 * 
	 * 	- The output is a 4x4 matrix, representing the view matrix as defined by the input
	 * parameters.
	 * 	- The matrix contains the effects of both rotation and translation on the camera's
	 * view, allowing for perspective transformation and clipping of the resulting image.
	 * 	- The rotation component of the matrix captures the camera's orientation in 3D
	 * space, while the translation component represents the position of the camera
	 * relative to its original position.
	 * 	- The resulting matrix is a product of two matrices: the projection matrix and
	 * the camera rotation matrix. This composition allows for a concise representation
	 * of the view transformation.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a matrix that represents the translation of a 3D object from its original
	 * position to a new position, based on the position of the camera.
	 * 
	 * @returns a 4x4 homogeneous transformation matrix representing the camera's position
	 * and translation.
	 * 
	 * The function returns a 4x4 matrix representation of a translation transformation.
	 * The translation is applied to the origin of the coordinate system, represented by
	 * the vector (0, 0, 0).
	 * The matrix has the form `a * (x, y, z) + b`, where `a` and `b` are the components
	 * of the matrix, and `x`, `y`, and `z` are the coordinates of the translation vector.
	 * In this case, `a` is a 4x4 identity matrix, so the translation vector `b` is applied
	 * to the origin of the coordinate system.
	 * The resulting matrix represents a transformation that moves the origin of the
	 * coordinate system by the specified translation vector.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * returns a `Transform` object, which represents a transformation matrix for affine
	 * transformations.
	 * 
	 * @returns a `Transform` object.
	 * 
	 * The `transform` variable is an instance of the `Transform` class. This class has
	 * no specific details mentioned in the provided code snippet. Therefore, its attributes
	 * and properties are not explicitly described here.
	 * However, given that the method returns an object of type `Transform`, it likely
	 * contains information related to geometric transformations such as translations,
	 * rotations, scalings, or combinations thereof.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that provides a framework for implementing various camera-related
	 * structures. It has an abstract method `getAsMatrix4()` that allows subclasses to
	 * return a Matrix4f object, which can be used to represent the camera's projection
	 * matrix. The class does not provide any fields or methods of its own, instead relying
	 * on subclasses to implement their own functionality.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
