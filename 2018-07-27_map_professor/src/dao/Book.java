package dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

import util.RandomLibrary;

public class Book {
	
	public static final long DEFAULT_ISBN = 0;
	public static final Set<Author> DEFAULT_AUTHORS = Author.getEmptyAuthors();
	public static final String DEFAULT_TITLE = "no title";
	public static final Publisher DEFAULT_PUBLISHER = Publisher.getEmptyPublisher();
	public static final LocalDate DEFAULT_EDITION_DATE = LocalDate.of(0, 1, 1);
	public static final double DEFAULT_PRICE = 0.;
	
	private static final String TITLE_PREFIX = "title";
	
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy", new Locale("us"));
	
	
	private long ISBN;
	private Set<Author> authors;
	private String title;
	private Publisher publisher;
	private LocalDate edition;
	private double price;
	
	public Book(long iSBN, Set<Author> authors, String title, Publisher publisher, LocalDate edition, double price) {
		super();
		setISBN(iSBN);
		setAuthors(authors);
		setTitle(title);
		setPublisher(publisher);
		setEdition(edition);
		setPrice(price);
	}
	
	public long getISBN() {	return ISBN;}
	public Set<Author> getAuthors() {return authors;}
	public String getTitle() {return title;}
	public Publisher getPublisher() {return publisher;}
	public LocalDate getEdition() {return edition;}
	public double getPrice() {return price;}


	public void setISBN(long iSBN) {this.ISBN = iSBN <= 0 ? DEFAULT_ISBN : iSBN;}
	public void setAuthors(Set<Author> authors) {this.authors = authors == null ? DEFAULT_AUTHORS : authors;}
	public void setTitle(String title) {this.title = title == null ? DEFAULT_TITLE  : title;}
	public void setPublisher(Publisher publisher) {	this.publisher = publisher == null ? DEFAULT_PUBLISHER : publisher;}
	public void setEdition(LocalDate edition) {this.edition = edition == null ? DEFAULT_EDITION_DATE : edition;}
	public void setPrice(double price) {this.price = price < 0. ? DEFAULT_PRICE : price;}

	public static Book getEmptyBook(){return new Book(DEFAULT_ISBN,
														DEFAULT_AUTHORS,
														DEFAULT_TITLE,
														DEFAULT_PUBLISHER,
														DEFAULT_EDITION_DATE,
														DEFAULT_PRICE);}
	
	private static final long OLDEST_BOOK_AGE = 100;
	private static final double MIN_BOOK_PRICE = 100.;
	private static final double MAX_BOOK_PRICE = 1000.;
	private static final int NUM_BOOK_TITLES = 10;
	
	public static Book getRandomBook(){
		
		long bookISBN = Math.abs(RandomLibrary.gen.nextLong())%10000000000000l;
		while(bookISBN < 1000000000000l)bookISBN *= 10l;
		
		LocalDate now = LocalDate.now();
		LocalDate oldest = now.minusYears(OLDEST_BOOK_AGE);
		
		return new Book(
				bookISBN,
				Author.getRandomAuthors(),
				TITLE_PREFIX+"#"+RandomLibrary.gen.nextInt(NUM_BOOK_TITLES),
				Publisher.getRandomPublisher(),
				RandomLibrary.getRandomDate(oldest, now),
				RandomLibrary.nextDoubleRange(MIN_BOOK_PRICE, MAX_BOOK_PRICE)
			);
	}

	@Override
	public String toString() {
		return "Book:<"+ ISBN + "; "+ authors + "; " + title + "; " + publisher
				+ "; " + edition.format(dtf) + "; " + String.format("%.2f", price)+">";
	}
}
