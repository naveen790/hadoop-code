#!/usr/bin/env python

import sys

for line in sys.stdin:
	line = line.strip()                  
	words = line.split()                 #this is used to split the words  
	#print (words)
	i=0
	int(i)
	a=[]
	for word in words:
		a.append(word) #store the words in list 'a'
	x=1
	int (x)
	x=1

	while x < len(a):                        

                t1=a[x]
                z=0
                int (z)
                z=0

                while z< len(a):

                    t2=a[z]

                    if(t1<t2):

                        if(a[0]<t1):                                      # with the person inbetween friends,the list of three is printed
																			#the friend is the key and all possible combination is printed
                            print '%s \t %s \t %s' % (t2,a[0],t1)
                            print '%s \t %s \t %s' % (t1,a[0],t2)

                        elif(a[0]<t2):

                             print '%s \t %s \t %s' % (t1,a[0],t2)
                    
                    z=z+int(1)		

		x=x+int(1)
    
