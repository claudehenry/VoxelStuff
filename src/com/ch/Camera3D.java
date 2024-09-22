package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Handles 3D camera functionality, encompassing projection matrix calculation and
 * adjustment to viewport size. It processes user input from mouse and keyboard events
 * for rotation and movement control. The class extends the base Camera class with
 * specific features for 3D rendering.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix from the camera's data. The function retrieves the
	 * matrix representation of the camera's structure and assigns it to the `projection`
	 * variable before returning it. The returned matrix is likely used for 3D rendering
	 * or transformations.
	 *
	 * @param data 3D camera settings, which are used to generate the projection matrix.
	 *
	 * @returns a projected matrix, specifically a `Matrix4f`.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates camera aspect ratio and projection matrix when viewport dimensions change.
	 * It attempts to recalculate view matrix but catches and ignores any NullPointerExceptions
	 * that may occur. Finally, it sets the OpenGL viewport dimensions to match the new
	 * width and height.
	 *
	 * @param width 2D viewport's horizontal resolution that is used to set the aspect
	 * ratio for projection and view matrix calculations as well as specify the GL11
	 * glViewport parameters.
	 *
	 * @param height height of the viewport, which is used to calculate the aspect ratio
	 * and adjust various matrices accordingly.
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
	 * Represents a camera configuration with specific attributes for perspective projection
	 * and is used to generate a matrix representing this configuration. It encapsulates
	 * data required for perspective projection calculations. This class extends the
	 * CameraStruct class, inheriting its basic functionality.
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
		 * Initializes and returns a perspective projection matrix based on field-of-view
		 * (fov), aspect ratio, near clip plane (zNear), and far clip plane (zFar) values.
		 * It creates and configures a new Matrix4f object to perform the projection. The
		 * resulting matrix is returned.
		 *
		 * @returns a perspective matrix.
		 * This matrix defines a camera's view volume and projection.
		 * It uses FOV, aspect ratio, near plane, and far plane values.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the object's rotation based on mouse movement and keyboard input, applying
	 * a speed modifier when the left shift key is pressed. It also translates the object
	 * along its forward direction based on user input from W, S, A, and D keys.
	 *
	 * @param dt time delta, used to calculate movement distance based on speed and time
	 * elapsed since last frame update.
	 *
	 * @param speed speed at which an object moves, and its value is used to determine
	 * the amount of movement when pressing movement keys.
	 *
	 * @param sens sensitivity of mouse movement, used to calculate rotation angles based
	 * on mouse delta values.
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
	 * Updates an object's position by adding a scaled vector to its current position.
	 * The scaling factor is determined by multiplying the input direction vector by a
	 * specified amount. This adjusts the object's location accordingly.
	 *
	 * @param dir 3D direction vector that determines the movement of an object, multiplied
	 * by an amount specified by the `amt` parameter to calculate the new position.
	 *
	 * @param amt scalar factor that multiplies the direction vector to determine the
	 * amount of movement along each axis.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
