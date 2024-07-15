package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Defines a camera class that inherits from the `Camera` class and has additional
 * features such as adjusting to the viewport size, calculating the projection matrix,
 * and processing input events. The class also includes a `processInput` method that
 * handles mouse and keyboard input events and moves the camera accordingly.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Takes a `CameraStruct` object as input and returns the projection matrix in form
	 * of a `Matrix4f` object.
	 * 
	 * @param data 3D camera's properties, such as its position, orientation, and field
	 * of view, which are used to calculate the projection matrix.
	 * 
	 * @returns a `Matrix4f` object representing the projection matrix as specified by
	 * the input `CameraStruct`.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the camera's projection and view matrices to fit within the specified
	 * viewport dimensions. It also sets the viewport dimensions using `GL11.glViewport()`.
	 * 
	 * @param width width of the viewport in pixels.
	 * 
	 * @param height 2D viewport size of the graphical representation of the scene, which
	 * is used to calculate the appropriate projection matrix and view matrix for the
	 * camera's rendering.
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
	 * Is a sub-class of the Camera Struct that contains additional attributes and methods
	 * to handle 3D camera calculations, such as fov, aspect, zNear, and zFar. It also
	 * provides a method for getting the matrix representation of the camera's projection.
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
		 * Initializes a matrix representing a perspective projection with the specified field
		 * of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a Matrix4f object representing a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Rotates the object based on mouse input and key presses, moving it along the forward
	 * direction of its rotation.
	 * 
	 * @param dt 3D game time step, which is used to calculate the movement of the object
	 * based on keyboard input.
	 * 
	 * @param speed 3D movement speed of the entity being controlled by the code, which
	 * is multiplied by the time interval `dt` to determine the distance traveled during
	 * each iteration of the function.
	 * 
	 * @param sens sensitivity of the mouse input, which determines how much the character
	 * will move when the mouse is moved.
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
	 * Modifies the position of an object by adding a directional vector multiplied by a
	 * scalar value to its current position.
	 * 
	 * @param dir 3D direction in which to move the object, with the magnitude of the
	 * movement specified by the `amt` parameter.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the transform.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
