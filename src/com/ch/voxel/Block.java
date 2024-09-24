package com.ch.voxel;

/**
 * Represents a three-dimensional block with properties in space.
 *
 * - z (int): represents a three-dimensional coordinate.
 *
 * - rt (boolean): represents a boolean value indicating whether the block has a right
 * side facing outward.
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
