package util;

import java.time.LocalDate;

import dao.Author;
import dao.Book;
import dao.Publisher;

/**
 * Class that provides enums for Library sorting operations
 */
public enum BookSortWays {
	AUTHOR {
		public BookKey <Author> getKey(Book book) {
			return BookKey.AuthorBookKey(book.getAuthors().iterator().next());
		}
	},
	TITLE {
		public BookKey<String> getKey(Book book) {
			return BookKey.StringBookKey(book.getTitle());
		}
	},
	PUBLISHER {
		public BookKey<Publisher> getKey(Book book) {
			return  BookKey.PublisherBookKey(book.getPublisher());
		}
	},
	PUBCOUNTRY {
		public BookKey<String> getKey(Book book) {
			return BookKey.StringBookKey(book.getPublisher().getCountry().name());
		}
	},
	EDITIONDATE {
		public BookKey<LocalDate> getKey(Book book) {
			return BookKey.LocalDateBookKey(book.getEdition());
		}
	},
	PRICE {
		public BookKey<Double> getKey(Book book) {
			return BookKey.DoubleBookKey(book.getPrice());
		}
	};

	public abstract BookKey<?> getKey (Book book);


}