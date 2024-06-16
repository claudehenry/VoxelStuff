package com.ch.voxel;

/**
 * Defines a data structure with multiple attributes and methods for representing a
 * block in a voxel environment.
 * Fields:
 * 	- z (int): represents an integer value representing the block's position in the
 * x-y plane.
 * 	- rt (boolean): in the Block class represents whether a block is on fire.
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
