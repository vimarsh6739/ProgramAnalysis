#!/usr/bin/env python

import matplotlib.pyplot as plt
import pandas as pd

df = pd.read_csv('benchmarks.csv')

base_time = df['mean'][0]
df['speedups'] = df['mean'].apply(lambda x: base_time/x)
df.plot(x='parameter_threads',
        y='speedups',
        xlabel='Num of Threads',
        ylabel='Speedup',
        legend=False,
        ylim=(0,6),
        title='Line Graph for Speedup')
plt.savefig('speedup_graph.png',bbox_inches='tight')

