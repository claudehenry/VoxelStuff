package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Extends an existing Camera class and provides functionality for 3D camera manipulation,
 * including projection matrix calculation, viewport adjustment, and input processing.
 * It also includes features for camera rotation and movement based on user input.
 * The class appears to be designed for use in a game or graphics application.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Retrieves a projection matrix from a provided `CameraStruct` object and assigns
	 * it to the variable `projection`. It then returns the retrieved matrix, effectively
	 * passing through the original value without modification. The result is stored in
	 * the calling context.
	 *
	 * @param data 3D camera data that is converted to and returned as a Matrix4f object,
	 * which holds the projection matrix information.
	 *
	 * @returns a matrix representing the camera's projection.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates a 3D camera's aspect ratio and projection matrix based on the current
	 * viewport dimensions. It also attempts to update the view matrix but catches any
	 * resulting NullPointerExceptions. The GL11 viewport is then updated with the new dimensions.
	 *
	 * @param width 2D viewport's width and is used to calculate the aspect ratio for the
	 * camera projection matrix and to specify the viewport area on the screen.
	 *
	 * @param height height of the viewport and is used to calculate the aspect ratio for
	 * projection matrix calculation.
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
	 * Encapsulates camera settings and projection matrix calculation in a structured
	 * format. It extends the CameraStruct class to include specific properties for a 3D
	 * camera. The class facilitates the creation of a projection matrix based on these
	 * settings.
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
		 * Returns a new instance of `Matrix4f` initialized with perspective matrix values
		 * based on field of view (`fov`), aspect ratio (`aspect`), near clipping plane
		 * (`zNear`), and far clipping plane (`zFar`).
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Rotates and translates an object based on mouse movement and keyboard input. It
	 * scales speed when left shift is pressed, and moves the object forward, backward,
	 * left, or right based on WASD keys pressed. The rotation and translation are
	 * calculated using the object's current rotation and direction.
	 *
	 * @param dt 3D rendering time step, used to calculate movement amounts based on game
	 * speed and delta time.
	 *
	 * @param speed movement speed of an object, which is multiplied by the time difference
	 * (`dt`) to determine the actual movement amount.
	 *
	 * @param sens sensitivity of mouse rotation, used to calculate the rotation amount
	 * based on the horizontal mouse movement.
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
	 * Translates an object by multiplying a specified direction vector by a given amount
	 * and adding the result to its current position, updating its transformation
	 * accordingly. The translation is performed in three-dimensional space using a
	 * `Vector3f` type. The new position reflects the movement of the object.
	 *
	 * @param dir 3D direction vector used to calculate the new position of an object by
	 * scaling its magnitude with the amount specified by the `amt` parameter and adding
	 * it to the current position.
	 *
	 * @param amt amount by which to multiply the direction vector, determining the
	 * magnitude of movement along that direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
