package com.ch;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.ch.math.Matrix4f;
import com.ch.math.Vector3f;

/**
 * is a custom camera class that extends the Camera class and provides additional
 * functionality for a 3D environment. It has a constructor that takes fov, aspect,
 * zNear, and zFar parameters to initialize the camera's perspective projection matrix.
 * The class also has an adjustToViewport method that updates the camera's projection
 * matrix based on the viewport dimensions and tries to calculate the view matrix.
 * The processInput method processes input from the mouse and keyboard and applies
 * transformations to the camera's position, rotation, and scale.
 */
public class Camera3D extends Camera {

	public Camera3D(float fov, float aspect, float zNear, float zFar) {
		super(new Matrix4f());
		this.values = new CameraStruct3D(fov, aspect, zNear, zFar);
		calculateProjectionMatrix(values);
	}

	/**
	 * calculates a projection matrix based on input from the `CameraStruct` data object.
	 * The returned matrix is expected to be a `Matrix4f` object.
	 * 
	 * @param data 3D camera structure that contains the parameters required to calculate
	 * the projection matrix.
	 * 
	 * 	- `projection`: A `Matrix4f` object representing the projection matrix as defined
	 * by the `CameraStruct`.
	 * 
	 * The function returns the `projection` matrix.
	 * 
	 * @returns a Matrix4f object containing the projection matrix.
	 * 
	 * The `Matrix4f` object `projection` contains the 4x4 homography matrix that represents
	 * the perspective projection of a 3D scene from a camera's point of view. Specifically,
	 * it maps the 3D points in screen space to their corresponding 2D positions on the
	 * image plane.
	 * 
	 * The matrix elements represent the scaling and translation factors between the 3D
	 * points and their projected counterparts on the image plane. These matrices are
	 * used in various computer vision applications such as object recognition, tracking,
	 * and rendering.
	 * 
	 * In summary, the `calculateProjectionMatrix` function returns a 4x4 homography
	 * matrix that represents the perspective projection of a 3D scene from a camera's
	 * point of view.
	 */
	@Override
	public Matrix4f calculateProjectionMatrix(CameraStruct data) {
		return (projection = data.getAsMatrix4());
	}

	/**
	 * adjusts the camera's aspect ratio and projection matrix to fit within a viewport
	 * of specified width and height. It then sets the viewport size and calls
	 * `calculateViewMatrix()` method.
	 * 
	 * @param width 2D viewport width to which the 3D camera's projection matrix should
	 * be adjusted.
	 * 
	 * @param height 2D viewport dimension of the component being rendered, which is used
	 * to calculate the aspect ratio and projection matrix for proper perspective correction
	 * and viewport transformation.
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
	 * is an extension of the CameraStruct class with additional fields for floating-point
	 * values representing the field of view (fov), aspect ratio, near and far distances.
	 * The getAsMatrix4() method returns a Matrix4f object initialized with a perspective
	 * projection matrix based on the provided fov, aspect, zNear, and zFar values.
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
		 * creates a `Matrix4f` object representing a perspective projection matrix, initialized
		 * with specified field of view (fov), aspect ratio, near and far distances.
		 * 
		 * @returns a 4x4 matrix representing a perspective projection view of the given field
		 * of view, aspect ratio, near and far distances.
		 * 
		 * 1/ The function returns a Matrix4f object, which represents a 4x4 matrix in
		 * homogeneous coordinates.
		 * 2/ The Matrix4f object is initialized with values from the perspective projection
		 * parameters fov, aspect, zNear, and zFar.
		 * 3/ The resulting Matrix4f object can be used to perform perspective transformations
		 * on 3D points or vectors.
		 */
		public Matrix4f getAsMatrix4() {
			return new Matrix4f().initPerspective(fov, aspect, zNear, zFar);
		}

	}

	/**
	 * processes mouse input and keyboard commands to move a transform. It rotates the
	 * transform based on the mouse position and keyboard shortcuts, and moves the transform
	 * along a specified direction.
	 * 
	 * @param dt time step, which determines the speed at which the object moves based
	 * on the input from the keyboard keys.
	 * 
	 * @param speed 3D movement speed of the object being controlled, which is multiplied
	 * by the time elapsed (represented by `dt`) to calculate the total distance moved
	 * during each frame.
	 * 
	 * @param sens sensitivity of the character's movement in response to mouse input,
	 * and it is multiplied with the delta time (`dt`) to adjust the movement speed.
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
	 * modifies the position of an object by adding a specified amount to its current
	 * position in the direction of a given vector.
	 * 
	 * @param dir 3D direction in which the object should be moved, with the magnitude
	 * of the movement determined by the `amt` parameter.
	 * 
	 * 	- `dir`: A `Vector3f` object representing a 3D direction vector, containing the
	 * x, y, and z components.
	 * 
	 * @param amt amount of movement to be applied to the object's position along the
	 * specified direction.
	 */
	private void move(Vector3f dir, float amt) {
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

}
