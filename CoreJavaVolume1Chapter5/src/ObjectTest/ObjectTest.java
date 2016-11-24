package ObjectTest;

public class ObjectTest
{

	public static void main(String[] args)
	{
		// String class
		String s = "Ok";
		StringBuilder sb = new StringBuilder(s);
		
		int hash = 0;
		
		for (int i = 0; i < s.length(); i++)
		{
			hash = 31 * hash + s.charAt(i);
		}
			
		System.out.println(hash);
		System.out.println(s.hashCode());
		System.out.println(sb.hashCode());
		System.out.println(sb.toString());

	}

}
