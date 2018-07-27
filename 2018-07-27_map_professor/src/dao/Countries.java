package dao;

public enum Countries {
	USA(0.4), Germany(0.3), Russia(0.2), Israel(0.1), unknown(0.);

	private double probability;

	private Countries(double probability) {
		this.probability = probability;
	}
	public double getProbability(){return probability;}
}
