package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Is used to represent 3D models in a graphics pipeline. It provides methods for
 * binding and unbinding vertex arrays, enabling and disabling vertex attributes, and
 * storing data for vertices and indices. The class also provides a method for loading
 * models from an array of vertices and indices.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a vertex array object, enables vertex attributes for position and color, and
	 * renders a mesh using glDrawElements or glDrawArrays.
	 */
	public void draw() {
		GL30.glBindVertexArray(vao);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, size);
		GL11.glDrawElements(GL11.GL_TRIANGLES, size, GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Enables vertex attribute arrays for two attributes, 0 and 1, using `glEnableVertexAttribArray`.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables both vertex attribute arrays (VAs) 0 and 1 using the `glDisableVertexAttribArray`
	 * method from the OpenGL API.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Retrieves the value of a field named `vao`.
	 * 
	 * @returns the value of the `vao` field, which is an integer.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns the value of a field named `size`.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Loads model data from an array of vertices and an array of indices into a `Model`
	 * object. It creates a Vertex Array Object (VAO), stores the indices and vertex data,
	 * unbinds the VAO, and returns a new `Model`.
	 * 
	 * @param vertices 3D model's geometric data, specifically an array of floating-point
	 * values that represent the coordinates of the model's vertices.
	 * 
	 * @param indices 3D model's vertex indices, which are used to bind the vertices of
	 * the model to its geometry when it is rendered.
	 * 
	 * @returns a `Model` object representing the loaded 3D model.
	 */
	public static Model load(float[] vertices, int[] indices) {
		int vao = createVAO();
		storeIndices(indices);
		storeData(0, vertices);
		unbindVAO();
		int v_count = indices.length;
		return new Model(vao, v_count);
	}
	
	/**
	 * Generates a new vertex array object (VAO) using the `glGenVertexArrays` method and
	 * binds it to the current context using `glBindVertexArray`.
	 * 
	 * @returns an integer value representing a unique vertex array object (VAO) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates a vertex buffer object (VBO) and binds it to the GPU. It then sets up
	 * vertex attributes for two floating-point arrays using the `GLVertexAttribPointer`
	 * method, binding the first array to an attribute at position `attrib` and the second
	 * array to an attribute at position `attrib + 1`.
	 * 
	 * @param attrib 3D vertex attribute index that contains the data to be stored in the
	 * VBO.
	 * 
	 * @param data 2D array of float values that will be stored in the Vertex Buffer
	 * Object (VBO) created by the function.
	 */
	private static void storeData(int attrib, float[] data) {
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.createFlippedBuffer(data), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 5 * 4,     0);
		GL20.glVertexAttribPointer(attrib + 1, 2, GL11.GL_FLOAT, false, 5 * 4, 3 * 4);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * Genrates a new buffer object, binds it, and stores an array of indices in it using
	 * the `GL_STATIC_DRAW` mode.
	 * 
	 * @param indices 3D indices of vertices in a buffer that are to be stored in an
	 * element array buffer.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Terminates the binding of a Vertex Array Object (VAO) by passing the handle to GL30.glBindVertexArray(0).
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
