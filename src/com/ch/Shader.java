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
 * in Java provides a framework for creating and managing shaders for use in OpenGL
 * applications. It allows for the creation of vertex and fragment shaders, as well
 * as the binding and uniform functions to manipulate data within these shaders.
 * Additionally, the class provides methods for loading and validating shaders, as
 * well as utility methods for handling input streams.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to a set of data.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`.
	 * 
	 * @returns an integer value representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * is a method that sets a uniform float value to a specific location in a GL context
	 * based on the length of the input array.
	 * 
	 * @param name name of the uniform location in the GL context, which is used to
	 * determine the correct uniform function to call in the switch statement.
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
	 * sets a 4x4 matrix as a uniform variable in OpenGL context using `glUniformMatrix4`.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored.
	 * 
	 * @param mat 4x4 matrix to be uniformed.
	 * 
	 * 	- `name`: A string representing the uniform name.
	 * 	- `mat`: A `Matrix4f` object containing 16 double-precision floating-point values
	 * that represent a 4x4 matrix.
	 * 
	 * The function calls `GL20.glUniformMatrix4()` to set the uniform matrix with the
	 * specified name and data. The `getLocation()` method is used to retrieve the location
	 * of the uniform in the shader program, and the `Util.createFlippedBuffer()` method
	 * is used to create a flipped buffer of the matrix data for efficient storage in the
	 * GPU memory.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a program using the `GL20` class.
	 * 
	 * @param name name of a uniform in the OpenGL program, which is used to locate the
	 * corresponding uniform in the program's layout.
	 * 
	 * @returns an integer representing the location of a uniform in a graphics program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates its structure.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a new `Shader` object representing the combined vertex and fragment shaders
	 * from the given file names.
	 * 
	 * 	- The output is of type `Shader`, which represents a shader program created by
	 * combining a vertex shader and a fragment shader.
	 * 	- The shader program is represented by an integer ID assigned by GL20, which is
	 * a part of the Android API for graphics rendering.
	 * 	- The shader program can be validated using the `validateProgram` function, which
	 * checks for errors in the program's structure or syntax.
	 * 
	 * Overall, the `loadShader` function creates and returns a shader program by combining
	 * two shaders and then validating the resulting program.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program, compiles and attaches it to a program in OpenGL.
	 * 
	 * @param target type of shader to be created, which determines the functionality and
	 * structure of the generated shader code.
	 * 
	 * @param src source code of the shader to be compiled.
	 * 
	 * @param program 3D graphics program to which the loaded shader will be attached.
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
	 * validates a GPU program by linking and validating it with the OpenGL API.
	 * 
	 * @param program 3D graphics program to be validated and linked with the GPU.
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
	 * reads the contents of a file and returns its text content as a string.
	 * 
	 * @param file file to read and return its contents as a String.
	 * 
	 * @returns a string containing the contents of the specified file.
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
