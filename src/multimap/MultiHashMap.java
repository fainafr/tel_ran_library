package multimap;

public class MultiHashMap<K, E> extends MultiMap<K, E> {

	public MultiHashMap(CollectionEnum collectionClass) {
		super(MapEnum.HASHMAP, collectionClass);
	}
}
