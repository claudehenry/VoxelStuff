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
 * Initializes and sets up a 3D graphics application using LWJGL. It creates a window
 * with a specified display mode and enables various OpenGL features. The main loop
 * updates the game state, clears the screen, renders the scene, and checks for user
 * input and quit conditions.
 */
public class Main {
	/**
	 * Initializes display and GL, then enters a loop before exiting the program with
	 * code 0. It appears to be an entry point for a graphical application that uses
	 * OpenGL, responsible for setting up the environment and running the main event loop.
	 *
	 * @param args command-line arguments passed to the Java application, which can be
	 * accessed within the `main` method for processing or validation purposes.
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
	 * Initializes a display with specific settings. It sets the display mode to 1920x1080
	 * and creates a new display context with forward-compatible and profile core attributes.
	 * The function also enables vertical sync and prints the OpenGL version to the console.
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
	 * Initializes graphics settings and creates various objects for a 3D application.
	 * It enables clear and depth testing, sets up camera, shader, and texture, and creates
	 * a world object. A cube is also defined by its vertices and indices but not rendered.
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
	 * Initializes and updates a timer, game loop logic, display settings, and rendering
	 * operations continuously until the game window is closed or the escape key is
	 * pressed. The timer's FPS, memory usage, and other data are displayed on the title
	 * bar.
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
	 * Processes user input and updates an object's position using its current transform
	 * values. The input processing involves a constant time step (dt) with a threshold
	 * of 5 units and a decay rate of 0.3. The object's position is updated accordingly
	 * based on the processed input.
	 *
	 * @param dt time elapsed since the last update, used to control the rate at which
	 * game logic is processed.
	 */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

	/**
	 * Binds a shader and renders 3D models using it.
	 * It calls another method (`w.render`) to render models.
	 * Model rendering is not fully implemented, with commented-out code suggesting
	 * previous implementation for rendering multiple models with different colors.
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
	 * Terminates the Java application by calling `System.exit`, passing an integer status
	 * code to indicate the exit reason. This method immediately stops all threads and
	 * exits the virtual machine, ensuring a clean shutdown.
	 *
	 * @param status 2-digit error code or exit status that the program returns when it
	 * terminates, indicating its state before exiting.
	 */
	private static void exit(int status) {
		System.exit(status);
	}
}
