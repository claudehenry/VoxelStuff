package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera class that provides additional functionality for
 * manipulating a camera's position and orientation in 3D space. It includes a
 * calculateProjectionMatrix method, which calculates the camera's projection matrix
 * based on its fov, aspect, zNear, and zFar parameters. The class also has a
 * adjustToViewport method that adjusts the camera's projection matrix based on the
 * viewport dimensions, and a processInput method that processes input events such
 * as mouse movements and keyboard shortcuts to move the camera around.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a 4x4 matrix representing a perspective projection, based on the input
	 * `CameraStruct` data.
	 * 
	 * @param data 3D camera structure containing the parameters for calculating the
	 * projection matrix.
	 * 
	 * 	- `projection`: The input `data` contains a 4x4 matrix representation of the
	 * camera's projection parameters.
	 * 	- `getAsMatrix4()`: This method returns the `data` object as a 4x4 matrix representation.
	 * 
	 * @returns a Matrix4f object representing the camera's projection matrix.
	 * 
	 * The output is a `Matrix4f` object representing a 4x4 matrix. This matrix contains
	 * the projection parameters in the form of homogeneous coordinates, where each element
	 * represents a value between 0 and 1 that determines the perspective projection of
	 * a 3D point on the image plane.
	 * 
	 * The matrix has four rows, each representing a different component of the projection
	 * transformation: translation, rotation, scaling, and shearing. Each row is a 4x1
	 * vector containing the corresponding values for that component.
	 * 
	 * The matrix elements are arranged in a specific order, with the upper-left element
	 * being the translation vector, followed by the rotation, scaling, and shearing
	 * vectors in the correct order. This arrangement allows for efficient computation
	 * of the perspective projection of a 3D point on the image plane using the matrix.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts a 3D object's projection and view matrices to fit within the specified
	 * width and height of the viewport.
	 * 
	 * @param width 2D viewport width.
	 * 
	 * @param height 2D screen size of the viewport into which the 3D scene will be rendered.
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
	 * is a custom implementation of the Camera Struct in Java, which extends the basic
	 * Camera Struct with additional functionality for handling perspective projection
	 * and viewport adjustments. The class provides a Matrix4f object for representing
	 * the camera's projection matrix, and offers methods for calculating the matrix based
	 * on the camera's parameters and adjusting it to the viewport size.
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
		 * returns a matrix representing a perspective projection, where the field of view
		 * (fov), aspect ratio, near and far distances are set.
		 * 
		 * @returns a matrix representing a perspective projection with field of view (FOV),
		 * aspect ratio, near and far distances.
		 * 
		 * 	- `fov`: The field of view (FOV) of the matrix, representing the angle of the
		 * perspective projection in radians.
		 * 	- `aspect`: The aspect ratio of the viewport, which determines how much the matrix
		 * stretches or shrinks the image along the x and y axes.
		 * 	- `zNear` and `zFar`: The near and far clipping planes of the matrix, used to
		 * restrict the region of space that is visible in the projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes input from the mouse and keyboard, rotating and moving a transform based
	 * on user input.
	 * 
	 * @param dt time step, which is used to calculate the movement of the object based
	 * on its speed and sensitivity.
	 * 
	 * @param speed 3D movement speed of the object being controlled, and it is multiplied
	 * by the time interval `dt` to determine the distance traveled during that time.
	 * 
	 * @param sens sensitivity of the character's movement in response to user input,
	 * which affects the amount of rotation applied to the character's position when the
	 * shift key is pressed.
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
	 * moves a transform's position by a specified amount in a given direction, using the
	 * object's getTransform() method to access its transformation matrix and perform the
	 * translation.
	 * 
	 * @param dir 3D direction in which the object should be moved, with its x, y, and z
	 * components multiplied by the specified amount (`amt`).
	 * 
	 * 	- `dir`: A Vector3f object representing a 3D direction, with components in x, y,
	 * and z directions.
	 * 	- `amt`: An integer value representing the amount to move along the specified direction.
	 * 
	 * @param amt amount of movement to be applied to the object's position along the
	 * specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
