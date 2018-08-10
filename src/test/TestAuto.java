package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import comparators.BookGeneralComparator;
import dao.Author;
import dao.Book;
import dao.Countries;
import dao.Publisher;
import model.Library;

public class TestAuto {

	/*
	 * Testing strategy The library class implements selftest that checks the
	 * integrity of its data We partition the input of Books: 0, 1, 2 and > 2;
	 * We test that after each add/removal/sorting the library preserves its
	 * integrity; We test contains() and size() to make sure that the library
	 * behaves as expected We test that the sorting works
	 */

	private static final Author AUTHOR1 = new Author("Jackob", "Ford");
	private static final Author AUTHOR2 = new Author("Thomas", "Moor");

	private static final String TITLE1 = "Love and Hate";
	private static final String TITLE2 = "Church and Yoghurt";

	private static final Publisher PUBLISHER1 = new Publisher(Countries.USA, "Star");
	private static final Publisher PUBLISHER2 = new Publisher(Countries.Germany, "Sun");

	private static final long ISBN1 = 1112223334440l;
	private static final long ISBN2 = 2223334445550l;

	private static final LocalDate DATE1 = LocalDate.of(2016, Month.JANUARY, 01);
	private static final LocalDate DATE2 = LocalDate.of(2018, Month.JANUARY, 01);

	private static final double PRICE1 = 149.99;
	private static final double PRICE2 = 256.00;

	private static final Set<Author> AUTHORS12 = new HashSet<>(Arrays.asList(AUTHOR1, AUTHOR2));
	private static final Set<Author> AUTHORS23 = new HashSet<>(Arrays.asList(AUTHOR1, AUTHOR2));

	private static final Book BOOK1 = new Book(ISBN1, AUTHORS12, TITLE1, PUBLISHER1, DATE1, PRICE1);
	private static final Book BOOK2 = new Book(ISBN2, AUTHORS23, TITLE2, PUBLISHER2, DATE2, PRICE2);

	private void runAllSorts(Library model) {
		model.getAllBooks();
		model.getAllBooksSortedByAuthors();
		model.getAllBooksSortedByEditionDate();
		model.getAllBooksSortedByPrice();
		model.getAllBooksSortedByPublisherCountries();
		model.getAllBooksSortedByPublisherNames();
		model.getAllBooksSortedByTitle();
	}

	/**
	 * Testing that the sorting, then removing, then sorting again works
	 */
	private static final int SORTITERATIONS = 20;


	@Test
	public void testAuthorst() {

		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		Book bkMiddle = null;
		for (int i = 0; i < SORTITERATIONS; i++) {
			Book rbk = Book.getRandomBook();
			lib.add(rbk);
			if (i == SORTITERATIONS / 2)
				bkMiddle = rbk;
		}
		Library model = new Library();
		for (Book b : lib)
			model.addBook(b);
		model.getAllBooksSortedByAuthors();
		model.displayAuthors();
		System.out.println("MODEL_SIZE= "+model.size());
		System.out.println();
		// System.out.println("Removing");
		for (Book b : lib.tailSet(bkMiddle)){
			model.removeAll(lib.tailSet(bkMiddle));
		}
		// System.out.println("============");
		model.displayAuthors();
		System.out.println("MODEL_SIZE= "+model.size());
		System.out.println();
		for (Book b : model.getAllBooks()){
			System.out.println(b.getISBN()+" "+b.getAuthors());
		}
		assertTrue(model.selfTestOK());
	}

}
