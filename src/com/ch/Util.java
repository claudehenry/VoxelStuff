package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class Util {
	
	/**
	 * This function creates a new instance of the FloatBuffer class and returns it to
	 * the caller with the given size parameter that defines how many memory locations
	 * are requested.
	 * 
	 * @param size The size parameter is the total number of floating-point numbers to
	 * be stored within the newly created float buffer object.
	 * 
	 * @returns The function "createFloatBuffer" returns a floating point buffer object
	 * using the BufferUtils class. The object will contain an array of float values and
	 * has size as the number of elements it can hold.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * This function creates an integer buffer of the specified size using the BufferUtils
	 * class.
	 * 
	 * @param size The `size` input parameter specifies the size of the int buffer to be
	 * created. It determines the number of elements the buffer can hold.
	 * 
	 * @returns This function called `createIntBuffer` has a return type of `public static
	 * IntBuffer` and takes an integer parameter called `size`. The function returns an
	 * instance of `IntBuffer`.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * The provided function "createByteBuffer" creates a byte buffer of a given size
	 * using the method "createByteBuffer" of the class "BufferUtils".
	 * 
	 * @param size The size parameter determines how many bytes of memory are allotted
	 * for the resulting ByteBuffer.
	 * 
	 * @returns Based on the provided code snippet:
	 * 
	 * The output of `createByteBuffer(size)` is a newly created `ByteBuffer` object with
	 * the specified `size`. The function calls `BufferUtils.createByteBuffer(size)` to
	 * create the `ByteBuffer`, and returns it. Therefore the output is a `ByteBuffer`
	 * object with the specified size.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

 /**
  * This function creates an int buffer from a varargs parameter `values`, puts the
  * values into the buffer and flips the buffer. Then returns the flipped buffer.
  * 
  * @param values The `values` input parameter is an array of integers that is put
  * into the newly created `IntBuffer` instance.
  * 
  * @returns The function `createFlippedBuffer()` takes an array of integers as input
  * and returns an int buffer that contains the elements of the input array reversed
  * (i.e., flipped).
  */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
 /**
  * This function creates a FloatBuffer and fills it with the provided float values.
  * Then it flips the buffer to make it ready for drawing.
  * 
  * @param values The `values` parameter is an array of floats that is passed to the
  * `put()` method of the `FloatBuffer` object to fill it with data.
  * 
  * @returns The output returned by this function is a FloatBuffer object that represents
  * the provided array of floats with the components reversed (i.e., the elements are
  * arranged from back to front). The function creates a new FloatBuffer instance and
  * puts the contents of the input array into it. Then it flips the buffer to make the
  * data ready for use.
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
