package ObjectAndClasses;

import java.text.DateFormatSymbols;
import java.util.Calendar;
//import java.util.Date;
import java.util.GregorianCalendar;

/**
 * print the current month and mark current day
 * CalendarTest
 * @author john
 *
 */
public class UsingPredefinedClassDemo
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		// construct d as current date
		GregorianCalendar d = new GregorianCalendar();
		
		int today = d.get(Calendar.DAY_OF_MONTH);// 25
		int month = d.get(Calendar.MONTH);		 // 8 September ¾ÅÔÂ
		
		// set d to start date of the month
		d.set(Calendar.DAY_OF_MONTH, 1);		// 25 --> 1
		
		int weekday = d.get(Calendar.DAY_OF_WEEK); // 1
		
		// get first day of week (Sunday in the U.S.)
		int firstDayOfWeek = d.getFirstDayOfWeek(); // Calendar.SUNDAY
		
		// datermine the required indentation of the first line
		int indent = 0;
		while (weekday != firstDayOfWeek)
		{
			indent++;
			d.add(Calendar.DAY_OF_WEEK, -1);
			weekday = d.get(Calendar.DAY_OF_WEEK);
		}
		
		//print weekday names
		String[] weekdayNames = new DateFormatSymbols().getShortWeekdays();
		do
		{
			System.out.printf("%4s", weekdayNames[weekday]);
			d.add(Calendar.DAY_OF_MONTH, 1);
			weekday = d.get(Calendar.DAY_OF_WEEK);
			
		}
		while (weekday != firstDayOfWeek);
		System.out.println();
		
		for (int i = 1; i <= indent; i++)
		{
			System.out.print("    ");
		}
		
		d.set(Calendar.DAY_OF_MONTH,1);
		do
		{
			//print day
			int day = d.get(Calendar.DAY_OF_MONTH);
			System.out.printf("%3d", day);
			
			//mark current day with *
			if (day == today)
				System.out.print("*");
			else
				System.out.print(" ");
			
			//advance d to the next day
			d.add(Calendar.DAY_OF_MONTH, 1);
			weekday = d.get(Calendar.DAY_OF_WEEK);
			
			//start a new line at the start of the week
			if (weekday == firstDayOfWeek)
				System.out.println();
			
			
		}
		while (d.get(Calendar.MONTH) == month);
		// the loop exits when d is day 1 of the next month
		
		//print final end of line if necessary
		if (weekday != firstDayOfWeek)
			System.out.println();
		// test
		GregorianCalendar dd = new GregorianCalendar();
		System.out.println(dd.get(Calendar.YEAR));
		System.out.println(dd.get(Calendar.MONTH));
		System.out.println(dd.get(Calendar.WEEK_OF_YEAR));
		System.out.println(dd.get(Calendar.WEEK_OF_MONTH));
		System.out.println(dd.get(Calendar.DAY_OF_YEAR));
		System.out.println(dd.get(Calendar.DAY_OF_MONTH));
		System.out.println(dd.get(Calendar.DAY_OF_WEEK));
		System.out.println(dd.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println(dd.get(Calendar.SECOND));
		System.out.println(dd.getFirstDayOfWeek());
		
	}
	
}
