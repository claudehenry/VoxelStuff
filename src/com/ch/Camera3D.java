package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera class that provides additional functionality for
 * handling 3D camera inputs and calculations. It has a constructor that takes fov,
 * aspect, zNear, and zFar parameters to initialize the camera's projection matrix.
 * The calculateProjectionMatrix method calculates the perspective projection matrix
 * based on the input parameters. The adjustToViewport method adjusts the camera's
 * projection matrix to fit the viewport size and then calls the calculateViewMatrix
 * method to update the view matrix. The processInput method processes keyboard and
 * mouse inputs to rotate and move the camera.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a projection matrix based on input from the `CameraStruct` data structure.
	 * 
	 * @param data 3D camera's current state, including its position, orientation, and
	 * other parameters that are used to calculate the projection matrix.
	 * 
	 * 	- `getAsMatrix4()` returns a `Matrix4f` object containing the camera projection
	 * matrix as a 4x4 homogenous transformation matrix.
	 * 	- The returned matrix contains the intrinsic and extrinsic parameters of the
	 * camera, such as the focal length, principal point, distortion coefficients, and
	 * rotation and translation vectors.
	 * 
	 * @returns a Matrix4f object containing the projection matrix as specified by the
	 * `data` parameter.
	 * 
	 * The return value is a `Matrix4f` object, which represents a 4x4 homogeneous
	 * transformation matrix that maps 3D space to 2D image space.
	 * 
	 * The elements of the matrix are defined by the `projection` parameter, which is
	 * passed as an instance of the `CameraStruct` class. This means that the matrix can
	 * be customized based on the desired projection parameters, such as field of view,
	 * aspect ratio, and near and far clipping planes.
	 * 
	 * The matrix represents a perspective projection, where the viewer is located at (0,
	 * 0), and the image is rendered from a top-down perspective. The matrix also includes
	 * the camera's position, rotation, and scale factors to ensure that the projected
	 * image is correctly aligned with the real-world environment.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's projection and view matrices to fit within the specified
	 * viewport dimensions, ensuring proper aspect ratio and perspective.
	 * 
	 * @param width 2D viewport width for which the 3D object is to be adjusted.
	 * 
	 * @param height 2D viewport dimensions in pixels and is used to calculate the aspect
	 * ratio of the 3D scene and create the appropriate perspective projection matrix.
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
	 * is a extension of the CameraStruct Class and provides additional functionality for
	 * handling 3D cameras. It includes fields for fov, aspect, zNear, and zFar, which
	 * are used to calculate the camera's projection matrix. The class also includes
	 * methods for calculating the view matrix and moving the camera based on input from
	 * the mouse and keyboard.
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
		 * initializes a matrix representation of a perspective projection, with field of
		 * view (fov), aspect ratio, near and far distances for the Z-buffer.
		 * 
		 * @returns a matrix representation of a perspective projection, with fields for field
		 * of view, aspect ratio, near and far distances.
		 * 
		 * 	- The `Matrix4f` object represents a 4x4 homogeneous transformation matrix, where
		 * each element is a floating-point value between -1 and 1.
		 * 	- The matrix is initialized with a perspective projection, which is defined by
		 * four parameters: field of view (fov), aspect ratio, near z-value, and far z-value.
		 * These parameters are used to calculate the appropriate values for the matrix elements.
		 * 	- The `initPerspective` method of the `Matrix4f` class is used to initialize the
		 * matrix with the provided projection parameters.
		 * 
		 * Overall, the `getAsMatrix4` function returns a matrix that represents a 3D perspective
		 * projection, which can be used in various applications such as computer graphics,
		 * game development, and robotics.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes mouse and keyboard input, rotating the object based on sensitivity and
	 * speed, and moving it based on input keys.
	 * 
	 * @param dt time step passed since the last call to the function, and is used to
	 * calculate the movement of the object based on its speed.
	 * 
	 * @param speed 3D movement speed of the object being controlled, which is multiplied
	 * by the time interval `dt` to determine the total distance traveled during the frame.
	 * 
	 * @param sens sensitivity of the character's movement, which determines how much the
	 * character will move based on the amount of mouse input.
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
	 * modifies the position of an object by adding a directional vector multiplied by a
	 * scalar value to its current position.
	 * 
	 * @param dir 3D direction to move the entity in the game world, with its magnitude
	 * specified by the `amt` parameter.
	 * 
	 * 	- `dir`: A `Vector3f` object representing the direction to move in 3D space. It
	 * has three attributes: `x`, `y`, and `z`, which represent the x, y, and z components
	 * of the vector, respectively.
	 * 
	 * @param amt amount of movement along the specified direction, as described by the
	 * `dir` parameter.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
