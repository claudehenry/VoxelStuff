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
 * Initializes and sets up an LWJGL-based rendering environment with a camera, shader,
 * texture, and world object. It then enters a loop where it updates the camera's
 * input, updates the world position, clears the screen, renders the scene using the
 * shader and world objects, and checks for window close requests or escape key presses
 * to exit the application.
 */
public class Main {
	
	/**
	 * Initializes the display and OpenGL, then enters an infinite loop where it repeatedly
	 * renders graphics until terminated by a call to the `exit` method. The loop is not
	 * explicitly broken or limited, suggesting continuous rendering until program termination.
	 *
	 * @param args command-line arguments passed to the Java application when it is run,
	 * which can be accessed and processed within the main method.
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
	 * Initializes a display mode with a resolution of 1920x1080, creates a display context
	 * with pixel format and attribs, enables vertical sync, and prints the OpenGL version
	 * to the console.
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
	 * Initializes the OpenGL environment by setting clear color, enabling culling face
	 * and depth test. It also loads a shader, texture, and camera, and creates a world
	 * object. Additionally, it defines vertices and indices for a shape, but does not
	 * generate a model or chunk data.
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
	 * Initializes a timer, then enters an infinite loop that updates and renders the
	 * display until a close request or the escape key is pressed. It displays the current
	 * FPS, memory usage, and clears the screen before rendering and updating the display.
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
	 * Processes input and updates the position of an object using a specified delta time.
	 * It calls the `processInput` method on a `c` object, passing in the delta time, and
	 * then updates the position of another object `w` based on the position of `c`.
	 *
	 * @param dt delta time, used to update the game logic and physics calculations based
	 * on the elapsed time since the last frame.
	 */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

	/**
	 * Binds a shader and then renders a 3D scene by rendering each element in a 4x4x4
	 * array of objects, using uniform color and model view projection matrices. Finally,
	 * it calls another render method on an object named `w`.
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
	 * Terminates the Java Virtual Machine (JVM) with a specified exit status. It calls
	 * the `System.exit` method, passing the provided integer value as an argument to
	 * indicate the termination status. This allows programs to signal specific errors
	 * or conditions when exiting.
	 *
	 * @param status 16-bit integer that is passed as the exit value to the operating
	 * system when the program terminates.
	 */
	private static void exit(int status) {
		System.exit(status);
	}
}
