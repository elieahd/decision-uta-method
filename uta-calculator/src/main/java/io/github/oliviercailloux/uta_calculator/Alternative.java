package io.github.oliviercailloux.uta_calculator;

import java.util.HashMap;
import java.util.Map;

public class Alternative {
	
	//Attributes
	private int id;
	private String name;
	private Map<Criterion, Double> evaluations;
	private Map<String, Double> marginalValue;
	
	//Constructors
	public Alternative(int id, String name, Map<Criterion,Double> evaluations){
		this.id = id;
		this.name = name;
		this.evaluations = evaluations;
		this.marginalValue = new HashMap<>();
	}

	//Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<Criterion, Double> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(Map<Criterion, Double> evaluations) {
		this.evaluations = evaluations;
	}
	public Map<String, Double> getMarginalValue() {
		return marginalValue;
	}
	public void setMarginalValue(Map<String, Double> marginalValue) {
		this.marginalValue = marginalValue;
	}
	
}