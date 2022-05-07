package me.itstautvydas.debugstick;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import me.itstautvydas.debugstick.blockstates.BlockStates;
import me.itstautvydas.debugstick.event.DebugStickBlockDataChangeEvent;
import me.itstautvydas.debugstick.event.DebugStickChangeStateEvent;
import me.itstautvydas.debugstick.helper.ModifiedData;
import me.itstautvydas.debugstick.support.PlotSquared;
import me.itstautvydas.debugstick.support.WorldGuard;
import me.itstautvydas.debugstick.util.DebugStickUtils;
import me.itstautvydas.debugstick.util.SupportedVersions;

public class DebugStick {
	private DebugStickPlugin plg;
	private Player lastPlayer;
	
	private HashMap<String, String> selectedState;
	private HashMap<String, Material> selectedBlock;
	
	public DebugStick(DebugStickPlugin plg, HashMap<String, String> selectedState,
			HashMap<String, Material> selectedBlock) {
		this.plg = plg;
		this.selectedBlock = selectedBlock;
		this.selectedState = selectedState;
	}
	
	public DebugStickPlugin getPlugin() {
		return plg;
	}
	
	public Player getLastPlayer() {
		return lastPlayer;
	}
	
	private void a(String a, HashMap<String, String> replacements) {
		if (lastPlayer != null) plg.sendMessage(lastPlayer, a.split("\\.")[0], a.split("\\.")[1], a.split("\\.")[2], replacements);
	}
	
	private String b(String a) {
		if (a.split("\\.")[1].matches(".*\\d.*"))
			return a.split("\\.")[1];
		else
			return plg.getMessage(a.split("\\.")[0], a.split("\\.")[1], null);
	}
	
	private String c(String a, HashMap<String, String> replacements) {
		return plg.getMessage(a.split("\\.")[0], a.split("\\.")[1], replacements);
	}

	public boolean click(Player player, Location location, boolean isShifting, Action action/*, byte permissionLevel*/) {
		boolean checkForPermissions = true;//permissionLevel > -1;
		boolean checkForPermissionsForWorldGuard = true;//permissionLevel > 0;
		boolean checkForPermissionsForPlotSquaredPlugin = true;//permissionLevel > 1;
		
		this.lastPlayer = player;
		
		HashMap<String, String> rep = new HashMap<>();
		String pN = player.getName();
		
		Block block = location.getBlock();
		selectedBlock.put(pN, block.getType());
		
		rep.put("%version%", SupportedVersions.getServerVersion());
		rep.put("%player%", player.getName());
		rep.put("%playerdisplayname%", player.getDisplayName());
		rep.put("%world%", player.getWorld().getName());
		rep.put("%material%", b("block-materials."+block.getType().toString().toLowerCase().replace("_", "-")));
		
		/*modify*/ boolean worldGuardCheck = checkForPermissionsForWorldGuard ? 
				(WorldGuard.checkFlag(plg, location, "debugstick", player, true) || WorldGuard.hasBypass(plg, player, false) || plg.hasPermission(player, "worldguard-modify-bypass")) :
					true;
		
		if (!(checkForPermissions ? plg.hasPermission(player, "modify") : true)) {
			if ((boolean)plg.getConfigValue("no-perm-messages", "default-modify")) {
				rep.put("%perm%", plg.getPerm("modify"));
				a("messages.no-permission.no-permission", rep);
				return true;
			}
			return false;
		}
				
		if (!plg.isWorldAllowed(player.getWorld()) && (plg.getConfigValue("allowed-worlds", "bypass-permission", boolean.class) ? !player.hasPermission(plg.getPerm("disabled-world-bypass").replace("{world}", player.getWorld().getName())) : true)) {
			if ((boolean)plg.getConfigValue("no-perm-messages", "disabled-world")) {
				rep.put("%perm%", plg.getPerm("disabled-world-bypass").replace("{world}", player.getWorld().getName()));
				a("messages.disabled-world.disabled-world", rep);
				return true;
			}
			
			return false;
		}
		
		boolean canModifyIfPlot = checkForPermissionsForPlotSquaredPlugin ? PlotSquared.canModifyIfPlot(plg, location, player, true) : true;
		boolean canModifyIfRoad = checkForPermissionsForPlotSquaredPlugin ? PlotSquared.canModifyIfRoad(plg, location, player, true) : true;
		
		if (!worldGuardCheck) {
			if ((boolean)plg.getConfigValue("no-perm-messages", "worldguard-modify")) {
				rep.put("%perm%", plg.getPerm("worldguard-modify-bypass"));
				a("messages.worldguard-no-permission.worldguard-no-permission", rep);
				return true;
			}
			
			return false;
		}
		
		if (!(canModifyIfPlot && canModifyIfRoad)) {
			boolean owner = PlotSquared.isOwner(plg, location, player);
			boolean member = PlotSquared.isMember(plg, location, player);
			boolean claimed = PlotSquared.isClaimed(plg, location);
			boolean road = PlotSquared.isRoad(plg, location);

			if ((boolean)plg.getConfigValue("no-perm-messages", "plotsquared-modify")) {
				if (road) {
					rep.put("%perm%", plg.getPerm("plotsquared-modify-roads"));
					a("messages.plotsquared-cannot-modify-road.plotsquared-no-permission", rep);
					return true;
				} else if (owner && claimed && !plg.hasPermission(player, "plotsquared-modify-owned-plots")) {
					rep.put("%perm%", plg.getPerm("plotsquared-modify-owned-plots"));
					a("messages.plotsquared-cannot-modify-owned-plot.plotsquared-no-permission", rep);
					return true;
				} else if (member && claimed && !plg.hasPermission(player, "plotsquared-modify-friend-plots")) {
					rep.put("%perm%", plg.getPerm("plotsquared-modify-friend-plots"));
					a("messages.plotsquared-cannot-modify-friends-plot.plotsquared-no-permission", rep);
					return true;
				} else if (!member && !owner && !claimed && !plg.hasPermission(player, "plotsquared-modify-unclaimed-plots")) {
					rep.put("%perm%", plg.getPerm("plotsquared-modify-unclaimed-plots"));
					a("messages.plotsquared-cannot-modify-unclaimed-plot.plotsquared-no-permission", rep);
					return true;
				} else if (!member && !owner && claimed && !plg.hasPermission(player, "plotsquared-modify-claimed-plots")) {
					rep.put("%perm%", plg.getPerm("plotsquared-modify-claimed-plots"));
					a("messages.plotsquared-cannot-modify-claimed-plot.plotsquared-no-permission", rep);
					return true;
				}
			}
			
			return false;
		}
		
		if (action.equals(Action.LEFT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if (BlockStates.fromMaterial(block.getType()) != null) {
				BlockStates b_ = BlockStates.fromMaterial(block.getType());
				if (selectedState.get(pN) == "" || selectedBlock.get(pN) != block.getType() || selectedBlock.get(pN) == null
						|| selectedState.get(pN) == null || !b_.hasState(selectedState.get(pN)))
					selectedState.put(pN, b_.getDefaultState());
				
				rep.put("%state%", b("block-states."+selectedState.get(pN).replace("_", "-")));
				if (action.equals(Action.LEFT_CLICK_BLOCK)) {
					rep.put("%oldstate%", b("block-states."+selectedState.get(pN).replace("_", "-")));
					try {
						selectedState.put(pN, DebugStickUtils.nextState(b_, selectedState.get(pN), isShifting));
					} catch (NullPointerException e) {
						plg.getLogger().warning("Something went wrong getting block's next state.");
						a("messages.error-getting-next-state.error-messages", rep);
						return true;
					}
					
					rep.put("%state%", b("block-states."+selectedState.get(pN).replace("_", "-")));
					Object blockdata = DebugStickUtils.getBlockData(block, selectedState.get(pN));
					
					if (DebugStickUtils.hasDataErrors(blockdata) || blockdata == null) {
						String error = DebugStickUtils.readDataError(blockdata == null ?
								-4 : blockdata);
						if (blockdata instanceof Integer && (int)blockdata != -4) {
							plg.getLogger().warning("Something went wrong getting block's data, reason: "+error);
							rep.put("%reason%", c("error-reasons."+error.replace(" ", "-"), rep));
							a("messages.error-getting-block-data.error-messages", rep);
						} else {
							a("messages.no-such-state.no-such-state", rep);
						}
						return true;
					} else {
						DebugStickChangeStateEvent event2 = new DebugStickChangeStateEvent(location, player, action, rep.get("%oldstate%").replace("-", "_"), selectedState.get(pN), isShifting);
						Bukkit.getServer().getPluginManager().callEvent(event2);
						rep.put("%blockdata%", b("block-data-values."+blockdata.toString().toLowerCase().replace("_", "-")));
						a("messages.selected-state.selecting-state", rep);
					}
					return true;
				} else if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
					rep.put("%state%", b("block-states."+selectedState.get(pN).replace("_", "-")));
					Object blockdata = DebugStickUtils.getBlockData(block, selectedState.get(pN));
					Object next = DebugStickUtils.nextStateValue(b_, selectedState.get(pN), blockdata, isShifting);
					ModifiedData dc = DebugStickUtils.changeBlockData(block, selectedState.get(pN), next);
					
					if (dc != null && dc.getErrorInt() == 0) {
						rep.put("%blockdata%", b("block-data-values."+next.toString().replace("_", "-")));
						rep.put("%oldblockdata%",
								blockdata != null ? b("block-data-values."+blockdata.toString().replace("_", "-")) : "");
						a("messages.data-changed.changing-data", rep);
						
						DebugStickBlockDataChangeEvent event2 = new DebugStickBlockDataChangeEvent(dc, player);
						Bukkit.getServer().getPluginManager().callEvent(event2);
					} else {
						plg.getLogger().warning("Something went wrong changing block's data, reason: "+dc.readError());
						rep.put("%reason%", c("error-reasons."+dc.readError().replace(" ", "-"), rep));
						a("messages.error-changing-data.error-messages", rep);
					}
					return true;
				}
			} else {
				a("messages.no-properties.no-properties", rep);
				return true;
			}
		}
		
		return false;
	}
}
