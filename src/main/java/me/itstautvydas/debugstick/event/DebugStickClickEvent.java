package me.itstautvydas.debugstick.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;

public class DebugStickClickEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	private Location loc;
	private Player player;
	private Action action;
	private boolean shifting = false;
	private boolean cancelled = false;

	public DebugStickClickEvent(Location loc, Player player, Action action, boolean isShifting) {
		this.loc = loc;;
		this.player = player;
		this.action = action;
		this.shifting = isShifting;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {       
		return handlers;   
	}
	
	public boolean isShifting() {
		return shifting;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public Action getAction() {
		return action;
	}
	
	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}
