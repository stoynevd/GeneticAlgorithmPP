import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class GeneticAlgorithm {

	public static final double MUTATION_RATE = 0.25;
	public static final int NUMBER_OF_GOODS = 20;
	public static final int NUMBER_OF_SUPERMARKETS = 20;
	public static final int TOURNAMENT_SELECTION_SIZE = 5;
	public static final int NUMBER_OF_ELITE_ROUTES = 1;
	public static final int NUMBER_OF_GENERATIONS = 5;

	private ArrayList<Supermarket> initialSupermarkets = null;

	public GeneticAlgorithm(ArrayList<Supermarket> initialSupermarkets) {
		this.initialSupermarkets = initialSupermarkets;
	}

	public ArrayList<Supermarket> getInitialRoute() {
		return initialSupermarkets;
	}

	public void setInitialRoute(ArrayList<Supermarket> initialSupermarkets) {
		this.initialSupermarkets = initialSupermarkets;
	}

	public Supermarket mutateRoute(Supermarket supermarket) {

		for (double d : supermarket.getPrices()) {

			if (Math.random() < MUTATION_RATE) {

				double min = -1.0;

				double max = 1.0;

				double a = min + new Random().nextDouble() * (max - min);

				d = d + a;

			}
		}

		return supermarket;

	}

	public Supermarket fillNulls(Supermarket crossover, Supermarket supermarket) {

		for (int i = 0; i < crossover.getPrices().length; i++) {

			if (crossover.getPrices()[i] == 0.0) {

				if (!Arrays.asList(crossover.getPrices()).contains(supermarket.getPrices()[i])) {

					crossover.getPrices()[i] = supermarket.getPrices()[i];

				}

			}

		}

		return crossover;

	}

	public Supermarket crossoverSupermarketPrices(Supermarket supermarket1, Supermarket supermarket2) {

		Supermarket crossoverRoute = new Supermarket(new double[supermarket1.getPrices().length]);

		int r1 = new Random().nextInt(supermarket1.getPrices().length);

		for (int i = r1; i < r1 + 3; i++) {

			if (i >= supermarket1.getPrices().length) {

				i = 0;

			} else if (r1 + 3 > supermarket1.getPrices().length) {

				r1 = r1 + 3 - supermarket1.getPrices().length;

			}

			crossoverRoute.getPrices()[i] = supermarket1.getPrices()[i];
		}

		return fillNulls(crossoverRoute, supermarket2);

	}

	public Population crossoverPopulations(Population population) {

		Population crossoverPopulation = new Population(NUMBER_OF_GOODS, NUMBER_OF_SUPERMARKETS);

		sortSupermarketsByFitness(population);

		for (int x = 0; x < NUMBER_OF_ELITE_ROUTES; x++) {

			crossoverPopulation.getSupermarkets().set(x, population.getSupermarkets().get(0));

		}

		for (int x = NUMBER_OF_ELITE_ROUTES; x < population.getSupermarkets().size(); x++) {

//			Supermarket supermarket1 = selectTournamentPopulations(population).getSupermarkets().get(0);
//
//			Supermarket supermarket2 = selectTournamentPopulations(population).getSupermarkets().get(0);
			
			Supermarket supermarket1 = roulette(population);
					
			Supermarket supermarket2 = roulette(population);		

			crossoverPopulation.getSupermarkets().set(x, crossoverSupermarketPrices(supermarket1, supermarket2));

		}

		return crossoverPopulation;

	}

	public Population selectTournamentPopulations(Population population) {

		Population tournamentPopulation = new Population(NUMBER_OF_GOODS, TOURNAMENT_SELECTION_SIZE);

		for (int i = 0; i < TOURNAMENT_SELECTION_SIZE; i++) {

			tournamentPopulation.getSupermarkets().set(i,
					population.getSupermarkets().get(new Random().nextInt(population.getSupermarkets().size())));

		}

		return tournamentPopulation;

	}

	public Population mutatePopulations(Population population) {

		Population mutatePopulation = new Population(NUMBER_OF_GOODS, NUMBER_OF_SUPERMARKETS);

		for (int x = 0; x < population.getSupermarkets().size(); x++) {

			mutatePopulation.getSupermarkets().set(x, mutateRoute(population.getSupermarkets().get(x)));

		}

		return mutatePopulation;

	}

	public Population evolve(Population population) {

		return mutatePopulations(crossoverPopulations(population));

	}

	public void performGA(Population population) {

		int initialGeneration = 0;

		long currentTime = System.currentTimeMillis();

		long endTime = currentTime + 10000;

		while (initialGeneration < NUMBER_OF_GENERATIONS) {

			System.out.println(initialGeneration + "\n");

			population = evolve(population);

			sortSupermarketsByFitness(population);

			printPopulation(population);

			initialGeneration++;

			currentTime = System.currentTimeMillis();
		}
	}

	public void printPopulation(Population population) {

		population.getSupermarkets().forEach(x -> {

			int s = population.getSupermarkets().indexOf(x) + 1;

			System.out.println(
					"Supermarket: " + s + " --> " + x.getFitness() + "\n" + Arrays.toString(x.getPrices()) + "\n");

		});

	}

	public void sortSupermarketsByFitness(Population population) {

		population.getSupermarkets().sort((supermarket1, supermarket2) -> {

			int flag = 0;

			if (supermarket1.getFitness() > supermarket2.getFitness()) {

				flag = -1;

			} else if (supermarket1.getFitness() < supermarket2.getFitness()) {

				flag = 1;

			}

			return flag;

		});

	}

	public Supermarket roulette(Population population) {

		double sum = 0.0;

		Supermarket r1 = population.getSupermarkets().get(0);

		for (int i = 0; i < population.getSupermarkets().size(); i++) {

			sum += PricingProblem.courseworkInstance().evaluate(population.getSupermarkets().get(i).getPrices());

		}

		double r = 0.0 + new Random().nextDouble() * (sum - 0.0);
		
		sum = 0.0;

		for (int i = 0; i < population.getSupermarkets().size(); i++) {

			if (sum > r) {

				r1 = population.getSupermarkets().get(i);

				break;

			} else {

				sum += PricingProblem.courseworkInstance().evaluate(population.getSupermarkets().get(i).getPrices());

			}

		}

		return r1;
	}

}
