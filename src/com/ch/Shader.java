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
 * Is designed to manage OpenGL shaders in a program. It loads shader files and
 * compiles them into a single program that can be used for rendering graphics. The
 * class provides methods to bind the shader program, set uniform values, and validate
 * the program.
 */
public class Shader {	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Specifies a program for rendering graphics using OpenGL's GL20 library. It uses
	 * the `glUseProgram` function to bind the specified program, which enables the usage
	 * of its shaders and other resources for drawing graphics.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves and returns the value of the instance variable `program`. This variable
	 * is presumably a data member of the class, holding some information related to a
	 * program. The function provides access to this internal state without modifying it.
	 *
	 * @returns an integer representing the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Sets uniform floating-point values for a shader program. It accepts a variable
	 * number of float arguments and determines the correct OpenGL function to call based
	 * on the number of arguments, then applies them to the specified location using the
	 * corresponding `glUniform` method.
	 *
	 * @param name location of a uniform variable in OpenGL and is used to identify the
	 * target uniform variable for subsequent updates.
	 *
	 * @param vals 1 to 4 floating-point values passed as an array, which determine the
	 * number of uniform floats to set based on its length.
	 *
	 * Varargs array is an array of float with variable length. It accepts one or more
	 * values and passes them to the function. The main property of varargs in Java is
	 * that it can be called with any number of arguments.
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
	 * Sets a uniform matrix for a shader program. It takes a name and a Matrix4f object
	 * as inputs, converts the matrix to a buffer, and then applies it using `glUniformMatrix4`.
	 *
	 * @param name name of the uniform variable that is being set to the specified matrix
	 * value.
	 *
	 * @param mat 4x4 matrix that is to be set as the uniform variable with the specified
	 * name.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the uniform location for a specified shader program and variable name
	 * using the OpenGL API's `glGetUniformLocation` method. The returned value represents
	 * the location in memory where the uniform is stored. This information is used to
	 * access and modify the uniform's values.
	 *
	 * @param name identifier of the uniform variable for which the location is to be retrieved.
	 *
	 * @returns an integer representing a uniform location in OpenGL.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader by creating a new OpenGL program and attaching vertex and fragment
	 * shaders to it. It then validates the program and returns a new `Shader` object
	 * with the loaded program.
	 *
	 * @param filename name of a file from which shader code is retrieved for loading and
	 * compilation.
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
	 * Creates a shader object based on the specified target, compiles the shader source
	 * code, checks for compilation errors, and attaches the compiled shader to a program
	 * if no errors occur during compilation.
	 *
	 * @param target type of shader to be created, specifying whether it is a vertex or
	 * fragment shader.
	 *
	 * @param src source code of the shader that is being loaded and compiled using the
	 * OpenGL API.
	 *
	 * @param program OpenGL program object to which the created shader is attached for
	 * compilation and linking purposes.
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
	 * Links and validates a program with OpenGL. It checks if linking was successful,
	 * prints an error message and exits if not. Then it validates the program, and if
	 * validation fails, it prints an error log and terminates the program.
	 *
	 * @param program 32-bit named buffer object that is being linked or validated for
	 * OpenGL usage.
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
	 * Reads the contents of a specified file into a string variable, converting each
	 * character to its corresponding ASCII value and appending it to the string. If an
	 * IO error occurs during the reading process, it prints the error message and
	 * terminates the program.
	 *
	 * @param file file path that is to be read and its contents are returned as a string
	 * by the function.
	 *
	 * @returns a string representation of the specified file's content.
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
