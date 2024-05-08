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
	
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	public int getVAO() {
		return vao;
	}
	
	public int getSize() {
		return size;
	}
	
	/**
	 * Loads a 3D model from an array of vertices and an array of indices, creates a
	 * Vertex Array Object (VAO), stores the indices and vertices data, unbinds the VAO,
	 * and returns a new Model object.
	 * 
	 * @param vertices 3D vertices data for the model being loaded.
	 * 
	 * @param indices 3D coordinates of the vertices that make up the model, which are
	 * stored in an array for efficient access and rendering.
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
	
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates a new VBO, binds it and copies the input data to it, then sets vertex
	 * attrib pointers for the data.
	 * 
	 * @param attrib 2D vertex attribute index for the data stored in the VBO.
	 * 
	 * @param data 3D data to be stored in a vertex buffer object (VBO).
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
	 * Generates a new buffer object and binds it to an element array buffer slot, then
	 * stores the provided integer array in the buffer using the `GL_STATIC_DRAW` mode.
	 * 
	 * @param indices 3D vertices' indices of an object to be rendered as a mesh when
	 * passed to the `storeIndices()` function.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
