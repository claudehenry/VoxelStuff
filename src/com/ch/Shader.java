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
 * Is designed to manage OpenGL shader programs and facilitate their use in graphics
 * rendering. It provides functionality for loading shaders from file, binding them
 * to the context, and setting uniform variables. The class also includes methods for
 * handling program validation and linking.
 */
public class Shader {	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Sets the active shader program for OpenGL rendering by calling `GL20.glUseProgram(program)`,
	 * where `program` is a valid shader program object. This prepares the graphics
	 * pipeline to render with the specified shaders. The default shader program remains
	 * bound after the call.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Returns an integer value stored in a private variable named `program`. It allows
	 * access to the program's ID from outside the class. The returned value represents
	 * the program identifier, which may be used for further processing or storage.
	 *
	 * @returns an integer representing a program value stored within the object.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Sets a uniform variable on the GPU with a single or multiple float values, depending
	 * on the number of values passed to it. It uses a switch statement to determine which
	 * glUniform function to call (unifrom1f, uniform2f, etc.) based on the length of the
	 * vals array.
	 *
	 * @param name uniform variable name to which the values are assigned.
	 *
	 * @param vals 1, 2, or 3 dimensions of floating-point values to be set as uniform
	 * variables on the GPU.
	 *
	 * Varargs array `float ...vals` is deconstructed into an array.
	 * Its length can be 1, 2, 3 or 4.
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
	 * Uploads a 4x4 matrix to a uniform location on the GPU. It retrieves the linear
	 * data from the provided matrix, creates a buffer with the flipped data, and passes
	 * it to OpenGL for use in a shader. The false flag indicates that the matrix is not
	 * transposed.
	 *
	 * @param name 1D uniform location of the matrix within the shader program, used to
	 * identify and update its value using the `glUniformMatrix4` method.
	 *
	 * @param mat 4x4 matrix to be transferred to the graphics pipeline for uniform
	 * application across the program.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the location of a uniform variable with the specified name from an OpenGL
	 * program. It uses the `GL20.glGetUniformLocation` method to obtain the location.
	 * The function returns the integer value representing the location of the uniform variable.
	 *
	 * @param name uniform location name to be retrieved from the OpenGL program.
	 *
	 * @returns an integer representing a uniform location ID.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Creates a shader program by loading and linking vertex and fragment shaders from
	 * specified files. It uses the GL20 library to create and validate the program, then
	 * returns a new instance of a Shader class with the created program ID.
	 *
	 * @param filename 3D shader file to be loaded and is used as a prefix for both vertex
	 * and fragment shader filenames.
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
	 * Compiles and attaches a new shader to an existing program. It creates a shader
	 * with the specified target type, sets its source code, and checks for compilation
	 * errors. If errors occur, it prints the error log and exits.
	 *
	 * @param target type of shader being created, such as GL_VERTEX_SHADER or
	 * GL_FRAGMENT_SHADER, to be used with OpenGL.
	 *
	 * @param src source code of the shader, which is used to set the shader's source
	 * code using `GL20.glShaderSource(shader, src)`.
	 *
	 * @param program ID of an existing program to which the newly created shader is attached.
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
	 * Validates a linked OpenGL program. It first links and then validates the program
	 * with `glLinkProgram` and `glValidateProgram`. If either operation fails, it prints
	 * the error log and exits the program with status code 1 using `System.err.println`
	 * and `System.exit`.
	 *
	 * @param program 32-bit handle to an OpenGL program object.
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
	 * Reads a specified file, opens it as an InputStream, reads its contents character
	 * by character, and concatenates them into a string. It returns this resulting string.
	 * If an IOException occurs during this process, the program prints the error message
	 * and terminates.
	 *
	 * @param file path to the file that is being read and processed by the method.
	 *
	 * @returns a string representation of the file's contents.
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
