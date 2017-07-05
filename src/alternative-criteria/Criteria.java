package com.lamsade.alternativecriteria;


import java.util.List;
import java.util.Map;

public class Criteria {
	
	//Attributes
	private String id;
	private static int counter;
	private int minValue, maxValue;
	private double a, b;// y = ax + b
	private double w;
	private double vr;
	
	//Constructors
	public Criteria(){
		counter++;
		this.id = "c" + counter;
		this.minValue = 10;
		this.maxValue = 20;
	}
	
	//Getters and Setters
	public String getId() {
		return id;
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
		calculateFunction(); // https://fr.wikipedia.org/wiki/Fonction_affine
	}

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}
	
	public double getVr() {
		return vr;
	}

	public String getFunction(){
		return "y = " + a + "x + " + b;
	}
	
	//Methods
	public void calculateFunction(){		
		double x1 = minValue;
		double y1 = 0.0;
		double x2 = maxValue;
		double y2 = w;
		
		this.a = (y2 - y1) / (x2 - x1);
		this.b = ((x2 * y1) - (x1 * y2))/(x2-x1);
		
		//y = ax + b
	}
	
	public void calculateVr(List<Alternative> alternatives){
		double sumWp = 0.0;
		for(Alternative alternative : alternatives){
			for (Map.Entry<Criteria,Double> entry : alternative.getCriteriaWp().entrySet()) {
				if(entry.getKey().getId().equals(this.getId())){
					sumWp += entry.getValue();
					break;
				}
			}
		}
		this.vr = sumWp;
	}
		
}