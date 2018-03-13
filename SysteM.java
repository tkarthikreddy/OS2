import java.io.BufferedWriter;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SysteM {
	static String file="";
	static Error_Handler error_code=new Error_Handler();
	static int job=0;
	public static void main(String[] args) {
		File output1=new File("output_file1.txt");
			if(output1.exists()) {
			output1.delete();
			}
			File traceFile1=new File("traceFile.txt");
				if(traceFile1.exists()) {
				traceFile1.delete();
				}
	job+=1;
	file=args[0];
	try{
	      
	      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./output_"+file,true)));
	      out.println("Cumulative job identification number:"+job);
	      out.println();
		  out.flush();
	      out.close();
	}
	catch(IOException e){System.out.println("File Exception");}    
	Loader loader = new Loader();
    Memory M=new Memory();
    try {
		loader.load(args[1]);
	} catch (IOException e) {
	     }

    
    
    Cpu C = new Cpu(Loader.inintialPC,Loader.traceFlag);

try{
	      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./output_"+file,true)));
	      out.println();
	      out.println("Clock (Hex) is :"+Integer.toHexString(Cpu.clock)+" vtu");
	      out.println("Run Time (decimal) is:"+Integer.toString(Cpu.clock)+" vtu");
	      out.println("Execution time (decimal) is :"+Cpu.execution_time+" vtu");
	      out.println("I/O time (decimal) is :"+Cpu.IO+" vtu");
	      out.flush();    out.close();
	}
	catch(IOException e){System.out.println("File Exception");}
	}

 
    
	
	
	static String user_input() {
		Memory m=new Memory();
		int value=0;
		Scanner scanner=new Scanner(System.in);
		try {
		
		value=scanner.nextInt();
		}
		catch(InputMismatchException I){
		boolean isHex = Integer.toString(value).matches("[0-9]+");
		if(!isHex) {
			error_code.handle(311,"Invalid input.\n");
		}}
		


		
		scanner.close();
	    String input="";
	    if(value<0)
	    return(Integer.toBinaryString(value).substring(16));
	    else return new BigInteger(Integer.toString(value), 10).toString(2);
	    	
	}
	
	
    static void cpu_output(String s) {
    
    	
		try {
			PrintWriter output= new PrintWriter(new BufferedWriter(new FileWriter("./output_"+file,true)));
			output.println("Output in binary : \n"+s);
			output.flush();
	    	output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        }
    			}
		
	
    
    

