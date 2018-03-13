import java.math.BigInteger;

public class Memory {

	static Error_Handler error_code=new Error_Handler();
	static String[] memory=new String[256];
	public static int index=0;
	
	static String binary_dec(String to_be_converted) { // converting a hex word number to binary
	    String decimal=null;
	    decimal=new BigInteger(to_be_converted, 2).toString(10); // converting the number from base 16 to base 2
	    return decimal;
	}
	static String dec_binary(String to_be_converted) { // converting a hex word number to binary
	    String binary=null;
	    binary=new BigInteger(to_be_converted, 10).toString(2); // converting the number from base 16 to base 2
	    
	    return binary;
	}
	
	public static String Memory(String X,int Y, String Z) {
		int YEA=Y;//Integer.parseInt(Y);
		try {	
		if (X=="Write") {
				memory[YEA]=Z;
			
					}
			else if(X=="Read") {
				Z=memory[YEA];
				}
			else {
				error_code.handle(201,"Error in Memory Read/Write instruction\n");
			}
			
			}catch(ArrayIndexOutOfBoundsException E) {
				error_code.handle(211,"Invalid reference to Memory address.\n");	
			}
		return Z;
	}
    }
