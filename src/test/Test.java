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
		for (int i = 0; i < 50; i++)
			lib.add(Book.getRandomBook());
		Library model = new Library();
		for (Book b : lib)
			model.addBook(b);

		System.out.println("BY TITLE");
		ArrayList<Book> sortedByTitle = new ArrayList<Book>();
		sortedByTitle.addAll((Collection<? extends Book>) model.getAllBooksSortedByTitle());
		for (Book b : sortedByTitle)
			System.out.println(b);

		System.out.println("BY AUTHORS");
		ArrayList<Book> sortedByAuthor = new ArrayList<Book>();
		sortedByAuthor.addAll((Collection<? extends Book>) model.getAllBooksSortedByAuthors());
		for (Book b : sortedByAuthor)
			System.out.println(b);
	}

}
