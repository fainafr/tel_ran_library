package model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import dao.Author;
import dao.Book;
import dao.Countries;
import dao.Publisher;

public interface ILibrary {
	
	// add
	public boolean addBook(Book book);
	public boolean addAll(Collection<Book> bCollection);
	public boolean addLibary(Library lib);
	
	//fill
	public void fillRandomLibrary(int numBooks);
	public void fillWithIterable(Iterable<Book> iterable);
	
	//contains
	public boolean contains(Book book);
	public boolean containsAll(Collection<Book> bCollection);
	public Iterable<Book> containsAtLastOne(Collection<Book> bCollection);
	
	//remove
	public boolean remove(Book book);
	public Iterable<Book> removeAll(Collection<Book> bCollection);
	public Iterable<Book> retainAll(Collection<Book> bCollection);
	
	// sort and filter
	
	public Iterable<Book> getAllBooksSortedWithComparator(Comparator<Book> comparator);
	public Iterable<Book> getAllBooksFilteredWithPredicate(Predicate<Book> predicate);
	
	// correct
	
	public boolean correctBookISBN(long isbn, long newISBN);
	public boolean correctBookAuthors(long isbn, Set<Author> newAuthors);
	public boolean correctBookTitle(long isbn, String newTitle);
	public boolean correctBookPublisher(long isbn, Publisher newPublisher);
	public boolean correctBookEditionDate(long isbn, LocalDate newEditionDate);
	public boolean correctBookPrice(long isbn, double newPrice);
	
	public boolean correctBookWithPattern(long isbn, Book pattern);
		
	//queries
	
	public Book getBookByISBN(long isbn);
	public Iterable<Book> getBooksByAuthor(Author author);
	public Iterable<Book> getBooksByAllAuthors(Collection<Author> aCollection);
	public Iterable<Book> getBooksByAtLastOneAuthor(Collection<Author> aCollection);
	public Iterable<Book> getBooksByTitle(String title);
	public Iterable<Book> getBooksByPublisher(Publisher publicher);
	public Iterable<Book> getBooksByPublisherName(String pName);
	public Iterable<Book> getBooksByPublisherCountry(Countries pCountry);
	
	//public Iterable<Book> getBooksByPattern(Book pattern);
	
	//all books
	
	public Iterable<Book> getAllBooks();                  //sorted by ISBN
	public Iterable<Book> getAllBooksSortedByAuthors();
	public Iterable<Book> getAllBooksSortedByTitle();
	public Iterable<Book> getAllBooksSortedByPublisherNames();
	public Iterable<Book> getAllBooksSortedByPublisherCountries();
	public Iterable<Book> getAllBooksSortedByEditionDate();
	public Iterable<Book> getAllBooksSortedByPrice();
	
	//range queries
	
	public Iterable<Book> getBooksPrintedBefore(LocalDate max);
	public Iterable<Book> getBooksPrintedBefore(int year);
	public Iterable<Book> getBooksPrintedAfter(LocalDate min);
	public Iterable<Book> getBooksPrintedAfter(int year);
	public Iterable<Book> getBooksPrintedInRange(LocalDate min,LocalDate max);
	public Iterable<Book> getBooksPrintedInRange(int yearMin, int yearMax);
	
	public Iterable<Book> getBooksCheaperThan(double maxPrice);
	public Iterable<Book> getBooksMoreExpensiveThan(double minPrice);
	public Iterable<Book> getBooksPricedInRange(double minPrice, double maxPrice);
	
	//size
	public int size();
	public boolean isEmpty();
	public void clear();

}
