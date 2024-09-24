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
 * Handles OpenGL shader programs, enabling compilation and loading of shaders from
 * files, uniform variable management, and program binding. It facilitates the creation
 * and use of shader programs in an application. The class includes functionality for
 * error handling and program validation.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Binds a program using OpenGL's `glUseProgram` command, selecting it for subsequent
	 * rendering operations. This operation specifies which program is currently active
	 * and ready to use. It enables the use of shaders associated with the bound program.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Returns the value of a field named `program`. It is a getter method that allows
	 * access to the program attribute, allowing it to be used and manipulated elsewhere
	 * in the code. This attribute stores some data related to the program or application
	 * being referenced.
	 *
	 * @returns an integer representing a program value stored within the class instance.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Sets a uniform variable in OpenGL with specified name and values. The number of
	 * values determines the type of the uniform (1, 2, 3 or 4 floats). Values are passed
	 * as varargs.
	 *
	 * @param name location of a uniform variable and is used to identify which uniform
	 * value is being set.
	 *
	 * @param vals 1-4 floating-point values to be passed to the GLSL shader uniform
	 * variable with the specified name, allowing for different number of values to be
	 * uniformly set based on their length.
	 *
	 * Vals is an array of float values with variable length. It can be passed as one or
	 * more arguments to the function. The varargs (variable number of arguments) syntax
	 * enables this flexibility.
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
	 * Uploads a 4x4 matrix to OpenGL using the `glUniformMatrix4` function.
	 * It takes two parameters: the name of the uniform variable and the matrix data to
	 * be uploaded.
	 * The matrix data is copied into a buffer that can be accessed by OpenGL.
	 *
	 * @param name identifier for the uniform matrix location to be set on the GPU.
	 *
	 * @param mat 4x4 matrix to be uploaded to the GPU for uniform use.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Returns a uniform location from an OpenGL program based on the given variable name.
	 * It uses the `glGetUniformLocation` function to retrieve the location of the specified
	 * uniform variable. The returned value can be used for passing data to shaders.
	 *
	 * @param name uniform location name to be retrieved from OpenGL.
	 *
	 * @returns an integer representing a uniform location.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Creates a shader object by loading a vertex and fragment shader from files specified
	 * by filename, assembling them into a program, validating it, and returning a new
	 * Shader instance with its ID.
	 *
	 * CreateProgram is called to generate the program ID.
	 * Shader code is loaded into the program using loadShader method calls.
	 * ValidateProgram is called to check for errors in the program.
	 *
	 * @param filename 3D model file name from which to load vertex and fragment shader
	 * source code.
	 *
	 * @returns a `Shader` object initialized with an OpenGL program ID.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Creates a new shader object and compiles it from source code. If compilation fails,
	 * it prints an error message and exits the program with status 1. The compiled shader
	 * is then attached to an existing program object.
	 *
	 * @param target type of shader being created, such as GL_VERTEX_SHADER or GL_FRAGMENT_SHADER.
	 *
	 * @param src source code of the shader that is being loaded and compiled into a
	 * shader object.
	 *
	 * @param program ID of the program object to which the newly created shader is
	 * attached for compilation and linking.
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
	 * Links and validates an OpenGL program with a given ID. It checks for linking errors
	 * by querying GL_LINK_STATUS and prints log if error occurs, then exits. It also
	 * validates the program by checking GL_VALIDATE_STATUS and handles validation errors
	 * similarly.
	 *
	 * @param program 32-bit name of an OpenGL program object to be validated for linking
	 * and validation status.
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
	 * Reads a specified file, opens it as an input stream, and extracts its contents
	 * character by character. The extracted characters are concatenated into a string,
	 * which is then returned by the function after closing the input stream.
	 *
	 * @param file path to the file from which the function reads and returns its contents
	 * as a string.
	 *
	 * @returns a string representation of the file's contents.
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
