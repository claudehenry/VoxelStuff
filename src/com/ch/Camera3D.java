package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Implements a basic 3D camera system with support for mouse input and keyboard
 * controls to rotate and translate the camera's position. It uses LWJGL libraries
 * for OpenGL functionality and matrix calculations for projection and view matrices.
 * The class also handles viewport adjustments based on screen width and height.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a Matrix4f object, which is assigned the value of the projection matrix
	 * retrieved from the CameraStruct data as a Matrix4f. The retrieved matrix is stored
	 * in a local variable and then returned by the function.
	 *
	 * @param data 3D camera configuration, which is used to retrieve its projection
	 * matrix as a Matrix4f object.
	 *
	 * @returns a matrix of type `Matrix4f`.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Sets up the camera's aspect ratio and projection matrix based on the viewport
	 * dimensions. It then attempts to calculate the view matrix but catches any
	 * NullPointerException exceptions that may occur. The GL11.glViewport method is
	 * called to set the viewport to match the provided width and height.
	 *
	 * @param width 2D viewport's horizontal resolution and is used to calculate the
	 * aspect ratio of the camera and set its projection matrix accordingly.
	 *
	 * @param height vertical resolution of the viewport, used to calculate the camera's
	 * aspect ratio and scale the projection matrix accordingly.
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
	 * Represents a camera structure with specific parameters for perspective projection
	 * and is used to initialize a matrix for rendering. It extends the CameraStruct class
	 * and provides a method to convert its data into a Matrix4f object. This allows for
	 * easy integration with 3D graphics libraries.
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
		 * Returns a new instance of a matrix initialized with a perspective projection
		 * transformation based on specified field-of-view (FOV), aspect ratio, near, and far
		 * clipping plane values. The resulting matrix is returned as an instance of `Matrix4f`.
		 * A new instance is created each time the method is called.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the object's rotation based on mouse input and movement speed, then applies
	 * keyboard-driven movement along the object's forward, left, right, or backward
	 * directions. Movement speed is increased when the shift key is held down.
	 *
	 * @param dt time delta, used to calculate movement speed by multiplying it with other
	 * parameters such as speed and keyboard inputs.
	 *
	 * @param speed 3D movement speed of the object and is used to calculate the distance
	 * moved when the W, A, S, or D keys are pressed.
	 *
	 * @param sens 2D sensitivity of mouse movement, which affects the rotation speed of
	 * the object.
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
	 * It multiplies the direction vector by the amount, and adds the result to the current
	 * position, updating it accordingly. The updated position is set using the `setPos`
	 * method of the transform component.
	 *
	 * @param dir 3D direction vector that defines the movement direction of the object,
	 * scaled by the `amt` factor.
	 *
	 * @param amt 3D distance to be added along the specified direction vector, scaling
	 * its magnitude.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
