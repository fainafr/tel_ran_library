package dao;

import util.RandomLibrary;

public class Publisher implements Comparable<Publisher>{

	public static final Countries DEFAULT_COUNTRY = Countries.unknown;
	public static final String DEFAULT_PUBLISHER_NAME = "no name";
	
	private static final String PUBLISHER_NAME_PREFIX = "pName";
	
	private Countries country;
	private String name;
	
	public Publisher(Countries country, String name) {
		super();
		setCountry(country);
		setName(name);
	}
	
	public Countries getCountry() {return country;}
	public String getName() {return name;}

	public void setCountry(Countries country) {this.country = country == null ? DEFAULT_COUNTRY : country;}
	public void setName(String name) {this.name = name == null ? DEFAULT_PUBLISHER_NAME : name;}

	public static Publisher getEmptyPublisher(){return new Publisher(null, null);}
	
	private static final int NUM_PUBLISHER_NAMES = 10;
	
	public static Publisher getRandomPublisher(){
		Countries publisherCountry = RandomLibrary.getRandomCountry();
		String publisherName = 
				PUBLISHER_NAME_PREFIX+"#"+RandomLibrary.gen.nextInt(NUM_PUBLISHER_NAMES);
		return new Publisher(publisherCountry, publisherName);
	}

	@Override
	public String toString() {
		return "<publisher: "+name+", "+country+">";
	}

	@Override
	public int compareTo(Publisher other) {
		int c = name.compareTo(other.name);
		return c == 0 ? country.name().compareTo(other.country.name()) : c;
	}
	
	
	
	
}
