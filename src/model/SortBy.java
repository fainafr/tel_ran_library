package model;

import java.time.LocalDate;
import java.util.Set;

import dao.Author;
import dao.Book;
import dao.Publisher;

public enum SortBy {
	AUTHOR {
		public Set<Author> getKey(Book book) {
			return book.getAuthors();
		}
	},
	TITLE {
		public String getKey(Book book) {
			return book.getTitle();
		}
	},
	PUBLISHER {
		public Publisher getKey(Book book) {
			return book.getPublisher();
		}
	},
	PUBCOUNTRY {
		public String getKey(Book book) {
			return book.getPublisher().getCountry().name();
		}
	},
	EDITION {
		public LocalDate getKey(Book book) {
			return book.getEdition();
		}
	},
	PRICE {
		public Double getKey(Book book) {
			return book.getPrice();
		}
	},;

	public abstract Object getKey(Book book);

}