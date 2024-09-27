package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Handles camera movement and projection matrix calculation in 3D space.
 * It adjusts the camera's projection matrix based on the viewport dimensions and
 * updates the camera's position and rotation based on user input.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates and returns a projection matrix based on a camera's data. The projection
	 * matrix is retrieved from the camera's data and stored in the `projection` variable.
	 * The result is then returned.
	 *
	 * @param data camera data that is converted into a matrix.
	 *
	 * @returns a 4x4 projection matrix based on the input camera data.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adapts the camera's aspect ratio and projection matrix to the current viewport
	 * dimensions, updating the camera's aspect ratio and recalculating the projection
	 * and view matrices, while also setting the OpenGL viewport to the specified dimensions.
	 *
	 * @param width new viewport width.
	 *
	 * @param height height of the viewport, used to calculate the aspect ratio.
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
	 * Represents camera projection parameters in a 3D environment.
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
		 * Returns a new `Matrix4f` object initialized with a perspective matrix based on the
		 * specified field of view (`fov`), aspect ratio (`aspect`), near clipping plane
		 * (`zNear`), and far clipping plane (`zFar`).
		 *
		 * @returns a 4x4 perspective projection matrix initialized with specified parameters.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Rotates the view based on mouse movement and adjusts speed with the left shift
	 * key. It then moves the object forward, backward, left, or right based on keyboard
	 * input and a delta time value.
	 *
	 * @param dt delta time, used to scale the `speed` parameter and calculate the `movAmt`
	 * to ensure smooth movement regardless of the frame rate.
	 *
	 * @param speed base movement speed of the object.
	 *
	 * @param sens sensitivity of the mouse controls, affecting the rotation speed.
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
	 * Translates an object by a specified amount in a given direction. It multiplies the
	 * direction vector by the translation amount, then adds the result to the object's
	 * current position. The new position is set for the object.
	 *
	 * @param dir direction in which the object is moved.
	 *
	 * @param amt amount by which the position is modified.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
