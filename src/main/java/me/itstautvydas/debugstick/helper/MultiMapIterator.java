package me.itstautvydas.debugstick.helper;

import java.util.Iterator;

public class MultiMapIterator<V,K> implements Iterator<V> {
	
	private MultiMap<V,K> map;
	
	protected MultiMapIterator(MultiMap<V,K> map) {
		this.map = map;
	}

	private int index = 0;

	@Override
	public boolean hasNext() {
		return index < map.size();
	}

	@Override @Deprecated
	public V next() {
		return map.getKey(index++);
	}
	
	public V nextKey() {
		return map.getKey(index++);
	}
	
	public K nextValue() {
		return map.getValue(index++);
	}
}
