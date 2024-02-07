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
  * This function adjusts the viewport of a 3D camera to fit within a specified width
  * and height. It calculates the aspect ratio of the viewport and updates the projection
  * and view matrices.
  * 
  * 
  * @param { int } width - The `width` input parameter specifies the width of the viewport.
  * 
  * @param { int } height - The `height` input parameter specifies the height of the
  * viewport. It is used to set the aspect ratio of the camera's perspective projection
  * matrix based on the dimensions of the viewport.
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
   * This function creates a new Matrix4f object and initializes it with a perspective
   * projection matrix using the specified field of view (fov), aspect ratio (aspect),
   * near and far planes (zNear and zFar).
   * 
   * 
   * @returns { Matrix4f } This function returns a `Matrix4f` object representing a
   * perspective projection matrix. The returned matrix has the following properties:
   * 
   * 	- Perspective angle of view (fov)
   * 	- Aspect ratio (aspect)
   * 	- Near plane distance (zNear)
   * 	- Far plane distance (zFar)
   */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

 /**
  * This function processes user input to move the camera and rotates it according to
  * the mouse movements and keyboard keys pressed. It adjusts the speed of the camera
  * based on whether the LShift key is pressed or not.
  * 
  * 
  * @param { float } dt - The `dt` input parameter represents the time passed since
  * the last update (in floating-point seconds), and is used to control the movement
  * speed of the entity based on the elapsed time.
  * 
  * @param { float } speed - The `speed` input parameter multiplies the movement speed
  * of the object based on the LShift key being pressed or not. If the key is down
  * (i.e., true), the movement speed is increased by a factor of 10; otherwise (i.e.,
  * false), the default movement speed is used.
  * 
  * @param { float } sens - The `sens` input parameter controls the sensitivity of the
  * mouse movement. It is used to convert the pixel distance moved by the mouse into
  * a corresponding angular distance moveable by the player character.
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
  * This function moves the object by a certain amount ( Amt ) along a specified
  * direction ( Vector3f Dir). It updates the object's transform using setPos () method
  * and adds the product of direction vector and amplitude to the existing position.
  * 
  * 
  * @param { Vector3f } dir - The `dir` parameter is a `Vector3f` object that specifies
  * the direction of movement. It represents the direction and magnitude of the movement.
  * 
  * @param { float } amt - The `amt` input parameter is a float value that represents
  * the amount or distance to move the object. It multiplies the `dir` vector before
  * adding it to the current position of the object.
  */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}


