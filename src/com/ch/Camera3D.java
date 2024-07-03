package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an extension of the Camera class with additional functionality for handling
 * perspective projection and viewport adjustments. It also includes input handling
 * for rotations and movements.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a projection matrix based on input from the `CameraStruct` data object,
	 * returning the resulting matrix as a `Matrix4f` value.
	 * 
	 * @param data 3D camera's state, including its position, orientation, and projection
	 * parameters, which are used to calculate the projection matrix.
	 * 
	 * @returns a Matrix4f object representing the projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the camera's projection and view matrices to fit within the specified
	 * viewport dimensions. It also sets the viewport dimensions to the camera's aspect
	 * ratio.
	 * 
	 * @param width 2D viewport width.
	 * 
	 * @param height 2D viewport size in pixels and is used to calculate the aspect ratio
	 * of the camera's projection matrix and to set the viewport size in the GL11 class.
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
	 * Is a customized implementation of the Camera Struct that includes additional fields
	 * to store 3D parameters such as fov, aspect, zNear, and zFar. The getAsMatrix4()
	 * method returns a Matrix4f instance representing a perspective projection with the
	 * specified FOV, aspect ratio, near plane, and far plane.
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
		 * Initializes a `Matrix4f` instance with a perspective projection matrix based on
		 * input parameters `fov`, `aspect`, `zNear`, and `zFar`.
		 * 
		 * @returns a matrix representation of a perspective projection, with fields for field
		 * of view, aspect ratio, near and far distances.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes input from the mouse and keyboard, rotating and moving a game object
	 * based on user inputs.
	 * 
	 * @param dt delta time (a measure of how much time has passed since the last frame)
	 * and is used to calculate the movement of the object based on the keyboard inputs.
	 * 
	 * @param speed 3D movement speed of the object being controlled, which is multiplied
	 * by the time elapsed (`dt`) to determine the total distance traveled during each frame.
	 * 
	 * @param sens sensitivity of the mouse input, which determines how much the character
	 * will move when the user moves the mouse cursor.
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
	 * position along a direction vector.
	 * 
	 * @param dir 3D direction in which the object should move, with the movement amount
	 * being applied to its current position.
	 * 
	 * @param amt amount of movement to be applied to the entity's position, which is
	 * then added to its current position using the `setPos()` method.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
