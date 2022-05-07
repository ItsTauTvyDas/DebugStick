package me.itstautvydas.debugstick.blockstates;

import static me.itstautvydas.debugstick.util.OtherUtil.safeValueOf;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;;

public enum BlockStates19 {
	
	TRIPWIRE(s("powered", "attached", "disarmed"), v(g(false, false, true), g(false, false, true), g(false, false, true)),
			dv(va(0, 1), va(0, 4), va(0, 8))),
	COMMAND(s("facing", "conditional", "type"), v(g("down", "down", "up", "north", "east", "south", "west"), g(false, false, true),
			g("impulse", "impulse", "chain", "repeating")), dv(va(0, 1, 2, 5, 3, 4), va(0, 8)), new Material[] {Material.COMMAND,
					safeValueOf("COMMAND_CHAIN"), safeValueOf("COMMAND_REPEATING")}),
	STAIRS(s("facing", "half"), v(g("north", "north", "east", "south", "west"), g("bottom", "bottom", "top")), dv(va(3, 0, 2, 1), va(0, 4)),
			new Material[] {Material.WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.SMOOTH_STAIRS,
					Material.NETHER_BRICK_STAIRS, Material.SANDSTONE_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.BIRCH_WOOD_STAIRS,
					Material.JUNGLE_WOOD_STAIRS, Material.QUARTZ_STAIRS, Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS,
					Material.RED_SANDSTONE_STAIRS, safeValueOf("PURPUR_STAIRS")}),
	PURPUR_PILLAR(s("axis"), v(g("y", "y", "x", "z")), dv(va(0, 4, 8))),
	PURPUR_SLAB(s("half"), v(g("bottom", "bottom", "top")), dv(va(0, 8))),
	CHORUS_FLOWER(s("age"), v(g(0, 0, 1, 2, 3, 4, 5)), dv(va(0, 1, 2, 3, 4, 5))),
	FROSTED_ICE(s("age"), v(g(0, 0, 1, 2, 3)), dv(va(0, 1, 2, 3))),
	END_ROD(s("facing"), v(g("up", "up", "down", "north", "east", "south", "west")), dv(va(1, 0, 2, 5, 3, 4))),
	STRUCTURE_BLOCK(s("mode"), v(g("save", "save", "load", "corner", "data")), dv(va(0, 1, 2, 3))),
	BEETROOT_BLOCK(s("age"), v(g(0, 0, 1, 2, 3)), dv(va(0, 1, 2, 3)));
	
	protected List<String> supportedStates = new ArrayList<>();
	protected List<List<Object>> allowedValues = new ArrayList<>();
	protected List<List<Byte>> dataValues = new ArrayList<>();
	protected Material[] materials = null;
	
	BlockStates19(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues, Material[] materials) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
		this.materials = materials;
	}
	
	BlockStates19(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
	}
	
	public static boolean has(String e) {
		return _values().contains(e.toUpperCase());
	}
	
	public static List<String> _values() {
		List<String> a = new ArrayList<>();
		for (BlockStates19 b : values()) {
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
