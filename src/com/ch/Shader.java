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
	
	/**
	 * glUseProgram(program) to associate a program with a set of GL objects.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves and returns the value of the `program` field of the current object.
	 * 
	 * @returns an integer representation of the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * sets a uniform float value to a specified location in an OpenGL context based on
	 * the length of the provided array of float values.
	 * 
	 * @param name location of the uniform in the program.
	 * 
	 * @param vals 0 or more values that will be passed to the corresponding GL function,
	 * depending on the length of the input array.
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
	 * glUniformMatrix4 on a specified location with false as the second argument and the
	 * matrix's linear data in a flipped buffer.
	 * 
	 * @param name name of the uniform location to which the matrix will be assigned.
	 * 
	 * @param mat 4x4 homogeneous transformation matrix that is to be uniformized and
	 * passed as a buffer to the `glUniformMatrix4` method.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a program specified by the `name` parameter
	 * using the `GL20.glGetUniformLocation` method.
	 * 
	 * @param name 0-based index of the uniform location to retrieve in the OpenGL program.
	 * 
	 * @returns an integer representing the location of a uniform in a GPU program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader from a file and creates a program in the OpenGL ES 2.0 framework.
	 * It loads two shaders, one for the vertex and one for the fragment, and validates
	 * the program before returning it as a new Shader object.
	 * 
	 * @param filename path to a shader file that is being loaded into the program.
	 * 
	 * @returns a new instance of the `Shader` class, representing a shader program created
	 * from the specified vertex and fragment shaders.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader program and attaches it to the program. It compiles the shader
	 * source code into a shader object and logs any compilation errors if they occur.
	 * 
	 * @param target type of shader (vertex or fragment) that the function is creating
	 * and compiling.
	 * 
	 * @param src Shaders source code to be compiled.
	 * 
	 * @param program 3D graphics program in which the generated shader will be attached
	 * for use.
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
	 * validates a program object by checking its link and validation statuses, printing
	 * any error messages to the console if necessary, and exiting the program if validation
	 * fails.
	 * 
	 * @param program 3D graphics program to be validated and linked with the OpenGL driver.
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
	 * reads the contents of a specified file and returns the resulting string.
	 * 
	 * @param file file whose contents are to be read and returned as a string by the
	 * `getText()` function.
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
