package com.ch.voxel;

/**
 * Defines a basic block structure with properties for spatial coordinates and boolean
 * flags to represent various directions in three-dimensional space.
 *
 * - z (int): is an integer representing a three-dimensional coordinate in space.
 *
 * - rt (boolean): is a boolean variable indicating the right side of the block.
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
