package comparators;

import java.util.Comparator;

import dao.Book;

public class BookTitleComparator implements Comparator<Book> {

	@Override
	public int compare(Book book1, Book book2) {
		return book1.getTitle().compareTo(book2.getTitle());
	}

}
