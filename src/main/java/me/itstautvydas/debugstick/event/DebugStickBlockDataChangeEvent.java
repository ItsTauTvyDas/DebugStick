package me.itstautvydas.debugstick.event;

import org.bukkit.block.Block;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.itstautvydas.debugstick.blockstates.BlockStates;
import me.itstautvydas.debugstick.helper.ModifiedData;

public class DebugStickBlockDataChangeEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Player player = null;
	private Block block = null;
	private String state = null;
	private Object value = null;
	private Object oldValue = null;
	private BlockStates type = null;
	private boolean isDataSame = false;
	
	public DebugStickBlockDataChangeEvent(ModifiedData dc, Player player) {
		this.player = player;
		this.block = dc.block;
		this.state = dc.state;
		this.value = dc.value;
		this.oldValue = dc.oldValue;
		this.type = dc.type;
		this.isDataSame = dc.isDataSame;
	}

	public DebugStickBlockDataChangeEvent(Player player, Block block, String state, Object value, Object oldValue, BlockStates type,
			boolean dataChanged, boolean isDataSame) {
		this.player = player;
		this.block = block;
		this.state = state;
		this.value = value;
		this.oldValue = oldValue;
		this.type = type;
		this.isDataSame = isDataSame;
	}
	
	public static HandlerList getHandlerList() {       
		return handlers;   
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getState() {
		return state;
	}
	
	/**
	 * @return String, boolean or int.
	 */
	public Object getBlockData() {
		return value;
	}
	
	/**
	 * @return String, boolean or int.
	 */
	public Object getOldBlockData() {
		return oldValue;
	}
	
	public BlockStates getBlockState() {
		return type;
	}
	
	/**
	 * @return true, if data was not same as previous.
	 */
	public boolean hasDataActuallyChanged() {
		return !isDataSame;
	}

}
