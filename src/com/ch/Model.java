package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Handles rendering of 3D models using OpenGL, providing functionality to create and
 * draw vertices with their associated indices. It encapsulates vertex array objects
 * (VAOs) and vertex buffer objects (VBOs) for efficient data storage and rendering.
 * The class includes methods for loading, drawing, and managing model data.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a vertex array object, enables attribute arrays for vertices and normals,
	 * draws triangles using indexed data, and then disables the attribute arrays and
	 * unbinds the vertex array object.
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
	 * Enables two vertex attribute arrays with indices 0 and 1 for OpenGL rendering,
	 * allowing data to be retrieved from these attributes during rendering operations.
	 * This is typically used for setting up vertex buffers and enabling user-defined
	 * data attributes. The function affects the current GL context.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables two vertex attribute arrays with indices 0 and 1 using OpenGL's GL20
	 * library. This typically indicates that no further data should be sent to these
	 * attributes for rendering purposes. The corresponding data is prevented from being
	 * used by the GPU.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Retrieves and returns a previously initialized VAO object stored in a variable
	 * named `vao`. The function does not modify any state but simply exposes the current
	 * value of the `vao` variable to the caller. It is likely used for rendering purposes.
	 *
	 * @returns an integer representing a valid OpenGL Vertex Array Object ID.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns an integer value representing the current size. It retrieves a stored
	 * `size` variable and provides it as output. This allows other parts of the program
	 * to access or use the current size value.
	 *
	 * @returns an integer representing the current object's property or attribute value.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Initializes a 3D model by creating a Vertex Array Object (VAO), storing index and
	 * vertex data, and returning an instance of the `Model` class with its VAO ID and
	 * vertex count. It binds and unbinds VAO accordingly to preserve GPU resources.
	 *
	 * @param vertices 3D vertex coordinates to be stored in the Vertex Array Object (VAO)
	 * for rendering.
	 *
	 * @param indices 1D array of vertex index values that define the connectivity between
	 * vertices in the 3D model.
	 *
	 * @returns a `Model` object containing VAO ID and vertex count.
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
	 * Generates a unique ID for a Vertex Array Object (VAO), binds it to the current
	 * OpenGL context, and returns its identifier. This VAO can be used to store vertex
	 * array data. The VAO is created and bound in one operation.
	 *
	 * @returns a unique identifier for a Vertex Array Object (VAO).
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates and initializes a buffer object to store vertex data on the GPU. It binds
	 * a new buffer, copies data from a float array into it, and specifies how the buffer's
	 * attributes should be interpreted by the GPU. Buffer is then unbound.
	 *
	 * @param attrib 1-based index of an attribute in the vertex shader that is being
	 * associated with the given data.
	 *
	 * @param data 1D array of floating-point values that will be stored as vertex data
	 * in an OpenGL buffer object.
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
	 * Generates a buffer object and stores an array of indices within it for use by
	 * OpenGL. The indices are created as a flipped byte buffer with the `createFlippedBuffer`
	 * method. The buffer is bound to the GL_ELEMENT_ARRAY_BUFFER target.
	 *
	 * @param indices 1D array of integers containing the indices that will be stored as
	 * an element array buffer.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Unbinds a Vertex Array Object (VAO) from the current OpenGL context, returning the
	 * default VAO (index 0). This is typically done to prevent memory leaks or unexpected
	 * behavior when switching between different VAOs. The operation is performed via an
	 * OpenGL API call.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
