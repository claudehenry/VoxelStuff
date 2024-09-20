package com.ch.voxel;

import java.util.ArrayList;
import java.util.List;

import com.ch.Model;
import com.ch.SimplexNoise;
import com.ch.Util;
import com.ch.math.Matrix4f;

/**
 * Represents a three-dimensional area of blocks in a game world, generated using
 * Simplex Noise to create terrain. It has methods for updating block relationships
 * and generating model data for rendering. The class also provides access to the
 * generated model and its transformation matrix.
 */
public class Chunk {

	public static final int CHUNK_SIZE = 64;
	private static final int CHUNK_SIZE_SQUARED = CHUNK_SIZE * CHUNK_SIZE;
	private static final int CHUNK_SIZE_CUBED = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE;

	private Block[] blocks;
	public int x, y, z;
	private Model model;
	
	/**
	 * Returns a model, optionally creating it if necessary. It checks a flag to determine
	 * if creation is needed and calls `createModel` if so, then resets the flag. The
	 * created or existing model is returned as a result.
	 *
	 * @returns a generated or cached instance of the Model class.
	 */
	public Model getModel() {
		if (to_gen_model) {
			createModel();
			to_gen_model = false;
		}
		return model;
	}
	
	/**
	 * Initializes a 4x4 transformation matrix with a translation component that moves
	 * an object by a certain offset (x * CHUNK_SIZE, y * CHUNK_SIZE, z * CHUNK_SIZE)
	 * based on its position (x, y, z). A new instance of Matrix4f is created and initialized.
	 *
	 * @returns a translation matrix.
	 * The matrix represents position (x * CHUNK_SIZE, y * CHUNK_SIZE, z * CHUNK_SIZE)
	 * in 3D space.
	 * It initializes a new Matrix4f object with this translation.
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
	 * Checks each block's neighbors and updates its flags, indicating whether it is
	 * adjacent to an existing block or not. It iterates over a three-dimensional array
	 * of blocks, examining each block's north-south, east-west, and up-down neighbors.
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
	 * Calls another method with a default argument value of false when called directly.
	 * This suggests that the function has an overloaded variant that takes a boolean
	 * parameter. The purpose of this function is to initiate model generation without
	 * specifying additional parameters or values.
	 */
	public void toGenModel() { toGenModel(false); };
	
	/**
	 * Generates a model by iterating through an array of blocks, generating vertices and
	 * indices for each block, and storing them in the `vertices` and `indices` arrays.
	 * The generated model is then loaded into memory if the `now` parameter is true.
	 *
	 * @param now generation of a model immediately, as opposed to deferring it for later.
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
	 * Initializes a 3D model by loading data from an array of vertices and indices into
	 * the `model` object using the `load` method provided by the `Model` class. The data
	 * is converted to float and int arrays, respectively, using utility methods before
	 * being loaded.
	 */
	private void createModel() {
		this.model = Model.load(Util.toFloatArray(vertices), Util.toIntArray(indices));
	}
	
	/**
	 * Generates a model and returns it. It is called with an argument that triggers the
	 * generation process by executing the `toGenModel` method. The generated model is
	 * stored in the `model` property, which is then returned.
	 *
	 * @returns a generated model instance stored in the `model` variable.
	 */
	public Model genModel() {
		
		toGenModel(true);
		
		return this.model;
	}

	/**
	 * Generates vertices and indices for a 3D cube based on the properties of a given
	 * block, adding the generated data to corresponding lists. It handles six sides of
	 * a cube: front, back, top, bottom, left, and right.
	 *
	 * @param vertices 3D model's vertex data, where vertices are appended to it with
	 * newly generated coordinates for each block face.
	 *
	 * Accumulates floats representing 3D vertex coordinates.
	 * Can grow by adding new float values iteratively.
	 *
	 * @param indices 3D object's face indices, which are incremented and added to as
	 * vertices are generated for each of its constituent parts.
	 *
	 * Indices are added to this list from four different blocks (ft, bk, bt, tp) with
	 * specific values based on block flags and their respective vertex coordinates. The
	 * indices for each block are in a cyclic pattern of [0, 1, 2, 0, 2, 3] or [0, 3, 2,
	 * 0, 2, 1].
	 *
	 * @param block 3D block that is being processed, and its properties are used to
	 * determine which faces of the block should be generated.
	 *
	 * Set X-coordinate to x.
	 * Set Y-coordinate to y.
	 * Set Z-coordinate to z.
	 * FT is a boolean property.
	 * BK is a boolean property.
	 * BT is a boolean property.
	 * TP is a boolean property.
	 * LT is a boolean property.
	 * RT is a boolean property.
	 *
	 * @param max_index current maximum index that should be assigned to new vertices and
	 * indices added to the `vertices` and `indices` lists, respectively.
	 *
	 * @returns an updated maximum index value.
	 *
	 * The method returns an integer value representing the updated maximum index.
	 * The value is incremented by 4 for each valid block condition met.
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
