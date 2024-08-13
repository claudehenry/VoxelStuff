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
 * Manages and loads OpenGL shader programs from files. It provides methods for
 * binding, unifying values and matrices to shader uniforms, and loading shaders from
 * vertex and fragment files.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Binds a graphics program to the Graphics Library (GL) for usage by other functions.
	 * It specifies which shader program should be used for rendering and animation tasks.
	 * The function sets the current program to the provided program identifier (`program`).
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves and returns an integer value representing a program. This value is stored
	 * within the current object instance, specifically within the `this.program` field.
	 * The returned value is the exact same as the one stored internally.
	 *
	 * @returns an integer value representing a program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Sets uniform floating-point values for a given variable name, depending on the
	 * number of values provided. It uses the OpenGL API to set the values: one value for
	 * `GLUniform1f`, two values for `GLUniform2f`, three values for `GLUniform3f`, and
	 * four values for `GLUniform4f`.
	 *
	 * @param name name of the uniform variable to be updated, and is used as an argument
	 * for the OpenGL functions.
	 *
	 * @param vals 1-4 floating-point values that are passed to the function, depending
	 * on its length, which determines the type of uniform variable that is set using
	 * OpenGL API calls.
	 *
	 * Varies in length, taking values from 1 to 4.
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
	 * Sets a uniform matrix for an OpenGL program. It specifies the location of the
	 * uniform matrix by name and updates its value with the given `Matrix4f`. The matrix
	 * is passed as a linear data buffer to prevent copying.
	 *
	 * @param name location of the uniform matrix that is being set with the given
	 * `Matrix4f` value.
	 *
	 * @param mat 4x4 matrix that is to be set as a uniform variable with the specified
	 * name in the OpenGL program.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves an unsigned integer representing the location of a uniform variable with
	 * the specified name from a shader program. The result is returned as an integer
	 * value. This location is used to set the uniform variable's value programmatically
	 * during rendering.
	 *
	 * @param name uniform variable or shader storage block to be located within the
	 * OpenGL program.
	 *
	 * @returns an integer representing a uniform location in OpenGL.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader by creating a new OpenGL program, adding vertex and fragment shaders
	 * to it, validating the program, and returning a new instance of `Shader`.
	 *
	 * @param filename name of the shader file to be loaded from the file system and used
	 * as the source for vertex and fragment shaders.
	 *
	 * @returns an instance of the `Shader` class.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Creates a shader object with a specified target type and sets its source code. It
	 * then compiles the shader, checks for compilation errors, and attaches it to an
	 * existing program if successful. If errors occur during compilation, it prints an
	 * error log and exits the program.
	 *
	 * @param target type of shader to be created, such as GL_VERTEX_SHADER or GL_FRAGMENT_SHADER.
	 *
	 * @param src source code of the shader, which is used to create and compile the
	 * shader object using GL20.glShaderSource().
	 *
	 * @param program OpenGL program object to which the shader is attached and linked.
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
	 * Validates and links a OpenGL program. It attempts to link the program and checks
	 * if it is successful. If not, it prints the error message and exits the application.
	 * Then, it validates the program and checks if it is successful again.
	 *
	 * @param program program object that is being linked and validated for use with
	 * OpenGL operations.
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
	 * Reads a file and returns its content as a string. It uses a FileInputStream to
	 * read the file, character by character, and appends each character to a string until
	 * the end of the file is reached. If an I/O error occurs, it prints the exception
	 * and exits the program.
	 *
	 * @param file file to be read, and its path is used as an argument for creating a `FileInputStream`.
	 *
	 * @returns a string representation of the content read from the specified file.
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
