/**
 * 
 */
package com.shubham.genetic_algo;

/**
 * @author Shubham Agrawal
 *
 */
public class Equation {

	public int[] equation = new int[GeneticAlgo.VARIABLES];
	public double evaluation;
	public double fitness;
	public double probability;
	public double cumulativeProbability;

	public Equation() { }

	public Equation(Equation eq) {

		for (int i = 0; i < GeneticAlgo.VARIABLES; i++) {
			equation[i] = eq.equation[i];
		}

		evaluation = eq.evaluation;
		fitness = eq.fitness;
		probability = eq.probability;
		cumulativeProbability = eq.cumulativeProbability;

	}

}