# decision-uta-method
A repository containing the work done during my research about UTA method (Internship at LAMSADE - Dauphine).
> Work in progress 

Upon decompressing the archive, you will get the following structure:
.decision-uta-method
├── src 							<- directory containing Java projects
│   ├── alternative-criteria		<- directory containing alternative-criteria project
│   ├── examples 					<- directory containing all of the examples made
│   ├── lib 						<- directory containing libraries and jar files
│   └── utils 						<- directory containing all utils created
├── docs 							<- directory containing all of the docs produced
│   ├── images 						<- directory containing all of the images used in this repo
│   ├── litterature					<- directory containing all of the literatures found
│   ├── reports						<- directory containing all of the reports made
│   ├── summary-uta.pdf 			<- pdf file explaining the UTA method created in LaTeX
│   ├── summary-uta.tex 			<- LaTeX file that generated the summary-uta.pdf file
│   └── exercices                	<- directory containing exercices made
├── .gitignore 						<- ignoring file
└── README 							<- this file

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

For example, let's generate 4 numbers with 0 digits precision that have the sum of 10: 
#### on windows
```bash
java -cp objs; com.lamsade.utils.GenerateNumbers 4 10  
```

You will have the following result : 
<p align="center">
  <img src="/docs/images/integers.PNG?raw=true" alt="Example result"/>
</p>

<br />

Let's say you want to generate 4 numbers with 2 digits precision that have the sum of 1, you should execute the following command : 
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

If we want to generate 3 criterias and 4 alternatives, you should execute the following command : 
#### on windows
```bash
java -cp objs; com.lamsade.alternativecriteria.Main 3 4 
```

You will have the following result : 
<p align="center">
  <img src="/docs/images/alternative-criteria.PNG?raw=true" alt="Alternative criteria result"/>
</p>

# Example of a java class that will solve a Linear Program
An example of a LP problem: 
<p align="center">
  <img src="/docs/images/example-lp.PNG?raw=true" alt="Alternative criteria result"/>
</p>

Running the examples will involve compiling them, then running them. 

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

You will have the following result : 
<p align="center">
  <img src="/docs/images/example-result-lp.PNG?raw=true" alt="LP result"/>
</p>

# Linear Program for the Choice of Tranportation exercice [UTA]
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

Once you run the program, you will have the following result : 
<p align="center">
  <img src="/docs/images/example_choicetransportation_lp-result.PNG?raw=true" alt="LP result"/>
</p>

