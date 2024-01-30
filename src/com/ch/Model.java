package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * This function sets up a vertex array object (VAO), enables vertex attribute arrays
	 * for two attributes (0 and 1), draws elements using `glDrawElements` with the
	 * specified size and type (`GL_TRIANGLES` and `GL_UNSIGNED_INT`, respectively), and
	 * disables the vertex attribute arrays again.
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
	 * This function enables two vertex attributes (0 and 1) using the `glEnableVertexAttribArray()`
	 * method from the `GL20` class.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * This function disables two vertex attrib arrays used by the GPU.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * This function returns the value of the "vao" field.
	 * 
	 * @returns The output returned by this function is `null`, because the field `vao`
	 * is not initialized.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * This function returns the value of the "size" field of the object.
	 * 
	 * @returns The output returned by this function is `undefined`. The reason for this
	 * is that the function has no initial value assigned to its `size` variable and hence
	 * it is not possible to return any valid value from the function.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * This function creates a Vertex Array Object (VAO) and populates it with the contents
	 * of the given arrays of vertices and indices. It then returns a new Model object
	 * containing the VAO and the count of vertices.
	 * 
	 * @param vertices The `vertices` input parameter passes an array of floats that
	 * contains the vertex data for the model.
	 * 
	 * @param indices The `indices` parameter is an array of integers that contains the
	 * vertex indexes of the underlying data structure. This parameter is used to configure
	 * the geometry of the Model being loaded.
	 * 
	 * @returns The function takes two arrays as input - `vertices` and `indices`, and
	 * returns an object of type `Model`. The `Model` object has two fields: `vao` and
	 * `v_count`. `vao` is the handle to a VAO (VerteX Array Object) created by the
	 * function and `v_count` represents the number of vertices present int he model.
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
	 * This function creates and returns a handle for a vertex array object (VAO) on the
	 * GPU. It does so by calling `glGenVertexArrays()` to create the VAO and then binding
	 * it using `glBindVertexArray()`.
	 * 
	 * @returns The output returned by this function is an `int` value that represents
	 * the handle of a newly created Vertex Array Object (VAO) on the GPU.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * This function stores data into an OpenGL VBO (Vertex Buffer Object) and sets up
	 * vertex attributes for the GPU to access the data.
	 * 
	 * @param attrib The `attrib` input parameter specifies the index of the vertex
	 * attribute that the function should configure. In this case there are two vertex
	 * attributes: position (first attribute) and normal (second attribute), so attrib
	 * can have the values 0 or 1.
	 * 
	 * @param data The `data` input parameter is an array of floats that contains the
	 * data to be stored.
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
	 * This function creates and binds an index buffer for drawingElements() using the
	 * indices stored the `int[]` array `indices`.
	 * 
	 * @param indices The `indices` input parameter is an integer array that contains the
	 * indices of the elements to be rendered.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * This function disables the use of a Vertex Array Object (VAO) by binding a zero
	 * value to the `GL30.glBindVertexArray()` function.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}

