package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.List;

public class PartialValueFunction {

	//Attributes
	private Criterion criterion;
	private List<Point> intervals;

	//Constructors
	public PartialValueFunction(Criterion criterion){
		this.criterion = criterion;
		intervals = new ArrayList<>();
	}
	public PartialValueFunction(Criterion criterion, List<Point> intervals){
		this.criterion = criterion;
		this.intervals = intervals;
	}

	//Getters and Setters
	public Criterion getCriterion() {
		return criterion;
	}
	public void setCriterion(Criterion criterion) {
		this.criterion = criterion;
	}
	public List<Point> getIntervals() {
		return intervals;
	}
	public void setIntervals(List<Point> intervals) {
		this.intervals = intervals;
	}

	//Methods
	public double getPartialValue(double x){
		System.out.println("                   " + getDerivative(x) + " * " +  x + "+" + getStartSegment(x));
		return getDerivative(x) * x + getStartSegment(x); // y = a x + b
	}
	public double getDerivative(double x){
		if(x < criterion.getScale().get(0)){return -1;}
		if(x > criterion.getScale().get(criterion.getScale().size() -1 )){return -1;}
		
		for(int i = 0 ; i < criterion.getScale().size();i++){
			if(x == criterion.getScale().get(i)){
				for(Point point : intervals){
					if(point.getX() == x){
						return point.getY();
					}
				}
				return -1.0;
			}

			if(x < criterion.getScale().get(i)){
				double x1 = criterion.getScale().get(i-1),  y1 = 0.0 ;
				double x2 = criterion.getScale().get(i), y2 = 0.0;
				for(Point point : intervals){
					if(point.getX() == criterion.getScale().get(i)){
						y2 = point.getY();
					}
					if(point.getX()== criterion.getScale().get(i-1)){
						y1 = point.getY();
					}
				}
				return (y2 - y1) / (x2 - x1);
			}
		}
		
		return -1.0;
		

	}
	public double getStartSegment(double x){
		if(x < criterion.getScale().get(0)){return -1;}
		if(x > criterion.getScale().get(criterion.getScale().size() -1 )){return -1;}
		
		for(int i = 0 ; i < criterion.getScale().size();i++){
			if(x == criterion.getScale().get(i)){
				return 0.0;
			}
			if(x < criterion.getScale().get(i)){
				double x1 = criterion.getScale().get(i-1);
				double x2 = criterion.getScale().get(i);
				double y1 = 0.0, y2 = 0.0;
				for(Point point : intervals){
					if(point.getX() == x1){
						y1 = point.getY();
					}
					if(point.getX() == x2){
						y2 = point.getY();
					}
				}
				double result = ((x2 * y1) - (x1 * y2))/(x2-x1);
				return result;
			}
		}
		
		return -1.0;
	}
	public double getWeight(){
		double maxWeight = 0.0;
		for(Point point : intervals){
			if(point.getY() > maxWeight){
				maxWeight = point.getY();
			}
		}
		return maxWeight;
	}

}