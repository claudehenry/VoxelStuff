package com.ch.voxel;

/**
 * Represents a basic 3D block with its coordinates and defines six boolean variables
 * to track neighboring blocks in the x, y, z directions.
 *
 * - z (int): is an integer representing a three-dimensional position coordinate in
 * a block structure.
 *
 * - rt (boolean): represents a boolean variable.
 */
public class Block {
	
	public int x, y, z;
	public boolean ft, bk, tp, bt, lt, rt;
	
	public Block(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.ft = false;
		this.bk = false;
		this.tp = false;
		this.bt = false;
		this.lt = false;
		this.rt = false;
	}

}
