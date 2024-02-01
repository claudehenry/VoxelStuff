package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * This function sets up and draws a batch of triangle data using GL_TRIANGLES mode
	 * with Vertex Array Object (VAO) and OpenGL 4.x functions.
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
	 * This function enables two vertex attribute arrays (indices 0 and 1) using the
	 * `glEnableVertexAttribArray` method of the OpenGL render context.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * This function disables two vertex attribute arrays (VAAs) specified by the indices
	 * 0 and 1 using the `glDisableVertexAttribArray()` method of the GL20 context.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * This function returns the value of the field "vao" which is an integer.
	 * 
	 * @returns The output of the function `getVAO()` is `null`.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * This function returns the value of the "size" field of the object.
	 * 
	 * @returns The output returned by this function is `undefined`. The function attempts
	 * to return the value of `size`, but since `size` is not defined or initialized
	 * anywhere before it is attempted to be returned. Therefore any attempt to use it
	 * or retrieve its value results on a `ReferenceError: size is not defined`, returning
	 * nothing (and thus the value is undefined).
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * This function loads a 3D model from an array of vertices and an array of indices.
	 * It creates a VAO (Vertex Array Object), stores the indices and vertex data into
	 * the VAO buffer object and then returns a Model object containing the loaded data
	 * and information about the number of vertices (v_count).
	 * 
	 * @param vertices The `vertices` input parameter specifies an array of floats
	 * representing the Vertex positions for the 3D model to be loaded.
	 * 
	 * @param indices The `indices` input parameter specifies the indices of the vertices
	 * that make up the model.
	 * 
	 * @returns The function "load" returns a new instance of the class "Model", along
	 * with its accompanying vertex array object (VAO). The "Model" instance represents
	 * the loaded geometry and has two fields: vao (the created VAO) and v_count (the
	 * number of indices stored).
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
	 * This function creates a vertex array object (VAO) and returns its ID.
	 * 
	 * @returns This function creates a Vertex Array Object (VAO) and returns its ID. The
	 * output of the function is the ID of the newly created VAO.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * This function stores 3D vertex data into a GPU buffer object using Java bindings
	 * for OpenGL. Specifically:
	 * 
	 * 1/ Generates a new VBO (vertex buffer object) and binds it.
	 * 2/ Uploads the vertex data into the VBO using `glBufferData()`.
	 * 3/ Sets up the vertex attributes for the GPU using `glVertexAttribPointer()`.
	 * 
	 * @param attrib The `attrib` input parameter specifies the vertex attribute buffer
	 * index for the float arrays being stored. In other words , it indicates which vertex
	 * attributes the stored data should be bound to .
	 * 
	 * @param data The `data` input parameter is an array of floating-point numbers that
	 * represents the data to be stored.
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
	 * This function creates an integer buffer object and stores the contents of the
	 * `indices` array within it for later use as an element array buffer.
	 * 
	 * @param indices The `indices` input parameter is an integer array that contains the
	 * indices of the elements to be rendered. This function stores these indices into a
	 * buffer object.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * This function sets the current vertex array object to 0 (disabling it) using the
	 * `GL30.glBindVertexArray()` method.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}


