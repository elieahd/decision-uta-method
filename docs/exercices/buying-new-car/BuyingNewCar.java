package com.lamsade.lp;

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

import java.util.*;

public class BuyingNewCar {
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

    Map<String, MPVariable> errors = new HashMap<>();
    errors.put("ec4+",solver.makeNumVar(0.0, infinity, "ec4+"));
    errors.put("ec4-",solver.makeNumVar(0.0, infinity, "ec4-"));
    errors.put("ep208+",solver.makeNumVar(0.0, infinity, "ep208+"));
    errors.put("ep208-",solver.makeNumVar(0.0, infinity, "ep208-"));
    errors.put("ep308+",solver.makeNumVar(0.0, infinity, "ep308+"));
    errors.put("ep308-",solver.makeNumVar(0.0, infinity, "ep308-"));
    errors.put("ens+",solver.makeNumVar(0.0, infinity, "ens+"));
    errors.put("ens-",solver.makeNumVar(0.0, infinity, "ens-"));

    // Minimize Sum of Errors
    MPObjective objective = solver.objective();
    for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
       objective.setCoefficient(entry.getValue(), 1);
    }
    objective.setMinimization();

    MPConstraint c3 = solver.makeConstraint(sigma, infinity);
    c3.setCoefficient(variables.get("w12"), -0.6);
    c3.setCoefficient(variables.get("w23"), 1);
    c3.setCoefficient(variables.get("w31"), 0.5);
    c3.setCoefficient(variables.get("w32"), 0.5);
    c3.setCoefficient(errors.get("ens-"), 1);
    c3.setCoefficient(errors.get("ens+"), -1);
    c3.setCoefficient(errors.get("ec4+"), 1);
    c3.setCoefficient(errors.get("ec4-"), -1);

    MPConstraint c1 = solver.makeConstraint(sigma, infinity);
    c1.setCoefficient(variables.get("w11"), 1);
    c1.setCoefficient(variables.get("w12"), 1);
    c1.setCoefficient(variables.get("w22"), 1);
    c1.setCoefficient(variables.get("w31"), -0.5);
    c1.setCoefficient(errors.get("ec4-"), 1);
    c1.setCoefficient(errors.get("ec4+"), -1);
    c1.setCoefficient(errors.get("ep208+"), 1);
    c1.setCoefficient(errors.get("ep208-"), -1);

    MPConstraint c2 = solver.makeConstraint(sigma, infinity);
    c2.setCoefficient(variables.get("w21"), 1);
    c2.setCoefficient(variables.get("w11"), -1);
    c2.setCoefficient(variables.get("w12"), -0.3);
    c2.setCoefficient(errors.get("ep208-"), 1);
    c2.setCoefficient(errors.get("ep208+"), -1);
    c2.setCoefficient(errors.get("ep308+"), 1);
    c2.setCoefficient(errors.get("ep308-"), -1);


    
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
    for (Map.Entry<String, MPVariable> entry : errors.entrySet()){
        System.out.println(entry.getKey() + " = " + entry.getValue().solutionValue());
    }
  }

  public static void main(String[] args) throws Exception {
    runLinearProgrammingExample("GLOP_LINEAR_PROGRAMMING", true);
  }

}