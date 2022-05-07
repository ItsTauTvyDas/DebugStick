package me.itstautvydas.debugstick.util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

public class OtherUtil {
	
	public static Material safeValueOf(String a) {
		try {
			return Material.valueOf(a);
		} catch (IllegalArgumentException e) {
			return Material.AIR;
		}
	}
	
	public static void checkBlockStatesClasses() {
		Plugin plg = Bukkit.getPluginManager().getPlugin("DebugStick");
		for (int i : SupportedVersions.getVersionsInt())
			if (SupportedVersions.getInteger(null) > i-1)
				if (!checkBlockStatesClass(i))
					Bukkit.getPluginManager().getPlugin("DebugStick").getLogger().warning(
							"Couldn't load (or it is missing) 1."+i+" version's block states, plugin may not function correctly.");
				else
					try {
						Class<?> c = Class.forName("me.itstautvydas.debugstick.blockstates.BlockStates1"+i);
						c.getMethod("has", String.class);
						c.getDeclaredField("supportedStates");
						c.getDeclaredField("allowedValues");
						c.getDeclaredField("dataValues");
						c.getDeclaredField("materials");
					} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | NoSuchFieldException |
							IllegalArgumentException e) {
						plg.getLogger().warning(
								"Couldn't setup block states for 1."+i+" version, got exception: "+Arrays.asList(
										e.getClass().getName().split("\\.")).get(
												e.getClass().getName().split("\\.").length-1)+".");
					}
	}
	
	public static boolean checkBlockStatesClass(int i) {
		try {
			Class.forName("me.itstautvydas.debugstick.blockstates.BlockStates1"+i);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
}
