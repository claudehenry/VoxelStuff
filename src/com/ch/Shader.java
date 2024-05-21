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
 * has various methods for loading and binding shaders, uniform floating-point values,
 * and matrix4f values. It also provides a loadShader method to load shaders from
 * files and validate the program after loading.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to a specific context, enabling its use for rendering and other
	 * graphical operations.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * returns the value of a field named `program`.
	 * 
	 * @returns an integer representing the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * uniforms floating-point values for a given shader program based on the length of
	 * an array of input values.
	 * 
	 * @param name location of the uniform in the shader program, which is used to determine
	 * the correct GL API call for uniform assignment.
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
	 * glUniformMatrix4 method to set a 4x4 matrix uniform buffer object in OpenGL. It
	 * takes a string parameter representing the uniform location and a Matrix4f object
	 * containing the matrix data, which is then passed to OpenGL through the GL20 class.
	 * 
	 * @param name name of the uniform location for which the matrix is being set.
	 * 
	 * @param mat 4x4 uniform matrix to be stored as a GLUniform buffer.
	 * 
	 * 1/ `name`: A string representing the location where the uniform buffer will be
	 * stored in the GPU's memory.
	 * 2/ `mat`: A `Matrix4f` object containing a 4x4 matrix of floating-point values,
	 * which is passed as an argument to the function for storage in the GPU's memory
	 * using `GL20.glUniformMatrix4`. The `getLinearData()` method is used to create a
	 * flipped buffer of the matrix data, which is then passed to `GL20.glUniformMatrix4`
	 * for storage in the GPU's memory.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a GL20 program based on its name.
	 * 
	 * @param name 0-based index of a uniform location within a program's uniform buffer
	 * objects, as specified by the `GL20` function.
	 * 
	 * @returns an integer representing the location of a uniform variable in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader from a file and creates a program object that represents it.
	 * 
	 * @param filename file name of the shader to be loaded.
	 * 
	 * @returns a new instance of the `Shader` class, representing a shader program created
	 * by combining a vertex shader and a fragment shader.
	 * 
	 * 	- The function returns a new Shader object, which is an instance of the Shader
	 * class in Java.
	 * 	- The Shader object contains information about the vertex and fragment shaders
	 * that were loaded, including their handle (program) and the text of the shaders.
	 * 	- The program handle is an integer value that corresponds to the handle returned
	 * by GL20.glCreateProgram(), which was used to create the program for the shader.
	 * 	- The text of the vertex and fragment shaders are strings that contain the actual
	 * source code of the shaders, which were loaded from files using the `loadShader` function.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * loads a shader program into a graphics processing unit (GPU) from a string source.
	 * It creates a shader object using the `glCreateShader` method, sets its source code
	 * using `glShaderSource`, compiles it using `glCompileShader`, and attaches it to a
	 * program using `glAttachShader.
	 * 
	 * @param target type of shader to be created, with possible values of `GL_VERTEX_SHADER`,
	 * `GL_FRAGMENT_SHADER`, or `GL_GEOMETRY_SHADER`.
	 * 
	 * @param src 1:1 copy of the source code for the shader to be compiled.
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
	 * links and validates a program object, checking for errors and printing any error
	 * messages if they occur.
	 * 
	 * @param program 3D rendering program to be validated and linked with the OpenGL driver.
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
	 * reads the contents of a file and returns the resulting string.
	 * 
	 * @param file path of the file that contains the text to be read.
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
