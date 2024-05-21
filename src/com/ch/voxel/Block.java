package com.ch.voxel;

/**
 * represents a three-dimensional block with position and boolean flags for various
 * states.
 * Fields:
 * 	- z (int): represents the vertical position of the block in the 3D space.
 * 	- rt (boolean): in the Block class represents whether the block has been right-clicked
 * on by a player.
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
