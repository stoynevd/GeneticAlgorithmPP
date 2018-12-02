
public class mainActivity {

	public static void main(String[] args) {
		
		
		GeneticAlgorithm ga = new GeneticAlgorithm(new Population(20, 5).getSupermarkets());
		
//		ga.crossoverSupermarketPrices(population.getSupermarkets().get(0), population.getSupermarkets().get(1));

		ga.performGA(new Population(GeneticAlgorithm.NUMBER_OF_GOODS, GeneticAlgorithm.NUMBER_OF_SUPERMARKETS));

		
	}

}
