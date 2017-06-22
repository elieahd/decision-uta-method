# decision-uta-method
A repository containing the doc and the work done during my research about UTA method.

# UTA
The UTA method is used to solve a multi-criteria problem. It build a utility function based on the preferences of the DM and it consist in solving a linear program (LP).

An improved version of the UTA is the UTASTAR. In UTA we used a single error in UTASTAR we use a double positive error function. The updated version has performed better than the regular method. 

Upon decompressing the archive, you will get the following structure:

decision-uta-method/<return>
  src              <- directory containing Java projects done <return>
  docs             <- directory containing all of the docs produced <return>
  README           <- This file <return>
  
# src
src/ <br/>
  lib/             <- directory containing libraries and jar files. <br/>
  objs/            <- directory containing Java compiled class (*.class) <br/>

Running the examples will involve compiling them, then running them:

#### on unix
```bash
javac -d objs -cp lib/com.google.ortools.jar:lib/protobuf.jar LinearProgramming.java
java -Djava.library.path=lib -cp objs:lib/com.google.ortools.jar com.google.ortools.samples.LinearProgramming
```

#### on windows
```bash
javac -d objs -cp lib/com.google.ortools.jar;lib/protobuf.jar LinearProgramming.java
java -Djava.library.path=lib -cp objs;lib/com.google.ortools.jar com.google.ortools.samples.LinearProgramming
```


Work in progress.
