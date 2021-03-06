package io.github.oliviercailloux.uta_calculator.model;

public class Point {
	
	//Attributes
	private final double x;
	private final double y;
	
	//Constructor
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	//Getters
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ") ";
	}
	
}