/**
 * 
 */
package com.shubham.genetic_algo;

import java.util.LinkedList;

/**
 * @author Shubham Agrawal
 *
 */
public class GeneticAlgoMain {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		GeneticAlgo geneticAlgo = new GeneticAlgo();
		LinkedList<Equation> chromosomeSet, newChromosomeSet;

		chromosomeSet = new LinkedList<Equation>();

		geneticAlgo.initChromosomes(chromosomeSet);

		boolean flg = false;
		int i = 0;

		while (true) {

			System.out.println("Iteration: " + (i));
			geneticAlgo.print(chromosomeSet);
			
			geneticAlgo.calculateCumulativeProbability(chromosomeSet);

			newChromosomeSet = new LinkedList<Equation>();

			geneticAlgo.selectNewChromosomes(chromosomeSet, newChromosomeSet);
			chromosomeSet = (LinkedList<Equation>) newChromosomeSet.clone();

			geneticAlgo.crossover(chromosomeSet);

			geneticAlgo.mutate(chromosomeSet);

			for (int j = 0; j < GeneticAlgo.CHROMOSOME_SIZE; j++) {

				if (((Equation) (chromosomeSet.get(j))).fitness == 1) {
					flg = true;
					break;
				}

			}

			System.out.println("Iteration: " + (i++));
			geneticAlgo.print(chromosomeSet);

			if (flg == true) {
				break;
			}

		}

	}

}