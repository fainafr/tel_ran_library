package dao;

import java.util.Set;
import java.util.TreeSet;

import util.RandomLibrary;

public class Author implements Comparable<Author>{

	public static final String DEFAULT_FIRST_NAME = "no fName";
	public static final String DEFAULT_LAST_NAME = "no lName";
	
	//private static final String FIRST_NAME_PREFIX = "fName";
	//private static final String LAST_NAME_PREFIX = "lName";
	
	//private static final Comparator<Author> authorComparator = new AuthorComparator();
	
	private String firstName;
	private String lastName;
	
	public Author(String firstName, String lastName) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
	}

	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	
	public void setFirstName(String firstName) {this.firstName = firstName == null ? DEFAULT_FIRST_NAME : firstName;}
	public void setLastName(String lastName) {this.lastName = lastName == null ? DEFAULT_LAST_NAME : lastName;}
	
	public static Author getEmptyAuthor(){return new Author(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME);}
	public static Set<Author> getEmptyAuthors(){return new TreeSet<Author>();}
		
	//private static final int NUMBER_OF_AUTHOR_FIRST_NAMES = 10;	
	//private static final int NUMBER_OF_AUTHOR_LAST_NAMES = 10;
	private static final String[] FIRSTNAMES = 
		{"John", "Robert", "Jackob", "Thomas", "Edward","William","Henry","George","Gregory","Charles"};
	private static final String[] LASTNAMES = 
		{"Johnson", "Smith", "Lee", "Linn", "Fox","Simpson","Ford","Piper","Moor","Philips"};
	private static final int AUTHOR_LIST_MAX_LENGTH = 3;
	
	public static Author getRandomAuthor(){
		//String authorFirstName = FIRST_NAME_PREFIX+ "#"+ RandomLibrary.gen.nextInt(NUMBER_OF_AUTHOR_FIRST_NAMES);
		//String authorLastName = LAST_NAME_PREFIX+ "#"+ RandomLibrary.gen.nextInt(NUMBER_OF_AUTHOR_LAST_NAMES);
		String authorFirstName = FIRSTNAMES[RandomLibrary.gen.nextInt(FIRSTNAMES.length)];
		String authorLastName = LASTNAMES[RandomLibrary.gen.nextInt(LASTNAMES.length)];
		return new Author(authorFirstName, authorLastName);
	}
	
	public static Set<Author> getRandomAuthors(){
		int numAuthors = RandomLibrary.nextIntRange(1, AUTHOR_LIST_MAX_LENGTH);
		Set<Author> authors = new TreeSet<Author>();
		for (int i=0; i < numAuthors; i++) authors.add(getRandomAuthor());
		return authors;
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	@Override
	public int compareTo(Author other) {
		int c = firstName.compareTo(other.firstName);
		return c == 0 ? lastName.compareTo(other.lastName): c;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Author other = (Author) obj;
		if (firstName!= other.getFirstName())
			return false;
		if (lastName!= other.getLastName())
			return false;
		
		return true;
	}
}
