package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * Provides various utility methods for manipulating data types such as integers,
 * floats, and buffers. Methods include creating and manipulating buffers, removing
 * empty strings from an array, and converting arrays to int or float arrays.
 * Additionally, the class provides methods for flipping buffers and converting
 * matrices to floating-point values.
 */
public class Util {
	
	/**
	 * Creates a new `FloatBuffer` instance with the specified size. It uses the `BufferUtils`
	 * class to create the buffer, ensuring efficient memory management.
	 * 
	 * @param size amount of memory to allocate for the FloatBuffer.
	 * 
	 * @returns a `FloatBuffer` object with the specified size.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * Creates an `IntBuffer` object with the specified size. The returned buffer stores
	 * integer values and can be used for various operations such as data manipulation
	 * or transfer to other memory locations.
	 * 
	 * @param size number of integers that will be stored in the resulting `IntBuffer`.
	 * 
	 * @returns an instance of the `IntBuffer` class, representing a buffer of integers
	 * with the specified size.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * Returns a newly allocated `ByteBuffer` instance with the specified size.
	 * 
	 * @param size initial capacity of the ByteBuffer that will be created, which determines
	 * the amount of memory allocated for the buffer.
	 * 
	 * @returns a non-null ` ByteBuffer` instance of the specified size.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * Creates an `IntBuffer` object from an array of integers and flips it to have the
	 * lowest index at the beginning, allowing for efficient random access.
	 * 
	 * @returns an flipped IntBuffer containing the input values.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * Creates a new `FloatBuffer` instance, copies the provided `float` values to it,
	 * and then flips the buffer. It returns the flipped buffer.
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
	 * Removes empty strings from an array of strings and returns a new array with non-empty
	 * strings.
	 * 
	 * @param data 1D array of strings to be processed, which is used to store the result
	 * of the function after the filtering operation.
	 * 
	 * * Length: The array has `data.length` elements.
	 * * Content: Each element in the array is a string, which may be empty or non-empty.
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
	 * Converts an array of integers to an integer array, copying the elements of the
	 * input array into the output array without modifying their values.
	 * 
	 * @param data array of integers that will be converted to an integer array by the
	 * `toIntArray()` method.
	 * 
	 * @returns an integer array with the same length as the input `data` array, containing
	 * the same values.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Takes a list of integers and returns an integer array with the same size as the
	 * list, containing the corresponding integer values.
	 * 
	 * @param data list of integers that will be converted to an integer array by the
	 * `toIntArray()` method.
	 * 
	 * @returns an integer array containing the elements of the given list.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * Converts a `Float[]` array into a `float[]` array with the same length, copying
	 * each value from the input array to the output array.
	 * 
	 * @param data Float array that is to be converted into a float array.
	 * 
	 * @returns an array of `float` values, where each value corresponds to the original
	 * `Float` value.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of `Float` objects to an array of `Float` values, preserving the
	 * order and size of the original list.
	 * 
	 * @param data list of float values that will be converted into an array of floats
	 * by the function.
	 * 
	 * @returns an array of floating-point numbers representing the input list of floats.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
