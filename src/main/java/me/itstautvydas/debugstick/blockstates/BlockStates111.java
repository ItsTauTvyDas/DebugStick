package me.itstautvydas.debugstick.blockstates;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum BlockStates111 {
	SHULKER_BOX(s("facing"), v(g("up", "up", "down", "north", "east", "south", "west"),
			g("white", "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue",
					"brown", "green", "red", "black")), dv(va(1, 0, 2, 5, 3, 4)), 1),
	OBSERVER(s("facing", "powered"), v(g("up", "up", "down", "north", "east", "south", "west"), g(false, false, true)),
			dv(va(1, 0, 2, 5, 3, 4), va(0, 8)));
	
	protected List<String> supportedStates = new ArrayList<>();
	protected List<List<Object>> allowedValues = new ArrayList<>();
	protected List<List<Byte>> dataValues = new ArrayList<>();
	protected Material[] materials = null;
	
	BlockStates111(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues, Material[] materials) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
		this.materials = materials;
	}
	
	BlockStates111(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues, int i) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
		
		List<Material> b = new ArrayList<>();
		for (Object a : allowedValues.get(i)) {String aa = a.toString().toUpperCase(); b.add(Material.valueOf(aa+"_"+this.name().toString()));}
		this.materials = b.toArray(new Material[] {});
	}
	
	BlockStates111(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
	}
	
	public static boolean has(String e) {
		return _values().contains(e.toUpperCase());
	}
	
	public static List<String> _values() {
		List<String> a = new ArrayList<>();
		for (BlockStates111 b : values()) {
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
