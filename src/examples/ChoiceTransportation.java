package com.lamsade.lp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

public class ChoiceTransportation {
  static { System.loadLibrary("jniortools"); }

  private static void runLinearProgrammingExample(String solverType, boolean printModel) {
  MPSolver solver = new MPSolver("LinearProgrammingExample", MPSolver.OptimizationProblemType.valueOf(solverType));
    
    if (solver == null) {
      System.out.println("Could not create solver " + solverType);
      return;
    }
    
    double infinity = MPSolver.infinity();
    double sigma = 0.05;
    
    Map<String, MPVariable> variables = new HashMap<>();
    variables.put("w11",solver.makeNumVar(0.0, infinity, "w11"));
    variables.put("w12",solver.makeNumVar(0.0, infinity, "w12"));
    variables.put("w21",solver.makeNumVar(0.0, infinity, "w21"));
    variables.put("w22",solver.makeNumVar(0.0, infinity, "w22"));
    variables.put("w23",solver.makeNumVar(0.0, infinity, "w23"));
    variables.put("w31",solver.makeNumVar(0.0, infinity, "w31"));
    variables.put("w32",solver.makeNumVar(0.0, infinity, "w32"));
    variables.put("w33",solver.makeNumVar(0.0, infinity, "w33"));
    
    Map<String, MPVariable> errors = new HashMap<>();
    errors.put("eRER",solver.makeNumVar(0.0, infinity, "eRER"));
    errors.put("eMETRO1",solver.makeNumVar(0.0, infinity, "eMETRO1"));
    errors.put("eMETRO2",solver.makeNumVar(0.0, infinity, "eMOTRE2"));
    errors.put("eBUS",solver.makeNumVar(0.0, infinity, "eBUS"));
    errors.put("eTAXI",solver.makeNumVar(0.0, infinity, "eTAXI"));
    
    //Objectif : Minimizing eRER + eMETRO1 + eMETRO2 + eBUS + eTAXI
    MPObjective objective = solver.objective();
  for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
    objective.setCoefficient(entry.getValue(), 1);
  }
    objective.setMinimization();

    List<MPConstraint> contraints = new ArrayList<>();
    MPConstraint c0 = solver.makeConstraint(sigma, infinity);
    c0.setCoefficient(variables.get("w12"), 0.07);
    c0.setCoefficient(variables.get("w23"), 1);
    c0.setCoefficient(variables.get("w32"), -1);
    c0.setCoefficient(errors.get("eRER"), 1);
    c0.setCoefficient(errors.get("eMETRO1"), -1);
    contraints.add(c0);
    
    MPConstraint c1 = solver.makeConstraint(0,0);
    c1.setCoefficient(variables.get("w12"), -0.14);
    c1.setCoefficient(variables.get("w31"), 1);
    c1.setCoefficient(variables.get("w32"), 1);
    c1.setCoefficient(errors.get("eMETRO1"), 1);
    c1.setCoefficient(errors.get("eMETRO2"), -1);
    contraints.add(c1);
    
    MPConstraint c2 = solver.makeConstraint(sigma, infinity);
    c2.setCoefficient(variables.get("w12"), 0.29);
    c2.setCoefficient(variables.get("w21"), 1);
    c2.setCoefficient(variables.get("w22"), 1);
    c2.setCoefficient(errors.get("eMETRO2"), 1);
    c2.setCoefficient(errors.get("eBUS"), -1);
    contraints.add(c2);
    
    MPConstraint c3 = solver.makeConstraint(sigma, infinity);
    c3.setCoefficient(variables.get("w11"), 1);
    c3.setCoefficient(variables.get("w12"), 0.71);
    c3.setCoefficient(variables.get("w21"), -1);
    c3.setCoefficient(variables.get("w31"), -1);
    c3.setCoefficient(variables.get("w32"), -1);
    c3.setCoefficient(variables.get("w33"), -1);
    c3.setCoefficient(errors.get("eBUS"), 1);
    c3.setCoefficient(errors.get("eTAXI"), -1);
    contraints.add(c3);
    
    MPConstraint c4 = solver.makeConstraint(1, 1);
    for (Map.Entry<String, MPVariable> entry : variables.entrySet()){
      c4.setCoefficient(variables.get(entry.getValue()), 1);
    }
    contraints.add(c4);

    System.out.println("Number of variables = " + solver.numVariables());
    System.out.println("Number of constraints = " + solver.numConstraints());

    if (printModel) {
      String model = solver.exportModelAsLpFormat(false);
      System.out.println(model);
    }

    final MPSolver.ResultStatus resultStatus = solver.solve();

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

    // The objective value of the solution.
    System.out.println("Optimal objective value = " + solver.objective().value());

    // The value of each variable in the solution.
    for (Map.Entry<String, MPVariable> entry : variables.entrySet()){
      System.out.println(entry.getKey() + " = " + entry.getValue().solutionValue());
    }

  }

  public static void main(String[] args) throws Exception {
    runLinearProgrammingExample("GLOP_LINEAR_PROGRAMMING", true);
  }

}