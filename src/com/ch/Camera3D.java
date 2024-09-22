package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents a 3D camera that can be positioned and rotated in space. It extends the
 * base Camera class and provides functionality for handling user input and updating
 * its transformation matrix accordingly. The class also manages projection matrices
 * and viewports based on the camera's settings.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix from a CameraStruct object's internal representation
	 * as a matrix. The projection matrix is assigned to the 'projection' variable, and
	 * its value is returned. This implies an assignment of the camera data to the
	 * projection matrix.
	 *
	 * @param data 4x4 projection matrix from which the method returns a copy as an
	 * instance variable called `projection`.
	 *
	 * @returns a projection matrix from the provided camera data.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates a camera's aspect ratio and projection matrix based on the given viewport
	 * dimensions. It then attempts to update the view matrix but catches any
	 * NullPointerExceptions. Finally, it sets the OpenGL viewport coordinates.
	 *
	 * @param width 2D viewport's width and is used to calculate the aspect ratio of the
	 * camera and set the viewport's dimensions in OpenGL.
	 *
	 * @param height viewport's vertical size and is used to calculate the aspect ratio
	 * for projection matrix calculation.
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
	 * Is defined as a subclass of CameraStruct and encapsulates perspective projection
	 * parameters for a 3D camera. It creates a Matrix4f object from these parameters
	 * using the getAsMatrix4 method. The class extends the functionality of its superclass
	 * to accommodate 3D projections.
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
		 * Initializes a new perspective matrix using given FOV, aspect ratio, near and far
		 * clipping planes. The function returns a new `Matrix4f` object initialized with the
		 * calculated perspective matrix values. It creates a new matrix instance on each
		 * call without reusing or modifying existing matrices.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Controls character movement and rotation based on user input from keyboard and
	 * mouse. It adjusts speed and rotation sensitivity based on key presses, then updates
	 * the character's position and orientation accordingly. Movement is constrained to
	 * forward, backward, left, and right directions.
	 *
	 * @param dt time delta, or change in time since the last frame, used to calculate
	 * the movement amount based on the given speed and time elapsed.
	 *
	 * @param speed 3D object's movement speed and is multiplied by the time step (`dt`)
	 * to determine the actual movement distance per frame.
	 *
	 * @param sens sensitivity of mouse rotation, with higher values resulting in faster
	 * rotation.
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
	 * Adjusts an object's position by a specified amount along a given direction vector.
	 * It multiplies the direction vector by the specified amount, then adds the result
	 * to the object's current position using its transformation data. The new position
	 * is updated accordingly.
	 *
	 * @param dir 3D direction vector that determines the movement trajectory, with its
	 * magnitude and sign influencing the extent and orientation of the movement.
	 *
	 * @param amt magnitude of movement in the specified direction, scaling the vector
	 * multiplication result accordingly.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
