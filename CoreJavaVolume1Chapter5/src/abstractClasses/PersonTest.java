package abstractClasses;
/**
 * This program demonstrates abstract class
 * @version 1.01 2016-09-26
 * @author arckal
 *
 */
public class PersonTest
{

	public static void main(String[] args)
	{
		Person [] people = new Person[2];
		
		// fill the people array with Student and Employee objects
		people[0] = new Employee("Harry Hacker", 50000, 1990, 10, 1);
		people[1] = new Student("Maria Morris", "computer science");
		
		// print out name and descriptions of all Person objects
		for (Person p : people)
			System.out.println(p.getName() + "," + p.getDescription());

	}

}
