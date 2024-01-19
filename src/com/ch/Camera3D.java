package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

 /**
  * This function returns the projection matrix of a camera based on the given
  * `CameraStruct` data.
  * 
  * @param data The `data` input parameter of the `calculateProjectionMatrix()` function
  * is an instance of the `CameraStruct` class and contains all the necessary information
  * for calculating the projection matrix. It includes the camera's position (in the
  * world space), direction (i.e. gaze direction), up-axis (usually parallel to the
  * floor or a vertical surface) and finally the screen aspect ratio (used for clipping
  * near plane to fit on screen).
  * 
  * @returns The function calculates the projection matrix based on the given `
  * CameraStruct` data and returns a `Matrix4f` object representing the resulting
  * projection matrix.
  */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

 /**
  * This function adjusts the 3D camera's projection and view matrices to fit within
  * a 2D viewport defined by the width and height parameters. It calculates the aspect
  * ratio of the viewport and updates the projection matrix accordingly. Additionally
  * it attempts to calculate the view matrix but catches any NullPointerExceptions
  * that may occur.
  * 
  * @param width The `width` input parameter specifies the width of the viewport that
  * the camera should be adjusted to fit within.
  * 
  * @param height The `height` input parameter represents the height of the viewport.
  * It is used to adjust the aspect ratio of the camera's view frustum to match the
  * height of the viewport.
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

	protected class CameraStruct3D extends CameraStruct {

		public float fov, aspect, zNear, zFar;

		public CameraStruct3D(float fov, float aspect, float zNear, float zFar) {
			this.fov = fov;
			this.aspect = aspect;
			this.zNear = zNear;
			this.zFar = zFar;
		}

  /**
   * This function creates a new `Matrix4f` object and initializes it with a perspective
   * transformation based on the given fields of view (fov), aspect ratio (aspect),
   * near (zNear) and far (zFar) z-values.
   * 
   * @returns The output of the given function `getAsMatrix4()` is a new `Matrix4f`
   * object initialized with a perspective projection matrix that represents a frustum
   * with the specified field of view (fov), aspect ratio (aspect), near (zNear) and
   * far (zFar) z-coordinates.
   */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

 /**
  * This function processes user input (mouse movement and keyboard keys) to control
  * the movement of a 3D object. It adjusts the object's rotation and position based
  * on the input speed and sensitivity values provided as arguments. The function
  * rotates the object to face the mouse cursor direction and moves the object
  * forward/backward or left/right based on key presses.
  * 
  * @param dt The `dt` input parameter represents the "delta time" (i.e., the time
  * difference between the current frame and the previous frame) and is used to calculate
  * the speed at which the player moves based on the mouse input. It affects the
  * `movAmt` variable and ultimately determines how far the player moves per frame.
  * 
  * @param speed The `speed` input parameter controls the magnitude of the player's
  * movement. When the user presses the shift key (Keyboard.KEY_LSHIFT), the speed of
  * the player's movement increases by a factor of 10.
  * 
  * @param sens The `sens` input parameter determines the sensitivity of the user
  * input. It multiplies the input device's reading (e.g., mouse dx and dy) by the
  * given value before applying rotations and movements. A higher sens value will
  * resultin more pronounced movement/rotation for the same amount of input device movement.
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
  * This function takes a `dir` parameter representing a direction vector and an `amt`
  * parameter representing a distance to move. It then sets the position of the entity
  * to its current position plus the product of the direction vector and the distance
  * moved.
  * 
  * @param dir The `dir` parameter is a vector that determines the direction of movement.
  * It is multiplied by the `amt` parameter to determine the distance moved.
  * 
  * @param amt The `amt` input parameter represents a "multiplier" that scales the
  * movement of the object based on the direction given by `dir`.
  */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
