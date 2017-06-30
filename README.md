# decision-uta-method
A repository containing the work done during my research about UTA method (Internship at LAMSADE - Dauphine).

###### N.B. Work in progress.

Upon decompressing the archive, you will get the following structure:
```
.decision-uta-method
 |-- src                      <- directory containing Java projects
 |   `-- examples             <- directory containing examples made
 |   `-- lib                  <- directory containing libraries and jar files
 |   `-- objs                 <- directory containing Java compiled class (*.class)
 |-- docs                     <- directory containing all of the docs produced
 |   `-- reports              <- directory containing all of the reports made
 |   `-- images               <- directory containing all of the images used in the summary-uta document
 |   `-- summary-uta.pdf      <- pdf file explaining the UTA method created in LaTeX
 |   `-- summary-uta.tex      <- LaTeX file that generated the summary-uta.pdf file 
 |-- README                   <- this file
```

# UTA
The UTA method is used to solve a multi-criteria problem. It build a utility function based on the preferences of the DM and it consist in solving a linear program (LP).

An improved version of the UTA is the UTASTAR. In UTA we used a single error in UTASTAR we use a double positive error function. The updated version has performed better than the regular method. 
  
# Examples
Running the examples will involve compiling them, then running them. 

For the LinearProgramming example, you should run the following commands : 

#### on unix
```bash
javac -d objs -cp lib/com.google.ortools.jar:lib/protobuf.jar examples/LinearProgramming.java
java -Djava.library.path=lib -cp objs:lib/com.google.ortools.jar com.lamsade.lp.LinearProgramming
```

#### on windows
```bash
javac -d objs -cp lib/com.google.ortools.jar;lib/protobuf.jar examples/LinearProgramming.java
java -Djava.library.path=lib -cp objs;lib/com.google.ortools.jar com.lamsade.lp.LinearProgramming
```

For the ChoiceTransportation exercice, you should run the following commands : 

#### on unix
```bash
javac -d objs -cp lib/com.google.ortools.jar:lib/protobuf.jar examples/ChoiceTransportation.java
java -Djava.library.path=lib -cp objs:lib/com.google.ortools.jar com.lamsade.lp.ChoiceTransportation
```

#### on windows
```bash
javac -d objs -cp lib/com.google.ortools.jar;lib/protobuf.jar examples/ChoiceTransportation.java
java -Djava.library.path=lib -cp objs;lib/com.google.ortools.jar com.lamsade.lp.ChoiceTransportation
```

# Utils
Let's say we want to generate random x doubles that will have the sum of 1, you should run the following commands : 

#### on windows
```bash
javac -d objs examples/Utils.java
java -cp objs; com.lamsade.utils.Utils 4 2 // Generate 4 numbers with 2 decimal digits 
```

# Alternative - Criteria

#### on windows
```bash
javac -d objs alternative-criteria/Alternative.java alternative-criteria/Criteria.java alternative-criteria/Main.java 
java -cp objs; com.lamsade.alternativecriteria.Main 4 10 // Generate 4 criterias and 10 alternatives
```
