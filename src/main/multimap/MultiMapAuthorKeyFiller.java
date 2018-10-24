package multimap;

import java.util.HashSet;
import java.util.Set;
import dao.Author;
import dao.Book;

public class MultiMapAuthorKeyFiller extends MultiMap<Author, Set<Book>, Book> {

	public MultiMapAuthorKeyFiller() {
	}

	@Override
	public HashSet<Book> createMapCollection() {
		return new HashSet<Book>();
	}
 
}
