package com.tetrasoft.app.util;

import java.util.Comparator;
import java.util.Map;

public class ValueComparer implements Comparator {
	private Map _data = null;

	public ValueComparer( Map data ) {
		super();
		_data = data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compare( Object key2, Object key1 ) {
		Comparable value1 = (Comparable)_data.get( key1 );
		Comparable value2 = (Comparable)_data.get( key2 );
		int c = value1.compareTo( value2 );
		if ( 0 != c ) return c;
		Integer h1 = key1.hashCode(), h2 = key2.hashCode();
		return h2.compareTo( h1 );
	}
}
