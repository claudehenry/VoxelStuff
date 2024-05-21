package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * is used to manage vertex and element arrays for rendering 3D objects. It provides
 * methods for binding and unbinding vertex array objects (VAOs) and elements array
 * buffer objects (EABOs), as well as storing data in these buffers. Additionally,
 * the class provides a way to load data from an array of vertices and indices and
 * create a new Model instance.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * binds a vertex array object, enables two vertex attributes, and calls either
	 * `glDrawArrays` or `glDrawElements` to render a triangle-based shape.
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
	 * disables both vertex attribute arrays for a GL20 context.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the value of a variable `vao`.
	 * 
	 * @returns an integer value representing the `vao` field.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * returns the value of the `size` field, which represents the number of elements in
	 * a collection or array.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * loads a model into memory from a set of vertex and index data. It creates a Vertex
	 * Array Object (VAO), stores the indices, and then stores the vertex data before
	 * unbinding the VAO. The function returns a `Model` object containing the loaded data.
	 * 
	 * @param vertices 3D model's geometry as an array of float values, which are stored
	 * and bound to a Vertex Array Object (VAO) for rendering.
	 * 
	 * 	- `vertices`: An array of `float` values representing 3D vertices for a model.
	 * 	- `indices`: An array of `int` values representing the indices of the vertices
	 * in the array.
	 * 	- `v_count`: The number of vertices in the input array, which is also the length
	 * of the `indices` array.
	 * 
	 * @param indices 3D model's index data for the vertices, which is stored and bound
	 * to a Vertex Array Object (VAO) by the function for efficient rendering.
	 * 
	 * 	- `indices`: An array of integers representing the indices of the vertices in the
	 * model.
	 * 	- `v_count`: The number of vertices in the model, which can be calculated by
	 * summing up the lengths of all the arrays that `indices` points to.
	 * 
	 * @returns a `Model` object representing a 3D model.
	 * 
	 * 	- The output is a `Model` object, which represents a 3D model in the scene.
	 * 	- The `vao` field of the `Model` object contains the handle to the Vertex Array
	 * Object (VAO) used to store and manipulate the vertices of the model.
	 * 	- The `v_count` field of the `Model` object indicates the number of vertices in
	 * the model.
	 * 
	 * In summary, the output of the `load` function is a `Model` object with a handle
	 * to a VAO and the number of vertices in the model.
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
	 * generates a new vertex array object (VBO) using the `glGenVertexArrays` method and
	 * binds it to the current context using `glBindVertexArray`. The returned value is
	 * the ID of the newly created VBO.
	 * 
	 * @returns an integer value representing a unique vertex array object (Vao) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * 1) generates a vertex buffer object (VBO), 2) binds it, 3) stores array data in
	 * it, and 4) sets vertex attribute pointers for rendering.
	 * 
	 * @param attrib attribute index of the vertex data to be stored in the VBO.
	 * 
	 * @param data 2D array of floating-point values that will be stored in the VBO as
	 * an array of three floats, with each float value located at a specific index in the
	 * array.
	 * 
	 * 	- The input `data` is an array of 3D floats, indicating that it has three dimensions
	 * (x, y, and z) and contains floating-point values.
	 * 	- The `createFlippedBuffer` method is used to create a buffer object from the
	 * input data, which is then passed as a parameter to the `GL15.glBufferData` function
	 * for storage in the VBO.
	 * 	- The `GL20.glVertexAttribPointer` function is called twice, once for each attribute
	 * (x and y) of the input data, to set the corresponding vertex attributes for
	 * rendering. The second attribute (`z`) is not included as it is not needed for this
	 * specific use case.
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
	 * stores an array of indices in a buffer for static draw operations using OpenGL's
	 * `GL_ELEMENT_ARRAY_BUFFER` binding.
	 * 
	 * @param indices 3D vertex positions of a mesh to be stored in an element array
	 * buffer for rendering in a 3D graphics pipeline.
	 * 
	 * 	- `GL15.glGenBuffers()` creates a new buffer object with the method `GL15.glGenBuffers()`.
	 * 	- `GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo)` binds the newly created
	 * buffer object to the element array buffer slot using `GL15.glBindBuffer()`.
	 * 	- `GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices),
	 * GL15.GL_STATIC_DRAW)` copies the deserialized input `indices` into a buffer object
	 * using `GL15.glBufferData()`, and then binds it to the element array buffer slot
	 * using `GL15.glBindBuffer()`. The `Util.createFlippedBuffer(indices)` method creates
	 * a flipped copy of the input `indices`.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * disables the vertex array object (VAO) bound to the current program by passing a
	 * value of `0` to the `glBindVertexArray` method.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
