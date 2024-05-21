package com.ch.voxel;

/**
 * represents a three-dimensional block with x, y, and z coordinates and various
 * boolean flags for its properties.
 * Fields:
 * 	- z (int): represents the depth value of the block in the 3D space.
 * 	- rt (boolean): represents whether the block has been right-clicked on by a player.
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
