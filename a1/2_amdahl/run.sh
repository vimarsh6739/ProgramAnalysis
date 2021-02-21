#!/bin/bash

# Compile code
javac Saxpy.java

# Benchmark each program for 10 runs
hyperfine -P threads 1 40 -M 10 'java Saxpy -j {threads} -g 2000' --export-csv benchmarks.csv

# Plot graph
python benchmark.py
