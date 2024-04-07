package com.ch;

import java.util.List;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

/**
 * provides various methods for manipulating data arrays and buffers in Java. These
 * include:
 * 
 * 	- Creating FloatBuffer, IntBuffer, and ByteBuffer objects for storing and
 * manipulating data.
 * 	- Methods for flipping buffers and converting them to other data types (e.g.,
 * Vertex[] to FloatBuffer).
 * 	- A method for removing empty strings from an array.
 * 	- Methods for converting Integer, Float, and List objects to arrays.
 */
public class Util {
	
 /**
  * creates a new `FloatBuffer` object with the specified size.
  * 
  * @param size number of elements to be stored in the `FloatBuffer`.
  * 
  * @returns a `FloatBuffer` object representing a buffer of size `size` containing
  * floating-point values.
  * 
  * 	- The method returns a `FloatBuffer` object, which is a type-safe wrapper around
  * a native floating-point buffer.
  * 	- The buffer size is determined by the `size` parameter passed to the method,
  * which cannot be negative.
  * 	- The buffer is created using the `BufferUtils` class, which provides utility
  * methods for creating and manipulating buffers in a type-safe manner.
  */
	public static FloatBuffer createFloatBuffer(int size) {
		return BufferUtils.createFloatBuffer(size);
	}

 /**
  * creates an `IntBuffer` object of a specified size, using the `BufferUtils` class.
  * The created buffer can be used for efficient memory access and manipulation of
  * integer values.
  * 
  * @param size integer capacity of the IntBuffer that is to be created.
  * 
  * @returns an `IntBuffer` object that represents a contiguous block of integers with
  * the specified size.
  * 
  * The `IntBuffer` object is created using the `BufferUtils` class, which is responsible
  * for managing buffers in Java. The size parameter passed to the function determines
  * the capacity of the buffer, which can be greater than or equal to 0.
  * The buffer is guaranteed to have enough space to hold at least `size` elements of
  * type `int`. If the actual number of elements stored in the buffer exceeds the
  * specified `size`, the remaining elements will be discarded without notice.
  * The buffer's position is set to 0, indicating that the first element in the buffer
  * can be accessed by invoking the `get` method with a valid index.
  */
	public static IntBuffer createIntBuffer(int size) {
		return BufferUtils.createIntBuffer(size);
	}

 /**
  * creates a new byte buffer with the specified size using the `BufferUtils` class.
  * 
  * @param size buffer size in bytes that the `createByteBuffer` method creates.
  * 
  * @returns a ByteBuffer instance of the specified size.
  * 
  * The returned ByteBuffer has a capacity of `size` bytes, which is the parameter
  * passed to the function.
  * It is created by BufferUtils, whose class and method are unspecified in the code
  * snippet provided. Therefore, its implementation details are unknown.
  * The ByteBuffer can be used for reading or writing binary data, as it represents a
  * buffer that can hold binary data of any size.
  */
	public static ByteBuffer createByteBuffer(int size) {
		return BufferUtils.createByteBuffer(size);
	}

 /**
  * creates an `IntBuffer` from an array of integers and flips it, allowing for efficient
  * access to its elements.
  * 
  * @returns an IntBuffer containing the flipped version of the input array.
  * 
  * 	- The `IntBuffer` object returned by the function is flipped, meaning that its
  * elements are reversed in memory.
  * 	- The buffer's length is equal to the number of elements passed to the function,
  * as reflected by the `values.length` parameter.
  * 	- The buffer's capacity is unchanged after being flipped, which means it can still
  * store the same amount of data.
  */
	public static IntBuffer createFlippedBuffer(int... values) {
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();

		return buffer;
	}
	
 /**
  * creates a new `FloatBuffer` object by copying a given array of floating-point
  * values and flipping it.
  * 
  * @returns a flipped FloatBuffer containing the provided values.
  * 
  * 	- `FloatBuffer buffer`: This is a `FloatBuffer` object that contains the flipped
  * version of the input values.
  * 	- `values.length`: This is the number of input values that were passed to the function.
  * 	- `put()` method: The `buffer.put(values)` line calls the `put()` method, which
  * adds the input values to the buffer in a contiguous block of memory.
  * 	- `flip()` method: The `buffer.flip()` line calls the `flip()` method, which makes
  * the buffer's data position points to the end of the buffer, and its position pointer
  * to the beginning of the buffer. This allows for efficient random access to the
  * buffer's data.
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
  * removes all empty strings from an array of strings and returns an array of non-empty
  * strings.
  * 
  * @param data array of strings that the function will operate on, and it is used to
  * store the result of the operation after the empty strings have been removed.
  * 
  * 	- Length: The length of the input array is specified as `data.length`.
  * 	- Elements: Each element in the input array is a `String`, which means that each
  * element has a type of `String` and can contain text or other data types enclosed
  * within double quotes (`""`).
  * 	- Emptiness: The function checks if each element is not empty by using the `!`
  * operator. If an element is empty, it is not included in the resulting array `result`.
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
  * takes an `Integer[]` array as input and returns a new `int[]` array containing the
  * same values.
  * 
  * @param data integer array that is converted to an int array by the function.
  * 
  * 	- The function takes an `Integer[]` array as input, indicating that the original
  * data is also an integer array.
  * 	- The function creates a new `int[]` array with the same length as the input `data`.
  * 	- The function then iterates over each element in the input array and assigns it
  * to the corresponding position in the output array using a simple loop.
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
  * takes a list of integers and returns an integer array with the same size as the
  * input list. It iterates through the list and copies each element to a corresponding
  * position in the output array.
  * 
  * @param data List of integers to be converted into an integer array.
  * 
  * 	- `data` is an instance of `List`, implying it contains a collection of items
  * that can be of any data type, including `Integer`.
  * 	- `data.size()` returns the number of elements in the list.
  * 	- The method `get(int index)` is used to retrieve the element at position `index`
  * in the list. It returns an `Optional`, which may contain the value if it exists
  * or `None` otherwise.
  * 
  * @returns an integer array containing the elements of the input list.
  */
	public static int[] toIntArray(List<Integer> data) {
		int[] result = new int[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
	
 /**
  * takes a `Float` array as input and returns an array of the same length containing
  * the corresponding values converted to `float`.
  * 
  * @param data Float array that is to be converted into a `float[]` array.
  * 
  * 	- The type of `data` is `Float[]`.
  * 	- The length of `data` is determined by its size, which is a compile-time constant.
  * 	- Each element in `data` is of type `Float`.
  * 
  * @returns an array of `float` values, equal to the length of the input `Float[]` data.
  */
	public static float[] toFloatArray(Float[] data) {
		float[] result = new float[data.length];

		for (int i = 0; i < data.length; i++)
			result[i] = data[i];

		return result;
	}
	
 /**
  * converts a list of floating-point numbers into an array of floats, copying the
  * elements of the list to the array.
  * 
  * @param data List of Float values that are to be converted into an array of Float
  * values.
  * 
  * The input `data` is of type `List<Float>`, indicating that it is an array-based
  * collection containing floating-point values. The size of the list is represented
  * by the `size()` method.
  * 
  * Within the function, a new array of floats is created using the `float[]` constructor,
  * and its length is set to match the size of `data`. Then, the elements of `data`
  * are copied into the newly created array using a standard for-loop.
  * 
  * @returns an array of float values representing the elements of the input list.
  */
	public static float[] toFloatArray(List<Float> data) {
		float[] result = new float[data.size()];

		for (int i = 0; i < data.size(); i++)
			result[i] = data.get(i);

		return result;
	}
}
