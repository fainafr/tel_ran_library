package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import IO.LibraryIO;
import dao.Author;
import dao.Book;
import model.Library;
import util.RandomLibrary;

public class TestManual {

	private static final String LIBRARY_FILE = "Library.txt";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Library model = RandomLibrary.randomModel(4);

		printByAuthors(model);
		
		serializeAndReparseVisual(model);
		
		dumpAndParseVisual(model);
		
		testRemovingsI(model);

		testCorrectionsI(model);

		testSortings(model);
	}

	
	/**
	 * Prints each author in the library and the books credited to him.
	 * @param model library 
	 * Creates
	 */
	public static void printByAuthors(Library model) {
		authors(model)
		.peek(a -> System.out.print(a.toString() + ": ")) // From each author..
		.map(a -> model.getBooksByAuthor(a)) // to Iterable..
		.flatMap(a -> StreamSupport.stream(a.spliterator(), false)) // To stream..
		.forEach(b -> System.out.println(b.toShortString()));		// Custom print
	}
	
	/**
	 * @param model Library
	 * @return multimap of books by author
	 */
	public static Map<Author, Set<Book>> multiMapAuthors(Library model) {
		Map<Author, Set<Book>> multiAuthors = new HashMap<>();
		authors(model)
		.forEach(a -> multiAuthors.put(a, createSet(model.getBooksByAuthor(a))));
		return multiAuthors;		
	}
	
	/**
	 * @param booksByAuthor Iterable
	 * @return Set from this iterable
	 */
	private static Set<Book> createSet(Iterable<Book> booksByAuthor) {
		return StreamSupport.stream(booksByAuthor.spliterator(), false).collect(Collectors.toSet());
	}

	/**
	 * @param model library
	 * @return Stream of authors in the model
	 */
	private static Stream<Author> authors(Library model) {
		return StreamSupport.stream(model.getAllBooksSortedByAuthors().spliterator(), false)
		.map((Book b) -> b.getAuthors())
		.flatMap(a -> a.stream()).distinct();
	}

	/**
	 * Visual test for serializing, dumping and reparsing the lib
	 * 
	 * @param model
	 * @throws IOException
	 */
	private static void serializeAndReparseVisual(Library model)
			throws IOException, FileNotFoundException, ClassNotFoundException {
		File fileobj = new File("lib.dta");
		fileobj.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileobj));
		oos.writeObject(model.getAllBooks());
		oos.close();

		ObjectInputStream ols = new ObjectInputStream(new FileInputStream("lib.dta"));
		Library reparse = new Library();
		@SuppressWarnings("unchecked")
		Iterable<Book> reparseIT = (Iterable<Book>) ols.readObject();
		reparseIT.forEach(b -> reparse.addBook(b));
		ols.close();

		System.out.println("ORIGINAL BOOKZ");
		for (Book book : model.getAllBooks()) {
			System.out.println(book);
		}
		System.out.println();
		System.out.println("REPARSED BOOKZ");
		for (Book book : reparse.getAllBooks()) {
			System.out.println(book);
		}
	}

	/**
	 * Visual test for dumping and reparsing the lib
	 * 
	 * @param model
	 * @throws IOException
	 */
	private static void dumpAndParseVisual(Library model) throws IOException {
		Library reparse;
		LibraryIO.dumpToFile(model, LIBRARY_FILE);
		reparse = LibraryIO.readFromFile(LIBRARY_FILE);
		Iterable<Book> modelIt = model.getAllBooks();
		Iterable<Book> reparseIt = reparse.getAllBooks();

		System.out.println("ORIGINAL BOOKZ");
		for (Book book : modelIt) {
			System.out.println(book);
		}
		System.out.println();
		System.out.println("REPARSED BOOKZ");
		for (Book book : reparseIt) {
			System.out.println(book);
		}
	}

	private static void testCorrectionsI(Library model) {
		System.out.println("CORRECTIONS");
		System.out.println("ISBN");
		Book randiii = model.getAllBooks().iterator().next();
		System.out.println(randiii);
		System.out.println();
		for (Book b : model.getAllBooks())
			System.out.println(b);
		model.correctBookISBN(randiii.getISBN(), randiii.getISBN() + 1);
		System.out.println();
		for (Book b : model.getAllBooks())
			System.out.println(b);
		model.selfTestOK();
	}

	private static void testRemovingsI(Library model) {
		Book rand = model.getAllBooks().iterator().next();
		System.out.println("REMOVE I");
		System.out.println(rand);
		model.remove(rand);

		System.out.println("BY AUTHORS");
		for (Book b : model.getAllBooksSortedByAuthors())
			System.out.println(b);
		model.selfTestOK();

		Book randii = model.getAllBooks().iterator().next();
		System.out.println("REMOVE II");
		System.out.println(randii);
		model.remove(randii);

		System.out.println("BY AUTHORS");
		for (Book b : model.getAllBooksSortedByAuthors())
			System.out.println(b);
		model.selfTestOK();
	}

	private static void testSortings(Library model) {
		System.out.println("BY HASH");
		for (Book b : model.getAllBooks())
			System.out.println(b);

		System.out.println("BY TITLE");
		for (Book b : model.getAllBooksSortedByTitle())
			System.out.println(b);

		System.out.println("BY AUTHORS");
		for (Book b : model.getAllBooksSortedByAuthors())
			System.out.println(b);

		System.out.println("BY DATE");
		for (Book b : model.getAllBooksSortedByEditionDate())
			System.out.println(b);

		System.out.println("BY PUBLISHER");
		for (Book b : model.getAllBooksSortedByPublisherNames())
			System.out.println(b);

		System.out.println("BY COUNTRY");
		for (Book b : model.getAllBooksSortedByPublisherCountries())
			System.out.println(b);

		System.out.println("BY PRICE");
		for (Book b : model.getAllBooksSortedByPrice())
			System.out.println(b);
	}

}
