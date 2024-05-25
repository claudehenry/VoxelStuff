package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * in Java provides functionality for loading and rendering 3D models using OpenGL.
 * It takes in various parameters such as vertex count, size, and indices to render
 * the model. The class also includes methods for binding and unbinding vertex arrays
 * and enabling/disabling vertex attribs.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
	/**
	 * binds a vertex array object, enables vertex attributes, draws a set of triangles
	 * using `glDrawElements`, and disables the vertex attributes again.
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
	 * enables vertex attribute arrays 0 and 1 for rendering.
	 */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * disables both vertex attributes (arrays) for a given program.
	 */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	/**
	 * returns the value of `vao`.
	 * 
	 * @returns an integer value representing the Vulkan abstract object (VAO) handle.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * retrieves the value of the `size` field of an object.
	 * 
	 * @returns the value of the `size` field.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * loads data from an array of vertices and an array of indices into a model object,
	 * creating a Vertex Array Object (VAO) for binding the data, storing the indices,
	 * and returning a new Model object containing the loaded data.
	 * 
	 * @param vertices 3D model's geometric data, which is stored in an array of float
	 * values and passed to the `storeData()` method for storage in the vertex array
	 * object (VAO).
	 * 
	 * 	- `vertices` is an array of floating-point values representing 3D vertices in a
	 * model.
	 * 	- `indices` is an array of integers representing the indices of the vertices in
	 * the VBO.
	 * 	- The function creates a VAO, binds it, stores the index data, and then unbinds
	 * it before returning a new Model object containing the VAO handle and the number
	 * of vertices.
	 * 
	 * @param indices 3D model's vertex indices, which are used to bind the vertices of
	 * the model to the appropriate buffer objects in the GPU.
	 * 
	 * 	- `indices`: An array of integers representing the indices of the vertices in the
	 * model.
	 * 	- `v_count`: The number of vertices in the model, which is equal to the length
	 * of the `indices` array.
	 * 
	 * @returns a `Model` object representing the loaded 3D model.
	 * 
	 * 	- The output is a `Model` object, which represents a 3D model in the program.
	 * 	- The `vao` field of the output refers to an integer value that represents the
	 * vertex array object (VAO) used to store the vertices and indices of the model.
	 * 	- The `v_count` field of the output indicates the number of vertices in the model,
	 * which is equal to the number of indices stored in the `indices` array.
	 * 
	 * Therefore, the output of the `load` function can be described as a `Model` object
	 * that stores the vertices and indices of a 3D model using a VAO, and provides access
	 * to the vertex count of the model.
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
	 * generates a new vertex array object (VAO) using the `glGenVertexArrays` method and
	 * binds it to the current GL context using `glBindVertexArray`.
	 * 
	 * @returns an integer value representing a unique vertex array object (VBO) handle.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * generates a vertex buffer object (VBO) and binds it to a graphics processing unit
	 * (GPU). Then it populates the VBO with an array of float data and sets up vertex
	 * attributes for rendering. Finally, the function unbinds the VBO and returns.
	 * 
	 * @param attrib 2D vertex attribute that contains the data to be stored in the VBO.
	 * 
	 * @param data 3D array of float values that are stored in a vertex buffer object
	 * (VBO) for further use in the GPU rendering process.
	 * 
	 * 	- `data`: A `float[]` array representing the data to be stored in the Vertex
	 * Buffer Object (VBO).
	 * 	- `attrib`: An integer variable that represents the attribute number of the VBO
	 * where the data should be stored.
	 * 	- `Util.createFlippedBuffer(data)`: This method creates a new buffer object by
	 * serializing and deserializing the input `data` array using the `GL15.glBufferData()`
	 * function. The resulting buffer is flipped to match the expected layout of the VBO.
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
	 * generates a new buffer object using the `glGenBuffers()` method, binds it to the
	 * `GL_ELEMENT_ARRAY_BUFFER` target using `glBindBuffer()`, and then copies the
	 * provided array of indices into the buffer using `glBufferData()`.
	 * 
	 * @param indices 3D indices of vertices in a triangle strip, which are stored in a
	 * buffer for later use in rendering.
	 * 
	 * 	- `indices`: An integer array representing the indices of a 2D buffer to be bound
	 * to an element array buffer.
	 * 	- `GL15`: A class for working with OpenGL functions, specifically those related
	 * to buffer management and binding.
	 * 	- `glGenBuffers()`: Creates a new buffer ID using the `glGenBuffers()` function.
	 * 	- `glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)`: Binds the newly created buffer
	 * ID to an element array buffer slot using the `glBindBuffer()` function with the
	 * value of `ibo`.
	 * 	- `Util.createFlippedBuffer(indices)`: Creates a new buffer filled with the
	 * elements of `indices` in a flipped order for use as a 2D buffer.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * disables the vertex array object (VAO) previously bound using `GL30.glBindVertexArray()`.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
