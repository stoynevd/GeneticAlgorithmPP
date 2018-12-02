
import java.util.Arrays;
import java.util.Random;

public class Supermarket {

	private double[] prices;

	public Supermarket(double[] prices) {

		this.prices = prices;

	}

	public double[] getPrices() {
		return prices;
	}

	public void setprices(double[] prices) {
		this.prices = prices;
	}
	
	public double getFitness() {
		
		double d = PricingProblem.courseworkInstance().evaluate(prices);
		
		return d;
	
	}

}
