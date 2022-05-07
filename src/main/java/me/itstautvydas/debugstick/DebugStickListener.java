package me.itstautvydas.debugstick;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.itstautvydas.debugstick.event.DebugStickClickEvent;
import me.itstautvydas.debugstick.util.DebugStickUtils;

public class DebugStickListener implements Listener {
	
	private DebugStick ds;
	private DebugStickPlugin plg;

	public DebugStickListener(DebugStickPlugin plg) {
		this.ds = plg.getDebugStickInstance();
		this.plg = plg;
	}
	
	@EventHandler
	public void onPlayerGUI(InventoryOpenEvent event) {
		HumanEntity p = event.getPlayer();
		@SuppressWarnings("deprecation")
		ItemStack iteminhand = p.getItemInHand();
		if (DebugStickUtils.verifyItem(plg, iteminhand))
			if (plg.hasPermission(Bukkit.getPlayer(p.getName()), "modify"))
				if (allowed(p.getGameMode()))
					event.setCancelled(true);
	}
	
	private boolean allowed(GameMode gm) {
		if (!gm.equals(GameMode.SPECTATOR))
			return (boolean)plg.getConfigValue("allowed-gamemodes", gm.toString().toLowerCase());
		return false;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Player p = event.getPlayer();
			Block b = event.getClickedBlock();
			
			@SuppressWarnings("deprecation")
			ItemStack iteminhand = p.getItemInHand();

			if (isHand(event) && DebugStickUtils.verifyItem(plg, iteminhand) && allowed(p.getGameMode())) {
				if (b != null && !b.isLiquid() && !b.isEmpty() && b.getType() != Material.AIR) {
					DebugStickClickEvent event1 = new DebugStickClickEvent(b.getLocation(), p, event.getAction(), p.isSneaking());
					Bukkit.getServer().getPluginManager().callEvent(event1);
					if (!event1.isCancelled()) {
						if (plg.getConfig().getStringList("item.lores").size() > 0 && !iteminhand.getItemMeta().getLore().containsAll(plg.getConfig().getStringList("item.lores"))) {
							ItemMeta im = iteminhand.getItemMeta();
							ArrayList<String> lores = new ArrayList<>();
							for (String lore : plg.getConfig().getStringList("item.lores"))
								if (plg.getConfigValue("item", "colored-lores", boolean.class))
									lores.add(ChatColor.translateAlternateColorCodes('&', lore));
								else
									lores.add(lore);
							im.setLore(lores);
							iteminhand.setItemMeta(im);
						}
						
						if (ds.click(p, b.getLocation(), p.isSneaking(), event.getAction())) {
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	private boolean isHand(PlayerInteractEvent event) {
		try {
			Class<?> a = event.getClass();
			Method handMethod = a.getMethod("getHand", (Class<?>[]) null);
			@SuppressWarnings("rawtypes")
			Class b = Class.forName("org.bukkit.inventory.EquipmentSlot");
			@SuppressWarnings("unchecked")
			Enum<?> e = Enum.valueOf(b, "HAND");
			return (Enum<?>)handMethod.invoke(event) == e;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
		  return true;
		}
	}
}