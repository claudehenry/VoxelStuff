package com.ch.voxel;

import java.awt.Color;

import com.ch.Camera;
import com.ch.Shader;


/**
 * in the given code is responsible for managing a 3D world, including its chunk
 * structure, rendering, and collision detection. The class has several methods for
 * generating new chunks based on the player's position, updating block positions,
 * and rendering the chunks using a shader. It also includes a render method that
 * takes a camera and shader as parameters and renders the chunks based on their positions.
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
  * iterates over a grid of chunks, creating and updating blocks within each chunk,
  * and then converting them to a gen model.
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
  * updates the position of the chunk's block based on the user input and checks for
  * collisions with other chunks, updating the chunk's blocks and reassigning them to
  * the new position if necessary.
  * 
  * @param x 2D coordinate of the chunk's position within the world, which is used to
  * update the chunk's position and dimensions based on its z-coordinate.
  * 
  * @param y 2D coordinate of the chunk's position in the world, which is used to
  * determine the correct chunk to update based on the `x` and `z` coordinates.
  * 
  * @param z 3D position of the chunk to be generated, and it is used to determine the
  * correct chunk to be generated based on the differences between the current chunk
  * and the specified chunk at the specified position.
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
  * renders a 3D model using a shader and a camera. It iterates over the chunks of a
  * 3D scene, sets the color of each chunk using its position, and then draws the
  * corresponding 3D model using the shader and camera.
  * 
  * @param s 3D rendering shader that is being applied to the graphics pipeline, and
  * it is used to set the uniform values of the shader using the `unifrom*` methods.
  * 
  * 	- `s`: A `Shader` object representing the current shader being used. Its properties
  * include the shader's name, version, and texture units.
  * 	- `c`: A `Camera` object representing the active camera in the scene. It has
  * properties such as the position, orientation, and field of view.
  * 
  * The function iterates over a 3D grid of chunks, computing the color of each chunk
  * using the current shader and drawing it on the screen using the active camera's
  * view projection matrix.
  * 
  * @param c 3D camera object that determines the viewpoint for rendering the scene.
  * 
  * 	- `Camera c`: This is an instance of the `Camera` class, which represents a camera
  * in the game engine. It has various properties such as `getViewProjection()`,
  * `getModelMatrix()`, and `draw()` that are used to render the 3D model from the
  * specified camera perspective.
  * 	- `Shader s`: This is an instance of the `Shader` class, which represents a shader
  * program in the game engine. It has various properties such as `uniformf()` and
  * `unifromMat4()` that are used to set uniform values and matrix transformations for
  * the shader.
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
