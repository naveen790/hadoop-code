package org.myorg;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class tweet
	{
	static int c1=0;
	static int cou=0;
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>
		{
		private Text word = new Text();
		private Text word1 = new Text();
		private Text q = new Text();
		private Text l = new Text();
        String s = new String();
		private final static IntWritable one=new IntWritable(1);
		public void map(LongWritable key, Text value,OutputCollector<Text, IntWritable> output, Reporter reporter)throws IOException
			{
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line); //split the input into set of tokens
			String[] a = new String [10000];
			int count = 0;
		    while (tokenizer.hasMoreTokens())
		    	{
		    	a[count] = tokenizer.nextToken();//save all the inputs in the form of tokens into an array
		    	count++;
		    	}
		    	a[count]="\0";
			for (int n = 1; n < count; n++)
				{
				s= (a[n]);
				if (a[n].startsWith("#"))
					{
					word1.set(a[n]);
					output.collect(word1, one);
					}
				}
			}
		}
	public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable>
	{
		int i=0;
		int k,l,t;
		public void reduce(Text key, Iterator<IntWritable> values,OutputCollector<Text, IntWritable> output,Reporter reporter)throws IOException
			{
			int sum = 0;
			String s=new String();
			String a[]=new String[1000];
			int b[]=new int[1000];
			while (values.hasNext())
				{
				sum += values.next().get();
				}
			output.collect(key, new IntWritable(sum));
			}

	}
	public static class Map1 extends MapReduceBase implements Mapper<LongWritable, Text,IntWritable, Text>
	 {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		public void map(LongWritable key, Text value, OutputCollector<IntWritable,Text> output, Reporter reporter) throws IOException
		{

	        String []n=new String[1000];
	        int []count=new int[1000];
	        String []n1=new String[1000];
	        int []count1=new int[1000];
	        int i=0,l,j,f=0;
			String line = value.toString();
		    StringTokenizer tokenizer = new StringTokenizer(line);
			String s=new String();
			int x;
			String s2=new String();
			while (tokenizer.hasMoreTokens())
			    {
			    s=tokenizer.nextToken();
				x= Integer.parseInt(tokenizer.nextToken());
			    if (s.startsWith("#"))
					{
					word.set(s);
					System.out.println("Map2:"+" "+word+" "+x);
					output.collect(new IntWritable(x),word);
					cou++;
					}
				}

		}
	 }
	public static class Reduce1 extends MapReduceBase implements Reducer< IntWritable, Text, IntWritable,Text>
	 {

	 String h[]=new String[10000];
	 int c[]=new int[10000];
	 private Text word = new Text();
	      public void reduce(IntWritable key, Iterator<Text> values, OutputCollector<IntWritable,Text> output, Reporter reporter) throws IOException
	      {
	      int y,i,j;
		  Text t= new Text();

	      String temp=new String();
		  String temp1=new String();


	      while (values.hasNext())
		      {
			   t=values.next();
			   temp1=key.toString();
			   int sum= Integer.parseInt(temp1);
			   c[c1]=sum;
			   temp= t.toString();
			   h[c1]=temp;
			   System.out.println("Red2:"+" "+h[c1]+" "+c[c1]);

				word.set(h[c1]);
				output.collect(new IntWritable(c[c1]),word);
				c1++;
		      }
			if(c1==cou)
			{
				for(i=c1-1;i>c1-11;i--)
					{
						word.set(h[i]);
						output.collect(new IntWritable(c[i]),word);
					}
			}

		}
	}


	public static void main(String[] args) throws Exception
		{
		long start = System.currentTimeMillis();
		JobConf conf = new JobConf(tweet.class);
		conf.setJobName("tweet");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setMapperClass(Map.class);
		conf.setReducerClass(Reduce.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		JobClient.runJob(conf);
		JobConf conf1 = new JobConf(tweet.class);
		conf1.setJobName("tweet1");
		conf1.setOutputKeyClass(IntWritable.class);
		conf1.setOutputValueClass(Text.class);
		conf1.setMapperClass(Map1.class);
		conf1.setReducerClass(Reduce1.class);
		conf1.setInputFormat(TextInputFormat.class);
		conf1.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(conf1, new Path(args[1]));
		FileOutputFormat.setOutputPath(conf1, new Path(args[2]));
		conf1.setNumReduceTasks(1);
		JobClient.runJob(conf1);
		long end = System.currentTimeMillis();
        System.out.println("running time " + (end - start) / 1000 + "s");
		}
	}
