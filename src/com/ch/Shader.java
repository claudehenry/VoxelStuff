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
 * has a constructor that takes an integer program ID and provides methods to bind
 * the program, get the program ID, and set uniform values for floating-point and
 * matrix4f data types. Additionally, there are methods to load vertex and fragment
 * shaders and validate the program.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to a specific GL context, enabling its use for rendering operations.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of the `program` field of the current instance.
	 * 
	 * @returns an integer value representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * takes a string name and an array of float values, and uses the `glUniformf()`
	 * method to set the corresponding uniform value(s) in the current GL context. The
	 * length of the input array determines which `glUniformf()` method is called, with
	 * longer arrays using more parameters.
	 * 
	 * @param name name of the uniform location in the GL context, which is used to
	 * determine the appropriate OpenGL function to call for the specified number of float
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
	 * sets a 4x4 uniform matrix value using the `glUniformMatrix4` method from the OpenGL
	 * API. The matrix data is passed as a parameter and flipped before being stored in
	 * the GPU's memory location.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored
	 * in the GPU memory.
	 * 
	 * @param mat 4x4 matrix to be uniformed and is passed as an instance of the `Matrix4f`
	 * class.
	 * 
	 * 	- `getLocation(name)` is used to retrieve the location of a uniform buffer in the
	 * current program state.
	 * 	- `GL20.glUniformMatrix4()` is a method that sets a 4x4 matrix as a uniform
	 * variable in the currently active program.
	 * 	- `false` indicates that the matrix should be stored in row-major order, which
	 * is the default for GL matrices.
	 * 	- `Util.createFlippedBuffer(mat.getLinearData())` creates a new buffer containing
	 * the matrix data in row-major order, and returns it as a flipped copy of the original
	 * matrix.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform named `name` within a program's shader, using
	 * the `GL20` class to interact with the GPU.
	 * 
	 * @param name 0-based index of the uniform location to retrieve in the program's
	 * uniform buffer objects.
	 * 
	 * @returns an integer representing the location of a uniform in a graphics program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * creates a shader program by loading vertex and fragment shaders from files,
	 * validating the program, and returning a new shader object representing the program.
	 * 
	 * @param filename file name of the shader to be loaded.
	 * 
	 * @returns a new `Shader` object representing a shader program created by combining
	 * a vertex shader and a fragment shader.
	 * 
	 * 	- The returned value is an instance of the `Shader` class, which represents a
	 * shader program in the OpenGL context.
	 * 	- The `Shader` object contains information about the shader program, including
	 * its handle (program), which can be used to access and manipulate the program's resources.
	 * 	- The `Shader` object also stores the handles of the vertex and fragment shaders
	 * that make up the program, allowing for easy manipulation and modification of these
	 * components.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader program and attaches it to a program, loading the given source
	 * code as a shader module.
	 * 
	 * @param target type of shader to create, with possible values of 0 for vertex shader,
	 * 1 for fragment shader, and 128 for geometry shader.
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
	 * validates a program by linking and validating it using the `glLinkProgram` and
	 * `glValidateProgram` functions, respectively. If any errors occur during validation,
	 * the function prints the error message to `System.err` and exits with a non-zero
	 * status code.
	 * 
	 * @param program 3D program to be validated and linked with the OpenGL driver.
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
	 * reads the contents of a given file as a string, handling potential IOExceptions.
	 * 
	 * @param file file whose contents are to be read and returned as a string by the
	 * `getText()` function.
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
