package plaf;

import java.awt.event.WindowEvent;


import javax.swing.JOptionPane;

public class Terminator extends WindowAdapter
{
	public void windowClosing(WindowEvent e)
	{
		JOptionPane.showMessageDialog(null, "Are you closing?");
		System.exit(0);
	}
}
