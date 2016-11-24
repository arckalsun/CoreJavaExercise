package abstractClasses;

public class Student extends Person
{
	private String major;
	/**
	 * @param n the student's name
	 * @param m the student's major
	 */
	public Student(String n, String m)
	{
		// pass n to superclass contructor
		super(n);
		major = m;
	}

	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		return "a student majoring in " + major;
	}

}
