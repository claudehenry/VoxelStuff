package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * Is a subclass of Camera that provides functionality for 3D camera movement and projection.
 * It extends the basic camera functionality with features such as zooming, panning,
 * and object movement based on user input.
 * This class enables developers to create immersive 3D experiences by handling user
 * interaction and updating the camera's position and orientation accordingly.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * Calculates a projection matrix from a given CameraStruct and stores it in a local
	 * variable before returning it as a Matrix4f object. The result is a transformation
	 * matrix representing camera's view frustum or perspective projection. It retrieves
	 * the underlying 4x4 matrix data of the CameraStruct.
	 *
	 * @param data 3D camera data, which is used to retrieve and return its projection
	 * matrix as a Matrix4f object.
	 *
	 * @returns a Matrix4f object representing camera projection.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's aspect ratio and calculates its projection matrix based on
	 * the provided viewport dimensions. It also attempts to calculate the view matrix,
	 * ignoring any potential null pointer exceptions. Finally, it sets the OpenGL viewport
	 * to match the specified width and height.
	 *
	 * @param width 2D viewport width and is used to set the camera's aspect ratio and
	 * specify the viewport area for rendering.
	 *
	 * @param height 3D viewport's vertical dimension and is used to calculate the aspect
	 * ratio of the camera projection matrix.
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
	 * Represents a data structure for storing camera projection settings, allowing for
	 * creation of a perspective matrix based on these settings. It extends an existing
	 * struct class and enables the conversion of its data into a Matrix4f object. The
	 * class encapsulates properties relevant to 3D camera projections.
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
		 * Initializes a new perspective matrix. It takes four parameters: field of view
		 * (fov), aspect ratio (aspect), near clipping plane distance (zNear), and far clipping
		 * plane distance (zFar). The function returns the initialized perspective matrix as
		 * a Matrix4f object.
		 *
		 * @returns a 4x4 matrix representing a perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Updates the object's rotation and movement based on user input, including mouse
	 * movements for camera rotation and keyboard keys for direction control, adjusting
	 * speed as needed. It modifies the object's transform accordingly. Movement is
	 * relative to the object's forward direction.
	 *
	 * @param dt 3D rendering time step, which is used to calculate the movement amount
	 * based on the desired speed.
	 *
	 * @param speed 3D movement speed of an object and is used to calculate the amount
	 * of movement (`movAmt`) based on the time elapsed (`dt`).
	 *
	 * @param sens sensitivity of mouse rotation, where higher values result in more rapid
	 * camera rotation.
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
	 * Updates an object's position based on a specified direction and amount. It multiplies
	 * the given vector by the specified amount, adds the result to the current position,
	 * and sets the updated position as the new transform position. The transformation
	 * is applied instantaneously.
	 *
	 * @param dir 3D direction vector that specifies the movement direction and is scaled
	 * by the `amt` factor to determine the magnitude of movement.
	 *
	 * @param amt amount by which the position is incremented when moving in the specified
	 * direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
