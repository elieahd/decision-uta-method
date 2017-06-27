package com.lamsade.lp;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.util.*;

public class ChoiceTransportation {
  static { System.loadLibrary("jniortools"); }

  private static void runLinearProgrammingExample(String solverType, boolean printModel) {
    MPSolver solver = new MPSolver("Choice of transportation", MPSolver.OptimizationProblemType.valueOf(solverType));
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
    errors.put("eRER+",solver.makeNumVar(0.0, infinity, "eRER+"));
    errors.put("eRER-",solver.makeNumVar(0.0, infinity, "eRER-"));
    errors.put("eMETRO1+",solver.makeNumVar(0.0, infinity, "eMETRO1+"));
    errors.put("eMETRO1-",solver.makeNumVar(0.0, infinity, "eMETRO1-"));
    errors.put("eMETRO2+",solver.makeNumVar(0.0, infinity, "eMETRO2+"));
    errors.put("eMETRO2-",solver.makeNumVar(0.0, infinity, "eMETRO2-"));
    errors.put("eTAXI+",solver.makeNumVar(0.0, infinity, "eTAXI+"));
    errors.put("eTAXI-",solver.makeNumVar(0.0, infinity, "eTAXI-"));
    errors.put("eBUS+",solver.makeNumVar(0.0, infinity, "eBUS+"));
    errors.put("eBUS-",solver.makeNumVar(0.0, infinity, "eBUS-"));

    // Minimize Sum of Errors
    MPObjective objective = solver.objective();
    for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
       objective.setCoefficient(entry.getValue(), 1);
    }
    objective.setMinimization();

    // Delta(RER,METRO1) = 0.07 w12 + w23 + - w32 - eRER+ + eRER- + eMETRO1+ - eMETRO1- >= sigma
    MPConstraint c1 = solver.makeConstraint(sigma, infinity);
    c1.setCoefficient(variables.get("w12"), 0.07);
    c1.setCoefficient(variables.get("w23"), 1);
    c1.setCoefficient(variables.get("w32"), -1);
    c1.setCoefficient(errors.get("eRER-"), 1);
    c1.setCoefficient(errors.get("eRER+"), -1);
    c1.setCoefficient(errors.get("eMETRO1+"), 1);
    c1.setCoefficient(errors.get("eMETRO1-"), -1);

    // Delta(METRO1,METRO2) = -0.14 w12 + w31 + w32 - METRO1+ + eMETRO1- + eMETRO2+ - eMETRO2- = 0
    MPConstraint c2 = solver.makeConstraint(0, 0);
    c2.setCoefficient(variables.get("w12"), -0.14);
    c2.setCoefficient(variables.get("w31"), 1);
    c2.setCoefficient(variables.get("w32"), 1);
    c2.setCoefficient(errors.get("eMETRO1-"), 1);
    c2.setCoefficient(errors.get("eMETRO1+"), -1);
    c2.setCoefficient(errors.get("eMETRO2+"), 1);
    c2.setCoefficient(errors.get("eMETRO2-"), -1);

    // Delta(METRO2,BUS) = 0.29 w12 + w21 + w22 - METRO2+ + eMETRO2- + eBUS+ - eBUS- >= sigma
    MPConstraint c3 = solver.makeConstraint(sigma, infinity);
    c3.setCoefficient(variables.get("w12"), 0.29);
    c3.setCoefficient(variables.get("w21"), 1);
    c3.setCoefficient(variables.get("w22"), 1);
    c3.setCoefficient(errors.get("eMETRO2-"), 1);
    c3.setCoefficient(errors.get("eMETRO2+"), -1);
    c3.setCoefficient(errors.get("eBUS+"), 1);
    c3.setCoefficient(errors.get("eBUS-"), -1);

    // Delta(BUS,TAXI) = w11 + 0.71 w21 - w22 - w31 - w32 - w33 - eBUS+ + eBUS- + eTAXI+ - eTAXI- >= sigma
    MPConstraint c4 = solver.makeConstraint(sigma, infinity);
    c4.setCoefficient(variables.get("w11"), 1);
    c4.setCoefficient(variables.get("w21"), 0.71);
    c4.setCoefficient(variables.get("w22"), -1);
    c4.setCoefficient(variables.get("w31"), -1);
    c4.setCoefficient(variables.get("w32"), -1);
    c4.setCoefficient(variables.get("w33"), -1);
    c4.setCoefficient(errors.get("eBUS-"), 1);
    c4.setCoefficient(errors.get("eBUS+"), -1);
    c4.setCoefficient(errors.get("eTAXI+"), 1);
    c4.setCoefficient(errors.get("eTAXI-"), -1);
    
    MPConstraint c5 = solver.makeConstraint(1, 1);
    for (Map.Entry<String, MPVariable> entry : variables.entrySet()){
       c5.setCoefficient(entry.getValue(), 1);
    }

    System.out.println("Number of variables = " + solver.numVariables());
    System.out.println("Number of constraints = " + solver.numConstraints());

    if (printModel) {
      String model = solver.exportModelAsLpFormat(false);
      System.out.println(model);
    }

    final MPSolver.ResultStatus resultStatus = solver.solve();

    // Check that the problem has an optimal solution.
    if (resultStatus != MPSolver.ResultStatus.OPTIMAL) {
      System.err.println("The problem does not have an optimal solution!");
      return;
    }

    // Verify that the solution satisfies all constraints (when using solvers
    // others than GLOP_LINEAR_PROGRAMMING, this is highly recommended!).
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

    /*
    final double[] activities = solver.computeConstraintActivities();

    System.out.println("Advanced usage:");
    System.out.println("Problem solved in " + solver.iterations() + " iterations");
    System.out.println("x1: reduced cost = " + x1.reducedCost());
    System.out.println("x2: reduced cost = " + x2.reducedCost());
    System.out.println("x3: reduced cost = " + x3.reducedCost());
    System.out.println("c0: dual value = " + c0.dualValue());
    System.out.println("    activity = " + activities[c0.index()]);
    System.out.println("c1: dual value = " + c1.dualValue());
    System.out.println("    activity = " + activities[c1.index()]);
    System.out.println("c2: dual value = " + c2.dualValue());
    System.out.println("    activity = " + activities[c2.index()]);
  	*/
  }

  public static void main(String[] args) throws Exception {
    runLinearProgrammingExample("GLOP_LINEAR_PROGRAMMING", true);
  }

}