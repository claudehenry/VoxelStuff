package com.ch;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL20;

import com.ch.math.Matrix4f;

/**
 * TODO
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
 /**
  * glues a program to the current GL context, enabling its use for rendering and other
  * graphics-related tasks.
  */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
 /**
  * retrieves the value of a field named `program`.
  * 
  * @returns an integer value representing the program.
  */
	public int getProgram() {
		return this.program;
	}
	
 /**
  * updates a uniform variable in a shader program based on the length of the passed
  * float array. It calls `glUniformx` with the correct parameter for the length of
  * the array.
  * 
  * @param name name of the uniform location for which the values are being set.
  * 
  * 	- `name`: A `String` variable representing the name of the uniform.
  * 	- `vals`: An array of `float` values to be set as the uniform value. The length
  * of the array determines the number of uniform values to be set.
  */
	public void uniformf(String name, float ...vals) {
		switch (vals.length) {
		case 1:
			GL20.glUniform1f(getLoaction(name), vals[0]);
			break;
		case 2:
			GL20.glUniform2f(getLoaction(name), vals[0], vals[1]);
			break;
		case 3:
			GL20.glUniform3f(getLoaction(name), vals[0], vals[1], vals[2]);
			break;
		case 4:
			GL20.glUniform4f(getLoaction(name), vals[0], vals[1], vals[2], vals[3]);
			break;
		}
	}
	
 /**
  * sets a 4x4 matrix as a uniform buffer object (UBO) using the `glUniformMatrix4`
  * method from OpenGL. The matrix is passed as an argument, and the function returns
  * immediately after setting the matrix in the UBO.
  * 
  * @param name name of the uniform variable to which the matrix will be assigned.
  * 
  * 	- `name`: A string variable representing the name of the uniform location to which
  * the matrix will be assigned.
  * 	- `mat`: A `Matrix4f` object containing the matrix data that will be stored in
  * the uniform location.
  * 	- `getLocation()`: A function used to retrieve the desired uniform location based
  * on the `name` argument.
  * 	- `glUniformMatrix4()`: A method from the OpenGL API that sets a 4x4 matrix as a
  * uniform in the current program. The `false` argument indicates that the matrix
  * should be stored in row-major order.
  * 
  * @param mat 4x4 homogeneous transformation matrix to be uniformed.
  * 
  * 	- The input `mat` is an instance of the `Matrix4f` class, which represents a 4x4
  * matrix with floating-point elements.
  * 	- The `getLinearData()` method returns a buffer containing the raw data elements
  * of the matrix.
  * 	- The `Util.createFlippedBuffer(mat.getLinearData())` call creates a new buffer
  * that is the mirror image of the original buffer, which is necessary for some GPU
  * functions to properly process the input data.
  */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
 /**
  * retrieves the location of a uniform in a program using the `GL20.glGetUniformLocation`
  * method.
  * 
  * @param name 0-based index of a uniform location within a shader program that the
  * `getLocation()` method will retrieve and return.
  * 
  * 	- `name`: A string variable representing the name of a uniform in a computer
  * graphics program's shader.
  * 	- `program`: An integer value representing the ID of the shader program that the
  * uniform belongs to.
  * 
  * @returns an integer representing the location of a uniform in a program within
  * theGL20 library.
  */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
 /**
  * loads a shader program from a file and creates a new shader object to represent it.
  * 
  * @param filename filename of the shader file to be loaded, which is used to identify
  * and load the appropriate vertex and fragment shaders.
  * 
  * 	- `filename`: A string representing the path to a shader file in the form of
  * "filename.vert" or "filename.frag".
  * 	- `GL20`: A class that provides methods for creating and managing OpenGL programs,
  * including `glCreateProgram()` for creating a new program, `glCreateShader()` for
  * creating a new shader, and `glLinkProgram()` for linking the shaders to the program.
  * 
  * @returns a new `Shader` object representing a shader program created by combining
  * a vertex shader and a fragment shader.
  * 
  * 	- The `Shader` object returned is an instance of the class `Shader`, which
  * represents a shader program in the OpenGL ES environment.
  * 	- The `program` field of the returned `Shader` object contains the handle to the
  * created shader program, which can be used for further operations such as setting
  * uniforms or binding the shader to a render buffer.
  * 	- The `getText` method used to load the vertex and fragment shaders returns the
  * binary data of the shader code as a string.
  * 	- The `validateProgram` method is called after creating the shader program to
  * ensure that it is valid and can be used for rendering.
  */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
 /**
  * creates a shader program and attaches it to the program handle provided.
  * 
  * @param target type of shader to be created or modified, with valid values being 0
  * for vertex shaders and 1 for fragment shaders.
  * 
  * @param src source code of the shader to be compiled.
  * 
  * 	- `target`: An integer parameter representing the type of shader to be created,
  * which can be either `GL_VERTEX_SHADER` or `GL_FRAGMENT_SHADER`.
  * 	- `src`: A string parameter containing the source code of the shader, which is
  * deserialized from a file or database.
  * 	- `program`: An integer parameter representing the program to which the shader
  * will be attached, which is created using the `GL20.glCreateProgram()` function.
  * 
  * @param program 3D graphics program that the loaded shader will be attached to.
  */
	private static void loadShader(int target, String src, int program) {
		int shader = GL20.glCreateShader(target);
		
		GL20.glShaderSource(shader, src);
		GL20.glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		GL20.glAttachShader(program, shader);
	}
	
 /**
  * validates a GPU program by linking and validating it with the OpenGL API.
  * 
  * @param program 3D graphics program to be validated, and its value is passed as an
  * argument to the `glLinkProgram`, `glValidateProgram`, and `glGetProgramInfoLog` functions.
  */
	private static void validateProgram(int program) {
		GL20.glLinkProgram(program);
		
		if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		GL20.glValidateProgram(program);
		
		if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
	}
	
 /**
  * reads the contents of a file as a string, handling potential IOExceptions gracefully.
  * 
  * @param file path to the file containing the text to be read.
  * 
  * 	- `file`: A `String` parameter that represents the path to the file to be read.
  * 	- `InputStream is`: An `InputStream` object created from the file specified in
  * `file`. This object provides access to the binary data contained within the file.
  * 	- `int ch`: An integer variable used to read individual bytes from the input
  * stream. The value of this variable ranges from -1 (end of file) to the value of a
  * single byte.
  * 	- `String text`: A `String` variable that is initialized to an empty string and
  * will be populated with the binary data read from the input stream.
  * 	- `IOException e`: An exception object that may be thrown if there is an error
  * reading from the input stream. The `e.printStackTrace()` method is called to display
  * any error messages to the user. If the error is severe enough, the program exits
  * through the `System.exit(1)` method.
  * 
  * @returns a string representation of the contents of a specified file.
  * 
  * 1/ The output is a String object, which represents the text contents of the given
  * file.
  * 2/ The String object is created by reading the bytes of the file and converting
  * them into characters using the ASCII code.
  * 3/ The resulting String object contains the entire contents of the file as a
  * sequence of characters.
  */
	private static String getText(String file) {
		String text = "";
		try {
			InputStream is = new FileInputStream(file);
			int ch;
			while ((ch = is.read()) != -1)
				text += (char) ch;
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return text;
	}

}
