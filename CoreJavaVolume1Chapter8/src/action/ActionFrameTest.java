package action;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class ActionFrameTest
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						JFrame actionfFrame = new  ActionFrame();
						actionfFrame.setTitle("PlafFrameTest");
						actionfFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						actionfFrame.setVisible(true);
					}
				}
				
				);

	}

}
