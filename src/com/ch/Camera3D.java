package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Provides camera functionality for 3D rendering. It calculates and updates projection
 * and view matrices based on input from the user and adjusts to the viewport
 * accordingly. The class also handles camera movement in response to keyboard and
 * mouse inputs.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a matrix representing a camera's projection, retrieved from a provided
	 * `CameraStruct` object and stored in the `projection` variable. The function does
	 * not perform any calculations, but instead assigns the result directly from the
	 * input data.
	 *
	 * @param data 3D camera object from which the method retrieves and assigns the
	 * projection matrix to the `projection` variable.
	 *
	 * @returns a Matrix4f object, specifically the projection matrix from the input CameraStruct.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts camera settings based on viewport dimensions. It updates aspect ratio and
	 * calculates projection and view matrices. If a null pointer exception occurs during
	 * view matrix calculation, it is caught and ignored. The viewport is then set using
	 * OpenGL.
	 *
	 * @param width 2D viewport width to be set for the OpenGL context, which is used to
	 * update the aspect ratio and recalculate the projection matrix accordingly.
	 *
	 * @param height 2D viewport's height, which is used to calculate the aspect ratio
	 * and update the projection matrix accordingly.
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
	 * Represents a struct-like data structure for storing camera-related settings and
	 * returns a perspective projection matrix based on these settings. It is used to
	 * initialize and update the camera's view and projection matrices. The class extends
	 * the base CameraStruct class and provides specific functionality for 3D cameras.
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
		 * Initializes a perspective projection matrix for rendering 3D graphics. It takes
		 * four parameters: field-of-view (fov), aspect ratio, near clipping plane distance
		 * (zNear), and far clipping plane distance (zFar). The function returns the initialized
		 * Matrix4f object.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates camera rotation based on mouse input and speed, and translates the camera
	 * position based on keyboard input. It adjusts camera movement speed when left shift
	 * key is pressed and applies the movement to the camera transform.
	 *
	 * @param dt elapsed time since the last frame, used to scale the movement amount
	 * based on the speed and time passed.
	 *
	 * @param speed 3D movement speed of an object, which is adjusted to be faster when
	 * the left shift key is pressed and used as a factor in calculating the movement amount.
	 *
	 * @param sens sensitivity of mouse rotation, used to calculate the rotation angle
	 * based on mouse movement.
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
	 * Translates an object by a specified amount along a given direction vector. It
	 * multiplies the direction vector by the amount and adds the result to the current
	 * position, updating the object's transform accordingly.
	 *
	 * @param dir 3D vector direction in which movement is to be applied, scaled by the
	 * specified amount (`amt`).
	 *
	 * @param amt scalar amount to multiply the direction vector by, affecting the distance
	 * of movement.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
