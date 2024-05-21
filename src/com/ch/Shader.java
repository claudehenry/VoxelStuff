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
 * in Java provides functionality for loading and binding shaders, uniform and matrix
 * operations, and validation of the program. The loadShader method takes the filename
 * as input and returns a new Shader object with the loaded program. The class also
 * includes methods for uniform and matrix operations, and validation of the program.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to the current GL context, making its resources available for use.
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
	 * is a Java method that takes a string parameter `name` and an array of floating-point
	 * values `vals`. It switches on the length of the input array to call the appropriate
	 * `glUniform` function from OpenGL, setting the specified uniform value for the given
	 * name in the current GL context.
	 * 
	 * @param name name of the uniform location to which the values are being applied.
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
	 * updates a uniform matrix using the `glUniformMatrix4` method, passing the uniform
	 * location handle and flipping the matrix data if necessary.
	 * 
	 * @param name 0-based integer index of the uniform location where the matrix is to
	 * be stored.
	 * 
	 * @param mat 4x4 matrix that contains the uniform value to be set for the specified
	 * location name.
	 * 
	 * 	- `name`: The name of the uniform variable, which is used to identify the location
	 * where the uniform value should be stored in the GPU's memory.
	 * 	- `mat`: A `Matrix4f` object representing a 4x4 matrix with floating-point elements,
	 * which contains the uniform value to be stored in the GPU's memory. The `getLinearData()`
	 * method is used to retrieve the array data of the matrix, which is then passed as
	 * an argument to `GL20.glUniformMatrix4()`.
	 * 	- `false`: A boolean parameter indicating whether the uniform value should be
	 * transposed (i.e., flipped) before being stored in the GPU's memory.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform variable in a shader program using the
	 * `GL20.glGetUniformLocation` method.
	 * 
	 * @param name identity of a uniform program in the code being searched for location
	 * information.
	 * 
	 * @returns an integer representing the location of a uniform in a GPU program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it.
	 * 
	 * @param filename filename of the shader to be loaded.
	 * 
	 * @returns a newly created Shader object.
	 * 
	 * The return type of the function is `Shader`, which represents a shader program
	 * that can be used for rendering 3D graphics in a Java application.
	 * 
	 * The `Shader` object contains information about the shader program, including its
	 * handle (represented by the variable `program`) and the names of its vertex and
	 * fragment shaders (stored in the `vertexShader` and `fragmentShader` fields, respectively).
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and loads a shader source code into it. It compiles the
	 * shader and attaches it to the program.
	 * 
	 * @param target type of shader being created, which determines the shader's functionality
	 * and how it will be used in the program.
	 * 
	 * @param src 10-character buffer containing the shader source code to be compiled.
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
	 * verifies the linking and validation of a program with the specified ID, using
	 * `glLinkProgram`, `glGetProgramInfoLog`, and `glValidateProgram`.
	 * 
	 * @param program 3D rendering program to be validated and linked with the GPU.
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
	 * reads the contents of a specified file and returns a `String` representation of
	 * its contents.
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
