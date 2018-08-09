package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dao.Author;
import dao.Book;
import dao.Publisher;

public class Library implements ILibraryA{
	
	private static final TreeSet<Book> EMPTY_TREE_SET = new TreeSet<Book>();
	private static final ArrayList<LibraryField> fields = new ArrayList<>();
	private HashMap<Long, Book> isbnHM;
	private HashMap<String, Map<String,TreeSet<Book>>> mapmap = new HashMap<>();
	
	public Library(){
		makeFields();
		emptyLibrary();
	}
	
	private void makeFields(){
		fields.add(new LibraryField("author", Maps.TREE));
		fields.add(new LibraryField("title", Maps.TREE));
		fields.add(new LibraryField("publisher", Maps.HASH));
		fields.add(new LibraryField("publisherName", Maps.TREE));
		fields.add(new LibraryField("publisherCountry", Maps.TREE));
		fields.add(new LibraryField("edition", Maps.TREE));
		fields.add(new LibraryField("price", Maps.TREE));
	}
	
	private void makeMapmap(){
		
		for (LibraryField lf : fields){
			switch (lf.getKind()){
				case HASH: mapmap.put(lf.getName(), new LinkedHashMap<String,TreeSet<Book>>()); break;
				case LINKED_HASH: mapmap.put(lf.getName(), new LinkedHashMap<String,TreeSet<Book>>()); break;
				case TREE:
				default: mapmap.put(lf.getName(), new TreeMap<String,TreeSet<Book>>());
			}
		}
	}

	private void emptyLibrary() {
		
		isbnHM = new HashMap<Long, Book>();
		makeMapmap();
	}

	@Override
	public boolean addBook(Book book) {
		if (book == null) return false;
		
		if (isbnHM.putIfAbsent(book.getISBN(), book) != null) return false;
		for (String s : mapmap.keySet()){
			if(s.equals("author"))
				for(Author a : book.getAuthors())
					putToMultivalueMap(mapmap.get("author"), a.toString(), book);
			else putToMultivalueMap(mapmap.get(s),getFieldKey(book,s), book);
		}
		
		return true;
	}
	public String getFieldKey(Book book, String fieldName){
		switch (fieldName){
			case "title": return book.getTitle();
			case "publisher": return book.getPublisher().toString();
			case "publisherName": return book.getPublisher().getName();
			case "publisherCountry": return book.getPublisher().getCountry().name();
			case "edition": return book.getEdition().toString();
			case "price": return Double.toString(book.getPrice());
		}
		return null;
	}
	
	private static <K> void putToMultivalueMap(Map<String,TreeSet<Book>> map, String key, Book book){
		TreeSet<Book> tsb = map.get(key);
		if (tsb == null) {
			tsb = new TreeSet<>();
			map.put(key, tsb);
		}
		tsb.add(book);
	}

	@Override
	public boolean addAll(Collection<Book> bCollection) {
		boolean res = true;
		for (Book book : bCollection) res = addBook(book) && res;
		return res;
	}

	@Override
	public boolean addLibary(Library lib) {
		boolean res = true;
		for (Book book : lib.getAllBooks()) res = addBook(book) && res;
		return res;
	}

	@Override
	public void fillRandomLibrary(int numBooks) {
		emptyLibrary();
		int counter = 0;
		while (counter < numBooks){
			if (addBook(Book.getRandomBook())) counter++;
		}		
	}

	@Override
	public void fillWithIterable(Iterable<Book> iterable) {
		emptyLibrary();
		for (Book book : iterable) addBook(book);
	}

	@Override
	public boolean contains(Book book) {
		return isbnHM.containsKey(book.getISBN());
	}

	@Override
	public boolean containsAll(Collection<Book> bCollection) {
		for(Book book : bCollection){
			if (!contains(book)) return false;
		}
		return true;
	}

	@Override
	public Iterable<Book> containsAtLeastOne(Collection<Book> bCollection) {
		TreeSet<Book> tsb = new TreeSet<>();
		for(Book book : bCollection){
			if (contains(book)) tsb.add(book);
		}		
		return tsb;
	}
	
	private void removeFromTreeSet(Map<String,TreeSet<Book>> map, String key, Book book){
		TreeSet<Book> tsb = map.get(key);
		if (tsb == null) return;
		if (tsb.size() < 2)map.remove(key);
		else tsb.remove(book);
	}

	@Override
	public boolean remove(Book book) {
		boolean res = isbnHM.remove(book.getISBN()) != null;
		if (res){
			for (String s : mapmap.keySet()){
				if(s.equals("author"))
					for(Author a : book.getAuthors())removeFromTreeSet(mapmap.get("author"), a.toString(), book);
				else removeFromTreeSet(mapmap.get(s),getFieldKey(book,s), book);
			}
		}
		return res;
	}

	@Override
	/**/public Iterable<Book> removeAll(Collection<Book> bCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Book> retainAll(Collection<Book> bCollection) {
		TreeSet<Book> res = new TreeSet<>();
		for (Book book : isbnHM.values()){
			if(!bCollection.contains(book)) {
				remove(book);
				res.add(book);
			}
		}
		return res;
	}

	@Override
	public Iterable<Book> getAllBooksSortedWithComparator(Comparator<Book> comparator) {
		TreeSet<Book> tsb = new TreeSet<>(comparator);
		for(Book book : isbnHM.values()) tsb.add(book);
		return tsb;
	}

	@Override
	public Iterable<Book> getAllBooksFilteredWithPredicate(Predicate<Book> predicate) {
		TreeSet<Book> tsb = new TreeSet<>();
		for (Book book : isbnHM.values()){
			if(predicate.test(book))tsb.add(book);
		}
		return tsb;
	}
	
	private boolean badISBN(long isbn) {
		return !isbnHM.containsKey(isbn);
	}

	@Override
	public boolean correctBookISBN(long isbn, long newISBN) {
		if (badISBN(isbn) || !badISBN(newISBN)) return false;
		
		Book book = isbnHM.get(isbn);
		remove(book);
		book.setISBN(newISBN);
		addBook(book);
		return true;
	}
	
	private void setField(Book book, String field, Object value){
		switch(field){
			case "authors": book.setAuthors((Set<Author>)value);break;
			case "title": book.setTitle((String)value);break;
			case "publisher": book.setPublisher((Publisher)value);break;
			case "edition": book.setEdition((LocalDate)value);break;
			case "price": book.setPrice((Double)value);break;
			default:
		}
	}

	@Override
	public boolean correctBookByField(long isbn, Object newValue, String field) {
		
		if(badISBN(isbn)) return false;
		
		Book book = isbnHM.get(isbn);
		remove(book);
		setField(book, field, newValue);
		addBook(book);
		return true;
	}

	@Override
	public boolean correctBookWithPattern(long isbn, Book pattern) {
		if(badISBN(isbn) || !badISBN(pattern.getISBN())) return false;
		
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
	public Book getBookByISBN(long isbn) {
		return isbnHM.get(isbn);
	}

	public Iterable<Book> getBooksByField(String field, Object value) {
		return mapmap.get(field).get(value.toString());
	}
	
	private <K> Iterable<Book> getAllBooksSorted(Map<K,TreeSet<Book>> map){
		LinkedHashSet<Book> lhsb = new LinkedHashSet<>();
		for(Collection<Book> c : map.values())lhsb.addAll(c);
		return lhsb;
	}

	@Override
	public Iterable<Book> getAllBooks() {
		TreeSet<Book> tsb = new TreeSet<>();
		tsb.addAll(isbnHM.values());
		return tsb;
	}
	
	public Iterable<Book> getAllBooksSortedByField(String field){
		return getAllBooksSorted(mapmap.get(field));
	}
	
	@Override
	public Iterable<Book> getBooksByAllAuthors(Collection<Author> aCollection) {
		TreeSet<Book> tsb = new TreeSet<>();
		for(Author a : aCollection){
			TreeSet<Book> tsa = mapmap.get("author").get(a);
			if (tsa != null)tsb.addAll(tsa);
			else return EMPTY_TREE_SET;
		}
		return tsb;
	}

	@Override
	public Iterable<Book> getBooksByAtLeastOneAuthor(Collection<Author> aCollection) {
		TreeSet<Book> tsb = new TreeSet<>();
		for(Author a : aCollection){
			TreeSet<Book> tsa = mapmap.get("author").get(a);
			if (tsa != null)tsb.addAll(tsa);
		}
		return tsb;
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


	

	@Override
	public Iterable<Book> getAllBooksByField(String field) {
		Map<String, TreeSet<Book>> col = mapmap.get(field);
		System.out.println(col);
		ArrayList<Book> alb = new ArrayList<Book>();
		for (Entry<String, TreeSet<Book>> e : col.entrySet()) {
			alb.addAll(e.getValue());
		}
		return alb;
	}
}


