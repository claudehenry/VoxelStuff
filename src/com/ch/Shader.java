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
 * Loads and manages OpenGL shaders for graphics rendering. It provides functionality
 * to bind, validate, and access shader variables, including scalar and matrix types.
 * The class also enables loading of vertex and fragment shaders from text files.
 */
public class Shader {	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Selects a program object for use by the GL. It binds a specified program to the
	 * current rendering context, making it active and available for drawing operations.
	 * The bound program is identified by its `program` identifier.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves and returns an integer value stored in the instance variable `this.program`.
	 * The method has no parameters and does not modify external state, it simply exposes
	 * internal state for access. It is a getter method that provides read-only access
	 * to its attribute.
	 *
	 * @returns the value of an integer field named "program".
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Handles setting float values for uniform variables with different sizes (1, 2, 3
	 * or 4 floats). It uses a switch statement to determine the correct method (e.g.
	 * `glUniform1f`, `glUniform2f`) based on the number of values passed in and then
	 * calls it accordingly.
	 *
	 * @param name name of a uniform variable to be set on the GPU, passed as a string
	 * to the OpenGL API's glUniform functions.
	 *
	 * @param vals 1 to 4 float values being passed for uniform variable setting, depending
	 * on its length.
	 *
	 * The `vals` variable is an array of floats with a variable length, known as varargs.
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
	 * Sets a uniform matrix variable on the GPU with the given name and data from the
	 * specified Matrix4f object. The data is passed through a flipped buffer to ensure
	 * correct ordering. The location of the uniform variable is retrieved using the
	 * getLoaction method.
	 *
	 * @param name name of the uniform matrix being passed to OpenGL.
	 *
	 * @param mat 4x4 matrix to be passed to the OpenGL uniform location for assignment.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the location of a uniform variable with the given name from the current
	 * OpenGL program. It uses the `glGetUniformLocation` function to find the location.
	 * The result is returned as an integer value.
	 *
	 * @param name uniform variable's identifier used to retrieve its location from the
	 * OpenGL context via `GL20.glGetUniformLocation`.
	 *
	 * @returns an integer representing a uniform location in a shader program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Creates a shader program from a given filename. It loads and attaches vertex and
	 * fragment shaders, validates the program, and returns a newly created `Shader`
	 * object containing the program ID. The shaders are loaded using `getText`, which
	 * retrieves text from a file.
	 *
	 * @param filename filename of the shader file to be loaded and compiled, with its
	 * vertex and fragment parts combined with string constants VERT and FRAG to form
	 * full filenames for loading text content.
	 *
	 * @returns a new instance of class `Shader`.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Creates and compiles a shader object based on the given source code. It checks for
	 * compilation errors and attaches the compiled shader to an existing program if no
	 * issues are found. The attached shader can then be used by the graphics pipeline.
	 *
	 * @param target type of shader to be created, with options including GL_VERTEX_SHADER
	 * or GL_FRAGMENT_SHADER for vertex and fragment shaders respectively.
	 *
	 * @param src source code of the shader being loaded, which is passed to the
	 * `GL20.glShaderSource` method for compilation.
	 *
	 * @param program shader program to which the newly created shader will be attached,
	 * allowing it to participate in rendering operations.
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
	 * Checks if a linked OpenGL program is valid and compilable.
	 * It calls `glLinkProgram` to link the program, then queries for its linking status.
	 * If invalid, it prints error log and exits with code 1.
	 *
	 * @param program 32-bit identifier for an OpenGL program object that is being linked
	 * and validated.
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
	 * Reads the contents of a specified file into memory, returning it as a string. It
	 * opens the file for reading, iterates through each character, and appends it to a
	 * string buffer. If an I/O exception occurs during this process, the function prints
	 * the error and exits.
	 *
	 * @param file path to a file that is to be read and returned as a string.
	 *
	 * @returns a string representing the contents of the specified file.
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
