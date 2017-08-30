package io.github.oliviercailloux.uta_calculator.model;

import io.github.oliviercailloux.uta_calculator.utils.NumbersGenerator;

import java.util.ArrayList;
import java.util.List;

public class ValueFunctionGenerator {

	//Attributes
	private List<Criterion> criteria;
	private List<PartialValueFunction> partialValueFunctions;

	//Constructors
	public ValueFunctionGenerator(List<Criterion> criteria) {
		this.criteria = criteria;
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
		NumbersGenerator generateNumbers = new NumbersGenerator();
		List<Double> randomWeights = generateNumbers.generate(criteria.size(),1.0);
		int j = 0;
		for(Criterion criterion : criteria){
			int numberCuts = criterion.getScale().size();
			double criterionWeight = randomWeights.get(j); 
			List<Point> intervals = new ArrayList<>();
			List<Double> randomPartialWeights = generateNumbers.generate(numberCuts-1,criterionWeight);
			double currentSum = 0;
			for(int i = 0; i < numberCuts ; i++){
				double wij = currentSum;
				intervals.add(new Point(criterion.getScale().get(i),wij));
				if(i != numberCuts - 1){
					currentSum +=  randomPartialWeights.get(i);
				}
			}
			PartialValueFunction pvf = new PartialValueFunction(criterion, intervals);
			partialValueFunctions.add(pvf);
			j++;
		}
		ValueFunction valueFunction = new ValueFunction(partialValueFunctions);
		return valueFunction;
	}

}