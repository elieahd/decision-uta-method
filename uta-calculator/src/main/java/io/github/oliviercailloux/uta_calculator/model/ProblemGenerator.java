package io.github.oliviercailloux.uta_calculator.model;

import io.github.oliviercailloux.uta_calculator.utils.ScaleGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ProblemGenerator {

	//Attributes
	private Random random;
	private List<Criterion> criteria;
	private List<Alternative> alternatives;

	//Constructors
	public ProblemGenerator(List<Criterion> criteria, List<Alternative> alternatives){
		this.criteria = criteria;
		this.alternatives = alternatives;
		random = new Random();
	}

	public ProblemGenerator(){
		this.criteria = new ArrayList<>();
		this.alternatives = new ArrayList<>();
		random = new Random();
	}

	//Getters and Setters
	public Random getRandom() {
		return random;
	}
	public void setRandom(Random random) {
		this.random = random;
	}
	public List<Criterion> getCriteria() {
		return criteria;
	}
	public void setCriteria(List<Criterion> criteria) {
		this.criteria = criteria;
	}
	public List<Alternative> getAlternatives() {
		return alternatives;
	}
	public void setAlternatives(List<Alternative> alternatives) {
		this.alternatives = alternatives;
	}

	//Methods
	public void generateCriteria(int number, double minValue, double maxValue, int cuts){
		ScaleGenerator scaleGenerator = new ScaleGenerator();
		for(int i = 0; i < number; i++){
			int id = i + 1;
			Criterion criterion = new Criterion(id, "c" + id , scaleGenerator.generate(minValue, maxValue, cuts));
			criteria.add(criterion);
		}
	}

	public void generateAlternatives(int number){
		for(int i = 0; i < number; i++){
			int id = i + 1;
			Map<Criterion, Double> evaluations = new HashMap<>();
			for(Criterion criterion : criteria){
				double randomValue = criterion.getMinValue() + (criterion.getMaxValue() - criterion.getMinValue()) * random.nextDouble();
				evaluations.put(criterion, randomValue);
			}
			Alternative alternative = new Alternative(id, "a" + id , evaluations);
			alternatives.add(alternative);
		}
	}

	@Override
	public String toString() {
		String result = "";
		result += "Criteria : \n";

		for (int i = 0; i < criteria.size(); i++){
			result += criteria.get(i).getName() + " --> " + criteria.get(i).getScale() + " \n";
		}

		result += "\nAlternatives : \n";
		for(Alternative alternative : alternatives){
			result += alternative.getName() + " --> ";
			for (int i = 0; i < criteria.size(); i++){
				result += alternative.getEvaluations().get(criteria.get(i)) + " ";
			}
			result += "\n";
		}
				
		return result;
	}
	
	
}