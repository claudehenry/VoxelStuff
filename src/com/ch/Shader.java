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
 * provides functionality for binding a program, uniform float and matrix operations,
 * and loading shaders from files.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * activates a predefined OpenGL program, specified by the `program` variable.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of the `program` field of the object.
	 * 
	 * @returns an integer representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * is a method that sets floating-point uniform values for a given name and array
	 * length in a OpenGL context.
	 * 
	 * @param name name of the uniform to be set, which is used as an index to determine
	 * the appropriate glUniform* function to call.
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
	 * updates a uniform matrix with the specified name using the `glUniformMatrix4`
	 * method and passes the matrix data to it in a flipped buffer format.
	 * 
	 * @param name 16-character uniform name for the matrix.
	 * 
	 * @param mat 4x4 uniform matrix to be stored as a GL_UNIFORM buffer.
	 * 
	 * 	- `name`: The name of the uniform location where the matrix will be stored.
	 * 	- `mat`: A `Matrix4f` object representing a 4x4 homogeneous transformation matrix.
	 * 	- `getLocation()`: A method that returns a `String` representing the location of
	 * the uniform in the current program.
	 * 	- `glUniformMatrix4()`: A glslang API function used to set the value of a uniform
	 * matrix.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * is a part of Java code that retrieves the location of a uniform in a GPU program.
	 * Specifically, it invokes the `glGetUniformLocation` method from the GL20 class to
	 * obtain the location of the uniform with the given name in the program's shader.
	 * 
	 * @param name name of the uniform location to retrieve in the OpenGL ES 2.0 program
	 * object passed as the `program` parameter, and it is used by the `GL20.glGetUniformLocation()`
	 * function to locate the corresponding uniform variable within the program.
	 * 
	 * @returns an integer representing the location of a uniform in a program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it.
	 * 
	 * @param filename path to a file containing the vertex and fragment shader code for
	 * loading into a shader program using the `loadShader()` function.
	 * 
	 * @returns a newly created Shader object representing the combined vertex and fragment
	 * shaders.
	 * 
	 * 	- The `Shader` object is returned as the result of the function, representing a
	 * shader program that has been loaded from a file.
	 * 	- The `Shader` object contains information about the shader program, including
	 * its name and the types of its vertex and fragment shaders.
	 * 	- The `program` variable contains the ID of the created program, which is used
	 * to reference the shader program in subsequent API calls.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader program and attaches it to the main program, loading a shader
	 * source code into the shader and compiling it before attaching it to the program.
	 * 
	 * @param target type of shader being created, with possible values of `GL_VERTEX_SHADER`,
	 * `GL_FRAGMENT_SHADER`, or `GL_GEOMETRY_SHADER`.
	 * 
	 * @param src Shaders source code that is being loaded and compiled into a shader program.
	 * 
	 * @param program 3D graphics program that the shader will be attached to.
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
	 * validates a program object in Open GL by checking its linking and validation
	 * statuses, and prints any error messages if there are any.
	 * 
	 * @param program 3D graphics program to be validated and linked with the OpenGL API.
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
	 * reads the contents of a specified file and returns the resulting string.
	 * 
	 * @param file file from which the text is to be read.
	 * 
	 * @returns a string representation of the contents of the specified file.
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
