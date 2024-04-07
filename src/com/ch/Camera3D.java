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

	/**
	 * generates a projection matrix based on input from the `CameraStruct`.
	 * 
	 * @param data 3D camera parameters, including its position, view direction, and field
	 * of view, which are used to compute the projection matrix.
	 * 
	 * @returns a Matrix4f object containing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts a camera's projection matrix to fit within a specified viewport size. It
	 * does this by calculating the aspect ratio of the viewport and then using that ratio
	 * to scale the camera's projection matrix.
	 * 
	 * @param width width of the viewport.
	 * 
	 * @param height 2D size of the viewport in pixels and is used to calculate the aspect
	 * ratio of the camera's projection matrix and to set the viewport size in GL11.glViewport().
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

		/**
		 * returns a Matrix4f object initialized with a perspective projection matrix based
		 * on the given field of view (fov), aspect ratio, near plane (zNear), and far plane
		 * (zFar).
		 * 
		 * @returns a matrix representation of a perspective projection transformation.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes input from the mouse and keyboard, applying rotations and movements to
	 * an object based on user inputs.
	 * 
	 * @param dt time step for which the code is being executed, and it is used to determine
	 * the movement of the object based on its speed and sensitivity.
	 * 
	 * @param speed 3D movement speed of the object being manipulated by the code, and
	 * it is multiplied by the time differential `dt` to determine the total distance
	 * traveled during each frame.
	 * 
	 * @param sens sensitivity of the mouse input, which determines how much the character
	 * will move based on the user's cursor position.
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
	 * updates the position of an object by adding a directional vector multiplied by a
	 * scalar amount to its current position.
	 * 
	 * @param dir 3D direction of movement, which is added to the current position of the
	 * object using the multiplication operator.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the transform.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
