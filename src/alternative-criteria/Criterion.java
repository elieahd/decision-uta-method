package com.lamsade.alternativecriteria;

import java.util.List;

public class Criterion {
	
	//Attributes
	private int id;
	private String name;
	private List<Double> scale;
	
	//Constructors
	public Criterion(int id, String name, List<Double> scale){
		this.id = id;
		this.name = name;
		this.scale = scale;
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
	public List<Double> getScale() {
		return scale;
	}
	public void setScale(List<Double> scale) {
		this.scale = scale;
	}
	
	//Methods
	public double getMinValue(){
		return scale.get(0);
	}
	public double getMaxValue(){
		return scale.get(scale.size() -1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Criterion other = (Criterion) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}