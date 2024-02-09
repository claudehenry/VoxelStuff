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
	 * This function sets the active program on the GPU to the one defined by `program`.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * The provided function "getProgram" returns the value of the field "program".
	 * 
	 * @returns The output returned by this function is the value of the field "program"
	 * of the object that this method belongs to. In other words: an integer value which
	 * is the program that is referred by this object. This can be thought as a return
	 * statement which retrieves the data associated with the field of an object.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * This method sets OpenGL uniform floats based on the number of input values. It
	 * calls glUniform*f() depending on the length of the vals array (1-4 values).
	 * 
	 * @param name The `name` parameter is a string that specifies the name of the uniform
	 * variable being set. It is used to identify the specific uniform variable that the
	 * caller wants to set.
	 * 
	 * @param vals The vals input is an array of float values passed into the function
	 * that will be used to set uniforms for the current GL context.  The length of the
	 * array determines which overload of glUniformXf() will be called with different
	 * sets of floats at different locations of the parameter list.
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
	 * This function sets the value of a uniform matrix using an already flipped buffer
	 * for the matrix data and tells OpenGL which location (specified by name) to use.
	 * 
	 * @param name The `name` parameter is an alias identifier for the uniform that you
	 * wish to bind and thus provide a readable name as to which matrix uniform will be
	 * called by binding the buffer data with GL20. `getLocation` retrieves the location
	 * value for the uniform identified by the name; the last param of false implies don't
	 * generate a OpenGL exception when the uniform is not found on the shader.
	 * 
	 * @param mat The `mat` parameter is a `Matrix4f` object containing the data to be
	 * set as the uniform value for a specified location.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * The `getLocation` function retrieves the OpenGL uniform location associated with
	 * the name passed as a parameter.
	 * 
	 * @param name The input parameter "name" is a string that specifies the name of a
	 * uniform variable that should be retrieved using glGetUniformLocation().
	 * 
	 * @returns The output of this function is an integer value that represents the
	 * location of the uniform specified by the given name within the currently bound
	 * shader program (as per the GLSL standard). Specifically GL20.glGetUniformLocation()
	 * returns the integer location of the specified uniform within the program object
	 * that is currently linked and active. This location can then be used to set the
	 * value of the uniform using gluniform xxx () functions.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * This function loads a shader program from a file specified by the string 'filename',
	 * and returns a Shader object that represents the program.
	 * 
	 * @param filename The filename parameter is used to specify the name of a file
	 * containing GLSL shader code for loading into the OpenGL shading pipeline. It's
	 * used as part of the construction of vertex and fragment shader source codes.
	 * 
	 * @returns Based on the code snippet provided the output of the loadShader function
	 * is a Shader object.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * This function loads a shader code and compiles it into a GL shader object. If the
	 * compilation fails then it displays an error message and exits the program. Finally
	 * it attaches the shader object to a OpenGL program.
	 * 
	 * @param target Here's the function again with the target parameter highlighted:
	 * 
	 * private static void loadShader(int target[,String src,[int program]]) {
	 * 
	 * The input `target` sets which type of shader the function will create (i.e., a
	 * vertex or fragment shader). Therefore 'target' specifies the shader type and is
	 * used as the first parameter to `GL20.glCreateShader()`.
	 * 
	 * @param src The `src` parameter provides a string containing the shader code for
	 * GLASSAD(API) shaders.
	 * 
	 * @param program The program parameter specifies which OpenGL program the shader
	 * should be attached to. It is passed by reference and serves as an index into the
	 * OpenGL program's attached shaders array.
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
	 * This validateProgram function validates the given program using the GL_LINK_STATUS
	 * and GL_VALIDATE_STATUS OpenGL commands. If either of these commands return zero
	 * (i.e., an error occurs), it prints an error message and exits the program.
	 * 
	 * @param program In this particular situation: "int program" is the ID of a shader
	 * program created with GL20.createProgram() earlier before invoking the validateProgram();
	 * function. The input will serve as a key for access the stored data related to that
	 * specific program.
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
	 * This function reads the contents of a file and returns its text as a string. It
	 * takes one parameter - the file name- and uses try-catch blocks to handle input
	 * output exceptions. It reads characters from the file one by one until it encounters
	 * an end of file and closes the input stream before returning the text.
	 * 
	 * @param file The `file` input parameter is a String that contains the name of a
	 * file whose contents should be read into a string using an InputStream object.
	 * 
	 * @returns This function reads a file and returns its contents as a string. Specifically:
	 * 
	 * 1/ It takes one parameter of type string called file.
	 * 2/ It declares a local variable called text that will hold the file's contents.
	 * 3/ It opens the file specified by the user using FileInputStream.
	 * 4/ Inside a while loop it reads every available character from the file (which
	 * means if there is no such character- then the loop terminates) and stores each one
	 * into the string text as its last Character.
	 * 5/ When the file has no more characters left to read. It closes the input stream
	 * 6/ Finally it returns the value of the String variable text.
	 * 
	 * In a word the output is a string representing all of the contents found within the
	 * specified file
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
