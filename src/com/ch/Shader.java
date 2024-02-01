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
	 * This function binds a previously created OpenGL program object to the currently
	 * active graphics pipeline using `GL20.glUseProgram()` method.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * This function returns the value of the `program` field of the object that it is
	 * called on.
	 * 
	 * @returns The output returned by this function is "undefined".
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * This function sets a single or multiple floating-point uniform variables of a GL20
	 * GPU shader program.
	 * 
	 * @param name The `name` input parameter is a string that identifies the uniform
	 * being set. It is used to retrieve the location of the uniform within the Shader Program.
	 * 
	 * @param vals The `vals` parameter is an array of float values that are used to set
	 * the uniform values for the specified location (represented by the `name` parameter).
	 * The number of elements inside the `vals` array determines which overloaded version
	 * of the `glUniform` method will be called.
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
	 * This function sets the uniform matrix 4 on a location defined by `name`, using the
	 * linear data from `mat` and flipping it if necessary.
	 * 
	 * @param name The `name` input parameter is a string that is used as an index into
	 * a buffer object to store the matrix data. It is passed as the first argument to
	 * the `glUniformMatrix4` method.
	 * 
	 * @param mat The `mat` input parameter is a `Matrix4f` object that contains the
	 * uniform transformation matrix to be applied to the specified object.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * This function returns the OpenGL uniform location for a specified name within a program.
	 * 
	 * @param name The `name` input parameter passed to `glGetUniformLocation()` specifies
	 * the name of the uniform that we want to locate. It is a string representing the
	 * name of the uniform we are looking for.
	 * 
	 * @returns The function `getLoaction` returns the integer location of a uniform
	 * within a shader program specified by the name of the uniform. The return type is
	 * an `int`, and the value returned is the memory location of theuniform within the
	 * program's uniform block.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
 /**
  * This function loads a shader program from a file specified by the `filename`
  * parameter. It creates a new OpenGL program object and loads the vertices and
  * fragments shaders from the file into the program. Then it validates the program
  * and returns a new Shader object representing the loaded program.
  * 
  * @param filename The `filename` parameter is a string that specifies the name of
  * the shader file to be loaded.
  * 
  * @returns The function `loadShader` returns an object of type `Shader`, which
  * represents a graphics shader program created from the specified vertex and fragment
  * shader files.
  */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
 /**
  * This function loads a shader into a program for the GPU to execute. It takes three
  * parameters: the target for the shader (e.g., GL_VERTEX_SHADER), the source code
  * of the shader as a string., and the ID of the program to which the shader will be
  * attached. The function first creates a new shader using glCreateShader(), then
  * sets the source code for that shader using glShaderSource(). It then compiles the
  * shader using glCompileShader() and checks if compilation was successful using
  * glGetShaderInfoLog() and exiting the program with an error message if necessary.
  * Finally it attaches the compiled shader to a specified program using glAttachShader().
  * 
  * @param target The `target` parameter specifies the type of shader that should be
  * created.
  * 
  * @param src The `src` parameter is the source code of the shader program.
  * 
  * @param program The `program` input parameter specifies the target program object
  * to which the shader is attached.
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
  * This function validates a given OpenGL program by linking and validating it. It
  * first links the program using `glLinkProgram()`, then retrieves any error messages
  * using `glGetProgramInfoLog()` and prints them to the console if there were any
  * errors. Finally，it validates the program again using `glValidateProgram()`, retrieves
  * any additional error messages，and prints them to the console if there were any errors.
  * 
  * @param program The `program` input parameter is the GL program object that is being
  * validated. It is passed to the `glLinkProgram()` and `glValidateProgram()` methods
  * to validate the program's link and validation statuses.
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
  * This function reads the contents of a file specified by the parameter `file`, and
  * returns the content as a string.
  * 
  * @param file The `file` input parameter is the path to a file that the function
  * reads to retrieve its contents as a string of text.
  * 
  * @returns This function reads the contents of a file specified as an argument and
  * returns its content as a string. The function takes a file path as a parameter and
  * reads it character by character using the FileInputStream class. Any input errors
  * or exceptions are caught and ignored by the try-catch block. The function simply
  * returns the collected string representation of all characters read from the file.
  * Therefore the output of this function is a String object containing the contents
  * of the specified file.
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

