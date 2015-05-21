package operationsServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import exceptionCommands.ErrorCommandException;
import exceptionCommands.NonexistentCommandException;

public class CommandsServerThread extends Thread
{
	private Socket socket = null;
	
	public CommandsServerThread(Socket _socket)
	{
		super("CommandsServerThread");
		this.socket = _socket;
	}
	
	public void run()
	{
		try
		{
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream())); 
	    
	        String inputLine, outputLine;
	        
	        //initiate conversation with client
	        Commands commands = new Commands();
	        
	        /*outputLine = commands.processCommand("0");
	        if(outputLine == "Connection started.")
	        	out.println(outputLine);
	        */
	        while(true)
	        {
	        	//read what has been sent by the client
	        	inputLine = in.readLine();
	        	
	        	//timer
	        	Timer timer = new Timer(10000, out);
	        	
	        	//process what the client has sent
	        	if(inputLine != null)
	        	{
	        		if(inputLine.equals("exit"))
        			{
        				out.println("Connection closed.");
        				break;
        			}
	        		
	        		try
	        		{
	        			//start timer to see if timeout occurs
	    	        	timer.start();
	        			
		        		//process command service required
	        			StringBuilder stringBuilder;
	        			stringBuilder = commands.processCommand(inputLine);
	        			
			        	if(commands.finishCommandProcessing() == true)
			        	{
			        		//if the command is "exit", finish connection
			        		Scanner scan = new Scanner(stringBuilder.toString()); // I have named your StringBuilder object sb
			        		while (scan.hasNextLine())
			        		{
			        			outputLine = scan.nextLine();
				        		out.println(outputLine);
			        		}
			        		timer.reset();
			        		timer.stop();
			        	}
			        	else
			        	{
			        		out.println("Command was processed but an error occured.\n");
			        		out.println("Try a new command again.\n");
			        		break;
			        	}
	        		} catch(NonexistentCommandException e)
	        	    {
	        	    	out.println(e.toString());
	        		} catch (ErrorCommandException e) 
	        	    {
	        			out.println(e.toString());
	        	    }
        			catch(ArrayIndexOutOfBoundsException e)
	        		{
	        			out.println("Comanda este invalida.");
	        		}
	        	}
	        }
	        
	        socket.close();
	
		}catch(IOException e)
		{
			System.err.println("A client was disconnected because a timeout occured. "+socket.getRemoteSocketAddress());
		} catch (InterruptedException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

