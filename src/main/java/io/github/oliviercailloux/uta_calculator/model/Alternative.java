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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((evaluations == null) ? 0 : evaluations.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alternative other = (Alternative) obj;
		if (evaluations == null) {
			if (other.evaluations != null)
				return false;
		} else if (!evaluations.equals(other.evaluations))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}