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
	 * This function sets up and draws a set of vertices using GL_TRIANGLES mode. It
	 * enables vertex attrib arrays 0 and 1 then binds a vertex array object (vao) to the
	 * currently bound vertex array buffer object (vb). It also disables the vertex attrib
	 * arrays after drawing the geometry. Finally it unbinds the vao from the gl context.
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
	 * This function enables two vertex attrib arrays (0 and 1) using the
	 * `glEnableVertexAttribArray()` method of the `GL20` class.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * This function disables two vertex attrib arrays using the `glDisableVertexAttribArray`
	 * method.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * This function returns the value of the variable `vao`, which is an integer.
	 * 
	 * @returns The output returned by this function is `undefined`.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * This function returns the value of the field "size" of the object that contains it.
	 * 
	 * @returns The output returned by this function is `undefined`.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * This function loads a 3D model from an array of vertices and an array of indices.
	 * It creates aVAO (Vertex Array Object), stores the vertex data and indices on the
	 * VAO using the `storeData` and `storeIndices` methods (which are not shown here),
	 * binds the VAO using `bindVAO`, returns the count of vertices (`v_count`) as a Model
	 * object.
	 * 
	 * @param vertices The `vertices` input parameter stores the vertex data for the model
	 * being loaded.
	 * 
	 * @param indices The `indices` input parameter is an array of integer indices that
	 * define the Connectivity of the vertices specified by the `vertices` input parameter.
	 * It is used to store the Vertex Information for later access.
	 * 
	 * @returns The function `load` returns a `Model` object containing the data loaded
	 * from the floating-point array of vertices and integer array of indices. The output
	 * consists of two parts:
	 * 
	 * 1/ A validated vertex array object (VAO) created using the `createVAO()` method.
	 * 2/ An integer value representing the count of vertices (indices.length) stored
	 * within the VAO.
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
	 * This function creates and returns an OpenGL vertex array object (VAO) instance.
	 * It generates a unique integer identifier for the VAO using `glGenVertexArrays()`
	 * and then binds the new VAO to the currently active rendering context using `glBindVertexArray()`.
	 * 
	 * @returns This function returns an `int` value that represents the handle of a newly
	 * created vertex array object (VAO) on the GPU.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * This function stores data into a vertex buffer object (VBO) for later usage by the
	 * GPU. It takes two parameters:
	 * 
	 * 	- `attrib`: an integer specifying which attributes to modify
	 * 	- `data`: a floating-point array containing the data to be stored
	 * 
	 * It creates a new VBO using `GLGenBuffers`, binds it using `GLBindBuffer`, and then
	 * sends the data to the VBO using `GLBufferData`. Finally it sets the attribute
	 * pointers using `GLVertexAttribPointer` and disables the VBO using `GLBindBuffer(0)`.
	 * 
	 * @param attrib The `attrib` input parameter specifies the vertex attribute index
	 * that will be used to store the data into the VBO (Vertez Buffer Object). In this
	 * function ,it is used to specify the attributes to be stored into the VBO such as
	 * ( attrib ,1 ) .
	 * 
	 * @param data The `data` parameter is an array of float values that are stored into
	 * the vertex buffer object (VBO) for subsequent rendering.
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
	 * This function stores the integers array "indices" into a OpenGL buffer object named
	 * "ibo".
	 * 
	 * @param indices The `indices` input parameter is an array of integer values that
	 * represents the indices of the elements to be storedin the element buffer.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * This function `unbindVAO()` unbinds the currently bound vertex array object (VAO)
	 * by calling `glBindVertexArray(0)`.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}


