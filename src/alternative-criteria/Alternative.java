package com.lamsade.alternativecriteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Alternative {

	//Alternatives
	private String id; 
	private static int counter = 0;
	private Map<Criteria, Integer> criteriaEvaluation;

	//Constructors
	public Alternative(List<Criteria> criterias){
		counter++;
		this.id = "a"+counter; 
		criteriaEvaluation = new HashMap<>();
		randomizeValue(criterias);
	}

	//Getters
	public String getId() {
		return id;
	}

	public Map<Criteria, Integer> getCriteriaEvaluation() {
		return criteriaEvaluation;
	}

	//Methods
	private void randomizeValue(List<Criteria> criterias) {
		for(Criteria criteria : criterias){
			Random r = new Random();
			this.criteriaEvaluation.put(criteria, r.nextInt((criteria.getMaxValue()+1) - criteria.getMinValue()) + criteria.getMinValue());
		}
	}

}