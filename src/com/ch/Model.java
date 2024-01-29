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
	 * This function prepares and renders a mesh using VBOs (vertex buffers) andVAOs
	 * (vertex array objects). It binds the VAO to the currently active rendering context;
	 * enables vertex attributes array for both positions and colors; binds the VBO to
	 * the VAO; draws triangles using `GL_TRIANGLES` primitive mode and `UNSIGNED_INT`
	 * type; disables vertex attribute arrays again. After finishing drawing one mesh ,
	 * the function resets everything.
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
	 * method provided by the OpenGL interface.
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
	 * This function returns the value of the "vao" field or variable.
	 * 
	 * @returns The output of this function is undefined because the variable `vao` has
	 * not been initialized or assigned a value.
	 */
	public int getVAO() {
		return vao;
	}
	
	/**
	 * This function returns the value of the `size` field of the object.
	 * 
	 * @returns The function `getSize()` returns the value of the variable `size`, which
	 * is currently `undefined`. Therefore the output returned by this function is `undefined`.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * This function loads a 3D model from an array of vertices and an array of indices.
	 * It creates a Vertex Array Object (VAO), stores the vertices and indices into it
	 * using `storeIndices` and `storeData`, unbinds the VAO afterwards. Finally it returns
	 * a new Model object containing the data and its VAO ID.
	 * 
	 * @param vertices The `vertices` input parameter stores the position data of the 3D
	 * model's vertices.
	 * 
	 * @param indices The `indices` input parameter specifies the vertices' connections
	 * to each other as a list of triangle indices.
	 * 
	 * @returns The output of this function is a `Model` object that contains the information
	 * required to render a 3D model on the screen. The `Model` object has two members:
	 * `vao`, which is a vertex array object that stores the 3D vertices of the model;
	 * and `v_count`, which is the number of indices stored for the model's mesh.
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
	 * This function creates and returns a unique integer identifier (ID) for a Vertex
	 * Array Object (VAO) on the current GL context. It also binds the VAO to the current
	 * GPU context using the `GL30.glBindVertexArray()` method.
	 * 
	 * @returns The function `createVAO()` creates a new vertex array object (VAO) and
	 * returns its ID. The output returned by this function is the ID of the newly created
	 * VAO.
	 */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	/**
	 * This function stores 3D vertex data into a buffer object and sets up vertex attribute
	 * pointers for later use with the GPU.
	 * 
	 * @param attrib The `attrib` input parameter specifies the attribute (i.e., position
	 * or texture coordinate) to which the data storedin the vertex buffer object will
	 * be bound. In this case，it defines the first and second attribute (position and
	 * texture coordindates respectively)
	 * 
	 * @param data The `data` input parameter is an array of float values that are used
	 * to fill the vertex buffers for the mesh.
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
	 * This function creates an index buffer for rendering data storedin the `indices`
	 * array using OpenGL's `glGenBuffers()` and `glBindBuffer()` functions. The data is
	 * stored into the buffer using `glBufferData()`, with the `GL_ELEMENT_ARRAY_BUFFER`
	 * target and `GL_STATIC_DRAW` usage.
	 * 
	 * @param indices The `indices` input parameter is an integer array that contains the
	 * indices of the elements to be drawn. It is used to define the Vertex Array Object
	 * (VAO) and specify which vertices should be rendered.
	 */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
	/**
	 * This function releases the currently bound vertex array object (VAO) by binding
	 * it to 0.
	 */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
