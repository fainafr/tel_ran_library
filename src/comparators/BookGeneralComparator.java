package comparators;

import java.util.Comparator;
import dao.Book;

public class BookGeneralComparator{
	private static Comparator<Book> instance = (new BookAuthorsComparator())
			                            	.thenComparing(new BookTitleComparator())
			                            	.thenComparing(new BookPublisherComparator())
			                            	.thenComparing(new BookEditionDateComparator())
			                            	.thenComparing(new BookPriceComparator())
			                            	.thenComparing(new BookISBNComparator());
	
	public static Comparator<Book> getInstance(){return instance;}									   
}
