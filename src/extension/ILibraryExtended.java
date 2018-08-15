package extension;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

import dao.Author;
import dao.Book;
import model.LibraryBasic;

public interface ILibraryExtended {
	
	public boolean addAll(Collection<Book> bCollection);
	public boolean addLibary(LibraryBasic lib);
	
	public void fillWithIterable(Iterable<Book> iterable);
	
	public boolean containsAll(Collection<Book> bCollection);
	public Iterable<Book> containsAtLeastOne(Collection<Book> bCollection);
	
	public Iterable<Book> removeAll(Collection<Book> bCollection);
	public Iterable<Book> retainAll(Collection<Book> bCollection);
	
	public Iterable<Book> getAllBooksSortedWithComparator(Comparator<Book> comparator);
	public Iterable<Book> getAllBooksFilteredWithPredicate(Predicate<Book> predicate);
	
	public boolean correctBookWithPattern(long isbn, Book pattern);
	
	public Iterable<Book> getBooksByAllAuthors(Collection<Author> aCollection);
	public Iterable<Book> getBooksByAtLeastOneAuthor(Collection<Author> aCollection);
	
	public Iterable<Book> getBooksPrintedBefore(LocalDate max);
	public Iterable<Book> getBooksPrintedBefore(int year);
	public Iterable<Book> getBooksPrintedAfter(LocalDate min);
	public Iterable<Book> getBooksPrintedAfter(int year);
	public Iterable<Book> getBooksPrintedInRange(int yearMin, int yearMax);
	
	public Iterable<Book> getBooksCheaperThan(double maxPrice);
	public Iterable<Book> getBooksMoreExpensiveThan(double minPrice);
		

}
