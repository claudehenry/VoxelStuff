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
 * Initializes display settings, sets up OpenGL environment and rendering context,
 * loads shaders, textures, and world data, and enters an event-driven loop to render
 * and update the graphics. The loop continuously updates and renders the scene until
 * the user closes the window or presses the escape key.
 */
public class Main {
	
	/**
	 * Initializes a display, sets up OpenGL, enters a loop, and then exits with status
	 * code 0. It appears to be the entry point of an application that utilizes OpenGL
	 * for graphical rendering. The loop presumably contains the game or simulation logic.
	 *
	 * @param args command-line arguments passed to the Java program when it is executed,
	 * which are an array of strings that can be accessed within the main method.
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
	 * Initializes the display settings for a Java-based graphics application using LWJGL.
	 * It sets the display mode to a resolution of 1920x1080, creates a new context with
	 * forward compatibility and profile core enabled, and enables vertical sync.
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
	 * Initializes OpenGL settings, sets up camera and shaders, loads a texture, defines
	 * vertex and index data for a model, and creates a world object without populating
	 * it with chunks or models. It then sets the initial position of the camera to (0,
	 * 0, 0).
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
	 * Initializes a timer and enters an infinite loop that updates the display, rendering
	 * game content while checking for window close or escape key presses. The function
	 * periodically clears the screen, renders graphics, and updates the display's title
	 * with FPS and system memory information.
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
	 * Processes input, updating a value based on the elapsed time, then updates the
	 * position of an object using its current transform and position values. The input
	 * processing is performed by the `c` object's `processInput` method.
	 *
	 * @param dt time difference since the last update, used to process game inputs and
	 * updates positions accordingly.
	 */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

	/**
	 * Binds a shader and renders a scene by setting uniforms for color and MVP matrices,
	 * and then calling the `draw` method on model instances contained in a 4x4x4 grid.
	 * The rendering is performed by a separate object `w`.
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
	 * Terminates the Java program with a specified exit status. It calls the `System.exit`
	 * method, which forces the Java Virtual Machine (JVM) to stop running and return the
	 * given status code to the operating system. This allows for controlled termination
	 * of the program.
	 *
	 * @param status 16-bit integer that is passed as the exit value to the parent process
	 * or the operating system when the Java program terminates.
	 */
	private static void exit(int status) {
		System.exit(status);
	}
}
