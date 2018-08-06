package model;

import java.time.LocalDate;
import java.util.Set;

import dao.Author;
import dao.Book;
import dao.Publisher;

public enum SortBy {
	AUTHOR {
		public Author key(Book book) {
			//multi key is tough
			return null;
		}
	},
	TITLE {
		public String key(Book book) {
			return book.getTitle();
		}
	},
	PUBLISHER {
		public Publisher key(Book book) {
			return book.getPublisher();
		}
	},
	PUBCOUNTRY {
		public String key(Book book) {
			return book.getPublisher().getCountry().name();
		}
	},
	EDITION {
		public LocalDate key(Book book) {
			return book.getEdition();
		}
	},
	PRICE {
		public Double key(Book book) {
			return book.getPrice();
		}
	},;

	public abstract Object key(Book book);

}

// putToMultivalueMap(authorTM, a, book);}
// putToMultivalueMap(titleTM, book.getTitle(), book);
// putToMultivalueMap(publisherHM, book.getPublisher(), book);
// putToMultivalueMap(publisherNameTM, book.getPublisher().getName(), book);
// putToMultivalueMap(publisherCountryTM,
// book.getPublisher().getCountry().name(), book);
// putToMultivalueMap(editionTM, book.getEdition(), book);
// putToMultivalueMap(priceTM, book.getPrice(), book);
