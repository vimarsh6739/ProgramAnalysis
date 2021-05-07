#!/bin/bash
######################################################
## Script to run alias analysis on a single program ##
## provided in the qTACoJava format. Run as         ##
## ./run_program.sh <inputfile> <outputfile>        ##
######################################################

# Accept commandline args for input and output files
input=$1
if [ -z "$1" ]
  then
    echo "Error: No input file provided. Exiting script."
    exit 1
fi

bname=$(basename ${input})
filename=${bname%.java}
output=$2

if [ -z "$2" ]
  then
    echo "Warning: No output file provided. Using default argument"
    output="${input%.java}.txt"
fi

# Compile base code
echo "Compiling source"
ant

# Run program
echo "Running on input ${bname}"

(cat $input | java -jar dist/AliasAnalysis.jar) > $output

echo "Generated output ${output}"