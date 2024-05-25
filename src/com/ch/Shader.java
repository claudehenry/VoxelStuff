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
 * in Java provides a framework for creating and managing shaders for use in graphics
 * rendering applications. The class offers functions for binding and unbinding
 * shaders, as well as uniform and matrix uniform operations. Additionally, the class
 * includes methods for loading and validating shaders.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to an active context, enabling its use for rendering tasks.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of the `program` field of the current instance.
	 * 
	 * @returns the value of the `program` field, which is an integer.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * updates a uniform value in a shader program based on the length of an array of
	 * values passed as an argument. It calls `glUniform<n>` with the location of the
	 * uniform and the corresponding value from the array, where `n` is the length of the
	 * array.
	 * 
	 * @param name location of the uniform in the shader program, and is used to determine
	 * the appropriate glUniform*() function to call for the given number of input values.
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
	 * updates a uniform matrix with the given name using the `glUniformMatrix4` method
	 * from the `GL20` class, passing the matrix data as a flipped buffer.
	 * 
	 * @param name 0-based index of the uniform location where the matrix is to be stored.
	 * 
	 * @param mat 4x4 matrix to be uniformed and is passed as an instance of the `Matrix4f`
	 * class to the `unifromMat4()` function for uniforming.
	 * 
	 * 	- `GL20.glUniformMatrix4`: This is a GL API function that sets a 4x4 uniform matrix.
	 * 	- `getLocation(name)`: This function retrieves the location object for the specified
	 * name. The name can be any valid Java identifier.
	 * 	- `Util.createFlippedBuffer(mat.getLinearData())`: This creates a flipped buffer
	 * of the input matrix's linear data, which is necessary for some GL implementations.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * returns the location of a uniform in a specified OpenGL program using the `GL20`
	 * class and `glGetUniformLocation` method.
	 * 
	 * @param name name of the uniform location to retrieve, which is passed as a string
	 * argument to the `glGetUniformLocation()` function.
	 * 
	 * @returns an integer representing the location of a uniform in a graphics program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it. It creates a new shader object
	 * and returns it upon validation success.
	 * 
	 * @param filename file name of the shader to be loaded.
	 * 
	 * @returns a newly created `Shader` object that represents a shader program loaded
	 * from a file.
	 * 
	 * 	- The output is a `Shader` object that represents a shader program created by
	 * combining a vertex shader and a fragment shader using the `glCreateProgram` method.
	 * 	- The `Shader` object has two fields: `program`, which stores the program ID
	 * returned by `glCreateProgram`, and `text`, which stores the text of the vertex and
	 * fragment shaders used to create the program.
	 * 	- The `program` field is an integer value that uniquely identifies the shader program.
	 * 	- The `text` field is a string that contains the source code of the vertex and
	 * fragment shaders used to create the program.
	 * 
	 * The returned `Shader` object can be used for various purposes such as rendering
	 * graphics, performing computation on GPU, or storing data in GPU memory.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * loads a shader program into memory by creating a new shader object and setting its
	 * source code using the `GL20.glShaderSource()` method, then compiling it using
	 * `GL20.glCompileShader()`. If compilation fails, an error message is printed to
	 * `System.err` and the program exits with a status of 1. Finally, the shader is
	 * attached to a program object using `GL20.glAttachShader()`.
	 * 
	 * @param target type of shader being created, which is specified by one of the values
	 * defined in the `GL20.GL_VERTEX_SHADER` constant.
	 * 
	 * @param src Shader source code to be compiled.
	 * 
	 * @param program 3D graphics program that the shader will be attached to.
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
	 * validates a program object in OpenGL. It first checks the linking status and then
	 * validates the program using the `glValidateProgram` function. If there are any
	 * errors, it prints the error message to `System.err` and exits with a non-zero
	 * status code.
	 * 
	 * @param program 3D rendering program to be validated and linked with the OpenGL library.
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
	 * reads the contents of a specified file as a string, handling potential exceptions.
	 * 
	 * @param file file that contains the text to be read.
	 * 
	 * @returns a string representation of the contents of a given file.
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
