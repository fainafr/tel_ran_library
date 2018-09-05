package multimap;

import java.util.Comparator;
import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Book;
import model.BookKey;
import model.BookSortWay;
import multimap.MultiMap;

/**
 * Concrete class for putting things into multimap.
 */
public class MultiMapFillerByBook extends MultiMapKey<BookKey<?>, TreeSet<Book>, Book> {

	private static final Comparator<Book> generalComparator = BookGeneralComparator.getInstance();

	private BookSortWay sortWay;

	public MultiMapFillerByBook(BookSortWay sortBy) {
		this.sortWay = sortBy;
	}

	@Override
	public TreeSet<Book> createMapCollection() {
		return new TreeSet<Book>(generalComparator);
	}

	@Override
	public BookKey<?> getKey(Book element) {
		return sortWay.getKey(element);
	}
}
