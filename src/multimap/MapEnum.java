package multimap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public enum MapEnum {
	
	HASHMAP(HashMap.class), 
	LINKED_HASHMAP(LinkedHashMap.class), 
	TREEMAP(java.util.TreeMap.class);
	
	Class<?> cl;
	
	MapEnum(Class<?> cl) {
		this.cl = cl;
		TreeMap nn;
	}
	
	public Class<?> getValue() {return cl;}

}
