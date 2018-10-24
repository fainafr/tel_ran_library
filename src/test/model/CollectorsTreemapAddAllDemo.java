package model;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dao.Book;

public class CollectorsTreemapAddAllDemo {

	/**
	 * Class that demonstrates use of Collectors;
	 */
	public static void main(String[] args) {
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
		}, TreeMap::new)).entrySet().forEach(x -> System.out.println(x.getKey() + " : " + x.getValue()));
	}

}
