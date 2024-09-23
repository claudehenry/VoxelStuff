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
 * Is designed to manage OpenGL shaders and programs, allowing for easy loading,
 * binding, and usage of shaders in graphics rendering applications. It provides
 * methods for loading shader source code from files, compiling and linking shaders,
 * and setting uniform variables with various data types. The class also handles error
 * checking and program validation.
 */
public class Shader {	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Binds a specified OpenGL program object using GL20's `glUseProgram` method. The
	 * program object is identified by the 'program' parameter, which is assumed to be
	 * previously created and linked. This enables the program for rendering operations.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Returns the value of the instance variable `program`. This method allows access
	 * to the program data from outside the class, enabling other parts of the application
	 * to retrieve and use it as needed. It is a getter method for encapsulation purposes.
	 *
	 * @returns an integer representing a program's value stored in the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Sets uniform values on a graphics pipeline based on their type. It uses a switch
	 * statement to determine the number of values provided and calls the corresponding
	 * glUniform method (e.g., glUniform1f, glUniform2f, etc.) with the provided values.
	 *
	 * @param name location of the uniform variable to be set on the graphics processing
	 * unit (GPU).
	 *
	 * @param vals 1D array of floating-point values passed to the uniform variable, which
	 * can have a length of 1, 2, 3, or 4 elements.
	 *
	 * Arrays are declared with variable number of arguments using `...`. The array's
	 * length is determined by `vals.length`. Array elements can be accessed via indexing
	 * with square brackets.
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
	 * Sets a uniform matrix for OpenGL using the provided location and name. It loads
	 * the data from a Matrix4f object into a buffer, flips its order, and then transfers
	 * it to the GPU using glUniformMatrix4. The operation is performed in place without
	 * saving the original state.
	 *
	 * @param name location of a uniform matrix that will be updated with the provided
	 * matrix data.
	 *
	 * @param mat 4x4 matrix data that is being passed to the shader for uniform setting.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Returns a location identifier associated with a uniform variable named by the input
	 * string within a shader program designated as "program". The location is retrieved
	 * using OpenGL's glGetUniformLocation method.
	 *
	 * @param name 32-bit string identifier of a uniform variable within a shader program
	 * to be located.
	 * It is used to identify the specific uniform variable for which the location needs
	 * to be retrieved.
	 * This parameter is passed to the glGetUniformLocation function to obtain its
	 * corresponding uniform location.
	 *
	 * @returns a signed integer representing a uniform location.
	 * The location is retrieved from OpenGL using the specified program and variable name.
	 * It defaults to -1 if no such location exists.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Creates a shader program from a given file, loading vertex and fragment shaders
	 * from separate files based on the filename. It validates the program after loading
	 * the shaders and returns a new Shader object with the created program ID.
	 *
	 * @param filename 3D graphics shader file to be loaded and compiled into an OpenGL
	 * shader program.
	 *
	 * @returns a newly created `Shader` object.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Creates and compiles a shader based on the provided source code and target type.
	 * It checks for compilation errors, logs any issues, and exits the program if an
	 * error occurs. Finally, it attaches the compiled shader to a given program.
	 *
	 * @param target type of OpenGL shader to be created, such as GL_VERTEX_SHADER or GL_FRAGMENT_SHADER.
	 *
	 * @param src source code of the shader to be loaded and compiled by the function.
	 *
	 * @param program shader program to which the newly created shader will be attached.
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
	 * Links and validates an OpenGL program. It checks if the program compilation is
	 * successful by verifying the link status and prints any error messages. If validation
	 * fails, it exits the application with a status code of 1.
	 *
	 * @param program 32-bit OpenGL program object ID to be validated and linked against
	 * the GPU's capabilities.
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
	 * Reads the contents of a specified file and returns it as a string.
	 * It opens the file using a FileInputStream, reads its characters one by one, and
	 * appends them to a string.
	 * The function exits the program if an I/O exception occurs while reading the file.
	 *
	 * @param file path to a file from which its contents are read and returned as a string.
	 *
	 * @returns a string containing the file's contents.
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
