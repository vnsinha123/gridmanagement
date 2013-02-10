	// Resource Initialization

import java.net.*;
import java.io.*;
import java.util.*;
import java.math.BigInteger;

class ResourceThread implements Runnable
 {
           Socket cli=null;
           Thread t=null;
            BufferedReader br=null;
            PrintWriter pw=null;
            String st="";
            
            public ResourceThread(Socket cli,String str)
             {
	this.cli=cli;
	System.out.println("Connected to Grid : "+cli);
	t=new Thread(this,str);
	st=str;
	try
	 {
		br=new BufferedReader(new InputStreamReader(cli.getInputStream()));
	            pw=new PrintWriter(new OutputStreamWriter(cli.getOutputStream()),true);
	 }
	catch(Exception e)
	 { }
	t.start();
             }

           public void run()
            {
	try
	 {
		String str="";
		str=br.readLine();   // Accepts the data sent from Grid
		StringTokenizer st=new StringTokenizer(str,",");
		String fname="";
		int val=0,val1=0;
		if(st.hasMoreTokens())
		 {
		         fname=st.nextToken();
		         val=Integer.parseInt(st.nextToken());
		         val1=Integer.parseInt(st.nextToken());
		 }
		str="";    	                        
	  	if(fname.equals("Prime"))
		     str=ResourceThread.Prime(val);
		else if(fname.equals("Factorial"))
		     str=String.valueOf(ResourceThread.Factorial(val));
		else if(fname.equals("PrimeByRange"))
		 {
		   System.out.println("Calling Function : "+val+","+val1);
		     str=String.valueOf(ResourceThread.PrimeByRange(val,val1));
		 }
		else if(fname.equals("FactorialByRange"))
		 {
		     str=String.valueOf(ResourceThread.FactorialByRange(val,val1));
		 }

		System.out.println("Output Value : "+str);
	                  pw.println(str);    // Sends output to Grid
	 }
	catch(Exception e)
	 {
	System.out.println("Error : "+e);
	  }
            }

         public static BigInteger Factorial(int n)
          {
	/*long f=1;
	for(int i=1;i<=n;i++)
	        f=f*i;

	return f;*/
      BigInteger factorial = BigInteger.ONE; 
            for(int i=1;i<=n;i++)
      	 factorial = factorial.multiply(BigInteger.valueOf(i));
       
                 return factorial; 
          }

       public static String Prime(int no)
        {
	int c=0;
	for(int i=1;i<=no;i++)
	 {
	        if(no%i==0)
		c++;
	 }

	if(c==2)
	      return "Given Number is Prime...";
	else
	      return "Given Number is Composite...";
        }

       public static String PrimeByRange(int m,int n)
        {
	String st="";
	
	for(int i=m;i<=n;i++)
	 {
	           int c=0;
	           for(int j=1;j<=i;j++)
	            {
		 if(i%j==0)
		          c++;
	            }

	          if(c==2)
		st=st+i+",";
	 }
	if(st.length()!=0)
	     st=st.substring(0,st.length()-1);
	System.out.println(st);
	return st;
        }

       public static long FactorialByRange(int m,int n)
        {
	long f=1;
	for(int i=m;i<=n;i++)
	 {
	               f=f*i;
	 }
	return f;
        }
 }

class Resource
 {
          public static void main(String arg[])
           {
	try
	 {
		ServerSocket r1=new ServerSocket(2001);

	          int i=1;
	          while(true)
	            {
	     	Socket cli=r1.accept();   // Waits for Grid Request 
		ResourceThread rt=new ResourceThread(cli,String.valueOf(i++));
		
		if(i==-1)
		         break;
	             }
	 }
	catch(Exception e)
	 {
	         System.out.println("Error in Initializing the Resource...."+e);
	 }
           }
 }

