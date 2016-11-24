package test;
import java.util.*;
public class SortTest
{

	public static void main(String[] args)
	{
		List<Item> items = new ArrayList<>();
		items.add(new Item("four",4));
		items.add(new Item("five",5));
		items.add(new Item("six",6));
		items.add(new Item("three",3));
		
		Collections.sort(items);
		
		System.out.println(items);

	}

}

class Item implements Comparable<Item>
{
	private String a;
	private int b;
	
	public Item(String pa, int pb)
	{
		this.a = pa;
		this.b = pb;
	}
	public String toString()
	{
		return this.a + ": "+this.b;
	}
	public int compareTo(Item other)
	{
		return this.b - other.b;
	}
}
