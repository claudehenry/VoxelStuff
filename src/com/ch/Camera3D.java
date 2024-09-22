package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents a 3D camera with features for perspective projection and input processing.
 * It uses LWJGL libraries to handle mouse and keyboard events. The class provides
 * functionality for adjusting the view matrix based on screen resolution and user input.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix based on input from a camera struct. The function
	 * retrieves a matrix from the camera data and assigns it to a local variable. The
	 * retrieved matrix is then returned as the result.
	 *
	 * @param data 4x4 matrix of a CameraStruct object, from which the projection matrix
	 * is retrieved.
	 *
	 * @returns a matrix of type `Matrix4f`.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's aspect ratio and projection matrix based on the provided
	 * viewport dimensions. It also attempts to calculate the view matrix and resets the
	 * viewport using OpenGL. A NullPointerException is caught without handling.
	 *
	 * @param width 2D viewport's width and is used to update the aspect ratio and viewport
	 * settings within the function.
	 *
	 * @param height height of the viewport and is used to calculate the aspect ratio for
	 * setting the camera's projection matrix.
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
	 * Represents camera settings for a 3D perspective projection, encapsulating essential
	 * parameters for rendering. It extends CameraStruct and provides functionality to
	 * generate a Matrix4f object from these settings. The class is used to create camera
	 * matrices in a game or graphics application.
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
		 * Initializes and returns a perspective projection matrix based on specified fields
		 * of view, aspect ratio, near, and far clip planes. It creates a new instance of
		 * `Matrix4f`, sets its perspective using the provided parameters, and returns it.
		 * The resulting matrix represents a projection for 3D space.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the camera's rotation and position based on user input from mouse and
	 * keyboard. It handles camera movement, rotation, and speed adjustments using LSHIFT
	 * key. The function uses delta time (dt) to adjust movement amount accordingly.
	 *
	 * @param dt time delta, which is used to calculate the movement amount based on the
	 * speed and delta time.
	 *
	 * @param speed 3D movement speed and affects the distance the object travels when a
	 * key is pressed, with the left shift key increasing the speed by a factor of ten.
	 *
	 * @param sens 3D mouse sensitivity factor, used to calculate rotational angles based
	 * on mouse movement deltas.
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
	 * Adjusts the position of a transform based on a direction vector and an amount. It
	 * multiplies the direction by the amount, then adds the result to the current position.
	 * The new position is set as the current position for the transform.
	 *
	 * @param dir 3D direction vector specifying the axis and magnitude of movement to
	 * be applied to the object's position.
	 *
	 * @param amt 3D distance to move along the specified direction vector, where larger
	 * values result in greater displacement.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
