/**
 * 
 */
package com.shubham.genetic_algo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * @author Shubham Agrawal
 *
 */
public class GeneticAlgo {

	private float totalFitness;
	private Random random;
	private Equation Eq;
	
	private static double CROSSOVER_RATE, MUTATION_RATE;
	public static int VARIABLES;
	public static int CHROMOSOME_SIZE;

	private double[] cumProb = new double[CHROMOSOME_SIZE];

	static {

		VARIABLES = 4;				//the variables in the equation, which represnts the (a, b, c, d)
		CHROMOSOME_SIZE = 6;		//the size of chromosoms population
		CROSSOVER_RATE = 0.25;
		MUTATION_RATE = 0.1;

	}

	public GeneticAlgo() {

		random = new Random();
		totalFitness = 0;

	}

	// Initialize the chromosomes
	public void initChromosomes(LinkedList<Equation> chromosomeSet) {

		for (int i = 0; i < CHROMOSOME_SIZE; i++) {

			Eq = new Equation();

			for (int j = 0; j < VARIABLES; j++) {

				Eq.equation[j] = random.nextInt(41);;
				Eq.fitness = 0;

			}

			chromosomeSet.add(Eq);

		}
	}

	// Calculate Cumulative Probability
	public void calculateCumulativeProbability(LinkedList<Equation> chromosomeSet) {

		totalFitness = 0;
		double probability = 0;
		double cumulativeProbability = 0;

		for (int i = 0; i < CHROMOSOME_SIZE; i++) {

			Eq = chromosomeSet.get(i);
			Eq.evaluation = calculateObjeciveFunction(Eq);
			Eq.fitness = calculateFitness(Eq);
			totalFitness += Eq.fitness;

		}

		for (int i = 0; i < CHROMOSOME_SIZE; i++) {

			Eq = chromosomeSet.get(i);
			probability = calculateProbability(Eq);
			cumulativeProbability += probability;
			Eq.probability = probability;
			Eq.cumulativeProbability = cumulativeProbability;
			cumProb[i] = Eq.cumulativeProbability;

		}

	}

	// Calculate objective function of chromosome
	private double calculateObjeciveFunction(Equation eq) {

		return (double) Math.abs((eq.equation[0] + 2*eq.equation[1] + 3*eq.equation[2] + 4*eq.equation[3]) - 40);

	}

	// Calculate Fitness of chromosome
	private double calculateFitness(Equation eq) {

		return 1 / (1 + eq.evaluation);

	}

	// Calculate Probability of chromosome
	private double calculateProbability(Equation eq) {

		return eq.fitness / totalFitness;

	}

	// Select new chromosome
	public void selectNewChromosomes(LinkedList<Equation> chromosomeSet, LinkedList<Equation> newChromosomeSet) {

		for (int i = 0; i < CHROMOSOME_SIZE; i++) {

			double randomNumber = random.nextDouble();

			for (int j = 0; j < CHROMOSOME_SIZE; j++) {

				if (randomNumber < cumProb[j]) {
					Eq = chromosomeSet.get(j);
					Equation tmp = new Equation(Eq);
					newChromosomeSet.add(tmp);
					break;
				}

			}

		}

	}

	// Crossover the chromosomes
	public void crossover(LinkedList<Equation> chromosomeSet) {

		LinkedList<Equation> tmpChromosomeSet = new LinkedList<Equation>();
		ArrayList<Integer> tmpChromosomeSetIndex = new ArrayList<Integer>();

		double randomNum1;
		int randomNum2;

		for (int i = 0; i < CHROMOSOME_SIZE; i++) {

			randomNum1 = random.nextDouble();

			if (randomNum1 < CROSSOVER_RATE) {

				tmpChromosomeSet.add(chromosomeSet.get(i));
				tmpChromosomeSetIndex.add(i);

			}

		}

		for (int i = 0; i < tmpChromosomeSet.size()-1; i++) {

			Eq = chromosomeSet.get(tmpChromosomeSetIndex.get(i));

			Equation tmpEquation = tmpChromosomeSet.get(i + 1);

			randomNum2 = random.nextInt(VARIABLES-1)+1;

			for (int j = VARIABLES - 1; j >= randomNum2; j--) {
				Eq.equation[j] = tmpEquation.equation[j];
			}

		}
	}

	// Mutation of chromosomes
	public void mutate(LinkedList<Equation> chromosomeSet) {

		int index;
		int randomNum1, randomNum2;

		int totalVariables = VARIABLES * CHROMOSOME_SIZE;
		int mutations = (int) (MUTATION_RATE * totalVariables);

		for (int i = 0; i < mutations; i++) {

			randomNum1 = random.nextInt(totalVariables)+1;
			randomNum2 = random.nextInt(41);

			index = (randomNum1 - 1) % VARIABLES;
			Eq = (Equation) chromosomeSet.get((randomNum1 - 1) / VARIABLES);
			Eq.equation[index] = randomNum2;

		}

	}

	// print chromosomes
	public void print(LinkedList<Equation> chromosomeSet) {

		for (int j = 0; j < chromosomeSet.size(); j++) {

			for (int i = 0; i < 4; i++) {
				System.out.print(((Equation) chromosomeSet.get(j)).equation[i] + ", ");
			}

			System.out.println(" fitness = " + ((Equation) chromosomeSet.get(j)).fitness + "\n");
		}

	}

}
