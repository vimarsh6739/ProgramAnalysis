## Alias Analysis using JavaCC and JTB

The goal is to implement flow-insensitive context-insensitive may alias analysis for programs written in [qTACoJava](http://www.cse.iitm.ac.in/~krishna/cs6235/subsets.html). Each alias query is represented as 
```java
A x = ...
A y = ...
/* x alias? y */
```
## Folder Structure

The workspace contains the following folders, where:

- `src`: Contains source code for visitor
- `lib`: Contains dependency **jtb132.jar**
- `tests`: Contains sample testcases for the program
- `bin`: Contains the compiled `.class` files

## Building and running the code

The code can be built into a `bin` folder using the following command
```bash
javac -sourcepath src/ -d bin/ src/Main.java
java -cp bin/ Main < INPUT.java
```
where `INPUT.java` is the input program.
