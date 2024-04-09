package com.ch;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * in the provided code is used to manage vertex attributes and indices for rendering
 * 3D models. It provides methods to enable and disable vertex attribs, bind and
 * unbind vertex arrays, and load data from arrays. The class also provides a method
 * to get the VAO and size of the model.
 */
public class Model {

	private int vao, size;
	
	public Model(int vao, int count) {
		this.vao = vao;
		this.size = count;
	}
	
 /**
  * binds a vertex array object (VAO), enables vertex attrib arrays for position and
  * texture coord, and renders triangles using `glDrawElements`.
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
  * enables vertex attribute arrays for two attributes (vertex position and color)
  * using the `glEnableVertexAttribArray` method from OpenGL API.
  */
	public static void enableAttribs() {
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
 /**
  * disables both vertex attributes in a shader program using `glDisableVertexAttribArray`.
  */
	public static void disableAttribs() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
 /**
  * returns the value of `vao`.
  * 
  * @returns an integer value representing the VAO.
  */
	public int getVAO() {
		return vao;
	}
	
 /**
  * returns the current size of an object's storage.
  * 
  * @returns the value of `size`.
  */
	public int getSize() {
		return size;
	}
	
 /**
  * loads data into a model. It creates a Vertex Array Object (VAO) and stores indices
  * and vertices data. It then unbinds the VAO, returns the model instance and the
  * number of vertices stored.
  * 
  * @param vertices 3D model's geometry data, consisting of a floating-point array of
  * vertices that define the model's shape.
  * 
  * 	- `vertices` is an array of floating-point numbers representing 3D vertices.
  * 	- The length of the `vertices` array is determined by the number of indices stored
  * in `indices`.
  * 	- Each element in `vertices` represents a 3D vertex with three components (x, y,
  * z) representing its position in space.
  * 
  * @param indices 3D model's vertex index array, which is used to store the indices
  * of the vertices that make up the model.
  * 
  * 	- `indices` is an array of integers that represents the index of each vertex in
  * the model.
  * 	- The length of `indices` is equal to the number of vertices in the model, which
  * is determined by the size of the `vertices` array passed as an argument.
  * 	- Each element of `indices` corresponds to a unique vertex in the model, with the
  * first vertex having index 0 and the last vertex having index `v_count-1`.
  * 
  * @returns a `Model` object representing the 3D model.
  * 
  * 	- The returned output is of type `Model`.
  * 	- The `vao` field contains the value of an integer that represents the vertex
  * array object (VAO) created by the function.
  * 	- The `v_count` field contains the value of an integer that represents the number
  * of vertices stored in the VAO.
  * 
  * The output is a combination of a VAO and the number of vertices it contains, which
  * are used to render 3D objects in a graphics pipeline.
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
  * creates a vertex array object (VBO) using the `glGenVertexArrays` method and binds
  * it with `glBindVertexArray`. The created VBO is returned as an integer value.
  * 
  * @returns an integer value representing the newly created vertex array object (VAO).
  */
	private static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
 /**
  * genrates a vertex buffer object (VBO) and stores float data in it using `glBufferData`.
  * It also sets vertex attributepoints for the data using `glVertexAttribPointer`.
  * 
  * @param attrib 2D vertex attribute index that contains the data to be stored in the
  * VBO.
  * 
  * @param data 3D data to be stored in the VBO buffer.
  * 
  * 	- `data` is an array of `float` type.
  * 	- The length of the array is determined by the value of `Util.createFlippedBuffer(data)`,
  * which is a method that creates a buffer object from the input data.
  * 	- The buffer is bound to the vertex attribute pointer using `GL20.glVertexAttribPointer()`.
  * 	- Two vertex attributes are defined in the function, one with 3 float values and
  * another with 2 float values.
  * 	- The first vertex attribute is defined as `GL11.GL_FLOAT`, indicating that the
  * data should be stored as floating-point numbers.
  * 	- The second vertex attribute is defined as `GL11.GL_FLOAT`, indicating that the
  * data should be stored as floating-point numbers.
  * 	- The `false` value for the `glBufferData()` method indicates that the buffer
  * data should be stored in a static draw mode, meaning that it will be stored
  * permanently in the GPU memory.
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
  * generates a new buffer object and binds it to an element array buffer slot, then
  * stores the provided integer indices in the buffer using the `GLBufferData` method
  * with `GL_STATIC_DRAW`.
  * 
  * @param indices 3D vertex indices of a geometric shape to be stored in an element
  * array buffer for further rendering operations.
  * 
  * 	- `indices` is an array of integers that represents the indices of vertices in a
  * 3D model.
  * 	- The size of the array is determined by the number of vertices in the model.
  * 	- Each element of the array corresponds to a vertex in the model, with each vertex
  * having an index assigned to it.
  * 	- The values stored in the `indices` array are unique and non-negative, representing
  * the order in which the vertices were defined in the 3D model.
  */
	private static void storeIndices(int[] indices) {
		int ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL15.GL_STATIC_DRAW);
	}
	
 /**
  * binds a vertex array object (VAO) to a specific context, disabling its association
  * with any vertex buffer objects (VBOs) or element arrays (EBOs).
  */
	private static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
}
