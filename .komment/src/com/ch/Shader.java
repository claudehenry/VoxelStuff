{"name":"Shader.java","path":"src/com/ch/Shader.java","content":{"structured":{"description":"A `Shader` class that loads and manages OpenGL shaders. It uses the `GL20` package for GL functionality. The `loadShader()` method loads a vertex and fragment shader from files and creates a program, validates it, and returns a `Shader` object. The `bind()` method binds the shader program to the current context, and the `uniformf()` and `unifromMat4()` methods set uniform values for floating-point numbers or matrix transformations, respectively.","items":[{"id":"b5fe45c5-49da-fa96-504c-1a199c7095e0","ancestors":[],"type":"function","description":"is used to create and manage shaders for use in a 3D graphics pipeline. It provides methods for binding the shader program, setting uniform values, and loading shader code from files. The class also includes utility methods for handling matrix operations and validating the shader program.","name":"Shader","code":"public class Shader {\n\t\n\tprivate int program;\n\t\n\tpublic Shader(int program) {\n\t\tthis.program = program;\n\t}\n\t\n\tpublic void bind() {\n\t\tGL20.glUseProgram(program);\n\t}\n\t\n\tpublic int getProgram() {\n\t\treturn this.program;\n\t}\n\t\n\tpublic void uniformf(String name, float ...vals) {\n\t\tswitch (vals.length) {\n\t\tcase 1:\n\t\t\tGL20.glUniform1f(getLoaction(name), vals[0]);\n\t\t\tbreak;\n\t\tcase 2:\n\t\t\tGL20.glUniform2f(getLoaction(name), vals[0], vals[1]);\n\t\t\tbreak;\n\t\tcase 3:\n\t\t\tGL20.glUniform3f(getLoaction(name), vals[0], vals[1], vals[2]);\n\t\t\tbreak;\n\t\tcase 4:\n\t\t\tGL20.glUniform4f(getLoaction(name), vals[0], vals[1], vals[2], vals[3]);\n\t\t\tbreak;\n\t\t}\n\t}\n\t\n\tpublic void unifromMat4(String name, Matrix4f mat) {\n\t\tGL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));\n\t}\n\t\n\tpublic int getLoaction(String name) {\n\t\treturn GL20.glGetUniformLocation(program, name);\n\t}\n\t\n\tprivate static final String VERT = \".vert\", FRAG = \".frag\";\n\t\n\tpublic static Shader loadShader(String filename) {\n\t\tint program = GL20.glCreateProgram();\n\t\tloadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);\n\t\tloadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);\n\t\tvalidateProgram(program);\n\t\treturn new Shader(program);\n\t}\n\t\n\tprivate static void loadShader(int target, String src, int program) {\n\t\tint shader = GL20.glCreateShader(target);\n\t\t\n\t\tGL20.glShaderSource(shader, src);\n\t\tGL20.glCompileShader(shader);\n\t\t\n\t\tif (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {\n\t\t\tSystem.err.println(glGetShaderInfoLog(shader, 1024));\n\t\t\tSystem.exit(1);\n\t\t}\n\t\t\n\t\tGL20.glAttachShader(program, shader);\n\t}\n\t\n\tprivate static void validateProgram(int program) {\n\t\tGL20.glLinkProgram(program);\n\t\t\n\t\tif (glGetProgrami(program, GL_LINK_STATUS) == 0) {\n\t\t\tSystem.err.println(glGetProgramInfoLog(program, 1024));\n\t\t\tSystem.exit(1);\n\t\t}\n\t\t\n\t\tGL20.glValidateProgram(program);\n\t\t\n\t\tif (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {\n\t\t\tSystem.err.println(glGetProgramInfoLog(program, 1024));\n\t\t\tSystem.exit(1);\n\t\t}\n\t}\n\t\n\tprivate static String getText(String file) {\n\t\tString text = \"\";\n\t\ttry {\n\t\t\tInputStream is = new FileInputStream(file);\n\t\t\tint ch;\n\t\t\twhile ((ch = is.read()) != -1)\n\t\t\t\ttext += (char) ch;\n\t\t\tis.close();\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t\tSystem.exit(1);\n\t\t}\n\t\treturn text;\n\t}\n\n}","location":{"start":19,"insert":19,"offset":" ","indent":0,"comment":null},"item_type":"class","length":97},{"id":"e38c48e7-eaff-f6a0-f247-0b6838939cf2","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"glues a program object to a specific GL context, allowing the use of shaders and other program-related resources.","params":[],"usage":{"language":"java","code":"public void render() {\n    GL20.glUseProgram(program); //Bind the shader to OpenGL\n    GL20.glUniform1f(\"time\", System.currentTimeMillis()); //Pass a uniform variable \"time\" of type float and set it to current time in milliseconds\n}\n","description":""},"name":"bind","code":"public void bind() {\n\t\tGL20.glUseProgram(program);\n\t}","location":{"start":27,"insert":27,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"31a74e32-f4f2-7a9b-8f44-0dc05fb8ecc0","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"returns the value of a class member variable `program`.","params":[],"returns":{"type_name":"int","description":"the value of the `program` field.","complex_type":false},"usage":{"language":"java","code":"Shader shader = Shader.loadShader(\"myShader\");\nshader.bind();\nint program = shader.getProgram();\n","description":""},"name":"getProgram","code":"public int getProgram() {\n\t\treturn this.program;\n\t}","location":{"start":31,"insert":31,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"edc3876e-96d5-5ca2-b146-165c0cd6911a","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"updates a uniform variable in a shader program based on the length of an array of float values passed as an argument. It calls the appropriate GL method (glUniform1f, glUniform2f, etc.) to set the value of the uniform at the specified location.","params":[{"name":"name","type_name":"String","description":"location of the uniform in the program, which is used to specify the target glUniform() method.","complex_type":false}],"usage":{"language":"java","code":"Shader shader = Shader.loadShader(\"test\");\nshader.bind();\nshader.uniformf(\"myFloat\", 1.5f);\n","description":""},"name":"uniformf","code":"public void uniformf(String name, float ...vals) {\n\t\tswitch (vals.length) {\n\t\tcase 1:\n\t\t\tGL20.glUniform1f(getLoaction(name), vals[0]);\n\t\t\tbreak;\n\t\tcase 2:\n\t\t\tGL20.glUniform2f(getLoaction(name), vals[0], vals[1]);\n\t\t\tbreak;\n\t\tcase 3:\n\t\t\tGL20.glUniform3f(getLoaction(name), vals[0], vals[1], vals[2]);\n\t\t\tbreak;\n\t\tcase 4:\n\t\t\tGL20.glUniform4f(getLoaction(name), vals[0], vals[1], vals[2], vals[3]);\n\t\t\tbreak;\n\t\t}\n\t}","location":{"start":35,"insert":35,"offset":" ","indent":1,"comment":null},"item_type":"method","length":16},{"id":"c551bb32-5568-3885-9b4b-906e8906840c","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"glUniformMatrix4 method to set a 4x4 matrix as a uniform buffer in OpenGL.","params":[{"name":"name","type_name":"String","description":"0-based index of the uniform location where the matrix is to be stored.","complex_type":false},{"name":"mat","type_name":"Matrix4f","description":"4x4 matrix to be uniformed and is passed as a `Matrix4f` object to the `unifromMat4()` function.\n\n* `name`: The name of the uniform variable being assigned.\n* `mat`: A `Matrix4f` object representing a 4x4 homogeneous matrix in standard form (i.e., column-major order).\n* `getLocation()`: A method that returns an integer indicating the location where the uniform variable will be stored.\n* `false`: A boolean value indicating whether the uniform variable should be stored as a 16-bit float or a 32-bit floating-point number.","complex_type":true}],"usage":{"language":"java","code":"Matrix4f myMatrix = new Matrix4f(); // Create a matrix with values to be used in the shader\nShader shader = Shader.loadShader(\"myShader\"); // Load a shader using Shader.loadShader() method\nshader.bind(); // Bind the shader to the current context\nshader.unifromMat4(\"myMatrix\", myMatrix); // Pass the matrix data to the \"myMatrix\" uniform in the shader","description":""},"name":"unifromMat4","code":"public void unifromMat4(String name, Matrix4f mat) {\n\t\tGL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));\n\t}","location":{"start":52,"insert":52,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"546238e3-953d-d2b1-3846-54582e372b8a","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"retrieves the location of a uniform named `name` within a program's uniform buffer using the `GL20` class and its `glGetUniformLocation()` method.","params":[{"name":"name","type_name":"String","description":"0-based index of a uniform location in the current program, which is used by the `GL20.glGetUniformLocation()` method to retrieve information about the uniform.","complex_type":false}],"returns":{"type_name":"int","description":"an integer representing the location of a uniform within a program.","complex_type":false},"usage":{"language":"java","code":"public int main(String[] args) {\n    Shader shader = new Shader(\"shaderFile\"); // create a shader object\n    \n    int uniformLocation = shader.getLoaction(\"uniformName\"); // find location of the uniform name in the shader file\n    \n    return 0; // exit program\n}\n","description":""},"name":"getLoaction","code":"public int getLoaction(String name) {\n\t\treturn GL20.glGetUniformLocation(program, name);\n\t}","location":{"start":56,"insert":56,"offset":" ","indent":1,"comment":null},"item_type":"method","length":3},{"id":"03565fdb-1125-66bc-0848-fd6c03347ee9","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"loads a shader program from a file and validates it.","params":[{"name":"filename","type_name":"String","description":"name of the shader file to be loaded.","complex_type":false}],"returns":{"type_name":"Shader","description":"a new instance of the `Shader` class, representing a shader program created by combining a vertex shader and a fragment shader.\n\n* The output is a new Shader object representing a program created by combining two shaders using the `glCreateProgram` method.\n* The Shader object contains the program ID generated by the `glCreateProgram` method, which can be used to access and manipulate the program's contents.\n* The Shader object provides methods for validating the program and setting various attributes such as the vertex and fragment shaders used to create it.","complex_type":true},"name":"loadShader","code":"public static Shader loadShader(String filename) {\n\t\tint program = GL20.glCreateProgram();\n\t\tloadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);\n\t\tloadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);\n\t\tvalidateProgram(program);\n\t\treturn new Shader(program);\n\t}","location":{"start":62,"insert":62,"offset":" ","indent":1,"comment":null},"item_type":"method","length":7},{"id":"98e13e59-5350-8dbc-6640-6c7edf2d9fe9","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"creates a shader program and attaches it to a program object. It also compiles and logs any error messages if they occur.","params":[{"name":"target","type_name":"int","description":"3D graphics program that the shader will be added to or modified within the function.","complex_type":false},{"name":"src","type_name":"String","description":"1:1 equivalent of the source code for the shader to be loaded and compiled.","complex_type":false},{"name":"program","type_name":"int","description":"3D graphics program that the shader will be attached to after being compiled.","complex_type":false}],"name":"loadShader","code":"private static void loadShader(int target, String src, int program) {\n\t\tint shader = GL20.glCreateShader(target);\n\t\t\n\t\tGL20.glShaderSource(shader, src);\n\t\tGL20.glCompileShader(shader);\n\t\t\n\t\tif (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {\n\t\t\tSystem.err.println(glGetShaderInfoLog(shader, 1024));\n\t\t\tSystem.exit(1);\n\t\t}\n\t\t\n\t\tGL20.glAttachShader(program, shader);\n\t}","location":{"start":70,"insert":70,"offset":" ","indent":1,"comment":null},"item_type":"method","length":13},{"id":"860b08b5-b470-03ae-c041-d3f632153870","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"validates a program object by linking and validating it with the OpenGL API, printing any error messages to `System.err` if there are any.","params":[{"name":"program","type_name":"int","description":"3D graphics program to be validated and linked with the GPU.","complex_type":false}],"name":"validateProgram","code":"private static void validateProgram(int program) {\n\t\tGL20.glLinkProgram(program);\n\t\t\n\t\tif (glGetProgrami(program, GL_LINK_STATUS) == 0) {\n\t\t\tSystem.err.println(glGetProgramInfoLog(program, 1024));\n\t\t\tSystem.exit(1);\n\t\t}\n\t\t\n\t\tGL20.glValidateProgram(program);\n\t\t\n\t\tif (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {\n\t\t\tSystem.err.println(glGetProgramInfoLog(program, 1024));\n\t\t\tSystem.exit(1);\n\t\t}\n\t}","location":{"start":84,"insert":84,"offset":" ","indent":1,"comment":null},"item_type":"method","length":15},{"id":"9921e589-283f-eaa2-114e-ff857ed2b506","ancestors":["b5fe45c5-49da-fa96-504c-1a199c7095e0"],"type":"function","description":"reads the contents of a specified file and returns its text content as a string.","params":[{"name":"file","type_name":"String","description":"file from which the text is to be read.","complex_type":false}],"returns":{"type_name":"String","description":"a string representation of the contents of a specified file.","complex_type":false},"name":"getText","code":"private static String getText(String file) {\n\t\tString text = \"\";\n\t\ttry {\n\t\t\tInputStream is = new FileInputStream(file);\n\t\t\tint ch;\n\t\t\twhile ((ch = is.read()) != -1)\n\t\t\t\ttext += (char) ch;\n\t\t\tis.close();\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t\tSystem.exit(1);\n\t\t}\n\t\treturn text;\n\t}","location":{"start":100,"insert":100,"offset":" ","indent":1,"comment":null},"item_type":"method","length":14}]}}}