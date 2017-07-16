
import java.io.*;
import java.util.*;
public class Simulator {
	private static final int String = 0;
	static int ans=0;
	static String r=null;
	static String q = null;
	static String p =null;
	static String tswitch = null;//This global variable is used to store trace switch
	static String buff1=null;//This is a buffer used to store the data into memory
	static String error=null;//This is used to display the error type
	static int pc=0;
	static int j=0;
	static int m=0;
	static int i=0;
	static int jobid=0;//This is used for jobid
	static String r6;
	static int sysclock=0;//This is used for calculating the time for the execution of the program
	static int inclock=0;//This is used for calculating the I/O time
	static String EA="000000000000";//This used for Effective address
	static String r4="000000000000";
	static String r5="000000000000";
	static String Ir=null;//This is used for instruction register
	static String value;
	static String termination=null;//This tell us about the type of termination
	static ArrayList<java.lang.String> inbuff = new ArrayList<String>();
	static String[] mem1=new String[4095];//This is used for storing the values as in the memory
	static String arg=null;
	static int size=0;
	
	
	
	/*
	 *****************GLOBAL VARIABLE DESCRIPTION*************************************************************
	   The most important global variables are registers r4 and r5 their used as Index register and Accumulator 
	 "q" is used as program counter,"IR" is used as Instruction register in which we store the instruction
	  "tswitch" is used to store TraceBit value."error" is used to display the type of error,"Jobid" is used to 
	  display the jobid of the program.Sysclock is used to print the system execution time."inclock" is used to
	  store the I/O time,"inbuff" and "buff1" is used as buffers."mem1[]" is used for storing the values in the 
	  memory ."arg" is used for getting the arguments passed during execution
	
	/**********************************************************************************************************/
	
	
	
    /*
     * *****************Description of Main Program**********************************************************
     This program is an simulator for running a sample os program with the given specifications
     In our program we run only one job at time i.e batch processing .Our input is given from a file in Hexadecimal
     format we have to read from file line by line convert it into decimal do all the calculations and process 
     the job and returns the output 
	 * In the main program firstly initialising all values in the memory location to zero
	 * Then call the System method 
	 * when we return back to main then we print the output file
	 **********************************************************************************************************/
	
	
	
	
	public static void main(String[] args) throws IOException 
	{ 
		arg="C:\\Users\\sri\\Documents\\NetBeansProjects\\OSPhase1_1\\src\\phase1_input.txt";
		for(int q=0;q<270;q++)
		{
			mem1[q]="000000000000";
		}
		System();	
	File f2=new File("Output.txt");
	FileWriter p = new FileWriter(f2);
	f2.createNewFile();
	p.write("Job Id::"+jobid+"(DEC)"+"\n");
	p.write("job terminated::" +termination+"\n");
	p.write("program Output::"+ans+"(DEC)"+"\n");
	p.write("time for the program::"+"00"+Integer.toHexString(sysclock).toUpperCase()+"(HEX)"+"\n");
	p.write("I/O time for the program::"+inclock+"(DEC)"+"\n");
	p.write("Execution time for the program::"+(sysclock-inclock)+"(DEC)");
	p.close();
	}
/**
 * System method calls all other four methods
 * Loader(),Memory(),CPU(),Errorhandler()
 * In this subsystem only we are reading the data from the input file 
 * and storing the data into the buffer later on using it in loader
 * Even the reading of the data from keyboard is also done here 
 * Even te writing of the result to output is done here only
 */
public static void System() throws IOException {
	double iostart,ioend;
	jobid=jobid+1;
	try
	{
		ArrayList<String> list = new ArrayList<String>();
	FileInputStream ips=new FileInputStream(arg);//reading the data from the file
	DataInputStream in=new DataInputStream(ips);
	BufferedReader br=new BufferedReader(new InputStreamReader(in));
	java.lang.String data;
	String d,e;
	String[] l = new String[2];
	int line=0,k=0,count=0,v=1;
	while((d=br.readLine())!=null) {
		list.add(d);
		line++;
		}
	br.close();
	int v1=0;
	FileInputStream i=new FileInputStream(arg);
	DataInputStream in1=new DataInputStream(i);
	BufferedReader b=new BufferedReader(new InputStreamReader(in1));
	while((data=b.readLine())!=null)
	{
		if(data.length()>12)
			{
				/*Here we are throwing an error for improper
				 * loader format
				 */
				Errorhandler(7);
				System.exit(0);
			}
		
		
		count++;
		
		if((line)!= count) 
		{
            //here we are reading the values from the file into buffer
			 inbuff.add(data);
			 }
		  else 
		  {
			  l=data.split(" ");
			 
			   q = hexToBin(l[0]);
			   p=q;
			   tswitch = l[1];
		  }
		  }
	
b.close();
	}
	catch(IOException e)
	{
		Errorhandler(4);
		System.exit(0);
	}
	
	if(!tswitch.equals("1") && !tswitch.equals("0"))//throws error for invalid trace bit
	 {
		 Errorhandler(2);
		 System.exit(0);
	 }
	load( q,tswitch);//calling the loader method
	
		cpu(q,tswitch);//calling the cpu method
	
	/*Here we are reading the input from keyboard 
	 * here we are also incrementing the sysclock by 10	
	 */
		while(i==5)
			
		{
			inclock=inclock+10;
			i=0;
			iostart = System.currentTimeMillis();
			
			sysclock=sysclock+10;
			Scanner a=new Scanner(System.in);
			String z=null;int z1,z2=0;
			try
			{
			z2=a.nextInt();
			}
			catch(InputMismatchException Imp)
			{
				Errorhandler(1);
				System.exit(0);
				}
			if(z2>4095)
			{
				Errorhandler(6);
				System.exit(0);
			}
			z=Integer.toBinaryString(z2);
			z1=binToDec(z);
			if(Ir.charAt(4)=='1')
			{
			r4=z;
			}
			else
				r5=z;
			cpu(q,tswitch);
		}
	     /* Here we are writing on to the screen values
	      * the clock here is incremented by 10
	      * 	
	      */
	     while(j==4)
		{
	    	 inclock=inclock+10;
	        j=0;int m=0;
			sysclock=sysclock+10;
			if(Ir.charAt(4)=='1')
			{
				m=binToDec(r4);
			System.out.println(binToDec(r4));
			}
			else{
				m=binToDec(r5);
				System.out.println(binToDec(r5));
			}
			cpu(q,tswitch);
			ans=m;//printing the final output to the variable ans
		}
	     
		}
		/**This is the read submodule 
		 */
		static void RD(String Ir) {
			i=5;
		}
	 
/**
 * This is the Memory method which is having three arguments first one is to verify  it is read,write or dump
 * second one is for effective address,third is for variable
 * this method is called in loader for storing the data into the memory
 * This is called by cpu for fetching the instruction one after the other
 */
private static String Memory(int x,int y,String z)
	 {
	if(y>4095)
	{
		Errorhandler(9);
		System.exit(0);
	}
	//This is for reading
		 if(x == 0)	 
		 {
			 z = mem1[y];
			 value = z;
			 return z;
		 }
	//This for writing
		 if(x == 1)
		 {
	if(z.length()<=48 && z.length()>12)
		{
		for(int j=0;j<z.length()-1;j+=12)
		{
			 mem1[y] = z.substring(j,j+12);
			 y++;
			 size++;
		}
		return null;
		}
		if(z.length()==12)
		{
			mem1[y]=z;
			return null;
		}
	
	}
	
//This is for dump		 
  if(x==2)
	{
		dump();
		
			}
	return null;
	
	 }
/*This method is used for dumping the memory to the output file 
 * whenever an error occurs
 */
private static void dump(){
	try{
	File f=new File("Output.txt");
	if(f.exists())
	{
		f.delete();
	}
	f.createNewFile();
   BufferedWriter fstream = new BufferedWriter(new FileWriter(f,true));
   fstream.write("JobId:"+jobid+"\n");
   fstream.write("Termination Type:"+termination+"\n");
   fstream.write("ERROR IS:"+error);
   fstream.write("\n");
	 int p=0;String hex=null;
	 for(int g=0;g<256;g++)
	 {
		if(g==0)
		{
			hex=Integer.toHexString(g).toUpperCase();
			fstream.write((hex+"000")+"\t");
		}
		 if(p<8) 
		 {
		fstream.write(mem1[g]+"\t");
		p++;
		 }
		 else
		 {
			 p=1;
			 fstream.write("\n \n");
			 String hex1=null;
			 hex1=Integer.toHexString(g).toUpperCase();
			 while(hex1.length()<4)
			 {
				 hex1="0"+hex1;
			 }
			 fstream.write(hex1+"\t");
			 fstream.write(mem1[g]+"\t");
	 }
	 }
	 
	 fstream.close();
	}
	
	catch(IOException e)
	{
		
	}
}

/**
 *This is one of the important method in our project it takes two arguments one is the starting address of the program
 * the other one is the tracebit It takes one instruction at one time  and resolve it into different instruction types
 * based on opcodes  each type is subdivided into different types based on the types calculations are taking place
 * 
 */
private static void cpu(String X, String Y) throws IOException{
	String opc=null;
	while(1<2)
	{
	pc=binToDec(q);
	Ir=Memory(0,pc,Ir);//Fetching the instruction one after the other
	if(Y.equals("1")){//Checking whether trace bit is set or not
	writeinTrace(1);}//write it into Trace
	q=add(q,"000000000001");//Incrementing the opcode after the fetch of 
	opc=Ir.substring(1, 4);
	if(opc.equals("110"))//Checking for the opcode type
		type2(Ir);
	else if(opc.equals("111"))
		type3R4(Ir);
	else type1(opc,Ir);
	if(Y.equals("1")){
	writeinTrace(2);
	}
	if(i==3||i==5||j==4)
	{
		break;
	}
	}
	}

/*This is error handler this is called when ever an error occurs 
 * this method tells us about the type of error along with the type of termination
 * This finally calls the dump() to dump the memory to the output file
 */
private static void Errorhandler(int i)
{
	switch(i)
	{
	case 1:termination="Abnormal termination";
		   error="Illegal input";
	       Memory(2,0,"");
	       break;
	case 2:termination="Abnormal termination";
	        error="Illegal traceBit";
	        Memory(2,0,"");
	        break;
	case 3:termination="Abnormal termination";
	        error="Input file does not consist hexadecimal values";
	        Memory(2,0,"");
	        break;
	case 4:termination="Abnormal termination";
	       error="Input File not Found";
	       Memory(2,0,"");
	       break;
	case 5:termination="Abnormal termination";
	       error="output overflow";
	       Memory(2,0,"");
	       break;
	case 6:termination="Abnormal termination";
	       error="input overflow";
	       Memory(2,0,"");
	       break;
	case 7:termination="Abnormal termination";
	       error="In proper loader format";
	       Memory(2,0," ");
	       break;
	case 8:termination="Abnormal termination";
	       error="Program size to large";
	       Memory(2,0,"");
	       break;
	case 9:termination="Abnormal termination";
	       error="Memory out of range";
	       Memory(2,0,"");
	       break;
	case 10:termination="Abnormal termination";
	        error="Arguments are not passed";
	        Memory(2,0,"");
	        break;
	case 11:termination="Abnormal termination";
	        error="Memory overflow";
	        Memory(2,0,"");
	        break;
	     }
	
}
/**
 * This method is used for writing the trace in the file 
 * this trace starts if the trace bit is set if not this file does not exits 
 * it traces the program counter,Instruction register,effective address before execution,Accumulator before execution ,effective address after execution
 * Accumulator after execution this is done for each every instruction
 */
private static void writeinTrace(int i) throws IOException
{
	File f2=new File("C:\\Users\\sri\\Desktop\\os\\Trace.txt");
	
	BufferedWriter output = new BufferedWriter(new FileWriter(f2,true));
	if(m==0){
	output.write("PC(BINARY) \t ");
	output.write("IR(BINARY)\t");
	output.write("EABE(BINARY)\t ");
	output.write("ACBE(BINARY)\t ");
	output.write("EAAE(BINARY)\t ");
	output.write("ACAE(BINARY)\n");
	m=m+1;
	}
	
    if(i == 1){
	if(Ir.charAt(4)=='1')
	{
		while(r4.length()<12)
		{
		r4="0"+r4;
		}
		output.write(q+"\t"+Ir+"\t"+Integer.toHexString(Integer.parseInt(EA, 2))+"\t"+r4+"\t");
	
	}
	else{int j=r5.length();
		while(r5.length()<12)
		{
		r5="0"+r5;
		}
		output.write(q+"\t"+Ir+"\t"+Integer.toHexString(Integer.parseInt(EA, 2))+"\t"+r5+"\t");
	
	}
	}
	if(i==2) 
	{
		if(Ir.charAt(4)=='1')
		{
		
			while(r4.length()<12)
			{
			r4="0"+r4;
			}
		output.write(Integer.toHexString(Integer.parseInt(EA, 2))+"\t"+r4+"\t\n");
		 
			
		}
		else{
			
			while(r5.length()<12)
				{
					r5="0"+r5;
				}
				output.write(EA+"\t"+r5+"\t\n");
			
			
		}
		}
	output.close();
	}
/**
 * This method calculates the effective address.
 * This is used in type one instructions
 */
public static String effadd()
{
	String s5=null;
	s5=Ir.substring(6);
	while(s5.length()<12)
	{
		s5="0"+s5;
	}
	EA=add(q,s5);//This passes to the add subroutine to calculate addition two binary strings
    return EA;

}

/**
 * From  cpu the control comes to this method when the opcode matches the type1
 * Here again we are partioning it into various types based on the different types
 * effective address calculation varies with the different types of addressing 
 * modes 
 */
private static void type1(String opc,String Ir7) {
String as=effadd();
sysclock=sysclock+1;
	if(Ir7.charAt(0)=='0'&& Ir7.charAt(5)=='0')//direct addressing
	{
		EA=as;
		EA = "000000" + EA.substring(6, 12);
	}
	else if(Ir7.charAt(0)=='0' && Ir7.charAt(5)=='1')//indexing
	{
		int n=0;
		String p=null;
		n=binToDec(r4);
		p=Memory(0,n,p);
		EA=add(as,p);
		EA = "000000" + EA.substring(6, 12);
	}
	else if(Ir7.charAt(0)=='1' && Ir7.charAt(5)=='0')//indirection
	{
		int n=0;String p1=null;
		n=binToDec(as);
		EA=Memory(0,n,p1);
		EA = "000000" + EA.substring(6, 12);
	}
	else if(Ir7.charAt(0)=='1' && Ir7.charAt(5)=='1')//indirection+indexing
	{
		int n=binToDec(r4);String p2=null;String p3=null;
		int n1=binToDec(as);
		p2=Memory(0,n,p2);
		p3=Memory(0,n1,p3);
		EA=add(p2,p3);
		EA = "000000" + EA.substring(6, 12);
	}

	
	switch(Integer.parseInt(opc,2))
	{
	case 0: AND(Ir7); break;
	case 1: ADD(Ir7); break;
	case 2: STR(Ir7); break;
	case 3: LD(Ir7); break;
	case 4:JMP(Ir7); break;
	case 5:JPL(Ir7); break;
	
	}
	
	
}

/*From the cpu the control comes to this method based on the opcode
 *Here again we are going to differentiate between type3 and type4
 *based on the first bit if it is zero---Type 3 else it is Type 4
 */
private static void type3R4(String ir2) {
	
	String s2=ir2.substring(0, 1);
	sysclock=sysclock+1;
	if(s2.equals("0"))//Type 3 starts
	{
		if(ir2.charAt(5)=='1')
		{
			if(ir2.charAt(4)=='1')//for clearing the bit
			{
				r4="000000000000";
			}
			else r5="000000000000";
		}
		if(ir2.charAt(6)=='1')
		{
		if(ir2.charAt(4)=='1')
		{
			r4=add(r4,"000000000001");//for incrementing the bit
		}
		else r5=add(r5,"0000000000001");
		}
		if(ir2.charAt(7)=='1')
		{
			if(ir2.charAt(4)=='1')
				{
				r4=comp(r4);//for complement
				}
			else r5=comp(r5);
		}
		if(ir2.charAt(8)=='1')
		{
		if(ir2.charAt(4)=='1')
		{
			String s1=r4.substring(0, 6);//Byte swap
			String s4=r4.substring(6, 12);
			r4=s4+s1;
		}
		else
		{
			String p1=r5.substring(0, 6);
			String p2=r5.substring(6, 12);
			r5=p2+p1;
		}
		}
		if(ir2.charAt(11)=='0')
		{
			if(ir2.charAt(9)=='1')
			{
				if(ir2.charAt(4)=='1')
				{
					r4=r4.substring(1, 12)+r4.charAt(0);//Rotate left
				}
				else r5=r5.substring(1, 12)+r5.charAt(0);
		
		      }
			else if(ir2.charAt(10)=='1') 
			{
				if(ir2.charAt(4)=='1')
				{
					r4=r4.charAt(11)+r4.substring(0, 11);
				}
				else r5=r5.charAt(11)+r5.substring(0, 11);
				
			}
			
			
	    }
		
		
		else if(ir2.charAt(11)=='1')// Rotate Right
		{
			if(ir2.charAt(9)=='1')
			{
				if(ir2.charAt(4)=='1')
				{
					r4=r4.substring(2, 12)+r4.substring(0, 2);
				}
				else r5=r5.substring(2, 12)+r5.substring(0, 2);
		
		      }
			else if(ir2.charAt(10)=='1') 
			{
				if(ir2.charAt(4)=='1')
				{
					r4=r4.substring(10, 12)+r4.substring(0, 10);
				}
				else r5=r5.substring(10, 12)+r5.substring(0, 10);
				
			}
		
		}
		}
	
	else if(s2.equals("1"))//Type 4 starts
	{
		String s3=ir2.substring(5, 8);
		switch(Integer.parseInt(s3,2))
		{
		case 0:break;
		case 1:GTR(ir2);break;
		case 2:LSS(ir2);break;
		case 3:NEQ(ir2);break;
		case 4:EQL(ir2);break;
		case 5:GRE(ir2);break;
		case 6:LSE(ir2);break;
		case 7:USK();break;
		}
	}
}
	/**
	 * This method calculates complement function
	 */
	private static String comp(String w) {
	String s="";
for(int i=0;i<w.length();i++)
{
if(w.charAt(i)=='1')
{
	s=s+"0";
}
else
	s=s+"1";
}
	return s;
}
private static void USK() {
		q=add(q,"000000000001");// This performs unconditional Skip

	
}
private static void LSE(String ir2)// This performs less than or equal to
{
	if(ir2.charAt(4)=='1')
	{
		int n1=0;
		n1=binToDec(r4);
		if(n1<=0)
			q=add(q,"000000000001");
	
	}
	else
	{
		int n1=0;
		n1=binToDec(r5);
		if(n1<=0)
			q=add(q,"000000000001");
	
	}
	
	
}
private static void GRE(String ir2)//This performs greater than or equal to 
{
	if(ir2.charAt(4)=='1')
	{
		int n2=0;
		n2=binToDec(r4);
		if(n2>=0)
			q=add(q,"000000000001");
	
	}
	else
	{
		int n1=0;
		n1=binToDec(r5);
		if(n1>=0)
			q=add(q,"000000000001");
	}
	
}
private static void EQL(String ir2)//This performs equal to operations  
{
	if(ir2.charAt(4)=='1')
	{
	int n1=0;
	n1=binToDec(r4);
	if(n1==0)
		q=add(q,"000000000001");
	}
	else
	{
		int n1=0;
		n1=binToDec(r5);
		if(n1==0)
			q=add(q,"000000000001");
		
	}
	
}
private static void NEQ(String ir2) //This performs not equal operation
{
	if(ir2.charAt(4)=='1'){
	int n1=0;
	n1=binToDec(r4);
	if(n1!=0)
		q=add(q,"000000000001");
	}
	else
	{
		int n1=0;
		n1=binToDec(r5);
		if(n1!=0)
			q=add(q,"000000000001");
	}
}
private static void LSS(String ir2) //It checks the less than condition
{
	if(ir2.charAt(4)=='1'){
	int n=0;
	n=binToDec(r4);
	if(n<0)
		q=add(q,"000000000001");
	}
	else{
		int n1;
		n1=binToDec(r5);
		if(n1<0)
			q=add(q,"000000000001");
	
		
	}

	
	
}
private static void GTR(String ir2) //It checks the greater than condition
{
	if(ir2.charAt(4)=='1')
	{
		int n=0;
		n=binToDec(r4);
		if(n>0)
			q=add(q,"000000000001");
	}
	else
	{
		int n1;
		n1=binToDec(r5);
		if(n1>0)
			q=add(q,"000000000001");
	
	}
	
}
/**
 * control comes to this method based on the type of instructions 
 * According to the instruction if the type is 2 it comes here
 * Here when ever the read operation occurs the control must go to the system where the value is 
 * read from the keyboard and print to the screen also occurs here when write comes to the picture
 *It goes to HLT when the program is executed successfully
 */
private static void type2(String ir3) {
	sysclock=sysclock+1;
	String s1=ir3.substring(5,8);
	if(s1.equals("100"))
		RD(ir3);//read operation
	else if(s1.equals("010"))
		WR(ir3);//write operation
	else if(s1.equals("001"))
		H();//halt operation
	
}
private static void H() //HLT METHOD
{
	i=3;
	termination="Normally";
	}
private static void WR(String ir6)//Write method
{
	j=4;
	}
private static void JMP(String w) //JUMP opertion is performed
{
	q=EA;
	
}
private static void STR(String w) // This is the store method here we are going to store the value to memory location from the register 
{
	if(w.charAt(4)=='1')
	{
		int f3=Integer.parseInt(EA,2);
	while(r4.length()<12)
		r4="0"+r4;
		Memory(1,f3,r4);//storing in the memory b calling Memory() subsystem
			}
	else
		{
		int f4=Integer.parseInt(EA,2);
		while(r5.length()<12)
			r5="0"+r5;
	Memory(1,f4,r5);
		}
}
private static void LD(String w)//Here we are going to load the value into the memory location 
{
	if(w.charAt(4)=='1')
	{
		int f=binToDec(EA);
		r4=Memory(0,f,r4);//calling the Memory
	}
	else
		{
		int f1=binToDec(EA);
		r5=Memory(0,f1,r5);//calling the Memory
		
		}
	
}
private static void ADD(String w) //This performs the add operation by reading the value from the respective memory location
//and the register
{
	if(w.charAt(4)=='1')
	{int d;
		String u1=null;
		String u=null;
		String u2=null;
		int h=binToDec(EA);
		u2=r4;
		u=Memory(0,h,u);//call the memory
		u1=add(r4,u);//calling the binary addition subroutine
		r4=u1;
		
	}
	else 
		
	{ 
		String u1=null;
		String u=null;
		String u2=null;
		int h1=binToDec(EA);
		u2=r5;
 		u=Memory(0,h1,u);
		u1=add(r5,u);
		r5=u1;
	}	
}
private static void AND(String w)//Here we are performing the logicalAND operation by calling andop() subroutine
{
	String p=null;
if(w.charAt(4)=='1')
{
	int s=binToDec(EA);
	p=Memory(0,s,p);//calling memory
	r4=andop(p,r4);//calling andop() subroutine
}
else{
	int s1=binToDec(EA);
	p=Memory(0,s1,p);//calling memory
	r5=andop(p,r5);//calling andop() subroutine
}
}
private static String andop(String s1,String s2)// This method performs the Logical AND operation 
{
	String r="";
	s1="1001001";
	s2="1110001";
	for(int i=0;i<s1.length();i++)
	{
		if(s1.charAt(i)=='1' && s2.charAt(i)=='1')
		{
			r=r+'1';
			
		}
		else
			r=r+'0';
	}
	 return r;
}
private static void JPL(String ir7)//This performs jump and link operation  
{
	if(ir7.charAt(4)=='1')
	{
		r4=q;
		q=EA;
	}
	else
		{r5=q;q=EA;}
}
/**
 * This method is called by System().This method is responsible for reading the data from the 
 * buffer line by line and converting it from hexdecimal to binary later on call the 
 * Memory subsystem to load the data into the memory 12bits as one word in the memory
 * Check for the illegal trace bit
 */
public static void load(String X,String y)
{int e=0;
int data=0;
	 int z=-4,v1=1,n=0;
	 try
	 {
	 while(v1<inbuff.size())
	 { 
		 data=binToDec((hexToBin(inbuff.get(n))));// calling the error handler
		 if(data>64)
		 {
			 Errorhandler(8);
			 System.exit(0);
		 }
		 z=z+4;
		 buff1=hexToBin(inbuff.get(v1));//calling the hextobin subroutine
		 v1++;
		 Memory(1,z,buff1);//calling memory
	 }
	 }
	 catch(NumberFormatException NFE)
	 {
		 Errorhandler(3);
		 System.exit(0);
	 }
	 e=binToDec((hexToBin(inbuff.get(n))));
	if(e!=size)
	{
		Errorhandler(11);
		System.exit(0);
	}
 if(tswitch.equals("1") || tswitch.equals("0"))//checking illegal trace 
		{
			File Trace=new File("Trace.txt");
			if(Trace.exists())
			{
				Trace.delete();
			}
			
		}
}
/*This method is to convert the hexadecimal string to the Binary string 
 */
private static String hexToBin(String hex){
    String bin = "";
    String binFragment = "";
    int iHex;
    hex = hex.trim();
    hex = hex.replaceFirst("0x", "");

    for(int i = 0; i < hex.length(); i++){
        iHex = Integer.parseInt(""+hex.charAt(i),16);
        binFragment = Integer.toBinaryString(iHex);

        while(binFragment.length() < 4){
            binFragment = "0" + binFragment;
        }
        bin += binFragment;
    }
    return bin;
}

/**
 * This method is used to convert the binary string to the Decimal value it takes the 
 * Binary string as argument
 */
public static int binToDec(String N)
{
        int bin = Integer.parseInt(N);
        int power=0,result=0;
        int dec = 0;
        while (bin != 0)
        {
            int digit = bin % 10;                  
			dec = digit*(int)Math.pow(2,power); 
            bin = bin / 10;  
            power++;
         result=result+dec;
        }
return result;
}
//This is subroutine for using in the method add()
public static String addBit(String a, String b, String c)
{
String r="";
if(a.equals("1") && b.equals("0") || a.equals("0") && b.equals("1")) 
{
if( c.equals("0")) r="1";
else { r="0"; c="1"; }
} 
else if( a.equals("0") && b.equals("0") )
{
if(c.equals("0")) r="0";
else {r="1";c="0";}
}
else if( a.equals("1") && b.equals("1") )
{
if(c.equals("0")){ r="0"; c="1"; }
else { r="1"; c="1"; }
}

return c+r; 
} 
/*
 This subroutine performs addition of two strings
 this routine takes 
 */
public static String add(String a, String b)
{
String r="";
int len=a.length(),d;
String carry="0";

for(int i=len-1;i>=0;i--)
{
String ai=a.substring(i, i+1);
String bi=b.substring(i,i+1);

String res=addBit(ai,bi,carry);

String cb=res.substring(0,1);
String rb=res.substring(1,2);
r=rb+r;
carry=cb;
}

return r; 
}
}


	

