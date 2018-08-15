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
import extension.LibraryExtended;
import model.BookFieldNames;

public class TestAuto {
	
	/*
	 * Testing strategy 
	 * The LibraryExtended class implements selftest that checks the integrity of its data
	 * We partition the input of Books: 0, 1, 2 and > 2; 
	 * We test that after each add/removal/sorting the LibraryExtended preserves its integrity;
	 * We test contains() and size() to make sure that the LibraryExtended behaves as expected
	 * We test that the sorting works
	 */
	
	private static final Author AUTHOR1 = new Author ("Jackob", "Ford");
	private static final Author AUTHOR2 = new Author ("Thomas", "Moor");
	
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
	
	
	/**
	 * Assertions must be enabled
	 */
	@Test(expected=AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}
	
	/**
	 * Empty work correctly
	 */
	@Test
	public void testEmpty() {
		LibraryExtended model = new LibraryExtended();
		assertTrue(model.isEmpty());
	}
	
	private void runAllSorts(LibraryExtended model) {
		model.getAllBooks();
		model.getAllBooksSortedBy(BookFieldNames.AUTHOR);
		model.getAllBooksSortedBy(BookFieldNames.EDITION);
		model.getAllBooksSortedBy(BookFieldNames.PRICE);
		model.getAllBooksSortedBy(BookFieldNames.PUBLISHER_COUNTRY);
		model.getAllBooksSortedBy(BookFieldNames.PUBLISHER_NAME);
		model.getAllBooksSortedBy(BookFieldNames.TITLE);
	}
	
	/**
	 * add works correctly; 
	 * partition: 1 book
	 */
	@Test
	public void testAdd() {
		LibraryExtended model = new LibraryExtended();
		for (int i = 0; i< 1000 ;i++) model.addBook(BOOK1);
		assertTrue(model.contains(BOOK1));
		assertTrue(model.size() == 1);
		runAllSorts(model);
		assertTrue(model.selfTestOK());
	}
	
	/**
	 * remove works correctly
	 * partition: 1 book
	 */
	@Test
	public void testRemove() {
		LibraryExtended model = new LibraryExtended();
		model.addBook(BOOK1);
		model.remove(BOOK1);
		assertTrue(model.size() == 0);
		assertTrue(model.isEmpty());
		assertTrue(model.selfTestOK());
	}
	

	/**
	 * add works correctly; 
	 * partition: 2 books
	 */
	@Test
	public void testAddII() {
		LibraryExtended model = new LibraryExtended();
		for (int i = 0; i< 100 ;i++) model.addBook(BOOK1);
		for (int i = 0; i< 100 ;i++) model.addBook(BOOK2);
		assertTrue(model.contains(BOOK1));
		assertTrue(model.contains(BOOK2));
		assertTrue(model.size() == 2);
		runAllSorts(model);
		assertTrue(model.selfTestOK());
	}
	
	/**
	 * remove works correctly
	 * partition: 2 books
	 */
	@Test
	public void testRemoveII() {
		LibraryExtended model = new LibraryExtended();
		model.addBook(BOOK1);
		model.addBook(BOOK2);
		model.remove(BOOK1);
		assertTrue(model.size() == 1);
		assertFalse(model.isEmpty());
		assertTrue(model.selfTestOK());
	}
	
	private static final Book BOOK3 = new Book(ISBN1+1, AUTHORS23, TITLE1, PUBLISHER1, DATE1, PRICE1);
	
	private static final Set<Book> set10(){
		Set<Book> sb = new HashSet<Book>(); 
		for (int i =1 ; i <= 10 ; i++) {
			sb.add(new Book(ISBN1+i, AUTHORS23, TITLE1, PUBLISHER1, DATE1, PRICE1));
		}
		return sb;
	}
	
	private static final Set<Book> set20(){
		Set<Book> sb = new HashSet<Book>(); 
		for (int i =1 ; i <= 20 ; i++) {
			sb.add(new Book(ISBN1+i, AUTHORS23, TITLE1, PUBLISHER1, DATE1, PRICE1));
		}
		return sb;
	}
	
	/**
	 * addAll works correctly
	 */
	@Test
	public void testAddAll() {
		LibraryExtended model = new LibraryExtended();
		model.addAll(set10());
		model.addAll(set20());
		assertFalse(model.contains(BOOK1));
		assertTrue(model.contains(BOOK3));
		assertTrue(model.containsAll(set10()));
		assertTrue(model.containsAll(set20()));
		assertTrue(model.size() == 20);
		runAllSorts(model);
		assertTrue(model.selfTestOK());
	}
	
	/**
	 * RemoveAll works correctly
	 */
	@Test
	public void testRemoveAll() {
		LibraryExtended model = new LibraryExtended();
		model.addAll(set20());
		runAllSorts(model);
		assertTrue(model.selfTestOK());
		model.removeAll(set10());
		assertTrue(model.size() == 10);
		runAllSorts(model);
		assertTrue(model.selfTestOK());
	}
	
	/**
	 * Testing that the sorting, then removing, then sorting again works
	 */
	@Test
	public void testMoneySort() {
		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		Book bkMiddle = null;
		for (int i = 0; i < 1000; i++) {
			Book rbk = Book.getRandomBook();
			lib.add(rbk);
			if (i == 500) bkMiddle = rbk;
		}
		LibraryExtended model = new LibraryExtended();
		for (Book b : lib)
			model.addBook(b);
		moneySortCheck(model);
		model.removeAll(lib.tailSet(bkMiddle));
		moneySortCheck(model);
		assertTrue(model.selfTestOK());
	}

	private void moneySortCheck(LibraryExtended model) {
		Iterable<Book> alp = model.getAllBooksSortedByPrice();
		double priceStart = alp.iterator().next().getPrice();
		for (Book b : alp) {
			assertTrue(b.getPrice() >= priceStart);
			priceStart = b.getPrice();
		}
	}

}
