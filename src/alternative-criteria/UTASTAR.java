package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public class UTASTAR {
	static { System.loadLibrary("jniortools"); }

	//Attibutes
	private List<Criterion> criteria;
	private List<Alternative> alternatives;
	public static final double SIGMA = 0.05;

	//Constructor
	public UTASTAR(List<Criterion> criteria, List<Alternative> alternatives){
		this.criteria = criteria;
		this.alternatives = alternatives;
	}

	//Getters
	public List<Criterion> getCriteria() {
		return criteria;
	}
	public List<Alternative> getAlternatives() {
		return alternatives;
	}

	//Methods
	public ValueFunction findValueFunction(){

		long start = System.currentTimeMillis();
		System.out.println("Scale of criterias");
		for(Criterion criterion : criteria){
			System.out.println("	" + criterion.getName() + " --> " + criterion.getScale());
		}

		System.out.println();
		System.out.println("Expressing the global value of the alternatives in terms of variables wij");
		for(Alternative alternative : alternatives){
			Map<String, Double> map = new HashMap<>();
			for (int i = 0; i < criteria.size(); i++){
				Criterion criterion = criteria.get(i);
				for(Entry<String, Double> e : getPartialMarginalValue(criterion, alternative.getEvaluations().get(criterion).intValue(), (i+1)).entrySet()) {
					String val = e.getKey();
					Double coef = e.getValue();
					map.put(val, coef);
				}				
			}
			alternative.setMarginalValue(map);
			String marginalValueStr = "";
			for(Entry<String, Double> e : alternative.getMarginalValue().entrySet()) {
				if(marginalValueStr.length() > 0) {marginalValueStr += " + ";}
				marginalValueStr += e.getValue() + " " + e.getKey();
			}
			System.out.println("	" + alternative.getName() + " : " + marginalValueStr);
		}
		 
		MPSolver solver = new MPSolver("UTASTAR - Solver", MPSolver.OptimizationProblemType.valueOf("GLOP_LINEAR_PROGRAMMING"));
		
		double infinity = MPSolver.infinity();
		Map<String, MPVariable> errors = new HashMap<>();		
		for(Alternative alternative : alternatives){
			errors.put("e"+alternative.getName() +"POS",solver.makeNumVar(0.0, infinity, "e"+alternative.getName() +"POS"));
			errors.put("e"+alternative.getName() +"NEG",solver.makeNumVar(0.0, infinity, "e"+alternative.getName() +"NEG"));
		}

		MPObjective objective = solver.objective();
		String objStr = "";
		for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
			objStr += entry.getValue() + " + ";
			objective.setCoefficient(entry.getValue(), 1);
		}
		objective.setMinimization();
		objStr = "Minimization : " + objStr;
		
		Map<String, MPVariable> variables = new HashMap<>();
		for (int i = 0; i < criteria.size(); i++){
			for(int j = 1; j < criteria.get(i).getScale().size(); j++){
				variables.put("w"+(i+1)+j,solver.makeNumVar(0.0, infinity, "w"+(i+1)+j));
			}
		}

		System.out.println();
		System.out.println("Introducing 2 errors functions by writing for each pair of consecutive actions");
		for (int i = 0; i < alternatives.size() - 1; i++){
			MPConstraint constraint = solver.makeConstraint(SIGMA, infinity);
			Alternative a1 = alternatives.get(i);
			Alternative a2 = alternatives.get(i+1);
			for(Entry<String, Double> e1 : a1.getMarginalValue().entrySet()) {
				boolean found = false;
				for(Entry<String, Double> e2 : a2.getMarginalValue().entrySet()) {
					if(e1.getKey().equals(e2.getKey())){
						constraint.setCoefficient(variables.get(e1.getKey()), (e1.getValue() - e2.getValue()));
						found = true; break;
					}
				}
				if(!found){
					constraint.setCoefficient(variables.get(e1.getKey()), (e1.getValue()));
				}
			}
			for(Entry<String, Double> e2 : a2.getMarginalValue().entrySet()) {
				boolean found = false;
				for(Entry<String, Double> e1 : a1.getMarginalValue().entrySet()) {
					if(e1.getKey().equals(e2.getKey())){
						found = true;
						break;
					}
				}
				if(!found){
					constraint.setCoefficient(variables.get(e2.getKey()), (-e2.getValue()));
				}
			}

			constraint.setCoefficient(errors.get("e"+ a1.getName() +"NEG"), 1); 
			constraint.setCoefficient(errors.get("e"+ a1.getName() +"POS"), -1);
			constraint.setCoefficient(errors.get("e"+ a2.getName() +"POS"), 1);
			constraint.setCoefficient(errors.get("e"+ a2.getName() +"NEG"), -1);
		}
		
		MPConstraint constraint = solver.makeConstraint(1, 1);
		for (Map.Entry<String, MPVariable> entry : variables.entrySet()){
			constraint.setCoefficient(entry.getValue(), 1);
		}
		
		final MPSolver.ResultStatus resultStatus = solver.solve();
		
		String model = solver.exportModelAsLpFormat(false);
		if (resultStatus != MPSolver.ResultStatus.OPTIMAL) {System.err.println("The problem does not have an optimal solution!"); return null;}
		if (!solver.verifySolution(/*tolerance=*/1e-7, /*logErrors=*/true)) {System.err.println("The solution returned by the solver violated the problem constraints by at least 1e-7"); return null;}

		System.out.println("Optimal objective value = " + solver.objective().value());

		System.out.println(model);
		for (Map.Entry<String, MPVariable> entry : variables.entrySet()){
			System.out.println(entry.getKey() + " = " + entry.getValue().solutionValue());
		}
		for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
			System.out.println(entry.getKey() + " = " + entry.getValue().solutionValue());
		}

		List<PartialValueFunction> partialValueFunctions = new ArrayList<>();
		for(int i = 0; i < criteria.size(); i++){
			PartialValueFunction partialValueFunction = new PartialValueFunction(criteria.get(i));
			for(int j = 0 ; j < criteria.get(i).getScale().size(); j++){
				if(j==0){
					partialValueFunction.getIntervals().add(new Point(criteria.get(i).getScale().get(j), 0));
				}else{
					String current = "w" + (i+1) + (j);
					partialValueFunction.getIntervals().add(new Point(criteria.get(i).getScale().get(j), variables.get(current).solutionValue()));
				}
			}
			partialValueFunctions.add(partialValueFunction);
		}
		
		ValueFunction valueFunction = new ValueFunction(partialValueFunctions); 
		long time = System.currentTimeMillis() - start;
		System.out.println("Problem solved in : " + time + " milliseconds");
		
		return valueFunction;
	}
	public Map<String, Double> getPartialMarginalValue(Criterion criterion, int value, int criteriaId){
		List<Double> scale = criterion.getScale();
		Collections.sort(scale);
		Map<String, Double> result = new HashMap<>();

		int i = 0;
		while(value > scale.get(i)){i++;}

		for(int x = 1 ; x <= i; x++){
			double coef = 1;
			if((x == i) && (scale.get(i)!=value)){
				coef = 1 - ((scale.get(x)- value) / (scale.get(i) - scale.get((i-1))));
			}
			result.put("w" + criteriaId + x, coef);
		}

		return result;

	}
}