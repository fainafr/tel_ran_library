package util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Book;
import util.MultiMap;

/**
 * Concrete class for putting things into multimap.
 */
public class MultiMapFillerBook extends MultiMap<BookKey<?>, TreeSet<Book>, Book> {

	private static final Comparator<Book> generalComparator = BookGeneralComparator.getInstance();

	private BookSortWay sortBy;

	public MultiMapFillerBook(BookSortWay sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public TreeSet<Book> getMapCollection(Map<BookKey<?>, TreeSet<Book>> map,  BookKey<?> key) {
		return map.get(key);
	}

	@Override
	public TreeSet<Book> createMapCollection() {
		return new TreeSet<Book>(generalComparator);
	}

	@Override
	public BookKey<?> getKey(Book element) {
		return sortBy.getKey(element);
	}
}
