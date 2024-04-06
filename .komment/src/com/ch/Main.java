{"name":"Main.java","path":"src/com/ch/Main.java","content":{"structured":{"description":"A 3D rendering engine using LWJGL library for creating a window, initializing GL context, displaying image, and handling user input events. The main function creates a display, initializes GL, loads a default shader, loads a texture, generates a 3D model, and renders the scene. The code also includes an update function to handle user input and render the scene, and an exit function to close the application.","image":"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<!-- Generated by graphviz version 2.43.0 (0)\n -->\n<!-- Title: com.ch.Camera3D Pages: 1 -->\n<svg width=\"115pt\" height=\"82pt\"\n viewBox=\"0.00 0.00 115.00 82.00\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n<g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 78)\">\n<title>com.ch.Camera3D</title>\n<!-- Node1 -->\n<g id=\"Node000001\" class=\"node\">\n<title>Node1</title>\n<g id=\"a_Node000001\"><a xlink:title=\" \">\n<polygon fill=\"#999999\" stroke=\"#666666\" points=\"107,-19 0,-19 0,0 107,0 107,-19\"/>\n<text text-anchor=\"middle\" x=\"53.5\" y=\"-7\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.ch.Camera3D</text>\n</a>\n</g>\n</g>\n<!-- Node2 -->\n<g id=\"Node000002\" class=\"node\">\n<title>Node2</title>\n<g id=\"a_Node000002\"><a xlink:href=\"classcom_1_1ch_1_1Camera.html\" target=\"_top\" xlink:title=\" \">\n<polygon fill=\"white\" stroke=\"#666666\" points=\"100,-74 7,-74 7,-55 100,-55 100,-74\"/>\n<text text-anchor=\"middle\" x=\"53.5\" y=\"-62\" font-family=\"Helvetica,sans-Serif\" font-size=\"10.00\">com.ch.Camera</text>\n</a>\n</g>\n</g>\n<!-- Node2&#45;&gt;Node1 -->\n<g id=\"edge1_Node000001_Node000002\" class=\"edge\">\n<title>Node2&#45;&gt;Node1</title>\n<g id=\"a_edge1_Node000001_Node000002\"><a xlink:title=\" \">\n<path fill=\"none\" stroke=\"#63b8ff\" d=\"M53.5,-44.66C53.5,-35.93 53.5,-25.99 53.5,-19.09\"/>\n<polygon fill=\"#63b8ff\" stroke=\"#63b8ff\" points=\"50,-44.75 53.5,-54.75 57,-44.75 50,-44.75\"/>\n</a>\n</g>\n</g>\n</g>\n</svg>\n","items":[{"id":"ff92ae65-fa32-8f8f-0b44-d9c7d98910e5","ancestors":[],"type":"function","description":"TODO","name":"Main","code":"public class Main {\n\t\n\tpublic static void main(String[] args) {\n\t\t\n\t\tinitDisplay();\n\t\tinitGL();\n\t\tloop();\n\t\texit(0);\n\t\t\n\t}\n\t\n\tprivate static Model m;\n\tprivate static Shader s;\n\tprivate static Texture t;\n\tprivate static Camera3D c;\n//\tprivate static Chunk[][][] ch;\n\tprivate static World w;\n\t\n\tprivate static void initDisplay() {\n\t\ttry {\n\t\t\tDisplay.setDisplayMode(new DisplayMode(1920, 1080));\n\t\t\tDisplay.create(new PixelFormat(), new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));\n\t\t\tDisplay.setVSyncEnabled(true);\n\t\t\tSystem.out.println(GL11.glGetString(GL11.GL_VERSION));\n\t\t} catch (LWJGLException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t}\n\t\n\tprivate static void initGL() {\n\t\t\n\t\tGL11.glClearColor(0.1f, 0.7f, 1f, 1);\n\t\t\n\t\tMouse.setGrabbed(true);\n\t\t\n\t\tGL11.glEnable(GL11.GL_CULL_FACE);\n\t\tGL11.glCullFace(GL11.GL_BACK);\n\t\t\n\t\tGL11.glEnable(GL11.GL_DEPTH_TEST);\n\t\t\n\t\tc = new Camera3D(70, 16.f/9, .03f, 1000);\n\t\t\n\t\ts = Shader.loadShader(\"res/shaders/default\");\n\t\t\n\t\tt = new Texture(\"res/textures/block0.png\");\n\t\t\n\t\tfloat[] vertices = {\n\t\t\t-.5f, -.5f, 0,\n\t\t\t-.5f,  .5f, 0,\n\t\t\t .5f,  .5f, 0,\n\t\t\t .5f, -.5f, 0,\n\t\t\t\n\t\t};\n\t\tint[] indices = {\n\t\t\t\t0, 1, 2, 0, 2, 3\n\t\t};\n//\t\tch = new Chunk[4][4][4];\n//\t\tfor (int i = 0; i < 4; i++)\n//\t\t\tfor (int j = 0; j < 4; j++)\n//\t\t\t\tfor (int k = 0; k < 4; k++) {\n//\t\t\t\t\tch[i][j][k] = new Chunk(i, j, k);\n//\t\t\t\t\tch[i][j][k].updateBlocks();\n//\t\t\t\t\tch[i][j][k].genModel();\n//\t\t\t\t}\n\t\tw = new World();\n\t\t//m = c.genModel();//Model.load(vertices, indices);\n\t\t\n\t\tc.getTransform().setPos(new Vector3f(0, 0, 0));\n\t\t\n\t}\n\t\n\tprivate static void loop() {\n\t\t\n\t\tTimer.init();\n\t\t\n\t\twhile (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {\n\t\t\t\n\t\t\tTimer.update();\n\t\t\t\n\t\t\tDisplay.setTitle(\"\" + Timer.getFPS() + \n\t\t\t\t\t/* \"   \" + c.getTransform().getPos().toString() +*/ \"   \" \n\t\t\t\t\t+ ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + \" of \" + (Runtime.getRuntime().maxMemory() / 1048576));\n\t\t\t\n\t\t\tupdate(Timer.getDelta());\n\t\t\tGL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);\n\t\t\trender();\n\t\t\t\n\t\t\tDisplay.update();\n\t\t\t\n\t\t}\n\t\t\n\t}\n\t\n\tprivate static void update(float dt) {\n\t\tc.processInput(dt, 5, .3f);\n\t\tw.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());\n\t}\n\n\tprivate static void render() {\n\t\t\n//\t\tModel.enableAttribs();\n\t\t\n\t\ts.bind();\n//\t\tfor (int i = 0; i < 4; i++)\n//\t\t\tfor (int j = 0; j < 4; j++)\n//\t\t\t\tfor (int k = 0; k < 4; k++) {\n//\t\t\t\t\tfloat r = (4 - i) / 4f;\n//\t\t\t\t\tfloat g = j / 4f;\n//\t\t\t\t\tfloat b = k / 4f;\n//\t\t\t\t\ts.uniformf(\"color\", r, g, b);\n//\t\t\t\t\ts.unifromMat4(\"MVP\", (c.getViewProjection().mul(ch[i][j][k].getModelMatrix())));\n//\t\t\t\t\tch[i][j][k].getModel().draw();\n//\t\t\t\t}\n\t\t\n\t\tw.render(s, c);\n\t\t\n//\t\tModel.disableAttribs();\n\t}\n\t\n\tprivate static void exit(int status) {\n\t\tSystem.exit(status);\n\t}\n}","location":{"start":15,"insert":15,"offset":" ","indent":0},"item_type":"class","length":123},{"id":"9060879e-0f09-2089-ef40-3639ac4d8138","ancestors":["ff92ae65-fa32-8f8f-0b44-d9c7d98910e5"],"type":"function","description":"initializes display and GL resources, enters an event loop, and exits with a return value of 0.","params":[{"name":"args","type_name":"String[]","description":"1 or more command-line arguments passed to the `main` function when the program is launched, which are then ignored in this particular implementation.\n\n* `args`: An array of strings containing command-line arguments passed to the program by the user.\n* Length: The number of elements in the `args` array, which is always non-zero in this case.","complex_type":true}],"usage":{"language":"java","code":"public static void main(String[] args) {\n\tinitDisplay(); // Initializes the display\n\tinitGL(); // Initializes graphics and OpenGL\n\tloop(); // Runs a loop for the program\n\texit(0); // Closes the program\n}\n","description":""},"name":"main","code":"public static void main(String[] args) {\n\t\t\n\t\tinitDisplay();\n\t\tinitGL();\n\t\tloop();\n\t\texit(0);\n\t\t\n\t}","location":{"start":17,"insert":17,"offset":" ","indent":1},"item_type":"method","length":8},{"id":"53c45e0e-2b15-0f93-304d-282c0a4330c9","ancestors":["ff92ae65-fa32-8f8f-0b44-d9c7d98910e5"],"type":"function","description":"initializes the display mode, creates a GL context, enables VSync, and prints the GL version string.","params":[],"usage":{"language":"java","code":"import org.lwjgl.LWJGLException;\nimport org.lwjgl.input.Keyboard;\nimport org.lwjgl.input.Mouse;\nimport org.lwjgl.opengl.ContextAttribs;\nimport org.lwjgl.opengl.Display;\nimport org.lwjgl.opengl.DisplayMode;\nimport org.lwjgl.opengl.PixelFormat;\n\npublic class Main {\n\tpublic static void main(String[] args) {\n\t\ttry {\n\t\t\tinitDisplay();\n\t\t} catch (LWJGLException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t}\n\n\tprivate static void initDisplay() throws LWJGLException {\n\t\tDisplay.setDisplayMode(new DisplayMode(1920, 1080));\n\t\tDisplay.create(new PixelFormat(), new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));\n\t\tDisplay.setVSyncEnabled(true);\n\t\tSystem.out.println(GL11.glGetString(GL11.GL_VERSION));\n\t}\n}\n","description":"\nThis code will set the display mode, create a new OpenGL context and enable vertical synchronization (which helps reduce screen tearing)."},"name":"initDisplay","code":"private static void initDisplay() {\n\t\ttry {\n\t\t\tDisplay.setDisplayMode(new DisplayMode(1920, 1080));\n\t\t\tDisplay.create(new PixelFormat(), new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true));\n\t\t\tDisplay.setVSyncEnabled(true);\n\t\t\tSystem.out.println(GL11.glGetString(GL11.GL_VERSION));\n\t\t} catch (LWJGLException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\t}","location":{"start":33,"insert":33,"offset":" ","indent":1},"item_type":"method","length":10},{"id":"11f57d8b-b428-59a0-9546-38be3c95386f","ancestors":["ff92ae65-fa32-8f8f-0b44-d9c7d98910e5"],"type":"function","description":"initializes the GL context by setting up camera, lighting, and shading parameters. It also loads a texture and creates a model.","params":[],"usage":{"language":"java","code":"private static void initGL() {\n    // Initialize variables\n    GL11.glClearColor(0.1f, 0.7f, 1f, 1);\n    Mouse.setGrabbed(true);\n    GL11.glEnable(GL11.GL_CULL_FACE);\n    GL11.glCullFace(GL11.GL_BACK);\n    GL11.glEnable(GL11.GL_DEPTH_TEST);\n    \n    // Set up camera and shader\n    c = new Camera3D(70, 16.f/9, .03f, 1000);\n    s = Shader.loadShader(\"res/shaders/default\");\n    \n    // Load texture\n    t = new Texture(\"res/textures/block0.png\");\n    \n    // Initialize chunk variables and world\n//    float[] vertices = {\n//        -.5f, -.5f, 0,\n//        -.5f,   .5f, 0,\n//          .5f,   .5f, 0,\n//          .5f, -.5f, 0,\n//    };\n//    int[] indices = {\n//            0, 1, 2, 0, 2, 3\n//    };\n//    ch = new Chunk[4][4][4];\n//    for (int i = 0; i < 4; i++)\n//        for (int j = 0; j < 4; j++)\n//            for (int k = 0; k < 4; k++) {\n//                ch[i][j][k] = new Chunk(i, j, k);\n//                ch[i][j][k].updateBlocks();\n//                ch[i][j][k].genModel();\n//            }\n    w = new World();\n    \n    // Update camera position and set model matrix for camera\n    c.getTransform().setPos(new Vector3f(0, 0, 0));\n}\n","description":"\nIn this example, the method initGL is setting up several variables that are necessary for the game to work correctly. It sets the clear color for the screen, grabs the mouse and enables face culling. It also sets up a camera object (c) using the Camera3D class, loads in a shader (s), and loads in a texture (t). Additionally, it initializes chunks of blocks (ch) and updates their model matrices (genModel()). Finally, it creates a world object (w). Note that this is just an example and may not be correct for your specific implementation."},"name":"initGL","code":"private static void initGL() {\n\t\t\n\t\tGL11.glClearColor(0.1f, 0.7f, 1f, 1);\n\t\t\n\t\tMouse.setGrabbed(true);\n\t\t\n\t\tGL11.glEnable(GL11.GL_CULL_FACE);\n\t\tGL11.glCullFace(GL11.GL_BACK);\n\t\t\n\t\tGL11.glEnable(GL11.GL_DEPTH_TEST);\n\t\t\n\t\tc = new Camera3D(70, 16.f/9, .03f, 1000);\n\t\t\n\t\ts = Shader.loadShader(\"res/shaders/default\");\n\t\t\n\t\tt = new Texture(\"res/textures/block0.png\");\n\t\t\n\t\tfloat[] vertices = {\n\t\t\t-.5f, -.5f, 0,\n\t\t\t-.5f,  .5f, 0,\n\t\t\t .5f,  .5f, 0,\n\t\t\t .5f, -.5f, 0,\n\t\t\t\n\t\t};\n\t\tint[] indices = {\n\t\t\t\t0, 1, 2, 0, 2, 3\n\t\t};\n//\t\tch = new Chunk[4][4][4];\n//\t\tfor (int i = 0; i < 4; i++)\n//\t\t\tfor (int j = 0; j < 4; j++)\n//\t\t\t\tfor (int k = 0; k < 4; k++) {\n//\t\t\t\t\tch[i][j][k] = new Chunk(i, j, k);\n//\t\t\t\t\tch[i][j][k].updateBlocks();\n//\t\t\t\t\tch[i][j][k].genModel();\n//\t\t\t\t}\n\t\tw = new World();\n\t\t//m = c.genModel();//Model.load(vertices, indices);\n\t\t\n\t\tc.getTransform().setPos(new Vector3f(0, 0, 0));\n\t\t\n\t}","location":{"start":44,"insert":44,"offset":" ","indent":1},"item_type":"method","length":41},{"id":"e5fc4be6-5ddd-7bb8-6f43-c523a10937dd","ancestors":["ff92ae65-fa32-8f8f-0b44-d9c7d98910e5"],"type":"function","description":"updates a display's title and renders an image using OpenGL every 100 milliseconds until the user closes the display or presses the escape key.","params":[],"usage":{"language":"java","code":"public static void main(String[] args) {\n\t//...\n\tloop();\n}\n","description":"\nThis will call the method loop() which runs the program and updates the display, renders the models, and processes the input.     It also updates the title of the window with the number of frames per second, the memory usage of the program, and the position of the camera.\nThe method loop() should be a private static void method as it is only needed within the main method to run the program correctly.   The method loop() will call other methods such as update(), render(), and processInput() to run the program correctly.    These other methods are explained in detail below:\n"},"name":"loop","code":"private static void loop() {\n\t\t\n\t\tTimer.init();\n\t\t\n\t\twhile (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {\n\t\t\t\n\t\t\tTimer.update();\n\t\t\t\n\t\t\tDisplay.setTitle(\"\" + Timer.getFPS() + \n\t\t\t\t\t/* \"   \" + c.getTransform().getPos().toString() +*/ \"   \" \n\t\t\t\t\t+ ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1048576) + \" of \" + (Runtime.getRuntime().maxMemory() / 1048576));\n\t\t\t\n\t\t\tupdate(Timer.getDelta());\n\t\t\tGL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);\n\t\t\trender();\n\t\t\t\n\t\t\tDisplay.update();\n\t\t\t\n\t\t}\n\t\t\n\t}","location":{"start":86,"insert":86,"offset":" ","indent":1},"item_type":"method","length":21},{"id":"fc98de92-d5bb-53bf-4a4e-164fcbd540c1","ancestors":["ff92ae65-fa32-8f8f-0b44-d9c7d98910e5"],"type":"function","description":"updates the position of an entity (`w`) based on input and transform values, using the `processInput` method to manipulate the entity's state.","params":[{"name":"dt","type_name":"float","description":"3D time step that is used to update the position of the object in the scene.","complex_type":false}],"usage":{"language":"java","code":"float dt = 0; // The time elapsed between two frames in seconds\n\nupdate(dt);\n\n// c.processInput() now updates the position of c based on the given dt value\n// w.updatePos() also updates the position of w based on the given x, y, and z values\n","description":""},"name":"update","code":"private static void update(float dt) {\n\t\tc.processInput(dt, 5, .3f);\n\t\tw.updatePos(c.getTransform().getPos().getX(), c.getTransform().getPos().getY(), c.getTransform().getPos().getZ());\n\t}","location":{"start":108,"insert":108,"offset":" ","indent":1},"item_type":"method","length":4},{"id":"a652c658-ea8e-df83-3b4d-cc069f4c43b7","ancestors":["ff92ae65-fa32-8f8f-0b44-d9c7d98910e5"],"type":"function","description":"renders a 3D model using the specified color and transforms it according to the view and projection matrices.","params":[],"usage":{"language":"java","code":"public class Main {\n  private static void render() {\n    // Model.enableAttribs();\n\n    Shader s = new Shader(\"vertex.vs\", \"fragment.fs\");\n    s.bind();\n\n    World w = new World();\n    Camera c = new Camera();\n\n    for (int i = 0; i < 4; i++)\n      for (int j = 0; j < 4; j++)\n        for (int k = 0; k < 4; k++) {\n          float r = (4 - i) / 4f;\n          float g = j / 4f;\n          float b = k / 4f;\n\n          s.uniformf(\"color\", r, g, b);\n\n          Mat4 MVP = c.getViewProjection().mul(ch[i][j][k].getModelMatrix());\n          s.uniformMat4(\"MVP\", MVP);\n          ch[i][j][k].getModel().draw();\n        }\n\n    w.render(s, c);\n\n    // Model.disableAttribs();\n  }\n}\n","description":"\nIn this example code, the render method is being used to display a 3D world with a camera and lighting using a shader program. The render method first creates an instance of the Shader class and binds it for use. It then creates new instances of the World and Camera classes. Then, it iterates through all of the blocks in the world and sets the uniform variables color and MVP for each block before drawing them using their respective model matrices. Finally, the method calls render on the world with the shader and camera to display it to the screen.\n\nIt is important to note that this example code will not work as is, as the Shader class is a placeholder until a real shader program is created. Additionally, the Camera and World classes are also placeholders and require actual code before they can be used properly."},"name":"render","code":"private static void render() {\n\t\t\n//\t\tModel.enableAttribs();\n\t\t\n\t\ts.bind();\n//\t\tfor (int i = 0; i < 4; i++)\n//\t\t\tfor (int j = 0; j < 4; j++)\n//\t\t\t\tfor (int k = 0; k < 4; k++) {\n//\t\t\t\t\tfloat r = (4 - i) / 4f;\n//\t\t\t\t\tfloat g = j / 4f;\n//\t\t\t\t\tfloat b = k / 4f;\n//\t\t\t\t\ts.uniformf(\"color\", r, g, b);\n//\t\t\t\t\ts.unifromMat4(\"MVP\", (c.getViewProjection().mul(ch[i][j][k].getModelMatrix())));\n//\t\t\t\t\tch[i][j][k].getModel().draw();\n//\t\t\t\t}\n\t\t\n\t\tw.render(s, c);\n\t\t\n//\t\tModel.disableAttribs();\n\t}","location":{"start":113,"insert":113,"offset":" ","indent":1},"item_type":"method","length":20},{"id":"512a69aa-5071-c5bb-9249-ed5c5a877986","ancestors":["ff92ae65-fa32-8f8f-0b44-d9c7d98910e5"],"type":"function","description":"terminates the current Java process with the specified status code.","params":[{"name":"status","type_name":"int","description":"exit code to be returned by the `System.exit()` method, indicating the outcome of the program's execution.","complex_type":false}],"usage":{"language":"java","code":"public class Main {\n\tpublic static void main(String[] args) {\n\t\tint status = 0; // any integer value you want to pass as a parameter\n\t\texit(status);\n\t}\n}\n","description":""},"name":"exit","code":"private static void exit(int status) {\n\t\tSystem.exit(status);\n\t}","location":{"start":134,"insert":134,"offset":" ","indent":1},"item_type":"method","length":3}]}}}