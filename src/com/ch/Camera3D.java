package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is designed to manage camera movements in a 3D environment. It handles projection
 * matrix calculations and viewport adjustments based on screen size. The class also
 * processes input from the user to rotate and move the camera within the scene.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Retrieves a projection matrix from a camera object, storing it in a variable named
	 * `projection`. It then returns the stored matrix. The resulting matrix is presumably
	 * used for transformations or projections within a graphics context.
	 *
	 * @param data 4x4 matrix containing camera projection data that is returned by its
	 * `getAsMatrix4()` method and used to set the `projection` field.
	 *
	 * @returns a Matrix4f object representing a projection matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Adjusts various matrices to fit the current viewport size and aspect ratio. It
	 * updates the camera's aspect ratio, recalculates projection and view matrices, and
	 * sets the OpenGL viewport accordingly.
	 *
	 * @param width 2D viewport's width and is used to set the camera's aspect ratio and
	 * GL11 viewport size.
	 *
	 * @param height vertical dimension of the viewport and is used to calculate the
	 * aspect ratio for projection matrix calculation purposes.
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
	 * Encapsulates perspective projection parameters and provides a method to convert
	 * these parameters into a Matrix4f object representing a perspective projection
	 * matrix. It extends the CameraStruct class. The purpose is to facilitate camera
	 * setup and rendering in 3D graphics applications.
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
		 * Initializes and returns a perspective projection matrix with the specified field
		 * of view (fov), aspect ratio (aspect), near clipping plane (zNear), and far clipping
		 * plane (zFar) values. The function creates a new Matrix4f instance and configures
		 * it as a perspective projection matrix.
		 *
		 * @returns a 4x4 perspective matrix.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes user input to update object movement and rotation based on mouse and
	 * keyboard inputs. It applies speed scaling when left shift is pressed, updates
	 * camera orientation with mouse movements, and translates the object forward or
	 * backward, left or right based on keyboard input.
	 *
	 * @param dt time elapsed since the last frame update, used to calculate movement
	 * amount based on speed and time.
	 *
	 * @param speed 3D movement speed, which is scaled up by a factor of 10 when the left
	 * shift key is pressed and used to calculate the movement amount for each frame based
	 * on the elapsed time (`dt`).
	 *
	 * @param sens 3D sensitivity factor, which affects the rotation speed of the entity
	 * in response to mouse movement, with higher values resulting in faster rotations.
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
	 * Translates an object by a specified amount along a given direction vector. The
	 * translation is calculated by multiplying the direction vector by the amplitude and
	 * adding it to the current position of the object's transform.
	 *
	 * @param dir 3D direction vector that determines the movement direction of the object
	 * when multiplied by the specified amount.
	 *
	 * @param amt 3D distance to move along the specified direction, scaling the movement
	 * accordingly.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
