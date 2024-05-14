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
 * from the file provides functionality for loading and managing shaders in OpenGL.
 * It allows for the creation of a vertex and fragment shader, and provides methods
 * for binding the shaders, uniform floats and matrices, and validating the program.
 * Additionally, it provides a method for getting the location of a uniform in the shader.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
 /**
  * uses the `glUseProgram` method from the OpenGL API to associate a program with the
  * current GL context.
  */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
 /**
  * retrieves the program value stored in the object's `program` field and returns it
  * as an integer.
  * 
  * @returns an integer value representing the program.
  */
	public int getProgram() {
		return this.program;
	}
	
 /**
  * takes a string name and an array of float values, and uses the `glUniformf` method
  * to set the corresponding uniform value in a shader program. The function handles
  * different length arrays by calling the appropriate overload of `glUniformf`.
  * 
  * @param name name of the uniform location for which the values are being set.
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
  * glUniformMatrix4 method to specify a uniform matrix in a shader program.
  * 
  * @param name name of the uniform location to which the matrix will be assigned.
  * 
  * @param mat 4x4 matrix to be stored as a uniform buffer object (UBO) in the GPU.
  * 
  * 	- `GL20.glUniformMatrix4`: This is a method from the Android GL20 library that
  * takes two arguments: the location of the uniform and a boolean value indicating
  * whether to upload the uniform as row major or column major.
  * 	- `getLocation(name)`: This is a method that returns the location of a uniform
  * in memory, based on its name. The method may perform additional operations such
  * as binding the uniform to an index before returning its location.
  * 	- `Util.createFlippedBuffer(mat.getLinearData())`: This method creates a new
  * buffer object from the linear data of the input `mat`. The resulting buffer is
  * likely to be in column major format, which is the default for GL20 matrix uniforms.
  */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
 /**
  * glGetUniformLocation(program, name) returns the location of a uniform in a program
  * with the specified name.
  * 
  * @param name 0-based index of a uniform location to retrieve within a program
  * specified by the `program` argument.
  * 
  * @returns an integer representing the location of a uniform in a graphics program.
  */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
 /**
  * loads a shader program from a file and stores it in a new shader object.
  * 
  * @param filename name of the shader file to be loaded, which is used to identify
  * and load the appropriate vertex and fragment shaders.
  * 
  * @returns a newly created shader object containing the loaded shader programs.
  * 
  * 	- The returned output is a `Shader` object, which represents a shader program
  * that can be used to render 3D graphics in a computer graphics pipeline.
  * 	- The `Shader` object has an `id` field that contains the unique identifier of
  * the shader program, which can be used to retrieve the program from the GPU.
  * 	- The `Shader` object also has a `program` field that contains the actual program
  * ID returned by GL20.glCreateProgram().
  * 	- The function returns the `Shader` object after creating and validating the
  * program, so it is guaranteed to be a valid shader program that can be used for rendering.
  */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
 /**
  * creates a new shader program and attaches it to a program handle, loading the
  * shader source code from a string parameter and compiling it using the OpenGL API.
  * 
  * @param target type of shader to be created, which determines the shader's functionality
  * and how it will be used in the program.
  * 
  * @param src source code of the shader to be loaded into the program.
  * 
  * @param program 3D graphics program that the shader will be attached to after being
  * compiled.
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
  * validates a GL program by linking and validating it, checking for any errors or
  * warnings in the process.
  * 
  * @param program 3D program to be validated and linked, which is passed through the
  * `glLinkProgram()` and `glValidateProgram()` methods for validation and linking.
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
  * reads and returns the contents of a given file as a string.
  * 
  * @param file name of a file to read its contents.
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
