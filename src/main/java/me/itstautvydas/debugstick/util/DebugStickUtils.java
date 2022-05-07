package me.itstautvydas.debugstick.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import me.itstautvydas.debugstick.DebugStickPlugin;
import me.itstautvydas.debugstick.blockstates.BlockStates;
import me.itstautvydas.debugstick.blockstates.BlockStates110;
import me.itstautvydas.debugstick.blockstates.BlockStates111;
import me.itstautvydas.debugstick.blockstates.BlockStates112;
import me.itstautvydas.debugstick.blockstates.BlockStates18;
import me.itstautvydas.debugstick.blockstates.BlockStates19;
import me.itstautvydas.debugstick.helper.ModifiedData;

public class DebugStickUtils {
	
	private static ModifiedData alsoVerify(ModifiedData dc, int plus) {
		byte data = dc.type.valueToByte(dc.state, dc.value, plus);
		if (data != -1)
			return setData(dc, data);
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private static ModifiedData setData(ModifiedData dc, byte data) {
		byte oldData = dc.block.getData();
		dc.block.setData(data);
		dc.isDataSame = oldData == data;
		return dc;
	}
	
	@SuppressWarnings("deprecation")
	private static ModifiedData setData(ModifiedData dc, Material mat) {
		dc.block.setTypeIdAndData(mat.getId(), dc.block.getData(), false);
		dc.isDataSame = dc.block.getType() == mat;
		return dc;
	}
	
	/**
	 * @see #changeBlockData(Block, String, Object, int)
	 */
	public static ModifiedData changeBlockData(Block block, String state, Object value) {
		return changeBlockData(block, state, value, SupportedVersions.getInteger(null));
	}
	
	/**
	 * 
	 * @param block
	 * @param state
	 * @param value
	 * @param version Force to use specified version instead of the server version
	 * @return In some rare cases can return null.
	 */
	public static ModifiedData changeBlockData(Block block, String state, Object value, int version) {
		Object val = value;
		BlockStates type = BlockStates.fromMaterial(block.getType());
		Object oldValue = getBlockData(block, state);
		ModifiedData dc = new ModifiedData(block, state, val, oldValue, type);
		
		if (version == 8) {
			if (BlockStates18.has(type.toString()))
				switch(type.toEnum18()) {
				case ANVIL:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("damage", getBlockData(block, "damage"), 0)));
					if (state.equalsIgnoreCase("damage"))
						return setData(dc, type.valueToByte("facing", getBlockData(block, "facing"), type.valueToByte(state, val, 0)));
					break;
				case CHEST:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case LADDER:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case WALL_BANNER:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case STANDING_BANNER:
					 if (state.equalsIgnoreCase("rotation"))
						 return alsoVerify(dc, 0);
					 break;
				case SIGN_POST:
					 if (state.equalsIgnoreCase("rotation"))
						 return alsoVerify(dc, 0);
					 break;
				case WALL_SIGN:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case CROPS:
					 if (state.equalsIgnoreCase("age"))
						 return alsoVerify(dc, 0);
					 break;
				case STEM:
					 if (state.equalsIgnoreCase("age"))
						 return alsoVerify(dc, 0);
					 break;
				case CARROT:
					 if (state.equalsIgnoreCase("age"))
						 return alsoVerify(dc, 0);
					 break;
				case POTATO:
					 if (state.equalsIgnoreCase("age"))
						 return alsoVerify(dc, 0);
					 break;
				case SOIL:
					 if (state.equalsIgnoreCase("moisture"))
						 return alsoVerify(dc, 0);
					 break;
				case CACTUS:
					 if (state.equalsIgnoreCase("age"))
						 return alsoVerify(dc, 0);
					break;
				case NETHER_WARTS:
					 if (state.equalsIgnoreCase("age"))
						 return alsoVerify(dc, 0);
					break;
				case CAKE_BLOCK:
					 if (state.equalsIgnoreCase("bites"))
						 return alsoVerify(dc, 0);
					break;
				case PUMPKIN:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					if (state.equalsIgnoreCase("lit")) {
						@SuppressWarnings("deprecation")
						Material material = Material.getMaterial(Byte.toUnsignedInt(type.valueToByte(dc.state, dc.value, 0)));
						if (material != null)
							return setData(dc, material);
					}
					break;
				case JACK_O_LANTERN:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					if (state.equalsIgnoreCase("lit")) {
						@SuppressWarnings("deprecation")
						Material material = Material.getMaterial(Byte.toUnsignedInt(type.valueToByte(dc.state, dc.value, 0)));
						if (material != null)
							return setData(dc, material);
					}
					break;
				case CAULDRON:
					 if (state.equalsIgnoreCase("level"))
						 return alsoVerify(dc, 0);
					break;
				case SUGAR_CANE_BLOCK:
					 if (state.equalsIgnoreCase("age"))
						 return alsoVerify(dc, 0);
					break;
				case STAINED_CLAY:
					if (state.equalsIgnoreCase("color"))
						return alsoVerify(dc, 0);
					break;
				case STAINED_GLASS_PANE:
					if (state.equalsIgnoreCase("color"))
						return alsoVerify(dc, 0);
					break;
				case CARPET:
					if (state.equalsIgnoreCase("color"))
						return alsoVerify(dc, 0);
					break;
				case STAINED_GLASS:
					if (state.equalsIgnoreCase("color"))
						return alsoVerify(dc, 0);
					break;
				case FURNACE:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					if (state.equalsIgnoreCase("lit"))
						if ((boolean)val)
							return setData(dc, Material.BURNING_FURNACE);
						else
							return setData(dc, Material.FURNACE);
					break;
				case FENCE:
					if (state.equalsIgnoreCase("variant")) {
						@SuppressWarnings("deprecation")
						Material material = Material.getMaterial(Byte.toUnsignedInt(type.valueToByte(dc.state, dc.value, 0)));
						if (material != null)
							return setData(dc, material);
					}
					break;
				case WOOD_STEP:
					if (state.equalsIgnoreCase("variant"))
						return setData(dc, (byte)(type.valueToByte(state, (String)val,
								type.valueToByte("half", getBlockData(block, "half"), 0))));
					if (state.equalsIgnoreCase("half"))
						return setData(dc, type.valueToByte("variant", getBlockData(block, "variant"),
								type.valueToByte(state, (String)val, 0)));
					break;
				case STEP:
					if (state.equalsIgnoreCase("variant"))
						return setData(dc, type.valueToByte(state, (String)val, type.valueToByte("half", getBlockData(block, "half"), 0)));
					if (state.equalsIgnoreCase("half"))
						return setData(dc, type.valueToByte("variant", getBlockData(block, "variant"), 
								type.valueToByte(state, (String)val, 0)));
					break;
				case STAIRS:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("half", getBlockData(block, "half"), 0)));
					if (state.equalsIgnoreCase("half"))
						return setData(dc, type.valueToByte("facing", getBlockData(block, "facing"), type.valueToByte(state, val, 0)));
					break;
				case COCOA:
					if (state.equalsIgnoreCase("age"))
						return setData(dc, type.valueToByte("facing", getBlockData(block, "facing"), type.valueToByte(state, val, 0)));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte("age", getBlockData(block, "age"), type.valueToByte(state, val, 0)));
					break;
				case SKULL:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte("nodrop", getBlockData(block, "nodrop"), type.valueToByte(state, val, 0)));
					if (state.equalsIgnoreCase("nodrop"))
						return setData(dc, type.valueToByte("facing", getBlockData(block, "facing"), type.valueToByte(state, val, 0)));
					break;
				case DOUBLE_STEP:
					if (state.equalsIgnoreCase("variant"))
						return setData(dc, type.valueToByte("seamless", getBlockData(block, "seamless"), type.valueToByte(state, val, 0)));
					if (state.equalsIgnoreCase("seamless"))
						return setData(dc, type.valueToByte("variant", getBlockData(block, "variant"), type.valueToByte(state, val, 0)));
					break;
				case DOUBLE_STONE_SLAB2:
					if (state.equalsIgnoreCase("seamless"))
						return alsoVerify(dc, 0);
					break;
				case WOOD_DOUBLE_STEP:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case WOOD:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case STONE:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case SAND:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case TNT:
					if (state.equalsIgnoreCase("explode"))
						return alsoVerify(dc, 0);
					break;
				case COMMAND:
					if (state.equalsIgnoreCase("triggered"))
						return alsoVerify(dc, 0);
					break;
				case SPONGE:
					if (state.equalsIgnoreCase("wet"))
						return alsoVerify(dc, 0);
					break;
				case LOG_2:
					if (state.equalsIgnoreCase("axis"))
						return setData(dc, type.valueToByte("variant", getBlockData(block, "variant"), type.valueToByte(state, val, 0)));
					if (state.equalsIgnoreCase("variant"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("axis", getBlockData(block, "axis"), 0)));
					break;
				case LOG:
					if (state.equalsIgnoreCase("axis"))
						return setData(dc, type.valueToByte("variant", getBlockData(block, "variant"), type.valueToByte(state, val, 0)));
					if (state.equalsIgnoreCase("variant"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("axis", getBlockData(block, "axis"), 0)));
					break;
				case PISTON_EXTENSION:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte("type", getBlockData(block, "type"), type.valueToByte(state, val, 0)));
					if (state.equalsIgnoreCase("type"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					break;
				case HAY_BLOCK:
					if (state.equalsIgnoreCase("axis"))
						return alsoVerify(dc, 0);
					break;
				case WOOL:
					if (state.equalsIgnoreCase("color"))
						return alsoVerify(dc, 0);
					break;
				case DISPENSER:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case DROPPER:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case BUTTON:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, (String)val,
								type.valueToByte("powered", getBlockData(block, "powered"), 0)));
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, (boolean)val,
								type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					break;
				case JUKEBOX:
					if (state.equalsIgnoreCase("has_record"))
						return alsoVerify(dc, 0);
					break;
				case PRISMARINE:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case COBBLE_WALL:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case TORCH:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case REDSTONE_TORCH:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					if (state.equalsIgnoreCase("lit"))
						if ((boolean)val)
							return setData(dc, Material.REDSTONE_TORCH_ON);
						else
							return setData(dc, Material.REDSTONE_TORCH_OFF);
					break;
				case LONG_GRASS:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case RED_ROSE:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case HUGE_MUSHROOM:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case HOPPER:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case RAILS:
					if (state.equalsIgnoreCase("shape"))
						return alsoVerify(dc, 0);
					break;
				case RAIL:
					if (state.equalsIgnoreCase("shape"))
						return setData(dc, type.valueToByte(state, (String)val,
								type.valueToByte("powered", getBlockData(block, "powered"), 0)));
					
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, (boolean)val,
								type.valueToByte("shape", getBlockData(block, "shape"), 0)));
					break;
				case STONE_SLAB2:
					if (state.equalsIgnoreCase("half"))
						return alsoVerify(dc, 0);
					break;
				case RED_SANDSTONE:
					if (state.equalsIgnoreCase("type"))
						return alsoVerify(dc, 0);
					break;
				case SANDSTONE:
					if (state.equalsIgnoreCase("type"))
						return alsoVerify(dc, 0);
					break;
				case DOUBLE_PLANT:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case MONSTER_EGGS:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case SNOW:
					if (state.equalsIgnoreCase("layers"))
						return alsoVerify(dc, 0);
					break;
				case DIRT:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case SMOOTH_BRICK:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case QUARTZ_BLOCK:
					if (state.equalsIgnoreCase("variant"))
						return alsoVerify(dc, 0);
					break;
				case LEAVES:
					if (state.equalsIgnoreCase("check_decay"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("variant", getBlockData(block, "variant"),
								type.valueToByte("decayable", getBlockData(block, "decayable"), 0))));
					if (state.equalsIgnoreCase("decayable"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("variant", getBlockData(block, "variant"),
								type.valueToByte("check_decay", getBlockData(block, "check_decay"), 0))));
					if (state.equalsIgnoreCase("variant"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("decayable", getBlockData(block, "decayable"),
								type.valueToByte("check_decay", getBlockData(block, "check_decay"), 0))));
					break;
				case LEAVES_2:
					if (state.equalsIgnoreCase("check_decay"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("variant", getBlockData(block, "variant"),
								type.valueToByte("decayable", getBlockData(block, "decayable"), 0))));
					if (state.equalsIgnoreCase("decayable"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("variant", getBlockData(block, "variant"),
								type.valueToByte("check_decay", getBlockData(block, "check_decay"), 0))));
					if (state.equalsIgnoreCase("variant"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("decayable", getBlockData(block, "decayable"),
								type.valueToByte("check_decay", getBlockData(block, "check_decay"), 0))));
					break;
				case FENCE_GATE:
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("open", getBlockData(block, "open"), 0))));
					if (state.equalsIgnoreCase("open"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("powered", getBlockData(block, "powered"),
								type.valueToByte("facing", getBlockData(block, "facing"), 0))));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("powered", getBlockData(block, "powered"),
								type.valueToByte("open", getBlockData(block, "open"), 0))));
					break;
				case TRAP_DOOR:
					if (state.equalsIgnoreCase("half"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("open", getBlockData(block, "open"), 0))));
					if (state.equalsIgnoreCase("open"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("half", getBlockData(block, "half"),
								type.valueToByte("facing", getBlockData(block, "facing"), 0))));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("half", getBlockData(block, "half"),
								type.valueToByte("open", getBlockData(block, "open"), 0))));
					break;
				case IRON_TRAPDOOR:
					if (state.equalsIgnoreCase("half"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("open", getBlockData(block, "open"), 0))));
					if (state.equalsIgnoreCase("open"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("half", getBlockData(block, "half"),
								type.valueToByte("facing", getBlockData(block, "facing"), 0))));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("half", getBlockData(block, "half"),
								type.valueToByte("open", getBlockData(block, "open"), 0))));
					break;
				case TRIPWIRE:
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("suspended", getBlockData(block, "suspended"),
										type.valueToByte("attached", getBlockData(block, "attached"),
												type.valueToByte("disarmed", getBlockData(block, "disarmed"), 0)))));
					if (state.equalsIgnoreCase("suspended"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("powered", getBlockData(block, "powered"),
										type.valueToByte("attached", getBlockData(block, "attached"),
												type.valueToByte("disarmed", getBlockData(block, "disarmed"), 0)))));
					if (state.equalsIgnoreCase("attached"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("suspended", getBlockData(block, "suspended"),
										type.valueToByte("powered", getBlockData(block, "powered"),
												type.valueToByte("disarmed", getBlockData(block, "disarmed"), 0)))));
					if (state.equalsIgnoreCase("disarmed"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("suspended", getBlockData(block, "suspended"),
										type.valueToByte("attached", getBlockData(block, "attached"),
												type.valueToByte("powered", getBlockData(block, "powered"), 0)))));
					break;
				case TRIPWIRE_HOOK:
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("attached", getBlockData(block, "attached"), 0))));
					if (state.equalsIgnoreCase("attached"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("powered", getBlockData(block, "powered"), 0))));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("attached", getBlockData(block, "attached"),
								type.valueToByte("powered", getBlockData(block, "powered"), 0))));
					break;
				case REDSTONE_COMPARATOR:
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("mode", getBlockData(block, "mode"), 0))));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("mode", getBlockData(block, "mode"),
								type.valueToByte("powered", getBlockData(block, "powered"), 0))));
					if (state.equalsIgnoreCase("mode"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("powered", getBlockData(block, "powered"), 0))));
					break;
				case DIODE_BLOCK:
					if (state.equalsIgnoreCase("powered"))
						if ((boolean)val)
							return setData(dc, Material.DIODE_BLOCK_ON);
						else
							return setData(dc, Material.DIODE_BLOCK_OFF);
					if (state.equalsIgnoreCase("delay"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("delay", getBlockData(block, "delay"), 0)));
					break;
				case ENDER_PORTAL_FRAME:
					if (state.equalsIgnoreCase("eye"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("eye", getBlockData(block, "eye"), 0)));
					break;
				case IRON_PLATE:
					if (state.equalsIgnoreCase("power"))
						return alsoVerify(dc, 0);
					break;
				case GOLD_PLATE:
					if (state.equalsIgnoreCase("power"))
						return alsoVerify(dc, 0);
					break;
				case WOOD_PLATE:
					if (state.equalsIgnoreCase("powered"))
						return alsoVerify(dc, 0);
					break;
				case STONE_PLATE:
					if (state.equalsIgnoreCase("powered"))
						return alsoVerify(dc, 0);
					break;
				case REDSTONE_WIRE:
					if (state.equalsIgnoreCase("power"))
						return alsoVerify(dc, 0);
					break;
				case LEVER:
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("powered", getBlockData(block, "powered"), 0)));
					break;
				case PISTON:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("extended", getBlockData(block, "extended"), 0)));
					if (state.equalsIgnoreCase("extended"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					break;
				case PORTAL:
					if (state.equalsIgnoreCase("axis"))
						return alsoVerify(dc, 0);
					break;
				case SAPLING:
					if (state.equalsIgnoreCase("type"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("stage", getBlockData(block, "stage"), 0)));
					if (state.equalsIgnoreCase("stage"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("type", getBlockData(block, "type"), 0)));
					break;
				case BED_BLOCK:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("part", getBlockData(block, "part"), 0)));
					if (state.equalsIgnoreCase("part"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					break;
				case DOOR:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("half", getBlockData(block, "half"),
								type.valueToByte("open", getBlockData(block, "open"), 0))));
					if (state.equalsIgnoreCase("half"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("open", getBlockData(block, "open"), 0)));
					if (state.equalsIgnoreCase("open"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("facing", getBlockData(block, "facing"),
								type.valueToByte("half", getBlockData(block, "half"), 0))));
					break;
				case VINE:
					if (state.equalsIgnoreCase("north"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("east", getBlockData(block, "east"),
								type.valueToByte("south", getBlockData(block, "south"),
										type.valueToByte("west", getBlockData(block, "west"), 0)))));
					if (state.equalsIgnoreCase("east"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("north", getBlockData(block, "north"),
								type.valueToByte("south", getBlockData(block, "south"),
										type.valueToByte("west", getBlockData(block, "west"), 0)))));
					if (state.equalsIgnoreCase("south"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("east", getBlockData(block, "east"),
								type.valueToByte("north", getBlockData(block, "north"),
										type.valueToByte("west", getBlockData(block, "west"), 0)))));
					if (state.equalsIgnoreCase("west"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("east", getBlockData(block, "east"),
								type.valueToByte("south", getBlockData(block, "south"),
										type.valueToByte("north", getBlockData(block, "north"), 0)))));
					break;
				default:
					return new ModifiedData(-1);
				}
			else
				return new ModifiedData(-4);
		} else if (version == 9) {
			if (BlockStates19.has(type.toString()))
				switch(type.toEnum19()) {
				case TRIPWIRE:
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte(state, val,
										type.valueToByte("attached", getBlockData(block, "attached"),
												type.valueToByte("disarmed", getBlockData(block, "disarmed"), 0))));
					if (state.equalsIgnoreCase("attached"))
						return setData(dc, type.valueToByte(state, val,
									type.valueToByte("powered", getBlockData(block, "powered"),
											type.valueToByte("disarmed", getBlockData(block, "disarmed"), 0))));
					if (state.equalsIgnoreCase("disarmed"))
						return setData(dc, type.valueToByte(state, val,
									type.valueToByte("attached", getBlockData(block, "attached"),
											type.valueToByte("powered", getBlockData(block, "powered"), 0))));
					break;
				case STAIRS:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("half", getBlockData(block, "half"), 0)));
					if (state.equalsIgnoreCase("half"))
						return setData(dc, type.valueToByte("facing", getBlockData(block, "facing"), type.valueToByte(state, val, 0)));
					break;
				case COMMAND:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("conditional", getBlockData(block, "conditional"), 0)));
					if (state.equalsIgnoreCase("conditional"))
						return setData(dc, type.valueToByte(state, val,
								type.valueToByte("facing", getBlockData(block, "facing"), 0)));
					if (state.equalsIgnoreCase("type"))
						if (val.equals("impulse"))
							return setData(dc, Material.COMMAND);
						else if (val.equals("chain"))
							return setData(dc, Material.valueOf("COMMAND_CHAIN"));
						else if (val.equals("repeating"))
							return setData(dc, Material.valueOf("COMMAND_REPEATING"));
					break;
				case PURPUR_PILLAR:
					if (state.equalsIgnoreCase("axis"))
						return alsoVerify(dc, 0);
					break;
				case PURPUR_SLAB:
					if (state.equalsIgnoreCase("half"))
						return alsoVerify(dc, 0);
					break;
				case CHORUS_FLOWER:
					if (state.equalsIgnoreCase("age"))
						return alsoVerify(dc, 0);
					break;
				case FROSTED_ICE:
					if (state.equalsIgnoreCase("age"))
						return alsoVerify(dc, 0);
					break;
				case END_ROD:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					break;
				case STRUCTURE_BLOCK:
					if (state.equalsIgnoreCase("mode"))
						return alsoVerify(dc, 0);
					break;
				case BEETROOT_BLOCK:
					if (state.equalsIgnoreCase("age"))
						return alsoVerify(dc, 0);
					break;
				default:
					return new ModifiedData(-1);
				}
			else
				return changeBlockData(block, state, val, 8);
		} else if (version == 10) {
			if (BlockStates110.has(type.toString()))
				switch(type.toEnum110()) {
				case BONE_BLOCK:
					if (state.equalsIgnoreCase("axis"))
						return alsoVerify(dc, 0);
					break;
				default:
					return new ModifiedData(-1);
				}
			else
				return changeBlockData(block, state, val, 9);
		} else if (version == 11) {
			if (BlockStates111.has(type.toString()))
				switch(type.toEnum111()) {
				case OBSERVER:
					if (state.equalsIgnoreCase("facing"))
						return setData(dc, type.valueToByte(state, val, type.valueToByte("powered", getBlockData(block, "powered"), 0)));
					if (state.equalsIgnoreCase("powered"))
						return setData(dc, type.valueToByte("facing", getBlockData(block, "facing"), type.valueToByte(state, val, 0)));
					break;
				case SHULKER_BOX:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					/*if (state.equalsIgnoreCase("color"))
						return setData(dc, Material.valueOf(val.toString().toUpperCase()+"_SHULKER_BOX"));*/
					break;
				default:
					return new ModifiedData(-1);
				}
			else
				return changeBlockData(block, state, val, 10);
		} else if (version == 12) {
			if (BlockStates112.has(type.toString()))
				switch(type.toEnum112()) {
				case CONCRETE:
					if (state.equalsIgnoreCase("color"))
						return alsoVerify(dc, 0);
					break;
				case GLAZED_TERRACOTTA:
					if (state.equalsIgnoreCase("facing"))
						return alsoVerify(dc, 0);
					if (state.equalsIgnoreCase("color"))
						return setData(dc, Material.valueOf(val.toString().toUpperCase()+"_GLAZED_TERRACOTTA"));
					break;
				default:
					return new ModifiedData(-1);
				}
			else
				return changeBlockData(block, state, val, 11);
		} else {
			return new ModifiedData(-3);
		}
		return new ModifiedData(-4);
	}
	
	public static Object getBlockData(Block block, String state) {
		@SuppressWarnings("deprecation")
		byte data = block.getData();
		
		return dataReader(data, block.getType(), state, SupportedVersions.getInteger(null));
	}
	
	public static Object getBlockData(Block block, String state, int version) {
		@SuppressWarnings("deprecation")
		byte data = block.getData();
		
		return dataReader(data, block.getType(), state, version);
	}
	
	public static Object dataReader(byte data, Material material, String state) {
		return dataReader(data, material, state, SupportedVersions.getInteger(null));
	}
	
	public static Object dataReader(byte data, Material material, String state, int version) {
		if (BlockStates.fromMaterial(material) == null)
			return null;
		
		BlockStates type = BlockStates.fromMaterial(material);
		if (version == 8) {
			if (BlockStates18.has(type.toString())) {
				if (!type.hasState(state))
					return -4;
				
				switch(type.toEnum18()) {
				case ANVIL:
					if (state.equalsIgnoreCase("facing"))
						if (data == 0 || data == 4 || data == 8)
							return "south";
						else if (data == 1 || data == 5 || data == 9)
							return "west";
						else if (data == 2 || data == 6 || data == 10)
							return "north";
						else if (data == 3 || data == 7 || data == 11)
							return "east";
					
					if (state.equalsIgnoreCase("damage"))
						if (data < 4)
							return 0;
						else if (data < 8)
							return 1;
						else if (data < 12)
							return 2;
					break;
				case PRISMARINE:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case DIRT:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case DOUBLE_PLANT:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case SMOOTH_BRICK:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case QUARTZ_BLOCK:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case COBBLE_WALL:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case RED_SANDSTONE:
					if (state.equalsIgnoreCase("type"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case SANDSTONE:
					if (state.equalsIgnoreCase("type"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case STONE_SLAB2:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get(data == 0 ? 0 : 1);
					break;
				case MONSTER_EGGS:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case JUKEBOX:
					if (state.equalsIgnoreCase("has_record"))
						return (boolean)type.byteToValue(state, data, 0);
					break;
				case COMMAND:
					if (state.equalsIgnoreCase("triggered"))
						return (boolean)type.byteToValue(state, data, 0);
					break;
				case CHEST:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case LADDER:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case HOPPER:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case WALL_BANNER:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case STANDING_BANNER:
					if (state.equalsIgnoreCase("rotation"))
						return (int)data;
					break;
				case SIGN_POST:
					if (state.equalsIgnoreCase("rotation"))
						return (int)data;
					break;
				case WALL_SIGN:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case STAINED_GLASS_PANE:
					if (state.equalsIgnoreCase("color"))
						return (String)type.getAllowedValues(state).get((int)data);
					break;
				case STAINED_GLASS:
					if (state.equalsIgnoreCase("color"))
						return (String)type.getAllowedValues(state).get((int)data);
					break;
				case CARPET:
					if (state.equalsIgnoreCase("color"))
						return (String)type.getAllowedValues(state).get((int)data);
					break;
				case CACTUS:
					if (state.equalsIgnoreCase("age"))
						return (int)data;
					break;
				case STEM:
					if (state.equalsIgnoreCase("age"))
						return (int)data;
					break;
				case CARROT:
					if (state.equalsIgnoreCase("age"))
						return (int)data;
					break;
				case POTATO:
					if (state.equalsIgnoreCase("age"))
						return (int)data;
					break;
				case NETHER_WARTS:
					if (state.equalsIgnoreCase("age"))
						return (int)data;
					break;
				case CAULDRON:
					if (state.equalsIgnoreCase("level"))
						return (int)data;
					break;
				case SUGAR_CANE_BLOCK:
					if (state.equalsIgnoreCase("age"))
						return (int)data;
					break;
				case CROPS:
					if (state.equalsIgnoreCase("age"))
						return (int)data;
					break;
				case SOIL:
					if (state.equalsIgnoreCase("moisture"))
						return (int)data;
					break;
				case WOOL:
					if (state.equalsIgnoreCase("color"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case STAINED_CLAY:
					if (state.equalsIgnoreCase("color"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case SNOW:
					if (state.equalsIgnoreCase("layers"))
						return (int)type.byteToValue(state, data, 0);
					break;
				case FURNACE:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, 0);
					if (state.equalsIgnoreCase("lit"))
						return material.equals(Material.BURNING_FURNACE);
					break;
				case TORCH:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					break;
				case REDSTONE_TORCH:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					if (state.equalsIgnoreCase("lit"))
						return material.equals(Material.REDSTONE_TORCH_ON);
					break;
				case LONG_GRASS:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case RED_ROSE:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case RAILS:
					if (state.equalsIgnoreCase("shape"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case RAIL:
					if (state.equalsIgnoreCase("shape"))
						return (String)type.byteToValue(state, data, (int)data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("powered"))
						return data > 7;
					break;
				case WOOD_STEP:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.getAllowedValues(state).get((int)(data-(data >= 8 ? 8 : 0)));
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get((int)data <= 7 ? 0 : 1);
					break;
				case STEP:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.getAllowedValues(state).get((int)(data-(data >= 8 ? 8 : 0)));
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get((int)data <= 7 ? 0 : 1);
					break;
				case STAIRS:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, (byte)0, (byte)(data >= 4 ? data-4 : data));
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get((int)data <= 3 ? 0 : 1);
					break;
				case SKULL:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("nodrop"))
						return (boolean)type.getAllowedValues(state).get((int)data > 7 ? 1 : 0);
					break;
				case DOUBLE_STEP:
					if (state.equalsIgnoreCase("variant"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("seamless"))
						return (boolean)type.getAllowedValues(state).get((int)data > 7 ? 1 : 0);
					break;
				case DOUBLE_STONE_SLAB2:
					if (state.equalsIgnoreCase("seamless"))
						return (boolean)type.getAllowedValues(state).get((int)data > 7 ? 1 : 0);
					break;
				case WOOD_DOUBLE_STEP:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case WOOD:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case FENCE:
					@SuppressWarnings("deprecation") byte d = (byte) type.b().getId();
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, d, 0);
					break;
				case STONE:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case TNT:
					if (state.equalsIgnoreCase("explode"))
						return (boolean)type.byteToValue(state, data, 0);
					break;
				case SAND:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case SPONGE:
					if (state.equalsIgnoreCase("wet"))
						return (boolean)type.byteToValue(state, data, 0);
					break;
				case HUGE_MUSHROOM:
					if (state.equalsIgnoreCase("variant"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case LOG_2:
					if (state.equalsIgnoreCase("axis"))
						if (data == 0 || data == 1)
							return "y";
						else if (data == 4 || data == 5)
							return "x";
						else if (data == 8 || data == 9)
							return "z";
						else if (data == 12 || data == 13)
							return "none";
					
					if (state.equalsIgnoreCase("variant"))
						if (data == 0 || data == 4 || data == 8 || data == 12) //acacia
							return "acacia";
						else if (data == 1 || data == 5 || data == 9 || data == 13) //dark_oak
							return "dark_oak";
					break;
				case LOG:
					if (state.equalsIgnoreCase("axis"))
						if (data < 4)
							return "y";
						else if (data < 8)
							return "x";
						else if (data < 12)
							return "z";
						else if (data < 16)
							return "none";
					if (state.equalsIgnoreCase("variant"))
						if (data == 0 || data == 4 || data == 8 || data == 12) //oak
							return "oak";
						else if (data == 1 || data == 5 || data == 9 || data == 13) //spruce
							return "spruce";
						else if (data == 2 || data == 6 || data == 10 || data == 14) //birch
							return "birch";
						else if (data == 3 || data == 7 || data == 11 || data == 15) //jungle
							return "jungle";
					break;
				case PISTON_EXTENSION:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("type"))
						return data > 7 ? "sticky" : "normal";
					break;
				case CAKE_BLOCK:
					if (state.equalsIgnoreCase("bites"))
						return (int)data;
					break;
				case HAY_BLOCK:
					if (state.equalsIgnoreCase("axis"))
						return type.byteToValue(state, data, 0);
					break;
				case PUMPKIN:
					@SuppressWarnings("deprecation") byte d_ = (byte) type.b().getId();
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					if (state.equalsIgnoreCase("lit"))
						return type.byteToValue(state, d_, 0);
					break;
				case JACK_O_LANTERN:
					@SuppressWarnings("deprecation") byte d__ = (byte) type.b().getId();
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					if (state.equalsIgnoreCase("lit"))
						return type.byteToValue(state, d__, 0);
					break;
				case DISPENSER:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					break;
				case DROPPER:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					break;
				case BUTTON:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, (int)data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("powered"))
						return data > 7;
					break;
				case LEAVES:
					if (state.equalsIgnoreCase("check_decay"))
						return Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15).contains((int)data);
					
					if (state.equalsIgnoreCase("decayable"))
						return Arrays.asList(0, 1, 2, 3, 8, 9, 10, 11).contains((int)data);
					
					if (state.equalsIgnoreCase("variant"))
						if (data == 0 || data == 4 || data == 8 || data == 12)
							return "oak";
						else if (data == 1 || data == 5 || data == 9 || data == 13)
							return "spruce";
						else if (data == 2 || data == 6 || data == 10 || data == 14)
							return "birch";
						else if (data == 3 || data == 7 || data == 11 || data == 15)
							return "jungle";
					
					break;
				case LEAVES_2:
					if (state.equalsIgnoreCase("check_decay"))
						return Arrays.asList(8, 9, 12, 13).contains((int)data);
					
					if (state.equalsIgnoreCase("decayable"))
						return Arrays.asList(0, 1, 8, 9).contains((int)data);
					
					if (state.equalsIgnoreCase("variant"))
						if (data == 0 || data == 4 || data == 8 || data == 12)
							return "acacia";
						else if (data == 1 || data == 5 || data == 9 || data == 13)
							return "dark_oak";
					
					break;
				case FENCE_GATE:
					if (state.equalsIgnoreCase("open"))
						return Arrays.asList(4, 5, 6, 7, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("powered"))
						return Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("facing"))
						if (Arrays.asList(0, 4, 8, 12).contains((int)data))
							return "south";
						else if (Arrays.asList(1, 5, 9, 13).contains((int)data))
							return "west";
						else if (Arrays.asList(2, 6, 10, 14).contains((int)data))
							return "north";
						else if (Arrays.asList(3, 7, 11, 15).contains((int)data))
							return "east";
					break;
				case ENDER_PORTAL_FRAME:
					if (state.equalsIgnoreCase("eye"))
						return data > 3;
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 3 ? -4 : 0);
					break;
				case COCOA:
					if (state.equalsIgnoreCase("age"))
						if (Arrays.asList(0, 1, 2, 3).contains((int)data))
							return 0;
						else if (Arrays.asList(4, 5, 6, 7).contains((int)data))
							return 1;
						else if (Arrays.asList(8, 9, 10, 11).contains((int)data))
							return 2;
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, -(int)dataReader(data, material, "age", version)*4);
					break;
				case DIODE_BLOCK:
					if (state.equalsIgnoreCase("powered"))
						return material.equals(Material.DIODE_BLOCK_ON);
					if (state.equalsIgnoreCase("delay"))
						if (Arrays.asList(0, 1, 2, 3).contains((int)data))
							return 1;
						else if (Arrays.asList(4, 5, 6, 7).contains((int)data))
							return 2;
						else if (Arrays.asList(8, 9, 10, 11).contains((int)data))
							return 3;
						else if (Arrays.asList(12, 13, 14, 15).contains((int)data))
							return 4;
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, -(((int)dataReader(data, material, "delay", version)-1)*4));
					break;
				case REDSTONE_COMPARATOR:
					if (state.equalsIgnoreCase("powered"))
						return Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("facing"))
						if (data == 0 || data == 4 || data == 8 || data == 12)
							return "south";
						else if (data == 1 || data == 5 || data == 9 || data == 13)
							return "west";
						else if (data == 2 || data == 6 || data == 10 || data == 14)
							return "north";
						else if (data == 3 || data == 7 || data == 11 || data == 15)
							return "east";
					if (state.equalsIgnoreCase("mode"))
						if (Arrays.asList(0, 1, 2, 3, 8, 9, 10, 11).contains((int)data))
							return "compare";
						else if (Arrays.asList(4, 5, 6, 7, 12, 13, 14, 15).contains((int)data))
							return "subtract";
					break;
				case IRON_PLATE:
					if (state.equalsIgnoreCase("power"))
						return type.byteToValue(state, data, 0);
					break;
				case GOLD_PLATE:
					if (state.equalsIgnoreCase("power"))
						return type.byteToValue(state, data, 0);
					break;
				case STONE_PLATE:
					if (state.equalsIgnoreCase("powered"))
						return type.byteToValue(state, data, 0);
					break;
				case WOOD_PLATE:
					if (state.equalsIgnoreCase("powered"))
						return type.byteToValue(state, data, 0);
					break;
				case REDSTONE_WIRE:
					if (state.equalsIgnoreCase("power"))
						return type.byteToValue(state, data, 0);
					break;
				case LEVER:
					if (state.equalsIgnoreCase("powered"))
						return data > 7;
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					break;
				case PISTON:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("extended"))
						return data > 7;
					break;
				case TRAP_DOOR:
					if (state.equalsIgnoreCase("open"))
						return Arrays.asList(4, 5, 6, 7, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get((int)data <= 7 ? 0 : 1);
					if (state.equalsIgnoreCase("facing"))
						if (Arrays.asList(0, 4, 8, 12).contains((int)data))
							return "north";
						else if (Arrays.asList(1, 5, 9, 13).contains((int)data))
							return "south";
						else if (Arrays.asList(2, 6, 10, 14).contains((int)data))
							return "west";
						else if (Arrays.asList(3, 7, 11, 15).contains((int)data))
							return "east";
					break;
				case IRON_TRAPDOOR:
					if (state.equalsIgnoreCase("open"))
						return Arrays.asList(4, 5, 6, 7, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get((int)data <= 7 ? 0 : 1);
					if (state.equalsIgnoreCase("facing"))
						if (Arrays.asList(0, 4, 8, 12).contains((int)data))
							return "north";
						else if (Arrays.asList(1, 5, 9, 13).contains((int)data))
							return "south";
						else if (Arrays.asList(2, 6, 10, 14).contains((int)data))
							return "west";
						else if (Arrays.asList(3, 7, 11, 15).contains((int)data))
							return "east";
					break;
				case PORTAL:
					if (state.equalsIgnoreCase("axis"))
						return type.byteToValue(state, data, 0);
					break;
				case TRIPWIRE:
					if (state.equalsIgnoreCase("powered"))
						return Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15).contains((int)data);
					if (state.equalsIgnoreCase("suspended"))
						return Arrays.asList(2, 3, 6, 7, 10, 11, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("attached"))
						return Arrays.asList(4, 5, 6, 7, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("disarmed"))
						return Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15).contains((int)data);
					break;
				case TRIPWIRE_HOOK:
					if (state.equalsIgnoreCase("powered"))
						return Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("attached"))
						return Arrays.asList(4, 5, 6, 7, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("facing"))
						if (Arrays.asList(0, 4, 8, 12).contains((int)data))
							return "south";
						else if (Arrays.asList(1, 5, 9, 13).contains((int)data))
							return "west";
						else if (Arrays.asList(2, 6, 10, 14).contains((int)data))
							return "north";
						else if (Arrays.asList(3, 7, 11, 15).contains((int)data))
							return "east";
					break;
				case SAPLING:
					if (state.equalsIgnoreCase("type"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("stage"))
						return type.getAllowedValues(state).get((int)data > 7 ? 1 : 0);
					break;
				case BED_BLOCK:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("part"))
						return type.getAllowedValues(state).get((int)data > 7 ? 1 : 0);
					break;
				case DOOR:
					if (state.equalsIgnoreCase("facing"))
						if (Arrays.asList(1, 5).contains((int)data))
							return "south";
						else if (Arrays.asList(2, 6).contains((int)data))
							return "west";
						else if (Arrays.asList(3, 7).contains((int)data))
							return "north";
						else if (Arrays.asList(0, 4).contains((int)data))
							return "east";
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get((int)data > 7 ? 1 : 0);
					if (state.equalsIgnoreCase("open"))
						return Arrays.asList(4, 5, 6, 7).contains((int)data);
					break;
				case VINE:
					if (state.equalsIgnoreCase("north"))
						return Arrays.asList(4, 5, 6, 7, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("east"))
						return Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15).contains((int)data);
					if (state.equalsIgnoreCase("south"))
						return Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15).contains((int)data);
					if (state.equalsIgnoreCase("west"))
						return Arrays.asList(2, 3, 6, 7, 10, 11, 14, 15).contains((int)data);
					break;
				default:
					return -1;
				}
			} else
				return -4;
		} else if (version == 9) {
			if (BlockStates19.has(type.toString())) {
				switch(type.toEnum19()) {
				case TRIPWIRE:
					if (state.equalsIgnoreCase("powered"))
						return Arrays.asList(1, 5, 9, 13).contains((int)data);
					if (state.equalsIgnoreCase("attached"))
						return Arrays.asList(4, 5, 12, 13).contains((int)data);
					if (state.equalsIgnoreCase("disarmed"))
						return Arrays.asList(8, 9, 12, 13).contains((int)data);
					break;
				case STAIRS:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, (byte)0, (byte)(data >= 4 ? data-4 : data));
					if (state.equalsIgnoreCase("half"))
						return (String)type.getAllowedValues(state).get((int)data <= 3 ? 0 : 1);
					break;
				case COMMAND:
					if (state.equalsIgnoreCase("type"))
						if (material.equals(Material.COMMAND))
							return "impulse";
						else if (material.equals(Material.valueOf("COMMAND_CHAIN")))
							return "chain";
						else if (material.equals(Material.valueOf("COMMAND_REPEATING")))
							return "repeating";
					if (state.equalsIgnoreCase("conditional"))
						return type.getAllowedValues(state).get((int)data > 7 ? 1 : 0);
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					break;
				case PURPUR_PILLAR:
					if (state.equalsIgnoreCase("axis"))
						return type.byteToValue(state, data, 0);
					break;
				case PURPUR_SLAB:
					if (state.equalsIgnoreCase("half"))
						return type.byteToValue(state, data, 0);
					break;
				case CHORUS_FLOWER:
					if (state.equalsIgnoreCase("age"))
						return type.byteToValue(state, data, 0);
					break;
				case FROSTED_ICE:
					if (state.equalsIgnoreCase("age"))
						return type.byteToValue(state, data, 0);
					break;
				case END_ROD:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					break;
				case STRUCTURE_BLOCK:
					if (state.equalsIgnoreCase("mode"))
						return type.byteToValue(state, data, 0);
					break;
				case BEETROOT_BLOCK:
					if (state.equalsIgnoreCase("age"))
						return type.byteToValue(state, data, 0);
					break;
				default:
					return -1;
				}
			} else {
				return dataReader(data, material, state, 8);
			}
		} else if (version == 10) {
			if (BlockStates110.has(type.toString())) {
				switch(type.toEnum110()) {
				case BONE_BLOCK:
					if (state.equalsIgnoreCase("axis"))
						return type.byteToValue(state, data, 0);
					break;
				default:
					return -1;
				}
			} else {
				return dataReader(data, material, state, 9);
			}
		} else if (version == 11) {
			if (BlockStates111.has(type.toString()))
				switch(type.toEnum111()) {
				case OBSERVER:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, data > 7 ? -8 : 0);
					if (state.equalsIgnoreCase("powered"))
						return type.getAllowedValues(state).get(data > 7 ? 1 : 0);
					break;
				case SHULKER_BOX:
					if (state.equalsIgnoreCase("facing"))
						return type.byteToValue(state, data, 0);
					/*if (state.equalsIgnoreCase("color"))
						if (material.toString().split("_").length == 3)
							return material.toString().split("_")[0].toLowerCase();
						else if (material.toString().split("_").length == 4)
							return material.toString().split("_")[0].toLowerCase()
									+"_"+material.toString().split("_")[1].toLowerCase();*/
					break;
				default:
					return -1;
				}
			else
				return dataReader(data, material, state, 10);
		} else if (version == 12) {
			if (BlockStates112.has(type.toString()))
				switch(type.toEnum112()) {
				case CONCRETE:
					if (state.equalsIgnoreCase("color"))
						return (String)type.byteToValue(state, data, 0);
					break;
				case GLAZED_TERRACOTTA:
					if (state.equalsIgnoreCase("facing"))
						return (String)type.byteToValue(state, data, 0);
					if (state.equalsIgnoreCase("color"))
						if (material.toString().split("_").length == 3)
							return material.toString().split("_")[0].toLowerCase();
						else if (material.toString().split("_").length == 4)
							return material.toString().split("_")[0].toLowerCase()
									+"_"+material.toString().split("_")[1].toLowerCase();
					break;
				default:
					return -1;
				}
			else
				return dataReader(data, material, state, 11);
		} else {
			return -3;
		}
		return -4;
	}
	
	public static boolean hasDataErrors(Object data) {
		if (data instanceof Integer)
			return (int)data == -1 || (int)data == -2 || (int)data == -3 || (int)data == -4;
		return false;
	}
	
	/**
	 * @param data block's data
	 * @return if data param is -1 then <i>block missing</i> string will be returned<br>-2 state missing<br>
	 * -3 version not supported<br>-4 no such state<br>else null, meaning no error.
	 */
	public static String readDataError(Object data) {
		if (data instanceof Integer)
			if ((int)data == -1)
				return "block missing";
			else if ((int)data == -2)
				return "state missing";
			else if ((int)data == -3)
				return "version not supported";
			else if ((int)data == -4)
				return "no such state";
			else
				return null;
		else
			return null;
	}
	
	public static String nextState(BlockStates bstate, String currentstate, boolean backwards) {
		List<String> a = bstate.getStates();
		int max = a.size()-1;
		int now = getByIdState(bstate, currentstate, backwards);
		if (backwards)
			a = Lists.reverse(a);
			
		int x = now == max ? 0 : now + 1;
		return a.get(x);
	}
	
	public static Object nextStateValue(BlockStates bstate, String state, Object currentvalue, boolean backwards) {
		List<Object> a = bstate.getAllowedValues(state);
		int max = a.size()-1;
		int now = getById(bstate, state, currentvalue, backwards);
		if (backwards)
			a = Lists.reverse(a);
		
		int x = now == max ? 0 : now + 1;
		return a.get(x);
	}

	private static int getByIdState(BlockStates st, String state, boolean backwards) {
		List<String> a = st.getStates();
		int i = 0;
		int id = -1;
		if (backwards)
			a = Lists.reverse(a);
		for (String b : a) {
			if (b.equalsIgnoreCase(state)) {
				id = i;
			}
			i++;
		}
		return id;
	}
	
	private static int getById(BlockStates arg1, String arg2, Object value, boolean backwards) {
		List<Object> a = arg1.getAllowedValues(arg2);
		int i = 0;
		int id = -1;
		if (backwards)
			a = Lists.reverse(a);
		for (Object b : a) {
			if (b.equals(value)) {
				id = i;
			}
			i++;
		}
		return id;
	}
	
	public static ItemStack createDebugStick(DebugStickPlugin plg) {
		try {
			boolean enchanted = (boolean)plg.getConfigValue("item", "enchanted");
			byte data = Integer.valueOf(plg.getConfigValue("item", "data", Integer.class)).byteValue();
			Material material = Material.valueOf(plg.getConfigValue("item", "material", String.class));
			List<String> lores = plg.getConfig().getStringList("item.lores");
			List<String> colored_lores = new ArrayList<>();
			if (plg.getConfigValue("item", "colored-lores", boolean.class))
				for (String lore : lores)
					colored_lores.add(ChatColor.translateAlternateColorCodes('&', lore));
			else
				colored_lores.addAll(lores);
			String customname = plg.getConfigValue("item", "custom-name", String.class);
			@SuppressWarnings("deprecation")
			ItemStack stick = new ItemStack(material, 1, (short)0, data);
			if (enchanted) stick.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, -1);
			ItemMeta meta = stick.getItemMeta();
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r&f"+customname));
			meta.setLore(colored_lores);
			if (enchanted) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			stick.setItemMeta(meta);
			return stick;
		} catch (IllegalArgumentException e) {
			plg.getLogger().severe("Couldn't create debug stick item. Unknown material.");
			return null;
		}
	}
	
	public static void give(DebugStickPlugin plugin, Player p) {
		ItemStack i = createDebugStick(plugin);
		if (i != null)
			p.getInventory().addItem(i);
	}

	@SuppressWarnings("deprecation")
	public static boolean verifyItem(DebugStickPlugin plg, ItemStack i) {
		try {
			boolean enchanted = (boolean)plg.getConfigValue("item", "enchanted");
			Material material = Material.valueOf((String)plg.getConfigValue("item", "material"));
			byte data = Integer.valueOf(plg.getConfigValue("item", "data", Integer.class)).byteValue();
			String customname = (String)plg.getConfigValue("item", "custom-name");
			return ((enchanted ? i.getEnchantments().containsKey(Enchantment.ARROW_INFINITE) : true) &&
					(i.getItemMeta().hasDisplayName() ?
							i.getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&r&f"+customname)) : false) &&
					(enchanted ? i.getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS) : true) &&
					i.getType().equals(material) && i.getData().getData() == data);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
}
