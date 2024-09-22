package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents a 3D camera that can be manipulated using user input and calculates
 * projection matrices based on its field of view and aspect ratio. It handles mouse
 * movement to rotate the camera and keyboard input to move it in different directions.
 * The class adjusts its projection matrix when the viewport size changes.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix from the provided camera data, where the matrix is
	 * retrieved using `data.getAsMatrix4()` and assigned to the local variable `projection`.
	 * The function's result is then returned as the projection matrix.
	 *
	 * @param data 3D camera data, from which its internal state is extracted as a 4x4
	 * matrix and assigned to a local variable `projection`.
	 *
	 * @returns a matrix representing the camera's projection.
	 * It returns a Matrix4f object. The result is assigned to the 'projection' variable.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts camera settings and rendering context to match a specified viewport size.
	 * It recalculates aspect ratio, projection matrix, and view matrix based on the new
	 * dimensions. The function then updates the OpenGL viewport with the given width and
	 * height.
	 *
	 * @param width 2D viewport's width and is used to calculate the aspect ratio for
	 * projection matrix calculation.
	 *
	 * @param height vertical size of the viewport and is used to calculate the aspect
	 * ratio for the camera's projection matrix.
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
	 * Represents a data structure to store camera-specific settings and provides
	 * functionality to create a projection matrix based on these settings. It extends
	 * the CameraStruct class. The class is designed for use in 3D graphics rendering.
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
		 * Returns a new instance of a perspective matrix. This matrix is initialized with
		 * given field-of-view angle (`fov`), aspect ratio (`aspect`), near clipping plane
		 * distance (`zNear`), and far clipping plane distance (`zFar`).
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the object's rotation and movement based on user input from mouse and keyboard.
	 * It rotates the object according to the mouse delta, speed, and sensitivity values.
	 * It also moves the object forward, backward, left, or right based on keyboard key
	 * presses.
	 *
	 * @param dt time elapsed since the last update, used to calculate the movement amount
	 * based on the `speed` and `sens` parameters.
	 *
	 * @param speed 3D movement speed of an object, which can be adjusted based on the
	 * state of the left shift key.
	 *
	 * @param sens sensitivity of mouse movements, which affects the rotation speed of
	 * the object.
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
	 * Modifies an object's position by adding a specified amount to its current position
	 * based on a given direction vector and multiplier. The new position is calculated
	 * by scaling the direction vector, then translating it from the current position.
	 * This updates the object's global transform.
	 *
	 * @param dir 3D direction vector used to calculate the new position of the object
	 * being moved.
	 *
	 * @param amt scale factor for multiplying with the direction vector to determine the
	 * movement distance.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
