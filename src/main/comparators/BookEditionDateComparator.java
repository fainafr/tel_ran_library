package comparators;

import java.util.Comparator;

import dao.Book;

public class BookEditionDateComparator implements Comparator<Book> {

	@Override
	public int compare(Book book1, Book book2) {
		return book1.getEdition().compareTo(book2.getEdition());
	}

}
