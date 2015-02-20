#!/usr/bin/env python

import sys

def reducer():
    max_entries = 0
    old_key = None
    hour_in_day = ''

    for line in sys.stdin:
        data = line.strip().split("\t)
        if len(data) != 3:
            continue
        this_key = data[0]
        entry = float(data[1])
        hour = data[2]


        if old_key and old_key != this_key:        
            print "{0}\t{1}\t{2}".format(old_key, hour_in_day, max_entries)
            max_entries = 0
            hour_in_day = ''
            
        old_key = this_key
        if max_entries == 0 or max_entries <= entry:
            max_entries = entry
            hour_in_day = hour
        
    if old_key != None:
        print "{0}\t{1}\t{2}".format(old_key, hour_in_day, max_entries)


