package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents a 3D camera object that can be adjusted to viewports and respond to
 * user input. It maintains its own projection matrix and transform, allowing it to
 * project 3D scenes onto the screen. The class also handles user movement using
 * keyboard and mouse input.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix from a CameraStruct object, storing it as the `projection`
	 * variable and returning its value.
	 *
	 * @param data 3D camera configuration and returns its projection matrix, which is
	 * assigned to the `projection` variable and returned by the method.
	 *
	 * @returns a Matrix4f object representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's aspect ratio and projection matrix when the viewport dimensions
	 * change. It attempts to calculate the view matrix but catches any resulting
	 * NullPointerExceptions. The updated view and projection matrices are then applied
	 * to OpenGL using the `GL11.glViewport` method.
	 *
	 * @param width 2D viewport's width and is used to set the camera's aspect ratio and
	 * update the projection matrix accordingly.
	 *
	 * @param height vertical dimension of the viewport and is used to calculate the
	 * aspect ratio.
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
	 * Encapsulates camera projection settings and provides functionality to convert these
	 * settings into a Matrix4f object for use in rendering. It extends the base CameraStruct
	 * class with additional methods specific to 3D projections. The class is designed
	 * to be instantiated and used within the Camera3D context.
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
		 * Creates a new perspective projection matrix based on specified parameters, including
		 * field of view (`fov`), aspect ratio (`aspect`), near clipping plane (`zNear`), and
		 * far clipping plane (`zFar`). The function initializes the new matrix with this
		 * projection. A Matrix4f object is returned.
		 *
		 * @returns a perspective matrix.
		 * This matrix represents an orthographic projection.
		 * Its dimensions are 4x4.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the object's rotation and translation based on user input from mouse and
	 * keyboard. It applies camera rotation to follow mouse movements, adjusts speed with
	 * shift key press, and moves the object forward/backward/left/right based on WASD
	 * key presses.
	 *
	 * @param dt time elapsed since the last frame update, used to calculate movement
	 * amounts based on speed and frame rate.
	 *
	 * @param speed 3D translation speed, which is used to calculate the movement amount
	 * (`movAmt`) based on the elapsed time (`dt`).
	 *
	 * @param sens sensitivity of mouse rotation, with higher values resulting in faster
	 * rotation speed.
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
	 * Updates the position of an object by adding a specified amount to its current
	 * position, with the direction and magnitude determined by the input parameters. It
	 * utilizes vector multiplication for scaling and translation. The resulting new
	 * position is set on the object's transform component.
	 *
	 * @param dir 3D direction vector that specifies the movement axis, which is multiplied
	 * by the amount of movement (`amt`) to calculate the new position.
	 *
	 * @param amt amount of movement in the specified direction, used to scale the vector
	 * multiplication result before adding it to the object's position.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
