package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Criteria {

	//Attributes
	private String id;
	private static int counter;
	private int minValue, maxValue;
	private double a, b;// y = ax + b
	private double w;
	private List<Double> scale;
	public static final int SCALE_INTERVAL = 4;

	//Constructors
	public Criteria(){
		counter++;
		this.id = "c" + counter;
		this.minValue = 10;
		this.maxValue = 20;
		scale = new ArrayList<>();
		setScale();
	}
	
	public Criteria(String id, int minValue, int maxValue, int scaleInterval){
		counter++;
		this.id = id;
		this.minValue = minValue;
		this.maxValue = maxValue;
		scale = new ArrayList<>();
		setScale(scaleInterval);
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

	public String getFunction(){
		return "y = " + a + "x + " + b;
	}
	
	private void setScale (int scaleInterval){
		scale = new ArrayList<Double>();
		double total_length = maxValue - minValue;
		double subrange_length = total_length/(scaleInterval-1);

		double current_start = minValue;
		for (int i = 0; i < scaleInterval; ++i) {
			if(i == 0){
				scale.add(Double.parseDouble(minValue+""));
			}
			else if(i==(scaleInterval-1)){
				scale.add(Double.parseDouble(maxValue+""));
			}else{
				scale.add(current_start);
			}
			current_start += subrange_length;
		}
	}
	
	private void setScale(){
		setScale(SCALE_INTERVAL);
	}

	public List<Double> getScale(){
		return scale;
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

	public Map<String, Double> getMarginalValue(int value, int criteriaId){
		Collections.sort(scale);
		Map<String, Double> result = new HashMap<>();
		
		int i = 0;
		while(value > scale.get(i)){i++;}

		for(int x = 1 ; x <= i; x++){
			double coef = 1;
			if((x == i) && (scale.get(i)!=value)){
				coef = 1 - ((scale.get(x)- value) / (scale.get(i) - scale.get((i-1))));
			}
			result.put("w" + criteriaId + x, coef);
		}

		return result;

	}

	@Override
	public String toString() {
		return id;
	}

}