package com.lamsade.alternativecriteria;

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
		double value = 0.0;
		System.out.println("PVFFFF");
		for (Map.Entry<Criterion, Double> entry : alternative.getEvaluations().entrySet()){
			for(PartialValueFunction pvf : partialValueFunctions){
				if(pvf.getCriterion() == entry.getKey()){
					System.out.println(pvf.getCriterion() + " --> " + entry.getValue() + " = " + pvf.getPartialValue(entry.getValue()));
					value += pvf.getPartialValue(entry.getValue());
					break;
				}
			}
		}
		return value;
	}

}