package operationsServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class OperationsServer 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		/*if (args.length != 1) {
            System.err.println("Usage: java OperationsServer <port number>");
            System.exit(1);
        }*/
		
		//int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;
		
		try
		{
			InetSocketAddress addr  = new InetSocketAddress("127.0.0.1", 8899);
			
			ServerSocket serverSocket = new ServerSocket();
			serverSocket.bind(addr);
			
			System.out.println(serverSocket);
			while(listening)
			{
				new CommandsServerThread(serverSocket.accept()).start();
			}
		}catch (IOException e) 
		{
	            System.out.println("Exception caught when trying to listen on port 8889"
	                /*+ portNumber */+ " or listening for a connection");
	            System.out.println(e.getMessage());
	    }
	}
}

