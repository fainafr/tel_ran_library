package model;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import dao.Author;
import dao.Book;
import mapofmaps.DaoField;
import multimap.MultiMap;

public class LibraryBasic implements ILibraryBasic{
	
	protected HashMap<BookFieldNames, DaoField<Book>> fieldsMap = BookFieldsMap.fieldsMap;
	protected HashMap<Long, Book> isbnHM = new HashMap<>();
	protected MultiMap authorTM = BookFieldsMap.fieldsMap.get(BookFieldNames.AUTHOR).getMap();
	protected MultiMap titleTM = BookFieldsMap.fieldsMap.get(BookFieldNames.TITLE).getMap();
	protected MultiMap publisherHM = BookFieldsMap.fieldsMap.get(BookFieldNames.PUBLISHER).getMap();
	protected MultiMap publisherNameTM = BookFieldsMap.fieldsMap.get(BookFieldNames.PUBLISHER_NAME).getMap();
	protected MultiMap publisherCountryHM = BookFieldsMap.fieldsMap.get(BookFieldNames.PUBLISHER_COUNTRY).getMap();
	protected MultiMap editionTM = BookFieldsMap.fieldsMap.get(BookFieldNames.EDITION).getMap();
	protected MultiMap priceTM = BookFieldsMap.fieldsMap.get(BookFieldNames.PRICE).getMap();
	
	@Override
	public boolean addBook(Book book) {
		
		if (contains(book)) return false;
		isbnHM.put(book.getISBN(), book);
		
		DaoField<Book>daofield = null;
		for (Entry<BookFieldNames, DaoField<Book>> e : fieldsMap.entrySet()) {
			if (e.getKey().equals(BookFieldNames.AUTHOR)) for(Author author : book.getAuthors()) authorTM.put(author, book);
			else {
				daofield = e.getValue();
				daofield.getMap().put(daofield.getGetter().apply(book), book);
			}
		}
		return true;
	}

	@Override
	public void fillRandomLibrary(int numBooks) {
		for (int i=0; i<numBooks; i++) {
			addBook(Book.getRandomBook());	
		}
	}

	@Override
	public boolean contains(Book book) {
		return isbnHM.containsKey(book.getISBN());
	}

	@Override
	public boolean remove(Book book) {
		
		if (!contains(book)) return false;
		
		isbnHM.remove(book.getISBN());
		
		DaoField<Book>daofield = null;
		for (Entry<BookFieldNames, DaoField<Book>> e : fieldsMap.entrySet()) {
			if (e.getKey().equals(BookFieldNames.AUTHOR)) for(Author author : book.getAuthors()) authorTM.remove(author, book);
			else {
				daofield = e.getValue();
				daofield.getMap().remove(daofield.getGetter().apply(book), book);
			}
		}
		return true;
	}
	
	protected boolean ISBN_present(long isbn) {
		return isbnHM.containsKey(isbn);
	}
	
	@Override
	public boolean correctBook(long isbn, BookFieldNames field, Object value) {
		if (!ISBN_present(isbn)) return false;
		if (field.equals(BookFieldNames.ISBN) && ISBN_present((Long)value)) return false;
		
		Book book = isbnHM.get(isbn);
		remove(book);
		fieldsMap.get(field).getSetter().accept(book, value);
		addBook(book);
		return true;
	}
	
	@Override
	public Iterable<Book> getAllBooks() {
		TreeSet<Book> tsb = new TreeSet<>();
		tsb.addAll(isbnHM.values());
		return tsb;
	}

	@Override
	public Iterable<Book> getBooksBy(BookFieldNames field, Object key) {
		LinkedHashSet<Book> res = new LinkedHashSet<>();
		if (field.equals(BookFieldNames.ISBN)) res.add(isbnHM.get((Long)key));
		else {
			DaoField<Book> daofield = fieldsMap.get(field);
			Collection<Book> col = (Collection<Book>) daofield.getMap().map().get(key);
			if (col != null)res.addAll(col);
		}
		return res;
	}
	
	@Override
	public Iterable<Book> getAllBooksSortedBy(BookFieldNames field) {
		if (field.equals(BookFieldNames.ISBN)) {
			TreeSet<Book> tsb = new TreeSet<Book>();
			tsb.addAll(isbnHM.values());
			return tsb;
		}
		else return fieldsMap.get(field).getMap().multiMapToIterable();
	}

	@Override
	public Iterable<Book> getBooksPrintedInRange(LocalDate min, LocalDate max) {
		ArrayList<Book> alb = new ArrayList<>();
		for(Object c : ((TreeMap)editionTM.map()).subMap(min, max).values())alb.addAll((TreeSet<Book>)c);
		return alb;
	}

	@Override
	public Iterable<Book> getBooksPricedInRange(double minPrice, double maxPrice) {
		ArrayList<Book> alb = new ArrayList<>();
		for(Object c : ((TreeMap)priceTM.map()).subMap(minPrice, maxPrice).values())alb.addAll((TreeSet<Book>)c);
		return alb;
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
		isbnHM.clear();
		BookFieldsMap.setEmptyMaps();
	}
	
	/**
	 * @return true if the representation of class is ok;
	 */
	private boolean checkRep() {
		DaoField<Book>daofield = null;
		for (Entry<BookFieldNames, DaoField<Book>> e : fieldsMap.entrySet()) {

				daofield = e.getValue();
				daofield.getMap().

		}
		return (s-> s.getSize() == isbnHM.size());
	}

	/**
	 * wrapper procedure for testing
	 * @return true if class self-tested successfully
	 */
	public boolean selfTestOK() {
		boolean repIsOK = checkRep(); 
		assertTrue(repIsOK);
		return repIsOK;
	}
	
}
