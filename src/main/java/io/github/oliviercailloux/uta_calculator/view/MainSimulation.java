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

		int numAlternative = 10000;
		List<Integer> comparingList = new ArrayList<>();
		comparingList.add(10);
		comparingList.add(100);
		comparingList.add(500);
		comparingList.add(1000);
		comparingList.add(2500);
		comparingList.add(5000);
		comparingList.add(7500);
		comparingList.add(9000);

		for(int criteria = 3; criteria <= 3; criteria++){
			for(Integer comparing : comparingList){
				List<Double> differenceList = new ArrayList<>();
				for(int i = 0; i < 500; i++){
					double difference = getDifferenceRank(numAlternative,criteria,comparing);
					differenceList.add(difference);
				}
				Statistics stats = new Statistics();
				String current = "getDifferenceRank(" + numAlternative + "," + criteria + "," + comparing + ") : ";
				System.out.println(current + " : " + stats.getMean(differenceList));	
			}
		}

	}

	private double getDifferenceRank(int numberAlternatives, int numberCriteria, int numberAlternativesToCompare){
		int numberCuts = 3;
		double minValue = 10.0;
		double maxValue = 20.0;

		ProblemGenerator problem = new ProblemGenerator();
		problem.generateCriteria(numberCriteria, minValue, maxValue, numberCuts);
		problem.generateAlternatives(numberAlternatives);

		ValueFunctionGenerator  vfg = new ValueFunctionGenerator(problem.getCriteria());
		ValueFunction vR = vfg.generateValueFunction();
		LOGGER.debug("ValueFunction R: {}.",vR);

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
		LOGGER.debug("Examples: {}.",exampleAlternatives);

		List<Alternative> alternativesToCompare = new ArrayList<>();
		for(int i = numberAlternativesToCompare; i < alternatives.size(); i++){
			alternativesToCompare.add(alternatives.get(i));
		}
		LOGGER.debug("To compare: {}.",alternativesToCompare);

		UTASTAR utastar = new UTASTAR(problem.getCriteria(), exampleAlternatives);
		utastar.setPrint(false);
		ValueFunction vT = utastar.findValueFunction();
		LOGGER.debug("ValueFunction T: {}.",vT);

		return compare(vR, vT, alternativesToCompare);
	}

	private double compare(ValueFunction vR, ValueFunction vT, List<Alternative> alternatives) {

		List<Alternative> alternativeR = new ArrayList<>(alternatives);
		List<Alternative> alternativeT = new ArrayList<>(alternatives);

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

		List<Double> a1 = new ArrayList<Double>();
		List<Double> a2 = new ArrayList<Double>();
		int differenceRank = 0;
		for(int i = 0;  i < alternatives.size(); i++){
			if(alternativeR.get(i).getId() != alternativeT.get(i).getId()){
				for(int j = 0; j < alternativeT.size(); j++){
					if(alternativeR.get(i).getId() == alternativeT.get(j).getId()){
						differenceRank += Math.abs(i-j);
						a1.add(Double.parseDouble(i+""));
						a2.add(Double.parseDouble(j+""));
						break;

					}
				}
			}
		}

		Statistics stats = new Statistics();
		double kendallTau = 1.0;
		if(differenceRank != 0){
			kendallTau = stats.getKendalTau(a1,a2);
		}
		LOGGER.debug("Difference of rank : ",differenceRank);
		LOGGER.debug("Kendall tau : ",kendallTau);
		
		return kendallTau;
	} 

}