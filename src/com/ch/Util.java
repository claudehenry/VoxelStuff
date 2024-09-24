package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * Provides utility functions for creating and manipulating various types of buffers
 * and arrays, including FloatBuffer, IntBuffer, ByteBuffer, and primitive type arrays.
 * It also includes methods for filtering empty strings and converting between array
 * and list data structures. The class is designed to facilitate efficient memory
 * allocation and data manipulation in applications using the LWJGL library.
 */
public class Util {
	
	/**
	 * Creates a new float buffer with a specified capacity and returns it as a FloatBuffer
	 * object. The buffer is created using an unspecified helper utility, BufferUtils.
	 * The size parameter determines the initial capacity of the buffer.
	 *
	 * @param size umber of floating-point values that the returned buffer will be able
	 * to hold.
	 *
	 * @returns a FloatBuffer instance of specified capacity.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * Returns a new instance of an IntBuffer, which is a buffer of int values, created
	 * with the specified size using BufferUtils.createIntBuffer(size). The returned
	 * buffer can be used to store and manipulate integer data. It has a fixed capacity
	 * equal to the specified size.
	 *
	 * @param size umber of integers that will be stored in the buffer, which is used to
	 * create an IntBuffer instance with the specified capacity.
	 *
	 * @returns an instance of a dynamically resizing int buffer.
	 * The buffer has a specified initial capacity and will automatically expand as
	 * necessary to accommodate data.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * Allocates a new ByteBuffer instance. The ByteBuffer is created with a specified
	 * size, determined by the input parameter. The function returns the newly allocated
	 * ByteBuffer object.
	 *
	 * @param size umber of bytes to be allocated for the buffer, specifying its capacity.
	 *
	 * @returns a non-null instance of `ByteBuffer`.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * Creates a new IntBuffer from an array of integers and flips its position to mark
	 * it as ready for reading. The function takes a variable number of integer values,
	 * creates a buffer with sufficient capacity, copies the values into the buffer, and
	 * returns the flipped buffer.
	 *
	 * @param values 0-based array of integer values to be stored in the created IntBuffer,
	 * and its length is used to determine the initial capacity of the buffer.
	 *
	 * @returns a flipped IntBuffer containing the input values.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * Creates a FloatBuffer containing the specified float values and flips its position
	 * to allow for reading data from the buffer instead of writing. It takes variable
	 * number of floats as an argument and returns a flipped FloatBuffer instance. The
	 * buffer is created with the length equal to the number of provided values.
	 *
	 * @param values floating-point numbers to be stored in the returned `FloatBuffer`.
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
	 * Iterates over an input array, copying non-empty strings to a result list, then
	 * converts the list to an array and returns it. It removes all empty string elements
	 * from the original array.
	 *
	 * @param data 2D array of strings from which empty strings are to be removed and its
	 * non-empty elements returned as an array.
	 *
	 * Declared as an array of String type, it represents a collection of strings. It has
	 * a fixed length that is determined during its declaration or initialization. Its
	 * elements can be accessed using their index within square brackets.
	 *
	 * @returns an array of non-empty strings from the input array.
	 *
	 * The result is an array of non-empty strings. The array contains elements from the
	 * input array with empty strings filtered out. It preserves the original order and
	 * duplicates of the input elements.
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
	 * Converts an array of Integer objects to a primitive int array, preserving original
	 * values and length. It iterates over each element in the input array, assigning its
	 * value to the corresponding index in the result array. The resulting int array is
	 * returned as output.
	 *
	 * @param data 1-based array of Integer objects to be converted into an equivalent
	 * 0-based int array.
	 *
	 * @returns an array of integers.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a List of Integer objects into an integer array. It iterates over the
	 * list elements, assigning each value to the corresponding index in the result array.
	 * The resulting array is returned with the same size as the input list.
	 *
	 * @param data List of Integer values to be converted into an array.
	 *
	 * @returns an array of integers corresponding to the input list's elements.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * Converts a Float array to a float array, copying all elements from the original
	 * array into a new one with the same length. The source array is not modified during
	 * this process. The result is returned as an instance of a newly created float array.
	 *
	 * @param data 1-dimensional array of Float objects to be converted into an array of
	 * primitive float values.
	 *
	 * @returns an array of floats, identical to input.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * Converts a list of Float objects into an array of float values, allowing for easier
	 * numerical operations. It iterates through each element in the input list, assigning
	 * its value to the corresponding index in the result array. This enables efficient
	 * data processing and manipulation.
	 *
	 * @param data list of Float objects to be converted into an array of floats.
	 *
	 * @returns an array of float values corresponding to input list's elements.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
