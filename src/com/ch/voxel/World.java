package com.ch.voxel;

import java.awt.Color;

import com.ch.Camera;
import com.ch.Shader;


public class World {

	private int x, y, z; // in chunks
			// private int cunk_max;
	private Chunk[][][] chunks; // TODO: unwrap
	private int W = 4, H = 2, D = 4;

	public World() {
		x = 0;
		y = 0;
		z = 0;
		chunks = new Chunk[W][H][D];
		gen();
	}
	
 /**
  * This function "gen" creates an array of arrays of arrays of Chunk objects (3D),
  * and for each Chunk object:
  * 
  * 1/ Instantiates it with position (i-W/2+x/, j-H/2+y/, k-D/2+z)
  * 2/ Calls its "updateBlocks" method
  * 3/ Calls its "toGenModel" method
  * 
  * Effectively generating and updating blocks for the 3D chunk grid.
  */
	private void gen() {
		for (int i = 0; i < W; i++)
			for (int j = 0; j < H; j++)
				for (int k = 0; k < D; k++) {
					chunks[i][j][k] = new Chunk(i - W / 2 + x, j - H / 2 + y, k - D / 2 + z);
					chunks[i][j][k].updateBlocks();
					chunks[i][j][k].toGenModel();
				}
	}

 /**
  * This function appears to update the `chunks` array of a `World` object with a new
  * chunk at the current position `_x`, `_y`, and `_z`, based on the current grid size
  * `W` and height `H`. It checks for collisions with existing chunks and updates the
  * blocks accordingly. If a collision occurs within a certain distance `D`, it recreates
  * the chunk at the corrected position. The function also includes logic for updating
  * the chunk borders and creating new chunks when moving towards the edges of the world.
  * 
  * @param x The `x` input parameter is used to update the position of the current
  * chunk being generated. It represents the new x-coordinate for the chunk and is set
  * to the `_x` value if the chunk has not been completely generated or set to the
  * original x-coordinate if the chunk has been fully generated.
  * 
  * @param y The `y` parameter is used to determine the position of the generated chunk
  * within the map's vertical dimension (height). It determines the top-most point of
  * the generated chunk within the world map.
  * 
  * @param z The `z` input parameter is used to specify the depth (level) of the new
  * chunk being generated.
  */
	public void updatePos(float x, float y, float z) {
		final int _x = (int) (x / Chunk.CHUNK_SIZE);
		final int _y = 0;//(int) (y / Chunk.CHUNK_SIZE);
		final int _z = (int) (z / Chunk.CHUNK_SIZE);

		if (this.x == _x && this.y == _y && this.z == _z) { // short circuit
															// check for any
															// change
			//System.out.println("hello");
			return;
		}
		
		int wx = this.x;
		int wy = this.y;
		int wz = this.z;
		
//		class internal_chunk_thread extends Thread {
//			
//		private int  wx, wy, wz;
//		
//		void set(int x, int y, int z) {
//			this.wx = x;
//			this.wy = y;
//			this.wz = z;
//		}
//			
//		public void run() {

		/*
		 * all logic is unwrapped because its more efficient.. while its a pain
		 * to code and read.. tradeoff taken :D
		 */

		/* dont think these cases occure
		if (this.x != _x && this.y != _y && this.z != _z) {
			if (this.x < _x) {
				if (this.y < _y) {
					if (this.z < _z) {
						
					} else {
						
					}
				} else {
					if (this.z < _z) {
						
					} else {
						
					}
				}
			} else {
				if (this.y < _y) {
					if (this.z < _z) {
						
					} else {
						
					}
				} else {
					if (this.z < _z) {
						
					} else {
						
					}
				}
			}
		} else if (this.x != _x && this.y != _y) {
			if (this.x < _x) {
				if (this.y < _y) {
					
				} else {
					
				}
			} else {
				if (this.y < _y) {
					
				} else {
					
				}
			}
		} else if (this.x != _x && this.z != _z) {
			if (this.x < _x) {
				if (this.z < _z) {
					
				} else {
					
				}
			} else {
				if (this.z < _z) {
					
				} else {
					
				}
			}
		} else if (this.y != _y && this.z != _z) {
			if (this.y < _y) {
				if (this.z < _z) {
					
				} else {
					
				}
			} else {
				if (this.z < _z) {
					
				} else {
					
				}
			}
		} else 
		*/
		if (wx != _x) {
			if (wx < _x) {
				
			} else {
				
			}
		} else if (wy != _y) {
			if (wy < _y) {
				
			} else {
				
			}
		} else if (wz != _z) {
			if (wz < _z) {
				int dif = _z - wz;
				if (dif > D) {
					wx = _x;
					wy = _y;
					wz = _z;
					gen();
					return;
				} else {
					Chunk[][][] n_chunks = new Chunk[W][H][D];
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++)
							for (int k = 0; k < D - 1; k++) {
								n_chunks[i][j][k] = chunks[i][j][k + 1];
							}
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++) {
							n_chunks[i][j][D - 1] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, (D - 1) - D / 2 + _z);
							n_chunks[i][j][D - 1].updateBlocks();
							n_chunks[i][j][D - 1].toGenModel();
						}
					World.this.chunks = n_chunks;
				}
			} else {
				int dif = wz - _z;
				if (dif > D) {
					wx = _x;
					wy = _y;
					wz = _z;
					gen();
					return;
				} else {
					Chunk[][][] n_chunks = new Chunk[W][H][D];
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++)
							for (int k = 1; k < D; k++) {
								n_chunks[i][j][k] = chunks[i][j][k - 1];
							}
					for (int i = 0; i < W; i++)
						for (int j = 0; j < H; j++) {
							n_chunks[i][j][0] = new Chunk(i - W / 2 + _x, j - H / 2 + _y, 0 - D / 2 + _z);
							n_chunks[i][j][0].updateBlocks();
							n_chunks[i][j][0].toGenModel();
						}
					World.this.chunks = n_chunks;
				}
			}
		}
//		
//		}
//		
//		};
//		internal_chunk_thread t = new internal_chunk_thread();
//		t.set(this.x, this.y, this.z);
//		t.start();
		
		this.x = _x;
		this.y = _y;
		this.z = _z;
		
		/* welp... this logic sure looks aweful */
	}

 /**
  * This function renders a 3D grid of chunks using a shader. It loops through each
  * chunk and sets the color and modelview-projection matrices for each one using the
  * chip's model matrix.
  * 
  * @param s The `s` input parameter is a `Shader` object that the `render` function
  * uses to set the colors and modelview projection matrix for each chunk being rendered.
  * 
  * @param c The `c` parameter is a `Camera` object that represents the camera used
  * to view the 3D scene. The function uses this camera to perform transformations on
  * the chunk models before drawing them. Specifically:
  * 
  * 	- The function multiplies the camera's view projection matrix by each chunk's
  * model matrix to transform the chunk's vertices into camera space.
  * 	- It then uses the resulting transformation matrix (MVP = Model View Projection)
  * to draw the transformed chunk models.
  */
	public void render(Shader s, Camera c) {
		for (int i = 0; i < W; i++)
			for (int j = 0; j < H; j++)
				for (int k = 0; k < D; k++) {
					Chunk ch = chunks[i][j][k];
					if (ch != null) { // just in case for now although i dont suspect it will ever be
	//					float r = (W - i) / (float) W;
	//					float g = j / (float) H;
	//					float b = k / (float) D;
						Color cl = new Color(("" + ch.x + ch.y + ch.z + (ch.x * ch.z) + (ch.y * ch.y)).hashCode());
						
						float r = cl.getRed() / 255f;
						float g = cl.getGreen() / 255f;
						float b = cl.getBlue() / 255f;
						s.uniformf("color", r, g, b);
						s.unifromMat4("MVP", (c.getViewProjection().mul(ch.getModelMatrix())));
						ch.getModel().draw();
					}
				}
	}

	// public

}


