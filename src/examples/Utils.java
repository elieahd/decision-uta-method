package com.lamsade.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

	public static void main(String[] args) {
		int counter, digitsDecimal, targetSum;
		
		try{counter = Integer.parseInt(args[0]);}catch(Exception e){counter = 4;}
		try{targetSum = Integer.parseInt(args[1]);}catch(Exception e){targetSum = 1;}
		try{digitsDecimal = Integer.parseInt(args[2]);}catch(Exception e){digitsDecimal = 0;}
		
		String result = "";
		if(digitsDecimal == 0){
			result = "Generating " + counter + " number that have the sum of " + targetSum + " : " + generateIntegers(counter, targetSum);
		}else{
			result = "Generating " + counter + " number with " + digitsDecimal + " digits precision that have the sum of " + targetSum + " : " + generateDoubles(counter, targetSum, digitsDecimal);
		}
		System.out.println(result);		
	}
	
	private static List<Double> generateDoubles(int counter, int targetSum, int digitsDecimal){
		List<Double> result = new ArrayList<Double>();
		int digits = (int) Math.pow(10, digitsDecimal);
		
		List<Integer> resultInt = generateIntegers(counter, targetSum * digits);
		
		for (Integer integer : resultInt){
			result.add((double) (integer * Math.pow(10, -digitsDecimal)));
		}
		return result;
	}
	
	private static List<Integer> generateIntegers(int counter, int targetSum){
		Random r = new Random();
		List<Integer> result = new ArrayList<Integer>();
		int temp = 0;
		int currentSum = 0;
		for (int i = 1; i <= counter; i++) {
			if (i != counter) {
				temp = r.nextInt((targetSum - currentSum) / (counter - i)) + 1;//temp = r.nextInt(targetSum - currentSum);
			} else {
				temp = targetSum - currentSum;
			}
			result.add(temp);
			currentSum += temp;
		}
		return result;
	}

}