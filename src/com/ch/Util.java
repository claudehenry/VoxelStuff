package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class Util {
	
	/**
	 * This function creates a Float Buffer of a specific size using the BufferUtils class.
	 * 
	 * @param size The `size` parameter is used to specify the number of floating-point
	 * elements that the created buffer will hold. It determines the capacity of the buffer.
	 * 
	 * @returns Based on the code snippet provided above:
	 * 
	 * The output of this function will be a FloatBuffer object that holds an array of
	 * floating-point numbers with size bytes (or whatever the size argument passed into
	 * the method is). To be more explicit about BufferUtil.createFloatBuffer (), which
	 * is called inside this function to create a new float buffer.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * This function creates an integer buffer of a specified size using the Buffers
	 * utility class's createIntBuffer method.
	 * 
	 * @param size The `size` parameter specifies the number of elements that the created
	 * `IntBuffer` should be able to hold.
	 * 
	 * @returns Based on the information provided about the function `createIntBuffer`,
	 * it appears to be a factory method that creates an instance of the `IntBuffer` class
	 * with a specified size. The function call `BufferUtils.createIntBuffer(size)` is
	 * used to create the `IntBuffer`.
	 * The output returned by this function is therefore an `IntBuffer` object with the
	 * specified size.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * This function creates a ByteBuffer of a specific size using the BufferUtils class.
	 * 
	 * @param size The input parameter `size` represents the initial capacity of the
	 * created `ByteBuffer`, and it is used to determine how much memory to allocate for
	 * the buffer when it's created.
	 * 
	 * @returns The `createByteBuffer()` method takes an integer `size` as input and
	 * returns a `ByteBuffer` object that can hold bytes up to that size. The return type
	 * is `BufferUtils.createByteBuffer(size)`, which is a static method that creates a
	 * new byte buffer of the specified size using the `BufferUtil` class.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * The provided function takes an array of integers as input and returns an Intel
	 * Buffer with the same content but flipped.
	 * 
	 * @param values The values input parameter provides an array of integers that are
	 * placed into the IntBuffer object created by the function.
	 * 
	 * @returns The function `createFlippedBuffer` creates an integer buffer using an
	 * array of integers as its initial contents and returns it as a flipped buffer. 
	 * Therefore the output is an instance of `IntBuffer`.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * This function creates a float buffer flipped and returns it to the user after
	 * putting input data inside.
	 * 
	 * @param values The input parameter values is an array of float values that will be
	 * put into the buffer.
	 * 
	 * @returns The `createFlippedBuffer()` function returns a floating-point Buffer that
	 * has been flipped to become viewable as a double-based array with values correlating
	 * to its capacity instead of its position within the buffer. Essentially an array-like
	 * object populated with data drawn from the passed float array but arranged to
	 * maintain natural alignment between successive floats rather than being packed
	 * densely to utilize minimum memory space.
	 */
	public static FloatBuffer createFlippedBuffer(float... values) {
		FloatBuffer buffer = createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}

	/*
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

		for (int i = 0; i < vertices.length; i++) {
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
			buffer.put(vertices[i].getTexCoord().getX());
			buffer.put(vertices[i].getTexCoord().getY());
			buffer.put(vertices[i].getNormal().getX());
			buffer.put(vertices[i].getNormal().getY());
			buffer.put(vertices[i].getNormal().getZ());
			buffer.put(vertices[i].getTangent().getX());
			buffer.put(vertices[i].getTangent().getY());
			buffer.put(vertices[i].getTangent().getZ());
		}

		buffer.flip();

		return buffer;
	}
	*/

	/*
	public static FloatBuffer createFlippedBuffer(Matrix4f value) {
		FloatBuffer buffer = createFloatBuffer(4 * 4);

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				buffer.put(value.get(i, j));

		buffer.flip();

		return buffer;
	}
	
	public static Matrix4f loatMat4(FloatBuffer vals) {
		
//		vals.flip();
		
		Matrix4f m = new Matrix4f();
		
		int index;
		for (index = 0; index < 16; index++)
			m.set(index % 4, index / 4, vals.get());
		
		return m;
	}
*/
 /**
  * This function removes empty strings from an input array of strings and returns a
  * new array containing only the non-empty strings.
  * 
  * @param data The `data` input parameter is an array of strings that is passed to
  * the function for processing.
  * 
  * @returns This function takes an input array of strings and removes any empty strings
  * from it. The output is a new array of strings that contains only the non-empty
  * strings from the input array.
  * 
  * The function works as follows:
  * 
  * 1/ It creates an empty list (ArrayList) to store the non-empty strings.
  * 2/ It loops through the input array and checks each string. If the string is not
  * empty (i.e., != ""), it adds it to the list.
  * 3/ After all the strings have been processed. The function creates a new array of
  * the same size as the list (result) and copies the contents of the list into the
  * new array.
  * 4/ The function returns the new array of non-empty strings.
  */
	public static String[] removeEmptyStrings(String[] data) {
		ArrayList<String> result = new ArrayList<String>();

		for (int i = 0; i < data.length; i++)
			if (!data[i].equals(""))
				result.add(data[i]);

		String[] res = new String[result.size()];
		result.toArray(res);

		return res;
	}

 /**
  * This function takes an array of integers as input and returns a new integer array
  * containing the same elements as the input array.
  * 
  * @param data The `data` input parameter is an array of integers that is being passed
  * to the function. The function returns an array of integers after performing a copy
  * of the content of the `data` array into a new integer array.
  * 
  * @returns This function takes an integer array `data` as input and returns an integer
  * array `result` of the same length as `data`. The elements of `result` are copied
  * from `data` with the same indices; that is to say the first element of `result`
  * corresponds to the first element of `data`, the second element of `result` corresponds
  * to the second element of `data`, and so forth.
  * 
  * In other words: The function returns a new integer array containing the same values
  * as the input array.
  */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * This function takes a list of integers as input and returns an integer array
  * containing all the elements of the input list.
  * 
  * @param data The `data` input parameter is a `List<Integer>` that contains the
  * integers to be converted into an `int[]`.
  * 
  * @returns This function takes a `List<Integer>` as input and returns an `int[]`
  * array containing all the elements of the list. In other words: it converts a list
  * of integers into an integer array.
  */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
 /**
  * This function takes an array of float values as input and returns a new float array
  * containing the same values.
  * 
  * @param data The `data` input parameter is an array of floating-point numbers that
  * is being transformed into a new array of the same length containing only the
  * primitive float values.
  * 
  * @returns The output returned by this function is an array of float values that
  * represents the elements of the input Float array. The function takes a Float array
  * as input and returns a new float array containing the same elements as the input
  * array.
  */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * This function takes a List<Float> input and returns a float[] output. It converts
  * the contents of the List to a floating-point array.
  * 
  * @param data The `data` input parameter is a List of Floats that the function
  * operates on to create a new float array.
  * 
  * @returns The function `toFloatArray` takes a list of float values as input and
  * returns a float array of the same size. Each element of the array is set to the
  * corresponding value from the input list. In other words:
  * 
  * Output: A new float array containing the same elements as the input list.
  */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
