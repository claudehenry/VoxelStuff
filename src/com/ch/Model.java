package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Provides an interface for rendering and storing vertex data in OpenGL. It encapsulates
 * the process of loading and drawing 3D models from arrays of vertices and indices.
 * The class also includes utility methods for enabling and disabling attribute access.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Enables and disables vertex attribute arrays and binds a vertex array object to
	 * the GPU context for rendering, drawing specified geometry with indexed vertices
	 * using triangles.
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
	 * Enables two generic vertex attribute arrays (GL20) by calling glEnableVertexAttribArray
	 * with indices 0 and 1, respectively. This allows OpenGL to access specific attributes
	 * of a mesh's vertices during rendering operations. Attribute array state is set for
	 * the current context.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables vertex attribute arrays for vertices and normals, indicated by indices 0
	 * and 1 respectively, thereby preventing data from these locations being used during
	 * rendering operations. The changes are applied globally. Vertex data from these
	 * sources is no longer sent to the GPU.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Returns an integer representing a Vertex Array Object (VAO). The VAO is presumably
	 * used for storing and managing vertex array data. This data is likely related to
	 * graphics rendering and may be used by other functions or methods within the class.
	 *
	 * @returns an integer value representing a VAO (Vertex Array Object) ID.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns an integer representing the current size of a data structure or object.
	 * The size is presumably stored as an instance variable named `size`. This value can
	 * be accessed by calling the `getSize` method.
	 *
	 * @returns an integer representing the current size of a data structure.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Creates a model by binding vertex array object (VAO), storing vertices and indices
	 * data, and unbinding VAO, ultimately returning a new `Model` instance with the
	 * created VAO and vertex count. It takes float arrays of vertices and int arrays of
	 * indices as input.
	 *
	 * @param vertices 3D vertex data of an object to be rendered, which is stored in the
	 * graphics card's memory using OpenGL commands.
	 *
	 * @param indices 1D array of indices that define the connectivity between vertices
	 * for rendering purposes.
	 *
	 * @returns a new instance of the `Model` class.
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
	 * Generates a unique identifier for a vertex array object, binds it to the current
	 * OpenGL context, and returns the generated ID. This allows the application to store
	 * references to vertex arrays for later use. The VAO is created on the GPU.
	 *
	 * @returns a unique identifier for a Vertex Array Object.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates and configures a vertex buffer object (VBO) to store data for rendering.
	 * It creates a VBO, binds it, and uploads vertex data from an array into the GPU's
	 * memory, configuring two attributes with their respective formats and offsets. The
	 * VBO is then unbound.
	 *
	 * @param attrib 16-bit integer index specifying the location of an attribute buffer
	 * within the vertex shader.
	 *
	 * @param data 1D array of floating-point values that will be stored in an OpenGL
	 * buffer object for use as vertex attributes.
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
	 * Generates a buffer object and stores an array of indices in it for use with OpenGL.
	 * It creates a static draw buffer from the provided index array, binding it as an
	 * element array buffer. The buffer is then stored for future use.
	 *
	 * @param indices 1D array of integer vertex indices to be stored in an OpenGL buffer
	 * object.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Resets the current vertex array object (VAO) to the default state, effectively
	 * disassociating it from any previously bound VAO. It achieves this by binding a VAO
	 * with an index of 0 using the `glBindVertexArray` function. This clears the current
	 * binding.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
