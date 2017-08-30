package io.github.oliviercailloux.uta_calculator.view;

import io.github.oliviercailloux.uta_calculator.model.Alternative;
import io.github.oliviercailloux.uta_calculator.model.Criterion;
import io.github.oliviercailloux.uta_calculator.model.PartialValueFunction;
import io.github.oliviercailloux.uta_calculator.model.ProblemGenerator;
import io.github.oliviercailloux.uta_calculator.model.UTASTAR;
import io.github.oliviercailloux.uta_calculator.model.ValueFunction;
import io.github.oliviercailloux.uta_calculator.utils.ScaleGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainBuyingNewCar {
	
	public static void main(String[] args) {
		MainBuyingNewCar main = new MainBuyingNewCar();

		ProblemGenerator p1 = main.generateBuyingNewCar();
		System.out.println("Problem");
		System.out.println(p1);
		UTASTAR utastar = new UTASTAR(p1.getCriteria(), p1.getAlternatives());
		System.out.println("Start of UTASTAR Algorithm");
		ValueFunction vf = utastar.findValueFunction();
		System.out.println("End of UTASTAR Algorithm");
		System.out.println();
		System.out.println("Displaying Value Function");
		for(PartialValueFunction pvf : vf.getPartialValueFunctions()){
			System.out.println(pvf.getCriterion().getName() + " : {" + pvf.getIntervals() + "}");
		}

		System.out.println();
		System.out.println("Displaying the value of the alternatives from the function valueFunction.getValue(alternative) :");
		for(Alternative alternative : p1.getAlternatives()){
			System.out.println( alternative.getName() + " : " + vf.getValue(alternative));
		}
	}
	
	private ProblemGenerator generateBuyingNewCar() {
		ScaleGenerator scaleGenerator = new ScaleGenerator();
		List<Criterion> criteria = new ArrayList<>();
		Criterion price = new Criterion(1, "price", scaleGenerator.generate(15000.0, 25000.0, 3));
		criteria.add(price);
		Criterion comfort = new Criterion(2, "comfort", scaleGenerator.generate(1.0, 4.0, 4));
		criteria.add(comfort);
		Criterion safety = new Criterion(3, "safety", scaleGenerator.generate(1.0, 5.0, 3));
		criteria.add(safety);

		List<Alternative> alternatives = new ArrayList<>();
		Map<Criterion, Double> evaluation = new HashMap<>();
		evaluation.put(price, 22000.0);
		evaluation.put(comfort, 4.0);
		evaluation.put(safety, 4.0);
		Alternative ns = new Alternative(1,"Nissan Sentra", evaluation);
		alternatives.add(ns);

		evaluation = new HashMap<>();
		evaluation.put(price, 25000.0);
		evaluation.put(comfort, 3.0);
		evaluation.put(safety, 2.0);
		Alternative c4 = new Alternative(2, "Citroen C4", evaluation);
		alternatives.add(c4);

		evaluation = new HashMap<>();
		evaluation.put(price, 15000.0);
		evaluation.put(comfort, 2.0);
		evaluation.put(safety, 3.0);
		Alternative p208 = new Alternative(3, "Peugeot 208 GT", evaluation);
		alternatives.add(p208);

		evaluation = new HashMap<>();
		evaluation.put(price, 21500.0);
		evaluation.put(comfort, 1.0);
		evaluation.put(safety, 3.0);
		Alternative p308 = new Alternative(4, "Peugeot 308 Berline", evaluation);
		alternatives.add(p308);

		ProblemGenerator problem = new ProblemGenerator(criteria, alternatives);
		return problem;
	}

}
