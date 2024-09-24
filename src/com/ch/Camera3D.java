package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Manages a 3D camera with perspective projection and rotation based on user input.
 * It uses LWJGL for handling keyboard and mouse events to control the camera's
 * position and orientation.
 * Adjustments are made to the viewport size to maintain proper rendering aspects.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a matrix for projecting 3D objects onto a 2D surface. It retrieves a
	 * projection matrix from a provided camera structure and assigns it to a local
	 * variable before returning it. The resulting matrix is used to transform vertices
	 * into screen space.
	 *
	 * @param data 3D camera data, which is retrieved as a Matrix4f and assigned to the
	 * projection variable.
	 *
	 * @returns a `Matrix4f` object representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts a camera's projection and view matrices based on the current viewport
	 * dimensions. It updates the camera's aspect ratio and recalculates its projection
	 * matrix accordingly. The view matrix is also recalculated, ignoring any potential
	 * null pointer exception.
	 *
	 * @param width 2D viewport's width and is used to set the camera's aspect ratio and
	 * update the projection matrix.
	 *
	 * @param height viewport's vertical dimension that is used to calculate the aspect
	 * ratio and subsequently update the projection matrix.
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
	 * Encapsulates camera-specific data and provides a method to obtain a projection
	 * matrix based on this data. It is designed to store parameters for a 3D perspective
	 * projection.
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
		 * Returns a perspective projection matrix. It initializes a new instance of `Matrix4f`
		 * with the given field of view (fov), aspect ratio (aspect), near clipping plane
		 * (zNear), and far clipping plane (zFar). The resulting matrix is used for 3D
		 * projection transformations.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates an object's rotation and movement based on user input from mouse and
	 * keyboard. It calculates rotations based on mouse delta and sensitivity, then moves
	 * the object forward, backward, left, or right based on key presses while scaling
	 * speed by a factor when LShift is pressed.
	 *
	 * @param dt time delta between two consecutive frames, used to calculate the movement
	 * amount for the game object.
	 *
	 * @param speed 3D movement speed of the entity, which is adjusted based on shift key
	 * status and used to calculate the movement amount for each frame.
	 *
	 * @param sens sensitivity of mouse rotation, affecting the amount of camera rotation
	 * per pixel movement.
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
	 * Moves an object a specified amount along a given direction. It multiplies the
	 * direction vector by the movement amount, then adds this result to the current
	 * position, updating it accordingly. The new position is set on the object's transform.
	 *
	 * @param dir 3D direction vector that determines the movement of the object, with
	 * its magnitude being scaled by the specified amount.
	 *
	 * @param amt scalar amount to multiply the direction vector by, effectively scaling
	 * the movement distance.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
