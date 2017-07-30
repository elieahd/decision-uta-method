package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {

		int numCriteria, numAlternative;
		try{numCriteria = Integer.parseInt(args[0]);}catch(Exception e) {numCriteria = 5;}
		try{numAlternative = Integer.parseInt(args[1]);}catch(Exception e) {numAlternative = 3;}

		generateBuyingNewCarEx();

		//generateData(numCriteria, numAlternative);
	}

	private static void generateBuyingNewCarEx(){
		//Initialize criterias
		List<Criteria> criterias = new ArrayList<>();
		Criteria price = new Criteria("price", 15000, 25000, 3);
		Criteria comfort = new Criteria("comfort", 1, 4, 4);
		Criteria safety = new Criteria("safety", 1, 5, 3);
		criterias.add(price);
		criterias.add(comfort);
		criterias.add(safety);

		//Initialize alternatives
		List<Alternative> alternatives = new ArrayList<>();
		Map<Criteria, Integer> nsEvaluation = new HashMap<>();
		nsEvaluation.put(price, 22000);
		nsEvaluation.put(comfort, 4);
		nsEvaluation.put(safety, 4);
		Alternative ns = new Alternative("Nissan Sentra", nsEvaluation);
		alternatives.add(ns);

		Map<Criteria, Integer> c4Evaluation = new HashMap<>();
		c4Evaluation.put(price, 25000);
		c4Evaluation.put(comfort, 3);
		c4Evaluation.put(safety, 2);
		Alternative c4 = new Alternative("Citroen C4", c4Evaluation);
		alternatives.add(c4);

		Map<Criteria, Integer> p208Evaluation = new HashMap<>();
		p208Evaluation.put(price, 15000);
		p208Evaluation.put(comfort, 2);
		p208Evaluation.put(safety, 3);
		Alternative p208 = new Alternative("Peugeot 208 GT", p208Evaluation);
		alternatives.add(p208);

		Map<Criteria, Integer> p308Evaluation = new HashMap<>();
		p308Evaluation.put(price, 21500);
		p308Evaluation.put(comfort, 1);
		p308Evaluation.put(safety, 3);
		Alternative p308 = new Alternative("Peugeot 308 Berline", p308Evaluation);
		alternatives.add(p308);

		new UTASTAR(alternatives, criterias);		
	}

	private static void generateData(int numCriteria, int numAlternative){
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
		List<Double> randomDoubles = GenerateNumbers.generate(numCriteria, 1);
		for(int i = 0; i < criterias.size(); i++){
			criterias.get(i).setW(randomDoubles.get(i));
			//System.out.println(criterias.get(i).getScale());
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

		Collections.sort(alternatives, new Comparator<Alternative>() {
			public int compare(Alternative a1, Alternative a2) {
				Double vr1 = a1.getVr();
				Double vr2 = a2.getVr();

				return vr2.compareTo(vr1);
			}
		});

		System.out.println();
		System.out.println("Preference");
		for (int i = 0; i<alternatives.size(); i++){
			System.out.print(alternatives.get(i).getId());
			if(i != (alternatives.size() - 1)){
				if(alternatives.get(i).getVr() == alternatives.get(i+1).getVr()){
					System.out.print( " >= ");
				}else{
					System.out.print( " > ");
				}
			}
		}
	}

}