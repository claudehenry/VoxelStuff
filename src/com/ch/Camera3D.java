package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is an extension of the Camera class that processes mouse and keyboard input to
 * rotate and move an object based on its transform. It also multiplies the movement
 * speed by 10 when the 'LShift' key is pressed. The processInput method calculates
 * the movement amount based on the user's input and moves the object along a specified
 * direction using the getTransform().add() method.
 */
public class Camera3D extends Camera {
	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a matrix representing the camera's perspective projection, based on the
	 * given `CameraStruct` data.
	 * 
	 * @param data 3D camera parameters, which are used to compute the perspective
	 * projection matrix.
	 * 
	 * 	- `getAsMatrix4()` is a method that returns a `Matrix4f` object representing the
	 * camera's projection matrix.
	 * 
	 * The function then assigns this projection matrix to a variable called `projection`.
	 * 
	 * @returns a Matrix4f object representing the projection matrix as defined by the
	 * input `CameraStruct` data.
	 * 
	 * The `Matrix4f` object that is returned represents a 4x4 matrix, which encodes the
	 * perspective projection transformation. The matrix has the following elements:
	 * 
	 * 	- The upper left 3x3 submatrix represents the viewport transform, with the origin
	 * at the center of the viewport and the dimensions of the viewport set by the
	 * `viewport` field in the `CameraStruct`.
	 * 	- The lower right 1x4 submatrix represents the projection transform, which maps
	 * the 3D scene coordinates to the image plane. The elements of this submatrix are
	 * determined by the `projection` field in the `CameraStruct`.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * Updates the camera's matrices to fit a given viewport size and aspect ratio. It
	 * sets the camera's aspect ratio and calculates the projection and view matrices,
	 * then sets the viewport size using `GL11.glViewport`.
	 * 
	 * @param width 2D viewport width for the adjustment of the 3D camera's projection matrix.
	 * 
	 * @param height vertical size of the viewport in pixels, which is used to calculate
	 * the aspect ratio and projection matrix for the 3D scene.
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
	 * is a subclass of the Camera Struct class and provides additional functionality for
	 * handling 3D camera movements. It has fields for fov, aspect, zNear, and zFar, which
	 * are used to calculate the perspective matrix, and a method for getting the perspective
	 * matrix as a Matrix4f object.
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
		 * initializes a `Matrix4f` object representing a perspective projection matrix with
		 * the specified field of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection matrix.
		 * 
		 * The `Matrix4f` object returned by the function is an instance of the Matrix4f
		 * class, which represents a 4x4 matrix in homogeneous coordinates.
		 * 
		 * The `initPerspective` method used to create the matrix sets the field of view (fov)
		 * in radians, the aspect ratio of the image, the near plane distance (zNear), and
		 * the far plane distance (zFar). These values determine the perspective projection
		 * of the 3D space, where objects appear smaller as they recede into the distance.
		 * 
		 * The matrix returned by the function is a representation of the viewing transformation
		 * that maps 3D points from the world coordinate system to the screen coordinate
		 * system, taking into account the perspective projection.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * Processes mouse and keyboard input to rotate and move an object based on its
	 * transform. It also multiplies the movement speed by 10 when the 'LShift' key is pressed.
	 * 
	 * @param dt time differential since the last frame, and is used to calculate the
	 * movement speed of the object based on the keyboard input.
	 * 
	 * @param speed 3D movement speed of the game object being processed, which is
	 * multiplied by the time elapsed (represented by `dt`) to determine the actual
	 * distance moved.
	 * 
	 * @param sens sensitivity of the mouse input, which affects how much the object
	 * rotates based on the user's mouse movement.
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
	 * Moves the object by a specified distance along a given direction.
	 * 
	 * @param dir 3D direction of movement, which is added to the current position of the
	 * transform entity using the `add()` method, resulting in a new position after the
	 * movement has taken place.
	 * 
	 * @param amt 3D displacement amount of the object along the specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
