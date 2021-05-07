## May Happen in Parallel (MHP) Analysis using JavaCC and JTB

The goal is to implement a flow-sensitive MHP analysis for programs written in [qParJava](http://www.cse.iitm.ac.in/~krishna/cs6235/subsets.html) form.  
I have attempted to re-implement (with some slight changes) the worklist algorithm for computing MHP sets as described in the paper:    [An efficient algorithm for computing MHP information for concurrent Java programs](https://dl.acm.org/doi/10.1145/318774.319252).  
To avoid having to perform a flow sensitive alias analysis before computing MHP information (as it's time consuming!), it is assumed that 2 variables with the same identifier are must aliases.  
A sample query is of the following form.
```java
// Thread 1
/* S1: */ a = b;
...

// Thread 2
synchronized(c){
/* S2: */ d = e;
}
...

/* S1 mhp? S2 */
```

## Folder Structure

The workspace contains the following folders:

- `src`: source code for performing analysis
- `src/tools`: contains code for working with a given 
- `lib`: Contains the `jar` for [JTB](http://compilers.cs.ucla.edu/jtb/)
- `tests`: Contains various testcases for testing MHP Analysis

## Scripts
The `run_all.sh` script can be used to run and verify all tests as follows
```bash
bash -c run_all.sh [INPUT FOLDER] [EXPECTED OUTPUT FOLDER] [GENERATED OUTPUT FOLDER]
```
The script `generate_vimir.sh` generates and prints internal flow sensitive MHP information along with control flow and parallel execution graph edges for every basic block in the program (excluding blocks and synchronized blocks, as they are treated as placeholder blocks), in a format I like to call `vimir`(short for Vimarsh IR :p). 
To view the code in `vimir` format, do
```bash
bash -c generate_vimir.sh [INPUT FOLDER] [OUTPUT FOLDER]
```
or pass the `--codegen` flag when executing with the built `jar` file(for single file inputs).