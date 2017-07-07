package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		int numCriteria, numAlternative;
		try{numCriteria = Integer.parseInt(args[0]);}catch(Exception e) {numCriteria = 5;}
		try{numAlternative = Integer.parseInt(args[1]);}catch(Exception e) {numAlternative = 3;}

		//Initialize criterias
		List<Criteria> criterias = new ArrayList<>();
		for (int i = 0; i < numCriteria; i++){
			criterias.add(new Criteria());
		}

		//Initialize alternatives
		List<Alternative> alternatives = new ArrayList<>();
		for (int i = 0; i < numAlternative; i++){
			alternatives.add(new Alternative(criterias));
		}

		//Set Randomly W for each criteria
		List<Double> randomDoubles = GenerateNumbers.generateDoubles(numCriteria, 1, 2);
		for(int i = 0; i < criterias.size(); i++){
			criterias.get(i).setW(randomDoubles.get(i));
		}
		
		//Calculate Wpartial and V^R for each alternative
		for(Alternative alternative : alternatives){
			alternative.calculateWp(criterias);
		}

		//Display
		display(alternatives, criterias);
		
	}

	private static void display(List<Alternative> alternatives, List<Criteria> criterias) {
		System.out.println(alternatives.size() + " alternatives and " + criterias.size() + " criterias");

		System.out.println();
		System.out.println("Evaluation of the alternatives on the criterias (Random)");
		System.out.print("     ");
		for (int i = 0; i < criterias.size(); i++){
			System.out.print(criterias.get(i).getId() + " ");
		}
		System.out.println();
		for(Alternative alternative : alternatives){
			System.out.print(alternative.getId() + " : ");
			for (int i = 0; i < criterias.size(); i++){
				System.out.print(alternative.getCriteriaEvaluation().get(criterias.get(i)).intValue() + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Displaying W^R for each criteria (Random): ");
		for(Criteria criteria : criterias){
			System.out.println(criteria.getId() + "'s W^R = " + criteria.getW());
		}		
		
		System.out.println();
		System.out.println("Displaying the function for each criteria y = ax + b : ");
		for(Criteria criteria : criterias){
			System.out.println(criteria.getId() + " : " + criteria.getFunction());
		}
		
		System.out.println();
		System.out.println("Calculating the W partial of each alternative");
		for(Alternative alternative : alternatives){
			System.out.println("-- Alternative : " + alternative.getId());
			for (int i = 0; i < criterias.size(); i++){
				System.out.print("---- " +criterias.get(i).getId()  + " : ");
				System.out.print(alternative.getCriteriaWp().get(criterias.get(i)));
				System.out.println();
			}
			System.out.println();
		}
		
		System.out.println("Calculating the V^R for each alternative");
		for(Alternative  alternative : alternatives){
			System.out.println(alternative.getId() + "'s V^R = " + alternative.getVr());
		}
		
	}
	
}