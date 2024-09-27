package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * Provides utility methods for data conversion and buffer creation, enabling efficient
 * handling of arrays and collections.
 */
public class Util {
	/**
	 * Allocates a new FloatBuffer of a specified size, utilizing the BufferUtils class.
	 *
	 * @param size number of float values the buffer will hold.
	 *
	 * @returns a FloatBuffer object, a mutable buffer of floats with the specified size.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * Creates an IntBuffer of a specified size. The size parameter determines the number
	 * of integer values the buffer can hold. The function returns a new IntBuffer instance.
	 *
	 * @param size number of integer values the buffer will be able to hold.
	 *
	 * @returns a new IntBuffer object with the specified size.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * Allocates a ByteBuffer of a specified size using the provided BufferUtils class,
	 * enabling memory management for storing binary data.
	 *
	 * @param size number of bytes to allocate in the ByteBuffer.
	 *
	 * @returns a ByteBuffer object of specified size.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * Creates a new `IntBuffer` instance from the given `values` array, flips its position
	 * to the end, and returns it. The buffer's capacity is set to the length of the input
	 * array, and its values are populated using the `put` method.
	 *
	 * @param values array of integer values to be stored in the buffer.
	 *
	 * @returns an IntBuffer with its position and limit switched, ready for reading.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * Creates a new FloatBuffer instance, populates it with the given float values, and
	 * flips the buffer to a read-only state, making it ready for sequential access.
	 *
	 * @param values array of float values to be stored in the FloatBuffer.
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
	 * Eliminates empty strings from the input array and returns a new array containing
	 * the non-empty strings.
	 *
	 * @param data array of strings from which empty strings are to be removed.
	 *
	 * Array of strings.
	 *
	 * @returns an array of non-empty strings from the input array.
	 *
	 * Result is an array of non-empty strings.
	 * The array contains elements from the input array with empty strings removed.
	 * The array has the same order as the input array.
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
	 * Converts an array of Integer objects to an array of primitive int values, copying
	 * the contents of the input array to the result array.
	 *
	 * @param data array of Integer objects to be converted into an array of primitive integers.
	 *
	 * @returns an array of integers containing the values from the input array.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a given list of integers into an integer array, preserving the original
	 * order and values of the elements. The function iterates over the list, assigning
	 * each element to the corresponding index in the newly created array. The resulting
	 * array is then returned.
	 *
	 * @param data list of integers to be converted into an array.
	 *
	 * @returns an array of integers corresponding to the input list.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * Converts a given array of `Float` objects to an array of primitive `float` values,
	 * returning the resulting array.
	 *
	 * @param data array of Float objects to be converted into a float array.
	 *
	 * @returns a copy of the input `data` array, converted to `float` type.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of Float objects to a float array, copying the elements from the
	 * list to the array. The array size is determined by the list size. The function
	 * returns the resulting float array.
	 *
	 * @param data list of float values to be converted into a float array.
	 *
	 * @returns an array of float values representing the input data.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
