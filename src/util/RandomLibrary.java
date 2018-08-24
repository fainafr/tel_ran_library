package util;

import java.time.LocalDate;
import java.util.Random;
import java.util.TreeSet;

import comparators.BookGeneralComparator;
import dao.Book;
import dao.Countries;
import model.Library;

public class RandomLibrary {
	
	public static Random gen = new Random();
	private static String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static int alphabetLength = alphabet.length();
	private static Countries[] countries = Countries.values();
	private static final int MODELMAXBOOKS = 4;
	
	public static int nextIntRange(int min, int max){
		return  min + gen.nextInt(max - min + 1);
	}
	
	public static double nextDoubleRange(double min, double max){
		return min + gen.nextDouble()*(max-min);
	}
	
	public static boolean booleanWithProbability(double probability){
		return gen.nextDouble() < probability;
	}
	
	public static String nextRandomString(int len){
		if (len <= 0) return "";
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<len; i++)sb.append(alphabet.charAt(gen.nextInt(alphabetLength)));
		return sb.toString();
	}
	
	public static Countries getRandomCountry(){
		
		double random = gen.nextDouble();
		
		double probability = countries[0].getProbability();
		for (int i=0; i<countries.length;){
			if (random < probability) return countries[i];
			else probability += countries[++i].getProbability();
		}
		return null;
	}
	
	public static LocalDate getRandomDate(LocalDate from, LocalDate to){
		return LocalDate.ofEpochDay((long)nextIntRange((int)from.toEpochDay(), (int)to.toEpochDay()));
	}
	
	
	/**
	 * @return new random library
	 */
	public static Library randomModel() {
		return randomModel(MODELMAXBOOKS);
	}
	
	/**
	 * @return new random library
	 */
	public static Library randomModel(int maxBooks) {
		TreeSet<Book> lib = new TreeSet<>(BookGeneralComparator.getInstance());
		for (int i = 0; i < maxBooks; i++)
			lib.add(Book.getRandomBook());
		Library model = new Library();
		for (Book b : lib)
			model.addBook(b);
		return model;
	}

}
