package test;
import java.util.*;

public class DateTest
{

	public static void main(String[] args)
	{
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		
		System.out.println(date.getYear());
		System.out.println(c.get(Calendar.YEAR));
		System.out.println(date.getMonth());
		System.out.println(c.get(Calendar.MONTH));
	}

}
