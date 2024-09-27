package com.ch.voxel;

/**
 * Represents a three-dimensional block with attributes to track its position and
 * orientation in a virtual environment.
 *
 * - z (int): represents a three-dimensional coordinate.
 *
 * - rt (boolean): Represents a boolean value indicating a right-facing orientation.
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
