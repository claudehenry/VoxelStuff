package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Handles camera movement and projection calculations in a 3D scene. It extends the
 * Camera class and provides functionality for adjusting to viewport sizes and
 * processing user input from keyboard and mouse controls. The class also performs
 * rotation, translation, and scaling of the camera's position.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Returns a projection matrix by assigning it to the variable `projection`. The
	 * assigned value is obtained from the `getAsMatrix4` method of the input object,
	 * which must be an instance of `CameraStruct`.
	 *
	 * @param data 4x4 projection matrix data, which is accessed via its `getAsMatrix4()`
	 * method to obtain the actual 4x4 matrix values.
	 *
	 * @returns a projection matrix represented as a `Matrix4f` object.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates camera settings based on viewport dimensions. It adjusts aspect ratio and
	 * calculates projection matrix, then attempts to calculate view matrix before setting
	 * GL viewport coordinates. The try-catch block suppresses NullPointerException
	 * exceptions from the view matrix calculation.
	 *
	 * @param width 2D viewport's width that determines the camera's field of view and
	 * is used to set the OpenGL viewport.
	 *
	 * @param height vertical resolution of the viewport and is used to calculate the
	 * aspect ratio, which is then assigned to the camera's aspect property.
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
	 * Represents a specific camera configuration and provides functionality to convert
	 * this configuration into a Matrix4f object. It encapsulates essential camera
	 * parameters for projection calculations.
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
		 * Initializes a matrix for perspective projection given field of view (fov), aspect
		 * ratio (aspect) and near and far clipping planes (zNear and zFar). It creates and
		 * returns a new Matrix4f object initialized with the specified perspective projection
		 * parameters. The returned matrix is used for transformations in a 3D space.
		 *
		 * @returns a 4x4 perspective projection matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Handles user input to control a 3D object's movement and rotation based on mouse
	 * and keyboard inputs. It adjusts the object's rotation according to mouse sensitivity
	 * and speed, and translates it according to user-specified keys (WASD) with adjustable
	 * acceleration factor.
	 *
	 * @param dt time since the last frame, used to calculate the movement amount based
	 * on the speed and time elapsed.
	 *
	 * @param speed 3D movement speed, which can be scaled up to 10 times when the left
	 * shift key is pressed.
	 *
	 * @param sens sensitivity of mouse rotation, affecting how much the character rotates
	 * for each unit of mouse movement.
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
	 * Updates an object's position by adding a specified vector multiplied by an amount
	 * to its current transformation's position. The new position is set on the transformation,
	 * effectively moving the object along a direction for a given distance. Vector
	 * multiplication scales the direction vector.
	 *
	 * @param dir 3D direction vector used to calculate the new position of an object by
	 * multiplying it with the specified amount (`amt`).
	 *
	 * @param amt scalar multiplier applied to the direction vector `dir`, determining
	 * the magnitude of movement in that direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
