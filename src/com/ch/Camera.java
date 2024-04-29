package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices, as well as handling transformations and adjusting to the viewport. The
 * class has various fields and methods for managing camera parameters and transformations,
 * but the overall purpose of the class is to provide a framework for creating cameras
 * with different properties and behaviors.
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
	 * computes and returns a `Matrix4f` object representing the view projection
	 * transformation. It checks if the `viewProjectionMat4` is null or has changed, and
	 * calculates it when necessary using the `calculateViewMatrix()` method.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * 	- The `viewProjectionMat4` variable is an instance of the `Matrix4f` class in
	 * Java, representing a 4x4 matrix that encodes the view and projection transformations.
	 * 	- If the `viewProjectionMat4` matrix is null or has changed since the last call
	 * to the function, it will be recalculated using the `calculateViewMatrix()` method.
	 * 	- The `viewProjectionMat4` matrix contains 16 elements that represent the
	 * coefficients of the view and projection transformations, which are applied to the
	 * input image coordinates to produce the final output coordinates.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix that represents the combination of a camera rotation and
	 * translation, using the `projection` matrix to transform the result.
	 * 
	 * @returns a matrix representing the view transformation of a camera.
	 * 
	 * 	- The output is a 4x4 matrix represented as a product of three matrices: `projection`,
	 * `cameraRotation`, and `cameraTranslation`.
	 * 	- The resulting matrix represents the view projection matrix, which combines the
	 * effects of camera rotation and translation.
	 * 	- The `projection` matrix is responsible for projecting the 3D scene onto the
	 * image plane, based on the camera's intrinsic and extrinsic parameters.
	 * 	- The `cameraRotation` matrix represents the rotation of the camera relative to
	 * the world coordinates, which is applied after the projection step.
	 * 	- The `cameraTranslation` matrix represents the translation of the camera relative
	 * to the world origin, which is also applied after the rotation step.
	 * 
	 * Overall, the `calculateViewMatrix` function returns a view projection matrix that
	 * takes into account the camera's orientation and position in the world, allowing
	 * for accurate rendering of 3D scenes from different viewpoints.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a transformation matrix that translates the origin of the world coordinate
	 * system by a vector represented by the `Vector3f` object `cameraPos`.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position by a
	 * vector in the x, y, and z directions.
	 * 
	 * The returned Matrix4f object represents a 4x4 transformation matrix that translates
	 * the origin of the coordinate system by the vector (x, y, z). The translation vector
	 * is computed as the negative of the transform position of the parent GameObject.
	 * 
	 * The components of the translation vector are represented in the column major order,
	 * with the x-coordinate in the first column, followed by the y-coordinate in the
	 * second column, and so on. This means that the z-coordinate is in the third column,
	 * and the w-coordinate is in the fourth column.
	 * 
	 * The resulting matrix represents a 4x4 transformation matrix that can be applied
	 * to any vector or point in 3D space to translate it by the specified amount.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * returns the `transform` object, which is a variable containing the transformation
	 * logic for the application.
	 * 
	 * @returns a reference to a `Transform` object.
	 * 
	 * The returned transform object represents the result of applying a transformation
	 * to the input.
	 * It has access to the original input through the `transform` field, which is a
	 * reference to the original input.
	 * The transform object provides methods for manipulating and transforming the input,
	 * such as scaling, rotating, and translating.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that provides a framework for storing and manipulating
	 * camera-related data. It contains an abstract method `getAsMatrix4()` that returns
	 * a Matrix4f object, which can be used to represent the camera's projection matrix
	 * in 3D space. The class does not provide any field or method information.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
