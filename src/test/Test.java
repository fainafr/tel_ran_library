package test;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.Author;
import dao.Book;
import extension.LibraryExtended;
import model.BookFieldNames;
import model.LibraryBasic;

public class Test {
	public static void main(String[] args) {
		LibraryExtended lib = new LibraryExtended();
		lib.fillRandomLibrary(100);
		ArrayList<Author> ala = new ArrayList<>();
		ala.add(new Author("John","Johnson"));
		ala.add(new Author("John","Smith"));
		ala.add(new Author("John","Fox"));
		for (Book book : lib.getBooksPrintedAfter(1970))System.out.println(book);
	}
}
