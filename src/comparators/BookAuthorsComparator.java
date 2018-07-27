package comparators;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import dao.Author;
import dao.Book;

public class BookAuthorsComparator implements Comparator<Book> {

	@Override
	public int compare(Book book1, Book book2) {
		Set<Author> auth1 = book1.getAuthors();
		Set<Author> auth2 = book2.getAuthors();
		
		Iterator<Author> it1 = auth1.iterator();
		Iterator<Author> it2 = auth2.iterator();
		
		int c = 0;
		for(; c == 0 && it1.hasNext() && it2.hasNext(); c = it1.next().compareTo(it2.next()));
		return c != 0 ? c : auth1.size()- auth2.size();
	}

}
