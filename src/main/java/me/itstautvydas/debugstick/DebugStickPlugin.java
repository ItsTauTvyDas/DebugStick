package me.itstautvydas.debugstick;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.logging.Level;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.itstautvydas.debugstick.blockstates.BlockStates;
import me.itstautvydas.debugstick.support.WorldGuard;
import me.itstautvydas.debugstick.util.ActionBar;
import me.itstautvydas.debugstick.util.OtherUtil;
import me.itstautvydas.debugstick.util.Reflection;
import me.itstautvydas.debugstick.util.SupportedVersions;

public class DebugStickPlugin extends JavaPlugin {
	
	private File msgsconfigFile;
	private YamlConfiguration msgsconfig;
	
	private DebugStick debugstick;
	private DebugStickCommand cmd;
	
	private HashMap<String, String> selectedState = new HashMap<>();
	private HashMap<String, Material> selectedBlock = new HashMap<>();
	
	private boolean hasPlotSquared;
	private boolean hasWorldGuard;
	
	private String[] plotsquared_classes = new String[] {};
	
	public DebugStick getDebugStickInstance() {
		return debugstick;
	}
	
	public YamlConfiguration getMessagesConfig() {
		return msgsconfig;
	}
	
	public DebugStickPlugin getPlugin() {
		return this;
	}
	
	public boolean hasPlotSquared() {
		return hasPlotSquared && getPlotSquaredClasses().length == 2;
	}
	
	public boolean hasWorldGuard() {
		return hasWorldGuard;
	}
	
	public void load(boolean reload) {
		selectedBlock.clear();
		selectedState.clear();
		
		if (!reload) {
			setupConfigs();
		} else {
			reloadConfig();
			reloadMessages();
		}
		
		hasWorldGuard = Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
		hasPlotSquared = Bukkit.getPluginManager().getPlugin("PlotSquared") != null;
		
		debugstick = new DebugStick(this, selectedState, selectedBlock);
	}
	
	public String getPerm(String name) {
		if (getConfig().getKeys(true).contains("permissions."+name))
			return (String)getConfigValue("permissions", name);
		return null;
	}
	
	public String getMessage(String category, String key, @Nullable HashMap<String, String> replacements) {
		Objects.requireNonNull(key);
		if (getMessagesConfig().getKeys(true).contains(category.concat(".").concat(key)))
			return applyPlaceholders(getMessagesConfig().getConfigurationSection(category).get(key).toString(), replacements);
		return null;
	}
	
	public Object getConfigValue(String category, String key) {
		if (category != null) {
			if (getConfig().getKeys(true).contains(category.concat(".").concat(key)))
				return getConfig().getConfigurationSection(category).get(key);
		} else {
			return getConfig().get(key);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getConfigValue(String category, String key, Class<T> clazz) {
		if (category != null) {
			if (getConfig().getKeys(true).contains(category.concat(".").concat(key)))
				return (T)getConfig().getConfigurationSection(category).get(key);
		} else {
			return (T)getConfig().get(key);
		}
		return null;
	}
	
	public boolean hasPermission(Player player, String permissionName) {
		if (getConfig().getKeys(true).contains("permissions.".concat(permissionName)))
			return player.hasPermission(getConfig().getConfigurationSection("permissions").getString(permissionName));
		return false;
	}
	
	public boolean hasPermission(CommandSender sender, String permissionName) {
		if (getConfig().getKeys(true).contains("permissions.".concat(permissionName)))
			return sender.hasPermission(getConfig().getConfigurationSection("permissions").getString(permissionName));
		return false;
	}
	
	public boolean isWorldAllowed(World world) {
		@SuppressWarnings("unchecked")
		List<String> list = getConfigValue("allowed-worlds", "list", List.class);
		boolean reversed = getConfigValue("allowed-worlds", "reverse", Boolean.class);
		return reversed && !list.contains(world.getName());
	}
	
	public void cleanMemory() {
		for (Entry<String, String> a : selectedState.entrySet())
			if (Bukkit.getServer().getPlayerExact(a.getKey()) == null)
				selectedState.remove(a.getKey());
		
		for (Entry<String, Material> a : selectedBlock.entrySet())
			if (Bukkit.getServer().getPlayerExact(a.getKey()) == null)
				selectedState.remove(a.getKey());
	}
	
    public void saveMessages() {
        try {
            getMessagesConfig().save(msgsconfigFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + msgsconfigFile, ex);
        }
    }
	
	public void reloadMessages() {
		msgsconfig = YamlConfiguration.loadConfiguration(msgsconfigFile);
	}
	
	public void sendSimpleMessage(Player player, String message) {
		Objects.requireNonNull(player).sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(message)));
	}
	
	public void sendActionBar(Player player, String message) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(message);
		
		new ActionBar().setPlugin(this).setPlayer(player).setMessage(message).send();
	}
	
	public String applyPlaceholders(String message, HashMap<String, String> placeholders) {
		if (placeholders != null)
			for (Entry<String, String> x : placeholders.entrySet())
				if (x.getKey() != null && x.getValue() != null)
					message = message.replace(x.getKey(), x.getValue());
		return message;
	}
	
	public void sendMessage(Player player, String category, String key, String actionbarKey, @Nullable HashMap<String, String> placeholders) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(category);
		Objects.requireNonNull(key);
		
		if (placeholders == null)
			placeholders = new HashMap<>();
		
		String message = getMessage(category, key, placeholders);
		
		if (actionbarKey != null ? getConfigValue("use-actionbar", actionbarKey, boolean.class) : false)
			sendActionBar(player, message);
		else
			sendSimpleMessage(player, message);
	}
	
	private void loadMessages() {
		getMessagesConfig().addDefault("command.no-arguments", "&6/%command% give &o<player> &f- &eGives player a debug stick.\n"
				+ "&6/%command% reload &f- &eReloads configuration and messages.\n"
				+ "&6/%command% update &f- &eChecks for updates, current &6%pluginversion%");
		getMessagesConfig().addDefault("command.invalid-arguments", "&cInvalid \"%args%\" arguments.");
		getMessagesConfig().addDefault("command.reloaded", "&6DebugStick &eplugin reloaded in &6%time%s &e!");
		getMessagesConfig().addDefault("command.player-is-offline", "&cPlayer %target% is offline!");
		getMessagesConfig().addDefault("command.given-debugstick", "&eGiven &6Debug Stick &eto &6%target%");
		getMessagesConfig().addDefault("command.given-debugstick-to-yourself", "&eGiven &6Debug Stick &eto &6you");
		getMessagesConfig().addDefault("command.update-available", "&6[DebugStick] &eA new version was found: &6%newversion%");
		getMessagesConfig().addDefault("command.update-unavailable", "&6[DebugStick] &cUnable to perform an update check.");
		getMessagesConfig().addDefault("command.latest-update", "&6[DebugStick] &eYou are using the latest version.");
		getMessagesConfig().addDefault("command.checking-for-updates", "&6[DebugStick] &eChecking for updates...");
		
		getMessagesConfig().addDefault("messages.data-changed", "\"%state%\" to %blockdata%");
		getMessagesConfig().addDefault("messages.selected-state", "Selected \"%state%\" (%blockdata%)");
		getMessagesConfig().addDefault("messages.no-properties", "minecraft:%material% has no properties");
		getMessagesConfig().addDefault("messages.no-such-state", "minecraft:%material% has no state such as \"%state%\"");
		getMessagesConfig().addDefault("messages.no-permission", "&cYou don't have permission to do this.");
		//getMessagesConfig().addDefault("messages.please-wait", "&cPlease wait until you can use it again.");
		getMessagesConfig().addDefault("messages.plotsquared-cannot-modify-road", "&cYou don't have permission to modify roads.");
		getMessagesConfig().addDefault("messages.plotsquared-cannot-modify-unclaimed-plot", "&cYou don't have permission to modify unclaimed plot.");
		getMessagesConfig().addDefault("messages.plotsquared-cannot-modify-owned-plot", "&cYou don't have permission to modify your own plot.");
		getMessagesConfig().addDefault("messages.plotsquared-cannot-modify-claimed-plot", "&cYou don't have permission to modify claimed plot.");
		getMessagesConfig().addDefault("messages.plotsquared-cannot-modify-friends-plot", "&cYou don't have permission to modify your friend's plot.");
		getMessagesConfig().addDefault("messages.worldguard-no-permission", "&cYou don't have permission to modify in this region.");
		getMessagesConfig().addDefault("messages.disabled-world", "&cYou don't have permission to modify in this world.");

		getMessagesConfig().addDefault("messages.error-getting-next-state", "Something went wrong getting block's next state.");
		getMessagesConfig().addDefault("messages.error-getting-block-data", "Something went wrong getting block's data, reason: %reason%");
		getMessagesConfig().addDefault("messages.error-changing-data", "Something went wrong changing block's data, reason: %reason%");
		
		getMessagesConfig().addDefault("error-reasons.block-missing", "block data is missing.");
		getMessagesConfig().addDefault("error-reasons.version-not-supported", "version is not supported.");
		getMessagesConfig().addDefault("error-reasons.state-missing", "state is missing.");
		
		Map<String, Object> values = new HashMap<>();
		Map<String, Object> states = new HashMap<>();
		Map<String, Object> materials = new HashMap<>();
		
		for (Material mat : Material.values())
			if (mat.isBlock() && mat != Material.WATER && mat != Material.LAVA && mat != Material.STATIONARY_LAVA
			&& mat != Material.STATIONARY_WATER && mat != Material.FIRE)
				materials.put("block-materials."+mat.toString().toLowerCase().replace("_", "-"), mat.toString().toLowerCase());
		
		for (BlockStates bsv : BlockStates.values())
			for (String state : bsv.getStates()) {
				states.put("block-states."+state.toLowerCase().replace("_", "-"), state.toLowerCase());
				for (Object value : bsv.getAllowedValues(state))
					if (!(value instanceof Integer))
						values.put("block-data-values."+value.toString().toLowerCase().replace("_", "-"), value.toString().toLowerCase());
			}

		getMessagesConfig().addDefaults(materials);
		getMessagesConfig().addDefaults(states);
		getMessagesConfig().addDefaults(values);
		getMessagesConfig().options().copyDefaults(true);
		saveMessages();
	}
	
	private void loadConfig() {
		getConfig().addDefault("config-version", 1);
		getConfig().addDefault("enabled", true);
		getConfig().addDefault("custom-command", "debugstick");
		
		getConfig().addDefault("item.custom-name", "Debug Stick");
		getConfig().addDefault("item.enchanted", true);
		getConfig().addDefault("item.material", "STICK");
		getConfig().addDefault("item.data", 0);
		getConfig().addDefault("item.lores", new ArrayList<String>());
		getConfig().addDefault("item.colored-lores", true);
		
		getConfig().addDefault("allowed-worlds.reverse", true);
		getConfig().addDefault("allowed-worlds.list", Arrays.asList("disabledworld"));
		getConfig().addDefault("allowed-worlds.bypass-permission", true);
		
		getConfig().addDefault("allowed-gamemodes.creative", true);
		getConfig().addDefault("allowed-gamemodes.survival", false);
		getConfig().addDefault("allowed-gamemodes.adventure", false);
		
		getConfig().addDefault("use-actionbar.changing-data", true);
		getConfig().addDefault("use-actionbar.selecting-state", true);
		getConfig().addDefault("use-actionbar.no-properties", true);
		getConfig().addDefault("use-actionbar.no-such-state", true);
		getConfig().addDefault("use-actionbar.no-permission", false);
		getConfig().addDefault("use-actionbar.worldguard-no-permission", false);
		getConfig().addDefault("use-actionbar.plotsquared-no-permission", false);
		getConfig().addDefault("use-actionbar.error-messages", true);
		getConfig().addDefault("use-actionbar.disabled-world", false);
		
		getConfig().addDefault("no-perm-messages.default-modify", false);
		getConfig().addDefault("no-perm-messages.worldguard-modify", true);
		getConfig().addDefault("no-perm-messages.plotsquared-modify", true);
		getConfig().addDefault("no-perm-messages.disabled-world", true);
		
		getConfig().addDefault("permissions.modify", "debugstick.modify");
		getConfig().addDefault("permissions.command-use", "debugstick.command.use");
		getConfig().addDefault("permissions.command-give", "debugstick.command.give");
		getConfig().addDefault("permissions.command-give-others", "debugstick.command.give.others");
		getConfig().addDefault("permissions.command-reload", "debugstick.command.reload");
		getConfig().addDefault("permissions.command-check-for-updates", "debugstick.command.update");
		getConfig().addDefault("permissions.disabled-world-bypass", "debugstick.world.{world}");
		
		getConfig().addDefault("permissions.plotsquared-modify-roads", "debugstick.modify.plots.roads");
		getConfig().addDefault("permissions.plotsquared-modify-claimed-plots", "debugstick.modify.plots.claimed");
		getConfig().addDefault("permissions.plotsquared-modify-unclaimed-plots", "debugstick.modify.plots.unclaimed");
		getConfig().addDefault("permissions.plotsquared-modify-friend-plots", "debugstick.modify.plots.friend");
		getConfig().addDefault("permissions.plotsquared-modify-owned-plots", "debugstick.modify.plots.owned");
		
		getConfig().addDefault("permissions.worldguard-modify-bypass", "debugstick.worldguard.bypass");
		
		getConfig().options().copyDefaults(true);
		
		if (getConfig().getInt("config-version") < getConfig().getDefaults().getInt("config-version")) {
			getMessagesConfig().set("command.no-arguments", getMessagesConfig().getDefaults().getString("command.no-arguments"));
			getConfig().set("config-version", 1);
			saveMessages();
			saveConfig();
		}
		
		saveConfig();
	}
	
	public String[] getPlotSquaredClasses() {
		return plotsquared_classes;
	}
	
	@Override
	public void onDisable() {
		cmd.unregister();
	}
	
	@Override
	public void onLoad() {
		hasWorldGuard = Bukkit.getPluginManager().getPlugin("WorldGuard") != null;
		hasPlotSquared = Bukkit.getPluginManager().getPlugin("PlotSquared") != null;
		
		if (hasPlotSquared) {
			if (Reflection.getClass("com.intellectualcrafters.plot.object.Plot") != null && Reflection.getClass("com.intellectualcrafters.plot.object.Location") != null) {
				plotsquared_classes = new String[] { "com.intellectualcrafters.plot.object.Plot", "com.intellectualcrafters.plot.object.Location" };
			} else if (Reflection.getClass("com.plotsquared.core.plot.Plot") != null && Reflection.getClass("com.plotsquared.core.location.Location") != null) {
				plotsquared_classes = new String[] { "com.plotsquared.core.plot.Plot", "com.plotsquared.core.location.Location" };
			} else {
				getLogger().info("PlotSquared plugin package is not supported.");
			}
		}
		
		if (hasWorldGuard)
			WorldGuard.register(this);
	}
	
	private void setupConfigs() {
		msgsconfigFile = new File(getDataFolder(), "messages.yml");
		msgsconfig = YamlConfiguration.loadConfiguration(msgsconfigFile);
		
		loadMessages();
		loadConfig();
	}
	
	@Override
	public void onEnable() {
		if (SupportedVersions.isSupported(null)) {
			load(false);
			
			if (getConfig().getBoolean("enabled")) {
				OtherUtil.checkBlockStatesClasses();
				cmd = new DebugStickCommand(getConfig().getString("custom-command"), this);
				cmd.register();
				
				getServer().getPluginManager().registerEvents(new DebugStickListener(this), this);
			} else {
				this.getPluginLoader().disablePlugin(this);
			}
		} else {
			getLogger().severe("Server version ("+SupportedVersions.getServerVersion()+") is not supported."
					+ " Supported versions: 1.8 - 1.12.2");
			this.getPluginLoader().disablePlugin(this);
		}
	}
}
