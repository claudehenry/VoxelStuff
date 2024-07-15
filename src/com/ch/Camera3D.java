package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an extension of the Camera class that adds additional functionality for
 * manipulating a camera's position and view in a 3D space. The class has several
 * methods for calculating projection and view matrices, as well as handling user
 * input for rotating and moving the camera.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a matrix representation of the camera's projection, based on the given
	 * `CameraStruct` data.
	 * 
	 * @param data 3D camera parameters as a `CameraStruct` object, which is used to
	 * create the projection matrix.
	 * 
	 * @returns a Matrix4f object representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates a 3D scene's projection and view matrices based on the window size, aspect
	 * ratio, and original values stored in the `CameraStruct3D`.
	 * 
	 * @param width 2D viewport width for which the camera's projection matrix is calculated
	 * and displayed.
	 * 
	 * @param height 2D viewport size of the canvas on which the 3D scene is rendered.
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
	 * Is a customized implementation of a camera class that includes additional parameters
	 * for the field of view (fov), aspect ratio (aspect), near clipping plane (zNear),
	 * and far clipping plane (zFar). The class provides methods for calculating the
	 * perspective projection matrix and the view matrix, as well as handling input from
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
		 * Initializes a matrix representation of a 3D perspective projection, setting its
		 * field of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes input from the mouse and keyboard, applying rotations and translations
	 * to an object based on user inputs. It also scales the movement speed based on the
	 * LShift key.
	 * 
	 * @param dt time step used to update the object's position and rotation during
	 * animation, with a value of `dt` determining how much the object moves or rotates
	 * within the frame.
	 * 
	 * @param speed 3D movement speed of the object being controlled by the player, and
	 * it is multiplied by the time interval `dt` to determine the total distance traveled
	 * during the frame.
	 * 
	 * @param sens sensitivity of the object's rotation in response to mouse movements,
	 * which affects the amount of rotation applied to the object's transform.
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
	 * Updates the position of an object by adding a directional vector multiplied by a
	 * scalar value to its current position.
	 * 
	 * @param dir 3D direction to move the object in the game world.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the transform.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
