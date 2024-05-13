package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * is used for storing and manipulating 3D model data in OpenGL. It provides methods
 * for drawing the model, binding an Vertex Array Object (VAO), enabling vertex
 * attributes, and disabling them. The class also provides a method for loading the
 * model from a float array and an integer array of indices.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * binds a vertex array object, enables vertex attributes for position and color,
	 * draws a collection of triangles using `glDrawElements`, and then disables the
	 * vertex attributes and releases the vertex array object.
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
	 * enables vertex attributes 0 and 1 for rendering.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * disables vertex attribute arrays for two attributes using `glDisableVertexAttribArray`.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the value of the `vao` field.
	 * 
	 * @returns an integer value representing the Vulkan array object (VAO) handle.
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
	 * loads a model from an array of vertices and an array of indices, creates a Vulkan
	 * vertex array object (VAO), stores the indices and vertex data, unbinds the VAO,
	 * and returns a new `Model` instance.
	 * 
	 * @param vertices 3D model's vertices.
	 * 
	 * 	- `vao`: A valid instance of an `int` variable that holds the value of the vertex
	 * array object (VAO) created by the function.
	 * 	- `v_count`: An integer variable representing the number of vertices in the input
	 * data.
	 * 
	 * The `vertices` array contains the 3D coordinates of the model's vertices, stored
	 * as a series of float values.
	 * 
	 * @param indices 3D model's vertex indices, which are used to store the vertices of
	 * the model in the GPU's memory for rendering.
	 * 
	 * 	- `indices`: an `int[]` array containing the index values of the vertices in the
	 * model.
	 * 	- `v_count`: an `int` value representing the number of indices in the `indices`
	 * array.
	 * 
	 * @returns a `Model` object representing the loaded 3D model.
	 * 
	 * 	- The `Model` object returned has two attributes: `vao` and `v_count`. `vao` is
	 * an integer representing the VAO handle, and `v_count` is an integer representing
	 * the number of vertices in the model.
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
	 * generates a vertex array object (Vao) and binds it using `GL30.glBindVertexArray()`.
	 * The returned value is the handle to the Vao.
	 * 
	 * @returns an integer value representing a unique vertex array object (Vao) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * generates a vertex buffer object (VBO) for storing float data and binds it to a
	 * shader program using the `glVertexAttribPointer` method, setting two floating-point
	 * attributes.
	 * 
	 * @param attrib 3D vertex attribute index that the function will use to specify the
	 * data location in the GPU memory for storage.
	 * 
	 * @param data 3D data that will be stored in the vertex buffer object (VBO) and used
	 * for rendering.
	 * 
	 * 	- `Util.createFlippedBuffer(data)` creates a buffer containing the data in a
	 * flipped order, where each element is followed by its corresponding mirrored element.
	 * 	- `GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)` binds the `vbo` to an array
	 * buffer object (ABO).
	 * 	- `GL15.glBufferData(GL15.GL_ARRAY_BUFFER, Util.createFlippedBuffer(data),
	 * GL15.GL_STATIC_DRAW)` stores the data in the ABO using the `static draw` mode.
	 * 	- `GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 5 * 4,     0)`
	 * sets up a vertex attribute pointer for the first attrib (attrib 0), using three
	 * floating-point values per vertex.
	 * 	- `GL20.glVertexAttribPointer(attrib + 1, 2, GL11.GL_FLOAT, false, 5 * 4, 3 * 4)`
	 * sets up a second vertex attribute pointer for the second attrib (attrib 1), using
	 * two floating-point values per vertex.
	 * 	- `GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)` releases the ABO.
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
	 * genarates a buffer for storing indices, binds it, and then sets the data for the
	 * buffer using a flipped array.
	 * 
	 * @param indices 3D indices of vertices in a buffer that will be stored in the element
	 * array buffer.
	 * 
	 * 	- `indices` is an array of integers representing the indices of vertices in a 3D
	 * model.
	 * 	- The size of the array is determined by the number of vertices in the model.
	 * 	- Each element of the array corresponds to a vertex in the model, with the index
	 * being a unique identifier for each vertex.
	 * 	- The order of the elements in the array is significant and must be maintained
	 * when storing or retrieving the indices.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * disables the vertex array object (VAO) bindings, effectively ending its use.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
}
