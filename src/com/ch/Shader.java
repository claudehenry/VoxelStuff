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
 * in Java provides functions for binding a program, uniform floats and matrices, and
 * loading vertex and fragment shaders. It also includes methods for validating the
 * program and getting location of uniforms and variables.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * sets the current GL program to be used for rendering.
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
	 * uniforms floating-point values in a shader program based on the length of the input
	 * array.
	 * 
	 * @param name location of the uniform in the shader program, which is used to determine
	 * the appropriate glUniform* function to call for the given number of input values.
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
	 * sets a 4x4 matrix as a uniform buffer in OpenGL using `glUniformMatrix4`. The
	 * matrix is obtained from the `mat` parameter and flipped before being passed to
	 * OpenGL for storage.
	 * 
	 * @param name 16-character name of the uniform to be updated.
	 * 
	 * @param mat 4x4 uniform matrix to be updated or manipulated by the `unifromMat4()`
	 * function.
	 * 
	 * 	- `name`: A string representing the location in the scene where the matrix will
	 * be applied.
	 * 	- `mat`: An instance of `Matrix4f`, which is a type-safe representation of a 4x4
	 * homogeneous transformation matrix, as described by the OpenGL specification.
	 * 	- `getLocation()`: A method that retrieves a `GLint` representing the location
	 * in the scene where the matrix will be applied. This method is used to retrieve the
	 * correct location index from the `name` parameter.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform named `name` within a program's universe.
	 * 
	 * @param name name of the uniform location to be retrieved.
	 * 
	 * @returns an integer representing the location of a uniform in a GPU program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it. It creates a new shader program
	 * object and returns it after loading the vertex and fragment shaders from the file.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a new `Shader` object representing a compiled shader program.
	 * 
	 * 	- `Shader`: This is the type of the returned object, which is a wrapper around
	 * the underlying GPU program.
	 * 	- `program`: This is an integer value that represents the program ID created by
	 * GL20 for the shader.
	 * 	- `loadShader()` method: This method creates and loads two separate shaders (vertex
	 * and fragment) into the GPU program, and validates the program for errors.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader program and attaches it to the program, compiling the shader
	 * source code using the `glCompileShader` method.
	 * 
	 * @param target type of shader to be created, with possible values of `GL_VERTEX_SHADER`,
	 * `GL_FRAGMENT_SHADER`, or `GL_GEOMETRY_SHADER`.
	 * 
	 * @param src 4 byte code of the shader program to be compiled.
	 * 
	 * @param program 3D graphical program to which the loaded shader will be attached.
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
	 * validates a program object in OpenGL by checking its linking and validation statuses.
	 * If any errors are found, the function prints the error message to the console and
	 * exits with a non-zero status code.
	 * 
	 * @param program 3D graphics program to be validated, and it is passed to the
	 * `glLinkProgram()` and `glValidateProgram()` functions for validation.
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
	 * reads the contents of a given file and returns its text content as a string.
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
