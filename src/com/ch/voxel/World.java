package com.ch.voxel;

import java.awt.Color;

import com.ch.Camera;
import com.ch.Shader;


/**
 * has several methods and fields:
 * 
 * 	- `x`, `y`, and `z` are instance variables representing the position of the world
 * in the game environment.
 * 	- `W`, `H`, and `D` are constants representing the size of the world in each dimension.
 * 	- `gen()` is a method that generates new chunk data based on the current position
 * and size of the world.
 * 	- `render(Shader, Camera)` is a method that renders the chunks in the world using
 * a shader and a camera object.
 * 
 * The World Class also has several internal fields and methods that are not shown
 * here, but they are likely used for managing and updating the chunk data and rendering
 * the world.
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
	 * iterates over a 3D grid of chunks, creating new chunks at each position and updating
	 * their blocks and transforming them into a gen model.
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
	 * updates an object's position based on changes to its x, y, and z components. It
	 * checks for conflicts with nearby chunks and generates a new chunk if necessary.
	 * 
	 * @param x 3D coordinates of the position being updated within the Chunk structure,
	 * and it is used to calculate the new value for the `wx`, `wy`, and `wz` variables.
	 * 
	 * @param y 2D coordinate of the point within the Chunk, which is being updated based
	 * on the new values of `x` and `z`.
	 * 
	 * @param z 3D position of the current chunk, which is used to update the chunk's
	 * blocks and models when the `x` and `y` positions change.
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
	 * renders a 3D scene by drawing models based on their positions and orientations,
	 * using a shader to colorize them according to their coordinates.
	 * 
	 * @param s 3D shader instance that receives the rendering output from the function,
	 * which is then used to compute and apply the appropriate color values using the
	 * `uniformf()` method.
	 * 
	 * 	- `s`: A `Shader` object that contains the fragment shader code and various
	 * attributes such as uniform locations, texture units, and attribute pointers.
	 * 	- `c`: A `Camera` object representing the camera used for rendering. It has various
	 * properties such as position, direction, and field of view.
	 * 
	 * The function iterates over each pixel in the scene using a 3D loop, where `i`,
	 * `j`, and `k` are indices into the `chunks` array. For each pixel, it checks if
	 * there is a corresponding chunk in the `chunks` array, and if so, it applies the
	 * following operations:
	 * 
	 * 1/ Calculates the color of the chunk using a hash code based on its position
	 * (`ch.x`, `ch.y`, and `ch.z`).
	 * 2/ Sets the red, green, and blue components of the color to arbitrary values between
	 * 0 and 1 using `s.uniformf()`.
	 * 3/ Multiplies the color with the view-projection matrix of the camera using `s.unifromMat4()`.
	 * 4/ Draws the chunk using the model matrix of the chunk multiplied by the view-projection
	 * matrix.
	 * 
	 * Note that the function does not handle the `s` object's destruction, as it is not
	 * explicitly mentioned in the code snippet provided.
	 * 
	 * @param c 3D camera object used to project the scene onto the viewport, allowing
	 * for rendering of the 3D environment from the specified viewpoint.
	 * 
	 * 	- `c`: A `Camera` object, which represents the camera's perspective on the 3D
	 * scene. It has various attributes such as `getViewProjection()` that returns a
	 * matrix representing the view and projection transformations combined, and
	 * `getModelMatrix()` that returns a matrix representing the model transformation of
	 * the objects in the scene.
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
