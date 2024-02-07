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
	 * Here is the concise answer you requested:
	 * 
	 * The given draw() method prepares to draw geometric data as triangles using the
	 * given VAO (a Vertex Array Object). It also enables two attribute arrays before
	 * binding and then drawing with GL_TRIANGLES with the proper vertex attributes from
	 * the array object. Later it disables both enabled vertex arrays.
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
	 * Vertex attrib arrays are enabled by the function.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Verifies that no more data is accessed via currently disabled arrays of vertices.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the current value of vao
	 * 
	 * @returns The function "getVAO" returns an integer value named "vao".
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Return the value of the "size" field.
	 * 
	 * @returns The function returns an integer value denoted as "size".
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * CREATES A MODEL.
	 * It creates a vertex array object (VAO), stores the indices and data provided and
	 * binds none afterwards returning the created Model of v count elements where the
	 * count is length of indices.
	 * 
	 * @param vertices Vertices are stored.
	 * 
	 * @param indices STORE INDICES.
	 * 
	 * @returns The function returns a new instance of the class Model which contains a
	 * vertex array object (VAO) and a count of the number of vertices (v_count).
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
	 * Vertex Array Object (VAO) is created.
	 * The VAO id is returned.
	 * GL_Bind_Vertext_Array is executed on the created VAO.
	 * 
	 * @returns The output returned by this function is an integer identifier for a newly
	 * created OpenGL Vertex Array Object (VAO).
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Okay. Here's your answer:
	 * 
	 * Generates a new vertex buffer object and fills it with the contents of a provided
	 * floating point array data; establishes pointer connections between specified
	 * attributes within the shader program and the stored buffer data.
	 * 
	 * @param attrib The `attrib` input parameter is passed as the first and second vertex
	 * attribute indexes to the `glVertexAttribPointer()` methods.
	 * 
	 * @param data Of course. Here's your answer:
	 * 
	 * The `data` input parameter provides a float array for storing data through the
	 * `Buffer Objects`.
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
	 * StoreIndices(int[]) Buffers flipped indices data for static draw usage with GL15's
	 * gen buffer(), bind buffer(element array buffer), and bufferData() methods.
	 * 
	 * @param indices INDEXES ARE BINDed TO AN ELEMENT ARRAY BUFFER.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Verb: Unbinds a Vertex Array Object (VAO).
	 * 
	 * The function specified clears the current Vertex Array Object (VAO) of any previous
	 * bindings by issuing a glBindVertexArray(0) command.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
