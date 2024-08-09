package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is designed to handle camera movement and projection in a 3D environment. It extends
 * the Camera class and provides methods for calculating projection matrices, adjusting
 * to viewports, and processing user input. The class includes features such as
 * mouse-based rotation and keyboard-controlled movement.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix calculated from a given camera structure. It retrieves
	 * the projection matrix from the camera structure using the `getAsMatrix4()` method
	 * and assigns it to the `projection` variable, returning the result as a Matrix4f object.
	 *
	 * @param data 4x4 matrix that is retrieved and assigned to the local variable `projection`.
	 *
	 * @returns a Matrix4f object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts the camera's aspect ratio and calculates projection and view matrices based
	 * on the provided viewport dimensions. It also sets the OpenGL viewport to the
	 * specified width and height, effectively adjusting the rendering area to match the
	 * new dimensions.
	 *
	 * @param width 2D viewport's width and is used to calculate the aspect ratio of the
	 * camera's projection matrix.
	 *
	 * @param height height of the viewport, which is used to calculate the aspect ratio
	 * and set the glViewport.
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
	 * Is an extension of the CameraStruct class, used to define parameters for a camera
	 * in a 3D environment. It initializes these parameters through its constructor and
	 * provides a method to generate a perspective projection matrix based on these values.
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
		 * Creates and initializes a perspective matrix with a specified field of view (fov),
		 * aspect ratio, near clipping plane distance (zNear), and far clipping plane distance
		 * (zFar). The resulting matrix is represented by the `Matrix4f` class.
		 *
		 * @returns a Perspective Matrix4f object initialized with specified parameters.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Calculates mouse rotation and applies it to the object's transform, then adjusts
	 * movement speed based on keyboard input. It enables faster movement when the left
	 * shift key is pressed and controls movement direction with W, S, A, D keys.
	 *
	 * @param dt 3D time interval, used to calculate the movement amount based on the
	 * speed and other inputs.
	 *
	 * @param speed magnitude of movement, which is scaled up by a factor of 10 when the
	 * left shift key is pressed and used to calculate the amount of movement along each
	 * axis.
	 *
	 * @param sens sensitivity of mouse rotation, used to calculate the amount of rotation
	 * based on the horizontal and vertical mouse movement.
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
	 * Translates an object's position by adding a vector `dir` scaled by a floating-point
	 * value `amt`. The new position is set as the current transform's position using the
	 * `getTransform()` method. This results in moving the object along the specified
	 * direction for a certain amount.
	 *
	 * @param dir 3D direction vector that is multiplied by the specified amount (`amt`)
	 * and added to the current position.
	 *
	 * @param amt scalar amount to multiply with the direction vector `dir`, determining
	 * the magnitude of the movement.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
