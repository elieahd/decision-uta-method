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

public class MainSimulation {

	public static void main(String[] args) {
		MainSimulation main = new MainSimulation();
		main.simulation();
	}
	
	private void simulation(){
		List<Double> differenceList = new ArrayList<>();
		System.out.println("Start of Simulation");
		for(int i = 0; i < 10; i++){
			double difference = getDifferenceRank(1000,3,10);
			System.out.println("#" + i + " : " + difference);
			differenceList.add(difference);
		}
		System.out.println("End of Simulation");
		Statistics stats = new Statistics();
		System.out.println("Mean : " + stats.getMean(differenceList));
		System.out.println("Standard Deviation : " + stats.getStd(differenceList));
	}

	private int getDifferenceRank(int numberAlternatives, int numberCriteria, int numberAlternativesToCompare){
		int numberCuts = 3;
		double minValue = 10.0;
		double maxValue = 20.0;
		
		ValueFunctionGenerator  vfg = new ValueFunctionGenerator(numberCriteria,numberCuts);
		ValueFunction vR = vfg.generateValueFunction();

		ProblemGenerator problem = new ProblemGenerator();
		problem.generateCriteria(numberCriteria, minValue, maxValue, numberCuts);
		problem.generateAlternatives(numberAlternatives);

		List<Alternative> alternatives = problem.getAlternatives();
		Collections.sort(alternatives, new Comparator<Alternative>() {
			@Override
			public int compare(final Alternative a1, final Alternative a2) {
				return ((Double)vR.getValue(a1)).compareTo(vR.getValue(a2));
			}
		});
		
		List<Alternative> alternativesToCompare = new ArrayList<>();
		for(int i = 0; i < numberAlternativesToCompare; i++){
			alternativesToCompare.add(alternatives.get(i));
		}

		UTASTAR utastar = new UTASTAR(problem.getCriteria(), alternativesToCompare);
		utastar.setPrint(false);
		ValueFunction vT = utastar.findValueFunction();

		return compare(vR, vT, alternativesToCompare);
	}

	private int compare(ValueFunction vR, ValueFunction vT, List<Alternative> alternatives) {

		List<Alternative> alternativeR = alternatives;
		Collections.sort(alternativeR, new Comparator<Alternative>() {
			@Override
			public int compare(final Alternative a1, final Alternative a2) {
				return ((Double)vR.getValue(a1)).compareTo(vR.getValue(a2));
			}
		});

		List<Alternative> alternativeT = alternatives;
		Collections.sort(alternativeT, new Comparator<Alternative>() {
			@Override
			public int compare(final Alternative a1, final Alternative a2) {
				return ((Double)vT.getValue(a1)).compareTo(vT.getValue(a2));
			}
		});

//		System.out.println("Alternative R : " + alternativeR);
//		System.out.println("Alternative T : " + alternativeT);

		int differenceRank = 0;
		for(int i = 0;  i < alternatives.size(); i++){
			if(alternativeR.get(i).getId() != alternativeT.get(i).getId()){
				for(int j = 0; j < alternativeT.size(); j++){
					if(alternativeR.get(i).getId() == alternativeT.get(j).getId()){
						differenceRank += Math.abs(i-j);
						break;
					}
				}
			}
		}
		return differenceRank;
	} 

}