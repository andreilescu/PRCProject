package operationsServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import exceptionCommands.ErrorCommand;
import exceptionCommands.ErrorCommandException;
import exceptionCommands.NonExistentCommand;
import exceptionCommands.NonexistentCommandException;

public class Commands 
{
	private NonExistentCommand nonexistentComm;
	private ErrorCommand errorComm;
	private boolean errorReal;
	private boolean nonExistentReal;
	private boolean finishedP = false;
	
	private StringBuilder linesString;
	
	public StringBuilder processCommand(String _intputCommand) throws InterruptedException, NonexistentCommandException, ErrorCommandException, IOException
	{
		linesString = new StringBuilder();
		String line = null;
		String result = null;
		
	    try 
	    {   
	    	nonexistentComm = new NonExistentCommand();
	    	nonExistentReal = nonexistentComm.VerifyNonExistentCommand(_intputCommand);
	    	
	    	//execute process
	        Process process = Runtime.getRuntime().exec(_intputCommand);  
	        process.waitFor();
	        
        	BufferedReader stdError = new BufferedReader(new
	             InputStreamReader(process.getErrorStream()));
	        
        	
        	errorComm = new ErrorCommand();
	        //exceptions
	        errorReal  = errorComm.VerifyError(_intputCommand, stdError);
	        
	        BufferedReader stdInput = new BufferedReader(new
		             InputStreamReader(process.getInputStream()));

	        // read the output from the command 
	        linesString.append("Command successful.\n");
	        while((line = stdInput.readLine()) != null)
	        {
	        	linesString.append(line+"\n");
	        }
	 
	        
	    } catch(NonexistentCommandException e)
	    {
	    	throw new NonexistentCommandException(_intputCommand);
		} catch (ErrorCommandException e) 
	    {
			throw new ErrorCommandException(_intputCommand);
		} catch (IOException e) 
	    {
			throw new ErrorCommandException(_intputCommand);
	    } catch(ArrayIndexOutOfBoundsException e)
	    {
	    	throw new ArrayIndexOutOfBoundsException();
	    }
	    
	    finishedP = true;
		return linesString;
	}

	public boolean finishCommandProcessing() 
	{
		return finishedP;
	}
}
