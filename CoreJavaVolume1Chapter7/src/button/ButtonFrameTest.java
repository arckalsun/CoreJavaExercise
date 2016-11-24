package button;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class ButtonFrameTest
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{
					JFrame frame = new ButtonFrame();
					frame.setTitle("ButtonFrameTest");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
				}

			});

	}

}
