package com.ch.voxel;

/**
 * Represents a 3D block with its position coordinates (x, y, z) and six boolean flags
 * indicating various directional faces (front, back, top, bottom, left, right).
 *
 * - z (int): is an integer attribute of the Block class.
 *
 * - rt (boolean): represents a boolean indicating whether the Block has its right
 * side facing.
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
