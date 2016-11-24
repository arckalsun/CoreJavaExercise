package randomAccess;

import java.util.*;

public class Employee
{
   private String name;
   private double salary;
   private Date hireDay;
   public static final int NAME_SIZE = 40; // 40 ¸ö×Ö·û£¬80¸ö×Ö½Ú
   public static final int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4 + 4 + 4;
   
   public Employee()
   {
   }

   public Employee(String n, double s, int year, int month, int day)
   {
      name = n;
      salary = s;
      GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day);
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

   public Date getHireDay()
   {
      return hireDay;
   }

   public void raiseSalary(double byPercent)
   {
      double raise = salary * byPercent / 100;
      salary += raise;
   }

   public String toString()
   {
      return getClass().getName() + "[name=" + name + ",salary=" + salary + ",hireDay=" + hireDay
            + "]";
   }
}
