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
 * in Java is a class that provides functionality for loading and managing shaders,
 * including uniform and matrix4f functions. It also includes loadShader and
 * validateProgram methods to load and validate the shaders. Additionally, it has
 * private fields and methods to handle the GL20 API calls.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to the current GL context, enabling its use for rendering and other
	 * graphics operations.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`. The returned value is an integer.
	 * 
	 * @returns an integer representing the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * in Java takes a string name and an array of floats as input, and uses the GL20
	 * class to set uniform values for a shader program based on the length of the input
	 * array.
	 * 
	 * @param name 0-based index of the uniform location within the current program.
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
	 * glues a uniform matrix to the GPU. It takes a name and a Matrix4f object as input,
	 * then passes the matrix data to GL20.glUniformMatrix4 with the appropriate location
	 * and transpose parameters.
	 * 
	 * @param name name of the uniform to be updated with the matrix value.
	 * 
	 * @param mat 4x4 matrix that will be stored and accessed as a uniform buffer in the
	 * shader program.
	 * 
	 * 	- The `Matrix4f` class represents a 4D matrix with floating-point elements.
	 * 	- The `getLinearData()` method returns a copy of the matrix's linear data as an
	 * array of floats.
	 * 	- The `Util.createFlippedBuffer()` method creates a new buffer that stores the
	 * mirrored version of the input matrix.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform variable in a GPU program specified by the
	 * `name` parameter using the `GL20.glGetUniformLocation()` method.
	 * 
	 * @param name 0-based index of an uniform location within the currently active program
	 * for which to retrieve information, as passed through the `GL20.glGetUniformLocation()`
	 * method.
	 * 
	 * @returns an integer representing the location of a uniform variable in a graphics
	 * processing unit (GPU) program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it for use in a graphics application.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a `Shader` object representing the combined vertex and fragment shaders
	 * loaded from a file.
	 * 
	 * 	- The returned value is an instance of `Shader`, which represents a shader program
	 * that has been loaded from a file.
	 * 	- The `Shader` object contains information about the shader program, such as its
	 * name and the names of its vertex and fragment shaders.
	 * 	- The `program` field of the `Shader` object refers to the OpenGL program handle
	 * created by `glCreateProgram()`.
	 * 	- The `validateProgram()` function is called after the shader programs are loaded
	 * to ensure that they are valid and can be used for rendering.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and compiles it from a string source, then attaches it
	 * to a program handle.
	 * 
	 * @param target type of shader being created, which determines the shader's behavior
	 * and functionality.
	 * 
	 * @param src source code of the shader to be compiled.
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
	 * validates a program by checking its linking and validation status, printing any
	 * error messages if they occur.
	 * 
	 * @param program 3D programming handle that is being validated and linked.
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
	 * reads the contents of a specified file as a string and returns it.
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
