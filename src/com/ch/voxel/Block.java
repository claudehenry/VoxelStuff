package com.ch.voxel;

/**
 * represents a three-dimensional block with coordinates and various boolean flags
 * for its properties.
 * Fields:
 * 	- z (int): in the Block class represents the depth value of the block, with
 * possible values ranging from 0 to 255.
 * 	- rt (boolean): represents whether a block is right-clickable on the specific
 * instance of the Block class.
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
