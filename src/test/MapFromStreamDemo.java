package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


import dao.Author;
import dao.Book;
import model.Library;
import multimap.MultiMap;
import util.RandomLibrary;

public class MapFromStreamDemo {

	/**
	 *Class that demonstrates use of Collectors;
	 */
	public static void main(String[] args)  {
		Library model = RandomLibrary.randomModel(4);
		System.out.println("Displaying By Author");
		MultiMap.display(multiMapAuthorsCollector(model));
		System.out.println();
		System.out.println("Displaying By Book - all prices");
		printAllPricesForEachBook();
	}

	public static void printAllPricesForEachBook() {
		Stream.generate(Book::getRandomBook).limit(30).collect(Collectors.toMap(b -> b.getTitle(), b -> {
			TreeSet<Double> tsp = new TreeSet<>();
			tsp.add(b.getPrice());
			return tsp;
		}, (x, y) -> {
			x.addAll(y);
			return x;
		}, TreeMap::new)).entrySet().forEach(x -> System.out.println(x.getKey()+" : "+x.getValue()));
	}

	/**
	 * Prints each author in the library and the books credited to him.
	 * 
	 * @param model
	 *            library
	 */
	public static void printByAuthors(Library model) {
		authors(model).peek(a -> System.out.println(a.toString() + ": ")) // From
																			// each
																			// author..
				.map(a -> model.getBooksByAuthor(a)) // to Iterable..
				.flatMap(a -> StreamSupport.stream(a.spliterator(), false)) // To
																			// stream..
				.forEach(b -> System.out.print(b.toShortString())); // Custom
																	// print
	}

	/**
	 * @param model
	 *            Library
	 * @return multimap of books by author
	 */
	public static Map<Author, Set<Book>> multiMapAuthors(Library model) {
		Map<Author, Set<Book>> multiAuthors = new HashMap<>();
		authors(model).forEach(a -> multiAuthors.put(a, createSet(model.getBooksByAuthor(a))));
		return multiAuthors;
	}

	/**
	 * @param model
	 *            Library
	 * @return multimap of books by author using Collector
	 */
	public static Map<Author, Set<Book>> multiMapAuthorsCollector(Library model) {
		return authors(model).collect(Collectors.toMap((Author a) -> a, a -> createSet(model.getBooksByAuthor(a))));
	}

	/**
	 * @param booksByAuthor
	 *            Iterable
	 * @return Set from this iterable
	 */
	private static Set<Book> createSet(Iterable<Book> booksByAuthor) {
		return StreamSupport.stream(booksByAuthor.spliterator(), false).collect(Collectors.toSet());
	}

	/**
	 * @param model
	 *            library
	 * @return Stream of authors in the model
	 */
	private static Stream<Author> authors(Library model) {
		return StreamSupport.stream(model.getAllBooks().spliterator(), false).map((Book b) -> b.getAuthors())
				.flatMap((Set<Author> a) -> a.stream()).map((Author a) -> a).distinct();
	}

}
