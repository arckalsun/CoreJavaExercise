package sieve;
import java.util.*;
/**
 * This program run the Sieve of Eratosthenes benchmark. It computes all primes up to 2,000,000.
 * @version 1.0 2016-11-07
 * @author john
 *
 */
public class SieveTest
{

	public static void main(String[] args)
	{
		int n = 2000000;
		long start = System.currentTimeMillis();
		BitSet b = new BitSet(n + 1);
		int count = 0;
		//int falsecount = 0;
		int i;
		for (i = 2; i <= n; i++) /* all set true */
			b.set(i);
		i = 2;
		while (i*i <= n) /* 2,3, ..., 1414 */
		{
			if (b.get(i))
			{
				count ++;
				int k = 2 * i; // 将2,3, ... 1414的倍数全设为false
				while (k <= n)
				{
					b.clear(k);
					k += i;
				}
			}
			/*else
				falsecount ++;*/
			i++;
		}
		//System.out.println("count = " + count +", falsecount = " + falsecount);
		while (i <= n)
		{
			if (b.get(i))
				count ++;
			i++;
			
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " primes");
		System.out.println((end - start) + " milliseconds");

	}

}
