package com.ch.voxel;

/**
 * represents a block with positional and boolean attributes for various properties,
 * including flexibility, breakability, and top/bottom placement.
 * Fields:
 * 	- z (int): of the Block class represents an integer value that represents the
 * depth or height of the block in the 3D space.
 * 	- rt (boolean): of the Block class represents a boolean value indicating whether
 * the block is rotated to the right.
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
