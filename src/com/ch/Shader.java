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
 * from the file provides functionality for loading and binding shaders, uniform
 * floating-point values, and matrices. It also includes utility methods for validating
 * the program and getting location of uniforms and variables in the shader code.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to the current GL context, allowing it to be used for rendering.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`.
	 * 
	 * @returns an integer representing the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * is a void method that takes a string and an array of float values as input, then
	 * applies a switch statement to determine the appropriate GL20 method call for setting
	 * uniform float values in a shader program based on the length of the input array.
	 * 
	 * @param name location of the uniform in the shader program, and is used to specify
	 * the properglUniform*() function to use for updating the uniform value.
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
	 * sets a 4x4 uniform matrix `mat` using the `glUniformMatrix4` method with the given
	 * location name and transpose status.
	 * 
	 * @param name 0-based index of an uniform matrix location to which the `mat` parameter's
	 * value will be assigned.
	 * 
	 * @param mat 4x4 matrix of transformation values that will be applied to the specified
	 * location in the current GL context.
	 * 
	 * The input `mat` is an instance of the `Matrix4f` class, which represents a 4x4
	 * homogeneous transformation matrix in floating-point format. The `getLinearData()`
	 * method returns a byte array containing the binary representation of the matrix.
	 * The `Util.createFlippedBuffer(mat.getLinearData())` line reverses the order of the
	 * bytes in the buffer for compatibility with GL's uniform matrix storage layout.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * returns the uniform location of a named uniform in a specified program using the
	 * `GL20` class method `glGetUniformLocation`.
	 * 
	 * @param name 17-digit program and uniform location to retrieve within the OpenGL
	 * (GL) API.
	 * 
	 * @returns an integer representing the location of a uniform in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and creates a new shader object representing it.
	 * 
	 * @param filename filename of the shader to be loaded.
	 * 
	 * @returns a `Shader` object representing a compiled shader program.
	 * 
	 * The function returns a new Shader object representing a vertex shader and a fragment
	 * shader that have been linked together using the `GL_CREATE_PROGRAM` function. The
	 * vertex shader is identified by the `GL20.glCreateProgram()` value of 1, while the
	 * fragment shader is identified by the `GL20.glCreateProgram()` value of 2.
	 * 
	 * The Shader object returned by the function has two components: the vertex shader
	 * and the fragment shader. These components are represented as integers in the
	 * program, specifically the values of 1 and 2, respectively.
	 * 
	 * The Shader object also includes other attributes such as the name of the file from
	 * which the shader was loaded (represented by the `filename` parameter), the type
	 * of shader (vertex or fragment), and the program ID created by `GL20.glCreateProgram()`.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program, compiles it and attaches it to a program handle.
	 * 
	 * @param target type of shader being created, with possible values of 0 for a vertex
	 * shader or 1 for a fragment shader.
	 * 
	 * @param src Shading source code for the shader being created.
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
	 * validates a program by checking its linking and validation status, printing any
	 * error messages if they occur.
	 * 
	 * @param program 3D graphics program to be validated and linked with the OpenGL API.
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
	 * reads the contents of a given text file and returns its encoded string value.
	 * 
	 * @param file file whose text is to be read and returned as a string by the `getText()`
	 * function.
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
