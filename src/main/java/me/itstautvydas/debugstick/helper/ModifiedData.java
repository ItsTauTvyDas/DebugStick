package me.itstautvydas.debugstick.helper;

import org.bukkit.block.Block;

import me.itstautvydas.debugstick.blockstates.BlockStates;
import me.itstautvydas.debugstick.util.DebugStickUtils;

public class ModifiedData {
	
	public Block block;
	public String state;
	public Object value;
	public Object oldValue;
	public BlockStates type;
	public boolean isDataSame;
	private int error = 0;
	
	public ModifiedData(int error) {
		this.error = error;
	}
	
	public ModifiedData(Block block, String state, Object value, Object oldValue, BlockStates type) {
		this.block = block;
		this.state = state;
		this.type = type;
		this.value = value;
		this.oldValue = oldValue;
	}
	
	public int getErrorInt() {
		return error;
	}
	
	/**
	 * @see me.itstautvydas.debugstick.util.DebugStickUtils#readDataError(Object)
	 * @return
	 */
	public String readError() {
		return DebugStickUtils.readDataError(error);
	}
	
}
