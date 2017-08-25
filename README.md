# decision-uta-method
A repository containing the work done during my research about UTA method (Internship at LAMSADE - Dauphine).
> Work in progress 

Upon decompressing the archive, you will get the following structure:
```
.decision-uta-method
├── diagrams                            <- directory containing diagrams used
├── uta-calculator                      <- directory containing Java projects
├── docs                                <- directory containing all of the docs produced
│   ├── images                          <- directory containing all of the images used in this repo
│   ├── reports                         <- directory containing all of the reports made
│   ├── internship-report.pdf           <- pdf file explaining all the stuff done during the internship
│   └── internship-report.tex           <- LaTeX file that generated the internship-report.pdf file
├── .gitignore                          <- ignoring file
└── README                              <- this file
```

# UTA
The UTA method is used to solve a multi-criteria problem. It build a utility function based on the preferences of the DM and it consist in solving a linear program (LP).

An improved version of the UTA is the UTASTAR. In UTA we used a single error in UTASTAR we use a double positive error function. The updated version has performed better than the regular method. 

In the internship-report, you will find a section that explain the UTA method, illustrated with an example. 

# UTA Calculator - Criteria
You should install the following jar file: com.google.ortools.jar and protobuf.jar. You can find them in the lib folder and execute the 2 following commands: 

#### on windows
```bash
mvn install:install-file -Dfile=uta-calculator\lib\com.google.ortools.jar -DgroupId=com.google.ortools -DartifactId=ortools -Dversion=2015.08 -Dpackaging=jar
mvn install:install-file -Dfile=uta-calculator\lib\protobuf.jar -DgroupId=protobuf -DartifactId=protobuf -Dversion=3.3.0 -Dpackaging=jar
```

After installing the 2 libraries, you should specify in the Eclipse the location of the Native Library Location. By going to the properties of the project > Java Build Path > Source Tabulation and Editing the location of Native library like the image below.
<p align="center">
  <img src="/docs/images/readme/Capture.PNG?raw=true" alt="step 1"/>
</p>
And then specify the location of the library folder by clicking on the External Folder button 
<p align="center">
  <img src="/docs/images/readme/Capture2.PNG?raw=true" alt="step 2"/>
</p>
And finally navigate through the repository and select the folder lib
<p align="center">
  <img src="/docs/images/readme/Capture3.PNG?raw=true" alt="step 3"/>
</p>


To build the class of this program, you should execute the following command: 
#### on windows
```bash
mvn compile
```

# Example of a java class that will solve a Linear Program
An example of a LP problem: 
<p align="center">
  <img src="/docs/images/readme/example-lp.PNG?raw=true" alt="Alternative criteria result"/>
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
  
# Utils - NumbersGenerator
This class will alow to generate numbers that have a target Sum. 

To build the class Utils, you should execute the following command: 
#### on windows
```bash
javac -d objs utils/NumbersGenerator.java 
```

When running the program, you can insert 2 arguments : 
```java
int counter; // default value : 4 
double targetSum; // default value : 1.0
```

For example, if you want to generate 4 numbers that have the sum of 1, all you have to do is execute the following command: 
#### on windows
```bash
java -cp objs; com.lamsade.utacalculator.NumbersGenerator 4 1.0  
```

# Utils - ScaleGenerator
This class will alow to generate a scale, this has been used to generate a scale for a criteria. 

To build the class Utils, you should execute the following command: 
#### on windows
```bash
javac -d objs utils/ScaleGenerator.java 
```

When running the program, you can insert 3 arguments : 
```java
double minValue;// default value : 10.0
double maxValue; // default value : 20.0 
int cuts; // default value : 4
```

For example, if you want to generate the scale of criteria that have a minimum value of 10 and a maximum value of 20 with 4 cuts, all you have to do is execute the following command: 
#### on windows
```bash
java -cp objs; com.lamsade.utacalculator.ScaleGenerator 10.0 20.0 4  
```

# Linear Program for the exercice Choice of Tranportation [UTA]
For the ChoiceTransportation exercice, you should run the following commands : 

#### on windows
```bash
javac -d objs -cp lib/com.google.ortools.jar;lib/protobuf.jar examples/ChoiceTransportation.java
java -Djava.library.path=lib -cp objs;lib/com.google.ortools.jar com.lamsade.lp.ChoiceTransportation
```

# Linear Program for the exercice Buying New Car [UTA]
For the exercice Buying New Car, you should run the following commands : 

#### on windows
```bash
javac -d objs -cp lib/com.google.ortools.jar;lib/protobuf.jar examples/BuyingNewCar.java
java -Djava.library.path=lib -cp objs;lib/com.google.ortools.jar com.lamsade.lp.BuyingNewCar
```