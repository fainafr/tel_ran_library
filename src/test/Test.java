package test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Book;
import model.Library;

public class Test {

	public static void main(String[] args) {
		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		for (int i = 0; i < 100; i++)
			lib.add(Book.getRandomBook());
		Library model = new Library();
		for (Book b : lib)
			model.addBook(b);
		ArrayList<Book> sortedByTitle = new ArrayList<Book>();
		sortedByTitle.addAll((Collection<? extends Book>) model.getAllBooksSortedByTitle());
		for (Book b : sortedByTitle)
			System.out.println(b);
	}

}
