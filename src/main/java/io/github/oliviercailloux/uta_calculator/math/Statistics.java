package io.github.oliviercailloux.uta_calculator.math;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.correlation.KendallsCorrelation;

public class Statistics {

	//Attributes
	private DescriptiveStatistics stats;
	private KendallsCorrelation kendallsCorrelation;

	//Constructors
	public Statistics(){
		stats = new DescriptiveStatistics();
		kendallsCorrelation = new KendallsCorrelation();
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

	public double getKendalTau(List<Double> input1, List<Double> input2){
		kendallsCorrelation = new KendallsCorrelation();
		return kendallsCorrelation.correlation(convertToArray(input1), convertToArray(input2));
	}

	private void setStat(List<Double> input){
		stats = new DescriptiveStatistics();
		for( int i = 0; i < input.size(); i++) {
			stats.addValue(input.get(i));
		}
	}

	private double[] convertToArray(List<Double> arraylist){
		double[] array = new double[arraylist.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = arraylist.get(i);                
		}
		return array;
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