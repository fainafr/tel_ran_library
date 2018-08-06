package model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import comparators.BookGeneralComparator;
import dao.Author;
import dao.Book;
import dao.Countries;
import dao.Publisher;
import util.BookKey;
import util.MapUtilBookKey;
import util.BookSortWays;

public class Library implements ILibrary, Iterable<Entry<Long, Book>> {

	private static final Comparator<Book> generalComparator = BookGeneralComparator.getInstance();
	private static final TreeSet<Book> EMPTY_TREE_SET = new TreeSet<Book>();

	private HashMap<Long, Book> isbnHM;

	public Library() {
		emptyLibrary();
	}

	@Override
	public Iterator<Entry<Long, Book>> iterator() {
		return isbnHM.entrySet().iterator();
	}

	private void emptyLibrary() {
		isbnHM = new HashMap<Long, Book>();
	}

	/**
	 * Class that handles treemap for sorting;
	 * 
	 */
	//plans: store sorting maps!
	//plans: convert mapUtil to static
	private class Sorter {
		
		private MapUtilBookKey mapUtil;
		private TreeMap<BookKey<?>, TreeSet<Book>> sortingMap;

		private Sorter(final BookSortWays sortBy) {
			mapUtil = new MapUtilBookKey(sortBy);
			sortingMap = new TreeMap<BookKey<?>, TreeSet<Book>>();
		}

		private void putToIterableMap(Book book) {
			mapUtil.putToIterableMap(sortingMap, book);
		}

		public Iterable<Book> getIterable() {
			return MapUtilBookKey.getList(sortingMap);
		}
	}

	@Override
	public boolean addBook(Book book) {
		if (book == null)
			return false;
		if (isbnHM.putIfAbsent(book.getISBN(), book) != null)
			return false;
		return true;
	}

	private static <K> void putToMultivalueMap(Map<K, TreeSet<Book>> map, K key, Book book) {
		TreeSet<Book> tsb = map.get(key);
		if (tsb == null) {
			tsb = new TreeSet<>(generalComparator);
			map.put(key, tsb);
		}
		tsb.add(book);
	}

	@Override
	/**/public boolean addAll(Collection<Book> bCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/**/public boolean addLibary(Library lib) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fillRandomLibrary(int numBooks) {
		int counter = 0;
		while (counter < numBooks) {
			if (addBook(Book.getRandomBook()))
				counter++;
		}
	}

	@Override
	/**/public void fillWithIterable(Iterable<Book> iterable) {
		// TODO Auto-generated method stub

	}

	@Override
	/**/public boolean contains(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/**/public boolean containsAll(Collection<Book> bCollection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/**/public Iterable<Book> containsAtLeastOne(Collection<Book> bCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Book> removeAll(Collection<Book> bCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> retainAll(Collection<Book> bCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getAllBooksSortedWithComparator(Comparator<Book> comparator) {
		TreeSet<Book> tsb = new TreeSet<>(comparator);
		for (Book book : isbnHM.values())
			tsb.add(book);
		return tsb;
	}

	@Override
	/**/public Iterable<Book> getAllBooksFilteredWithPredicate(Predicate<Book> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean correctBookISBN(long isbn, long newISBN) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean correctBookAuthors(long isbn, Set<Author> newAuthors) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean correctBookTitle(long isbn, String newTitle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean correctBookPublisher(long isbn, Publisher newPublisher) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean correctBookEditionDate(long isbn, LocalDate newEditionDate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean correctBookPrice(long isbn, double newPrice) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean correctBookWithPattern(long isbn, Book pattern) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/**/public Book getBookByISBN(long isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getBooksByAuthor(Author author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getBooksByAllAuthors(Collection<Author> aCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getBooksByAtLeastOneAuthor(Collection<Author> aCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getBooksByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getBooksByPublisher(Publisher publicher) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getBooksByPublisherName(String pName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getBooksByPublisherCountry(Countries pCountry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**/public Iterable<Book> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Iterable<Book> toIterable(Sorter sorter) {
		StreamSupport.stream(this.spliterator(), false).forEach(e -> sorter.putToIterableMap(e.getValue()));
		return sorter.getIterable();
	}

	// sorts by 1st author;
	@Override
	public Iterable<Book> getAllBooksSortedByAuthors(){
		return toIterable(new Sorter(BookSortWays.AUTHOR));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByTitle() {
		return toIterable(new Sorter(BookSortWays.TITLE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherNames() {
		return toIterable(new Sorter(BookSortWays.PUBLISHER));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherCountries() {
		return toIterable(new Sorter(BookSortWays.PUBCOUNTRY));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByEditionDate() {
		return toIterable(new Sorter(BookSortWays.EDITIONDATE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPrice() {
		return toIterable(new Sorter(BookSortWays.PRICE));
	}

	@Override
	public Iterable<Book> getBooksPrintedBefore(LocalDate max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksPrintedBefore(int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksPrintedAfter(LocalDate min) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksPrintedAfter(int year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksPrintedInRange(LocalDate min, LocalDate max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksPrintedInRange(int yearMin, int yearMax) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksCheaperThan(double maxPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksMoreExpensiveThan(double minPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> getBooksPricedInRange(double minPrice, double maxPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

}
