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
	 * This function binds the OpenGL program object identified by `program` to the current
	 * graphics context.
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
	 * This function sets a single uniform (a value that is applied to all vertices of a
	 * render object) on the currently bound OpenGL GPU context using the glUniform*()
	 * method. It takes a name of the uniform and an array of float values as input. The
	 * length of the input array determines which overloaded version of glUniform*() to
	 * call based on the number of float values passed.
	 * 
	 * @param name The `name` input parameter is a string that represents the name of the
	 * uniform to be set. It is used to identify the specific uniform among multiple
	 * uniforms that are available on the graphics pipeline.
	 * 
	 * @param vals The `vals` input parameter is an array of floats that specifies the
	 * values to be uniformized.
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
	 * This function sets the uniform matrix4 of a specific name (passed as string) to
	 * the value of a given Matrix4f object (also passed as argument).
	 * 
	 * @param name The `name` parameter is a string that specifies the uniform name for
	 * which the `Matrix4f` data is being bound. It is used to identify the specific
	 * uniform buffer object (UBO) that the matrix data should be bound to.
	 * 
	 * @param mat The `mat` input parameter is a `Matrix4f` object that contains the
	 * matrix data to be set as a uniform for a given OpenGL location.
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

