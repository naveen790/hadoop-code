	package org.myorg;
	import java.io.IOException;
	import java.util.*;
	import org.apache.hadoop.fs.Path;
	import org.apache.hadoop.conf.*;
	import org.apache.hadoop.io.*;
	import org.apache.hadoop.mapred.*;
	import org.apache.hadoop.util.*;
	
	public class fof1 {
	
	    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, IntWritable, Text> 
	    {
	     public void map(LongWritable key, Text value, OutputCollector<IntWritable,Text> output, Reporter reporter) throws IOException 
	      {
	        String line = value.toString();
	        StringTokenizer tokenizer = new StringTokenizer(line); //splits the text into tokens
	        int a[]=new int[100000];
	        int i=0;
	        Text word = new Text();
	        while (tokenizer.hasMoreTokens()) 
			{
	        	a[i]=Integer.valueOf(tokenizer.nextToken());       //converting the tokens to integer and storing in array
	        	i++;
 
			}
			int person,r1,r2;
			person=a[0];
			Text flist1=new Text();
			Text flist2=new Text();
			word.set(Integer.toString(person));  //this is the person for whom frieds list is given
	        for(int x=1;x<i;x++)
	        	{
	        	r1=a[x];	
	        	for(int y=1;y<i;y++)
	        		{
	        		r2=a[y];
	        		if(r1<r2)                            //in this two loops we create a triplets list based on the person 
	        			{									//and the person is always in the middle and a friend before and after him
	        			if(person<r1)						//the friend before person is the key 
	        				{
	        				IntWritable k1= new IntWritable(r1);
	        				IntWritable k2=new IntWritable(r2);
	        				String s1=Integer.toString(r1);
	        				String s2=Integer.toString(r2);
	        				String s3=Integer.toString(person);
	        				String s4=s3.concat(" ");
	        				String s5=s4.concat(s2);
	        				String s6=s4.concat(s1);
					    	flist1.set(s5);
	        				flist2.set(s6);
	        				output.collect(k1,flist1);
						    output.collect(k2,flist2);
							}
	        			else if(person<r2)
	        				{
	        				IntWritable k= new IntWritable(r1);
	        				String s2=Integer.toString(r2);
	        				String s3=Integer.toString(person);
	        				String s4=s3.concat(" ");
	        				String s5=s4.concat(s2);
	        				flist1.set(s5);
	        				output.collect(k, flist1);
							}
	        			}
	    	
	        		}
	        	}
	      }
	    
	    }
	    public static class Reduce extends MapReduceBase implements Reducer< IntWritable, Text, IntWritable,Text> 
	    {
		public void reduce(IntWritable key, Iterator<Text> values, OutputCollector< IntWritable, Text> output, Reporter reporter) throws IOException 
	    {
	        int j = 0,i=0,z=0;
	        String h,tem11;
			String temp1[]=new String[100000];
	        String e[]=new String[100000];
	        int a[]=new int[100000];
	        int b[]=new int[100000];
	        String symbol=",";
			Text flist=new Text();
			while(values.hasNext()) // this loop converts the values to string and stores them in array
	        {	
			 	tem11=values.next().toString();
				temp1[j]=tem11;
				j++;
	        }
			
			for(i=0;i<j;i++)
				{
				tem11=temp1[i];
				StringTokenizer tokenizer1 = new StringTokenizer(tem11);
			    while (tokenizer1.hasMoreTokens())                      // inthis converting the string to tokens and then into integer and store in array
					{
					a[z]=Integer.parseInt(tokenizer1.nextToken());
					b[z]=Integer.parseInt(tokenizer1.nextToken());
					z++;
	                }
				}
			
				
			int r1,r2,r3,check=0;
	        for(j=0;j<z;j++)
	        {
        	r1=b[j];
			check=0;                          // in this we know that the first and second element are friends. only check if the first and third element are friends
        	for(i=0;i<z;i++) 			//so we take the third element and compare with all second elements and if present ,print the triplets
	        	{ 
	        		r2=a[i];
					r3=a[j];
	        		if(r1==r2)
	        		{
						     if(check==0)            //check is used to avoid duplicates
							 {
							 String s1=Integer.toString(r3);
							 String s2=Integer.toString(r1);
							 String s3=s1.concat(symbol);
							 String s4=s3.concat(s2);
							 String s5=symbol.concat(s4);
							 flist.set(s5);
							 output.collect(key,flist);
							 check=1;
							 }
						}
	        		}
	        	}
	        }
	        
	        
	      
	    }
	
	    
	    public static void main(String[] args) throws Exception 
		{
	      JobConf conf = new JobConf(fof1.class);
	      conf.setJobName("fof1");
	      
	      
	      conf.setOutputKeyClass(IntWritable.class);
	      conf.setOutputValueClass(Text.class);
	     
	      conf.setMapperClass(Map.class);
	      
	      conf.setReducerClass(Reduce.class);
	
	      conf.setInputFormat(TextInputFormat.class);
	      conf.setOutputFormat(TextOutputFormat.class);
	
	      FileInputFormat.setInputPaths(conf, new Path(args[0]));
	      FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	
	      JobClient.runJob(conf);
	    }
	}
	