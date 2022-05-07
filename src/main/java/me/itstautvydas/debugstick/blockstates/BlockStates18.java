package me.itstautvydas.debugstick.blockstates;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum BlockStates18 {
	ANVIL(s("facing", "damage"), v(
			g("north", "north", "east", "south", "west"),
			g(0, 0, 1, 2)),
			dv(va(2, 3, 0, 1), va(0, 4, 8))),
	WALL_BANNER(s("facing"), v(
			g("north", "north", "east", "south", "west")),
			dv(va(2, 5, 3, 4))),
	WALL_SIGN(s("facing"), v(
			g("north", "north", "east", "south", "west")),
			dv(va(2, 5, 3, 4))),
	CHEST(s("facing"), v(
			g("north", "north", "east", "south", "west")),
			dv(va(2, 5, 3, 4)), new Material[] {Material.CHEST, Material.TRAPPED_CHEST, Material.ENDER_CHEST}),
	LADDER(s("facing"), v(
			g("north", "north", "east", "south", "west")),
			dv(va(2, 5, 3, 4))),
	STANDING_BANNER(s("rotation"), v(
			g(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	SIGN_POST(s("rotation"), v(
			g(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	LEAVES(s("check_decay", "decayable", "variant"), v(
			g(false, false, true),
			g(false, false, true),
			g("oak", "oak", "spruce", "birch", "jungle")), dv(va(0, 8), va(4, 0), va(0, 1, 2, 3))),
	LEAVES_2(s("check_decay", "decayable", "variant"), v(
			g(false, false, true),
			g(false, false, true),
			g("acacia", "acacia", "dark_oak")), dv(va(0, 8), va(4, 0), va(0, 1))),
	FURNACE(s("facing", "lit"), v(
			g("north", "north", "east", "south", "west"),
			g(false, false, true)),
			dv(va(2, 5, 3, 4), null), new Material[] {Material.FURNACE, Material.BURNING_FURNACE}),
	FENCE(s("variant"), v(
			g("oak", "oak", "spruce", "birch", "jungle", "dark_oak", "acacia")),
			dv(va(85, 188, 189, 190, 191, 192)), new Material[] {Material.FENCE, Material.SPRUCE_FENCE,
					Material.BIRCH_FENCE, Material.JUNGLE_FENCE, Material.DARK_OAK_FENCE, Material.ACACIA_FENCE}),
	WOOL(s("color"), v(
			g("white", "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver",
					"cyan", "purple", "blue", "brown", "green", "red", "black")), dv(
			va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	STAINED_CLAY(s("color"), v(g("white", "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver",
			"cyan", "purple", "blue", "brown", "green", "red", "black")), dv(
					va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	WOOD(s("variant"), v(g("oak", "oak", "spruce", "birch", "jungle", "acacia", "dark_oak")), dv(va(0, 1, 2, 3, 4, 5))),
	WOOD_STEP(s("variant", "half"), v(
			g("oak", "oak", "spruce", "birch", "jungle", "acacia", "dark_oak"),
			g("bottom", "bottom", "top")
			), dv(
					va(0, 1, 2, 3, 4, 5),
					va(0, 8))),
	STEP(s("variant", "half"), v(
			g("stone", "stone", "sandstone", "wood_old", "cobblestone", "brick", "stone_brick", "nether_brick", "quartz"),
			g("bottom", "bottom", "top")), dv(
					va(0, 1, 2, 3, 4, 5, 6, 7),
					va(0, 8))),
	WOOD_DOUBLE_STEP(s("variant"), v(g("oak", "oak", "spruce", "birch", "jungle", "acacia", "dark_oak")), dv(va(0, 1, 2, 3, 4, 5))),
	DOUBLE_STEP(s("variant", "seamless"), v(
			g("stone", "stone", "sandstone", "wood_old", "cobblestone", "brick", "stone_brick", "nether_brick", "quartz"),
			g(false, false, true)), dv(va(0, 1, 2, 3, 4, 5, 6, 7), va(0, 8))),
	DOUBLE_STONE_SLAB2(s("seamless"), v(g(false, false, true)), dv(va(0, 8))),
	STONE(s("variant"), v(
			g("stone", "stone", "granite", "smooth_granite", "diorite", "smooth_diorite", "andesite", "smooth_andesite")), 
			dv(
					va(0, 1, 2, 3, 4, 5, 6))),
	CROPS(s("age"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7)), dv(va(0, 1, 2, 3, 4, 5, 6, 7))),
	SOIL(s("moisture"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7)), dv(va(0, 1, 2, 3, 4, 5, 6, 7))),
	LONG_GRASS(s("variant"), v(g("dead_bush", "dead_bush", "tall_grass", "fern")), dv(va(0, 1, 2))),
	RED_ROSE(s("variant"), v(
			g("poppy", "poppy", "blue_orchid", "allium", "houstonia", "red_tulip", "orange_tulip", "white_tulip", "pink_tulip",
					"oxeye_daisy")), dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8))),
	TNT(s("explode"), v(g(false, false, true)), dv(va(0, 1))),
	TORCH(s("facing"), v(g("up", "up", "east", "south", "west", "north")), dv(va(5, 1, 3, 2, 4))),
	REDSTONE_TORCH(s("facing", "lit"), v(g("up", "up", "east", "south", "west", "north"), g(true, false, true)),
			dv(va(5, 1, 3, 2, 4), va(76, 75)), new Material[] {Material.REDSTONE_TORCH_ON, Material.REDSTONE_TORCH_OFF}),
	SPONGE(s("wet"), v(g(false, false, true)), dv(va(0, 1))),
	SAND(s("variant"), v(g("sand", "sand", "red_sand")), dv(va(0, 1))),
	STAINED_GLASS_PANE(s("color"), v(
			g("white", "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver",
					"cyan", "purple", "blue", "brown", "green", "red", "black")), dv(
							va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	CARPET(s("color"), v(
			g("white", "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver",
					"cyan", "purple", "blue", "brown", "green", "red", "black")), dv(
							va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	STAINED_GLASS(s("color"), v(
			g("white", "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver",
					"cyan", "purple", "blue", "brown", "green", "red", "black")), dv(
							va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	BUTTON(s("facing", "powered"), v(
			g("north", "north", "east", "south", "west", "up", "down"),
			g(false, false, true)),
			dv(
					va(4, 1, 3, 2, 5, 0),
					va(0, 8)), new Material[] {Material.STONE_BUTTON, Material.WOOD_BUTTON}),
	PRISMARINE(s("variant"), v(g("prismarine", "prismarine", "prismarine_bricks", "dark_prismarine")), dv(va(0, 1, 2))),
	DIRT(s("variant"), v(g("dirt", "dirt", "coarse_dirt", "podzol")), dv(va(0, 1, 2))),
	SMOOTH_BRICK(s("variant"), v(g("stonebrick", "stonebrick", "mossy_stonebrick", "cracked_stonebrick", "chiseled_stonebrick")), 
			dv(va(0, 1, 2, 3))),
	QUARTZ_BLOCK(s("variant"), v(g("default", "default", "chiseled", "lines", "lines x", "lines z")), dv(va(0, 1, 2, 3, 4))),
	COBBLE_WALL(s("variant"), v(g("cobblestone", "cobblestone", "mossy_cobblestone")), dv(va(0, 1))),
	RED_SANDSTONE(s("type"), v(g("red_sandstone", "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone")), 
			dv(va(0, 1, 2))),
	SANDSTONE(s("type"), v(g("sandstone", "sandstone", "chiseled_sandstone", "smooth_sandstone")), 
			dv(va(0, 1, 2))),
	STONE_SLAB2(s("half"), v(g("bottom", "bottom", "top")), dv(va(0, 8))),
	SNOW(s("layers"), v(g(1, 1, 2, 3, 4, 5, 6, 7, 8)), dv(va(0, 1, 2, 3, 4, 5, 6, 7))),
	MONSTER_EGGS(s("variant"), v(g("stone", "stone", "cobblestone", "stone_brick", "mossy_brick", "cracked_brick", "chiseled_brick")), 
			dv(va(0, 1, 2, 3, 4, 5))),
	JUKEBOX(s("has_record"), v(g(false, false, true)), dv(va(0, 1))),
	DISPENSER(s("facing"), v(g("down", "down", "north", "east", "south", "west", "up")), dv(va(0, 2, 5, 3, 4, 1))),
	DROPPER(s("facing"), v(g("down", "down", "north", "east", "south", "west", "up")), dv(va(0, 2, 5, 3, 4, 1))),
	RAILS(s("shape"), v(
			g("north_south", "north_south", "east_west", "ascending_north", "ascending_east", "ascending_south", "ascending_west")),
			dv(va(0, 1, 4, 2, 5, 3))),
	RAIL(s("powered", "shape"), v(
			g(false, false, true),
			g("north_south", "north_south", "east_west", "ascending_north", "ascending_east", "ascending_south", "ascending_west")),
			dv(va(0, 8), va(0, 1, 4, 2, 5, 3)), new Material[] {Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.ACTIVATOR_RAIL}),
	LOG(s("axis", "variant"), v(
			g("y", "y", "x", "z", "none"),
			g("oak", "oak", "spruce", "birch", "jungle")), dv(
					va(0, 4, 8, 12),
					va(0, 1, 2, 3))),
	LOG_2(s("axis", "variant"), v(
			g("y", "y", "x", "z", "none"),
			g("acacia", "acacia", "dark_oak")), dv(
					va(0, 4, 8, 12),
					va(0, 1))),
	HAY_BLOCK(s("axis"), v(g("y", "y", "x", "z")), dv(va(0, 4, 8))),
	CACTUS(s("age"), v(
			g(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	SUGAR_CANE_BLOCK(s("age"), v(
			g(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	NETHER_WARTS(s("age"), v(g(0, 0, 1, 2, 3)), dv(va(0, 1, 2, 3))),
	CAULDRON(s("level"), v(g(0, 0, 1, 2, 3)), dv(va(0, 1, 2, 3))),
	COMMAND(s("triggered"), v(g(false, false, true)), dv(va(0, 1))),
	ENDER_PORTAL_FRAME(s("eye", "facing"), v(g(false, false, true),
			g("north", "north", "east", "south", "west")), dv(va(0, 4), va(2, 3, 0, 1))),
	PUMPKIN(s("facing", "lit"), v(g("north", "north", "east", "south", "west"), g(false, false, true)), dv(va(2, 3, 0, 1), va(86, 91))),
	JACK_O_LANTERN(s("facing", "lit"), v(g("north", "north", "east", "south", "west"), g(true, false, true)), dv(va(2, 3, 0, 1), va(86, 91))),
	CAKE_BLOCK(s("bites"), v(g(0, 0, 1, 2, 3, 4, 5, 6)), dv(va(0, 1, 2, 3, 4, 5, 6))),
	STAIRS(s("facing", "half"), v(g("north", "north", "east", "south", "west"), g("bottom", "bottom", "top")), dv(va(3, 0, 2, 1), va(0, 4)),
			new Material[] {Material.WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.SMOOTH_STAIRS,
					Material.NETHER_BRICK_STAIRS, Material.SANDSTONE_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.BIRCH_WOOD_STAIRS,
					Material.JUNGLE_WOOD_STAIRS, Material.QUARTZ_STAIRS, Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS,
					Material.RED_SANDSTONE_STAIRS}),
	HUGE_MUSHROOM(s("variant"), v(g("all_inside", "all_inside", "north_west", "north", "north_east", "west", "center", "east",
			"south_west", "south", "south_east", "stem", "all_outside", "all_stem")), dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 14, 15)),
			new Material[] {Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2}),
	CARROT(s("age"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7)), dv(va(0, 1, 2, 3, 4, 5, 6, 7))),
	POTATO(s("age"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7)), dv(va(0, 1, 2, 3, 4, 5, 6, 7))),
	HOPPER(s("facing"), v(g("down", "down", "north", "east", "south", "west")), dv(va(0, 2, 5, 3, 4))),
	DOUBLE_PLANT(s("variant"), v(g("sunflower", "sunflower", "syringa", "double_grass", "double_fern", "double_rose",
			"paeonia")), dv(va(0, 1, 2, 3, 4, 5))),
	SKULL(s("facing", "nodrop"), v(g("up", "down", "up", "north", "east", "south", "west"), g(false, false, true)), dv(va(0, 1, 2, 5, 3, 4),
			va(0, 8))),
	COCOA(s("age", "facing"), v(g(0, 0, 1, 2), g("south", "south", "west", "north", "east")), dv(va(0, 4, 8), va(0, 1, 2, 3))),
	IRON_PLATE(s("power"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	GOLD_PLATE(s("power"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	WOOD_PLATE(s("powered"), v(g(false, false, true)), dv(va(0, 1))),
	STONE_PLATE(s("powered"), v(g(false, false, true)), dv(va(0, 1))),
	REDSTONE_WIRE(s("power"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))),
	LEVER(s("facing", "powered"), v(g("down_x", "down_x", "down_z", "up_x", "up_z", "north", "east", "south", "west"), g(false, false, true)),
			dv(va(0, 7, 6, 5, 4, 1, 3, 2), va(0, 8))),
	FENCE_GATE(s("facing", "open", "powered"), v(g("north", "north", "east", "south", "west"), g(false, false, true), g(false, false, true)),
			dv(va(2, 3, 0, 1), va(0, 4), va(0, 8)), new Material[] {Material.FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE,
					Material.JUNGLE_FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.DARK_OAK_FENCE_GATE}),
	PISTON(s("facing", "extended"), v(g("down", "down", "up", "north", "east", "south", "west"), g(false, false, true)),
			dv(va(0, 1, 2, 5, 3, 4), va(0, 8)), new Material[] {Material.PISTON_BASE, Material.PISTON_STICKY_BASE}),
	TRAP_DOOR(s("facing", "half", "open"), v(g("north", "north", "east", "south", "west"), g("bottom", "bottom", "top"),
			g(false, false, true)), dv(va(0, 3, 1, 2), va(0, 8), va(0, 4))),
	IRON_TRAPDOOR(s("facing", "half", "open"), v(g("north", "north", "east", "south", "west"), g("bottom", "bottom", "top"),
			g(false, false, true)), dv(va(0, 3, 1, 2), va(0, 8), va(0, 4))),
	PORTAL(s("axis"), v(g("x", "x", "z")), dv(va(1, 2))),
	TRIPWIRE(s("powered", "suspended", "attached", "disarmed"), v(g(false, false, true), g(false, false, true), g(false, false, true),
			g(false, false, true)), dv(va(0, 1), va(0, 2), va(0, 4), va(0, 8))),
	TRIPWIRE_HOOK(s("attached", "facing", "powered"), v(g(false, false, true), g("north", "north", "east", "south", "west"),
			g(false, false, true)), dv(va(0, 4), va(2, 3, 0, 1), va(0, 8))),
	DIODE_BLOCK(s("facing", "delay", "powered"), v(g("north", "north", "east", "south", "west"), g(1, 1, 2, 3, 4), g(false, false, true)),
			dv(va(2, 3, 0, 1), va(0, 4, 8, 12), null), new Material[] {Material.DIODE_BLOCK_ON, Material.DIODE_BLOCK_OFF}),
	REDSTONE_COMPARATOR(s("facing", "mode", "powered"), v(g("north", "north", "east", "south", "west"),
			g("compare", "compare", "subtract"), g(false, false, true)), dv(va(2, 3, 0, 1), va(0, 4), va(0, 8)),
			new Material[] {Material.REDSTONE_COMPARATOR_ON, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF}),
	BED_BLOCK(s("facing", "part"), v(g("north", "north", "east", "south", "west"), g("foot", "foot", "head")), dv(va(2, 3, 0, 1), va(0, 8))),
	SAPLING(s("type", "stage"), v(g("oak", "oak", "spruce", "birch", "jungle", "acacia", "dark_oak"),
			g(0, 0, 1)), dv(va(0, 1, 2, 3, 4, 5), va(0, 8))),
	DOOR(s("facing", "half", "open"), v(g("north", "north", "east", "south", "west"), g("lower", "lower", "upper"), g(false, false, true)),
			dv(va(3, 0, 1, 2), va(0, 8), va(0, 4)), new Material[] {Material.WOODEN_DOOR, Material.SPRUCE_DOOR, Material.BIRCH_DOOR,
					Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.IRON_DOOR_BLOCK}),
	STEM(s("age"), v(g(0, 0, 1, 2, 3, 4, 5, 6, 7)), dv(va(0, 1, 2, 3, 4, 5, 6, 7)), new Material[] {Material.MELON_STEM, Material.PUMPKIN_STEM}),
	PISTON_EXTENSION(s("facing", "type"), v(g("up", "up", "down", "north", "east", "south", "west"), g("normal", "normal", "sticky")),
			dv(va(1, 0, 2, 5, 3, 4), va(0, 8))),
	VINE(s("north", "east", "south", "west"), v(g(false, false, true), g(false, false, true), g(false, false, true), g(false, false, true)),
			dv(va(0, 4), va(0, 8), va(0, 1), va(0, 2)));
	
	protected List<String> supportedStates = new ArrayList<>();
	protected List<List<Object>> allowedValues = new ArrayList<>();
	protected List<List<Byte>> dataValues = new ArrayList<>();
	protected Material[] materials = null;
	
	BlockStates18(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues, Material[] materials) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
		this.materials = materials;
		
		
	}
	
	BlockStates18(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
	}
	
	public static boolean has(String e) {
		return _values().contains(e.toUpperCase());
	}
	
	public static List<String> _values() {
		List<String> a = new ArrayList<>();
		for (BlockStates18 b : values()) {
			a.add(b.toString());
		}
		return a;
	}
	
	private static List<String> s(String... a) {
		List<String> a_ = new ArrayList<>();
		for (String b : a) {
			a_.add(b);
		}
		return a_;
	}
	
	private static List<Object> g(Object defaultV, Object... o) {
		List<Object> o__ = new ArrayList<>();
		o__.add(defaultV);
		for (Object o_ : o) {
			o__.add(o_);
		}
		return o__;
	}
	
	@SafeVarargs
	private static List<List<Object>> v(List<Object>... obs) {
		List<List<Object>> obs_ = new ArrayList<>();
		for (List<Object> obs__ : obs) {
			obs_.add(obs__);
		}
		return obs_;
	}
	
	@SafeVarargs
	private static List<List<Byte>> dv(List<Byte>... d) {
		List<List<Byte>> obs_ = new ArrayList<>();
		for (List<Byte> obs__ : d) {
			obs_.add(obs__);
		}
		return obs_;
	}
	
	private static List<Byte> va(int... o) {
		List<Byte> o__ = new ArrayList<>();
		for (int o_ : o) {
			o__.add((byte)o_);
		}
		return o__;
	}
}
