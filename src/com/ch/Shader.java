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
 * Is designed to manage and load shaders for OpenGL applications. It allows users
 * to bind a shader program, set uniform values, and validate the compilation and
 * linking of shader programs. The class provides methods for loading vertex and
 * fragment shaders from files and compiling them into an executable program.
 */
public class Shader {	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * Binds a graphics program with an identifier `program` using the OpenGL function
	 * `glUseProgram`. This associates the specified program with the current rendering
	 * context, making it active for subsequent drawing operations. The program's shaders
	 * and uniforms are loaded for use by the GPU.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Retrieves the value of the `program` variable and returns it as an integer. This
	 * allows access to the program value from outside the class, providing a way to read
	 * its current state. The function simply returns the stored value without performing
	 * any computations or modifications.
	 *
	 * @returns an integer value representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Sets uniform floating-point values for a given location in OpenGL. It takes a
	 * variable number of float arguments, and based on their count, it calls corresponding
	 * OpenGL function to set the uniform value: 1, 2, 3 or 4 float values are passed
	 * depending on the case.
	 *
	 * @param name name of the uniform variable that is being set with the provided values,
	 * used to locate and update the corresponding uniform state within the OpenGL context.
	 *
	 * @param vals 1 to 4 floating-point values passed as a variable number of arguments,
	 * used to set uniform variables according to their length.
	 *
	 * Varargs array is passed to the method with variable number of elements.
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
	 * Sets a uniform matrix for a specified location named `name`. It takes a `Matrix4f`
	 * object `mat` and its linear data is converted to a buffer using `Util.createFlippedBuffer`.
	 * The resulting buffer is then passed to `GL20.glUniformMatrix4` along with the
	 * location name and a boolean indicating whether the transpose of the matrix should
	 * be used.
	 *
	 * @param name location of a uniform variable in the shader program that will be
	 * updated with the specified matrix data.
	 *
	 * @param mat 4x4 matrix to be uniformly set as the value of a shader program variable
	 * with the given name.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Retrieves a uniform location for a specified variable within a program. It takes
	 * a string representing the variable's name and returns an integer value corresponding
	 * to its location. The result is obtained using the OpenGL API's `glGetUniformLocation`
	 * function.
	 *
	 * @param name uniform variable name to be retrieved from the OpenGL program using
	 * the `glGetUniformLocation` method.
	 *
	 * @returns an integer representing a uniform location in the shader program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader from a file and creates a new shader object. It uses GL20 to create
	 * a vertex shader, fragment shader, and program, then validates the program before
	 * returning a new Shader object.
	 *
	 * @param filename name of a file to be loaded as a vertex or fragment shader, which
	 * is then used to generate the corresponding GLSL code for the shader program.
	 *
	 * @returns an instance of the `Shader` class.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Compiles and attaches a shader to a program for rendering graphics. It creates a
	 * shader object from a source string, compiles it, and checks if compilation is
	 * successful. If not, it prints an error message and exits the program.
	 *
	 * @param target type of shader to be created, such as GL_VERTEX_SHADER or
	 * GL_FRAGMENT_SHADER, and is used by the OpenGL API to determine the correct shader
	 * object creation.
	 *
	 * @param src source code of the shader, which is used to set the source for the
	 * shader object using `GL20.glShaderSource`.
	 *
	 * @param program OpenGL program to which the newly created shader is attached.
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
	 * Validates a given OpenGL program. It links and then validates the program using
	 * `glLinkProgram` and `glValidateProgram` respectively. If either linking or validation
	 * fails, it prints an error log and exits with status code 1.
	 *
	 * @param program 32-bit identifier of the program object being linked and validated.
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
	 * Reads a file and returns its contents as a string. It uses an input stream to read
	 * the file character by character, appending each character to a string until the
	 * end of the file is reached.
	 *
	 * @param file path to a file that is read and its content converted into a string,
	 * which is then returned by the method.
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
