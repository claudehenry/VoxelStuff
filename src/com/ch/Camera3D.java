package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Represents a 3D camera with rotation and movement capabilities. It extends the
 * base Camera class and includes features for handling user input to adjust its
 * position and orientation in real-time. The class also handles projection matrix
 * calculation based on viewport dimensions.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix by assigning it the value of the input camera's
	 * projection matrix, as provided through the `getAsMatrix4()` method. The assignment
	 * is stored in a local variable named `projection`. The function does not modify the
	 * original input data.
	 *
	 * @param data 3D camera settings that are used to generate a projection matrix, and
	 * its `getAsMatrix4()` method is called to obtain the actual data as a Matrix4f object.
	 *
	 * @returns a 4x4 matrix stored in the `projection` variable.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's aspect ratio and projection matrix when the viewport changes.
	 * It also attempts to calculate a view matrix but catches and ignores any resulting
	 * NullPointerException. Finally, it sets the OpenGL viewport.
	 *
	 * @param width 2D viewport's width and is used to set the camera aspect ratio and
	 * update the GL11 viewport accordingly.
	 *
	 * @param height height of the viewport, which is used to calculate the aspect ratio
	 * for the projection matrix and to set the GL viewport dimensions.
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
	 * Represents a data structure containing camera projection settings, allowing for
	 * easy initialization and retrieval of a perspective matrix based on these settings.
	 * It serves as an extension to a base CameraStruct class. Its primary purpose is to
	 * encapsulate camera configuration values.
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
		 * Returns a new perspective projection matrix using the provided field of view (fov),
		 * aspect ratio (aspect), near clipping plane (zNear), and far clipping plane (zFar)
		 * values. The returned matrix is used for 3D rendering purposes. A Matrix4f object
		 * is initialized with the specified parameters.
		 *
		 * @returns a perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the camera's rotation and translation based on user input from mouse and
	 * keyboard. Mouse movement rotates the camera, while keyboard keys W, A, S, D, and
	 * LSHIFT control forward, side, backward, rightward motion, and speed respectively.
	 *
	 * @param dt 3D transformation's rotation speed, used to calculate the movement amount
	 * based on the elapsed time since the last frame update.
	 *
	 * @param speed 3D movement speed of the object, which can be multiplied by up to 10
	 * when the left shift key is held down.
	 *
	 * @param sens 3D camera's sensitivity to mouse movement, scaling the rotation based
	 * on its value.
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
	 * Modifies the position of an object by adding a specified amount to its current
	 * position along a given direction. It multiplies the direction vector by the specified
	 * amount, then adds the result to the object's current position. The new position
	 * is immediately set on the object.
	 *
	 * @param dir 3D direction vector used to calculate the new position of the object,
	 * where its magnitude is scaled by the specified amount before addition to the current
	 * position.
	 *
	 * @param amt 3D vector scaling factor that determines how far to move an object along
	 * the specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
