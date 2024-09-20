package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * Provides utility methods for creating various types of buffers and converting
 * between data structures such as arrays and lists to primitive arrays. It includes
 * functionality for handling FloatBuffers, IntBuffers, ByteBuffers, and string arrays.
 * The class also contains helper functions for array conversions.
 */
public class Util {
	
	/**
	 * Creates a FloatBuffer with a specified size, returning it as an instance variable.
	 * It utilizes the BufferUtils class to achieve this creation. The buffer's capacity
	 * is set based on the provided size parameter.
	 *
	 * @param size umber of elements to be stored in the float buffer.
	 *
	 * @returns a `FloatBuffer` object representing a new buffer of specified size.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * Returns an instance of `IntBuffer`, a buffer containing integer values, initialized
	 * with a specified size. The size is used to allocate memory for the buffer. The
	 * actual creation of the buffer is delegated to an external utility class `BufferUtils`.
	 *
	 * @param size umber of integers that the created buffer will be able to hold.
	 *
	 * @returns an instance of `IntBuffer`.
	 * It represents a mutable buffer containing integers.
	 * Its capacity is set to the specified size.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * Allocates a new buffer with the specified size. It utilizes an external utility
	 * class, `BufferUtils`, to perform the allocation. The allocated buffer is then
	 * returned as a `ByteBuffer`.
	 *
	 * @param size number of bytes to allocate in the ByteBuffer created by the
	 * `BufferUtils.createByteBuffer()` method.
	 *
	 * @returns a pre-allocated ByteBuffer instance with specified capacity.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * Creates a new IntBuffer from an array of integers, and then flips it to make the
	 * content accessible from both ends. The flipped buffer is returned for further
	 * processing. The original array's values are written into the newly created IntBuffer.
	 *
	 * @param values 0-based array of integer values to be stored in an IntBuffer.
	 *
	 * @returns a flipped IntBuffer object containing input values.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * Creates a new FloatBuffer from an array of float values and flips its position to
	 * start reading from the end of the buffer, leaving it ready for relative put
	 * operations. It returns the new FloatBuffer. The buffer's capacity is determined
	 * by its argument length.
	 *
	 * @param values 3... array of float values to be stored in the returned FloatBuffer
	 * object.
	 *
	 * @returns a FloatBuffer instance with its data flipped.
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
	 * Removes empty strings from a given array of strings, returning a new string array
	 * containing only non-empty strings. It iterates through the input array, adds valid
	 * strings to an internal list, and then converts this list back into a string array
	 * for return.
	 *
	 * @param data 1D array of strings from which empty string elements are to be removed
	 * and returned as a new array.
	 *
	 * Exist, type: String array.
	 * Elements of each index represent individual strings.
	 *
	 * @returns an array of non-empty strings.
	 *
	 * The output is an array of non-empty strings.
	 * It retains the original order of elements from the input array.
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
	 * Converts an array of Integer objects into a primitive int array by copying each
	 * element from the input array to the output array. The new array has the same length
	 * as the input array. Each integer value is directly assigned to its corresponding
	 * index in the result.
	 *
	 * @param data 1D array of Integer objects to be converted into an equivalent 1D array
	 * of primitive integers.
	 *
	 * @returns an array of integers equivalent to the input Integer array.
	 * It contains the same elements as the input array but converted from Integer objects
	 * to primitive int values.
	 * Result is a new integer array with same size and content as the input data.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of integers into an array of integers. It takes a list as input
	 * and returns a new integer array where each element is copied from the corresponding
	 * index in the input list. The original list remains unchanged.
	 *
	 * @param data collection of integers to be converted into an integer array, and its
	 * size determines the length of the resulting array.
	 *
	 * @returns an array of integers from the input list. The array contains each element
	 * of the original list as an integer.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * Converts a Float array into a float array. It creates a new float array with the
	 * same length as the input array and copies each element from the input array to the
	 * corresponding position in the result array. The resulting float array is then returned.
	 *
	 * @param data 1D array of Float objects to be converted into an equivalent float array.
	 *
	 * @returns a new float array containing the input Float[] values.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of Float objects to a float array. It iterates over the list,
	 * assigning each element to a corresponding index in the result array. The resulting
	 * array preserves the original elements and order of the input list.
	 *
	 * @param data collection of floating-point numbers to be converted into an array,
	 * passed as a List of Float objects.
	 *
	 * @returns a float array containing the input list's elements.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
