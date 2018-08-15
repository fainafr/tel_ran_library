package comparators;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import dao.Author;
import dao.Book;

public class BookComparators {
	
	public static Comparator<Book> BookAuthorsComparator = (book1, book2) -> {
		Set<Author> auth1 = book1.getAuthors();
		Set<Author> auth2 = book2.getAuthors();
		
		Iterator<Author> it1 = auth1.iterator();
		Iterator<Author> it2 = auth2.iterator();
		
		int c = 0;
		for(; c == 0 && it1.hasNext() && it2.hasNext(); c = it1.next().compareTo(it2.next()));
		return c != 0 ? c : auth1.size()- auth2.size();
	};
	
	public static Comparator<Book> BookEditionComparator = (book1, book2) -> {
		return book1.getEdition().compareTo(book2.getEdition());
	};
	
	public static Comparator<Book> BookISBNComparator = (book1, book2) -> {
		Long isbn1 = book1.getISBN();
		Long isbn2 = book2.getISBN();
		return isbn1.compareTo(isbn2);
	};
	
	public static Comparator<Book> BookPriceComparator = (book1, book2) -> {
		Double p1 = book1.getPrice();
		Double p2 = book2.getPrice();
		return p1.compareTo(p2);
	};
	
	public static Comparator<Book> BookPublisherComparator = (book1, book2) -> {
		return book1.getPublisher().compareTo(book2.getPublisher());
	};
	
	public static Comparator<Book> BookTitleComparator = (book1, book2) -> {
		return book1.getTitle().compareTo(book2.getTitle());
	};
	
	
	

}
