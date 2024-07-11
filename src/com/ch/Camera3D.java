package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is a custom camera class that extends the Camera class and provides additional
 * functionality for manipulating the camera's position and orientation in 3D space.
 * The class has several methods for calculating matrix transformations, adjusting
 * to the viewport, and processing input from the user. Additionally, it has a protected
 * inner class called CameraStruct3D that stores camera parameters and provides methods
 * for calculating perspective projections and rotations.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a matrix representing the projection of a 3D scene from a camera's
	 * perspective. The returned matrix contains the projection parameters in a `Matrix4f`
	 * format.
	 * 
	 * @param data 3D camera's properties, such as its position, view angle, and field
	 * of view, which are used to calculate the projection matrix.
	 * 
	 * @returns a Matrix4f object representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts a 3D camera's projection and view matrices to fit within the viewport of
	 * a given size.
	 * 
	 * @param width width of the viewport for which the function is adjusting the camera's
	 * projection and view matrices.
	 * 
	 * @param height 2D viewport dimension of the camera's field of view, which is used
	 * to calculate the view matrix and set the viewport dimensions in GL11.glViewport().
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
	 * Is an extension of the CameraStruct class that includes additional parameters to
	 * represent a 3D camera's field of view, aspect ratio, near and far distances. The
	 * class provides a method to return the camera's projection matrix as a Matrix4f
	 * object, which can be used for rendering purposes.
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
		 * Generates a matrix representation of the current perspective projection, initialized
		 * with field of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a matrix representation of a perspective projection, with field of view
		 * (fov), aspect ratio, near and far distances for the z-buffer.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes input from the mouse and keyboard, rotating the object based on input
	 * and moving it along a direction based on keyboard inputs.
	 * 
	 * @param dt 3D time interval (in seconds) since the last frame, and is used to
	 * calculate the movement of the object based on its speed.
	 * 
	 * @param speed 3D movement speed of the object being controlled by the function,
	 * which is multiplied by the time elapsed (represented by `dt`) to determine the
	 * total distance moved.
	 * 
	 * @param sens sensitivity of the character's movement, and it is used to calculate
	 * the amount of rotation applied to the character's position based on the user's
	 * mouse input.
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
	 * Takes a direction vector `dir` and a magnitude `amt` as input, and moves the
	 * position of an object represented by a `Transform` instance by adding the dot
	 * product of `dir` and `amt` to its current position.
	 * 
	 * @param dir 3D direction to move the object in the specified amount.
	 * 
	 * @param amt amount of movement along the specified direction, which is added to the
	 * current position of the transform.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
