package multimap;

import java.util.Collection;
import java.util.Map;

/**
 * A subset of MultiMap where K is contained in E
 * 
 * @param <K>
 *            map key, must be contained in E
 * @param <V>
 *            Collection iterable by E
 * @param <E>
 *            element
 */
public abstract class MultiMapKey<K, V extends Collection<E> & Iterable<E>, E> extends MultiMap<K, V, E> {
	/**
	 * Adding a new element into the multimap: a map where key corresponds to a
	 * collection.
	 * 
	 * @param map<K,V>
	 *            Multimap, K must be contained in E
	 * @param element
	 *            E Element of the map
	 */
	public boolean putToIterableMap(Map<K, V> map, E element) {
		K key = getKey(element);
		return putToIterableMap(map, element, key);
	}

	/**
	 * Removing an element from the multimap: a map where key corresponds to a
	 * collection.
	 * 
	 * @param map<K,V>
	 *            Multimap, K must be contained in E
	 * @param element
	 *            E Element of the map
	 */

	public boolean removeFromIterableMap(Map<K, V> map, E element) {
		K key = getKey(element);
		return removeFromIterableMap(map, element, key);
	}

	public abstract V createMapCollection();

	public abstract K getKey(E element);
}
