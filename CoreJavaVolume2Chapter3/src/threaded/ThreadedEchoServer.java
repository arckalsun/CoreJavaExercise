package threaded;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This program implements a multithreaded server that listens to port 8189 and echoes back all client input.
 * @author john
 *
 */
public class ThreadedEchoServer
{
	private static ServerSocket s;
	
	public static void main(String[] args)
	{
		int i = 1;
		try (ServerSocket s = new ServerSocket(8189))
		{
			while (true)
			{
				Socket incoming = s.accept();
				System.out.println("Spawning " + i + "\t" + incoming.getRemoteSocketAddress());
				Runnable r = new ThreadedEchoHandler(incoming);
				Thread t =new Thread(r);
				t.start();
				i++;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		

	}

}

/**
 * This class handles the client input for one server socket connection.
 */
class ThreadedEchoHandler implements Runnable
{
	private Socket incoming;
	
	/**
	 * Constructs a hanlder.
	 * @param i the incoming socket
	 */
	public ThreadedEchoHandler(Socket i)
	{
		incoming = i;
	}
	public void run()
	{
		try
		{
			try
			{
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();
				
				Scanner in = new Scanner(inStream);
				PrintWriter out = new PrintWriter(outStream,true /* AutoFlush */);
				
				out.println("Hello, Enter BYE to exit.");
				
				// echo client input
				boolean done = false;
				while (!done && in.hasNextLine())
				{
					String line = in.nextLine();
					out.println("Echo: " + line);
					System.out.println(incoming.getRemoteSocketAddress()+":\t"+line);
					if (line.trim().equals("BYE")) done = true;
					
				}
				
			}
			finally
			{
				incoming.close();
				System.out.println(incoming.getRemoteSocketAddress()+"closed.");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
