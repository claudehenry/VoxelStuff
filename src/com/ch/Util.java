package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * is a collection of methods for manipulating buffers and matrices in Java. The class
 * provides several methods for creating and manipulating buffers, including FloatBuffer,
 * IntBuffer, and ByteBuffer creation, as well as flipping and converting buffers
 * between different data types. Additionally, the class offers methods for working
 * with matrices, including creation of a Matrix4f object and conversion of a float
 * array to a matrix.
 */
public class Util {
	
	/**
	 * creates a new `FloatBuffer` object of a specified size, using the `BufferUtils`
	 * class to create the buffer.
	 * 
	 * @param size number of elements to be stored in the `FloatBuffer`.
	 * 
	 * @returns a `FloatBuffer` object with the specified size.
	 * 
	 * The output is an instance of `FloatBuffer`.
	 * This buffer has a size that matches the input parameter passed to the function.
	 * The buffer contains a collection of floating-point values, each occupying 4 bytes
	 * of memory.
	 * The buffer can be used for efficient reading and writing of floating-point data
	 * in Java applications.
	 */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

	/**
	 * creates an `IntBuffer` object from a specified size.
	 * 
	 * @param size number of elements to be stored in the buffer when it is created.
	 * 
	 * @returns an `IntBuffer` instance filled with the specified number of integers.
	 * 
	 * The returned IntBuffer has a capacity of size, indicating that it can hold exactly
	 * size integer values.
	 * 
	 * It is immutable, meaning that once created, its contents cannot be modified.
	 * 
	 * The buffer is backed by a direct map of the underlying memory, providing efficient
	 * access to the data.
	 */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

	/**
	 * creates a new byte buffer with the specified size using `BufferUtils`.
	 * 
	 * @param size desired capacity of the ByteBuffer to be created, which determines its
	 * internal storage format and performance.
	 * 
	 * @returns a non-null `ByteBuffer` instance with the specified size.
	 * 
	 * 	- The `createByteBuffer` function returns a `ByteBuffer` object, which is a
	 * lightweight object that stores binary data in memory.
	 * 	- The ` ByteBuffer` object can hold up to 2^31-1 bytes of data, making it a useful
	 * tool for large-scale data processing.
	 * 	- The returned `ByteBuffer` object has various methods and fields that allow for
	 * manipulation and analysis of the stored data. These include methods for reading
	 * and writing specific ranges of the buffer, as well as fields for accessing the
	 * buffer's position, capacity, and mark position.
	 */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

	/**
	 * creates an `IntBuffer` from an array of integers and flips it for access as a view
	 * on the original data.
	 * 
	 * @returns an flipped IntBuffer containing the input values.
	 * 
	 * 	- The `IntBuffer` object returned by the function is an instance of the `IntBuffer`
	 * class in Java.
	 * 	- It represents a mutable array of integers that can be accessed and modified
	 * using the `put` method.
	 * 	- The `flip()` method is called on the buffer after the `put` method to reverse
	 * the order of the elements in the buffer, effectively "flipping" it.
	 */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
	/**
	 * creates a new `FloatBuffer` instance from an array of `float` values, stores them
	 * in the buffer, and flips the buffer for efficient access.
	 * 
	 * @returns a flipped `FloatBuffer` containing the input values.
	 * 
	 * The `FloatBuffer` object returned by the function is flipped after putting the
	 * `float... values`. This means that the buffer's position is advanced to the end
	 * of its data, and any subsequent method calls on the buffer will start from the end
	 * of the data.
	 * 
	 * The buffer itself is a dynamic memory allocation of type `FloatBuffer`, which
	 * allows for efficient manipulation of large arrays of floating-point numbers. Its
	 * length is determined by the number of values passed to the function, and it can
	 * be resized as needed using the `put` method.
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
	 * takes an array of strings and returns a new array with all non-empty strings. It
	 * uses an ArrayList to store the non-empty strings and then converts it to an array
	 * using the `toArray` method.
	 * 
	 * @param data array of strings to be filtered and returned as an array of non-empty
	 * strings.
	 * 
	 * 	- `data` is an array of strings.
	 * 	- It has a length property that indicates the number of elements in the array.
	 * 	- Each element in the array is a string object with a unique value.
	 * 	- The values of the string objects are not empty, as the function checks for
	 * emptiness before adding them to the result array.
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
	 * converts an array of integers to an integer array, by simply copying the elements
	 * of the original array into the new integer array.
	 * 
	 * @param data 1D array of integers that is converted to a 1D integer array by the function.
	 * 
	 * 	- The input is an array of integers, represented by `Integer[]`.
	 * 	- The length of the input array is specified using the `length` property.
	 * 	- Each element in the input array is copied to the corresponding position in the
	 * output array using a for-each loop.
	 * 
	 * @returns an integer array with the same elements as the input `data` array.
	 */
	public static int[] toIntArray(Integer[] data) {
		int[] result = new int[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * converts a list of integers to an integer array with the same size as the list.
	 * It does this by using a loop to extract each element from the list and store it
	 * in the corresponding position of the resulting integer array.
	 * 
	 * @param data list of integers that are converted to an integer array by the
	 * `toIntArray()` method.
	 * 
	 * 	- `data` is a list of integers.
	 * 	- The size of `data` can be determined by using the `size()` method.
	 * 	- Each element in the list can be accessed using its index with the help of the
	 * `get()` method.
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
	 * takes a `Float[]` array as input and returns an equivalent `float[]` array with
	 * the same elements.
	 * 
	 * @param data 1D array of `Float` values that will be converted to a 1D array of
	 * `float` values by the function.
	 * 
	 * 	- The input `data` is an array of `Float` type.
	 * 	- The length of the array is equal to the number of elements in the input array.
	 * 	- Each element in the array is a `Float` value.
	 * 
	 * @returns an array of `float` values, where each value corresponds to a corresponding
	 * value in the input `Float[]` array.
	 */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
	/**
	 * converts a list of `Float` objects into an array of `Float` values, size equal to
	 * the number of elements in the list.
	 * 
	 * @param data list of floating-point numbers that are converted to an array of the
	 * same size.
	 * 
	 * 	- `data`: A list containing floating-point numbers.
	 * 	- `size()`: Returns the number of elements in the list, which is equal to the
	 * number of floating-point numbers.
	 * 	- `get(i)`: Retrieves the `i`-th element from the list and returns its value as
	 * a `float`.
	 * 
	 * @returns an array of floating-point numbers containing the elements of the input
	 * list.
	 */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
