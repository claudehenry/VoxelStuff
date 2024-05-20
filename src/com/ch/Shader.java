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
 * from the file provides functions for binding and unbinding a program, as well as
 * setting uniform values and matrices. The loadShader method loads vertex and fragment
 * shaders into the program and validates the program after loading.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to be used by GL rendering functions.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a variable named `program`.
	 * 
	 * @returns the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * in Java takes a string parameter `name` and an array of floats `vals`, and uses
	 * the `GL20.glUniformf()` method to set a uniform float value for the specified
	 * location based on the length of the array.
	 * 
	 * @param name location of the uniform in the program.
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
	 * sets a 4x4 uniform matrix using the `glUniformMatrix4` method, passing the matrix
	 * data as a flipped buffer to the GPU.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored.
	 * 
	 * @param mat 4x4 homogeneous transformation matrix to be uniformed.
	 * 
	 * 	- The `Matrix4f` class is used to create a 4x4 homogeneous transformation matrix,
	 * which is the input to this function.
	 * 	- The `getLinearData()` method returns the raw data of the matrix as a float
	 * array, which is then passed to `GL20.glUniformMatrix4()` for storage in the GPU's
	 * uniform buffer.
	 * 	- The `Util.createFlippedBuffer()` method creates a flipped copy of the input
	 * matrix, which is necessary because the GPU's uniform buffer is only accessible as
	 * a left-handed matrix.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform named `name` within a program's OpenGL 20
	 * (GL20) handle.
	 * 
	 * @param name name of the uniform location to retrieve in the OpenGL ES 2.0 program.
	 * 
	 * @returns an integer representing the location of a uniform in a program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * creates a shader program by loading and linking vertex and fragment shaders.
	 * 
	 * @param filename name of the shader file to be loaded.
	 * 
	 * @returns a new instance of the `Shader` class, representing a shader program created
	 * by combining a vertex shader and a fragment shader.
	 * 
	 * 	- `program`: An integer that represents the program object created by `glCreateProgram()`.
	 * 	- `Shader`: A class instance that wraps the program object and provides methods
	 * for accessing its attributes.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates and compiles a shader program using the given source code, then attaches
	 * it to a program object.
	 * 
	 * @param target type of shader (either Vertex or Fragment) that the function is loading.
	 * 
	 * @param src source code of the shader that is to be compiled and linked into the program.
	 * 
	 * @param program 3D graphics program into which the created shader is attached for
	 * use.
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
	 * validates a shader program by checking its linking and validation status, and
	 * prints any error messages if there are any.
	 * 
	 * @param program 3D graphics program to be validated and linked with the GPU.
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
	 * reads the contents of a given file as a string, handling potential IOExceptions
	 * and returning the resulting string.
	 * 
	 * @param file path to a file that contains the text to be read.
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
