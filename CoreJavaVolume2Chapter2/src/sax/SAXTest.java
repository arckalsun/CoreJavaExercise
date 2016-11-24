package sax;

import java.io.*;
import java.net.URL;

import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This program demonstrates how to use a SAX parser. The program prints all
 * hyperlinks of an XHTML web page.<br>
 * 
 * @author john
 *
 */
public class SAXTest
{
	public static void main(String[] args) throws Exception
	{
		String url;
		if (args.length == 0)
		{
			url = "http://www.w3c.org";
			System.out.println("Using " + url);
		} else
			url = args[0];

		DefaultHandler handler = new DefaultHandler()
		{
			public void startElement(String namespaceURI, String lname, String qname, Attributes attrs)
			{
				if (lname.equals("a") && attrs != null)
				{
					for (int i = 0; i < attrs.getLength(); i++)
					{
						String aname = attrs.getLocalName(i);
						if (aname.equals("href"))
							System.out.println(attrs.getValue(i));
					}
				}
			}
		};
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		SAXParser parser = factory.newSAXParser();
		InputStream in = new URL(url).openStream();
		parser.parse(in, handler);
		

	}
}
