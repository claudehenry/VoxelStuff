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
 * provides a mechanism for binding a program to uniform and matrix operations in
 * OpenGL. It offers methods for setting uniform values and matrix representations,
 * as well as loading shaders from files.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to a context, allowing it to be executed and its data to be accessed.
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
	 * takes a string name and an array of floats as input, and uses the `glUniformf`
	 * method to set the specified uniform value(s) in the shader program. The function
	 * performs type checking and selects the appropriate `glUniformf` method based on
	 * the length of the input array.
	 * 
	 * @param name location of the uniform value in the shader program, which is used to
	 * determine the appropriate glUniform* function to call for the given number of
	 * uniform values.
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
	 * glUniformMatrix4 method to set a 4x4 matrix as a uniform buffer in OpenGL.
	 * 
	 * @param name 2D uniform matrix location for which the `mat` parameter provides the
	 * actual matrix data.
	 * 
	 * @param mat 4x4 uniform matrix to be stored as a GL buffer.
	 * 
	 * 	- `mat`: A `Matrix4f` object representing a 4x4 matrix with floating-point elements.
	 * 	- `name`: The name of the uniform location where the matrix will be stored.
	 * 	- `getLocation()`: A method that returns a `IntBuffer` containing the index of
	 * the uniform location.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a OpenGL program based on its name.
	 * 
	 * @param name 0-based index of the uniform location to retrieve in the program's
	 * uniform buffer.
	 * 
	 * @returns an integer representing the uniform location in the program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it.
	 * 
	 * @param filename file path of the shader to be loaded.
	 * 
	 * @returns a `Shader` object representing a shader program created from the given
	 * vertex and fragment shaders.
	 * 
	 * 	- The returned output is an instance of `Shader`, which represents a shader program
	 * loaded from a file.
	 * 	- The program ID returned by the function is an integer value representing the
	 * program object created by GL20.glCreateProgram().
	 * 	- The program object contains the vertex and fragment shaders loaded from the
	 * file, as well as other attributes such as the shader handles for the vertex and
	 * fragment shaders.
	 * 	- The `validateProgram` method is called after loading the vertex and fragment
	 * shaders to ensure that the program is valid and can be used for rendering.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a shader program and loads a shader source into it. It compiles the shader
	 * and attaches it to the program.
	 * 
	 * @param target type of shader to be created, with values of 0 for a vertex shader
	 * and non-zero values for a fragment shader or a combination shader.
	 * 
	 * @param src 1:1 equivalent of the source code for the shader to be compiled, which
	 * is passed as a string.
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
	 * validates a program object in OpenGL by linking and validating it. If any errors
	 * occur during validation, an error message is printed to the console and the program
	 * exits with a status code of 1.
	 * 
	 * @param program 3D program to be linked and validated by the `GL20.glLinkProgram()`
	 * and `GL20.glValidateProgram()` methods, respectively.
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
	 * reads the contents of a file and returns its text content as a string.
	 * 
	 * @param file file to read and is used to store the contents of the file into a
	 * string variable called `text`.
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
