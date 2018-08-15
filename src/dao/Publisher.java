package dao;

import util.RandomLibrary;

public class Publisher implements Comparable<Publisher>{

	public static final Countries DEFAULT_COUNTRY = Countries.unknown;
	public static final String DEFAULT_PUBLISHER_NAME = "no name";
	public static final Countries[] countries = Countries.values();
	
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

	public static Publisher getEmptyPublisher(){return new Publisher(DEFAULT_COUNTRY, DEFAULT_PUBLISHER_NAME);}
	
	private static final String[] PUBLISHER_NAMES 
		= {"Star","Red Star","Black Star","Golden Star","Dead Star", "Sun", "Green Sun", "Sea", "Yellow Sea", "Red Sea"};
	
	public static Publisher getRandomPublisher(){
		Countries publisherCountry = getRandomCountry();
		String publisherName = PUBLISHER_NAMES[RandomLibrary.gen.nextInt(PUBLISHER_NAMES.length)];
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
	
	private static Countries getRandomCountry(){
		
		double random = RandomLibrary.gen.nextDouble();
		
		double probability = countries[0].getProbability();
		for (int i=0; i<countries.length;){
			if (random < probability) return countries[i];
			else probability += countries[++i].getProbability();
		}
		return null;
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
