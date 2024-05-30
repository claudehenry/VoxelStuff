package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an extension of the Camera class that provides additional functionality for
 * manipulating the camera's position and orientation in 3D space. The class has
 * several methods for calculating and adjusting the camera's projection matrix, as
 * well as processing input from the user. These include move(), calculateProjectionMatrix(),
 * and adjustToViewport().
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a matrix representing the projection of a 3D scene from a specified
	 * viewpoint, based on the data provided by the `CameraStruct` parameter.
	 * 
	 * @param data 3D camera's projection matrix.
	 * 
	 * @returns a Matrix4f object representing the projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the camera's projection matrix and view matrix to fit within the given
	 * viewport dimensions, and then sets the viewport size using `GL11.glViewport()`.
	 * 
	 * @param width 2D viewport width for which the projection and view matrices are calculated.
	 * 
	 * @param height 2D viewport dimensions, which are used to calculate the aspect ratio
	 * and projection matrix of the 3D scene.
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
	 * Is an extension of the Camera Struct class that adds additional parameters to
	 * represent the field of view (fov), aspect ratio, near and far distances, and
	 * provides a method to get the matrix representation of the camera's perspective projection.
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
		 * Initializes a `Matrix4f` object with a perspective projection, combining the field
		 * of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes mouse and keyboard input to move an object based on sensitivity and
	 * speed, rotates the object based on the mouse input, and implements basic movement
	 * using the W, S, A, and D keys.
	 * 
	 * @param dt time step of the simulation, which is multiplied with the movement speed
	 * to determine the amount of movement per frame.
	 * 
	 * @param speed 3D movement speed of the object being manipulated by the function,
	 * which is multiplied by the time elapsed (represented by `dt`) to determine the
	 * total distance traveled during the specified time interval.
	 * 
	 * @param sens sensitivity of the mouse input, which determines how much the character
	 * will move based on the user's mouse input.
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
	 * Moves an object by a specified distance in a particular direction using a transform's
	 * position property.
	 * 
	 * @param dir 3D direction in which to move the object, with the direction being
	 * multiplied by the specified amount (`amt`).
	 * 
	 * @param amt amount of movement to apply to the object's position, as measured in
	 * the direction specified by the `dir` parameter.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
