package comparators;

import java.util.Comparator;
import dao.Book;

public class BookPublisherComparator implements Comparator<Book> {

	@Override
	public int compare(Book book1, Book book2) {
		return book1.getPublisher().compareTo(book2.getPublisher());
	}

}
