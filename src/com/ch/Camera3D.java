package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

public class Camera3D extends Camera {
	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's matrices to fit a given viewport size and aspect ratio. It
	 * sets the camera's aspect ratio and calculates the projection and view matrices,
	 * then sets the viewport size using `GL11.glViewport`.
	 * 
	 * @param width 2D viewport width for the adjustment of the 3D camera's projection matrix.
	 * 
	 * @param height vertical size of the viewport in pixels, which is used to calculate
	 * the aspect ratio and projection matrix for the 3D scene.
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

	protected class CameraStruct3D extends CameraStruct {

		public float fov, aspect, zNear, zFar;

		public CameraStruct3D(float fov, float aspect, float zNear, float zFar) {
			this.fov = fov;
			this.aspect = aspect;
			this.zNear = zNear;
			this.zFar = zFar;
		}

		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes mouse and keyboard input to rotate and move an object based on its
	 * transform. It also multiplies the movement speed by 10 when the 'LShift' key is pressed.
	 * 
	 * @param dt time differential since the last frame, and is used to calculate the
	 * movement speed of the object based on the keyboard input.
	 * 
	 * @param speed 3D movement speed of the game object being processed, which is
	 * multiplied by the time elapsed (represented by `dt`) to determine the actual
	 * distance moved.
	 * 
	 * @param sens sensitivity of the mouse input, which affects how much the object
	 * rotates based on the user's mouse movement.
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
	 * Moves the object by a specified distance along a given direction.
	 * 
	 * @param dir 3D direction of movement, which is added to the current position of the
	 * transform entity using the `add()` method, resulting in a new position after the
	 * movement has taken place.
	 * 
	 * @param amt 3D displacement amount of the object along the specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
