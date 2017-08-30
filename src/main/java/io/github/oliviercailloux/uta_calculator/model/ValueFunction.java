package io.github.oliviercailloux.uta_calculator.model;

import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;

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
	public PartialValueFunction getPartialValueFunction(Criterion criterion){
		for(PartialValueFunction pvf : partialValueFunctions){
			if(pvf.getCriterion() == criterion){
				return pvf;
			}
		}
		throw new IllegalArgumentException();
	}
	public double getValue(Alternative alternative){
		double value = 0.0;
		for (Map.Entry<Criterion, Double> entry : alternative.getEvaluations().entrySet()){
			PartialValueFunction pvf = getPartialValueFunction(entry.getKey());
			double newValue = pvf.getPartialValue(entry.getValue());
			value += newValue;
		}
		return value;
	}	
	public String toString() {
		ToStringHelper stringHelper = MoreObjects.toStringHelper(this);
		stringHelper.add("partialValueFunctions", partialValueFunctions);
		return stringHelper.toString();
	}

}