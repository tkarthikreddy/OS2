import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class Cpu {  
	public static String[] S= {"00000","00000","00000","00000","00000","00000","00000"};
	public static int clock=0;
	public static int execution_time=0;
	public static int IO=0;
	public static int Instruction_type=-1;
	private static String PC;
	private static String IR;
	private String BR="0";
	static int Tos=0;
    char index;
    static String EA = "0000000";
    static String Z = null;

	public Cpu(String X, int y) // X is the Program counter and Y is the trace switch. When Trace switch is 1, the details are written to a trace file
	{
		PC=X;
		IR=PC;		
		
		
		
		
if(y==1) {
			
	try{
		File file=new File("./trace_"+SysteM.file);
		if ( file.exists())  {
			
			file.delete();
		}
		
		  PrintWriter out2 = new PrintWriter(new BufferedWriter(new FileWriter("./trace_"+SysteM.file,true)));
	      out2.printf("%2s","PC(hex)");
	      out2.printf("%12s","BR(hex)");
	      out2.printf("%20s","Instruction(hex)");
	      out2.printf("%20s","TOS(before)(hex)");
	      out2.printf("%24s","S[TOS](before)(hex)");
	      out2.printf("%20s","EA(before)(hex)");
	      out2.printf("%30s","Contents of EA(before)(hex)");
	      out2.printf("%18s","TOS(after)(hex)");
	      out2.printf("%22s","S[TOS](after)(hex)");
	      out2.printf("%18s","EA(after)(hex)");
	      out2.printf("%28s","Contents of EA(after)(hex)");
	      out2.println();
	      out2.close();
	  }
	catch(IOException e){
		e.printStackTrace();
		}



		
		


        }
		
		boolean loop=true;
		while(loop) {
			IR=PC;
			
			PC=increment(PC);
			
			int a=binary_dec(IR);
		String instruction=Memory.memory[a];

		char t=instruction.charAt(0); //read the instruction type of the instruction. 
		
		
		if(t=='0') {
			
			t=instruction.charAt(8);
			
			if(t=='0') {
				Instruction_type=0;
			}
			else {
				
			}
		}
		else if(t=='1') {
			Instruction_type=1;
		}
		else {	
		}
		String[] Op={"00000","00000"};
		
		if(Instruction_type==0) {
			Op[0]=instruction.substring(3,8);
			Op[1]=instruction.substring(11,16);
			}
		else if(Instruction_type==1) {

			Op[0]=instruction.substring(1,6);Op[1]="";
			String DAddr=instruction.substring(9,16);
			index=instruction.charAt(6);
			
			if(index=='1') {
				EA=dec_binary(binary_dec(DAddr)+binary_dec(S[Tos]));
				
			}
			else if (index=='0') {
				EA=DAddr;	
				}
			else {
				}
				}
		
		int i=0;
		int length1=Op.length;
		int Operand=0;
		while(i<2) {
			
			if(Op[i]=="")break;
			else {
				int decrement_Tos_By=0;		
				if(Instruction_type==0) {
					decrement_Tos_By=1;
				}else if(Instruction_type==1) {
				}
				
				
			  int   OpNumber=binary_dec(Op[i]);
				if(y==1) {
					try{
				      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./trace_"+SysteM.file,true)));
				      out.printf("%4s",binary_hex(IR));
				      out.printf("%13s",binary_hex(BR));
				      out.printf("%16s",binary_hex(instruction));
				      out.printf("%20s",decimal_hex(Tos));
				      out.printf("%21s",binary_hex(S[Tos]));
				      out.printf("%21s",binary_hex(EA));
				      out.printf("%23s",binary_hex(Memory.memory[binary_dec(EA)]));
				      out.close();
				    }
				    catch(IOException e) {
				    }
				}
	
		         if(Instruction_type==0) {
		        	 if(OpNumber==19||OpNumber==20) {clock=clock+16;IO+=16;}
		            	 else if(OpNumber==0) {clock=clock+0;execution_time+=0;
		        		         }
		        	     else {clock++;execution_time++;
		        		       	 }
		         } 
		         if(Instruction_type==1) {
		        	 if(OpNumber==19||OpNumber==20) {clock=clock+19;IO+=19;}
	            	 else if(OpNumber==0) {clock=clock+0;execution_time+=0;
	        		         }
	        	     else {clock+=4;execution_time+=4;
	        		       	 }
		         }
		         
			     switch(OpNumber) {
			     
			     case 0:                        break;                               
			     //NOP - No Operation
			     case 1:                        Operand=getOperand();
			    	                            S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])|Operand);       //OR
			    	                            Tos=Tos-decrement_Tos_By;
			                                    break;
			                                    
			     case 2:                        Operand=getOperand();
			    	                            S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])&Operand);       //And
			    	 						    
	                                            break;
	                                            
			     case 3:                        if(Instruction_type==0) {
			    	                            clock++;
			    	                            int k=(~binary_dec(S[Tos]));		    	 //String k=dec_binary(~binary_dec(S[Tos]));
			    	                            S[Tos]=dec_binary(k);              //Not
			                                    }
			    	                            break;
			    	                            
			     case 4:                        Operand=getOperand();
			                                    S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])^Operand);       //XOR
                                                Tos=Tos-decrement_Tos_By;          
			    	                            break;
			    	                            
			     case 5:                        Operand=getOperand();
			    	                            S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])+Operand);       //Add
                                                Tos=Tos-decrement_Tos_By;          
                                                break;
                                                
			     case 6:                        Operand=getOperand();
			    	                            if(Instruction_type==0) {
			     	                            S[Tos-decrement_Tos_By]=dec_binary(Operand-binary_dec(S[Tos]));       //Sub
                                                Tos=Tos-decrement_Tos_By;          
                                                break;}
			                                    else if(Instruction_type==1){
                                                S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])-Operand);       //Sub
                                                Tos=Tos-decrement_Tos_By;          
                                                break;
			                                    }
			    	                            
   			     case 7:                        Operand=getOperand();
   			                                    S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])*Operand);       //Mul
                                                Tos=Tos-decrement_Tos_By;          
                                                break;
	 	
   			     case 8:                        Operand=getOperand();
	 		                                    if(Instruction_type==0) {
			     	                            S[Tos-decrement_Tos_By]=dec_binary(Operand%binary_dec(S[Tos]));       //Mod
                                                Tos=Tos-decrement_Tos_By;          
                                                break;
                      			                }
	 		                                    else if(Instruction_type==1){
			                                    S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])%Operand);       //Div
                                                Tos=Tos-decrement_Tos_By;          
                                                break;	   
			                                    }
	 		                                    
                 case 9:                        Operand=getOperand();
                                                if(Instruction_type==0) {
			    	                            S[Tos-decrement_Tos_By]=dec_binary(Operand%binary_dec(S[Tos]));       //Mod
                                                Tos=Tos-decrement_Tos_By;          
                                                break;
			                                    }
                                                else if(Instruction_type==1){
			                                    S[Tos-decrement_Tos_By]=dec_binary(binary_dec(S[Tos])%Operand);       //Mod
                                                Tos=Tos-decrement_Tos_By;          
                                                break;	   
			                                    }
                                                
                 case 10:                       if(Instruction_type==0) {
                	                            S[Tos]=dec_binary(binary_dec(S[Tos])<<1);                            //SL   
                                                }
                                                break;
                                                
                 case 11:                       if(Instruction_type==0) {
            	                                S[Tos]=dec_binary(binary_dec(S[Tos])>>1);                           //SR
                                                }
            	                                break;
            	                                
			     case 12:                       Operand=getOperand();
			                                    if(Instruction_type==0) {
			    	 
			    	                            S[Tos+1]=bool_binary(Operand>binary_dec(S[Tos]));                     //CPG
                                                Tos=Tos+1;          
                                                break;
                                                }
			                                    else if(Instruction_type==1){
			                                   	int r=read_from_stack(S[Tos]);
			                                    S[Tos+1]=bool_binary(r>Operand);                     //CPG       
                                                Tos=Tos+1;          
                                                break;
                                                }

			     case 13:                       Operand=getOperand();
			                                    if(Instruction_type==0) {
			    	                            S[Tos+1]=bool_binary(Operand<binary_dec(S[Tos]));                            //CPL
                                                Tos=Tos+1;          
                                                break;
                                                }
			                                    else if(Instruction_type==1){
                                                S[Tos+1]=bool_binary(binary_dec(S[Tos])<Operand);                            //CPL
                                                Tos=Tos+1;          
                                                break;
                                                }
			                                    
			     case 14:                       Operand=getOperand();
			                                    if(Instruction_type==0) {
			    	                            S[Tos+1]=bool_binary(Operand==binary_dec(S[Tos]));                            //CPE
                                                Tos=Tos+1;          
                                                break;
                                                }
			                                    else if(Instruction_type==1){
                                                S[Tos+1]=bool_binary(binary_dec(S[Tos])==Operand);                            //CPE
                                                Tos=Tos+1;          
                                                break;
                                                }
			                                    
			     case 15:                       if(Instruction_type==1) {
			    	                            PC=EA;      
			                                    }    	 
			    	                            break;
			    	                            
			     case 16:                       if(Instruction_type==1) {
			                                	String sa=S[Tos];String sb="1";
			    	                            if(S[Tos].equals("1")) {
			    	    	                    PC=EA;
			    	                            }
			    	    	                    Tos=Tos-1;
			    	    	                    }
			                                    break;
			                                    
			     case 17:                       if(Instruction_type==1) {
		    	                                if(S[Tos]=="0") {
		    	    	                        PC=EA;
		    	                                }
		    	    	                        Tos=Tos-1;
			                                    }
		    	                                break;
		    	                                
			     case 18:                       if(Instruction_type==1) {
			    	                            Tos=Tos+1;
			    	                            S[Tos]=PC;
			    	                            PC=EA;      
		                                        }
			                                    break;
			                                    
			     case 19:                       if(Instruction_type==0) {
			    	                            Tos=Tos+1;
			    	                            S[Tos]=SysteM.user_input();
			                                    }
			                                    break;

			     case 20:                       if(Instruction_type==0) {
			    	                            SysteM.cpu_output(S[Tos]);
			    	                            Tos=Tos-1;
			                                    }
			                                    break;
			     
			     case 21:                       if(Instruction_type==0) {
			    	                            PC=S[Tos];
			    	                            Tos=Tos-1;
			                                    }
			                                    break;
			                                    
			     case 22:                       if(Instruction_type==1) {
			    	             		    	Tos=Tos+1;
			    	                            S[Tos]=Memory.memory[binary_dec(EA)];
			    	                            
			                                    }
			                                    break;
			                                    
			     case 23:                       if(Instruction_type==1) {
			    	                            Memory.memory[binary_dec(EA)]=S[Tos];
			    	                            Tos=Tos-1;
			    	                            //(clock);
			                                    }
			     
			     case 24:                       if(Instruction_type==0) {
			    	                            length1=-1;
			    	                            loop=false;
			                                    }
			                                    break;
			                                    }
			     i++;
			Op[i-1]="";
		if(y==1) {try{
			    
				  PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter("./trace_"+SysteM.file,true)));
			      out1.printf("%26s",decimal_hex(Tos));
			      out1.printf("%24s",binary_hex(S[Tos]));
			      out1.printf("%20s",binary_hex(EA));
			      out1.printf("%22s",binary_hex(Memory.memory[binary_dec(EA)]));
			      out1.println();
			      out1.close();
	       		  }
			      catch(IOException e) {
					 }
				 
			      }
		    		}
	    	}
		}
	}
	
    public String bool_binary(boolean to_be_converted) {
	    if(to_be_converted) {
    	   	return new BigInteger(Integer.toString(1, 10)).toString(2);
    	}
    	else {
    		return new BigInteger(Integer.toString(0, 10)).toString(2);
    	}
    }
	public String increment(String to_be_converted) { // converting a hex word number to binary
		int decimal=Integer.parseInt(new BigInteger(to_be_converted, 2).toString(10));
		String ret=new BigInteger(Integer.toString(decimal+1, 10)).toString(2);
		while(ret.length()<7)
		    ret="0"+ret;
		    return ret;
			}
	static int binary_dec(String to_be_converted) {
		   int decimal=Integer.parseInt(to_be_converted, 2);
	       return decimal;
	}
	
	static String dec_binary(int to_be_converted) { // converting a hex word number to binary
	    String binary=null;
	    binary=new BigInteger(Integer.toString(to_be_converted), 10).toString(2); // converting the number from base 16 to base 2
	    return binary;
	}	
    static int getOperand() {
    	int i=0;
    	if(Instruction_type==0) {
    		i=binary_dec(S[Tos-1]);
    		}
    	else if(Instruction_type==1) {	
    		int ea=binary_dec(EA);
    		i=binary_dec(Memory.Memory("Read",ea,Z));
    		
    	}
		return i;
    }
    static String binary_hex(String to_be_converted) { // converting a hex word number to binary
	    String hex="";
	    hex=new BigInteger(to_be_converted, 2).toString(16); // converting the number from base 16 to base 2
	    
	    while(hex.length()<4)
	    hex="0"+hex;
	    return hex;
    }
    static String decimal_hex(int to_be_converted) { // converting a hex word number to binary
	    String hex=null;
	    hex=new BigInteger(Integer.toString(to_be_converted), 10).toString(16); // converting the number from base 16 to base 2
	    return hex;
    }
    static int read_from_stack(String to_be_converted) {
    	String s=to_be_converted;
    	while (s.length()<16) {
    		s="0"+s;
    	}
    	char c=s.charAt(0);
    	String num=new BigInteger(to_be_converted, 2).toString(10);
    	int val=0;
    	if(c=='1') {
    		val=-1*Integer.parseInt(num.substring(1));
    	}
    	else {
    		val=Integer.parseInt(s);
    	}
    	return val;
    }
}
