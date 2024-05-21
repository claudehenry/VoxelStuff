package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * provides various methods for manipulating and transforming arrays of data types
 * such as integers, floats, and bytes. These methods include creating new buffers,
 * flipping buffers, and converting between different data types. Additionally, the
 * class provides a method for removing empty strings from an array.
 */
public class Util {
	
	/**
	 * creates a new `FloatBuffer` object with the specified size. The buffer is created
	 * using the `BufferUtils` class, which provides utility methods for working with buffers.
	 * 
	 * @param size number of elements to be stored in the resulting `FloatBuffer`.
	 * 
	 * @returns a `FloatBuffer` object with the specified size.
	 * 
	 * 	- The function returns a `FloatBuffer` object, which is a type-safe and efficient
	 * way to manipulate floating-point data in Java.
	 * 	- The `size` parameter passed to the function determines the number of elements
	 * in the buffer.
	 * 	- The buffer is created using the `BufferUtils` class, which provides utility
	 * methods for working with buffers in Java.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * creates an `IntBuffer` instance with the specified size, using the `BufferUtils`
	 * class to handle the buffer creation.
	 * 
	 * @param size capacity of the IntBuffer that will be created.
	 * 
	 * @returns an `IntBuffer` object representing a contiguous block of integers of the
	 * specified size.
	 * 
	 * 	- The function returns an `IntBuffer` object, which is a type-safe wrapper around
	 * a native `int[]` array.
	 * 	- The size parameter determines the capacity of the buffer, and it must be non-negative.
	 * 	- The buffer is created using the `BufferUtils` class, which provides a set of
	 * utility methods for working with buffers in a safe and efficient manner.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * creates a new ByteBuffer instance with a given size. The returned buffer can be
	 * used for reading and writing binary data.
	 * 
	 * @param size maximum capacity of the byte buffer to be created, which determines
	 * the number of bytes that can be stored in the buffer.
	 * 
	 * @returns a non-null, non-empty ByteBuffer object with the specified size.
	 * 
	 * 	- The input parameter `size` represents the capacity of the buffer, which is used
	 * to create a new instance of the `ByteBuffer` class.
	 * 	- The `ByteBuffer` instance returned by the function is immutable and thread-safe,
	 * making it suitable for use in various applications.
	 * 	- The buffer can be accessed and manipulated using various methods provided by
	 * the `ByteBuffer` class, such as `get`, `put', `mark', and `clear'.
	 * 	- The `createByteBuffer' function does not modify the input parameter, ensuring
	 * that the original size remains unchanged after the call.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * creates an `IntBuffer` object from a list of integers and flips it to create a
	 * view of the buffer with the elements in reverse order.
	 * 
	 * @returns an `IntBuffer` object that contains the input values in a flipped state.
	 * 
	 * 1/ The output is an `IntBuffer`, which means it is a view of a portion of the
	 * underlying memory as an array of integers.
	 * 2/ The buffer is created by copying the input array `values` into a new `IntBuffer`.
	 * 3/ The buffer is flipped using the `flip()` method, which makes all the elements
	 * of the buffer inaccessible for writing and accessible for reading.
	 * 
	 * Therefore, the output of the `createFlippedBuffer` function is an `IntBuffer` that
	 * contains a copy of the input array `values`, with the added property of being
	 * flipped for read-only access.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * creates a new FloatBuffer by copying a provided array of floating-point values
	 * into it, then flipping the buffer to enable random access to its elements.
	 * 
	 * @returns a flipped `FloatBuffer` containing the input values.
	 * 
	 * 	- The `FloatBuffer` object returned is created by calling the `createFloatBuffer`
	 * method with the length of the input array as an argument.
	 * 	- The `buffer.put(values)` method is called to store the input array values in
	 * the buffer.
	 * 	- The `buffer.flip()` method is called to flip the buffer, making its data available
	 * for reading from the beginning of the buffer.
	 * 
	 * The output of the `createFlippedBuffer` function is a `FloatBuffer` object that
	 * contains the input array values in a reversed order, starting from the end of the
	 * buffer and moving towards the beginning.
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
	 * removes empty strings from an array of strings, returning an array of non-empty strings.
	 * 
	 * @param data 0-length array of strings to be filtered, which is then processed by
	 * the function to produce an output array of non-empty string elements.
	 * 
	 * 	- `data` is an array of strings.
	 * 	- Its length is defined as `data.length`.
	 * 	- Each element in the array is a string object.
	 * 	- The elements may be empty strings, represented by an empty string value.
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
	 * converts an array of integers into an integer array with the same length. It does
	 * so by creating a new integer array and copying each element from the original array
	 * to the new one.
	 * 
	 * @param data Integer array that contains the values to be converted into an integer
	 * array.
	 * 
	 * 	- It is an instance of `Integer[]`.
	 * 	- Its length is specified by the function parameter `length`.
	 * 	- Each element in the array is a distinct integer value.
	 * 
	 * @returns an integer array with the same length as the input `data` array, containing
	 * the corresponding integers from the input array.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * converts a `List<Integer>` to an `int[]` array, copying the list's elements into
	 * the array.
	 * 
	 * @param data List of integers that are converted to an integer array by the
	 * `toIntArray()` method.
	 * 
	 * 	- `data`: A list of integers that is passed as input to the function.
	 * 	- `size()`: This method returns the number of elements in the list, which is used
	 * to create an array of the same size.
	 * 	- `get(int index)`: This method retrieves the value of a specific element in the
	 * list at the given index. The method throws `IndexOutOfBoundsException` if the index
	 * is out of range (i.e., negative or greater than the list's size).
	 * 
	 * @returns an integer array with the same size as the input list of integers.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * converts a `Float` array to a `float` array, copying the values of the original
	 * array to the new one.
	 * 
	 * @param data 1D array of `Float` values that are to be converted into a new 1D array
	 * of `float` values.
	 * 
	 * 	- `data`: The input parameter is an array of `Float`.
	 * 	- Length: The length of the `data` array matches the length of the returned
	 * `result` array.
	 * 	- Data elements: Each element in the `data` array is assigned to a corresponding
	 * position in the `result` array.
	 * 
	 * @returns an array of floating-point values, containing the same values as the input
	 * `data` array.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * takes a list of floating-point numbers and returns an array of the same size
	 * containing the corresponding float values.
	 * 
	 * @param data list of floating-point numbers that will be converted into an array
	 * of identical floats.
	 * 
	 * 	- The type of `data` is `List<Float>`, which means it's an array of floating-point
	 * numbers.
	 * 	- The size of `data` is equal to its number of elements, which can be accessed
	 * through the `size()` method.
	 * 	- Each element in `data` is a `Float` value, which is why the function uses a
	 * `for` loop to iterate over the elements and copy them into the resulting array.
	 * 
	 * @returns a floating-point array containing the values of the given list of floats.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
