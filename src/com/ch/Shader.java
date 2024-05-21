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
 * from the given file provides a way to load and manage shaders for use in OpenGL
 * rendering. It allows for the loading of vertex and fragment shaders, and provides
 * methods for binding and uniformfying values in the shaders. The class also includes
 * validation and linking of the program upon creation.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to the current GL context, allowing its use for rendering.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`.
	 * 
	 * @returns an integer value representing the program variable.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * is a Java method that sets a uniform float value(s) for a specified location in
	 * an OpenGL context, based on the length of the input array of float values.
	 * 
	 * @param name location of the uniform in the shader program, and is used to determine
	 * the appropriate GL function to call for the given number of `float` values.
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
	 * sets a 4x4 matrix as a uniform buffer in the GL context using the `glUniformMatrix4`
	 * method.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored.
	 * 
	 * @param mat 4x4 matrix to be uniformed.
	 * 
	 * 	- `name`: A string indicating the location where the uniform matrix will be stored.
	 * 	- `mat`: An instance of the `Matrix4f` class, representing a 4x4 homogeneous
	 * transformation matrix.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform named `name` within a program specified by `program`.
	 * 
	 * @param name name of the uniform location to retrieve, and it is passed as an
	 * argument to the `glGetUniformLocation()` function to specify the location of the
	 * uniform to retrieve.
	 * 
	 * @returns an integer representing the location of a uniform in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file, creating a vertex and fragment shader and
	 * validating the program.
	 * 
	 * @param filename path to a shader file that contains the vertex and fragment shaders
	 * to be loaded into the program.
	 * 
	 * @returns a newly created shader object.
	 * 
	 * 	- The `Shader` object returned is an instance of the `Shader` class, which
	 * represents a shader program created by combining a vertex shader and a fragment shader.
	 * 	- The `program` field of the `Shader` object refers to the handle of the created
	 * program. This handle can be used for further manipulation or destruction of the
	 * program using the `GL20` class methods.
	 * 	- The function `loadShader` creates two shaders (vertex and fragment) by calling
	 * the `loadShader` method on each shader file separately, passing in the appropriate
	 * identifier (either `GL20.GL_VERTEX_SHADER` or `GL20.GL_FRAGMENT_SHADER`) as the
	 * first argument.
	 * 	- The function `validateProgram` is called after creating the program to validate
	 * its structure and ensure it is in a valid state for use.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and attaches it to a program handle, compiling and linking
	 * the shader code if successful.
	 * 
	 * @param target type of shader being created, which can be either a vertex shader
	 * or a fragment shader.
	 * 
	 * @param src source code of the shader to be compiled.
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
	 * validates a program by linking and validating it using the `glLinkProgram` and
	 * `glValidateProgram` functions, respectively. It outputs any error messages if
	 * validation fails.
	 * 
	 * @param program 3D programming handle that is being validated and linked.
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
	 * reads the contents of a specified file as a string, handling potential IOExceptions.
	 * 
	 * @param file path to a file containing the text to be read.
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
