package test;

import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Book;

public class Test {

	public static void main(String[] args) {
		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		for (int i=0; i<100; i++)lib.add(Book.getRandomBook());
		for (Book b : lib) System.out.println(b);
	}

}
