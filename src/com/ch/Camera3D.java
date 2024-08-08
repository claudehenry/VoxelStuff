package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Provides a 3D camera functionality with perspective projection and mouse/keyboard
 * input handling for movement and rotation of the camera. The class extends a base
 * `Camera` class and initializes its internal state based on provided parameters.
 * It also includes methods to adjust the viewport size and recalculate matrices
 * according to changes in camera settings or screen resolution.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a 4x4 matrix representing the projection transformation for a given camera.
	 * It takes a `CameraStruct` object as input, extracts the 4x4 matrix from it using
	 * `getAsMatrix4`, and assigns it to a local variable before returning it.
	 *
	 * @param data 4x4 matrix that is passed to the function and returned as the projection
	 * matrix after being assigned to the local variable `projection`.
	 *
	 * @returns a `Matrix4f` object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the aspect ratio and calculates the projection matrix based on the provided
	 * viewport dimensions. It also attempts to calculate the view matrix but catches any
	 * resulting NullPointerExceptions. Finally, it sets the OpenGL viewport to match the
	 * specified width and height.
	 *
	 * @param width 2D viewport width that is used to calculate the aspect ratio for the
	 * CameraStruct3D and sets the GL11 glViewport with the specified width, height, and
	 * x, y coordinates.
	 *
	 * @param height 2D viewport's height and is used to calculate the aspect ratio of
	 * the camera projection matrix and set the viewport dimensions for OpenGL rendering.
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
	 * Is designed to represent a specific type of camera struct for 3D rendering. It
	 * encapsulates the necessary data for perspective projection and provides a method
	 * to generate a Matrix4f object based on this data, which can then be used to set
	 * up a 3D view.
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
		 * Creates a new perspective matrix with the given field-of-view (fov), aspect ratio,
		 * near clipping plane distance (zNear), and far clipping plane distance (zFar). It
		 * initializes the new matrix with these values and returns it. The resulting matrix
		 * is used for 3D transformations.
		 *
		 * @returns a 4x4 perspective transformation matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the game object's rotation based on mouse movement and keyboard input. It
	 * also adjusts speed depending on whether the shift key is held down. The function
	 * then applies movement to the object based on the keyboard input, taking into account
	 * its current rotation.
	 *
	 * @param dt 3D time, used to calculate the movement amount based on the speed and
	 * sensitivity values.
	 *
	 * @param speed 3D movement speed of an object, which is affected by a modifier factor
	 * when the left shift key is pressed.
	 *
	 * @param sens sensitivity of the mouse rotation, which is used to calculate the
	 * rotation angles based on the horizontal and vertical mouse movements.
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
	 * Updates the position of an object by adding a specified vector multiplied by a
	 * given amount to its current position, using the provided transform. The new position
	 * is then set for the object. This function moves the object along the specified
	 * direction by the given distance.
	 *
	 * @param dir 3D direction vector that is multiplied by the specified amount (`amt`)
	 * to calculate the translation offset.
	 *
	 * @param amt multiplier for scaling the direction vector `dir`, effectively controlling
	 * the distance moved by the object along that direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
