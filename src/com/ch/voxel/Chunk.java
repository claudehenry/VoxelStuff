package com.ch.voxel;

import java.util.ArrayList;
import java.util.List;

import com.ch.Model;
import com.ch.SimplexNoise;
import com.ch.Util;
import com.ch.math.Matrix4f;

/**
 * in this code is responsible for generating and loading 3D models from a text file
 * representing a cubic grid of blocks. It provides methods for generating new vertices
 * and indices based on the block layout, and it also loads the model from the text
 * file. The class has several fields and methods for handling different types of
 * blocks and their associated data.
 */
public class Chunk {

	public static final int CHUNK_SIZE = 64;
	private static final int CHUNK_SIZE_SQUARED = CHUNK_SIZE * CHUNK_SIZE;
	private static final int CHUNK_SIZE_CUBED = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE;

	private Block[] blocks;
	public int x, y, z;
	private Model model;
	
 /**
  * retrieves a model object from storage, creates one if necessary, and returns it.
  * 
  * @returns a `Model` object.
  * 
  * 	- `model`: A `Model` object that represents the generated model. This object
  * contains the results of the model generation process.
  * 	- `to_gen_model`: A boolean flag indicating whether the model has been generated
  * or not. If set to `true`, the model has been generated, and if set to `false`, it
  * has not.
  */
	public Model getModel() {
		if (to_gen_model) {
			createModel();
			to_gen_model = false;
		}
		return model;
	}
	
 /**
  * initializes a matrix that translates a given distance (represented by `x`, `y`,
  * and `z`) from the origin along the X, Y, and Z axes, respectively.
  * 
  * @returns a matrix representing a translation of a certain distance in the x, y,
  * and z dimensions.
  * 
  * 	- The Matrix4f object represents a 4x4 transformation matrix, which can be used
  * to perform transformations such as translations, rotations, and scaling in 3D space.
  * 	- The initTranslation method of the Matrix4f class is used to create a new matrix
  * instance with a translation component in the (0, 0, 0) position.
  * 	- The x, y, and z parameters passed to the initTranslation method represent the
  * translation vector in the respective coordinate directions.
  * 	- The resulting matrix represents a translation of (x * CHUNK_SIZE, y * CHUNK_SIZE,
  * z * CHUNK_SIZE) from the origin, where CHUNK_SIZE is a constant representing the
  * size of each chunk in the 3D space.
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
  * updates the block state for a given chunk of voxels based on its neighbors,
  * considering their states and proximity to each other.
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
  * sets a parameter to false.
  */
	public void toGenModel() { toGenModel(false); };
	
 /**
  * generates a model from a set of vertices, indices, and blocks. It recursively
  * traverses the blocks and updates the vertex and index arrays accordingly. It also
  * filters and re-indexes the data for textured cubes.
  * 
  * @param now condition for generating a new model, where it is set to `true` to
  * generate a new model and `false` otherwise.
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
  * loads a 3D model from an array of vertices and indices using the `Model.load()`
  * method, storing it in the class instance field `model`.
  */
	private void createModel() {
		this.model = Model.load(Util.toFloatArray(vertices), Util.toIntArray(indices));
	}
	
 /**
  * generates a model based on input parameters and returns the generated model.
  * 
  * @returns a `Model` object containing the generated model.
  * 
  * The `model` object is a `Model` type variable that represents a model generated
  * by the toGenModel method call. It has various attributes and methods related to
  * the model, such as its structure, constraints, and equations.
  */
	public Model genModel() {
		
		toGenModel(true);
		
		return this.model;
	}

 /**
  * generates vertices and indices for a 3D mesh based on the provided block's geometry,
  * including faces, edges, and vertices. It increases the maximum index value for
  * each new addition.
  * 
  * @param vertices 2D array of floating-point values that will be used to store the
  * vertices of the mesh being generated, and it is passed by reference to the function
  * for modification.
  * 
  * 	- The list `vertices` contains a sequence of floating-point numbers (representing
  * 3D vertices).
  * 	- Each element in the list is represented as a single float value.
  * 	- The list may have any number of elements, but the function does not provide any
  * guarantees about its length.
  * 
  * In addition to `vertices`, the function also operates on a list `indices` of
  * integers, which represents the indices of the vertices in the original mesh. The
  * function adds, removes, and updates the elements of both lists based on the
  * properties of the `block` parameter.
  * 
  * @param indices 3D indices of the vertices in the mesh, which are added to the
  * `vertices` list and used to calculate the new index of the next vertex to be processed.
  * 
  * 	- `indices` is a list of integers that represents the vertices of a 3D mesh.
  * 	- The size of `indices` depends on the number of blocks in the mesh, and each
  * index in the list corresponds to a vertex in the mesh.
  * 	- Each element in `indices` is an integer between 0 and the total number of
  * vertices in the mesh - 1, indicating the position of a vertex in the mesh.
  * 	- The indices in `indices` are unique, with no duplicates or gaps in the list.
  * 	- The order of the indices in `indices` corresponds to the winding order of the
  * vertices in the mesh.
  * 
  * In summary, `indices` is a list of integers that represents the vertices of a 3D
  * mesh, and its properties are explained above.
  * 
  * @param block 3D block to be rendered, and the function generates additional vertices
  * and indices for the block based on its type.
  * 
  * 	- `ft`: The `float` array contains the vertex positions for the front face of the
  * block.
  * 	- `bk`: The `float` array contains the vertex positions for the back face of the
  * block.
  * 	- `bt`: The `float` array contains the vertex positions for the top face of the
  * block.
  * 	- `tp`: The `float` array contains the vertex positions for the pillar of the block.
  * 	- `lt`: The `float` array contains the vertex positions for the left top of the
  * block.
  * 	- `rt`: The `float` array contains the vertex positions for the right top of the
  * block.
  * 
  * The function then serializes the `vertices` and `indices` lists to create a new
  * mesh object.
  * 
  * @param max_index 0-based index of the current block being generated, and is
  * incremented each time a new set of vertices or indices are added to the list.
  * 
  * @returns an integer representing the maximum index added to the `indices` list
  * after generating vertices and indices for a given block.
  * 
  * 	- The `max_index` variable is used to keep track of the maximum index of the
  * vertices and indices arrays.
  * 	- The `vertices` array is used to store the coordinates of the blocks in the
  * scene. It is initialized with four elements: (x, y, z, 0), (x+1, y, z, 0), (x+1,
  * y+1, z, 0), and (x, y, z, 0).
  * 	- The `indices` array is used to store the indices of the vertices in the scene.
  * It is initialized with four elements: 0, 1, 2, and 3.
  * 	- The `block` parameter represents a block in the scene, which determines the
  * type of vertices and indices that are generated. The `ft`, `bk`, `bt`, `tp`, and
  * `lt` fields of the `block` object are used to determine which types of vertices
  * and indices are generated.
  * 	- The `x`, `y`, and `z` fields of the `block` object represent the coordinates
  * of the block in the scene.
  * 	- The `vertices` and `indices` arrays are grown by four elements each time a new
  * type of vertex or index is added, indicating the number of additional vertices and
  * indices generated for that block.
  * 	- The returned value of the `gen` function is the maximum index of the `indices`
  * array, which represents the total number of vertices in the scene.
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
