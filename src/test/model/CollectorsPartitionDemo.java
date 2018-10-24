package model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import dao.Book;

public class CollectorsPartitionDemo {
	public static void main(String[] args){
		partitioningByDemo();
		partitioningByCountDemo();
	}

	/**
	 * Demostrates partitioningBy method in collectors; 
	 */
	private static void partitioningByDemo() {
		Stream.generate(Book::getRandomBook)
		.limit(30)
		.collect(Collectors.partitioningBy((Book b) -> b.getPrice() < 500.))
		.entrySet()
		.forEach(System.out::println);
	}
	private static void partitioningByCountDemo() {
		Stream.generate(Book::getRandomBook)
		.limit(30)
		.collect(Collectors.partitioningBy((Book b) -> b.getPrice() < 500., Collectors.counting()))
		.entrySet()
		.forEach(System.out::println);
	}
}
