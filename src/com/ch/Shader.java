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
 * provides functionality for creating and managing shaders in OpenGL. It allows for
 * loading and validating shaders, as well as binding and unbinding them from the
 * program. Additionally, it provides methods for uniform and matrix uniforms.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to a specific OpenGL context, allowing for efficient rendering and
	 * manipulation of graphics data.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`.
	 * 
	 * @returns an integer value representing the program.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * takes a string parameter `name` and an array of float values `vals`. It dynamically
	 * generates GL calls to set uniform values based on the length of the `vals` array.
	 * 
	 * @param name name of the uniform location being set, which is used to identify the
	 * location in the OpenGL state where the values in the `vals` array are being assigned.
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
	 * uniformly sets a 4x4 floating-point matrix to a specified location in memory using
	 * the `glUniformMatrix4` function from OpenGL.
	 * 
	 * @param name 3D matrix's uniform location in the shader program, as specified by
	 * the GL20 class method `glUniformMatrix4`.
	 * 
	 * @param mat 4x4 homogeneous transformation matrix to be uniformed across the current
	 * GL context.
	 * 
	 * 	- `name`: A string parameter indicating the uniform name to be used for the matrix.
	 * 	- `mat`: A `Matrix4f` object representing a 4x4 homogeneous transformation matrix.
	 * The matrix elements can be accessed as `mat.getLinearData()` and are stored in a
	 * flipped buffer when passed to `glUniformMatrix4`.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a program specified by an integer identifier
	 * using the `GL20.glGetUniformLocation` method.
	 * 
	 * @param name 1D array index of the uniform location to retrieve in the OpenGL ES
	 * 2.0 program.
	 * 
	 * @returns an integer representing the location of a uniform variable in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it, returning a new shader object.
	 * 
	 * @param filename path to a shader file that contains the vertex and fragment shaders
	 * for loading into the program.
	 * 
	 * @returns a newly created shader object representing a compiled shader program.
	 * 
	 * 	- The function returns a `Shader` object that represents a shader program created
	 * by combining a vertex shader and a fragment shader.
	 * 	- The `Shader` object is an instance of the `com.badlogic.gdx.graphics.glutils.Shader`
	 * class, which provides a set of methods for manipulating and validating the shader
	 * program.
	 * 	- The `program` field of the `Shader` object contains the handle to the created
	 * shader program.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and attaches it to a program handle. It compiles the
	 * shader source code and prints an error message if compilation fails.
	 * 
	 * @param target type of shader being created, with possible values of 0 for vertex
	 * shaders and 1 for fragment shaders.
	 * 
	 * @param src 1:1 representation of the shader source code that is to be compiled and
	 * executed by the `glShaderSource()` function.
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
	 * validates a program object and checks its linking and validation status.
	 * 
	 * @param program 3D rendering program to be validated and linked with the OpenGL API.
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
	 * reads a file and returns its contents as a string.
	 * 
	 * @param file file from which the text is to be read.
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
