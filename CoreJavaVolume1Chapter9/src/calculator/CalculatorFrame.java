package calculator;

import java.awt.Dimension;

import javax.swing.*;

/**
 * 
 * A Frame containing a panel for testing CalculatorPanel
 *
 */
class CalculatorFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalculatorFrame()
	{
		//setSize(500, 500);
		//setPreferredSize(new Dimension(500,500));
		//setSize(new Dimension(500,500));
		add(new CalculatorPanel());
		pack();
	}

}
