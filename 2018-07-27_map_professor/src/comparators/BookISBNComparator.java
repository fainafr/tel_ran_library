package comparators;

import java.util.Comparator;
import dao.Book;

public class BookISBNComparator implements Comparator<Book>{

	@Override
	public int compare(Book book1, Book book2) {
		Long isbn1 = book1.getISBN();
		Long isbn2 = book2.getISBN();
		return isbn1.compareTo(isbn2);
	}

}
