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
 * in Java provides a way to load and manipulate shaders for use with OpenGL. It
 * allows for binding and uniform functions, as well as loading shaders from files
 * and validating the program.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to an active rendering context, allowing for the use of its resources
	 * and functions in subsequent API calls.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`.
	 * 
	 * @returns the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * takes a string name and an array of float values, and uses the `glUniformf` method
	 * to set the specified uniform value in the OpenGL context. The function handles
	 * different lengths of arrays using a switch statement, calling the appropriate
	 * `glUniformf` method for each length.
	 * 
	 * @param name name of the uniform location being set, which is used to determine the
	 * correct GL20 method to call for the given number of float values.
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
	 * updates an OpenGL uniform matrix with the specified name using the `glUniformMatrix4`
	 * method and stores the matrix data in a flipped buffer for later use.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored.
	 * 
	 * @param mat 4x4 matrix to be uniformed and is passed as an instance of the `Matrix4f`
	 * class to the `unifromMat4` function.
	 * 
	 * 	- `name`: A string parameter representing the location to which the matrix will
	 * be assigned in the GPU program's uniform buffer.
	 * 	- `mat`: An instance of `Matrix4f`, containing the 16 elements that make up a 4x4
	 * homogeneous transformation matrix. These elements are organized into four rows,
	 * each consisting of a scalar, a vector, a vector, and another scalar.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a GL program using the `GL20` class.
	 * 
	 * @param name 0-based index of an uniform location within a GLSL program that is
	 * being accessed through the `getLocation()` function, and it is used to retrieve
	 * the location of the corresponding uniform variable.
	 * 
	 * @returns an integer representing the uniform location within a shader program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it. It creates a program object
	 * and loads a vertex and fragment shader from files specified by the filename
	 * parameter, linking them together to create a complete shader program.
	 * 
	 * @param filename file name of the shader to be loaded, which is used to identify
	 * and load the corresponding vertex and fragment shaders from disk.
	 * 
	 * @returns a new `Shader` object representing a compiled shader program.
	 * 
	 * 	- The `Shader` object returned is an instance of the `Shader` class, which
	 * represents a shader program that can be used in a 3D graphics pipeline.
	 * 	- The `program` field of the `Shader` object refers to the program ID created by
	 * `GL20.glCreateProgram()`, which is a unique identifier for the shader program.
	 * 	- The `getText` method used to load the vertex and fragment shaders returns the
	 * actual text of the shaders as a string, which is then passed to `GL20.glCreateShader()`
	 * to create the shader programs.
	 * 	- The `validateProgram()` method is called after creating the shader programs to
	 * ensure that they are valid and can be used in the 3D graphics pipeline.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and attaches it to a program handle, compiling the shader
	 * source code using the GL20 API.
	 * 
	 * @param target type of shader being created, and it determines the shader's functionality.
	 * 
	 * @param src source code for the shader to be compiled and executed by the GPU.
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
	 * validates a program object by calling `glLinkProgram`, `glValidateProgram`, and
	 * checking their statuses to ensure linking and validation succeed.
	 * 
	 * @param program 3D graphics program to be validated and linked with the OpenGL driver.
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
	 * @param file file from which the text is to be read.
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
