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
	 * This function binds the GPU to use the specified program (indicated by "program").
	 * In other words，it prepares the program for execution on the GPU.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * This function returns the value of the field "program" within the object.
	 * 
	 * @returns The output returned by this function is the value of the field "program"
	 * which is an integer.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * This method sets uniform floating point values for a given name (parameter "name")
	 * using an vararg parameter ("vals") with length between 1 and 4. The correct overload
	 * of glUniform* is called depending on the length of vals.
	 * 
	 * @param name The "name" parameter is a string that represents the name of the uniform
	 * variable that the function will set. It is used to differentiate between different
	 * uniform variables and ensure that the correct one is updated with the given values.
	 * 
	 * @param vals The vals input parameter is an array of float values that are used to
	 * set the values of a uniform variable declared with a specific size(1-4) and specified
	 * by name.
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
	 * This function sets the uniform matrix for a specific name ( passed as a string)
	 * using the linear data from a Matrix4f object mat. It uses Util.createFlippedBuffer
	 * to flip the matrix before passing it to glUniformMatrix4.
	 * 
	 * @param name The "name" parameter is a string used to retrieve a location of auniform
	 * that corresponds to it's name and it is not mandatory as the function takes into
	 * account if null and locatonId is passed to be assigned one.
	 * 
	 * @param mat The `mat` input parameter represents the 4x4 matrix data to be uniformed
	 * and it is passed as a `Matrix4f` object.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * This function returns the location of a specified uniform within a shader program.
	 * 
	 * @param name The `name` parameter is the name of the uniform to look up. The
	 * `GL_UNIFORM_LOCATION` function returns the location of that uniform within the
	 * provided program.
	 * 
	 * @returns The output of this function is an `int` that represents the location
	 * (index) of a specified uniform variable within the currently bound `GLProgram`.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * This function creates and returns a shader object that is loaded from a specified
	 * filename using two separate GLSL shaders for the vertex and fragment stages.
	 * 
	 * @param filename The `filename` input parameter specifies the file name of the
	 * shader that is to be loaded.
	 * 
	 * @returns This function loads a shader and returns an instance of a `Shader` class
	 * representing that shader.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * This function loads a shader program and compiles it. If the compile fails with a
	 * fatal error the program exits.
	 * 
	 * @param target The `target` parameter specifies the type of shader that the function
	 * creates (either vertex or fragment shader). It is an integer value representing
	 * the desired shader target.
	 * 
	 * @param src The `src` input parameter specifies the source code for the shader.
	 * 
	 * @param program The `program` parameter specifies the target program object that
	 * will contain the compiled shader object once the method has finished executing.
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
	 * This function validates a GL program object (i.e. checks its link and validate
	 * statuses) using two consecutive calls to glValidateProgram() and glGetProgramInfoLog()
	 * 
	 * @param program The `program` input parameter is the GL Program object that we wish
	 * to validate by linking it and then validating it before rendering any frames.
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
	 * This function reads the contents of a file as a string. It takes a file path as
	 * input and returns the text of that file as a string
	 * 
	 * @param file Here is a concise explanation of what the file input parameter does:
	 * 
	 * The input file to be read and converted to string text.
	 * 
	 * @returns The function takes a file path as an argument and returns its contents
	 * as a string. Specifically the contents of the file specified by file are read
	 * line-by-line and concatenated into a single string.
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
