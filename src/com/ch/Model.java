package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Is used to handle vertex arrays and indices for rendering 3D models in a graphics
 * application. It provides methods for drawing the model, enabling and disabling
 * vertex attribs, getting the VAO and size of the model, and loading data from an
 * array and indices.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a vertex array object, enables and disables vertex attributes for two
	 * attributes, and then calls either `glDrawArrays` or `glDrawElements` to render a
	 * triangle shape with specified vertices.
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
	 * Enables vertex attributes 0 and 1 for rendering.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables both vertex attribute arrays.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Retrieves the value of a field called `vao`.
	 * 
	 * @returns the value of `vao`, which is an `int`.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns the size of an object.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Loads a 3D model from a set of vertices and indices stored in arrays, creates a
	 * vertex array object (VAO), stores the indices and vertex data, unbinds the VAO,
	 * and returns a `Model` object representing the loaded model.
	 * 
	 * @param vertices 3D model's geometry as a float array containing the coordinates
	 * of the vertices.
	 * 
	 * @param indices 3D model's vertex positions in the format of an integer array, which
	 * is stored in the function for later use in rendering the model.
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
	 * Generates a Vertex Array Object (VAO) and binds it to the current context, allowing
	 * for efficient rendering of 3D models.
	 * 
	 * @returns an integer value representing a valid vertex array object (Vao) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Creates a vertex buffer object (VBO) to store floating-point data, binds it to the
	 * GPU, and sets up vertex attributes for rendering.
	 * 
	 * @param attrib 2D vertex attribute index that corresponds to the data stored in the
	 * `data` array.
	 * 
	 * @param data 2D array of floating-point values that will be stored in a vertex
	 * buffer object (VBO) and passed as attribute data to the GPU for rendering.
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
	 * Genarates a buffer object for storing element array indices, binds it, and sets
	 * the data to be stored using `GL_STATIC_DRAW`.
	 * 
	 * @param indices 3D model's indices to be stored in the buffer.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Terminates the binding of a vertex array object (VAO) by passing a zero value to
	 * `GL30.glBindVertexArray()`.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
