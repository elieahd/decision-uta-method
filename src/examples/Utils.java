package com.lamsade.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

	public static void main(String[] args) {
		int counter = 0;
		int digitsDecimal = 0;
		try{
			counter = Integer.parseInt(args[0]);
		}catch(Exception e){
			counter = 4;
		}
		try{
			digitsDecimal = Integer.parseInt(args[1]);
		}catch(Exception e){
			digitsDecimal = 2;
		}
		System.out.println(generate(counter,digitsDecimal));
	}
		
	private static List<Double> generate(int counter, int digitsDecimal){
		List<Double> result = new ArrayList<Double>();
		int digits = (int) Math.pow(10, digitsDecimal);
		List<Integer> resultInt = generateIntegers(counter, digits);
		
		for (Integer integer : resultInt){
			result.add((double) (integer * Math.pow(10, -digitsDecimal)));
		}
		return result;
	}
	
	private static int getDecimalDigits(double input){
		String strInput = Double.toString(Math.abs(input));
		int integerPlaces = strInput.indexOf('.');
		int decimalPlaces = strInput.length() - integerPlaces - 1;
		return decimalPlaces;
	}
	
	private static List<Double> generateDouble(int counter, double targetSum){
		List<Double> result = new ArrayList<Double>();
		int intTargetSum = (int) (targetSum * Math.pow(10,getDecimalDigits(targetSum)));
		List<Integer> resultInInteger = generateIntegers(counter, intTargetSum);
		
		for (Integer integer : resultInInteger){
			result.add((double) (integer/(Math.pow(10,getDecimalDigits(targetSum)))));
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
				temp = r.nextInt(targetSum - currentSum); // alow 0 
				//temp = r.nextInt((targetSum - currentSum) / (counter - i)) + 1;// don't allow 0 
			} else {
				temp = targetSum - currentSum;
			}
			result.add(temp);
			currentSum += temp;
		}
		return result;
	}

}