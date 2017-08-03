package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumbersGenerator{
	
	//attributes
	private Random random;

	//Constructors
	public NumbersGenerator(){
		this.random = new Random();
	}
	
	//Getters and Setters;
	public Random getRandom() {
		return random;
	}
	public void setRandom(Random random) {
		this.random = random;
	}
	
	//Methods
	public List<Double> generate(int counter, double targetSum){
		List<Double> result = new ArrayList<>();
		double sum = 0;
		for (int i = 1; i <= counter; i++) {
			double temp = 0;
			if (i != counter && targetSum > sum) {
				temp = random.nextDouble() * (targetSum-sum);
			} else {
				temp = targetSum - sum;
			}
			result.add(temp);
			sum += temp;
		}
		return result;
	}

}