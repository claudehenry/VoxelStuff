package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * in Java manages vertex data and indices for rendering 3D objects using OpenGL. It
 * has several methods to enable and disable vertex attribs, bind and unbind the
 * Vertex Array Object (VAO), and load data from an array of vertices and indices.
 * The class also provides a way to get the VAO handle and size of the data.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * binds a vertex array object (vao), enables vertex attributes for position and
	 * texture coord, draws vertices using `glDrawElements`, disables the vertex attributes,
	 * and unbinds the vao.
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
	 * enables two vertex attributes in a GL context using `glEnableVertexAttribArray`.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * disables two vertex attributes (attribs) using `glDisableVertexAttribArray`. The
	 * first attrib is disabled by passing `0` as the argument, and the second attrib is
	 * disabled by passing `1` as the argument.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the value of a field called `vao`.
	 * 
	 * @returns an integer value representing the `vao` field.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * returns the value of a field named `size`.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * loads a 3D model from a set of vertex and index buffers. It creates a Vertex Array
	 * Object (VAO), stores the indices and vertex data, unbinds the VAO, and returns a
	 * `Model` object representing the loaded model.
	 * 
	 * @param vertices 3D geometry of the model to be loaded, which is stored in an array
	 * of floating-point values.
	 * 
	 * 	- `vertices`: An array of `float` values representing 3D vertices.
	 * 	- `indices`: An array of integers representing the indices of the vertices in the
	 * VAO.
	 * 
	 * @param indices 2D array of index values that correspond to the vertices in the
	 * `vertices` array, which are then stored in a Vulkan vertex buffer object (VBO) for
	 * rendering.
	 * 
	 * 	- `indices`: An integer array representing the vertices of a 3D model.
	 * 	- `length`: The number of indices in the array, which corresponds to the number
	 * of vertices in the model.
	 * 
	 * @returns a `Model` object containing the loaded vertex array and index count.
	 * 
	 * 	- The output is of type `Model`, indicating that it represents a 3D model.
	 * 	- The `vao` field contains the value of an integer, which is the handle for the
	 * Vertex Array Object (VAO) used to store and manipulate the vertices of the model.
	 * 	- The `v_count` field contains the value of an integer, which is the number of
	 * vertices in the model.
	 * 
	 * The `Model` object returned by the `load` function represents a 3D model that can
	 * be rendered using the GPU. The VAO handle and vertex count are used to manage the
	 * model's vertices and indices during rendering.
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
	 * creates a new vertex array object (VAO) and binds it to the GPU for further use.
	 * 
	 * @returns an integer representing a unique vertex array object (Vao) identifier
	 * created and bound by GL30.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * genrates a vertex buffer object (VBO) and binds it to the graphics processing unit
	 * (GPU). It then sets the data at the specified attribute indices in the VBO using
	 * the `glVertexAttribPointer()` method. Finally, it unbinds the VBO.
	 * 
	 * @param attrib 2D vertex attribute index that contains the data to be stored in the
	 * VBO.
	 * 
	 * @param data 3D data to be stored in the vertex buffer object (VBO) and is used to
	 * populate the buffer with the appropriate data.
	 * 
	 * 	- `data` is an array of `float`s.
	 * 	- The length of the array is specified as `5 * 4`, indicating that there are 5
	 * arrays of 4 elements each in the input data.
	 * 	- The second attribute in the function, `attrib + 1`, refers to a different
	 * attribute than the first one, indicating that there are multiple attributes in the
	 * input data.
	 * 	- The property `GL11.GL_FLOAT` is used to describe the data type of each element
	 * in the array.
	 * 	- The value `false` is passed as the second argument to `GL15.glBufferData()`,
	 * indicating that the data should be stored in the buffer using the default behavior.
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
	 * generates a new buffer for storing indices, binds it, and then stores the given
	 * indices in the buffer using `GL15.glBufferData()`.
	 * 
	 * @param indices 3D indices of the vertices in an array that will be stored in an
	 * element array buffer.
	 * 
	 * 	- The input `indices` is an integer array of unknown size, as indicated by the
	 * type `int[]`.
	 * 	- The `GL15.glGenBuffers()` method is called to create a new buffer object (`ibo`).
	 * 	- The `GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)` method binds the
	 * newly created buffer object to the element array buffer slot.
	 * 	- The `GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices),
	 * GL15.GL_STATIC_DRAW)` method sets the data for the element array buffer using the
	 * `Util.createFlippedBuffer(indices)` method, which creates a copy of the input
	 * `indices` array with its elements flipped. The `GL15.glBufferData()` method takes
	 * three arguments: the target buffer object (`GL15.GL_ELEMENT_ARRAY_BUFFER`), the
	 * data to be stored in the buffer (`Util.createFlippedBuffer(indices`)), and the
	 * draw mode (`GL15.GL_STATIC_DRAW`).
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * disengages the Vertex Array Object (VAO) bound to it, releasing the GPU resources
	 * associated with the VAO.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
