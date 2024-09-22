package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Implements a 3D camera system that handles projection matrix calculation and view
 * adjustment based on the viewport size. It also manages camera movement through
 * keyboard input and mouse rotation. The class extends an existing Camera class,
 * inheriting some functionality while adding specific features for 3-dimensional rendering.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Retrieves a projection matrix from a camera struct and assigns it to a variable
	 * named 'projection'. It then returns the assigned projection matrix. The function
	 * appears to be used for setting up a perspective or orthogonal projection in a 3D
	 * graphics context.
	 *
	 * @param data 3D camera structure that contains matrix data, which is extracted and
	 * assigned to the `projection` variable within the function.
	 *
	 * @returns a Matrix4f representation of the camera's projection.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts camera settings to match a specified viewport size and aspect ratio. It
	 * calculates the projection matrix and view matrix based on these settings, then
	 * updates the OpenGL viewport. The adjustment is done by setting the aspect ratio,
	 * recalculating matrices, and updating GL11.
	 *
	 * @param width 2D viewport's horizontal resolution and is used to set the camera's
	 * aspect ratio and update the GL11 viewport.
	 *
	 * @param height vertical dimension of the viewport and is used to calculate the
	 * aspect ratio for projection matrix calculation.
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
	 * Encapsulates camera-specific data and utility functions for creating a projection
	 * matrix based on this data. It extends the CameraStruct class to include additional
	 * functionality. The class facilitates the creation of a perspective projection
	 * matrix from its properties.
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
		 * Initializes and returns a perspective matrix based on specified parameters: field
		 * of view (`fov`), aspect ratio (`aspect`), near clipping plane (`zNear`), and far
		 * clipping plane (`zFar`). The resulting matrix is created from scratch each time
		 * the function is called.
		 *
		 * @returns a 4x4 perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes keyboard and mouse input to update game camera movement and rotation.
	 * It scales speed when left shift key is pressed, rotates the camera based on mouse
	 * movement, and translates the camera based on arrow key presses. The `dt` parameter
	 * represents time since last frame.
	 *
	 * @param dt time difference since the last frame, used to scale movement speed by
	 * this time interval.
	 *
	 * @param speed 3D movement speed of an object, which can be modified by holding down
	 * the left shift key to increase it tenfold.
	 *
	 * @param sens sensitivity of mouse rotation, where higher values result in faster
	 * rotation and lower values result in slower rotation.
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
	 * Updates the position of an object by a specified amount in a given direction. It
	 * multiplies a vector representing the direction by an amount to calculate the
	 * displacement, then adds it to the current position. The result is a new position
	 * that replaces the old one.
	 *
	 * @param dir 3D direction vector that determines the movement path of the object,
	 * scaled by the `amt` factor to set its magnitude.
	 *
	 * @param amt amount to multiply the direction vector by, scaling its magnitude and
	 * determining the distance moved along that direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
