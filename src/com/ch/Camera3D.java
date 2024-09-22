package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is designed to manage camera movement and projection in a 3D environment. It extends
 * the Camera class and includes features for adjusting the camera's perspective
 * matrix based on the viewport dimensions and processing user input from keyboard
 * and mouse. The class allows for smooth movement and rotation of the camera.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a projection matrix based on input from a camera and assigns it to a
	 * field named `projection`. The input is obtained by calling a method named
	 * `getAsMatrix4` on a `CameraStruct` object, which presumably contains the necessary
	 * data for calculation.
	 *
	 * @param data 3D camera settings from which a projection matrix is extracted and
	 * assigned to the local variable `projection`.
	 *
	 * @returns a matrix of type `Matrix4f`.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts camera settings based on viewport dimensions. It updates the aspect ratio
	 * and calculates new projection and view matrices accordingly. The viewport is then
	 * updated using OpenGL to reflect these changes.
	 *
	 * @param width 2D viewport's width and is used to update the camera's aspect ratio
	 * and to set the new viewport dimensions via OpenGL.
	 *
	 * @param height vertical dimension of the viewport, used to calculate the aspect
	 * ratio and set the viewport boundaries.
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
	 * Encapsulates parameters for a 3D perspective projection matrix. It provides an
	 * initialization constructor and a method to retrieve this data as a Matrix4f object.
	 * The class extends the CameraStruct class.
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
		 * Returns a new perspective matrix. It initializes a new `Matrix4f` object and sets
		 * its values using the `initPerspective` method with specified field-of-view, aspect
		 * ratio, near clipping plane, and far clipping plane parameters. The resulting matrix
		 * is returned by the function.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes input from mouse and keyboard to control the movement of an object. It
	 * calculates rotation based on mouse movement, adjusts speed with keyboard shift
	 * key, and moves the object forward/backward/left/right based on WASD keys.
	 *
	 * @param dt time delta, which is used to calculate the movement amount based on the
	 * speed and time elapsed since the last frame.
	 *
	 * @param speed 3D movement speed of an object and is used to calculate the movement
	 * amount based on the elapsed time delta (`dt`).
	 *
	 * @param sens sensitivity of mouse rotation, where higher values increase the rate
	 * of rotation.
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
	 * Updates an object's position by adding a vector multiplied by a scalar to its
	 * current position. It takes a direction and amount as input, modifying the object's
	 * transform accordingly. The result is a new position based on the original position
	 * plus the scaled direction vector.
	 *
	 * @param dir 3D direction vector indicating the axis and magnitude of movement.
	 *
	 * @param amt amount by which the direction vector is scaled to determine the
	 * displacement of the position.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
