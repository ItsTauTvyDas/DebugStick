package me.itstautvydas.debugstick.blockstates;

import static me.itstautvydas.debugstick.util.OtherUtil.safeValueOf;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;;

public enum BlockStates112 {
	
	CONCRETE(s("color"), v(g("white", "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver",
			"cyan", "purple", "blue", "brown", "green", "red", "black")), dv(va(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)),
			new Material[] {safeValueOf("CONCRETE"), safeValueOf("CONCRETE_POWDER")}),
	GLAZED_TERRACOTTA(s("facing", "color"), v(g("north", "north", "east", "south", "west"), g("white", "white", "orange", "magenta",
			"light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black")),
			dv(va(2, 3, 0, 1)), 1);
	
	protected List<String> supportedStates = new ArrayList<>();
	protected List<List<Object>> allowedValues = new ArrayList<>();
	protected List<List<Byte>> dataValues = new ArrayList<>();
	protected Material[] materials = null;
	
	BlockStates112(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues, Material[] materials) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
		this.materials = materials;
	}
	
	BlockStates112(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues, int i) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
		
		List<Material> b = new ArrayList<>();
		for (Object a : allowedValues.get(i)) {String aa = a.toString().toUpperCase(); b.add(Material.valueOf(aa+"_"+this.name().toString()));}
		this.materials = b.toArray(new Material[] {});
	}
	
	BlockStates112(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
	}
	
	public static boolean has(String e) {
		return _values().contains(e.toUpperCase());
	}
	
	public static List<String> _values() {
		List<String> a = new ArrayList<>();
		for (BlockStates112 b : values()) {
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
