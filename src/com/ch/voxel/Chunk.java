package com.ch.voxel;

import java.util.ArrayList;
import java.util.List;

import com.ch.Model;
import com.ch.SimplexNoise;
import com.ch.Util;
import com.ch.math.Matrix4f;

/**
 * in this code is responsible for storing and manipulating data related to a 3D grid.
 * It contains several fields and methods for handling the grid's geometry, including
 * vertices, indices, and genModel() and createModel() methods for generating and
 * creating models from the grid data. The class also includes utilities for working
 * with floats and integers.
 */
public class Chunk {

	public static final int CHUNK_SIZE = 64;
	private static final int CHUNK_SIZE_SQUARED = CHUNK_SIZE * CHUNK_SIZE;
	private static final int CHUNK_SIZE_CUBED = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE;

	private Block[] blocks;
	public int x, y, z;
	private Model model;
	
	/**
	 * retrieves a `Model` object from storage and returns it after potentially creating
	 * a new one if necessary.
	 * 
	 * @returns a `Model` object.
	 * 
	 * 	- The `model` variable is of type `Model`.
	 * 	- The value of the `to_gen_model` field is determined by a boolean expression
	 * that is not provided in the code snippet.
	 * 	- The value of `to_gen_model` influences whether or not to create a new instance
	 * of the `Model` class and assign it to the `model` variable. If `to_gen_model` is
	 * true, a new instance will be created; otherwise, the existing `model` variable
	 * will be returned unchanged.
	 */
	public Model getModel() {
		if (to_gen_model) {
			createModel();
			to_gen_model = false;
		}
		return model;
	}
	
	/**
	 * returns a Matrix4f object representing a translation of a specific amount along
	 * the x, y, and z axes.
	 * 
	 * @returns a 4x4 transformation matrix representing a translation by the product of
	 * `x`, `y`, and `z` values.
	 * 
	 * The `Matrix4f` object returned by the function represents a 4x4 transformation
	 * matrix, which includes the translation components x, y, and z, as well as the other
	 * elements that define the matrix's transformation properties. The translation
	 * components represent the position of the transform in 3D space.
	 */
	public Matrix4f getModelMatrix() {
		return new Matrix4f().initTranslation(x * CHUNK_SIZE, y * CHUNK_SIZE, z * CHUNK_SIZE);
	}

	public Chunk(int _x, int _y, int _z) {
		
		this.x = _x;
		this.y = _y;
		this.z = _z;
		
		blocks = new Block[CHUNK_SIZE_CUBED];
		
		for (int i = 0; i < CHUNK_SIZE_CUBED; i++) {
			int z = i / CHUNK_SIZE_SQUARED;
			int ii = i - (z * CHUNK_SIZE_SQUARED);
			int y = ii / CHUNK_SIZE;
			int x = ii % CHUNK_SIZE;
			if (SimplexNoise.noise((x + this.x * CHUNK_SIZE) / 10f, (y + this.y * CHUNK_SIZE) / 10f, (z + this.z * CHUNK_SIZE) / 10f) > 0.1f) {
			blocks[i] = new Block(x, y, z);
			}
		}
	}
	
	

	/**
	 * updates the block state for each block in a chunk, considering neighboring blocks
	 * and updating the block's type, true, false, or null.
	 */
	public void updateBlocks() {
		for (int i = 0; i < CHUNK_SIZE_CUBED; i++) {
			Block b = blocks[i];
				if (b != null) {
				int n_x = i - 1;
				int p_x = i + 1;
				int n_y = i - CHUNK_SIZE;
				int p_y = i + CHUNK_SIZE;
				int n_z = i - CHUNK_SIZE_SQUARED;
				int p_z = i + CHUNK_SIZE_SQUARED;
				
				if (b.x - 1 < 0) {
					//TODO: check neighbor chunk
					b.lt = false;
				} else {
					Block bl = blocks[n_x];
					if (bl == null)
						b.lt = true;
					else
						b.lt = false;
				}
				if (b.y - 1 < 0) {
					//TODO: check neighbor chunk
					b.bt = false;
				} else {
					Block bl = blocks[n_y];
					if (bl == null)
						b.bt = true;
					else
						b.bt = false;
				}
				if (b.z - 1 < 0) {
					//TODO: check neighbor chunk
					b.ft = false;
				} else {
					Block bl = blocks[n_z];
					if (bl == null)
						b.ft = true;
					else
						b.ft = false;
				}
				
				if (b.x + 1 >= CHUNK_SIZE) {
					//TODO: check neighbor chunk
					b.rt = false;
				} else {
					Block bl = blocks[p_x];
					if (bl == null)
						b.rt = true;
					else
						b.rt = false;
				}
				if (b.y + 1 >= CHUNK_SIZE) {
					//TODO: check neighbor chunk
					b.tp = false;
				} else {
					Block bl = blocks[p_y];
					if (bl == null)
						b.tp = true;
					else
						b.tp = false;
				}
				if (b.z + 1 >= CHUNK_SIZE) {
					//TODO: check neighbor chunk
					b.bk = false;
				} else {
					Block bl = blocks[p_z];
					if (bl == null)
						b.bk = true;
					else
						b.bk = false;
				}
			}
		}
	}
	
//	class Vertex3i {
//		
//		public int x, y, z; 
//		public float u, v;
//		
//		public Vertex3i(x, y, z, u, v) {
//			
//		}
//		
//	}
	
	private ArrayList<Float> vertices = new ArrayList<>();
	private ArrayList<Integer> indices = new ArrayList<>();
	private boolean to_gen_model;
	
	
	/**
	 * sets a model element's generation status to `true`.
	 */
	public void toGenModel() { toGenModel(false); };
	
	/**
	 * generates a 3D model from a set of vertices, indices, and blocks. It loops through
	 * each block and updates a maximum index, before loading the model arrays into an
	 * instance of the `Model` class. The function also maintains a boolean variable to
	 * track whether the model is being generated or loaded.
	 * 
	 * @param now boolean value of whether to generate a new model or not when calling
	 * the `toGenModel()` method.
	 */
	public void toGenModel(boolean now) {

		int max_index = 0;
//		System.out.println("gen model");
		for (int i = 0; i < CHUNK_SIZE_CUBED; i++) {
			Block b = blocks[i];
			if (b != null) {
				max_index = gen(vertices, indices, b, max_index);
					
			}
		}
//		System.out.println("vertice   : " + vertices.size() / 5 + " -- floats : " + vertices.size());
//		System.out.println("indices   : " + indices.size());
//		System.out.println("triangles : " + indices.size() / 3);
//		System.out.println("quads     : " + indices.size() / 6);
//		System.out.println("---------------------------\nloading model arrays");
		
		// cant implement filtering and re-indexing for textured cubes
//		{
//			
//			ArrayList<Integer>
//			
//		}
		
//		return Model.load(Util.toFloatArray(new_vertices), Util.toIntArray(new_indices));
		
		if (now) {
			createModel();
			to_gen_model = false;
		} else {
			to_gen_model = true;
		}
		
	}
	
	/**
	 * loads a 3D model from a buffer and stores it as an instance variable, `model`.
	 */
	private void createModel() {
		this.model = Model.load(Util.toFloatArray(vertices), Util.toIntArray(indices));
	}
	
	/**
	 * generates a model based on a given input.
	 * 
	 * @returns a `Model` object representing the generated model.
	 * 
	 * 	- `model`: A `Model` object representing the generated model.
	 * 	- `toGenModel(true)`: The call to the `toGenModel` method, which sets the input
	 * for the generation process.
	 * 	- `this.model`: The current instance of the `Model` class, which is used as the
	 * basis for the generation.
	 */
	public Model genModel() {
		
		toGenModel(true);
		
		return this.model;
	}

	/**
	 * generates new vertices and indices for a 3D mesh based on the block's transformation
	 * and the current maximum index value.
	 * 
	 * @param vertices 3D vertices of a shape, which are added to an internal list called
	 * `vertices` within the function.
	 * 
	 * 	- The `List<Float>` `vertices` is used to store the vertex positions of the current
	 * block in 3D space. Each element in the list represents a single vertex position
	 * in 3D space, represented as a float value.
	 * 	- The `indices` list stores the indices of the vertices in the `vertices` list,
	 * which are used to define the faces of the current block in 3D space. Each element
	 * in the list represents an index into the `vertices` list, indicating the position
	 * of a vertex in the block.
	 * 	- The `block` parameter is an instance of the `Block` class, which contains
	 * information about the current block being generated. It includes properties such
	 * as `x`, `y`, and `z` representing the position of the block in 3D space, as well
	 * as flags `ft`, `bk`, `bt`, `tp`, and `lt` indicating various properties of the block.
	 * 	- The `max_index` variable is used to keep track of the index of the current
	 * vertex in the `vertices` list, which is used to determine the next vertex position
	 * to be generated based on the properties of the current block.
	 * 
	 * @param indices 3D indices of the vertices in the mesh, which are added to a list
	 * as new blocks are generated.
	 * 
	 * 	- The `indices` list is an array of integers that corresponds to the vertices in
	 * the generated mesh. Each element in the list represents a vertex index in the mesh.
	 * 	- The indices are sequential and start from 0, indicating that they are contiguous
	 * and follow a consistent order.
	 * 	- The list contains a total of `4 * (block.ft + block.bk + block.bt + block.tp +
	 * block.lt + block.rt)` elements, where each element represents a vertex in the
	 * generated mesh. This value is calculated based on the properties of the `block` parameter.
	 * 	- The indices are not null or empty, as they always contain at least one element
	 * representing the first vertex in the mesh.
	 * 
	 * In summary, the `indices` list has the following properties: it is an array of
	 * integers, it is sequential and starts from 0, it contains a total of `4 * (block.ft
	 * + block.bk + block.bt + block.tp + block.lt + block.rt)` elements, and it is never
	 * null or empty.
	 * 
	 * @param block 3D block being generated, and its properties are used to determine
	 * which vertices and indices to add to the `vertices` and `indices` lists, respectively.
	 * 
	 * 	- `x`, `y`, and `z` represent the coordinates of the block in 3D space.
	 * 	- `ft`, `bk`, `bt`, `tp`, and `lt` represent different attributes or properties
	 * of the block, which are used to generate new vertices and indices for the 3D mesh.
	 * 	- `indices` is a list of integers that represents the order of the vertices in
	 * the mesh.
	 * 	- `max_index` is an integer variable that keeps track of the total number of
	 * vertices added so far.
	 * 
	 * The function generates new vertices and indices based on the properties of the
	 * `block` input, and increments `max_index` accordingly.
	 * 
	 * @param max_index 0-based index of the last vertex added to the list of vertices
	 * before each iteration of the block's faces is generated, and it serves as a counter
	 * for the number of vertices generated so far.
	 * 
	 * @returns an integer representing the maximum index added to the `indices` list
	 * after generating vertices and indices for a given block.
	 */
	private static int gen(List<Float> vertices, List<Integer> indices, Block block, int max_index) {
		
		float x = block.x;
		float y = block.y;
		float z = block.z;
		
		if (block.ft) {
			float[] tmp_v = { //
				x,   y,   z,   0, 0, //
				x+1, y,   z,   1, 0, //
				x+1, y+1, z,   1, 1, //
				x,   y+1, z,   0, 1, //
			}; //
			for (float f : tmp_v) vertices.add(f);
			for (int i : new int[] {0, 1, 2, 0, 2, 3}) indices.add(max_index + i);
			max_index += 4;
		}
		if (block.bk) {
			float[] tmp_v = { //
				x,   y,   z+1,   1, 0, //
				x+1, y,   z+1,   0, 0, //
				x+1, y+1, z+1,   0, 1, //
				x,   y+1, z+1,   1, 1, //
			}; //
			for (float f : tmp_v) vertices.add(f);
			for (int i : new int[] {0, 3, 2, 0, 2, 1}) indices.add(max_index + i);
			max_index += 4;
		}
		if (block.bt) {
			float[] tmp_v = { //
				x,   y,   z,     0, 0, //
				x+1, y,   z,   	 1, 0, //
				x+1, y,   z+1,   1, 1, //
				x,   y,   z+1,   0, 1, //
			}; //
			for (float f : tmp_v) vertices.add(f);
			for (int i : new int[] {0, 3, 2, 0, 2, 1}) indices.add(max_index + i);
			max_index += 4;
		}
		if (block.tp) {
			float[] tmp_v = { //
				x,   y+1, z,     0, 0, //
				x+1, y+1, z,     1, 0, //
				x+1, y+1, z+1,   1, 1, //
				x,   y+1, z+1,   0, 1, //
			}; //
			for (float f : tmp_v) vertices.add(f);
			for (int i : new int[] {0, 1, 2, 0, 2, 3}) indices.add(max_index + i);
			max_index += 4;
		}
		if (block.lt) {
			float[] tmp_v = { //
				x,   y,   z,     1, 0, //
				x,   y+1, z,     1, 1, //
				x,   y+1, z+1,   0, 1, //
				x,   y,   z+1,   0, 0, //
			}; //
			for (float f : tmp_v) vertices.add(f);
			for (int i : new int[] {0, 1, 2, 0, 2, 3}) indices.add(max_index + i);
			max_index += 4;
		}
		if (block.rt) {
			float[] tmp_v = { //
				x+1, y,   z,     0, 0, //
				x+1, y+1, z,     0, 1, //
				x+1, y+1, z+1,   1, 1, //
				x+1, y,   z+1,   1, 0, //
			}; //
			for (float f : tmp_v) vertices.add(f);
			for (int i : new int[] {0, 3, 2, 0, 2, 1}) indices.add(max_index + i);
			max_index += 4;
		}
		return max_index;
	}

}
