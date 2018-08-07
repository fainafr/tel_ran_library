package model;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
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
import java.util.stream.Stream;

import dao.Author;
import dao.Book;
import dao.Countries;
import dao.Publisher;
import util.BookKey;
import util.MultiMapFillerByBook;
import util.MultiMap;
import util.BookSortWay;

public class Library implements ILibrary, Iterable<Entry<Long, Book>> {
	
	//AF: map (books) + map (sorters) -> library with sorting views;
	//RI: size of map (books) == size of each map in sorters; 
	private HashMap<Long, Book> isbnHM;
	private HashMap<BookSortWay, Sorter> sortedHM; // storage class
	
	public Library() {
		emptyLibrary();
	}

	@Override
	public Iterator<Entry<Long, Book>> iterator() {
		return isbnHM.entrySet().iterator();
	}

	private void emptyLibrary() {
		isbnHM = new HashMap<Long, Book>();
		sortedHM = new HashMap<BookSortWay, Sorter>();
	}
	
	private void checkRep() {
		streamValues(sortedHM)
		.forEach(s -> assertTrue(s.getSize() == isbnHM.size()));
	}
	
	public void selfTest() {
		checkRep();
	}

	/**
	 * Class that handles treemap for sorting;
	 */
	
	private class Sorter {

		private final BookSortWay sortWay;
		private final MultiMapFillerByBook filler; 
		private final TreeMap<BookKey<?>, TreeSet<Book>> sortingMap;
		
		public BookSortWay getSortWay() {
			return sortWay;
		}
		
		private Sorter(final BookSortWay sortWay) {
			this.sortWay = sortWay;
			filler = new MultiMapFillerByBook(sortWay);
			sortingMap = new TreeMap<BookKey<?>, TreeSet<Book>>();
		}

		private boolean putToIterableMap(Book book) {
			return filler.putToIterableMap(sortingMap, book);
		}
		
		private boolean removeFromIterableMap(Book book) {
			return filler.removeFromIterableMap(sortingMap, book);
		}

		public Iterable<Book> getIterable() {
			return MultiMap.getList(sortingMap);
		}
		
		public long getSize() {
			return MultiMap.getMultiMapSize(sortingMap);
		}
	}
	
	private Sorter getSorter(final BookSortWay sortWay) {
		Sorter sorter = sortedHM.get(sortWay);
		if (sorter == null) {
			sorter = new Sorter(sortWay);
			sortedHM.put(sortWay, sorter);
		}
		return sorter;
	}
	
	/**
	 * @return stream of map Entries: ISBN and Book, unsorted
	 */
	private  <K, V> Stream<Map.Entry<K, V>> streamEntries(Map<K, V> map){
		return map.entrySet().stream();
	}
	
	/**
	 * @return stream of map values, unsorted
	 */
	private <K, V> Stream<V> streamValues(Map<K, V> map){
		return map.entrySet().stream().map(e -> e.getValue());
	}
	
	/**
	 * @return Iterable of map values, unsorted
	 */
	private  <K, V> Iterable<V> toIterable(Map<K, V> map) {
		ArrayList<V> alb = new ArrayList<V>();
	    streamValues(map).forEach(alb::add);
		return alb;
	}
	
	/**
	 * @param sorter instance of Sorter class
	 * @return Iterable of Books, sorted
	 */
	private Iterable<Book> toIterableSorted(Sorter sorter) {
		streamValues(isbnHM).forEach(sorter::putToIterableMap);
		return sorter.getIterable();
	}
	
	@Override
	public boolean addBook(Book book) {
		if (book == null)
			return false;
		if (isbnHM.putIfAbsent(book.getISBN(), book) != null)
			return false;
		streamValues(sortedHM).forEach(s -> s.putToIterableMap(book));
		return true;
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
		if (book == null)
			return false;
		if (!isbnHM.containsKey(book.getISBN()))
			return false;
		isbnHM.remove(book.getISBN());
		streamValues(sortedHM).forEach(s -> s.removeFromIterableMap(book));
		return true;
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

	// all this depends on isbn stream;
	@Override
	public Book getBookByISBN(long isbn) {
		return isbnHM.get(isbn);
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
	public Iterable<Book> getAllBooks() {
		return toIterable(isbnHM);
	}

	// sorts by 1st author;
	// replace new Sorter with static sorter factory; 
	// consider memento pattern or something so sorters wouldn't resort after each entry;
	@Override
	public Iterable<Book> getAllBooksSortedByAuthors(){
		return toIterableSorted(getSorter(BookSortWay.AUTHOR));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByTitle() {
		return toIterableSorted(getSorter(BookSortWay.TITLE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherNames() {
		return toIterableSorted(getSorter(BookSortWay.PUBLISHER));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherCountries() {
		return toIterableSorted(getSorter(BookSortWay.PUBCOUNTRY));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByEditionDate() {
		return toIterableSorted(getSorter(BookSortWay.EDITIONDATE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPrice() {
		return toIterableSorted(getSorter(BookSortWay.PRICE));
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
