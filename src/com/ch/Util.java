package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * is a collection of methods for manipulating buffers and matrices in Java. It
 * provides functions for creating and manipulating FloatBuffer, IntBuffer, ByteBuffer
 * objects as well as flipping and converting them to different data types. Additionally,
 * it includes methods for removing empty strings from an array, converting an integer
 * array to an int array, and converting a float array to a float array or a List of
 * Floats.
 */
public class Util {
	
	/**
	 * creates a new `FloatBuffer` object with the specified size, using the `BufferUtils`
	 * class to handle the buffer creation.
	 * 
	 * @param size number of floating-point values to be stored in the `FloatBuffer`.
	 * 
	 * @returns a `FloatBuffer` object representing a buffer of size `size` containing
	 * floating-point values.
	 * 
	 * The `createFloatBuffer` function returns a `FloatBuffer` object, which represents
	 * a contiguous block of floating-point numbers in memory. The buffer is created by
	 * allocating memory for the specified number of elements using the `BufferUtils`
	 * class. Each element in the buffer is represented by a single 32-bit floating-point
	 * value, with values ranging from -3.4e38 to 3.4e38. The buffer's capacity and
	 * position are independent variables that can be modified by the user.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * creates a new `IntBuffer` instance with the specified size. The buffer is created
	 * using the `BufferUtils` class.
	 * 
	 * @param size integer capacity of the IntBuffer to be created, which determines the
	 * maximum number of integers that can be stored in the buffer.
	 * 
	 * @returns an `IntBuffer` object containing the specified number of integers.
	 * 
	 * 	- The IntBuffer is created using the `BufferUtils` class.
	 * 	- The buffer size is specified by the `size` parameter passed to the function.
	 * 	- The buffer is an instance of the `IntBuffer` class, which provides methods for
	 * reading and writing integer values to the buffer.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * creates a new byte buffer instance with the specified size.
	 * 
	 * @param size desired capacity of the resulting `ByteBuffer`.
	 * 
	 * @returns a non-nullable ByteBuffer instance with the specified size.
	 * 
	 * The function returns a `ByteBuffer` object that represents a contiguous block of
	 * memory used for storing binary data.
	 * This buffer can be reused by other functions or applications.
	 * The size of the buffer is determined by the input parameter `size`.
	 * The buffer's capacity and position are reset to zero after creation, allowing it
	 * to be used for different purposes without any previous contents affecting the new
	 * usage.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * creates an `IntBuffer` object from an array of integers and flips its order.
	 * 
	 * @returns an IntBuffer containing the provided values in a flipped state.
	 * 
	 * 	- The `IntBuffer` object is the output of the function, which represents a flipped
	 * view of the input array.
	 * 	- The `buffer.put(values)` method is called inside the function to copy the
	 * elements of the input array into the buffer.
	 * 	- The `buffer.flip()` method is called after the data has been copied to flip the
	 * buffer's position, making the last element the first one, and so on.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * creates a new `FloatBuffer` object and stores the input `float` values in it using
	 * the `put` method. Then, it flips the buffer to make the data accessible for reading
	 * from the end of the buffer. Finally, it returns the flipped buffer.
	 * 
	 * @returns a flipped FloatBuffer containing the input values.
	 * 
	 * 	- The `FloatBuffer` object is created using the `createFloatBuffer` method with
	 * the length of the input array `values`.
	 * 	- The buffer's elements are set to the corresponding values in the input array
	 * using the `put` method.
	 * 	- The `flip` method is called on the buffer to create a flipped view of the data,
	 * allowing for efficient random access.
	 * 
	 * Overall, the `createFlippedBuffer` function returns a flipped view of an array of
	 * floating-point values, which can be used for efficient random access in subsequent
	 * operations.
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
	 * removes empty strings from an array of strings and returns a new array with the
	 * non-empty strings.
	 * 
	 * @param data array of strings to be filtered and contains the values that will be
	 * processed by the function.
	 * 
	 * 	- `data` is an array of strings of length `data.length`.
	 * 	- Each element in the array may or may not be empty, as denoted by the comparison
	 * to the empty string ("") in the for loop.
	 * 	- The `ArrayList<String>` created inside the function contains all non-empty
	 * elements from the original array.
	 * 	- The resulting `String[]` is created by calling the `toArray()` method on the `ArrayList`.
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
	 * converts an `Integer[]` array to an `int[]` array with the same length, copying
	 * the values of the original elements to the new array.
	 * 
	 * @param data 1D array of integers that will be converted to a 1D integer array.
	 * 
	 * 	- Length: The `Integer[]` parameter `data` has a fixed length, which is also the
	 * length of the returned `int[]`.
	 * 	- Each element: Each element in the original `data` array is converted to an `int`
	 * value using the ` Integer.valueOf()` method before being added to the returned `int[]`.
	 * 
	 * @returns an integer array with the same length as the input array `data`.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * transforms a list of integers into an integer array with the same size as the list.
	 * It uses a loop to iterate over the list and copy each element to the corresponding
	 * position in the array.
	 * 
	 * @param data List of integers that are converted to an integer array by the function.
	 * 
	 * 	- The input `data` is of type `List`, which implies that it is a collection of
	 * items that can be of any data type.
	 * 	- The size of the list is represented by `data.size()`, which returns the number
	 * of elements in the list.
	 * 	- Each element in the list is accessed through the `get()` method, which retrieves
	 * the value of the corresponding element.
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
	 * converts a `Float[]` array to an equivalent `float[]` array, preserving the original
	 * data's order and values.
	 * 
	 * @param data 1D array of Float values that are converted to a 1D float array through
	 * the function.
	 * 
	 * 	- Type: `Float[]` indicating an array of floating-point numbers.
	 * 	- Length: `data.length`, which represents the number of elements in the array.
	 * 	- Element type: `float`, indicating each element in the array is a single
	 * floating-point number.
	 * 
	 * @returns an array of `float` values, equal in length to the input `Float[]` array.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * converts a list of `Float` objects into an array of `Float` values, size of the list.
	 * 
	 * @param data List of floating-point numbers to be converted into an array of
	 * floating-point values.
	 * 
	 * The `List<Float>` type refers to an array of floating-point numbers represented
	 * as a list.
	 * The `get()` method is used to access each element in the list.
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
