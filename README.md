# decision-uta-method
A repository containing the work done during my research about UTA method (Internship at LAMSADE - Dauphine).

###### N.B. Work in progress.

Upon decompressing the archive, you will get the following structure:
```
.decision-uta-method
 |-- src                      			<- directory containing Java projects
 |   `-- alternative-criteria			<- directory containing alternative-criteria project
 |   	`-- Alternative				<- java class representing the Alternative model
 |   	`-- Criteria				<- java class representing the Criteria model
 |   	`-- Main				<- java class for testing the project
 |   `-- examples             			<- directory containing examples made
 |   	`-- ChoiceTransportation		<- java class representing the LP of the Choice of Transportation
 |   	`-- LinearProgramming			<- java class representing a LP example
 |   	`-- Utils				<- java class to generate a list of doubles that have a sum
 |   `-- lib                  			<- directory containing libraries and jar files
 |   `-- objs                 			<- directory containing Java compiled class (*.class)
 |-- docs                     			<- directory containing all of the docs produced
 |   `-- reports              			<- directory containing all of the reports made
 |   `-- images               			<- directory containing all of the images used in this repo
 |   `-- summary-uta.pdf      			<- pdf file explaining the UTA method created in LaTeX
 |   `-- summary-uta.tex      			<- LaTeX file that generated the summary-uta.pdf file 
 |-- .gitignore               			<- ignoring file
 |-- README                   			<- this file
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
This class will alow to generate numbers (doubles or integer) that have a target Sum. 

To build the class Utils, you should execute the following command: 
#### on windows
```bash
javac -d objs utils/GenerateNumbers.java 
```

When running the program, you can insert 3 arguments : 
```java
int counter; // default value : 4 
int targetSum; // default value : 10
int digitsDecimal; // precision digits. default value : 0  
```

For example if we want to generate 4 numbers with 0 digits precision that have the sum of 10 we should execute the following command : 
#### on windows
```bash
java -cp objs; com.lamsade.utils.GenerateNumbers 4 10  
```

We will have the following result : 
<p align="center">
  <img src="/docs/images/integers.PNG?raw=true" alt="Example result"/>
</p>

<br />

If we want to generate 4 numbers with 2 digits precision that have the sum of 1 we should execute the following command : 
#### on windows
```bash
java -cp objs; com.lamsade.utils.GenerateNumbers 4 1 2
```

We will have the following result : 
<p align="center">
  <img src="/docs/images/doubles.PNG?raw=true" alt="Example result"/>
</p>


# Alternative - Criteria

#### on windows
```bash
javac -d objs alternative-criteria/Alternative.java alternative-criteria/Criteria.java alternative-criteria/Main.java 
java -cp objs; com.lamsade.alternativecriteria.Main 4 10 // Generate 4 criterias and 10 alternatives
```
