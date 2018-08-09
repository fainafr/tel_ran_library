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

	// AF: map (books) + map (sorters) -> library with sorting views;
	// RI: size of map (books) == size of each map in sorters;
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
		streamValues(sortedHM).forEach(s -> assertTrue(s.getSize() == isbnHM.size()));
	}

	public void selfTest() {
		checkRep();
	}

	
	private static enum multiMapAction {
		ADD, REMOVE;
	}
	/**
	 * Class that handles treemap for sorting;
	 */
	private class Sorter {

		private final BookSortWay sortWay;
		private final MultiMapFillerByBook filler;
		private final TreeMap<BookKey<?>, TreeSet<Book>> sortingMap;

		private Sorter(final BookSortWay sortWay) {
			this.sortWay = sortWay;
			filler = new MultiMapFillerByBook(sortWay);
			sortingMap = new TreeMap<BookKey<?>, TreeSet<Book>>();
		}

		private boolean putToIterableMap(Book book) {
			return updateMultiMap(book, multiMapAction.ADD);
		}

		private boolean removeFromIterableMap(Book book) {
			return updateMultiMap(book, multiMapAction.REMOVE);
		}

		/**
		 * updates the multimap state: adding / removing book 
		 * @param book
		 * @param action
		 * @return result
		 */
		private boolean updateMultiMap(Book book, multiMapAction action) {
			switch (sortWay) {
			case AUTHOR: {
				boolean res = false;
				for (Iterator<Author> i = book.getAuthors().iterator();i.hasNext();i.next()) {
					res = res || action == multiMapAction.ADD ? filler.putToIterableMap(sortingMap, book)
							: filler.removeFromIterableMap(sortingMap, book);
				}
				return res;
			}
			default:
				return action == multiMapAction.ADD ? filler.putToIterableMap(sortingMap, book)
						: filler.removeFromIterableMap(sortingMap, book);
			}
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
			System.out.println(" NEW SORTER ");
			sorter = new Sorter(sortWay);
			sortedHM.put(sortWay, sorter);
		}
		return sorter;
	}
	
	/**
	 * @return stream of map values, unsorted
	 */
	private <K, V> Stream<V> streamValues(Map<K, V> map) {
		return map.entrySet().stream().map(e -> e.getValue());
	}

	/**
	 * @return Iterable of map values, unsorted
	 */
	private <K, V> Iterable<V> mapToIterable(Map<K, V> map) {
		ArrayList<V> alb = new ArrayList<V>();
		streamValues(map).forEach(alb::add);
		return alb;
	}

	/**
	 * @param sorter
	 *            instance of Sorter class
	 * @return Iterable of Books, sorted
	 */
	private Iterable<Book> booksToSortedIterable(Sorter sorter) {
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
	public boolean addAll(Collection<Book> bCollection) {
		boolean res = true;
		for (Book book : bCollection)
			res = addBook(book) && res;
		return res;
	}

	@Override
	public boolean addLibary(Library lib) {
		boolean res = true;
		for (Book book : lib.getAllBooks())
			res = addBook(book) && res;
		return res;
	}

	@Override
	public void fillRandomLibrary(int numBooks) {
		emptyLibrary();
		int counter = 0;
		while (counter < numBooks) {
			if (addBook(Book.getRandomBook()))
				counter++;
		}
	}

	@Override
	public void fillWithIterable(Iterable<Book> iterable) {
		emptyLibrary();
		for (Book book : iterable)
			addBook(book);
	}

	@Override
	public boolean contains(Book book) {
		return isbnHM.containsKey(book.getISBN());
	}

	@Override
	public boolean containsAll(Collection<Book> bCollection) {
		for (Book book : bCollection) {
			if (!contains(book))
				return false;
		}
		return true;
	}

	@Override
	/**/public Iterable<Book> containsAtLeastOne(Collection<Book> bCollection) {
		TreeSet<Book> tsb = new TreeSet<>();
		for (Book book : bCollection) {
			if (contains(book))
				tsb.add(book);
		}
		return tsb;
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
		TreeSet<Book> res = new TreeSet<>();
		for (Book book : isbnHM.values()) {
			if (!bCollection.contains(book)) {
				remove(book);
				res.add(book);
			}
		}
		return res;
	}

	@Override
	public Iterable<Book> getAllBooksSortedWithComparator(Comparator<Book> comparator) {
		TreeSet<Book> tsb = new TreeSet<>(comparator);
		for (Book book : isbnHM.values())
			tsb.add(book);
		return tsb;
	}

	@Override
	public Iterable<Book> getAllBooksFilteredWithPredicate(Predicate<Book> predicate) {
		TreeSet<Book> tsb = new TreeSet<>();
		for (Book book : isbnHM.values()) {
			if (predicate.test(book))
				tsb.add(book);
		}
		return tsb;
	}

	private boolean badISBN(long isbn) {
		return !isbnHM.containsKey(isbn);
	}

	@Override
	public boolean correctBookISBN(long isbn, long newISBN) {

		if (badISBN(isbn) || !badISBN(newISBN))
			return false;
		Book book = isbnHM.get(isbn);
		remove(book);
		book.setISBN(newISBN);
		addBook(book);
		return true;
	}

	@Override
	public boolean correctBookAuthors(long isbn, Set<Author> newAuthors) {
		// TODO Auto-generated method stub
		// Sorter sorter = getSorter(BookSortWay.AUTHOR);
		// for(Author a : newAuthors){
		// TreeSet<Book> tsa = sorter.getIterable().
		// if (tsa != null) tsb.addAll(tsa);
		// else return EMPTY_TREE_SET;
		// }
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
		return mapToIterable(isbnHM);
	}

	// sorts by 1st author;
	// replace new Sorter with static sorter factory;
	// consider memento pattern or something so sorters wouldn't resort after each
	// entry;
	@Override
	public Iterable<Book> getAllBooksSortedByAuthors() {
		return booksToSortedIterable(getSorter(BookSortWay.AUTHOR));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByTitle() {
		return booksToSortedIterable(getSorter(BookSortWay.TITLE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherNames() {
		return booksToSortedIterable(getSorter(BookSortWay.PUBLISHER));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherCountries() {
		return booksToSortedIterable(getSorter(BookSortWay.PUBCOUNTRY));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByEditionDate() {
		return booksToSortedIterable(getSorter(BookSortWay.EDITIONDATE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPrice() {
		return booksToSortedIterable(getSorter(BookSortWay.PRICE));
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
		return isbnHM.size();
	}

	@Override
	public boolean isEmpty() {
		return isbnHM.isEmpty();
	}

	@Override
	public void clear() {
		emptyLibrary();
	}

}
