package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Implements a 3D camera system with features for projection matrix calculation and
 * adjustment to viewport dimensions. It also handles user input for rotation and
 * movement, utilizing the Lwjgl library for keyboard and mouse functionality. The
 * class extends an existing "Camera" class and utilizes an internal struct for storing
 * camera parameters.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Retrieves a matrix from a camera structure and assigns it to a projection variable,
	 * returning the assigned value as a result. This function appears to be a straightforward
	 * data accessor with minimal transformation or computation. The assignment operation
	 * is chained with the return statement.
	 *
	 * @param data 3D camera data that is used to calculate and return its projection
	 * matrix as a Matrix4f object.
	 *
	 * @returns a projection matrix stored in the `projection` variable.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates aspect ratio and projection matrix based on viewport dimensions. It attempts
	 * to calculate view matrix but catches and ignores any `NullPointerException`.
	 * Finally, it sets the OpenGL viewport to match the provided width and height.
	 *
	 * @param width 2D viewport's width and is used to calculate the aspect ratio for
	 * projection matrix calculation and set the GL11 viewport accordingly.
	 *
	 * @param height vertical dimension of the viewport and is used to calculate the
	 * aspect ratio for the projection matrix.
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
	 * Represents camera projection data, storing settings for field of view, aspect
	 * ratio, near and far clipping planes. It encapsulates these values into an object
	 * that can be used to initialize a projection matrix. The class provides functionality
	 * to convert its data into a Matrix4f representation.
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
		 * aspect ratio (aspect), and near/far clipping planes (zNear and zFar). It returns
		 * an instance of a 4x4 transformation matrix represented as a float. The matrix is
		 * used for projecting 3D points onto a 2D plane.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Handles user input for movement and rotation within a 3D space. It processes
	 * keyboard and mouse inputs to rotate and move an object based on its current
	 * orientation, with optional speed boost when left shift is pressed.
	 *
	 * @param dt time delta, used to calculate the movement amount based on the game speed
	 * and elapsed time since the last frame.
	 *
	 * @param speed 3D movement speed of the object, which is adjusted up to 10 times
	 * when the LShift key is pressed.
	 *
	 * @param sens sensitivity of mouse movement, affecting the rotation amount around
	 * each axis based on its value.
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
	 * Updates an object's position by a specified amount along a given direction vector.
	 * It multiplies the direction vector by the amount, then adds the result to the
	 * current position. The new position is set as the object's transform position.
	 *
	 * @param dir 3D direction vector used to calculate the new position by scaling it
	 * with the specified amount and adding it to the current position.
	 *
	 * @param amt amount by which to multiply the direction vector, effectively scaling
	 * its magnitude to control the movement distance.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
