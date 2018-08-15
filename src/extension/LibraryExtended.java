package extension;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;

import dao.Author;
import dao.Book;
import model.LibraryBasic;

public class LibraryExtended extends LibraryBasic implements ILibraryExtended{
	
	@Override
	public boolean addAll(Collection<Book> bCollection) {
		boolean res = true;
		for (Book book : bCollection)res = addBook(book) && res;
		return res;
	}

	@Override
	public boolean addLibary(LibraryBasic lib) {
		boolean res = true;
		for (Book book : lib.getAllBooks())res = addBook(book) && res;
		return res;
	}
	
	@Override
	public void fillWithIterable(Iterable<Book> iterable) {
		for (Book book : iterable)addBook(book);	
	}
	
	@Override
	public boolean containsAll(Collection<Book> bCollection) {
		boolean res = true;
		for (Book book : bCollection)res = contains(book) && res;
		return res;
	}

	@Override
	public Iterable<Book> containsAtLeastOne(Collection<Book> bCollection) {
		LinkedHashSet<Book> lhsb = new LinkedHashSet<>();
		for (Book book : bCollection) {
			if(contains(book)) lhsb.add(book);
		}
		return lhsb;
	}
	
	@Override
	public Iterable<Book> removeAll(Collection<Book> bCollection) {
		LinkedHashSet<Book> lhsb = new LinkedHashSet<>();
		for (Book book : bCollection) {
			if (remove(book))lhsb.add(book);
		}
		return lhsb;
	}

	@Override
	public Iterable<Book> retainAll(Collection<Book> bCollection) {
		ArrayList<Book> alb = new ArrayList<>();
		Collection<Book> all = isbnHM.values();
		for (Book book : all) {
			if (!bCollection.contains(book)) {
				remove(book);
				alb.add(book);
			}
		}
		return alb;
	}

	@Override
	public Iterable<Book> getAllBooksSortedWithComparator(Comparator<Book> comparator) {
		TreeSet<Book> tsb = new TreeSet<>(comparator);
		Collection<Book> all = isbnHM.values();
		tsb.addAll(all);
		return tsb;
	}

	@Override
	public Iterable<Book> getAllBooksFilteredWithPredicate(Predicate<Book> predicate) {
		TreeSet<Book> tsb = new TreeSet<>();
		Collection<Book> all =  isbnHM.values();
		for (Book book : all) {
			if (predicate.test(book))tsb.add(book);
		}
		return tsb;
	}

	@Override
	public Iterable<Book> getBooksByAllAuthors(Collection<Author> aCollection) {
		TreeSet<Book> tsb = new TreeSet<>();
		boolean first = true;
		for(Author a : aCollection){
			TreeSet<Book> tsa = (TreeSet<Book>) authorTM.get(a);
			if (tsa == null) return new TreeSet<>();
			if (first) {
				tsb.addAll(tsa);
				first = false;
			}
			else tsb.retainAll(tsa);
		}
		return tsb;
	}

	@Override
	public Iterable<Book> getBooksByAtLeastOneAuthor(Collection<Author> aCollection) {
		TreeSet<Book> tsb = new TreeSet<>();
		for(Author a : aCollection){
			TreeSet<Book> tsa = (TreeSet<Book>) authorTM.get(a);
			if (tsa != null)tsb.addAll(tsa);
		}
		return tsb;
	}
	
	@Override
	public boolean correctBookWithPattern(long isbn, Book pattern) {
		
		if (!ISBN_present(isbn)) return false;
		if (ISBN_present(pattern.getISBN())) return false;
		
		Book book = isbnHM.get(isbn);
		
		remove(book);
		
		if(pattern.getISBN() != Book.DEFAULT_ISBN)book.setISBN(pattern.getISBN());
		if(pattern.getAuthors() != Book.DEFAULT_AUTHORS)book.setAuthors(pattern.getAuthors());
		if(pattern.getTitle() != Book.DEFAULT_TITLE)book.setTitle(pattern.getTitle());
		if(pattern.getPublisher() != Book.DEFAULT_PUBLISHER)book.setPublisher(pattern.getPublisher());
		if(pattern.getEdition() != Book.DEFAULT_EDITION_DATE)book.setEdition(pattern.getEdition());
		if(pattern.getPrice() != Book.DEFAULT_PRICE)book.setPrice(pattern.getPrice());
	
		addBook(book);
		return true;
	}

	@Override
	public Iterable<Book> getBooksPrintedBefore(LocalDate max) {
		ArrayList<Book> alb = new ArrayList<>();
		for(Object c : ((TreeMap)editionTM.map()).headMap(max).values())alb.addAll((TreeSet<Book>)c);
		return alb;
	}

	@Override
	public Iterable<Book> getBooksPrintedBefore(int year) {
		return getBooksPrintedBefore(LocalDate.of(year, 1, 1));
	}

	@Override
	public Iterable<Book> getBooksPrintedAfter(LocalDate min) {
		ArrayList<Book> alb = new ArrayList<>();
		for(Object c : ((TreeMap)editionTM.map()).tailMap(min).values())alb.addAll((TreeSet<Book>)c);
		return alb;
	}

	@Override
	public Iterable<Book> getBooksPrintedAfter(int year) {
		return getBooksPrintedAfter(LocalDate.of(year, 12, 31));
	}
	
	@Override
	public Iterable<Book> getBooksPrintedInRange(int yearMin, int yearMax) {
		return getBooksPrintedInRange(LocalDate.of(yearMin, 1, 1), LocalDate.of(yearMax+1, 1, 1));
	}

	@Override
	public Iterable<Book> getBooksCheaperThan(double maxPrice) {
		ArrayList<Book> alb = new ArrayList<>();
		for(Object c : ((TreeMap)priceTM.map()).headMap(maxPrice).values())alb.addAll((TreeSet<Book>)c);
		return alb;
	}

	@Override
	public Iterable<Book> getBooksMoreExpensiveThan(double minPrice) {
		ArrayList<Book> alb = new ArrayList<>();
		for(Object c : ((TreeMap)priceTM.map()).tailMap(minPrice).values())alb.addAll((TreeSet<Book>)c);
		return alb;
	}





}
