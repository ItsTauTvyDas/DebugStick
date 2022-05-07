package me.itstautvydas.debugstick.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ActionBar {
	
	private String message;
	private Player player;
	private Plugin plugin;
	
	public ActionBar(Plugin plugin, Player player, String message) {
		this.message = message;
		this.player = player;
	}
	
	public ActionBar() {}
	
	public ActionBar setPlugin(Plugin plugin) {
		this.plugin = plugin;
		return this;
	}
	
	public ActionBar setPlayer(Player player) {
		this.player = player;
		return this;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ActionBar setMessage(String newMessage) {
		this.message = newMessage;
		return this;
	}
	
	public ActionBar send() {
		Objects.requireNonNull(plugin);
		Objects.requireNonNull(player);
		Objects.requireNonNull(message);
		send(plugin, player, message);
		return this;
	}
	
	//private static int x = 0;
	
	private static void _send(Player player, String msg) {
		if (SupportedVersions.getInteger(null) > 11) { //1.12
			try {
				@SuppressWarnings("rawtypes")
				Class cmtClass = getNMSClass("ChatMessageType");
				@SuppressWarnings("unchecked")
				Enum<?> cmtEnum = Enum.valueOf(cmtClass, "GAME_INFO");
				Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), cmtClass);
				Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + msg + "\"}");
				Object packet = constructor.newInstance(icbc, cmtEnum);
				
				Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
				Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
				playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				 e.printStackTrace();
			}
		} else { //1.8
			try {
				Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class);
				Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\":\"" + msg + "\"}");
				Object packet = constructor.newInstance(icbc, (byte) 2);
				
				Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
				Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
				playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				 e.printStackTrace();
			}
		}
	}
	
	public static void send(Plugin plugin, Player player, String message/*, final int duration, boolean fadeout*/) {
		_send(player, ChatColor.translateAlternateColorCodes('&', message.replace("\"", "\\\"")));
	}
   
    public static void send(Plugin plugin, Player player, String message, boolean chat) {
    	if (chat)
    		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    	else
    		send(plugin, player, message);
    }
    
    public static void send(Plugin plugin, CommandSender sender, String message, boolean chat) {
    	if (sender instanceof Player)
    		send(plugin, (Player)sender, message, chat);
    }
	
	private static Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + getVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

}