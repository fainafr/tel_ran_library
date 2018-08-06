package util;

import java.time.LocalDate;

import dao.Author;
import dao.Publisher;


/**
 * Type safety class for Library
 * @param <T> any type that the Book class returns
 */
public class BookKey<T extends Comparable<? super T>> implements Comparable<BookKey<T>>{
	
	private T data;
	public T getData() {
		return data;
	}
	public BookKey(T value) {
		this.data = value;
	} 
	public static BookKey<Author> AuthorBookKey(Author value){
		return new BookKey<Author>(value);
	}
	public static BookKey<Publisher> PublisherBookKey(Publisher value){
		return new BookKey<Publisher>(value);
	}
	public static BookKey<String> StringBookKey(String value){
		return new BookKey<String>(value);
	}
	public static BookKey<Double> DoubleBookKey(Double value){
		return new BookKey<Double>(value);
	}
	public static BookKey<LocalDate> LocalDateBookKey(LocalDate value){
		return new BookKey<LocalDate>(value);
	}
	
	@Override
	public int compareTo(BookKey<T> other) {
		return this.data.compareTo(other.getData());
	}
}