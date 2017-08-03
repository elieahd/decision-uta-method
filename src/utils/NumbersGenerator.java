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

	public static void main(String[] args) throws Exception {
		int counter;
		double targetSum;

		if(args.length == 0){
			counter = 4;
			targetSum = 1.0;
		}else if(args.length == 2){
			try{
				counter = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {throw new Exception(args[0] + " is not an integer. This should be the counter of numbers you want to generate");}
			try{
				targetSum = Double.parseDouble(args[1]);
			} catch (NumberFormatException e) {throw new Exception(args[1] + " is not a double. This should be the target sum you want to generate");}
		}else{
			throw new Exception("Please insert 0 or 2 arguments");
		}

		NumbersGenerator numbersGenerator = new NumbersGenerator();

		String result = "Generating " + counter + " number that have the sum of " + targetSum + " : " + numbersGenerator.generate(counter, targetSum);
		System.out.println(result);		
	}

}