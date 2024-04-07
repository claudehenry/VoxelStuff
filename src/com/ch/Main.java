package com.ch;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import com.ch.math.Vector3f;
import com.ch.voxel.World;

/**
 * in this Java program is responsible for rendering a 3D scene using the OpenGL API.
 * It sets up the GLFW window, creates a camera, loads a shader, and initializes a
 * world object. The loop method updates the position of the camera and renders the
 * scene using the shader and world object. The update method processes input events
 * and updates the position of the camera. The render method draws the 3D scene using
 * the world object's blocks.
 * 
 * Overall, this program is a basic implementation of a 3D rendering engine that can
 * handle simple 3D objects and block-based rendering.
 */
public class Main {
	
 /**
  * initializes display and GL resources, enters an event loop, and exits with a
  * successful return value of 0.
  * 
  * @param args 0 or more command line arguments passed to the program, which are
  * ignored in this case since `main` is a static method and does not have access to
  * external information.
  * 
  * 	- The `String[]` parameter `args` represents an array of strings passed as
  * command-line arguments to the program by the user.
  * 	- Each element in the array is a separate string argument that can be accessed
  * using its index (e.g., `args[0]`).
  * 	- The `length` property of `args` returns the number of elements in the array.
  * 
  * The function then proceeds to initialize display and GL resources, loop, and exit
  * with a status code of 0.
  */
	public static void main(String[] args) {
		
		initDisplay();
		initGL();
		loop();
		exit(0);
		
	}
	
	private static Model m;
	private static Shader s;
	private static Texture t;
	private static Camera3D c;
//	private static Chunk[][][] ch;
	private static World w;
	
 /**
  * sets up a display window with a resolution of 1920x1080, enables vsync, and prints
  * the GL version string.
  */
	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(1920, 1080));
			Display.create(new PixelFormat(), new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));
			Display.setVSyncEnabled(true);
			System.out.println(GL11.glGetString(GL11.GL_VERSION));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
 /**
  * initializes OpenGL and sets up the rendering environment for a 3D game. It sets
  * the clear color, enables culling and depth testing, loads a shader, creates a
  * camera, and defines a texture.
  */
	private static void initGL() {
		
		GL11.glClearColor(0.1f, 0.7f, 1f, 1);
		
		Mouse.setGrabbed(true);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		c = new Camera3D(70, 16.f/9, .03f, 1000);
		
		s = Shader.loadShader("res/shaders/default");
		
		t = new Texture("res/textures/block0.png");
		
		float[] vertices = {
			-.5f, -.5f, 0,
			-.5f,  .5f, 0,
			 .5f,  .5f, 0,
			 .5f, -.5f, 0,
			
		};
		int[] indices = {
				0, 1, 2, 0, 2, 3
		};
//		ch = new Chunk[4][4][4];
//		for (int i = 0; i < 4; i++)
//			for (int j = 0; j < 4; j++)
//				for (int k = 0; k < 4; k++) {
//					ch[i][j][k] = new Chunk(i, j, k);
//					ch[i][j][k].updateBlocks();
//					ch[i][j][k].genModel();
//				}
		w = new World();
		//m = c.genModel();//Model.load(vertices, indices);
		
		c.getTransform().setPos(new Vector3f(0, 0, 0));
		
	}
	
 /**
  * continuously updates and renders a 2D scene while displaying relevant information
  * about the program's performance, including the frame rate, memory usage, and the
  * ratio of used to total memory.
  */
	private static void loop() {
		
		Timer.init();
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			
			Timer.update();
			
			Display.setTitle("" + Timer.getFPS() + 
					/* "   " + c.getTransform().getPos().toString() +*/ "   " 
					+ ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + " of " + (Runtime.getRuntime().maxMemory() / 1048576));
			
			update(Timer.getDelta());
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			render();
			
			Display.update();
			
		}
		
	}
	
 /**
  * updates the position of an object (w) based on input (c.processInput) and time (dt).
  * 
  * @param dt time step value used to update the entity's position and state.
  */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

 /**
  * renders a 3D scene using a shader, binding and uniforming various model-view-projection
  * matrices, and drawing the models in the scene.
  */
	private static void render() {
		
//		Model.enableAttribs();
		
		s.bind();
//		for (int i = 0; i < 4; i++)
//			for (int j = 0; j < 4; j++)
//				for (int k = 0; k < 4; k++) {
//					float r = (4 - i) / 4f;
//					float g = j / 4f;
//					float b = k / 4f;
//					s.uniformf("color", r, g, b);
//					s.unifromMat4("MVP", (c.getViewProjection().mul(ch[i][j][k].getModelMatrix())));
//					ch[i][j][k].getModel().draw();
//				}
		
		w.render(s, c);
		
//		Model.disableAttribs();
	}
	
 /**
  * terminates the Java program with the specified exit status (0-255).
  * 
  * @param status value to be passed as an argument to the `System.exit()` method,
  * indicating the exit status of the program.
  */
	private static void exit(int status) {
		System.exit(status);
	}
}
