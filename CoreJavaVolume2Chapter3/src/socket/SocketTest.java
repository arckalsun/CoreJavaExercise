package socket;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.*;

/**
 * This program make a socket connection to the atomic clock in Boulder,
 * Colorado, and prints the time that the server sends.
 * 
 * @author john
 *
 */
public class SocketTest
{

	public static void main(String[] args) throws IOException
	{
		String hostname = "localhost";
		int port = 8189;

		try (Socket s = new Socket(hostname, port))
		{
			InputStream inStream = s.getInputStream();
			Scanner in = new Scanner(inStream);
			Scanner sin = new Scanner(System.in);
			
			PrintWriter out = new PrintWriter(s.getOutputStream(),true);
			boolean done = false;
			while (!done && in.hasNextLine())
			{
				System.out.println(in.nextLine());
				while (!done && sin.hasNextLine())
				{
					String line = sin.nextLine();
					out.println(line);
					if (line.trim().equals("BYE")) done = true;
				}
			}

		}

	}

	public static void InetSocketTest() throws UnknownHostException
	{
		InetAddress address = InetAddress.getLocalHost();
		System.out.println(address.toString());

		/*
		 * byte[] addressBytes = address.getAddress(); for (byte b :
		 * addressBytes) System.out.println(b);
		 */

		InetAddress[] addresses = InetAddress.getAllByName("baidu.com");
		for (InetAddress addr : addresses)
			System.out.println(addr.getHostAddress());

	}

	public static void socketTest(String hostname, int port) throws IOException
	{
		try (Socket s = new Socket())
		{
			s.connect(new InetSocketAddress(hostname, port), 10000);

			InputStream inStream = s.getInputStream();
			Scanner in = new Scanner(inStream);
			StringBuilder builder = new StringBuilder();

			while (in.hasNextByte())
			{
				// String line = in.nextLine();
				String ch = in.next();
				builder.append(ch);

			}
			System.out.print(builder.toString());
		}
	}

}
