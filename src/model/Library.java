package model;

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
import java.util.stream.StreamSupport;

import dao.Author;
import dao.Book;
import dao.Countries;
import dao.Publisher;
import util.BookKey;
import util.MultiMapFillerByBook;
import util.MultiMap;
import util.BookSortWay;

public class Library implements ILibrary, Iterable<Entry<Long, Book>> {

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
	 */
	
	private class Sorter {

		private final MultiMapFillerByBook filler; 
		private final TreeMap<BookKey<?>, TreeSet<Book>> sortingMap;

		private Sorter(final BookSortWay sortWay) {
			filler = new MultiMapFillerByBook(sortWay);
			sortingMap = new TreeMap<BookKey<?>, TreeSet<Book>>();
		}

		private void putToIterableMap(Book book) {
			filler.putToIterableMap(sortingMap, book);
		}

		public Iterable<Book> getIterable() {
			return MultiMap.getList(sortingMap);
		}
	}
	
	/**
	 * @return stream of Entries: ISBN and Book, unsorted
	 */
	private Stream<Map.Entry<Long, Book>> toIsbnMapStream(){
		return StreamSupport.stream(this.spliterator(), false);
	}
	
	
	/**
	 * @return stream of Books, unsorted
	 */
	private Stream<Book> toBookStream(){
		return StreamSupport.stream(this.spliterator(), false).map(e -> e.getValue());
	}
	
	/**
	 * @return Iterable of Books, unsorted
	 */
	private Iterable<Book> toIterable() {
		ArrayList<Book> alb = new ArrayList<Book>();
		this.toBookStream().forEach(alb::add);
		return alb;
	}
	
	/**
	 * @param sorter instance of Sorter class
	 * @return Iterable of Books, sorted
	 */
	private Iterable<Book> toIterableSorted(Sorter sorter) {
		this.toBookStream().forEach(sorter::putToIterableMap);
		return sorter.getIterable();
	}
	
	

	@Override
	public boolean addBook(Book book) {
		if (book == null)
			return false;
		if (isbnHM.putIfAbsent(book.getISBN(), book) != null)
			return false;
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

	// sorts by 1st author;
	@Override
	public Iterable<Book> getAllBooksSortedByAuthors(){
		return toIterableSorted(new Sorter(BookSortWay.AUTHOR));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByTitle() {
		return toIterableSorted(new Sorter(BookSortWay.TITLE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherNames() {
		return toIterableSorted(new Sorter(BookSortWay.PUBLISHER));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPublisherCountries() {
		return toIterableSorted(new Sorter(BookSortWay.PUBCOUNTRY));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByEditionDate() {
		return toIterableSorted(new Sorter(BookSortWay.EDITIONDATE));
	}

	@Override
	public Iterable<Book> getAllBooksSortedByPrice() {
		return toIterableSorted(new Sorter(BookSortWay.PRICE));
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
