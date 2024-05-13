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
 * of the provided Java code is responsible for creating and rendering a 3D scene
 * using the Android framework. It sets up the graphics display, loads a shader,
 * creates a camera, and defines a world. The loop function updates the position of
 * the camera and renders the 3D scene using the shader and the World object. The
 * exit function is called to exit the program when the user presses the escape key.
 */
public class Main {
	
	/**
	 * initializes display and graphics libraries, enters an endless loop, and exits with
	 * a successful status code.
	 * 
	 * @param args program's command-line arguments passed by the
	 * operating system when the program is launched.
	 * 
	 * 	- `args`: an array of strings representing command-line arguments passed to the
	 * program.
	 * 	- Length: The number of elements in the `args` array, which is always equal to
	 * the number of command-line arguments passed to the program.
	 * 	- Elements: Each element in the `args` array represents a separate command-line
	 * argument, which can be accessed using the corresponding index (e.g., `args[0]`).
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
	 * sets up a display with a resolution of 1920x1080, enables vsync, and prints the
	 * GL version number to the console.
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
	 * initializes the OpenGL context for a 3D graphics application. It sets up camera
	 * parameters, loads a shader, and creates a texture and vertices for rendering a 3D
	 * scene.
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
	 * initializes a timer, then enters a while loop that runs until the user requests
	 * closure or presses the escape key. The function updates the title and FPS display,
	 * renders the game scene, and updates the display before checking if the loop should
	 * continue.
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
	 * updates the position of an object (w) based on input from a component (c) and a
	 * time step (dt).
	 * 
	 * @param dt time interval since the last update of the game state, which is used to
	 * calculate the change in position for the objects in the scene.
	 */
	private static void update(float dt) {
		c.processInput(dt, 5, .3f);
		w.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());
	}

	/**
	 * renders a 3D scene using the `GLSL` shader language, by setting uniform values and
	 * calling the `draw()` method of each object in the scene.
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
	 * terminates the Java application with a specified exit status, which is then
	 * propagated to the operating system.
	 * 
	 * @param status exit code for the program, which is passed to the `System.exit()`
	 * method to terminate the program with the specified status.
	 */
	private static void exit(int status) {
		System.exit(status);
	}
}
