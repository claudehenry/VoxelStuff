package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating and manipulating views
 * and projections of a 3D scene from a camera's perspective. It includes fields for
 * storing camera position, rotation, and projection matrix, as well as methods for
 * calculating view and projection matrices, and adjusting to the size of a target
 * viewport. The class also includes an abstract method for calculating a projection
 * matrix for a given camera structure.
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
	 * calculates and returns a `Matrix4f` object representing the view projection matrix,
	 * which combines the view and projection matrices needed for rendering 3D graphics.
	 * 
	 * @returns a `Matrix4f` object representing the view projection matrix.
	 * 
	 * 	- `viewProjectionMat4`: A matrix representing the view projection transformation.
	 * It is a product of the view and projection matrices.
	 * 	- `transform`: A reference to the transform state, which is used to determine
	 * whether the view projection matrix needs to be recalculated.
	 * 
	 * The function returns the view projection matrix, which is used to transform 3D
	 * points from the world coordinate system to the screen coordinate system. The matrix
	 * is a product of the view and projection matrices, and it represents the transformation
	 * that combines the view and projection effects.
	 */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

	/**
	 * calculates a view matrix by multiplying the camera rotation and translation matrices,
	 * then applying them to a projection matrix.
	 * 
	 * @returns a matrix representation of the view transformation, which combines the
	 * camera rotation and translation matrices.
	 * 
	 * The `calculateViewMatrix` function takes the `projection`, `transform`, and
	 * `getTranslationMatrix` methods as inputs and returns a `Matrix4f` object representing
	 * the view matrix. The view matrix is a 4x4 homogeneous transformation matrix that
	 * encodes the orientation and translation of the camera relative to the world
	 * coordinate system.
	 * 
	 * The `projection` method multiplies the camera rotation matrix (represented by
	 * `cameraRotation`) with the camera translation matrix (represented by `cameraTranslation`),
	 * producing a new matrix that represents the view transformation. This operation
	 * combines the effects of both rotations and translations on the camera's position
	 * in the world.
	 * 
	 * The resulting view matrix is then returned as the output of the `calculateViewMatrix`
	 * function.
	 */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

	/**
	 * generates a 4x4 transformation matrix that represents a translation, where the
	 * translation vector is negative of the current position of the transform node.
	 * 
	 * @returns a 4x4 matrix representing the translation of the camera position from its
	 * initial state.
	 * 
	 * 	- The Matrix4f object represents a 4x4 transformation matrix that contains the
	 * translation vector (in the format (x, y, z)) in the top-left corner.
	 * 	- The translation vector is computed by taking the negative of the transform
	 * position, which is represented as a Vector3f instance.
	 * 	- The resulting Matrix4f object can be used to perform transformations on 3D
	 * objects or scenes, such as moving an object from one location to another.
	 */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

	/**
	 * retrieves the `transform` object, which is an instance of the `Transform` class
	 * and represents a transformation in a mathematical or computational context.
	 * 
	 * @returns a reference to a `Transform` object.
	 * 
	 * 	- `transform`: The transformed value is a `Transform` object representing the
	 * result of applying the transformation to the input.
	 */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

	/**
	 * is an abstract class that provides a framework for implementing various camera-related
	 * structures. It has an abstract method `getAsMatrix4()` that returns a Matrix4f
	 * object, which can be used to represent the camera's projection matrix. The class
	 * also provides a high level of flexibility by allowing subclasses to define their
	 * own methods and fields as needed.
	 */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
