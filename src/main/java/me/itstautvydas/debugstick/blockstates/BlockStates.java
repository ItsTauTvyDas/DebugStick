package me.itstautvydas.debugstick.blockstates;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

import me.itstautvydas.debugstick.util.SupportedVersions;

public class BlockStates {
	
	private List<String> supportedStates = new ArrayList<>();
	private List<List<Object>> allowedValues = new ArrayList<>();
	private List<List<Byte>> dataValues = new ArrayList<>();
	private Material[] materials = null;

	private String block;
	private static int v = SupportedVersions.getInteger(null);
	
	private static HashMap<String, List<Integer>> disabledBlocks = new HashMap<>();
	
	static {
		disabledBlocks.put("STRUCTURE_BLOCK", Arrays.asList(10, 11, 12));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setup(String b, int v) {
		for (int i : SupportedVersions.getVersionsInt()) {
			if (v == i) {
				try {
					Class c = Class.forName("me.itstautvydas.debugstick.blockstates.BlockStates1"+i);
					Method m = c.getMethod("has", String.class);
					if ((boolean)m.invoke(null, b)) {
						Method mm = c.getMethod("valueOf", String.class);
						this.supportedStates = (List<String>)c.getDeclaredField("supportedStates").get(mm.invoke(null, b));
						this.allowedValues = (List<List<Object>>)c.getDeclaredField("allowedValues").get(mm.invoke(null, b));
						this.dataValues = (List<List<Byte>>)c.getDeclaredField("dataValues").get(mm.invoke(null, b));
						this.materials = (Material[])c.getDeclaredField("materials").get(mm.invoke(null, b));
					} else {
						if (i != 8)
							setup(b, i-1);
					}
				} catch (Exception e) {}
			}
		}
	}
	
	public BlockStates(String block, int version) {
		this.block = block.toUpperCase();
		this.setup(this.block, version);
	}

	public static BlockStates get(String block, int version) {
		return new BlockStates(block, version);
	}
	
	private static boolean isBlocked(BlockStates a) {
		return disabledBlocks.containsKey(a.toString()) && disabledBlocks.get(a.toString()).contains(v);
	}
	
	public static BlockStates fromMaterial(Material material) {
		for (BlockStates a : BlockStates.values()) {
			a.a(material);
			if (isBlocked(a))
				continue;
			
			if (a.isGroup()) {
				if (Arrays.asList(a.getMaterials()).contains(material))
					return a;
			} else {
				if (a.getMaterial().equals(material))
					return a;
			}
		}
		return null;
	}
	
	public static BlockStates valueOf(String block) {
		return new BlockStates(block, v);
	}
	
	public static Set<Material> getModifiableMaterials() {
		Set<Material> mats = new HashSet<>();
		for (BlockStates bs : values()) {
			if (bs.isGroup()) {
				for (Material mat : bs.getMaterials()) {
					mats.add(mat);
				}
			} else {
				mats.add(bs.getMaterial());
			}
		}
		return mats;
	}
	
	public static Set<BlockStates> values() {
		Set<BlockStates> a = new HashSet<>();
		for (int i : SupportedVersions.getVersionsInt()) {
				if (v > (i-1))
					try {
						Class<?> c = Class.forName("me.itstautvydas.debugstick.blockstates.BlockStates1"+i);
						for (Object aa: c.getEnumConstants()) {
							a.add(BlockStates.valueOf(aa.toString()));
						}
					} catch (ClassNotFoundException e) {}
		}
		
		return a;
	}
	
	public String toString() {
		return block;
	}
	
	public boolean hasState(String state) {
		for (String a : getStates()) {
			if (state.equalsIgnoreCase(a))
				return true;
		}
		return false;
	}
	
	public List<Byte> getDataValues(String state) {
		int i = 0;
		for (String _state : supportedStates) {
			if (state.equalsIgnoreCase(_state))
				return dataValues.get(i);
			i++;
		}
		return null;
	}
	
	public byte valueToByte(String state, Object value, int plus) {
		List<Object> o = getAllowedValues(state);
		int i = 0;
		for (Object b : o) {
			if (b.equals(value)) {
				return (byte)((int)getDataValues(state).get(i)+plus);
			}
			i++;
		}
		return -1;
	}
	
	public Object byteToValue(String state, byte byt, int plus) {
		List<Byte> o = getDataValues(state);
		int i = 0;
		for (byte b : o) {
			if (b == (byte)(byt+plus)) {
				return (Object)getAllowedValues(state).get(i);
			}
			i++;
		}
		return null;
	}
	
	public String getDefaultState() {
		return getStates().get(0);
	}
	
	public List<String> getStates() {
		return supportedStates;
	}
	
	public boolean isValueAllowed(String state, Object value) {
		return getAllowedValues(state).contains(value);
	}
	
	public List<Object> getAllowedValues(String state) {
		int i = 0;
		for (String _state : supportedStates) {
			if (state.equalsIgnoreCase(_state))
				return hideDefault(allowedValues.get(i), 0);
			i++;
		}
		return null;
	}
	
	public Object getDefaultValue(String state) {
		if (hasState(state))
			return getAllowedValues(state).get(0);
		else
			return null;
	}
	
	public boolean isGroup() {
		return materials != null;
	}
	
	public Material[] getMaterials() {
		return materials == null ? new Material[] {Material.valueOf(this.block)} : materials;
	}
	
	public Material getMaterial() {
		return materials == null ? Material.valueOf(this.block) : null;
	}
	
	public boolean equals(Material mat) {
		if (!isGroup()) {
			String a = mat.toString().toUpperCase();
			String b = this.toString().toUpperCase();
			
			return a.equalsIgnoreCase(b);
		} else {
			return Arrays.asList(materials).contains(mat);
		}
	}
	
	public BlockStates18 toEnum18() {
		return BlockStates18.valueOf(block);
	}
	
	public BlockStates19 toEnum19() {
		return BlockStates19.valueOf(block);
	}
	
	public BlockStates110 toEnum110() {
		return BlockStates110.valueOf(block);
	}
	
	public BlockStates111 toEnum111() {
		return BlockStates111.valueOf(block);
	}
	
	public BlockStates112 toEnum112() {
		return BlockStates112.valueOf(block);
	}
	
	private List<Object> hideDefault(List<Object> obs, int pos) {
		List<Object> x = new ArrayList<>();
		obs.forEach(a -> {x.add(a);});
		x.remove(pos);
		return x;
	}
	
	
	private Material original;
	
	public void a(Material arg0) {
		original = arg0;
	}
	
	public Material b() {
		return original;
	}
}
