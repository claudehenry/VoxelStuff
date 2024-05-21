package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * is a collection of methods for manipulating and buffering data in various formats,
 * including integers, floats, bytes, and matrices. These methods include creating
 * and manipulating buffers, as well as removing empty strings from an array.
 */
public class Util {
	
	/**
	 * creates a new `FloatBuffer` object with the specified size, using the `BufferUtils`
	 * class to perform the creation.
	 * 
	 * @param size amount of memory required to store the float data in the buffer.
	 * 
	 * @returns a `FloatBuffer` object that represents a contiguous block of floating-point
	 * data with a specified size.
	 * 
	 * 	- The output is an instance of `FloatBuffer`, which is a class in Java for managing
	 * a buffer of floating-point numbers.
	 * 	- The `size` parameter passed to the function determines the number of elements
	 * in the buffer.
	 * 	- The buffer is created using the `BufferUtils` class, which provides utility
	 * methods for working with buffers in Java.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * creates an `IntBuffer` instance with a specified size. The returned buffer contains
	 * integers that can be accessed and manipulated using standard buffer operations.
	 * 
	 * @param size buffer capacity that is required for the IntBuffer creation.
	 * 
	 * @returns an `IntBuffer` instance that can be used to store and manipulate integer
	 * values.
	 * 
	 * The returned IntBuffer is a direct view of the underlying memory, which means that
	 * any changes made to the buffer will affect the original data as well.
	 * The buffer's position is 0-based, meaning that the first element in the buffer is
	 * at position 0.
	 * The buffer's capacity is equal to the size parameter passed to the function,
	 * indicating the maximum number of elements that can be stored in the buffer.
	 * The buffer's mark and reset methods are supported, allowing for efficient manipulation
	 * of large datasets without overrunning the buffer.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * creates a new Java `Buffer` object with a specified size, using the `BufferUtils`
	 * class to handle the buffer creation.
	 * 
	 * @param size integer length of a byte buffer to be created, which determines the
	 * capacity of the buffer.
	 * 
	 * @returns a non-NULL, direct, and fast byte buffer object instance of the specified
	 * size.
	 * 
	 * 	- The `createByteBuffer` function returns a `ByteBuffer` instance representing
	 * an uninitialized buffer with the specified size in bytes.
	 * 	- The `ByteBuffer` object provides a way to manipulate memory-mapped files or
	 * buffers as a sequence of bytes.
	 * 	- The buffer can be accessed and modified using various methods, such as reading
	 * and writing bytes at specific offsets or ranges.
	 * 	- The buffer's capacity is determined by the size passed to the `createByteBuffer`
	 * function, which cannot be changed after creation.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * creates an `IntBuffer` object from an array of integers, copies the values to the
	 * buffer, and flips the buffer for efficient random access.
	 * 
	 * @returns an IntBuffer containing the provided values flipped in memory.
	 * 
	 * 	- The `IntBuffer` object returned by the function is flipped, meaning that its
	 * data is inverted or mirrored.
	 * 	- The buffer's length is equal to the number of values passed to the function.
	 * 	- The buffer's `put` method is used to store the input values in the buffer.
	 * 
	 * No summary is provided at the end of the explanation.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * creates a new `FloatBuffer` object containing the provided `float` values, flipping
	 * it after putting them into the buffer.
	 * 
	 * @returns a flipped FloatBuffer containing the input values.
	 * 
	 * 	- The `FloatBuffer` object that is returned is created by putting the input
	 * `float... values` into a buffer using the `put` method.
	 * 	- The `flip` method is called on the buffer after it has been populated to convert
	 * the data from an uninitialized state to a flipped state, where the buffer's position
	 * points to the last element written.
	 * 
	 * The returned buffer has several attributes, including:
	 * 
	 * 	- Its size, which is equal to the number of input `float... values`.
	 * 	- The data stored in its positions, which are the input `float... values` that
	 * were put into the buffer.
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
	 * filters a given array of strings, removing any empty strings and returning an array
	 * of remaining non-empty strings.
	 * 
	 * @param data array of strings to be filtered and returned as a new array.
	 * 
	 * 	- Length: The method takes an array of strings as input, which has a fixed length.
	 * 	- Elements: Each element in the input array is a string object.
	 * 	- Emptiness: The function checks if each string is not empty before adding it to
	 * the result array.
	 * 	- Return type: The function returns an array of strings, where each element is
	 * obtained by adding the non-empty strings from the input array to the result list.
	 * 
	 * @returns an array of non-empty strings obtained by filtering the input array.
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
	 * converts an `Integer[]` array to an integer array by copying its elements.
	 * 
	 * @param data array of integers that is to be converted into an integer array.
	 * 
	 * 	- `data` is an array of integers that needs to be converted into an integer array.
	 * 	- The length of `data` matches the length of the resulting integer array `result`.
	 * 	- Each element in `data` is copied unchanged to its corresponding position in `result`.
	 * 
	 * @returns an integer array with the same length as the input `data` array, containing
	 * the original values of the `data` elements.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * converts a list of integers to an integer array with the same size as the list.
	 * It uses a loop to iterate over the elements of the list and stores each element
	 * in the corresponding position of the array.
	 * 
	 * @param data list of integers that are converted to an integer array by the
	 * `toIntArray()` method.
	 * 
	 * 	- `data` is an instance of the `List` interface representing a collection of integers.
	 * 	- `data.size()` returns the number of elements in the list.
	 * 	- `data.get(i)` returns the `i`-th element of the list, where `0 ≤ i < data.size()`.
	 * 
	 * @returns an integer array of size equal to the number of elements in the input list.
	 */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
	/**
	 * converts a `Float[]` array to an equivalent `float[]` array, preserving the original
	 * data's order and values.
	 * 
	 * @param data Float array that is being converted to a float array.
	 * 
	 * 	- The function takes a `Float[]` parameter named `data`.
	 * 	- The function creates a new `float[]` array with the same length as `data`.
	 * 	- The function iterates through each element in `data` and assigns it to the
	 * corresponding position in the new `float[]` array.
	 * 
	 * Overall, this function appears to deserialize a `Float[]` array into a new `float[]`
	 * array for further processing or storage.
	 * 
	 * @returns an array of floating-point values, equivalent to the input `data` array.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * converts a `List<Float>` to a `float[]`. It iterates through the list and assigns
	 * each element to its corresponding position in the array.
	 * 
	 * @param data list of floats that is to be converted into an array of floats.
	 * 
	 * The function takes a `List<Float>` as input, which represents an array of
	 * floating-point numbers.
	 * 
	 * The `size()` method is used to access the number of elements in the list, which
	 * is the size of the input array.
	 * 
	 * The `get(int index)` method is used to retrieve the value of the element at the
	 * specified position in the list.
	 * 
	 * @returns an array of `float` values representing the input list of `Float` elements.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
