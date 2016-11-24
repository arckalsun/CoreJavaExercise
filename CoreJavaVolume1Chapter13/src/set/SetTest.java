package set;

import java.util.*;
/**
 * This program demonstrates prints all unique words in System.in.
 * @author john
 * @version 2016-11-04
 */
public class SetTest
{

	public static void main(String[] args)
	{
		//TreeSet();
		HashSet();
	}
	public static void TreeSet()
	{
		SortedSet<String> sorter = new TreeSet<>();
		long totalTime = 0;
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			String word = in.next();
			long callTime = System.currentTimeMillis();
			sorter.add(word);
			callTime = System.currentTimeMillis() - callTime;
			totalTime += callTime;
			
		}
		
		for (String s : sorter)
			System.out.println(s);
		System.out.println("......");
		System.out.println(sorter.size() + " distinct words. " + totalTime + " milliseconds.");
		in.close();	
	}
	public static void HashSet()
	{
		Set<String> words = new HashSet<>();
		long totalTime = 0;
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			String word = in.next();
			long callTime = System.currentTimeMillis();
			words.add(word);
			callTime = System.currentTimeMillis() - callTime;
			totalTime += callTime;
			
		}
		Iterator<String> iter = words.iterator();
		for (int i = 1; i <= 20 && iter.hasNext(); i++)
		{
			System.out.println(iter.next());
		}
		System.out.println("......");
		System.out.println(words.size() + " distinct words. " + totalTime + " milliseconds.");
		in.close();	
	}

}
