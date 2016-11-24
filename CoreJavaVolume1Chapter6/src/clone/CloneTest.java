package clone;

/**
 * This program demonstrates cloning.
 * @version 1.0 2016-09-27
 * @author john
 *
 */
public class CloneTest
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		try
		{
			Employee original = new Employee("John Q. Public", 50000);
			original.setHireDay(2016, 9, 27);
			Employee copy = original.clone();
			copy.raiseSalary(10);
			copy.setHireDay(2008, 8, 8);
			System.out.println("Original=" + original);
			System.out.println("Copy=" + copy);
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}

	}

}
