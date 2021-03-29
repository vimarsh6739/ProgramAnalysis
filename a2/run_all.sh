#!/bin/bash

## Parallel Script to run alias analysis on all testcases provided in the qTACoJava format.   
## Reads input from tests/inputs and tests/outputs and dumps outputs in tests/temp  
## Usage:
## ./run_all.sh <input folder> <output folder> <temp folder>  

singlerun () {
    input=$1
    # outdir=$2
    # expdir=$3
    bname=$(basename ${input})
    
    filename=${bname%.java}
    output="${outdir}/${filename}.txt"
    expout="${expdir}/${filename}.txt"
    
    # Remove output file if it already exists
    rm -f ${output}
    
    # Create an empty file
    touch ${output}

    fmt_pass="%-40s${GREEN}%-10s${NC}\n"
    fmt_fail="%-40s${RED}%-10s${NC}\n"

    # Populate file 
    (cat $input | java -jar dist/AliasAnalysis.jar)  > ${output} 
    
    # Compare with expected output
    diff -w ${output} ${expout} &> /dev/null

    if [ $? -ne 0 ]; then
        printf ${fmt_fail} "Testing ${bname}: " "FAIL"
        exit 1
    else 
        printf ${fmt_pass} "Testing ${bname}: " "PASS"
    fi 
}

export RED='\033[0;31m'    # Red color
export NC='\033[0m'        # No Color
export GREEN='\033[0;32m'  # Green Color
export BLUE='\033[0;34m'   # Blue Color

# Export function to be used by GNU Parallel 
export -f singlerun

# Accept commandline args for input and output files
indir=$1
if [ -z "$1" ]
  then
    echo -e "${BLUE}WARNING: No input directory provided. Defaulting to ./tests/inputs/"
    indir="tests/inputs"
fi

expdir=$2
if [ -z "$2" ]
  then
    echo -e "WARNING: No expected output directory provided. Defaulting to ./tests/outputs/"
    expdir="tests/outputs"
fi

outdir=$3
if [ -z "$3" ]
  then
    echo -e "WARNING: No storage location provided for storing outputs. Defaulting to ./tests/temp/${NC}"
    outdir="tests/temp"
fi

export expdir
export outdir

# Make the output directory if it doesn't exist
mkdir -p ${outdir}

# Compile all classes
echo "Compiling source"
ant -v

# Check number of failures
fail=0

# Compute outputs for all inputs
if type parallel >&  /dev/null; then
    # Evaluate parallely if GNU parallel is available 
    parallel singlerun ::: "${indir}"/*.java
    fail=$?
else
	fail=0
    echo -e "${RED}GNU Parallel NOT DETECTED."
    echo -e "Beginning serial execution of tests.${NC}"
    # Evaluate serially
    for FILE in "${indir}"/*.java ; do
        singlerun ${FILE} ${outdir} ${expdir}
        fail=$(( $fail + $? ))
    done
fi

# Return based on value of fail
if [ $fail -ne 0 ] ; then 
	exit 1
fi
