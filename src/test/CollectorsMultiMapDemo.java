package test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import dao.Author;
import dao.Book;
import model.Library;
import multimap.MultiMap;
import multimap.MultiMapAuthorKeyFiller;
import util.RandomLibrary;

public class CollectorsMultiMapDemo {
	
	private static MultiMapAuthorKeyFiller filler = new MultiMapAuthorKeyFiller();
	/**
	 * Class that demonstrates use of Collectors;
	 */
	public static void main(String[] args) {
		Library model = RandomLibrary.randomModel(4);
		System.out.println("Displaying By Author");
		MultiMap.display(multiMapAuthorsCollector(model));
		System.out.println("Displaying By Author II");
		MultiMap.display(getByAuthor(model.getAllBooks()));
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

	
	
	/**
	 * Optimised way of getting the Author, Set<Book> collection
	 * @param books iterable by Book
	 * @return Authors with Books they wrote
	 */
	private static Map<Author, Set<Book>> getByAuthor(Iterable<Book> books){
		Map<Author, Set<Book>> mb = new HashMap<>();
		StreamSupport.stream(books.spliterator(), false)
		.forEach((Book b) -> b.getAuthors().forEach(((Author a)->filler.putToIterableMap(mb, b, a))));
		return mb;
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
	 * @return multimap of books by author using Collector
	 */
	private static Map<Author, Set<Book>> multiMapAuthorsCollector(Library model) {
		return authors(model).collect(Collectors.toMap((Author a) -> a, a -> createSet(model.getBooksByAuthor(a))));
	}

	/**
	 * @param model
	 *            Library
	 * @return multimap of books by author
	 */
	//Legacy method that will be used in performance tests
	@SuppressWarnings("unused")
	private static Map<Author, Set<Book>> multiMapAuthors(Library model) {
		Map<Author, Set<Book>> multiAuthors = new HashMap<>();
		authors(model).forEach(a -> multiAuthors.put(a, createSet(model.getBooksByAuthor(a))));
		return multiAuthors;
	}
	
	/**
	 * @param booksByAuthor
	 *            Iterable
	 * @return Set from this iterable
	 */
	private static Set<Book> createSet(Iterable<Book> booksByAuthor) {
		return StreamSupport.stream(booksByAuthor.spliterator(), false).collect(Collectors.toSet());
	}

	
}
