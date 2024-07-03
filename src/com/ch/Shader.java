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
 * Provides a framework for creating and managing shaders for use in OpenGL applications.
 * It allows for the creation of vertex and fragment shaders, as well as the binding
 * and uniforming of values to specific locations within the shaders. The class also
 * includes methods for loading and validating the shaders.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Glues a program to the current GL context, allowing for access and manipulation
	 * of its resources.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves the value of a field named `program`.
	 * 
	 * @returns an integer representing the program value.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Takes a string `name` and an array of floats `vals`, then uses the `glUniformf`
	 * method to set the uniform value of the same name in the current OpenGL context,
	 * depending on the length of the `vals` array.
	 * 
	 * @param name name of the uniform location in the GL context.
	 * 
	 * @param vals 0 or more floating-point values that are passed to the `glUniform*`
	 * functions to set uniform parameters for a GL20 context.
	 * 
	 * * Length: `vals.length` is a variable that represents the number of elements in
	 * the `vals` array. It can take on values of 1, 2, 3, or 4.
	 * * Elements: Each element in the `vals` array represents a single float value. The
	 * values are stored in indices 0, 1, 2, or 3, depending on the length of the array.
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
	 * Sets a 4x4 uniform matrix using the `glUniformMatrix4` method from OpenGL's GL20
	 * class. The matrix is passed as a parameter and flipped before being stored in
	 * memory for later use.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored.
	 * 
	 * @param mat 4x4 homogeneous transformation matrix to be set as an uniform buffer
	 * object (UBO) using the `glUniformMatrix4` method.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves the location of a uniform in a program using the `GL20` class and the
	 * `glGetUniformLocation` method.
	 * 
	 * @param name name of the uniform location to retrieve in GL20.glGetUniformLocation().
	 * 
	 * @returns an integer representing the location of a uniform in a program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader program from a file and validates its components, returning a
	 * `Shader` object representing the program.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a `Shader` object representing a shader program created from the given
	 * vertex and fragment shaders.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Creates a new shader program and attaches it to the program object, compiles the
	 * shader source code and logs any compilation errors if they occur.
	 * 
	 * @param target type of shader being created, with possible values of `GL_VERTEX_SHADER`,
	 * `GL_FRAGMENT_SHADER`, or `GL_GEOMETRY_SHADER`.
	 * 
	 * @param src 1:1 text replacement of the source code of the shader to be compiled.
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
	 * Validates a program object and checks for any errors.
	 * 
	 * @param program 3D program to be linked and validated by the `GL20.glLinkProgram()`
	 * and `GL20.glValidateProgram()` methods, respectively.
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
	 * Reads the contents of a specified file as a string, handling potential errors gracefully.
	 * 
	 * @param file file whose contents are to be read and returned as a string by the
	 * `getText()` function.
	 * 
	 * @returns a string representation of the contents of a specified file.
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
