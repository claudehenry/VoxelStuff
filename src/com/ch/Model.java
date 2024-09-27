package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Handles vertex array objects and data for 3D rendering.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Binds a vertex array object (VAO), enables vertex attribute arrays, draws triangles
	 * using indexed vertices, disables the arrays, and unbinds the VAO. It uses OpenGL
	 * functionality to render graphics.
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
	 * Enables vertex attribute arrays 0 and 1 for OpenGL rendering. This allows the
	 * graphics card to access vertex attributes, such as positions and colors, to render
	 * 3D graphics.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables vertex attribute array 0 and vertex attribute array 1.
	 * It uses the `glDisableVertexAttribArray` function from the GL20 class to achieve
	 * this.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Returns the value of the `vao` variable, providing access to the VAO (Vertex Array
	 * Object) ID.
	 *
	 * @returns the value of the `vao` variable.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns the current value of the `size` variable, providing access to its value.
	 *
	 * @returns the current value of the `size` variable.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Creates a VAO, stores vertex indices and data, and returns a new Model object with
	 * the VAO ID and vertex count.
	 *
	 * @param vertices array of 3D vertices that define the geometry of the model, stored
	 * in a float array.
	 *
	 * @param indices array of vertex indices used to define the connectivity of the
	 * model's vertices.
	 *
	 * @returns an instance of the `Model` class, containing a VAO ID and vertex count.
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
	 * Generates a unique Vertex Array Object (VAO) ID using `glGenVertexArrays`, binds
	 * it to the current OpenGL context using `glBindVertexArray`, and returns the generated
	 * VAO ID.
	 *
	 * @returns a unique identifier for a generated vertex array object.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Generates a buffer object, uploads vertex data to it, and sets up vertex attribute
	 * pointers for two attributes. The data is stored in a static draw buffer and the
	 * vertex attributes are 3 floats and 2 floats each with a stride of 20 bytes.
	 *
	 * @param attrib attribute index used to specify the location of the vertex attribute
	 * data in the buffer.
	 *
	 * @param data array of floating-point values that is stored in the vertex buffer
	 * object (VBO) for use in the graphics pipeline.
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
	 * Generates a new buffer object, binds it as an element array buffer, and stores the
	 * provided indices in it using static drawing.
	 *
	 * @param indices array of vertex indices to be stored in the buffer object.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Binds the default vertex array object (VAO) to the OpenGL context, effectively
	 * unbinding any previously bound VAO. This prevents unintended VAO modifications and
	 * ensures data integrity. The default VAO is typically used for non-vertex array operations.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
