package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera Class that provides additional functionality for
 * manipulating a camera's position and orientation in a 3D space. It includes a
 * perspective projection matrix and allows for adjusting the viewport size, as well
 * as processing input from the mouse and keyboard to rotate and move the camera.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * takes a `CameraStruct` object and returns a `Matrix4f` object representing the
	 * projection matrix.
	 * 
	 * @param data 3D camera parameters, which are used to calculate the projection matrix.
	 * 
	 * 	- `projection`: This is a `Matrix4f` object that represents the camera's projection
	 * matrix.
	 * 	- `getAsMatrix4()`: This method returns the `Matrix4f` representation of the input
	 * data, which is then assigned to the `projection` variable.
	 * 
	 * @returns a `Matrix4f` object representing the camera's projection matrix.
	 * 
	 * The output is a Matrix4f object representing the projection matrix.
	 * This matrix determines how the 3D scene will be projected onto the 2D image plane.
	 * It consists of four rows and four columns, with elements that represent the scaling,
	 * rotation, and translation of the 3D coordinates to the 2D image plane.
	 * The Matrix4f object has several properties such as getAsMatrix4(), set(), etc.
	 * that can be used to manipulate or access the matrix's elements.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's projection and view matrices to fit within the specified
	 * viewport dimensions. It also sets the viewport dimensions and updates the camera's
	 * position using the calculated matrix values.
	 * 
	 * @param width 2D viewport width for the 3D object being rendered.
	 * 
	 * @param height 2D viewport size in pixels and is used to calculate the camera's
	 * projection matrix and view matrix, as well as to set the viewport size in the
	 * OpenGL context.
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
	 * is a custom class that extends the CameraStruct class and provides additional
	 * functionality for a 3D camera. It has four fields: fov, aspect, zNear, and zFar,
	 * which are used to calculate the perspective projection matrix. The class also
	 * provides a method getAsMatrix4() that returns a Matrix4f object representing the
	 * perspective projection matrix.
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
		 * initializes a `Matrix4f` object with a perspective projection matrix based on the
		 * given field of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection view of the input scene.
		 * 
		 * The returned matrix is an instance of the `Matrix4f` class in Java, which represents
		 * a 4D homogeneous transformation matrix. The matrix contains four elements: the
		 * identity element (1, 0, 0, 0), the view direction (0, 0, -2*fov/w, 0), the z-buffer
		 * coordinates (0, 0, 0, zNear), and the far clip coordinate (0, 0, 0, zFar).
		 * 
		 * The fov attribute represents the field of view angle in radians, while the aspect
		 * attribute is the aspect ratio of the image. The zNear and zFar attributes represent
		 * the near and far clipping planes, respectively.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes input from the mouse and keyboard, rotating and moving a transform based
	 * on user input. It also multiplies the speed of movement by 10 when the 'LShift'
	 * key is pressed.
	 * 
	 * @param dt time step for which the function is being called, and it is used to
	 * calculate the movement of the object based on its speed and sensitivity.
	 * 
	 * @param speed 3D movement speed of the object being controlled by the function,
	 * which is multiplied by the time interval `dt` to determine the total distance moved.
	 * 
	 * @param sens sensitivity of the mouse input, which affects the amount of rotation
	 * applied to the object's transform when the user moves the mouse cursor.
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
	 * moves the position of an object by a specified distance and direction, using the
	 * transform's `setPos()` method.
	 * 
	 * @param dir 3D direction in which the object should be moved, with the magnitude
	 * of the movement specified by the `amt` parameter.
	 * 
	 * 	- `dir` is a `Vector3f` class instance representing a 3D vector with x, y, and z
	 * components.
	 * 	- The `mul()` method of `dir` performs multiplication between the vector and a
	 * scalar value, in this case `amt`. This operation adds the scalar value to the
	 * vector's components, resulting in a new vector representation.
	 * 
	 * The function then applies this modified vector to the position component of the
	 * transform by using the `setPos()` method of the transform object, which stores the
	 * position of the game entity.
	 * 
	 * @param amt amount of movement to be applied along the specified direction, as
	 * indicated by the `dir` parameter.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
