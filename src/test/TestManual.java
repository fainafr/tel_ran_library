package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import IO.LibraryIO;
import dao.Book;
import model.Library;
import util.RandomLibrary;

public class TestManual {

	private static final String LIBRARY_FILE = "Library.txt";

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Library model = RandomLibrary.randomModel(4);

		serializeAndReparseVisual(model);
		
		dumpAndParseVisual(model);
		
		testRemovingsI(model);

		testCorrectionsI(model);

		testSortings(model);
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
