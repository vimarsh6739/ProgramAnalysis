#!/bin/bash

## Parallel Script to parse syntax of all testcases provided in the qPARJava format.   
## Reads input from tests/inputs by default
## Usage:
## ./parse_all.sh <input folder> 

singlerun () {
    inputfile=$1
    bname=$(basename ${inputfile})

    # Check file
    printf "Checking ${bname}: \n" 
    cat $inputfile | java -jar dist/MHPAnalysis.jar 
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
    echo -e "${BLUE}WARNING: No input directory provided. Defaulting to ./tests/inputs/${NC}"
    indir="tests/inputs"
fi

# Compile all classes
echo "Compiling Parser"
ant 


# Compute outputs for all inputs
if type parallel >&  /dev/null; then
    # Evaluate parallely if GNU parallel is available 
    parallel singlerun ::: "${indir}"/*.java
else
	
    echo -e "${RED}GNU Parallel NOT DETECTED."
    echo -e "Beginning serial execution of tests.${NC}"
    # Evaluate serially
    for FILE in "${indir}"/*.java ; do
        singlerun ${FILE} ${outdir} ${expdir}
    done
fi
