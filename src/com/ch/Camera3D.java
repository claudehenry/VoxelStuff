package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is designed to handle camera movement and projection in a 3D environment. It extends
 * the base Camera class and includes methods for calculating projection matrices,
 * adjusting to viewports, processing user input, and moving the camera. The class
 * also maintains a camera struct with fields for field of view, aspect ratio, near
 * plane distance, and far plane distance.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a matrix representing a camera's projection. It takes a `CameraStruct`
	 * object as input and assigns its internal matrix to the function's return value,
	 * effectively copying the matrix without modification or calculation. The returned
	 * matrix is not calculated based on any camera-specific parameters.
	 *
	 * @param data 4x4 matrix from which the projection matrix is retrieved and returned.
	 *
	 * @returns a `Matrix4f` object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Sets the aspect ratio for a camera and calculates projection and view matrices
	 * based on the viewport dimensions. It also handles potential null pointer exceptions
	 * during calculation. The viewport is then set to match the specified width and height.
	 *
	 * @param width 2D viewport width and is used to set the aspect ratio of the
	 * CameraStruct3D object's projection matrix and update the GL11 glViewport with the
	 * specified width.
	 *
	 * @param height height of the viewport to be adjusted to, which is used to calculate
	 * the aspect ratio and adjust the camera's projection matrix accordingly.
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
	 * Extends an unnamed parent class and represents camera settings for 3D rendering.
	 * It encapsulates four float values: field of view, aspect ratio, near clipping plane
	 * distance, and far clipping plane distance. The class provides a method to calculate
	 * the perspective projection matrix from these settings.
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
		 * Initializes a perspective matrix with specified parameters: field-of-view (fov),
		 * aspect ratio (aspect), near clipping plane distance (zNear), and far clipping plane
		 * distance (zFar). It returns a new Matrix4f object with these values set.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the rotation and movement of an object based on user input. It uses mouse
	 * delta values to rotate the object around its vertical axis, and keyboard key states
	 * to move it forward, backward, left, or right.
	 *
	 * @param dt 3D delta time, used to adjust movement speed according to the elapsed
	 * time since the last frame update.
	 *
	 * @param speed movement speed of an object, which is scaled by a factor of 10 when
	 * the left shift key is pressed.
	 *
	 * @param sens sensitivity of the mouse rotation, affecting the amount of rotation
	 * applied to the object's orientation based on the mouse movement.
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
	 * Alters an object's position by adding a specified amount to its current position
	 * along a defined direction. The function multiplies the direction vector by the
	 * specified amount, then adds the result to the current position before updating it.
	 *
	 * @param dir 3D direction vector that is scaled by the `amt` factor and added to the
	 * current position of the object, moving it accordingly.
	 *
	 * @param amt amount by which the direction vector is multiplied to determine the
	 * translation distance.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
