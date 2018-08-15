package model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import dao.Author;
import dao.Book;
import dao.Publisher;
import mapofmaps.DaoField;
import model.BookFieldNames;
import multimap.CollectionEnum;
import multimap.MapEnum;
import multimap.MultiHashMapTree;
import multimap.MultiMap;
import multimap.MultiTreeMapTree;
import dao.Countries;

public class BookFieldsMap {
	
	public static HashMap<BookFieldNames, DaoField<Book>> fieldsMap = new HashMap<BookFieldNames, DaoField<Book>>();
	
	static {
		
		/*fieldsMap.put(BookFieldNames.ISBN, 
				new DaoField<Book>((Function<Book,Long>)(Book book)->book.getISBN(), (Book book, Object isbn)->book.setISBN((Long)isbn), false));*/
		fieldsMap.put(BookFieldNames.AUTHOR, 
				new DaoField<Book>((Function<Book,Set<Author>>)(Book book)-> book.getAuthors(), (Book book, Object authors)->book.setAuthors((Set<Author>)authors)));
		fieldsMap.put(BookFieldNames.TITLE, 
				new DaoField<Book>((Function<Book,String>)(Book book)->book.getTitle(), (Book book, Object title)->book.setTitle((String)title)));
		fieldsMap.put(BookFieldNames.PUBLISHER, 
				new DaoField<Book>((Function<Book,Publisher>)(Book book)->book.getPublisher(), (Book book, Object publisher)->book.setPublisher((Publisher)publisher)));
		fieldsMap.put(BookFieldNames.PUBLISHER_NAME, 
				new DaoField<Book>((Function<Book,String>)(Book book)->book.getPublisher().getName(), (Book book, Object pName)->book.getPublisher().setName((String)pName)));
		fieldsMap.put(BookFieldNames.PUBLISHER_COUNTRY, 
				new DaoField<Book>((Function<Book,String>)(Book book)->book.getPublisher().getCountry().name(), (Book book, Object country)->book.getPublisher().setCountry((Countries)country)));
		fieldsMap.put(BookFieldNames.EDITION, 
				new DaoField<Book>((Function<Book,LocalDate>)(Book book)->book.getEdition(), (Book book, Object edition)->book.setEdition((LocalDate)edition)));
		fieldsMap.put(BookFieldNames.PRICE, 
				new DaoField<Book>((Function<Book,Double>)(Book book)->book.getPrice(), (Book book, Object price)->book.setPrice((Double)price)));
		
		setEmptyMaps();
		
	}
	
	public static void setEmptyMaps() {
		//fieldsMap.get(BookFieldNames.ISBN).setMap(new MultiMap<Long, Book>(MapEnum.HASHMAP, CollectionEnum.ARRAYLIST));
		fieldsMap.get(BookFieldNames.AUTHOR).setMap(new MultiTreeMapTree<Author,Book>());
		fieldsMap.get(BookFieldNames.TITLE).setMap(new MultiTreeMapTree<String,Book>());
		fieldsMap.get(BookFieldNames.PUBLISHER).setMap(new MultiHashMapTree<Publisher,Book>());
		fieldsMap.get(BookFieldNames.PUBLISHER_NAME).setMap(new MultiTreeMapTree<String,Book>());
		fieldsMap.get(BookFieldNames.PUBLISHER_COUNTRY).setMap(new MultiTreeMapTree<String,Book>());
		fieldsMap.get(BookFieldNames.EDITION).setMap(new MultiTreeMapTree<LocalDate,Book>());
		fieldsMap.get(BookFieldNames.PRICE).setMap(new MultiTreeMapTree<Double,Book>());
	}
}
