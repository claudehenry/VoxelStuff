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
	 * Computes a matrix for projection based on camera data.
	 * 
	 * @param data 3D camera object, which provides the projection parameters needed to
	 * calculate the projection matrix.
	 * 
	 * @returns a `Matrix4f` object representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Resizes and rotates a 3D view based on the viewport dimensions passed in, and
	 * updates the camera's projection and view matrices accordingly.
	 * 
	 * @param width width of the viewport in pixels and is used to update the aspect ratio
	 * of the camera's projection matrix.
	 * 
	 * @param height 2D image height that the adjustment will be made for, which is used
	 * to calculate the aspect ratio and projection matrix, and to set the viewport size
	 * in the graphics display buffer.
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
		 * Creates a matrix representation of the current camera's view matrix in the format
		 * Matrix4f, initialized with the specified field of view (fov), aspect ratio, near
		 * and far z coordinates.
		 * 
		 * @returns a 4x4 homogeneous transformation matrix representing a perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Rotates and translates a transform based on mouse and keyboard inputs, while also
	 * scaling the speed based on the `LShift` key.
	 * 
	 * @param dt time differential that the code should be executed over, and it is used
	 * to calculate the movement of the object based on the speed input.
	 * 
	 * @param speed 2D movement speed of the entity being controlled, and its value is
	 * multiplied by the time interval `dt` to determine the total distance traveled
	 * during the frame.
	 * 
	 * @param sens sensitivity of the character's movement in response to mouse movements,
	 * which affects how much the character will move when the user moves their mouse cursor.
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
	 * Moves the transform's position along a specified direction by a certain amount,
	 * updating the position accordingly.
	 * 
	 * @param dir 3D direction of movement to which the entity will be moved, with the
	 * magnitude specified by the `amt` parameter.
	 * 
	 * @param amt 3D direction to move the transform in the world, multiplied by the
	 * specified amount.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
