package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * This function adjusts the 3D camera's perspective and viewport to fit within a
	 * specified viewport of size (width x height) on the screen.
	 * 
	 * @param width The `width` input parameter represents the desired width of the
	 * viewport and is used to set the aspect ratio of the camera's perspective projection
	 * matrix.
	 * 
	 * @param height The `height` input parameter is used to adjust the viewport size
	 * based on the aspect ratio of the camera's rendering area. It represents the number
	 * of pixels that are present vertically.
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

	protected class CameraStruct3D extends CameraStruct {

		public float fov, aspect, zNear, zFar;

		public CameraStruct3D(float fov, float aspect, float zNear, float zFar) {
			this.fov = fov;
			this.aspect = aspect;
			this.zNear = zNear;
			this.zFar = zFar;
		}

		/**
		 * This function creates and returns a new Matrix4f object representing a perspective
		 * projection transformation with the specified fields of view (fov), aspect ratio
		 * (aspect), near (zNear) and far (zFar) plane distances.
		 * 
		 * @returns This function returns a `Matrix4f` object that represents a perspective
		 * transformation matrix. The matrix is initialized with the given field of view
		 * (fov), aspect ratio (aspect), near plane distance (zNear), and far plane distance
		 * (zFar) using the `initPerspective()` method of the `Matrix4f` class.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * This function processes user input to move the object. It takes into account the
	 * shift key (multiplies the movement speed by 10 when pressed), and allows for four
	 * possible directions of movement (forward/backward and left/right) based on the
	 * keyboard keys pressed. It then updates the object's position using the movement
	 * amount and direction.
	 * 
	 * @param dt The `dt` input parameter represents the elapsed time since the last
	 * frame. In this function it is used to scale the speed of movement based on the
	 * amount of time passed. The faster the user presses the movement keys the more the
	 * character will move per unit of time.
	 * 
	 * @param speed The `speed` input parameter represents the multiplier for the movement
	 * speed of the character. When the LSHIFT key is pressed (as detected by the
	 * `Keyboard.isKeyDown()` method), the `speed` value is multiplied by 10.
	 * 
	 * @param sens The `sens` input parameter controls the sensitivity of the camera
	 * rotation based on mouse movement. A higher value for `sens` will cause the camera
	 * to rotate more sharply when the user moves the mouse.
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
	 * This function takes two parameters `dir` and `amt`, and moves the object's position
	 * by `amt` units along the direction specified by `dir`.
	 * 
	 * @param dir The `dir` parameter is a `Vector3f` object that represents the direction
	 * of movement. It controls the direction along which the object will be moved when
	 * the function is called.
	 * 
	 * @param amt The `amt` input parameter represents a scaling factor for the direction
	 * vector passed as `dir`. It multiplies the `dir` vector by the specified amount
	 * before adding it to the current position.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}

