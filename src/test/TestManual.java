package test;

import java.io.IOException;
import java.util.Iterator;

import IO.LibraryIO;
import dao.Book;
import model.Library;
import util.RandomLibrary;

public class TestManual {
	
	
	private static final String LIBRARY_FILE = "Library.txt";

	public static void main(String[] args) throws IOException {
		Library model = RandomLibrary.randomModel(4);
		
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
		LibraryIO.dumpToFile(model, LIBRARY_FILE);
		reparse = LibraryIO.readFromFile(LIBRARY_FILE);
		Iterable<Book> modelIt = model.getAllBooks();
		Iterable<Book> reparseIt = reparse.getAllBooks();
		
		if(model.size() != reparse.size()) return;
		Iterator<Book> mIt = modelIt.iterator();
		Iterator<Book> rpIt = reparseIt.iterator();
		
		for(int i = 0; i < model.size(); i++){
			
			Book mBook = mIt.next();
			Book rpBook = rpIt.next();
			System.out.println(mBook);
			System.out.println(rpBook);
			if (!mBook.equals(rpBook)) System.out.println("Inequals");
		}
		
		System.out.println("ORIGINAL BOOKZ");
		for (Book book : modelIt){
			System.out.println(book);
		}
		
		System.out.println();
		System.out.println("REPARSED BOOKZ");
		for (Book book : reparseIt){
			System.out.println(book);
		}
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
