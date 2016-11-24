package unsynch;

/**
 * A runnable that transfer money from one account to another in a bank.
 * @author john
 *
 */
public class TransferRunnable implements Runnable
{
	private Bank bank;
	private int fromAccount;
	private double maxAmount;
	private int DELAY = 5000;
	/**
	 * Constructs a transfer runnable.
	 * @param b
	 * @param from
	 * @param max
	 */
	public TransferRunnable(Bank b, int from, double max)
	{
		bank = b;
		fromAccount = from;
		maxAmount = max;
	}
	public void run()
	{
		try
		{
			for (int i = 0; i < 5; i++)
			{
				int toAccount = (int) (bank.size() * Math.random());
				double amount = maxAmount * Math.random();
				bank.transfer(fromAccount, toAccount, amount);
				Thread.sleep((int ) (DELAY * Math.random()) );
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace(System.err);
		}
	}
	
}
