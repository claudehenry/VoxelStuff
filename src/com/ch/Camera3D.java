package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is responsible for managing and controlling camera movements in a 3D environment.
 * It calculates the projection matrix based on aspect ratio, near and far clipping
 * planes, and adjusts it according to the viewport size. Additionally, the class
 * handles user input events such as mouse movement and keyboard keys to manipulate
 * the camera's position and rotation.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a projection matrix based on input from a `CameraStruct`. It retrieves
	 * the projection matrix from the `CameraStruct` using its `getAsMatrix4()` method
	 * and assigns it to the `projection` variable, returning this value.
	 *
	 * @param data 4x4 matrix from which the `calculateProjectionMatrix` method retrieves
	 * and assigns to the local variable `projection`.
	 *
	 * @returns a `Matrix4f` object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts camera settings to match the specified viewport dimensions. It calculates
	 * and updates aspect ratio, projection matrix, and view matrix based on provided
	 * values. Additionally, it sets up OpenGL viewport with the given width and height.
	 *
	 * @param width 2D viewport width and is used to calculate the aspect ratio of the
	 * camera and set it as the `aspect` property of the `CameraStruct3D`.
	 *
	 * @param height height of the viewport and is used to calculate the aspect ratio for
	 * projection matrix calculation.
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
	 * Represents a custom camera structure for 3D projection matrices with specified
	 * field of view (FOV), aspect ratio, near and far clipping planes. It provides a
	 * method to initialize a perspective matrix using these parameters. The class extends
	 * the CameraStruct abstract class.
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
		 * Initializes a perspective matrix with specified field-of-view (fov), aspect ratio,
		 * near clipping plane distance (zNear), and far clipping plane distance (zFar) and
		 * returns it as a Matrix4f object. It creates a new Matrix4f instance and uses the
		 * initPerspective method to set its values.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates a game object's rotation and movement based on user input from mouse and
	 * keyboard. It calculates rotation around y-axis and x-axis, adjusts speed with shift
	 * key, and moves the object forward-backward, left-right using W-A-S-D keys or
	 * LShift+W-A-S-D keys for faster movement.
	 *
	 * @param dt 3D rendering time step, used to calculate the movement amount based on
	 * the speed and keyboard inputs.
	 *
	 * @param speed 3D movement speed, which is multiplied by the time delta (`dt`) to
	 * determine the actual movement amount.
	 *
	 * @param sens sensitivity of the camera rotation and movement, used to scale the
	 * amount of rotation or movement based on user input.
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
	 * Modifies the position of an object by adding a specified vector multiplied by a
	 * given amount to its current position. The modification is based on the object's
	 * transform, which represents its location and orientation in space. The updated
	 * position is then set as the new position of the object.
	 *
	 * @param dir 3D vector direction for movement, which is multiplied by the amount
	 * `amt` to determine the displacement of the object's position.
	 *
	 * @param amt amount by which the direction vector is scaled before adding it to the
	 * current position of an object.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
