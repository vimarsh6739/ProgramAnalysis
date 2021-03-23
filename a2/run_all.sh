#!/bin/bash
##################################################
## Parallel Script to run alias analysis on all ##
## testcases provided in the qTACoJava format.  ##
## Run as                                       ##
## ./run_all.sh                                 ##
##################################################  

singlerun (){
    input=$1
    bname=$(basename ${input})
    
    echo "###### Testing ${bname} ######"
    
    filename=${bname%.java}
    output="${filename}.out"
    
    echo "Generating ${output}"
    java -cp bin/ Main < $input > ./tests/outputs/"$output"

    # TODO: Comparison with sample outputs
    # echo "###### ${output} pass ######"
    # echo "###### ${output} fail ######"
}

# Export function to be used by GNU Parallel 
export -f singlerun

# Create folder for storing all binary files
mkdir -p bin/

# Compile all classes
echo "Compiling source"
#javac -sourcepath src/ -d bin/ src/Main.java
ant compile

# Compute outputs for all inputs
parallel singlerun ::: ./tests/inputs/*.java
