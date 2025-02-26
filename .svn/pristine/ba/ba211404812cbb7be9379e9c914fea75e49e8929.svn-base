package com.tetrasoft.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

final public class SortInverted<K extends Comparable<K>, V extends Comparable<V>> {
	
	SortedSet<ComparatorInverted<V,K>> listIni;
	
	public SortInverted(Map<K,V> map) {
		this.put(map);
	}
	
	public void put(Map<K,V> map) {
		TreeSet<ComparatorInverted<V,K>> list = new TreeSet<ComparatorInverted<V,K>>();
		for (K key: map.keySet()) {
			list.add( new ComparatorInverted<V,K>(map.get(key),key) );
		}
		this.listIni = list;
		this.listReturn=null;
	}

	private class ComparatorInverted<C extends Comparable<C>, U extends Comparable<U>> implements Comparable<ComparatorInverted<C,U>> {
		
		private C comparable;
		private U unit;
		
		private ComparatorInverted(C comparable, U unit) {
			this.comparable=comparable;
			this.unit=unit;
		}
		
		private C getComparable() {
			return comparable;
		}

		private U getUnit() {
			return unit;
		}

		public int compareTo(ComparatorInverted<C,U> o) {
			if (this.equals(o))
				return 0;
			int ret = this.getComparable().compareTo(o.getComparable());
			if (ret==0)
				return this.getUnit().compareTo(o.getUnit());
			return ret;
		}
		
		@Override
		public boolean equals(Object o) {
			return this.getUnit().equals(o);
		}
		
		@Override
		public String toString() {
			return this.getUnit().toString();
		}
		
		@Override
		public int hashCode() {
			return this.getUnit().hashCode();
		}

	}

	private List<K> listReturn;
	
	public List<K> get() {
		if (this.listReturn==null) {
			List<K> list = new ArrayList<K>();
			for (ComparatorInverted<V,K> comp: this.listIni) {
				list.add(comp.getUnit());
			}
			this.listIni=null;
			this.listReturn=list;
		}
		return this.listReturn;
	}
	
}
