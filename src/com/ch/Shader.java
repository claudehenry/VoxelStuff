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
 * from the file provides a way to load and manage shaders in OpenGL. It allows for
 * the creation of a vertex shader and a fragment shader, which can be linked together
 * to create a program. The class also provides methods for uniform floats and matrices,
 * as well as validation and logging functions to ensure proper functioning of the
 * shader program.
 */
public class Shader {
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to the current GL context, enabling its use for rendering.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field called `program`.
	 * 
	 * @returns the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * in Java is a method that sets floating-point values to GL uniform locations based
	 * on the length of an array of values passed as a parameter.
	 * 
	 * @param name 0-based index of the uniform location in the program's layout, which
	 * is used to determine the correct GL function to call for the given number of input
	 * values.
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
	 * sets a 4x4 uniform matrix value using the `glUniformMatrix4` method and storing
	 * it in a location specified by a name parameter.
	 * 
	 * @param name 0-based index of the uniform location where the matrix is to be stored.
	 * 
	 * @param mat 4x4 homogeneous transformation matrix that is to be uniformized.
	 * 
	 * 	- `name`: A string representing the name of the uniform location where the matrix
	 * will be stored.
	 * 	- `mat`: A `Matrix4f` object representing a 4x4 floating-point matrix. This object
	 * contains the matrix data in its linear data, which is passed to the `glUniformMatrix4`
	 * function to store the matrix at the specified location.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform named `name` within a program's GPU using `GL20.glGetUniformLocation`.
	 * 
	 * @param name 0-based index of a uniform location within a program specified by the
	 * `program` argument, which is passed as a GL20 object to the `glGetUniformLocation()`
	 * function.
	 * 
	 * @returns an integer representing the location of a uniform in a GPU program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file, creating and validating the program upon
	 * successful loading. It returns a new `Shader` object representing the loaded program.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a new instance of the `Shader` class, representing a shader program created
	 * by combining a vertex shader and a fragment shader.
	 * 
	 * 	- `program`: The program object created by `glCreateProgram()` is stored in the
	 * variable `program`. This object represents the linked shader program that can be
	 * used for rendering.
	 * 	- `Shader`: The `Shader` class instance is returned as the output of the function,
	 * which contains information about the shader program, including its program object
	 * and other relevant details.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader object, loads shader source code from a string, compiles it,
	 * and attaches the resulting shader to a program object.
	 * 
	 * @param target type of shader to create, with values ranging from 0 (vertex shader)
	 * to 1 (fragment shader).
	 * 
	 * @param src source code for the shader to be compiled.
	 * 
	 * @param program 3D graphics program that the loaded shader will be attached to and
	 * used by.
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
	 * verifies the linking and validation of a GPU program. It checks for errors during
	 * linking and validation, printing an error message to the console if any occur, and
	 * exits the program with a failure status if necessary.
	 * 
	 * @param program 3D program that needs to be validated and linked.
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
	 * reads the contents of a specified file as a string, handling potential IOExceptions.
	 * 
	 * @param file file from which to read the text.
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
