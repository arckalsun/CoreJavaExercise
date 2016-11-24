package EmployeeTest;


/**
 * This program tests the Employee class and demonstrates static methods and object construction.
 * @author john
 * @version v1.0
 */
public class EmployeeTest
{

	public static void main(String[] args)
	{
		// fill the staff array with three Employee objects
		Employee[] staff = new Employee[3];
		staff[0] = new Employee("Carl Cracker",75000, 1987, 12, 15);
		staff[1] = new Employee("Harry Cracker",75000, 1987, 12, 15);
		staff[2] = new Employee("Tony Cracker",75000, 1987, 12, 15);
		//raise everyone's salary by 5%
		for (Employee e : staff)
			e.raiseSalary(5);
		
		// print out information about all employee objects
		for (Employee e : staff)
		{
			//e.setId();
			System.out.println("name=" + e.getName() +", id=" + e.getId() + ",salary=" + e.getSalary() + ",hireDay=" + e.getHireDay());
		
		}
		int n = Employee.getNextId(); // calls static method	
		System.out.println("Next available id=" + n);
		//System.out.println(staff[1].equals(staff[2]));
	}

}
