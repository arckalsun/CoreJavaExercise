package array;

import java.util.Arrays;

public class ArrayTest
{

	public static void main(String[] args)
	{
		
	
		
		int [] y = {};
		System.out.println(y);
		System.out.println(y.length);
		
		y = Arrays.copyOf(y, y.length+3);
		System.out.println(y);
		System.out.println(y.length);
		
		y = null;
		System.out.println();
		
	}

}
