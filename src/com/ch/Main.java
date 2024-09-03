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
 * Initializes an OpenGL context and enters a main loop where it continuously updates
 * and renders the game world.
 * It uses LWJGL for graphics rendering and handles user input through keyboard and
 * mouse events.
 * The class ultimately exits the application when the escape key is pressed or the
 * window is closed.
 */
public class Main {
	
	/**
	 * Initializes a display and OpenGL, then enters a loop where it executes continuously
	 * until an exit condition is met. The program terminates with a successful status
	 * code of 0.
	 *
	 * @param args command line arguments passed to the program, which can be accessed
	 * and processed within the code.
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
	 * Initializes and sets up a display mode for a graphical application, enabling
	 * vertical sync and printing the GL version to the console. It creates an OpenGL
	 * context with specific attributes and throws exceptions if LWJGL errors occur during
	 * initialization. A display is created at 1920x1080 resolution.
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
	 * Initializes a 3D graphics environment by setting OpenGL parameters, enabling
	 * features such as culling and depth testing, loading shaders and textures, and
	 * creating camera, world, and chunk objects. It also sets the camera position to the
	 * origin.
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
	 * Initializes and updates a timer, displays game information, updates and renders
	 * graphics, and repeats until the user closes the window or presses the Escape key.
	 * It manages the game loop, handling input and output. The function is designed for
	 * a graphics-intensive application.
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
	 * Processes input, updates a camera's position based on its current transform. It
	 * takes a delta time value and uses it to process user input and update the world's
	 * position accordingly. The camera's transformation data is retrieved and used for
	 * updating.
	 *
	 * @param dt time interval since the last update, used to control the rate of processing
	 * and updating game objects within the function.
	 */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

	/**
	 * Binds an OpenGL shader object and renders a world scene using it. The render method
	 * is called on an instance of `w`, which appears to be a game or graphics object
	 * that has been passed scene-related objects as parameters, including a camera `c`.
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
	 * Terminates the Java application immediately with a specified exit status. It
	 * delegates the actual exit operation to the underlying system via the `System.exit`
	 * method, passing the provided status code as an argument. The JVM exits gracefully
	 * upon receiving this call.
	 *
	 * @param status 2-digit integer value passed to the JVM for termination, indicating
	 * the program's execution status or exit code.
	 */
	private static void exit(int status) {
		System.exit(status);
	}
}
