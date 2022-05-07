package me.itstautvydas.debugstick.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")
public class MultiMap<V,K> implements Iterable<V> {
	
	private List<V> keys;
	private List<K> values;
	
	public MultiMap() {
		keys = new ArrayList<>();
		values = new ArrayList<>();
	}
	
	public List<V> getKeys() {
		return keys;
	}
	
	public int size() {
		return keys.size();
	}
	
	public List<K> getValues() {
		return values;
	}
	
	public V getKey(int index) {
		return keys.get(index);
	}
	
	public K getValue(int index) {
		return values.get(index);
	}
	
	public K get(Object key) {
		if (keys.contains(key))
			return values.get(keys.indexOf(key));
		return null;
	}
	
	public boolean isEmpty() {
		return keys.size() == 0;
	}
	
	public MultiMapIterator<V,K> iterator() {
		return new MultiMapIterator<V,K>(this);
	}
	
	public boolean remove(Object key) {
		if (keys.contains(key)) {
			keys.remove(keys.indexOf(key));
			values.remove(keys.indexOf(key));
			return true;
		}
		return false;
	}
	
	public void remove(int index) {
		keys.remove(index);
		values.remove(index);
	}
	
	public MultiMap<V, K> add(V key, K value) {
		keys.add(key);
		values.add(value);
		return this;
	}

	public void clear() {
		keys.clear();
		values.clear();
	}
}