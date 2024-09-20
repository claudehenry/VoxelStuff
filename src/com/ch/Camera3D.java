package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is a 3-dimensional camera class that extends the base Camera class.
 * It handles projection and view matrices calculation based on input parameters.
 * It also processes user input for camera movement and rotation.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a projection matrix based on input data from a camera struct and returns
	 * it as a Matrix4f object. The projection matrix is obtained directly from the camera
	 * data using its getAsMatrix4 method.
	 *
	 * @param data 3D camera information that is used to calculate the projection matrix.
	 *
	 * @returns a Matrix4f representing the camera's projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's aspect ratio and calculates projection and view matrices based
	 * on the viewport dimensions. It then sets the OpenGL viewport to the specified size.
	 * The calculateViewMatrix call is attempted but ignored if a NullPointerException occurs.
	 *
	 * @param width 2D viewport's width and is used to calculate the camera's aspect ratio
	 * and adjust the projection matrix accordingly.
	 *
	 * @param height new height of the viewport and is used to calculate the aspect ratio
	 * for updating the projection matrix.
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
	 * Is a subclass that extends the functionality of its parent class to accommodate
	 * three-dimensional camera projections. It encapsulates essential camera parameters
	 * for perspective projection calculations. The class provides an implementation for
	 * generating a Matrix4f object based on these parameters.
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
		 * Returns a perspective projection matrix with specified field-of-view (`fov`),
		 * aspect ratio (`aspect`), near plane distance (`zNear`), and far plane distance
		 * (`zFar`). It initializes a new `Matrix4f` object with these parameters. The result
		 * is a pre-configured projection matrix.
		 *
		 * @returns a matrix representing a perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the camera's rotation and translation based on user input from the mouse
	 * and keyboard. It handles movement, rotation, and speed adjustments according to
	 * key press combinations.
	 *
	 * @param dt time elapsed since the last frame update, used to calculate movement distances.
	 *
	 * @param speed 3D movement speed, which affects the distance traveled by the object
	 * when movement keys are pressed, with optional acceleration applied when the left
	 * shift key is held down.
	 *
	 * @param sens sensitivity of mouse movement, used to calculate rotation angles based
	 * on mouse delta values.
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
	 * Updates an object's position by a specified amount along a given direction vector.
	 * The `dir` parameter specifies the direction, and `amt` is the magnitude of movement.
	 *
	 * @param dir 3D direction vector that is multiplied by the scalar amount `amt` to
	 * determine the movement distance.
	 *
	 * @param amt magnitude of the movement along the specified direction, scaling the
	 * vector `dir`.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
