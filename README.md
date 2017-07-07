# decision-uta-method
A repository containing the work done during my research about UTA method (Internship at LAMSADE - Dauphine).

###### Work in progress.

Upon decompressing the archive, you will get the following structure:
```
.decision-uta-method
 |-- src                      			<- directory containing Java projects
 |   `-- alternative-criteria			<- directory containing alternative-criteria project
 |   	`-- Alternative				<- java class representing the Alternative model
 |   	`-- Criteria				<- java class representing the Criteria model
 |   	`-- Main				<- java class for testing the project
 |   `-- utils             			<- directory containing all utils created
 |   	`-- GenerateNumbers			<- java class that will generate numbers with a targetSum
 |   `-- examples             			<- directory containing examples made
 |   	`-- ChoiceTransportation		<- java class representing the LP of the Choice of Transportation
 |   	`-- LinearProgramming			<- java class representing a LP example
 |   `-- lib                  			<- directory containing libraries and jar files
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
  
# Utils - Generate Numbers
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

For example if we want to generate 4 numbers with 0 digits precision that have the sum of 10, you should execute the following command : 
#### on windows
```bash
java -cp objs; com.lamsade.utils.GenerateNumbers 4 10  
```

You will have the following result : 
<p align="center">
  <img src="/docs/images/integers.PNG?raw=true" alt="Example result"/>
</p>

<br />

If we want to generate 4 numbers with 2 digits precision that have the sum of 1, you should execute the following command : 
#### on windows
```bash
java -cp objs; com.lamsade.utils.GenerateNumbers 4 1 2
```

You will have the following result : 
<p align="center">
  <img src="/docs/images/doubles.PNG?raw=true" alt="Example result"/>
</p>

# Alternative - Criteria
This a the class diagram of this project : 
<p align="center">
  <img src="/docs/images/alternative-criteria class diagram.png?raw=true" alt="Alternative Criteria Class Diagram"/>
</p>

To build the class of this program, you should execute the following command: 
#### on windows
```bash
javac -d objs alternative-criteria/Alternative.java alternative-criteria/Criteria.java alternative-criteria/GenerateNumbers.java alternative-criteria/Main.java    
```

When running the program, you can insert 2 arguments : 
```java
int numCriteria; // default value : 3 
int numAlternative; // default value : 10
```

If we want to generate 4 criterias and 9 alternatives, you should execute the following command : 
#### on windows
```bash
java -cp objs; com.lamsade.alternativecriteria.Main 3 4 
```

You will have the following result : 
<p align="center">
  <img src="/docs/images/alternative-criteria.PNG?raw=true" alt="Alternative criteria result"/>
</p>

# Examples of a simple Linear Program
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

# Examples of the Linear Program for the Choice of Tranportation exercice [UTA]
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