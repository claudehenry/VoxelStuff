package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is responsible for managing the camera's perspective and movement in a 3D scene.
 * It calculates the projection matrix based on user input and adjusts to the viewport
 * size. The class also processes keyboard and mouse inputs to control the camera's
 * rotation and translation.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix from the given `CameraStruct`. The function assigns
	 * the result to a local variable and then returns it, effectively setting the
	 * projection matrix.
	 *
	 * @param data 3D camera settings, providing the necessary information for calculating
	 * the projection matrix.
	 *
	 * @returns a `Matrix4f` object from the given camera data.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts camera settings based on provided viewport dimensions. It updates aspect
	 * ratio and calculates projection matrix, then attempts to calculate view matrix.
	 * If unsuccessful, it catches null pointer exception and continues with viewport
	 * setup using GL11 API.
	 *
	 * @param width 2D viewport's width and is used to calculate the aspect ratio of the
	 * camera and set the OpenGL viewport.
	 *
	 * @param height height of the viewport, which is used to calculate the aspect ratio
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
	 * Is an extension of CameraStruct that holds camera-specific parameters for projection
	 * matrix calculation. It encapsulates essential information such as field of view,
	 * aspect ratio, near and far clipping planes. This class facilitates the generation
	 * of a perspective projection matrix using these parameters.
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
		 * Initializes a new matrix with a perspective projection based on the given field-of-view
		 * (fov), aspect ratio, near clip plane distance (zNear), and far clip plane distance
		 * (zFar).
		 *
		 * @returns a perspective transformation matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the rotation and movement of an object based on user input from mouse and
	 * keyboard. It uses sensitivity values to adjust the rotation amount, applies speed
	 * boosts for left shift key press, and moves the object forward, backward, left, or
	 * right depending on keyboard inputs.
	 *
	 * @param dt 3D transformation's delta time, used to calculate the movement amount
	 * based on the speed and frame rate.
	 *
	 * @param speed factor by which movement is scaled, with higher values resulting in
	 * faster movement when keyboard keys are pressed.
	 *
	 * @param sens sensitivity of mouse movement, which affects the rotation of the object
	 * around its Y axis and the translation along its X and Z axes when the user moves
	 * the mouse.
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
	 * Adds a specified amount to an object's position based on a given direction vector.
	 * The new position is calculated by multiplying the direction vector with the specified
	 * amount and then adding it to the current position.
	 *
	 * @param dir 3D vector direction in which the object should be moved, scaled by the
	 * amount specified in the `amt` parameter.
	 *
	 * @param amt amount by which to multiply the direction vector, effectively scaling
	 * its magnitude and determining the distance moved along that direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
