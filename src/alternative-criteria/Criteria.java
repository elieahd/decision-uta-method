package com.lamsade.alternativecriteria;

public class Criteria {
	//Attributes
	private String id;
	private static int counter;
	private int minValue;
	private int maxValue;
	
	//Constructors
	public Criteria(String id, int minValue, int maxValue){
		this.id = id;
		this.minValue = minValue;
		this.maxValue = maxValue;
		counter++;
	}
	
	public Criteria(String id){
		this.id = id;
		counter++;
		this.minValue = 10;
		this.maxValue = 20;
	}
	
	public Criteria(){
		counter++;
		this.id = "c" + counter;
		this.minValue = 10;
		this.maxValue = 20;
	}
	
	//Getters
	public String getId() {
		return id;
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}
	
}