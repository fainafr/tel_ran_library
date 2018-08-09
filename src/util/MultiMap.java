package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * MultiMap utilites
 * 
 * @param <K>
 *            map key, must be contained in E
 * @param <V>
 *            Collection iterable by E
 * @param <E>
 *            element
 */
public abstract class MultiMap<K, V extends Collection<E> & Iterable<E>, E> {

	/**
	 * Adding a new element into the multimap: a map where key corresponds to a
	 * collection. Template pattern employed to reduce code duplication.
	 * 
	 * @param map
	 *            Multimap
	 * @param element
	 *            Element of the map
	 */
	public boolean putToIterableMap(Map<K, V> map, E element) {
		K key = getKey(element);
		if (key == null)
			return false;
		V col = getMapCollection(map, key);
		if (col == null) {
			col = createMapCollection();
			map.put(key, col);
		}
		return col.add(element);
	}

	public abstract V getMapCollection(Map<K, V> map, K key);

	public abstract V createMapCollection();

	public abstract K getKey(E element);

	/**
	 * Removing an element from the multimap: a map where key corresponds to a
	 * collection. Template pattern employed to reduce code duplication.
	 * 
	 * @param map
	 *            Multimap
	 * @param element
	 *            Element of the map
	 */
	public boolean removeFromIterableMap(Map<K, V> map, E element) {
		K key = getKey(element);
		V col = getMapCollection(map, key);
		if (col == null)
			return false;
		// System.out.println("KEY "+key+" COL "+col);
		// System.out.println("TO REMOVE ONE "+element);
		// boolean res = col.remove(element);
		// System.out.println(col);
		// System.out.println();
		// return res;
		// return col.remove(element);
		// res = true;
		boolean res = true;
		do { // had to remove same element several times to get rid of it; why?
			// System.out.println("KEY "+key+" COL "+col);
			// System.out.println("TO REMOVE LOOP "+element);
			res = res && (col.remove(element));
			// System.out.println(col);
			// System.out.println();

		} while (res);
		return res;
	}

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

	/**
	 * @param map
	 *            multimap
	 * @return integer count of multimap cells
	 */
	public static final <K, V extends Collection<E>, E> long getMultiMapSize(Map<K, V> map) {
		long res = 0;
		for (Entry<K, V> entry : map.entrySet()) {
			res += entry.getValue().size();
		}
		return res;
	}

	/**
	 * prints a multimap into console
	 * 
	 * @param map
	 *            multimap
	 */
	public static final <K, V extends Iterable<E>, E> void display(Map<K, V> map) {
		System.out.println();
		System.out.println("DISPLAYING MAP");
		for (Entry<K, V> entry : map.entrySet()) {
			long size = entry.getValue().spliterator().getExactSizeIfKnown();
			System.out.println("KEY= " + entry.getKey() + " SIZE= " + size + " " + entry.toString());
		}
	}
}
