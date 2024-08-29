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
 * Is used to manage OpenGL shaders and provides methods for loading and using shader
 * programs. It allows users to bind the program, set uniforms, and get the location
 * of uniform variables.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Binds a program identified by `program`. It uses OpenGL API to bind the program,
	 * making it active for rendering and further operations. The bound program is then
	 * ready for use with subsequent OpenGL commands.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves and returns the value of a variable named `program`. This variable is
	 * likely an instance field or property that stores a program-related data. The
	 * function provides read-only access to this data, allowing external code to access
	 * its value without modifying it.
	 *
	 * @returns an integer value representing the current program state or instance variable.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Sets uniform floating-point values for a given location in OpenGL, depending on
	 * the number of values provided. It uses variable-length argument lists to accommodate
	 * single, two-element, three-element, or four-element vectors. The specified values
	 * are applied using corresponding OpenGL API calls.
	 *
	 * @param name uniform variable name, which is used to specify the location of the
	 * uniform variable in the shader program.
	 *
	 * @param vals 1 to 4 floating-point values that are passed as a variable-length
	 * argument, which determines the type and number of uniform variables set using
	 * OpenGL API calls.
	 *
	 * Varargs type array float, accepting one or more values; length determined by number
	 * of arguments provided.
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
	 * Sets a uniform matrix to the OpenGL shader program for a given location named by
	 * the `name` parameter. It uses the provided `Matrix4f` object, mat, and returns
	 * without any output.
	 *
	 * @param name uniform variable name to which the provided matrix is assigned by the
	 * OpenGL API.
	 *
	 * @param mat 4x4 matrix to be uniformly set as a shader variable with the specified
	 * name.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the location of a uniform variable with the given name from an OpenGL
	 * program. It takes a string parameter representing the uniform variable name and
	 * returns the corresponding location as an integer. The function uses the
	 * `GL20.glGetUniformLocation` method to perform this operation.
	 *
	 * @param name uniform variable for which the function retrieves the location in the
	 * OpenGL program.
	 *
	 * @returns an integer representing a uniform location.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader from a file. It creates a new OpenGL program, loads vertex and
	 * fragment shaders from separate files, validates the program, and returns a new
	 * `Shader` object representing the loaded program.
	 *
	 * @param filename name of a file that is used to retrieve shader code, which is then
	 * loaded and processed by the function.
	 *
	 * @returns a new instance of the `Shader` class.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Compiles a shader with the given source code for a specified target and attaches
	 * it to a program object. It checks for compilation errors and exits the program if
	 * an error occurs.
	 *
	 * @param target type of shader to be created, such as GL_VERTEX_SHADER or GL_FRAGMENT_SHADER.
	 *
	 * @param src source code of the shader that is to be compiled and attached to a
	 * program using OpenGL.
	 *
	 * @param program ID of an existing OpenGL program to which the shader is attached
	 * for further processing.
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
	 * Links and validates a specified OpenGL program. If linking or validation fails,
	 * it prints the corresponding error log and terminates with exit status 1.
	 *
	 * @param program program object to be linked and validated.
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
	 * Reads a file specified by the input parameter `file`. It returns the contents of
	 * the file as a string, converting each byte to a character and concatenating them
	 * into a single string. The function handles any exceptions that occur during reading
	 * and exits the program if an error occurs.
	 *
	 * @param file path to a file that is read and its contents are returned as a string.
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
