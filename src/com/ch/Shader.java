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
 * Provides functionality for creating and manipulating shaders in OpenGL. It allows
 * users to load and validate shaders, as well as uniforms and matrices. The class
 * also provides methods for binding the shader program and getting the location of
 * uniforms and variables in the shader code. Additionally, it includes utility methods
 * for loading shader codes from files and creating flipped buffers for matrix operations.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Glues a program to a particular context, enabling its use for rendering and other
	 * graphics-related tasks.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves the program variable and returns its value as an integer.
	 * 
	 * @returns an integer value representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Dynamically updates a uniform value stored at a specified location in an OpenGL
	 * context based on the length of an array of floating-point values passed to it.
	 * 
	 * @param name location of the uniform in the program, which is used to determine the
	 * appropriate GL function to call for uniforms with different lengths.
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
	 * Sets a 4x4 matrix uniform buffer object (UBO) in OpenGL using the `glUniformMatrix4`
	 * function. The matrix data is passed as an argument and is flipped before being
	 * stored in the UBO location.
	 * 
	 * @param name 3D model's matrix uniform name.
	 * 
	 * @param mat 4x4 uniform matrix to be uploaded to the GPU.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the location of a uniform variable in a shader program using the
	 * `GL20.glGetUniformLocation()` method.
	 * 
	 * @param name 0-based index of a uniform location within the current program, which
	 * is used to retrieve the corresponding uniform value by calling `GL20.glGetUniformLocation()`.
	 * 
	 * @returns an integer representing the location of a uniform in the GPU program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader program from a file and validates it. It creates a new program
	 * object and loads the vertex and fragment shaders contained within the file into
	 * the program.
	 * 
	 * @param filename filename of the shader to be loaded.
	 * 
	 * @returns a new `Shader` object representing the combined vertex and fragment shaders.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Creates a shader object, sets its source code, compiles it, and attaches it to a
	 * program.
	 * 
	 * @param target type of shader being created, which determines the shader's functionality
	 * and how it is used in the program.
	 * 
	 * @param src source code of the shader to be compiled.
	 * 
	 * @param program 3D graphics program that the shader will be attached to after being
	 * compiled.
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
	 * Validates a program object and checks if it has been successfully linked and
	 * validated. It logs any errors to the console and exits the program if there are
	 * any issues.
	 * 
	 * @param program 3D graphics program to be validated and linked with the GL API.
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
	 * Reads the contents of a specified file as a string, handling potential IOExceptions
	 * and returning the result.
	 * 
	 * @param file file from which the text is to be read.
	 * 
	 * @returns a string containing the contents of the specified file.
	 * 
	 * * The output is a string containing the text from the specified file.
	 * * The text is obtained by reading the bytes of the file using an `InputStream`,
	 * and then converting them to characters using their ASCII values.
	 * * The output is a single string representing the entire contents of the input file.
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
