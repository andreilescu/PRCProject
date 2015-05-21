package exceptionCommands;

public class ErrorCommandException extends Exception
{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String command;
	public ErrorCommandException(String _commandToVerify)
	{
		command = _commandToVerify;
	}
	
	public String toString()
	{
		return "Eroare. Comanda '" + command + "' nu a fost executata.";
	}
}
