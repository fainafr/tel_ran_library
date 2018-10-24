package comparators;

import java.util.Comparator;

import dao.Book;

public class BookPriceComparator implements Comparator<Book> {

	@Override
	public int compare(Book book1, Book book2) {
		Double p1 = book1.getPrice();
		Double p2 = book2.getPrice();
		return p1.compareTo(p2);
	}

}
