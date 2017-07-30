package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateNumbers{

	public static void main(String[] args) {
		int counter;
		double targetSum;
		
		try{counter = Integer.parseInt(args[0]);}catch(Exception e){counter = 4;}
		try{targetSum = Integer.parseInt(args[1]);}catch(Exception e){targetSum = 1.0;}
		
		String result = "Generating " + counter + " number that have the sum of " + targetSum + " : " + generate(counter, targetSum);
		System.out.println(result);		
	}

	public static List<Double> generate(int counter, double targetSum){
		List<Double> result = new ArrayList<>();
		double sum = 0;
		for (int i = 1; i <= counter; i++) {
			double temp = 0;
			if (i != counter && targetSum > sum) {
				temp = new Random().nextDouble() * (targetSum-sum);
			} else {
				temp = targetSum - sum;
			}
			result.add(temp);
			sum += temp;
		}
		return result;
	}

}