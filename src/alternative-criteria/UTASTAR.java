package com.lamsade.alternativecriteria;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;
import com.lamsade.alternativecriteria.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class UTASTAR {
	
	static { System.loadLibrary("jniortools"); }
	
	//attributes
	private List<Alternative> alternatives;
	private List<Criteria> criterias;
	public static final double SIGMA = 0.05;
	
	//constructors
	public UTASTAR(List<Alternative> alternatives, List<Criteria> criterias){
		this.alternatives = alternatives;
		this.criterias = criterias;
		runAlgorithm();
	}
	
	//mehtod
	public void runAlgorithm(){
		System.out.println("Scale of criterias");
		for(Criteria criteria : criterias){
			System.out.println("	" + criteria.getId() + " --> " + criteria.getScale());
		}
		
		System.out.println();
		System.out.println("Expressing the global value of the action in terms of variables wij");
		for(Alternative alternative : alternatives){
			Map<String, Double> map = new HashMap<>();
			for (int i = 0; i < criterias.size(); i++){
				Criteria criteria = criterias.get(i);
			    for(Entry<String, Double> e : criteria.getMarginalValue(alternative.getCriteriaEvaluation().get(criteria).intValue(), (i+1)).entrySet()) {
			        String val = e.getKey();
			        Double coef = e.getValue();
			        map.put(val, coef);
			    }				
			}
			alternative.setMarginalValueMap(map);
			System.out.println("	" + alternative.getId() + " : " + alternative.getMarginalValueString());
		}
		
		MPSolver solver = new MPSolver("UTASTAR - Solution", MPSolver.OptimizationProblemType.valueOf("GLOP_LINEAR_PROGRAMMING"));
	    if (solver == null) {
	      System.out.println("Could not create solver GLOP_LINEAR_PROGRAMMING");
	      return;
	    }

	    double infinity = MPSolver.infinity();
	    
	    Map<String, MPVariable> errors = new HashMap<>();		
		for(Alternative alternative : alternatives){
		    errors.put("e"+alternative.getId() +"POS",solver.makeNumVar(0.0, infinity, "e"+alternative.getId() +"POS"));
		    errors.put("e"+alternative.getId() +"NEG",solver.makeNumVar(0.0, infinity, "e"+alternative.getId() +"NEG"));
		}

	    MPObjective objective = solver.objective();
	    for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
	       objective.setCoefficient(entry.getValue(), 1);
	    }
	    objective.setMinimization();
		
	    Map<String, MPVariable> variables = new HashMap<>();
		for (int i = 0; i < criterias.size(); i++){
			for(int j = 1; j < criterias.get(i).getScale().size(); j++){
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
		    	
		    constraint.setCoefficient(errors.get("e"+ a1.getId() +"NEG"), 1);
		    constraint.setCoefficient(errors.get("e"+ a1.getId() +"POS"), -1);
		    constraint.setCoefficient(errors.get("e"+ a2.getId() +"POS"), 1);
		    constraint.setCoefficient(errors.get("e"+ a2.getId() +"NEG"), -1);
		   
		}

	    MPConstraint constraint = solver.makeConstraint(1, 1);
	    for (Map.Entry<String, MPVariable> entry : variables.entrySet()){
	    	constraint.setCoefficient(entry.getValue(), 1);
	    }
	    
	    final MPSolver.ResultStatus resultStatus = solver.solve();

 		String model = solver.exportModelAsLpFormat(false);
      	System.out.println(model);

	    // Check that the problem has an optimal solution.
	    if (resultStatus != MPSolver.ResultStatus.OPTIMAL) {
	      System.err.println("The problem does not have an optimal solution!");
	      return;
	    }

	    if (!solver.verifySolution(/*tolerance=*/1e-7, /*logErrors=*/true)) {
	      System.err.println("The solution returned by the solver violated the"
	                         + " problem constraints by at least 1e-7");
	      return;
	    }

	    System.out.println("Problem solved in " + solver.wallTime() + " milliseconds");
	    System.out.println("Optimal objective value = " + solver.objective().value());

	    for (Map.Entry<String, MPVariable> entry : variables.entrySet()){
	        System.out.println(entry.getKey() + " = " + entry.getValue().solutionValue());
	    }
	    for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
	        System.out.println(entry.getKey() + " = " + entry.getValue().solutionValue());
	    }
		
	}

}