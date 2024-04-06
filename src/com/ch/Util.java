package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * TODO
 */
public class Util {
	
 /**
  * creates a new `FloatBuffer` instance with the specified size. The returned buffer
  * contains a contiguous block of memory capable of storing floating-point values.
  * 
  * @param size number of floating-point values to be stored in the `FloatBuffer`.
  * 
  * @returns a `FloatBuffer` object of the specified size, created using the `BufferUtils`
  * class.
  * 
  * 	- The output is an instance of the `FloatBuffer` class, which represents a
  * contiguous block of memory that can be accessed and modified as a vector of
  * floating-point values.
  * 	- The size of the buffer is determined by the parameter passed to the function,
  * which must be a positive integer.
  * 	- The buffer is created using the `BufferUtils` class, which provides utility
  * methods for working with buffers in Java.
  */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

 /**
  * creates an `IntBuffer` instance with the specified size.
  * 
  * @param size number of elements to be stored in the buffer.
  * 
  * @returns an `IntBuffer` object that represents a contiguous block of integers with
  * the specified size.
  * 
  * The function returns an `IntBuffer` object, which is a type-safe wrapper around a
  * native buffer containing integer values. The buffer is managed by the Java platform
  * and provides efficient access to the underlying data. The size of the buffer is
  * specified by the parameter `size`.
  */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

 /**
  * creates a new `ByteBuffer` instance with a specified size.
  * 
  * @param size capacity of the byte buffer to be created, which determines the maximum
  * amount of memory allocated for storing data.
  * 
  * @returns a byte buffer with the specified size.
  * 
  * 	- The `createByteBuffer` function returns a `ByteBuffer` object that represents
  * a contiguous block of memory with a specified capacity for storing binary data.
  * 	- The ` ByteBuffer` object is a type-safe wrapper around a low-level memory buffer,
  * providing a set of methods for reading and writing data to the buffer.
  * 	- The size parameter passed to the function determines the initial capacity of
  * the returned buffer. If the size is zero or negative, the buffer will be empty and
  * have no data stored in it.
  */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

 /**
  * creates an `IntBuffer` object containing the given array elements in a flipped
  * state, i.e., with all bits set to 1 on the bottom half of the buffer.
  * 
  * @returns an IntBuffer containing the flipped versions of the input values.
  * 
  * 	- The function creates an `IntBuffer` object named `buffer`.
  * 	- The `buffer` object is filled with the input values using the `put()` method.
  * 	- The `buffer` object is flipped to create a new buffer that can be used for
  * reading and writing operations.
  * 
  * The output of the function is an `IntBuffer` object, which is a mutable array of
  * integers that can be modified through the use of the `put()`, `get()`, and `flip()`
  * methods.
  */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
 /**
  * creates a new FloatBuffer object and assigns it the provided array of floats, then
  * flips the buffer to make the data available for reading from the end of the buffer.
  * 
  * @returns a flipped FloatBuffer containing the input values.
  * 
  * 	- `FloatBuffer buffer`: This is the buffer that contains the flipped float values.
  * 	- `values.length`: The number of float values stored in the buffer.
  * 	- `put(values)`: The method used to store the float values in the buffer.
  * 	- `flip()`: The method used to flip the buffer, making its elements accessible
  * through the `get` methods.
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
  * removes any empty strings from an array of strings and returns an new array with
  * only non-empty strings.
  * 
  * @param data 0-length array of strings that is to be processed by the function, and
  * its elements are checked for emptiness before being added to an ArrayList for
  * subsequent conversion into an array output.
  * 
  * 	- `data` is an array of strings.
  * 	- The length of the array `data` is specified by an integer variable `data.length`.
  * 	- Each element in the array `data` can be a string, or it can be empty (represented
  * by the value `""`).
  * 
  * The function then performs operations on the elements of the array `data`, including:
  * 
  * 	- Iterating through each element of `data` using a loop from 0 to `data.length -
  * 1`.
  * 	- Checking if each element is not empty (i.e., it is not `""`). If it is not
  * empty, the function adds it to an ArrayList named `result`.
  * 	- After all elements are processed, the `result` ArrayList is converted into a
  * new array of strings using the `toArray()` method.
  * 	- The resulting array of strings is returned as the output of the function.
  * 
  * @returns an array of non-empty strings.
  * 
  * 	- The returned array `res` has a length equal to the number of non-empty strings
  * in the input array `data`.
  * 	- Each element in `res` is a non-empty string from the original input array.
  * 	- The order of the elements in `res` is the same as the order of the elements in
  * `data`.
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
  * converts an `Integer[]` array to an `int[]` array with the same length, by simply
  * copying the values of the original array to the new array.
  * 
  * @param data 1D array of integers that is converted into a new 1D integer array by
  * the `toIntArray()` method.
  * 
  * The function takes an `Integer[]` input `data`. The length of the array is consistent
  * throughout the execution of the function.
  * 
  * Each element of the input `data` is copied to a newly created `int[]` output called
  * `result`.
  * 
  * @returns an integer array with the same elements as the input array.
  */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * takes a list of integers and returns an integer array with the same size as the
  * list. It loops through each element in the list and assigns it to the corresponding
  * position in the integer array.
  * 
  * @param data List of integers that is to be converted into an integer array by the
  * `toIntArray()` method.
  * 
  * 1/ The function takes in a `List<Integer>` as input, indicating that the list
  * contains only integer elements.
  * 2/ The function creates an array of integers with size equal to the number of
  * elements in the input list using the `int[]` data type.
  * 3/ The function iterates over each element in the input list and assigns it to the
  * corresponding position in the output array using the `result[i]` syntax.
  * 4/ The function returns the populated integer array as a whole, which can be used
  * for further processing or analysis.
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
  * converts a `Float` array to a `float[]` array, with each element of the input array
  * copied to the output array.
  * 
  * @param data Float array that contains the values to be converted to a float array.
  * 
  * 	- `data` is an instance of the `Float` class, indicating that it contains a
  * collection of floating-point numbers.
  * 	- The method takes a single argument `data`, which is an array of `Float` objects.
  * 	- The function creates a new `float[]` array with the same length as `data`.
  * 	- It then iterates over each element in the input `data` array and assigns it to
  * a corresponding position in the new `float[]` array.
  * 
  * Therefore, the resulting `float[]` array contains the exact values of the original
  * `data` array.
  * 
  * @returns an array of floating-point values, each corresponding to a value in the
  * input array.
  */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * converts a `List<Float>` into an array of float values, by using a loop to iterate
  * through the list and assign each value to a corresponding index in the resulting
  * array.
  * 
  * @param data list of floating-point numbers to be converted into an array of float
  * values.
  * 
  * 	- `data`: A `List<Float>` containing the serialized data to be converted into an
  * array of floating-point numbers.
  * 
  * @returns an array of floating-point numbers representing the input list of float
  * values.
  */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
