package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		int numCriteria, numAlternative;
		try{numCriteria = Integer.parseInt(args[0]);}catch(Exception e) {numCriteria = 3;}
		try{numAlternative = Integer.parseInt(args[1]);}catch(Exception e) {numAlternative = 10;}
		
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

		display(alternatives, criterias);
	}

	private static void display(List<Alternative> alternatives, List<Criteria> criterias) {
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
	}
	
}