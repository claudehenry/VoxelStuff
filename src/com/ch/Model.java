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
  * This function is a mesh drawing function that binds a vertex array object (VAO),
  * enables two vertex attribute arrays (position and color), draws a set of triangles
  * using `glDrawElements`, and then disables the vertex attribute arrays and unbinds
  * the VAO.
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
  * This function enables two vertex attribute arrays (indexed as 0 and 1) using the
  * `glEnableVertexAttribArray` method of the OpenGL context.
  */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
 /**
  * This function disables two vertex attribute arrays (VAAs) with indices 0 and 1
  * using the `glDisableVertexAttribArray()` method.
  */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
 /**
  * This function returns the value of the field "vao" which is of type integer.
  * 
  * 
  * @returns { int } The output returned by this function is `undefined`.
  */
	public int getVAO() {
		return vao;
	}
	
 /**
  * This function returns the value of the field "size".
  * 
  * 
  * @returns { int } The output returned by this function is "undefined". The reason
  * for this is that the function does not contain any code that provides a valid value
  * for the "size" field. The "size" field is simply declared as an instance variable
  * without being initialized or assigned a value. Therefore、when the function is
  * called and returns the value of "size", it will return undefined since no value
  * has been assigned to "size".
  */
	public int getSize() {
		return size;
	}
	
 /**
  * This function loads a 3D model from an array of vertices and an array of indices.
  * It creates aVAO (Vertex Array Object), stores the indices and vertex data using
  * bindMethod(), unbinds the VAO and returns a new Model object with the loaded data.
  * 
  * 
  * @param { float[] } vertices - The `vertices` input parameter is an array of floats
  * that contains the vertex data for the mesh. It is used to store the positional
  * information for each vertex of the mesh.
  * 
  * @param { int[] } indices - The `indices` parameter specifies the index locations
  * of the vertices In the vertex array object (VAO)
  * 
  * @returns { Model } The function "load" returns a "Model" object that contains the
  * information of theVAO (Vertecle Array Object) and the count of vertices(v_count).
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
  * This function creates a new vertex array object (VAO) and returns its ID. It also
  * binds the VAO to the currently active context.
  * 
  * 
  * @returns { int } The output returned by this function is an `int` value that
  * represents the handle of a newly created Vertex Array Object (VAO) from the OpenGL
  * context. The function creates a VAO using the `glGenVertexArrays()` method and
  * then binds the created VAO to the current context using the `glBindVertexArray()`
  * method. Finally returns the handle of the bound VAO.
  */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
 /**
  * This function stores data from a float[] array into VBO (Verteix Buffer Object)
  * for later use with Vertex Attrib Pointers. It generates a VBO using glGenBuffers(),
  * binds it using glBindBuffer() and sets the data with glBufferData(). Then it sets
  * the vertex attrib pointers for the two float arrays using glVertexAttribPointer().
  * Finally it releases the VBO using glBindBuffer(0).
  * 
  * 
  * @param { int } attrib - The `attrib` parameter specifies the index of the vertex
  * attribute buffer to which the data should be bound. In this case it defines which
  * data (XYZ) is sent where within the rendering process. It effectively tells which
  * data sets should be sent where as a single integer value (ie 3 and 2).
  * 
  * @param { float[] } data - The `data` input parameter is an array of float values
  * that are used to fill the vertex buffer object (VBO) during rendering.
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
  * This function stores the contents of the `int[]` array `indices` into an OpenGL
  * buffer object (BO) for later use with glDrawElements.
  * 
  * 
  * @param { int[] } indices - The `indices` parameter is an array of integers that
  * represents the indices of the vertices of a primitive (e.g. a triangle) to be drawn
  * on the screen. In this function it is used to populate the element buffer object
  * (EBO) with the necessary data to render the primitive.
  */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
 /**
  * This function unbinds the current Vertex Array Object (VAO).
  */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}


