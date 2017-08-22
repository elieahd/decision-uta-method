package io.github.oliviercailloux.uta_calculator.view;

import io.github.oliviercailloux.uta_calculator.math.Statistics;
import io.github.oliviercailloux.uta_calculator.model.Alternative;
import io.github.oliviercailloux.uta_calculator.model.ProblemGenerator;
import io.github.oliviercailloux.uta_calculator.model.UTASTAR;
import io.github.oliviercailloux.uta_calculator.model.ValueFunction;
import io.github.oliviercailloux.uta_calculator.model.ValueFunctionGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainSimulation {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainSimulation.class);

	public static void main(String[] args) {
		MainSimulation main = new MainSimulation();
		main.simulation();
	}

	private void simulation(){
		List<Double> differenceList = new ArrayList<>();
		LOGGER.info("Start of Simulation");
		for(int i = 0; i < 1000; i++){
			double difference = getDifferenceRank(20,3,10);
			differenceList.add(difference);
		}
		LOGGER.info("End of Simulation");
		Statistics stats = new Statistics();
		System.out.println("Mean : " + stats.getMean(differenceList));
		System.out.println("Std : " + stats.getStd(differenceList));
	}

	private int getDifferenceRank(int numberAlternatives, int numberCriteria, int numberAlternativesToCompare){
		int numberCuts = 3;
		double minValue = 10.0;
		double maxValue = 20.0;

		ProblemGenerator problem = new ProblemGenerator();
		problem.generateCriteria(numberCriteria, minValue, maxValue, numberCuts);
		problem.generateAlternatives(numberAlternatives);

		ValueFunctionGenerator  vfg = new ValueFunctionGenerator(problem.getCriteria());
		ValueFunction vR = vfg.generateValueFunction();
//		LOGGER.info("ValueFunction R: {}.",vR);

		List<Alternative> alternatives = problem.getAlternatives();
		Collections.sort(alternatives, new Comparator<Alternative>() {
			@Override
			public int compare(final Alternative a1, final Alternative a2) {
				return ((Double)vR.getValue(a1)).compareTo(vR.getValue(a2));
			}
		});

		List<Alternative> exampleAlternatives = new ArrayList<>();
		for(int i = 0; i < numberAlternativesToCompare; i++){
			exampleAlternatives.add(alternatives.get(i));
		}
//		LOGGER.info("Examples: {}.",exampleAlternatives);

		List<Alternative> alternativesToCompare = new ArrayList<>();
		for(int i = numberAlternativesToCompare; i < alternatives.size(); i++){
			alternativesToCompare.add(alternatives.get(i));
		}
//		LOGGER.info("To compare: {}.",alternativesToCompare);

		UTASTAR utastar = new UTASTAR(problem.getCriteria(), exampleAlternatives);
		utastar.setPrint(false);
		ValueFunction vT = utastar.findValueFunction();
//		LOGGER.info("ValueFunction T: {}.",vT);

		return compare(vR, vT, alternativesToCompare);
	}

	private int compare(ValueFunction vR, ValueFunction vT, List<Alternative> alternatives) {

		List<Alternative> alternativeR = new ArrayList<>();
		List<Alternative> alternativeT = new ArrayList<>();
		for(Alternative alternative : alternatives){
			alternativeR.add(alternative);
			alternativeT.add(alternative);
		}
		
		Collections.sort(alternativeR, new Comparator<Alternative>() {
			@Override
			public int compare(final Alternative a1, final Alternative a2) {
				return ((Double)vR.getValue(a1)).compareTo(vR.getValue(a2));
			}
		});

		Collections.sort(alternativeT, new Comparator<Alternative>() {
			@Override
			public int compare(final Alternative a1, final Alternative a2) {
				return ((Double)vT.getValue(a1)).compareTo(vT.getValue(a2));
			}
		});
		
		int differenceRank = 0;
		for(int i = 0;  i < alternatives.size(); i++){
			if(alternativeR.get(i).getId() != alternativeT.get(i).getId()){
				for(int j = 0; j < alternativeT.size(); j++){
					if(alternativeR.get(i).getId() == alternativeT.get(j).getId()){
						System.out.println(i + " ? " + j);
						differenceRank += Math.abs(i-j);
						break;
					}
				}
			}
		}
		System.out.println(differenceRank);
		return differenceRank;
	} 

}