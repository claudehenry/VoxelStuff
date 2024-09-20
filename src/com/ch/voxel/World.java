package com.ch.voxel;

import java.awt.Color;

import com.ch.Camera;
import com.ch.Shader;


/**
 * Manages a three-dimensional world of chunks, generating and updating them as the
 * game's position changes. It handles chunk loading and unloading to optimize
 * performance. The class also provides a render method for drawing the world using
 * shaders and a camera.
 */
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
	 * Initializes and updates a three-dimensional array of Chunk objects, populating it
	 * with instances of Chunk at specified positions. Each chunk instance is updated to
	 * generate its block model. The chunks are initialized across a grid defined by
	 * dimensions W, H, and D.
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
	 * Updates the current position of a chunk in a world based on new coordinates provided
	 * as parameters. If the position has changed, it generates new chunks and updates
	 * the world's chunk array accordingly.
	 *
	 * @param x 3D coordinate position of an object or entity in a world, and is used to
	 * update its chunk position by dividing it by Chunk.CHUNK_SIZE.
	 *
	 * @param y 2D coordinate of the chunk being updated but is immediately replaced with
	 * a hardcoded value of 0 when casting to an integer, effectively rendering it unnecessary.
	 *
	 * @param z 3D coordinate of a chunk and is used to determine when chunks need to be
	 * generated or updated due to changes in the world's height.
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
	 * 3D models associated with chunks of a world, iterating over a 3D array of chunk
	 * data and drawing each chunk's model using a shader program, camera, and uniform
	 * variables. It handles null chunk references and calculates colors from chunk coordinates.
	 *
	 * @param s shader used for rendering, allowing it to set uniforms and matrices such
	 * as colors and model-view-projection transforms.
	 *
	 * Set multiple shader uniforms:
	 * - "color" with values r, g, b
	 * - "MVP" with a 4x4 matrix value resulting from multiplying two matrices together.
	 *
	 * @param c 3D camera, which is used to calculate the model-view-projection (MVP)
	 * matrix and pass it to the shader through the "MVP" uniform.
	 *
	 * Transforms the camera's view projection matrix with the chunk's model matrix using
	 * `mul` operation.
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
