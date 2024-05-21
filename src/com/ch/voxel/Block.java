package com.ch.voxel;

/**
 * represents a three-dimensional spatial location with various flags for its properties,
 * such as whether it is solid or not.
 * Fields:
 * 	- z (int): represents the depth of the block in the 3D space.
 * 	- rt (boolean): in the Block class represents whether the block has been right-clicked
 * on by the player.
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
