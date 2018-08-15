package multimap;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;


public class MultiMap<K,E>{
	
	private MapEnum mapClass;
	private CollectionEnum collectionClass;

	protected Map<K, Collection<E>> map;
	
	public MultiMap(MapEnum mapClass, CollectionEnum collectionClass){
		this.mapClass =mapClass;
		this.collectionClass = collectionClass;
		try {map = (Map<K, Collection<E>>) mapClass.getValue().newInstance();} catch (Exception e) {}
	}
	
	// main things
	
	public boolean put(K key, E element) {
		if (key == null) return false;
		Collection<E> col = (Collection<E>) map.get(key);
		if (col == null) {
			try {col = (Collection<E>) collectionClass.getValue().newInstance();} catch (Exception e) {}
			map.put(key, col);
		}
		return col.add(element);
	}
	
	public boolean remove( K key, E element) {
		
		if (key == null) return false;
		Collection<E> collection = (Collection<E>) map.get(key);
		if (collection == null)return false;
		if (collection.size() == 0) return false;
		
		if (collection.size() == 1) {
			map.remove(key);
			return true;
		}
		
		boolean res = false;
		for(; collection.remove(element); res=true);
		return res;
	}
	
	public Collection<E> get(K key) {
		return map.get(key);
	}
	
	// calculated keys
	
	public boolean put(Function<E,K> keyGetter, E element) {
		return put(keyGetter.apply(element), element);
	}
	
	public boolean remove(Function<E,K> keyGetter, E element) {
		return remove(keyGetter.apply(element), element);	
	}
	
	// utils
	
	public boolean putIterable(Function<E,Iterable<K>> keyGetter, E element) {
		boolean res = true;
		for(K key : keyGetter.apply(element)) res = put(key, element) && res;
		return true;
	}
		
	public Iterable<E> multiMapToIterable(){
		LinkedHashSet<E> lhse = new LinkedHashSet<>();
		if (mapClass != MapEnum.TREEMAP) {
			TreeSet<K> tsk = new TreeSet<>(map.keySet());
			for (K key : tsk)lhse.addAll(map.get(key));
		}
		else {
			for (Collection<E> c : map.values())lhse.addAll(c);
		}
		return lhse;
	}
	
	public void clear() {if(map != null)map.clear();}
	
	// getters
	
	public Map<K, Collection<E>> map(){return map;}
	public MapEnum getMapClass() {return mapClass;}
	public CollectionEnum getCollectionClass() {return collectionClass;}
}
