package me.itstautvydas.debugstick.support;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.itstautvydas.debugstick.DebugStickPlugin;
import me.itstautvydas.debugstick.helper.MultiMap;
import me.itstautvydas.debugstick.util.Reflection;

public class WorldGuard {
	
	private WorldGuard() {}
	
	public static void register(JavaPlugin plugin) {
		Objects.requireNonNull(plugin);
		
		try {
			Class<?> plgClass = Class.forName("com.sk89q.worldguard.bukkit.WorldGuardPlugin");
			//Class<?> regClass = Class.forName("com.sk89q.worldguard.protection.flags.registry.FlagRegistry");
			//Class<?> exceptionClass = Class.forName("com.sk89q.worldguard.protection.flags.registry.FlagConflictException");
			
			MultiMap<Class<?>, Object> args = new MultiMap<Class<?>, Object>();
			args.add(String.class, "debugstick");
			args.add(boolean.class, true);
			
			Object plg = plgClass.cast(Bukkit.getPluginManager().getPlugin("WorldGuard"));
			Object registry = Reflection.getMethod(plg, "getFlagRegistry", Object.class, null, null);
			Object modify = Reflection.newInstance("com.sk89q.worldguard.protection.flags.StateFlag", Object.class, args, null);
			
			//com.sk89q.worldguard.protection.flags.registry.FlagRegistry registrya = ((com.sk89q.worldguard.bukkit.WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard")).getFlagRegistry();
			//com.sk89q.worldguard.protection.flags.StateFlag modifya = new com.sk89q.worldguard.protection.flags.StateFlag("debugstick", true);
			
			//try {
				args.clear();
				args.add(Class.forName("com.sk89q.worldguard.protection.flags.Flag"), modify);
				//plugin.getLogger().info("Registering flag...");
				Reflection.getMethod(registry, "register", Object.class, args, null);
			//} catch (IllegalStateException e) {
				//if (e.getClass().getName().equals(exceptionClass.getClass().getName()))
				//	plugin.getLogger().info("Flag already registered.");
				//plugin.getLogger().info("Flag can be only registered while server is loading.");
			//}
		} catch (IllegalStateException e) {
			plugin.getLogger().info("Flag can be only registered while server is loading.");
		} catch (NullPointerException e) {
			plugin.getLogger().warning("Couldn't register flag, something went wrong while accessing the registry class.");
		} catch (ClassNotFoundException e) {
			plugin.getLogger().warning("Couldn't register flag, please use 6.2 or newer version of worldguard.");
		}
	}
	
	public static boolean checkFlag(DebugStickPlugin plugin, Location location, String flag, Player player, boolean def) {
		Objects.requireNonNull(plugin);
		Objects.requireNonNull(location);
		Objects.requireNonNull(flag);
		Objects.requireNonNull(player);
		
		if (plugin.hasWorldGuard()) {
			try {
				com.sk89q.worldguard.protection.flags.StateFlag objflag = (com.sk89q.worldguard.protection.flags.StateFlag)com.sk89q.worldguard.bukkit.WorldGuardPlugin.inst().getFlagRegistry().get(flag);
				com.sk89q.worldguard.bukkit.RegionContainer container = ((com.sk89q.worldguard.bukkit.WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard")).getRegionContainer();
				com.sk89q.worldguard.bukkit.RegionQuery query = container.createQuery();
	
				return query.testState(location, player, objflag) || ((com.sk89q.worldguard.bukkit.WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard")).getSessionManager().hasBypass(player, player.getWorld());
			} catch (Exception e) {
				
			}
		}
		return def;
	}

	public static boolean hasBypass(DebugStickPlugin plugin, Player player, boolean def) {
		if (plugin.hasWorldGuard())
			return ((com.sk89q.worldguard.bukkit.WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard")).getSessionManager().hasBypass(player, player.getWorld());
		return def;
	}
}
