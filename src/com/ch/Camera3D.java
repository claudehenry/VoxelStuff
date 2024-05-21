package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera Class that includes additional functionality for
 * handling 3D camera inputs and calculations. It has a constructor that takes fov,
 * aspect, zNear, and zFar parameters to initialize the camera's projection matrix.
 * The class also has a method adjustToViewport that adjusts the camera's projection
 * matrix based on the viewport dimensions. Additionally, the class has a protected
 * inner class CameraStruct3D that contains the camera's projection matrix, which is
 * used in the calculateProjectionMatrix method.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a projection matrix based on input data from a `CameraStruct`.
	 * 
	 * @param data 3D camera information that is used to calculate the projection matrix.
	 * 
	 * 	- `getAsMatrix4()` is an instance method that returns a `Matrix4f` object
	 * representing the 4x4 homogeneous transformation matrix for the camera.
	 * 
	 * @returns a Matrix4f object representing the projection matrix based on the provided
	 * camera data.
	 * 
	 * The `Matrix4f` object that is returned represents the projection matrix in 3D
	 * space. It contains the necessary values for transforming 3D points and vectors
	 * into screen coordinates. The matrix has four rows and four columns, with each
	 * element representing a value in the range of -1 to 1.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's projection and view matrices to fit within the specified width
	 * and height of the viewport, while maintaining the aspect ratio of the scene.
	 * 
	 * @param width 2D viewport width.
	 * 
	 * @param height vertical dimension of the viewport for which the camera's projection
	 * and view matrices are calculated.
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
	 * is an extension of the CameraStruct class and provides additional functionality
	 * for handling 3D camera movements. It has fields for fov, aspect, zNear, and zFar,
	 * which are used to calculate the camera's perspective matrix. The class also provides
	 * a method for getting the camera's perspective matrix as a Matrix4f object.
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
		 * initializes a `Matrix4f` object with a perspective projection matrix based on field
		 * of view (`fov`), aspect ratio, near and far z-coordinates.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection.
		 * 
		 * 	- The `Matrix4f` object returned represents a 4x4 matrix with elements representing
		 * homogeneous coordinates.
		 * 	- The matrix is initialized using the `initPerspective` method, which sets the
		 * field of view (fov), aspect ratio (aspect), near clip plane (zNear), and far clip
		 * plane (zFar) values.
		 * 	- The resulting matrix is a perspective projection matrix, used to transform 3D
		 * points into screen coordinates for rendering purposes.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes input from the keyboard and mouse, applying rotations to an object's
	 * transform based on user input, and moving the object along a direction based on
	 * the keyboard inputs.
	 * 
	 * @param dt time step of the simulation, which is used to calculate the movement of
	 * the object based on its velocity.
	 * 
	 * @param speed 3D movement speed of the object being controlled by the player, and
	 * it is multiplied by the time interval `dt` to determine the total distance traveled
	 * during that time.
	 * 
	 * @param sens sensitivity of the movement, which determines how much the object will
	 * move based on the user's input.
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
	 * updates the position of an object by adding a directional vector multiplied by a
	 * scalar amount to its current position.
	 * 
	 * @param dir 3D direction in which the object should be moved, with the magnitude
	 * of the movement specified by the `amt` parameter.
	 * 
	 * 	- `dir`: A `Vector3f` object representing the direction of movement. It has three
	 * components: x, y, and z, which correspond to the horizontal, vertical, and forward
	 * motion, respectively.
	 * 	- `amt`: An integer value representing the amount of movement to be applied in
	 * the specified direction.
	 * 
	 * @param amt amount of movement to be applied to the object's position along the
	 * specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
