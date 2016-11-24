package treemap;
import java.util.*;

public class WordCountTest
{

	public static void main(String[] args)
	{
		//读取
		Map<String,Integer> map = new HashMap<>();
		Scanner in = new Scanner(System.in);
		while (in.hasNext())
		{
			String word = in.next();
			word =word.replace(",", " ").replace(".", " ").replace("\"", " ").replace("“", " ").replace("”", " ").replace(";", " ");
			if (!map.containsKey(word))
				map.put(word, 1);
			else
				map.put(word, (map.get(word)+1));
		}
		//排序
		List<Map.Entry<String, Integer>> arraylist = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(arraylist,new Comparator<Map.Entry<String, Integer>>(){
			public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2)
			{
				return ((Integer) obj2.getValue()).compareTo((Integer) obj1.getValue());
			}
		});
		// 输出次数前20的单词
		List<Map.Entry<String, Integer>> list = arraylist.subList(0, 20);
		System.out.println("出现频率前20的单词：");
		for (Map.Entry<String, Integer> item : list)
			System.out.println(item.getKey() + "=" + item.getValue());
		
	}

}
