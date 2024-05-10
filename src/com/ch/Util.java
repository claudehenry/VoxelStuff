package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

public class Util {
	/**
	 * Creates a new FloatBuffer with the specified size using the `BufferUtils` class.
	 * 
	 * @param size number of elements to be stored in the `FloatBuffer`.
	 * 
	 * @returns a `FloatBuffer` object of the specified size.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * Creates an `IntBuffer` object with the specified size.
	 * 
	 * @param size number of elements to be stored in the `IntBuffer`.
	 * 
	 * @returns an `IntBuffer` instance representing a buffer of integers with the specified
	 * size.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * Creates a new instance of a `ByteBuffer`.
	 * 
	 * @param size integer amount of memory to be allocated for the ByteBuffer.
	 * 
	 * @returns a byte buffer instance created from a specified size using `BufferUtils`.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * Creates an `IntBuffer` instance containing the specified `int` values and flips
	 * it for processing.
	 * 
	 * @param values 1D array of integers that will be converted into an IntBuffer by the
	 * `createFlippedBuffer()` method.
	 * 
	 * @returns an IntBuffer containing the input values in a flipped state.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * Creates a new `FloatBuffer` object from an array of floating-point values, stores
	 * the values in the buffer, and flips the buffer for efficient reading.
	 * 
	 * @param values floating-point values to be stored in the created `FloatBuffer`.
	 * 
	 * @returns a flipped FloatBuffer containing the input values.
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
	 * Removes empty strings from an array of strings and returns a new array with the
	 * non-empty strings.
	 * 
	 * @param data 0-length array of strings that is to be filtered for empty strings,
	 * with the resulting non-empty strings stored in a new array returned by the function.
	 * 
	 * @returns an array of non-empty strings.
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
	 * Converts an `Integer[]` input to an `int[]` output, copying the values of the input
	 * array to the output array.
	 * 
	 * @param data 1D array of integers that is converted to an int array by the
	 * `toIntArray()` method.
	 * 
	 * @returns an integer array containing the same values as the input array of integers.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a `List<Integer>` to an `int[]` array, by using a `for` loop to iterate
	 * over the list and assign each element to a corresponding index in the array.
	 * 
	 * @param data list of integers that will be converted to an integer array by the
	 * `toIntArray()` method.
	 * 
	 * @returns an integer array containing the elements of the provided list.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * Creates a new array of floats containing the same values as the given Float array.
	 * 
	 * @param data 0-based integer array of floating-point values to be converted into a
	 * corresponding 0-based float array.
	 * 
	 * @returns an array of float values, containing the same values as the input `data`
	 * array.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a `List<Float>` data collection into an array of floating-point values,
	 * by utilizing its `size()` and `get()` methods to iterate through the list and
	 * assign each element to its corresponding position in the array.
	 * 
	 * @param data list of floating-point numbers that are converted into an array of the
	 * same size.
	 * 
	 * @returns an array of `float` values representing the input list of `Float` values.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
