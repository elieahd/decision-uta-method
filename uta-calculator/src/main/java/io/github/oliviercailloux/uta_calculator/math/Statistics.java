package io.github.oliviercailloux.uta_calculator.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;

public class Statistics {

	//Attributes
	DescriptiveStatistics stats;
		
	//Constructors
	public Statistics(){
		stats = new DescriptiveStatistics();
	}

	//Method
	public double getMean(List<Double> input){
		setStat(input);
		return stats.getMean();
	}
	
	public double getStd(List<Double> input){
		setStat(input);
		return stats.getStandardDeviation();
	}
	
	public void setStat(List<Double> input){
		stats = new DescriptiveStatistics();
		for( int i = 0; i < input.size(); i++) {
	        stats.addValue(input.get(i));
		}
	}

	public static void main(String[] args) {
		Statistics statistics = new Statistics();
		List<Double> list = new ArrayList<Double>();
		for(double i = 0; i < 10; i++){
			list.add(i);
		}
		System.out.println("List: " + list);
		System.out.println("Mean: " + statistics.getMean(list));
		System.out.println("Std: " + statistics.getStd(list));
	}


}