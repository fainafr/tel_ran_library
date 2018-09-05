package multimap;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Author;
import dao.Book;
import model.BookKey;
import model.BookSortWay;

public class MultiMapAuthorKeyFiller extends MultiMap<Author, Set<Book>, Book> {

	public MultiMapAuthorKeyFiller() {
	}

	@Override
	public HashSet<Book> createMapCollection() {
		return new HashSet<Book>();
	}

	@Override
	//we have to override this despite not needing it for multimap with key;
	public Author getKey(Book element) {
		return element.getAuthors().iterator().next();
	}

 
}
