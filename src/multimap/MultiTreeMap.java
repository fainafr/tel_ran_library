package multimap;

public class MultiTreeMap<K, E> extends MultiMap<K, E> {

	public MultiTreeMap(CollectionEnum collectionClass) {
		super(MapEnum.TREEMAP, collectionClass);
	}
}
