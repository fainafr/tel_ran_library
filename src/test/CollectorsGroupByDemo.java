package test;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dao.Book;

public class CollectorsGroupByDemo {

	public static void main(String[] args) {
		System.out.println("    DEFAULT GROUPBY");
		groupByDefault();
		System.out.println("    DEFAULT DOWNSTREM GROUPBY");
		groupByDefaultDownstream();
		System.out.println("    FULL GROUPBY");
		groupByFullDemo();
		System.out.println("    COUNT USING REFS TO HACK LAMBDA FINAL");
		groupCount();

	}

	/**
	 * Using reference types to trick lambda "effectively final" restriction
	 */
	private static void groupCount() {
		ArrayList<Integer> ali = new ArrayList<>();
		ali.add(0);
		Stream.generate(Book::getRandomBook)
		.limit(30)
		.peek((b)->ali.set(0,  ali.get(0)+1))
		.forEach(b->{});
		System.out.println(ali);
	}
	
	
	/**
	 * Demo of groupBy: key; where to put right part; what to do with right part
	 */
	private static void groupByFullDemo() {
		Stream.generate(Book::getRandomBook)
		.limit(30)
		.collect(Collectors.groupingBy((Book b) -> b.getTitle(), TreeMap::new, Collectors.counting()))
		.entrySet()
		.forEach(System.out::println);;
	}
	
	
	/**
	 * Demo of groupBy: key, downstream operation;
	 * Puts in hashmap and returns it
	 */
	private static void groupByDefaultDownstream() {
		Stream.generate(Book::getRandomBook)
		.limit(30)
		.collect(Collectors.groupingBy((Book b) -> b.getTitle(), Collectors.counting()))
		.entrySet()
		.forEach(System.out::println);;
	}
	
	/**
	 * Demo of groupBy: key; 
	 * Puts in hashmap and returns it
	 */
	private static void groupByDefault() {
		Stream.generate(Book::getRandomBook)
		.limit(30)
		.collect(Collectors.groupingBy((Book b) -> b.getTitle()))
		.entrySet()
		.forEach(System.out::println);;
	}

}
