package me.itstautvydas.debugstick.blockstates;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public enum BlockStates110 {
	
	BONE_BLOCK(s("axis"), v(g("y", "y", "x", "z")), dv(va(0, 4, 8)));
	
	protected List<String> supportedStates = new ArrayList<>();
	protected List<List<Object>> allowedValues = new ArrayList<>();
	protected List<List<Byte>> dataValues = new ArrayList<>();
	protected Material[] materials = null;
	
	BlockStates110(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues, Material[] materials) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
		this.materials = materials;
	}
	
	BlockStates110(List<String> supportedStates, List<List<Object>> allowedValues, List<List<Byte>> dataValues) {
		this.supportedStates = supportedStates;
		this.allowedValues = allowedValues;
		this.dataValues = dataValues;
	}
	
	public static boolean has(String e) {
		return _values().contains(e.toUpperCase());
	}
	
	public static List<String> _values() {
		List<String> a = new ArrayList<>();
		for (BlockStates110 b : values()) {
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
