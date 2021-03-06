package server;
import java.io.*;

import java.net.*;
import java.util.Scanner;

public class EchoServer
{

	public static void main(String[] args) throws IOException
	{
		try (ServerSocket server = new ServerSocket(8189))
		{
			// wait for client connection
			try(Socket incoming = server.accept())
			{
				System.out.println(incoming.getRemoteSocketAddress());
				//System.out.println(incoming.getPort());
				
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();
				
				Scanner in = new Scanner(inStream);
				PrintWriter out = new PrintWriter(outStream, true /* autoFlush*/);
				
				out.println("Hello, Enter BYE to exit!");
				
				//echo client input
				boolean done = false;
				while (!done && in.hasNextLine())
				{
					String line = in.nextLine();
					out.println("Echo: " + line);
					System.out.println(line);
					if (line.trim().equals("BYE"))
						done = true;
				}
				
				
			}
			System.out.println("client closed!");
		}

	}

}
