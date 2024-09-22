package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents a 3D camera with perspective projection and rotation capabilities based
 * on user input from mouse and keyboard. It extends the base Camera class to include
 * features for calculating the projection matrix, adjusting the viewport, and
 * processing user input. The class also includes functionality for movement and
 * rotation of the camera.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a projection matrix by copying a matrix from a camera structure and
	 * assigns it to a local variable, returning the result as a Matrix4f object. The
	 * input data is expected to be convertible to a Matrix4f.
	 *
	 * @param data 3D camera structure that contains matrix transformation data which is
	 * then extracted and assigned to the `projection` variable.
	 *
	 * @returns a `Matrix4f` object representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's aspect ratio and projection matrix based on the provided
	 * viewport dimensions. It then attempts to calculate the view matrix but catches and
	 * ignores any resulting `NullPointerException`. Finally, it sets the OpenGL viewport
	 * with the given width and height.
	 *
	 * @param width 2D viewport's width, used to set the `aspect` field of the `CameraStruct3D`
	 * object and calculate projection and view matrices based on it.
	 *
	 * @param height height of the viewport and is used to calculate the aspect ratio.
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
	 * Defines a custom camera structure for 3D projections. It encapsulates essential
	 * parameters for perspective projection and provides a method to generate a corresponding
	 * Matrix4f object. This class extends the base CameraStruct.
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
		 * Initializes a perspective projection matrix based on field-of-view (FOV), aspect
		 * ratio, and near and far clipping planes. It creates a new Matrix4f object and sets
		 * its initial value to a perspective projection matrix using the specified parameters.
		 * The resulting matrix is then returned.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates camera rotation and position based on mouse movement and keyboard input.
	 * It calculates camera rotation from mouse delta and sensitivity, then adjusts speed
	 * based on shift key press before updating camera position using movement keys (WASD).
	 *
	 * @param dt time delta, which is used to scale movement speed over time, allowing
	 * for smooth and consistent movement regardless of frame rate.
	 *
	 * @param speed 3D movement speed of an object, which is multiplied by the time delta
	 * (`dt`) to determine the actual movement amount.
	 *
	 * @param sens 2D sensitivity of the mouse input, used to calculate the rotation angle
	 * based on mouse movement.
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
	 * Updates the position of an object based on a given direction and amount. It
	 * multiplies the direction by the specified amount, adds the result to the current
	 * position, and applies the new position to the object's transform. The position is
	 * changed by the specified direction amount.
	 *
	 * @param dir 3D direction vector that determines the new position to which the object
	 * will be moved.
	 *
	 * @param amt amplitude or intensity of the movement direction, scaling its effect
	 * on the position update.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
