import java.util.ArrayList;
import java.util.Random;

public class Population {

	private ArrayList<Supermarket> supermarkets;

	public Population(int problemSize, int numberOfSupermarkets) {

		supermarkets = new ArrayList<Supermarket>();

		for (int i = 0; i < numberOfSupermarkets; i++) {

			supermarkets.add(new Supermarket(randomPrices(problemSize)));

		}

	}

	public ArrayList<Supermarket> getSupermarkets() {

		return supermarkets;

	}

	public void setSupermarkets(ArrayList<Supermarket> supermarkets) {

		this.supermarkets = supermarkets;

	}

	public double[] randomPrices(int problemSize) {

		double[] design = new double[problemSize];

		double min = 0.1;

		double max = 10.0;

		for (int i = 0; i < design.length; i++) {

			double d = min + new Random().nextDouble() * (max - min);

			design[i] = d;

		}
		return design;
	}

}
