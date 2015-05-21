package exceptionCommands;

import java.io.BufferedReader;
import java.io.IOException;

public class ErrorCommand 
{
	public boolean VerifyError(String _intputCommand, BufferedReader stdError) throws ErrorCommandException, IOException 
	{
		String s = "";
		// read any errors from the attempted command
        //System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) 
        {
            s += s;
            if(s != null)
            {
            	throw new ErrorCommandException(_intputCommand);
            }
        }
        
		return false;
	}
}
