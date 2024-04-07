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
	 * binds a vertex array object, enables vertex attributes, and then draws a set of
	 * triangles using `glDrawElements`.
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
	 * enables vertex attributes for two attributes, specified by the indices passed as
	 * arguments.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * disables the vertex attribute arrays for two attributes, specified by the function
	 * arguments.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the value of the `vao` field.
	 * 
	 * @returns an integer representing the value of `vao`.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * returns the value of `size`.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * loads a model from vertex and index data stored elsewhere, creating and binding a
	 * Vertex Array Object (VAO) to store the indices, and then returning a new Model
	 * object containing the VAO and number of vertices.
	 * 
	 * @param vertices 3D model's geometry, which is stored as an array of floating-point
	 * values.
	 * 
	 * @param indices 3D model's index array that contains the vertices' indices in the
	 * vertex buffer object (VBO).
	 * 
	 * @returns a `Model` object containing the loaded data.
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
	 * generates a new vertex array object (VBO) and binds it to the current context,
	 * allowing for manipulation of vertices in the 3D graphics pipeline.
	 * 
	 * @returns an integer value representing the generated vertex array object (Vao).
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * creates a new VBO and binds it to an attribute in a shader program
	 * 
	 * @param attrib 3D attribute being stored, specifying the buffer object (VBO) for
	 * storing the data in the given format.
	 * 
	 * @param data 3D vertex data to be stored in the VBO.
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
	 * genrates a new buffer for storing indices, binds it, and buffers the indices data
	 * using the `GL_STATIC_DRAW` mode.
	 * 
	 * @param indices 3D coordinates of the vertices that make up the geometry to be
	 * rendered, and is used to store them in a buffer for later use by the GPU.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * disables the Vertex Array Object (VAO) bound to vertex array index 0, effectively
	 * releasing any resources associated with it.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
