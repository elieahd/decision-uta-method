package io.github.oliviercailloux.uta_calculator.model;

import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

public class Alternative {
	
	//Attributes
	private int id;
	private String name;
	private Map<Criterion, Double> evaluations;
	
	//Constructors
	public Alternative(int id, String name, Map<Criterion,Double> evaluations){
		this.id = id;
		this.name = name;
		this.evaluations = evaluations;
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

	@Override
	public String toString() {
		ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
		stringHelper.add("id", id).add("name",name).add("evaluations", evaluations);
		return stringHelper.toString();
	}
	
}