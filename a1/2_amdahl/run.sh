#!/bin/bash

# Compile code
javac saxpy.java

# Benchmark each program for 10 runs
hyperfine -P threads 1 40 -M 10 'java saxpy -j {threads} -g 2000' --export-csv benchmarks.csv

# Plot graph
python benchmark.py