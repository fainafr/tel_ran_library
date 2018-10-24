package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dao.Book;
import util.RandomLibrary;

public class SerializationTest {

	private static <E> List<E> arrayListFromIterable(Iterable<E> iter) {
		ArrayList<E> list = new ArrayList<E>();
		for (E item : iter) {
			list.add(item);
		}
		return list;
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		serializeBook();
		
		serializeBooks();

	}

	private static void serializeBook()
			throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {

		Library library = RandomLibrary.randomModel(1);
	
		Book book =  library.getAllBooks().iterator().next();
			System.out.println(book);
		
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		
		
        String json = mapper.writeValueAsString(book);
		System.out.println(json);
		
		
		Book restoredBook =
				mapper.readValue(json, Book.class);
		System.out.println(restoredBook);
		System.out.println("serialize Book ended");
	}

	private static void serializeBooks()
			throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
		Library library = RandomLibrary.randomModel(4);

		for (Book book : library.getAllBooks())
			System.out.println(book);

		List<Book> books = arrayListFromIterable(library.getAllBooks());

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		
		JavaType jt = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);

		ObjectWriter ow = mapper.writerFor(jt);
		String json = ow.writeValueAsString(books);
		System.out.println(json);

		ArrayList<Book> restoredBooks = mapper.readValue(json, jt);
		for (Book book : restoredBooks)
			System.out.println(book);
		System.out.println("serialize Books ended");
	}

}
