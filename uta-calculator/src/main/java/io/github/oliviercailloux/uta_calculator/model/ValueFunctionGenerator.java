package io.github.oliviercailloux.uta_calculator.model;

import io.github.oliviercailloux.uta_calculator.utils.NumbersGenerator;
import io.github.oliviercailloux.uta_calculator.utils.ScaleGenerator;

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
		ScaleGenerator scaleGenerator = new ScaleGenerator();
		NumbersGenerator generateNumbers = new NumbersGenerator();
		List<Double> randomWeights = generateNumbers.generate(numberCriteria,1.0);
		
		double minValue = 10.0;
		double maxValue = 20.0;
		
		for(int j = 0; j < numberCriteria; j++){
			Criterion criterion = new Criterion(j, "c" + j , scaleGenerator.generate(minValue, maxValue, numberCuts));
			double criterionWeight = randomWeights.get(j); 
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
			partialValueFunctions.add(pvf);
		}
		ValueFunction valueFunction = new ValueFunction(partialValueFunctions);
		return valueFunction;
	}

}