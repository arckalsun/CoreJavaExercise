package simpleframe;


import java.awt.EventQueue;

import javax.swing.*;
/**
 * @version 1.0 2016-09-27
 * @author john
 *
 */
public class SimpleFrameTest
{

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
				{
			public void run()
			{
				SimpleFrame frame = new SimpleFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
				});

	}

}

class SimpleFrame extends JFrame
{
	private static final int DEFAULT_WIDTH = 300;
	private static final int DEFAULT_HEIGHT = 200;
	
	public SimpleFrame()
	{
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}