package me.itstautvydas.debugstick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.itstautvydas.debugstick.helper.AbstractCommand;
import me.itstautvydas.debugstick.helper.UpdateCheck;
import me.itstautvydas.debugstick.util.DebugStickUtils;
import me.itstautvydas.debugstick.util.SupportedVersions;

public class DebugStickCommand extends AbstractCommand {
	
	private DebugStickPlugin plugin;
	private CommandSender sender;
	
	private void a(String a, String b, HashMap<String, String> rep) {
		if (sender instanceof Player)
			plugin.sendMessage((Player)sender, a, b, null, rep);
			//plugin.sendActionBar((Player)sender, plugin.getMessage(a, b, rep));
	}
	
	private void b(String a, String b, String c, HashMap<String, String> rep) {
		if (sender instanceof Player)
			plugin.sendMessage((Player)sender, a, b, c, rep);
	}

	public DebugStickCommand(String command, DebugStickPlugin plugin) {
		super(command);
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		this.sender = sender;
		
		HashMap<String, String> rep = new HashMap<>();
		rep.put("%command%", this.command);
		rep.put("%serverversion%", SupportedVersions.getServerVersion());
		rep.put("%pluginversion%", plugin.getDescription().getVersion());
		rep.put("%args%", String.join(" ", args));
		
		if (sender instanceof Player) {
			rep.put("%player%", ((Player)sender).getName());
			rep.put("%playerdisplayname%", ((Player)sender).getDisplayName());
			rep.put("%world%", ((Player)sender).getWorld().getName());
		} else {
			rep.put("%player%", sender.getName());
			rep.put("%playerdisplayname%", sender.getName());
			rep.put("%world%", "unknown");
		}
		
		if (plugin.hasPermission(sender, "command-use")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (plugin.hasPermission(sender, "command-reload")) {
						long start = System.currentTimeMillis();
						plugin.load(true);
						long elapsedTimeMillis = System.currentTimeMillis()-start;
						rep.put("%time%", String.valueOf(elapsedTimeMillis/1000F));
						a("command", "reloaded", rep);
					} else {
						rep.put("%perm%", plugin.getPerm("command-reload"));
						b("messages", "no-permission", "no-permission", rep);
					}
				} else if (args[0].equalsIgnoreCase("update")) {
					if (plugin.hasPermission(sender, "command-check-for-updates")) {
						a("command", "checking-for-updates", rep);
						
						UpdateCheck
							.of(plugin.getDescription().getName())
							.resourceId(75020)
							.handleResponse((versionResponse, version) -> {
					            switch (versionResponse) {
				                case FOUND_NEW:
				                	rep.put("%newversion%", version);
				                	a("command", "update-available", rep);
				                    break;
				                case LATEST:
				                	a("command", "latest-update", rep);
				                    break;
				                case UNAVAILABLE:
				                	a("command", "update-unavailable", rep);
					            }
					        }).check();
					} else {
						rep.put("%perm%", plugin.getPerm("command-check-for-updates"));
						b("messages", "no-permission", "no-permission", rep);
					}
				} else if (args[0].equalsIgnoreCase("give")) {
					if (plugin.hasPermission(sender, "command-give")) {
						if (args.length > 1) {
							if (plugin.hasPermission(sender, "command-give-others")) {
								rep.put("%target%", args[1]);
								rep.put("%targetdisplayname%", args[1]);
								Player target = Bukkit.getPlayer(args[1]);
								if (target != null && target.isOnline()) {
									rep.put("%target%", target.getName());
									rep.put("%world%", target.getWorld().getName());
									rep.put("%targetdisplayname%", target.getDisplayName());
									DebugStickUtils.give(plugin, target);
									a("command", "given-debugstick", rep);
								} else {
									a("command", "player-is-offline", rep);
								}
							} else {
								rep.put("%perm%", plugin.getPerm("command-give-others"));
								b("messages", "no-permission", "no-permission", rep);
							}
						} else {
							if (sender instanceof Player) {
								DebugStickUtils.give(plugin, (Player)sender);
								a("command", "given-debugstick-to-yourself", rep);
							}
						}
					} else {
						rep.put("%perm%", plugin.getPerm("command-give"));
						b("messages", "no-permission", "no-permission", rep);
					}
				} else {
					a("command", "invalid-arguments", rep);
				}
			} else {
				a("command", "no-arguments", rep);
			}
		} else {
			rep.put("%perm%", plugin.getPerm("command-use"));
			b("messages", "no-permission", "no-permission", rep);
		}
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			List<String> tabs = new ArrayList<>();
			Player p = (Player)sender;
			if (args.length > 1 && args[0].equalsIgnoreCase("give")) {
				if (plugin.hasPermission(p, "command-give") && plugin.hasPermission(p, "command-give-others"))
					for (Player p2 : plugin.getServer().getOnlinePlayers()) {
						tabs.add(p2.getName());
					}
				return tabs;
			} else if (args.length > 0) {
				if (plugin.hasPermission(p, "command-give") || plugin.hasPermission(p, "command-give-others"))
					tabs.add("give");
				if (plugin.hasPermission(p, "command-reload"))
					tabs.add("reload");
				if (plugin.hasPermission(p, "command-check-for-updates"))
					tabs.add("update");
				return tabs;
			}
		}
		return null;
	}

}
