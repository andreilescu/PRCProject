package operationsClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class OperationsClient extends Thread {

	private String command;

	private String response;

	private String hostName;

	private int port;

	public OperationsClient(String command) {
		this.command = command;
	}

	@Override
	public void run() {
		// String[] argss = new String[] { "127.0.0.1", "8887" };

		// // TODO Auto-generated method stub
		// if (argss.length != 2) {
		// System.err
		// .println("Usage: java OperationsClient <host name> <port number>");
		// System.exit(1);
		// }

		// String hostName = argss[0];
		// int portNumber = Integer.parseInt(argss[1]);
		hostName = "127.0.0.1";
		port = 8899;

		// ping -n 3 name

		try {
			Socket clientSocket = new Socket(hostName, port);
			PrintWriter outSendServ = new PrintWriter(
					clientSocket.getOutputStream());
			BufferedReader inReceiveServ = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(
					System.in));
			String fromServer;
			String fromUser;
			boolean connectionClosed = false;

			do {
				// read user input and send it to the server
				System.out.println("Command you want to send:");
				// fromUser = stdInput.readLine();
				fromUser = this.command;

				if (fromUser != null) {
					System.out.println("Client sent: " + fromUser);
					outSendServ.println(fromUser);
					outSendServ.flush();
				}

				this.response = "";
				// show what you got from server if server sent something
				while ((fromServer = inReceiveServ.readLine()) != null) {
					this.response = this.response + "\n" + fromServer;
					System.out.println("Server: " + fromServer);
					// exit while if server sent "Connection closed."
					if (fromServer.equals("Connection closed.")
							|| fromServer
									.equals("Netword timeout occurred ... terminating")) {
						connectionClosed = true;
						break;
					}

				}
			
				if (connectionClosed) {
					break;
				}
			} while (connectionClosed == false);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to "
					+ hostName);
			System.exit(1);
		}
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
