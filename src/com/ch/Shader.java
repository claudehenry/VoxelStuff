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
 * in Java provides functionality for binding a program and uniform values, as well
 * as loading shaders from files. The class also includes methods for validating the
 * program and getting location of uniforms and matrices.
 */
public class Shader {
	
	private int program;
	
	public Shader(int program) {
		this.program = program;
	}
	
	/**
	 * glues a program to a context, enabling its use for rendering.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * retrieves the value of a field named `program`.
	 * 
	 * @returns the value of the `program` field.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * takes a string name and an array of floats as input, and uses the `glUniformf`
	 * method to set the corresponding uniform value in a GL context based on the length
	 * of the input array.
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
	 * updates a uniform matrix with the specified name using the `glUniformMatrix4`
	 * method from the `GL20` class, passing in the matrix's linear data in a flipped buffer.
	 * 
	 * @param name 0-based index of the uniform location where the matrix will be stored.
	 * 
	 * @param mat 4x4 matrix of homogeneous coordinates that is to be set as a uniform
	 * buffer object for rendering purposes using the `GL20.glUniformMatrix4()` method.
	 * 
	 * 	- `name`: The name of the uniform location where the matrix will be stored.
	 * 	- `mat`: A `Matrix4f` object representing a 4x4 matrix with floating-point elements.
	 * Its linear data is stored in a buffer that is created using `Util.createFlippedBuffer()`.
	 * 	- `getLocation(name)`: A method of the current GL20 context that returns a handle
	 * to a specific uniform location, which is specified by the `name` parameter. The
	 * location is retrieved using the `GL20.glGetUniformLocation()` function and stored
	 * in a member variable for later use.
	 * 	- `false`: A boolean value indicating whether the matrix should be transferred
	 * from the Vertex Shader or the Fragment Shader. In this case, it is transferred
	 * from the Vertex Shader.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * retrieves the location of a uniform in a GPU program using the `GL20` class and
	 * method `glGetUniformLocation`.
	 * 
	 * @param name 0-based index of the uniform location to retrieve in the program's
	 * uniform buffer.
	 * 
	 * @returns an integer representing the location of a uniform in a GPU program.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * loads a shader program from a file and validates it. It creates a new shader object
	 * and returns it upon successful loading.
	 * 
	 * @param filename path to a file containing the vertex and fragment shaders to be
	 * loaded into the program.
	 * 
	 * @returns a `Shader` object representing a compiled shader program.
	 * 
	 * 	- `Shader`: This is the type of the returned object, which is a `Shader` instance
	 * representing a shader program created by combining a vertex shader and a fragment
	 * shader.
	 * 	- `program`: This is the handle of the created shader program, which can be used
	 * for further operations such as attaching the shader to a GPU context or linking
	 * it with other shaders.
	 * 
	 * The function takes two string arguments: `filename` and `GL20.GL_VERTEX_SHADER`
	 * (or `GL20.GL_FRAGMENT_SHADER`). The first argument is the filename of the shader
	 * file to be loaded, while the second argument specifies which type of shader to
	 * load from the file (either vertex or fragment shader).
	 * 
	 * The function creates a new shader program using the `glCreateProgram` function of
	 * the OpenGL API, and then loads the specified shader from the file using `glCreateShader`
	 * functions. Once the shaders are loaded, the function validates the program by
	 * calling the `validateProgram` function, and returns a new `Shader` instance
	 * representing the combined shader program.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * creates a new shader object and loads shader source code from a string parameter.
	 * It compiles the shader and attaches it to a program object if compilation succeeds.
	 * If compilation fails, an error message is printed and the program exits.
	 * 
	 * @param target type of shader being created, which determines the shader's functionality
	 * and usage in the program.
	 * 
	 * @param src Shader source code to be compiled and linked with the specified target
	 * and program.
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
	 * validates a program object by calling `glLinkProgram`, `glValidateProgram`, and
	 * checking their return values. If any of these calls fail, it prints an error message
	 * and exits the program with a non-zero status code.
	 * 
	 * @param program 3D graphics program to be validated, which is linked and then
	 * validated using the `glLinkProgram()` and `glValidateProgram()` functions respectively.
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
	 * reads the contents of a specified file and returns a String containing its characters.
	 * 
	 * @param file path of a file that contains text, which is read and returned as a
	 * string by the `getText()` function.
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
