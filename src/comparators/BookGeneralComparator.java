package comparators;

import java.util.Comparator;
import dao.Book;

public class BookGeneralComparator{
	private static Comparator<Book> instance = 
					(BookComparators.BookAuthorsComparator)
	  .thenComparing(BookComparators.BookTitleComparator)
	  .thenComparing(BookComparators.BookEditionComparator)
	  .thenComparing(BookComparators.BookPriceComparator)
	  .thenComparing(BookComparators.BookPublisherComparator)
	  .thenComparing(BookComparators.BookISBNComparator);
	
	public static Comparator<Book> getInstance(){return instance;}									   
}
