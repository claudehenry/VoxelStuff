package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Is designed to manage and render 3D models using OpenGL. It provides methods for
 * creating and binding Vertex Array Objects (VAOs), storing vertex data, enabling
 * and disabling attribute arrays, and drawing the model on screen. The class also
 * includes a static method for loading vertices and indices into a VAO.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a vertex array object (VAO), enables two vertex attribute arrays, draws
	 * elements using triangles and unsigned integers, disables the attributes, and unbinds
	 * the VAO.
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
	 * Enables vertex attribute array for two attributes with indices 0 and 1. This
	 * prepares the graphics processing unit (GPU) to process data from these attributes
	 * when rendering objects. The GPU will use the enabled attributes to draw the object
	 * according to the specified vertex buffer object (VBO).
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables vertex attribute arrays at indices 0 and 1 using OpenGL's API. It achieves
	 * this by calling `glDisableVertexAttribArray` twice, once for each index. The result
	 * is that subsequent rendering operations will not consider the values stored in
	 * these arrays.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Retrieves and returns a value representing a vertex array object (VAO). The returned
	 * VAO is an instance variable, accessible by the calling code for further processing
	 * or use. This function serves as a getter, providing read-only access to the stored
	 * VAO value.
	 *
	 * @returns an integer value representing a VAO (Vertex Array Object).
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns an integer value representing the size attribute. It retrieves and exposes
	 * the current value of the `size` variable, providing access to its contents. This
	 * allows external code to obtain the size value without modifying it directly.
	 *
	 * @returns an integer representing the current value of the variable `size`.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Creates a vertex array object (VAO), stores vertex data and indices, unbinds the
	 * VAO, and returns a new instance of `Model` with the created VAO and count of
	 * vertices. The function initializes a model from provided vertex and index data.
	 *
	 * @param vertices 3D model's vertex coordinates that are stored in memory using the
	 * `storeData` method to populate the Vertex Array Object (VAO).
	 *
	 * @param indices 1D array of integers that defines the order and connectivity of the
	 * vertices in the model's geometry.
	 *
	 * @returns a `Model` object containing the VAO ID and vertex count.
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
	 * Generates a unique vertex array object (VAO) using `GL30.glGenVertexArrays`, binds
	 * it to the context with `GL30.glBindVertexArray`, and returns the generated VAO ID.
	 * This allows for storing state information, such as vertex buffers and vertex
	 * attributes, and is used in rendering operations.
	 *
	 * @returns a unique integer identifier for the generated vertex array object.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates and binds a buffer object for storing vertex attribute data. It loads
	 * the data into the buffer using `glBufferData`, and then configures two vertex
	 * attributes with corresponding formats and offsets. The function unbinds the buffer
	 * after configuration is complete.
	 *
	 * @param attrib attribute index that specifies the location of the vertex data in
	 * the buffer object for the OpenGL rendering process.
	 *
	 * @param data 1D array of floats that is stored in the vertex buffer object (VBO)
	 * using `GLBufferData`.
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
	 * Generates a buffer object and binds it to store vertex indices. It then fills the
	 * buffer with the given array of indices, specifying that they are static data that
	 * will not change during rendering.
	 *
	 * @param indices 1D array of indices to be stored as an element array buffer for
	 * rendering purposes.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Disables the rendering context from using a vertex array object (VAO). It achieves
	 * this by binding VAO index 0, effectively disassociating any previous VAO with the
	 * current rendering context. This allows for switching between different VAOs or
	 * releasing resources.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
