package EmployeeTest;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

class Employee
{
	private static int nextId;
	
	private String name;
	private double salary;
	private int id;
	private Date hireDay;
	
	/*public static void main(String [] args)
	{
		System.out.println("This is Employee main method! ");
	}
	*/
	// static initialization block
	static
	{
		Random generator = new Random();
		// set nextId to a random number between 0 and 9999
		nextId = generator.nextInt(10000);
		
	}
	// object initialization block
	{
		id = nextId;
		nextId++;
	}
	// constructors
	public Employee()
	{
		//name ==> ""
		//salary ==> 0
		//id ==> initialized in initialization block
	}
	public Employee(double s)
	{
		// calls the Employee(String, double) constructor
		this("Employee #" + nextId, s);
		
	}
	public Employee(String n, double s)
	{
		name = n;
		salary = s;
		id = 0;
	}
	public Employee(String n, double s, int year, int month, int day)
	{
		name = n;
		salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year,month-1,day);
		hireDay = calendar.getTime();
		
	}
	public String getName()
	{
		return name;
	}
	public double getSalary()
	{
		return salary;
		
	}
	public int getId()
	{
		return id;
	}
	public void setId()
	{
		id = nextId;// set id to next available id
		nextId++;
	}
	public static int getNextId()
	{
		return nextId; // return static field
	}
	public Date getHireDay()
	{
		//return hireDay; // do not return references to mutable objects
		return (Date) hireDay.clone();
		
	}
	/**
	 * Raise the salary of an employee
	 * @param byPercent the percentage by which to raise the salary (e.g. 10 means 10%)
	
	 */
	public void raiseSalary(double byPercent)
	{
		double raise = salary * byPercent / 100;
		salary += raise;
	}
	public boolean equals(Employee other)
	{
		return name.equals(other.name);
		
	}

}
