package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Handles the rendering of 3D models in an OpenGL context. It provides a mechanism
 * for loading and drawing vertex arrays, storing indices, and enabling/disabling
 * attributes. The class is designed to facilitate efficient rendering of complex 3D
 * scenes.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a vertex array object (VAO), enables attribute arrays, draws elements using
	 * triangle rendering, and then disables attribute arrays and unbinds the VAO.
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
	 * Enables two vertex attribute arrays with indices 0 and 1 for OpenGL rendering.
	 * This is typically done to prepare data for rendering, such as specifying vertex
	 * positions or colors. The function uses the GL20 class to interact with OpenGL functionality.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables vertex attribute array enabling for two attributes at indices 0 and 1
	 * using OpenGL API calls. This essentially stops the rendering of vertex data
	 * associated with these attributes.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Retrieves and returns an integer value representing a vertex array object (VAO).
	 * This VAO is presumably used for rendering graphical data in a Java-based graphics
	 * application. The returned value allows other parts of the program to access and
	 * utilize this VAO.
	 *
	 * @returns an integer value representing a VAO object.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns an integer representing the value of the `size` variable. This function
	 * retrieves and exposes the current state of the `size` attribute to the outside
	 * world, allowing it to be accessed and utilized by other parts of the program or system.
	 *
	 * @returns an integer value representing the size attribute.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Loads model data from an array of vertices and an array of indices into a Vertex
	 * Array Object (VAO). It stores the indices and vertex data, unbinds the VAO, and
	 * returns a new `Model` object with the loaded data.
	 *
	 * @param vertices 3D vertices of an object to be loaded and is used to store its
	 * data in the graphics processing unit (GPU).
	 *
	 * @param indices array of vertex indices that are used to store and retrieve data
	 * from the vertex buffer object (VBO) associated with the VAO created by the function.
	 *
	 * @returns a `Model` object with VAO and vertex count.
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
	 * Generates a unique identifier for a vertex array object (VAO) using the OpenGL
	 * API, and then binds it to the current rendering context. The generated VAO is
	 * returned as an integer value. This allows the creation of a new VAO that can be
	 * used for rendering.
	 *
	 * @returns an integer value representing a newly generated vertex array object.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Stores float data in a vertex buffer object (VBO) for later rendering using OpenGL.
	 * It generates a VBO, binds it to an array buffer target, and uploads the provided
	 * data.
	 *
	 * @param attrib attribute index used to specify the location and data format of the
	 * vertex attributes stored in the buffer object.
	 *
	 * @param data 1D array of float values that are stored in the vertex buffer object
	 * (VBO) using `glBufferData`.
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
	 * Generates a buffer object and binds it to store an array of integer indices for
	 * OpenGL. It then loads the indices into the buffer using `glBufferData`, specifying
	 * that the data is static and will not change during rendering.
	 *
	 * @param indices 1D array of integer indices that are used to store vertex buffer data.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Disassociates the current Vertex Array Object (VAO) from the OpenGL context,
	 * effectively releasing its resources and restoring the default VAO to zero. This
	 * frees up system memory and prevents accidental modifications to other VAOs.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
