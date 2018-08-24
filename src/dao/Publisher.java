package dao;

import java.io.Serializable;

import util.RandomLibrary;

public class Publisher implements Comparable<Publisher>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1731476746324573216L;
	public static final Countries DEFAULT_COUNTRY = Countries.unknown;
	public static final String DEFAULT_PUBLISHER_NAME = "no name";
	
	//private static final String PUBLISHER_NAME_PREFIX = "pName";
	
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
	
	//private static final int NUM_PUBLISHER_NAMES = 10;
	private static final String[] PUBLISHER_NAMES 
		= {"Star","Red Star","Black Star","Golden Star","Dead Star", "Sun", "Green Sun", "Sea", "Yellow Sea", "Red Sea"};
	
	public static Publisher getRandomPublisher(){
		Countries publisherCountry = RandomLibrary.getRandomCountry();
		String publisherName = PUBLISHER_NAMES[RandomLibrary.gen.nextInt(PUBLISHER_NAMES.length)];
				//PUBLISHER_NAME_PREFIX+"#"+RandomLibrary.gen.nextInt(NUM_PUBLISHER_NAMES);
		return new Publisher(publisherCountry, publisherName);
	}

	@Override
	public String toString() {
		return "["+name+", "+country+"]";
	}

	@Override
	public int compareTo(Publisher other) {
		int c = name.compareTo(other.name);
		return c == 0 ? country.name().compareTo(other.country.name()) : c;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		if (country != other.country)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
	
	
}
