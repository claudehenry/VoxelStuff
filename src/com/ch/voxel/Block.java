package com.ch.voxel;

/**
 * Represents a three-dimensional block in space with properties such as coordinates
 * and flags indicating its front, back, top, bottom, left, and right sides.
 *
 * - z (int): represents an integer variable of a block's coordinates.
 *
 * - rt (boolean): represents a boolean variable in the Block class indicating a
 * right-sided property.
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
