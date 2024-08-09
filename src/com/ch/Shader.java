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
 * Is designed to load and manage shaders for an OpenGL context. It provides functions
 * for binding a program, getting the location of uniform and uniform matrix functions,
 * and loading vertex and fragment shaders. The class also validates the program after
 * loading the shaders.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * GlUseProgram(program) to associate a program object with the current GL context.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * Returns the value of the `program` field.
	 * 
	 * @returns an integer representing the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * Is a method that sets a uniform float value(s) for a given shader program location,
	 * based on the length of the input array of float values.
	 * 
	 * @param name name of the uniform location for which the values are being set.
	 * 
	 * @param vals 0 or more floating-point values that will be passed to the corresponding
	 * glUniform() method to set the uniform value of the same name as the function.
	 * 
	 * * Length: 1 to 4
	 * * Content: A sequence of floating-point values
	 * 
	 * The length of `vals` determines the number of arguments passed to the corresponding
	 * GL uniform function (GL20.glUniform1f, GL20.glUniform2f, GL20.glUniform3f, or GL20.glUniform4f).
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
	 * Unloads a 4x4 uniform matrix from an OpenGL context to a flipped buffer.
	 * 
	 * @param name 3D uniform matrix that is to be loaded and applied as a transformation
	 * to the graphics object.
	 * 
	 * @param mat 4x4 matrix to be uniformed.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * Is a part of the Java API for OpenGL, and it returns the uniform location of a
	 * named uniform in an OpenGL program.
	 * 
	 * @param name name of the uniform to locate.
	 * 
	 * @returns an integer representing the location of a uniform in a program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * Loads a shader program from a file and creates a new shader object that represents
	 * it.
	 * 
	 * @param filename name of a shader file to be loaded.
	 * 
	 * @returns a new instance of `Shader` object that represents a shader program created
	 * by combining a vertex shader and a fragment shader.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * Loads a shader program into memory and attaches it to a program handle.
	 * 
	 * @param target type of shader to be created, which can be either a fragment shader
	 * or a vertex shader.
	 * 
	 * @param src 3D graphics shader source code that will be compiled and linked to
	 * create the final shader program.
	 * 
	 * @param program 3D graphics program that will be used to link the loaded shader
	 * with, allowing for the creation of a fully functional graphics program.
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
	 * Validates a program by checking its link and validation statuses, printing any
	 * error messages if they exist, and exiting the program with an error code if there
	 * are issues.
	 * 
	 * @param program 3D program to be validated and linked, and is passed through the
	 * `glLinkProgram()` and `glValidateProgram()` functions for validation and linking.
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
	 * Reads the contents of a given text file and returns its raw string value.
	 * 
	 * @param file file to read and return its text content.
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
