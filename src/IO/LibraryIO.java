package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import dao.Author;
import dao.Book;
import dao.Countries;
import dao.Publisher;
import model.Library;

public class LibraryIO {

	private static final int PUBSTART = 13;

	/**
	 * Dumps any library to "Library.txt" in the root
	 * 
	 * @param lib
	 *            any library
	 * @throws IOException
	 */
	public static void dumpToFile(Library lib, String fileName) throws IOException {

		File file = new File(fileName);
		file.createNewFile();
		PrintWriter filewriter = null;
		filewriter = new PrintWriter(fileName);
		// overwrites
		for (Book book : lib.getAllBooks())
			filewriter.println(book.toString());
		// apply
		filewriter.flush();
		filewriter.close();
	}

	/**
	 * Dumps any library to "Library.txt" in the root
	 * 
	 * @param lib
	 *            any library
	 * @throws IOException
	 */
	public static Library readFromFile(String fileName) throws IOException {

		Library lib = new Library();
//		System.out.println(parseFromString(
//				"Book: 8410407527469; author(s): [George Philips, Moris Druon, Alex Sominsky]; 'Bread and Stone'; publisher: [Yellow Sea, USA]; 12.12.1949; 102.05"));
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new FileReader(fileName));
			String bookRec;
			while ((bookRec = inputStream.readLine()) != null) {
				lib.addBook(parseFromString(bookRec));
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return lib;
	}

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy", new Locale("us"));

	private static Book parseFromString(String bookStr) {
		long ISBN;
		Set<Author> authors;
		String title;
		Publisher publisher;
		LocalDate edition;
		double price;

		List<String> bookRec = new ArrayList<String>(Arrays.asList(bookStr.split(";")));
		ISBN = getISBN(bookRec);
		authors = parseAuthors(bookRec);
		title = parseTitle(bookRec);
		publisher = parsePublisher(bookRec);
		edition = parseEdition(bookRec);
		price = Double.parseDouble(bookRec.get(5));

		return new Book(ISBN, authors, title, publisher, edition, price);

	}

	/**
	 * 
	 * @param bookRec
	 * @return
	 */
	private static String parseTitle(List<String> bookRec) {
		String title;
		title = cropLeadingSpace(bookRec.get(2));
		title = title.substring(1, title.length() - 1);
		return title;
	}

	/**
	 * @param bookRec
	 * @return
	 */
	private static LocalDate parseEdition(List<String> bookRec) {
		LocalDate edition;
		String dateString = bookRec.get(4);
		dateString = cropLeadingSpace(dateString);
		edition = LocalDate.parse(dateString, dtf);
		return edition;
	}

	/**
	 * @param bookRec
	 * @return
	 */
	private static Publisher parsePublisher(List<String> bookRec) {
		Publisher publisher;
		String pubString = bookRec.get(3).substring(PUBSTART, bookRec.get(3).length() - 1);
		int lastComma = pubString.lastIndexOf(',');
		Countries pubCountry = Countries.valueOf(pubString.substring(lastComma + 2));
		publisher = new Publisher(pubCountry, pubString.substring(0, lastComma));
		return publisher;
	}

	/**
	 * 
	 * @param bookRec
	 * @return
	 */
	private static long getISBN(List<String> bookRec) {
		long ISBN;
		String ISBNstr = bookRec.get(0);
		int lastSpace = ISBNstr.lastIndexOf(' ');
		try {
			ISBN = Long.parseLong(cropLeadingSpace(ISBNstr.substring(lastSpace)));
		} catch (NumberFormatException e) {
			ISBN = Book.DEFAULT_ISBN;
		}
		return ISBN;
	}

	private static final int AUTHORSTART = 13;

	/**
	 * @param authors
	 * @param bookRec
	 */
	private static Set<Author> parseAuthors(final List<String> bookRec) {
		Set<Author> authors = new HashSet<Author>();
		String[] aStrings = bookRec.get(1).substring(AUTHORSTART, bookRec.get(1).length() - 1).split(",");
		List<String> aStrList = new ArrayList<String>(Arrays.asList(aStrings));
		String[] names = null;
		for (String aStr : aStrList) {
			aStr = cropLeadingSpace(aStr);
			names = aStr.split(" ");
			authors.add(new Author(names[0], names[1]));
		}
		return authors;
	}

	/**
	 * Crops leading space
	 * @param aStr
	 * @return
	 */
	private static String cropLeadingSpace(String aStr) {
		if (aStr.startsWith(" "))
			aStr = aStr.substring(1, aStr.length());
		return aStr;
	}
}
