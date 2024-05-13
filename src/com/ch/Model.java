package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * in this Java file manages a 3D model's vertices and indices for rendering. It
 * creates and binds a Vertex Array Object (VAO) and sets up vertex attributebuffers
 * for position and normal data, as well as an Element Array Buffer for storing
 * indices. The `draw()` method calls the appropriate glDrawArrays or glDrawElements
 * to render the model. The `enableAttribs()` and `disableAttribs()` methods
 * enable/disable the vertex attributebuffers, while the `getVAO()` and `getSize()`
 * methods return the VAO handle and the number of vertices in the model, respectively.
 * The `load()` method creates a new Model instance from an array of vertices and
 * indices, and stores the data in the appropriate buffers before unbinding the VAO.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * binds a vertex array object, enables vertex attributes for position and texture
	 * coordinates, draws triangles using `glDrawElements`, and disables the vertex
	 * attributes afterwards.
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
	 * enables two vertex attributes for rendering in a GL context: attribute 0 and
	 * attribute 1.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * disables vertex attributes 0 and 1 in a graphics context using OpenGL API calls.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * retrieves the value of an integer variable called `vao`.
	 * 
	 * @returns an integer value representing the `vao` field.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * retrieves the value of a field named `size`.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * loads a 3D model from a list of vertices and a list of indices into a VAO (Vertex
	 * Array Object) and returns a new `Model` object containing the loaded data.
	 * 
	 * @param vertices 3D model's geometry data, which is stored in an array of floating-point
	 * values and passed to the `storeData()` method for storage in the vertex array
	 * object (VAO).
	 * 
	 * 	- `vao`: An integer representing the value of the created Vertex Array Object (VAO).
	 * 	- `v_count`: An integer indicating the number of vertices in the dataset.
	 * 
	 * @param indices 3D model's index list, which is used to bind the vertex array object
	 * (VAO) and store the vertices' positions.
	 * 
	 * 	- `indices`: An `int[]` array representing the indices of the vertices in the model.
	 * 	- `v_count`: The number of indices in the `indices` array, which is equal to the
	 * number of vertices in the model.
	 * 
	 * @returns a `Model` object containing the loaded data.
	 * 
	 * 	- The `Model` object is created with the `vao` handle obtained through the
	 * `createVAO()` method and the number of indices stored in the `indices` array.
	 * 	- The `v_count` variable denotes the total number of vertices in the model, which
	 * is equal to the number of indices stored in the `indices` array.
	 * 
	 * Therefore, the output returned by the `load` function is a `Model` object with the
	 * specified vertex and index data, along with the handle for the Vertex Array Object
	 * (VAO) used to store and manipulate the data.
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
	 * generates a new vertex array object (VBO) and binds it to the current GL context,
	 * allowing for efficient rendering of 3D models.
	 * 
	 * @returns an integer value representing a unique vertex array object (VAO) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * genrates a VBO and binds it to store 2D arrays data in vertex attributes.
	 * 
	 * @param attrib 3D vertex attribute index that stores the data for the current buffer.
	 * 
	 * @param data 3D data to be stored in the vertex buffer object (VBO).
	 * 
	 * 	- `data` is an array of 4-element vectors in floating-point format.
	 * 	- It is generated using the `Util.createFlippedBuffer()` method.
	 * 	- The array elements are stored as 3D coordinates (x, y, z) with each element
	 * represented by a single float value.
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
	 * generates a new buffer for storing indices, binds it, and stores the provided array
	 * of indices in the buffer using the `GL_STATIC_DRAW` mode.
	 * 
	 * @param indices 3D vertices of a geometric shape, which are stored in a two-dimensional
	 * array and transferred to an element array buffer for rendering using the GPU.
	 * 
	 * 	- `indices` is an array of integers that represents the indices of vertices in a
	 * 3D model.
	 * 	- The length of the array indicates the number of vertices in the model.
	 * 	- Each element in the array corresponds to a vertex in the model, with each vertex
	 * represented by an integer index.
	 * 	- The values in the array are unique and follow a specific order, which is
	 * determined by the application or use case.
	 * 	- The data type of `indices` is `int`, indicating that the values in the array
	 * are 32-bit integers.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * unbinds a vertex array object (VAO) previously bound using `GL30.glBindVertexArray()`.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
}
