import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Error_Handler {

	public void handle(int error_code,String msg) {

			 int fatal_error=0;
			 try{
			   PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./output_"+SysteM.file,true)));
			   if(error_code>=100 && error_code<199){
			      fatal_error=1;
			      out.println("Exception in LOADER");	
			   }
			   if(error_code>=200 && error_code<299){
			      fatal_error=1;
			      out.println("Exception in MEMORY");
			   }
			   if(error_code>=300 && error_code<399){
			      fatal_error=1;
			      out.println("Exception in SYSTEM");
			   }
			   if(error_code>=400 && error_code<499){
			      fatal_error=1;
			      out.println("Exception in CPU");
			   }
			   if(error_code>=500 && error_code<599){
			      out.println("Warning in LOADER");
			   }
			   if(error_code>=600 && error_code<699){
			      out.println("Warning in MEMORY");
			   }
			   if(error_code>=700 && error_code<799){
			      out.println("Warning in SYSTEM");
			   }
		       if(error_code>=450 && error_code<500){
			   out.println("Warning in CPU");	
			   }
			   out.println("Warning:"+error_code+" "+msg);
			   if(fatal_error==0){out.println("Normal Termination");}
			   out.println();
			   out.close();
		         }
			 catch(IOException e){e.printStackTrace();}
			    if(fatal_error==1){
			      try{
			      PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./output_"+SysteM.file,true)));
			      out.println("Abnormal Termination");out.println();out.close();
			      System.exit(0);
			      }
			      catch(IOException e){e.printStackTrace();}	

			    }
			  
			  
			}

	
	
	
	
	}


