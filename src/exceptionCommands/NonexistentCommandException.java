package exceptionCommands;

public class NonexistentCommandException extends Exception 
{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String command;
	public NonexistentCommandException(String _commandToVerify)
	{
		command = _commandToVerify;
	}
	
	public String toString()
	{
		return "Comanda \"" + command +"\" nu exista.";
	}
}