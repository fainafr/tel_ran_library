package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * MultiMap utilites 
 * @param <K> map key, must be contained in E
 * @param <V> Collection iterable by E 
 * @param <E> element 
 */
public abstract class MultiMap<K, V extends Collection<E> & Iterable<E>, E> {
	
	/**
	 * Adding a new element into the multimap: a map where key corresponds to a collection.
	 * Template pattern employed to reduce code duplication.
	 * @param map Multimap
	 * @param element Element of the map
	 */
	public void putToIterableMap(Map<K, V> map, E element) {
		K key = getKey(element);
		V col = getMapCollection(map, key);
		if (col == null) {
			col = createMapCollection();
			map.put(key, col);
		}
		col.add(element);
	}
	
	public abstract V getMapCollection(Map<K, V> map, K key);

	public abstract V createMapCollection();
	
	public abstract K getKey(E element);


	/**
	 * Converts the map with iterable payload into ArrayList
	 * 
	 * @param map
	 *            any map where value is iterable
	 */
	public static final <K, V extends Iterable<E>, E> ArrayList<E> getList(Map<K, V> map) {
		ArrayList<E> lst = new ArrayList<E>();
		for (Entry<K, V> entry : map.entrySet()) {
			for (E element : entry.getValue())
				lst.add(element);
		}
		return lst;
	}
}
