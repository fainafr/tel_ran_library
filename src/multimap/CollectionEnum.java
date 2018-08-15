package multimap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeSet;

public enum CollectionEnum {
	
	ARRAYLIST(ArrayList.class),
	LINKEDLIST(LinkedList.class),
	HASHSET(HashSet.class),
	LINKEDHASHSET(LinkedHashSet.class),
	TREESET(TreeSet.class);
	
	Class<?> cl;
	
	CollectionEnum(Class<?> cl) {
		this.cl = cl;
		TreeSet aa;
	}
	
	public Class<?> getValue() {return cl;}

}
