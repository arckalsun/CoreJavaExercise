package inheritance;

public class Manager extends Employee
{
	private double bonus;
	/**
	 * @param n the employee's name
	 * @param s the salary
	 * @param year the hire year
	 * @param month the hire month
	 * @param day the hire day
	 */
	public Manager(String n, double s, int year, int month, int day)
	{
		super(n, s, year, month, day);
		bonus = 0;
	}
	
	public double getSalary()
	{
		double baseSalary = super.getSalary();
		return baseSalary + bonus;
	}
	
	public void setBouns(double b)
	{
		bonus = b;
	}
	
	public boolean equals(Object otherObject)
	{
		if (!super.equals(otherObject)) return false;
		Manager other = (Manager) otherObject;
		// super.euqals checked that this and other belong to the same class
		return bonus == other.bonus;
	}
	public int hashCode()
	{
		return super.hashCode() + 17 * new Double(bonus).hashCode(); 
	}
	public String toString()
	{
		return super.toString() + "[bonus=" + bonus + "]";
	}
}
