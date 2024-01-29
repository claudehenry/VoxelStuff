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
	 * This function binds the graphics program specified by `program` to the current GPU
	 * context using the `GL20.glUseProgram()` method.
	 */
	public void bind() {
		GL20.glUseProgram(program);
	}
	
	/**
	 * This function returns the value of the field "program" of the current object.
	 * 
	 * @returns The function `getProgram()` returns the value of the field `program` which
	 * is undefined (not assigned any value). Therefore the output returned by this
	 * function is `undefined`.
	 */
	public int getProgram() {
		return this.program;
	}
	
	/**
	 * This function sets uniform float values for a Shader program. It takes a name of
	 * a uniform and a variable number of float arguments. Based on the length of the
	 * argument list (1 to 4), it calls the appropriate overloaded form of glUniform[1/2/3/4]f
	 * to set the corresponding number of float uniforms.
	 * 
	 * @param name The `name` input parameter is a String that identifies the uniform
	 * variable that will receive the floating-point values passed as arguments to the
	 * `uniformf()` method.
	 * 
	 * @param vals The `vals` parameter is an array of floating-point values that are
	 * passed to the `glUniform*` methods to set uniform values for the shader program.
	 * The length of the `vals` array determines which overloaded method of `glUniform*`
	 * is called.
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
	 * This function sets theuniform of a matrix4f on the GPU using the `glUniformMatrix4`
	 * method. It takes a `String` parameter `name` and a `Matrix4f` parameter `mat`, and
	 * sets the uniform at the location specified by `name`.
	 * 
	 * @param name The `name` parameter is a string that identifies the uniform variable
	 * to be set. It is used as an index into the OpenGL memory to access the correct
	 * uniform variable.
	 * 
	 * @param mat The `mat` input parameter is a `Matrix4f` object that contains the
	 * uniform matrix data to be set on the GPU.
	 */
	public void unifromMat4(String name, Matrix4f mat) {
		GL20.glUniformMatrix4(getLoaction(name), false, Util.createFlippedBuffer(mat.getLinearData()));
	}
	
	/**
	 * This function returns the OpenGL uniform location associated with the given name.
	 * 
	 * @param name The `name` input parameter is a string that specifies the name of the
	 * uniform to look up. It is passed to `glGetUniformLocation()` to retrieve the
	 * location of the specified uniform within the shader program.
	 * 
	 * @returns The output returned by the function `getLoaction(String name)` is an `int`
	 * value representing the location of a uniform variable within the program's shader
	 * code. Specifically:
	 * 
	 * 	- If the uniform variable with the specified name exists within the program and
	 * its type can be resolved to a single scalar integer value (i.e., not an array or
	 * vector), then the function returns its location as an `int` value.
	 * 	- Otherwise (if the uniform variable does not exist or its type cannot be resolved),
	 * the function returns `UNDEFINED`.
	 */
	public int getLoaction(String name) {
		return GL20.glGetUniformLocation(program, name);
	}
	
	private static final String VERT = ".vert", FRAG = ".frag";
	
	/**
	 * This function loads a shader program from a file specified by the `filename`
	 * parameter. It first creates an empty program object and then loads the vertex and
	 * fragment shaders from the file using the `loadShader()` method. After validating
	 * the program object and loading the shaders successfully it returns a new `Shader`
	 * object which contains all of these information loaded into its own buffer objects
	 * so that it is usable directly within the mainline graphics render loop without
	 * further processing required
	 * 
	 * @param filename The `filename` parameter specifies the file name of the shader
	 * script (either vertex or fragment) that should be loaded and compiled into a program.
	 * 
	 * @returns Based on the provided function signature and functionality:
	 * 
	 * The output returned by `loadShader(String filename)` is a `Shader` object that
	 * represents the loaded shader program. The `Shader` object contains the OpenGL
	 * program ID and other metadata.
	 */
	public static Shader loadShader(String filename) {
		int program = GL20.glCreateProgram();
		loadShader(GL20.GL_VERTEX_SHADER, getText(filename + VERT), program);
		loadShader(GL20.GL_FRAGMENT_SHADER, getText(filename + FRAG), program);
		validateProgram(program);
		return new Shader(program);
	}
	
	/**
	 * This function loads a shader program into the GPU by:
	 * 
	 * 1/ Creating an empty shader object.
	 * 2/ Setting the shader source code.
	 * 3/ Compiling the shader.
	 * 4/ Checking for compile errors.
	 * 5/ Attaching the compiled shader to a shader program.
	 * 
	 * @param target The `target` input parameter specifies the type of shader that should
	 * be created. In this case specifically it could be a Vertex Shader (VERTEX_SHADER)
	 * or a Fragment Shader (FRAGMENT_SHADER).
	 * 
	 * @param src The `src` parameter is a string containing the source code for the
	 * shader. It is passed to `GL20.glShaderSource()` to set the contents of the shader
	 * program.
	 * 
	 * @param program The `program` input parameter is a handle to the shader program
	 * that will be associated with the compiled shader.
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
	 * This function validates a program using OpenGL's validation and reporting functions.
	 * It first links the program and then validates it to ensure there are no errors.
	 * If an error is found it prints the error message and exits the program.
	 * 
	 * @param program The `program` input parameter is the OpenGL program object that
	 * should be validated.
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
	 * This function reads the contents of a file and returns it as a string.
	 * 
	 * @param file The `file` input parameter is a string that specifies the file from
	 * which the function should read the text.
	 * 
	 * @returns The output returned by this function is a string of characters read from
	 * the file specified as an argument. The function reads the contents of the file and
	 * returns the entire contents as a string.
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
