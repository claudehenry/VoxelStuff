package com.ch;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an abstract class that provides methods for calculating view and projection
 * matrices. It also has a transform field that can be used to adjust the camera's
 * position and orientation in 3D space. The class has several abstract methods for
 * calculating matrix representations of the camera's projection and transformation,
 * as well as methods for adjusting the camera to fit within a specified viewport.
 */
/**
 * is an abstract class that provides functionality for transforming 3D points in
 * world space to camera space and vice versa. It has various methods for calculating
 * view and projection matrices, as well as adjusting the matrix to fit a specific
 * viewport size. The class also has an abstract method `getAsMatrix4()` which is not
 * implemented in this class, and is meant to provide a common framework for other
 * classes to inherit functionality from.
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
  * computes and returns a matrix that represents the view projection transformation,
  * based on the current view matrix and other transforms.
  * 
  * @returns a Matrix4f object representing the view and projection transformation.
  * 
  * The `getViewProjection` function returns a `Matrix4f` object representing the view
  * projection matrix. This matrix is used to transform 3D points from the world
  * coordinate system to the camera's coordinate system. The matrix has four rows and
  * four columns, with elements representing the transformations of x, y, z, and w coordinates.
  * The function first checks if the `viewProjectionMat4` object is null or if the
  * `transform` field has changed since the last call to this function. If either of
  * these conditions is true, the function calculates the view matrix using a proprietary
  * method. The view matrix is then returned by the function.
  */
 /**
  * computes and returns a `Matrix4f` object representing the view-projection
  * transformation for a given transform state. If the view-projection matrix has
  * changed or is null, it recalculates it using the `calculateViewMatrix` method.
  * 
  * @returns a Matrix4f object representing the view and projection transformations.
  * 
  * 	- The `Matrix4f` object represents the view-projection matrix, which is used to
  * transform 3D points from world space to screen space.
  * 	- If the `viewProjectionMat4` variable is null or has changed, a new view matrix
  * is calculated using the `calculateViewMatrix()` function.
  * 	- The matrix is returned as a `Matrix4f` object.
  */
	public Matrix4f getViewProjection() {

		if (viewProjectionMat4 == null || transform.hasChanged()) {
			calculateViewMatrix();
		}

		return viewProjectionMat4;
	}

 /**
  * calculates a view matrix that combines the rotation and translation of a camera
  * with the projection of a 3D scene.
  * 
  * @returns a 4x4 matrix representing the view projection transformation.
  * 
  * 	- The output is a matrix object of type `Matrix4f`.
  * 	- The matrix represents the view transformation of the camera, combining the
  * rotation and translation of the camera.
  * 	- The rotation part of the matrix is represented by the `cameraRotation` variable,
  * which is a conjugate transpose of a rotation matrix.
  * 	- The translation part of the matrix is represented by the `cameraTranslation`
  * variable, which contains the displacement of the camera in 3D space.
  * 	- The multiplication of the `projection` matrix with the `cameraRotation` and
  * then with the `cameraTranslation` results in the final view transformation matrix.
  */
 /**
  * calculates a view matrix for a given camera transformation, including rotation and
  * translation. The resulting matrix is used to transform 3D points into screen coordinates.
  * 
  * @returns a 4x4 matrix representing the combined effect of camera rotation and
  * translation on the view space.
  * 
  * 	- `viewProjectionMat4`: The resulting matrix that represents the view projection
  * transformation. It is a 4x4 homogeneous matrix, where the first three columns
  * represent the camera's view direction (in homogeneous coordinates), and the fourth
  * column represents the camera's translation vector.
  * 	- `cameraRotation`: A 3x3 rotation matrix representing the rotation of the camera
  * around its center.
  * 	- `cameraTranslation`: A 3x1 vector representing the translation of the camera
  * relative to its initial position.
  */
	public Matrix4f calculateViewMatrix() {

		Matrix4f cameraRotation = transform.getTransformedRot().conjugate().toRotationMatrix();
		Matrix4f cameraTranslation = getTranslationMatrix();

		return (viewProjectionMat4 = projection.mul(cameraRotation.mul(cameraTranslation)));

	}

 /**
  * generates a 4x4 transformation matrix that represents a translation from the current
  * position of the transform to a new position.
  * 
  * @returns a 4x4 transformation matrix representing the camera's position in world
  * coordinates, with the origin at the camera's position.
  * 
  * 	- The Matrix4f object represents a 4x4 transformation matrix that translates by
  * the vector (x, y, z) in the world coordinates.
  * 	- The translation vector is computed as the inverse of the transform position
  * vector multiplied by -1.
  * 	- The resulting matrix is stored in a new Matrix4f object and returned from the
  * function.
  */
 /**
  * generates a 4x4 transformation matrix that represents a translation from the origin
  * to the position of a 3D transform.
  * 
  * @returns a 4x4 matrix representing a translation in 3D space, with the x, y, and
  * z components of the translation vector as the respective elements of the matrix.
  * 
  * 	- The Matrix4f object represents a 4x4 transformation matrix that translates the
  * origin by a vector in 3D space.
  * 	- The elements of the matrix represent the coordinates of the translation vector
  * in the order X, Y, Z, and W (in that order).
  * 	- The translation vector is negative of the camera position due to the mul()
  * method call. This means that the translated position will be opposite to the
  * original camera position.
  */
	public Matrix4f getTranslationMatrix() {
		Vector3f cameraPos = transform.getTransformedPos().mul(-1);
		return new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());
	}

 /**
  * returns the `transform` object, which contains the mapping between original and
  * transformed data.
  * 
  * @returns a reference to an instance of the `Transform` class.
  * 
  * The `transform` variable is an instance of the `Transform` class, which represents
  * a transformation matrix. The `Transform` class has several properties and methods
  * for manipulating transformations, including scaling, rotating, and translating
  * objects in 3D space.
  */
 /**
  * returns the `transform` object, which is a central component of the application's
  * logic.
  * 
  * @returns a reference to an instance of the `Transform` class.
  * 
  * 	- The `transform` variable is of type `Transform`, which represents a transformation
  * matrix.
  * 	- It contains information about the position, rotation, and scaling of an object
  * in 3D space.
  * 	- The values of the matrix represent the transformation coefficients that can be
  * applied to a 3D point to obtain its transformed position.
  */
	public Transform getTransform() {
		return transform;
	}
	
	public abstract Matrix4f calculateProjectionMatrix(CameraStruct data);

	public abstract void adjustToViewport(int width, int height);

 /**
  * is an abstract class that serves as a base for other classes inheriting from it
  * in the Camera package. It contains an abstract method `getAsMatrix4()` that returns
  * a Matrix4f object, which is not implemented in this class. The purpose of this
  * class seems to be to provide a common framework for other classes to build upon
  * and inherit functionality from.
  */
 /**
  * is an abstract class that serves as a base for other classes in the Camera package.
  * It contains an abstract method `getAsMatrix4()` that returns a Matrix4f object,
  * which is not implemented in this class. The purpose of this class seems to be to
  * provide a common framework for other classes to build upon and inherit functionality
  * from.
  */
	protected abstract class CameraStruct {

		protected abstract Matrix4f getAsMatrix4();

	}

}
