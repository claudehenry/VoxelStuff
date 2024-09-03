package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Manages OpenGL resources for rendering 3D models.
 * It provides functionality for loading and drawing model data.
 * A static method is available for creating and loading model instances.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a vertex array object (VAO), enables vertex attribute arrays for position
	 * and texture coordinates, draws triangles using indices specified by unsigned
	 * integers, and disables vertex attribute arrays before unbinding the VAO.
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
	 * Enables vertex attribute arrays with indices 0 and 1, allowing OpenGL to access
	 * and use them for rendering purposes. This typically involves setting up vertices
	 * or other geometric data. Attribute arrays enable efficient rendering of complex graphics.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables two generic vertex attribute arrays at indices 0 and 1, effectively
	 * stopping their usage for drawing purposes, likely to save resources or prevent
	 * unintended behavior. The specific attributes are associated with positions and
	 * texture coordinates, respectively. This is a common practice in OpenGL programming.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Returns an integer value representing a Visual Array Object (VAO), indicating its
	 * unique identifier or index. The VAO is likely used for graphics rendering, and
	 * this getter provides access to it. The function simply retrieves and returns the
	 * pre-existing `vao` variable's value.
	 *
	 * @returns an integer value representing a VAO (Vertex Array Object).
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns an integer value representing a container's size or capacity. It is likely
	 * used to retrieve current size information from objects that maintain their own
	 * size tracking. The method does not modify any state and its purpose appears to be
	 * read-only retrieval of stored data.
	 *
	 * @returns the current value of an integer variable named `size`.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Creates a model from provided vertex data and index array. It binds the data to a
	 * VAO, stores the indices and vertices, and returns a model object with the assigned
	 * VAO ID and vertex count. The function initializes a new model for rendering purposes.
	 *
	 * @param vertices 3D model's vertex data to be stored and rendered, typically
	 * comprising of float values for position, normal, and color coordinates.
	 *
	 * @param indices 1D array of vertex index values that determine how to connect the
	 * vertices specified by the `vertices` array.
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
	 * Generates a unique Vertex Array Object ID and binds it for use in OpenGL rendering
	 * operations. It returns the generated VAO ID to be used elsewhere in the code. The
	 * VAO is created and bound in a single operation, allowing for immediate usage.
	 *
	 * @returns a unique identifier for a newly created Vertex Array Object.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates a buffer object and stores vertex data in it for subsequent rendering.
	 * It creates an array buffer with vertex attribute pointers set up for two attributes:
	 * one with 3 floats per vertex and another with 2 floats, offset from the first by
	 * 12 bytes.
	 *
	 * @param attrib 1D index of an attribute in the vertex shader to bind data to, which
	 * is used to specify the location and layout of vertex attributes.
	 *
	 * @param data 2D array of floating-point values to be stored in an OpenGL buffer object.
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
	 * Generates a buffer object to store an array of integer indices for OpenGL rendering,
	 * binds it as an element array buffer, and loads the indices into it from memory
	 * with static draw optimization. It utilizes the `Util.createFlippedBuffer` method
	 * to create a flipped buffer.
	 *
	 * @param indices 1D array of integers that stores indices into vertex data arrays,
	 * passed to the OpenGL buffer storage for rendering purposes.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Resets the vertex array object binding to the default state, effectively disassociating
	 * it from any previously bound buffer objects. This restores the default VAO state,
	 * allowing for flexibility and reusability in graphics rendering operations. The
	 * glBindVertexArray is reset to 0.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
