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

public class Shader {
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * is a utility method that sets a uniform float value for a given location in an
	 * OpenGL context, depending on the number of input values provided.
	 * 
	 * @param name name of the uniform being set.
	 * 
	 * @param vals 0 or more float values that will be passed to the `glUniform*` function
	 * to set the uniform value(s) for the specified location in the graphics processing.
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
	 * sets a 4x4 matrix to a uniform buffer using the `glUniformMatrix4` method.
	 * 
	 * @param name 0-based index of the uniform location to which the matrix will be
	 * stored in memory.
	 * 
	 * @param mat 4x4 matrix to be uniformed.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * glGetUniformLocation method to retrieve the location of a uniform buffer object
	 * (UBO) in a program.
	 * 
	 * @param name name of the uniform location to retrieve in the GL20 function call.
	 * 
	 * @returns an integer representing the location of a uniform in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a given filename, creating and validating the program
	 * upon completion.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a newly created Shader object.
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
	 * shader and attaches it to the program, before checking for compilation errors and
	 * exiting if necessary.
	 * 
	 * @param target 3D graphic's card that will host the shader.
	 * 
	 * @param src Shader source code that will be compiled into the shader program.
	 * 
	 * @param program 3D rendering program in which the loaded shader will be attached.
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
	 * validates a program by linking it and then checking its validation status, printing
	 * any error messages to the console if there are any.
	 * 
	 * @param program 3D programming object that will be validated and linked, and its
	 * status is checked after the validation process to ensure proper execution.
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
	 * reads the contents of a file and returns its encoded text as a string.
	 * 
	 * @param file file whose text is to be read and returned by the `getText()` function.
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
