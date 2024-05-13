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
 * in this Java program implements a 3D graphics rendering engine using the OpenGL
 * API. It sets up a camera, loads a shader and a texture, and creates a world object
 * to render 3D chunks. The loop method updates the camera position, renders the
 * scene, and displays the frame rate. The update method processes input events and
 * updates the world position based on the input. The render method draws the 3D
 * chunks using the shader and texture.
 */
public class Main {
	
	/**
	 * initializes display and GL libraries, loops, and exits with a successful status
	 * code (0).
	 * 
	 * @param args 1 or more command line arguments passed to the program by the user,
	 * which are used to initialize and configure the graphical interface and loop.
	 * 
	 * 	- `String[] args`: An array of strings that contains the command-line arguments
	 * passed to the program.
	 * 	- Length: The number of elements in the `args` array, which is equal to the number
	 * of command-line arguments passed to the program.
	 * 	- Elements: Each element in the `args` array represents a separate command-line
	 * argument passed to the program.
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
	 * the version of GL to the console.
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
	 * initializes the OpenGL context for a 3D graphics application. It sets up the camera,
	 * loads a texture, and creates a world object.
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
	 * updates a display's title and renders an image using the GL11 API every time the
	 * condition `!Display.isCloseRequested()` or `!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)`
	 * is met, until the condition `Display.isCloseRequested()` is met.
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
	 * updates the position of an object (`w`) based on input provided by a component
	 * (`c`). The update is performed using the transform's position and the input value
	 * multiplied by a constant.
	 * 
	 * @param dt time step value used to update the object's position and state in the simulation.
	 */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

	/**
	 * renders a 3D scene using a shader and models. It sets up the uniforms for the
	 * shader, binds the model matrices, and draws the models.
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
	 * terminates the Java process with the specified exit status.
	 * 
	 * @param status exit code that the `System.exit()` method will use to terminate the
	 * application.
	 */
	private static void exit(int status) {
		System.exit(status);
	}
}
