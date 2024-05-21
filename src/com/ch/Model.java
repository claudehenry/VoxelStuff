package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * in Java provides a way to load and display a 3D model using OpenGL. It takes in
 * the vertex array object (VAO) and size of the model as parameters in its constructor,
 * and then loads the model data into memory and binds it to the VAO for display. The
 * class also includes methods for enabling and disabling certain attribute arrays,
 * getting the VAO handle, and loading the model data from an array of vertices and
 * an array of indices.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * binds a Vertex Array Object (VAO), enables vertex attributes, draws a collection
	 * of triangles using `GL_TRIANGLES`, disables the vertex attributes, and then unbinds
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
	 * enables two vertex attributes for rendering in the GPU.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * disables both vertex attribute arrays (0 and 1) for a given GL context.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the value of the `vao` field, which is an integer.
	 * 
	 * @returns the value of the `vao` field, which is an integer.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * returns the current size of an object's internal state.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * loads a 3D model into memory by creating a Vertex Array Object (VAO), storing
	 * indices and vertex data, and returning a `Model` object representing the loaded model.
	 * 
	 * @param vertices 3D vertices data of the model to be loaded.
	 * 
	 * 	- `vertices`: An array of `float` values representing the 3D model's vertices.
	 * 	- `indices`: An array of `int` values representing the indices of the vertices
	 * in the `vertices` array.
	 * 	- `v_count`: The number of vertices in the `vertices` array, which is determined
	 * by the length of the `indices` array.
	 * 
	 * @param indices 3D model's vertex indices, which are used to store and manipulate
	 * the vertices of the model within the GPU memory.
	 * 
	 * 	- `indices.length`: The number of indices in the buffer.
	 * 	- `indices[i]`: Each index represents a vertex in the 3D model.
	 * 
	 * @returns a `Model` object representing the loaded 3D model.
	 * 
	 * 	- `vao`: A valid VAO (Vertex Array Object) handle representing the loaded 3D model.
	 * 	- `v_count`: The number of vertices in the loaded model.
	 * 	- `Model`: An instance of the `Model` class, which contains information about the
	 * loaded model, including its handle and vertex count.
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
	 * generates a new Vertex Array Object (VAO) and binds it to the current GL context,
	 * making it available for rendering.
	 * 
	 * @returns an integer value representing a valid vertex array object (VBO) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * binds an array buffer to a vertex attribute and stores data in it. It then binds
	 * a second vertex attribute and stores additional data in it.
	 * 
	 * @param attrib 2D vertex attribute index that stores the data in the VBO.
	 * 
	 * @param data 3D data to be stored in the vertex buffer object (VBO).
	 * 
	 * 	- `data` is an array of `float` type.
	 * 	- The length of the array is 4 for both dimensions (height and width).
	 * 	- The first element in each row represents the vertex coordinates in the order
	 * (x, y, z), where (0, 0, 0) is the origin.
	 * 	- The remaining elements in each row represent the texture coordinates (u, v) in
	 * the range of [0, 1].
	 * 	- The last two elements in each row are padding values.
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
	 * generates a new buffer object and binds it to an element array buffer, storing the
	 * provided integer indices in the buffer using the `GL_STATIC_DRAW` mode.
	 * 
	 * @param indices 3D vertex indices of a geometric shape to be stored in an element
	 * array buffer for rendering purposes.
	 * 
	 * 	- `indices` is an integer array.
	 * 	- `ibo` is an integer that represents the buffer object handle generated by `GL15.glGenBuffers()`.
	 * 	- `GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)` binds the buffer object
	 * to the element array buffer slot.
	 * 	- `GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices),
	 * GL15.GL_STATIC_DRAW)` copies the elements of `indices` to the bound buffer object
	 * using the `GL15.glBufferData()` method. The `Util.createFlippedBuffer(indices)`
	 * method is used to create a flipped copy of the array, which is necessary for
	 * efficient rendering of element arrays in OpenGL.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * disengages the vertex array object (VAO) bound to the GPU.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
