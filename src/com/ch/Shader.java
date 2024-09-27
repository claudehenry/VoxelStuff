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
 * Handles OpenGL shader program creation, management, and binding.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Binds a program to the OpenGL context.
	 * It sets the OpenGL program to be used for rendering.
	 * It calls `glUseProgram` to bind the program.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Returns the value of the `program` field.
	 *
	 * @returns an integer value representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Loads and sets a uniform value in the OpenGL context. It takes a name and variable
	 * number of float values, and uses a switch statement to determine the correct
	 * glUniform function to call based on the number of values provided.
	 *
	 * @param name location of a uniform variable in a shader program, used to identify
	 * the target of the `glUniform` function calls.
	 *
	 * @param vals variable number of floating-point values to be passed to the GLSL
	 * uniform variable specified by the `name` parameter.
	 *
	 * Decompose into its elements: `vals` is an array of float values.
	 * Its length is variable, determined at runtime.
	 * It can contain from 1 to 4 elements.
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
	 * Uploads a given 4x4 matrix to the specified uniform location in the OpenGL context.
	 * It uses a flipped buffer to ensure correct matrix orientation. The operation is
	 * performed using the `glUniformMatrix4` function.
	 *
	 * @param name location of a uniform matrix in a shader program.
	 *
	 * @param mat matrix data to be passed to the OpenGL uniform location.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the location of a uniform variable with the given name in the specified
	 * OpenGL program.
	 *
	 * @param name name of a uniform variable in the shader program.
	 *
	 * @returns a uniform location integer from the OpenGL context.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader program from a file, specifying vertex and fragment shaders, validates
	 * the program, and returns a new `Shader` object. It takes a filename as input and
	 * returns a `Shader` instance.
	 *
	 * @param filename name of the file containing shader code, used to construct file
	 * names for vertex and fragment shader files.
	 *
	 * @returns an instance of the `Shader` class, representing a loaded shader program.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Compiles a shader from a given source string, attaches it to a shader program, and
	 * checks for compilation errors. If an error occurs, it prints the error log and
	 * exits the program.
	 *
	 * @param target type of shader to be created, such as GL_VERTEX_SHADER or GL_FRAGMENT_SHADER.
	 *
	 * @param src source code of the shader that is being created.
	 *
	 * @param program shader program to which the created shader is attached.
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
	 * Links and validates a shader program. It checks for link status and prints error
	 * messages if the program fails to link.
	 *
	 * @param program OpenGL program object ID being validated.
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
	 * Opens a specified file, reads its contents character by character, and returns the
	 * text as a string.
	 *
	 * @param file path to the file from which the function reads and returns its text content.
	 *
	 * @returns the contents of the specified file as a string.
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
