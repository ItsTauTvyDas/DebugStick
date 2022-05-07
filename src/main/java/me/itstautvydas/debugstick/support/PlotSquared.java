package me.itstautvydas.debugstick.support;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.itstautvydas.debugstick.DebugStickPlugin;
import me.itstautvydas.debugstick.helper.MultiMap;
import me.itstautvydas.debugstick.util.Reflection;

public class PlotSquared {
	
	private PlotSquared() {}
	
	private static String l(DebugStickPlugin plg) {
		return plg.getPlotSquaredClasses()[1].equals("com.intellectualcrafters.plot.object.Location") ?
				"com.intellectualcrafters.plot.object.Location" :
					"com.plotsquared.core.location.Location";
	}
	
	private static String p(DebugStickPlugin plg) {
		return plg.getPlotSquaredClasses()[0].equals("com.intellectualcrafters.plot.object.Plot") ?
				"com.intellectualcrafters.plot.object.Plot" :
					"com.plotsquared.core.plot.Plot";
	}
	
	private static Object convert(DebugStickPlugin plg, Location loc) {
		MultiMap<Class<?>, Object> parameters = new MultiMap<>();
		parameters.add(String.class, loc.getWorld().getName());
		parameters.add(int.class, loc.getBlockX());
		parameters.add(int.class, loc.getBlockY());
		parameters.add(int.class, loc.getBlockZ());
		return Reflection.newInstance(l(plg), Reflection.getClass(l(plg)), parameters, null);
	}
	
	public static boolean isRoad(DebugStickPlugin plugin, Location location) {
		if (plugin.hasPlotSquared())
			return Reflection.getMethod(convert(plugin, location), "isPlotRoad", boolean.class, null, false);
		return false;
	}
	
	public static boolean isPlot(DebugStickPlugin plugin, Location location) {
		if (plugin.hasPlotSquared())
			return Reflection.getMethod(convert(plugin, location), "isPlotArea", boolean.class, null, false);
		return false;
	}
	
	public static boolean isMember(DebugStickPlugin plugin, Location location, Player player) {
		if (plugin.hasPlotSquared())
			if (isPlot(plugin, location)) {
				Object plot = getPlot(plugin, location);
				if (plot != null)
					return Reflection.getMethod(plot, "getMembers", HashSet.class, null, new HashSet<>()).contains(player.getUniqueId());
			}
		return true;
	}
	
	public static boolean isOwner(DebugStickPlugin plugin, Location location, Player player) {
		if (plugin.hasPlotSquared())
			if (isPlot(plugin, location)) {
				Object plot = getPlot(plugin, location);
				MultiMap<Class<?>, Object> x = new MultiMap<>();
				x.add(UUID.class, player.getUniqueId());
				
				if (plot != null)
					return Reflection.getMethod(plot, "isOwner", boolean.class, x, false);
			}
		return true;
	}
	
	public static Object getPlot(DebugStickPlugin plugin, Location location) {
		if (isPlot(plugin, location))
			return Reflection.getMethod(convert(plugin, location), "getPlot", Reflection.getClass(p(plugin)), null, null);
		return null;
	}

	public static boolean isClaimed(DebugStickPlugin plugin, Location location) {
		if (plugin.hasPlotSquared())
			if (isPlot(plugin, location)) {
				Object plot = getPlot(plugin, location);
				if (plot != null)
					return Reflection.getMethod(plot, "hasOwner", boolean.class, null, false);
			}
		return false;
	}
	
	public static boolean canModifyByDefault(DebugStickPlugin plugin, Location location, Player player) {
		return plugin.hasPermission(player, "modify");
	}
	
	public static boolean canModifyIfPlot(DebugStickPlugin plugin, Location location, Player player, boolean def) {
		
		boolean owner = isOwner(plugin, location, player);
		boolean member = isMember(plugin, location, player);
		boolean claimed = isClaimed(plugin, location);
		boolean road = isRoad(plugin, location);
		
		if (!road && isPlot(plugin, location)) {
			if (!owner && !member && !claimed)
				return plugin.hasPermission(player, "plotsquared-modify-unclaimed-plots");
			else if (!owner && !member && claimed)
				return plugin.hasPermission(player, "plotsquared-modify-claimed-plots");
			else if (member && claimed)
				return plugin.hasPermission(player, "plotsquared-modify-friend-plots");
			else if (owner && claimed)
				return plugin.hasPermission(player, "plotsquared-modify-owned-plots");
		}
		
		return def;
	}
	
	public static boolean canModifyIfRoad(DebugStickPlugin plugin, Location location, Player player, boolean def) {
		if (isRoad(plugin, location))
			return player.hasPermission(plugin.getPerm("plotsquared-modify-roads"));
		return def;
	}

	@SuppressWarnings("unchecked")
	public static Set<UUID> getOwners(DebugStickPlugin plugin, Location location) {
		if (isPlot(plugin, location) && isClaimed(plugin, location)) {
			Object plot = getPlot(plugin, location);
			if (plot != null)
				return Reflection.getMethod(plot, "getOwners", Set.class, null, new HashSet<>());
		}
		return null;
	}
}
