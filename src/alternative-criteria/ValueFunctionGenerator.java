package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.List;

public class ValueFunctionGenerator {

	//Attributes
	private int numberCriteria; 
	private int numberCuts;
	private List<PartialValueFunction> partialValueFunctions;

	public static void main(String[] args) {
		ValueFunctionGenerator g = new ValueFunctionGenerator(3,4);
		g.generateValueFunction();
	}

	//Constructors
	public ValueFunctionGenerator(int numberCriteria, int numberCuts){
		this.numberCriteria = numberCriteria;
		this.numberCuts = numberCuts;
		partialValueFunctions= new ArrayList<>();
	}
	
	//Getters
	public List<PartialValueFunction> getPartialValueFunctions() {
		return partialValueFunctions;
	}
	public void setPartialValueFunctions(
			List<PartialValueFunction> partialValueFunctions) {
		this.partialValueFunctions = partialValueFunctions;
	}
	
	//Methods
	public ValueFunction generateValueFunction(){
		Utils util = new Utils();
		NumbersGenerator generateNumbers = new NumbersGenerator();
		List<Double> randomWeights = generateNumbers.generate(numberCriteria,1.0);

		for(int j = 0; j < numberCriteria; j++){
			Criterion criterion = new Criterion(j, "c" + j , util.generateScale(10.0, 40.0, numberCuts));
			double criterionWeight = randomWeights.get(j); //0.4
			List<Point> intervals = new ArrayList<>();
			List<Double> randomPartialWeights = generateNumbers.generate(numberCuts-1,criterionWeight);
			double currentSum = 0;
			for(int i = 0; i < numberCuts ; i++){
				double wij = currentSum;
				intervals.add(new Point(criterion.getScale().get(i),wij));
				if(i != numberCuts -1){
					currentSum +=  randomPartialWeights.get(i);
				}
			}
			PartialValueFunction pvf = new PartialValueFunction(criterion, intervals);
//			String result =  criterion.getName() + " wj =  " +  criterionWeight + " = {";
//			for(Point point : pvf.getIntervals()){
//				result += "("+ point.getX() + "," + point.getY() +")"; 
//			}
//			result += "}";
//			System.out.println(result);
			partialValueFunctions.add(pvf);
		}
		ValueFunction valueFunction = new ValueFunction(partialValueFunctions);
		return valueFunction;
	}

}