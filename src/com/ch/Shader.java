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
 * provides a binding mechanism for program and uniform functions, as well as methods
 * to set uniform values and matrices. It also includes loadShader methods to load
 * shaders from files and validate the program.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to a specific OpenGL context, enabling its use for rendering.
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
	 * takes a string `name` and an array of float values `vals`. It dynamically calls
	 * `glUniform` with the appropriate argument count based on the length of the `vals`
	 * array, passing the values to the uniform location in the GL context.
	 * 
	 * @param name name of the uniform location for which the values are being set.
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
	 * sets a 4x4 floating-point matrix as a uniform buffer using the `glUniformMatrix4`
	 * method from OpenGL.
	 * 
	 * @param name 0-based index of an uniform matrix location where the `mat` parameter's
	 * 4x4 floating-point matrix representation will be stored as a glUniformMatrix4 call.
	 * 
	 * @param mat 4x4 homogeneous transformation matrix to be uniformized.
	 * 
	 * 	- `getLocation(name)` is an unspecified method that retrieves the location of a
	 * uniform variable with the given name.
	 * 	- `glUniformMatrix4()` is a GL20 method that sets a 4x4 matrix as a uniform buffer
	 * in the current program's GPU.
	 * 	- `false` is a boolean argument indicating whether to create a new buffer or use
	 * an existing one.
	 * 	- `Util.createFlippedBuffer(mat.getLinearData())` is a utility method that creates
	 * a flipped copy of the input matrix data, which is necessary for proper GL matrix
	 * handling.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the uniform location of a named uniform within a specified program using
	 * the `GL20` class.
	 * 
	 * @param name name of the uniform location to retrieve.
	 * 
	 * @returns an integer representing the location of a uniform in a program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads and creates a shader program based on two separate shader files (vertex and
	 * fragment shaders) and validates the resulting program.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a `Shader` object representing a shader program created by combining two
	 * shader modules.
	 * 
	 * 	- The `Shader` object returned is of type `Shader`, which represents a shader
	 * program in OpenGL.
	 * 	- The `program` field of the `Shader` object contains the handle of the created
	 * program.
	 * 	- The `TextureUnits` field of the `Shader` object contains an array of texture
	 * units that are bound to the program.
	 * 	- The `Uniforms` field of the `Shader` object contains an array of uniforms that
	 * are defined in the shader code.
	 * 	- The `Attributes` field of the `Shader` object contains an array of attributes
	 * that are defined in the shader code.
	 * 
	 * The `loadShader` function takes two strings as input, which represent the vertex
	 * and fragment shader codes, respectively. These codes are loaded into the program
	 * using the `GL20.glCreateProgram()` function, and then validated using the
	 * `validateProgram()` function. Finally, a new `Shader` object is created and returned
	 * to the caller.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and attaches it to a program, compiles the shader source
	 * code, and handles any compilation errors.
	 * 
	 * @param target type of shader to be created, with possible values being `GL_VERTEX_SHADER`,
	 * `GL_FRAGMENT_SHADER`, or `GL_GEOMETRY_SHADER`.
	 * 
	 * @param src source code of the shader to be compiled.
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
	 * validates a GL program by linking it and checking its validation status. If there
	 * are any errors, the function prints the error message and exits with a non-zero
	 * status code.
	 * 
	 * @param program 3D program to be validated and linked with the GPU.
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
	 * reads the contents of a specified file and returns the resulting string.
	 * 
	 * @param file file from which the text is to be read.
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
