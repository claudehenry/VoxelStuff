package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Represents a graphical model that can be drawn using OpenGL. It manages vertex
 * arrays and buffers to store data and indices for rendering. The class provides
 * functionality for loading and drawing the model.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Enables vertex attributes and binds a vertex array object (VAO) to render a triangle
	 * mesh with GL_TRIANGLES primitive type, using unsigned integer indices from memory
	 * location 0. It disables attributes after rendering and releases the VAO binding.
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
	 * Enables vertex attribute arrays for rendering on a graphics device. It sets two
	 * arrays, indexed by 0 and 1, to be enabled for rendering. This allows for the use
	 * of multiple attributes, such as position and color, when drawing graphics.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables the vertex attribute arrays with indices 0 and 1 for OpenGL operations,
	 * effectively stopping the processing of vertex data associated with these attributes.
	 * This is typically used to optimize performance by reducing the amount of data being
	 * processed.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Returns an integer value representing a Variable-Attribute Object (VAO). It retrieves
	 * and provides access to the VAO object, allowing for further manipulation or
	 * inspection of its attributes. The returned value can be used as needed by the program.
	 *
	 * @returns an integer value representing a vertex array object.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Retrieves and returns an integer value representing the size attribute. It does
	 * not modify the size or perform any calculations; it simply provides access to the
	 * existing value. The returned value is used by other parts of the program to utilize
	 * the size information.
	 *
	 * @returns an integer value representing the size of an object.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Initializes a Model object by creating and configuring Vertex Array Object (VAO)
	 * for rendering 3D graphics. It stores vertex data and indices, and then binds the
	 * VAO. The function returns the newly created Model object with its associated VAO
	 * and vertex count.
	 *
	 * @param vertices 3D vertex data that is stored in the Vertex Array Object (VAO)
	 * using the `storeData` method.
	 *
	 * @param indices array of indices used to specify the order in which vertices are
	 * drawn for rendering purposes.
	 *
	 * @returns an instance of the `Model` class.
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
	 * Generates a vertex array object (VAO) using `glGenVertexArrays` and binds it to
	 * the current rendering context with `glBindVertexArray`, returning the VAO's identifier.
	 *
	 * @returns an integer representing a generated vertex array object (VAO).
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates a vertex buffer object (VBO), binds and loads data into it using
	 * `glBufferData`, then sets up vertex attribute pointers for OpenGL rendering. The
	 * function takes an attribute number and an array of float values as input, specifying
	 * the layout of the VBO.
	 *
	 * @param attrib attribute number for which buffer data is being stored and used as
	 * an index to specify the location of the corresponding vertex attribute array.
	 *
	 * @param data 1D array of float values that is used to store vertex data for rendering.
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
	 * Generates a buffer object for storing element array indices and binds it to the
	 * current target buffer object. It then loads the provided `indices` array into the
	 * bound buffer using static draw data.
	 *
	 * @param indices 1D array of integers that is used to store indices for rendering
	 * elements in OpenGL.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Binds a null value to the current vertex array object, effectively releasing any
	 * previously bound vertex array and restoring the default state. This ensures that
	 * subsequent rendering operations do not inadvertently use the previously bound
	 * vertex array.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
