package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * TODO
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

 /**
  * takes a `CameraStruct` object as input and returns a `Matrix4f` object representing
  * the camera's projection matrix.
  * 
  * @param data 3D camera structure, which contains the necessary information for
  * calculating the perspective projection matrix.
  * 
  * 1/ `getAsMatrix4()` returns a `Matrix4f` object representing the projection matrix
  * as a 4x4 homogeneous transformation matrix.
  * 2/ `projection` is a variable that stores the resulting projection matrix after
  * applying the necessary calculations.
  * 
  * @returns a Matrix4f object representing the camera's projection matrix.
  * 
  * 1/ Matrix4f structure: The function returns a `Matrix4f` object representing the
  * projection matrix.
  * 2/ Data member access: The function uses the `getAsMatrix4()` method to retrieve
  * the projection matrix from the `data` parameter.
  * 3/ Projection matrix creation: The function creates a new `Matrix4f` object that
  * represents the projection matrix, as specified by the `data` parameter.
  */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

 /**
  * updates the view matrix and sets the viewport size based on the provided width and
  * height parameters, ensuring the aspect ratio is maintained.
  * 
  * @param width 2D viewport size in pixels for which the view matrix is being calculated
  * and displayed.
  * 
  * @param height 2D viewport size, which is used to calculate the perspective projection
  * matrix and to set the viewport dimensions in GL11.glViewport().
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
  * TODO
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
   * returns a matrix object representing a perspective projection matrix with specified
   * field of view (fov), aspect ratio, near and far distances.
   * 
   * @returns a 4x4 matrix representation of a perspective projection transformation.
   * 
   * 1/ The returned Matrix4f object represents a 4x4 matrix that contains the perspective
   * projection matrix in homogeneous coordinate format.
   * 2/ The elements of the matrix are initialized using the `initPerspective` method
   * with the given field of view (fov), aspect ratio (aspect), near clipping plane
   * (zNear), and far clipping plane (zFar).
   * 3/ The resulting matrix is a 4x4 homogeneous transformation matrix, which means
   * that it can be used to perform transformations in 4D space using homogeneous coordinates.
   */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

 /**
  * processes input from the mouse and keyboard to update the rotation and translation
  * of an object. It rotates the object based on mouse wheel and shift key inputs, and
  * moves the object along the forward axis based on W, S, A, and D keys.
  * 
  * @param dt time step of the simulation, which determines how much the game object
  * moves during each iteration of the program.
  * 
  * @param speed 3D movement speed of the object being controlled, which is multiplied
  * by the time step (`dt`) to determine the total movement distance.
  * 
  * @param sens sensitivity of the character's movement in response to mouse movements,
  * which determines how much the character will move based on the user's mouse input.
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
  * updates the position of an object by adding a vector direction to its current
  * position, scaled by a given amount.
  * 
  * @param dir 3D direction of movement, specifying how much to move the object in
  * that direction.
  * 
  * 	- `dir` is a `Vector3f` object representing a 3D vector with three components (x,
  * y, z) that can be used to specify a direction or movement.
  * 	- The `mul()` method is used to multiply the `dir` vector by a scalar value
  * (`amt`), which determines the amount of movement in the specified direction.
  * 
  * @param amt amount of movement along the specified direction, as indicated by the
  * `dir` parameter.
  */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
