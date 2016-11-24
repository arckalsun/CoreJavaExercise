package pair1;
/**
 * 
 * @author john
 *
 */

public class PairTest1
{
	public static void main(String [] args)
	{
		String [] words = {"Marry", "had","a","little","lamb"};
		Pair<String> mm = ArrayAlg.minmax(words);
		System.out.println("min = " + mm.getFirst());
		System.out.println("max = " + mm.getSecond());
		
		double mid = ArrayAlg.getMiddle(3.14, 3.15, 3.16);
		System.out.println(mid);
	}
}

class ArrayAlg
{
	/**
	 * Gets the minimum and maximum of an array of string.
	 * @param a an array of strings.
	 * @return a pair with the min and max value, or null if a is null or empty.
	 */
	public static Pair<String> minmax(String [] a)
	{
		if (a == null || a.length == 0) return null;
		String min = a[0];
		String max = a[0];
		for (int i = 0; i < a.length; i++)
		{
			if (min.compareTo(a[i]) > 0) min = a[i];
			if (max.compareTo(a[i] )< 0) max = a[i];
		}
		
		return new Pair<>(min,max);
	}
	public static <T> T getMiddle(T... a)
	{
		return a[a.length / 2];
	}
}

