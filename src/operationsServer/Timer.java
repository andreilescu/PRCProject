package operationsServer;

import java.io.PrintWriter;

public class Timer extends Thread
{
	//rate at which timer is checked
	protected int checkRate = 100;
	//length of timeout, length of time before timeout occurs
	private int timeLength;
	//time elapsed
	private int timeElapsed;
	
	private boolean timeoutOccured = false;
	private String timeoutMessage;
	public boolean isTimeoutOccured() 
	{
		return timeoutOccured;
	}
	public String getTimeoutMessage() 
	{
		return timeoutMessage;
	}
	private PrintWriter outToClient;
	
	public Timer(int _length, PrintWriter out)
	{
		timeLength = _length;
		outToClient = out;
		timeElapsed = 0;
	}
	
	//resets timer back to 0
	public synchronized void reset()
	{
		timeElapsed = 0;
	}
	
	public void run()
	{
		for(;;)
		{
			//put timer to sleep for the length specified
			try
			{
				Thread.sleep(checkRate); //miliseconds
			} catch(InterruptedException iex)
			{
				continue;
			}
			
			//synchronized to avoid conflicts
			synchronized(this)
			{
				//increment time remaining by adding the rate at which timer is checked
				timeElapsed += checkRate;
				
				//check to see if the time has been exceeded
				if(timeElapsed > timeLength)
				{
					//trigger timeout
					timeout();
				}
			}
		}
	}
	
	public void timeout()
	{
		timeoutOccured = true;
		outToClient.println("Netword timeout occurred ... terminating");
	}
}
