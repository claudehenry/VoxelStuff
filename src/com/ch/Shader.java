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
 * from the file provides functionality for binding a program, uniform float and
 * matrix4f, getting location of a uniform, loading shaders, and validating program.
 * It also includes methods to load shaders from vertex and fragment shaders and
 * attach them to the program.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program object to the currently bound GPU context, enabling its use for
	 * rendering and other graphics operations.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of the `program` field of the enclosing object.
	 * 
	 * @returns an integer representation of the program value.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * in Java is a method that sets uniform values for a variable number of float arguments
	 * based on the length of the argument array.
	 * 
	 * @param name name of the uniform being set, which is used to determine the location
	 * of the uniform in the shader program.
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
	 * sets a 4x4 matrix uniform buffer in OpenGL using the `glUniformMatrix4` method.
	 * The matrix is passed as an argument and is stored in a flipped buffer for proper
	 * orientation.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored.
	 * 
	 * @param mat 4x4 homogeneous transformation matrix to be uniformed.
	 * 
	 * 	- `name`: The name of the uniform location to which the matrix will be assigned.
	 * 	- `mat`: A `Matrix4f` object representing a 4x4 homogeneous transformation matrix.
	 * Its linear data is stored in a buffer, which is passed as an argument to the
	 * `glUniformMatrix4` function.
	 * 	- `getLocation()`: A method that returns the uniform location associated with the
	 * given name.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a GPU program using the `GL20` class and
	 * the `glGetUniformLocation` method.
	 * 
	 * @param name name of the uniform location to retrieve in the OpenGL ES 2.0 program.
	 * 
	 * @returns an integer representing the location of a uniform in a GPU program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it, returning a newly created
	 * Shader object representing the program.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a new instance of the `Shader` class, representing a shader program created
	 * by combining a vertex shader and a fragment shader.
	 * 
	 * 	- The output is of type `Shader`, which represents a shader program that can be
	 * used to render 3D graphics in a Graphics Library (GL).
	 * 	- The `Shader` object has a single field, `program`, which contains the program
	 * ID created by the `glCreateProgram()` function. This program ID can be used to
	 * access and manipulate the shader program using the GL API functions.
	 * 	- The `loadShader` function creates two vertex shaders and one fragment shader
	 * and combines them into a single shader program using the `glAttachShader()` and
	 * `glLinkProgram()` functions. Therefore, the output is a shader program that can
	 * be used for both vertex and fragment processing.
	 * 	- The `validateProgram()` function is called after creating the shader program
	 * to check its validation status. If the program is invalid, it will throw an exception.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader program and attaches it to an existing program. It compiles
	 * the shader source code and reports any compilation errors if they occur.
	 * 
	 * @param target type of shader to be created, which determines the shader's functionality.
	 * 
	 * @param src shader source code that is to be compiled and linked to create a shader
	 * program.
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
	 * validates a program by checking its linking and validation status, printing any
	 * error messages to the console if necessary, and exiting the application with an
	 * error code if there are issues.
	 * 
	 * @param program 3D graphics program to be validated and linked with the OpenGL API.
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
	 * reads the contents of a specified file and returns the text as a string.
	 * 
	 * @param file file to read and return its contents as a string.
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
