package IteratorTest;

import java.util.*;

public class IteratorTest
{

	public static void main(String[] args)
	{
		List<String> staff = new LinkedList<>();
		staff.add("Amy");
		staff.add("Bob");
		staff.add("Carl");
		
		Iterator iter = staff.iterator();
		String first = (String) iter.next();
		Object second = iter.next();
		iter.remove();
		System.out.println(second.getClass().toString());

	}

}
