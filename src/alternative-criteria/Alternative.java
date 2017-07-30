package com.lamsade.alternativecriteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class Alternative {

	//Alternatives
	private String id; 
	private static int counter = 0;
	private Map<Criteria, Integer> criteriaEvaluation;
	private Map<Criteria, Double> criteriaWp;
	private Map<String,Double> marginalValue;
	private double vr;

	//Constructors
	public Alternative(List<Criteria> criterias){
		counter++;
		this.id = "a"+counter; 
		this.criteriaEvaluation = new HashMap<>();
		this.criteriaWp = new HashMap<>();
		this.marginalValue = new HashMap<>();
		randomizeValue(criterias);
	}

	public Alternative(String id, Map<Criteria, Integer> criteriaEvaluation){
		counter++;
		this.id = id;
		this.criteriaEvaluation = criteriaEvaluation;
		this.criteriaWp = new HashMap<>();
		this.marginalValue = new HashMap<>();
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

	public double getVr() {
		return vr;
	}

	public Map<String, Double> getMarginalValue() {
		return marginalValue;
	}

	public String getMarginalValueString(){
		String result = "";
		for(Entry<String, Double> e : getMarginalValue().entrySet()) {
			if(result.length() > 0) {result += " + ";}
			result += e.getValue() + " " + e.getKey();
		}
		return result;
	}

	public void setMarginalValueMap(Map<String,Double> marginalValue){
		this.marginalValue = marginalValue;
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
		double sumWp = 0.0;
		for(Criteria criteria : criterias){
			double wp = criteria.getA() * this.criteriaEvaluation.get(criteria) + criteria.getB();
			this.criteriaWp.put(criteria,wp);
			sumWp += wp;
		}
		this.vr = sumWp;
	}

}