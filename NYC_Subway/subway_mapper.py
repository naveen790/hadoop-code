#!/usr/bin/env python

import sys

for line in sys.stdin:
    data = line.split(",")
    if len(data)!=22 or data[1]=='UNIT':
        continue
    x = "{0}\t{1}\t{2}".format(data[1], data[6],data[4])
    print x
