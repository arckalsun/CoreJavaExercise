package plaf;

import java.awt.EventQueue;
import javax.swing.*;

public class PlafFrameTest
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						
						JFrame plafFrame = new PlafFrame();
						plafFrame.setTitle("PlafFrameTest");
						plafFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						plafFrame.setVisible(true);
						
						
					}
				});

	}

}
