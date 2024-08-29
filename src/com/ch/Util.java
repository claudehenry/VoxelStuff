package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * Provides utility functions for creating and manipulating buffers, arrays, and lists
 * of integers and floats. It also includes functions to remove empty strings from
 * an array and convert between different data types. The class is designed to assist
 * with various tasks in a game engine or similar software application.
 */
public class Util {
	
	/**
	 * Returns a new float buffer with the specified size, using the `BufferUtils` class
	 * to allocate memory for it. The function does not modify or process the input size
	 * value. It merely wraps the buffer returned by `BufferUtils.createFloatBuffer(size)`.
	 *
	 * @param size umber of floating-point values to be allocated and returned as a
	 * FloatBuffer object.
	 *
	 * @returns a FloatBuffer object of specified size.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * Creates a new int buffer with a specified size using the `BufferUtils` class, and
	 * returns it as an instance of `IntBuffer`. The function takes one parameter: the
	 * desired size of the buffer. It does not modify or process the buffer in any way.
	 *
	 * @param size umber of elements to be allocated in the returned IntBuffer, which is
	 * used by BufferUtils.createIntBuffer() to create an IntBuffer with the specified capacity.
	 *
	 * @returns an instance of `IntBuffer`.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * Allocates a new byte buffer with a specified size and returns it. The buffer is
	 * created using the `BufferUtils` class, which provides utility methods for working
	 * with buffers. The function takes an integer parameter representing the desired
	 * size of the buffer to be created.
	 *
	 * @param size umber of bytes that the returned `ByteBuffer` will be initialized to
	 * store.
	 *
	 * @returns a `ByteBuffer` object of specified size created using `BufferUtils`.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * Creates an IntBuffer with a specified number of values and fills it with the
	 * provided integers. It then flips the buffer, which marks it as read-only and sets
	 * its limit to its position. The resulting buffer is returned.
	 *
	 * @param values 0-based indices of the values to be stored in the IntBuffer, which
	 * are then put into the buffer by the `buffer.put(values)` method call.
	 *
	 * @returns an IntBuffer object with the given values.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * Creates a FloatBuffer from an array of float values, writes those values into the
	 * buffer, and then flips the buffer to prepare it for reading. The resulting buffer
	 * is ready for use as input or output to a stream or channel.
	 *
	 * @param values floating-point values to be stored in the FloatBuffer, which are
	 * then written into the buffer using the `put` method.
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
	 * Filters out empty strings from a given array of strings, retaining only non-empty
	 * strings. The filtered result is then converted back to an array and returned.
	 *
	 * @param data 2D array of strings to be processed by removing any empty strings from
	 * it.
	 *
	 * Array of strings contains elements of varying lengths. Elements can be either empty
	 * or non-empty strings. The order and frequency of elements in the array are preserved.
	 *
	 * @returns an array of non-empty strings from the input data.
	 *
	 * Returns an array of non-empty strings from the input array, in the same order as
	 * they appeared in the input array. The returned array is an immutable object with
	 * a fixed size equal to the number of non-empty strings in the input array.
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
	 * Converts an array of integers wrapped in `Integer` objects to a primitive integer
	 * array. It initializes a new integer array with the same length as the input array
	 * and then copies each element from the input array into the result array.
	 *
	 * @param data 2D array of integers that needs to be converted into an array of
	 * primitive integers and returned by the method.
	 *
	 * @returns an integer array containing the same elements as the input integer array.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of integers into an integer array. It iterates over the input list,
	 * assigning each element to its corresponding index in the output array. The resulting
	 * array is then returned.
	 *
	 * @param data List of Integer objects to be converted into an integer array, which
	 * is then processed and returned by the method.
	 *
	 * @returns an integer array containing elements from the input list.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * Converts a given array of Float objects to an array of primitive float values,
	 * maintaining the original length and order of elements from the input array.
	 *
	 * @param data 2D array of Float objects that needs to be converted into a 1D array
	 * of primitive float values.
	 *
	 * @returns a new float array containing identical elements to the input Float array.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of Float objects to a float array. It iterates through the input
	 * list, assigning each Float object's value to the corresponding index in the output
	 * array. The resulting float array is then returned.
	 *
	 * @param data List of Float values to be converted into an array of float type.
	 *
	 * @returns an array of floating-point numbers.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
