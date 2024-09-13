package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Implements a camera system for 3D graphics, handling projection and viewport
 * adjustments as well as user input to control the camera's position and rotation.
 * It extends a parent Camera class and utilizes a CameraStruct3D data structure to
 * store projection settings. The class processes mouse and keyboard events to update
 * the camera's transformation matrix.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Retrieves a projection matrix from a camera data structure and assigns it to a
	 * variable named `projection`, then returns that matrix as a result. The retrieved
	 * matrix is presumably a 4x4 transformation matrix representing the camera's projection
	 * parameters. It is assigned directly.
	 *
	 * @param data 3D camera data to be converted into a matrix for projection calculations.
	 *
	 * @returns a Matrix4f object representing a camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the aspect ratio and recalculates projection and view matrices when the
	 * viewport size changes. It catches a potential `NullPointerException` during view
	 * matrix calculation and suppresses it. The new viewport is then set using OpenGL commands.
	 *
	 * @param width 2D viewport width and is used to calculate the aspect ratio of the
	 * camera and update the projection matrix.
	 *
	 * @param height height of the viewport and is used to calculate the aspect ratio for
	 * adjusting camera projection.
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
	 * Is a container for camera settings and projection data. It encapsulates key values
	 * used in 3D rendering such as field of view, aspect ratio, near plane distance, and
	 * far plane distance. The class provides a method to retrieve the camera's projection
	 * matrix.
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
		 * Initializes a perspective matrix from given parameters and returns it as a `Matrix4f`
		 * object. The parameters are field of view (fov), aspect ratio (aspect), near clipping
		 * plane distance (zNear), and far clipping plane distance (zFar). It uses the
		 * `initPerspective` method to create the perspective matrix.
		 *
		 * @returns a perspective projection matrix with specified parameters.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates rotation and movement based on user input, including mouse sensitivity and
	 * keyboard controls for translation. Key presses on 'W' and 'S' keys move the object
	 * forward or backward, while key presses on 'A' and 'D' keys move it left or right.
	 *
	 * @param dt time elapsed since the last frame update, used to scale movement speed
	 * calculations.
	 *
	 * @param speed 3D movement speed of an object, which is multiplied by the elapsed
	 * time (`dt`) to determine the actual movement distance (`movAmt`).
	 *
	 * @param sens sensitivity of mouse rotation, and is used to calculate the rotation
	 * amount in radians based on the mouse movement.
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
	 * Updates an object's position by adding a scaled direction vector to its current
	 * position, where the scaling factor is determined by the specified amount.
	 * The result of the multiplication operation is added to the object's current position.
	 * The new position is then set as the object's updated position.
	 *
	 * @param dir 3D direction vector to which the amount specified by `amt` will be
	 * multiplied and added to the object's current position.
	 *
	 * @param amt scalar amount by which to multiply the vector direction, controlling
	 * the distance moved along that direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
