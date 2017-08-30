package io.github.oliviercailloux.uta_calculator.view;

import io.github.oliviercailloux.uta_calculator.model.Alternative;
import io.github.oliviercailloux.uta_calculator.model.PartialValueFunction;
import io.github.oliviercailloux.uta_calculator.model.ProblemGenerator;
import io.github.oliviercailloux.uta_calculator.model.UTASTAR;
import io.github.oliviercailloux.uta_calculator.model.ValueFunction;

import java.util.Scanner;

public class MainRandomProblem {

	Scanner clavier = new Scanner(System.in);
	
	public static void main(String[] args) {
		MainRandomProblem main = new MainRandomProblem();
		System.out.println("Criterion information");
		int numbersCriteria= main.askUserInteger("	Number of criterion : ");
		double minValueCriterion = main.askUserDouble("	Min value of a criterion : ");
		double maxValueCriterion = main.askUserDouble("	Max value of a criterion : ");
		int cuts = main.askUserInteger("	Number of cuts of a criterion : ");
		int numbersAlternative =  main.askUserInteger("Number of alternatives : ");
		
		ProblemGenerator p1 = main.generateRandom(numbersCriteria, minValueCriterion, maxValueCriterion, cuts, numbersAlternative);
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

	private double askUserDouble(String question) {
		System.out.print(question);
		while (!clavier.hasNextDouble()){
			System.err.println("Error : please insert a double");
			System.out.print(question);
			clavier.next();
		}
		return clavier.nextDouble();
	}
	
	private int askUserInteger(String question) {
		System.out.print(question);
		while (!clavier.hasNextInt()){
			System.err.println("Error : please insert an integer");
			System.out.print(question);
			clavier.next();
		}
		return clavier.nextInt();
	}

	private ProblemGenerator generateRandom(int numbersCriteria, double minValueCriterion, double maxValueCriterion, int cuts, int numbersAlternative) {
		ProblemGenerator problem = new ProblemGenerator();
		problem.generateCriteria(numbersCriteria, minValueCriterion, maxValueCriterion, cuts);
		problem.generateAlternatives(numbersAlternative);
		return problem;
	}

}