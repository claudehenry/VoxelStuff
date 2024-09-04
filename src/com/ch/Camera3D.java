package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Implements a 3D camera system with capabilities to adjust projection matrices and
 * handle input events such as mouse rotations and keyboard movements. It extends a
 * base Camera class and uses a nested CameraStruct3D class for matrix calculations.
 * The class can be adjusted based on the current viewport size and handles transformations
 * accordingly.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a projection matrix based on input from a camera struct. It retrieves
	 * the projection matrix from the camera data and assigns it to a local variable,
	 * then returns the result. The projection matrix is stored in the `projection` variable.
	 *
	 * @param data 3D camera structure that is being used to generate the projection matrix.
	 *
	 * @returns a Matrix4f object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts a camera's projection and view matrices based on the current viewport
	 * dimensions. It sets the aspect ratio and recalculates the projection matrix. The
	 * function also attempts to calculate the view matrix, ignoring any resulting
	 * NullPointerException. Finally, it updates the OpenGL viewport.
	 *
	 * @param width 2D viewport's width and is used to update the aspect ratio of the 3D
	 * camera and then applied to the OpenGL context via `GL11.glViewport`.
	 *
	 * @param height vertical resolution of the viewport and is used to calculate the
	 * camera's aspect ratio and update the projection matrix accordingly.
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
	 * Represents a camera configuration with perspective projection settings. It
	 * encapsulates essential camera parameters for rendering 3D graphics. The class
	 * provides a method to convert its settings into a Matrix4f object.
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
		 * Initializes a perspective projection matrix with specified field-of-view (fov),
		 * aspect ratio (aspect), near plane distance (zNear), and far plane distance (zFar).
		 * It returns the initialized Matrix4f object. The perspective projection matrix is
		 * used for rendering 3D graphics in a perspective-correct manner.
		 *
		 * @returns a perspective projection matrix.
		 * A 4x4 float matrix representing perspective transformation parameters is initialized
		 * and set.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Controls camera rotation and movement based on user input. It updates the camera's
	 * rotation using mouse delta values and speed scaling, and then moves the camera
	 * forward or sideways based on keyboard key presses while keeping track of its current
	 * orientation.
	 *
	 * @param dt time interval between the current frame and the previous one, used to
	 * calculate movement amount based on speed.
	 *
	 * @param speed 3D movement speed of an object, which is scaled up to 10 times when
	 * the left shift key is pressed.
	 *
	 * @param sens 3D mouse sensitivity, affecting how far the camera rotates based on
	 * mouse movement input.
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
	 * Updates the object's position by adding a vector to its current position based on
	 * the provided direction and amount, scaling the direction by the specified amount
	 * before addition. The result is assigned back to the object's position. A transformation
	 * of the object occurs as a result.
	 *
	 * @param dir 3D direction vector used to calculate the new position of an object
	 * when moving by a specified amount along that direction.
	 *
	 * @param amt multiplier for scaling the direction vector, effectively controlling
	 * how far to move along the specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
