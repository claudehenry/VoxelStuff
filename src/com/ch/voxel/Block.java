package com.ch.voxel;

/**
 * represents a 3D coordinate with various flags for its properties.
 * Fields:
 * 	- z (int): represents the vertical position of a block in a 3D space.
 * 	- rt (boolean): of the Block class represents whether the block is currently being
 * mined.
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
