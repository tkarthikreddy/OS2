import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

public class Loader {

	static int bufferIndex = 0; // index used to access the elements of the buffer
	static String[] buffer= new String[4]; // buffer used to hold the words in binary format.
	public static String[] file_read=new String[256]; 
	static String file;
	public static String jobID;
	public static String loadAddress;
	public static String inintialPC;
	public static int size;
	public static int traceFlag;
	static Error_Handler error_code=new Error_Handler();
	
	    public static void load(String args) throws IOException {

	    file = args; // file name
		bufferIndex = 0; 
		
		String line_read=null; //the line that is read from the file
		String line_read_binary=null; // holds the binary equivalent of the line that is read from the file
		FileReader fileReader = new FileReader(file); // creating a file reader to use with a BufferedReader
		BufferedReader bufferedReader = new BufferedReader(fileReader); //assigning the file to the BufferedReader
		
		if((line_read = bufferedReader.readLine()) != null) { // reading a line in the file
			
			String [] firstLine=line_read.split("\\s+");
	
			if(firstLine.length!=5) {
				error_code.handle(101,"Not the correct number of fields in the first line\n");
			}
			else {
			
				if(firstLine[0].length()==2) {
					jobID=hex_binary(firstLine[0]);
				}
				else {
					error_code.handle(111,"Job ID format is incorrect.\n");
				}
				if(firstLine[1].length()==2) {
					loadAddress=hex_binary(firstLine[1]);
				}
				else {
					error_code.handle(121,"Error in the location where the program should be stored.\n");
				}
				if(firstLine[2].length()==2) {
					inintialPC=hex_binary(firstLine[2]);
					

				}
				else {
					error_code.handle(131,"Error in the location of first instruction. \n");
				}
				if(firstLine[3].length()==2) {
					size=Integer.parseInt(new BigInteger(firstLine[3],16).toString(10));
				}
				else {
					error_code.handle(141,"Error in the length of the job.\n");
				}
				if(firstLine[4].length()==1) {
					traceFlag=Integer.parseInt(firstLine[4].toString());
					if(traceFlag!=1&&traceFlag!=0)
						error_code.handle(521,"Incorrect Trace flag.\n");
				}
				else {
					error_code.handle(501,"Error in the trace flag. \n");
				}
			  
				int number_of_lines=size/4;
				int line_number=0;
				String next_line;			
			    while((line_read = bufferedReader.readLine()) != null) {
			    	if(line_read.length()!=16) {
		    		      if((next_line=bufferedReader.readLine()) != null)
			    		             error_code.handle(511,"Insufficient number of instructions in line "+ (line_number+1) +" of the job");
			    	}
			    	String binary_code="";
			    	try {
			    	binary_code=hex_binary(line_read);
			    	}
			    	catch(NumberFormatException E) {
			    		boolean isHex = binary_code.matches("[0-9A-F]+");
			    		if(!isHex) {
				    		error_code.handle(191,"Job "+Cpu.binary_hex(jobID)+" contains data which is not in Hex format  in the line "+ (line_number+1)+ "\n");
				    	}
			    	}
			    	
			    	int len_bin=binary_code.length();
			    	
			    	int index=0;
			    	bufferIndex=0;
			    	
			    	while(index<len_bin) {
			    		buffer[bufferIndex]=binary_code.substring(index, index+16);
			    		index=index+16;
			    		bufferIndex++;  	
			 
			    	if(bufferIndex==4) {
			        	int i=0;
			        	while(i<bufferIndex) {
			        	Memory.memory[Memory.index]=buffer[i];
			        	
			        	Memory.index++;
			        	i++;      	
			        	}
			         }
			      }
			   line_number++;  
			   }
			    if(line_number>number_of_lines) {
			    	error_code.handle(521,"Job length out of bound.\n line number ");
			    }else if(line_number<number_of_lines) {
			    	error_code.handle(531,"Too few lines in the Job\n");
			    }
			    
			}
		}
    }	

	    
		static String hex_binary(String to_be_converted) { // converting a hex word number to binary
		    String binary=null;
		    binary=new BigInteger(to_be_converted, 16).toString(2); // converting the number from base 16 to base 2
		    //add err
		    while(binary.length()<to_be_converted.length()*4)
		    binary="0"+binary;
		    return binary;
		}

	    
}