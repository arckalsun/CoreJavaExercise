package calculator;

import javax.swing.*;
import java.awt.EventQueue;

public class CalculatorPanelTest
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						JFrame plafFrame = new CalculatorFrame();
						
						plafFrame.setTitle("CalculatorPanelTest");
						plafFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						plafFrame.setVisible(true);
						
					
						
						
					}
				});

	}

}
