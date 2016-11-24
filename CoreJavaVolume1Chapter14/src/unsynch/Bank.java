package unsynch;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A bank with a number of bank accounts.
 * 
 * @author john
 *
 */
public class Bank
{
	private final double[] accounts;
	private Lock bankLock = new ReentrantLock();

	/**
	 * Constructs the bank.
	 * 
	 * @param n
	 *            the number of accounts.
	 * @param initialBalance
	 *            the initial balance for each account.
	 */
	public Bank(int n, double initialBalance)
	{
		accounts = new double[n];
		for (int i = 0; i < accounts.length; i++)
			accounts[i] = initialBalance;
	}

	/**
	 * Transfers money from one account to another.
	 * 
	 * @param from
	 * @param to
	 * @param amount
	 */
	public void transfer(int from, int to, double amount)
	{
		bankLock.lock();
		try
		{
			if (accounts[from] < amount)
			{
				System.out.printf("%s\t账户%d: 余额%10.2f, 小于转账所需金额%10.2f\n",Thread.currentThread(),from, accounts[from],amount);
				return;
			}
				
			System.out.print(Thread.currentThread());
			accounts[from] -= amount;
			System.out.printf(" %10.2f from %d to %d", amount, from, to);
			accounts[to] += amount;
			System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
		} finally
		{
			bankLock.unlock();
		}
	}

	/**
	 * Gets the sum of all accounts balances.
	 * 
	 * @return the total balance
	 */
	public double getTotalBalance()
	{
		bankLock.lock();
		try
		{
			double sum = 0;
		for (double a : accounts)
			sum += a;

		return sum;
		}
		finally
		{
			bankLock.unlock();
		}
		
	}

	/**
	 * Gets the number of accounts in the bank.
	 * 
	 * @return
	 */
	public int size()
	{
		return accounts.length;
	}
}
