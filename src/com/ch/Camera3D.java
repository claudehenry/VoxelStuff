package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is an extension of the base Camera class, designed for 3D camera functionality.
 * It handles projection matrix calculation, viewport adjustment, and user input
 * processing for camera movement. The class utilizes Matrix4f and Vector3f classes
 * to perform rotations and movements in 3D space.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Obtains a projection matrix from a camera's data and stores it in the `projection`
	 * variable, returning the same value. The `getAsMatrix4()` method is assumed to
	 * extract the necessary information from the camera struct.
	 *
	 * @param data 4x4 matrix structure, which is retrieved as a Matrix4f object using
	 * its `getAsMatrix4()` method and used to calculate the projection matrix.
	 *
	 * @returns a Matrix4f object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts camera settings based on viewport dimensions, setting aspect ratio and
	 * recalculating projection and view matrices. It then updates the OpenGL viewport
	 * to match the new dimensions, rendering at specified width and height with origin
	 * at (0,0).
	 *
	 * @param width 2D viewport's width and is used to set the camera aspect ratio and
	 * update the projection matrix accordingly.
	 *
	 * @param height current viewport's vertical dimension and is used to calculate the
	 * aspect ratio for the projection matrix.
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
	 * Encapsulates projection parameters and provides a method to convert them into a
	 * Matrix4f object for use in 3D projections. It extends the CameraStruct class with
	 * specific attributes for 3D cameras. The class is designed to simplify the creation
	 * of perspective matrices.
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
		 * Initializes and returns a perspective matrix. It creates a new `Matrix4f` object
		 * and calls its `initPerspective` method to set up a projection matrix based on
		 * field-of-view (fov), aspect ratio, near clipping plane (zNear), and far clipping
		 * plane (zFar) values.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates game object movement and rotation based on user input. It processes keyboard
	 * keys W, A, S, D for translation and mouse movements to rotate the object around
	 * its x-axis and y-axis.
	 *
	 * @param dt time delta, used to scale movement speed linearly with respect to elapsed
	 * time.
	 *
	 * @param speed 3D movement speed of an object, which is scaled up to 10 times when
	 * the left shift key is pressed.
	 *
	 * @param sens sensitivity of mouse rotation, affecting the amount of rotation applied
	 * to the object based on the mouse's horizontal movement.
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
	 * Translates an object by a specified amount along a given direction vector. It
	 * updates the object's position by adding the product of the direction vector and
	 * the translation amount to its current position. The result is a new position for
	 * the object.
	 *
	 * @param dir 3D direction vector to which a scalar amount is multiplied, resulting
	 * in the movement offset applied to the object's position.
	 *
	 * @param amt amount by which the position is being offset from the current position
	 * when moving in the specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
