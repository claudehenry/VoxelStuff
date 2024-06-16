package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Defines a camera class that extends the Camera class and provides additional
 * functionality for a 3D environment. The class has a constructor that takes fov,
 * aspect, zNear, and zFar parameters to calculate the projection matrix, and an
 * adjustToViewport method to set the camera's viewport size. The class also has a
 * processInput method that processes keyboard input and moves the camera accordingly.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a matrix representing the projection of a 3D scene onto a 2D view plane,
	 * based on input data provided by the `CameraStruct` object.
	 * 
	 * @param data 3D camera's intrinsic and extrinsic parameters, which are used to
	 * calculate the projection matrix.
	 * 
	 * @returns a `Matrix4f` object representing the projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the camera's projection and view matrices to fit within the bounds of a
	 * specified viewport. It sets the aspect ratio of the camera's projection matrix
	 * based on the width and height of the viewport, calculates the view matrix, and
	 * sets the viewport dimensions using `GL11.glViewport()`.
	 * 
	 * @param width 2D viewport width in pixels for which the camera's projection and
	 * view matrices are calculated and displayed.
	 * 
	 * @param height 2D viewport dimension of the screen, which is used to calculate the
	 * projection and view matrices for rendering the 3D scene in the appropriate scale.
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
	 * Is an extension of the CameraStruct class and provides additional functionality
	 * for representing a 3D camera perspective. It contains fields for fov, aspect,
	 * zNear, and zFar, which are used to calculate the matrix for perspectives and
	 * viewport dimensions. The getAsMatrix4() method returns a Matrix4f object initialized
	 * with the camera's perspective parameters.
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
		 * @returns a matrix representation of a perspective projection, initialized with the
		 * specified field of view, aspect ratio, near and far distances.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes mouse and keyboard input to move an object in a 3D space. It rotates the
	 * object based on user input, and accelerates or decelerates its movement based on
	 * the shift key press.
	 * 
	 * @param dt time step, which is used to calculate the movement of the object based
	 * on its velocity and sensitivity.
	 * 
	 * @param speed 3D movement speed of the object being controlled, which is multiplied
	 * by the time step `dt` to determine the total movement distance.
	 * 
	 * @param sens sensitivity of the character's movement in response to user input,
	 * which determines how much the character will move based on small changes in user
	 * input.
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
	 * Moves an object by a specified distance in a specified direction, applying the
	 * transformation of the object's parent transform.
	 * 
	 * @param dir 3D direction to move the object in the specified amount, which is
	 * calculated as the product of the `dir` vector and the scaling factor `amt`.
	 * 
	 * @param amt amount of movement to apply to the object's position along the specified
	 * direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
