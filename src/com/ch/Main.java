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
 * Initializes and sets up an OpenGL display with camera and lighting, loads shaders
 * and textures, and creates a world object. It then enters a loop where it updates
 * the scene based on user input and renders the world using the loaded shaders and
 * textures. The program continues to run until the user closes the window or presses
 * the escape key.
 */
public class Main {
	
	/**
	 * Initializes display and OpenGL, enters a loop for continuous execution, and
	 * terminates with exit status 0. The sequence of actions involves setup followed by
	 * repetitive execution, concluding with termination.
	 *
	 * @param args array of strings passed as command-line arguments to the Java program.
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
	 * Initializes display settings, sets display mode to a specified resolution, creates
	 * a pixel format, and enables vertical sync. It also prints out the version string
	 * of the OpenGL library. If any error occurs during initialization, it catches and
	 * prints the exception.
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
	 * Initializes graphics settings, enabling culling and depth testing. It creates a
	 * camera object, loads a shader and texture, and sets up a world object. Additionally,
	 * it defines vertex coordinates for a shape and indices to render the shape.
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
	 * display, updating the title with FPS, memory usage, and other data, until either
	 * the close button is requested or the escape key is pressed.
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
	 * Processes input data with a given delta time, updates object positions based on
	 * camera transform values. It utilizes an instance of `c` and `w`, likely a camera
	 * and a world object respectively. The processing involves calling methods `processInput`,
	 * `getTransform`, and `updatePos`.
	 *
	 * @param dt delta time, which is used as an input to the `processInput` method of
	 * the `c` object for updating its state over time.
	 */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

	/**
	 * Renders a scene by binding a shader and setting uniforms for color and MVP matrices.
	 * It then calls another function `w.render` to render objects, and finally unbinds
	 * the shader.
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
	 * Terminates the Java virtual machine with a specified exit status. The provided
	 * status is passed to the operating system, which can be used by scripts or other
	 * programs to determine the program's termination state.
	 *
	 * @param status 16-bit integer exit status to be passed back to the parent process
	 * when the Java application terminates using the `System.exit` method.
	 */
	private static void exit(int status) {
		System.exit(status);
	}
}
