package mapofmaps;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import dao.Book;
import multimap.MultiMap;

public class DaoField<E> {
	
	MultiMap map;
	
	Function<E, ?> getter;
	BiConsumer<E, Object> setter;
	
	
	public DaoField(Function<E, ?> getter, BiConsumer<E, Object> setter) {
		super();
		this.getter = getter;
		this.setter = setter;
	}
	
	public DaoField(Function<E, ?> getter, BiConsumer<E, Object> setter, boolean sorted) {
		super();
		this.getter = getter;
		this.setter = setter;
	}
	
	public Function<E, ?> getGetter(){return getter;}
	public BiConsumer<E, Object> getSetter(){return setter;}
	public MultiMap getMap() {return map;}
	public void setMap(MultiMap map) {this.map = map;}
	
}
	
	
	
	
	
	
	


