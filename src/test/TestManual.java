package test;

import java.io.IOException;
import java.util.TreeSet;

import IO.LibraryIO;
import comparators.BookGeneralComparator;
import dao.Book;
import model.Library;

public class TestManual {
	
	private static final int MODELMAXBOOKS = 4;
	private static final String LIBRARY_FILE = "Library.txt";

	public static void main(String[] args) throws IOException {
		Library model = randomModel();
		
		dumpAndParseTest(model);

//		testRemovingsI(model);
//		
//		testCorrectionsI(model);
//		
//		testSortings(model);	
	}


	/**
	 * Visual test for dumping and reparsing the lib
	 * @param model
	 * @throws IOException
	 */
	private static void dumpAndParseTest(Library model) throws IOException {
		Library reparse;
		System.out.println("ORIGINAL BOOKZ");
		for (Book book : model.getAllBooks()){
			System.out.println(book);
		}
		
		LibraryIO.dumpToFile(model, LIBRARY_FILE);
		
		reparse = LibraryIO.readFromFile(LIBRARY_FILE);
		
		System.out.println();
		System.out.println("REPARSED BOOKZ");
		for (Book book : reparse.getAllBooks()){
			System.out.println(book);
		}
	}


	/**
	 * @return new random library
	 */
	private static Library randomModel() {
		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		for (int i = 0; i < MODELMAXBOOKS; i++)
			lib.add(Book.getRandomBook());
		Library model = new Library();
		for (Book b : lib)
			model.addBook(b);
		return model;
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
