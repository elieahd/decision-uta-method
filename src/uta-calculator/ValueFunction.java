package com.lamsade.utacalculator;

import java.util.List;
import java.util.Map;

public class ValueFunction {

	//Attributes
	private List<PartialValueFunction> partialValueFunctions;

	//Constructors
	public ValueFunction(List<PartialValueFunction> partialValueFunctions){
		this.partialValueFunctions = partialValueFunctions;
	}

	//Getters and Setters
	public List<PartialValueFunction> getPartialValueFunctions() {
		return partialValueFunctions;
	}
	public void setPartialValueFunctions(List<PartialValueFunction> partialValueFunctions) {
		this.partialValueFunctions = partialValueFunctions;
	}

	//Methods
	public double getValue(Alternative alternative){

		System.out.println(alternative.getName());
		double value = 0.0;
		for (Map.Entry<Criterion, Double> entry : alternative.getEvaluations().entrySet()){
			for(PartialValueFunction pvf : partialValueFunctions){
				if(pvf.getCriterion() == entry.getKey()){
					double newValue = pvf.getPartialValue(entry.getValue());
					System.out.println("Value : " + entry.getValue() + " --- " + pvf.getCriterion().getName() + " : " + newValue);
					value += newValue;
					break;
				}
			}
		}
		System.out.println();
		return value;
	}

}