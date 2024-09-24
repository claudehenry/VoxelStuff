package com.ch.voxel;

import java.util.ArrayList;
import java.util.List;

import com.ch.Model;
import com.ch.SimplexNoise;
import com.ch.Util;
import com.ch.math.Matrix4f;

/**
 * Represents a 3D grid of blocks in a game environment. It is responsible for
 * generating and updating the block structure based on noise patterns, and it provides
 * methods to create a model of the chunk for rendering purposes. The class also
 * handles generation of vertices and indices for the chunk's geometry.
 */
public class Chunk {

	public static final int CHUNK_SIZE = 64;
	private static final int CHUNK_SIZE_SQUARED = CHUNK_SIZE * CHUNK_SIZE;
	private static final int CHUNK_SIZE_CUBED = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE;

	private Block[] blocks;
	public int x, y, z;
	private Model model;
	
	/**
	 * Returns a pre-generated model or creates one if it doesn't exist, then returns it.
	 * It checks the `to_gen_model` flag to determine whether to create a new model. The
	 * created model is returned once for each call of this method.
	 *
	 * @returns a generated `model` object if `to_gen_model` is true.
	 */
	public Model getModel() {
		if (to_gen_model) {
			createModel();
			to_gen_model = false;
		}
		return model;
	}
	
	/**
	 * Initializes a new 4x4 matrix with translation components set to x, y, and z
	 * coordinates scaled by CHUNK_SIZE. It returns this newly initialized matrix
	 * representing the model's position in 3D space. The resulting matrix is based on
	 * the values of x, y, and z.
	 *
	 * @returns a matrix representing translation of chunks.
	 * The matrix is initialized with translation values based on coordinates x, y, and
	 * z multiplied by chunk size.
	 * Translation values are in the form of (x, y, z) * CHUNK_SIZE.
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
	 * Iterates through a three-dimensional array of blocks, updating each block's neighbor
	 * flags based on the presence or absence of adjacent blocks within the same chunk
	 * or neighboring chunks.
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
	 * Delegates its execution to another instance with a boolean argument set to false,
	 * implying it is called with default or initial settings. This suggests a method for
	 * generating a model without custom parameters. It simplifies the interface by
	 * providing a single entry point.
	 */
	public void toGenModel() { toGenModel(false); };
	
	/**
	 * Generates a model based on an array of blocks, updating vertices and indices arrays.
	 * It iterates through each block, generating data using the `gen` method, which is
	 * not shown here. The generated model is then created if a boolean flag `now` is true.
	 *
	 * @param now generation of the model, where if `true`, it creates the model and sets
	 * `to_gen_model` to `false`, otherwise it leaves `to_gen_model` as is or initially
	 * sets it to `true`.
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
	 * Loads a model from predefined vertices and indices into an object called `model`.
	 * The vertices are converted to floats, while the indices are converted to integers.
	 * This suggests that the model is being loaded for rendering or simulation purposes.
	 */
	private void createModel() {
		this.model = Model.load(Util.toFloatArray(vertices), Util.toIntArray(indices));
	}
	
	/**
	 * Initializes a model by calling another method and returns it. The initial value
	 * of the boolean parameter is true, indicating that the initialization process is
	 * explicit. The method's purpose is to generate or initialize an instance of the
	 * Model class.
	 *
	 * @returns an instance of a pre-generated model stored in the `model` field.
	 */
	public Model genModel() {
		
		toGenModel(true);
		
		return this.model;
	}

	/**
	 * Generates vertices and indices for a block representation in 3D space based on its
	 * orientation (ft, bk, bt, tp, lt, rt) and adds them to respective lists. It returns
	 * the updated maximum index of the list.
	 *
	 * @param vertices 3D vertex data of an object, which is modified by adding new
	 * vertices based on the block's orientation and position.
	 *
	 * Accumulates float values that define 3D vertices of block faces.
	 *
	 * @param indices 3D object's index data, which is appended to and incremented
	 * throughout the function as new faces are added based on the block orientations
	 * specified by other input parameters.
	 *
	 * Indices is populated with integers and undergoes several array manipulations using
	 * loops. The resulting sequence has a pattern of sequential additions with some
	 * specific offsets. It contains six types of index sequences corresponding to different
	 * block faces.
	 *
	 * @param block 3D block being processed, specifying its position and orientation
	 * through its fields (`x`, `y`, `z`) and flags (`ft`, `bk`, `bt`, `tp`, `lt`, `rt`).
	 *
	 * Add four vertices to `vertices` list and six indices to `indices` list for each
	 * face of the block if present.
	 * Block has six boolean fields representing its faces (ft, bk, bt, tp, lt, rt).
	 *
	 * @param max_index current maximum index value to be used for adding new indices to
	 * the `indices` list without overwriting existing ones.
	 *
	 * @returns the updated maximum index value.
	 *
	 * The return value is an integer representing the updated maximum index used for
	 * vertex and index list additions. The values added to the lists follow specific
	 * patterns based on flag conditions.
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
