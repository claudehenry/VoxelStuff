package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Handles vertex array objects (VAOs) and buffers for rendering 3D graphics. It
 * provides methods to load data into VAOs, draw primitives, and manage attribute
 * settings. The class facilitates the creation and manipulation of OpenGL resources.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * Enables vertex array and attribute arrays, binds a vertex array object, draws
	 * elements using a buffer, and disables the enabled arrays and unbinds the VAO. It
	 * prepares OpenGL to render graphics for a specific model or scene. It handles the
	 * rendering process.
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
	 * Enables vertex attribute array support for two attributes, typically used for
	 * vertices and colors, with indices 0 and 1 respectively. This allows the GPU to
	 * process these attributes during rendering. The actual attribute locations are
	 * assumed to be predefined.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Disables vertex attribute arrays with indices 0 and 1 for OpenGL rendering. It
	 * calls two separate functions, `glDisableVertexAttribArray`, to disable each array.
	 * This is typically done at the end of rendering a mesh or object.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * Retrieves and returns an integer value representing a Virtual Array Object (VAO).
	 * The VAO is likely a resource used for rendering in graphics contexts, such as
	 * OpenGL or Vulkan. Its purpose is to manage vertex array data.
	 *
	 * @returns an integer identifier for a Vertex Array Object (VAO).
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * Returns the value of the `size` variable. It is a getter method that provides
	 * access to the internal state of an object, allowing other parts of the code to
	 * retrieve its current size. This enables data retrieval and usage elsewhere in the
	 * program.
	 *
	 * @returns an integer value representing the current size.
	 * It is a getter method for instance variable size.
	 * The value is directly retrieved from class field size.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Initializes a 3D model by creating and configuring an OpenGL VAO (Vertex Array
	 * Object), stores vertex data and indices, and returns a `Model` object with its ID
	 * and count of vertices. The VAO is then unbound to free up resources.
	 *
	 * @param vertices 3D vertex data for the model to be loaded, stored as an array of
	 * floating-point numbers.
	 *
	 * @param indices 1D array of integers that specify which vertices to reference when
	 * rendering a model, and is used to store the vertex indices in a VAO (Vertex Array
	 * Object).
	 *
	 * @returns an instance of a custom `Model` class.
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
	 * Generates a unique vertex array object and binds it to the current OpenGL context,
	 * allowing for efficient rendering of graphics data. The generated ID is then returned.
	 * A Vertex Array Object (VAO) manages state for vertex attributes.
	 *
	 * @returns a unique, generated vertex array object identifier.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * Stores vertex data in OpenGL buffers. It generates a buffer object, loads float
	 * array data into it, and sets up vertex attribute pointers for two attributes with
	 * different formats and locations within the buffer. The data is drawn statically.
	 *
	 * @param attrib 0-based index of a vertex attribute array and is used to specify
	 * which attribute is being stored in the buffer object.
	 *
	 * @param data 1D array of float values to be stored in an OpenGL buffer object using
	 * vertex attribute pointers.
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
	 * Generates a buffer object to store element array indices. It creates an empty
	 * buffer object with glGenBuffers and binds it as an element array buffer with
	 * glBindBuffer. The buffer is then populated with indices from the provided array
	 * using glBufferData.
	 *
	 * @param indices 1D array of indices that will be stored in an OpenGL element buffer
	 * object (EBO).
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * Resets the current OpenGL Vertex Array Object (VAO) to its default state, binding
	 * no VAO. This allows subsequent rendering operations to use the default settings
	 * and prevents unintended side effects from previous rendering calls. It sets the
	 * active VAO to zero.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
