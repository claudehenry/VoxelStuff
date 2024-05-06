package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is a subclass of the Camera class that provides additional functionality for
 * manipulating the camera's position and viewport size. It contains a matrix4f
 * variable for storing the camera's projection matrix, and an inner class called
 * CameraStruct3D that stores the camera's position, viewport size, and other properties.
 * The processInput method processes input from the user, such as mouse movements and
 * keyboard keys, to adjust the camera's position and rotation.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a Matrix4f object representing the projection transformation based on
	 * the provided CameraStruct data.
	 * 
	 * @param data 3D camera information, including its intrinsic and extrinsic parameters,
	 * which are used to compute the projection matrix.
	 * 
	 * 	- `projection`: A `Matrix4f` object that represents the camera's projection matrix.
	 * 	- `getAsMatrix4()`: A method that returns the `Matrix4f` representation of the
	 * input data.
	 * 
	 * @returns a `Matrix4f` object representing the camera's projection matrix.
	 * 
	 * The `Matrix4f` object `projection` represents a 4x4 homogeneous transformation
	 * matrix that maps 3D points from the camera's coordinate system to the projection
	 * coordinate system.
	 * 
	 * The elements of the matrix are composed of the following:
	 * 
	 * 	- The upper left 3x3 block represents the camera's intrinsic parameters, such as
	 * the focal length and distortion coefficients.
	 * 	- The additional 1x4 block represents the camera's extrinsic parameters, such as
	 * the rotation and translation vectors relative to the world coordinate system.
	 * 
	 * The matrix is homogeneous, meaning that the last column represents a scalar value
	 * that can be used for division or multiplication with other matrices.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's projection matrix and view matrix based on the window size,
	 * and then sets the viewport to the new size using GL11.glViewport().
	 * 
	 * @param width 2D viewport width of the screen.
	 * 
	 * @param height 2D image size of the viewport, which is used to calculate the aspect
	 * ratio and projection matrix, and to set the viewport dimensions in the GL11.glViewport()
	 * method.
	 */
	@Override
	public void adjustToViewport(int width, int height) {
		((CameraStruct3D) this.values).aspect = (float) width / height;
		calculateProjectionMatrix(values);
		try {
			calculateViewMatrix();
		} catch (NullPointerException e) {
		}
		GL11.glViewport(0, 0, width, height);
	}

	/**
	 * is a custom class that extends the Camera Struct and provides additional functionality
	 * for a 3D camera. It includes fields for fov, aspect, zNear, and zFar, which are
	 * used to calculate the perspective projection matrix. The getAsMatrix4() method
	 * returns a Matrix4f object representing the perspective projection matrix.
	 */
	protected class CameraStruct3D extends CameraStruct {

		public float fov, aspect, zNear, zFar;

		public CameraStruct3D(float fov, float aspect, float zNear, float zFar) {
			this.fov = fov;
			this.aspect = aspect;
			this.zNear = zNear;
			this.zFar = zFar;
		}

		/**
		 * returns a matrix representing a perspective projection, with fields for field of
		 * view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection.
		 * 
		 * 	- The function returns a `Matrix4f` object representing a 4x4 matrix that encodes
		 * the perspective projection of a 3D scene in homogeneous coordinates.
		 * 	- The `fov` parameter represents the field of view (in radians) of the projection,
		 * while `aspect` is the aspect ratio of the viewport.
		 * 	- The `zNear` and `zFar` parameters define the near and far clipping planes of
		 * the projection, respectively.
		 * 
		 * The resulting matrix has the following properties:
		 * 
		 * 	- It represents a perspective projection, meaning that objects that are closer
		 * to the viewer will appear larger than those further away.
		 * 	- The matrix is inverted (i.e., transposed), as required for many applications
		 * in computer graphics and vision.
		 * 	- The matrix has determinant 1, indicating that it represents an orthogonal
		 * projection (i.e., a projection where the columns of the matrix are mutually perpendicular).
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes input from the mouse and keyboard to move an object in 3D space using
	 * rotation and translation. It adjusts movement speed based on the LShift key and
	 * moves the object in different directions based on other keys.
	 * 
	 * @param dt delta time, which is used to calculate the movement of the object based
	 * on the input from the keyboard keys.
	 * 
	 * @param speed 3D movement speed of the object being controlled, and it is multiplied
	 * by the time step `dt` to determine the total distance traveled during each frame.
	 * 
	 * @param sens sensitivity of the character's movement in response to user input,
	 * which affects how quickly the character moves in response to mouse movements.
	 */
	public void processInput(float dt, float speed, float sens) {

		float dx = Mouse.getDX();
		float dy = Mouse.getDY();
		float roty = (float)Math.toRadians(dx * sens);
		getTransform().rotate(new Vector3f(0, 1, 0), (float) roty);
		getTransform().rotate(getTransform().getRot().getRight(), (float) -Math.toRadians(dy * sens));
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
			speed *= 10;
		
		float movAmt = speed * dt;

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
			move(getTransform().getRot().getForward(), movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
			move(getTransform().getRot().getForward(), -movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
			move(getTransform().getRot().getLeft(), movAmt);
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
			move(getTransform().getRot().getRight(), movAmt);
		
	}

	/**
	 * moves the object by a specified amount in the direction of a provided vector, using
	 * the transform's `setPos()` method.
	 * 
	 * @param dir 3D direction in which the object should be moved, with its magnitude
	 * specified by the `amt` parameter.
	 * 
	 * 	- `dir`: A `Vector3f` object representing a 3D direction vector.
	 * 	+ It has three components: `x`, `y`, and `z`, which represent the respective
	 * coordinates of the direction vector in the X, Y, and Z axes.
	 * 	+ Each component has a value between -1 and 1, indicating the magnitude of the
	 * direction vector.
	 * 
	 * @param amt amount of movement to be applied to the object's position along the
	 * direction specified by the `dir` parameter.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
