#!/bin/bash

## Parallel Script to generate the peg of all testcases in the vimir (VimarshIR) format
## Reads input from tests/inputs and dumps annotated blocks in tests/vimir  
## Usage:
## ./generate_vimir.sh [INPUT FOLDER]  [CFG FOLDER]

singlerun () {
    input=$1
    
    bname=$(basename ${input})
    
    filename=${bname%.java}
    output="${outdir}/${filename}.vimir"
    
    # Remove output file if it already exists
    rm -f ${output}
    
    # Create an empty file
    touch ${output}

    echo "Generating ${filename}.vimir"
    
    # Populate file 
    (cat $input | java -jar dist/MHPAnalysis.jar)  > ${output} 
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

outdir=$2
if [ -z "$2" ]
  then
    echo -e "WARNING: No storage location provided for storing PEG files. Defaulting to ./tests/vimir/${NC}"
    outdir="tests/vimir"
fi

export outdir

# Make the output directory if it doesn't exist
mkdir -p ${outdir}

# Compile all classes
echo "Compiling source"
ant 

# Check number of failures

echo -e "${RED}Beginning serial CFG generation.${NC}"

# Evaluate serially
for FILE in "${indir}"/*.java ; do
    singlerun ${FILE}
done
