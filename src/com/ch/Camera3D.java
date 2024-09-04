package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is designed to manage camera movement and projection in a 3D environment. It
 * utilizes LWJGL for input handling and OpenGL for graphics rendering. The class
 * enables rotation and translation of the camera based on user input.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Projects a camera's view onto a projection plane by retrieving its internal matrix
	 * representation and assigning it to a local variable for use elsewhere. It returns
	 * the retrieved matrix, effectively copying or referencing the camera's inherent
	 * transformation. The operation is an assignment and return action.
	 *
	 * @param data CameraStruct object from which a matrix is extracted and assigned to
	 * the projection variable, to be returned as a Matrix4f object.
	 *
	 * @returns a matrix representing the camera's projection.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts a camera's aspect ratio and projection matrix to match the current viewport
	 * dimensions. It calculates a new view matrix but catches any potential NullPointerExceptions
	 * that may occur during calculation. The viewport is then updated with the provided
	 * width and height values.
	 *
	 * @param width 2D viewport's horizontal dimension that is used to update the camera
	 * aspect ratio and subsequent projection matrix calculations.
	 *
	 * @param height 2D viewport's height, which is used to calculate the camera's aspect
	 * ratio and update the view matrix accordingly.
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
	 * Represents a data structure for storing camera settings, including field of view,
	 * aspect ratio, near plane distance, and far plane distance, allowing for calculation
	 * of projection matrices. It provides a method to convert its data into a Matrix4f
	 * object representing the projection matrix. It is designed to work in conjunction
	 * with a 3D camera class.
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
		 * Creates a perspective projection matrix. It instantiates a new `Matrix4f`, initializes
		 * it with the given field of view (fov), aspect ratio, and near and far clipping
		 * planes through its constructor. The initialized matrix is then returned.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the object's rotation and movement based on user input. It handles keyboard
	 * keys for movement (W, A, S, D) and mouse input to rotate the object. The shift key
	 * increases movement speed when pressed. Movement is relative to the object's current
	 * orientation.
	 *
	 * @param dt 3D rendering time step, used to scale movement speed linearly with the
	 * frame rate.
	 *
	 * @param speed 3D movement rate and is multiplied by the delta time (`dt`) to determine
	 * the actual movement amount, while its value may be adjusted if the left shift key
	 * is pressed.
	 *
	 * @param sens sensitivity of mouse rotation, with higher values resulting in more
	 * pronounced rotation per unit of mouse movement.
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
	 * Updates the position of an object by a specified amount and direction. It multiplies
	 * a direction vector by an amount, then adds the result to the current position. The
	 * new position is set using the object's transform.
	 *
	 * @param dir 3D direction vector that determines the movement direction of the object,
	 * scaled by the specified amount.
	 *
	 * @param amt 1D scalar value that scales the direction vector, effectively controlling
	 * the magnitude of movement in the specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
