package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * Provides various utility functions for memory management and array manipulation.
 * It offers creation of FloatBuffer, IntBuffer, and ByteBuffer objects with specified
 * sizes. Additionally, it includes methods for flipping buffers, converting arrays
 * to integer or float arrays, and removing empty strings from an array.
 */
public class Util {
	
	/**
	 * Creates a new `FloatBuffer` instance with the specified `size`. It uses the
	 * `BufferUtils` class to allocate and initialize the buffer. The returned buffer is
	 * not initialized or filled with data, allowing the caller to use it as needed.
	 *
	 * @param size capacity of the float buffer to be created, determining how many
	 * floating-point values it can hold.
	 *
	 * @returns a `FloatBuffer` object of the specified size.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * Creates a new `IntBuffer` with the specified `size`. It utilizes the `BufferUtils`
	 * class to generate an `IntBuffer` instance, which is then returned by the function.
	 * The created buffer can be used for storing and manipulating integer values.
	 *
	 * @param size umber of integer values that will be stored in the buffer, which is
	 * used to create an IntBuffer with the specified size.
	 *
	 * @returns a new `IntBuffer` object of specified size.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * Allocates a new direct buffer with the specified size. It returns a direct
	 * `ByteBuffer` instance that can be used to access the allocated memory. The allocated
	 * buffer is initialized to zero and ready for use.
	 *
	 * @param size umber of bytes allocated for the returned `ByteBuffer`.
	 *
	 * @returns a `ByteBuffer` object of a specified size.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * Creates an `IntBuffer` from an array of integers, fills it with the provided values,
	 * and then flips its position to prepare for reading. The resulting buffer is returned.
	 *
	 * @param values 0-based index values to be stored in the created IntBuffer.
	 *
	 * @returns a flipped `IntBuffer` containing the input values.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * Creates a FloatBuffer from an array of float values and flips its content. It takes
	 * a variable number of float arguments and uses them to initialize the buffer. The
	 * buffer is then returned in a flipped state, allowing for both reading and writing
	 * operations.
	 *
	 * @param values 0-based array of floating-point numbers to be written into the
	 * FloatBuffer, which is then flipped and returned.
	 *
	 * @returns a flipped FloatBuffer containing the input float values.
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
	 * Filters out empty strings from an input array and returns a new string array
	 * containing only non-empty strings. It iterates over the input array, adding non-empty
	 * strings to an ArrayList, then converts the ArrayList to a string array for return.
	 *
	 * @param data 2D array of strings that needs to be filtered to remove empty strings
	 * and converted back into an array.
	 *
	 * Data is an array of Strings, with each String potentially being empty or non-empty.
	 * The size of this array is dynamic and can vary based on the number of elements in
	 * it.
	 *
	 * @returns an array of non-empty strings.
	 *
	 * It is an array of strings where all empty string elements from the input array
	 * have been removed. The resulting array contains only non-empty strings. Its length
	 * depends on the number of non-empty strings in the input array.
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
	 * Converts an array of Integer objects to an array of integer primitives. It creates
	 * a new int array with the same length as the input array, then iterates over the
	 * input array and assigns each element to its corresponding position in the output
	 * array.
	 *
	 * @param data 2D array of integers to be converted into an integer array, which is
	 * then returned by the function.
	 *
	 * @returns an integer array containing elements from the input Integer array.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a given list of integers into an integer array. It creates a new integer
	 * array with size equal to the input list, then iterates over the list and assigns
	 * each element to the corresponding position in the array.
	 *
	 * @param data List of Integer objects that is being converted into an integer array.
	 *
	 * @returns an integer array containing the elements of the input list.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * Converts a Float array into a float array, duplicating its contents. It iterates
	 * through each element of the input array and assigns it to the corresponding index
	 * in the output array, preserving the original data's order and values.
	 *
	 * @param data 2D array of Float objects that is to be converted into a 1D array of
	 * primitive float values.
	 *
	 * @returns a new float array containing the elements of the input Float array.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of Float values into a float array, preserving the original order
	 * and size of the input list. The resulting array contains each element from the
	 * input list as a float value. The function returns the newly created float array.
	 *
	 * @param data List of Float objects to be converted into an array of floats.
	 *
	 * @returns an array of floats.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
