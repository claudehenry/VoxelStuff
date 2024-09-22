package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Implements a 3D camera system that manages projection and view matrices based on
 * user input and viewport dimensions.
 * It extends the base Camera class with additional functionality for handling mouse
 * and keyboard input to control camera movement.
 * The class is designed to work in conjunction with OpenGL graphics rendering.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a matrix representing the camera's projection based on input from a
	 * CameraStruct, assigning the resulting matrix to the local variable `projection`.
	 * The assignment is done by calling `getAsMatrix4()` on the CameraStruct. The returned
	 * matrix is a Matrix4f.
	 *
	 * @param data 3D camera object whose projection matrix is retrieved and assigned to
	 * the local variable `projection`.
	 *
	 * @returns a `Matrix4f` object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the aspect ratio and projection matrix based on the given viewport dimensions.
	 * It then recalculates the view matrix and sets the OpenGL viewport to match the
	 * specified dimensions. It also catches any potential NullPointerExceptions during
	 * view matrix calculation.
	 *
	 * @param width 2D viewport width and is used to calculate the aspect ratio for the
	 * camera's projection matrix and to set the GL11 viewport.
	 *
	 * @param height viewport's height and is used to calculate the aspect ratio of the
	 * camera and update the view and projection matrices accordingly.
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
	 * Encapsulates perspective projection parameters for a 3D camera. It provides a
	 * constructor to initialize these parameters and a method to convert them into a
	 * matrix. This class is likely used in a graphics rendering application.
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
		 * Returns a new instance of a perspective matrix. It initializes the matrix with a
		 * specified field of view (fov), aspect ratio (aspect), near clipping plane distance
		 * (zNear), and far clipping plane distance (zFar). The resulting matrix is used to
		 * transform objects in 3D space.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates camera rotation and position based on user input.
	 * It rotates the camera left or right around its up axis and forward or backward
	 * along its rotation axes.
	 * Movement is scaled by a factor of 10 when the left shift key is pressed.
	 *
	 * @param dt time elapsed since the last update, used to calculate movement amount
	 * based on speed.
	 *
	 * @param speed 3D movement speed of an object and is used to calculate the amount
	 * by which the object's position is updated each frame.
	 *
	 * @param sens sensitivity of mouse rotation, affecting the rate at which the camera's
	 * view direction changes when the user moves their mouse.
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
	 * Updates the position of an object by adding a specified amount to its current
	 * position, based on a given direction vector and scalar multiplier. The new position
	 * is calculated by multiplying the direction vector by the scalar value and then
	 * adding it to the current position.
	 *
	 * @param dir 3D direction vector by which the object's position is updated.
	 *
	 * @param amt amount to multiply the direction vector by, effectively scaling its
	 * magnitude and determining the distance moved along the specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
