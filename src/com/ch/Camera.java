package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices, as well as adjusting to the desired viewport size. It also has a transform
 * method that returns the current transformation state of the camera.
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
	 * computes and returns a `Matrix4f` object representing the view-projection
	 * transformation for a given camera transform. The function checks if the
	 * `viewProjectionMat4` is null or has changed, and calculates it when necessary using
	 * the `calculateViewMatrix` method.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * 	- The `viewProjectionMat4` variable is a matrix that represents the view projection
	 * transformation.
	 * 	- The matrix is constructed using the `calculateViewMatrix()` method, which is
	 * called internally in this function if necessary.
	 * 	- The `transform` field is used to determine if the view projection matrix has
	 * changed since the last call to this function. If it has, the `calculateViewMatrix()`
	 * method is called to update the matrix.
	 * 
	 * In summary, the `getViewProjection` function returns a view projection transformation
	 * matrix that is constructed using the `calculateViewMatrix()` method, and the matrix
	 * is updated internally if necessary based on changes to the `transform` field.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix that represents the camera's perspective projection and
	 * rotation. It takes the transformed rotation matrix and translation matrix as inputs
	 * and returns the view matrix after multiplying them together.
	 * 
	 * @returns a matrix representing the view transformation of a 3D camera.
	 * 
	 * The output is a 4x4 matrix representing a view matrix.
	 * 
	 * The first three columns represent the world coordinates of the camera in the view
	 * direction (x, y, and z).
	 * 
	 * The fourth column represents the focus distance of the camera at the current position.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * calculates and returns a transformation matrix that translates a point by a specified
	 * distance in the x, y, and z axes.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera's position from
	 * its original location.
	 * 
	 * The `Matrix4f` object is initialized with a translation vector consisting of three
	 * components representing the x, y, and z coordinates of the camera position,
	 * respectively. The translation vector is computed by multiplying the transformed
	 * position vector of the camera transform by -1.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * returns the `transform` object, which is an instance of the `Transform` class.
	 * 
	 * @returns a reference to a `Transform` object.
	 * 
	 * 	- The return type of the function is `Transform`, which indicates that it is a
	 * class that contains methods and fields related to transformations.
	 * 	- The name of the variable returned by the function is `transform`, which suggests
	 * that this variable represents a transformation object or instance.
	 * 	- The variable `transform` is of type `Transform`, indicating that it is a specific
	 * type of object that has certain attributes and methods defined within the `Transform`
	 * class.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that serves as a base class for other camera-related classes
	 * in the package. It provides an abstract method called `getAsMatrix4()` which returns
	 * a Matrix4f object, but does not define any concrete implementation for this method.
	 * This suggests that the class is meant to be extended and customized by subclasses,
	 * rather than being used directly.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
