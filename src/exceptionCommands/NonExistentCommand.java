package exceptionCommands;

public class NonExistentCommand 
{
	public boolean VerifyNonExistentCommand(String _commandToVerify) throws NonexistentCommandException
	{
		if(((_commandToVerify == " ") || (_commandToVerify == "\t")
				|| (_commandToVerify == "\n")) || (_commandToVerify.isEmpty()))
		{
			throw new NonexistentCommandException(_commandToVerify);
		}
		
		return false;
	}
}
