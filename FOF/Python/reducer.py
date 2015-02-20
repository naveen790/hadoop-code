#!/usr/bin/env python

from operator import itemgetter
import sys
current_key= None
flist1=[]
flist2=[]

for line in sys.stdin:
    
	line = line.strip()
	
	key,friend1,friend2 = line.split('\t')
	
    if current_key == key :

		flist1.append(friend1)          # for a given key the friend 1 is stored in flist1 and friend 2 is stored in flist2
		flist2.append(friend2)
			
	elif current_key!=key or key==" " :

		x=0
		int (x)
		y=0
		int (y)
		check=0
		int (check)
		list_length1=len(flist1)
		list_length2=len(flist2)
	
		while x <  list_length2 :

			t1= flist2[x]
                    
                        y=0
			check=0
			while y < list_length1:						#in  this  checking if friend in flist2 is friend with the key using flist1 
                            
                            t2=flist1[y]
                            t3=flist1[x]
                            if (int(t1)==int(t2) and check==0):
                                print '%s,%s,%s' % (current_key,t3,t1)  # the output is printed if all are mutual friends.
                                check=1

                            y=y+int(1)
                            
			x=x+int(1)
					
		flist1=[]
		flist2=[]
		flist1.append(friend1)
		flist2.append(friend2)
        current_key = key

x=0
int (x)
y=0
int (y)
check=0
int (check)
list_length1=len(flist1)
list_length2=len(flist2)

while x <  list_length2 :

    t1= flist2[x]
    y=0
    check=0

    while y < list_length1:

        t2=flist1[y]
        t3=flist1[x]
        if (int(t1)==int(t2) and check==0):
            print '%s,%s,%s' % (current_key,t3,t1)
            check=1

        y=y+int(1)

    x=x+int(1)
		
