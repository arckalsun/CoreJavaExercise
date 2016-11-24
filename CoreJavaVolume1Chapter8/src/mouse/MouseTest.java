package mouse;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MouseTest
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						JFrame mouseFrame = new MouseFrame();
						mouseFrame.setTitle("mouseFrameTest");
						mouseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						mouseFrame.setVisible(true);
					}
			
				}
				);

	}

}
