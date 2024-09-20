package com.ch.voxel;

/**
 * Represents a three-dimensional spatial unit with defined coordinates and properties.
 *
 * - z (int): is an integer representing a three-dimensional coordinate value.
 *
 * - rt (boolean): is a boolean variable indicating a right-facing direction of an
 * object or entity.
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
