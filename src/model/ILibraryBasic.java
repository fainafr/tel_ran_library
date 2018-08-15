package model;

import java.time.LocalDate;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.function.Predicate;

import dao.Author;
import dao.Book;
import dao.Publisher;

public interface ILibraryBasic {
	
		// creation
		public void fillRandomLibrary(int numBooks);
		// basic
		public boolean addBook(Book book);
		public boolean contains(Book book);
		public boolean remove(Book book);
		boolean correctBook(long isbn, BookFieldNames field, Object value);				
		//queries
		public Iterable<Book> getAllBooks();
		public Iterable<Book> getBooksBy(BookFieldNames field, Object key);
		public Iterable<Book> getAllBooksSortedBy(BookFieldNames field);
		public Iterable<Book> getBooksPrintedInRange(LocalDate min,LocalDate max);
		public Iterable<Book> getBooksPricedInRange(double minPrice, double maxPrice);		
		//size
		public int size();
		public boolean isEmpty();
		public void clear();

		


}
