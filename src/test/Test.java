package test;

import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Book;
import model.Library;

public class Test {

	public static void main(String[] args) {
		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		for (int i = 0; i < 20; i++)
			lib.add(Book.getRandomBook());
		Library model = new Library();
		for (Book b : lib)
			model.addBook(b);

		System.out.println("BY TITLE");
		for(Book b : model.getAllBooksSortedByTitle()) System.out.println(b);
	
		System.out.println("BY AUTHORS");
		for(Book b : model.getAllBooksSortedByAuthors()) System.out.println(b);
		
		System.out.println("BY DATE");
		for(Book b : model.getAllBooksSortedByEditionDate()) System.out.println(b);
		
		System.out.println("BY PUBLISHER");
		for(Book b : model.getAllBooksSortedByPublisherNames()) System.out.println(b);
		
		System.out.println("BY COUNTRY");		
		for(Book b : model.getAllBooksSortedByPublisherCountries()) System.out.println(b);
		
		System.out.println("BY PRICE");		
		for(Book b : model.getAllBooksSortedByPrice()) System.out.println(b);
		
		Book rand = model.getAllBooks().iterator().next();
		System.out.println("REMOVE I");
		System.out.println(rand);
		model.remove(rand);
		
		System.out.println("BY AUTHORS");
		for(Book b : model.getAllBooksSortedByAuthors()) System.out.println(b);
		model.selfTest();
		
		Book randii = model.getAllBooks().iterator().next();
		System.out.println("REMOVE II");
		System.out.println(randii);
		model.remove(randii);
		
		System.out.println("BY AUTHORS");
		for(Book b : model.getAllBooksSortedByAuthors()) System.out.println(b);
		model.selfTest();
		
		
	}

}
