package test;

import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Book;
import model.Library;

public class TestManual {
	
	public static void main(String[] args) {
		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		for (int i = 0; i < 10; i++)
			lib.add(Book.getRandomBook());
		Library model = new Library();
		for (Book b : lib)
			model.addBook(b);

		testRemovingsI(model);
		
		testCorrectionsI(model);
		
		testSortings(model);	
	}

	private static void testCorrectionsI(Library model) {
		System.out.println("CORRECTIONS");
		System.out.println("ISBN");
		Book randiii = model.getAllBooks().iterator().next();
		System.out.println(randiii);
		System.out.println();
		for(Book b : model.getAllBooks()) System.out.println(b);
		model.correctBookISBN(randiii.getISBN(), randiii.getISBN()+1);
		System.out.println();
		for(Book b : model.getAllBooks())System.out.println(b);
		model.selfTestOK();
	}

	private static void testRemovingsI(Library model) {
		Book rand = model.getAllBooks().iterator().next();
		System.out.println("REMOVE I");
		System.out.println(rand);
		model.remove(rand);
		
		System.out.println("BY AUTHORS");
		for(Book b : model.getAllBooksSortedByAuthors()) System.out.println(b);
		model.selfTestOK();
		
		Book randii = model.getAllBooks().iterator().next();
		System.out.println("REMOVE II");
		System.out.println(randii);
		model.remove(randii);
		
		System.out.println("BY AUTHORS");
		for(Book b : model.getAllBooksSortedByAuthors()) System.out.println(b);
		model.selfTestOK();
	}

	private static void testSortings(Library model) {
		System.out.println("BY HASH");
		for(Book b : model.getAllBooks()) System.out.println(b);
		
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
	}

}
