package priorityQueue;

import java.util.*;
/**
 * This program demonstrates the use of a priority queue.
 * @version 2016-11-04
 * @author john
 *
 */
public class PriorityQueueTest
{

	public static void main(String[] args)
	{
		
		PriorityQueue<GregorianCalendar> pq = new PriorityQueue<>();
		pq.add(new GregorianCalendar(1906, Calendar.DECEMBER, 9));
		pq.add(new GregorianCalendar(1815, Calendar.DECEMBER, 10));
		pq.add(new GregorianCalendar(1910, Calendar.JUNE, 22));
		
		System.out.println("Iterating over elements...");
		for (GregorianCalendar date : pq)
			System.out.println(date.get(Calendar.YEAR));
		System.out.println("Removing elements...");
		while (!pq.isEmpty())
			System.out.println(pq.remove().get(Calendar.YEAR));
		

	}

}
