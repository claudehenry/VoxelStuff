package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is a subclass of the Camera class that extends the basic camera functionality with
 * additional features such as adjusting to the viewport size and processing input
 * from the mouse and keyboard. The class includes a protected inner class called
 * CameraStruct3D which contains the camera's intrinsic parameters and provides methods
 * for calculating the projection matrix and rotating the camera's position and orientation.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

 /**
  * calculates a matrix representing the projection of a 3D scene from a given camera's
  * perspective.
  * 
  * @param data 3D camera's projection matrix, which is used to calculate the final
  * projection matrix returned by the function.
  * 
  * 	- `getAsMatrix4()` is a method that returns the `Matrix4f` representation of the
  * input data.
  * 
  * @returns a Matrix4f object containing the projection matrix as specified by the
  * provided `CameraStruct` data.
  * 
  * The `Matrix4f` object returned by the function represents a 4D projection matrix
  * that is used to transform 3D points and vectors into screen space. It consists of
  * four rows and four columns, with each element representing a value in the range
  * [0, 1]. The matrix elements are manipulated using standard matrix operations, such
  * as addition, multiplication by a scalar, and multiplication by another matrix.
  * 
  * The returned matrix is a direct result of the `projection` variable assigned to
  * it, which is a `CameraStruct` object containing parameters defining the desired
  * projection transformation, such as the field of view, aspect ratio, and near and
  * far clipping planes.
  */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

 /**
  * adjusts the camera's projection matrix and view matrix to fit within the bounds
  * of a specified size window, while maintaining the aspect ratio of the camera's image.
  * 
  * @param width 2D viewport width of the current rendering context.
  * 
  * @param height 2D viewport size of the renderer's window, which is used to calculate
  * the perspective projection matrix and view matrix.
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
  * is a subclass of the CameraStruct class, which represents a 3D camera in a
  * mathematical format. The class has several fields and methods that allow for
  * manipulation of the camera's position, orientation, and perspective projection matrix.
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
   * generates a matrix representing a perspective projection, where the focal length,
   * aspect ratio, near and far distances are specified.
   * 
   * @returns a matrix representation of a perspective projection transformation.
   * 
   * 	- The Matrix4f object is initialized with Perspective transformation parameters
   * fov, aspect, zNear, and zFar.
   * 	- This matrix represents a perspective projection view matrix, which maps 3D
   * points to 2D screen coordinates based on their distance from the viewer's eye.
   * 	- The Field of View (FOV) parameter determines the maximum angle that can be seen
   * in the horizontal direction, measured in radians. A larger FOV means a wider field
   * of view.
   * 	- The Aspect parameter represents the ratio of the screen's width to height. This
   * affects how the projection is scaled along the X and Y axes.
   * 	- The Near and Far parameters represent the distances from the eye to the near
   * and far limits of the projection, respectively. These values determine the minimum
   * and maximum distances that can be represented in the projection.
   */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

 /**
  * processes input from the mouse and keyboard, rotating the object based on sensor
  * input and speeding up movement when the shift key is pressed.
  * 
  * @param dt time step of the simulation, which is used to calculate the movement of
  * the entity based on its speed and sensitivity.
  * 
  * @param speed 3D movement speed of the object being controlled by the user, which
  * is multiplied by the time step `dt` to determine the total distance moved during
  * each frame.
  * 
  * @param sens sensitivity of the movement, which determines how much the character
  * will move in response to a given input from the user.
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
  * moves the object in the specified direction by the given amount.
  * 
  * @param dir 3D direction in which the object should move, with its magnitude being
  * multiplied by the specified amount (`amt`) to determine the new position of the object.
  * 
  * 	- `dir`: A `Vector3f` object representing the direction to move in 3D space. It
  * has three attributes: `x`, `y`, and `z`, which correspond to the coordinates of
  * the movement along the direction vector.
  * 
  * @param amt amount of movement along the specified direction, which is added to the
  * current position of the transform.
  */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
