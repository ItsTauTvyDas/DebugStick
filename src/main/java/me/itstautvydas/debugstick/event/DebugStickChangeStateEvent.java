package me.itstautvydas.debugstick.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class DebugStickChangeStateEvent extends Event {
	
	private static final HandlerList handlers = new HandlerList();
	private Location loc;
	private Player player;
	private Action action;
	private boolean shifting = false;
	private String state;
	private String oldState;

	public DebugStickChangeStateEvent(Location loc, Player player, Action action, String oldState, String state, boolean wasShifting) {
		this.loc = loc;;
		this.player = player;
		this.action = action;
		this.shifting = wasShifting;
		this.oldState = oldState;
		this.state = state;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {       
		return handlers;   
	}
	
	public boolean wasShifting() {
		return shifting;
	}
	
	public Location getClickedLocation() {
		return loc;
	}
	
	public Action getClickAction() {
		return action;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public String getState() {
		return state;
	}
	
	public String getOldState() {
		return oldState;
	}
}
