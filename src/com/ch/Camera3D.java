package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera Class that provides additional functionality for 3D
 * cameras. It takes in fov, aspect, zNear, and zFar parameters to calculate the
 * projection matrix, and it has a protected class called CameraStruct3D that contains
 * the camera's position, rotation, and field of view. The processInput method processes
 * keyboard input and moves the camera accordingly.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a projection matrix based on provided camera data.
	 * 
	 * @param data 3D camera's parameters, which are used to create the projection matrix.
	 * 
	 * 1/ `projection`: This is an instance of `Matrix4f`. It contains the projection
	 * matrix, which defines how the 3D scene will be projected onto a 2D image plane.
	 * 2/ `getAsMatrix4()`: This method returns the projection matrix as a `Matrix4f` object.
	 * 
	 * Therefore, the function calculates and returns the projection matrix by calling
	 * the `getAsMatrix4()` method on the input `data` object.
	 * 
	 * @returns a Matrix4f object containing the projection matrix.
	 * 
	 * 	- The `Matrix4f` object represents a 4x4 homogenous transformation matrix that
	 * maps 3D space into a 2D image plane.
	 * 	- The matrix elements define the projection from a 3D coordinate (x, y, z) to a
	 * 2D screen coordinate (x, y).
	 * 	- The matrix is inhomogeneous, meaning it has an additional element (w) that
	 * represents the scale factor of the projection.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's projection and view matrices to fit within the specified width
	 * and height of the viewport, while maintaining aspect ratio.
	 * 
	 * @param width 2D viewport width in pixels and is used to update the aspect ratio
	 * of the camera's projection matrix.
	 * 
	 * @param height 2D image display size, which is used to calculate and set the view
	 * matrix and viewport size.
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
	 * is a subclass of CameraStruct that contains additional fields for fov, aspect,
	 * zNear, and zFar, which are used to calculate the perspective projection matrix in
	 * the calculateProjectionMatrix method. The getAsMatrix4 method returns a Matrix4f
	 * object initialized with a perspective projection matrix.
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
		 * initializes a `Matrix4f` instance with a perspective projection, using the provided
		 * fields as parameters for the `initPerspective` method.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection.
		 * 
		 * 1/ The `Matrix4f` object returned is an instance of the `Matrix4f` class in Java,
		 * which represents a 4x4 homogeneous matrix.
		 * 2/ The matrix is initialized with the `initPerspective` method of the `Matrix4f`
		 * class, which sets the values of the matrix based on the provided fov, aspect,
		 * zNear, and zFar parameters. Specifically, the matrix is set to a perspective
		 * projection matrix with the specified field of view (fov), aspect ratio (aspect),
		 * near plane (zNear), and far plane (zFar).
		 * 3/ The resulting matrix represents a 4x4 transformation matrix that can be used
		 * to transform 3D points in homogeneous coordinates into camera space, based on the
		 * perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes input from the mouse and keyboard, applying rotations and movements to
	 * an object based on sensor input and modifiers.
	 * 
	 * @param dt time step or elapsed time since the last frame, which is used to calculate
	 * the movement of the object based on the keyboard inputs received during that time.
	 * 
	 * @param speed 3D movement speed of the game object, which is multiplied by the time
	 * differential `dt` to determine the total distance traveled during each frame.
	 * 
	 * @param sens sensitivity of the object's movement in response to user input, which
	 * affects how quickly the object moves in response to user input.
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
	 * moves an object by a specified distance and direction using the transform's
	 * `setPos()` method. The function takes two parameters: `dir` representing the
	 * direction of movement, and `amt` representing the magnitude of the movement.
	 * 
	 * @param dir 3D direction in which the entity should move, with the magnitude of the
	 * movement specified by the `amt` parameter.
	 * 
	 * 	- `dir` is a `Vector3f` data type that represents a 3D direction vector.
	 * 	- `mul()` method is used to multiply the `dir` vector by a scalar value `amt`,
	 * which represents the magnitude or amount of movement along the specified direction.
	 * 
	 * @param amt amount of movement to be applied to the position of the object being
	 * manipulated, which is calculated by multiplying it with the direction vector
	 * provided as input.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
