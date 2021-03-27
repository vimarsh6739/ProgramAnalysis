#!/bin/bash
##################################################
## Parallel Script to run alias analysis on all ##
## testcases provided in the qTACoJava format.  ##
## Run as                                       ##
## ./run_all.sh infolder outfolder expfolder    ##
##################################################  

singlerun () {
    input=$1
    outdir=$2
    expdir=$3
    bname=$(basename ${input})
    
    # echo "###### Testing ${bname} ######"
    
    filename=${bname%.java}
    output="${outdir}/${filename}.txt"
    expout="${expdir}/${filename}.txt"

    printf "Generating $(basename ${output}): "
    (cat $input | java -jar dist/AliasAnalysis.jar)  > ${output} 
    
    # TODO: Comparison with sample outputs
    if cmp -l "${output}" "${expout}"; then
        printf "${GREEN}PASS${NC}\n"
    else 
        printf "${RED}FAIL${NC}\n"
        cat ${output}
    fi 

}

RED='\033[0;31m'    # Red color
NC='\033[0m'        # No Color
GREEN='\033[0;32m'  # Green Color
# Export function to be used by GNU Parallel 
export -f singlerun

# Accept commandline args for input and output files
indir=$1
if [ -z "$1" ]
  then
    echo "Error: No input directory provided. Exiting script."
    exit 1
fi

outdir=$2
if [ -z "$2" ]
  then
    echo "Error: No expected output directory provided. Exiting script."
    exit 1
fi

expdir=$3
if [ -z "$3" ]
  then
    echo "Error: No expected output folder provided. Exiting script."
    exit 1
fi

# Make the output directory if it doesn't exist
mkdir -p ${outdir}

# Compile all classes
echo "Compiling source"
ant 

# Compute outputs for all inputs
if type parallel >&  /dev/null; then
    # Evaluate parallely if GNU parallel is available 
    parallel singlerun ::: ${indir}/* ::: ${outdir} ::: ${expdir}
else
    echo -e "${RED}GNU Parallel NOT DETECTED."
    echo -e "Beginning serial execution of tests.${NC}"
    # Evaluate serially
    for FILE in "${indir}"/* ; do
        singlerun ${FILE} ${outdir} ${expdir}
    done
fi

