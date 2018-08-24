package test;
import java.util.stream.Stream;

import dao.Book;

public class FlatMapDemo {
	public static void main(String[] args) {
		Stream.generate(Book::getRandomBook)
		.limit(20)
		.flatMap(b -> b.getAuthors().stream())
		.distinct().sorted().forEach(System.out::println);	
	}
}
