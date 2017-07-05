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
	private Map<Criteria, Double> criteriaWp;

	//Constructors
	public Alternative(List<Criteria> criterias){
		counter++;
		this.id = "a"+counter; 
		this.criteriaEvaluation = new HashMap<>();
		this.criteriaWp = new HashMap<>();
		randomizeValue(criterias);
	}

	//Getters
	public String getId() {
		return id;
	}

	public Map<Criteria, Integer> getCriteriaEvaluation() {
		return criteriaEvaluation;
	}
	
	public Map<Criteria, Double> getCriteriaWp() {
		return criteriaWp;
	}

	//Methods
	private void randomizeValue(List<Criteria> criterias) {
		for(Criteria criteria : criterias){
			Random r = new Random();
			this.criteriaEvaluation.put(criteria, r.nextInt((criteria.getMaxValue()+1) - criteria.getMinValue()) + criteria.getMinValue());
		}
	}

	
	public void calculateWp(List<Criteria> criterias) {
		this.criteriaWp = new HashMap<>();
		for(Criteria criteria : criterias){
			double wp = criteria.getA() * this.criteriaEvaluation.get(criteria) + criteria.getB();
			this.criteriaWp.put(criteria,wp);
		}
	}
	
}